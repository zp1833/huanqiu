package com.alphasta.common.core.test;

import java.lang.reflect.Method;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.util.Assert;

import com.alphasta.common.core.util.PropertyUtil;
import com.alphasta.common.core.webapp.struts2.BaseAction;
import com.alphasta.common.core.webapp.struts2.BaseModelAction;

/**
 * 用于测试Struts2 Action等Web层Java对象的TestCase
 * @author Sam Lee
 * @deprecated 这个类还需要改进，也许在BaseAction直接使用Mock对象更好。
 */
public class BaseMockTestCase extends BaseTestCase {
  /**
   * ServletContext
   */
  private ServletContext servletContext;

  /**
   * HttpServletRequest
   */
  private HttpServletRequest request;

  /**
   * HttpServletResponse
   */
  private HttpServletResponse response;

  /**
   * 返回{@link MockHttpServletRequest}
   */
  protected HttpServletRequest getHttpServletRequest() {
    if (request == null) {
      request = new MockHttpServletRequest(getServletContext());
    }
    return request;
  }

  /**
   * 返回{@link MockHttpServletResponse}
   */
  protected HttpServletResponse getHttpServletResponse() {
    if (response == null) {
      response = new MockHttpServletResponse();
    }

    return response;
  }

  /**
   * @return {@link MockServletContext}
   */
  protected ServletContext getServletContext() {
    if (this.servletContext == null) {
      servletContext = new MockServletContext();
    }
    return servletContext;
  }

  /**
   * 返回Spring管理的BaseAction对象，如果没有，返回<code>null</code>
   * @param actionName name of action in spring.
   */
  @SuppressWarnings("unchecked")
  protected BaseAction getAction(String actionName) {
    Assert.hasLength(actionName);
    final Object object = applicationContext.getBean(actionName);
    if (!(object instanceof BaseModelAction)) {
      return (BaseAction) object;
    }
    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(object.getClass());
    enhancer.setCallback(new BaseActionMethodIntercepter(object));

    BaseModelAction action = (BaseModelAction) enhancer.create();
    action.setManager(((BaseModelAction) object).getManager());
    return action;
  }

  /**
   * 截获BaseAction中有关"获取Web应用程序上下文对象"的方法，用Mock对象替换。
   * 
   */
  private class BaseActionMethodIntercepter implements MethodInterceptor {
    /**
     * 原始的BaseAction对象，被代理的对象。
     */
    private Object origin;

    /**
     * Constructor.传入原始的BaseAction对象
     */
    public BaseActionMethodIntercepter(Object orginalObject) {
      this.origin = orginalObject;
    }

    /**
     * 方法截获。
     */
    public Object intercept(Object target, Method method, Object[] args,
        MethodProxy methodProxy) throws Throwable {
      
      String methodName = method.getName();
      if ((args == null || args.length == 0)
          && methodName.startsWith(PropertyUtil.GETTER_PREFIX)) {
        logger.debug("Proxy method " + methodName);
        if (methodName.equals("getRequest")) {
          return getHttpServletRequest();
        } else if (methodName.equals("getResponse")) {
          return getHttpServletResponse();
        } else if (methodName.equals("getServletContext")) {
          return getServletContext();
        } else if (methodName.equals("getSpring")) {
          return applicationContext;
        }
      }
      return method.invoke(origin, args);
    }
  }

}
