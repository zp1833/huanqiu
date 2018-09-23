package com.alphasta.common.security.user;

import com.alphasta.common.security.user.model.User;

/**
 * 实现UserPrincipalAwareBean接口的类可以接收一个User的实例。通常
 * 用于将web层当前登录用户传递给其他层。
 * @author Sam Lee
 *
 */
public interface UserPrincipalAwareBean {
  /**
   * 
   * @param currentLoginUser 当前登录的用户
   */
  public void setUserPrincipal(User currentLoginUser);
}
