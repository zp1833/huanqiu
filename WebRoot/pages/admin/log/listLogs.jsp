<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统日志列表</title>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/ec.jsp" %>
<%@include file="/common/calendar.jsp"%>
<%@include file="/common/extSkin.jsp" %>
<script type="text/javascript">
	
	function onQuery() {
	
	}
	
</script>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">系统日志管理</div>
    	<div class="x-toolbar">
	    	<table width="99%">	      
		      <tr>
		      	<td align="left">
		      		<form name="frmQuery" id="frmQuery" method="post" action="${ctx }/admin/log/listLogs.do">
						操作人<input type="text" name="operateUser" size="10" value="${operateUser}" />&nbsp;&nbsp;&nbsp;&nbsp;操作日期
						
						从<input type="text" popupDateFormat="yyyy-MM-dd HH:mm:ss" name="beginDate" size="10" id="beginDate" 
						class="Wdate" onfocus="return new WdatePicker(this, null, false, 'whyGreen')"
							value='<s:date name="beginDate" format="yyyy-MM-dd HH:mm:ss"/>'/>
						到<input type="text" name="endDate" size="10" id="endDate"
							class="Wdate" popupDateFormat="yyyy-MM-dd"
							onfocus="return new WdatePicker(this, null, false, 'whyGreen')"
							value='<s:date name="endDate" format="yyyy-MM-dd HH:mm:ss"/>'/>
						
						&nbsp;&nbsp;&nbsp;&nbsp;IP范围
						
						从<input type="text"  name="beginIp" size="10" id="beginIp" value="${beginIp }"/>
						到<input type="text" name="endIp" size="10" id="endIp" value="${endIp }"/>
						<input type="submit" value="查 询" class="button"/>
					</form>
				</td>
		         <td align="right">
		         </td>
		      </tr>
	       </table>
    	</div>
    	<div class="x-panel-body">
    	
    	    <div style="margin-left:-3px;" align="center">
		    	<ec:table items="items" var="item" retrieveRowsCallback="limit" sortRowsCallback="limit"
				action="listLogs.do"
				useAjax="true" doPreload="false"
				pageSizeList="10,15,20,30" 
				editable="false" 
				sortable="true"	
				rowsDisplayed="10"	
				generateScript="true"	
				resizeColWidth="true"	
				classic="true"
				width="100%" 	
				height="500"	
				minHeight="400"  
				>
				<ec:row style="height:30px">
					<ec:column width="30"  property="_s" title="序号" value="${GLOBALROWCOUNT}" style="text-align:center"/>
					<ec:column width="50" property="username" title="操作人" style="text-align:center"/>
					<ec:column width="100" property="deptName" title="所属部门" style="text-align:center"/>
					<ec:column width="100" property="ip" title="IP地址" style="text-align:center"/>
					<ec:column width="120" property="mac" title="MAC" style="text-align:center"/>
					<ec:column width="400" property="operation" title="操作内容"/>
					<ec:column width="130" property="operatedate" cell="date" style="text-align:center" format="yyyy-MM-dd HH:mm:ss" title="操作日期"/>
				</ec:row>
				</ec:table>
			</div>
    	</div>
</div>

</body>
<table>
<tr>
<td></td>
</tr>
</table>
</html>
