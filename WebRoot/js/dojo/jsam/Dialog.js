if(!dojo._hasResource["jsam.Dialog"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["jsam.Dialog"] = true;
dojo.provide("jsam.Dialog");

dojo.require("dojo.dnd.move");
dojo.require("dojo.fx");

dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.require("dijit.Dialog");
dojo.require("dijit.layout.ContentPane");
dojo.require("dijit.form.Form");
dojo.require("dojox.layout.ResizeHandle"); 

dojo.declare(
	"jsam.DialogUnderlay",
	[dijit.DialogUnderlay],
	{
	}
);
	
dojo.declare(
	"jsam.Dialog",
	[dijit.Dialog],
	{
		// summary:
		//		Pops up a modal dialog window, blocking access to the screen
		//		and also graying out the screen Dialog is extended from
		//		ContentPane so it supports all the same parameters (href, etc.)
    // SAM: add resizeHandle node.
		templateString: null,
		templateString:"<div class=\"dijitDialog\">\n\t\t<div dojoAttachPoint=\"titleBar\" class=\"dijitDialogTitleBar\" tabindex=\"0\" waiRole=\"dialog\" title=\"${title}\">\n\t\t<span dojoAttachPoint=\"titleNode\" class=\"dijitDialogTitle\">${title}</span>\n\t\t<span dojoAttachPoint=\"closeButtonNode\" class=\"dijitDialogCloseIcon\" dojoAttachEvent=\"onclick: hide\">\n\t\t\t<span dojoAttachPoint=\"closeText\" class=\"closeText\">x</span>\n\t\t</span>\n\t</div>\n\t\t<div dojoAttachPoint=\"containerNode\" class=\"dijitDialogPaneContent\"></div>\n\t<span dojoAttachPoint=\"resizeHandle\" class=\"dojoxResizeHandle\"></span>\n\t<span dojoAttachPoint=\"tabEnd\" dojoAttachEvent=\"onfocus:_cycleFocus\" tabindex=\"0\"></span>\n</div>\n",
		
		// resizable: Boolean
	  //	allow resizing of pane true/false
	  //SAM:add resizable property
	  resizable: false,

	  // resizeAxis: String
	  //	x | xy | y to limit pane's sizing direction
	  //SAM:add resizeAxis
	  resizeAxis: "xy",
				
		postCreate: function(){
			jsam.Dialog.superclass.postCreate.apply(this, arguments);
			
			//SAM: Display resizeHandle or not
			if(!this.resizable){
			  this.resizeHandle.style.display = "none"; 	
		  }
		},
		//SAM:override super's method, enable resize handle.
		startup: function(){	
			jsam.Dialog.superclass.startup.call(this); 
			
			if (this.resizable) {
				this.containerNode.style.overflow = "auto";
				var tmp = new dojox.layout.ResizeHandle({ 
					//targetContainer: this.containerNode, 
					targetId: this.id, 
					resizeAxis: this.resizeAxis 
				},this.resizeHandle);
			}
		},
		hide: function() {
		   this.onHide();
		   jsam.Dialog.superclass.hide.call(this); 
		},
		onHide : function () {
			
		}
});
}
