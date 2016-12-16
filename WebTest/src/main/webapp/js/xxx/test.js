function f_lzhd_loadJS(url, success) {
	var domScript = document.createElement("script");
	domScript.src = url;
	success = success || function() {};
	domScript.onload = domScript.onreadystatechange = function() {
		if (!this.readyState || "loaded" === this.readyState || "complete" === this.readyState) {
			success();
			this.onload = this.onreadystatechange = null;this.parentNode.removeChild(this)
		}
	};document.getElementsByTagName("head")[0].appendChild(domScript)
}
var lzhd_obj = new Object();
(function() {
	if (!window.jQuery) {
		var url;
		if ("https:" != document.location.protocol) {
			url = "http://tctj.yeepay.com/jquery-1.8.2.min.js"
		} else {
			url = "https://tctj.yeepay.com/jquery-1.8.2.min.js"
		}
		f_lzhd_loadJS(url, f_lzhd_start)
	} else {
		f_lzhd_start()
	}
})();jQuery(window).load(function() {
	var frameLength = window.frames.length;
	for (var i = 0; i < frameLength; i++) {
		try {
			jQuery(window.frames[i].document).delegate("body", "mousedown", function(e) {
				lzhd_obj.mouseX = e.screenX - lzhd_obj.offX;
				lzhd_obj.mouseY = e.screenY - lzhd_obj.offY;f_lzhd_sendhM_ex(lzhd_obj.mouseX, lzhd_obj.mouseY)
			})
		} catch (err) {
			continue
		}
	}
});
function f_lzhd_start() {
	lzhd_obj.mouseX = 0;
	lzhd_obj.mouseY = 0;
	lzhd_obj.offX = 0;
	lzhd_obj.offY = 0;jQuery(document).mousemove(function(e) {
		lzhd_obj.mouseX = e.pageX;
		lzhd_obj.mouseY = e.pageY;
		lzhd_obj.offX = e.screenX - e.pageX;
		lzhd_obj.offY = e.screenY - e.pageY
	});
	lzhd_obj.split = "***";
	lzhd_obj.isNew = 0;
	lzhd_obj.tOt = new Date();
	lzhd_obj.tIt = new Date();
	lzhd_obj.eM = new Object();
	lzhd_obj.lM = new Object();
	lzhd_obj.sM = new Object();
	lzhd_obj.hM = new Object();
	lzhd_obj.cPath = ";path=/";
	if ("https:" != document.location.protocol) {
		lzhd_obj.bPath1 = "http://tctj.yeepay.com/mallsend/collect/"
	} else {
		lzhd_obj.bPath1 = "https://tctj.yeepay.com/mallsend/collect/"
	}
	lzhd_obj.path_en1 = lzhd_obj.bPath1 + "sendEntryMessage";
	lzhd_obj.path_l1 = lzhd_obj.bPath1 + "sendLeaveMessage";
	lzhd_obj.path_s1 = lzhd_obj.bPath1 + "sendSearchMessage";
	lzhd_obj.path_h1 = lzhd_obj.bPath1 + "sendHeatMessage";var string = document.domain;
	var string2 = "";
	if (3 == string.split(".").length) {
		string2 = string.substring(string.split(".")[0].length, string.length)
	} else {
		if (2 == string.split(".").length) {
			string2 = ".".concat(string)
		}
	}
	lzhd_obj.cDomain = ";domain=".concat(string2);var src = jQuery("#LZHD-SCRIPT").attr("src");
	src = src.slice(src.indexOf("?") + 1);
	lzhd_obj.localname = src;
	lzhd_obj.tIt = new Date();var guid = f_lzhd_newGuid();
	lzhd_obj.eM.type = "type=1";
	lzhd_obj.eM.guid = "guid=" + guid;
	lzhd_obj.eM.domain = "domain=" + document.domain;
	lzhd_obj.eM.siteCode = "siteCode=" + lzhd_obj.localname;f_lzhd_getClassId();f_lzhd_getVname();
	lzhd_obj.eM.referrer = "referrer=" + document.referrer;var title = (jQuery.trim(document.title) != "") ? document.title : document.URL;
	lzhd_obj.eM.title = "title=" + title;f_lzhd_sendeM();jQuery(document).mousedown(function(e) {
		var x = e.pageX;
		var y = e.pageY;
		f_lzhd_sendhM_ex(x, y)
	})
}
function f_lzhd_sendhM_ex(x, y) {
	if ((x != undefined && x <= document.body.scrollWidth) && (y != undefined && y <= document.body.scrollHeight && y >= 0)) {
		var tempBodyX = document.body.scrollWidth / 2;
		x = Math.floor(x - tempBodyX);
		y = Math.floor(y);
		lzhd_obj.hM = new Object();
		lzhd_obj.hM.type = "type=4";
		lzhd_obj.hM.guid = lzhd_obj.eM.guid;
		lzhd_obj.hM.domain = lzhd_obj.eM.domain;
		lzhd_obj.hM.siteCode = lzhd_obj.eM.siteCode;
		lzhd_obj.hM.vname = lzhd_obj.eM.vname;
		lzhd_obj.hM.x = ("x=" + x);
		lzhd_obj.hM.y = ("y=" + y);
		lzhd_obj.hM.bodyX = ("bodyX=" + document.body.scrollWidth);
		lzhd_obj.hM.bodyY = ("bodyY=" + document.body.scrollHeight);
		lzhd_obj.hM.referrer = lzhd_obj.eM.referrer;f_lzhd_sendhM()
	}
}
function f_lzhd_newGuid() {
	var guid = "";
	for (var i = 1; i <= 32; i++) {
		var n = Math.floor(Math.random() * 16).toString(16);
		guid += n;
		if ((i == 8) || (i == 12) || (i == 16) || (i == 20)) {
			guid += "-"
		}
	}
	return guid
}
function f_lzhd_sendhM() {
	var message = f_lzhd_getMessage(lzhd_obj.split, lzhd_obj.hM);
	f_lzhd_ajax(message, lzhd_obj.path_h1)
}
function f_lzhd_ajax(message, url) {
	jQuery.ajax({
		url : url,
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		type : "GET",
		dataType : "jsonp",
		jsonp : "callback",
		data : {
			message : (message),
			siteCode : (lzhd_obj.localname)
		},
		cache : false,
		success : function(response) {},
		error : function(response) {}
	})
}
function f_lzhd_sendsM() {
	var message = f_lzhd_getMessage(lzhd_obj.split, lzhd_obj.sM);
	f_lzhd_ajax(message, lzhd_obj.path_s1)
}
function f_lzhd_sendeM() {
	var message = f_lzhd_getMessage(lzhd_obj.split, lzhd_obj.eM);
	f_lzhd_ajax(message, lzhd_obj.path_en1)
}
function f_lzhd_getClassId() {
	try {
		var classid = jQuery("input").filter("#YZHD_SUBMALL").val();
		if (classid == "" || classid == undefined) {
			lzhd_obj.eM.classid = "classid="
		} else {
			lzhd_obj.eM.classid = "classid=" + classid
		}
	} catch (err) {
		lzhd_obj.eM.classid = "classid=";return
	}
}
function f_lzhd_getVname() {
	var vname = "YZHD_USERID";
	vname = f_lzhd_getCookie(vname);
	vname == 0 ? (lzhd_obj.eM.vname = "vname=") : (lzhd_obj.eM.vname = "vname=" + vname)
}
function f_lzhd_getMessage(split, obj) {
	var message = "";
	for (var i in obj) {
		message = message + obj[i] + split
	}
	message = message.substring(0, message.lastIndexOf(split));return message
}
function f_lzhd_setCookie_s(name, value) {
	var curCookie = name + "=" + escape(value);
	if ((name + "=" + escape(value)).length <= 4000) {
		document.cookie = curCookie + lzhd_obj.cDomain + lzhd_obj.cPath
	}
}
function f_lzhd_setCookie(name, value) {
	var years = 2;
	var exp = new Date();
	exp.setTime(exp.getTime() + years * 365 * 24 * 60 * 60 * 1000);
	var curCookie = name + "=" + escape(value);
	if ((name + "=" + escape(value)).length <= 4000) {
		document.cookie = curCookie + ";expires=" + exp.toGMTString() + lzhd_obj.cDomain + lzhd_obj.cPath
	}
}
function f_lzhd_getCookie(name) {
	var prefix = name + "=";
	var cookieStartIndex = document.cookie.indexOf(prefix);
	if (cookieStartIndex == -1) {
		return 0
	}
	var cookieEndIndex = document.cookie.indexOf(";", cookieStartIndex + prefix.length);
	if (cookieEndIndex == -1) {
		cookieEndIndex = document.cookie.length
	}
	return unescape(document.cookie.substring(cookieStartIndex + prefix.length, cookieEndIndex))
}
;