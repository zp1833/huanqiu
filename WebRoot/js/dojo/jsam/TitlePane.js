if(!dojo._hasResource["jsam.TitlePane"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
    dojo._hasResource["jsam.TitlePane"] = true;
    dojo.provide("jsam.TitlePane");
    
    dojo.require("dijit._Widget");
    dojo.require("dijit._Templated");
    dojo.require("dijit.TitlePane");
    dojo.require("jsam.Cookie");
    
    	
    dojo.declare(
    	"jsam.TitlePane",
    	[dijit.TitlePane],
    	{
           cookie : "",
           
           toggle: function(){
                var cookies = new jsam.Cookie();
                jsam.TitlePane.superclass.toggle.apply(this, arguments);
                cookies.setCookie(this.cookie, (this.open == true) ? "open" : "close"); 
            },
    		postCreate: function(){
                var cookies = new jsam.Cookie();
                var isOpen = cookies.getCookie(this.cookie);
                this.open = (isOpen == "open" || isOpen == null) ? true : false;
                jsam.TitlePane.superclass.postCreate.apply(this, arguments);                
    		}
    });
}
