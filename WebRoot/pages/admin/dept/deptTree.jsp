<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>

<script type="text/javascript" src="${ctx}/js/dojo/dojo/dojo.js" djConfig="isDebug:true, parseOnLoad: true"></script>
<%@include file="/common/dwr.jsp" %>
<script type="text/javascript" src="${ctx}/dwr/interface/DeptAction.js"></script>
<style type="text/css">
		@import "${ctx}/js/dojo/jsam/resources/Dialog.css"; 
		@import "${ctx}/js/dojo/jsam/resources/tree.css";
		@import "${ctx}/js/dojo/jsam/resources/Grid.css";
</style>

<script type="text/javascript">
dojo.require("jsam.Tree");
dojo.require("jsam.Dialog");
dojo.require("dojo.parser");

var DeptSelector = function() {
};

DeptSelector.getTree = function() {
   return dijit.byId("deptTree");
};

DeptSelector.getDialog = function() {
   return dijit.byId("deptTreeDlg");
};

DeptSelector.forEach = function(dept) {
    return dept;
};

DeptSelector.every = function(dept) {
    DeptSelector.forEach(dept);
    if(dept.childNodes && dept.childNodes.length > 0) {
        for(var i = 0; i < dept.childNodes.length; i++) {
           DeptSelector.every(dept.childNodes[i]);
        }
    }
    
    return dept;
};

DeptSelector.prototype = {
    
    show : function() {
       DeptAction.getDeptTree(null, true, function(dept) {  
           var _sel = DeptSelector;
           dept = _sel.every(dept);
           var tree = _sel.getTree();
           tree.render(dept);
          
           _sel.getDialog().show();
       });       
    }
   
};
</script>
<div id="deptTreeDlg" dojoType="jsam.Dialog" title="<fmt:message key="selector.dept.title"/>" resizable="true"
    style="width: 300px; height: 380px;" >
	<div dojoType="jsam.Tree" id="deptTree" width="100%" openRoot="true"></div>
</div>