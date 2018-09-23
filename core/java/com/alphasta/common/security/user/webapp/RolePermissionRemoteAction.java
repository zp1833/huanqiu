package com.alphasta.common.security.user.webapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.alphasta.common.core.dao.support.Page;
import com.alphasta.common.core.util.ReflectUtil;
import com.alphasta.common.core.webapp.struts2.BaseAction;
import com.alphasta.common.security.user.model.Permission;
import com.alphasta.common.security.user.model.Role;
import com.alphasta.common.security.user.service.PermissionManager;
import com.alphasta.common.security.user.service.RoleManager;

/**
 * 通过DWR远程调用。为角色分配权限，或者删除一个角色所具有的权限。
 * @author yyf
 */
@SuppressWarnings({"serial", "unchecked" })
public class RolePermissionRemoteAction extends BaseAction {
  /**
   * 为用户添加的权限暂存在Session中
   */
  private static final String TEMPLATE_ADDED_PERMISSIONS = 
    "permissions_added_session";

  /**
   * 为角色删除的权限暂存在Session中
   */
  private static final String TEMPLATE_REMOVED_PERMISSIONS = 
    "permissions_removed_session";

  /**
   * Manager for role
   */
  private RoleManager roleManager;

  /**
   * Manager for permission
   */
  private PermissionManager permissionManager;

  /**
   * 返回所有权限，同时查询指定角色所具有的权限， 并在“所有”权限中标注该角色所拥有的权限的选择状态
   * @param roleId 指定角色id
   * 
   * @return
   */
  public Page getPermissionsOfRole(Integer roleId, int pageNo, int pageSize,
      String permissionName) {
    Role role = roleManager.get(Role.class, roleId);
    if (role == null) {
      log.warn("Role with id " + roleId + "does not exists.");
      return Page.EMPTY_PAGE;
    }
    // 得到该角色的权限
    Set<Permission> permissionsOfRole = role.getPermissions();
    log.debug("Role " + role.getId() + "has " + permissionsOfRole.size()
        + "permissions.");
    Page page = null;
    if (StringUtils.isBlank(permissionName)) {
      page = permissionManager.query("from Permission p", pageNo, pageSize);
    } else {
      page = permissionManager.query("from Permission p where p.name like ?",
          pageNo, pageSize, "%" + permissionName + "%");
    }
    // 得到所有权限
    List allPermissions = page.getData();
    // 从session中取得暂存的待分配的权限
    Set templatePermissionsAdded = getTemporaryPermissions(role,
        TEMPLATE_ADDED_PERMISSIONS);
    // 从session中取得暂存的待反分配的权限
    Set templatePermissionsRemoved = getTemporaryPermissions(role,
        TEMPLATE_REMOVED_PERMISSIONS);
    List mapPerms = new ArrayList(allPermissions.size());
    
    for (Iterator itr = allPermissions.iterator(); itr.hasNext();) {
      Permission permission = (Permission) itr.next();
      permission.setChanged(false);
      if (permissionsOfRole.contains(permission)) { // 如果权限已经分配给角色则选中
        permission.setChanged(true);
      }
      // 如果权限暂时分配了则选中
      if (templatePermissionsAdded != null
          && templatePermissionsAdded.contains(permission.getId())) {
        permission.setChanged(true);
      }
      // 如果权限暂时删除了则不选
      if (templatePermissionsRemoved != null
          && templatePermissionsRemoved.contains(permission.getId())) {
        permission.setChanged(false);
      }
     //    转换为Map，防止延迟加载
      Map mapPerm = ReflectUtil.toMap(permission, 
          new String[]{"id", "name", "descn"}, true);
      mapPerm.put("changed", permission.getChanged());
      mapPerms.add(mapPerm);
    }
    page.setData(mapPerms);
    
    return page;
  }

  /**
   * 从Session中取得某个角色的临时权限，包括待分配和待反分配的
   * @param role 指定的角色
   * @param sessionName Session名字
   * 
   * @return
   */
  private Set getTemporaryPermissions(Role role, String sessionName) {
    Map rolePermissions = (Map) getRequest().getSession().getAttribute(
        sessionName);
    if (rolePermissions == null) {
      rolePermissions = Collections.synchronizedMap(new HashMap());
      getRequest().getSession().setAttribute(sessionName, rolePermissions);
    }
    Set permissionIds = (Set) rolePermissions.get(role.getId());
    if (permissionIds == null) {
      permissionIds = Collections.synchronizedSet(new HashSet());
      rolePermissions.put(role.getId(), permissionIds);
    }

    return permissionIds;
  }

  /**
   * 检查权限名是否正在被使用
   * @param permissionName 权限名
   * 
   * @return true：权限名已经被使用；false：权限名未被使用
   */
  public boolean isPermissionNameInUse(String permissionName) {
    Permission permission = new Permission();
    permission.setName(permissionName);
    if (log.isDebugEnabled()) {
      log.debug("check for permission name '" + permissionName + "'");
    }
    // 如果给定名字不唯一，表示正在使用
    return permissionManager.exists(permission, "name");
  }

  /**
   * 页面通过AJAX方式把为角色分配的权限Id和角色Id传入服务器端。<tt>addPermission</tt>
   * 方法将权限Id保存在session中的一个Map对象中，该对象以Role实例和PermissionId为key-value.
   * @param permissionId 权限id
   * @param roleId 角色id
   * @param selected 是删除（false）还是添加（true）
   */
  public void selectPermission(Integer permissionId, Integer roleId,
      boolean selected) {
    if (permissionId == null || roleId == null) {
      return;
    }
    // 根据ID获得角色和权限，其中角色将作为“key”保存在Map对象中
    Role role = roleManager.get(Role.class, roleId);
    Permission permission = permissionManager.get(Permission.class,
        permissionId);
    if (role == null || permission == null) {
      return;
    }
    if (selected) {
      temporaryAddPermission(role, permission);
    } else {
      temporaryRemovePermission(role, permission);
    }
  }

  /**
   * 根据角色的授权情况，在Session中暂存一个待分配的Permission ID
   * @param role 给定角色
   * @param permission 即将分配给角色的Permission
   */
  private void temporaryAddPermission(Role role, Permission permission) {
    // 从Session中取得该角色临时分配的权限Id
    Set permissionIdsAdded = getTemporaryPermissions(role,
        TEMPLATE_ADDED_PERMISSIONS);

    // 如果新分配的Permission还没有保存在数据库中，则暂存这个Permission ID
    if (!role.getPermissions().contains(permission)) {
      permissionIdsAdded.add(permission.getId());
      log.debug("Temporary add permission " + permission.getId() + " for role "
          + role.getId());
    }
    // 同步即将删除的权限
    Set permissionIdsRemoved = getTemporaryPermissions(role,
        TEMPLATE_REMOVED_PERMISSIONS);
    permissionIdsRemoved.remove(permission.getId());
  }

  /**
   * 根据角色的授权情况，在Session中暂存一个反分配的Permission ID
   * @param role 给定角色
   * @param permission 即将从角色的权限中删除Permission
   */
  private void temporaryRemovePermission(Role role, Permission permission) {
    // 从session中取得临时反分配的Permission ID
    Set permissionIdsRemoved = getTemporaryPermissions(role,
        TEMPLATE_REMOVED_PERMISSIONS);
    // 如果该权限已经分配给角色了，则暂存
    if (role.getPermissions().contains(permission)) {
      permissionIdsRemoved.add(permission.getId());
      log.debug("Temporary remove permission " + permission.getId()
          + " for role " + role.getId());
    }
    // 同步即将添加的权限
    Set permissionIdsAdded = getTemporaryPermissions(role,
        TEMPLATE_ADDED_PERMISSIONS);
    permissionIdsAdded.remove(permission.getId());
  }

  /**
   * 为指定的角色分配权限
   * @param roleId 分配角色的id
   * @return 成功：successed 失败： false.
   */
  public boolean saveRolePermissions(Integer roleId) {
    if (roleId == null) {
      return false;
    }

    Role role = roleManager.get(Role.class, roleId);
    if (role == null) {
      return false;
    }
    Set<Integer> permissions = getTemporaryPermissions(role,
        TEMPLATE_REMOVED_PERMISSIONS);
    for (Integer permissionId : permissions) {
      Permission permission = permissionManager.get(Permission.class,
          permissionId);
      if (permission != null) {
        permission.getRoles().remove(role);
        role.getPermissions().remove(permission);
      }
    }

    permissions = getTemporaryPermissions(role, TEMPLATE_ADDED_PERMISSIONS);
    for (Integer permissionId : permissions) {
      Permission permission = permissionManager.get(Permission.class,
          permissionId);
      if (permission != null) {
        permission.getRoles().add(role);
        role.getPermissions().add(permission);
      }
    }

    roleManager.save(role);
    clearSession(role);

    return true;
  }

  /**
   * 取消保存角色的权限信息
   * @param roleId 角色Id
   */
  public void cancelSaveRolePermissions(Integer roleId) {
    Role role = roleManager.get(Role.class, roleId);
    if (role == null) {
      return;
    }
    clearSession(role);
  }

  /**
   * 清空session中有关角色的数据
   * 
   */
  private void clearSession(Role role) {
    HttpSession session = getRequest().getSession();
    Map map = (Map) session.getAttribute(TEMPLATE_ADDED_PERMISSIONS);
    if (map.containsKey(role.getId())) {
      map.remove(role.getId());
    }
    map = (Map) session.getAttribute(TEMPLATE_REMOVED_PERMISSIONS);
    if (map.containsKey(role.getId())) {
      map.remove(role.getId());
    }
    log.debug("Session of role " + role.getName() + " cleared.");
  }

  /**
   * @return the roleManager
   */
  public RoleManager getRoleManager() {
    return roleManager;
  }

  /**
   * @param roleManager the roleManager to set
   */
  public void setRoleManager(RoleManager roleManager) {
    this.roleManager = roleManager;
  }

  public PermissionManager getPermissionManager() {
    return permissionManager;
  }

  public void setPermissionManager(PermissionManager permissionManager) {
    this.permissionManager = permissionManager;
  }
}
