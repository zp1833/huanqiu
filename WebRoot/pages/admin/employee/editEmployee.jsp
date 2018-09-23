<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/meta.jsp" %>
<title>编辑用户</title>
<%@include file="/common/extSkin.jsp" %>
<script type="text/javascript" src="${ctx}/js/dojo/dojo/dojo.js" djConfig="isDebug:false, parseOnLoad: true"></script>
<script type="text/javascript" src="${ctx}/js/util.js"></script>
<%@include file="/common/dwr.jsp" %>
<%@include file="/common/dwrLoadingMessage.jsp" %>
<script type="text/javascript" src="${ctx}/dwr/interface/DeptAction.js"></script>
<script type="text/javascript" src="${ctx}/dwr/interface/GB2Alpha.js"></script>
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
    Util.resetDatePicker('graduateDate'); //毕业时间
    Util.resetDatePicker('birthday'); //出生日期
    Util.resetDatePicker('jobDate'); //参加工作时间
	DeptAction.getDeptTree(null, true, function(dept) {  
	           var tree = dijit.byId("deptTree");
	           tree.render(dept);
	});
});    

function chang() {
  var name = dojo.byId('name').value;
  GB2Alpha.string2Alpha(name, function(str){
    $('loginId').value = str.toLowerCase();
  });
}

function onCheck() {
  $("queryForm").action = "${ctx}/admin/employee/checkLoginId.do";
  $("queryForm").submit();
}

</script>
</head>
<body>
<%@include file="/common/calendar.jsp"%>
<div class="x-panel">
  <div class="x-panel-header">用户管理</div>
  <div class="x-toolbar">
    <table width="98%">
      <tr>
        <td align="right">
          <table>
            <tr>
              <td>
                <a href="${ctx}/admin/employee/listEmployees.do">
                  <img src="${ctx}/images/icons/home_1.gif" border="0"/>用户管理首页
                </a>
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
	<s:form action="saveEmployee" theme="simple" validate="true" method="POST" id="queryForm"
               enctype="multipart/form-data" cssClass="x-form">
    <s:hidden id="model.id" name="model.id"/>
    <s:hidden id="model.version" name="model.version"/>
    <s:hidden id="model.user.id" name="model.user.id" /> 
    <s:hidden id="model.user.status" name="model.user.status"/>
    <s:hidden id="model.orderId" name="model.orderId"/>
    <s:hidden id="model.user.version" name="model.user.version"/>
    <fieldset style="margin:10px;">
      <legend>用户基本信息</legend>
	  <table width="80%">
	    <tr>
		  <td style="text-align:right;">姓&nbsp;&nbsp;名&nbsp;：</td>
		  <td style="text-align:left;">
		    <s:textfield name="model.name" theme="simple" id="name" size="20" onchange="chang()"/>
		    &nbsp;<font color="red">*</font>
		  </td>
		  <td style="text-align:right;">是否兼职文案&nbsp;：</td>
		  <td style="text-align:left;">
		    <input type="radio" value="1" name="model.isDoDoc" ${model.isDoDoc=='1'?"checked":"" }>是&nbsp;
		    <input type="radio" value="0" name="model.isDoDoc" ${model.isDoDoc=='0'?"checked":"" }>否
		  </td>
		</tr>
		<tr>
		  <td style="text-align:right;">性&nbsp;&nbsp;别&nbsp;：</td>
		  <td style="text-align:left;">
		    <s:radio list="sexMap" name="model.sex" cssStyle="border:0px;"/>
		  </td>
		  <td style="text-align:right;">是否部门主管&nbsp;：</td>
		  <td style="text-align:left;">
		    <input type="radio" value="1" name="model.isDeptAdmin" ${model.isDeptAdmin=='1'?"checked":"" }>是&nbsp;
		    <input type="radio" value="0" name="model.isDeptAdmin" ${model.isDeptAdmin=='0'?"checked":"" }>否
		  </td>
		</tr>
		<tr>
		  <td style="text-align:right;">照&nbsp;&nbsp;片&nbsp;：</td>
		  <td style="text-align:left;">
		    <s:file name="image" size="8" cssClass="FileText"/> 
	        <c:if test="${model.photo != null && model.photo != ''}">
              <a href="${ctx}${model.photo}" target="_blank">
                <img src="${ctx}${model.photo}" width="20" height="20" border="0" title="查看大图"/>  
              </a>
            </c:if>
            <s:hidden name="model.photo" />
		  </td>
		  <td style="text-align:right;">民&nbsp;&nbsp;族&nbsp;：</td>
		  <td style="text-align:left;">
		    <s:textfield name="model.folk" theme="simple" size="20"/>
		  </td>
		</tr>
		<tr>
		  <td style="text-align:right;">籍&nbsp;&nbsp;贯&nbsp;：</td>
		  <td style="text-align:left;">
		     <s:textfield name="model.address" theme="simple" size="20" />
		  </td>
		  <td style="text-align:right;">邮&nbsp;&nbsp;编&nbsp;：</td>
		  <td style="text-align:left;">
		    <s:textfield name="model.zip" theme="simple" size="20"/>
		  </td>
		</tr>
		<tr>
		  <td style="text-align:right;">户口所在地&nbsp;：</td>
		  <td style="text-align:left;">
		    <s:textfield name="model.registeredPos" theme="simple" size="20"/>
		  </td>
		  <td style="text-align:right;">家庭电话&nbsp;：</td>
		  <td style="text-align:left;">
		    <s:textfield name="model.hTel" theme="simple" size="20"/>
		  </td>		  
		</tr>
		<tr>
		  <td style="text-align:right;">毕业学校&nbsp;：</td>
		  <td style="text-align:left;">
		    <s:textfield name="model.school" theme="simple" size="20"/>
		  </td>
		  <td style="text-align:right;">所学专业&nbsp;：</td>
		  <td style="text-align:left;">
		    <s:textfield name="model.speciality" theme="simple" size="20"/>
		  </td>
		</tr>
		<tr>
		  <td style="text-align:right;">学&nbsp;&nbsp;历&nbsp;：</td>
		  <td style="text-align:left;">
		    <s:select list="degreeMap" name="model.degree" cssStyle="width:150px;"/>
		  </td>
		  <td style="text-align:right;">政治面貌&nbsp;：</td>
		  <td style="text-align:left;">
		    <s:select list="politicalMap" name="model.political" cssStyle="width:150px;"/>
		  </td>
		</tr>
		<tr>
	      <td style="text-align:right;">出生日期&nbsp;：</td>
		  <td style="text-align:left;">
		    <input type="text" name="model.birthday" size="20" 
		           id="birthday" class="Wdate" value='<s:date name="model.birthday" format="yyyy-MM-dd"/>'
		            onfocus="new WdatePicker(this, null, false, 'whyGreen')" maxdate="#F{$('jobDate').value}"/>
		  </td>
		  <td style="text-align:right;">毕业时间&nbsp;：</td>
		  <td style="text-align:left;">
		    <input type="text" name="model.graduateDate" size="20" 
		           id="graduateDate" class="Wdate" value='<s:date name="model.graduateDate" format="yyyy-MM-dd"/>'
		            onfocus="new WdatePicker(this, null, false, 'whyGreen')" 
		            mindate="#F{$('birthday').value}" maxdate="2030-12-30"/>
		  </td>
		</tr>
	    <tr>
		  <td style="text-align:right;">参加工作时间&nbsp;：</td>
		  <td style="text-align:left;">
		    <input type="text" name="model.jobDate" size="20" 
		           id="jobDate" class="Wdate" value='<s:date name="model.jobDate" format="yyyy-MM-dd"/>'
		            onfocus="new WdatePicker(this, null, false, 'whyGreen')" 
		            mindate="#F{$('birthday').value}" maxdate="2030-12-30"/>
		  </td>
		  <td style="text-align:right;">传&nbsp;&nbsp;真&nbsp;：</td>
		  <td style="text-align:left;">
		    <s:textfield name="model.fax" theme="simple" size="20"/>
		  </td>
		</tr>
		<tr>
		  <td style="text-align:right;">所在部门&nbsp;：</td>
		  <td style="text-align:left;">
		    <s:if test="model.id != null && model.dept == null">
		      <s:textfield name="model.dept.name" theme="simple" size="20" id="deptName"
		       value="" cssClass="tree-select"/>&nbsp;<font color="red">*</font>
		      <s:hidden id="deptId" name="model.dept.id" value="0" />
		    </s:if>
		    <s:else>
		      <s:textfield name="model.dept.name" theme="simple" size="20" id="deptName"
		       cssClass="tree-select"/>&nbsp;<font color="red">*</font>
		      <s:hidden id="deptId" name="model.dept.id"/>
		    </s:else>            
            <div id="deptTree" 
              dojoType="jsam.TreeInput" 
		      textNodeId="deptName" 
		      class="samTreeList"
		      valueNodeId="deptId"></div>
		  </td>
		  <td style="text-align:right;">职&nbsp;&nbsp;位&nbsp;：</td>
		  <td style="text-align:left;">
		    <s:textfield name="model.place" theme="simple" size="20"/>
		  </td>
		</tr>
	    <tr>
		  <td style="text-align:right;">电子邮箱&nbsp;：</td>
		  <td style="text-align:left;">
		    <s:textfield name="model.email" theme="simple" size="20" />
		  </td>
		  <td style="text-align:right;">M&nbsp;S&nbsp;N&nbsp;：</td>
		  <td style="text-align:left;">
		    <s:textfield name="model.msn" theme="simple" size="20"/>
		  </td>
		</tr>
		<tr>
		  <td style="text-align:right;">手&nbsp;&nbsp;机&nbsp;：</td>
		  <td style="text-align:left;">
		    <s:textfield name="model.mobil" theme="simple" size="20"/>
		  </td>
		  <td style="text-align:right;">Q&nbsp;&nbsp;Q&nbsp;：</td>
		  <td style="text-align:left;">
		    <s:textfield name="model.qq" theme="simple" size="20"/>
		  </td>
		</tr>
		<tr>
		  <td style="text-align:right;">简&nbsp;&nbsp;介&nbsp;：</td>
		  <td colspan="3" style="text-align:left;">
		    <s:textarea name="model.descn" theme="simple" rows="3" cssStyle="width:90%;"/>
		  </td>
		</tr>
	  </table>
	</fieldset>
	<fieldset style="margin:10px;">
	  <legend>用户登录信息</legend>
	  <table width="80%">
	    <tr>
		  <td style="text-align:right;">登&nbsp;录&nbsp;名&nbsp;：</td>
		  <td style="text-align:left;">
		    <s:textfield name="model.user.loginId" id="loginId" theme="simple" size="20"/>&nbsp;<font color="red">*</font>		    
		  </td>
		  <td style="text-align:right;">
		    <%-- <input type="button" id="checkLoginId" value="检查用户名" onclick="onCheck()" class="button">--%>
		        被接替人&nbsp;：
		  </td>
		  <td>
		  	<input type="text" name="insteadUser" size="20">
		  	&nbsp;<font color="red">*请填写正确员工姓名，如不接替可不填</font>
		  </td>
		</tr>
		<tr>		  
		  <td style="text-align:right;">登录密码&nbsp;：</td>
		  <td style="text-align:left;">
		    <s:password name="model.user.password" id="password" theme="simple" cssStyle="width:150px"/>&nbsp;<font color="red">*</font>
		  </td>
		  <td style="text-align:right;">重复密码&nbsp;：</td>
		  <td style="text-align:left;">
		    <s:password name="model.user.confirmPwd" id="confirmPwd" theme="simple" cssStyle="width:150px"/>&nbsp;<font color="red">*</font>
		  </td>		   
		</tr>
	  </table>
	</fieldset>
	<table width="80%" style="margin-bottom:10px;">
	  <tr>
	    <td style="text-align:center;">
	      <s:hidden name="currentPageNo"></s:hidden>
	      <s:submit value="保存" cssClass="button"/>
	      <s:reset value="重置" cssClass="button"/>
	    </td>
	  </tr>
	</table>       
  </s:form>
  </div>
  <c:if test="${model.user.id != null}">
	  <script type="text/javascript">
	  dojo.addOnLoad(function() {
	     dojo.byId("password").value = "*********";
	     dojo.byId("confirmPwd").value = "*********";
	  });
	  </script>
  </c:if>
</body>
</html>