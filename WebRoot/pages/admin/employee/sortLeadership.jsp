<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/extSkin.jsp" %>
<%@include file="/common/ec.jsp" %>
<script type='text/javascript' src='${ctx}/pages/admin/links/sortSelect.js'></script>
<script type="text/javascript" src="${ctx}/dwr/interface/CatalogDwrAction.js"></script>
<%@include file="/common/dwr.jsp" %>
<title>用户排序</title>
</head>
<body>
<div class="x-panel">
  <div class="x-panel-header">用户管理</div>
  <div class="x-toolbar">
  	<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1">
	   <tr>
	    <td style=" padding-left:5px; padding-top:5px;">      
			<span class="STYLE2">管理导航</span>:
			<a href="${ctx}/admin/employee/listEmployees.do">用户管理首页</a> | 
			<a href="${ctx}/admin/employee/newEmployee.do"> 新建用户</a> | 
			<a href="${ctx}/admin/employee/listOrderEmployee.do">用户排序</a>
		 </td>	
	  </tr>
	 </table>
  </div>
	<table align="center">
	<s:form id="frmOrder" name="frmOrder" action="saveOrder" method="post" theme="simple">
		<tr>
			<td>
			<fieldset style="margin:10px; width: 250px">
              <legend>用户排序</legend>
                <table align="center">
				  <tr>
					<td>
				      <s:select id="order" name="order" list="orderEmployee" listKey="id" listValue="name" multiple="true" size="20"/>
					</td>
					<td>
					  <input id="first" type="button" class="button" onclick="sl.fnFirst()" value="第一"/>
					  <br/>
					  <br/>
					  <input id="up" type="button" class="button" onclick="sl.sortUp()" value="上移"/>
					  <br/>
					  <br/>
					  <input id="down" type="button" class="button" onclick="sl.sortDown()" value="下移"/>
					  <br/>
					  <br/>
					  <input id="fnEnd" type="button" class="button" onclick="sl.fnEnd()" value="最后"/>
					  <br/>
					  <br/>
					  <input id="ok" type="button" class="button" onclick="sl.ok()" value="确定"/>
					</td>
				  </tr>
				</table> 
              </fieldset>
			</td>
		</tr>
		<s:hidden name="seqNoList"/>
		<s:hidden name="queryValue"/>
		</s:form>
	</table>
</div>

<script type="text/javascript">
var sl = new SortSelect('frmOrder','order','search','jumpNum');
</script>
</body>
</html>