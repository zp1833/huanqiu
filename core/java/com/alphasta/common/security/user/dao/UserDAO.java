package com.alphasta.common.security.user.dao;

import java.util.List;

import com.alphasta.common.Constants;
import com.alphasta.common.core.dao.hibernate.BaseHibernateDAO;
import com.alphasta.common.security.user.model.Permission;
import com.alphasta.common.security.user.model.User;

/**
 * User Data Access Object。
 * @author Sam Lee
 *
 */
@SuppressWarnings("unchecked")
public class UserDAO extends BaseHibernateDAO {
  /**
   * 获得具有某一<code>Permission</code>的用户
   * 
   * @param perm  给定<code>Permission</code>
   * @return List of User or empty list.
   */
  public List<User> findUsersByPermission(Permission perm) {
    String hql = "select user from Permission permi "
        + "inner join permi.roles as role "
        + "inner join role.users as user where permi.status='"
        + Constants.STATUS_AVAILABLE + "' and permi=?";

    return find(hql, perm);
  }

  /**
   * 返回给定用户<code>User</code>所具有的权限（<code>Permission</code>）
   * 
   * @param user  给定用户
   * @return List of User or empty list.
   */

  public List<Permission> findPermissionsByUser(User user) {
    String hql = "select permi from Permission permi"
        + " inner join permi.roles as role "
        + " inner join role.users as user where permi.status='"
        + Constants.STATUS_AVAILABLE + "' and user=?";

    return find(hql, user);
  }
}
