<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<table width="100%" align="center" bgcolor="#FFFFFF" cellpadding="2" cellspacing="1">
	<tr>
	  	<td align="right" width="135" bgcolor="#EBEBEB">班号：</td>
		<td bgcolor="#EBEBEB" width="220">
			${model.className }
	  	</td>
		<td align="right" bgcolor="#EBEBEB" width="80">课程名称：</td>
		<td bgcolor="#EBEBEB" width="120">
			${model.courseName }
	  	</td>
	  	<td align="right" bgcolor="#EBEBEB" width="80">上课时间：</td>
		<td bgcolor="#EBEBEB">
			<s:date name="model.studyTime" format="yyyy-MM-dd"/>
	  	</td>
    </tr>
    <tr>
	  	<td align="right" bgcolor="#EBEBEB" width="135">学费：</td>
		<td bgcolor="#EBEBEB" width="120">
			${model.tuition }
	  	</td>
	  	<td align="right" bgcolor="#EBEBEB" width="80">实收金额：</td>
		<td bgcolor="#EBEBEB" width="120">
			${model.realMoney }
	  	</td>
	  	<td align="right" bgcolor="#EBEBEB" width="80">付款方式：</td>
		<td bgcolor="#EBEBEB">
			${model.payMethod }
	  	</td>
    </tr>
	<tr>
	  	<td align="right" bgcolor="#EBEBEB" width="135">报名校区：</td>
		<td bgcolor="#EBEBEB" width="120">
			${model.reportSchool }
	  	</td>
	  	<td align="right" bgcolor="#EBEBEB" width="80">经办人：</td>
		<td bgcolor="#EBEBEB" width="120">
			${model.dealPerson }
	  	</td>
	  	<td align="right" bgcolor="#EBEBEB" width="80">签约日期：</td>
		<td bgcolor="#EBEBEB">
			<s:date name="model.dealDate" format="yyyy-MM-dd"/>
	  	</td>
    </tr>
    <tr>
	  	<td align="right" bgcolor="#EBEBEB" width="135">实收金额1：</td>
		<td bgcolor="#EBEBEB" width="120">
			${model.realMoney1 }
	  	</td>
	  	<td align="right" bgcolor="#EBEBEB" width="80">实收金额2：</td>
		<td bgcolor="#EBEBEB" width="120">
			${model.realMoney2 }
	  	</td>
	  	<td align="right" bgcolor="#EBEBEB" width="80">实收金额3：</td>
		<td bgcolor="#EBEBEB">
			${model.realMoney3 }
	  	</td>
    </tr>
    <tr>
	  	<td align="right" bgcolor="#EBEBEB" width="135">实收金额4：</td>
		<td bgcolor="#EBEBEB" width="120">
			${model.realMoney4 }
	  	</td>
	  	<td align="right" bgcolor="#EBEBEB" width="80">实收金额5：</td>
		<td bgcolor="#EBEBEB" width="120">
			${model.realMoney5 }
	  	</td>
	  	<td align="right" bgcolor="#EBEBEB" width="80"></td>
		<td bgcolor="#EBEBEB"></td>
    </tr>
</table>
