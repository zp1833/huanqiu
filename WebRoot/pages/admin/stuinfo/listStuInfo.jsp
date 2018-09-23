
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学生信息列表</title>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/ec.jsp" %>
<%@include file="/common/extSkin.jsp" %>
<script type="text/javascript" src="${ctx}/calendar/WdatePicker.js"></script>
<%@include file="/common/dwr.jsp" %>
<script type="text/javascript" src="${ctx}/dwr/interface/StuInfoAction.js"></script>
<script type="text/javascript">

	function onRemove() {
	    var sels = document.getElementsByName('selectedItems');
	    var checked = false;
	    for(i = 0; i < sels.length; i++) {
	        if(sels[i].id == 'selectedItems' && sels[i].checked) {
	           checked = true;
	           break;
	        }
	    } 
	    if(!checked) {
	        alert('请至少选择一条记录。');
	        return;
	    }
	    
	    if(confirm("您确定删除所选记录吗？")) {
	       var articles = new Array();
	       var j=0;
		    for (var i = 0; i < sels.length; i ++) {
		    	if (sels[i].checked) {
		    		articles[j]=sels[i].value;
		    		j++;
		    	}
		    }

		  StuInfoAction.removeStuInfos(articles,function(result){
				  if(result == "1"){
				   	ECSideUtil.reload('ec');
				  } else {
				  	alert("删除中出现错误，请重新登陆！");
				  }
			});
	    } else {
	       return false;
	    }
	}
	
	function onChangeAdv() {
	    var sels = document.getElementsByName('selectedItems');
	    var checked = false;
	    for(i = 0; i < sels.length; i++) {
	        if(sels[i].id == 'selectedItems' && sels[i].checked) {
	           checked = true;
	           break;
	        }
	    } 
	    if(!checked) {
	        alert('请至少选择一条记录。');
	        return;
	    }
	    
	    var currentAdv = document.getElementById("advName").options[document.getElementById("advName").selectedIndex];
	    if(currentAdv.value == '') {
			alert("请选择顾问");    
		    return;
	    }
	    
	    var advName = document.getElementById("advName").options[document.getElementById("advName").selectedIndex].text;
	    if(confirm("您确定将所选记录转至" + advName + "名下吗？")) {
	       var articles = new Array();
	       var j=0;
		    for (var i = 0; i < sels.length; i ++) {
		    	if (sels[i].checked) {
		    		articles[j]=sels[i].value;
		    		j++;
		    	}
		    }

		  StuInfoAction.changeStuInfos(articles, advName, function(result){
				  if(result == "1"){
				   	ECSideUtil.reload('ec');
				  } else {
				  	alert("更改中出现错误，请重新登陆！");
				  }
			});
	    } else {
	       return false;
	    }
	}
	
	function selectField(value) {
		var params = window.showModalDialog("${ctx}/pages/admin/stuinfo/fieldDefine.jsp",value,"dialogWidth=700px;dialogHeight=520px");
		$("definedField").value = params;
	}

	function defineSearch(value) {
		var params = window.showModalDialog("${ctx}/pages/admin/stuinfo/defineSearch.jsp",value,"dialogWidth=400px;dialogHeight=170px");
		$("definedSearch").value = params;
	}
	
	function onModify(id,currentPageNo) {
		$("model.id").value = id;
		$("currentPageNo").value = currentPageNo;
		$("modifyFlag").value = "modify";
		$("listStuInfo").action = "${ctx}/admin/stuinfo/editStuInfo.do";
		$("listStuInfo").submit();
	}
	
	var falg = true;
	
	function initWatting(){
		<c:if test="${currentPageNo != 0}">
		var xec = ECSideUtil.getGridObj("ec");
		if(xec.waitingBar != null && falg == true){
			ECSideUtil.gotoPage(${currentPageNo},'ec');
			falg = false;
		}
		if(falg){
		 	setTimeout("initWatting()",200);	
		}
		if(!falg){
			var sels = document.getElementsByName('selectedItems');
			for(var i=0;i<sels.length;i++){
				if(sels[i].value == "${model.id}"){
					var row1 = sels[i].parentNode.parentNode;
					ECSideUtil.selectRow(row1,'ec');
				}
			}
		}
		</c:if>
		
	}
	
</script>
</head>
<body onload="initWatting();">

<div class="x-panel">
    	<div class="x-panel-header">
    	
    		<c:if test="${showType == null}">
    			学生信息总表
    		</c:if>
    		<c:if test="${showType == '0' || showType == ''}">
    			学生信息列表
    		</c:if>
    		<c:if test="${showType == '1'}">
    			签约客户列表
    		</c:if>
    		<c:if test="${showType == '2'}">
    			沉默客户列表
    		</c:if>
    		<c:if test="${showType == '3'}">
    			长期客户列表
    		</c:if>
    		<c:if test="${showType == 'member'}">
    			俱乐部会员表
    		</c:if>
    		
    	</div>
    	<div class="x-toolbar">
	    	<table width="99%">	      
		      <tr>	   
		      	 <td>
		      	 	<s:form action="listStuInfo" id="listStuInfo" method="post" theme="simple">
		      	 		<s:hidden name="model.id" id="model.id"/>
		      	 		<s:hidden name="currentPageNo" id="currentPageNo"/>
		      	 		<s:hidden name="modifyFlag" id="modifyFlag"/>
		      	 		<s:hidden name="showType" id="showType"/>
						<table border="0">	      
						      <tr>	   
						      	 <td>学生姓名：</td>
						      	 <td><input type="text" name="name" value="${name }" size="8"></td>
						      	 <td>咨询日期：</td>
						      	 <td><input type="text" name="beginAdvDate" size="12" id="beginAdvDate" 
									class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
										value='<s:date name="beginAdvDate" format="yyyy-MM-dd"/>'/> 至
								   <input type="text" name="endAdvDate" size="12" id="endAdvDate" 
									class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
										value='<s:date name="endAdvDate" format="yyyy-MM-dd"/>'/>
						      	 </td>
						      	 <td>当前进度：</td>
								 <td>
									<select name="currPlan" style="width:90px">
										<option value="">--请选择--</option>
										<option ${model.currentPlan=="咨询中"?"selected":"" } value="咨询中">咨询中</option>
										<option ${model.currentPlan=="测试阶段"?"selected":"" } value="测试阶段">测试阶段</option>
										<option ${model.currentPlan=="试听阶段"?"selected":"" } value="试听阶段">试听阶段</option>
										<option ${model.currentPlan=="报名中"?"selected":"" } value="报名中">报名中</option>
										<option ${model.currentPlan=="上课中"?"selected":"" } value="签证申请中">上课中</option>
										<option ${model.currentPlan=="后期服务"?"selected":"" } value="后期服务">后期服务</option>
									</select>
								 </td>
						      	 <td>下次回访时间：</td>
						      	 <td>
						      	 	<input type="text" name="beginVisitDate" size="12" id="beginVisitDate" 
										class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
											value='<s:date name="beginVisitDate" format="yyyy-MM-dd"/>'/> 至
									   <input type="text" name="endVisitDate" size="12" id="endVisitDate" 
										class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
											value='<s:date name="endVisitDate" format="yyyy-MM-dd"/>'/>
									<input type="submit" value="查询" class="button" style="width:50px"/>
						      	 </td>
						      	 <td align="right" colspan="2">
						      	 	<table>
								         <tr>
								         	<td>
									         	<stc:role ifAnyGranted="ROLE_ADMIN">
									         		<a href="#" onclick="onChangeAdv()">
									         		<img src="${ctx}/images/exticons/refresh.gif"/>更改顾问</a>
									         	</stc:role>
									         </td>
									         <td>
									         	<stc:role ifAnyGranted="ROLE_ADMIN,ROLE_REC">
									         		<a href="${ctx}/admin/stuinfo/newStuInfo.do">
									         		<img src="${ctx}/images/exticons/add.gif"/>添加</a>
									         	</stc:role>
									         </td>
								         	 <td>
								         	 	<stc:role ifAnyGranted="ROLE_ADMIN">
								         	 		<a href="#" onclick="onRemove()">
								         	 		<img src="${ctx}/images/icons/delete.gif"/> 删除</a>
								         	 	</stc:role>		
								         	 </td>
								         </tr>
							         </table>
						      	 </td>
						      </tr>
						      <tr>	   
						      	 <td>咨询课程：</td>
						      	 <td><input type="text" name="advCourse" value="${course }" size="8"></td>
						      	 <td>签约日期：</td>
						      	 <td>
						      	 	<input type="text" name="beginSignDate" size="12" id="beginSignDate" 
										class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
											value='<s:date name="beginSignDate" format="yyyy-MM-dd"/>'/> 至
									   <input type="text" name="endSignDate" size="12" id="endSignDate" 
										class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
											value='<s:date name="endSignDate" format="yyyy-MM-dd"/>'/>
						      	 </td>
						      	 <td>学生分类：</td>
								 <td>
									<select name="type" style="width:90px">
										<option value="">--请选择--</option>
										<option ${model.stuType=="签约率90%以上"?"selected":"" } value="签约率90%以上">签约率90%以上</option>
										<option ${model.stuType=="签约率80%"?"selected":"" } value="签约率80%">签约率80%</option>
										<option ${model.stuType=="签约率50%"?"selected":"" } value="签约率50%">签约率50%</option>
										<option ${model.stuType=="签约率20%"?"selected":"" } value="签约率20%">签约率20%</option>
									</select>
							  	 </td>
							  	 <td>顾问姓名：</td>
						      	 <td align="left">
						      	 	<span style="float:left">
							      	 	<select name="advName" id="advName" style="width: 80px">
											<option value="">--请选择--</option>
											<c:forEach items="${advEmps}" var="emp">
												<option ${advName==emp.name?"selected":"" } value="${emp.name }">${emp.name }</option>
											</c:forEach>
										</select>
									</span>
									<span style="float:right;padding-top: 3px">
									<stc:role ifAnyGranted="ROLE_ADMIN,ROLE_SEARCH,ROLE_ORADMIN,ROLE_ADV">
										<input type="button" value="选择字段" class="button" onclick="selectField($('definedField').value);">
							      	 	<input type="hidden" value="${definedField }" id="definedField" name="definedField">
									</stc:role>
							      	 	<input type="button" value="自定义查询" class="button" onclick="defineSearch($('definedSearch').value)">
							      	 	<input type="hidden" value="${definedSearch }" id="definedSearch" name="definedSearch">
						      	 	</span>
						      	 </td>
						      	 <td>入会时间：</td>
						      	 <td>
						      	 	<input type="text" name="beginJoinDate" size="12" id="beginJoinDate" 
										class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
											value='<s:date name="beginJoinDate" format="yyyy-MM-dd"/>'/> 至
									   <input type="text" name="endJoinDate" size="12" id="endJoinDate" 
										class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
											value='<s:date name="endJoinDate" format="yyyy-MM-dd"/>'/>
						      	 </td>
						      </tr>
					    </table>
					</s:form>
		      	 </td>      
		      </tr>
	       </table>
    	</div>
    	
    	<stc:role ifAnyGranted="ROLE_ADMIN">
    		<c:set var="xlsName" value="学生信息列表.xls"></c:set>
    	</stc:role>
    	<div class="x-panel-body">
    	    <div style="margin-left:-3px;" align="center">
	    	<ec:table items="items" var="item" retrieveRowsCallback="limit" sortRowsCallback="limit"
	    	filterRowsCallback="limit"
			action="listStuInfo.do"
			useAjax="true" doPreload="false"
			pageSizeList="10,30,45,60,100,500,1000,2000,3000" 
			editable="false" 
			sortable="true"	
			rowsDisplayed="10"	
			generateScript="true"	
			resizeColWidth="true"	
			classic="true"
			width="100%" 	
			height="240"	
			minHeight="240"  
			xlsFileName="${xlsName}">
			<c:if test="${definedField != null && definedField != ''}">
				<ec:row>
				    <ec:column width="40" property="_s" title="选择" style="text-align:center" sortable="false">
				    	<input id="selectedItems" type="checkbox" name="selectedItems" value='${item.id}'/>
				    </ec:column>
				<c:if test="${fn:contains(definedField, 'stuName_s')}">
					<ec:column width="70" property="stuName" title="学生姓名">
						<a href="lookStuInfo.do?id=${item.id}" target="stuDetails" title="查看详细信息" id="detail_${item.id}"><font color="blue">${item.stuName}</font></a>
						<span dojoType="dijit.Tooltip" connectId="detail_${item.id}" style="display:none;">
			                <span style="font-size:12px;">
			                	${item.traceDesc}
			                </span>
			             </span>
					</ec:column>
				</c:if>
				<c:if test="${fn:contains(definedField, 'advDate_s')}">
					<ec:column width="120" property="advDate" title="咨询日期" cell="date" format="yyyy-MM-dd" style="text-align:center"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'advCourse_s')}">
					<ec:column width="70" property="advCourse" title="咨询课程"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'source_s')}">
					<ec:column width="60" property="source" title="信息来源"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'sourceFrom_s')}">
					<ec:column width="60" property="sourceFrom" title="来源明细" style="height:30px"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'advType_s')}">
					<ec:column width="60" property="advType" title="咨询形式" style="text-align:center"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'recEmp_s')}">
					<ec:column width="60" property="recEmp" title="接待员工"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'advisor_s')}">
					<ec:column width="60" property="advisor" title="负责顾问"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'contact_s')}">
					<ec:column width="60" property="contact" title="联系方式"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'currentPlan_s')}">
					<ec:column width="60" property="currentPlan" title="当前进度"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'className_s')}">
					<ec:column width="60" property="className" title="班　　号"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'courseName_s')}">
					<ec:column width="60" property="courseName" title="课程名称"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'tuition_s')}">
					<ec:column width="60" property="tuition" title="学　　费"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'realMoney_s')}">
					<ec:column width="60" property="realMoney" title="实收金额"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'reportSchool_s')}">
					<ec:column width="60" property="reportSchool" title="报名校区"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'dealPerson_s')}">
					<ec:column width="60" property="dealPerson" title="经办人"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'stuType_s')}">
					<ec:column width="80" property="stuType" title="学生类型"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'country_s')}">
					<ec:column width="80" property="country" title="留学国家">
						${fn:replace(item.country, "#", "")}
					</ec:column>
				</c:if>
				<c:if test="${fn:contains(definedField, 'nextVisitTime_s')}">
					<ec:column width="90" property="nextVisitTime" title="下次回访时间" style="text-align:center" cell="date" format="yyyy-MM-dd"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'statusDefineTime_s')}">
					<ec:column width="90" property="statusDefineTime" title="状态定义日期" style="text-align:center" cell="date" format="yyyy-MM-dd"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'sex_s')}">
					<ec:column width="40" property="sex" title="性别" style="text-align:center">
						<c:if test="${item.sex==1}">
							男
						</c:if>
						<c:if test="${item.sex!=1}">
							女
						</c:if>
					</ec:column>
				</c:if>
				<c:if test="${fn:contains(definedField, 'birthday_s')}">
					<ec:column width="120" property="birthday" title="出生日期" cell="date" format="yyyy-MM-dd"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'isMerried_s')}">
					<ec:column width="40" property="isMerried" title="婚否">
						<c:if test="${item.isMerried==1}">
							是
						</c:if>
						<c:if test="${item.isMerried!=1}">
							否
						</c:if>
					</ec:column>
				</c:if>
				<c:if test="${fn:contains(definedField, 'education_s')}">
					<ec:column width="60" property="education" title="文化程度" style="text-align:center"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'speciality_s')}">
					<ec:column width="60" property="speciality" title="所学专业"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'finishSchool_s')}">
					<ec:column width="120" property="finishSchool" title="毕业院校"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'finishTime_s')}">
					<ec:column width="90" property="finishTime" title="毕业时间" cell="date" format="yyyy-MM-dd" style="text-align:center"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'currentSchool_s')}">
					<ec:column width="120" property="currentSchool" title="在读院校"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'currentGrade_s')}">
					<ec:column width="70" property="currentGrade" title="在读年级" style="text-align:center"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'collegeScore_s')}">
					<ec:column width="80" property="collegeScore" title="高考英语成绩" style="text-align:center"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'hopeDegree_s')}">
					<ec:column width="60" property="hopeDegree" title="目标学历"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'enDegree_s')}">
					<ec:column width="60" property="enDegree" title="外语程度"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'infoDesc_s')}">
					<ec:column width="100" property="infoDesc" title="信息备注" style="height:30px"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'traceDesc_s')}">
					<ec:column width="200" property="traceDesc" title="客户跟踪记录" style="height:30px"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'recentTraceTime')}">
					<ec:column width="90" property="recentTraceTime" title="最新跟踪时间" cell="date" format="yyyy-MM-dd" style="text-align:center"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'introToAgency_s')}">
					<ec:column width="100" property="introToAgency" title="介绍给中介"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'signStatus_s')}">
					<ec:column width="60" property="signStatus" title="签约情况" style="text-align:center">
						<c:if test="${item.signStatus==1}">
							是
						</c:if>
						<c:if test="${item.signStatus!=1}">
							否
						</c:if>
					</ec:column>
				</c:if>
				<c:if test="${fn:contains(definedField, 'dealDate_s')}">
					<ec:column width="90" property="dealDate" title="签约日期" cell="date" format="yyyy-MM-dd" style="text-align:center"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'signName_s')}">
					<ec:column width="60" property="signName" title="签约人"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'signDesc_s')}">
					<ec:column width="200" property="signDesc" title="签约备注" style="height:30px"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'schoolName_s')}">
					<ec:column width="100" property="schoolName" title="学校名称"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'isMember_s')}">
					<ec:column width="100" property="isMember" title="是否会员">
						<c:if test="${item.isMember=='1'}">
							是
						</c:if>
						<c:if test="${item.signStatus!='1'}">
							否
						</c:if>
					</ec:column>
				</c:if>
				<c:if test="${fn:contains(definedField, 'memberJoinTime_s')}">
					<ec:column width="90" property="memberJoinTime" title="入会时间" cell="date" format="yyyy-MM-dd" style="text-align:center"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'memberIntegral_s')}">
					<ec:column width="90" property="memberIntegral" title="入会积分" style="text-align:center"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'memberLastIntegralTime_s')}">
					<ec:column width="100" property="memberLastIntegralTime" title="最新积分时间" cell="date" format="yyyy-MM-dd" style="text-align:center"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'memberLastActiveTime_s')}">
					<ec:column width="90" property="memberLastActiveTime" title="最新活动时间" cell="date" format="yyyy-MM-dd" style="text-align:center"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'memberLastChangeTime_s')}">
					<ec:column width="90" property="memberLastChangeTime" title="最新兑换时间" cell="date" format="yyyy-MM-dd" style="text-align:center"/>
				</c:if>
				<c:if test="${fn:contains(definedField, 'memberLastSendTime_s')}">
					<ec:column width="90" property="memberLastSendTime" title="最新资料发送" cell="date" format="yyyy-MM-dd" style="text-align:center"/>
				</c:if>
					<ec:column width="60" property="_4" title="操作" style="text-align:center" sortable="false">
						<a href="#" onclick="onModify('${item.id }','${pageNo }')" target="main">
							<img src="${ctx }/images/icons/modify.gif" border="0"/>
						</a>
					</ec:column>
				</ec:row>
			</c:if>
			<c:if test="${definedField == null || definedField == ''}">
				<ec:row>
				    <ec:column width="40" property="_s" title="选择" style="text-align:center" sortable="false">
				    	<input id="selectedItems" type="checkbox" name="selectedItems" value='${item.id}'/>
				    </ec:column>
					<ec:column width="70" property="stuName" title="学生姓名">
						<a href="lookStuInfo.do?id=${item.id}" target="stuDetails" title="查看详细信息" id="detail_${item.id}"><font color="blue">${item.stuName}</font></a>
						 <span dojoType="dijit.Tooltip" connectId="detail_${item.id}" style="display:none;">
			                <span style="font-size:12px;">${item.traceDesc}
			                </span>
			             </span>
					</ec:column>
					<ec:column width="70" property="advCourse" title="咨询课程" style="text-align:center"/>
					<ec:column width="80" property="advisor" title="负责顾问" style="text-align:center"/>
					<ec:column width="70" property="currentGrade" title="在读年级" style="text-align:center"/>
					<ec:column width="80" property="advDate" title="咨询日期" format="yyyy-MM-dd" cell="date" style="text-align:center"/>
					<ec:column width="80" property="currentPlan" title="当前进度" style="text-align:center"/>
					<ec:column width="90" property="stuType" title="学生分类" style="text-align:center"/>
					<ec:column width="80" property="country" title="留学国家">
						${fn:replace(item.country, "#", "")}
					</ec:column>
					<ec:column width="120" property="introToAgency" title="介绍给中介" style="text-align:center"/>
					<ec:column width="90" property="nextVisitTime" title="下次回访时间" format="yyyy-MM-dd" cell="date" style="text-align:center"/>
					<ec:column width="60" property="signStatus" title="签约情况" style="text-align:center">
						<c:if test="${item.signStatus==1}">
							是
						</c:if>
						<c:if test="${item.signStatus!=1}">
							否
						</c:if>
					</ec:column>
				<c:if test="${showType == '1'}">
					<ec:column width="80" property="dealDate" title="签约日期" format="yyyy-MM-dd" cell="date" style="text-align:center"/>
				</c:if>
					<stc:role ifAnyGranted="ROLE_ADMIN,ROLE_ORADMIN,ROLE_ADV">
						<ec:column width="60" property="_4" title="操作" style="text-align:center" sortable="false">
							<a href="#" onclick="onModify('${item.id }','${pageNo }')" target="main">
								<img src="${ctx }/images/icons/modify.gif" border="0"/>
							</a>
						</ec:column>
					</stc:role>
				</ec:row>
			</c:if>
			</ec:table>
    	</div>
</div>
<iframe width="100%" name="stuDetails" id="stuDetails" height="380" frameborder="0" scrolling="auto">

</iframe>
</body>
</html>
