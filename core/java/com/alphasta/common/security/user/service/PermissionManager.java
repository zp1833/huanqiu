package com.alphasta.common.security.user.service;

import java.util.Set;

import com.alphasta.common.Constants;
import com.alphasta.common.core.exception.ApplicationException;
import com.alphasta.common.core.service.BaseManager;
import com.alphasta.common.core.util.ReflectUtil;
import com.alphasta.common.security.acegi.cache.AcegiCacheManager;
import com.alphasta.common.security.user.model.Permission;
import com.alphasta.common.security.user.model.Resource;
import com.alphasta.common.security.user.model.Role;

/**
 * @author SAM
 * 
 */
public class PermissionManager extends BaseManager {
  
  /**
   * 用于同步缓存
   */
  private AcegiCacheManager acegiCacheManager;

  /**
   * @see PermissionManager#getByRole(Role)
   */
  @SuppressWarnings("unchecked")
  public Permission[] getByRole(Role role) {
    Set permSet = role.getPermissions();
    Permission[] retPerms = new Permission[permSet.size()];
    permSet.toArray(retPerms);
    return retPerms;
  }

  /**
   * @see PermissionManager#isNameInUse(java.lang.String)
   */
  public boolean isNameInUse(String name) {
    Permission perm = new Permission();
    perm.setName(name);
    if (log.isDebugEnabled()) {
      log.debug("check for permission name '" + name + "'");
    }
    return exists(perm, "name"); // 如果给定name不唯一，则表示正在使用
  }

  /**
   * @see PermissionManager#savePerm(Permission)
   */
  @Override
  public void save(Object object) {
    if (object == null || !(object instanceof Permission)) {
      return;
    }
    Permission perm = (Permission) object;
    if (!exists(perm, "name")) {
      if (perm.getId() != null) { //更新
        Permission old = getDao().getObject(Permission.class, perm.getId());
        ReflectUtil.copyProperties(old, perm, 
            new String[]{"name", "descn", "operation", "version"});
        if (perm.getStatus() != null) {
          old.setStatus(perm.getStatus());
        }
        getDao().saveObject(old);
      } else { //新建
        perm.setStatus(Constants.STATUS_AVAILABLE);
        getDao().saveObject(perm);
      }
      if (acegiCacheManager != null) {
        acegiCacheManager.onPermissionChanged(perm);
      }
    } else {
      throw new ApplicationException("Reduplicate permission name ["
          + perm.getName() + "]");
    }
    
  }

  /**
   * @return the acegiCacheManager
   */
  public AcegiCacheManager getAcegiCacheManager() {
    return acegiCacheManager;
  }

  /**
   * @param acegiCacheManager the acegiCacheManager to set
   */
  public void setAcegiCacheManager(AcegiCacheManager acegiCacheManager) {
    this.acegiCacheManager = acegiCacheManager;
  }

  /**
   * @see BaseManager#remove(java.lang.Object)
   */
  @Override
  public void remove(Object object) {
    if (object == null || !(object instanceof Permission)) {
      return;
    }
    Permission perm = (Permission) object;
    if (perm == null || perm.getId() == null) {
      return;
    }
    // 删除与资源的关联关系
    Set<Resource> resSet = perm.getResources();
    Resource[] resources = resSet.toArray(new Resource[] {});
    log.debug("Remove the relations between Persmission and Resource");
    for (int i = 0; i < resources.length; i++) {
      resources[i].getPermissions().remove(perm);
      perm.getResources().remove(resources[i]);
      //saveObject(resources[i]);
    }
    // 删除与角色的关联关系
    Set<Role> roleSet = perm.getRoles();
    Role[] roles = roleSet.toArray(new Role[] {});
    log.debug("Remove the relations between Persmission and Roles");
    for (int i = 0; i < roles.length; i++) {
      roles[i].getPermissions().remove(perm);
      perm.getRoles().remove(roles[i]);
      //saveObject(roles[i]);
    }
    // 同步缓存
    if (acegiCacheManager != null) {
      acegiCacheManager.beforePermissionRemoved(perm);
    }    
    
    super.remove(perm);
  }

}
