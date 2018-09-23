if(!dojo._hasResource["jsam.common"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
	dojo._hasResource["jsam.common"] = true;
	dojo.provide("jsam.common");
	
	jsam.html = function(){};
	
	jsam.html.getParentByType = function(/* HTMLElement */node, /* string */type) {
		//	summary
		//	Returns the first ancestor of node with tagName type.
		
		var _document = dojo.doc;
		var parent = dojo.byId(node);
		type = type.toLowerCase();
		while((parent)&&(parent.nodeName.toLowerCase()!=type)){
			if(parent==(_document["body"]||_document["documentElement"])){
				return null;
			}
			parent = parent.parentNode;
		}
		return parent;	//	HTMLElement
	};
	
	jsam.html.getAttribute = function(/* HTMLElement */node, /* string */attr){
		//	summary
		//	Returns the value of attribute attr from node.
		
		node = dojo.byId(node);
		// FIXME: need to add support for attr-specific accessors
		if((!node)||(!node.getAttribute)){
			// if(attr !== 'nwType'){
			//	alert("getAttr of '" + attr + "' with bad node"); 
			// }
			return null;
		}
		var ta = typeof attr == 'string' ? attr : new String(attr);
	
		// first try the approach most likely to succeed
		var v = node.getAttribute(ta.toUpperCase());
		if((v)&&(typeof v == 'string')&&(v!="")){ 
			return v;	//	string 
		}
	
		// try returning the attributes value, if we couldn't get it as a string
		if(v && v.value){ 
			return v.value;	//	string 
		}
	
		// this should work on Opera 7, but it's a little on the crashy side
		if((node.getAttributeNode)&&(node.getAttributeNode(ta))){
			return (node.getAttributeNode(ta)).value;	//	string
		}else if(node.getAttribute(ta)){
			return node.getAttribute(ta);	//	string
		}else if(node.getAttribute(ta.toLowerCase())){
			return node.getAttribute(ta.toLowerCase());	//	string
		}
		return null;	//	string
	};
		
	jsam.html.hasAttribute = function(/* HTMLElement */node, /* string */attr){
		//	summary
		//	Determines whether or not the specified node carries a value for the attribute in question.
		return jsam.html.getAttribute(dojo.byId(node), attr) ? true : false;	//	boolean
	};
		
	jsam.html.getCursorPosition = function(/* DOMEvent */e){
		//	summary
		//	Returns the mouse position relative to the document (not the viewport).
		//	For example, if you have a document that is 10000px tall,
		//	but your browser window is only 100px tall,
		//	if you scroll to the bottom of the document and call this function it
		//	will return {x: 0, y: 10000}
		//	NOTE: for events delivered via dojo.event.connect() and/or dojoAttachEvent (for widgets),
		//	you can just access evt.pageX and evt.pageY, rather than calling this function.
		e = e || dojo.global().event;
		var cursor = {x:0, y:0};
		if(e.pageX || e.pageY){
			cursor.x = e.pageX;
			cursor.y = e.pageY;
		}else{
			var de = dojo.doc.documentElement;
			var db = dojo.body();
			cursor.x = e.clientX + ((de||db)["scrollLeft"]) - ((de||db)["clientLeft"]);
			cursor.y = e.clientY + ((de||db)["scrollTop"]) - ((de||db)["clientTop"]);
		}
		return cursor;	//	object
	};
	
	jsam.html.isTag = function(/* HTMLElement */node) {
		//	summary
		//	Like dojo.dom.isTag, except case-insensitive
		node = dojo.byId(node);
		if(node && node.tagName) {
			for (var i=1; i<arguments.length; i++){
				if (node.tagName.toLowerCase()==String(arguments[i]).toLowerCase()){
					return String(arguments[i]).toLowerCase();	//	string
				}
			}
		}
		return "";	//	string
	};
	
	jsam.html.renderedTextContent = function (node) {
	node = dojo.byId(node);
	var result = "";
	if (node == null) {
		return result;
	}
	for (var i = 0; i < node.childNodes.length; i++) {
		switch (node.childNodes[i].nodeType) {
		  case 1:
		  case 5:
			var display = "unknown";
			try {
				display = dojo.style(node.childNodes[i], "display");
			}
			catch (E) {
			}
			switch (display) {
			  case "block":
			  case "list-item":
			  case "run-in":
			  case "table":
			  case "table-row-group":
			  case "table-header-group":
			  case "table-footer-group":
			  case "table-row":
			  case "table-column-group":
			  case "table-column":
			  case "table-cell":
			  case "table-caption":
				result += "\n";
				result += jsam.html.renderedTextContent(node.childNodes[i]);
				result += "\n";
				break;
			  case "none":
				break;
			  default:
				if (node.childNodes[i].tagName && node.childNodes[i].tagName.toLowerCase() == "br") {
					result += "\n";
				} else {
					result += jsam.html.renderedTextContent(node.childNodes[i]);
				}
				break;
			}
			break;
		  case 3:
		  case 2:
		  case 4:
			var text = node.childNodes[i].nodeValue;
			var textTransform = "unknown";
			try {
				textTransform = dojo.style(node, "text-transform");
			}
			catch (E) {
			}
			switch (textTransform) {
			  case "capitalize":
				var words = text.split(" ");
				for (var i = 0; i < words.length; i++) {
					words[i] = words[i].charAt(0).toUpperCase() + words[i].substring(1);
				}
				text = words.join(" ");
				break;
			  case "uppercase":
				text = text.toUpperCase();
				break;
			  case "lowercase":
				text = text.toLowerCase();
				break;
			  default:
				break;
			}
			switch (textTransform) {
			  case "nowrap":
				break;
			  case "pre-wrap":
				break;
			  case "pre-line":
				break;
			  case "pre":
				break;
			  default:
				text = text.replace(/\s+/, " ");
				if (/\s$/.test(result)) {
					text.replace(/^\s/, "");
				}
				break;
			}
			result += text;
			break;
		  default:
			break;
		}
	}
	return result;
  };
  
  jsam.html.setClass = function(/* HTMLElement */node, /* string */classStr){
	//	summary
	//	Clobbers the existing list of classes for the node, replacing it with
	//	the list given in the 2nd argument. Returns true or false
	//	indicating success or failure.
	node = dojo.byId(node);
	var cs = new String(classStr);
	try{
		if(typeof node.className == "string"){
			node.className = cs;
		}else if(node.setAttribute){
			node.setAttribute("class", classStr);
			node.className = cs;
		}else{
			return false;
		}
	}catch(e){
		dojo.debug("dojo.html.setClass() failed", e);
	}
	return true;
  };
  jsam.html.getClasses = function(/* HTMLElement */node) {
	//	summary
	//	Returns an array of CSS classes currently assigned directly to the node in question. 
	//	Returns an empty array if no classes are found;
	var c = dojo.html.getClass(node);
	return (c == "") ? [] : c.split(/\s+/g);	//	array
  };
  jsam.html.removeClass = function(/* HTMLElement */node, /* string */classStr, /* boolean? */allowPartialMatches){
	//	summary
	//	Removes the className from the node;. Returns true or false indicating success or failure.
	try{
		if (!allowPartialMatches) {
			var newcs = jsam.html.getClass(node).replace(new RegExp('(^|\\s+)'+classStr+'(\\s+|$)'), "$1$2");
		} else {
			var newcs = jsam.html.getClass(node).replace(classStr,'');
		}
		jsam.html.setClass(node, newcs);
	}catch(e){
		console.log("dojo.html.removeClass() failed", e);
	}
	return true;	//	boolean
  };
  
  jsam.html.getEventTarget = function(e) {
  	  return dojo.isIE ? event.srcElement : e.target;
  };
  
  jsam.html.clientSize = function(w) {
    var win=w||window;
    var d = win.document;
    if(d.documentElement&& !(d.documentElement.scrollTop==undefined||d.documentElement.scrollTop==0)){
        return{'width':win.document.documentElement.clientWidth,'height':win.document.documentElement.clientHeight};
    }
    else if(win.document.body){
        return{'width':win.document.body.clientWidth,'height':win.document.body.clientHeight};
    }
  };
  
  jsam.html.scrollPos = function(w) {
    var win=w||window;
    var d = win.document;
    if(d.documentElement&& !(d.documentElement.scrollTop==undefined||d.documentElement.scrollTop==0)){
        return{'top':win.document.documentElement.scrollTop,'left':win.document.documentElement.scrollLeft};
    }
    else if(win.document.body){
        return{'top':win.document.body.scrollTop,'left':win.document.body.scrollLeft};
    }
  };
  
  jsam.html.getAbsM = function(topWin){
    if(topWin==null){
        topWin=top;
    }
    var leftM=0;
    var topM=0;
    var tempWin=window;
    while(tempWin!=topWin){
        var ifs=tempWin.parent.document.getElementsByTagName('iframe');
        for(var i=0;i<ifs.length;i++){
            try{
                if(ifs[i].contentWindow==tempWin.window){
                    var rc=jsam.html.getBoundingClientRect(ifs[i]);
                    leftM+=rc.left;
                    topM+=rc.top;
                    break;
                }
            }catch(e){
                continue;
            }
        }
        tempWin=tempWin.parent;
    }
    return{'leftM':leftM,'topM':topM};
};

jsam.html.getBoundingClientRect = function(obj){
    if(!dojo.isIE/*navigator.product=='Gecko'*/){
        var objWin=null;
        var top=obj.offsetTop;
        var left=obj.offsetLeft;
        var right=obj.offsetWidth;
        var bottom=obj.offsetHeight;
        while(obj=obj.offsetParent){
            top+=obj.offsetTop;
            left+=obj.offsetLeft;
            if(obj.tagName.toLowerCase()=="body"){
                objWin=obj.ownerDocument.defaultView;
            }
        }
        var theScroll=jsam.html.scrollPos(objWin);
        left-=theScroll.left;
        top-=theScroll.top;
        right+=left;
        bottom+=top;
        return{'left':left,'top':top,'right':right,'bottom':bottom};
    }
    else{
        return obj.getBoundingClientRect();
    }
 };
}