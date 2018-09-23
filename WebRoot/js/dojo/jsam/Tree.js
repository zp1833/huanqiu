/**
 * jsam tree widget.
 */
if(!dojo._hasResource["jsam.Tree"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
	dojo._hasResource["jsam.Tree"] = true;
	dojo.provide("jsam.TreeAbstractNode");
	dojo.provide("jsam.Tree");
	dojo.provide("jsam.TreeBase");	
	dojo.provide("jsam.TreeItem");
    dojo.provide("jsam.TreeInput");
    
    dojo.require("dojo.fx");
	dojo.require("jsam.Cookie");
	dojo.require("jsam.common");
    dojo.require("jsam.ArrayList");
	dojo.require("dijit._Widget");
	
	jsam.treeConfig = {
		rootIcon        : dojo.moduleUrl("jsam.resources", 'images/tree/foldericon.png'),
		openRootIcon    : dojo.moduleUrl("jsam.resources", 'images/tree/openfoldericon.png'),
		folderIcon      : dojo.moduleUrl("jsam.resources", 'images/tree/foldericon.png'),
		openFolderIcon  : dojo.moduleUrl("jsam.resources", 'images/tree/openfoldericon.png'),
		fileIcon        : dojo.moduleUrl("jsam.resources", 'images/tree/file.png'),
		iIcon           : dojo.moduleUrl("jsam.resources", 'images/tree/I.png'),
		lIcon           : dojo.moduleUrl("jsam.resources", 'images/tree/L.png'),
		lMinusIcon      : dojo.moduleUrl("jsam.resources", 'images/tree/Lminus.png'),
		lPlusIcon       : dojo.moduleUrl("jsam.resources", 'images/tree/Lplus.png'),
		tIcon           : dojo.moduleUrl("jsam.resources", 'images/tree/T.png'),
		tMinusIcon      : dojo.moduleUrl("jsam.resources", 'images/tree/Tminus.png'),
		tPlusIcon       : dojo.moduleUrl("jsam.resources", 'images/tree/Tplus.png'),
		blankIcon       : dojo.moduleUrl("jsam.resources", 'images/tree/blank.png'),
		defaultText     : 'Tree Item',
		defaultAction   : 'javascript:void(0);',
		defaultBehavior : 'classic',
		usePersistence	: true
	};
	jsam.treeHandler = {
		idCounter : 0,
		idPrefix  : "jsam-tree-object-",
		all       : {},
		behavior  : null,
		selected  : null,
        checked   : new jsam.ArrayList(),
		onSelect  : null, /* should be part of tree, not handler */
		getId     : function() { return this.idPrefix + this.idCounter++; },
		toggle    : function (oItem) { this.all[this.parseId(oItem.id)].toggle(); },
		select    : function (oItem) { this.all[this.parseId(oItem.id)].select(); },
		focus     : function (oItem) { this.all[this.parseId(oItem.id)].focus(); },
		blur      : function (oItem) { this.all[this.parseId(oItem.id)].blur(); },
		keydown   : function (oItem, e) { return this.all[this.parseId(oItem.id)].keydown(e.keyCode); },
		cookies   : new jsam.Cookie(),
		parseId   : function(identifer) { //prevent from event bubbling up
			if(identifer) {
				if(identifer.indexOf('-plus') > 0)   return identifer.replace('-plus','');
				if(identifer.indexOf('-icon') > 0)   return identifer.replace('-icon',  '');
				if(identifer.indexOf('-anchor') > 0) return identifer.replace('-anchor','');
                if(identifer.indexOf('-checkbox') > 0) return identifer.replace('-checkbox','');
				return identifer;
			} else return this.getId();// no use,identifer is always exists.
		},
        
        onItemCheck: function(checkboxId, isChecked) {
          var item = this.all[this.parseId(checkboxId)];
          if(isChecked) {
              var exists = false;
              var items = this.checked.getItems();
              for(i = 0; i < items.length; i++) {
                  if(items[i].value) {
                      if(dojo.isFunction(items[i].value.equals)) {
                         if(items[i].value.equals(item.value)) {
                             exists = true;
                             break;
                         }
                      } else if(items[i].value.id) {
                         if(items[i].value.id == item.value.id) {
                             exists = true;
                             break;
                         }
                      }
                  }
              }
              console.debug("Add item " + item.value['text']);
              if(!exists)  this.checked.add(item);
          } else {
              console.debug("Remove item " + item.value['text']);
              this.checked.remove(item);
          }
          if(!item || !item.childNodes) return;
          for(var i = 0; i < item.childNodes.length; i++) {
              dojo.byId(item.childNodes[i].id + "-checkbox").checked = isChecked;
              this.onItemCheck(item.childNodes[i].id + "-checkbox", isChecked);
          }
        }
	};
	/**
	 * The structure of the object that binds with tree item.
	 * User can redefine the fields value for customize structure.
	 * eg.
	 * var tree = dijit.byId("tree1");
	 * tree.render({text:'root', onclick: function(v){}});
	 */
	jsam.treeStructure = {
        id:           'id',
		text :        'text', // tree item's label
		onclick :     'onclick', // a function to be executed when click item
		childNodes :  'childNodes',// array of child items
		href:         'href', //the url for item linking
		ondblclick  : 'ondblclick'// a function to be executed when double click item
	};
	
	
	/**
	 * jsam.TreeAbstractNode class
	 */
	
	jsam.TreeAbstractNode = function (oValue) {
		this.childNodes  = [];
		this.id     = jsam.treeHandler.getId();
		this.value = oValue || {text : jsam.treeConfig.defaultText,
		   onclick : jsam.treeConfig.defaultAction};
        this.each(this.value);
		this._last  = false;
		jsam.treeHandler.all[this.id] = this;
	};
    
    jsam.TreeAbstractNode.prototype.useCheckbox = false;
    
    jsam.TreeAbstractNode.prototype.each = function(nodeValue) {
        return nodeValue;
    };
	
	/**
	 * To speed thing up if you're adding multiple nodes at once (after load)
	 * use the bNoIdent parameter to prevent automatic re-indentation and call
	 * the obj.ident() method manually once all nodes has been added.
	 */
	
	jsam.TreeAbstractNode.prototype.add = function (node, bNoIdent) {
		node.parentNode = this;
		this.childNodes[this.childNodes.length] = node;
		var root = this;
		if (this.childNodes.length >= 2) {
			this.childNodes[this.childNodes.length - 2]._last = false;
		}
		while (root.parentNode) { root = root.parentNode; }
		if (root.rendered) {
			if (this.childNodes.length >= 2) {
				dojo.byId(this.childNodes[this.childNodes.length - 2].id + '-plus').src = ((this.childNodes[this.childNodes.length -2].folder)?((this.childNodes[this.childNodes.length -2].open)?jsam.treeConfig.tMinusIcon:jsam.treeConfig.tPlusIcon):jsam.treeConfig.tIcon);
				this.childNodes[this.childNodes.length - 2].plusIcon = jsam.treeConfig.tPlusIcon;
				this.childNodes[this.childNodes.length - 2].minusIcon = jsam.treeConfig.tMinusIcon;
				this.childNodes[this.childNodes.length - 2]._last = false;
			}
			this._last = true;
			var foo = this;
			while (foo.parentNode) {
				for (var i = 0; i < foo.parentNode.childNodes.length; i++) {
					if (foo.id == foo.parentNode.childNodes[i].id) { break; }
				}
				if (i == foo.parentNode.childNodes.length - 1) { foo.parentNode._last = true; }
				else { foo.parentNode._last = false; }
				foo = foo.parentNode;
			}
			dojo.place(node.build(), dojo.byId(this.id + '-cont'), "last");			
			if ((!this.folder) && (!this.openIcon)) {
				this.icon = jsam.treeConfig.folderIcon;
				this.openIcon = jsam.treeConfig.openFolderIcon;
			}
			if (!this.folder) { this.folder = true; this.collapse(true); }
			if (!bNoIdent) { this.indent(); }
		}
		return node;
	};
	
	jsam.TreeAbstractNode.prototype.toggle = function() {
		if (this.folder) {
			if (this.open) { this.collapse(); }
			else { this.expand(); }
	}	};
	
	jsam.TreeAbstractNode.prototype.select = function() {
		dojo.byId(this.id + '-anchor').focus();
	};
	
	jsam.TreeAbstractNode.prototype.deSelect = function() {
		dojo.byId(this.id + '-anchor').className = '';
		jsam.treeHandler.selected = null;
	};
    
    jsam.TreeAbstractNode.prototype.uncheckedAll = function() {
        dojo.byId(this.id + '-checkbox').checked = false;
        jsam.treeHandler.checked.remove(this);
        for(var i = 0; i < this.childNodes.length; i++) {
            this.childNodes[i].uncheckedAll();
        }
    };
	
	jsam.TreeAbstractNode.prototype.focus = function() {
		if ((jsam.treeHandler.selected) && (jsam.treeHandler.selected != this)) { jsam.treeHandler.selected.deSelect(); }
		jsam.treeHandler.selected = this;
		if ((this.openIcon) && (jsam.treeHandler.behavior != 'classic')) { dojo.byId(this.id + '-icon').src = this.openIcon; }
		dojo.byId(this.id + '-anchor').className = 'selected';
		dojo.byId(this.id + '-anchor').focus();
		if (jsam.treeHandler.onSelect) { jsam.treeHandler.onSelect(this); }
	};
	
	jsam.TreeAbstractNode.prototype.blur = function() {
		if ((this.openIcon) && (jsam.treeHandler.behavior != 'classic')) { dojo.byId(this.id + '-icon').src = this.icon; }
		dojo.byId(this.id + '-anchor').className = 'selected-inactive';
	};
	
	jsam.TreeAbstractNode.prototype.doExpand = function() {
		if (jsam.treeHandler.behavior == 'classic') { dojo.byId(this.id + '-icon').src = this.openIcon; }
		if (this.childNodes.length) {  dojo.byId(this.id + '-cont').style.display = 'block'; }
		this.open = true;
		if (jsam.treeConfig.usePersistence) {
			jsam.treeHandler.cookies.setCookie(this._cookieKey(), '1');
	}	};
	
	jsam.TreeAbstractNode.prototype.doCollapse = function() {
		if (jsam.treeHandler.behavior == 'classic') { dojo.byId(this.id + '-icon').src = this.icon; }
		if (this.childNodes.length) { dojo.byId(this.id + '-cont').style.display = 'none'; }
		this.open = false;
		if (jsam.treeConfig.usePersistence) {
			jsam.treeHandler.cookies.setCookie(this._cookieKey(), '0');
	}	};
	
	jsam.TreeAbstractNode.prototype.expandAll = function() {
		this.expandChildren();
		if ((this.folder) && (!this.open)) { this.expand(); }
	};
	
	jsam.TreeAbstractNode.prototype.expandChildren = function() {
		for (var i = 0; i < this.childNodes.length; i++) {
			this.childNodes[i].expandAll();
	} };
	
	jsam.TreeAbstractNode.prototype.collapseAll = function() {
		this.collapseChildren();
		if ((this.folder) && (this.open)) { this.collapse(true); }
	};
	
	jsam.TreeAbstractNode.prototype.collapseChildren = function() {
		for (var i = 0; i < this.childNodes.length; i++) {
			this.childNodes[i].collapseAll();
	} };
	jsam.TreeAbstractNode.prototype._cookieKey = function() {
		return this.id.substr(jsam.treeHandler.idPrefix.length,this.id.length - jsam.treeHandler.idPrefix.length);
	};
	
	jsam.TreeAbstractNode.prototype._isRoot = function() {
		return (this.parentNode) ? false : true;
	};
	
	jsam.TreeAbstractNode.prototype._setParentLast = function(bLast) {
		if(this._isRoot()) return;
		this.parentNode._last = bLast; 
	};
	
	jsam.TreeAbstractNode.prototype._getParentLast = function() {
		if(this._isRoot()) return false;
		return this.parentNode._last;
	};
    
    jsam.TreeAbstractNode.prototype.doItemClick = null;
    jsam.TreeAbstractNode.prototype.doItemDblClick = null;
	/**
	 * Build the dom for a item by the item value, includes child items
	 * @param {Object} nItem - index of the item in the branch 
	 * @param {Object} nItemCount - all items count of the branch
	 */
	jsam.TreeAbstractNode.prototype._build = function(nItem, nItemCount) {
		var foo = this.parentNode;
		if (!this._isRoot() && (nItem + 1 == nItemCount)) { this._setParentLast(true);}
		var i = 0;
		var space = document.createElement('span');
		if(!this._isRoot()) {			
			while (foo.parentNode) {
				foo = foo.parentNode;
				indent = document.createElement('img');
				indent.setAttribute("id", this.id + "-indent-" + i);
				indent.setAttribute("src", ((foo._last)?jsam.treeConfig.blankIcon:jsam.treeConfig.iIcon));
				dojo.place(indent, space, "first");
				i++;
			}
		}
		
		this._level = i;
		if (this.childNodes.length) { this.folder = 1; }
		else { this.open = false; }
		if ((this.folder) || (jsam.treeHandler.behavior != 'classic')) {
			if (!this.icon) { this.icon = jsam.treeConfig.folderIcon; }
			if (!this.openIcon) { this.openIcon = jsam.treeConfig.openFolderIcon; }
		}
		else if (!this.icon) { this.icon = jsam.treeConfig.fileIcon; }
		
		this.plusIcon = ((this._getParentLast())?jsam.treeConfig.lPlusIcon:jsam.treeConfig.tPlusIcon);
		this.minusIcon = ((this._getParentLast())?jsam.treeConfig.lMinusIcon:jsam.treeConfig.tMinusIcon);
		
		
		var div = document.createElement("div");
		div.setAttribute("id", this.id);
		dojo.connect(div, "ondblclick", this, function(e) {
			jsam.treeHandler.toggle(jsam.html.getEventTarget(e));
		});
		dojo.connect(div, "onkeydown", this, function(e) {
			jsam.treeHandler.keydown(jsam.html.getEventTarget(e), dojo.isIE ? event : e);
		});
		jsam.html.setClass(div, "jsam-tree-item");
		div.appendChild(space);
        
		if(!this._isRoot()) {
			var imgPlus = document.createElement('img');
			imgPlus.setAttribute("id", this.id + "-plus");		
			imgPlus.setAttribute("src", ((this.folder)?
			    ((this.open)?
				    ((this._getParentLast())?jsam.treeConfig.lMinusIcon:jsam.treeConfig.tMinusIcon):
					((this._getParentLast())?jsam.treeConfig.lPlusIcon:jsam.treeConfig.tPlusIcon)):
				((this._getParentLast())?jsam.treeConfig.lIcon:jsam.treeConfig.tIcon)));
			
			dojo.connect(imgPlus, "onclick", this, function(e) {
				jsam.treeHandler.toggle(jsam.html.getEventTarget(e));
			});
			div.appendChild(imgPlus);
		}
        if(this.useCheckbox) {
            var cb = document.createElement("input");
            cb.setAttribute("type", "checkbox");
            cb.style.border = "0";
            if(dojo.isIE) {
              cb.style.height = "16px";
              cb.style.width = "16px";
            } else {
              cb.style.padding = "0px";
              cb.style.margin = "0px";
            }
            cb.setAttribute("id", this.id + "-checkbox");
            
            dojo.connect(cb, "onclick", this, function(e) {
                 var target = jsam.html.getEventTarget(e);
                 if(!target) return;
                 jsam.treeHandler.onItemCheck(target.id, target.checked);
            });
            div.appendChild(cb);
        }
		imgIcon = document.createElement('img');
		imgIcon.setAttribute("id", this.id + "-icon");	
		jsam.html.setClass(imgIcon, "jsam-tree-icon");
		imgIcon.setAttribute("src", ((jsam.treeHandler.behavior == 'classic' && this.open)?this.openIcon:this.icon));
		dojo.connect(imgIcon, "onclick", this, function(e) {
			jsam.treeHandler.select(jsam.html.getEventTarget(e));
		});
		div.appendChild(imgIcon);
		var a = document.createElement('a');
		if(this.value) {
			a.setAttribute("value", this.value);
		}
		a.setAttribute("id", this.id + "-anchor");
		dojo.connect(a, "onfocus", this, function(e) {
			jsam.treeHandler.focus(jsam.html.getEventTarget(e));
		});
		dojo.connect(a, "onblur", this, function(e) {
			jsam.treeHandler.blur(jsam.html.getEventTarget(e));
		});
        a.style.cursor = "pointer";
		if(this.value[jsam.treeStructure.href]) {			
			var href = this.value[jsam.treeStructure.href].replace(/</g, '&lt;').replace(/>/g, '&gt;');
			a.setAttribute("href", href);
		} else {
			a.setAttribute("href", "#");
		}
        //Item click event
        if(this.doItemClick) {           
           dojo.connect(a, "onclick", this, function(e) {
                this.doItemClick(this.value, e);
           });
        }
		if(dojo.isFunction(this.value[jsam.treeStructure.onclick])) {
			
			dojo.connect(a, "onclick", this, function(e) {
				this.value[jsam.treeStructure.onclick](this.value);
		   });
		}
        //Item bouble click event
        if(this.doItemDblClick) {
            dojo.connect(a, "ondblclick", this, function(e) {
                this.doItemDblClick(this.value, e);
           });
        }
		if(dojo.isFunction(this.value[jsam.treeStructure.ondblclick])) {			
			dojo.connect(a, "ondblclick", this, function(e) {
				this.value[jsam.treeStructure.ondblclick](this.value);
		   });
		}
		if(typeof this.value[jsam.treeStructure.text] == 'object') {
			a.appendChild(this.value[jsam.treeStructure.text]);
		} else {
			var label = this.value[jsam.treeStructure.text].replace(/</g, '&lt;').replace(/>/g, '&gt;');
			a.innerHTML = label;
		}
		div.appendChild(a);
		
		var contDiv = document.createElement('div');
		contDiv.setAttribute("id", this.id + "-cont");
		jsam.html.setClass(contDiv, 'jsam-tree-container');
		contDiv.style.display = ((this.open)?'block':'none');
		this.rendered = true;
		
		for (var i = 0; i < this.childNodes.length; i++) {
			contDiv.appendChild(this.childNodes[i].build(i,this.childNodes.length));
		}
		var container = document.createElement('div');
		container.appendChild(div);
		container.appendChild(contDiv);
		return container;
	};
    
    
	jsam.TreeAbstractNode.prototype.indent = function(lvl, del, last, level, nodesLeft) {
		/**
		 * Since we only want to modify items one level below ourself,
		 * and since the rightmost indentation position is occupied by
		 * the plus icon we set this to -2
		 */
		if (lvl == null) { lvl = -2; }
		var state = 0;
		for (var i = this.childNodes.length - 1; i >= 0 ; i--) {
			state = this.childNodes[i].indent(lvl + 1, del, last, level);
			if (state) { return; }
		}
		if (del) {
			if ((level >= this._level) && (dojo.byId(this.id + '-plus'))) {
				if (this.folder) {
					dojo.byId(this.id + '-plus').src = (this.open)?jsam.treeConfig.lMinusIcon:jsam.treeConfig.lPlusIcon;
					this.plusIcon = jsam.treeConfig.lPlusIcon;
					this.minusIcon = jsam.treeConfig.lMinusIcon;
				}
				else if (nodesLeft) { dojo.byId(this.id + '-plus').src = jsam.treeConfig.lIcon; }
				return 1;
		}	}
		var foo = dojo.byId(this.id + '-indent-' + lvl);
		if (foo) {
			if ((foo._last) || ((del) && (last))) { foo.src =  jsam.treeConfig.blankIcon; }
			else { foo.src =  jsam.treeConfig.iIcon; }
		}
		return 0;
	};
	
	
	
	/**
	 * jsam.TreeBase class
	 */
	
	jsam.TreeBase = function (oValue, sBehavior, sIcon, sOpenIcon) {
		this.base = jsam.TreeAbstractNode;
		this.base(oValue);
		this.icon      = sIcon || jsam.treeConfig.rootIcon;
		this.openIcon  = sOpenIcon || jsam.treeConfig.openRootIcon;
		/* Defaults to open */
		if (jsam.treeConfig.usePersistence) {
			this.open  = (jsam.treeHandler.cookies.getCookie(this._cookieKey()) == '0')?false:true;
		} else { this.open  = true; }
		this.folder    = true;
		this.rendered  = false;
		this.onSelect  = null;
		if (!jsam.treeHandler.behavior) {  jsam.treeHandler.behavior = sBehavior || jsam.treeConfig.defaultBehavior; }
	};
	
	jsam.TreeBase.prototype = new jsam.TreeAbstractNode;
	
	jsam.TreeBase.prototype.setBehavior = function (sBehavior) {
		jsam.treeHandler.behavior =  sBehavior;
	};
	
	jsam.TreeBase.prototype.getBehavior = function (sBehavior) {
		return jsam.treeHandler.behavior;
	};
	
	jsam.TreeBase.prototype.getSelected = function() {
		if (jsam.treeHandler.selected) { return jsam.treeHandler.selected; }
		else { return null; }
	};
	
	jsam.TreeBase.prototype.remove = function() { };
	
	jsam.TreeBase.prototype.expand = function() {
		this.doExpand();
	};
	
	jsam.TreeBase.prototype.collapse = function(b) {
		if (!b) { this.focus(); }
		this.doCollapse();
	};
	
	jsam.TreeBase.prototype.getFirst = function() {
		return null;
	};
	
	jsam.TreeBase.prototype.getLast = function() {
		return null;
	};
	
	jsam.TreeBase.prototype.getNextSibling = function() {
		return null;
	};
	
	jsam.TreeBase.prototype.getPreviousSibling = function() {
		return null;
	};
	
	jsam.TreeBase.prototype.keydown = function(key) {
		if (key == 39) {
			if (!this.open) { this.expand(); }
			else if (this.childNodes.length) { this.childNodes[0].select(); }
			return false;
		}
		if (key == 37) { this.collapse(); return false; }
		if ((key == 40) && (this.open) && (this.childNodes.length)) { this.childNodes[0].select(); return false; }
		return true;
	};
	
	jsam.TreeBase.prototype.build = function() {
		return this._build(0, 0);
	};
	
	/**
	 * jsam.TreeItem class
	 */
	
	jsam.TreeItem = function (oValue, eParent, sIcon, sOpenIcon) {
		this.base = jsam.TreeAbstractNode;
		this.base(oValue);
		/* Defaults to close */
		if (jsam.treeConfig.usePersistence) {
			this.open = (jsam.treeHandler.cookies.getCookie(this._cookieKey()) == '1')?true:false;
		} else { this.open = false; }
		if (sIcon) { this.icon = sIcon; }
		if (sOpenIcon) { this.openIcon = sOpenIcon; }
		if (eParent) { eParent.add(this); }
	};
	
	jsam.TreeItem.prototype = new jsam.TreeAbstractNode;
	
	jsam.TreeItem.prototype.remove = function() {
		var iconSrc = dojo.byId(this.id + '-plus').src;
		var parentNode = this.parentNode;
		var prevSibling = this.getPreviousSibling(true);
		var nextSibling = this.getNextSibling(true);
		var folder = this.parentNode.folder;
		var last = ((nextSibling) && (nextSibling.parentNode) && (nextSibling.parentNode.id == parentNode.id))?false:true;
		this.getPreviousSibling().focus();
		this._remove();
		if (parentNode.childNodes.length == 0) {
			dojo.byId(parentNode.id + '-cont').style.display = 'none';
			parentNode.doCollapse();
			parentNode.folder = false;
			parentNode.open = false;
		}
		if (!nextSibling || last) { parentNode.indent(null, true, last, this._level, parentNode.childNodes.length); }
		if ((prevSibling == parentNode) && !(parentNode.childNodes.length)) {
			prevSibling.folder = false;
			prevSibling.open = false;
			iconSrc = dojo.byId(prevSibling.id + '-plus').src;
			iconSrc = iconSrc.replace('minus', '').replace('plus', '');
			dojo.byId(prevSibling.id + '-plus').src = iconSrc;
			dojo.byId(prevSibling.id + '-icon').src = jsam.treeConfig.fileIcon;
		}
		if (dojo.byId(prevSibling.id + '-plus')) {
			if (parentNode == prevSibling.parentNode) {
				iconSrc = iconSrc.replace('minus', '').replace('plus', '');
				dojo.byId(prevSibling.id + '-plus').src = iconSrc;
	}	}	};
	
	jsam.TreeItem.prototype._remove = function() {
		for (var i = this.childNodes.length - 1; i >= 0; i--) {
			this.childNodes[i]._remove();
	 	}
		for (var i = 0; i < this.parentNode.childNodes.length; i++) {
			if (this == this.parentNode.childNodes[i]) {
				for (var j = i; j < this.parentNode.childNodes.length; j++) {
					this.parentNode.childNodes[j] = this.parentNode.childNodes[j+1];
				}
				this.parentNode.childNodes.length -= 1;
				if (i + 1 == this.parentNode.childNodes.length) { this.parentNode._last = true; }
				break;
		}	}
		jsam.treeHandler.all[this.id] = null;
		var tmp = dojo.byId(this.id);
		if (tmp) { tmp.parentNode.removeChild(tmp); }
		tmp = dojo.byId(this.id + '-cont');
		if (tmp) { tmp.parentNode.removeChild(tmp); }
	};
	
	jsam.TreeItem.prototype.expand = function() {
		this.doExpand();
		dojo.byId(this.id + '-plus').src = this.minusIcon;
	};
	
	jsam.TreeItem.prototype.collapse = function(b) {
		if (!b) { this.focus(); }
		this.doCollapse();
		dojo.byId(this.id + '-plus').src = this.plusIcon;
	};
	
	jsam.TreeItem.prototype.getFirst = function() {
		return this.childNodes[0];
	};
	
	jsam.TreeItem.prototype.getLast = function() {
		if (this.childNodes[this.childNodes.length - 1].open) { return this.childNodes[this.childNodes.length - 1].getLast(); }
		else { return this.childNodes[this.childNodes.length - 1]; }
	};
	
	jsam.TreeItem.prototype.getNextSibling = function() {
		for (var i = 0; i < this.parentNode.childNodes.length; i++) {
			if (this == this.parentNode.childNodes[i]) { break; }
		}
		if (++i == this.parentNode.childNodes.length) { return this.parentNode.getNextSibling(); }
		else { return this.parentNode.childNodes[i]; }
	};
	
	jsam.TreeItem.prototype.getPreviousSibling = function(b) {
		for (var i = 0; i < this.parentNode.childNodes.length; i++) {
			if (this == this.parentNode.childNodes[i]) { break; }
		}
		if (i == 0) { return this.parentNode; }
		else {
			if ((this.parentNode.childNodes[--i].open) || (b && this.parentNode.childNodes[i].folder)) { return this.parentNode.childNodes[i].getLast(); }
			else { return this.parentNode.childNodes[i]; }
	} };
	
	jsam.TreeItem.prototype.keydown = function(key) {
		if ((key == 39) && (this.folder)) {
			if (!this.open) { this.expand(); }
			else { this.getFirst().select(); }
			return false;
		}
		else if (key == 37) {
			if (this.open) { this.collapse(); }
			else { this.parentNode.select(); }
			return false;
		}
		else if (key == 40) {
			if (this.open) { this.getFirst().select(); }
			else {
				var sib = this.getNextSibling();
				if (sib) { sib.select(); }
			}
			return false;
		}
		else if (key == 38) { this.getPreviousSibling().select(); return false; }
		return true;
	};	
	
    
    
	jsam.TreeItem.prototype.build = function (nItem, nItemCount) {
		return this._build(nItem, nItemCount);
	};	
	
	/**
	 * Dojo tree widget, you can define a tree structure dom, and dojo can render it.
	 * eg.
	  <div id="tree2" text="ROOT" dojoType="jsam.Tree">
		    <div text="cccc"></div>
			<div text="cccc">
			    <div text="bbb"></div>
				<div text="bbb"></div>
			</div>
		</div>
	 */
	dojo.declare(
	"jsam.Tree",
	[dijit._Widget],
	{
        openRoot : false,
        useDefaultEvents: true,
        multiple: false,
        oKText: "",
        cancelText:"",
		treeBase: {}, //jsam.TreeBase
		height: "200px",
        width: "200px",
        
		getTreeBase: function() {
			return this.treeBase;
		},
        
        each: function(nodeValue){},
		
		render : function(treeData) {
			if(!treeData) return;
			this.treeBase = new jsam.TreeBase(treeData);
            this.treeBase.useCheckbox = this.multiple;
			this._buildTree(this.treeBase, treeData);
			if(this.domNode) {
				var children = this.domNode.childNodes;
				for(var i = 0; i < children.length; i++) {
					if(!children[i]) break;
					this.domNode.removeChild(children[i]);
				}
				this.domNode.innerHTML = "";
                var div = document.createElement('div');
                div.style.margin="3px";
                div.style.height=(parseInt(this.height.replace("px", "")) - 32) + "px";
                div.appendChild(this.treeBase.build());
				this.domNode.appendChild(div);
                
                if(this.multiple && this.oKText && this.cancelText) {
                     this.createButton();
                }                
			}
            if(this.openRoot)  {
               if (!this.treeBase.open) {this.treeBase.expand();}
            }
		},
        createButton: function() {
           var div = document.createElement('div');
           div.style.textAlign = "center";
           div.style.height = "32px";
           var ok = document.createElement('input');
           ok.setAttribute("id", this.id + "-ok");
           ok.setAttribute("type", "button");
          
           ok.value = this.oKText;
           dojo.addClass(ok, "samTreeBtn");
           div.appendChild(ok);
           dojo.connect(ok, "onclick", this, function(e) {
               this.onOk(e);
           });
           
           var cancel = document.createElement('input');
           cancel.setAttribute("id", this.id + "-cancel");
           cancel.setAttribute("type", "button");
           cancel.style.marginLeft="5px";
           cancel.value = this.cancelText;
           dojo.addClass(cancel, "samTreeBtn");
           div.appendChild(cancel);
           dojo.connect(cancel, "onclick", this, function(e) {
               this.onCancel(e);
           });
                     
           this.domNode.appendChild(div);
        },
        onOk: function(e) {
        },
        onCancel: function(e) {
        },
        
        getCheckedValues: function() {
           if(!this.multiple) return null;
           var items = jsam.treeHandler.checked.getItems();
           var result = [];
           for(var i = 0; i < items.length; i++) {
              if(items[i].value) result.push(items[i].value);
           }
           
           return result;
        },
		
        onItemClick: function(value, e) {
        },
        
        onItemDblClick: function(value, e) {
        },
        
		_buildTree : function(parentItem, parentData) {
			var children = parentData[jsam.treeStructure.childNodes];
			if(!children) return;
			for(var i = 0; i < children.length; i++) {
				var item = new jsam.TreeItem(children[i]);
                item.useCheckbox = this.multiple;
                if(!this.useDefaultEvents) {
                    item.doItemClick = this.onItemClick;
                    item.doItemDblClick = this.onItemDblClick;
                }
				parentItem.add(item);
				this._buildTree(item, children[i]);
			}
		},
		
		startup:function () {
			if(this._hasChild(this.domNode)) {
				this.render(this.parseData(this.domNode, {}));
			}
		},
		
		_hasChild : function(parentNode) {
			if(!parentNode) return false;
			if(!parentNode.childNodes || parentNode.childNodes.length <= 0) return false;
			if(parentNode.childNodes.length == 1 && !parentNode.childNodes[0].getAttribute) return false;
			return true;
		},
		
        
		parseData: function(parentNode, parentData) {
			if(!parentNode || !parentNode.getAttribute) return null;
			if(!parentData) parentData = {};
			parentData.text = parentNode.getAttribute(jsam.treeStructure.text);
			parentData.onclick = parentNode.getAttribute(jsam.treeStructure.onclick);
			if(!this._hasChild(parentNode)) {
				return parentData;
			}
			var childNodes = parentNode.childNodes;
			var childDatas = [];
			for(var i = 0; i < childNodes.length; i++) {
				var d = this.parseData(childNodes[i], {});
				if(d) childDatas.push(d);
			}
			parentData[jsam.treeStructure.childNodes] = childDatas;
			return parentData;
		}
	});
    
    dojo.declare(
    "jsam.TreeInput",
    [jsam.Tree],
    {
        textNodeId: "",
        valueNodeId: "",
        containerClass:"",
        duration: 300,
        _hide: true,
        
        autoPosition: true,
        postCreate: function() {
           var self = this;
           
           this.domNode.style.display = "none";
           var node = self.domNode;
           this._fadeIn = dojo.fx.combine(
                    [dojo.fadeIn({
                        node: self.domNode,
                        duration: self.duration,
                        onEnd:function(){
                            node.style.display="block";
                        }
                     })]
                );
          this._fadeOut = dojo.fx.combine(
                    [dojo.fadeOut({
                        node: self.domNode,
                        duration: self.duration,
                        onEnd: function(){
                            node.style.display="none";
                        }
                     })]
                );
           
           dojo.connect(dojo.byId(self.textNodeId), 'onclick', self, function(e) {
               if(self.domNode.style.display == "none") {
                  self._hide = true;
               } else {
                   if(this.multiple) self.onCancel(e);
               }
              // this._setPos();
               self.disp(); 
           });
          
           if(this.useDefaultEvents) {
               jsam.TreeAbstractNode.prototype.doItemClick = function(value, e) {
                  var valueNode = dojo.byId(self.valueNodeId);
                  var textNode = dojo.byId(self.textNodeId);
                   
                  textNode.value = value[jsam.treeStructure.text];
                  if(valueNode) valueNode.value = value[jsam.treeStructure.id];
               };
                var fo = this._fadeOut;
                jsam.TreeAbstractNode.prototype.doItemDblClick =  function(value, e) {
                   var valueNode = dojo.byId(self.valueNodeId);
                   var textNode = dojo.byId(self.textNodeId);
                   textNode.value = value[jsam.treeStructure.text];
                   if(valueNode) valueNode.value = value[jsam.treeStructure.id];
                   fo.play();
                   $("ifmid").style.visibility = 'hidden';
                };
            }
           if(self.autoPosition) self._setPos();
           
        },
        
        onOk: function(e) {
            this.doSelected(this.getCheckedValues());
            this._fadeOut.play();
        },
        
        onCancel: function(e) {
            this.treeBase.uncheckedAll();
            this._fadeOut.play();
        },
        
        doSelected: function(values) {
        },
        
        _setPos: function() {
             var top = window;
             var t = dojo.byId(this.textNodeId);
             var contRect = jsam.html.getBoundingClientRect(t);
             var winAbsM = jsam.html.getAbsM(top);
             var currWinSize = jsam.html.clientSize(top);
             var theScroll = jsam.html.scrollPos(top);
             var posTop = winAbsM.topM + contRect.bottom;             
             var posLeft = winAbsM.leftM + contRect.left;
             var contWidth = dojo.coords(t).w;
             var width = Math.max(parseInt(this.domNode.offsetWidth), contWidth);
             console.log("winAbsM.top=" + winAbsM.topM + "  contRect.bottom" + contRect.bottom
                 + "   theScroll.top" + theScroll.top);
             //this.domNode.style.top = (theScroll.top + posTop - height - t.offsetHeight - 3) + 'px';
             this.domNode.style.top=(theScroll.top + posTop + 1) + 'px';
             this.domNode.style.left = -1 + theScroll.left + Math.min(posLeft, currWinSize.width - width - 5) + 'px';
             this.domNode.style.height = this.height;
             var dispWidth = parseInt(this.width.replace("px", ""));
             if(isNaN(dispWidth)) dispWidth = 200;
             this.domNode.style.width = (dispWidth > contWidth) ? this.width : contWidth + 'px';
             this.domNode.style.position = 'absolute';
             this.domNode.style.zIndex = '2000';
        },
        disp: function() {
            (this._hide == true) ? this._fadeIn.play() : this._fadeOut.play();
            this._hide = !this._hide;
        }
    });
}