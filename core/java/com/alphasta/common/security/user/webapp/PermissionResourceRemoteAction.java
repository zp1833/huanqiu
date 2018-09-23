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
import com.alphasta.common.security.user.model.Resource;
import com.alphasta.common.security.user.service.PermissionManager;
import com.alphasta.common.security.user.service.ResourceManager;

/**
 * 通过DWR远程调用，为权限分配资源，或者删除一个权限所具有的资源
 * @author boom
 * 
 */
@SuppressWarnings({ "serial", "unchecked" })
public class PermissionResourceRemoteAction extends BaseAction {
  /**
   * 为权限增加的资源暂存在Session中名字
   */
  private static final String TEMPLATE_ADDED_RESOURCES 
      = "resources_added_session";

  /**
   * 为权限删除的资源暂存在Session中的名字
   */
  private static final String TEMPLATE_REMOVED_RESOURCES 
      = "resources_removed_session";

  /**
   * Manager for Permission
   */
  private PermissionManager permissionManager;

  /**
   * Manager for Resource
   */
  private ResourceManager resourceManager;

  /**
   * 返回所有资源，同时查询指定权限所具有的资源，
   * 并在“所有”资源中标注该权限所拥有的角色selected状态。
   * @param permissionId 指定用户id
   * @return List of Resources.
   */
  public Page getResourceOfPermission(Integer permissionId, int pageNo,
      int pageSize, String resourceName) {
    Permission permission = permissionManager.get(Permission.class,
        permissionId);
    if (permission == null) {
      log.warn("Permission with id " + permissionId + " does not exists.");
      return Page.EMPTY_PAGE;
    }

    // 得到该权限得到资源
    Set<Resource> resourceOfPremission = permission.getResources();
    log.debug("Permission " + permission.getId() + " has "
        + resourceOfPremission.size() + " resources.");
    Page page = null;

    if (StringUtils.isBlank(resourceName)) {
      page = resourceManager.query("from Resource r", pageNo, pageSize);
    } else {
      page = resourceManager.query("from Resource r where r.name like ?",
          pageNo, pageSize, "%" + resourceName + "%");
    }

    // 得到所有资源
    List allResources = page.getData();
    // 从session中取得暂存的待分配Resources
    Set templateResourcesAdded = getTemporaryResources(permission,
        TEMPLATE_ADDED_RESOURCES);
    // 从session中取得暂存的待反分配Resources
    Set templateResourcesRemoved = getTemporaryResources(permission,
        TEMPLATE_REMOVED_RESOURCES);
    List mapResources = new ArrayList(allResources.size());
    for (Iterator itr = allResources.iterator(); itr.hasNext();) {
      Resource resource = (Resource) itr.next();
      resource.setChanged(false);

      if (resourceOfPremission.contains(resource)) { // 如果资源已经分配给权限则选中
        resource.setChanged(true);
      }

      // 如果资源暂时分配了则选中
      if (templateResourcesAdded != null
          && templateResourcesAdded.contains(resource.getId())) {
        resource.setChanged(true);
      }
      // 如果资源暂时被删除则不选中
      if (templateResourcesRemoved != null
          && templateResourcesRemoved.contains(resource.getId())) {
        resource.setChanged(false);
      }
     //    转换为Map，防止延迟加载
      Map mapRes = ReflectUtil.toMap(resource, 
          new String[]{"id", "name", "descn"}, true);
      mapRes.put("changed", resource.getChanged());
      mapResources.add(mapRes);
    }
    page.setData(mapResources);
    
    return page;
  }

  /**
   * 检查资源名是否正在使用
   * @param resourceName 资源名
   * @return true:资源名已经被使用;false:资源名未被使用
   */
  public boolean isResourceNameInUse(String resourceName) {
    Resource resource = new Resource();
    resource.setName(resourceName);
    if (log.isDebugEnabled()) {
      log.debug("check for resource name '" + resourceName + "'");
    }
    // 如果给定name不唯一，则表示正在使用
    return resourceManager.exists(resource, "name");
  }

  /**
   * 页面通过AJAX方式把权限分配的资源Id和权限Id传入服务器端。
   * 方法将资源Id保存在session中的一个Map对象中，
   * 该对象以Permission实例和ResourceId为key-value.
   * @param permissionId 权限Id
   * @param resourceId 资源Id
   * @param selected 是删除(false)还是添加(true)
   */
  public void selectResource(Integer resourceId, Integer permissionId,
      boolean selected) {
    if (resourceId == null || permissionId == null) {
      return;
    }
    // 根据Id获得Resource和Permission，其中Permission将作为key保存在Map对象中
    Permission permission = permissionManager.get(Permission.class,
        permissionId);
    Resource resource = resourceManager.get(Resource.class, resourceId);
    if (permission == null || resource == null) {
      return;
    }
    if (selected) {
      temporaryAddResource(permission, resource);
    } else {
      temporaryRemoveResource(permission, resource);
    }
  }

  /**
   * 从session中取得某个权限的临时资源，包括待分配的和待反分配的。
   * @param permission 指定的权限
   * @param sessionName Session名字
   */
  private Set getTemporaryResources(Permission permission, String sessionName) {
    Map permissionResources = (Map) getRequest().getSession().getAttribute(
        sessionName);
    if (permissionResources == null) {
      permissionResources = Collections.synchronizedMap(new HashMap());
      getRequest().getSession().setAttribute(sessionName, permissionResources);
    }

    Set resourceIds = (Set) permissionResources.get(permission.getId());

    if (resourceIds == null) {
      resourceIds = Collections.synchronizedSet(new HashSet());
      permissionResources.put(permission.getId(), resourceIds);
    }

    return resourceIds;
  }

  /**
   * 权限授权情况，在Session中暂存一个待分配的Resource Id
   * @param permission 给定权限
   * @param resource 即将分配给权限的资源
   */
  private void temporaryAddResource(Permission permission, Resource resource) {
    // 从Session中取得该权限临时分配的资源Id
    Set resourceIdsAdded = getTemporaryResources(permission,
        TEMPLATE_ADDED_RESOURCES);

    // 如果新分配的Resource还没有保存在数据库中，则暂存这个Resource Id
    if (!permission.getResources().contains(resource)) {
      resourceIdsAdded.add(resource.getId());
      log.debug("Temporary add resource " + resource.getId()
          + " for permission " + permission.getId());
    }
    // 同步即将删除的角色
    Set resourceIdsRemoved = getTemporaryResources(permission,
        TEMPLATE_REMOVED_RESOURCES);
    resourceIdsRemoved.remove(resource.getId());
  }

  /**
   * 根据授权情况，在Session中暂存一个反分配的permissionId
   * @param permission 给定权限
   * @param resource 即将从权限中删除的资源
   */
  private void temporaryRemoveResource(Permission permission, 
      Resource resource) {
    // 从Session中取得该权限临时反分配的资源Id
    Set resourceIdsRemoved = getTemporaryResources(permission,
        TEMPLATE_REMOVED_RESOURCES);
    // 如果该资源已经分配给了权限，则暂存
    if (permission.getResources().contains(resource)) {
      resourceIdsRemoved.add(resource.getId());
      log.debug("Temporary remove resource " + resource.getId()
          + " for permission " + permission.getId());
      // 同步即将添加的角色
      Set resourceIdsAdded = getTemporaryResources(permission,
          TEMPLATE_ADDED_RESOURCES);
      resourceIdsAdded.remove(resource.getId());
    }
  }

  /**
   * 为指定权限分配资源
   * @param permissionId 权限Id
   * @return 成功为true否则为false
   */
  public boolean savePermissionResources(Integer permissionId) {
    if (permissionId == null) {
      return false;
    }
    Permission permission = permissionManager.get(Permission.class,
        permissionId);
    if (permission == null) {
      return false;
    }

    Set<Integer> resources = getTemporaryResources(permission,
        TEMPLATE_REMOVED_RESOURCES);
    for (Integer resourceId : resources) {
      Resource resource = resourceManager.get(Resource.class, resourceId);
      if (resource != null) {
        resource.getPermissions().remove(permission);
        permission.getResources().remove(resource);
      }
    }

    resources = getTemporaryResources(permission, TEMPLATE_ADDED_RESOURCES);
    for (Integer resourceId : resources) {
      Resource resource = resourceManager.get(Resource.class, resourceId);
      if (resource != null) {
        resource.getPermissions().add(permission);
        permission.getResources().add(resource);
      }
    }
    permissionManager.save(permission);
    clearSession(permission);

    return true;
  }

  /**
   * 清空Session中的有关数据
   */
  private void clearSession(Permission permission) {
    HttpSession session = getRequest().getSession();
    Map map = (Map) session.getAttribute(TEMPLATE_ADDED_RESOURCES);
    if (map.containsKey(permission.getId())) {
      map.remove(permission.getId());
    }
    map = (Map) session.getAttribute(TEMPLATE_REMOVED_RESOURCES);
    if (map.containsKey(permission.getId())) {
      map.remove(permission.getId());
    }
    log.debug("Session of permission " + permission.getName() + " cleared.");
  }

  /**
   * 取消保存权限的资源信息
   */
  public void cancleSavePermissionResources(Integer permissionId) {
    Permission permission = permissionManager.get(Permission.class,
        permissionId);
    if (permission == null) {
      return;
    }
    clearSession(permission);
  }

  public PermissionManager getPermissionManager() {
    return permissionManager;
  }

  public void setPermissionManager(PermissionManager permissionManager) {
    this.permissionManager = permissionManager;
  }

  public ResourceManager getResourceManager() {
    return resourceManager;
  }

  public void setResourceManager(ResourceManager resourceManager) {
    this.resourceManager = resourceManager;
  }
}
