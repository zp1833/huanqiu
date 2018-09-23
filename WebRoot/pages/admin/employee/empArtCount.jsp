<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户发文统计列表</title>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/ec.jsp" %>
<%@include file="/common/extSkin.jsp" %>
</head>
<body>
<div class="x-panel">
    	<div class="x-panel-header">用户发文统计</div>
    	<div class="x-panel-body">
    	    <div style="margin-left:-3px;" align="center">
		    	<ec:table items="items" var="item" retrieveRowsCallback="process" sortRowsCallback="process"
				action="empartCount.do"
				useAjax="true" doPreload="false"
				pageSizeList="15,20,30,40" 
				editable="false" 
				sortable="false"	
				rowsDisplayed="15"	
				generateScript="true"	
				resizeColWidth="true"	
				classic="true"
				width="100%" 	
				height="345"	
				minHeight="345"  
				xlsFileName="用户发文统计列表.xls" 
				>
				<ec:row>
					<ec:column width="40" property="_s" title="序号" value="${GLOBALROWCOUNT}" style="text-align:center" sortable="false"/>
					<ec:column width="114" property="_1" title="用户名" sortable="false">
						${item[1]}
					</ec:column>
					<ec:column width="80" property="_2" title="部门">
						${item[2]}
					</ec:column>
					<ec:column width="80" property="_3" title="文章数量">
						${item[3]}
					</ec:column>
				</ec:row>
				</ec:table>
			</div>
    	</div>
</div>
</body>
</html>
