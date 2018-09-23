package com.alphasta.common.security.user.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.acegisecurity.providers.encoding.PasswordEncoder;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.alphasta.common.Constants;
import com.alphasta.common.core.service.BaseManager;
import com.alphasta.common.core.util.ReflectUtil;
import com.alphasta.common.security.user.UserConstants;
import com.alphasta.common.security.user.dao.UserDAO;
import com.alphasta.common.security.user.model.Permission;
import com.alphasta.common.security.user.model.User;
import com.alphasta.common.security.user.service.listener.UserChangeListener;

/**
 * 用户管理类
 * @author Sam Lee
 *
 */
@SuppressWarnings("unchecked")
public class UserManager extends BaseManager {
  /**
   * 用于加密密码
   */
  private PasswordEncoder passwordEncoder;
  /**
   * <code>User</code>对象持久化操作监听器，可以直接注入UserListener的实例
   * 或完整的类名。
   * @see {@link com.alphasta.common.security.user.service.listener.UserChangeListener}
   */
  private List userChangeListeners;
  /**
   * JdbcTemplate，用户获取密码
   */
  private JdbcTemplate jdbcTemplate;
   /**
     * 获得具有某一<code>Permission</code>的用户
     * @param perm 给定<code>Permission</code>
     * @return List of User or empty list.
     */
  public List<User> findUsersByPermission(Permission perm) {
    return ((UserDAO) getDao()).findUsersByPermission(perm);
  }
   /**
     * 返回给定用户<code>User</code>所具有的权限（<code>Permission</code>）
     * @param user 给定用户
     * @return List of User or empty list.
     */
  public List<Permission> findPermissionsByUser(User user) {
    return ((UserDAO) getDao()).findPermissionsByUser(user);
  }
   /**
     * 根据登录名和密码得到<code>User</code>
     * @param loginId 登录名
     * @param password 密码
     * @return Instance of <code>User</code> or null.
     */
  public User getUser(String loginId, String password) {
    List<User> users = getDao().find(
        "from User user where user.loginId=? and user.password=?",
        new Object[] { loginId, password });
    if (users.isEmpty()) {
      return null;
    }
    return users.get(0);
  }
   /**
     * 根据用户登录名获得<code>User</code>对象
     * @param loginId 登录ID
     * @return Instance of <code>User</code> or null.
     */
   public User getUser(String loginId) {
     List<User> users = getDao().find("from User user where user.loginId=?", 
         loginId);
     if (users.isEmpty()) {
       return null;
     }
     return users.get(0);
   }
   /**
     * 更新用户的登录时间（当前时间）和IP地址
     * @param user 给定用户
     * @param loginIp 给定用户登录所在IP
     */
   public void updateLoginInformation(User user, String loginIp) {
     if (user == null || user.getId() == null) {
       log.error("No user login, return only.");
       return;
     }
     
     user.setLastLoginIp(loginIp);
     user.setLastLoginTime(new Date());
     if (user.getLoginTimes() == null) {
       user.setLoginTimes(1);
     } else {
       user.setLoginTimes(user.getLoginTimes().intValue() + 1);
     }
     
     getDao().saveObject(user);
   }   
   
  /**
   * @see BaseManager#save(java.lang.Object)
   */
  @Override
  public void save(Object object) {
    if (object == null || !(object instanceof User)) {
      return;
    }
    
    save((User) object, true);
  }
  /**
   * 保存用户信息，并根据下面的情况决定是否加密密码:<br><pre>
   * <ul>
   *    <li>encodePassword属性不得为null.</li>
   *    <li>如果是新建用户。</li>
   *    <li>如果是修改用户，并且输入的密码为空字符串或UserConstants.DEFAULT_PASSWORD。
   *    此时使用原来的密码</li>
   * </ul>
   * </pre>
   * 
   * @param user <code>User</code> to save.
   * @param encodePassword true if encode password.
   */
  public void save(User user, boolean encodePassword) {    
    if (user.getId() == null) { //新建用户的状态为正常
      user.setStatus(Constants.STATUS_AVAILABLE);
    }
    
    User oldUser = new User(); //用于同步缓存
    oldUser.setLoginId(user.getLoginId());
    oldUser.setId(user.getId());
    //如果是修改，并且输入的密码为空或UserConstants.DEFAULT_PASSWORD，则表示使用原来的密码
    if (user.getId() != null
        && (user.getPassword().equals(UserConstants.DEFAULT_PASSWORD) 
            || StringUtils.isBlank(user.getPassword()))) {
      String password = getOldPassword(user.getId());
      log.debug("Use old password:" + password);
      user.setPassword(password);
      //解决a different object with the same identifier value 
      //was already associated with the session的问题
      getDao().evict(get(User.class, user.getId()));
      getDao().getHibernateTemplate().update(user);
    } else {
      if (passwordEncoder != null && encodePassword) { // 密码加密
        user.setPassword(passwordEncoder.encodePassword(user.getPassword(),
            null));
      }
      getDao().getHibernateTemplate().save(user);
    }
    
    //同步缓存
    synchronousCache(user, oldUser);
  }
  
  /**
   * 修改密码
   * @param model 封装密码的User对象 
   * @param oldPassword 旧的密码
   * @throws Exception 
   */
  public void changePassword(User model, String oldPassword) throws Exception { 
  	//获得加密后的密码
  	String encodePasswrod = passwordEncoder.encodePassword(oldPassword, null);
  	//判断输入的原密码和数据库中的原密码是否一致
  	if (encodePasswrod.equals(getOldPassword(model.getId()))) {
  		User user = get(User.class, model.getId());
  		//设置新密码
  		user.setPassword(passwordEncoder.encodePassword(
  			model.getPassword(), null));
  		getDao().getHibernateTemplate().update(user);
  		//同步缓存
  		synchronousCache(user, model);
  	} else {
  		throw new Exception("旧密码不正确!");
  	}
  }
  
  /**
   * 同步缓存
   * @param user 新用户
   * @param oldUser 旧用户
   */
  @SuppressWarnings("unused")
	private void synchronousCache(User user, User oldUser) {
  	if (userChangeListeners != null) { 
			//执行UserChangeListener，实现缓存同步等功能
      for (Iterator itr = userChangeListeners.iterator(); itr.hasNext();) {
        UserChangeListener listener = getListener(itr.next());
        listener.onSave(user, oldUser);
      }
    }
  }
  
  /**
   * 返回指定UserId的密码
   */
  private String getOldPassword(Integer userId) {
    return (String) jdbcTemplate.query("select password from users where id=?",
        new Object[] { userId }, new ResultSetExtractor() {

          public Object extractData(ResultSet rs) throws SQLException {
            rs.next();
            return rs.getString(1);
          }

        });
  }
  
  /**
   * @see BaseManager#remove(java.lang.Object)
   */
  @Override
  public void remove(Object object) {
    if (object == null || !(object instanceof User)) {
      return;
    }
    User user = (User) object;
    user.setStatus("0");
    if (userChangeListeners != null) {
      for (Iterator itr = userChangeListeners.iterator(); itr.hasNext();) {
        UserChangeListener listener = getListener(itr.next());
        listener.onRemove((User) object);
      }
    }        
  }
  
  /**
   * @param userChangeListeners the userChangeListeners to set
   */
  public void setUserChangeListeners(List userChangeListeners) {
    this.userChangeListeners = userChangeListeners;
  }
  
  /**
   * 执行监听器
   * @see UserListener
   */
  private UserChangeListener getListener(Object obj) {
    if (obj == null) {
      return null;
    }
    if (obj instanceof String) {
      return (UserChangeListener) ReflectUtil.newInstance((String) obj);
    } else {
      return (UserChangeListener) obj;
    }
  }
  /**
   * @param passwordEncoder the passwordEncoder to set
   */
  public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }
  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }
}
