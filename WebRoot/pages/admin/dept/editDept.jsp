<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>编辑部门</title>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/extSkin.jsp" %>
<script type="text/javascript" src="${ctx}/js/dojo/dojo/dojo.js" djConfig="isDebug:false, parseOnLoad: true"></script>
<%@include file="/common/dwr.jsp" %>
<script type="text/javascript" src="${ctx}/dwr/interface/DeptAction.js"></script>
<style type="text/css">
		@import "${ctx}/js/dojo/jsam/resources/tree.css";
		@import "${ctx}/js/dojo/jsam/resources/treeList.css";
</style>
<script type="text/javascript">
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
</script>
</head>

<body onload="preFckEditor()">
<div class="x-panel">
    	<div class="x-panel-header">部门管理</div>
    	<div class="x-toolbar">
	    	<table width="100%">	      
		      <tr>	         
		         <td align="right">
			        <table>
				        <tr>
					        <td> <a href="${ctx}/admin/dept/list.do">
					             <img src="${ctx}/images/icons/home_1.gif" border="0"/> 部门首页</a>
					        </td>
				        </tr>
			        </table>
			     </td>
		      </tr>
	       </table>
    	</div>
    	<div class="x-panel-body">
	    	<div>
				<%@ include file="/common/messages.jsp"%>
			</div>
			
			<s:form action="save" theme="simple" validate="true" method="POST" onsubmit="return checkForm()" cssClass="x-form">
		   		<s:hidden name="model.id"/>
		   		<s:hidden name="model.serialNo"/>
		   		<s:hidden name="model.orderId"/>
		   		<fieldset style="margin:10px;">
			    <legend>部门基本信息</legend>
				<table width="100%">
                        <tr>
                             <td align="right">部门名称：</td>
                             <td style="text-align:left;"><s:textfield name="model.name" id="deptName" theme="simple" size="30"/></td>
                        </tr>
                        <%--<tr>
                             <td align="right">部门电话：</td>
                             <td style="text-align:left;"><s:textfield name="model.tel" id="deptTel" theme="simple" size="30"/></td>
                        </tr>
                        <tr>
                             <td align="right">部门邮箱：</td>
                             <td style="text-align:left;"><s:textfield name="model.email" id="deptEmail" theme="simple" size="30"/></td>
                        </tr>--%>
                        <tr>
                             <td align="right">上级部门：</td>
                             <td style="text-align:left;">
                                <div>
	                               <s:textfield id="parentName" name="model.parentDept.name" 
	                               theme="simple" size="30" cssClass="tree-select" readonly="true"/>
	                               &nbsp;注：双击部门树型列表，确定所属的上级部门。
	                             </div>
		                         <s:hidden id="parentId" name="model.parentDept.id"/>
		                         <div id="deptTree" 
		                         dojoType="jsam.TreeInput" 
		                         textNodeId="parentName" 
		                         class="samTreeList"
		                         valueNodeId="parentId" ></div>
                             </td>
                        </tr>
                        <tr align="right">
                             <td>部门描述：</td>
                             <td style="text-align:left;">
                             	<s:textarea name="model.descn" id="model.descn" theme="simple"
                             cssStyle="border:1px #cbcbcb solid;z-index:auto;width:500px;height:60px"/>
                             </td>
                        </tr>
                </table>
                </fieldset>
                <table width="100%" style="margin-bottom:10px;">
                       <tr>
                        <td style="text-align:center;">
                            <s:submit value="保存" cssClass="button"></s:submit>
                            <s:reset value="重置" cssClass="button"></s:reset></td>
                        </tr>
                </table>
                
                </s:form>
                
    	</div>
  </div>
</body>
</html>