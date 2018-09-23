<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<fieldset style="margin:8px;">
    <legend>the first party</legend>
	<table width="100%" align="center" bgcolor="#FFFFFF" cellpadding="2" cellspacing="1">
		<tr>
		  	<td align="right" width="80" bgcolor="#EBEBEB">是否学生：</td>
			<td bgcolor="#EBEBEB" width="120">
				<select name="model.isStu" id="model.isStu">
					<option ${model.isStu=="0"?"selected":"" } value="0">否</option>
					<option ${model.isStu=="1"?"selected":"" } value="1">是</option>
				</select>
		  	</td>
			<td align="right" bgcolor="#EBEBEB" width="80">入会时间：</td>
			<td bgcolor="#EBEBEB" width="120">
				<input type="text" name="model.memberJoinTime" size="12" id="model.memberJoinTime" 
								class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
									value='<s:date name="model.memberJoinTime" format="yyyy-MM-dd"/>'/>
		  	</td>
		  	<td align="right" bgcolor="#EBEBEB" width="80">会员积分：</td>
			<td bgcolor="#EBEBEB">
				<input size="12" maxlength="50" name="model.memberIntegral" id="model.memberIntegral" value="${model.memberIntegral }">
		  	</td>
	    </tr>
	    <tr>
		  	<td align="right" bgcolor="#EBEBEB" width="80">会员电话：</td>
			<td bgcolor="#EBEBEB" width="120">
				<input size="12" maxlength="50" name="model.memberTel" id="model.memberTel" value="${model.memberTel }">
		  	</td>
		  	<td align="right" bgcolor="#EBEBEB" width="80">会员QQ：</td>
			<td bgcolor="#EBEBEB" width="120">
				<input size="12" maxlength="50" name="model.memberQq" id="model.memberQq" value="${model.memberQq }">
		  	</td>
		  	<td align="right" bgcolor="#EBEBEB" width="80">会员Email：</td>
			<td bgcolor="#EBEBEB">
				<input size="12" maxlength="50" name="model.memberEmail" id="model.memberEmail" value="${model.memberEmail }">
		  	</td>
	    </tr>
		<tr>
		  	<td align="right" bgcolor="#EBEBEB" width="80">所在院校：</td>
			<td bgcolor="#EBEBEB" width="120">
				<input size="12" maxlength="50" name="model.memberSchool" id="model.memberSchool" value="${model.memberSchool }">
		  	</td>
		  	<td align="right" bgcolor="#EBEBEB" width="80">所在年级：</td>
			<td bgcolor="#EBEBEB" width="120">
				<input size="12" maxlength="50" name="model.memberGrade" id="model.memberGrade" value="${model.memberGrade }">
		  	</td>
		  	<td align="right" bgcolor="#EBEBEB" width="80">所学专业：</td>
			<td bgcolor="#EBEBEB">
				<input size="12" maxlength="50" name="model.memberSpeciality" id="model.memberSpeciality" value="${model.memberSpeciality }">
		  	</td>
	    </tr>
	</table>
</fieldset>
<fieldset style="margin:8px;">
    <legend>the second party</legend>
	<table width="100%" align="center" bgcolor="#FFFFFF" cellpadding="2" cellspacing="1">
		<tr>
		  	<td align="right" width="80" bgcolor="#EBEBEB">爱好特长：</td>
			<td bgcolor="#EBEBEB" width="420">
				<textarea style="width:400px; height: 40px" name="model.memberLike">${model.memberLike }</textarea>
		  	</td>
		  	<td align="right" width="100" bgcolor="#EBEBEB">希望的活动：</td>
			<td bgcolor="#EBEBEB">
				<textarea style="width:400px; height: 40px" name="model.memberHope">${model.memberHope }</textarea>
		  	</td>
	    </tr>
	    <tr>
		  	<td align="right" width="80" bgcolor="#EBEBEB">申请原因：</td>
			<td bgcolor="#EBEBEB" width="420">
				<textarea style="width:400px; height: 40px" name="model.memberReason">${model.memberReason }</textarea>
		  	</td>
		  	<td align="right" width="100" bgcolor="#EBEBEB">热心建议：</td>
			<td bgcolor="#EBEBEB">
				<textarea style="width:400px; height: 40px" name="model.memberAdvisory">${model.memberAdvisory }</textarea>
		  	</td>
	    </tr>
	    <tr>
		  	<td align="right" width="80" bgcolor="#EBEBEB">备注：</td>
			<td bgcolor="#EBEBEB" colspan="3">
				<textarea style="width:400px; height: 40px" name="model.memberDescn">${model.memberDescn }</textarea>
		  	</td>
	    </tr>
	</table>
</fieldset>
<fieldset style="margin:8px;">
    <legend>the third party</legend>
	<table width="100%" align="center" bgcolor="#FFFFFF" cellpadding="2" cellspacing="1">
		<tr>
		  	<td align="right" width="100" bgcolor="#EBEBEB">最新积分时间：</td>
			<td bgcolor="#EBEBEB" width="120">
				<input type="text" name="model.memberLastIntegralTime" size="12" id="model.memberLastIntegralTime" 
								class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
									value='<s:date name="model.memberLastIntegralTime" format="yyyy-MM-dd"/>'/>
		  	</td>
		  	<td align="right" width="80" bgcolor="#EBEBEB">积分历史：</td>
			<td bgcolor="#EBEBEB">
				<textarea style="width:500px; height: 40px" name="model.memberIntegralHistory">${model.memberIntegralHistory }</textarea>
		  	</td>
	    </tr>
	    <tr>
		  	<td align="right" width="100" bgcolor="#EBEBEB">最新活动时间：</td>
			<td bgcolor="#EBEBEB" width="120">
				<input type="text" name="model.memberLastActiveTime" size="12" id="model.memberLastActiveTime" 
								class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
									value='<s:date name="model.memberLastActiveTime" format="yyyy-MM-dd"/>'/>
		  	</td>
		  	<td align="right" width="80" bgcolor="#EBEBEB">活动历史：</td>
			<td bgcolor="#EBEBEB">
				<textarea style="width:500px; height: 40px" name="model.memberActiveHistory">${model.memberActiveHistory }</textarea>
		  	</td>
	    </tr>
	    <tr>
		  	<td align="right" width="100" bgcolor="#EBEBEB">最新兑换时间：</td>
			<td bgcolor="#EBEBEB" width="120">
				<input type="text" name="model.memberLastChangeTime" size="12" id="model.memberLastChangeTime" 
								class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
									value='<s:date name="model.memberLastChangeTime" format="yyyy-MM-dd"/>'/>
		  	</td>
		  	<td align="right" width="80" bgcolor="#EBEBEB">兑换历史：</td>
			<td bgcolor="#EBEBEB">
				<textarea style="width:500px; height: 40px" name="model.memberChangeHistory">${model.memberChangeHistory}</textarea>
		  	</td>
	    </tr>
	    <tr>
		  	<td align="right" width="100" bgcolor="#EBEBEB">最新资料发送：</td>
			<td bgcolor="#EBEBEB" width="120">
				<input type="text" name="model.memberLastSendTime" size="12" id="model.memberLastSendTime" 
								class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
									value='<s:date name="model.memberLastSendTime" format="yyyy-MM-dd"/>'/>
		  	</td>
		  	<td align="right" width="80" bgcolor="#EBEBEB">发送历史：</td>
			<td bgcolor="#EBEBEB">
				<textarea style="width:500px; height: 40px" name="model.memberSendHistory">${model.memberSendHistory }</textarea>
		  	</td>
	    </tr>
	</table>
</fieldset>