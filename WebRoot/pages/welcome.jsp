<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<%@include file="/common/extAll.jsp"%>
<html lang="en">
<head>
    <title>Ext - Welcome</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/styles/welcome/reset-min.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/styles/welcome/welcome.css"/>
    <style type="text/css">
<!--
.STYLE6 {color: #CCCCCC}
.tdborder{
	border-bottom-style:dotted;
	border-bottom-width:1px
}
.toptdborder{
	border-bottom-style:dotted;
	border-bottom-width:1px;
	border-bottom-color:#0A246A
}
.STYLE10 {font-size: 12}
.STYLE12 {color: #FF0000}
-->
    </style>
</head>
<body>

<stc:role ifAnyGranted="ROLE_ADMIN,ROLE_ADV,ROLE_ORADMIN,ROLE_AUDIT">

	<c:if test="${userInSession.employees.isDeptAdmin == '1'}">
		<div class="col" style="height:98%;">
			<div class="block" style="height:98%; overflow: auto;">
				<table width="100%" class="toptdborder">
					<tr>
						<td class="block-title"><img src="${ctx}/images/icons/authority.gif" width="16" height="16">
							顾问工作考核<font size="1">（昨日回访完成情况）</font>
						</td>
					</tr>
				</table>
				<div class="block-body">
					<table width="93%" border="0" cellpadding="0" cellspacing="0">
						<cms:remindTag isDeptAdmin="1"></cms:remindTag>
					</table>
				</div>
			</div>
		</div>
	</c:if>

	<div class="col" style="height:98%;">
		<div class="block" style="height:98%; overflow: auto;">
			<table width="100%" class="toptdborder">
				<tr>
					<td class="block-title"><img src="${ctx}/images/icons/authority.gif" width="16" height="16">
						<stc:role ifAnyGranted="ROLE_ADMIN,ROLE_ORADMIN,ROLE_AUDIT">
							顾问工作考核<font size="1">（昨日回访完成情况）</font>
						</stc:role>
						<stc:role ifNotGranted="ROLE_ADMIN,ROLE_ORADMIN,ROLE_AUDIT">
							顾问工作提醒<font size="1">（今日需回访的学生）</font>
						</stc:role>			
					</td>
				</tr>
			</table>
			<div class="block-body">
				<table width="93%" border="0" cellpadding="0" cellspacing="0">
					<cms:remindTag></cms:remindTag>
				</table>
			</div>
		</div>
	</div>
</stc:role>   
  
	<div class="col" style="height:98%">
		<div class="block" style="height:98%; overflow: auto;">
			<table width="100%" class="toptdborder">
				<tr>
					<td class="block-title"><img src="${ctx}/images/icons/authority.gif" width="16" height="16">
						工作计划
					</td>
				</tr>
			</table>
			<div class="block-body">
				<table width="93%" height="50%" border="0" cellpadding="0" cellspacing="4">
					<tr>
						<td valign="top">
							<table cellpadding="0" cellspacing="0" align="left" class="STYLE10">
								<cms:workPlanTag></cms:workPlanTag>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
    
</body>
</html>