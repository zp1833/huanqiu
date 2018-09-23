<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看学生信息</title>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/extSkin.jsp" %>
<script type="text/javascript" src="${ctx}/js/TabPanel/tabpanel.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/js/TabPanel/css/style.css" />
<script type="text/javascript">
	function changeTab() {
		<stc:role ifAnyGranted="ROLE_REC,ROLE_ADMIN,ROLE_ORADMIN">
			switchTab(document.getElementById('SystopTab1'),6);
		</stc:role>
		<stc:role ifAnyGranted="ROLE_ADV">
			switchTab(document.getElementById('SystopTab2'),6);
		</stc:role>
		<stc:role ifAnyGranted="ROLE_DOC">
			switchTab(document.getElementById('SystopTab4'),6);
		</stc:role>
	}
</script>
</head>
<body onload="changeTab()">
	<div class="x-panel">
	    <div class="x-panel-header">学生信息详情</div>
		<table width="100%" border="0" align="center">
		  <tr><td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="TabBarLevel" id="TabPage">
		      <tr align="center">
		        <td width="80" id="SystopTab1" valign="middle" class="SelectedTab" onclick=switchTab(this,6)>
					接待信息
				</td>
		        <td width="7" class="Black">&nbsp;</td>
		        <td width="80" id="SystopTab2" valign="middle" class="mousehand" onclick=switchTab(this,6)>
		        	学生信息
				</td>
		        <td width="7" class="Black">&nbsp;</td>
		        <td width="80" id="SystopTab3" valign="middle" class="mousehand" onclick=switchTab(this,6)>
		        	报班信息
		        </td>
		        <td width="7" class="Black">&nbsp;</td>
		        <td width="80" id="SystopTab4" valign="middle" class="mousehand" onclick=switchTab(this,6)>
		        	会员信息
		        </td>
		        <td width="7" class="Black">&nbsp;</td>
		        <td width="7" class="Black">&nbsp;</td>
	        	<td width="" align="right" class="Black">&nbsp;&nbsp;</td>
		      </tr>
		    </table>
	    
		    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="Content">
		        <tr>
		          <td height="100">
					<div id='SystopTabDiv1' style=" display:none">
						<%@include file="lookRecInfo.jsp" %>
					</div>
					<div id='SystopTabDiv2' style=" display:none">
						<%@include file="lookStuDetailsInfo.jsp" %>
					</div>
					<div id='SystopTabDiv3' style=" display:none">
						<%@include file="lookClassInfo.jsp" %>
					</div>
					<div id='SystopTabDiv4' style=" display:none">
						<%@include file="lookMemberInfo.jsp" %>
					</div>
				  </td>
		        </tr>
		    </table>		
		  </td></tr>
		</table>
	</div>
</body>
</html>