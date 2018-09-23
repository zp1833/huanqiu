<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>

<html>
<head>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/extSkin.jsp" %>
<title></title>
<%@include file="/common/ec.jsp" %>
<%@include file="/pages/admin/security/role/assignRoles.jsp" %>
<script type="text/javascript" src="${ctx}/js/dojo/dojo/dojo.js" djConfig="isDebug:true, parseOnLoad: true"></script>
<script type="text/javascript" src="${ctx}/dwr/interface/DeptAction.js"></script>
<style type="text/css">
@import "${ctx}/js/dojo/jsam/resources/tooltip.css"; 
  @import "${ctx}/js/dojo/jsam/resources/tree.css";
  @import "${ctx}/js/dojo/jsam/resources/treeList.css";
</style>
<script type="text/javascript">
dojo.require("dijit.Tooltip");
dojo.require("jsam.Tree");
dojo.require("dojo.parser");
jsam.TreeAbstractNode.prototype.each = function(nodeValue) {
    nodeValue.onclick = function(value){};
}
dojo.addOnLoad(function() {
	DeptAction.getDeptTree(null, true, function(dept) {  
	           var tree = dijit.byId("deptTree");
	           tree.render(dept);
	});
}); 
function onRemove() {
    var sels = document.getElementsByTagName('input');
    var checked = false;;
    for(i = 0; i < sels.length; i++) {
        if(sels[i].id == 'selectedItems' && sels[i].checked) {
           checked = true;
           break;
        }
    } 
    if(!checked) {
        alert('请至少选择一个部门。');
        return;
    }
    
    if(confirm("您确定删除部门吗？")) {
       $('ec').action = "${ctx}/admin/dept/remove.do";
       $('ec').submit();
    } else {
       return false;
    }
}
</script>
</head>
<body>
<div class="x-panel">
    	<div>
           <table  class="x-panel-header" width="100%">
           	<tr>
           		<td>部门管理</td>
           		<td align="right">
           			 
           		</td>
           	</tr>
           </table>
		</div>	           	
    	<div class="x-toolbar">
	    	<table width="99%">	      
		      <tr>	         
		         <td>
			         <s:form action="list" theme="simple">
				         部门名称：
		     	 <s:textfield theme="simple" name="name" size="15" id="deptName" cssClass="tree-select"/>
	                <s:hidden id="deptId" name="id"/>
	        <div id="deptTree" 
              dojoType="jsam.TreeInput" 
		      textNodeId="deptName" 
		      class="samTreeList"
		      valueNodeId="deptId" ></div>&nbsp;&nbsp;		      
	        <s:submit value="查询" cssClass="button"/>
			         </s:form>
		         </td>
		         <td align="right">
		         <table>
			         <tr>
			         <td><a href="${ctx}/admin/dept/create.do"><img src="${ctx}/images/icons/users_1.gif"/> 新建部门</a></td>
			         <td><span class="ytb-sep"></span></td>
			         <td><a href="#" onclick="onRemove()"><img src="${ctx}/images/icons/delete.gif"/> 删除部门</a></td>
		         </tr>
		         </table>
		         </td>
		      </tr>
	       </table>
    	</div>
    	<div class="x-panel-body">
    	    <div style="margin-left:-3px;" align="center">
		    	<ec:table items="items" var="item" retrieveRowsCallback="process"
				action="list.do"
				useAjax="true" doPreload="false"
				pageSizeList="10,20" 
				editable="false" 
				sortable="true"	
				rowsDisplayed="10"	
				generateScript="true"	
				resizeColWidth="true"	
				classic="true"
				width="100%" 	
				height="500px"	
				minHeight="255"  
				>
				<ec:row>
				    <ec:column width="50" property="_s" title="选择" style="text-align:center" sortable="false">
				       <input type="checkbox" name="selectedItems" id="selectedItems" value="${item.id}"/>
				    </ec:column>
					<ec:column width="150" property="name" title="部门名称" />
				    <ec:column width="80" property="descn" title="部门描述" sortable="false" viewsAllowed="html">
			             <div align="center" style="color:#6600FF" id="detail_${item.id}">浏览信息</div>
			             <span dojoType="dijit.Tooltip" connectId="detail_${item.id}" style="display:none;">
			                <span style="font-size:12px;">
			                ${item.descn}
			                </span>
			             </span> 
		            </ec:column> 
		            <ec:column width="70" property="_0" title="下级部门" style="text-align:center" sortable="false">
						<c:choose>
						    <c:when test="${item.hasChild}">
							    <a href="${ctx}/admin/dept/list.do?parentId=${item.id}">
							    <font color="blue">下级部门</font>
							    </a>
						    </c:when>
						    <c:otherwise>
						       无
						    </c:otherwise>
						</c:choose>
					</ec:column>
					<ec:column width="50" property="_0" title="操作" style="text-align:center" sortable="false">
					   <a href="edit.do?model.id=${item.id}">
					       编辑
					   </a>					    
					</ec:column>
				</ec:row>
				</ec:table>
			</div>
			
    	</div>
</div>

</html>