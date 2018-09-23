<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="stc" uri="/alphasta/common" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<div id="menu" style="display:none">
 	<div id="menu_catalog">
         <div style="padding-left:5px;">
            <stc:role ifAnyGranted="ROLE_ADMIN,ROLE_REC"> 
            <div style="padding-top:2px">
                <img src="${ctx}/images/exticons/add.gif" class="icon">
                <a href="${ctx}/admin/stuinfo/newStuInfo.do" target="main">添加学生信息</a>
            </div>
            </stc:role>
            <stc:role ifAnyGranted="ROLE_ADMIN,ROLE_SEARCH,ROLE_AUDIT,ROLE_ORADMIN,ROLE_ADV">
			<div style="padding-top:2px">
			    <img src="${ctx}/images/exticons/expand-all.gif" class="icon">
			    <a href="${ctx}/admin/stuinfo/listStuInfo.do" target="main">学生信息总表</a>
			</div>
			</stc:role>
            <stc:role ifAnyGranted="ROLE_ADMIN,ROLE_ADV,ROLE_ORADMIN,ROLE_AUDIT">
			<div style="padding-top:2px">
			    <img src="${ctx}/images/exticons/collapse-all.gif" class="icon">
			    <a href="${ctx}/admin/stuinfo/listStuInfo.do?showType=0" target="main">学生信息列表</a>
			</div>
			<div style="padding-top:2px">
			    <img src="${ctx}/images/exticons/collapse-all.gif" class="icon">
			    <a href="${ctx}/admin/stuinfo/listStuInfo.do?showType=1" target="main">签约客户列表</a>
			</div>
			<div style="padding-top:2px">
			    <img src="${ctx}/images/exticons/collapse-all.gif" class="icon">
			    <a href="${ctx}/admin/stuinfo/listStuInfo.do?showType=2" target="main">沉默客户列表</a>
			</div>
			<div style="padding-top:2px">
			    <img src="${ctx}/images/exticons/collapse-all.gif" class="icon">
			    <a href="${ctx}/admin/stuinfo/listStuInfo.do?showType=3" target="main">长期客户列表</a>
			</div>
			</stc:role>
			<stc:role ifAnyGranted="ROLE_ADMIN,ROLE_ORADMIN,ROLE_SEARCH">
			<div style="padding-top:2px">
			    <img src="${ctx}/images/exticons/collapse-all.gif" class="icon">
			    <a href="${ctx}/admin/stuinfo/listStuInfo.do?showType=member" target="main">俱乐部会员表</a>
			</div>
			</stc:role>
            <stc:role ifAnyGranted="ROLE_ADMIN,ROLE_ORADMIN,ROLE_ADV,ROLE_AUDIT"> 
			<div style="padding-top:2px">
			    <img src="${ctx}/images/exticons/list-items.gif" class="icon">
			    <a href="${ctx}/admin/stuinfo/listVisitStuInfo.do" target="main">顾问回访记录</a>
			</div>
			</stc:role>
         </div>
      </div>
	  <div id="menu_work">
         <div style="padding-left:5px;">
            <div style="padding-top:2px">
                <img src="${ctx}/images/exticons/add.gif" class="icon">
                <a href="${ctx}/admin/workplan/newWorkPlan.do" target="main">添加工作计划</a>
            </div>
			<div style="padding-top:2px">
			    <img src="${ctx}/images/exticons/collapse-all.gif" class="icon">
			    <a href="${ctx}/admin/workplan/listWorkPlan.do" target="main">工作计划列表</a>
			</div>
         </div>
      </div>
      <stc:role ifAnyGranted="ROLE_ADMIN,ROLE_ADV,ROLE_ORADMIN">
      <div id="menu_tradetel">
         <div style="padding-left:5px;">
            <div style="padding-top:2px">
                <img src="${ctx}/images/exticons/add.gif" class="icon">
                <a href="${ctx}/admin/tradetel/newTradeTel.do" target="main">添加同行电话</a>
            </div>
			<div style="padding-top:2px">
			    <img src="${ctx}/images/exticons/collapse-all.gif" class="icon">
			    <a href="${ctx}/admin/tradetel/listTradeTel.do" target="main">同行电话列表</a>
			</div>
         </div>
      </div>
      </stc:role>
      <stc:role ifAnyGranted="ROLE_ADMIN">  
       <div id="menu_sys">
         <div style="padding-left:5px;">
              <div style="padding-top:2px">
                  <img src="${ctx}/images/icons/user.jpg" class="icon">
                  <a href="${ctx}/admin/employee/listEmployees.do" target="main">用户管理</a>
              </div>         
              <div style="padding-top:2px">
                  <img src="${ctx}/images/icons/users_1.gif" class="icon">
                  <a href="${ctx}/admin/dept/list.do" target="main">部门管理</a>
              </div>
              <div style="padding-top:2px">
				 <img src="${ctx}/images/icons/role.gif" class="icon">
				 <a href="${ctx}/admin/security/role/listRoles.do" target="main">角色管理</a> 
			  </div>
         </div>       
      </div>
     </stc:role>

      <div id="menu_person">
         <div style="padding-left:5px;">	
              <div style="padding-top:2px">
                  <img src="${ctx}/images/exticons/square.gif" class="icon">
                  <a href="${ctx}/admin/security/user/newChange.do" target="main">密码管理</a>
              </div>	      
         </div>       
      </div>      
   </div>