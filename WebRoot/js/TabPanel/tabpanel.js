
function switchTab(selfTab,tabCount){ 
    for(var i=1;i< (tabCount + 1);i++){
        var x = document.getElementById('SystopTab' + i);   
        if(x != null) 
        	x.className = "mousehand";
    }
    selfTab.className = "SelectedTab";

	for(var i=1; i<(tabCount + 1); i++){
		 var divpanel = document.getElementById('SystopTabDiv' + i);
		 if(divpanel != null)    
         	divpanel.style.display = "none";
	}

    document.getElementById("SystopTabDiv" + selfTab.id.substring(9,10)).style.display = "";
}