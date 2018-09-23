<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp"%>
<html>
	<head>
		<style type="text/css">
@import "${ctx}/js/dojo/jsam/resources/tooltip.css";

@import "${ctx}/js/dojo/jsam/resources/tree.css";

@import "${ctx}/js/dojo/jsam/resources/treeList.css";
</style>
		<%@include file="/pages/admin/security/role/assignRoles.jsp"%>
		<%@include file="/common/meta.jsp"%>
		<%@include file="/common/extSkin.jsp"%>
		<script type='text/javascript'
			src='${ctx}/pages/admin/links/sortSelect.js'></script>
		<script type="text/javascript" src="${ctx}/js/dojo/dojo/dojo.js"
			djConfig="isDebug:true, parseOnLoad: true"></script>
		<script type="text/javascript"
			src="${ctx}/dwr/interface/DeptAction.js"></script>
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

	function hideSelect(tt){  
	   if(!/msie 6/i.test(navigator.userAgent)) 
	   return;
	   var L =   $("ifmid");
	   var s =   $("deptTree");   
	   var t =   tt.offsetTop;           //TT控件的定位点高   
	   var h =   tt.clientHeight;        //TT控件本身的高   
	   var l =   tt.offsetLeft;          //TT控件的定位点宽   
	   var ttyp  =   tt.type;            //TT控件的类型   
	   while (tt   =   tt.offsetParent){t+=tt.offsetTop;l+=tt.offsetLeft;}   
	   L.style.top =  (ttyp=="text")? t+h+5:t+h+6;   //层的   Y   坐标   
	   L.style.left=   l+1;                          //层的   X   坐标   
	   // 定义iframe的样式，宽高与s相同  
       L.width=s.style.width;  
       L.height=s.style.height; 
	   if(L.style.visibility == 'hidden'){
       		L.style.visibility = 'visible';
       	}else{
	   		L.style.visibility = 'hidden';
       	}    
 	 }	
</script>
		<title></title>
	</head>

	<body>
		<div class="x-panel">
			<div class="x-panel-header">
				部门管理
			</div>
			<div class="x-toolbar">
				<table width="30%">
					<tr align="left">
						<td>
							<a href="${ctx}/admin/dept/list.do"><img
									src="${ctx}/images/icons/home_1.gif" />部门管理首页</a>
						</td>
						<td>
							<span class="ytb-sep"></span>
						</td>
						<td>
							<a href="${ctx}/admin/dept/create.do"><img
									src="${ctx}/images/icons/users_1.gif" />新建部门</a>
						</td>
						<td>
							<span class="ytb-sep"></span>
						</td>
						<td>
							<a href="${ctx}/admin/dept/orderDepts.do"><img
									src="${ctx}/images/icons/col-move-top.gif" />部门排序</a>
						</td>
						<td>
							<span class="ytb-sep"></span>
						</td>
					</tr>
				</table>
			</div>

			<table width="400px" align="center">
				
				<tr>
					<td align="center">
						<fieldset style="margin: 10px;">
							<legend>
								部门查询
							</legend>
							<s:form action="orderDepts" theme="simple">
							<table>
							<tr>
								<td>部门名称：
									<s:textfield theme="simple" onclick="hideSelect(this);" name="name" size="15" id="deptName"
									cssClass="tree-select" />
								<s:hidden id="deptId" name="id" />
								<div id="deptTree" 
									dojoType="jsam.TreeInput"
									textNodeId="deptName" 
									class="samTreeList"
									 valueNodeId="deptId"></div>					
								&nbsp;&nbsp;
								<s:submit value="查询" cssClass="button" /></td>
							</tr>
							<tr>
							<td>
							<iframe id ="ifmid" style="position:absolute; visibility:hidden;z-index:0; filter='progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)';"></iframe>
							</td>		      
							</tr>
							</table>
							</s:form>
						</fieldset>
						<fieldset style="margin: 10px;">
							<legend>
								编辑部门信息
							</legend>
							<table align="center">
								<tr>
									<td align="center">
										<s:form id="frmOrder" name="frmOrder" namespace="/admin/dept"
											action="saveOrderDept" method="post" theme="simple">

											<s:hidden name="seqNoList" />
											<s:select id="order" name="order" list="orderDepts"
												listKey="id" listValue="name" multiple="true" size="20"
												cssStyle="width:210px;" />
											<br />
											<input type="text" id="jumpNum" name="jumpNum" size="10" />
											<input type="button" class="button" onclick="sl.jump()"
												value="跳 转" />
									</td>
									<td>
										<input id="first" type="button" class="button"
											onclick="sl.fnFirst()" value="第一" />
										<br />
										<br />
										<input id="up" type="button" class="button"
											onclick="sl.sortUp()" value="上移" />
										<br />
										<br />
										<input id="down" type="button" class="button"
											onclick="sl.sortDown()" value="下移" />
										<br />
										<br />
										<input id="fnEnd" type="button" class="button"
											onclick="sl.fnEnd()" value="最后" />
										<br />
										<br />
										<input id="ok" type="button" class="button" onclick="sl.ok()"
											value="确定" />
									</td>
								</tr>
							</table>
						</fieldset>
					</td>
				</tr>
			</table>
			</s:form>
		</div>
		<script type="text/javascript">
var sl = new SortSelect('frmOrder','order','search','jumpNum');
</script>
	</body>
</html>