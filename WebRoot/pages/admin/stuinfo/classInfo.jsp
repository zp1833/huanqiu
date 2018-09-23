<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<table width="100%" align="center" bgcolor="#FFFFFF" cellpadding="2" cellspacing="1">
	<tr>
	  	<td align="right" width="80" bgcolor="#EBEBEB">班号：</td>
		<td bgcolor="#EBEBEB" width="120">
			<input type="text" name="model.className" size="12" id="model.className" value='${model.className }'/>
	  	</td>
		<td align="right" bgcolor="#EBEBEB" width="80">课程名称：</td>
		<td bgcolor="#EBEBEB" width="120">
			<input type="text" name="model.courseName" size="12" id="model.courseName" value='${model.courseName }'/>
	  	</td>
	  	<td align="right" bgcolor="#EBEBEB" width="80">上课时间：</td>
		<td bgcolor="#EBEBEB">
			<input type="text" name="model.studyTime" size="12" id="model.studyTime" 
							class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
								value='<s:date name="model.studyTime" format="yyyy-MM-dd"/>'/>
	  	</td>
    </tr>
    <tr>
	  	<td align="right" bgcolor="#EBEBEB" width="80">学费：</td>
		<td bgcolor="#EBEBEB" width="120">
			<input size="12" maxlength="50" name="model.tuition" id="model.tuition" value="${model.tuition }">
	  	</td>
	  	<td align="right" bgcolor="#EBEBEB" width="80">实收金额：</td>
		<td bgcolor="#EBEBEB" width="120">
			<input size="12" maxlength="50" name="model.realMoney" id="model.realMoney" value="${model.realMoney }">
	  	</td>
	  	<td align="right" bgcolor="#EBEBEB" width="80">付款方式：</td>
		<td bgcolor="#EBEBEB">
			<select name="model.payMethod" id="model.payMethod">
				<option value="">-请选择-</option>
				<option value="0" ${model.payMethod=='0'?"selected":"" }>现金</option>
				<option value="1" ${model.payMethod=='1'?"selected":"" }>刷卡/转账</option>
			</select>
	  	</td>
    </tr>
	<tr>
	  	<td align="right" bgcolor="#EBEBEB" width="80">报名校区：</td>
		<td bgcolor="#EBEBEB" width="120">
			<input size="12" maxlength="50" name="model.reportSchool" id="model.reportSchool" value="${model.reportSchool }">
	  	</td>
	  	<td align="right" bgcolor="#EBEBEB" width="80">经办人：</td>
		<td bgcolor="#EBEBEB" width="120">
			<input size="12" maxlength="50" name="model.dealPerson" id="model.dealPerson" value="${model.dealPerson }">
	  	</td>
	  	<td align="right" bgcolor="#EBEBEB" width="80">缴费日期：</td>
		<td bgcolor="#EBEBEB">
			<input type="text" name="model.dealDate" size="12" id="model.dealDate" 
							class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
								value='<s:date name="model.dealDate" format="yyyy-MM-dd"/>'/>
	  	</td>
    </tr>
    <tr>
	  	<td align="right" bgcolor="#EBEBEB" width="80">实收金额1：</td>
		<td bgcolor="#EBEBEB" width="120">
			<input size="12" maxlength="50" name="model.realMoney1" id="model.realMoney1" value="${model.realMoney1 }">
	  	</td>
	  	<td align="right" bgcolor="#EBEBEB" width="80">实收金额2：</td>
		<td bgcolor="#EBEBEB" width="120">
			<input size="12" maxlength="50" name="model.realMoney2" id="model.realMoney2" value="${model.realMoney2 }">
	  	</td>
	  	<td align="right" bgcolor="#EBEBEB" width="80">实收金额3：</td>
		<td bgcolor="#EBEBEB">
			<input size="12" maxlength="50" name="model.realMoney3" id="model.realMoney3" value="${model.realMoney3 }">
	  	</td>
    </tr>
    <tr>
	  	<td align="right" bgcolor="#EBEBEB" width="80">实收金额4：</td>
		<td bgcolor="#EBEBEB" width="120">
			<input size="12" maxlength="50" name="model.realMoney4" id="model.realMoney4" value="${model.realMoney4 }">
	  	</td>
	  	<td align="right" bgcolor="#EBEBEB" width="80">实收金额5：</td>
		<td bgcolor="#EBEBEB" width="120">
			<input size="12" maxlength="50" name="model.realMoney5" id="model.realMoney5" value="${model.realMoney5 }">
	  	</td>
	  	<td align="right" bgcolor="#EBEBEB" width="80"></td>
		<td bgcolor="#EBEBEB">
	  	</td>
    </tr>
</table>
