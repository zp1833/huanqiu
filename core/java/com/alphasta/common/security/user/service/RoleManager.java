package com.alphasta.common.security.user.service;

import java.util.Set;

import com.alphasta.common.core.exception.ApplicationException;
import com.alphasta.common.core.service.BaseManager;
import com.alphasta.common.core.util.ReflectUtil;
import com.alphasta.common.security.acegi.cache.AcegiCacheManager;
import com.alphasta.common.security.user.model.Permission;
import com.alphasta.common.security.user.model.Role;
import com.alphasta.common.security.user.model.User;

/**
 * 角色管理
 * @author sam
 *
 */
public class RoleManager extends BaseManager {
  
  /**
   * <code>AcegiCacheManager</code>用于同步缓存
   */
  private AcegiCacheManager acegiCacheManager;

  /**
   * @see RoleManager#getRolesByUser(User)
   */
  @SuppressWarnings("unchecked")
  public Role[] getRolesByUser(User user) {
    Set roleSet = user.getRoles();
    Role[] retRoles = new Role[roleSet.size()];
    roleSet.toArray(retRoles);
    return retRoles;
  }

  /**
   * @see RoleManager#isRoleNameInUse(java.lang.String)
   */
  public boolean isRoleNameInUse(String rolename) {
    Role role = new Role();
    role.setName(rolename);
    if (log.isDebugEnabled()) {
      log.debug("check for role name '" + rolename + "'");
    }
    return exists(role, "name"); // 如果给定name不唯一，则表示正在使用
  }

  /**
   * 
   */
  @Override
  public void save(Object object) {    
    if (object == null || !(object instanceof Role)) {
      return;
    }
    Role role = (Role) object;
    if (!exists(role, "name")) {
      if (role.getId() != null) {
        Role old = getDao().getObject(Role.class, role.getId());
        ReflectUtil.copyProperties(old, role, 
            new String[]{"name", "descn", "version", "memo"});
        getDao().saveObject(old);
      } else {
        getDao().saveObject(role);
      }
      if (acegiCacheManager != null) {
        acegiCacheManager.onRoleChanged(role);
      }
    } else {
      throw new ApplicationException(role.getName());
    }
  }

  
  /**
   * @return the acegiCacheManager
   */
  public AcegiCacheManager getAcegiCacheManager() {
    return acegiCacheManager;
  }

  /**
   * @see com.alphasta.common.service.BaseManager#remove(java.lang.Object)
   */
  @Override
  public void remove(Object object) {
    if (object == null || !(object instanceof Role)) {
      return;
    }
    Role role = (Role) object;
    if (role == null || role.getId() == null) {
      return;
    }
    //删除与用户之间的关联关系
    Set<User> userSet = role.getUsers();
    User[] users = userSet.toArray(new User[]{});
    log.debug("Remove the relations between Role and User");
    for (int i = 0; i < users.length; i++) {
      users[i].getRoles().remove(role);
      role.getUsers().remove(users[i]);
      //saveObject(users[i]);
    }
   //  删除与权限之间的关联关系
    Set<Permission> permSet = role.getPermissions();
    Permission[] perms = permSet.toArray(new Permission[]{});
    log.debug("Remove the relations between Persmission and Role");
    for (int i = 0; i < perms.length; i++) {
      perms[i].getRoles().remove(role);
      role.getPermissions().remove(perms[i]);
      //saveObject(perms[i]);
    }
    //同步缓存
    if (acegiCacheManager != null) {
      acegiCacheManager.beforeRoleRemoved(role);
    }
    
    super.remove(role);
  }

 
  /**
   * @param acegiCacheManager the acegiCacheManager to set
   */
  public void setAcegiCacheManager(AcegiCacheManager acegiCacheManager) {
    this.acegiCacheManager = acegiCacheManager;
  }

}
