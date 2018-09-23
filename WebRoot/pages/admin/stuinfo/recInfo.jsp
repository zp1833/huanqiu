<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
<!--
	function inputCountry(obj) {
		if(obj.value == '0') {
			document.getElementById("otherCountry").style.display = "";
		} else {
			document.getElementById("otherCountry").style.display = "none";
		}
	}
	
	function changeJoinTime(obj) {
		$("model.memberJoinTime").value = obj.value;
	}
	
	function changeInfoType(obj) {
		if(obj.value == "俱乐部") {
			$("model.isMember").value = "1";
			$("model.isStu").value = "0";
		} else {
			$("model.isMember").value = "0";
			$("model.isStu").value = "1";
		}
	}
//-->
</script>
<table width="100%" align="center" bgcolor="#FFFFFF" cellpadding="2" cellspacing="1">
	<tr>
	  	<td align="right" width="100" bgcolor="#EBEBEB">咨询日期：</td>
		<td bgcolor="#EBEBEB" width="200">
			<input type="text" name="model.advDate" style="width:90px" id="model.advDate" 
						class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
							value='<s:date name="model.advDate" format="yyyy-MM-dd"/>' onchange="changeJoinTime(this);"/>
	  	</td>
		<td align="right" bgcolor="#EBEBEB" width="100">学生姓名：</td>
		<td bgcolor="#EBEBEB">
			<stc:role ifNotGranted="ROLE_ADV">
				<input maxlength="50" style="width:90px" name="model.stuName" id="model.stuName" value="${model.stuName }">
			</stc:role>
			
			<stc:role ifAllGranted="ROLE_ADV,ROLE_REC">
				<input maxlength="50" style="width:90px" name="model.stuName" id="model.stuName" value="${model.stuName }">
			</stc:role>
	  	</td>
    </tr>
    <tr>
	  	<td align="right" bgcolor="#EBEBEB">负责顾问：</td>
		<td bgcolor="#EBEBEB">
			<stc:role ifNotGranted="ROLE_ADV">
				<select name="model.advisor" id="model.advisor">
					<option value="">--请选择--</option>
					<c:forEach items="${advEmps}" var="emp">
						<option ${model.advisor==emp.name?"selected":"" } value="${emp.name }">${emp.name }</option>
					</c:forEach>
				</select>
			</stc:role>
			<stc:role ifAllGranted="ROLE_ADV,ROLE_REC">
				<select name="model.advisor" id="model.advisor">
					<option value="">--请选择--</option>
					<c:forEach items="${advEmps}" var="emp">
						<option ${model.advisor==emp.name?"selected":"" } value="${emp.name }">${emp.name }</option>
					</c:forEach>
				</select>
			</stc:role>
	  	</td>
		<td align="right" bgcolor="#EBEBEB">咨询课程：</td>
		<td bgcolor="#EBEBEB">
			<select name="model.advCourse" id="model.advCourse" style="width:88px" onchange="changeInfoType(this)">
				<option value="">--请选择--</option>
				<option ${model.advCourse=='日语'?"selected":"" } value="日语">日语</option>
				<option ${model.advCourse=='韩语'?"selected":"" } value="韩语">韩语</option>
				<option ${model.advCourse=='法语'?"selected":"" } value="法语">法语</option>
				<option ${model.advCourse=='剑桥'?"selected":"" } value="剑桥">剑桥</option>
				<option ${model.advCourse=='雅思'?"selected":"" } value="雅思">雅思</option>
				<option ${model.advCourse=='托福'?"selected":"" } value="托福">托福</option>
				<option ${model.advCourse=='剑桥'?"selected":"" } value="剑桥">剑桥</option>
				<option ${model.advCourse=='SAT'?"selected":"" } value="SAT">SAT</option>
				<option ${model.advCourse=='VIP'?"selected":"" } value="VIP">VIP</option>
				<option ${model.advCourse=='外教口语'?"selected":"" } value="外教口语">外教口语</option>
				<option ${model.advCourse=='新概念'?"selected":"" } value="新概念">新概念</option>
				<option ${model.advCourse=='小语种'?"selected":"" } value="小语种">小语种</option>
				<option ${model.advCourse=='俱乐部'?"selected":"" } value="俱乐部">俱乐部</option>
				<option ${model.advCourse=='分支课程'?"selected":"" } value="分支课程">分支课程</option>
				<option ${model.advCourse=='商务旅游英语'?"selected":"" } value="商务旅游英语">商务旅游英语</option>
				<option ${model.advCourse=='夏令营'?"selected":"" } value="夏令营">夏令营</option>
				<option ${model.advCourse=='预备一段'?"selected":"" } value="预备一段">预备一段</option>
				<option ${model.advCourse=='预备二段'?"selected":"" } value="预备二段">预备二段</option>
				<option ${model.advCourse=='预备三段'?"selected":"" } value="预备三段">预备三段</option>
				<option ${model.advCourse=='海外生存英语'?"selected":"" } value="海外生存英语">海外生存英语</option>
				<option ${model.advCourse=='词汇'?"selected":"" } value="词汇">词汇</option>
				<option ${model.advCourse=='语法'?"selected":"" } value="语法">语法</option>
				<option ${model.advCourse=='GRE'?"selected":"" } value="GRE">GRE</option>
				<option ${model.advCourse=='GMAT'?"selected":"" } value="GMAT">GMAT</option>
				<option ${model.advCourse=='ACT'?"selected":"" } value="ACT">ACT</option>
				<option ${model.advCourse=='AEAS'?"selected":"" } value="AEAS">AEAS</option>
				<option ${model.advCourse=='PTE'?"selected":"" } value="PTE">PTE</option>
				<option ${model.advCourse=='其它'?"selected":"" } value="其它">其它</option>
			</select>
	  	</td>
    </tr>
	<tr>
	  	<td align="right" bgcolor="#EBEBEB">信息来源：</td>
		<td bgcolor="#EBEBEB">
			<select name="model.source" id="model.source">
				<option value="">--请选择--</option>
				<option ${model.source=='网络搜索'?"selected":"" } value="网络搜索">网络搜索</option>
				<option ${model.source=='网代介绍'?"selected":"" } value="网代介绍">网代介绍</option>
				<option ${model.source=='朋友介绍'?"selected":"" } value="朋友介绍">朋友介绍</option>
				<option ${model.source=='内部转介'?"selected":"" } value="内部转介">内部转介</option>
				<option ${model.source=='报纸广告'?"selected":"" } value="报纸广告">报纸广告</option>
				<option ${model.source=='展会活动'?"selected":"" } value="展会活动">展会活动</option>
				<option ${model.source=='地推传单'?"selected":"" } value="地推传单">地推传单</option>
				<option ${model.source=='在线客服'?"selected":"" } value="在线客服">在线客服</option>
				<option ${model.source=='渠道合作'?"selected":"" } value="渠道合作">渠道合作</option>
				<option ${model.source=='室外广告'?"selected":"" } value="室外广告">室外广告</option>
				<option ${model.source=='校园宣传'?"selected":"" } value="校园宣传">校园宣传</option>
				<option ${model.source=='项目渠道'?"selected":"" } value="项目渠道">项目渠道</option>
				<option ${model.source=='雅思考点'?"selected":"" } value="雅思考点">雅思考点</option>
				<option ${model.source=='托福考点'?"selected":"" } value="托福考点">托福考点</option>
				<option ${model.source=='线上推广'?"selected":"" } value="线上推广">线上推广</option>
				<option ${model.source=='其他'?"selected":"" } value="其他">其他</option>
			</select>
	  	</td>
		<td align="right" bgcolor="#EBEBEB">来源明细：</td>
		<td bgcolor="#EBEBEB">
			<input name="model.sourceFrom" id="model.sourceFrom" value="${model.sourceFrom }" style="width:90px" maxlength="50">
	  	</td>
    </tr>
    <tr>
	  	<td align="right" bgcolor="#EBEBEB">咨询形式：</td>
		<td bgcolor="#EBEBEB">
			<select name="model.advType" id="model.advType">
				<option value="">--请选择--</option>
				<option ${model.advType=='来电'?"selected":"" } value="来电">来电</option>
				<option ${model.advType=='来访'?"selected":"" } value="来访">来访</option>
				<option ${model.advType=='网络'?"selected":"" } value="网络">网络</option>
				<option ${model.advType=='老学员介绍'?"selected":"" } value="老学员介绍">老学员介绍</option>
				<option ${model.advType=='其它'?"selected":"" } value="其它">其它</option>
			</select>
	  	</td>
		<td align="right" bgcolor="#EBEBEB">接待员工：</td>
		<td bgcolor="#EBEBEB">
			<select name="model.recEmp" id="model.recEmp" style="width:88px">
				<option value="">--请选择--</option>
				<c:forEach items="${allEmps}" var="emp">
					<option ${model.recEmp==emp.name?"selected":"" } value="${emp.name }">${emp.name }</option>
				</c:forEach>
			</select>
	  	</td>
    </tr>
    <stc:role ifAnyGranted="ROLE_REC">
    <tr>
	  	<td align="right" bgcolor="#EBEBEB">下次回访时间：</td>
		<td bgcolor="#EBEBEB" colspan="3">
			<input type="text" name="model.nextVisitTime" size="12" id="model.nextVisitTime" 
						class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d'})"
							value='<s:date name="model.nextVisitTime" format="yyyy-MM-dd"/>'/>
	  	</td>
    </tr>
    </stc:role>
</table>
