<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include  file="/common/taglibs.jsp"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>石家庄环球教育语言管理系统</title>
</head>
<STYLE type=text/css>
BODY { FONT-SIZE: 9pt}
TD {
	 FONT-SIZE: 9pt
}
A:link {
	COLOR: #333333; TEXT-DECORATION: none
}
A:visited {
	COLOR: #333333; TEXT-DECORATION: none
}
A:hover {COLOR: #0099cc; TEXT-DECORATION: underline; TEXT-DECORATION:none; position:relative; left: 1px; top: 1px
}
</STYLE>
<script type="text/javascript">
function onLogin() {
   var frm = document.getElementById('loginForm');
   if(frm) {
       frm.submit();
   }
}
function checkLogin() {
      if (loginForm.j_username.value.length == 0 ) {
        alert('请您输入用户名!');
        loginForm.j_username.focus();
        return false;
      }
      else if (loginForm.j_password.value.length == 0 ) {
        alert('请您输入登录密码!');
        loginForm.j_password.focus();
        return false;
      }
      else if (loginForm.j_captcha_response.value.length == 0 ) {
        alert('请您输入验证码!');
        loginForm.j_captcha_response.focus();
        return false;
      }
 	  loginForm.action = '${ctx}/j_security_check';
      loginForm.submit();
      return true;
    }
</script>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<div align="center">
<table border="0" width="1003" id="table1" cellspacing="0" cellpadding="0" height="600">
	<tr>
		<td background="images/index.jpg" valign="top">
		<div align="center">
			<table border="0" width="560" id="table2" cellspacing="0" cellpadding="0" style="margin-top:125px;">
				<tr>
					<td height="30" colspan="2" style="padding-right: 10px">
					<p align="right">
					<a href="#" onclick="javascript:window.close(1)">
					<img border="0" src="images/topclose.jpg" width="17" height="17"></a></td>
				</tr>
				<tr>
					<td height="80" colspan="2">　</td>
				</tr>
				<tr>
					<td width="240">　</td>
					<td width="320">
					<form name="loginForm" id="loginForm" method="post" onsubmit="return checkLogin();"
							action='${ctx}/j_security_check'>
					<table id="table3" width="100%" align="center" border="0">
						<tr>
							<td align="right" width="25%">用户名：</td>
							<td align="left" width="75%" colspan="3">
							<input id="j_username" onKeyDown="return (event.keyCode=='13')?loginForm.j_password.focus():true" style="WIDTH: 150px" name="j_username"></td>
						</tr>
						<tr>
							<td align="right">密&nbsp;&nbsp;码：</td>
							<td id="ext-gen84" align="left" colspan="3">
							<input id="j_password" onKeyDown="return (event.keyCode=='13')?loginForm.j_captcha_response.focus():true" style="WIDTH: 150px" type="password" value name="j_password"></td>
						</tr>
						<tr>
							<td vAlign="center" align="right">验证码：</td>
							<td id="ext-gen78" align="left" width="70">
							<input id="ext-gen70" onKeyDown="return (event.keyCode=='13')?checkLogin():true" style="width:67;height:20" name="j_captcha_response" autocomplete="off"> 
							</td>
							<td id="ext-gen78" align="left" width="80">
							<iframe frameborder="0" scrolling="no" style="width:80px;height:25px;" marginheight="0" marginwidth="0" id="captchaFrame" name="cf" src="<c:url value='/captcha.jpg' />"></iframe></td>
							<td id="ext-gen78" vAlign="center" align="left">
							<a href="#" style="color:#425E8D; text-decoration: none"
								onclick="frames['cf'].location='<c:url value="/captcha.jpg" />';">
							刷新图片</a></td>
						</tr>
						<tr>
							<td align="center" colspan="4" height="40">
							<a href="#" onClick="onLogin()"><img border="0" src="images/login.jpg" width="80" height="20"></a>&nbsp;
							<a href="#" onclick="javascript:window.close(1)"><img border="0" src="images/close.jpg" width="80" height="20"></a></td>
						</tr>
						
					</table>
					<table align="center" width="80%" cellpadding="0" cellspacing="0">
						<tr><td>
						<p align="center">
						<c:if test="${param.login_error == 'code_error'}">
						       <img border="0" src="<c:url value='/images/icons/warning.gif'/>"/>&nbsp;验证码输入错误！
						</c:if>
						<c:if test="${param.login_error == 'user_psw_error' || param.login_error == '1'}">
						       <img border="0" src="<c:url value='/images/icons/warning.gif'/>"/>&nbsp;用户名或密码错误，请重试！
						</c:if>
						<c:if test="${param.login_error == 'too_many_user_error'}">
						       <img border="0" src="<c:url value='/images/icons/warning.gif'/>"/>&nbsp;该用户已经在其他地方登录了，请稍候。
						</c:if>
						</td>
						</tr>
					</table>
					</form>
				</td>
				</tr>
			</table>
		</div>
		</td>
	</tr>
</table>

</div>

</body>

</html>