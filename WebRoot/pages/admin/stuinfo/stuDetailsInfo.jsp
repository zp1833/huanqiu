<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<stc:role ifNotGranted="ROLE_ADMIN,ROLE_ORADMIN,ROLE_REC,ROLE_DOC">
<table width="100%" align="center" bgcolor="#FFFFFF" style="margin-top: 5px" cellpadding="2" cellspacing="1">
	<tr>
		<td align="right" bgcolor="#EBEBEB" width="80" height="30">学生姓名：</td>
		<td bgcolor="#EBEBEB" width="70" style="padding-left: 5px">
			<input size="12" maxlength="50" name="model.stuName" id="model.stuName" value="${model.stuName }">
	  	</td>
	  	<td align="right" bgcolor="#EBEBEB" width="80">负责顾问：</td>
		<td bgcolor="#EBEBEB" width="70" style="padding-left: 5px">
			<select name="model.advisor" id="model.advisor">
				<option value="">--请选择--</option>
				<c:forEach items="${advEmps}" var="emp">
					<c:if test="${userInSession.employees.isDeptAdmin == '1' || model.advisor == emp.name}">
						<option ${model.advisor==emp.name?"selected":"" } value="${emp.name }">${emp.name }</option>
					</c:if>
				</c:forEach>
			</select>
	  	</td>
	  	<td align="right" width="80" bgcolor="#EBEBEB">咨询日期：</td>
		<td bgcolor="#EBEBEB" width="70" style="padding-left: 5px">
			<font color="blue"><s:date name="model.advDate" format="yyyy-MM-dd"/></font>
	  	</td>
	  	<td></td>
    </tr>
</table>
</stc:role>
<fieldset style="margin:8px;">
      <legend>学生信息</legend>
<table  align="left" bgcolor="#FFFFFF" cellpadding="2" cellspacing="1">
	
	<tr>
	  	<td align="right" style="width:90px; text-align: right" bgcolor="#EBEBEB">当前进度：</td>
		<td bgcolor="#EBEBEB" style="width:150px">
			<select name="model.currentPlan" id="model.currentPlan">
				<option value="">--请选择--</option>
				<option ${model.currentPlan=="咨询中"?"selected":"" } value="咨询中">咨询中</option>
				<option ${model.currentPlan=="测试阶段"?"selected":"" } value="测试阶段">测试阶段</option>
				<option ${model.currentPlan=="试听阶段"?"selected":"" } value="试听阶段">试听阶段</option>
				<option ${model.currentPlan=="报名中"?"selected":"" } value="报名中">报名中</option>
				<option ${model.currentPlan=="上课中"?"selected":"" } value="签证申请中">上课中</option>
				<option ${model.currentPlan=="后期服务"?"selected":"" } value="后期服务">后期服务</option>
			</select>
	  	</td>
		<td align="right" bgcolor="#EBEBEB" width="120">下次回访日期：</td>
		<td bgcolor="#EBEBEB" width="150">
		<stc:role ifNotGranted="ROLE_REC">
			<input type="text" name="model.nextVisitTime" size="12" id="model.nextVisitTime" 
						class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d'})"
							value='<s:date name="model.nextVisitTime" format="yyyy-MM-dd"/>'/>
		</stc:role>
	  	</td>
	  	<td align="right" bgcolor="#EBEBEB" width="100">状态定义日期：</td>
		<td bgcolor="#EBEBEB">
			<input type="text" name="model.statusDefineTime" size="12" id="model.statusDefineTime" 
						class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
							value='<s:date name="model.statusDefineTime" format="yyyy-MM-dd"/>'/>
	  	</td>
    </tr>
    <tr>
	  	<td align="right" bgcolor="#EBEBEB" style="width:90px">出生年月：</td>
		<td bgcolor="#EBEBEB" style="width:150px">
			<input type="text" name="model.birthday" size="12" id="model.birthday" 
						class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
							value='<s:date name="model.birthday" format="yyyy-MM-dd"/>'/>
	  	</td>
		<td align="right" bgcolor="#EBEBEB" width="120">毕业时间：</td>
		<td bgcolor="#EBEBEB">
			<input type="text" name="model.finishTime" size="12" id="model.finishTime" 
						class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
							value='<s:date name="model.finishTime" format="yyyy-MM-dd"/>'/>
	  	</td>
	  	<td align="right" bgcolor="#EBEBEB" width="100">学生分类：</td>
		<td bgcolor="#EBEBEB"style="width:150px">
			<select name="model.stuType" id="model.stuType" onchange="changeSignStatus(this)">
				<option value="">--请选择--</option>
				<option ${model.stuType=="签约率90%以上"?"selected":"" } value="签约率90%以上">签约率90%以上</option>
				<option ${model.stuType=="签约率80%"?"selected":"" } value="签约率80%">签约率80%</option>
				<option ${model.stuType=="签约率50%"?"selected":"" } value="签约率50%">签约率50%</option>
				<option ${model.stuType=="签约率20%"?"selected":"" } value="签约率20%">签约率20%</option>
				<option ${model.stuType=="沉默客户"?"selected":"" } value="沉默客户">沉默客户</option>
				<option ${model.stuType=="长期客户"?"selected":"" } value="长期客户">长期客户</option>
				<option ${model.stuType=="已签约"?"selected":"" } value="已签约">已签约</option>
			</select>
	  	</td>
    </tr>
    <tr>
	  	<td align="right" bgcolor="#EBEBEB" style="width:90px">文化程度：</td>
		<td bgcolor="#EBEBEB" style="width:150px">
			<select name="model.education">
				<option value="">--请选择--</option>
				<option ${model.education=="硕士"?"selected":"" } value="硕士">硕士</option>
				<option ${model.education=="本科"?"selected":"" } value="本科">本科</option>
				<option ${model.education=="专科"?"selected":"" } value="专科">专科</option>
				<option ${model.education=="高中"?"selected":"" } value="高中">高中</option>
				<option ${model.education=="高中以下"?"selected":"" } value="高中以下">高中以下</option>
			</select>
	  	</td>
	  	<td align="right" bgcolor="#EBEBEB" width="120">毕业院校：</td>
		<td bgcolor="#EBEBEB">
			<input name="model.finishSchool" value="${model.finishSchool }" size="15" maxlength="50">
	  	</td>
	  	<td align="right" bgcolor="#EBEBEB" width="100">在读年级：</td>
	  	<td bgcolor="#EBEBEB">
			<select name="model.currentGrade">
				<option value="">--请选择--</option>  
				<option ${model.currentGrade=="六年级"?"selected":"" } value="六年级">六年级</option>
				<option ${model.currentGrade=="初一"?"selected":"" } value="初一">初一</option>
				<option ${model.currentGrade=="初二"?"selected":"" } value="初二">初二</option>
				<option ${model.currentGrade=="初三"?"selected":"" } value="初三">初三</option>
				<option ${model.currentGrade=="高一"?"selected":"" } value="高一">高一</option>
				<option ${model.currentGrade=="高二"?"selected":"" } value="高二">高二</option>
				<option ${model.currentGrade=="高三"?"selected":"" } value="高三">高三</option>
				<option ${model.currentGrade=="中专"?"selected":"" } value="中专">中专</option>
				<option ${model.currentGrade=="大专"?"selected":"" } value="大专">大专</option>
				<option ${model.currentGrade=="大一"?"selected":"" } value="大一">大一</option>
				<option ${model.currentGrade=="大二"?"selected":"" } value="大二">大二</option>
				<option ${model.currentGrade=="大三"?"selected":"" } value="大三">大三</option>
				<option ${model.currentGrade=="大四"?"selected":"" } value="大四">大四</option>
				<option ${model.currentGrade=="研一"?"selected":"" } value="研一">研一</option>
				<option ${model.currentGrade=="研二"?"selected":"" } value="研二">研二</option>
				<option ${model.currentGrade=="成人"?"selected":"" } value="成人">成人</option>
			</select>
	  	</td>
    </tr>
    <tr>
    	<td align="right" bgcolor="#EBEBEB" style="width:90px">目标学历：</td>
		<td bgcolor="#EBEBEB" style="width:150px">
			<select name="model.hopeDegree" id="model.hopeDegree">
				<option value="">--请选择--</option>
				<option ${model.hopeDegree=="高中"?"selected":"" } value="高中">高中</option>
				<option ${model.hopeDegree=="专科"?"selected":"" } value="专科">专科</option>
				<option ${model.hopeDegree=="本科"?"selected":"" } value="本科">本科</option>
				<option ${model.hopeDegree=="硕士"?"selected":"" } value="硕士">硕士</option>
				<option ${model.hopeDegree=="博士"?"selected":"" } value="博士">博士</option>
			</select>
	  	</td>
	  	<td align="right" bgcolor="#EBEBEB" width="120">在读院校：</td>
		<td bgcolor="#EBEBEB" width="150">
			<input name="model.currentSchool" value="${model.currentSchool }" size="15" maxlength="50">
	  	</td>
	  	<td align="right" bgcolor="#EBEBEB" width="100">所学专业：</td>
		<td bgcolor="#EBEBEB">
			<input name="model.speciality" value="${model.speciality }" size="12" maxlength="50">
	  	</td>
    </tr>
    <tr>
	  	<td align="right" bgcolor="#EBEBEB" style="width:90px">签约情况：</td>
		<td bgcolor="#EBEBEB" style="width:150px">
			<select name="model.signStatus" id="model.signStatus" onchange="changeStuType(this)">
				<option ${model.signStatus=="0"?"selected":"" } value="0">否</option>
				<option ${model.signStatus=="1"?"selected":"" } value="1">是</option>
			</select>
	  	</td>
	  	<td align="right" bgcolor="#EBEBEB" width="120">联系方式：</td>
		<td bgcolor="#EBEBEB" width="150">
			<stc:role ifAnyGranted="ROLE_ADV">
				<c:set var="isAdv" value="1"></c:set>
			</stc:role>
			<input name="model.contact" id="model.contact" value="${model.contact }" size="12" maxlength="11" <c:if test="${userInSession.employees.isDeptAdmin != '1' && isAdv == '1'}">readonly="readonly"</c:if>>
			<input name="model.contact1" id="model.contact1" value="${model.contact1 }" size="12" maxlength="11" <c:if test="${userInSession.employees.isDeptAdmin != '1' && isAdv == '1'}">readonly="readonly"</c:if>>
	  	</td>
	  	<td align="right" width="100" bgcolor="#EBEBEB">高考英语成绩：</td>
		<td bgcolor="#EBEBEB" width="200">
			<input name="model.collegeScore" value="${model.collegeScore}" size="12" maxlength="4">
	  	</td>
    </tr>
    <tr>
	  	<td align="right" bgcolor="#EBEBEB" style="width:90px">婚否：</td>
		<td bgcolor="#EBEBEB" style="width:150px">
			<select name="model.isMerried">
				<option ${model.isMerried=="0"?"selected":"" } value="0">否</option>
				<option ${model.isMerried=="1"?"selected":"" } value="1">是</option>
			</select>
	  	</td>
		<td align="right" width="120" bgcolor="#EBEBEB">外语程度：</td>
		<td bgcolor="#EBEBEB" width="200">
			<input name="model.enDegree" value="${model.enDegree }" size="10" maxlength="50">
	  	</td>
	  	<td align="right" bgcolor="#EBEBEB" width="100">所需雅思成绩：</td>
		<td bgcolor="#EBEBEB" width="150">
			<select name="model.needScore">
				<option value="">--请选择--</option>
				<option value="4.5" ${model.needScore=="4.5"?"selected":"" }>4.5</option>
				<option value="5" ${model.needScore=="5"?"selected":"" }>5</option>
				<option value="5.5" ${model.needScore=="5.5"?"selected":"" }>5.5</option>
				<option value="6" ${model.needScore=="6"?"selected":"" }>6</option>
				<option value="6.5" ${model.needScore=="6.5"?"selected":"" }>6.5</option>
				<option value="7以上" ${model.needScore=="7以上"?"selected":"" }>7以上</option>
			</select>
	  	</td>
    </tr>
    <tr>
    	<td align="right" bgcolor="#EBEBEB" style="width:90px">性别：</td>
	  	<td bgcolor="#EBEBEB" style="width:150px">
			<select name="model.sex">
				<option ${model.sex=="1"?"selected":"" } value="1">男</option>
				<option ${model.sex=="0"?"selected":"" } value="0">女</option>
			</select>
	  	</td>
	  	<td align="right" width="120" bgcolor="#EBEBEB">是否参加过雅思考试：</td>
		<td bgcolor="#EBEBEB" width="200">
			<select name="model.isExam" id="model.isExam" onchange="showExamScore(this)">
				<option ${model.isExam=="0"?"selected":"" } value="0">否</option>
				<option ${model.isExam=="1"?"selected":"" } value="1">是</option>
			</select>
			<span id="examScoreInput" style="display: none">
				&nbsp;成绩：<input type="text" value="${model.examScore }" name="model.examScore" size="5"/>
			</span>
	  	</td>
	  	<td align="right" bgcolor="#EBEBEB" width="100">介绍给其他中介：</td>
		<td bgcolor="#EBEBEB">
			<input name="model.introToAgency" id="model.introToAgency" value="${model.introToAgency}" size="12" maxlength="50">
	  	</td>
    </tr>
    <tr>
    	<td align="right" bgcolor="#EBEBEB" style="width:90px">是否会员：</td>
	  	<td bgcolor="#EBEBEB" style="width:150px">
			<select name="model.isMember" id="model.isMember">
				<option ${model.isMember=="0"?"selected":"" } value="0">否</option>
				<option ${model.isMember=="1"?"selected":"" } value="1">是</option>
			</select>
	  	</td>
	  	<td align="right" width="120" bgcolor="#EBEBEB">留学国家：</td>
		<td bgcolor="#EBEBEB">
			<select name="model.country" id="model.country" style="width:88px" onchange="inputCountry(this)">
				<option value="">--请选择--</option>
				<option ${model.country=='美国'?"selected":"" } value="美国">美国</option>
				<option ${model.country=='英国'?"selected":"" } value="英国">英国</option>
				<option ${model.country=='加拿大'?"selected":"" } value="加拿大">加拿大</option>
				<option ${model.country=='澳大利亚'?"selected":"" } value="澳大利亚">澳大利亚</option>
				<option ${model.country=='新西兰'?"selected":"" } value="新西兰">新西兰</option>
				<option ${model.country=='新加坡'?"selected":"" } value="新加坡">新加坡</option>
				<option ${model.country=='日本'?"selected":"" } value="日本">日本</option>
				<option ${model.country=='韩国'?"selected":"" } value="韩国">韩国</option>
				<option ${model.country=='北欧'?"selected":"" } value="北欧">北欧</option>
				<option ${model.country=='法国'?"selected":"" } value="法国">法国</option>
				<option ${model.country=='意大利'?"selected":"" } value="意大利">意大利</option>
				<option ${model.country=='西班牙'?"selected":"" } value="西班牙">西班牙</option>
				<option ${model.country=='爱尔兰'?"selected":"" } value="爱尔兰">爱尔兰</option>
				<option ${model.country=='荷兰'?"selected":"" } value="荷兰">荷兰</option>
				<option ${model.country=='马来西亚'?"selected":"" } value="马来西亚">马来西亚</option>
				<option ${model.country=='日语'?"selected":"" } value="日语">日语</option>
				<option ${model.country=='韩语'?"selected":"" } value="韩语">韩语</option>
				<option ${model.country=='雅思'?"selected":"" } value="雅思">雅思</option>
				<option ${model.country=='英语提升'?"selected":"" } value="英语提升">英语提升</option>
				<option ${model.country=='未定'?"selected":"" } value="未定">未定</option>
			</select>
	  	</td>
	  	<td align="right" bgcolor="#EBEBEB" width="100">城市：</td>
		<td bgcolor="#EBEBEB">
			<input name="model.city" id="model.city" value="${model.city}" size="12" maxlength="50">
	  	</td>
    </tr>
    <tr>
	  	<td align="right" bgcolor="#EBEBEB" style="width:90px">微信/QQ：</td>
		<td bgcolor="#EBEBEB" colspan="5">
			<input name="model.qq" id="model.qq" type="text" value="${model.qq }" size="15" maxlength="50">
	  	</td>
    </tr>
    <tr>
    	<td align="right" bgcolor="#EBEBEB" style="width:90px">信息备注：</td>
	  	<td bgcolor="#EBEBEB" colspan="5">
			<textarea style="width:98%; height: 40px" name="model.infoDesc">${model.infoDesc }</textarea>
	  	</td>
    </tr>
</table>
</fieldset>

<fieldset style="margin:8px;">
    <legend>客户跟踪记录</legend>
	<table align="left" bgcolor="#FFFFFF" width="800" cellpadding="2" cellspacing="1">
		<tr>
			<td align="right" bgcolor="#EBEBEB" width="100">最新跟踪时间：</td>
			<td bgcolor="#EBEBEB" width="120">
				<input type="text" name="model.recentTraceTime" size="12" id="model.recentTraceTime" 
							class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})"
								value='<s:date name="model.recentTraceTime" format="yyyy-MM-dd"/>'/>
		  	</td>
		  	<td align="right" bgcolor="#EBEBEB" width="140">预计下次雅思考试时间：</td>
			<td bgcolor="#EBEBEB">
				<input type="text" name="model.nextExamTime" size="12" id="model.nextExamTime" 
							class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d'})"
								value='<s:date name="model.nextExamTime" format="yyyy-MM-dd"/>'/>
		  	</td>
		</tr>
		<tr>
		  	<td align="right" width="100" bgcolor="#EBEBEB">客户跟踪记录：</td>
			<td bgcolor="#EBEBEB" colspan="3">
				<textarea style="width:400px; height: 60px" name="model.traceDesc" id="model.traceDesc">${model.traceDesc }</textarea>
		  	</td>
	    </tr>
	</table>
</fieldset>
	