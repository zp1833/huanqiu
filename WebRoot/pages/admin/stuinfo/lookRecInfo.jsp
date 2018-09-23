<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<table width="100%" align="center" bgcolor="#FFFFFF" cellpadding="2" cellspacing="1">
	<tr>
	  	<td align="right" width="100" bgcolor="#EBEBEB">咨询日期：</td>
		<td bgcolor="#EBEBEB" width="200">
			<s:date name="model.advDate" format="yyyy-MM-dd"/>
	  	</td>
		<td align="right" bgcolor="#EBEBEB" width="100">学生姓名：</td>
		<td bgcolor="#EBEBEB">
			${model.stuName }
	  	</td>
    </tr>
    <tr>
	  	<td align="right" bgcolor="#EBEBEB">负责顾问：</td>
		<td bgcolor="#EBEBEB">
			${model.advisor }
	  	</td>
		<td align="right" bgcolor="#EBEBEB">咨询课程：</td>
		<td bgcolor="#EBEBEB">
			${model.advCourse}
	  	</td>
    </tr>
	<tr>
	  	<td align="right" bgcolor="#EBEBEB">信息来源：</td>
		<td bgcolor="#EBEBEB">
			${model.source }
	  	</td>
		<td align="right" bgcolor="#EBEBEB">来源明细：</td>
		<td bgcolor="#EBEBEB">
			${model.sourceFrom }
	  	</td>
    </tr>
    <tr>
	  	<td align="right" bgcolor="#EBEBEB">咨询形式：</td>
		<td bgcolor="#EBEBEB">
			${model.advType }
	  	</td>
		<td align="right" bgcolor="#EBEBEB">接待员工：</td>
		<td bgcolor="#EBEBEB">
			${model.recEmp }
	  	</td>
    </tr>
</table>
