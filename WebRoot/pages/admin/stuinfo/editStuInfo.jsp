<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑学员信息</title>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/extSkin.jsp" %>
<%@include file="/common/dwr.jsp" %>
<script type="text/javascript" src="${ctx}/dwr/interface/StuInfoManager.js"></script>
<script type="text/javascript" src="${ctx}/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/js/TabPanel/tabpanel.js"></script>
<script type="text/javascript" src="${ctx}/js/fckeditor/fckeditor.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/js/TabPanel/css/style.css" />
<script type="text/javascript">
	function changeTab() {
		<stc:role ifAnyGranted="ROLE_ORADMIN">
			switchTab($("SystopTab2"),4);
		</stc:role>
	}
	
	function preFckEditor(){
		var fckEditor = new FCKeditor('model.traceDesc') ;
	    fckEditor.BasePath = "${ctx}/js/fckeditor/";
	    fckEditor.ToolbarSet = 'zp';
	    fckEditor.Height = 100;
	    fckEditor.ReplaceTextarea();
	}
	
	function checkForm() {
		if($("model.advDate").value.length == 0) {
			alert("请选择咨询日期");
			switchTab($("SystopTab1"),4);
			$("model.advDate").focus();
			return false;
		}
		if($("model.stuName").value.length == 0) {
			alert("请填写学生姓名");
			switchTab($("SystopTab1"),4);
			$("model.stuName").focus();
			return false;
		}
		if($("model.advisor").value.length == 0) {
			alert("请选择负责顾问");
			switchTab($("SystopTab1"),4);
			return false;
		}
		if($("model.advCourse").value.length == 0) {
			alert("请选择咨询课程");
			return false;
		}
		if($("model.source").value.length == 0) {
			alert("请选择信息来源");
			return false;
		}
		if($("model.sourceFrom").value.length == 0) {
			alert("请填写来源明细");
			switchTab($("SystopTab1"),4);
			$("model.sourceFrom").focus();
			return false;
		}
		if($("model.advType").value.length == 0) {
			alert("请选择咨询形式");
			switchTab($("SystopTab1"),4);
			return false;
		}
		if($("model.recEmp").value.length == 0) {
			alert("请选择接待人");
			switchTab($("SystopTab1"),4);
			return false;
		}
		var isStu = $("model.isStu").value;
	<stc:role ifAnyGranted="ROLE_ADV" ifNotGranted="ROLE_REC">
		if(isStu == "1") {
			if($("model.nextVisitTime").value.length == 0) {
				alert("请选择下次回访时间");
				switchTab($("SystopTab2"),4);
				$("model.nextVisitTime").focus();
				return false;
			}
			if($("model.statusDefineTime").value.length == 0) {
				alert("请选择状态定义日期");
				switchTab($("SystopTab2"),4);
				$("model.statusDefineTime").focus();
				return false;
			}
			if($("model.stuType").value.length == 0) {
				alert("请选择学生分类");
				switchTab($("SystopTab2"),4);
				return false;
			}
			if($("model.contact").value.length == 0) {
				alert("请填写联系方式");
				switchTab($("SystopTab2"),4);
				$("model.contact").focus();
				return false;
			}
			if($("model.enDegree").value.length == 0) {
				alert("请填写外语程度");
				switchTab($("SystopTab2"),4);
				$("model.enDegree").focus();
				return false;
			}
			if($("model.finishSchool").value.length == 0) {
				alert("请填写毕业院校");
				switchTab($("SystopTab2"),4);
				$("model.finishSchool").focus();
				return false;
			}
			if($("model.currentSchool").value.length == 0) {
				alert("请填写在读院校");
				switchTab($("SystopTab2"),4);
				$("model.currentSchool").focus();
				return false;
			}
			if($("model.currentGrade").value.length == 0) {
				alert("请填写在读年级");
				switchTab($("SystopTab2"),4);
				$("model.currentGrade").focus();
				return false;
			}
			if($("model.introToAgency").value.length == 0) {
				alert("请填写介绍给中介");
				switchTab($("SystopTab2"),4);
				$("model.introToAgency").focus();
				return false;
			}
			if($("model.country").value.length == 0) {
				alert("请选择留学国家");
				switchTab($("SystopTab2"),4);
				$("model.country").focus();
				return false;
			}
			if($("model.city").value.length == 0) {
				alert("请填写城市");
				switchTab($("SystopTab2"),4);
				$("model.city").focus();
				return false;
			}
			if($("model.signStatus").value == "1" && $("model.stuType").value != "已签约" || $("model.signStatus").value != "1" && $("model.stuType").value == "已签约") {
				alert("学生分类与签约情况不一致，请重新选择");
				return false;
			}
			
			var className = $("model.className").value;
			var courseName = $("model.courseName").value;
			var studyTime = $("model.studyTime").value;
			var tuition = $("model.tuition").value;
			var realMoney = $("model.realMoney").value;
			var payMethod = $("model.payMethod").value;
			var reportSchool = $("model.reportSchool").value;
			var dealPerson = $("model.dealPerson").value;
			var dealDate = $("model.dealDate").value;
		
			var signStatus = $("model.signStatus").value;
			var stuType = $("model.stuType").value;
			
			if(signStatus == "1" || stuType == "已签约") {
				if(className.length == 0 || courseName.length == 0 || studyTime.length == 0 || tuition.length == 0 || 
					realMoney.length == 0 || payMethod.length == 0 || reportSchool.length == 0 || dealPerson.length == 0 || dealDate.length == 0) {
					alert("报班信息必须全部填写！");
					switchTab($("SystopTab3"),6);
					return false;
				}
			} else {
				if(className.length > 0 || courseName.length > 0 || studyTime.length > 0 || tuition.length > 0 || 
					realMoney.length > 0 || payMethod.length > 0 || reportSchool.length > 0 || dealPerson.length > 0 || dealDate.length > 0) {
					alert("报班信息中若有其中一项填写后，其他项必须全部填写并且学生分类和签约情况需发生相应改变！");
					return false;
				}
			}
		}
		
		var isMember = $("model.isMember").value;
		if(isMember == "1") {
			if($("model.memberJoinTime").value.length == "0") {
				alert("请选择入会时间！");
				switchTab($("SystopTab4"),4);
				$("model.memberJoinTime").focus();
				return false;
			}
			if($("model.memberIntegral").value.length == "0") {
				alert("请填写会员积分！");
				switchTab($("SystopTab4"),4);
				$("model.memberIntegral").focus();
				return false;
			}
			if($("model.memberTel").value.length == "0") {
				alert("请填写会员电话！");
				switchTab($("SystopTab4"),4);
				$("model.memberTel").focus();
				return false;
			}
			if($("model.memberEmail").value.length == "0") {
				alert("请填写会员Email！");
				switchTab($("SystopTab4"),4);
				$("model.memberEmail").focus();
				return false;
			}
			if($("model.memberSchool").value.length == "0") {
				alert("请填写所在院校！");
				switchTab($("SystopTab4"),4);
				$("model.memberSchool").focus();
				return false;
			}
			if($("model.memberGrade").value.length == "0") {
				alert("请填写所在年级！");
				switchTab($("SystopTab4"),4);
				$("model.memberGrade").focus();
				return false;
			}
			if($("model.memberSpeciality").value.length == "0") {
				alert("请填写所学专业！");
				switchTab($("SystopTab4"),4);
				$("model.memberSpeciality").focus();
				return false;
			}
		}
	</stc:role>
		if(isStu == "0" && isMember == "0") {
			alert("“是否学生”和“是否会员”必须至少有一项为是！");
			return false;
		}
		
		return true;
	}
	
	function doSubmit() {
		if(checkForm()) {
			var contact = document.getElementById("model.contact").value;
			var contact1 = document.getElementById("model.contact1").value;
			var id = document.getElementById("model.id").value;
			if(contact.length > 0 || contact1.length > 0) {
				StuInfoManager.isContactExist(id, contact, contact1, function(result) {
					if(result == "1") {
						alert("联系方式重复");
						return;
					}
					document.getElementById("form1").submit();
				});
			} else {
				document.getElementById("form1").submit();
			}
		}
	}
	
	function changeSignStatus(obj) {
		if(obj.value == "已签约")
			$("model.signStatus").value = "1";
		else
			$("model.signStatus").value = "0";
	}
	
	function changeStuType(obj) {
		if(obj.value == "1") {
			$("model.stuType").value = "已签约";
		}
	}
	
	function showExamScore(obj) {
		if(obj.value == "1")
			$("examScoreInput").style.display = "";
		else
			$("examScoreInput").style.display = "none";
	}
	
</script>
</head>
<body onload="preFckEditor();changeTab();">
<s:form namespace="/admin/stuinfo" action="saveStuInfo" id="form1" theme="simple" method="POST" onsubmit="return checkForm();">
	<div class="x-panel">
	    <div class="x-panel-header">学生信息管理</div>
	    <div class="x-toolbar">
		    <%@include file="navigation.jsp"  %>
	    </div>
	    <table width="100%" border="0" cellpadding="5"
			cellspacing="1">
			<tr>
				<td><%@ include file="/common/messages.jsp"%></td>
			</tr>
		</table>
		<table width="100%" border="0" align="center">
		  <tr><td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="TabBarLevel" id="TabPage">
		      <tr align="center">
		      <stc:role ifAnyGranted="ROLE_ADMIN,ROLE_REC">
		        <td width="80" id="SystopTab1" valign="middle" class="SelectedTab" onclick=switchTab(this,4)>
					接待信息
				</td>
		        <td width="7" class="Black">&nbsp;</td>
		      </stc:role>
		      
		      <stc:role ifAnyGranted="ROLE_ADMIN,ROLE_ADV,ROLE_ORADMIN,ROLE_REC">
		        <td width="80" id="SystopTab2"  valign="middle" class="mousehand" onclick=switchTab(this,4)>
		        	学生信息
				</td>
		        <td width="7" class="Black">&nbsp;</td>
		      </stc:role>
		      <stc:role ifAnyGranted="ROLE_ADMIN,ROLE_FINANCE,ROLE_ADV,ROLE_ORADMIN,ROLE_REC">
		        <td width="80" id="SystopTab3" valign="middle" class="mousehand" onclick=switchTab(this,4)>
		        	报班信息
		        </td>
		        <td width="7" class="Black">&nbsp;</td>
		      </stc:role>
		      
		      <c:if test="${showType == 'member'}">
		      	<td width="80" id="SystopTab4" valign="middle" class="mousehand" onclick=switchTab(this,4)>
		        	会员信息
		        </td>
		      </c:if>
		      <c:if test="${showType != 'member'}">
		      <stc:role ifAnyGranted="ROLE_ADMIN,ROLE_FINANCE,ROLE_ADV,ROLE_ORADMIN,ROLE_REC">
		        <td width="80" id="SystopTab4" valign="middle" class="mousehand" onclick=switchTab(this,4)>
		        	会员信息
		        </td>
		        <td width="7" class="Black">&nbsp;</td>
		      </stc:role>
		      </c:if>
	        	<td width="" align="right" class="Black">&nbsp;&nbsp;</td>
		      </tr>
		    </table>
	    
		    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="Content">
		        <tr>
		          <td height="110">
					<div id='SystopTabDiv1' <stc:role ifAnyGranted="ROLE_REC,ROLE_ADMIN">style=" display:block"</stc:role> style=" display:none">
						<%@include file="recInfo.jsp" %>
					</div>
					<div id='SystopTabDiv2' <stc:role ifAnyGranted="ROLE_ADV" ifNotGranted="ROLE_REC">style=" display:block"</stc:role> style=" display:none">
						<%@include file="stuDetailsInfo.jsp" %>
					</div>
					<div id='SystopTabDiv3' style=" display:none">
						<%@include file="classInfo.jsp" %>
					</div>
					<div id='SystopTabDiv4' style=" display:none">
						<%@include file="memberInfo.jsp" %>
					</div>
				  </td>
		        </tr>
		    </table>		
		  </td></tr>
		</table>
		<table width="100%" >
            <tr>
                <td style="text-align:center;">
                	<s:hidden name="model.id" id="model.id"/>
                	<input type="hidden" name="beginAdvDate" value='<s:date name="beginAdvDate" format="yyyy-MM-dd"/>'/>
                	<input type="hidden" name="endAdvDate" value='<s:date name="endAdvDate" format="yyyy-MM-dd"/>'/>
                	<input type="hidden" name="beginSignDate" value='<s:date name="beginSignDate" format="yyyy-MM-dd"/>'/>
                	<input type="hidden" name="endSignDate" value='<s:date name="endSignDate" format="yyyy-MM-dd"/>'/>
                	<input type="hidden" name="beginVisitDate" value='<s:date name="beginVisitDate" format="yyyy-MM-dd"/>'/>
                	<input type="hidden" name="endVisitDate" value='<s:date name="endVisitDate" format="yyyy-MM-dd"/>'/>
                	<s:hidden name="currentPageNo"/>
                	<s:hidden name="modifyFlag"/>
                	<s:hidden name="definedField" id="definedField"/>
                	<s:hidden name="definedSearch" id="definedSearch"/>
                	<s:hidden name="showType" id="showType"/>
                    <%--<s:submit value="保存" cssClass="button"></s:submit>--%>
                    <input value="保存" type="button" onclick="doSubmit()" class="button"/>
                    <s:reset value="重置" cssClass="button"></s:reset>
                </td>
            </tr>
        </table>
	</div>
</s:form>
</body>
</html>