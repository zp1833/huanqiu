package com.alphasta.common.security.acegi.taglibs;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alphasta.cms.model.Employees;
import com.alphasta.common.security.user.UserUtil;
import com.alphasta.common.security.user.model.User;
/**
 * 用于在页面上显示员工名
 * @author shiqiang
 *
 */
@SuppressWarnings("serial")
public class DisplayEmployeenameTag extends BodyTagSupport {
  /**
   * log
   */
  private static Log log = LogFactory.getLog(DisplayEmployeenameTag.class);
  
  /**
   * @see BodyTagSupport#doEndTag()
   */
  @Override
  public int doEndTag() throws JspException {
    HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
    User user = UserUtil.getPrincipal(request);

    log.debug("display-" + user);
    if (user != null) {
      try {
        Employees employees = user.getEmployees();
        pageContext.getOut().print(
            employees.getName());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return EVAL_PAGE;
  }

}
