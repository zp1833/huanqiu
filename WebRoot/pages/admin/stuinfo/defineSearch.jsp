<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>自定义查询</title>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/extSkin.jsp" %>
<script type="text/javascript" src="${ctx}/calendar/WdatePicker.js"></script>
<script type="text/javascript">
	function selectSearch() {
		
		var searchName = document.getElementById("searchName").value;
		var searchValue = document.getElementById("searchValue").value;
		
		if(searchName != "" && searchValue != "") {
			window.returnValue = searchName + "|$|" + searchValue;
			alert("选择成功！");
		} else {
			window.returnValue = "";
		}
		window.close();
	}
	
	function init() {
		var value = window.dialogArguments;
		if(value != "") {
			var params = value.split("|$|");
			document.getElementById("searchName").value = params[0];
			document.getElementById("searchValue").value = params[1];
		}
	}
	
	function changeSearchInput(obj) {
		if(obj.value == 'recentTraceTime' || obj.value == 'memberLastIntegralTime' || 
			obj.value == 'memberLastActiveTime' || obj.value == 'memberLastChangeTime' || obj.value == 'memberLastSendTime') {
			document.getElementById("searchInput").innerHTML = '<input type="text" value="" class="Wdate" id="searchValue" name="searchValue">';
			document.getElementById("searchValue").onfocus = function() {
				WdatePicker({dateFmt:'yyyy-MM-dd'});
			};
		} else if(obj.value == 'isMember') {
			document.getElementById("searchInput").innerHTML = '<select name="searchValue" id="searchValue">'+
							'<option value="1">是</option><option value="0">否</option></select>';
		} else {
			document.getElementById("searchInput").innerHTML = '<input type="text" value="" id="searchValue" name="searchValue">';
		}
	}
	
</script>
</head>
<body onload="init();">
<form action="" method="POST">
	<div class="x-panel">
	    <div class="x-panel-header">自定义查询</div>
		<table width="100%" border="0" align="center">
		  <tr><td>
		    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="Content">
		        <tr>
		          <td>
					<div>
						<table align="center" bgcolor="#FFFFFF" cellpadding="2" cellspacing="1" width="100%">
							<tr>
							  	<td align="right" bgcolor="#EBEBEB" width="130">查询条件：</td>
								<td bgcolor="#EBEBEB">
									<select name="searchName" id="searchName" onchange="changeSearchInput(this);">
										<option value="">--请选择--</option>
										<option value="source">信息来源</option>
										<option value="sourceFrom">来源明细</option>
										<option value="contact">联系方式</option>
										<option value="advType">咨询形式</option>
										<option value="currentGrade">在读年级</option>
										<option value="hopeDegree">目标学历</option>
										<option value="introToAgency">介绍给中介</option>
										<option value="recentTraceTime">最新跟踪时间</option>
										<option value="isMember">是否会员</option>
										<option value="memberIntegral">会员积分</option>
										<option value="memberLastIntegralTime">最新积分时间</option>
										<option value="memberLastActiveTime">最新活动时间</option>
										<option value="memberLastChangeTime">最新兑换时间</option>
										<option value="memberLastSendTime">最新资料发送</option>
										
										<option value="recEmp">接待员工</option>
										<option value="finishSchool">毕业院校</option>
										<option value="currentSchool">在读院校</option>
										<option value="country">留学国家</option>
										<option value="city">城市</option>
										<option value="qq">微信/QQ</option>
									</select>
							  	</td>
						    </tr>
						    <tr>
							  	<td align="right" bgcolor="#EBEBEB" width="130">查询内容：</td>
								<td bgcolor="#EBEBEB" id="searchInput">
									<input type="text" value="" id="searchValue" name="searchValue">
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
					<input type="button" onclick="selectSearch();" value="确定" class="button">&nbsp;&nbsp;             	
                    <input type="reset" value="重置" class="button">
                </td>
            </tr>
        </table>
	</div>
</form>
</body>
</html>