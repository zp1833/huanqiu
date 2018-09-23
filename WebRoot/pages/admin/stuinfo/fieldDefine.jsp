<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑学员信息</title>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/extSkin.jsp" %>
<script type="text/javascript">
	function selectFiled() {
		var checks = document.getElementsByTagName("input");
		var value = "";
		for(var i=0; i<checks.length; i++) {
			if(checks[i].type == "checkbox" && checks[i].checked) {
				value += checks[i].value+",";
			}
		}
		window.returnValue = value;
		alert("选择成功！");
		window.close();
	}
	
	function init() {
		var value = window.dialogArguments;
		if(value != "") {
			var params = value.split(",");
			for(var i=0; i<params.length-1; i++) {
				document.getElementById(params[i]).checked = "checked";
			}
		}
	}
	
</script>
</head>
<body onload="init();" onunload="selectFiled();">
<form action="" method="POST">
	<div class="x-panel">
	    <div class="x-panel-header">自定义显示列</div>
		<table width="100%" border="0" align="center">
		  <tr><td>
		    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="Content">
		        <tr>
		          <td height="110">
					<div>
						<table align="left" bgcolor="#FFFFFF" cellpadding="2" cellspacing="1">
							<tr>
							  	<td align="right" bgcolor="#EBEBEB" width="120">学生姓名</td>
								<td bgcolor="#EBEBEB" width="20">
									<input type="checkbox" value="stuName_s" id="stuName_s">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="100">咨询日期</td>
								<td bgcolor="#EBEBEB" width="20">
									<input type="checkbox" value="advDate_s" id="advDate_s">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="130">咨询课程</td>
								<td bgcolor="#EBEBEB" width="20">
									<input type="checkbox" value="advCourse_s" id="advCourse_s">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="130">信息来源</td>
								<td bgcolor="#EBEBEB">
									<input type="checkbox" value="source_s" id="source_s">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="130">来源明细</td>
								<td bgcolor="#EBEBEB">
									<input type="checkbox" value="sourceFrom_s" id="sourceFrom_s">
							  	</td>
						    </tr>
						    <tr>
							  	<td align="right" bgcolor="#EBEBEB" width="120">咨询形式</td>
								<td bgcolor="#EBEBEB" width="20">
									<input type="checkbox" value="advType_s" id="advType_s">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="100">接待员工</td>
								<td bgcolor="#EBEBEB" width="20">
									<input type="checkbox" value="recEmp_s" id="recEmp_s">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="100">负责顾问</td>
								<td bgcolor="#EBEBEB" width="20">
									<input type="checkbox" value="advisor_s" id="advisor_s">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="130">当前进度</td>
								<td bgcolor="#EBEBEB" width="20">
									<input type="checkbox" value="currentPlan_s" id="currentPlan_s">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="130">学生类型</td>
								<td bgcolor="#EBEBEB">
									<input type="checkbox" value="stuType_s" id="stuType_s">
							  	</td>
						    </tr>
						    <tr>
							  	<td align="right" bgcolor="#EBEBEB" width="120">状态定义日期</td>
								<td bgcolor="#EBEBEB" width="20">
									<input type="checkbox" value="statusDefineTime_s" id="statusDefineTime_s">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="100">联系方式</td>
								<td bgcolor="#EBEBEB" width="20">
									<input type="checkbox" value="contact_s" id="contact_s">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="100">性别</td>
								<td bgcolor="#EBEBEB" width="20">
									<input type="checkbox" value="sex_s" id="sex_s">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="130">出生日期</td>
								<td bgcolor="#EBEBEB" width="20">
									<input type="checkbox" value="birthday_s" id="birthday_s">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="130">婚否</td>
								<td bgcolor="#EBEBEB">
									<input type="checkbox" value="isMerried_s" id="isMerried_s">
							  	</td>
						    </tr>
						    <tr>
							  	<td align="right" bgcolor="#EBEBEB" width="120">所学专业</td>
								<td bgcolor="#EBEBEB" width="20">
									<input type="checkbox" value="speciality_s" id="speciality_s">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="100">毕业院校</td>
								<td bgcolor="#EBEBEB" width="20">
									<input type="checkbox" value="finishSchool_s" id="finishSchool_s">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="100">毕业时间</td>
								<td bgcolor="#EBEBEB" width="20">
									<input type="checkbox" value="finishTime_s" id="finishTime_s">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="130">在读院校</td>
								<td bgcolor="#EBEBEB" width="20">
									<input type="checkbox" value="currentSchool_s" id="currentSchool_s">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="130">在读年级</td>
								<td bgcolor="#EBEBEB">
									<input type="checkbox" value="currentGrade_s" id="currentGrade_s">
							  	</td>
						    </tr>
						    <tr>
							  	<td align="right" bgcolor="#EBEBEB" width="100">目标学历</td>
								<td bgcolor="#EBEBEB" width="20">
									<input type="checkbox" value="hopeDegree_s" id="hopeDegree_s">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="100">外语程度</td>
								<td bgcolor="#EBEBEB" width="20">
									<input type="checkbox" value="enDegree_s" id="enDegree_s">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="130">信息备注</td>
								<td bgcolor="#EBEBEB">
									<input type="checkbox" value="infoDesc_s" id="infoDesc_s">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="130">客户跟踪记录</td>
								<td bgcolor="#EBEBEB">
									<input type="checkbox" value="traceDesc_s" id="traceDesc_s">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="130">下次回访时间</td>
								<td bgcolor="#EBEBEB">
									<input type="checkbox" value="nextVisitTime_s" id="nextVisitTime_s">
							  	</td>
						    </tr>
						    <tr>
							  	<td align="right" bgcolor="#EBEBEB" width="120">最新跟踪时间</td>
								<td bgcolor="#EBEBEB" width="20">
									<input type="checkbox" value="recentTraceTime_s" id="recentTraceTime_s">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="100">介绍给中介</td>
								<td bgcolor="#EBEBEB" width="20">
									<input type="checkbox" value="introToAgency_s" id="introToAgency_s">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="100">签约情况</td>
								<td bgcolor="#EBEBEB" width="20">
									<input type="checkbox" value="signStatus_s" id="signStatus_s">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="130">签约日期</td>
								<td bgcolor="#EBEBEB" width="20">
									<input type="checkbox" value="dealDate_s" id="dealDate_s">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="130">学校名称</td>
								<td bgcolor="#EBEBEB" width="20">
									<input type="checkbox" value="schoolName_s" id="schoolName_s">
							  	</td>
						    </tr>
						    <tr>
						    	<td align="right" bgcolor="#EBEBEB" width="130">文化程度</td>
								<td bgcolor="#EBEBEB">
									<input type="checkbox" value="education_s" id="education_s">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="130">高考英语成绩</td>
								<td bgcolor="#EBEBEB">
									<input type="checkbox" value="collegeScore_s" id="collegeScore_s">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="130">班　　号</td>
								<td bgcolor="#EBEBEB">
									<input type="checkbox" value="className_s" id="className_s">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="130">课程名称</td>
								<td bgcolor="#EBEBEB">
									<input type="checkbox" value="courseName_s" id="courseName_s">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="130">学　　费</td>
								<td bgcolor="#EBEBEB">
									<input type="checkbox" value="tuition_s" id="tuition_s">
							  	</td>
						    </tr>
						    <tr>
						    	<td align="right" bgcolor="#EBEBEB" width="130">实收金额</td>
								<td bgcolor="#EBEBEB">
									<input type="checkbox" value="realMoney_s" id="realMoney_s">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="130">报名校区</td>
								<td bgcolor="#EBEBEB">
									<input type="checkbox" value="reportSchool_s" id="reportSchool_s">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="130">经办人</td>
								<td bgcolor="#EBEBEB">
									<input type="checkbox" value="dealPerson_s" id="dealPerson_s">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="130">是否会员</td>
								<td bgcolor="#EBEBEB">
									<input type="checkbox" value="isMember_s" id="isMember_s">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="130">入会时间</td>
								<td bgcolor="#EBEBEB">
									<input type="checkbox" value="memberJoinTime_s" id="memberJoinTime_s">
							  	</td>
						    </tr>
						    <tr>
						    	<td align="right" bgcolor="#EBEBEB" width="130">会员积分</td>
								<td bgcolor="#EBEBEB">
									<input type="checkbox" value="memberIntegral_s" id="memberIntegral_s">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="130">最新积分时间</td>
								<td bgcolor="#EBEBEB">
									<input type="checkbox" value="memberLastIntegralTime_s" id="memberLastIntegralTime_s">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="130">最新活动时间</td>
								<td bgcolor="#EBEBEB">
									<input type="checkbox" value="memberLastActiveTime_s" id="memberLastActiveTime_s">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="130">最新兑换时间</td>
								<td bgcolor="#EBEBEB">
									<input type="checkbox" value="memberLastChangeTime_s" id="memberLastChangeTime_s">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="130">最新资料发送</td>
								<td bgcolor="#EBEBEB">
									<input type="checkbox" value="memberLastSendTime_s" id="memberLastSendTime_s">
							  	</td>
						    </tr>
						    <tr>
						    	<td align="right" bgcolor="#EBEBEB" width="130">留学国家</td>
								<td bgcolor="#EBEBEB">
									<input type="checkbox" value="country_s" id="country_s">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="130"></td>
								<td bgcolor="#EBEBEB">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="130"></td>
								<td bgcolor="#EBEBEB">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="130"></td>
								<td bgcolor="#EBEBEB">
							  	</td>
							  	<td align="right" bgcolor="#EBEBEB" width="130"></td>
								<td bgcolor="#EBEBEB">
							  	</td>
						    </tr>
						</table>
						
					</div>
				  </td>
		        </tr>
		    </table>		
		  </td></tr>
		</table>
		<table width="100%" >
            <tr>
                <td style="text-align:center;">
					<input type="button" onclick="window.close();" value="确定" class="button">&nbsp;&nbsp;             	
                    <input type="reset" value="重置" class="button">
                </td>
            </tr>
        </table>
	</div>
</form>
</body>
</html>