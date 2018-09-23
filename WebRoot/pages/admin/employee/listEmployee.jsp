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
<script type="text/javascript" src="${ctx}/dwr/interface/DeptAction.js"></script>
<style type="text/css">
		@import "${ctx}/js/dojo/jsam/resources/tree.css";
		@import "${ctx}/js/dojo/jsam/resources/treeList.css";
		@import "${ctx}/js/dojo/jsam/resources/tooltip.css"; 
</style>
<script type="text/javascript">
var falg = true;
var intervalId = null;
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

function removeEmp(id, name) {
  if (confirm('您确定删除名称为："' + name + '" 的用户吗？')) {
    $("model.id").value = id;
    $("frmRem").submit();
  }
}

function initWatting(){
		<c:if test="${currentPageNo != 0}">
		var xec = ECSideUtil.getGridObj("ec");
		
		
			if(xec.waitingBar != null && falg == true){
				jump(${currentPageNo});
				falg = false;
			}
			if(falg){
			 	intervalId = setInterval(initWatting,200);	
			 }else{
			 clearInterval(intervalId);
			 
			 }
		if(!falg){
			var sels = document.getElementsByName('selectedItems');
			for(var i=0;i<sels.length;i++){
				if(sels[i].value == ${model.id}){
					var row1 = sels[i].parentNode.parentNode;
					ECSideUtil.selectRow(row1,'ec');
				}
			}
		}
		</c:if>
	}
	
	function jump(page){
		ECSideUtil.gotoPage(page,'ec');
	}
</script>
</head>
<body onload="initWatting();">
<div class="x-panel">
  <div class="x-panel-header">用户管理</div>
  <div class="x-toolbar">
    <table width="100%">
      <tr>
        <td>
          <s:form action="listEmployees" theme="simple">
              用户姓名：<s:textfield theme="simple" name="model.name" size="15"/>&nbsp;&nbsp;
	         部门名称：
	         <s:textfield theme="simple" name="model.dept.name" size="15" id="deptName" cssClass="tree-select"/>
	                <s:hidden id="deptId" name="model.dept.id"/>
	        <div id="deptTree" 
              dojoType="jsam.TreeInput" 
		      textNodeId="deptName" 
		      class="samTreeList"
		      valueNodeId="deptId"></div>&nbsp;&nbsp;		      
	        <s:submit value="查询" cssClass="button"/>
         </s:form>
        </td>
        <td align="right">
          <table>
            <tr>
              <td><a href="${ctx}/admin/employee/newEmployee.do"><img src="${ctx}/images/icons/user.jpg"/> 新建用户</a></td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </div>
  <div class="x-panel-body">
    <div style="margin-left:-3px;" align="center">
      <ec:table items="items" var="item" retrieveRowsCallback="limit"sortRowsCallback="limit"
	    action="listEmployees.do"
	    useAjax="true" doPreload="false"
	    xlsFileName="用户列表.xls" 
	    maxRowsExported="10000000"
	    pageSizeList="15,30,50,100,500,1000" 
		editable="false" 
		sortable="true"	
		rowsDisplayed="15"	
		generateScript="true"	
		resizeColWidth="true"	
		classic="false"	
		width="100%" 	
		height="350px"	
		minHeight="200" 
		>
		  <ec:row>
		    <ec:column width="40" property="_s" title="序号" value="${GLOBALROWCOUNT}" sortable="false" style="text-align:center;"/>
			<ec:column width="50" property="user.loginId" title="登录名" />
			<ec:column width="50" property="name" title="姓名" />
			<ec:column width="40" property="sex" title="性别" mappingItem="sexMap" style="text-align:center;"/>
			<ec:column width="70" property="dept.name" title="所属部门">
			  <c:if test="${item.dept == null}">
			    津桥留学
			  </c:if>
			  <c:if test="${item.dept != null}">
			    ${item.dept.name }
			  </c:if>
			  <input type="hidden" value="${item.id}" name="selectedItems">
			</ec:column>
			<ec:column width="100" property="mobil" title="手机" sortable="false"/>
			<ec:column width="100" property="email" title="电子邮箱" sortable="false"/>
			<ec:column width="80" property="_0" title="操作" style="text-align:center" sortable="false"  viewsAllowed="html">
		  	  <a href="editEmployee.do?model.id=${item.id}&currentPageNo=${pno}">
		  	  	<img src="${ctx}/images/icons/modify.gif" border="0" title="编辑"/>
		  	  </a>		  
		  	  <a href="#" onclick="removeEmp(${item.id}, '${item.name}')">
			  	<img src="${ctx}/images/icons/delete.gif" border="0" title="删除"/>
			   </a>  
			</ec:column>
			<ec:column width="40" property="_1" title="角色" style="text-align:center" sortable="false"  viewsAllowed="html">
			  <a href="#" id="role_${item.id}" onclick="javascript:UserManager.assignRoles(${item.user.id}, 1)">
		       <img src="${ctx}/images/icons/role.gif" border="0" title="分配角色"/>
		   　　    </a>		      
		      <span connectId="role_${item.id}" dojoType="dijit.Tooltip" style="display:none;">
		         <c:if test="${item.user.hasRoles == true}">
			         <table bgcolor="#ffffff" cellpadding="1" cellspacing="1">
				          <tr>
					          <td bgcolor="#cecece">角色名称</td>
					          <td bgcolor="#cecece">角色描述</td>
				          </tr>			         
				          <c:forEach items="${item.user.roles}" var="r">
				          	  <tr>
				          	      <td bgcolor="#eeeeee">${r.name}</td>
				          	      <td bgcolor="#eeeeee">${r.descn}</td>
				          	  </tr>
				          </c:forEach>	          
				      </table>
			      </c:if>
			      <c:if test="${item.user.hasRoles == false}">待分配...</c:if>
		      </span>
			</ec:column>
	  	  </ec:row>
		</ec:table>
      </div>
    </div>
  </div>
  <s:form id="frmRem" namespace="/admin/employee" action="removeEmployees" method="post">
  <s:hidden id="model.id" name="model.id"/>
  </s:form>
  <%@include file="/common/dwrLoadingMessage.jsp" %> 
</body>
</html>