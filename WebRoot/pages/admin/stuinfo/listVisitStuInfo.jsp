<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学生信息列表</title>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/ec.jsp" %>
<%@include file="/common/extSkin.jsp" %>
<%@include file="/common/dwr.jsp" %>
<script type="text/javascript" src="${ctx}/calendar/WdatePicker.js"></script>
</head>
<body>

<div class="x-panel">
    	<div class="x-panel-header">顾问回访记录</div>
    	<div class="x-toolbar">
	    	<table width="99%">	      
		      <tr>	   
		      	 <td>
		      	 	<s:form action="listVisitStuInfo" method="post" theme="simple">
						<table border="0">	      
					      <tr>	   
					      	 <td>回访日期：</td>
					      	 <td><input type="text" name="visitDate" size="12" id="visitDate" 
								class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})"
									value='<s:date name="visitDate" format="yyyy-MM-dd"/>'/>
					      	 </td>
			      	 		<td rowspan="2" valign="bottom">
					      	 	<input type="submit" value="查询" class="button" style="width:50px"/>
					      	</td>
					      </tr>
					    </table>
					</s:form>
		      	 </td>      
		      </tr>
	       </table>
    	</div>
    	<div class="x-panel-body">
			<div style="margin-left:-3px;" align="center">
		    	<ec:table items="items" var="item" retrieveRowsCallback="process" sortRowsCallback="limit"
		    	filterRowsCallback="limit"
				action="listVisitStuInfo.do"
				useAjax="true" doPreload="false"
				pageSizeList="10,20,45,60" 
				editable="false" 
				sortable="true"	
				rowsDisplayed="10"	
				generateScript="true"	
				resizeColWidth="true"	
				classic="true"
				width="100%" 	
				height="500"	
				minHeight="500"  
				xlsFileName="客户跟踪记录.xls"
				>
				<ec:row>
				    <ec:column width="40" property="_0" title="序号" value="${GLOBALROWCOUNT}"  style="text-align:center"/>
					<ec:column width="60" property="stuName" title="学生姓名">
						<a href="lookStuInfo.do?id=${item.id}" target="_blank" title="查看详细信息"><font color="blue">${item.stuName}</font></a>
					</ec:column>
					<ec:column width="60" property="advisor" title="负责顾问" style="text-align:center"/>
					<ec:column width="80" property="recentTraceTime" title="回访日期" format="yyyy-MM-dd" cell="date" style="text-align:center"/>
					<ec:column width="750" property="traceDesc" title="客户跟踪记录" style="height:80px;word-break:break-all;word-wrap:break-word;">
						<div style="height:80px;word-break:break-all;word-wrap:break-word;white-space:normal;line-height:15px">${item.traceDesc }</div>
					</ec:column>
				</ec:row>
			</ec:table>
		</div>
   	</div>
</div>
</body>
</html>
