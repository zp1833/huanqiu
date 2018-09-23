package com.alphasta.common.security.user.service.listener;

import javax.servlet.http.HttpServletRequest;

import com.alphasta.common.security.acegi.listener.UserLoginListener;
import com.alphasta.common.security.user.model.User;
import com.alphasta.common.security.user.service.UserManager;
/**
 * 简单的记录用户登录IP和登录时间等信息
 * @author Sam Lee
 *
 */
public class SimpleUserLoginListener implements UserLoginListener {
  /**
   * 用户管理对象
   */
  private UserManager userManager;

  /**
   * @param userManager the userManager to set
   */
  public void setUserManager(UserManager userManager) {
    this.userManager = userManager;
  }

  /**
   * @see UserLoginListener#loginFailed(HttpServletRequest)
   */
  public void loginFailed(HttpServletRequest request) {
    ; 
  }

  /**
   * @see UserLoginListener#loginSuccessed(User, HttpServletRequest)
   */
  public void loginSuccessed(User user, HttpServletRequest request) {
    userManager.updateLoginInformation(user, request.getRemoteAddr());
  }

}
