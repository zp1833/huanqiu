<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<%@include file="/common/meta.jsp" %>
<title></title>
<%@include file="/common/ec.jsp" %>
<%@include file="/common/extSkin.jsp" %>
<%@include file="/common/dwr.jsp" %>
<script type="text/javascript" src="${ctx}/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/dwr/interface/WorkPlanAction.js"></script>
</head>
<body>
<script type="text/javascript">
	function onRemove() {
	    var sels = document.getElementsByName('selectedItems');
	    var checked = false;
	    for(i = 0; i < sels.length; i++) {
	        if(sels[i].id == 'selectedItems' && sels[i].checked) {
	           checked = true;
	           break;
	        }
	    } 
	    if(!checked) {
	        alert('请至少选择一条记录。');
	        return;
	    }
	    
	    if(confirm("您确定删除所选记录吗？")) {
	       var articles = new Array();
	       var j=0;
		    for (var i = 0; i < sels.length; i ++) {
		    	if (sels[i].checked) {
		    		articles[j]=sels[i].value;
		    		j++;
		    	}
		    }

		  WorkPlanAction.removeWorkPlan(articles,function(result){
				  if(result == "1"){
				   	ECSideUtil.reload('ec');
				  } else {
				  	alert("删除中出现错误，请重新登陆！");
				  }
			});
	    } else {
	       return false;
	    }
	}
</script>
<div class="x-panel">
  <div class="x-panel-header">工作计划管理</div>
    <div class="x-toolbar">
      <table width="99%">
        <tr>
          <td> 
            <s:form action="listWorkPlan" theme="simple">
            <stc:role ifAnyGranted="ROLE_ADMIN,ROLE_ORADMIN">
            	员工姓名：<select name="model.employee.id" style="width:88px">
							<option value="">--请选择--</option>
							<c:forEach items="${allEmps}" var="emp">
								<option ${model.employee.id==emp.id?"selected":"" } value="${emp.id }">${emp.name }</option>
							</c:forEach>
						</select>
			</stc:role>
		        计划起始日期：<input type="text" name="beginStartDate" size="12" id="beginStartDate" 
					class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})"
						value='<s:date name="beginStartDate" format="yyyy-MM-dd"/>'/> 至
				   <input type="text" name="endStartDate" size="12" id="endStartDate" 
					class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})"
						value='<s:date name="endStartDate" format="yyyy-MM-dd"/>'/>
						
			    计划截止日期：<input type="text" name="beginEndDate" size="12" id="beginEndDate" 
					class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})"
						value='<s:date name="beginEndDate" format="yyyy-MM-dd"/>'/> 至
				   <input type="text" name="endEndDate" size="12" id="endEndDate" 
					class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})"
						value='<s:date name="endEndDate" format="yyyy-MM-dd"/>'/>		
	        &nbsp;&nbsp;<s:submit value="查询" cssClass="button"></s:submit>
         </s:form>
         </td>
         <td align="right">
         <table>
           <tr>
             <td><a href="${ctx}/admin/workplan/newWorkPlan.do"><img src="${ctx}/images/exticons/add.gif"/>添加</a></td>
             <td><span class="ytb-sep"></span></td>
             <td><a href="#" onclick="onRemove()"><img src="${ctx}/images/icons/delete.gif"/>删除</a></td>
           </tr>
         </table>
         <td>
       </tr>
     </table>
   </div>   

   <div class="x-panel-body">
     <div style="margin-left:-3px;" align="center">
	<ec:table items="items" var="item" retrieveRowsCallback="limit" sortRowsCallback="limit" 
	filterRowsCallback="limit"
	action="listWorkPlan.do"
	useAjax="true" doPreload="false"
	xlsFileName="工作计划列表.xls" 
	maxRowsExported="1000" 
	pageSizeList="5,10,20,100" 
	editable="false" 
	sortable="true"	
	rowsDisplayed="5"	
	generateScript="true"	
	resizeColWidth="true"	
	classic="false"	
	width="100%" 	
	height="350px"	
	minHeight="200"  
	>
	<ec:row>
	    <ec:column width="50" property="_s" title="选择" style="text-align:center" viewsAllowed="html">
	       <input type="checkbox" name="selectedItems" id="selectedItems" value="${item.id}"/>
	    </ec:column>
		<ec:column width="40" property="_0" title="序号" value="${GLOBALROWCOUNT}" style="text-align:center" sortable="false"/>
		<stc:role ifAnyGranted="ROLE_ADMIN,ROLE_ORADMIN">
			<ec:column width="60" property="employee" title="员工姓名" style="text-align:center">
				${item.employee.name }
			</ec:column>
		</stc:role>
		<ec:column width="80" property="startDate" title="起始日期" cell="date" format="yyyy-MM-dd" style="text-align:center"/>
		<ec:column width="80" property="endDate" title="截止日期" cell="date" format="yyyy-MM-dd" style="text-align:center"/>
		<ec:column width="400" property="content" title="工作内容" style="height:40px"/>
		<ec:column width="40" property="_0" title="操作" style="text-align:center" viewsAllowed="html">
		   <a href="editWorkPlan.do?model.id=${item.id}"> 
		      <img src="${ctx}/images/icons/modify.gif" style="border:0px" title="编辑"/>
		   </a>
		</ec:column>
	</ec:row>
	</ec:table>
  </div>
  </div>
  </div>
</body>
</html>