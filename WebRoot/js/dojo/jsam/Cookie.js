if(!dojo._hasResource["jsam.Cookie"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
	dojo._hasResource["jsam.Cookie"] = true;
	dojo.provide("jsam.Cookie");
	jsam.Cookie = function () {
	    if (document.cookie.length) { this.cookies = ' ' + document.cookie; }
    };

	jsam.Cookie.prototype.setCookie = function (key, value) {
		document.cookie = key + "=" + escape(value);
	};
	
	jsam.Cookie.prototype.getCookie = function (key) {
		if (this.cookies) {
			var start = this.cookies.indexOf(' ' + key + '=');
			if (start == -1) { return null; }
			var end = this.cookies.indexOf(";", start);
			if (end == -1) { end = this.cookies.length; }
			end -= start;
			var cookie = this.cookies.substr(start,end);
			return unescape(cookie.substr(cookie.indexOf('=') + 1, cookie.length - cookie.indexOf('=') + 1));
		}
		else { return null; }
	};
}