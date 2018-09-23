package com.alphasta.common.security.user.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.util.ResourceUtils;

import com.alphasta.common.core.exception.ApplicationException;
import com.alphasta.common.core.service.BaseManager;
import com.alphasta.common.security.acegi.cache.AcegiCacheManager;
import com.alphasta.common.security.acegi.resourcedetails.ResourceDetails;
import com.alphasta.common.security.user.model.Permission;
import com.alphasta.common.security.user.model.Resource;

/**
 * Manager of <code>Resource</code>
 * @author Sam
 */
@SuppressWarnings("unchecked")
public class ResourceManager extends BaseManager {
  
  /**
   * <code>AcegiCacheManager</code>用于同步缓存
   */
  private AcegiCacheManager acegiCacheManager;

  /**
   * @see BaseManager#remove(java.lang.Object)
   */
  @Override
  public void remove(Object resource) {
    if (resource == null || !(resource instanceof Resource)) {
      return;
    }
    Resource res = (Resource) resource;
    if (res.getId() == null) {
      return;
    }
    // 删除权限关联
    Set<Permission> permsSet = res.getPermissions();
    Permission[] perms = permsSet.toArray(new Permission[] {});
    log.debug("Remove the relations between Persmission and Resource");
    for (int i = 0; i < perms.length; i++) {
      perms[i].getResources().remove(resource);
      res.getPermissions().remove(perms[i]);
      //saveObject(perms[i]);
    }

    String old = res.getResString();
    super.remove(resource);
    if (acegiCacheManager != null) {
      acegiCacheManager.onResourceChanged(null, old);
    }
  }

  /**
   * @see BaseManager#save(java.lang.Object)
   */
  @Override
  public void save(Object object) {
    if (object == null) {
      return;
    }

    if (getDao().isAlreadyExists(object, "resString")) {
      throw new ApplicationException(((Resource) object).getResString());
    }

    String oldString = null; // 修改之前的resString
    Resource old = (Resource) object;
    if (old.getId() != null) {
      old = getDao().getObject(Resource.class, old.getId());
      // 因为有Version字段，所以要复制属性
      oldString = old.getResString();
      old.setName(((Resource) object).getName());
      old.setResType(((Resource) object).getResType());
      old.setDescn(((Resource) object).getDescn());
      old.setResString(((Resource) object).getResString());
    }

    super.save(old); // 更新Resource

    if (oldString == null) {
      oldString = ((Resource) object).getResString();
    }

    if (acegiCacheManager != null) { // 同步缓存
      acegiCacheManager.onResourceChanged(old, oldString);
    }

  }

  /**
   * @see ResourceManager#isNameInUse(java.lang.String)
   */
  public boolean isNameInUse(String name) {
    Resource res = new Resource();
    res.setName(name);
    if (log.isDebugEnabled()) {
      log.debug("check for resource name '" + name + "'");
    }
    return exists(res, "name"); // 如果给定name不唯一，则表示正在使用
  }

  /**
   * @see ResourceManager#getByPerm(Permission)
   */
  @SuppressWarnings("unchecked")
  public Resource[] getByPerm(Permission perm) {
    Set resSet = perm.getResources();
    Resource[] retResources = new Resource[resSet.size()];
    resSet.toArray(retResources);
    return retResources;
  }

  /**
   * 保存解析struts文件后生成的URL资源
   * 遍历所有的struts配置文件,并逐个解析,
   * 将解析后的节点以map的形式保存在List中,
   * 然后将得到的节点内容保存为URL资源
   * @param strutsXmls struts配置文件
   */
  public void saveResource(List strutsXmls) {
    log.info("The sise of struts's XML config file is: " + strutsXmls.size());
    for (Iterator itr = strutsXmls.iterator(); itr.hasNext();) {
      String filePath = (String) itr.next();
      File realFile = null;
      try {
        realFile = ResourceUtils.getFile("classpath:" + filePath);
        log.debug("获得工程所在的目录:" + realFile);
      } catch (FileNotFoundException e) {
        log.error("系统找不到指定的文件");
        return;
      }
      if (realFile == null) {
        return;
      }
      List listMap = readXMLFile(realFile);
      if (listMap != null && listMap.size() > 0) {
        for (Iterator listItr = listMap.iterator(); listItr.hasNext();) {
          Map map = (Map) listItr.next();
          Resource resource = new Resource();
          resource.setName(map.get("package").toString());
          resource.setResString(map.get("realPath").toString());
          resource.setResType(ResourceDetails.RES_TYPE_URL);
          if (!exists(resource, "resString")) {
            save(resource);
          }
          log.debug("packageName: " + map.get("package") 
              + " && " + "realPath: " + map.get("realPath"));
        }
      }
    }
  }
  
  /**
   * 读取配置文件
   * 将解析后的节点以map的形式保存在List中
   * @param file struts配置文件
   */

  private List readXMLFile(File file) {
    log.debug("读取struts配置文件：" + file.getName());
    // DocumentFactory factory = DocumentFactory.getInstance();
    // SAXReader saxreader = new SAXReader(factory);
    BufferedReader bufferedreader = null;
    SAXReader saxreader = new SAXReader();
    Document doc = null;
    try {
      bufferedreader = new BufferedReader(new FileReader(file));
      doc = (Document) saxreader.read(bufferedreader);
    } catch (FileNotFoundException e) {
      log.error("系统找不到指定的文件");
      return null;
    } catch (DocumentException e) {
      log.error("读取文件失败");
      return null;
    } 
    if (doc == null) {
      return null;
    }
    // 取得顶层节点root
    Element root = doc.getRootElement();
    log.debug("Root Element : " + root.getName());
    Element eleOfRoot = null;
    List<Map> resultList = new ArrayList();
    // 遍历顶层节点root的子节点
    for (Iterator eleIt = root.elementIterator(); eleIt.hasNext();) {
      eleOfRoot = (Element) eleIt.next();
      // 节点属性名称
      Attribute rootpackage = eleOfRoot.attribute("name");
      String rtpackage = rootpackage.getValue();
      int lin = rtpackage.lastIndexOf("."); 
      if (lin != -1) {
        rtpackage = rtpackage.substring(lin + 1);
      }
      Attribute rootAtt = eleOfRoot.attribute("namespace");
      String rootVal = rootAtt.getValue();
      // 保存解析后的资源名称和路径
      Map rootPathMap = new HashMap();
      rootPathMap.put("package", rtpackage);
      rootPathMap.put("realPath", rootVal + "/*");
      // 取得子节点的下级节点
      if (eleOfRoot.hasContent()) {
        Element grandchild = null;
        for (Iterator grandIt = eleOfRoot.elementIterator(); 
          grandIt.hasNext();) {
          StringBuilder actionPath = new StringBuilder();
          grandchild = (Element) grandIt.next();
          Attribute attribute = grandchild.attribute("name");
          String val = attribute.getValue();
          actionPath.append(rootVal).append("/")
              .append(val).append(".do").append("*");
          Map pathMap = new HashMap();
          pathMap.put("package", rtpackage + "." + val);
          pathMap.put("realPath", actionPath);
          resultList.add(pathMap);
        }
      }
      resultList.add(rootPathMap);
    }
    return resultList;
  }
  
  /**
   * @return the acegeCacheManager
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

  
}
