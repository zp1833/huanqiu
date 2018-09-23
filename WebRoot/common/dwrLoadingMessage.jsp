<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--You should include this page at the end of body.--%>
<script>
dwr.util._disabledZoneUseCount = 0;
dwr.util.useLoadingMessage = function(message) {
 
  dwr.engine.setPreHook(function() {
    var disabledZone = dwr.util.byId('disabledImageZone');
    disabledZone.style.visibility = 'visible';
    disabledZone.style.display = '';
    dwr.util._disabledZoneUseCount++;

  });
  dwr.engine.setPostHook(function() {
    dwr.util._disabledZoneUseCount--;
    if (dwr.util._disabledZoneUseCount == 0) {      
      var disabledZone = dwr.util.byId('disabledImageZone');
      disabledZone.style.visibility = 'hidden';
      disabledZone.style.display = 'none';
    }
  });
};

if (window.attachEvent) {  
    window.attachEvent('onload', function() {dwr.util.useLoadingMessage();});  
}else if(window.addEventListener){
    window.addEventListener('load', function() {dwr.util.useLoadingMessage();}, false );  
}
</script>
<div id="disabledImageZone" style="width:100%;height:32px;position:absolute; 
	z-index: 1000;display:none; top:0px;left: 0px;text-align: right;margin-right:10px;margin-top:5px;">
<img src="${ctx}/images/loading.gif" border="0" style="padding-right:5px;">
<span style="padding-right:5px;font:normal;color:#6593cf;"><fmt:message key="global.loading"/></span>
</div>
