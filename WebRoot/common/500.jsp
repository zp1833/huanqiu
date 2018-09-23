<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/meta.jsp" %>
<title>系统繁忙</title>
<script type="text/javascript">
	function goLogin() {
		top.location.href="${ctx}/login.jsp";
	}
</script>
</head>
<body>
<fieldset style="margin:10px;">
<legend>注意</legend>
系统繁忙或登录超时,请<a href="#" onclick="goLogin()" style="color:blue">重新登录</a>系统！ 
</fieldset>
</body>
</html>