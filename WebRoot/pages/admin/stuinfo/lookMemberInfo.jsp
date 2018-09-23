<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<fieldset style="margin:8px;">
    <legend>the first party</legend>
	<table width="100%" align="center" bgcolor="#FFFFFF" cellpadding="2" cellspacing="1">
		<tr>
		  	<td align="right" width="80" bgcolor="#EBEBEB">是否学生：</td>
			<td bgcolor="#EBEBEB" width="120">
				<c:if test="${model.isStu=='0' }">
					否
				</c:if>
				<c:if test="${model.isStu=='1' }">
					是
				</c:if>
		  	</td>
			<td align="right" bgcolor="#EBEBEB" width="80">入会时间：</td>
			<td bgcolor="#EBEBEB" width="120">
				<s:date name="model.memberJoinTime" format="yyyy-MM-dd"/>
		  	</td>
		  	<td align="right" bgcolor="#EBEBEB" width="80">会员积分：</td>
			<td bgcolor="#EBEBEB">
				${model.memberIntegral }
		  	</td>
	    </tr>
	    <tr>
		  	<td align="right" bgcolor="#EBEBEB" width="80">会员电话：</td>
			<td bgcolor="#EBEBEB" width="120">
				${model.memberTel }
		  	</td>
		  	<td align="right" bgcolor="#EBEBEB" width="80">会员QQ：</td>
			<td bgcolor="#EBEBEB" width="120">
				${model.memberQq }
		  	</td>
		  	<td align="right" bgcolor="#EBEBEB" width="80">会员Email：</td>
			<td bgcolor="#EBEBEB">
				${model.memberEmail }
		  	</td>
	    </tr>
		<tr>
		  	<td align="right" bgcolor="#EBEBEB" width="80">所在院校：</td>
			<td bgcolor="#EBEBEB" width="120">
				${model.memberSchool }
		  	</td>
		  	<td align="right" bgcolor="#EBEBEB" width="80">所在年级：</td>
			<td bgcolor="#EBEBEB" width="120">
				${model.memberGrade }
		  	</td>
		  	<td align="right" bgcolor="#EBEBEB" width="80">所学专业：</td>
			<td bgcolor="#EBEBEB">
				${model.memberSpeciality }
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
				${model.memberLike }
		  	</td>
		  	<td align="right" width="100" bgcolor="#EBEBEB">希望的活动：</td>
			<td bgcolor="#EBEBEB">
				${model.memberHope }
		  	</td>
	    </tr>
	    <tr>
		  	<td align="right" width="80" bgcolor="#EBEBEB">申请原因：</td>
			<td bgcolor="#EBEBEB" width="420">
				${model.memberReason }
		  	</td>
		  	<td align="right" width="100" bgcolor="#EBEBEB">热心建议：</td>
			<td bgcolor="#EBEBEB">
				${model.memberAdvisory }
		  	</td>
	    </tr>
	    <tr>
		  	<td align="right" width="80" bgcolor="#EBEBEB">备注：</td>
			<td bgcolor="#EBEBEB" colspan="3">
				${model.memberDescn }
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
				<s:date name="model.memberLastIntegralTime" format="yyyy-MM-dd"/>
		  	</td>
		  	<td align="right" width="80" bgcolor="#EBEBEB">积分历史：</td>
			<td bgcolor="#EBEBEB">
				${model.memberIntegralHistory }
		  	</td>
	    </tr>
	    <tr>
		  	<td align="right" width="100" bgcolor="#EBEBEB">最新活动时间：</td>
			<td bgcolor="#EBEBEB" width="120">
				<s:date name="model.memberLastActiveTime" format="yyyy-MM-dd"/>
		  	</td>
		  	<td align="right" width="80" bgcolor="#EBEBEB">活动历史：</td>
			<td bgcolor="#EBEBEB">
				${model.memberActiveHistory }
		  	</td>
	    </tr>
	    <tr>
		  	<td align="right" width="100" bgcolor="#EBEBEB">最新兑换时间：</td>
			<td bgcolor="#EBEBEB" width="120">
				<s:date name="model.memberLastChangeTime" format="yyyy-MM-dd"/>
		  	</td>
		  	<td align="right" width="80" bgcolor="#EBEBEB">兑换历史：</td>
			<td bgcolor="#EBEBEB">
				${model.memberChangeHistory}
		  	</td>
	    </tr>
	    <tr>
		  	<td align="right" width="100" bgcolor="#EBEBEB">最新资料发送：</td>
			<td bgcolor="#EBEBEB" width="120">
				<s:date name="model.memberLastSendTime" format="yyyy-MM-dd"/>
		  	</td>
		  	<td align="right" width="80" bgcolor="#EBEBEB">发送历史：</td>
			<td bgcolor="#EBEBEB">
				${model.memberSendHistory }
		  	</td>
	    </tr>
	</table>
</fieldset>