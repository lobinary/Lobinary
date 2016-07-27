dhtmlx = function(d) {
	for (var c in d) {
		dhtmlx[c] = d[c]
	}
	return dhtmlx
};
dhtmlx.extend_api = function(a, e, d) {
	var c = window[a];
	if (!c) {
		return
	}
	window[a] = function(h) {
		if (h && typeof h == "object" && !h.tagName) {
			var g = c.apply(this, (e._init ? e._init(h) : arguments));
			for (var f in dhtmlx) {
				if (e[f]) {
					this[e[f]](dhtmlx[f])
				}
			}
			for (var f in h) {
				if (e[f]) {
					this[e[f]](h[f])
				} else {
					if (f.indexOf("on") == 0) {
						this.attachEvent(f, h[f])
					}
				}
			}
		} else {
			var g = c.apply(this, arguments)
		}
		if (e._patch) {
			e._patch(this)
		}
		return g || this
	};
	window[a].prototype = c.prototype;
	if (d) {
		dhtmlXHeir(window[a].prototype, d)
	}
};
dhtmlxAjax = {
	get : function(a, d) {
		var c = new dtmlXMLLoaderObject(true);
		c.async = (arguments.length < 3);
		c.waitCall = d;
		c.loadXML(a);
		return c
	},
	post : function(a, d, e) {
		var c = new dtmlXMLLoaderObject(true);
		c.async = (arguments.length < 4);
		c.waitCall = e;
		c.loadXML(a, true, d);
		return c
	},
	getSync : function(a) {
		return this.get(a, null, true)
	},
	postSync : function(a, c) {
		return this.post(a, c, null, true)
	}
};
function dtmlXMLLoaderObject(c, e, d, a) {
	this.xmlDoc = "";
	if (typeof(d) != "undefined") {
		this.async = d
	} else {
		this.async = true
	}
	this.onloadAction = c || null;
	this.mainObject = e || null;
	this.waitCall = null;
	this.rSeed = a || false;
	return this
}
dtmlXMLLoaderObject.prototype.waitLoadFunction = function(c) {
	var a = true;
	this.check = function() {
		if ((c) && (c.onloadAction != null)) {
			if ((!c.xmlDoc.readyState) || (c.xmlDoc.readyState == 4)) {
				if (!a) {
					return
				}
				a = false;
				if (typeof c.onloadAction == "function") {
					c.onloadAction(c.mainObject, null, null, null, c)
				}
				if (c.waitCall) {
					c.waitCall.call(this, c.mainObject)
				}
			}
		}
	};
	return this.check
};
dtmlXMLLoaderObject.prototype.getXMLTopNode = function(d, a) {
	if (this.xmlDoc.responseXML) {
		var c = this.xmlDoc.responseXML.getElementsByTagName(d);
		if (c.length == 0 && d.indexOf(":") != -1) {
			var c = this.xmlDoc.responseXML
					.getElementsByTagName((d.split(":"))[1])
		}
		var f = c[0]
	} else {
		var f = this.xmlDoc.documentElement
	}
	if (f) {
		this._retry = false;
		return f
	}
	if ((_isIE) && (!this._retry)) {
		var e = this.xmlDoc.responseText;
		var a = this.xmlDoc;
		this._retry = true;
		this.xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
		this.xmlDoc.async = false;
		this.xmlDoc.loadXML(e);
		return this.getXMLTopNode(d, a)
	}
	dhtmlxError.throwError("LoadXML", "Incorrect XML", [(a || this.xmlDoc),
					this.mainObject]);
	return document.createElement("DIV")
};
dtmlXMLLoaderObject.prototype.loadXMLString = function(a) {
	if (!_isIE) {
		var c = new DOMParser();
		this.xmlDoc = c.parseFromString(a, "text/xml")
	} else {
		this.xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
		this.xmlDoc.async = this.async;
		this.xmlDoc.onreadystatechange = function() {
		};
		this.xmlDoc.loadXML(a)
	}
	if (this.onloadAction) {
		this.onloadAction(this.mainObject, null, null, null, this)
	}
	if (this.waitCall) {
		this.waitCall(this.mainObject);
		this.waitCall = null
	}
};
dtmlXMLLoaderObject.prototype.loadXML = function(d, c, a, e) {
	if (this.rSeed) {
		d += ((d.indexOf("?") != -1) ? "&" : "?") + "a_dhx_rSeed="
				+ (new Date()).valueOf()
	}
	this.filePath = d;
	if ((!_isIE) && (window.XMLHttpRequest)) {
		this.xmlDoc = new XMLHttpRequest()
	} else {
		this.xmlDoc = new ActiveXObject("Microsoft.XMLHTTP")
	}
	if (this.async) {
		this.xmlDoc.onreadystatechange = new this.waitLoadFunction(this)
	}
	this.xmlDoc.open(c ? "POST" : "GET", d, this.async);
	if (e) {
		this.xmlDoc.setRequestHeader("User-Agent", "dhtmlxRPC v0.1 ("
						+ navigator.userAgent + ")");
		this.xmlDoc.setRequestHeader("Content-type", "text/xml")
	} else {
		if (c) {
			this.xmlDoc.setRequestHeader("Content-type",
					"application/x-www-form-urlencoded")
		}
	}
	this.xmlDoc.setRequestHeader("X-Requested-With", "XMLHttpRequest");
	this.xmlDoc.send(null || a);
	if (!this.async) {
		(new this.waitLoadFunction(this))()
	}
};
dtmlXMLLoaderObject.prototype.destructor = function() {
	this._filterXPath = null;
	this._getAllNamedChilds = null;
	this._retry = null;
	this.async = null;
	this.rSeed = null;
	this.filePath = null;
	this.onloadAction = null;
	this.mainObject = null;
	this.xmlDoc = null;
	this.doXPath = null;
	this.doXPathOpera = null;
	this.doXSLTransToObject = null;
	this.doXSLTransToString = null;
	this.loadXML = null;
	this.loadXMLString = null;
	this.doSerialization = null;
	this.xmlNodeToJSON = null;
	this.getXMLTopNode = null;
	this.setXSLParamValue = null;
	return null
};
dtmlXMLLoaderObject.prototype.xmlNodeToJSON = function(e) {
	var d = {};
	for (var c = 0; c < e.attributes.length; c++) {
		d[e.attributes[c].name] = e.attributes[c].value
	}
	d._tagvalue = e.firstChild ? e.firstChild.nodeValue : "";
	for (var c = 0; c < e.childNodes.length; c++) {
		var a = e.childNodes[c].tagName;
		if (a) {
			if (!d[a]) {
				d[a] = []
			}
			d[a].push(this.xmlNodeToJSON(e.childNodes[c]))
		}
	}
	return d
};
function callerFunction(a, c) {
	this.handler = function(d) {
		if (!d) {
			d = window.event
		}
		a(d, c);
		return true
	};
	return this.handler
}
function getAbsoluteLeft(a) {
	return getOffset(a).left
}
function getAbsoluteTop(a) {
	return getOffset(a).top
}
function getOffsetSum(a) {
	var d = 0, c = 0;
	while (a) {
		d = d + parseInt(a.offsetTop);
		c = c + parseInt(a.offsetLeft);
		a = a.offsetParent
	}
	return {
		top : d,
		left : c
	}
}
function getOffsetRect(e) {
	var h = e.getBoundingClientRect();
	var j = document.body;
	var c = document.documentElement;
	var a = window.pageYOffset || c.scrollTop || j.scrollTop;
	var f = window.pageXOffset || c.scrollLeft || j.scrollLeft;
	var g = c.clientTop || j.clientTop || 0;
	var k = c.clientLeft || j.clientLeft || 0;
	var l = h.top + a - g;
	var d = h.left + f - k;
	return {
		top : Math.round(l),
		left : Math.round(d)
	}
}
function getOffset(a) {
	if (a.getBoundingClientRect) {
		return getOffsetRect(a)
	} else {
		return getOffsetSum(a)
	}
}
function convertStringToBoolean(a) {
	if (typeof(a) == "string") {
		a = a.toLowerCase()
	}
	switch (a) {
		case "1" :
		case "true" :
		case "yes" :
		case "y" :
		case 1 :
		case true :
			return true;
			break;
		default :
			return false
	}
}
function getUrlSymbol(a) {
	if (a.indexOf("?") != -1) {
		return "&"
	} else {
		return "?"
	}
}
function dhtmlDragAndDropObject() {
	if (window.dhtmlDragAndDrop) {
		return window.dhtmlDragAndDrop
	}
	this.lastLanding = 0;
	this.dragNode = 0;
	this.dragStartNode = 0;
	this.dragStartObject = 0;
	this.tempDOMU = null;
	this.tempDOMM = null;
	this.waitDrag = 0;
	window.dhtmlDragAndDrop = this;
	return this
}
dhtmlDragAndDropObject.prototype.removeDraggableItem = function(a) {
	a.onmousedown = null;
	a.dragStarter = null;
	a.dragLanding = null
};
dhtmlDragAndDropObject.prototype.addDraggableItem = function(a, c) {
	a.onmousedown = this.preCreateDragCopy;
	a.dragStarter = c;
	this.addDragLanding(a, c)
};
dhtmlDragAndDropObject.prototype.addDragLanding = function(a, c) {
	a.dragLanding = c
};
dhtmlDragAndDropObject.prototype.preCreateDragCopy = function(a) {
	if ((a || window.event) && (a || event).button == 2) {
		return
	}
	if (window.dhtmlDragAndDrop.waitDrag) {
		window.dhtmlDragAndDrop.waitDrag = 0;
		document.body.onmouseup = window.dhtmlDragAndDrop.tempDOMU;
		document.body.onmousemove = window.dhtmlDragAndDrop.tempDOMM;
		return false
	}
	if (window.dhtmlDragAndDrop.dragNode) {
		window.dhtmlDragAndDrop.stopDrag(a)
	}
	window.dhtmlDragAndDrop.waitDrag = 1;
	window.dhtmlDragAndDrop.tempDOMU = document.body.onmouseup;
	window.dhtmlDragAndDrop.tempDOMM = document.body.onmousemove;
	window.dhtmlDragAndDrop.dragStartNode = this;
	window.dhtmlDragAndDrop.dragStartObject = this.dragStarter;
	document.body.onmouseup = window.dhtmlDragAndDrop.preCreateDragCopy;
	document.body.onmousemove = window.dhtmlDragAndDrop.callDrag;
	window.dhtmlDragAndDrop.downtime = new Date().valueOf();
	if ((a) && (a.preventDefault)) {
		a.preventDefault();
		return false
	}
	return false
};
dhtmlDragAndDropObject.prototype.callDrag = function(d) {
	if (!d) {
		d = window.event
	}
	dragger = window.dhtmlDragAndDrop;
	if ((new Date()).valueOf() - dragger.downtime < 100) {
		return
	}
	if (!dragger.dragNode) {
		if (dragger.waitDrag) {
			dragger.dragNode = dragger.dragStartObject._createDragNode(
					dragger.dragStartNode, d);
			if (!dragger.dragNode) {
				return dragger.stopDrag()
			}
			dragger.dragNode.onselectstart = function() {
				return false
			};
			dragger.gldragNode = dragger.dragNode;
			document.body.appendChild(dragger.dragNode);
			document.body.onmouseup = dragger.stopDrag;
			dragger.waitDrag = 0;
			dragger.dragNode.pWindow = window;
			dragger.initFrameRoute()
		} else {
			return dragger.stopDrag(d, true)
		}
	}
	if (dragger.dragNode.parentNode != window.document.body
			&& dragger.gldragNode) {
		var a = dragger.gldragNode;
		if (dragger.gldragNode.old) {
			a = dragger.gldragNode.old
		}
		a.parentNode.removeChild(a);
		var c = dragger.dragNode.pWindow;
		if (a.pWindow && a.pWindow.dhtmlDragAndDrop.lastLanding) {
			a.pWindow.dhtmlDragAndDrop.lastLanding.dragLanding
					._dragOut(a.pWindow.dhtmlDragAndDrop.lastLanding)
		}
		if (_isIE) {
			var g = document.createElement("Div");
			g.innerHTML = dragger.dragNode.outerHTML;
			dragger.dragNode = g.childNodes[0]
		} else {
			dragger.dragNode = dragger.dragNode.cloneNode(true)
		}
		dragger.dragNode.pWindow = window;
		dragger.gldragNode.old = dragger.dragNode;
		document.body.appendChild(dragger.dragNode);
		c.dhtmlDragAndDrop.dragNode = dragger.dragNode
	}
	dragger.dragNode.style.left = d.clientX + 15
			+ (dragger.fx ? dragger.fx * (-1) : 0)
			+ (document.body.scrollLeft || document.documentElement.scrollLeft)
			+ "px";
	dragger.dragNode.style.top = d.clientY + 3
			+ (dragger.fy ? dragger.fy * (-1) : 0)
			+ (document.body.scrollTop || document.documentElement.scrollTop)
			+ "px";
	if (!d.srcElement) {
		var f = d.target
	} else {
		f = d.srcElement
	}
	dragger.checkLanding(f, d)
};
dhtmlDragAndDropObject.prototype.calculateFramePosition = function(f) {
	if (window.name) {
		var d = parent.frames[window.name].frameElement.offsetParent;
		var e = 0;
		var c = 0;
		while (d) {
			e += d.offsetLeft;
			c += d.offsetTop;
			d = d.offsetParent
		}
		if ((parent.dhtmlDragAndDrop)) {
			var a = parent.dhtmlDragAndDrop.calculateFramePosition(1);
			e += a.split("_")[0] * 1;
			c += a.split("_")[1] * 1
		}
		if (f) {
			return e + "_" + c
		} else {
			this.fx = e
		}
		this.fy = c
	}
	return "0_0"
};
dhtmlDragAndDropObject.prototype.checkLanding = function(c, a) {
	if ((c) && (c.dragLanding)) {
		if (this.lastLanding) {
			this.lastLanding.dragLanding._dragOut(this.lastLanding)
		}
		this.lastLanding = c;
		this.lastLanding = this.lastLanding.dragLanding._dragIn(
				this.lastLanding, this.dragStartNode, a.clientX, a.clientY, a);
		this.lastLanding_scr = (_isIE ? a.srcElement : a.target)
	} else {
		if ((c) && (c.tagName != "BODY")) {
			this.checkLanding(c.parentNode, a)
		} else {
			if (this.lastLanding) {
				this.lastLanding.dragLanding._dragOut(this.lastLanding,
						a.clientX, a.clientY, a)
			}
			this.lastLanding = 0;
			if (this._onNotFound) {
				this._onNotFound()
			}
		}
	}
};
dhtmlDragAndDropObject.prototype.stopDrag = function(c, d) {
	dragger = window.dhtmlDragAndDrop;
	if (!d) {
		dragger.stopFrameRoute();
		var a = dragger.lastLanding;
		dragger.lastLanding = null;
		if (a) {
			a.dragLanding._drag(dragger.dragStartNode, dragger.dragStartObject,
					a, (_isIE ? event.srcElement : c.target))
		}
	}
	dragger.lastLanding = null;
	if ((dragger.dragNode) && (dragger.dragNode.parentNode == document.body)) {
		dragger.dragNode.parentNode.removeChild(dragger.dragNode)
	}
	dragger.dragNode = 0;
	dragger.gldragNode = 0;
	dragger.fx = 0;
	dragger.fy = 0;
	dragger.dragStartNode = 0;
	dragger.dragStartObject = 0;
	document.body.onmouseup = dragger.tempDOMU;
	document.body.onmousemove = dragger.tempDOMM;
	dragger.tempDOMU = null;
	dragger.tempDOMM = null;
	dragger.waitDrag = 0
};
dhtmlDragAndDropObject.prototype.stopFrameRoute = function(d) {
	if (d) {
		window.dhtmlDragAndDrop.stopDrag(1, 1)
	}
	for (var a = 0; a < window.frames.length; a++) {
		try {
			if ((window.frames[a] != d) && (window.frames[a].dhtmlDragAndDrop)) {
				window.frames[a].dhtmlDragAndDrop.stopFrameRoute(window)
			}
		} catch (c) {
		}
	}
	try {
		if ((parent.dhtmlDragAndDrop) && (parent != window) && (parent != d)) {
			parent.dhtmlDragAndDrop.stopFrameRoute(window)
		}
	} catch (c) {
	}
};
dhtmlDragAndDropObject.prototype.initFrameRoute = function(d, f) {
	if (d) {
		window.dhtmlDragAndDrop.preCreateDragCopy();
		window.dhtmlDragAndDrop.dragStartNode = d.dhtmlDragAndDrop.dragStartNode;
		window.dhtmlDragAndDrop.dragStartObject = d.dhtmlDragAndDrop.dragStartObject;
		window.dhtmlDragAndDrop.dragNode = d.dhtmlDragAndDrop.dragNode;
		window.dhtmlDragAndDrop.gldragNode = d.dhtmlDragAndDrop.dragNode;
		window.document.body.onmouseup = window.dhtmlDragAndDrop.stopDrag;
		window.waitDrag = 0;
		if (((!_isIE) && (f)) && ((!_isFF) || (_FFrv < 1.8))) {
			window.dhtmlDragAndDrop.calculateFramePosition()
		}
	}
	try {
		if ((parent.dhtmlDragAndDrop) && (parent != window) && (parent != d)) {
			parent.dhtmlDragAndDrop.initFrameRoute(window)
		}
	} catch (c) {
	}
	for (var a = 0; a < window.frames.length; a++) {
		try {
			if ((window.frames[a] != d) && (window.frames[a].dhtmlDragAndDrop)) {
				window.frames[a].dhtmlDragAndDrop.initFrameRoute(window,
						((!d || f) ? 1 : 0))
			}
		} catch (c) {
		}
	}
};
var _isFF = false;
var _isIE = false;
var _isOpera = false;
var _isKHTML = false;
var _isMacOS = false;
var _isChrome = false;
if (navigator.userAgent.indexOf("Macintosh") != -1) {
	_isMacOS = true
}
if (navigator.userAgent.toLowerCase().indexOf("chrome") > -1) {
	_isChrome = true
}
if ((navigator.userAgent.indexOf("Safari") != -1)
		|| (navigator.userAgent.indexOf("Konqueror") != -1)) {
	var _KHTMLrv = parseFloat(navigator.userAgent.substr(navigator.userAgent
					.indexOf("Safari")
					+ 7, 5));
	if (_KHTMLrv > 525) {
		_isFF = true;
		var _FFrv = 1.9
	} else {
		_isKHTML = true
	}
} else {
	if (navigator.userAgent.indexOf("Opera") != -1) {
		_isOpera = true;
		_OperaRv = parseFloat(navigator.userAgent.substr(navigator.userAgent
						.indexOf("Opera")
						+ 6, 3))
	} else {
		if (navigator.appName.indexOf("Microsoft") != -1) {
			_isIE = true;
			if (navigator.appVersion.indexOf("MSIE 8.0") != -1
					&& document.compatMode != "BackCompat") {
				_isIE = 8
			}
			if (navigator.appVersion.indexOf("MSIE 9.0") != -1
					&& document.compatMode != "BackCompat") {
				_isIE = 8
			}
			if (navigator.appVersion.indexOf("MSIE 9.0") != -1
					&& document.compatMode != "BackCompat") {
				_isIE = 8
			}
		} else {
			_isFF = true;
			var _FFrv = parseFloat(navigator.userAgent.split("rv:")[1])
		}
	}
}
dtmlXMLLoaderObject.prototype.doXPath = function(d, f, e, k) {
	if (_isKHTML || (!_isIE && !window.XPathResult)) {
		return this.doXPathOpera(d, f)
	}
	if (_isIE) {
		if (!f) {
			if (!this.xmlDoc.nodeName) {
				f = this.xmlDoc.responseXML
			} else {
				f = this.xmlDoc
			}
		}
		if (!f) {
			dhtmlxError.throwError("LoadXML", "Incorrect XML", [
							(f || this.xmlDoc), this.mainObject])
		}
		if (e != null) {
			f.setProperty("SelectionNamespaces", "xmlns:xsl='" + e + "'")
		}
		if (k == "single") {
			return f.selectSingleNode(d)
		} else {
			return f.selectNodes(d) || new Array(0)
		}
	} else {
		var a = f;
		if (!f) {
			if (!this.xmlDoc.nodeName) {
				f = this.xmlDoc.responseXML
			} else {
				f = this.xmlDoc
			}
		}
		if (!f) {
			dhtmlxError.throwError("LoadXML", "Incorrect XML", [
							(f || this.xmlDoc), this.mainObject])
		}
		if (f.nodeName.indexOf("document") != -1) {
			a = f
		} else {
			a = f;
			f = f.ownerDocument
		}
		var h = XPathResult.ANY_TYPE;
		if (k == "single") {
			h = XPathResult.FIRST_ORDERED_NODE_TYPE
		}
		var g = new Array();
		var c = f.evaluate(d, a, function(l) {
					return e
				}, h, null);
		if (h == XPathResult.FIRST_ORDERED_NODE_TYPE) {
			return c.singleNodeValue
		}
		var j = c.iterateNext();
		while (j) {
			g[g.length] = j;
			j = c.iterateNext()
		}
		return g
	}
};
function _dhtmlxError(c, a, d) {
	if (!this.catches) {
		this.catches = new Array()
	}
	return this
}
_dhtmlxError.prototype.catchError = function(c, a) {
	this.catches[c] = a
};
_dhtmlxError.prototype.throwError = function(c, a, d) {
	if (this.catches[c]) {
		return this.catches[c](c, a, d)
	}
	if (this.catches.ALL) {
		return this.catches.ALL(c, a, d)
	}
	alert("Error type: " + arguments[0] + "\nDescription: " + arguments[1]);
	return null
};
window.dhtmlxError = new _dhtmlxError();
dtmlXMLLoaderObject.prototype.doXPathOpera = function(d, a) {
	var f = d.replace(/[\/]+/gi, "/").split("/");
	var e = null;
	var c = 1;
	if (!f.length) {
		return []
	}
	if (f[0] == ".") {
		e = [a]
	} else {
		if (f[0] == "") {
			e = (this.xmlDoc.responseXML || this.xmlDoc)
					.getElementsByTagName(f[c].replace(/\[[^\]]*\]/g, ""));
			c++
		} else {
			return []
		}
	}
	for (c; c < f.length; c++) {
		e = this._getAllNamedChilds(e, f[c])
	}
	if (f[c - 1].indexOf("[") != -1) {
		e = this._filterXPath(e, f[c - 1])
	}
	return e
};
dtmlXMLLoaderObject.prototype._filterXPath = function(e, d) {
	var g = new Array();
	var d = d.replace(/[^\[]*\[\@/g, "").replace(/[\[\]\@]*/g, "");
	for (var f = 0; f < e.length; f++) {
		if (e[f].getAttribute(d)) {
			g[g.length] = e[f]
		}
	}
	return g
};
dtmlXMLLoaderObject.prototype._getAllNamedChilds = function(e, d) {
	var h = new Array();
	if (_isKHTML) {
		d = d.toUpperCase()
	}
	for (var g = 0; g < e.length; g++) {
		for (var f = 0; f < e[g].childNodes.length; f++) {
			if (_isKHTML) {
				if (e[g].childNodes[f].tagName
						&& e[g].childNodes[f].tagName.toUpperCase() == d) {
					h[h.length] = e[g].childNodes[f]
				}
			} else {
				if (e[g].childNodes[f].tagName == d) {
					h[h.length] = e[g].childNodes[f]
				}
			}
		}
	}
	return h
};
function dhtmlXHeir(e, d) {
	for (var f in d) {
		if (typeof(d[f]) == "function") {
			e[f] = d[f]
		}
	}
	return e
}
function dhtmlxEvent(c, d, a) {
	if (c.addEventListener) {
		c.addEventListener(d, a, false)
	} else {
		if (c.attachEvent) {
			c.attachEvent("on" + d, a)
		}
	}
}
dtmlXMLLoaderObject.prototype.xslDoc = null;
dtmlXMLLoaderObject.prototype.setXSLParamValue = function(c, d, e) {
	if (!e) {
		e = this.xslDoc
	}
	if (e.responseXML) {
		e = e.responseXML
	}
	var a = this.doXPath("/xsl:stylesheet/xsl:variable[@name='" + c + "']", e,
			"http://www.w3.org/1999/XSL/Transform", "single");
	if (a != null) {
		a.firstChild.nodeValue = d
	}
};
dtmlXMLLoaderObject.prototype.doXSLTransToObject = function(f, c) {
	if (!f) {
		f = this.xslDoc
	}
	if (f.responseXML) {
		f = f.responseXML
	}
	if (!c) {
		c = this.xmlDoc
	}
	if (c.responseXML) {
		c = c.responseXML
	}
	if (!_isIE) {
		if (!this.XSLProcessor) {
			this.XSLProcessor = new XSLTProcessor();
			this.XSLProcessor.importStylesheet(f)
		}
		var a = this.XSLProcessor.transformToDocument(c)
	} else {
		var a = new ActiveXObject("Msxml2.DOMDocument.3.0");
		try {
			c.transformNodeToObject(f, a)
		} catch (d) {
			a = c.transformNode(f)
		}
	}
	return a
};
dtmlXMLLoaderObject.prototype.doXSLTransToString = function(d, c) {
	var a = this.doXSLTransToObject(d, c);
	if (typeof(a) == "string") {
		return a
	}
	return this.doSerialization(a)
};
dtmlXMLLoaderObject.prototype.doSerialization = function(c) {
	if (!c) {
		c = this.xmlDoc
	}
	if (c.responseXML) {
		c = c.responseXML
	}
	if (!_isIE) {
		var a = new XMLSerializer();
		return a.serializeToString(c)
	} else {
		return c.xml
	}
};
dhtmlxEventable = function(obj) {
	obj.attachEvent = function(name, catcher, callObj) {
		name = "ev_" + name.toLowerCase();
		if (!this[name]) {
			this[name] = new this.eventCatcher(callObj || this)
		}
		return (name + ":" + this[name].addEvent(catcher))
	};
	obj.callEvent = function(name, arg0) {
		name = "ev_" + name.toLowerCase();
		if (this[name]) {
			return this[name].apply(this, arg0)
		}
		return true
	};
	obj.checkEvent = function(name) {
		return (!!this["ev_" + name.toLowerCase()])
	};
	obj.eventCatcher = function(obj) {
		var dhx_catch = [];
		var z = function() {
			var res = true;
			for (var i = 0; i < dhx_catch.length; i++) {
				if (dhx_catch[i] != null) {
					var zr = dhx_catch[i].apply(obj, arguments);
					res = res && zr
				}
			}
			return res
		};
		z.addEvent = function(ev) {
			if (typeof(ev) != "function") {
				ev = eval(ev)
			}
			if (ev) {
				return dhx_catch.push(ev) - 1
			}
			return false
		};
		z.removeEvent = function(id) {
			dhx_catch[id] = null
		};
		return z
	};
	obj.detachEvent = function(id) {
		if (id != false) {
			var list = id.split(":");
			this[list[0]].removeEvent(list[1])
		}
	};
	obj.detachAllEvents = function() {
		for (var name in this) {
			if (name.indexOf("ev_") == 0) {
				delete this[name]
			}
		}
	}
};
function SearchEvent() {
	if (document.all) {
		return window.event
	}
	func = SearchEvent.caller;
	while (func != null) {
		var a = func.arguments[0];
		if (a) {
			if ((a.constructor == Event || a.constructor == MouseEvent)
					|| (typeof(a) == "object" && a.preventDefault && a.stopPropagation)) {
				return a
			}
		}
		func = func.caller
	}
	return null
}
function dhtmlXContainer(obj) {
	var that = this;
	this.obj = obj;
	this.dhxcont = null;
	this.st = document.createElement("DIV");
	this.st.style.position = "absolute";
	this.st.style.left = "-200px";
	this.st.style.top = "0px";
	this.st.style.width = "100px";
	this.st.style.height = "1px";
	this.st.style.visibility = "hidden";
	this.st.style.overflow = "hidden";
	document.body.insertBefore(this.st, document.body.childNodes[0]);
	this.obj._getSt = function() {
		return that.st
	};
	this.obj.dv = "def";
	this.obj.av = this.obj.dv;
	this.obj.cv = this.obj.av;
	this.obj.vs = {};
	this.obj.vs[this.obj.av] = {};
	this.obj.view = function(name) {
		if (!this.vs[name]) {
			this.vs[name] = {};
			this.vs[name].dhxcont = this.vs[this.dv].dhxcont;
			var mainCont = document.createElement("DIV");
			mainCont.style.position = "relative";
			mainCont.style.left = "0px";
			mainCont.style.width = "200px";
			mainCont.style.height = "200px";
			mainCont.style.overflow = "hidden";
			that.st.appendChild(mainCont);
			this.vs[name].dhxcont.mainCont[name] = mainCont
		}
		this.avt = this.av;
		this.av = name;
		return this
	};
	this.obj.setActive = function() {
		if (!this.vs[this.av]) {
			return
		}
		this.cv = this.av;
		if (this.vs[this.avt].dhxcont == this.vs[this.avt].dhxcont.mainCont[this.avt].parentNode) {
			that.st.appendChild(this.vs[this.avt].dhxcont.mainCont[this.avt]);
			if (this.vs[this.avt].menu) {
				that.st.appendChild(document
						.getElementById(this.vs[this.avt].menuId))
			}
			if (this.vs[this.avt].toolbar) {
				that.st.appendChild(document
						.getElementById(this.vs[this.avt].toolbarId))
			}
			if (this.vs[this.avt].sb) {
				that.st.appendChild(document
						.getElementById(this.vs[this.avt].sbId))
			}
		}
		if (this._isCell) {
		}
		if (this.vs[this.av].dhxcont != this.vs[this.av].dhxcont.mainCont[this.av].parentNode) {
			this.vs[this.av].dhxcont
					.insertBefore(
							this.vs[this.av].dhxcont.mainCont[this.av],
							this.vs[this.av].dhxcont.childNodes[this.vs[this.av].dhxcont.childNodes.length
									- 1]);
			if (this.vs[this.av].menu) {
				this.vs[this.av].dhxcont.insertBefore(document
								.getElementById(this.vs[this.av].menuId),
						this.vs[this.av].dhxcont.childNodes[0])
			}
			if (this.vs[this.av].toolbar) {
				this.vs[this.av].dhxcont
						.insertBefore(
								document
										.getElementById(this.vs[this.av].toolbarId),
								this.vs[this.av].dhxcont.childNodes[(this.vs[this.av].menu
										? 1
										: 0)])
			}
			if (this.vs[this.av].sb) {
				this.vs[this.av].dhxcont
						.insertBefore(
								document.getElementById(this.vs[this.av].sbId),
								this.vs[this.av].dhxcont.childNodes[this.vs[this.av].dhxcont.childNodes.length
										- 1])
			}
		}
		if (this._doOnResize) {
			this._doOnResize()
		}
		this.avt = null
	};
	this.obj._viewRestore = function() {
		var t = this.av;
		if (this.avt) {
			this.av = this.avt;
			this.avt = null
		}
		return t
	};
	this.setContent = function(data) {
		this.obj.vs[this.obj.av].dhxcont = data;
		this.obj._init()
	};
	this.obj._init = function() {
		this.vs[this.av].dhxcont.innerHTML = "<div ida='dhxMainCont' style='position: relative; left: 0px; top: 0px; overflow: hidden;'></div><div ida='dhxContBlocker' class='dhxcont_content_blocker' style='display: none;'></div>";
		this.vs[this.av].dhxcont.mainCont = {};
		this.vs[this.av].dhxcont.mainCont[this.av] = this.vs[this.av].dhxcont.childNodes[0]
	};
	this.obj._genStr = function(w) {
		var s = "";
		var z = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		for (var q = 0; q < w; q++) {
			s += z.charAt(Math.round(Math.random() * (z.length - 1)))
		}
		return s
	};
	this.obj.setMinContentSize = function(w, h) {
		this.vs[this.av]._minDataSizeW = w;
		this.vs[this.av]._minDataSizeH = h
	};
	this.obj._setPadding = function(p, altCss) {
		if (typeof(p) == "object") {
			this._offsetTop = p[0];
			this._offsetLeft = p[1];
			this._offsetWidth = p[2];
			this._offsetHeight = p[3]
		} else {
			this._offsetTop = p;
			this._offsetLeft = p;
			this._offsetWidth = -p * 2;
			this._offsetHeight = -p * 2
		}
		this.vs[this.av].dhxcont.className = "dhxcont_global_content_area "
				+ (altCss || "")
	};
	this.obj.moveContentTo = function(cont) {
		for (var a in this.vs) {
			cont.view(a).setActive();
			var pref = null;
			if (this.vs[a].grid) {
				pref = "grid"
			}
			if (this.vs[a].tree) {
				pref = "tree"
			}
			if (this.vs[a].tabbar) {
				pref = "tabbar"
			}
			if (this.vs[a].folders) {
				pref = "folders"
			}
			if (this.vs[a].layout) {
				pref = "layout"
			}
			if (pref != null) {
				cont.view(a).attachObject(this.vs[a][pref + "Id"]);
				cont.vs[a][pref] = this.vs[a][pref];
				cont.vs[a][pref + "Id"] = this.vs[a][pref + "Id"];
				cont.vs[a][pref + "Obj"] = this.vs[a][pref + "Obj"];
				this.vs[a][pref] = null;
				this.vs[a][pref + "Id"] = null;
				this.vs[a][pref + "Obj"] = null
			}
			if (this.vs[a]._frame) {
				cont.vs[a]._frame = this.vs[a]._frame;
				this.vs[a]._frame = null
			}
			if (this.vs[a].menu != null) {
				if (cont.cv == cont.av) {
					cont.vs[cont.av].dhxcont.insertBefore(document
									.getElementById(this.vs[a].menuId),
							cont.vs[cont.av].dhxcont.childNodes[0])
				} else {
					cont._getSt().appendChild(document
							.getElementById(this.vs[a].menuId))
				}
				cont.vs[a].menu = this.vs[a].menu;
				cont.vs[a].menuId = this.vs[a].menuId;
				cont.vs[a].menuHeight = this.vs[a].menuHeight;
				this.vs[a].menu = null;
				this.vs[a].menuId = null;
				this.vs[a].menuHeight = null;
				if (this.cv == this.av && this._doOnAttachMenu) {
					this._doOnAttachMenu("unload")
				}
				if (cont.cv == cont.av && cont._doOnAttachMenu) {
					cont._doOnAttachMenu("move")
				}
			}
			if (this.vs[a].toolbar != null) {
				if (cont.cv == cont.av) {
					cont.vs[cont.av].dhxcont
							.insertBefore(
									document
											.getElementById(this.vs[a].toolbarId),
									cont.vs[cont.av].dhxcont.childNodes[(cont.vs[cont.av].menu != null
											? 1
											: 0)])
				} else {
					cont._getSt().appendChild(document
							.getElementById(this.vs[a].toolbarId))
				}
				cont.vs[a].toolbar = this.vs[a].toolbar;
				cont.vs[a].toolbarId = this.vs[a].toolbarId;
				cont.vs[a].toolbarHeight = this.vs[a].toolbarHeight;
				this.vs[a].toolbar = null;
				this.vs[a].toolbarId = null;
				this.vs[a].toolbarHeight = null;
				if (this.cv == this.av && this._doOnAttachToolbar) {
					this._doOnAttachToolbar("unload")
				}
				if (cont.cv == cont.av && cont._doOnAttachToolbar) {
					cont._doOnAttachToolbar("move")
				}
			}
			if (this.vs[a].sb != null) {
				if (cont.cv == cont.av) {
					cont.vs[cont.av].dhxcont
							.insertBefore(
									document.getElementById(this.vs[a].sbId),
									cont.vs[cont.av].dhxcont.childNodes[cont.vs[cont.av].dhxcont.childNodes.length
											- 1])
				} else {
					cont._getSt().appendChild(document
							.getElementById(this.vs[a].sbId))
				}
				cont.vs[a].sb = this.vs[a].sb;
				cont.vs[a].sbId = this.vs[a].sbId;
				cont.vs[a].sbHeight = this.vs[a].sbHeight;
				this.vs[a].sb = null;
				this.vs[a].sbId = null;
				this.vs[a].sbHeight = null;
				if (this.cv == this.av && this._doOnAttachStatusBar) {
					this._doOnAttachStatusBar("unload")
				}
				if (cont.cv == cont.av && cont._doOnAttachStatusBar) {
					cont._doOnAttachStatusBar("move")
				}
			}
			var objA = this.vs[a].dhxcont.mainCont[a];
			var objB = cont.vs[a].dhxcont.mainCont[a];
			while (objA.childNodes.length > 0) {
				objB.appendChild(objA.childNodes[0])
			}
		}
		cont.view(this.av).setActive()
	};
	this.obj.adjustContent = function(parentObj, offsetTop, marginTop,
			notCalcWidth, offsetBottom) {
		this.vs[this.av].dhxcont.style.left = (this._offsetLeft || 0) + "px";
		this.vs[this.av].dhxcont.style.top = (this._offsetTop || 0) + offsetTop
				+ "px";
		var cw = parentObj.clientWidth + (this._offsetWidth || 0);
		if (notCalcWidth !== true) {
			this.vs[this.av].dhxcont.style.width = Math.max(0, cw) + "px"
		}
		if (notCalcWidth !== true) {
			if (this.vs[this.av].dhxcont.offsetWidth > cw) {
				this.vs[this.av].dhxcont.style.width = Math.max(0, cw * 2
								- this.vs[this.av].dhxcont.offsetWidth)
						+ "px"
			}
		}
		var ch = parentObj.clientHeight + (this._offsetHeight || 0);
		this.vs[this.av].dhxcont.style.height = Math.max(0, ch - offsetTop)
				+ (marginTop != null ? marginTop : 0) + "px";
		if (this.vs[this.av].dhxcont.offsetHeight > ch - offsetTop) {
			this.vs[this.av].dhxcont.style.height = Math.max(0,
					(ch - offsetTop) * 2
							- this.vs[this.av].dhxcont.offsetHeight)
					+ "px"
		}
		if (offsetBottom) {
			if (!isNaN(offsetBottom)) {
				this.vs[this.av].dhxcont.style.height = Math.max(0,
						parseInt(this.vs[this.av].dhxcont.style.height)
								- offsetBottom)
						+ "px"
			}
		}
		if (this.vs[this.av]._minDataSizeH != null) {
			if (parseInt(this.vs[this.av].dhxcont.style.height) < this.vs[this.av]._minDataSizeH) {
				this.vs[this.av].dhxcont.style.height = this.vs[this.av]._minDataSizeH
						+ "px"
			}
		}
		if (this.vs[this.av]._minDataSizeW != null) {
			if (parseInt(this.vs[this.av].dhxcont.style.width) < this.vs[this.av]._minDataSizeW) {
				this.vs[this.av].dhxcont.style.width = this.vs[this.av]._minDataSizeW
						+ "px"
			}
		}
		if (notCalcWidth !== true) {
			this.vs[this.av].dhxcont.mainCont[this.av].style.width = this.vs[this.av].dhxcont.clientWidth
					+ "px";
			if (this.vs[this.av].dhxcont.mainCont[this.av].offsetWidth > this.vs[this.av].dhxcont.clientWidth) {
				this.vs[this.av].dhxcont.mainCont[this.av].style.width = Math
						.max(
								0,
								this.vs[this.av].dhxcont.clientWidth
										* 2
										- this.vs[this.av].dhxcont.mainCont[this.av].offsetWidth)
						+ "px"
			}
		}
		var menuOffset = (this.vs[this.av].menu != null
				? (!this.vs[this.av].menuHidden
						? this.vs[this.av].menuHeight
						: 0)
				: 0);
		var toolbarOffset = (this.vs[this.av].toolbar != null
				? (!this.vs[this.av].toolbarHidden
						? this.vs[this.av].toolbarHeight
						: 0)
				: 0);
		var statusOffset = (this.vs[this.av].sb != null
				? (!this.vs[this.av].sbHidden ? this.vs[this.av].sbHeight : 0)
				: 0);
		this.vs[this.av].dhxcont.mainCont[this.av].style.height = this.vs[this.av].dhxcont.clientHeight
				+ "px";
		if (this.vs[this.av].dhxcont.mainCont[this.av].offsetHeight > this.vs[this.av].dhxcont.clientHeight) {
			this.vs[this.av].dhxcont.mainCont[this.av].style.height = Math
					.max(
							0,
							this.vs[this.av].dhxcont.clientHeight
									* 2
									- this.vs[this.av].dhxcont.mainCont[this.av].offsetHeight)
					+ "px"
		}
		this.vs[this.av].dhxcont.mainCont[this.av].style.height = Math
				.max(
						0,
						parseInt(this.vs[this.av].dhxcont.mainCont[this.av].style.height)
								- menuOffset - toolbarOffset - statusOffset)
				+ "px"
	};
	this.obj.coverBlocker = function() {
		return this.vs[this.av].dhxcont.childNodes[this.vs[this.av].dhxcont.childNodes.length
				- 1]
	};
	this.obj.showCoverBlocker = function() {
		this.coverBlocker().style.display = ""
	};
	this.obj.hideCoverBlocker = function() {
		this.coverBlocker().style.display = "none"
	};
	this.obj.updateNestedObjects = function() {
		if (this.vs[this.av].grid) {
			this.vs[this.av].grid.setSizes()
		}
		if (this.vs[this.av].sched) {
			this.vs[this.av].sched.setSizes()
		}
		if (this.vs[this.av].tabbar) {
			this.vs[this.av].tabbar.adjustOuterSize()
		}
		if (this.vs[this.av].folders) {
			this.vs[this.av].folders.setSizes()
		}
		if (this.vs[this.av].editor) {
			if (!_isIE) {
				this.vs[this.av].editor._prepareContent(true)
			}
			this.vs[this.av].editor.setSizes()
		}
		if (this.vs[this.av].layout) {
			if (this.vs[this.av]._isAcc
					&& this.vs[this.av].skin == "dhx_skyblue") {
				this.vs[this.av].layoutObj.style.width = parseInt(this.vs[this.av].dhxcont.mainCont[this.av].style.width)
						+ 2 + "px";
				this.vs[this.av].layoutObj.style.height = parseInt(this.vs[this.av].dhxcont.mainCont[this.av].style.height)
						+ 2 + "px"
			} else {
				this.vs[this.av].layoutObj.style.width = this.vs[this.av].dhxcont.mainCont[this.av].style.width;
				this.vs[this.av].layoutObj.style.height = this.vs[this.av].dhxcont.mainCont[this.av].style.height
			}
			this.vs[this.av].layout.setSizes()
		}
		if (this.vs[this.av].accordion != null) {
			if (this.vs[this.av].skin == "dhx_web") {
				this.vs[this.av].accordionObj.style.width = parseInt(this.vs[this.av].dhxcont.mainCont[this.av].style.width)
						+ "px";
				this.vs[this.av].accordionObj.style.height = parseInt(this.vs[this.av].dhxcont.mainCont[this.av].style.height)
						+ "px"
			} else {
				this.vs[this.av].accordionObj.style.width = parseInt(this.vs[this.av].dhxcont.mainCont[this.av].style.width)
						+ 2 + "px";
				this.vs[this.av].accordionObj.style.height = parseInt(this.vs[this.av].dhxcont.mainCont[this.av].style.height)
						+ 2 + "px"
			}
			this.vs[this.av].accordion.setSizes()
		}
		if (this.vs[this.av].dockedCell) {
			this.vs[this.av].dockedCell.updateNestedObjects()
		}
		if (this.vs[this.av].form) {
			this.vs[this.av].form.setSizes()
		}
	};
	this.obj.attachStatusBar = function() {
		if (this.vs[this.av].sb) {
			return
		}
		var sbObj = document.createElement("DIV");
		if (this._isCell) {
			sbObj.className = "dhxcont_sb_container_layoutcell"
		} else {
			sbObj.className = "dhxcont_sb_container"
		}
		sbObj.id = "sbobj_" + this._genStr(12);
		sbObj.innerHTML = "<div class='dhxcont_statusbar'></div>";
		if (this.cv == this.av) {
			this.vs[this.av].dhxcont
					.insertBefore(
							sbObj,
							this.vs[this.av].dhxcont.childNodes[this.vs[this.av].dhxcont.childNodes.length
									- 1])
		} else {
			that.st.appendChild(sbObj)
		}
		sbObj.setText = function(text) {
			this.childNodes[0].innerHTML = text
		};
		sbObj.getText = function() {
			return this.childNodes[0].innerHTML
		};
		sbObj.onselectstart = function(e) {
			e = e || event;
			e.returnValue = false;
			return false
		};
		this.vs[this.av].sb = sbObj;
		this.vs[this.av].sbHeight = (this.skin == "dhx_web"
				? 41
				: (this.skin == "dhx_skyblue" ? 23 : sbObj.offsetHeight));
		this.vs[this.av].sbId = sbObj.id;
		if (this._doOnAttachStatusBar) {
			this._doOnAttachStatusBar("init")
		}
		this.adjust();
		return this.vs[this._viewRestore()].sb
	};
	this.obj.detachStatusBar = function() {
		if (!this.vs[this.av].sb) {
			return
		}
		this.vs[this.av].sb.setText = null;
		this.vs[this.av].sb.getText = null;
		this.vs[this.av].sb.onselectstart = null;
		this.vs[this.av].sb.parentNode.removeChild(this.vs[this.av].sb);
		this.vs[this.av].sb = null;
		this.vs[this.av].sbHeight = null;
		this.vs[this.av].sbId = null;
		this._viewRestore();
		if (this._doOnAttachStatusBar) {
			this._doOnAttachStatusBar("unload")
		}
	};
	this.obj.attachMenu = function(skin) {
		if (this.vs[this.av].menu) {
			return
		}
		var menuObj = document.createElement("DIV");
		menuObj.style.position = "relative";
		menuObj.style.overflow = "hidden";
		menuObj.id = "dhxmenu_" + this._genStr(12);
		if (this.cv == this.av) {
			this.vs[this.av].dhxcont.insertBefore(menuObj,
					this.vs[this.av].dhxcont.childNodes[0])
		} else {
			that.st.appendChild(menuObj)
		}
		this.vs[this.av].menu = new dhtmlXMenuObject(menuObj.id,
				(skin || this.skin));
		this.vs[this.av].menuHeight = (this.skin == "dhx_web"
				? 29
				: menuObj.offsetHeight);
		this.vs[this.av].menuId = menuObj.id;
		if (this._doOnAttachMenu) {
			this._doOnAttachMenu("init")
		}
		this.adjust();
		return this.vs[this._viewRestore()].menu
	};
	this.obj.detachMenu = function() {
		if (!this.vs[this.av].menu) {
			return
		}
		var menuObj = document.getElementById(this.vs[this.av].menuId);
		this.vs[this.av].menu.unload();
		this.vs[this.av].menu = null;
		this.vs[this.av].menuId = null;
		this.vs[this.av].menuHeight = null;
		menuObj.parentNode.removeChild(menuObj);
		menuObj = null;
		this._viewRestore();
		if (this._doOnAttachMenu) {
			this._doOnAttachMenu("unload")
		}
	};
	this.obj.attachToolbar = function(skin) {
		if (this.vs[this.av].toolbar) {
			return
		}
		var toolbarObj = document.createElement("DIV");
		toolbarObj.style.position = "relative";
		toolbarObj.style.overflow = "hidden";
		toolbarObj.id = "dhxtoolbar_" + this._genStr(12);
		if (this.cv == this.av) {
			this.vs[this.av].dhxcont
					.insertBefore(
							toolbarObj,
							this.vs[this.av].dhxcont.childNodes[(this.vs[this.av].menu != null
									? 1
									: 0)])
		} else {
			that.st.appendChild(toolbarObj)
		}
		this.vs[this.av].toolbar = new dhtmlXToolbarObject(toolbarObj.id,
				(skin || this.skin));
		this.vs[this.av].toolbarHeight = (this.skin == "dhx_web"
				? 41
				: toolbarObj.offsetHeight
						+ (this._isLayout && this.skin == "dhx_skyblue" ? 2 : 0));
		this.vs[this.av].toolbarId = toolbarObj.id;
		if (this._doOnAttachToolbar) {
			this._doOnAttachToolbar("init")
		}
		this.adjust();
		return this.vs[this._viewRestore()].toolbar
	};
	this.obj.detachToolbar = function() {
		if (!this.vs[this.av].toolbar) {
			return
		}
		var toolbarObj = document.getElementById(this.vs[this.av].toolbarId);
		this.vs[this.av].toolbar.unload();
		this.vs[this.av].toolbar = null;
		this.vs[this.av].toolbarId = null;
		this.vs[this.av].toolbarHeight = null;
		toolbarObj.parentNode.removeChild(toolbarObj);
		toolbarObj = null;
		this._viewRestore();
		if (this._doOnAttachToolbar) {
			this._doOnAttachToolbar("unload")
		}
	};
	this.obj.attachGrid = function() {
		if (this._isWindow && this.skin == "dhx_skyblue") {
			this.vs[this.av].dhxcont.mainCont[this.av].style.border = "#a4bed4 1px solid";
			this._redraw()
		}
		var obj = document.createElement("DIV");
		obj.id = "dhxGridObj_" + this._genStr(12);
		obj.style.width = "100%";
		obj.style.height = "100%";
		obj.cmp = "grid";
		document.body.appendChild(obj);
		this.attachObject(obj.id, false, true);
		this.vs[this.av].grid = new dhtmlXGridObject(obj.id);
		this.vs[this.av].grid.setSkin(this.skin);
		if (this.skin != "dhx_web") {
			this.vs[this.av].grid.entBox.style.border = "0px solid white";
			this.vs[this.av].grid._sizeFix = 0
		}
		this.vs[this.av].gridId = obj.id;
		this.vs[this.av].gridObj = obj;
		return this.vs[this._viewRestore()].grid
	};
	this.obj.attachScheduler = function(day, mode) {
		var obj = document.createElement("DIV");
		obj.id = "dhxSchedObj_" + this._genStr(12);
		obj.innerHTML = '<div id="'
				+ obj.id
				+ '" class="dhx_cal_container" style="width:100%; height:100%;"><div class="dhx_cal_navline"><div class="dhx_cal_prev_button">&nbsp;</div><div class="dhx_cal_next_button">&nbsp;</div><div class="dhx_cal_today_button"></div><div class="dhx_cal_date"></div><div class="dhx_cal_tab" name="day_tab" style="right:204px;"></div><div class="dhx_cal_tab" name="week_tab" style="right:140px;"></div><div class="dhx_cal_tab" name="month_tab" style="right:76px;"></div></div><div class="dhx_cal_header"></div><div class="dhx_cal_data"></div></div>';
		document.body.appendChild(obj.firstChild);
		this.attachObject(obj.id, false, true);
		this.vs[this.av].sched = scheduler;
		this.vs[this.av].schedId = obj.id;
		scheduler.setSizes = scheduler.update_view;
		scheduler.destructor = function() {
		};
		scheduler.init(obj.id, day, mode);
		return this.vs[this._viewRestore()].sched
	};
	this.obj.attachTree = function(rootId) {
		if (this._isWindow && this.skin == "dhx_skyblue") {
			this.vs[this.av].dhxcont.mainCont[this.av].style.border = "#a4bed4 1px solid";
			this._redraw()
		}
		var obj = document.createElement("DIV");
		obj.id = "dhxTreeObj_" + this._genStr(12);
		obj.style.width = "100%";
		obj.style.height = "100%";
		obj.cmp = "tree";
		document.body.appendChild(obj);
		this.attachObject(obj.id, false, true);
		this.vs[this.av].tree = new dhtmlXTreeObject(obj.id, "100%", "100%",
				(rootId || 0));
		this.vs[this.av].tree.setSkin(this.skin);
		this.vs[this.av].tree.allTree.childNodes[0].style.marginTop = "2px";
		this.vs[this.av].tree.allTree.childNodes[0].style.marginBottom = "2px";
		this.vs[this.av].treeId = obj.id;
		this.vs[this.av].treeObj = obj;
		return this.vs[this._viewRestore()].tree
	};
	this.obj.attachTabbar = function(mode) {
		if (this._isWindow && this.skin == "dhx_skyblue") {
			this.vs[this.av].dhxcont.style.border = "none";
			this.setDimension(this.w, this.h)
		}
		var obj = document.createElement("DIV");
		obj.id = "dhxTabbarObj_" + this._genStr(12);
		obj.style.width = "100%";
		obj.style.height = "100%";
		obj.style.overflow = "hidden";
		obj.cmp = "tabbar";
		document.body.appendChild(obj);
		this.attachObject(obj.id, false, true);
		if (this.className == "dhtmlxLayoutSinglePoly") {
			this.hideHeader()
		}
		this.vs[this.av].tabbar = new dhtmlXTabBar(obj.id, mode || "top", 20);
		if (!this._isWindow) {
			this.vs[this.av].tabbar._s.expand = true
		}
		this.vs[this.av].tabbar.setSkin(this.skin);
		this.vs[this.av].tabbar.adjustOuterSize();
		this.vs[this.av].tabbarId = obj.id;
		this.vs[this.av].tabbarObj = obj;
		return this.vs[this._viewRestore()].tabbar
	};
	this.obj.attachFolders = function() {
		if (this._isWindow && this.skin == "dhx_skyblue") {
			this.vs[this.av].dhxcont.mainCont[this.av].style.border = "#a4bed4 1px solid";
			this._redraw()
		}
		var obj = document.createElement("DIV");
		obj.id = "dhxFoldersObj_" + this._genStr(12);
		obj.style.width = "100%";
		obj.style.height = "100%";
		obj.style.overflow = "hidden";
		obj.cmp = "folders";
		document.body.appendChild(obj);
		this.attachObject(obj.id, false, true);
		this.vs[this.av].folders = new dhtmlxFolders(obj.id);
		this.vs[this.av].folders.setSizes();
		this.vs[this.av].foldersId = obj.id;
		this.vs[this.av].foldersObj = obj;
		return this.vs[this._viewRestore()].folders
	};
	this.obj.attachAccordion = function() {
		if (this._isWindow && this.skin == "dhx_skyblue") {
			this.vs[this.av].dhxcont.mainCont[this.av].style.border = "#a4bed4 1px solid";
			this._redraw()
		}
		var obj = document.createElement("DIV");
		obj.id = "dhxAccordionObj_" + this._genStr(12);
		if (this.skin == "dhx_web") {
			obj.style.left = "0px";
			obj.style.top = "0px";
			obj.style.width = parseInt(this.vs[this.av].dhxcont.mainCont[this.av].style.width)
					+ "px";
			obj.style.height = parseInt(this.vs[this.av].dhxcont.mainCont[this.av].style.height)
					+ "px"
		} else {
			obj.style.left = "-1px";
			obj.style.top = "-1px";
			obj.style.width = parseInt(this.vs[this.av].dhxcont.mainCont[this.av].style.width)
					+ 2 + "px";
			obj.style.height = parseInt(this.vs[this.av].dhxcont.mainCont[this.av].style.height)
					+ 2 + "px"
		}
		obj.style.position = "relative";
		obj.cmp = "accordion";
		document.body.appendChild(obj);
		this.attachObject(obj.id, false, true);
		this.vs[this.av].accordion = new dhtmlXAccordion(obj.id, this.skin);
		this.vs[this.av].accordion.setSizes();
		this.vs[this.av].accordionId = obj.id;
		this.vs[this.av].accordionObj = obj;
		return this.vs[this._viewRestore()].accordion
	};
	this.obj.attachLayout = function(view, skin) {
		if (this._isCell && this.skin == "dhx_skyblue") {
			this.hideHeader();
			this.vs[this.av].dhxcont.style.border = "0px solid white";
			this.adjustContent(this.childNodes[0], 0)
		}
		if (this._isCell && this.skin == "dhx_web") {
			this.hideHeader()
		}
		var obj = document.createElement("DIV");
		obj.id = "dhxLayoutObj_" + this._genStr(12);
		obj.style.overflow = "hidden";
		obj.style.position = "absolute";
		obj.style.left = "0px";
		obj.style.top = "0px";
		obj.style.width = parseInt(this.vs[this.av].dhxcont.mainCont[this.av].style.width)
				+ "px";
		obj.style.height = parseInt(this.vs[this.av].dhxcont.mainCont[this.av].style.height)
				+ "px";
		if (this._isAcc && this.skin == "dhx_skyblue") {
			obj.style.left = "-1px";
			obj.style.top = "-1px";
			obj.style.width = parseInt(this.vs[this.av].dhxcont.mainCont[this.av].style.width)
					+ 2 + "px";
			obj.style.height = parseInt(this.vs[this.av].dhxcont.mainCont[this.av].style.height)
					+ 2 + "px"
		}
		obj.dhxContExists = true;
		obj.cmp = "layout";
		document.body.appendChild(obj);
		this.attachObject(obj.id, false, true);
		this.vs[this.av].layout = new dhtmlXLayoutObject(obj, view,
				(skin || this.skin));
		if (this._isWindow) {
			this.attachEvent("_onBeforeTryResize",
					this.vs[this.av].layout._defineWindowMinDimension)
		}
		this.vs[this.av].layoutId = obj.id;
		this.vs[this.av].layoutObj = obj;
		return this.vs[this._viewRestore()].layout
	};
	this.obj.attachEditor = function(skin) {
		if (this._isWindow && this.skin == "dhx_skyblue") {
			this.vs[this.av].dhxcont.mainCont[this.av].style.border = "#a4bed4 1px solid";
			this._redraw()
		}
		var obj = document.createElement("DIV");
		obj.id = "dhxEditorObj_" + this._genStr(12);
		obj.style.position = "relative";
		obj.style.display = "none";
		obj.style.overflow = "hidden";
		obj.style.width = "100%";
		obj.style.height = "100%";
		obj.cmp = "editor";
		document.body.appendChild(obj);
		this.attachObject(obj.id, false, true);
		this.vs[this.av].editor = new dhtmlXEditor(obj.id, this.skin);
		this.vs[this.av].editorId = obj.id;
		this.vs[this.av].editorObj = obj;
		return this.vs[this._viewRestore()].editor
	};
	this.obj.attachMap = function(opts) {
		var obj = document.createElement("DIV");
		obj.id = "GMapsObj_" + this._genStr(12);
		obj.style.position = "relative";
		obj.style.display = "none";
		obj.style.overflow = "hidden";
		obj.style.width = "100%";
		obj.style.height = "100%";
		obj.cmp = "gmaps";
		document.body.appendChild(obj);
		this.attachObject(obj.id, false, true);
		if (!opts) {
			opts = {
				center : new google.maps.LatLng(40.719837, -73.992348),
				zoom : 11,
				mapTypeId : google.maps.MapTypeId.ROADMAP
			}
		}
		this.vs[this.av].gmaps = new google.maps.Map(obj, opts);
		return this.vs[this.av].gmaps
	};
	this.obj.attachObject = function(obj, autoSize, localCall) {
		if (typeof(obj) == "string") {
			obj = document.getElementById(obj)
		}
		if (autoSize) {
			obj.style.visibility = "hidden";
			obj.style.display = "";
			var objW = obj.offsetWidth;
			var objH = obj.offsetHeight
		}
		this._attachContent("obj", obj);
		if (autoSize && this._isWindow) {
			obj.style.visibility = "visible";
			this._adjustToContent(objW, objH)
		}
		if (!localCall) {
			this._viewRestore()
		}
	};
	this.obj.detachObject = function(remove, moveTo) {
		var p = null;
		var pObj = null;
		var t = ["tree", "grid", "layout", "tabbar", "accordion", "folders"];
		for (var q = 0; q < t.length; q++) {
			if (this.vs[this.av][t[q]]) {
				p = this.vs[this.av][t[q]];
				pObj = this.vs[this.av][t[q] + "Obj"];
				if (remove) {
					if (p.unload) {
						p.unload()
					}
					if (p.destructor) {
						p.destructor()
					}
					while (pObj.childNodes.length > 0) {
						pObj.removeChild(pObj.childNodes[0])
					}
					pObj.parentNode.removeChild(pObj);
					pObj = null;
					p = null
				} else {
					document.body.appendChild(pObj);
					pObj.style.display = "none"
				}
				this.vs[this.av][t[q]] = null;
				this.vs[this.av][t[q] + "Id"] = null;
				this.vs[this.av][t[q] + "Obj"] = null
			}
		}
		if (p != null && pObj != null) {
			return new Array(p, pObj)
		}
		if (remove && this.vs[this.av]._frame) {
			this._detachURLEvents();
			this.vs[this.av]._frame = null
		}
		var objA = this.vs[this.av].dhxcont.mainCont[this.av];
		while (objA.childNodes.length > 0) {
			if (remove == true) {
				objA.removeChild(objA.childNodes[0])
			} else {
				var obj = objA.childNodes[0];
				if (moveTo != null) {
					if (typeof(moveTo) != "object") {
						moveTo = document.getElementById(moveTo)
					}
					moveTo.appendChild(obj)
				} else {
					document.body.appendChild(obj)
				}
				obj.style.display = "none"
			}
		}
	};
	this.obj.appendObject = function(obj) {
		if (typeof(obj) == "string") {
			obj = document.getElementById(obj)
		}
		this._attachContent("obj", obj, true)
	};
	this.obj.attachHTMLString = function(str) {
		this._attachContent("str", str);
		var z = str.match(/<script[^>]*>[^\f]*?<\/script>/g) || [];
		for (var i = 0; i < z.length; i++) {
			var s = z[i].replace(/<([\/]{0,1})script[^>]*>/g, "");
			if (window.execScript) {
				window.execScript(s)
			} else {
				window.eval(s)
			}
		}
	};
	this.obj.attachURL = function(url, ajax, parentWindow) {
		var parentContentIfa = document
				.getElementById("__dhtmlxwindowsiframe__");
		if (parentContentIfa) {
			parentContentIfa._parentWindow_ = parentWindow
		}
		this._attachContent((ajax == true ? "urlajax" : "url"), url, false);
		this._viewRestore()
	};
	this.obj.adjust = function() {
		if (this.skin == "dhx_skyblue") {
			if (this.vs[this.av].menu) {
				if (this._isWindow || this._isLayout) {
					this.vs[this.av].menu._topLevelOffsetLeft = 0;
					document.getElementById(this.vs[this.av].menuId).style.height = "26px";
					this.vs[this.av].menuHeight = document
							.getElementById(this.vs[this.av].menuId).offsetHeight;
					if (this._doOnAttachMenu) {
						this._doOnAttachMenu("show")
					}
				}
				if (this._isCell) {
					document.getElementById(this.vs[this.av].menuId).className += " in_layoutcell";
					this.vs[this.av].menuHeight = 25
				}
				if (this._isAcc) {
					document.getElementById(this.vs[this.av].menuId).className += " in_acccell";
					this.vs[this.av].menuHeight = 25
				}
				if (this._doOnAttachMenu) {
					this._doOnAttachMenu("adjust")
				}
			}
			if (this.vs[this.av].toolbar) {
				if (this._isWindow || this._isLayout) {
					document.getElementById(this.vs[this.av].toolbarId).style.height = "29px";
					this.vs[this.av].toolbarHeight = document
							.getElementById(this.vs[this.av].toolbarId).offsetHeight;
					if (this._doOnAttachToolbar) {
						this._doOnAttachToolbar("show")
					}
				}
				if (this._isCell) {
					document.getElementById(this.vs[this.av].toolbarId).className += " in_layoutcell"
				}
				if (this._isAcc) {
					document.getElementById(this.vs[this.av].toolbarId).className += " in_acccell"
				}
			}
		}
		if (this.skin == "dhx_web") {
		}
	};
	this.obj._attachContent = function(type, obj, append) {
		if (append !== true) {
			if (this.vs[this.av]._frame) {
				this._detachURLEvents();
				this.vs[this.av]._frame = null
			}
			while (this.vs[this.av].dhxcont.mainCont[this.av].childNodes.length > 0) {
				this.vs[this.av].dhxcont.mainCont[this.av]
						.removeChild(this.vs[this.av].dhxcont.mainCont[this.av].childNodes[0])
			}
		}
		if (type == "url") {
			if (this._isWindow && obj.cmp == null && this.skin == "dhx_skyblue") {
				this.vs[this.av].dhxcont.mainCont[this.av].style.border = "#a4bed4 1px solid";
				this._redraw()
			}
			var fr = document.createElement("IFRAME");
			fr.frameBorder = 0;
			fr.border = 0;
			fr.style.width = "100%";
			fr.style.height = "100%";
			fr.setAttribute("src", "javascript:false;");
			this.vs[this.av].dhxcont.mainCont[this.av].appendChild(fr);
			fr.src = obj;
			this.vs[this.av]._frame = fr;
			this._attachURLEvents()
		} else {
			if (type == "urlajax") {
				if (this._isWindow && obj.cmp == null
						&& this.skin == "dhx_skyblue") {
					this.vs[this.av].dhxcont.mainCont[this.av].style.border = "#a4bed4 1px solid";
					this.vs[this.av].dhxcont.mainCont[this.av].style.backgroundColor = "#FFFFFF";
					this._redraw()
				}
				var t = this;
				var xmlParser = function() {
					t.attachHTMLString(this.xmlDoc.responseText, this);
					if (t._doOnFrameContentLoaded) {
						t._doOnFrameContentLoaded()
					}
					this.destructor()
				};
				var xmlLoader = new dtmlXMLLoaderObject(xmlParser, window);
				xmlLoader.dhxWindowObject = this;
				xmlLoader.loadXML(obj)
			} else {
				if (type == "obj") {
					if (this._isWindow && obj.cmp == null
							&& this.skin == "dhx_skyblue") {
						this.vs[this.av].dhxcont.mainCont[this.av].style.border = "#a4bed4 1px solid";
						this.vs[this.av].dhxcont.mainCont[this.av].style.backgroundColor = "#FFFFFF";
						this._redraw()
					}
					this.vs[this.av].dhxcont._frame = null;
					this.vs[this.av].dhxcont.mainCont[this.av].appendChild(obj);
					this.vs[this.av].dhxcont.mainCont[this.av].style.overflow = (append === true
							? "auto"
							: "hidden");
					obj.style.display = ""
				} else {
					if (type == "str") {
						if (this._isWindow && obj.cmp == null
								&& this.skin == "dhx_skyblue") {
							this.vs[this.av].dhxcont.mainCont[this.av].style.border = "#a4bed4 1px solid";
							this.vs[this.av].dhxcont.mainCont[this.av].style.backgroundColor = "#FFFFFF";
							this._redraw()
						}
						this.vs[this.av].dhxcont._frame = null;
						this.vs[this.av].dhxcont.mainCont[this.av].innerHTML = obj
					}
				}
			}
		}
	};
	this.obj._attachURLEvents = function() {
		var t = this;
		var fr = this.vs[this.av]._frame;
		if (_isIE) {
			fr.onreadystatechange = function(a) {
				if (fr.readyState == "complete") {
					try {
						fr.contentWindow.document.body.onmousedown = function() {
							if (t._doOnFrameMouseDown) {
								t._doOnFrameMouseDown()
							}
						}
					} catch (e) {
					}
					try {
						if (t._doOnFrameContentLoaded) {
							t._doOnFrameContentLoaded()
						}
					} catch (e) {
					}
				}
			}
		} else {
			fr.onload = function() {
				try {
					fr.contentWindow.onmousedown = function() {
						if (t._doOnFrameMouseDown) {
							t._doOnFrameMouseDown()
						}
					}
				} catch (e) {
				}
				try {
					if (t._doOnFrameContentLoaded) {
						t._doOnFrameContentLoaded()
					}
				} catch (e) {
				}
			}
		}
	};
	this.obj._detachURLEvents = function() {
		if (_isIE) {
			try {
				this.vs[this.av]._frame.onreadystatechange = null;
				this.vs[this.av]._frame.contentWindow.document.body.onmousedown = null;
				this.vs[this.av]._frame.onload = null
			} catch (e) {
			}
		} else {
			try {
				this.vs[this.av]._frame.contentWindow.onmousedown = null;
				this.vs[this.av]._frame.onload = null
			} catch (e) {
			}
		}
	};
	this.obj.showMenu = function() {
		if (!(this.vs[this.av].menu && this.vs[this.av].menuId)) {
			return
		}
		if (document.getElementById(this.vs[this.av].menuId).style.display != "none") {
			return
		}
		this.vs[this.av].menuHidden = false;
		if (this._doOnAttachMenu) {
			this._doOnAttachMenu("show")
		}
		document.getElementById(this.vs[this.av].menuId).style.display = "";
		this._viewRestore()
	};
	this.obj.hideMenu = function() {
		if (!(this.vs[this.av].menu && this.vs[this.av].menuId)) {
			return
		}
		if (document.getElementById(this.vs[this.av].menuId).style.display == "none") {
			return
		}
		document.getElementById(this.vs[this.av].menuId).style.display = "none";
		this.vs[this.av].menuHidden = true;
		if (this._doOnAttachMenu) {
			this._doOnAttachMenu("hide")
		}
		this._viewRestore()
	};
	this.obj.showToolbar = function() {
		if (!(this.vs[this.av].toolbar && this.vs[this.av].toolbarId)) {
			return
		}
		if (document.getElementById(this.vs[this.av].toolbarId).style.display != "none") {
			return
		}
		this.vs[this.av].toolbarHidden = false;
		if (this._doOnAttachToolbar) {
			this._doOnAttachToolbar("show")
		}
		document.getElementById(this.vs[this.av].toolbarId).style.display = "";
		this._viewRestore()
	};
	this.obj.hideToolbar = function() {
		if (!(this.vs[this.av].toolbar && this.vs[this.av].toolbarId)) {
			return
		}
		if (document.getElementById(this.vs[this.av].toolbarId).style.display == "none") {
			return
		}
		this.vs[this.av].toolbarHidden = true;
		document.getElementById(this.vs[this.av].toolbarId).style.display = "none";
		if (this._doOnAttachToolbar) {
			this._doOnAttachToolbar("hide")
		}
		this._viewRestore()
	};
	this.obj.showStatusBar = function() {
		if (!(this.vs[this.av].sb && this.vs[this.av].sbId)) {
			return
		}
		if (document.getElementById(this.vs[this.av].sbId).style.display != "none") {
			return
		}
		this.vs[this.av].sbHidden = false;
		if (this._doOnAttachStatusBar) {
			this._doOnAttachStatusBar("show")
		}
		document.getElementById(this.vs[this.av].sbId).style.display = "";
		this._viewRestore()
	};
	this.obj.hideStatusBar = function() {
		if (!(this.vs[this.av].sb && this.vs[this.av].sbId)) {
			return
		}
		if (document.getElementById(this.vs[this.av].sbId).style.display == "none") {
			return
		}
		this.vs[this.av].sbHidden = true;
		document.getElementById(this.vs[this.av].sbId).style.display = "none";
		if (this._doOnAttachStatusBar) {
			this._doOnAttachStatusBar("hide")
		}
		this._viewRestore()
	};
	this.obj._dhxContDestruct = function() {
		var av = this.av;
		for (var a in this.vs) {
			this.av = a;
			this.detachMenu();
			this.detachToolbar();
			this.detachStatusBar();
			this.detachObject(true);
			this.vs[a].dhxcont.mainCont[a].parentNode
					.removeChild(this.vs[a].dhxcont.mainCont[a]);
			this.vs[a].dhxcont.mainCont[a] = null
		}
		this.vs[this.dv].dhxcont.mainCont = null;
		this.vs[this.dv].dhxcont.parentNode
				.removeChild(this.vs[this.dv].dhxcont);
		for (var a in this.vs) {
			this.vs[a].dhxcont = null
		}
		this.vs = null;
		this.attachMenu = null;
		this.attachToolbar = null;
		this.attachStatusBar = null;
		this.detachMenu = null;
		this.detachToolbar = null;
		this.detachStatusBar = null;
		this.showMenu = null;
		this.showToolbar = null;
		this.showStatusBar = null;
		this.hideMenu = null;
		this.hideToolbar = null;
		this.hideStatusBar = null;
		this.attachGrid = null;
		this.attachScheduler = null;
		this.attachTree = null;
		this.attachTabbar = null;
		this.attachFolders = null;
		this.attachAccordion = null;
		this.attachLayout = null;
		this.attachEditor = null;
		this.attachObject = null;
		this.detachObject = null;
		this.appendObject = null;
		this.attachHTMLString = null;
		this.attachURL = null;
		this.view = null;
		this.show = null;
		this.adjust = null;
		this.setMinContentSize = null;
		this.moveContentTo = null;
		this.adjustContent = null;
		this.coverBlocker = null;
		this.showCoverBlocker = null;
		this.hideCoverBlocker = null;
		this.updateNestedObjects = null;
		this._attachContent = null;
		this._attachURLEvents = null;
		this._detachURLEvents = null;
		this._viewRestore = null;
		this._setPadding = null;
		this._init = null;
		this._genStr = null;
		this._dhxContDestruct = null;
		that.st.parentNode.removeChild(that.st);
		that.st = null;
		that.setContent = null;
		that.dhxcont = null;
		that.obj = null;
		that = null;
		if (dhtmlx.detaches) {
			for (var a in dhtmlx.detaches) {
				dhtmlx.detaches[a](this)
			}
		}
	};
	if (dhtmlx.attaches) {
		for (var a in dhtmlx.attaches) {
			this.obj[a] = dhtmlx.attaches[a]
		}
	}
}
function dhtmlXComboFromSelect(h, n) {
	if (typeof(h) == "string") {
		h = document.getElementById(h)
	}
	var g;
	var s;
	if (h.tagName == "SPAN") {
		g = h;
		var e = $(h).siblings(".dhx-hide-select")[0];
		var s = e.getAttribute("opt_type");
		var d = "" + e.getAttribute("multiple");
		if ((s && s == "checkbox") || d == "true") {
			s = "checkbox"
		}
		n = n
				|| e.getAttribute("width")
				|| (window.getComputedStyle
						? window.getComputedStyle(e, null)["width"]
						: (e.currentStyle ? e.currentStyle.width : 0));
		if ((!n) || (n == "auto")) {
			n = e.getAttribute("width");
			n = parseInt(n, 10);
			if (isNaN(n)) {
				e.style.display = "block";
				n = e.offsetWidth;
				e.style.display = "none"
			}
			if (n == 0) {
				var l = e.cloneNode(true);
				l.style.display = "block";
				document.body.insertBefore(l);
				n = l.offsetWidth;
				l.removeNode(true)
			}
			n = n < 30 ? 30 : n
		}
	} else {
		n = n
				|| h.getAttribute("width")
				|| (window.getComputedStyle
						? window.getComputedStyle(h, null)["width"]
						: (h.currentStyle ? h.currentStyle.width : 0));
		if ((!n) || (n == "auto")) {
			n = h.offsetWidth;
			if (n == 0) {
				var l = h.cloneNode(true);
				document.body.insertBefore(l);
				n = l.offsetWidth;
				l.removeNode(true)
			}
			n = n < 30 ? 30 : n
		}
		g = document.createElement("SPAN");
		if (h.style.direction == "rtl") {
			g.style.direction = "rtl"
		}
		h.parentNode.insertBefore(g, h);
		h.style.display = "none";
		s = h.getAttribute("opt_type");
		var d = "" + h.getAttribute("multiple");
		if ((s && s == "checkbox") || d == "true") {
			s = "checkbox"
		}
	}
	var k = null;
	var o = [];
	if (h.tagName == "SPAN") {
		k = new dhtmlXCombo(g, e, n, s);
		k.source = e;
		o = k._initOptions(k.source);
		var a = parseInt(n);
		if (k.ischk && a == n) {
			k.setSize(a + 20)
		} else {
			k.setSize(a)
		}
	} else {
		k = new dhtmlXCombo(g, h, n, s);
		var j = new Array();
		for (var q = 0; q < h.options.length; q++) {
			var p = h.options[q];
			if (p.selected) {
				o.push(q)
			}
			var f = p.text;
			var u = p.value;
			if (!u) {
				u = f
			}
			j[q] = {
				value : u,
				text : f,
				index : q
			}
		}
		k.addOptions(j);
		k.source = h
	}
	var v = h.name;
	if (v) {
		h.removeAttribute("name");
		k.setName(v)
	}
	var c = h.getAttribute("class");
	var m = k.DOMelem_input.getAttribute("class");
	if (c) {
		k.DOMelem_input.className = c.replace("easyui-combobox", "") + " " + m
	}
	if (o.length > 0) {
		if (k.ischk) {
			k.selectOption(o.join(","))
		} else {
			k.selectOption(o[o.length - 1])
		}
		k.tempVal = o.join(",")
	}
	var r = k.source.getAttribute("selectedVal");
	if (r) {
		k.tempVal = r
	}
	if (k.source.onchange) {
		k.attachEvent("onChange", k.source.onchange)
	}
	return k
}
var dhtmlXCombo_optionTypes = [];
function dhtmlXCombo(k, f, d, h) {
	if (f) {
		if (!f.id) {
			f.id = "_" + (new Date().getTime())
		}
		this.source = f;
		var c = f.id;
		f.removeAttribute("id");
		var a = f.name;
		f.removeAttribute("name");
		var e = f.tabIndex;
		var g = f.required;
		var j = f.validType;
		this.tempVal = "";
		this.required = g;
		this.validType = j;
		this.selSource = f;
		this.timeoutIndex = null;
		this.keypressVal = "";
		this.isAfterFilter = false;
		this._afterChangeFlg = false;
		f.combobox = this
	}
	if (typeof(k) == "string") {
		k = document.getElementById(k)
	}
	this.dhx_Event();
	this.ischk = h == "checkbox" ? true : false;
	this.optionType = (h != undefined && dhtmlXCombo_optionTypes[h])
			? h
			: "default";
	this._optionObject = dhtmlXCombo_optionTypes[this.optionType];
	this._disabled = false;
	if (k.style.direction == "rtl") {
		this.rtl = true
	} else {
		this.rtl = false
	}
	if (!window.dhx_glbSelectAr) {
		window.dhx_glbSelectAr = new Array();
		window.dhx_openedSelect = null;
		window.dhx_SelectId = 1;
		dhtmlxEvent(document.body, "click", this.closeAll);
		dhtmlxEvent(document.body, "keydown", function(l) {
					try {
						if ((l || event).keyCode == 9) {
							window.dhx_glbSelectAr[0].closeAll()
						}
					} catch (l) {
					}
					return true
				})
	}
	if (k.tagName == "SPAN") {
		this._createSelf(k, c, a, d, e)
	} else {
		return dhtmlXComboFromSelect(k)
	}
	dhx_glbSelectAr.push(this)
}
dhtmlXCombo.prototype.setSize = function(a) {
	this.DOMlist.style.width = a + "px";
	if (this.DOMlistF) {
		this.DOMlistF.style.width = a + "px"
	}
	this.DOMelem.style.width = a + "px";
	this.DOMelem_input.style.width = Math.max(0, (a - 19)) + 2 + "px"
};
dhtmlXCombo.prototype.disable = function(c) {
	var a = convertStringToBoolean(c);
	this.DOMelem_input.readOnly = a;
	this.DOMelem_hidden_input.disabled = a;
	this.DOMelem_input.disabled = a;
	this._disabled = a
};
dhtmlXCombo.prototype.setReadOnly = function(c) {
	var a = convertStringToBoolean(c);
	this.DOMelem_input.readOnly = a;
	this.DOMelem_input.disabled = false;
	this.DOMelem_hidden_input.disabled = false;
	this._disabled = a;
	if (this.DOMelem_button && this.DOMelem_button.style) {
		if (a) {
			this.DOMelem_button.style.display = "none"
		} else {
			this.DOMelem_button.style.display = "block"
		}
	}
};
dhtmlXCombo.prototype.readonly = function(a, c) {
};
dhtmlXCombo.prototype.enableFilteringMode = function(e, d, c, a) {
	this._filter = convertStringToBoolean(e);
	if (d) {
		this._xml = d;
		this._autoxml = convertStringToBoolean(a)
	}
	if (convertStringToBoolean(c)) {
		this._xmlCache = []
	}
};
dhtmlXCombo.prototype.setFilteringParam = function(a, c) {
	if (!this._prs) {
		this._prs = []
	}
	this._prs.push([a, c])
};
dhtmlXCombo.prototype.getOption = function(c) {
	for (var a = 0; a < this.optionsArr.length; a++) {
		if (this.optionsArr[a].value == c) {
			return this.optionsArr[a]
		}
	}
	return null
};
dhtmlXCombo.prototype.getOptionByLabel = function(c) {
	for (var a = 0; a < this.optionsArr.length; a++) {
		if (this.optionsArr[a].text == c || this.optionsArr[a]._ctext == c) {
			return this.optionsArr[a]
		}
	}
	return null
};
dhtmlXCombo.prototype.getOptionByIndex = function(a) {
	return this.optionsArr[a]
};
dhtmlXCombo.prototype.clearAll = function() {
	this._selOption = [];
	this._tempSel = null;
	this.DOMelem_hidden_input.value = "";
	this.DOMelem_input.value = "";
	this.DOMelem_input.title = "";
	this.DOMelem_button.title = "";
	this.optionsArr = [];
	this.redrawOptions()
};
dhtmlXCombo.prototype.deleteOption = function(c) {
	var a = this.getIndexByValue(c);
	if (a < 0) {
		return
	}
	if (this.optionsArr[a] == this._selOption) {
		this._selOption = null
	}
	this.optionsArr.splice(a, 1);
	this.redrawOptions()
};
dhtmlXCombo.prototype.render = function(a) {
	this._skiprender = (!convertStringToBoolean(a));
	this.redrawOptions()
};
dhtmlXCombo.prototype.loadJsonData = function(param) {
	if (typeof(param) != "object") {
		param = eval("(" + param + ")")
	}
	var selOptions = [];
	var x = [];
	var selectedIndex = [];
	$.each(param, function(i, data) {
				var text = data.name;
				var value = data.id;
				var option = new Option(text, value);
				var selected = false;
				if (data.selected) {
					option.selected = true;
					selectedIndex.push(i)
				}
				selOptions.push(option);
				x[i] = {
					value : value,
					text : text,
					index : i
				}
			});
	this.clearAll();
	this.addOptions(x);
	if (this.ischk) {
		this.selectOption(selectedIndex.join(","))
	} else {
		this.selectOption(selectedIndex[selectedIndex.length - 1])
	}
	var selObj = this.selSource;
	selObj.options.length = 0;
	for (var i = 0; i < selOptions.length; i++) {
		selObj.options.add(selOptions[i])
	}
	var combobox = this;
	setTimeout(function() {
				var width = selObj.getAttribute("width");
				width = parseInt(width, 10);
				if (isNaN(width)) {
					selObj.style.display = "block";
					width = selObj.offsetWidth;
					selObj.style.display = "none"
				}
				if (width == 0) {
					var tempclonenode = selObj.cloneNode(true);
					tempclonenode.style.display = "block";
					document.body.insertBefore(tempclonenode);
					width = tempclonenode.offsetWidth;
					tempclonenode.removeNode(true)
				}
				width = width < 30 ? 30 : width;
				combobox.setSize(width)
			}, 100)
};
dhtmlXCombo.prototype.addOptions = function(e) {
	this.render(false);
	var g = this.optionsArr.length;
	for (var f = 0; f < e.length; f++) {
		var h = e[f];
		if (this.ischk) {
			var j = h.value;
			var a = h.text;
			if (!j && "" + j != "0") {
				g--;
				continue
			}
		}
		var c = new this._optionObject();
		h.index = g + f;
		c.setValue.call(c, h);
		if (h.selected) {
			this._selOption.add(c)
		}
		this.optionsArr.push(c);
		var d = ("" + h.value).toUpperCase();
		this.valueMap[d] = g + f
	}
	this._setSelectedOption(false);
	this.render(true)
};
dhtmlXCombo.prototype._initOptions = function(a) {
	var d = [];
	var k = a.options;
	var j = a.value;
	var h = k.length;
	for (var e = 0; e < h; e++) {
		var c = new this._optionObject();
		var f = {};
		f.text = k[e].text;
		f.value = k[e].value;
		f.index = e;
		c.setValue.call(c, f);
		if (k[e].selected) {
			d.push(e)
		}
		if (f.value == j) {
			this._selOption.add(c)
		}
		c.content = this.DOMlist.childNodes[e];
		this.optionsArr.push(c);
		var g = ("" + f.value).toUpperCase();
		this.valueMap[g] = e;
		this.DOMlist.childNodes[e]._self = c;
		if (this.ischk) {
			c.content.firstChild.onclick = function(m) {
				if (!this.parentNode._self.value) {
					return false
				}
				c.selected = this.checked;
				var l = this.parentNode.parentNode.combo;
				l._afterChangeFlg = true;
				l.DOMelem_input.focus();
				(m || event).cancelBubble = true;
				var n = null;
				if (this.parentNode._self) {
					n = this.parentNode._self.index
				}
				if (this.checked) {
					l.selectOption(n, n)
				} else {
					l.unSelectOption(n)
				}
			}
		}
	}
	this._setSelectedOption(false);
	return d
};
dhtmlXCombo.prototype.addOption = function(e, g, d) {
	var c = {
		value : e,
		text : g,
		selected : d
	};
	this.addOptions([c]);
	var f = this.selSource;
	f.options.add(new Option(g, e));
	var a = this;
	setTimeout(function() {
				var h = f.getAttribute("width");
				h = parseInt(h, 10);
				if (isNaN(h)) {
					f.style.display = "block";
					h = f.offsetWidth;
					f.style.display = "none"
				}
				a.setSize(h)
			}, 100)
};
dhtmlXCombo.prototype._setSelectedOption = function(c) {
	var n = [];
	var h = [];
	for (var e = 0; e < this._selOption.length; e++) {
		var g = this._selOption[e];
		g.select();
		g.render();
		n.push(g.text);
		h.push(g.value)
	}
	var f = h.join(",");
	var j = n.join(",");
	this.DOMelem_hidden_input.value = f;
	this.value = f;
	this.DOMelem_input.value = j;
	this.DOMelem_input.title = j;
	this.DOMelem_button.title = j;
	var m = this.source;
	if (m) {
		var a = m.options;
		var l = "," + f + ",";
		for (var k = 0; k < a.length; k++) {
			var d = a[k].value;
			if (l.indexOf("," + d + ",") > -1) {
				a[k].selected = true
			} else {
				a[k].selected = false
			}
		}
	}
	if (c && !this._afterChangeFlg) {
		this._afterChangeFlg = false;
		this.callEvent("onChange", [])
	}
};
dhtmlXCombo.prototype.execChange = function() {
	this.callEvent("onChange", [])
};
dhtmlXCombo.prototype.getIndexByValue = function(c) {
	for (var a = 0; a < this.optionsArr.length; a++) {
		if (this.optionsArr[a].value == c) {
			return a
		}
	}
	return -1
};
dhtmlXCombo.prototype.getValue = function() {
	return this.DOMelem_hidden_input.value
};
dhtmlXCombo.prototype.getText = function() {
	return this.DOMelem_input.value
};
dhtmlXCombo.prototype.setValue = function(h, f) {
	if (!f) {
		this.unSelectOption()
	}
	if (h || "" + h == "0") {
		h = "" + h;
		var g = h.split(",");
		var d = [];
		for (var e = 0; e < g.length; e++) {
			var a = ("" + g[e]).toUpperCase();
			var c = this.valueMap[a];
			if (c || c == 0) {
				d.push(c)
			}
		}
		if (f) {
			if (d.length > 0) {
				this.unSelectOption();
				this.selectOption(d)
			}
		} else {
			this.selectOption(d)
		}
	}
};
dhtmlXCombo.prototype.setTempValue = function(a) {
	this.tempVal = a
};
dhtmlXCombo.prototype.setName = function(a) {
	this.DOMelem_hidden_input.name = a;
	this.name = a
};
dhtmlXCombo.prototype.show = function(a) {
	if (convertStringToBoolean(a)) {
		this.DOMelem.style.display = ""
	} else {
		this.DOMelem.style.display = "none"
	}
};
dhtmlXCombo.prototype.destructor = function() {
	var a = this._inID;
	this.DOMParent.removeChild(this.DOMelem);
	this.DOMlist.parentNode.removeChild(this.DOMlist);
	var d = dhx_glbSelectAr;
	this.DOMParent = this.DOMlist = this.DOMelem = 0;
	this.DOMlist.combo = this.DOMelem.combo = 0;
	for (var c = 0; c < d.length; c++) {
		if (d[c]._inID == a) {
			d[c] = null;
			d.splice(c, 1);
			return
		}
	}
};
dhtmlXCombo.prototype._createSelf = function(d, f, e, g, j) {
	if (g.toString().indexOf("%") != -1) {
		var k = this;
		var c = parseInt(g) / 100;
		window.setInterval(function() {
					if (!d.parentNode) {
						return
					}
					var l = d.parentNode.offsetWidth * c - 2;
					if (l < 0) {
						return
					}
					if (l == k._lastTs) {
						return
					}
					k.setSize(k._lastTs = l)
				}, 500);
		var g = parseInt(d.offsetWidth)
	}
	var g = parseInt(g || 30);
	if (g < 30) {
		g = 30
	}
	this.ListPosition = "Bottom";
	this.DOMParent = d;
	this._inID = (new Date()).getTime() + parseInt(Math.random() * 100000, 10);
	this.name = e;
	this.value = "";
	this.text = "";
	this.id = f;
	this._selOption = [];
	this.valueMap = {};
	this.optionsArr = Array();
	var h = new this._optionObject();
	var a = $(d).next(".dhx_combo_list");
	if (a.size() > 0) {
		this.DOMlist = a[0];
		a.detach();
		this.DOMelem = $(".dhx_combo_box", d)[0];
		this.DOMelem_input = $(".dhx_combo_input", d)[0];
		this.DOMelem_hidden_input = $(".combobox-value", d)[0];
		this.DOMelem_hidden_input._self = this;
		if (this.source.onchange) {
			this.DOMelem_hidden_input.onchange = this.source.onchange
		}
		this.DOMelem_button = $(".dhx_combo_img", d)[0]
	} else {
		if (this.ischk) {
			g += 20
		}
		h.DrawHeader(this, f, e, g, j);
		this.DOMlist = document.createElement("DIV");
		this.DOMlist.className = "dhx_combo_list" + (this.rtl ? "_rtl" : "")
				+ " " + (dhtmlx.skin ? dhtmlx.skin + "_list" : "");
		this.DOMlist.style.width = g - (_isIE ? 0 : 0) + "px";
		if (_isOpera || _isKHTML) {
			this.DOMlist.style.overflow = "auto"
		}
		this.DOMlist.style.display = "none"
	}
	document.body.insertBefore(this.DOMlist, document.body.firstChild);
	if (_isIE) {
		this.DOMlistF = document.createElement("IFRAME");
		this.DOMlistF.style.border = "0px";
		this.DOMlistF.className = "dhx_combo_list";
		this.DOMlistF.style.width = g - (_isIE ? 0 : 0) + "px";
		this.DOMlistF.style.display = "none";
		this.DOMlistF.src = "javascript:false;";
		document.body.insertBefore(this.DOMlistF, document.body.firstChild)
	}
	this.DOMlist.combo = this.DOMelem.combo = this;
	this.DOMelem_input.onkeydown = this._onKey;
	this.DOMelem.onclick = this._toggleSelect;
	this.DOMlist.onclick = this._selectOption;
	this.DOMlist.onmousedown = function() {
		this._skipBlur = true
	};
	this.DOMlist.onkeydown = function(l) {
		this.combo.DOMelem_input.focus();
		(l || event).cancelBubble = true;
		this.combo.DOMelem_input.onkeydown(l)
	};
	this.DOMlist.onmouseover = this._listOver
};
dhtmlXCombo.prototype._onKey = function(f) {
	var d = this.parentNode.combo;
	var c = (f || event).keyCode;
	if (c > 15 && c < 19) {
		return true
	}
	if (c == 40) {
		d.selectNext(1)
	} else {
		if (c == 38) {
			d.selectNext(-1)
		} else {
			if (c == 13) {
				var a = d.selectedIndex;
				d.selectOption(a);
				d.closeAll()
			} else {
				if (c == 27) {
					d.closeAll()
				} else {
					d.openSelect();
					if (c != 32) {
						window.setTimeout(function() {
									d.filterSelf((c == 8) || (c == 46))
								}, 1)
					}
				}
			}
		}
	}
};
dhtmlXCombo.prototype.filterSelf = function(j) {
	window._dhtmlSelectedOption = this;
	this.isAfterFilter = true;
	var h = this.getText();
	h = h.replace(/(^\s*)|(\s*$)/g, "");
	if (this._xml) {
		this._lkmode = j;
		return this._fetchOptions(0, h)
	}
	try {
		var d = new RegExp("^" + h, "i")
	} catch (f) {
		var d = new RegExp(h.replace(/([\[\]\{\}\(\)\+\*\\])/g, "\\$1"))
	}
	var c = true;
	for (var a = 0; a < this.optionsArr.length; a++) {
		var g = d.test(this.optionsArr[a].text);
		this.optionsArr[a].hide(!g);
		if (g && c) {
			this.optionsArr[a].select(true);
			c = false
		}
	}
};
dhtmlXCombo.prototype.selectNext = function(d) {
	var c = this.selectedIndex;
	if (!c && c != 0) {
		c = -1
	} else {
		this.optionsArr[c].deselect(this.ischk)
	}
	c = c + d;
	var e = this.optionsArr.length;
	if (c < 0) {
		c = 0
	} else {
		if (c > e - 1) {
			c = 0
		}
	}
	var a = e;
	while (a > 0) {
		if (this.optionsArr[c].isHidden()) {
			c++
		}
		if (c > e - 1) {
			c = 0
		}
		a--
	}
	if (this.ischk) {
		this.optionsArr[c].select(true)
	} else {
		this.selectOption(c)
	}
};
dhtmlXCombo.prototype.getSelectedIndex = function() {
	var a = this._selOption.length;
	if (a > 0) {
		var c = [];
		for (var d = 0; d < a; d++) {
			var e = this._selOption[d];
			c.push(e.index)
		}
		return c.join(",")
	}
	return -1
};
dhtmlXCombo.prototype._listOver = function(d) {
	d = d || event;
	d.cancelBubble = true;
	var c = (_isIE ? event.srcElement : d.target);
	var a = this.combo;
	if (c.parentNode == a.DOMlist) {
		var f = c._self;
		if (a._tempSel && a._tempSel != f) {
			if (a._selOption.indexOf(a._tempSel) < 0) {
				a._tempSel.deselect()
			}
		}
		if (!a.ischk && a._selOption[0]) {
			a._selOption[0].deselect()
		}
		a._tempSel = f;
		a._tempSel.select(true)
	}
};
dhtmlXCombo.prototype._positList = function() {
	var a = this.getPosition(this.DOMelem);
	if (this.ListPosition == "Bottom") {
		this.DOMlist.style.top = a[1] + this.DOMelem.offsetHeight - 1 + "px";
		this.DOMlist.style.left = a[0] + "px"
	} else {
		if (this.ListPosition == "Top") {
			this.DOMlist.style.top = a[1] - this.DOMlist.offsetHeight + "px";
			this.DOMlist.style.left = a[0] + "px"
		} else {
			this.DOMlist.style.top = a[1] + "px";
			this.DOMlist.style.left = a[0] + this.DOMelem.offsetWidth + "px"
		}
	}
};
dhtmlXCombo.prototype.getPosition = function(f, c) {
	if (_isChrome) {
		if (!c) {
			var c = document.body
		}
		var a = f;
		var e = 0;
		var d = 0;
		while ((a) && (a != c)) {
			e += a.offsetLeft - a.scrollLeft;
			d += a.offsetTop - a.scrollTop;
			a = a.offsetParent
		}
		if (c == document.body) {
			if (_isIE && _isIE < 8) {
				if (document.documentElement.scrollTop) {
					d += document.documentElement.scrollTop
				}
				if (document.documentElement.scrollLeft) {
					e += document.documentElement.scrollLeft
				}
			} else {
				if (!_isFF) {
					e += document.body.offsetLeft;
					d += document.body.offsetTop
				}
			}
		}
		return new Array(e, d)
	}
	var g = getOffset(f);
	return [g.left, g.top]
};
dhtmlXCombo.prototype.redrawOptions = function() {
	if (this._skiprender) {
		return
	}
	for (var c = this.DOMlist.childNodes.length - 1; c >= 0; c--) {
		this.DOMlist.removeChild(this.DOMlist.childNodes[c])
	}
	for (var c = 0; c < this.optionsArr.length; c++) {
		this.DOMlist.appendChild(this.optionsArr[c].render())
	}
	var e = 300;
	var f = 100;
	this.DOMlist.style.display = "block";
	var d = this.DOMlist.scrollHeight + 2;
	var a = 0;
	a = Math.max(f, d);
	if (d >= f) {
		a = Math.min(e, d)
	}
	this.DOMlist.style.height = a + "px";
	if (this.DOMlistF) {
		this.DOMlistF.style.height = a + "px"
	}
	this.DOMlist.style.display = "none"
};
dhtmlXCombo.prototype.unSelectOption = function(d) {
	var c = this._selOption.length;
	if (d || d == 0) {
		var a = this.optionsArr[d];
		var e = this._selOption.indexOf(a);
		if (e > -1) {
			this._selOption[e].deselect();
			this._selOption.remove(e)
		}
	} else {
		for (var e = 0; e < this._selOption.length; e++) {
			this._selOption[e].deselect()
		}
		this._selOption = []
	}
	var f = this._selOption.length;
	var g = false;
	if (c != f) {
		g = true
	}
	this._setSelectedOption(g)
};
dhtmlXCombo.prototype.selectOption = function(c, a) {
	var d = false;
	if (c || c == 0) {
		var h = ("" + c).split(",");
		for (var e = 0; e < h.length; e++) {
			var k = h[e];
			var f = this.optionsArr[k];
			if (f == this._tempSel) {
				this._tempSel = null
			}
			if (f) {
				var j = false;
				if (!this.ischk) {
					j = this._selOption[0] == f;
					this._selOption = []
				}
				d = this._selOption.add(f);
				if (j) {
					d = true
				}
			}
		}
	}
	var g = null;
	if (a || a == 0) {
		g = this.optionsArr[a]
	} else {
		g = this._selOption[0]
	}
	if (g) {
		var l = g.content.offsetTop + g.content.offsetHeight
				- this.DOMlist.scrollTop - this.DOMlist.offsetHeight;
		if (l > 0) {
			this.DOMlist.scrollTop += l
		}
		l = this.DOMlist.scrollTop - g.content.offsetTop;
		if (l > 0) {
			this.DOMlist.scrollTop -= l
		}
	}
	this._setSelectedOption(d)
};
dhtmlXCombo.prototype._selectOption = function(f) {
	(f || event).cancelBubble = true;
	var d = (_isIE ? event.srcElement : f.target);
	var c = this.combo;
	while (!d._self) {
		d = d.parentNode;
		if (!d) {
			return
		}
	}
	var a = d._self.index;
	c.selectOption(a, a);
	c.closeAll();
	c.callEvent("onBlur", []);
	c._activeMode = false
};
dhtmlXCombo.prototype.openSelect = function() {
	if (this._disabled) {
		return
	}
	this._positList();
	this.DOMlist.style.display = "block";
	this.callEvent("onOpen", []);
	if (this._tempSel && this._selOption.indexOf(this._tempSel) < 0) {
		this._tempSel.deselect()
	}
	var a = this._selOption[0];
	if (a) {
		a.select();
		var c = a.content.offsetTop + a.content.offsetHeight
				- this.DOMlist.scrollTop - this.DOMlist.offsetHeight;
		if (c > 0) {
			this.DOMlist.scrollTop += c
		}
		c = this.DOMlist.scrollTop - a.content.offsetTop;
		if (c > 0) {
			this.DOMlist.scrollTop -= c
		}
	}
	if (_isIE) {
		this._IEFix(true)
	}
	this.DOMelem_input.focus()
};
dhtmlXCombo.prototype._toggleSelect = function(g) {
	var f = this.combo;
	if (f.DOMlist.style.display == "block") {
		f.closeAll()
	} else {
		var k = 300;
		var l = 100;
		f.DOMlist.style.display = "block";
		var m = f.DOMlist.scrollHeight + 2;
		var h = 0;
		h = Math.max(l, m);
		if (m >= l) {
			h = Math.min(k, m)
		}
		f.DOMlist.style.height = h + "px";
		if (f.DOMlistF) {
			f.DOMlistF.style.height = h + "px"
		}
		var d = f.source;
		var j = d.style.width;
		if (j) {
			var c = d.cloneNode(true);
			c.style.width = "";
			c.style.display = "block";
			document.body.insertBefore(c);
			var a = c.offsetWidth;
			c.removeNode(true);
			if (parseInt(a) > parseInt(j)) {
				f.DOMlist.style.width = a
			}
		}
		f.openSelect()
	}
	(g || event).cancelBubble = true
};
dhtmlXCombo.prototype._IEFix = function(a) {
	this.DOMlistF.style.display = (a ? "block" : "none");
	this.DOMlistF.style.top = this.DOMlist.style.top;
	this.DOMlistF.style.left = this.DOMlist.style.left
};
dhtmlXCombo.prototype.closeAll = function() {
	if (window.dhx_glbSelectAr) {
		for (var e = 0; e < dhx_glbSelectAr.length; e++) {
			var g = dhx_glbSelectAr[e];
			if (g.DOMlist.style.display == "block") {
				g.DOMlist.style.display = "none";
				if (_isIE) {
					g._IEFix(false)
				}
				if (g._afterChangeFlg) {
					g._afterChangeFlg = false;
					g.callEvent("onChange", [])
				}
			}
			g._activeMode = false;
			var c = 0;
			var f = null;
			for (var d = 0; d < g.optionsArr.length; d++) {
				if (g.optionsArr[d].isHidden()) {
					g.optionsArr[d].hide(false)
				} else {
					c++;
					f = g.optionsArr[d]
				}
			}
			if (g.isAfterFilter) {
				g.isAfterFilter = false;
				if (c == 1) {
					g.setValue(f.value)
				} else {
					var a = g.getText();
					var h = g.getOptionByLabel(a);
					if (h) {
						g.setValue(h.value)
					} else {
						g.setValue("")
					}
				}
			}
			window._dhtmlSelectedOption = null
		}
	}
};
dhtmlXCombo_defaultOption = function() {
	this.init()
};
dhtmlXCombo_defaultOption.prototype.init = function() {
	this.value = null;
	this.text = "";
	this.selected = false;
	this.css = ""
};
dhtmlXCombo_defaultOption.prototype.select = function() {
	if (this.content) {
		var a = this.content.parentNode.combo;
		var c = a.selectedIndex;
		if ((c || c == 0) && c > -1 && a.optionsArr[c]) {
			a.optionsArr[c].deselect(true)
		}
		a.selectedIndex = this.index;
		this.content.className = "dhx_selected_option"
				+ (dhtmlx.skin ? " combo_" + dhtmlx.skin + "_sel" : "")
	}
};
dhtmlXCombo_defaultOption.prototype.hide = function(a) {
	this.render().style.display = a ? "none" : ""
};
dhtmlXCombo_defaultOption.prototype.isHidden = function() {
	return (this.render().style.display == "none")
};
dhtmlXCombo_defaultOption.prototype.deselect = function() {
	if (this.content) {
		this.render();
		this.content.className = ""
	}
};
dhtmlXCombo_defaultOption.prototype.setValue = function(a) {
	this.index = a.index;
	this.value = a.value || "";
	this.text = a.text || "";
	this.css = a.css || "";
	this.content = null;
	this.data = a
};
dhtmlXCombo_defaultOption.prototype.render = function() {
	if (!this.content) {
		this.content = document.createElement("DIV");
		this.content._self = this;
		this.content.style.cssText = "width:100%; overflow:hidden;" + this.css;
		if (_isOpera || _isKHTML) {
			this.content.style.padding = "2px 0px 2px 0px"
		}
		this.content.innerHTML = this.text;
		this._ctext = _isIE ? this.content.innerText : this.content.textContent
	}
	return this.content
};
dhtmlXCombo_defaultOption.prototype.DrawHeader = function(a, g, c, e, d) {
	var f = document.createElement("DIV");
	f.style.width = e - 2 + "px";
	f.className = "dhx_combo_box " + (dhtmlx.skin || "");
	f._self = a;
	a.DOMelem = f;
	this._DrawHeaderInput(a, g, c, e + 2, d);
	this._DrawHeaderButton(a, c, e);
	a.DOMParent.appendChild(a.DOMelem)
};
dhtmlXCombo_defaultOption.prototype._DrawHeaderInput = function(a, g, c, e, d) {
	if (a.rtl && _isIE) {
		var f = document.createElement("textarea");
		f.style.overflow = "hidden";
		f.style.whiteSpace = "nowrap"
	} else {
		var f = document.createElement("input");
		f.setAttribute("autocomplete", "off");
		f.type = "text"
	}
	f.className = "input dhx_combo_input";
	if (a.required) {
		f.required = true
	}
	if (a.validType) {
		f.validType = a.validType
	}
	if (a.rtl) {
		f.style.left = "18px";
		f.style.direction = "rtl";
		f.style.unicodeBidi = "bidi-override"
	}
	if (d) {
		f.tabIndex = d
	}
	f.style.width = (e - 19) + "px";
	a.DOMelem.appendChild(f);
	a.DOMelem_input = f;
	if (!c) {
		c = ""
	}
	f = document
			.createElement('<input type="hidden" class="combobox-value" id="'
					+ g + '" name="' + c + '"/>');
	a.DOMelem.appendChild(f);
	a.DOMelem_hidden_input = f;
	f._self = a;
	f.onchange = function() {
		a.setValue(f.value)
	}
};
dhtmlXCombo_defaultOption.prototype._DrawHeaderButton = function(a, c, d) {
	var e = document.createElement("img");
	e.className = (a.rtl) ? "dhx_combo_img_rtl" : "dhx_combo_img";
	if (dhtmlx.image_path) {
		dhx_globalImgPath = dhtmlx.image_path
	}
	e.src = (window.dhx_globalImgPath ? dhx_globalImgPath : "")
			+ "combo_select" + (dhtmlx.skin ? "_" + dhtmlx.skin : "") + ".gif";
	a.DOMelem.appendChild(e);
	a.DOMelem_button = e
};
dhtmlXCombo_defaultOption.prototype.RedrawHeader = function(a) {
};
dhtmlXCombo_optionTypes["default"] = dhtmlXCombo_defaultOption;
dhtmlXCombo.prototype.dhx_Event = function() {
	this.dhx_SeverCatcherPath = "";
	this.attachEvent = function(original, catcher, CallObj) {
		CallObj = CallObj || this;
		original = "ev_" + original;
		if ((!this[original]) || (!this[original].addEvent)) {
			var z = new this.eventCatcher(CallObj);
			z.addEvent(this[original]);
			this[original] = z
		}
		return (original + ":" + this[original].addEvent(catcher))
	};
	this.callEvent = function(name, arg0) {
		if (this["ev_" + name]) {
			return this["ev_" + name].apply(this, arg0)
		}
		return true
	};
	this.checkEvent = function(name) {
		if (this["ev_" + name]) {
			return true
		}
		return false
	};
	this.eventCatcher = function(obj) {
		var dhx_catch = new Array();
		var m_obj = obj;
		var func_server = function(catcher, rpc) {
			catcher = catcher.split(":");
			var postVar = "";
			var postVar2 = "";
			var target = catcher[1];
			if (catcher[1] == "rpc") {
				postVar = '<?xml version="1.0"?><methodCall><methodName>'
						+ catcher[2] + "</methodName><params>";
				postVar2 = "</params></methodCall>";
				target = rpc
			}
			var z = function() {
			};
			return z
		};
		var z = function() {
			if (dhx_catch) {
				var res = true
			}
			for (var i = 0; i < dhx_catch.length; i++) {
				if (dhx_catch[i] != null) {
					var zr = dhx_catch[i].apply(m_obj, arguments);
					res = res && zr
				}
			}
			return res
		};
		z.addEvent = function(ev) {
			if (typeof(ev) != "function") {
				if (ev && ev.indexOf && ev.indexOf("server:") == 0) {
					ev = new func_server(ev, m_obj.rpcServer)
				} else {
					ev = eval(ev)
				}
			}
			if (ev) {
				return dhx_catch.push(ev) - 1
			}
			return false
		};
		z.removeEvent = function(id) {
			dhx_catch[id] = null
		};
		return z
	};
	this.detachEvent = function(id) {
		if (id != false) {
			var list = id.split(":");
			this[list[0]].removeEvent(list[1])
		}
	}
};
(function() {
	dhtmlx.extend_api("dhtmlXCombo", {
				_init : function(a) {
					if (a.image_path) {
						dhx_globalImgPath = a.image_path
					}
					return [a.parent, a.name, (a.width || "100%"), a.type,
							a.index]
				},
				auto_height : "enableOptionAutoHeight",
				auto_position : "enableOptionAutoPositioning",
				auto_width : "enableOptionAutoWidth",
				readonly : "readonly",
				items : "addOption"
			})
})();
Array.prototype.add = function(c) {
	for (var a = 0; a < this.length; a++) {
		if (this[a].index > c.index) {
			this.splice(a, 0, c);
			return true
		}
		if (this[a] == c) {
			return false
		}
	}
	this.push(c);
	return true
};
Array.prototype.indexOf = function(c) {
	for (var a = 0; a < this.length; a++) {
		if (this[a] == c) {
			return a
		}
	}
	return -1
};
Array.prototype.remove = function(d) {
	var c = -1;
	var a = this.length;
	if (typeof(d) == "number") {
		if (a > d && d > -1) {
			c = d
		}
	} else {
		if (typeof(d) == "object") {
			c = this.indexOf(d)
		}
	}
	if (c > -1) {
		this.splice(d, 1)
	}
};
dhtmlXCombo_imageOption = function() {
	this.init()
};
dhtmlXCombo_imageOption.prototype = new dhtmlXCombo_defaultOption;
dhtmlXCombo_imageOption.prototype.setValue = function(a) {
	this.value = a.value || "";
	this.text = a.text || "";
	this.css = a.css || "";
	this.img_src = a.img_src || this.getDefImage()
};
dhtmlXCombo_imageOption.prototype.render = function() {
	if (!this.content) {
		this.content = document.createElement("DIV");
		this.content._self = this;
		this.content.style.cssText = "width:100%; overflow:hidden; " + this.css;
		var a = "";
		if (this.img_src != "") {
			a += '<img style="float:left;" src="' + this.img_src + '" />'
		}
		a += '<div style="float:left">' + this.text + "</div>";
		this.content.innerHTML = a
	}
	return this.content
};
dhtmlXCombo_imageOption.prototype.data = function() {
	return [this.value, this.text, this.img_src]
};
dhtmlXCombo_imageOption.prototype.DrawHeader = function(a, c, d) {
	var e = document.createElement("DIV");
	e.style.width = d + "px";
	e.className = "dhx_combo_box";
	e._self = a;
	a.DOMelem = e;
	this._DrawHeaderImage(a, c, d);
	this._DrawHeaderInput(a, c, d - 23);
	this._DrawHeaderButton(a, c, d);
	a.DOMParent.appendChild(a.DOMelem)
};
dhtmlXCombo_imageOption.prototype._DrawHeaderImage = function(a, c, d) {
	var e = document.createElement("img");
	e.className = (a.rtl) ? "dhx_combo_option_img_rtl" : "dhx_combo_option_img";
	e.style.visibility = "hidden";
	a.DOMelem.appendChild(e);
	a.DOMelem_image = e
};
dhtmlXCombo_imageOption.prototype.RedrawHeader = function(a) {
	a.DOMelem_image.style.visibility = "visible";
	a.DOMelem_image.src = this.img_src
};
dhtmlXCombo_imageOption.prototype.getDefImage = function(a) {
	return ""
};
dhtmlXCombo.prototype.setDefaultImage = function(a) {
	dhtmlXCombo_imageOption.prototype.getDefImage = function() {
		return a
	}
};
dhtmlXCombo_optionTypes.image = dhtmlXCombo_imageOption;
dhtmlXCombo_checkboxOption = function() {
	this.init()
};
dhtmlXCombo_checkboxOption.prototype = new dhtmlXCombo_defaultOption;
dhtmlXCombo_checkboxOption.prototype.setValue = function(a) {
	this.index = a.index;
	this.value = a.value || "";
	this.text = a.text || "";
	this.css = a.css || "";
	this.selected = a.selected || 0;
	this.data = a
};
dhtmlXCombo_checkboxOption.prototype.render = function() {
	if (!this.content) {
		this.content = document.createElement("DIV");
		this.content._self = this;
		this.content.style.cssText = "width:100%; overflow:hidden; " + this.css;
		var a = "";
		if (this.value) {
			if (this.selected) {
				a += '<input style="float:left;" type="checkbox" checked   />'
			} else {
				a += '<input type="checkbox" />'
			}
			a += "<span>" + this.text + "</span>"
		} else {
			a += '<input style="float:left;" type="checkbox" disabled="true"   />';
			a += "<span>" + this.text + "</span>"
		}
		this.content.innerHTML = a;
		var c = this;
		this.content.firstChild.onclick = function(f) {
			if (!this.parentNode._self.value) {
				return false
			}
			c.selected = this.checked;
			var d = this.parentNode.parentNode.combo;
			d._afterChangeFlg = true;
			d.DOMelem_input.focus();
			(f || event).cancelBubble = true;
			var g = null;
			if (this.parentNode._self) {
				g = this.parentNode._self.index
			}
			if (this.checked) {
				d.selectOption(g, g)
			} else {
				d.unSelectOption(g)
			}
		}
	}
	return this.content
};
dhtmlXCombo_checkboxOption.prototype.DrawHeader = function(a, e, c, d) {
	a.DOMelem = document.createElement("DIV");
	a.DOMelem.style.width = d + "px";
	a.DOMelem.className = "dhx_combo_box";
	a.DOMelem._self = a;
	this._DrawHeaderCheckbox(a, c, d);
	this._DrawHeaderInput(a, e, c, d + 2);
	this._DrawHeaderButton(a, c, d);
	a.DOMParent.appendChild(a.DOMelem)
};
dhtmlXCombo_checkboxOption.prototype._DrawHeaderCheckbox = function(a, c, d) {
};
dhtmlXCombo_checkboxOption.prototype.RedrawHeader = function(a) {
};
dhtmlXCombo_checkboxOption.prototype.select = function(d) {
	if (this.content) {
		var a = this.content.parentNode.combo;
		var c = a.selectedIndex;
		if ((c || c == 0) && c > -1) {
			a.optionsArr[c].deselect(true)
		}
		a.selectedIndex = this.index;
		this.content.className = "dhx_selected_option"
				+ (dhtmlx.skin ? " combo_" + dhtmlx.skin + "_sel" : "");
		if (!d) {
			this.content.firstChild.checked = true
		}
	}
};
dhtmlXCombo_checkboxOption.prototype.deselect = function(d) {
	if (this.content) {
		var c = this.content.parentNode.combo;
		if (d) {
			var a = "," + c.getSelectedIndex() + ",";
			if (a.indexOf("," + this.index + ",") > -1) {
				return
			}
		}
		this.content.className = "";
		this.content.firstChild.checked = false
	}
};
dhtmlXCombo_optionTypes.checkbox = dhtmlXCombo_checkboxOption;
function xmlPointer(a) {
	this.d = a
}
xmlPointer.prototype = {
	text : function() {
		if (!_isFF) {
			return this.d.xml
		}
		var a = new XMLSerializer();
		return a.serializeToString(this.d)
	},
	get : function(a) {
		return this.d.getAttribute(a)
	},
	exists : function() {
		return !!this.d
	},
	content : function() {
		return this.d.firstChild ? this.d.firstChild.data : ""
	},
	each : function(e, j, h, g) {
		var d = this.d.childNodes;
		var k = new xmlPointer();
		if (d.length) {
			for (g = g || 0; g < d.length; g++) {
				if (d[g].tagName == e) {
					k.d = d[g];
					if (j.apply(h, [k, g]) == -1) {
						return
					}
				}
			}
		}
	},
	get_all : function() {
		var d = {};
		var c = this.d.attributes;
		for (var e = 0; e < c.length; e++) {
			d[c[e].name] = c[e].value
		}
		return d
	},
	sub : function(e) {
		var d = this.d.childNodes;
		var g = new xmlPointer();
		if (d.length) {
			for (var f = 0; f < d.length; f++) {
				if (d[f].tagName == e) {
					g.d = d[f];
					return g
				}
			}
		}
	},
	up : function(a) {
		return new xmlPointer(this.d.parentNode)
	},
	set : function(a, c) {
		this.d.setAttribute(a, c)
	},
	clone : function(a) {
		return new xmlPointer(this.d)
	},
	sub_exists : function(d) {
		var c = this.d.childNodes;
		if (c.length) {
			for (var e = 0; e < c.length; e++) {
				if (c[e].tagName == d) {
					return true
				}
			}
		}
		return false
	},
	through : function(d, j, m, g, n) {
		var k = this.d.childNodes;
		if (k.length) {
			for (var e = 0; e < k.length; e++) {
				if (k[e].tagName == d && k[e].getAttribute(j) != null
						&& k[e].getAttribute(j) != ""
						&& (!m || k[e].getAttribute(j) == m)) {
					var h = new xmlPointer(k[e]);
					g.apply(n, [h, e])
				}
				var l = this.d;
				this.d = k[e];
				this.through(d, j, m, g, n);
				this.d = l
			}
		}
	}
};
function dhtmlXTreeObject(h, f, c, a) {
	if (_isIE) {
		try {
			document.execCommand("BackgroundImageCache", false, true)
		} catch (g) {
		}
	}
	if (typeof(h) != "object") {
		this.parentObject = document.getElementById(h)
	} else {
		this.parentObject = h
	}
	this.parentObject.childTree = this;
	this.parentObject.style.overflow = "hidden";
	this._itim_dg = true;
	this.dlmtr = ",";
	this.dropLower = false;
	this.enableIEImageFix();
	this.xmlstate = 0;
	this.mytype = "tree";
	this.smcheck = true;
	this.width = f;
	this.height = c;
	this.rootId = a;
	this.childCalc = null;
	this.def_img_x = "18px";
	this.def_img_y = "18px";
	this.def_line_img_x = "18px";
	this.def_line_img_y = "18px";
	this._dragged = new Array();
	this._selected = new Array();
	this.style_pointer = "pointer";
	if (_isIE) {
		this.style_pointer = "hand"
	}
	this._aimgs = true;
	this.htmlcA = " [";
	this.htmlcB = "]";
	this.lWin = window;
	this.cMenu = 0;
	this.mlitems = 0;
	this.iconURL = "";
	this.dadmode = 0;
	this.slowParse = false;
	this.autoScroll = true;
	this.hfMode = 0;
	this.nodeCut = new Array();
	this.XMLsource = 0;
	this.XMLloadingWarning = 0;
	this._idpull = {};
	this._pullSize = 0;
	this.treeLinesOn = true;
	this.tscheck = false;
	this.timgen = true;
	this.dpcpy = false;
	this._ld_id = null;
	this._oie_onXLE = [];
	this.imPath = window.dhx_globalImgPath || "";
	this.checkArray = new Array("iconUncheckAll.gif", "iconCheckAll.gif",
			"iconCheckGray.gif", "iconUncheckDis.gif", "iconCheckDis.gif",
			"iconCheckDis.gif");
	this.radioArray = new Array("radio_off.gif", "radio_on.gif",
			"radio_on.gif", "radio_off.gif", "radio_on.gif", "radio_on.gif");
	this.lineArray = new Array("line2.gif", "line3.gif", "line4.gif",
			"blank.gif", "blank.gif", "line1.gif");
	this.minusArray = new Array("minus2.gif", "minus3.gif", "minus4.gif",
			"minus.gif", "minus5.gif");
	this.plusArray = new Array("plus2.gif", "plus3.gif", "plus4.gif",
			"plus.gif", "plus5.gif");
	this.imageArray = new Array("leaf.gif", "folderOpen.gif",
			"folderClosed.gif");
	this.cutImg = new Array(0, 0, 0);
	this.cutImage = "but_cut.gif";
	dhtmlxEventable(this);
	this.dragger = new dhtmlDragAndDropObject();
	this.htmlNode = new dhtmlXTreeItemObject(this.rootId, "", 0, this);
	this.htmlNode.htmlNode.childNodes[0].childNodes[0].style.display = "none";
	this.htmlNode.htmlNode.childNodes[0].childNodes[0].childNodes[0].className = "hiddenRow";
	this.allTree = this._createSelf();
	this.allTree.appendChild(this.htmlNode.htmlNode);
	if (_isFF) {
		this.allTree.childNodes[0].width = "100%";
		this.allTree.childNodes[0].style.overflow = "hidden"
	}
	var d = this;
	this.allTree.onselectstart = new Function("return false;");
	if (_isMacOS) {
		this.allTree.oncontextmenu = function(j) {
			return d._doContClick(j || window.event)
		}
	}
	this.allTree.onmousedown = function(j) {
		return d._doContClick(j || window.event)
	};
	this.XMLLoader = new dtmlXMLLoaderObject(this._parseXMLTree, this, true,
			this.no_cashe);
	if (_isIE) {
		this.preventIECashing(true)
	}
	this.selectionBar = document.createElement("DIV");
	this.selectionBar.className = "selectionBar";
	this.selectionBar.innerHTML = "&nbsp;";
	this.selectionBar.style.display = "none";
	this.allTree.appendChild(this.selectionBar);
	if (window.addEventListener) {
		window.addEventListener("unload", function() {
					try {
						d.destructor()
					} catch (j) {
					}
				}, false)
	}
	if (window.attachEvent) {
		window.attachEvent("onunload", function() {
					try {
						d.destructor()
					} catch (j) {
					}
				})
	}
	this.setImagesPath = this.setImagePath;
	this.setIconsPath = this.setIconPath;
	if (dhtmlx.image_path) {
		this.setImagePath(dhtmlx.image_path)
	}
	if (dhtmlx.skin) {
		this.setSkin(dhtmlx.skin)
	}
	return this
}
dhtmlXTreeObject.prototype.setDataMode = function(a) {
	this._datamode = a
};
dhtmlXTreeObject.prototype._doContClick = function(d) {
	if (d.button != 2) {
		if (this._acMenu) {
			if (this._acMenu.hideContextMenu) {
				this._acMenu.hideContextMenu()
			} else {
				this.cMenu._contextEnd()
			}
		}
		return true
	}
	var c = (_isIE ? d.srcElement : d.target);
	while ((c) && (c.tagName != "BODY")) {
		if (c.parentObject) {
			break
		}
		c = c.parentNode
	}
	if ((!c) || (!c.parentObject)) {
		return true
	}
	var f = c.parentObject;
	if (!this.callEvent("onRightClick", [f.id, d])) {
		(d.srcElement || d.target).oncontextmenu = function(k) {
			(k || event).cancelBubble = true;
			return false
		}
	}
	this._acMenu = (f.cMenu || this.cMenu);
	if (this._acMenu) {
		if (!(this.callEvent("onBeforeContextMenu", [f.id]))) {
			return true
		}
		(d.srcElement || d.target).oncontextmenu = function(k) {
			(k || event).cancelBubble = true;
			return false
		};
		if (this._acMenu.showContextMenu) {
			var h = window.document.documentElement;
			var e = window.document.body;
			var g = new Array((h.scrollLeft || e.scrollLeft),
					(h.scrollTop || e.scrollTop));
			if (_isIE) {
				var a = d.clientX + g[0];
				var j = d.clientY + g[1]
			} else {
				var a = d.pageX;
				var j = d.pageY
			}
			this._acMenu.showContextMenu(a - 1, j - 1);
			this.contextID = f.id;
			d.cancelBubble = true;
			this._acMenu._skip_hide = true
		} else {
			c.contextMenuId = f.id;
			c.contextMenu = this._acMenu;
			c.a = this._acMenu._contextStart;
			c.a(c, d);
			c.a = null
		}
		return false
	}
	return true
};
dhtmlXTreeObject.prototype.enableIEImageFix = function(a) {
	if (!a) {
		this._getImg = function(c) {
			return document.createElement((c == this.rootId) ? "div" : "img")
		};
		this._setSrc = function(d, c) {
			d.src = c
		};
		this._getSrc = function(c) {
			return c.src
		}
	} else {
		this._getImg = function() {
			var c = document.createElement("DIV");
			c.innerHTML = "&nbsp;";
			c.className = "dhx_bg_img_fix";
			return c
		};
		this._setSrc = function(d, c) {
			d.style.backgroundImage = "url(" + c + ")"
		};
		this._getSrc = function(c) {
			var d = c.style.backgroundImage;
			return d.substr(4, d.length - 5)
		}
	}
};
dhtmlXTreeObject.prototype.destructor = function() {
	for (var c in this._idpull) {
		var d = this._idpull[c];
		if (!d) {
			continue
		}
		d.parentObject = null;
		d.treeNod = null;
		d.childNodes = null;
		d.span = null;
		d.tr.nodem = null;
		d.tr = null;
		d.htmlNode.objBelong = null;
		d.htmlNode = null;
		this._idpull[c] = null
	}
	this.parentObject.innerHTML = "";
	if (this.XMLLoader) {
		this.XMLLoader.destructor()
	}
	this.allTree.onselectstart = null;
	this.allTree.oncontextmenu = null;
	this.allTree.onmousedown = null;
	for (var c in this) {
		this[c] = null
	}
};
function cObject() {
	return this
}
cObject.prototype = new Object;
cObject.prototype.clone = function() {
	function a() {
	}
	a.prototype = this;
	return new a()
};
function dhtmlXTreeItemObject(g, c, d, a, e, f) {
	this.htmlNode = "";
	this.acolor = "";
	this.scolor = "";
	this.tr = 0;
	this.childsCount = 0;
	this.tempDOMM = 0;
	this.tempDOMU = 0;
	this.dragSpan = 0;
	this.dragMove = 0;
	this.span = 0;
	this.closeble = 1;
	this.childNodes = new Array();
	this.userData = new cObject();
	this.checkstate = 0;
	this.treeNod = a;
	this.label = c;
	this.parentObject = d;
	this.actionHandler = e;
	this.images = new Array(a.imageArray[0], a.imageArray[1], a.imageArray[2]);
	this.id = a._globalIdStorageAdd(g, this);
	if (this.treeNod.checkBoxOff) {
		this.htmlNode = this.treeNod._createItem(1, this, f)
	} else {
		this.htmlNode = this.treeNod._createItem(0, this, f)
	}
	this.htmlNode.objBelong = this;
	return this
}
dhtmlXTreeObject.prototype._globalIdStorageAdd = function(c, a) {
	if (this._globalIdStorageFind(c, 1, 1)) {
		c = c + "_" + (new Date()).valueOf();
		return this._globalIdStorageAdd(c, a)
	}
	this._idpull[c] = a;
	this._pullSize++;
	return c
};
dhtmlXTreeObject.prototype._globalIdStorageSub = function(a) {
	if (this._idpull[a]) {
		this._unselectItem(this._idpull[a]);
		this._idpull[a] = null;
		this._pullSize--
	}
	if ((this._locker) && (this._locker[a])) {
		this._locker[a] = false
	}
};
dhtmlXTreeObject.prototype._globalIdStorageFind = function(f, a, c, d) {
	var e = this._idpull[f];
	if (e) {
		return e
	}
	return null
};
dhtmlXTreeObject.prototype.getAllId = function() {
	var d = this._idpull;
	var a = "";
	for (var c in d) {
		a += "," + c
	}
	return a
};
dhtmlXTreeObject.prototype._escape = function(a) {
	switch (this.utfesc) {
		case "none" :
			return a;
			break;
		case "utf8" :
			return encodeURIComponent(a);
			break;
		default :
			return escape(a);
			break
	}
};
dhtmlXTreeObject.prototype._drawNewTr = function(f, d) {
	var e = document.createElement("tr");
	var c = document.createElement("td");
	var a = document.createElement("td");
	c.appendChild(document.createTextNode(" "));
	a.colSpan = 3;
	a.appendChild(f);
	e.appendChild(c);
	e.appendChild(a);
	return e
};
dhtmlXTreeObject.prototype.loadXMLString = function(d, c) {
	var a = this;
	if (!this.parsCount) {
		this.callEvent("onXLS", [a, null])
	}
	this.xmlstate = 1;
	if (c) {
		this.XMLLoader.waitCall = c
	}
	this.XMLLoader.loadXMLString(d)
};
dhtmlXTreeObject.prototype.loadXML = function(a, d) {
	if (this._datamode && this._datamode != "xml") {
		return this["load" + this._datamode.toUpperCase()](a, d)
	}
	var c = this;
	if (!this.parsCount) {
		this.callEvent("onXLS", [c, this._ld_id])
	}
	this._ld_id = null;
	this.xmlstate = 1;
	this.XMLLoader = new dtmlXMLLoaderObject(this._parseXMLTree, this, true,
			this.no_cashe);
	if (d) {
		this.XMLLoader.waitCall = d
	}
	this.XMLLoader.loadXML(a)
};
dhtmlXTreeObject.prototype._attachChildNode = function(h, g, e, j, w, v, u, k,
		d, o, p) {
	if (o && o.parentObject) {
		h = o.parentObject
	}
	if (((h.XMLload == 0) && (this.XMLsource)) && (!this.XMLloadingWarning)) {
		h.XMLload = 1;
		this._loadDynXML(h.id)
	}
	var l = h.childsCount;
	var x = h.childNodes;
	if (p) {
		if (p.tr.previousSibling.previousSibling) {
			o = p.tr.previousSibling.nodem
		} else {
			k = k.replace("TOP", "") + ",TOP"
		}
	}
	if (o) {
		var f, s;
		for (f = 0; f < l; f++) {
			if (x[f] == o) {
				for (s = l; s != f; s--) {
					x[1 + s] = x[s]
				}
				break
			}
		}
		f++;
		l = f
	}
	if (k) {
		var q = k.split(",");
		for (var r = 0; r < q.length; r++) {
			switch (q[r]) {
				case "TOP" :
					if (h.childsCount > 0) {
						o = new Object;
						o.tr = h.childNodes[0].tr.previousSibling
					}
					h._has_top = true;
					for (f = l; f > 0; f--) {
						x[f] = x[f - 1]
					}
					l = 0;
					break
			}
		}
	}
	var m;
	if (!(m = this._idpull[g]) || m.span != -1) {
		m = x[l] = new dhtmlXTreeItemObject(g, e, h, this, j, 1);
		g = x[l].id;
		h.childsCount++
	}
	if (!m.htmlNode) {
		m.label = e;
		m.htmlNode = this._createItem((this.checkBoxOff ? 1 : 0), m);
		m.htmlNode.objBelong = m
	}
	if (w) {
		m.images[0] = w
	}
	if (v) {
		m.images[1] = v
	}
	if (u) {
		m.images[2] = u
	}
	var c = this._drawNewTr(m.htmlNode);
	if ((this.XMLloadingWarning) || (this._hAdI)) {
		m.htmlNode.parentNode.parentNode.style.display = "none"
	}
	if ((o) && (o.tr.nextSibling)) {
		h.htmlNode.childNodes[0].insertBefore(c, o.tr.nextSibling)
	} else {
		if (this.parsingOn == h.id) {
			this.parsedArray[this.parsedArray.length] = c
		} else {
			h.htmlNode.childNodes[0].appendChild(c)
		}
	}
	if ((o) && (!o.span)) {
		o = null
	}
	if (this.XMLsource) {
		if ((d) && (d != 0)) {
			m.XMLload = 0
		} else {
			m.XMLload = 1
		}
	}
	m.tr = c;
	c.nodem = m;
	if (h.itemId == 0) {
		c.childNodes[0].className = "hiddenRow"
	}
	if ((h._r_logic) || (this._frbtr)) {
		this
				._setSrc(
						m.htmlNode.childNodes[0].childNodes[0].childNodes[1].childNodes[0],
						this.imPath + this.radioArray[0])
	}
	if (k) {
		var q = k.split(",");
		for (var r = 0; r < q.length; r++) {
			switch (q[r]) {
				case "SELECT" :
					this.selectItem(g, false);
					break;
				case "CALL" :
					this.selectItem(g, true);
					break;
				case "CHILD" :
					m.XMLload = 0;
					break;
				case "CHECKED" :
					if (this.XMLloadingWarning) {
						this.setCheckList += this.dlmtr + g
					} else {
						this.setCheck(g, 1)
					}
					break;
				case "HCHECKED" :
					this._setCheck(m, "unsure");
					break;
				case "OPEN" :
					m.openMe = 1;
					break
			}
		}
	}
	if (!this.XMLloadingWarning) {
		if ((this._getOpenState(h) < 0) && (!this._hAdI)) {
			this.openItem(h.id)
		}
		if (o) {
			this._correctPlus(o);
			this._correctLine(o)
		}
		this._correctPlus(h);
		this._correctLine(h);
		this._correctPlus(m);
		if (h.childsCount >= 2) {
			this._correctPlus(x[h.childsCount - 2]);
			this._correctLine(x[h.childsCount - 2])
		}
		if (h.childsCount != 2) {
			this._correctPlus(x[0])
		}
		if (this.tscheck) {
			this._correctCheckStates(h)
		}
		if (this._onradh) {
			if (this.xmlstate == 1) {
				var a = this.onXLE;
				this.onXLE = function(n) {
					this._onradh(g);
					if (a) {
						a(n)
					}
				}
			} else {
				this._onradh(g)
			}
		}
	}
	return m
};
dhtmlXTreeObject.prototype.insertNewItem = function(e, j, l, d, h, g, f, c, a) {
	var m = this._globalIdStorageFind(e);
	if (!m) {
		return (-1)
	}
	var k = this._attachChildNode(m, j, l, d, h, g, f, c, a);
	return k
};
dhtmlXTreeObject.prototype.insertNewChild = function(e, j, k, d, h, g, f, c, a) {
	return this.insertNewItem(e, j, k, d, h, g, f, c, a)
};
dhtmlXTreeObject.prototype._parseXMLTree = function(f, e, k, j, g) {
	var h = new xmlPointer(g.getXMLTopNode("tree"));
	f._parse(h);
	f._p = h
};
dhtmlXTreeObject.prototype._parseItem = function(g, l, f, j) {
	var d;
	if (this._srnd && (!this._idpull[d = g.get("id")] || !this._idpull[d].span)) {
		this._addItemSRND(l.id, d, g);
		return
	}
	var h = g.get_all();
	if ((typeof(this.waitUpdateXML) == "object") && (!this.waitUpdateXML[h.id])) {
		this._parse(g, h.id, 1);
		return
	}
	var n = [];
	if (h.select) {
		n.push("SELECT")
	}
	if (h.top) {
		n.push("TOP")
	}
	if (h.call) {
		this.nodeAskingCall = h.id
	}
	if (h.checked == -1) {
		n.push("HCHECKED")
	} else {
		if (h.checked) {
			n.push("CHECKED")
		}
	}
	if (h.open) {
		n.push("OPEN")
	}
	if (this.waitUpdateXML) {
		if (this._globalIdStorageFind(h.id)) {
			var k = this.updateItem(h.id, h.text, h.im0, h.im1, h.im2,
					h.checked)
		} else {
			if (this.npl == 0) {
				n.push("TOP")
			} else {
				f = l.childNodes[this.npl]
			}
			var k = this._attachChildNode(l, h.id, h.text, 0, h.im0, h.im1,
					h.im2, n.join(","), h.child, 0, f);
			f = null
		}
	} else {
		var k = this._attachChildNode(l, h.id, h.text, 0, h.im0, h.im1, h.im2,
				n.join(","), h.child, (j || 0), f)
	}
	if (h.tooltip) {
		k.span.parentNode.parentNode.title = h.tooltip
	}
	if (h.style) {
		if (k.span.style.cssText) {
			k.span.style.cssText += (";" + h.style)
		} else {
			k.span.setAttribute("style", k.span.getAttribute("style") + "; "
							+ h.style)
		}
	}
	if (h.radio) {
		k._r_logic = true
	}
	if (h.nocheckbox) {
		var m = k.span.parentNode.previousSibling.previousSibling;
		m.childNodes[0].style.display = "none";
		if (window._KHTMLrv) {
			m.style.display = "none"
		}
		k.nocheckbox = true
	}
	if (h.disabled) {
		if (h.checked != null) {
			this._setCheck(k, h.checked)
		}
		this.disableCheckbox(k, 1)
	}
	k._acc = h.child || 0;
	if (this.parserExtension) {
		this.parserExtension._parseExtension.call(this, g, h, (l ? l.id : 0))
	}
	this.setItemColor(k, h.aCol, h.sCol);
	if (h.locked == "1") {
		this.lockItem(k.id, true, true)
	}
	if ((h.imwidth) || (h.imheight)) {
		this.setIconSize(h.imwidth, h.imheight, k)
	}
	if ((h.closeable == "0") || (h.closeable == "1")) {
		this.setItemCloseable(k, h.closeable)
	}
	var e = "";
	if (h.topoffset) {
		this.setItemTopOffset(k, h.topoffset)
	}
	if ((!this.slowParse) || (typeof(this.waitUpdateXML) == "object")) {
		if (g.sub_exists("item")) {
			e = this._parse(g, h.id, 1)
		}
	}
	if (e != "") {
		this.nodeAskingCall = e
	}
	g.each("userdata", function(a) {
				this.setUserData(g.get("id"), a.get("name"), a.content())
			}, this)
};
dhtmlXTreeObject.prototype._parse = function(d, g, a, c) {
	if (this._srnd && !this.parentObject.offsetHeight) {
		var o = this;
		return window.setTimeout(function() {
					o._parse(d, g, a, c)
				}, 100)
	}
	if (!d.exists()) {
		return
	}
	this.skipLock = true;
	if (!g) {
		g = d.get("id");
		if (d.get("radio")) {
			this.htmlNode._r_logic = true
		}
		this.parsingOn = g;
		this.parsedArray = new Array();
		this.setCheckList = "";
		this.nodeAskingCall = ""
	}
	var m = this._globalIdStorageFind(g);
	if (!m) {
		return dhtmlxError.throwError("DataStructure",
				"XML refers to not existing parent")
	}
	this.parsCount = this.parsCount ? (this.parsCount + 1) : 1;
	this.XMLloadingWarning = 1;
	if ((m.childsCount) && (!c) && (!this._edsbps) && (!m._has_top)) {
		var h = m.childNodes[m.childsCount - 1]
	} else {
		var h = 0
	}
	this.npl = 0;
	d.each("item", function(p, n) {
				m.XMLload = 1;
				if ((this._epgps) && (this._epgpsC == this.npl)) {
					this._setNextPageSign(m, this.npl + 1 * (c || 0), a, node);
					return -1
				}
				this._parseItem(p, m, h);
				this.npl++
			}, this, c);
	if (!a) {
		d.each("userdata", function(n) {
					this.setUserData(d.get("id"), n.get("name"), n.content())
				}, this);
		m.XMLload = 1;
		if (this.waitUpdateXML) {
			this.waitUpdateXML = false;
			for (var f = m.childsCount - 1; f >= 0; f--) {
				if (m.childNodes[f]._dmark) {
					this.deleteItem(m.childNodes[f].id)
				}
			}
		}
		var k = this._globalIdStorageFind(this.parsingOn);
		for (var f = 0; f < this.parsedArray.length; f++) {
			m.htmlNode.childNodes[0].appendChild(this.parsedArray[f])
		}
		this.lastLoadedXMLId = g;
		this.XMLloadingWarning = 0;
		var l = this.setCheckList.split(this.dlmtr);
		for (var e = 0; e < l.length; e++) {
			if (l[e]) {
				this.setCheck(l[e], 1)
			}
		}
		if ((this.XMLsource) && (this.tscheck) && (this.smcheck)
				&& (m.id != this.rootId)) {
			if (m.checkstate === 0) {
				this._setSubChecked(0, m)
			} else {
				if (m.checkstate === 1) {
					this._setSubChecked(1, m)
				}
			}
		}
		this._redrawFrom(this, null, c);
		if (d.get("order") && d.get("order") != "none") {
			this._reorderBranch(m, d.get("order"), true)
		}
		if (this.nodeAskingCall != "") {
			this.callEvent("onClick", [this.nodeAskingCall,
							this.getSelectedItemId()])
		}
		if (this._branchUpdate) {
			this._branchUpdateNext(d)
		}
	}
	if (this.parsCount == 1) {
		this.parsingOn = null;
		if ((!this._edsbps) || (!this._edsbpsA.length)) {
			var j = this;
			setTimeout(function() {
						try {
							j.callEvent("onXLE", [j, g])
						} catch (n) {
						}
					}, 10);
			this.xmlstate = 0
		}
		this.skipLock = false
	}
	this.parsCount--;
	if ((this._epgps) && (c)) {
		this._setPrevPageSign(m, (c || 0), a, node)
	}
	if (!a && this.onXLE) {
		this.onXLE(this, g)
	}
	return this.nodeAskingCall
};
dhtmlXTreeObject.prototype._branchUpdateNext = function(a) {
	a.each("item", function(e) {
				var d = e.get("id");
				if (this._idpull[d] && (!this._idpull[d].XMLload)) {
					return
				}
				this._branchUpdate++;
				this.smartRefreshItem(e.get("id"), e)
			}, this);
	this._branchUpdate--
};
dhtmlXTreeObject.prototype.checkUserData = function(c, d) {
	if ((c.nodeType == 1) && (c.tagName == "userdata")) {
		var a = c.getAttribute("name");
		if ((a) && (c.childNodes[0])) {
			this.setUserData(d, a, c.childNodes[0].data)
		}
	}
};
dhtmlXTreeObject.prototype._redrawFrom = function(h, a, g, c) {
	if (!a) {
		var e = h._globalIdStorageFind(h.lastLoadedXMLId);
		h.lastLoadedXMLId = -1;
		if (!e) {
			return 0
		}
	} else {
		e = a
	}
	var f = 0;
	for (var d = (g ? g - 1 : 0); d < e.childsCount; d++) {
		if ((!this._branchUpdate) || (this._getOpenState(e) == 1)) {
			if ((!a) || (c == 1)) {
				e.childNodes[d].htmlNode.parentNode.parentNode.style.display = ""
			}
		}
		if (e.childNodes[d].openMe == 1) {
			this._openItem(e.childNodes[d]);
			e.childNodes[d].openMe = 0
		}
		h._redrawFrom(h, e.childNodes[d])
	}
	if ((!e.unParsed) && ((e.XMLload) || (!this.XMLsource))) {
		e._acc = f
	}
	h._correctLine(e);
	h._correctPlus(e)
};
dhtmlXTreeObject.prototype._createSelf = function() {
	var c = document.createElement("div");
	c.className = "containerTableStyle";
	c.style.width = this.width;
	c.style.height = this.height;
	var a = this.height;
	this.parentObject.appendChild(c);
	return c
};
dhtmlXTreeObject.prototype._xcloseAll = function(c) {
	if (c.unParsed) {
		return
	}
	if (this.rootId != c.id) {
		var e = c.htmlNode.childNodes[0].childNodes;
		var a = e.length;
		for (var d = 1; d < a; d++) {
			e[d].style.display = "none"
		}
		this._correctPlus(c)
	}
	for (var d = 0; d < c.childsCount; d++) {
		if (c.childNodes[d].childsCount) {
			this._xcloseAll(c.childNodes[d])
		}
	}
};
dhtmlXTreeObject.prototype._xopenAll = function(a) {
	this._HideShow(a, 2);
	for (var c = 0; c < a.childsCount; c++) {
		this._xopenAll(a.childNodes[c])
	}
};
dhtmlXTreeObject.prototype._correctPlus = function(c) {
	if (!c.htmlNode) {
		return
	}
	var e = c.htmlNode.childNodes[0].childNodes[0].childNodes[0].lastChild;
	var g = c.htmlNode.childNodes[0].childNodes[0].childNodes[2].childNodes[0];
	var a = this.lineArray;
	if ((this.XMLsource) && (!c.XMLload)) {
		var a = this.plusArray;
		var d = c.images[2];
		if (d.indexOf("/") != 0) {
			d = this.iconURL + d
		}
		this._setSrc(g, d);
		if (this._txtimg) {
			return (e.innerHTML = "[+]")
		}
	} else {
		if ((c.childsCount) || (c.unParsed)) {
			if ((c.htmlNode.childNodes[0].childNodes[1])
					&& (c.htmlNode.childNodes[0].childNodes[1].style.display != "none")) {
				if (!c.wsign) {
					var a = this.minusArray
				}
				var d = c.images[1];
				if (d.indexOf("/") != 0) {
					d = this.iconURL + d
				}
				this._setSrc(g, d);
				if (this._txtimg) {
					return (e.innerHTML = "[-]")
				}
			} else {
				if (!c.wsign) {
					var a = this.plusArray
				}
				var d = c.images[2];
				if (d.indexOf("/") != 0) {
					d = this.iconURL + d
				}
				this._setSrc(g, d);
				if (this._txtimg) {
					return (e.innerHTML = "[+]")
				}
			}
		} else {
			var d = c.images[0];
			if (d.indexOf("/") != 0) {
				d = this.iconURL + d
			}
			this._setSrc(g, d)
		}
	}
	var f = 2;
	if (!c.treeNod.treeLinesOn) {
		this._setSrc(e, this.imPath + a[3])
	} else {
		if (c.parentObject) {
			f = this._getCountStatus(c.id, c.parentObject)
		}
		this._setSrc(e, this.imPath + a[f])
	}
};
dhtmlXTreeObject.prototype._correctLine = function(c) {
	if (!c.htmlNode) {
		return
	}
	var a = c.parentObject;
	if (a) {
		if ((this._getLineStatus(c.id, a) == 0) || (!this.treeLinesOn)) {
			for (var d = 1; d <= c.childsCount; d++) {
				if (!c.htmlNode.childNodes[0].childNodes[d]) {
					break
				}
				c.htmlNode.childNodes[0].childNodes[d].childNodes[0].style.backgroundImage = "";
				c.htmlNode.childNodes[0].childNodes[d].childNodes[0].style.backgroundRepeat = ""
			}
		} else {
			for (var d = 1; d <= c.childsCount; d++) {
				if (!c.htmlNode.childNodes[0].childNodes[d]) {
					break
				}
				c.htmlNode.childNodes[0].childNodes[d].childNodes[0].style.backgroundImage = "url("
						+ this.imPath + this.lineArray[5] + ")";
				c.htmlNode.childNodes[0].childNodes[d].childNodes[0].style.backgroundRepeat = "repeat-y"
			}
		}
	}
};
dhtmlXTreeObject.prototype._getCountStatus = function(c, a) {
	if (a.childsCount <= 1) {
		if (a.id == this.rootId) {
			return 4
		} else {
			return 0
		}
	}
	if (a.childNodes[0].id == c) {
		if (a.id == this.rootId) {
			return 2
		} else {
			return 1
		}
	}
	if (a.childNodes[a.childsCount - 1].id == c) {
		return 0
	}
	return 1
};
dhtmlXTreeObject.prototype._getLineStatus = function(c, a) {
	if (a.childNodes[a.childsCount - 1].id == c) {
		return 0
	}
	return 1
};
dhtmlXTreeObject.prototype._HideShow = function(c, g) {
	if ((this.XMLsource) && (!c.XMLload)) {
		if (g == 1) {
			return
		}
		c.XMLload = 1;
		this._loadDynXML(c.id);
		var e = SearchEvent();
		if (e) {
			e.cancelBubble = true
		}
		return
	}
	var f = c.htmlNode.childNodes[0].childNodes;
	var a = f.length;
	if (a > 1) {
		if (((f[1].style.display != "none") || (g == 1)) && (g != 2)) {
			this.allTree.childNodes[0].border = "1";
			this.allTree.childNodes[0].border = "0";
			nodestyle = "none"
		} else {
			nodestyle = ""
		}
		for (var d = 1; d < a; d++) {
			f[d].style.display = nodestyle
		}
	}
	this._correctPlus(c);
	var e = SearchEvent();
	if (e) {
		e.cancelBubble = true
	}
};
dhtmlXTreeObject.prototype._getOpenState = function(a) {
	var c = a.htmlNode.childNodes[0].childNodes;
	if (c.length <= 1) {
		return 0
	}
	if (c[1].style.display != "none") {
		return 1
	} else {
		return -1
	}
};
dhtmlXTreeObject.prototype.onRowClick2 = function() {
	var a = this.parentObject.treeNod;
	if (!a.callEvent("onDblClick", [this.parentObject.id, a])) {
		return false
	}
	if ((this.parentObject.closeble) && (this.parentObject.closeble != "0")) {
		a._HideShow(this.parentObject)
	} else {
		a._HideShow(this.parentObject, 2)
	}
	if (a.checkEvent("onOpenEnd")) {
		if (!a.xmlstate) {
			a.callEvent("onOpenEnd", [this.parentObject.id,
							a._getOpenState(this.parentObject)])
		} else {
			a._oie_onXLE.push(a.onXLE);
			a.onXLE = a._epnFHe
		}
	}
	return false
};
dhtmlXTreeObject.prototype.onRowClick = function() {
	var a = this.parentObject.treeNod;
	if (!a.callEvent("onOpenStart", [this.parentObject.id,
					a._getOpenState(this.parentObject)])) {
		return 0
	}
	if ((this.parentObject.closeble) && (this.parentObject.closeble != "0")) {
		a._HideShow(this.parentObject)
	} else {
		a._HideShow(this.parentObject, 2)
	}
	if (a.checkEvent("onOpenEnd")) {
		if (!a.xmlstate) {
			a.callEvent("onOpenEnd", [this.parentObject.id,
							a._getOpenState(this.parentObject)])
		} else {
			a._oie_onXLE.push(a.onXLE);
			a.onXLE = a._epnFHe
		}
	}
};
dhtmlXTreeObject.prototype._epnFHe = function(c, d, a) {
	if (d != this.rootId) {
		this.callEvent("onOpenEnd", [d, c.getOpenState(d)])
	}
	c.onXLE = c._oie_onXLE.pop();
	if (!a && !c._oie_onXLE.length) {
		if (c.onXLE) {
			c.onXLE(c, d)
		}
	}
};
dhtmlXTreeObject.prototype.onRowClickDown = function(c) {
	c = c || window.event;
	var a = this.parentObject.treeNod;
	a._selectItem(this.parentObject, c)
};
dhtmlXTreeObject.prototype.getSelectedItemId = function() {
	var c = new Array();
	for (var a = 0; a < this._selected.length; a++) {
		c[a] = this._selected[a].id
	}
	return (c.join(this.dlmtr))
};
dhtmlXTreeObject.prototype._selectItem = function(a, c) {
	if (this.checkEvent("onSelect")) {
		this._onSSCFold = this.getSelectedItemId()
	}
	this._unselectItems();
	this._markItem(a);
	if (this.checkEvent("onSelect")) {
		var d = this.getSelectedItemId();
		if (d != this._onSSCFold) {
			this.callEvent("onSelect", [d])
		}
	}
};
dhtmlXTreeObject.prototype._markItem = function(a) {
	if (a.scolor) {
		a.span.style.color = a.scolor
	}
	a.span.className = "selectedTreeRow";
	a.i_sel = true;
	this._selected[this._selected.length] = a
};
dhtmlXTreeObject.prototype.getIndexById = function(c) {
	var a = this._globalIdStorageFind(c);
	if (!a) {
		return null
	}
	return this._getIndex(a)
};
dhtmlXTreeObject.prototype._getIndex = function(a) {
	var d = a.parentObject;
	for (var c = 0; c < d.childsCount; c++) {
		if (d.childNodes[c] == a) {
			return c
		}
	}
};
dhtmlXTreeObject.prototype._unselectItem = function(c) {
	if ((c) && (c.i_sel)) {
		c.span.className = "standartTreeRow";
		if (c.acolor) {
			c.span.style.color = c.acolor
		}
		c.i_sel = false;
		for (var a = 0; a < this._selected.length; a++) {
			if (!this._selected[a].i_sel) {
				this._selected.splice(a, 1);
				break
			}
		}
	}
};
dhtmlXTreeObject.prototype._unselectItems = function() {
	for (var a = 0; a < this._selected.length; a++) {
		var c = this._selected[a];
		c.span.className = "standartTreeRow";
		if (c.acolor) {
			c.span.style.color = c.acolor
		}
		c.i_sel = false
	}
	this._selected = new Array()
};
dhtmlXTreeObject.prototype.onRowSelect = function(f, d, h) {
	f = f || window.event;
	var c = this.parentObject;
	if (d) {
		c = d.parentObject
	}
	var a = c.treeNod;
	var g = a.getSelectedItemId();
	if ((!f) || (!f.skipUnSel)) {
		a._selectItem(c, f)
	}
	if (!h) {
		if (c.actionHandler) {
			c.actionHandler(c.id, g)
		} else {
			a.callEvent("onClick", [c.id, g, a])
		}
	}
};
dhtmlXTreeObject.prototype._correctCheckStates = function(f) {
	if (!this.tscheck) {
		return
	}
	if (!f) {
		return
	}
	if (f.id == this.rootId) {
		return
	}
	var d = f.childNodes;
	var c = 0;
	var a = 0;
	if (f.childsCount == 0) {
		return
	}
	for (var e = 0; e < f.childsCount; e++) {
		if (d[e].dscheck) {
			continue
		}
		if (d[e].checkstate == 0) {
			c = 1
		} else {
			if (d[e].checkstate == 1) {
				a = 1
			} else {
				c = 1;
				a = 1;
				break
			}
		}
	}
	if ((c) && (a)) {
		this._setCheck(f, "unsure")
	} else {
		if (c) {
			this._setCheck(f, false)
		} else {
			this._setCheck(f, true)
		}
	}
	this._correctCheckStates(f.parentObject)
};
dhtmlXTreeObject.prototype.onCheckBoxClick = function(a) {
	if (!this.treeNod.callEvent("onBeforeCheck", [this.parentObject.id,
					this.parentObject.checkstate])) {
		return
	}
	if (this.parentObject.dscheck) {
		return true
	}
	if (this.treeNod.tscheck) {
		if (this.parentObject.checkstate == 1) {
			this.treeNod._setSubChecked(false, this.parentObject)
		} else {
			this.treeNod._setSubChecked(true, this.parentObject)
		}
	} else {
		if (this.parentObject.checkstate == 1) {
			this.treeNod._setCheck(this.parentObject, false)
		} else {
			this.treeNod._setCheck(this.parentObject, true)
		}
	}
	this.treeNod._correctCheckStates(this.parentObject.parentObject);
	(a || event).cancelBubble = true;
	return this.treeNod.callEvent("onCheck", [this.parentObject.id,
					this.parentObject.checkstate, this.treeNod])
};
dhtmlXTreeObject.prototype._createItem = function(o, m, j) {
	var p = document.createElement("table");
	p.cellSpacing = 0;
	p.cellPadding = 0;
	p.border = 0;
	if (this.hfMode) {
		p.style.tableLayout = "fixed"
	}
	p.style.margin = 0;
	p.style.padding = 0;
	var h = document.createElement("tbody");
	var l = document.createElement("tr");
	var e = document.createElement("td");
	e.className = "standartTreeImage";
	if (this._txtimg) {
		var f = document.createElement("div");
		e.appendChild(f);
		f.className = "dhx_tree_textSign"
	} else {
		var f = this._getImg(m.id);
		f.border = "0";
		if (f.tagName == "IMG") {
			f.align = "absmiddle"
		}
		e.appendChild(f);
		f.style.padding = 0;
		f.style.margin = 0;
		f.style.width = this.def_line_img_x;
		f.style.height = this.def_line_img_y
	}
	var d = document.createElement("td");
	var k = this._getImg(this.cBROf ? this.rootId : m.id);
	k.checked = 0;
	this._setSrc(k, this.imPath + this.checkArray[0]);
	k.style.width = "16px";
	k.style.height = "16px";
	if (!o) {
		((!_isIE) ? d : k).style.display = "none"
	}
	d.appendChild(k);
	if ((!this.cBROf) && (k.tagName == "IMG")) {
		k.align = "absmiddle"
	}
	k.onclick = this.onCheckBoxClick;
	k.treeNod = this;
	k.parentObject = m;
	if (!window._KHTMLrv) {
		d.width = "20px"
	} else {
		d.width = "16px"
	}
	var c = document.createElement("td");
	c.className = "standartTreeImage";
	var g = this._getImg(this.timgen ? m.id : this.rootId);
	g.onmousedown = this._preventNsDrag;
	g.ondragstart = this._preventNsDrag;
	g.border = "0";
	if (this._aimgs) {
		g.parentObject = m;
		if (g.tagName == "IMG") {
			g.align = "absmiddle"
		}
		g.onclick = this.onRowSelect
	}
	if (!j) {
		this._setSrc(g, this.iconURL + this.imageArray[0])
	}
	c.appendChild(g);
	g.style.padding = 0;
	g.style.margin = 0;
	if (this.timgen) {
		c.style.width = g.style.width = this.def_img_x;
		g.style.height = this.def_img_y
	} else {
		g.style.width = "0px";
		g.style.height = "0px";
		if (_isOpera || window._KHTMLrv) {
			c.style.display = "none"
		}
	}
	var a = document.createElement("td");
	a.className = "standartTreeRow";
	m.span = document.createElement("span");
	m.span.className = "standartTreeRow";
	if (this.mlitems) {
		m.span.style.width = this.mlitems;
		m.span.style.display = "block"
	} else {
		a.noWrap = true
	}
	if (_isIE && _isIE > 7) {
		a.style.width = "999999px"
	} else {
		if (!window._KHTMLrv) {
			a.style.width = "100%"
		}
	}
	var n = m.label;
	if (n) {
		n = n.replace(/&/g, "&amp;")
	}
	m.span.innerHTML = n;
	a.appendChild(m.span);
	a.parentObject = m;
	e.parentObject = m;
	a.onclick = this.onRowSelect;
	e.onclick = this.onRowClick;
	a.ondblclick = this.onRowClick2;
	if (this.ettip) {
		l.title = m.label
	}
	if (this.dragAndDropOff) {
		if (this._aimgs) {
			this.dragger.addDraggableItem(c, this);
			c.parentObject = m
		}
		this.dragger.addDraggableItem(a, this)
	}
	m.span.style.paddingLeft = "5px";
	m.span.style.paddingRight = "5px";
	a.style.verticalAlign = "";
	a.style.fontSize = "10pt";
	a.style.cursor = this.style_pointer;
	l.appendChild(e);
	l.appendChild(d);
	l.appendChild(c);
	l.appendChild(a);
	h.appendChild(l);
	p.appendChild(h);
	if (this.ehlt || this.checkEvent("onMouseIn")
			|| this.checkEvent("onMouseOut")) {
		l.onmousemove = this._itemMouseIn;
		l[(_isIE) ? "onmouseleave" : "onmouseout"] = this._itemMouseOut
	}
	return p
};
dhtmlXTreeObject.prototype.setImagePath = function(a) {
	this.imPath = a;
	this.iconURL = a
};
dhtmlXTreeObject.prototype.setIconPath = function(a) {
	this.iconURL = a
};
dhtmlXTreeObject.prototype.setOnRightClickHandler = function(a) {
	this.attachEvent("onRightClick", a)
};
dhtmlXTreeObject.prototype.setOnClickHandler = function(a) {
	this.attachEvent("onClick", a)
};
dhtmlXTreeObject.prototype.setOnSelectStateChange = function(a) {
	this.attachEvent("onSelect", a)
};
dhtmlXTreeObject.prototype.setXMLAutoLoading = function(a) {
	this.XMLsource = a
};
dhtmlXTreeObject.prototype.setOnCheckHandler = function(a) {
	this.attachEvent("onCheck", a)
};
dhtmlXTreeObject.prototype.setOnOpenHandler = function(a) {
	this.attachEvent("onOpenStart", a)
};
dhtmlXTreeObject.prototype.setOnOpenStartHandler = function(a) {
	this.attachEvent("onOpenStart", a)
};
dhtmlXTreeObject.prototype.setOnOpenEndHandler = function(a) {
	this.attachEvent("onOpenEnd", a)
};
dhtmlXTreeObject.prototype.setOnDblClickHandler = function(a) {
	this.attachEvent("onDblClick", a)
};
dhtmlXTreeObject.prototype.openAllItems = function(c) {
	var a = this._globalIdStorageFind(c);
	if (!a) {
		return 0
	}
	this._xopenAll(a)
};
dhtmlXTreeObject.prototype.openAllChildren = function(d) {
	var a = this._globalIdStorageFind(d);
	if (a) {
		this._HideShow(a, 2);
		for (var c = 0; c < a.childsCount; c++) {
			this._HideShow(a.childNodes[c], 2)
		}
	}
};
dhtmlXTreeObject.prototype.getOpenState = function(c) {
	var a = this._globalIdStorageFind(c);
	if (!a) {
		return ""
	}
	return this._getOpenState(a)
};
dhtmlXTreeObject.prototype.closeAllItems = function(c) {
	if (c === undefined) {
		c = this.rootId
	}
	var a = this._globalIdStorageFind(c);
	if (!a) {
		return 0
	}
	this._xcloseAll(a);
	this.allTree.childNodes[0].border = "1";
	this.allTree.childNodes[0].border = "0"
};
dhtmlXTreeObject.prototype.setUserData = function(itemId, name, value) {
	var sNode = this._globalIdStorageFind(itemId, 0, true);
	if (!sNode) {
		return
	}
	if (name == "hint") {
		sNode.htmlNode.childNodes[0].childNodes[0].title = value
	}
	if (typeof(sNode.userData["t_" + name]) == "undefined") {
		if (!sNode._userdatalist) {
			sNode._userdatalist = name
		} else {
			sNode._userdatalist += "," + name
		}
	}
	var objUserData = eval("(" + value + ")");
	if (!objUserData.id || objUserData.id == "") {
		objUserData.id = itemId
	}
	if (!objUserData.name || objUserData.name == "") {
		objUserData.name = this.getItemText(itemId)
	}
	sNode.userData["t_" + name] = objUserData
};
dhtmlXTreeObject.prototype.getUserData = function(d, c) {
	var a = this._globalIdStorageFind(d, 0, true);
	if (!a) {
		return
	}
	return a.userData["t_" + c]
};
dhtmlXTreeObject.prototype.getItemColor = function(d) {
	var a = this._globalIdStorageFind(d);
	if (!a) {
		return 0
	}
	var c = new Object();
	if (a.acolor) {
		c.acolor = a.acolor
	}
	if (a.scolor) {
		c.scolor = a.scolor
	}
	return c
};
dhtmlXTreeObject.prototype.setItemColor = function(d, c, e) {
	if ((d) && (d.span)) {
		var a = d
	} else {
		var a = this._globalIdStorageFind(d)
	}
	if (!a) {
		return 0
	} else {
		if (a.i_sel) {
			if (e) {
				a.span.style.color = e
			}
		} else {
			if (c) {
				a.span.style.color = c
			}
		}
		if (e) {
			a.scolor = e
		}
		if (c) {
			a.acolor = c
		}
	}
};
dhtmlXTreeObject.prototype.getItemText = function(c) {
	var a = this._globalIdStorageFind(c);
	if (!a) {
		return 0
	}
	return (a.htmlNode.childNodes[0].childNodes[0].childNodes[3].childNodes[0].innerHTML)
};
dhtmlXTreeObject.prototype.getItemById = function(a) {
	return this._globalIdStorageFind(a)
};
dhtmlXTreeObject.prototype.getParentId = function(c) {
	var a = this._globalIdStorageFind(c);
	if ((!a) || (!a.parentObject)) {
		return ""
	}
	return a.parentObject.id
};
dhtmlXTreeObject.prototype.changeItemId = function(c, d) {
	if (c == d) {
		return
	}
	var a = this._globalIdStorageFind(c);
	if (!a) {
		return 0
	}
	a.id = d;
	a.span.contextMenuId = d;
	this._idpull[d] = this._idpull[c];
	delete this._idpull[c]
};
dhtmlXTreeObject.prototype.doCut = function() {
	if (this.nodeCut) {
		this.clearCut()
	}
	this.nodeCut = (new Array()).concat(this._selected);
	for (var a = 0; a < this.nodeCut.length; a++) {
		var c = this.nodeCut[a];
		c._cimgs = new Array();
		c._cimgs[0] = c.images[0];
		c._cimgs[1] = c.images[1];
		c._cimgs[2] = c.images[2];
		c.images[0] = c.images[1] = c.images[2] = this.cutImage;
		this._correctPlus(c)
	}
};
dhtmlXTreeObject.prototype.doPaste = function(d) {
	var a = this._globalIdStorageFind(d);
	if (!a) {
		return 0
	}
	for (var c = 0; c < this.nodeCut.length; c++) {
		if (this._checkPNodes(a, this.nodeCut[c])) {
			continue
		}
		this._moveNode(this.nodeCut[c], a)
	}
	this.clearCut()
};
dhtmlXTreeObject.prototype.clearCut = function() {
	for (var a = 0; a < this.nodeCut.length; a++) {
		var c = this.nodeCut[a];
		c.images[0] = c._cimgs[0];
		c.images[1] = c._cimgs[1];
		c.images[2] = c._cimgs[2];
		this._correctPlus(c)
	}
	this.nodeCut = new Array()
};
dhtmlXTreeObject.prototype._moveNode = function(a, e) {
	var g = this.dadmodec;
	if (g == 1) {
		var f = e;
		if (this.dadmodefix < 0) {
			while (true) {
				f = this.pR(f);
				if ((f == -1)) {
					f = this.htmlNode;
					break
				}
				if ((f.tr == 0) || (f.tr.style.display == "")
						|| (!f.parentObject)) {
					break
				}
			}
			var d = f;
			var c = e
		} else {
			while (true) {
				f = this.eU(f);
				if ((f == -1)) {
					f = this.htmlNode;
					break
				}
				if ((f.tr.style.display == "") || (!f.parentObject)) {
					break
				}
			}
			var c = f;
			var d = e
		}
		if (this.uU(d, 0) > this.uU(c, 0)) {
			if (!this.aep) {
				return this._moveNodeTo(a, d.parentObject)
			} else {
				if (c.id != this.fO) {
					return this._moveNodeTo(a, c.parentObject, c)
				} else {
					return this._moveNodeTo(a, this.htmlNode, null)
				}
			}
		} else {
			return this._moveNodeTo(a, c.parentObject, c)
		}
	} else {
		return this._moveNodeTo(a, e)
	}
};
dhtmlXTreeObject.prototype.pR = function(a, c) {
	if ((a.tr) && (a.tr.previousSibling) && (a.tr.previousSibling.mX)) {
		return this.qK(a.tr.previousSibling.mX)
	}
	if (a.parentObject) {
		return a.parentObject
	} else {
		return -1
	}
};
dhtmlXTreeObject.prototype.eU = function(a, c) {
	if ((!c) && (a.aE)) {
		return a.childNodes[0]
	}
	if (a == this.htmlNode) {
		return -1
	}
	if ((a.tr) && (a.tr.nextSibling) && (a.tr.nextSibling.mX)) {
		return a.tr.nextSibling.mX
	}
	return this.eU(a.parentObject, true)
};
dhtmlXTreeObject.prototype.uU = function(c, a) {
	if (c.parentObject) {
		return this.uU(c.parentObject, a + 1)
	}
	return (a)
};
dhtmlXTreeObject.prototype._fixNodesCollection = function(j, g) {
	var c = 0;
	var e = 0;
	var h = j.childNodes;
	var a = j.childsCount - 1;
	if (g == h[a]) {
		return
	}
	for (var f = 0; f < a; f++) {
		if (h[f] == h[a]) {
			h[f] = h[f + 1];
			h[f + 1] = h[a]
		}
	}
	for (var f = 0; f < a + 1; f++) {
		if (c) {
			var d = h[f];
			h[f] = c;
			c = d
		} else {
			if (h[f] == g) {
				c = h[f];
				h[f] = h[a]
			}
		}
	}
};
dhtmlXTreeObject.prototype._recreateBranch = function(a, e, d, j) {
	var g;
	var c = "";
	if (d) {
		for (g = 0; g < e.childsCount; g++) {
			if (e.childNodes[g] == d) {
				break
			}
		}
		if (g != 0) {
			d = e.childNodes[g - 1]
		} else {
			c = "TOP";
			d = ""
		}
	}
	var h = this._onradh;
	this._onradh = null;
	var f = this._attachChildNode(e, a.id, a.label, 0, a.images[0],
			a.images[1], a.images[2], c, 0, d);
	f._userdatalist = a._userdatalist;
	f.userData = a.userData.clone();
	f.XMLload = a.XMLload;
	if (h) {
		this._onradh = h;
		this._onradh(f.id)
	}
	for (var g = 0; g < a.childsCount; g++) {
		this._recreateBranch(a.childNodes[g], f, 0, 1)
	}
	return f
};
dhtmlXTreeObject.prototype._moveNodeTo = function(n, p, m) {
	if (n.treeNod._nonTrivialNode) {
		return n.treeNod._nonTrivialNode(this, p, m, n)
	}
	if (p.mytype) {
		var h = (n.treeNod.lWin != p.lWin)
	} else {
		var h = (n.treeNod.lWin != p.treeNod.lWin)
	}
	if (!this.callEvent("onDrag", [n.id, p.id, (m ? m.id : null), n.treeNod,
					p.treeNod])) {
		return false
	}
	if ((p.XMLload == 0) && (this.XMLsource)) {
		p.XMLload = 1;
		this._loadDynXML(p.id)
	}
	this.openItem(p.id);
	var d = n.treeNod;
	var k = n.parentObject.childsCount;
	var l = n.parentObject;
	if ((h) || (d.dpcpy)) {
		var e = n.id;
		n = this._recreateBranch(n, p, m);
		if (!d.dpcpy) {
			d.deleteItem(e)
		}
	} else {
		var f = p.childsCount;
		var o = p.childNodes;
		if (f == 0) {
			p._open = true
		}
		d._unselectItem(n);
		o[f] = n;
		n.treeNod = p.treeNod;
		p.childsCount++;
		var j = this._drawNewTr(o[f].htmlNode);
		if (!m) {
			p.htmlNode.childNodes[0].appendChild(j);
			if (this.dadmode == 1) {
				this._fixNodesCollection(p, m)
			}
		} else {
			p.htmlNode.childNodes[0].insertBefore(j, m.tr);
			this._fixNodesCollection(p, m);
			o = p.childNodes
		}
	}
	if ((!d.dpcpy) && (!h)) {
		var a = n.tr;
		if ((document.all)
				&& (navigator.appVersion.search(/MSIE\ 5\.0/gi) != -1)) {
			window.setTimeout(function() {
						a.parentNode.removeChild(a)
					}, 250)
		} else {
			n.parentObject.htmlNode.childNodes[0].removeChild(n.tr)
		}
		if ((!m) || (p != n.parentObject)) {
			for (var g = 0; g < l.childsCount; g++) {
				if (l.childNodes[g].id == n.id) {
					l.childNodes[g] = 0;
					break
				}
			}
		} else {
			l.childNodes[l.childsCount - 1] = 0
		}
		d._compressChildList(l.childsCount, l.childNodes);
		l.childsCount--
	}
	if ((!h) && (!d.dpcpy)) {
		n.tr = j;
		j.nodem = n;
		n.parentObject = p;
		if (d != p.treeNod) {
			if (n.treeNod._registerBranch(n, d)) {
				return
			}
			this._clearStyles(n);
			this._redrawFrom(this, n.parentObject)
		}
		this._correctPlus(p);
		this._correctLine(p);
		this._correctLine(n);
		this._correctPlus(n);
		if (m) {
			this._correctPlus(m)
		} else {
			if (p.childsCount >= 2) {
				this._correctPlus(o[p.childsCount - 2]);
				this._correctLine(o[p.childsCount - 2])
			}
		}
		this._correctPlus(o[p.childsCount - 1]);
		if (this.tscheck) {
			this._correctCheckStates(p)
		}
		if (d.tscheck) {
			d._correctCheckStates(l)
		}
	}
	if (k > 1) {
		d._correctPlus(l.childNodes[k - 2]);
		d._correctLine(l.childNodes[k - 2])
	}
	d._correctPlus(l);
	d._correctLine(l);
	this.callEvent("onDrop", [n.id, p.id, (m ? m.id : null), d, p.treeNod]);
	return n.id
};
dhtmlXTreeObject.prototype._clearStyles = function(a) {
	if (!a.htmlNode) {
		return
	}
	var e = a.htmlNode.childNodes[0].childNodes[0].childNodes[1];
	var c = e.nextSibling.nextSibling;
	a.span.innerHTML = a.label;
	a.i_sel = false;
	if (a._aimgs) {
		this.dragger.removeDraggableItem(e.nextSibling)
	}
	if (this.checkBoxOff) {
		e.childNodes[0].style.display = "";
		e.childNodes[0].onclick = this.onCheckBoxClick;
		this._setSrc(e.childNodes[0], this.imPath
						+ this.checkArray[a.checkstate])
	} else {
		e.childNodes[0].style.display = "none"
	}
	e.childNodes[0].treeNod = this;
	this.dragger.removeDraggableItem(c);
	if (this.dragAndDropOff) {
		this.dragger.addDraggableItem(c, this)
	}
	if (this._aimgs) {
		this.dragger.addDraggableItem(e.nextSibling, this)
	}
	c.childNodes[0].className = "standartTreeRow";
	c.onclick = this.onRowSelect;
	c.ondblclick = this.onRowClick2;
	e.previousSibling.onclick = this.onRowClick;
	this._correctLine(a);
	this._correctPlus(a);
	for (var d = 0; d < a.childsCount; d++) {
		this._clearStyles(a.childNodes[d])
	}
};
dhtmlXTreeObject.prototype._registerBranch = function(c, a) {
	if (a) {
		a._globalIdStorageSub(c.id)
	}
	c.id = this._globalIdStorageAdd(c.id, c);
	c.treeNod = this;
	for (var d = 0; d < c.childsCount; d++) {
		this._registerBranch(c.childNodes[d], a)
	}
	return 0
};
dhtmlXTreeObject.prototype.enableThreeStateCheckboxes = function(a) {
	this.tscheck = convertStringToBoolean(a)
};
dhtmlXTreeObject.prototype.setOnMouseInHandler = function(a) {
	this.ehlt = true;
	this.attachEvent("onMouseIn", a)
};
dhtmlXTreeObject.prototype.setOnMouseOutHandler = function(a) {
	this.ehlt = true;
	this.attachEvent("onMouseOut", a)
};
dhtmlXTreeObject.prototype.enableTreeImages = function(a) {
	this.timgen = convertStringToBoolean(a)
};
dhtmlXTreeObject.prototype.enableFixedMode = function(a) {
	this.hfMode = convertStringToBoolean(a)
};
dhtmlXTreeObject.prototype.enableCheckBoxes = function(c, a) {
	this.checkBoxOff = convertStringToBoolean(c);
	this.cBROf = (!(this.checkBoxOff || convertStringToBoolean(a)))
};
dhtmlXTreeObject.prototype.setStdImages = function(a, d, c) {
	this.imageArray[0] = a;
	this.imageArray[1] = d;
	this.imageArray[2] = c
};
dhtmlXTreeObject.prototype.enableTreeLines = function(a) {
	this.treeLinesOn = convertStringToBoolean(a)
};
dhtmlXTreeObject.prototype.setImageArrays = function(e, a, g, f, d, c) {
	switch (e) {
		case "plus" :
			this.plusArray[0] = a;
			this.plusArray[1] = g;
			this.plusArray[2] = f;
			this.plusArray[3] = d;
			this.plusArray[4] = c;
			break;
		case "minus" :
			this.minusArray[0] = a;
			this.minusArray[1] = g;
			this.minusArray[2] = f;
			this.minusArray[3] = d;
			this.minusArray[4] = c;
			break
	}
};
dhtmlXTreeObject.prototype.openItem = function(c) {
	var a = this._globalIdStorageFind(c);
	if (!a) {
		return 0
	} else {
		return this._openItem(a)
	}
};
dhtmlXTreeObject.prototype._openItem = function(a) {
	var c = this._getOpenState(a);
	if ((c < 0) || (((this.XMLsource) && (!a.XMLload)))) {
		if (!this.callEvent("onOpenStart", [a.id, c])) {
			return 0
		}
		this._HideShow(a, 2);
		if (this.checkEvent("onOpenEnd")) {
			if (this.onXLE == this._epnFHe) {
				this._epnFHe(this, a.id, true)
			}
			if (!this.xmlstate || !this.XMLsource) {
				this.callEvent("onOpenEnd", [a.id, this._getOpenState(a)])
			} else {
				this._oie_onXLE.push(this.onXLE);
				this.onXLE = this._epnFHe
			}
		}
	} else {
		if (this._srnd) {
			this._HideShow(a, 2)
		}
	}
	if (a.parentObject && !this._skip_open_parent) {
		this._openItem(a.parentObject)
	}
};
dhtmlXTreeObject.prototype.closeItem = function(c) {
	if (this.rootId == c) {
		return 0
	}
	var a = this._globalIdStorageFind(c);
	if (!a) {
		return 0
	}
	if (a.closeble) {
		this._HideShow(a, 1)
	}
};
dhtmlXTreeObject.prototype.getLevel = function(c) {
	var a = this._globalIdStorageFind(c);
	if (!a) {
		return 0
	}
	return this._getNodeLevel(a, 0)
};
dhtmlXTreeObject.prototype.setItemCloseable = function(d, a) {
	a = convertStringToBoolean(a);
	if ((d) && (d.span)) {
		var c = d
	} else {
		var c = this._globalIdStorageFind(d)
	}
	if (!c) {
		return 0
	}
	c.closeble = a
};
dhtmlXTreeObject.prototype._getNodeLevel = function(a, c) {
	if (a.parentObject) {
		return this._getNodeLevel(a.parentObject, c + 1)
	}
	return (c)
};
dhtmlXTreeObject.prototype.hasChildren = function(c) {
	var a = this._globalIdStorageFind(c);
	if (!a) {
		return 0
	} else {
		if ((this.XMLsource) && (!a.XMLload)) {
			return true
		} else {
			return a.childsCount
		}
	}
};
dhtmlXTreeObject.prototype._getLeafCount = function(e) {
	var d = 0;
	for (var c = 0; c < e.childsCount; c++) {
		if (e.childNodes[c].childsCount == 0) {
			d++
		}
	}
	return d
};
dhtmlXTreeObject.prototype.setItemText = function(e, d, c) {
	var a = this._globalIdStorageFind(e);
	if (!a) {
		return 0
	}
	a.label = d;
	a.span.innerHTML = d;
	a.span.parentNode.parentNode.title = c || ""
};
dhtmlXTreeObject.prototype.getItemTooltip = function(c) {
	var a = this._globalIdStorageFind(c);
	if (!a) {
		return ""
	}
	return (a.span.parentNode.parentNode._dhx_title
			|| a.span.parentNode.parentNode.title || "")
};
dhtmlXTreeObject.prototype.refreshItem = function(c) {
	if (!c) {
		c = this.rootId
	}
	var a = this._globalIdStorageFind(c);
	this.deleteChildItems(c);
	this._loadDynXML(c)
};
dhtmlXTreeObject.prototype.setItemImage2 = function(e, a, f, d) {
	var c = this._globalIdStorageFind(e);
	if (!c) {
		return 0
	}
	c.images[1] = f;
	c.images[2] = d;
	c.images[0] = a;
	this._correctPlus(c)
};
dhtmlXTreeObject.prototype.setItemImage = function(d, a, e) {
	var c = this._globalIdStorageFind(d);
	if (!c) {
		return 0
	}
	if (e) {
		c.images[1] = a;
		c.images[2] = e
	} else {
		c.images[0] = a
	}
	this._correctPlus(c)
};
dhtmlXTreeObject.prototype.getSubItems = function(d) {
	var a = this._globalIdStorageFind(d, 0, 1);
	if (!a) {
		return 0
	}
	var c = "";
	for (i = 0; i < a.childsCount; i++) {
		if (!c) {
			c = a.childNodes[i].id
		} else {
			c += this.dlmtr + a.childNodes[i].id
		}
	}
	return c
};
dhtmlXTreeObject.prototype.getSubItemsObj = function(d) {
	var c = [];
	var a = this._globalIdStorageFind(d, 0, 1);
	if (!a) {
		return c
	}
	for (i = 0; i < a.childsCount; i++) {
		var e = a.childNodes[i];
		c.push({
					value : e.id,
					text : e.label
				})
	}
	return c
};
dhtmlXTreeObject.prototype._getAllScraggyItems = function(d) {
	var e = "";
	for (var c = 0; c < d.childsCount; c++) {
		if ((d.childNodes[c].unParsed) || (d.childNodes[c].childsCount > 0)) {
			if (d.childNodes[c].unParsed) {
				var a = this
						._getAllScraggyItemsXML(d.childNodes[c].unParsed, 1)
			} else {
				var a = this._getAllScraggyItems(d.childNodes[c])
			}
			if (a) {
				if (e) {
					e += this.dlmtr + a
				} else {
					e = a
				}
			}
		} else {
			if (!e) {
				e = d.childNodes[c].id
			} else {
				e += this.dlmtr + d.childNodes[c].id
			}
		}
	}
	return e
};
dhtmlXTreeObject.prototype._getAllFatItems = function(d) {
	var e = "";
	for (var c = 0; c < d.childsCount; c++) {
		if ((d.childNodes[c].unParsed) || (d.childNodes[c].childsCount > 0)) {
			if (!e) {
				e = d.childNodes[c].id
			} else {
				e += this.dlmtr + d.childNodes[c].id
			}
			if (d.childNodes[c].unParsed) {
				var a = this._getAllFatItemsXML(d.childNodes[c].unParsed, 1)
			} else {
				var a = this._getAllFatItems(d.childNodes[c])
			}
			if (a) {
				e += this.dlmtr + a
			}
		}
	}
	return e
};
dhtmlXTreeObject.prototype._getAllSubItems = function(g, f, e) {
	if (e) {
		c = e
	} else {
		var c = this._globalIdStorageFind(g)
	}
	if (!c) {
		return 0
	}
	f = "";
	for (var d = 0; d < c.childsCount; d++) {
		if (!f) {
			f = c.childNodes[d].id
		} else {
			f += this.dlmtr + c.childNodes[d].id
		}
		var a = this._getAllSubItems(0, f, c.childNodes[d]);
		if (a) {
			f += this.dlmtr + a
		}
	}
	return f
};
dhtmlXTreeObject.prototype.selectItem = function(e, d, c) {
	d = convertStringToBoolean(d);
	var a = this._globalIdStorageFind(e);
	if ((!a) || (!a.parentObject)) {
		return 0
	}
	if (this.XMLloadingWarning) {
		a.parentObject.openMe = 1
	} else {
		this._openItem(a.parentObject)
	}
	var f = null;
	if (c) {
		f = new Object;
		f.ctrlKey = true;
		if (a.i_sel) {
			f.skipUnSel = true
		}
	}
	if (d) {
		this.onRowSelect(f,
				a.htmlNode.childNodes[0].childNodes[0].childNodes[3], false)
	} else {
		this.onRowSelect(f,
				a.htmlNode.childNodes[0].childNodes[0].childNodes[3], true)
	}
};
dhtmlXTreeObject.prototype.getSelectedItemText = function() {
	var c = new Array();
	for (var a = 0; a < this._selected.length; a++) {
		c[a] = this._selected[a].span.innerHTML
	}
	return (c.join(this.dlmtr))
};
dhtmlXTreeObject.prototype._compressChildList = function(a, d) {
	a--;
	for (var c = 0; c < a; c++) {
		if (d[c] == 0) {
			d[c] = d[c + 1];
			d[c + 1] = 0
		}
	}
};
dhtmlXTreeObject.prototype._deleteNode = function(h, f, k) {
	if ((!f) || (!f.parentObject)) {
		return 0
	}
	var a = 0;
	var c = 0;
	if (f.tr.nextSibling) {
		a = f.tr.nextSibling.nodem
	}
	if (f.tr.previousSibling) {
		c = f.tr.previousSibling.nodem
	}
	var g = f.parentObject;
	var d = g.childsCount;
	var j = g.childNodes;
	for (var e = 0; e < d; e++) {
		if (j[e].id == h) {
			if (!k) {
				g.htmlNode.childNodes[0].removeChild(j[e].tr)
			}
			j[e] = 0;
			break
		}
	}
	this._compressChildList(d, j);
	if (!k) {
		g.childsCount--
	}
	if (a) {
		this._correctPlus(a);
		this._correctLine(a)
	}
	if (c) {
		this._correctPlus(c);
		this._correctLine(c)
	}
	if (this.tscheck) {
		this._correctCheckStates(g)
	}
	if (!k) {
		this._globalIdStorageRecSub(f)
	}
};
dhtmlXTreeObject.prototype.setCheck = function(d, c) {
	var a = this._globalIdStorageFind(d, 0, 1);
	if (!a) {
		return
	}
	if (c === "unsure") {
		this._setCheck(a, c)
	} else {
		c = convertStringToBoolean(c);
		if ((this.tscheck) && (this.smcheck)) {
			this._setSubChecked(c, a)
		} else {
			this._setCheck(a, c)
		}
	}
	if (this.smcheck) {
		this._correctCheckStates(a.parentObject)
	}
};
dhtmlXTreeObject.prototype._setCheck = function(a, d) {
	if (!a) {
		return
	}
	if (((a.parentObject._r_logic) || (this._frbtr)) && (d)) {
		if (this._frbtrs) {
			if (this._frbtrL) {
				this.setCheck(this._frbtrL.id, 0)
			}
			this._frbtrL = a
		} else {
			for (var c = 0; c < a.parentObject.childsCount; c++) {
				this._setCheck(a.parentObject.childNodes[c], 0)
			}
		}
	}
	var e = a.htmlNode.childNodes[0].childNodes[0].childNodes[1].childNodes[0];
	if (d == "unsure") {
		a.checkstate = 2
	} else {
		if (d) {
			a.checkstate = 1
		} else {
			a.checkstate = 0
		}
	}
	if (a.dscheck) {
		a.checkstate = a.dscheck
	}
	this._setSrc(e, this.imPath
					+ ((a.parentObject._r_logic || this._frbtr)
							? this.radioArray
							: this.checkArray)[a.checkstate])
};
dhtmlXTreeObject.prototype.setSubChecked = function(d, c) {
	var a = this._globalIdStorageFind(d);
	this._setSubChecked(c, a);
	this._correctCheckStates(a.parentObject)
};
dhtmlXTreeObject.prototype._setSubChecked = function(d, a) {
	d = convertStringToBoolean(d);
	if (!a) {
		return
	}
	if (((a.parentObject._r_logic) || (this._frbtr)) && (d)) {
		for (var c = 0; c < a.parentObject.childsCount; c++) {
			this._setSubChecked(0, a.parentObject.childNodes[c])
		}
	}
	if (a._r_logic || this._frbtr) {
		this._setSubChecked(d, a.childNodes[0])
	} else {
		for (var c = 0; c < a.childsCount; c++) {
			this._setSubChecked(d, a.childNodes[c])
		}
	}
	var e = a.htmlNode.childNodes[0].childNodes[0].childNodes[1].childNodes[0];
	if (d) {
		a.checkstate = 1
	} else {
		a.checkstate = 0
	}
	if (a.dscheck) {
		a.checkstate = a.dscheck
	}
	this._setSrc(e, this.imPath
					+ ((a.parentObject._r_logic || this._frbtr)
							? this.radioArray
							: this.checkArray)[a.checkstate])
};
dhtmlXTreeObject.prototype.isItemChecked = function(c) {
	var a = this._globalIdStorageFind(c);
	if (!a) {
		return
	}
	return a.checkstate
};
dhtmlXTreeObject.prototype.deleteChildItems = function(e) {
	var a = this._globalIdStorageFind(e);
	if (!a) {
		return
	}
	var c = a.childsCount;
	for (var d = 0; d < c; d++) {
		this._deleteNode(a.childNodes[0].id, a.childNodes[0])
	}
};
dhtmlXTreeObject.prototype.deleteItem = function(d, a) {
	if ((!this._onrdlh) || (this._onrdlh(d))) {
		var c = this._deleteItem(d, a)
	}
	this.allTree.childNodes[0].border = "1";
	this.allTree.childNodes[0].border = "0"
};
dhtmlXTreeObject.prototype._deleteItem = function(g, c, f) {
	c = convertStringToBoolean(c);
	var a = this._globalIdStorageFind(g);
	if (!a) {
		return
	}
	var d = this.getParentId(g);
	var e = a.parentObject;
	this._deleteNode(g, a, f);
	this._correctPlus(e);
	this._correctLine(e);
	if ((c) && (d != this.rootId)) {
		this.selectItem(d, 1)
	}
	return e
};
dhtmlXTreeObject.prototype._globalIdStorageRecSub = function(a) {
	for (var c = 0; c < a.childsCount; c++) {
		this._globalIdStorageRecSub(a.childNodes[c]);
		this._globalIdStorageSub(a.childNodes[c].id)
	}
	this._globalIdStorageSub(a.id);
	var d = a;
	d.span = null;
	d.tr.nodem = null;
	d.tr = null;
	d.htmlNode = null
};
dhtmlXTreeObject.prototype.insertNewNext = function(j, m, l, d, g, f, e, c, a) {
	var h = this._globalIdStorageFind(j);
	if ((!h) || (!h.parentObject)) {
		return (0)
	}
	var k = this._attachChildNode(0, m, l, d, g, f, e, c, a, h);
	return k
};
dhtmlXTreeObject.prototype.getItemIdByIndex = function(d, a) {
	var c = this._globalIdStorageFind(d);
	if ((!c) || (a >= c.childsCount)) {
		return null
	}
	return c.childNodes[a].id
};
dhtmlXTreeObject.prototype.getChildItemIdByIndex = function(d, a) {
	var c = this._globalIdStorageFind(d);
	if ((!c) || (a >= c.childsCount)) {
		return null
	}
	return c.childNodes[a].id
};
dhtmlXTreeObject.prototype.setDragHandler = function(a) {
	this.attachEvent("onDrag", a)
};
dhtmlXTreeObject.prototype._clearMove = function() {
	if (this._lastMark) {
		this._lastMark.className = this._lastMark.className.replace(
				/dragAndDropRow/g, "");
		this._lastMark = null
	}
	this.selectionBar.style.display = "none";
	this.allTree.className = this.allTree.className
			.replace(" selectionBox", "")
};
dhtmlXTreeObject.prototype.enableContextMenu = function(c) {
	if (c) {
		this.cMenu = c
	}
};
dhtmlXTreeObject.prototype.enableDragAndDrop = function(c, a) {
	if (c == "temporary_disabled") {
		this.dADTempOff = false;
		c = true
	} else {
		this.dADTempOff = true
	}
	this.dragAndDropOff = convertStringToBoolean(c);
	if (this.dragAndDropOff) {
		this.dragger.addDragLanding(this.allTree, this)
	}
	if (arguments.length > 1) {
		this._ddronr = (!convertStringToBoolean(a))
	}
};
dhtmlXTreeObject.prototype._setMove = function(f, d, h) {
	if (f.parentObject.span) {
		var e = getAbsoluteTop(f);
		var c = getAbsoluteTop(this.allTree) - this.allTree.scrollTop;
		this.dadmodec = this.dadmode;
		this.dadmodefix = 0;
		if (this.dadmode == 2) {
			var g = h
					- e
					+ (document.body.scrollTop || document.documentElement.scrollTop)
					- 2 - f.offsetHeight / 2;
			if ((Math.abs(g) - f.offsetHeight / 6) > 0) {
				this.dadmodec = 1;
				if (g < 0) {
					this.dadmodefix = 0 - f.offsetHeight
				}
			} else {
				this.dadmodec = 0
			}
		}
		if (this.dadmodec == 0) {
			var a = f.parentObject.span;
			a.className += " dragAndDropRow";
			this._lastMark = a
		} else {
			this._clearMove();
			this.selectionBar.style.top = (e
					- c
					+ ((parseInt(f.parentObject.span.parentNode.previousSibling.childNodes[0].style.height) || 18) - 1) + this.dadmodefix)
					+ "px";
			this.selectionBar.style.left = "5px";
			if (this.allTree.offsetWidth > 20) {
				this.selectionBar.style.width = (this.allTree.offsetWidth - (_isFF
						? 30
						: 25))
						+ "px"
			}
			this.selectionBar.style.display = ""
		}
		this._autoScroll(null, e, c)
	}
};
dhtmlXTreeObject.prototype._autoScroll = function(d, c, a) {
	if (this.autoScroll) {
		if (d) {
			c = getAbsoluteTop(d);
			a = getAbsoluteTop(this.allTree)
		}
		if ((c - a - parseInt(this.allTree.scrollTop)) > (parseInt(this.allTree.offsetHeight) - 50)) {
			this.allTree.scrollTop = parseInt(this.allTree.scrollTop) + 20
		}
		if ((c - a) < (parseInt(this.allTree.scrollTop) + 30)) {
			this.allTree.scrollTop = parseInt(this.allTree.scrollTop) - 20
		}
	}
};
dhtmlXTreeObject.prototype._createDragNode = function(g, f) {
	if (!this.dADTempOff) {
		return null
	}
	var d = g.parentObject;
	if (!this.callEvent("onBeforeDrag", [d.id])) {
		return null
	}
	if (!d.i_sel) {
		this._selectItem(d, f)
	}
	var c = document.createElement("div");
	var h = new Array();
	if (this._itim_dg) {
		for (var a = 0; a < this._selected.length; a++) {
			h[a] = "<table cellspacing='0' cellpadding='0'><tr><td><img width='18px' height='18px' src='"
					+ this
							._getSrc(this._selected[a].span.parentNode.previousSibling.childNodes[0])
					+ "'></td><td>"
					+ this._selected[a].span.innerHTML
					+ "</td></tr></table>"
		}
	} else {
		h = this.getSelectedItemText().split(this.dlmtr)
	}
	c.innerHTML = h.join("");
	c.style.position = "absolute";
	c.className = "dragSpanDiv";
	this._dragged = (new Array()).concat(this._selected);
	return c
};
dhtmlXTreeObject.prototype._focusNode = function(a) {
	var c = getAbsoluteTop(a.htmlNode) - getAbsoluteTop(this.allTree);
	if ((c > (this.allTree.scrollTop + this.allTree.offsetHeight - 30))
			|| (c < this.allTree.scrollTop)) {
		this.allTree.scrollTop = c
	}
};
dhtmlXTreeObject.prototype._preventNsDrag = function(a) {
	if ((a) && (a.preventDefault)) {
		a.preventDefault();
		return false
	}
	return false
};
dhtmlXTreeObject.prototype._drag = function(h, j, a) {
	if (this._autoOpenTimer) {
		clearTimeout(this._autoOpenTimer)
	}
	if (!a.parentObject) {
		a = this.htmlNode.htmlNode.childNodes[0].childNodes[0].childNodes[1].childNodes[0];
		this.dadmodec = 0
	}
	this._clearMove();
	var g = h.parentObject.treeNod;
	if ((g) && (g._clearMove)) {
		g._clearMove("")
	}
	if ((!this.dragMove) || (this.dragMove())) {
		if ((!g) || (!g._clearMove) || (!g._dragged)) {
			var e = new Array(h.parentObject)
		} else {
			var e = g._dragged
		}
		var c = a.parentObject;
		for (var f = 0; f < e.length; f++) {
			var d = this._moveNode(e[f], c);
			if ((this.dadmodec) && (d !== false)) {
				c = this._globalIdStorageFind(d, true, true)
			}
			if ((d) && (!this._sADnD)) {
				this.selectItem(d, 0, 1)
			}
		}
	}
	if (g) {
		g._dragged = new Array()
	}
};
dhtmlXTreeObject.prototype._dragIn = function(g, e, a, j) {
	if (!this.dADTempOff) {
		return 0
	}
	var h = e.parentObject;
	var c = g.parentObject;
	if ((!c) && (this._ddronr)) {
		return
	}
	if (!this.callEvent("onDragIn", [h.id, c ? c.id : null, h.treeNod, this])) {
		if (c) {
			this._autoScroll(g)
		}
		return 0
	}
	if (!c) {
		this.allTree.className += " selectionBox"
	} else {
		if (h.childNodes == null) {
			this._setMove(g, a, j);
			return g
		}
		var f = h.treeNod;
		for (var d = 0; d < f._dragged.length; d++) {
			if (this._checkPNodes(c, f._dragged[d])) {
				this._autoScroll(g);
				return 0
			}
		}
		this._setMove(g, a, j);
		if (this._getOpenState(c) <= 0) {
			this._autoOpenId = c.id;
			this._autoOpenTimer = window.setTimeout(new callerFunction(
							this._autoOpenItem, this), 1000)
		}
	}
	return g
};
dhtmlXTreeObject.prototype._autoOpenItem = function(c, a) {
	a.openItem(a._autoOpenId)
};
dhtmlXTreeObject.prototype._dragOut = function(a) {
	this._clearMove();
	if (this._autoOpenTimer) {
		clearTimeout(this._autoOpenTimer)
	}
};
dhtmlXTreeObject.prototype.moveItem = function(j, h, d, c) {
	var a = this._globalIdStorageFind(j);
	if (!a) {
		return (0)
	}
	switch (h) {
		case "right" :
			alert("Not supported yet");
			break;
		case "item_child" :
			var f = (c || this)._globalIdStorageFind(d);
			if (!f) {
				return (0)
			}
			(c || this)._moveNodeTo(a, f, 0);
			break;
		case "item_sibling" :
			var f = (c || this)._globalIdStorageFind(d);
			if (!f) {
				return (0)
			}
			(c || this)._moveNodeTo(a, f.parentObject, f);
			break;
		case "item_sibling_next" :
			var f = (c || this)._globalIdStorageFind(d);
			if (!f) {
				return (0)
			}
			if ((f.tr) && (f.tr.nextSibling) && (f.tr.nextSibling.nodem)) {
				(c || this)._moveNodeTo(a, f.parentObject,
						f.tr.nextSibling.nodem)
			} else {
				(c || this)._moveNodeTo(a, f.parentObject)
			}
			break;
		case "left" :
			if (a.parentObject.parentObject) {
				this
						._moveNodeTo(a, a.parentObject.parentObject,
								a.parentObject)
			}
			break;
		case "up" :
			var g = this._getPrevNode(a);
			if ((g == -1) || (!g.parentObject)) {
				return
			}
			this._moveNodeTo(a, g.parentObject, g);
			break;
		case "up_strict" :
			var g = this._getIndex(a);
			if (g != 0) {
				this._moveNodeTo(a, a.parentObject, a.parentObject.childNodes[g
								- 1])
			}
			break;
		case "down_strict" :
			var g = this._getIndex(a);
			var e = a.parentObject.childsCount - 2;
			if (g == e) {
				this._moveNodeTo(a, a.parentObject)
			} else {
				if (g < e) {
					this._moveNodeTo(a, a.parentObject,
							a.parentObject.childNodes[g + 2])
				}
			}
			break;
		case "down" :
			var g = this._getNextNode(this._lastChild(a));
			if ((g == -1) || (!g.parentObject)) {
				return
			}
			if (g.parentObject == a.parentObject) {
				var g = this._getNextNode(g)
			}
			if (g == -1) {
				this._moveNodeTo(a, a.parentObject)
			} else {
				if ((g == -1) || (!g.parentObject)) {
					return
				}
				this._moveNodeTo(a, g.parentObject, g)
			}
			break
	}
	if (_isIE && _isIE < 8) {
		this.allTree.childNodes[0].border = "1";
		this.allTree.childNodes[0].border = "0"
	}
};
dhtmlXTreeObject.prototype.setDragBehavior = function(c, a) {
	this._sADnD = (!convertStringToBoolean(a));
	switch (c) {
		case "child" :
			this.dadmode = 0;
			break;
		case "sibling" :
			this.dadmode = 1;
			break;
		case "complex" :
			this.dadmode = 2;
			break
	}
};
dhtmlXTreeObject.prototype._loadDynXML = function(d, c) {
	c = c || this.XMLsource;
	var a = (new Date()).valueOf();
	this._ld_id = d;
	this.loadXML(c + getUrlSymbol(c) + "uid=" + a + "&id=" + this._escape(d))
};
dhtmlXTreeObject.prototype._checkPNodes = function(c, a) {
	if (a == c) {
		return 1
	}
	if (c.parentObject) {
		return this._checkPNodes(c.parentObject, a)
	} else {
		return 0
	}
};
dhtmlXTreeObject.prototype.preventIECaching = function(a) {
	this.no_cashe = convertStringToBoolean(a);
	this.XMLLoader.rSeed = this.no_cashe
};
dhtmlXTreeObject.prototype.preventIECashing = dhtmlXTreeObject.prototype.preventIECaching;
dhtmlXTreeObject.prototype.disableCheckbox = function(d, c) {
	if (typeof(d) != "object") {
		var a = this._globalIdStorageFind(d, 0, 1)
	} else {
		var a = d
	}
	if (!a) {
		return
	}
	a.dscheck = convertStringToBoolean(c)
			? (((a.checkstate || 0) % 3) + 3)
			: ((a.checkstate > 2) ? (a.checkstate - 3) : a.checkstate);
	this._setCheck(a);
	if (a.dscheck < 3) {
		a.dscheck = false
	}
};
dhtmlXTreeObject.prototype.setEscapingMode = function(a) {
	this.utfesc = a
};
dhtmlXTreeObject.prototype.enableHighlighting = function(a) {
	this.ehlt = true;
	this.ehlta = convertStringToBoolean(a)
};
dhtmlXTreeObject.prototype._itemMouseOut = function() {
	var c = this.childNodes[3].parentObject;
	var a = c.treeNod;
	a.callEvent("onMouseOut", [c.id]);
	if (c.id == a._l_onMSI) {
		a._l_onMSI = null
	}
	if (!a.ehlta) {
		return
	}
	c.span.className = c.span.className.replace("_lor", "")
};
dhtmlXTreeObject.prototype._itemMouseIn = function() {
	var c = this.childNodes[3].parentObject;
	var a = c.treeNod;
	if (a._l_onMSI != c.id) {
		a.callEvent("onMouseIn", [c.id])
	}
	a._l_onMSI = c.id;
	if (!a.ehlta) {
		return
	}
	c.span.className = c.span.className.replace("_lor", "");
	c.span.className = c.span.className.replace(/((standart|selected)TreeRow)/,
			"$1_lor")
};
dhtmlXTreeObject.prototype.enableActiveImages = function(a) {
	this._aimgs = convertStringToBoolean(a)
};
dhtmlXTreeObject.prototype.focusItem = function(c) {
	var a = this._globalIdStorageFind(c);
	if (!a) {
		return (0)
	}
	this._focusNode(a)
};
dhtmlXTreeObject.prototype.getAllSubItems = function(a) {
	return this._getAllSubItems(a)
};
dhtmlXTreeObject.prototype.getAllChildless = function() {
	return this._getAllScraggyItems(this.htmlNode)
};
dhtmlXTreeObject.prototype.getAllLeafs = dhtmlXTreeObject.prototype.getAllChildless;
dhtmlXTreeObject.prototype._getAllScraggyItems = function(d) {
	var e = "";
	for (var c = 0; c < d.childsCount; c++) {
		if ((d.childNodes[c].unParsed) || (d.childNodes[c].childsCount > 0)) {
			if (d.childNodes[c].unParsed) {
				var a = this
						._getAllScraggyItemsXML(d.childNodes[c].unParsed, 1)
			} else {
				var a = this._getAllScraggyItems(d.childNodes[c])
			}
			if (a) {
				if (e) {
					e += this.dlmtr + a
				} else {
					e = a
				}
			}
		} else {
			if (!e) {
				e = d.childNodes[c].id
			} else {
				e += this.dlmtr + d.childNodes[c].id
			}
		}
	}
	return e
};
dhtmlXTreeObject.prototype._getAllFatItems = function(d) {
	var e = "";
	for (var c = 0; c < d.childsCount; c++) {
		if ((d.childNodes[c].unParsed) || (d.childNodes[c].childsCount > 0)) {
			if (!e) {
				e = d.childNodes[c].id
			} else {
				e += this.dlmtr + d.childNodes[c].id
			}
			if (d.childNodes[c].unParsed) {
				var a = this._getAllFatItemsXML(d.childNodes[c].unParsed, 1)
			} else {
				var a = this._getAllFatItems(d.childNodes[c])
			}
			if (a) {
				e += this.dlmtr + a
			}
		}
	}
	return e
};
dhtmlXTreeObject.prototype.getAllItemsWithKids = function() {
	return this._getAllFatItems(this.htmlNode)
};
dhtmlXTreeObject.prototype.getAllFatItems = dhtmlXTreeObject.prototype.getAllItemsWithKids;
dhtmlXTreeObject.prototype.getAllChecked = function() {
	return this._getAllChecked("", "", 1)
};
dhtmlXTreeObject.prototype.getAllUnchecked = function(a) {
	if (a) {
		a = this._globalIdStorageFind(a)
	}
	return this._getAllChecked(a, "", 0)
};
dhtmlXTreeObject.prototype.getAllPartiallyChecked = function() {
	return this._getAllChecked("", "", 2)
};
dhtmlXTreeObject.prototype.getAllCheckedBranches = function() {
	var a = this._getAllChecked("", "", 1);
	if (a != "") {
		a += this.dlmtr
	}
	return a + this._getAllChecked("", "", 2)
};
dhtmlXTreeObject.prototype._getAllChecked = function(e, d, f) {
	if (!e) {
		e = this.htmlNode
	}
	var g = e.checkstate;
	if (g == 1 || g == 2) {
		if (!e.nocheckbox) {
			if (d && e.id) {
				d.txt += this.dlmtr + e.span.innerHTML;
				d.val += this.dlmtr + e.id
			} else {
				if (e.id) {
					d = {};
					d.val = e.id;
					d.txt = e.span.innerHTML
				}
			}
		}
	}
	var a = e.childsCount;
	for (var c = 0; c < a; c++) {
		d = this._getAllChecked(e.childNodes[c], d, f)
	}
	if (d) {
		return d
	} else {
		return ""
	}
};
dhtmlXTreeObject.prototype.setItemStyle = function(e, d, c) {
	var c = c || false;
	var a = this._globalIdStorageFind(e);
	if (!a) {
		return 0
	}
	if (!a.span.style.cssText) {
		a.span.setAttribute("style", a.span.getAttribute("style") + "; " + d)
	} else {
		a.span.style.cssText = c ? d : a.span.style.cssText + ";" + d
	}
};
dhtmlXTreeObject.prototype.enableImageDrag = function(a) {
	this._itim_dg = convertStringToBoolean(a)
};
dhtmlXTreeObject.prototype.setOnDragIn = function(a) {
	this.attachEvent("onDragIn", a)
};
dhtmlXTreeObject.prototype.enableDragAndDropScrolling = function(a) {
	this.autoScroll = convertStringToBoolean(a)
};
dhtmlXTreeObject.prototype.setSkin = function(a) {
	var c = this.parentObject.className.replace(/dhxtree_[^ ]*/gi, "");
	this.parentObject.className = c + " dhxtree_" + a
};
(function() {
	dhtmlx.extend_api("dhtmlXTreeObject", {
				_init : function(a) {
					return [a.parent, (a.width || "100%"),
							(a.height || "100%"), (a.root_id || 0)]
				},
				auto_save_selection : "enableAutoSavingSelected",
				auto_tooltip : "enableAutoTooltips",
				checkbox : "enableCheckBoxes",
				checkbox_3_state : "enableThreeStateCheckboxes",
				checkbox_smart : "enableSmartCheckboxes",
				context_menu : "enableContextMenu",
				distributed_parsing : "enableDistributedParsing",
				drag : "enableDragAndDrop",
				drag_copy : "enableMercyDrag",
				drag_image : "enableImageDrag",
				drag_scroll : "enableDragAndDropScrolling",
				editor : "enableItemEditor",
				hover : "enableHighlighting",
				images : "enableTreeImages",
				image_fix : "enableIEImageFix",
				image_path : "setImagePath",
				lines : "enableTreeLines",
				loading_item : "enableLoadingItem",
				multiline : "enableMultiLineItems",
				multiselect : "enableMultiselection",
				navigation : "enableKeyboardNavigation",
				radio : "enableRadioButtons",
				radio_single : "enableSingleRadioMode",
				rtl : "enableRTL",
				search : "enableKeySearch",
				smart_parsing : "enableSmartXMLParsing",
				smart_rendering : "enableSmartRendering",
				text_icons : "enableTextSigns",
				xml : "loadXML",
				skin : "setSkin"
			}, {})
})();
function dhtmlXTreeFromHTML(obj) {
	if (typeof(obj) != "object") {
		obj = document.getElementById(obj)
	}
	var n = obj;
	var id = n.id;
	var cont = "";
	for (var j = 0; j < obj.childNodes.length; j++) {
		if (obj.childNodes[j].nodeType == "1") {
			if (obj.childNodes[j].tagName == "XMP") {
				var cHead = obj.childNodes[j];
				for (var m = 0; m < cHead.childNodes.length; m++) {
					cont += cHead.childNodes[m].data
				}
			} else {
				if (obj.childNodes[j].tagName.toLowerCase() == "ul") {
					cont = dhx_li2trees(obj.childNodes[j], new Array(), 0)
				}
			}
			break
		}
	}
	obj.innerHTML = "";
	var t = new dhtmlXTreeObject(obj, "100%", "100%", 0);
	var z_all = new Array();
	for (b in t) {
		z_all[b.toLowerCase()] = b
	}
	var atr = obj.attributes;
	for (var a = 0; a < atr.length; a++) {
		if ((atr[a].name.indexOf("set") == 0)
				|| (atr[a].name.indexOf("enable") == 0)) {
			var an = atr[a].name;
			if (!t[an]) {
				an = z_all[atr[a].name]
			}
			t[an].apply(t, atr[a].value.split(","))
		}
	}
	if (typeof(cont) == "object") {
		t.XMLloadingWarning = 1;
		for (var i = 0; i < cont.length; i++) {
			var n = t.insertNewItem(cont[i][0], cont[i][3], cont[i][1]);
			if (cont[i][2]) {
				t._setCheck(n, cont[i][2])
			}
		}
		t.XMLloadingWarning = 0;
		t.lastLoadedXMLId = 0;
		t._redrawFrom(t)
	} else {
		t.loadXMLString("<tree id='0'>" + cont + "</tree>")
	}
	if (id != "") {
		window[id] = t
	}
	var oninit = obj.getAttribute("oninit");
	if (oninit) {
		eval(oninit)
	}
	return t
}
function dhx_init_trees() {
	var c = document.getElementsByTagName("div");
	for (var a = 0; a < c.length; a++) {
		if (c[a].className == "dhtmlxTree") {
			dhtmlXTreeFromHTML(c[a])
		}
	}
}
function dhx_li2trees(n, g, d) {
	for (var h = 0; h < n.childNodes.length; h++) {
		var m = n.childNodes[h];
		if ((m.nodeType == 1) && (m.tagName.toLowerCase() == "li")) {
			var l = "";
			var k = null;
			var a = m.getAttribute("checked");
			for (var f = 0; f < m.childNodes.length; f++) {
				var e = m.childNodes[f];
				if (e.nodeType == 3) {
					l += e.data
				} else {
					if (e.tagName.toLowerCase() != "ul") {
						l += dhx_outer_html(e)
					} else {
						k = e
					}
				}
			}
			g[g.length] = [d, l, a, (m.id || (g.length + 1))];
			if (k) {
				g = dhx_li2trees(k, g, (m.id || g.length))
			}
		}
	}
	return g
}
function dhx_outer_html(c) {
	if (c.outerHTML) {
		return c.outerHTML
	}
	var a = document.createElement("DIV");
	a.appendChild(c.cloneNode(true));
	a = a.innerHTML;
	return a
}
if (window.addEventListener) {
	window.addEventListener("load", dhx_init_trees, false)
} else {
	if (window.attachEvent) {
		window.attachEvent("onload", dhx_init_trees)
	}
}
function dhtmlXMenuObject(d, g) {
	var a = this;
	this.addBaseIdAsContextZone = null;
	this.isDhtmlxMenuObject = true;
	this._unloaded = false;
	this.skin = (g != null ? g : "dhx_skyblue");
	this.imagePath = "";
	this._isIE6 = false;
	if (_isIE) {
		this._isIE6 = (window.XMLHttpRequest == null ? true : false)
	}
	if (d == null) {
		this.base = document.body
	} else {
		if (document.getElementById(d) != null) {
			this.base = document.getElementById(d);
			while (this.base.childNodes.length > 0) {
				this.base.removeChild(this.base.childNodes[0])
			}
			this.base.className += " dhtmlxMenu_" + this.skin
					+ "_Middle dir_left";
			this.base._autoSkinUpdate = true;
			this.addBaseIdAsContextZone = d;
			this.base.onselectstart = function(h) {
				h = h || event;
				h.returnValue = false;
				return false
			};
			this.base.oncontextmenu = function(h) {
				h = h || event;
				h.returnValue = false;
				return false
			}
		} else {
			this.base = document.body
		}
	}
	this.topId = "dhxWebMenuTopId";
	if (!this.extendedModule) {
		var e = function() {
			alert("dhtmlxmenu_ext.js required")
		};
		var c = new Array("setItemEnabled", "setItemDisabled", "isItemEnabled",
				"_changeItemState", "getItemText", "setItemText",
				"loadFromHTML", "hideItem", "showItem", "isItemHidden",
				"_changeItemVisible", "setUserData", "getUserData",
				"setOpenMode", "setWebModeTimeout", "enableDynamicLoading",
				"_updateLoaderIcon", "getItemImage", "setItemImage",
				"clearItemImage", "setAutoShowMode", "setAutoHideMode",
				"setContextMenuHideAllMode", "getContextMenuHideAllMode",
				"setVisibleArea", "setTooltip", "getTooltip", "setHotKey",
				"getHotKey", "setItemSelected", "setTopText", "setRTL",
				"setAlign", "setHref", "clearHref", "getCircuit",
				"_clearAllSelectedSubItemsInPolygon", "_checkArrowsState",
				"_addUpArrow", "_addDownArrow", "_removeUpArrow",
				"_removeDownArrow", "_isArrowExists", "_doScrollUp",
				"_doScrollDown", "_countPolygonItems", "setOverflowHeight",
				"_getRadioImgObj", "_setRadioState", "_radioOnClickHandler",
				"getRadioChecked", "setRadioChecked", "addRadioButton",
				"_getCheckboxState", "_setCheckboxState", "_readLevel",
				"_updateCheckboxImage", "_checkboxOnClickHandler",
				"setCheckboxState", "getCheckboxState", "addCheckbox",
				"serialize");
		for (var f = 0; f < c.length; f++) {
			this[c[f]] = e
		}
		c = null
	}
	this.fixedPosition = false;
	this.menuSelected = -1;
	this.menuLastClicked = -1;
	this.idPrefix = "";
	this.itemTagName = "item";
	this.itemTextTagName = "itemtext";
	this.userDataTagName = "userdata";
	this.itemTipTagName = "tooltip";
	this.itemHotKeyTagName = "hotkey";
	this.itemHrefTagName = "href";
	this.dirTopLevel = "bottom";
	this.dirSubLevel = "right";
	this.menuX1 = null;
	this.menuX2 = null;
	this.menuY1 = null;
	this.menuY2 = null;
	this.menuMode = "web";
	this.menuTimeoutMsec = 400;
	this.menuTimeoutHandler = null;
	this.idPull = {};
	this.itemPull = {};
	this.userData = {};
	this.radio = {};
	this._rtl = false;
	this._align = "left";
	this.menuTouched = false;
	this.zIndInit = 1200;
	this.zInd = this.zIndInit;
	this.zIndStep = 50;
	this.menuModeTopLevelTimeout = true;
	this.menuModeTopLevelTimeoutTime = 200;
	this.topLevelItemPaddingIconExists = 27;
	this.topLevelItemPaddingIconNotExists = 6;
	this._topLevelBottomMargin = 1;
	this._topLevelRightMargin = 0;
	this._topLevelOffsetLeft = 1;
	this._arrowFFFix = (_isIE
			? (document.compatMode == "BackCompat" ? 0 : -4)
			: -4);
	this.setSkin = function(j) {
		var k = this.skin;
		this.skin = j;
		switch (this.skin) {
			case "standard" :
				this._topLevelBottomMargin = 1;
				this._topLevelOffsetLeft = 0;
				this._arrowFFFix = (_isIE
						? (document.compatMode == "BackCompat"
								? 0
								: (this._isIE6 ? -5 : -4))
						: -4);
				break;
			case "clear_blue" :
			case "clear_green" :
			case "clear_silver" :
				this._topLevelBottomMargin = 3;
				this._topLevelOffsetLeft = 0;
				this._arrowFFFix = (_isIE
						? (document.compatMode == "BackCompat" ? 0 : -4)
						: -4);
				break;
			case "aqua_orange" :
			case "aqua_sky" :
			case "aqua_dark" :
				this._topLevelBottomMargin = 1;
				this._topLevelOffsetLeft = 0;
				this._arrowFFFix = (_isIE
						? (document.compatMode == "BackCompat" ? 0 : -2)
						: -2);
				break;
			case "modern_blue" :
			case "modern_red" :
			case "modern_black" :
				this._topLevelBottomMargin = 3;
				this._topLevelOffsetLeft = 0;
				this._arrowFFFix = (_isIE
						? (document.compatMode == "BackCompat" ? 0 : -2)
						: -2);
				break;
			case "glassy_blue" :
				this._topLevelBottomMargin = 0;
				this._topLevelOffsetLeft = 0;
				this._arrowFFFix = (_isIE
						? (document.compatMode == "BackCompat" ? 0 : -4)
						: -4);
				break;
			case "dhx_black" :
			case "dhx_blue" :
			case "dhx_skyblue" :
				this._topLevelBottomMargin = 2;
				this._topLevelRightMargin = 1;
				this._topLevelOffsetLeft = 1;
				this._arrowFFFix = (_isIE
						? (document.compatMode == "BackCompat" ? 0 : -4)
						: -4);
				break
		}
		if (this.base._autoSkinUpdate) {
			this.base.className = this.base.className.replace("dhtmlxMenu_" + k
							+ "_Middle", "")
					+ " dhtmlxMenu_" + this.skin + "_Middle"
		}
		for (var h in this.idPull) {
			this.idPull[h].className = String(this.idPull[h].className)
					.replace(k, this.skin)
		}
	};
	this.setSkin(this.skin);
	this.dLoad = false;
	this.dLoadUrl = "";
	this.dLoadSign = "?";
	this.loaderIcon = false;
	this.limit = 0;
	this._scrollUpTM = null;
	this._scrollUpTMTime = 20;
	this._scrollUpTMStep = 3;
	this._scrollDownTM = null;
	this._scrollDownTMTime = 20;
	this._scrollDownTMStep = 3;
	this.context = false;
	this.contextZones = {};
	this.contextMenuZoneId = false;
	this.contextAutoShow = true;
	this.contextAutoHide = true;
	this.contextHideAllMode = true;
	this.sxDacProc = null;
	this.dacSpeed = 10;
	this.dacCycles = [];
	for (var f = 0; f < 10; f++) {
		this.dacCycles[f] = f
	}
	this.dacSpeedIE = 10;
	this.dacCyclesIE = [];
	for (var f = 0; f < 10; f++) {
		this.dacCyclesIE[f] = f
	}
	this._enableDacSupport = function(h) {
		this.sxDacProc = h
	};
	this._selectedSubItems = new Array();
	this._openedPolygons = new Array();
	this._addSubItemToSelected = function(k, j) {
		var h = true;
		for (var l = 0; l < this._selectedSubItems.length; l++) {
			if ((this._selectedSubItems[l][0] == k)
					&& (this._selectedSubItems[l][1] == j)) {
				h = false
			}
		}
		if (h == true) {
			this._selectedSubItems.push(new Array(k, j))
		}
		return h
	};
	this._removeSubItemFromSelected = function(l, k) {
		var h = new Array();
		var j = false;
		for (var n = 0; n < this._selectedSubItems.length; n++) {
			if ((this._selectedSubItems[n][0] == l)
					&& (this._selectedSubItems[n][1] == k)) {
				j = true
			} else {
				h[h.length] = this._selectedSubItems[n]
			}
		}
		if (j == true) {
			this._selectedSubItems = h
		}
		return j
	};
	this._getSubItemToDeselectByPolygon = function(l) {
		var h = new Array();
		for (var n = 0; n < this._selectedSubItems.length; n++) {
			if (this._selectedSubItems[n][1] == l) {
				h[h.length] = this._selectedSubItems[n][0];
				h = h
						.concat(this
								._getSubItemToDeselectByPolygon(this._selectedSubItems[n][0]));
				var k = true;
				for (var j = 0; j < this._openedPolygons.length; j++) {
					if (this._openedPolygons[j] == this._selectedSubItems[n][0]) {
						k = false
					}
				}
				if (k == true) {
					this._openedPolygons[this._openedPolygons.length] = this._selectedSubItems[n][0]
				}
				this._selectedSubItems[n][0] = -1;
				this._selectedSubItems[n][1] = -1
			}
		}
		return h
	};
	this._hidePolygon = function(h) {
		if (this.idPull["polygon_" + h] != null) {
			if ((this.sxDacProc != null) && (this.idPull["sxDac_" + h] != null)) {
				this.idPull["sxDac_" + h]._hide()
			} else {
				if (this.idPull["polygon_" + h].style.display == "none") {
					return
				}
				this.idPull["polygon_" + h].style.display = "none";
				if (this.idPull["arrowup_" + h] != null) {
					this.idPull["arrowup_" + h].style.display = "none"
				}
				if (this.idPull["arrowdown_" + h] != null) {
					this.idPull["arrowdown_" + h].style.display = "none"
				}
				this._updateItemComplexState(h, true, false);
				if (this._isIE6) {
					if (this.idPull["polygon_" + h + "_ie6cover"] != null) {
						this.idPull["polygon_" + h + "_ie6cover"].style.display = "none"
					}
				}
				h = String(h).replace(this.idPrefix, "");
				if (h == this.topId) {
					h = null
				}
				this.callEvent("onHide", [h])
			}
		}
	};
	this._showPolygon = function(B, l) {
		var G = this._countVisiblePolygonItems(B);
		if (G == 0) {
			return
		}
		var C = "polygon_" + B;
		if ((this.idPull[C] != null) && (this.idPull[B] != null)) {
			if (this.menuModeTopLevelTimeout && this.menuMode == "web"
					&& !this.context) {
				if (!this.idPull[B]._mouseOver && l == this.dirTopLevel) {
					return
				}
			}
			if (!this.fixedPosition) {
				this._autoDetectVisibleArea()
			}
			var D = 0;
			var F = 0;
			var J = null;
			var u = null;
			if (this.limit > 0 && this.limit < G) {
				var H = "arrowup_" + B;
				var v = "arrowdown_" + B;
				if (this.idPull["arrowup_" + B] == null) {
					this._addUpArrow(String(B).replace(this.idPrefix, ""))
				}
				if (this.idPull["arrowdown_" + B] == null) {
					this._addDownArrow(String(B).replace(this.idPrefix, ""))
				}
				J = this.idPull["arrowup_" + B];
				J.style.visibility = "hidden";
				J.style.display = "";
				J.style.zIndex = this.zInd;
				D = J.offsetHeight;
				u = this.idPull["arrowdown_" + B];
				u.style.visibility = "hidden";
				u.style.display = "";
				u.style.zIndex = this.zInd;
				F = u.offsetHeight
			}
			this.idPull[C].style.visibility = "hidden";
			this.idPull[C].style.left = "0px";
			this.idPull[C].style.top = "0px";
			this.idPull[C].style.display = "";
			this.idPull[C].style.zIndex = this.zInd;
			if (this.limit > 0) {
				if (this.limit < G) {
					this.idPull[C].style.height = this.idPull[C].childNodes[0].offsetHeight
							* this.limit + "px";
					this.idPull[C].scrollTop = 0
				} else {
					this.idPull[C].style.height = ""
				}
			}
			this.zInd += this.zIndStep;
			if (this.itemPull[B] != null) {
				var s = "polygon_" + this.itemPull[B]["parent"]
			} else {
				if (this.context) {
					var s = this.idPull[this.idPrefix + this.topId]
				}
			}
			var j = (this.idPull[B].tagName != null
					? getAbsoluteLeft(this.idPull[B])
					: this.idPull[B][0]);
			var I = (this.idPull[B].tagName != null
					? getAbsoluteTop(this.idPull[B])
					: this.idPull[B][1]);
			var k = (this.idPull[B].tagName != null
					? this.idPull[B].offsetWidth
					: 0);
			var m = (this.idPull[B].tagName != null
					? this.idPull[B].offsetHeight + D + F
					: 0);
			var q = 0;
			var p = 0;
			var r = this.idPull[C].offsetWidth;
			var E = this.idPull[C].offsetHeight;
			if (l == "bottom") {
				if (this._rtl) {
					q = j + (k != null ? k : 0) - r
				} else {
					if (this._align == "right") {
						q = j + k - r
					} else {
						q = j
								- 1
								+ (l == this.dirTopLevel
										? this._topLevelRightMargin
										: 0)
					}
				}
				p = I - 1 + m - D - F + this._topLevelBottomMargin
			}
			if (l == "right") {
				q = j + k - 1;
				p = I + 2
			}
			if (l == "left") {
				q = j - this.idPull[C].offsetWidth + 2;
				p = I + 2
			}
			if (l == "top") {
				q = j - 1;
				p = I - E + 2
			}
			if (this.fixedPosition) {
				var A = 65536;
				var z = 65536
			} else {
				var A = (this.menuX2 != null ? this.menuX2 : 0);
				var z = (this.menuY2 != null ? this.menuY2 : 0);
				if (A == 0) {
					if (window.innerWidth) {
						A = window.innerWidth;
						z = window.innerHeight
					} else {
						A = document.body.offsetWidth;
						z = document.body.scrollHeight
					}
				}
			}
			if (q + r > A && !this._rtl) {
				q = j - r + 2
			}
			if (q < this.menuX1 && this._rtl) {
				q = j + k - 2
			}
			if (q < 0) {
				q = 0
			}
			if (p + E > z && this.menuY2 != null) {
				p = I + m - E + 2;
				if (this.itemPull[B] != null && !this.context) {
					if (this.itemPull[B]["parent"] == this.idPrefix
							+ this.topId) {
						p = p - this.base.offsetHeight
					}
				}
			}
			this.idPull[C].style.left = q + "px";
			this.idPull[C].style.top = p + D + "px";
			if ((this.sxDacProc != null) && (this.idPull["sxDac_" + B] != null)) {
				this.idPull["sxDac_" + B]._show()
			} else {
				this.idPull[C].style.visibility = "";
				if (this.limit > 0 && this.limit < G) {
					J.style.left = q + "px";
					J.style.top = p + "px";
					J.style.width = r + this._arrowFFFix + "px";
					J.style.visibility = "";
					u.style.left = q + "px";
					u.style.top = p + D + E + "px";
					u.style.width = r + this._arrowFFFix + "px";
					u.style.visibility = "";
					this._checkArrowsState(B)
				}
				if (this._isIE6) {
					var o = C + "_ie6cover";
					if (this.idPull[o] == null) {
						var n = document.createElement("IFRAME");
						n.className = "dhtmlxMenu_IE6CoverFix_" + this.skin;
						n.frameBorder = 0;
						n.setAttribute("src", "javascript:false;");
						document.body.insertBefore(n, document.body.firstChild);
						this.idPull[o] = n
					}
					this.idPull[o].style.left = this.idPull[C].style.left;
					this.idPull[o].style.top = this.idPull[C].style.top;
					this.idPull[o].style.width = this.idPull[C].offsetWidth
							+ "px";
					this.idPull[o].style.height = this.idPull[C].offsetHeight
							+ "px";
					this.idPull[o].style.zIndex = this.idPull[C].style.zIndex
							- 1;
					this.idPull[o].style.display = ""
				}
				B = String(B).replace(this.idPrefix, "");
				if (B == this.topId) {
					B = null
				}
				this.callEvent("onShow", [B])
			}
		}
	};
	this._redistribSubLevelSelection = function(m, l) {
		while (this._openedPolygons.length > 0) {
			this._openedPolygons.pop()
		}
		var h = this._getSubItemToDeselectByPolygon(l);
		this._removeSubItemFromSelected(-1, -1);
		for (var k = 0; k < h.length; k++) {
			if ((this.idPull[h[k]] != null) && (h[k] != m)) {
				if (this.itemPull[h[k]]["state"] == "enabled") {
					this.idPull[h[k]].className = "dhtmlxMenu_" + this.skin
							+ "_SubLevelArea_Item_Normal"
				}
			}
		}
		for (var k = 0; k < this._openedPolygons.length; k++) {
			if (this._openedPolygons[k] != l) {
				this._hidePolygon(this._openedPolygons[k])
			}
		}
		if (this.itemPull[m]["state"] == "enabled") {
			this.idPull[m].className = "dhtmlxMenu_" + this.skin
					+ "_SubLevelArea_Item_Selected";
			if (this.itemPull[m]["complex"] && this.dLoad
					&& (this.itemPull[m]["loaded"] == "no")) {
				if (this.loaderIcon == true) {
					this._updateLoaderIcon(m, true)
				}
				var j = new dtmlXMLLoaderObject(this._xmlParser, window);
				this.itemPull[m]["loaded"] = "get";
				this.callEvent("onXLS", []);
				j.loadXML(this.dLoadUrl + this.dLoadSign
						+ "action=loadMenu&parentId="
						+ m.replace(this.idPrefix, "") + "&etc="
						+ new Date().getTime())
			}
			if (this.itemPull[m]["complex"]
					|| (this.dLoad && (this.itemPull[m]["loaded"] == "yes"))) {
				if ((this.itemPull[m]["complex"])
						&& (this.idPull["polygon_" + m] != null)) {
					this._updateItemComplexState(m, true, true);
					this._showPolygon(m, this.dirSubLevel)
				}
			}
			this._addSubItemToSelected(m, l);
			this.menuSelected = m
		}
	};
	this._doOnClick = function(l, h, k) {
		this.menuLastClicked = l;
		if (this.itemPull[this.idPrefix + l]["href_link"] != null) {
			var j = document.createElement("FORM");
			j.action = this.itemPull[this.idPrefix + l]["href_link"];
			if (this.itemPull[this.idPrefix + l]["href_target"] != null) {
				j.target = this.itemPull[this.idPrefix + l]["href_target"]
			}
			j.style.display = "none";
			document.body.appendChild(j);
			j.submit();
			if (j != null) {
				document.body.removeChild(j);
				j = null
			}
			return
		}
		if (h.charAt(0) == "c") {
			return
		}
		if (h.charAt(1) == "d") {
			return
		}
		if (h.charAt(2) == "s") {
			return
		}
		if (this.checkEvent("onClick")) {
			this._clearAndHide();
			if (this._isContextMenuVisible() && this.contextAutoHide) {
				this._hideContextMenu()
			}
			this.callEvent("onClick", [l, this.contextMenuZoneId, k])
		} else {
			if ((h.charAt(1) == "d")
					|| (this.menuMode == "win" && h.charAt(2) == "t")) {
				return
			}
			this._clearAndHide();
			if (this._isContextMenuVisible() && this.contextAutoHide) {
				this._hideContextMenu()
			}
		}
	};
	this._doOnTouchMenu = function(h) {
		if (this.menuTouched == false) {
			this.menuTouched = true;
			if (this.checkEvent("onTouch")) {
				this.callEvent("onTouch", [h])
			}
		}
	};
	this._searchMenuNode = function(l, o) {
		var h = new Array();
		for (var n = 0; n < o.length; n++) {
			if (typeof(o[n]) == "object") {
				if (o[n].length == 5) {
					if (typeof(o[n][0]) != "object") {
						if ((o[n][0].replace(this.idPrefix, "") == l)
								&& (n == 0)) {
							h = o
						}
					}
				}
				var k = this._searchMenuNode(l, o[n]);
				if (k.length > 0) {
					h = k
				}
			}
		}
		return h
	};
	this._getMenuNodes = function(k) {
		var h = new Array;
		for (var j in this.itemPull) {
			if (this.itemPull[j]["parent"] == k) {
				h[h.length] = j
			}
		}
		return h
	};
	this._genStr = function(h) {
		var j = "";
		var l = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		for (var k = 0; k < h; k++) {
			j = j + l.charAt(Math.round(Math.random() * l.length))
		}
		return j
	};
	this.getItemType = function(h) {
		h = this.idPrefix + h;
		if (this.itemPull[h] == null) {
			return null
		}
		return this.itemPull[h]["type"]
	};
	this.forEachItem = function(j) {
		for (var h in this.itemPull) {
			j(String(h).replace(this.idPrefix, ""))
		}
	};
	this._clearAndHide = function() {
		a.menuSelected = -1;
		a.menuLastClicked = -1;
		while (a._openedPolygons.length > 0) {
			a._openedPolygons.pop()
		}
		for (var h = 0; h < a._selectedSubItems.length; h++) {
			var j = a._selectedSubItems[h][0];
			if (a.idPull[j] != null) {
				if (a.itemPull[j]["state"] == "enabled") {
					if (a.idPull[j].className == "dhtmlxMenu_" + a.skin
							+ "_SubLevelArea_Item_Selected") {
						a.idPull[j].className = "dhtmlxMenu_" + a.skin
								+ "_SubLevelArea_Item_Normal"
					}
					if (a.idPull[j].className == "dhtmlxMenu_" + a.skin
							+ "_TopLevel_Item_Selected") {
						if (a.itemPull[j]["cssNormal"] != null) {
							a.idPull[j].className = a.itemPull[j]["cssNormal"]
						} else {
							a.idPull[j].className = "dhtmlxMenu_" + a.skin
									+ "_TopLevel_Item_Normal"
						}
					}
				}
			}
			a._hidePolygon(j)
		}
		a.menuTouched = false;
		if (a.context) {
			if (a.contextHideAllMode) {
				a._hidePolygon(a.idPrefix + a.topId);
				a.zInd = a.zIndInit
			} else {
				a.zInd = a.zIndInit + a.zIndStep
			}
		}
	};
	this._doOnLoad = function() {
	};
	this.loadXML = function(h, j) {
		if (j) {
			this._doOnLoad = function() {
				j()
			}
		}
		this.callEvent("onXLS", []);
		this._xmlLoader.loadXML(h)
	};
	this.loadXMLString = function(j, h) {
		if (h) {
			this._doOnLoad = function() {
				h()
			}
		}
		this._xmlLoader.loadXMLString(j)
	};
	this._buildMenu = function(s, l) {
		var o = 0;
		for (var j = 0; j < s.childNodes.length; j++) {
			if (s.childNodes[j].tagName == this.itemTagName) {
				var h = s.childNodes[j];
				var p = {};
				p.id = this.idPrefix
						+ (h.getAttribute("id") || this._genStr(24));
				p.title = h.getAttribute("text") || "";
				p.imgen = h.getAttribute("img") || "";
				p.imgdis = h.getAttribute("imgdis") || "";
				p.tip = "";
				p.hotkey = "";
				if (h.getAttribute("cssNormal") != null) {
					p.cssNormal = h.getAttribute("cssNormal")
				}
				p.type = h.getAttribute("type") || "item";
				if (p.type == "checkbox") {
					p.checked = (h.getAttribute("checked") != null);
					p.imgen = "chbx_" + (p.checked ? "1" : "0");
					p.imgdis = "chbxdis_" + (p.checked ? "1" : "0")
				}
				if (p.type == "radio") {
					p.checked = (h.getAttribute("checked") != null);
					p.imgen = "rdbt_" + (p.checked ? "1" : "0");
					p.imgdis = "rdbtdis_" + (p.checked ? "1" : "0");
					p.group = h.getAttribute("group") || this._genStr(24);
					if (this.radio[p.group] == null) {
						this.radio[p.group] = new Array()
					}
					this.radio[p.group][this.radio[p.group].length] = p.id
				}
				p.state = (h.getAttribute("enabled") != null
						|| h.getAttribute("disabled") != null ? (h
						.getAttribute("enabled") == "false"
						|| h.getAttribute("disabled") == "true"
						? "disabled"
						: "enabled") : "enabled");
				p.parent = (l != null ? l : this.idPrefix + this.topId);
				p.complex = (this.dLoad ? (h.getAttribute("complex") != null
						? true
						: false) : (this._buildMenu(h, p.id) > 0));
				if (this.dLoad && p.complex) {
					p.loaded = "no"
				}
				this.itemPull[p.id] = p;
				for (var n = 0; n < h.childNodes.length; n++) {
					var k = h.childNodes[n].tagName;
					if (k != null) {
						k = k.toLowerCase()
					}
					if (k == this.userDataTagName) {
						var m = h.childNodes[n];
						if (m.getAttribute("name") != null) {
							this.userData[p.id + "_" + m.getAttribute("name")] = (m.firstChild.nodeValue != null
									? m.firstChild.nodeValue
									: "")
						}
					}
					if (k == this.itemTextTagName) {
						p.title = h.childNodes[n].firstChild.nodeValue
					}
					if (k == this.itemTipTagName) {
						p.tip = h.childNodes[n].firstChild.nodeValue
					}
					if (k == this.itemHotKeyTagName) {
						p.hotkey = h.childNodes[n].firstChild.nodeValue
					}
					if (k == this.itemHrefTagName && p.type == "item") {
						p.href_link = h.childNodes[n].firstChild.nodeValue;
						if (h.childNodes[n].getAttribute("target") != null) {
							p.href_target = h.childNodes[n]
									.getAttribute("target")
						}
					}
				}
				o++
			}
		}
		return o
	};
	this._xmlParser = function() {
		if (a.dLoad) {
			var m = this.getXMLTopNode("menu");
			parentId = (m.getAttribute("parentId") != null ? m
					.getAttribute("parentId") : null);
			if (parentId == null) {
				a._buildMenu(m, null);
				a._initTopLevelMenu()
			} else {
				a._buildMenu(m, a.idPrefix + parentId);
				a._addSubMenuPolygon(a.idPrefix + parentId, a.idPrefix
								+ parentId);
				if (a.menuSelected == a.idPrefix + parentId) {
					var l = a.idPrefix + parentId;
					var k = a.itemPull[a.idPrefix + parentId]["parent"] == a.idPrefix
							+ a.topId;
					var o = ((k && (!a.context))
							? a.dirTopLevel
							: a.dirSubLevel);
					var h = false;
					if (k && a.menuModeTopLevelTimeout && a.menuMode == "web"
							&& !a.context) {
						var n = a.idPull[a.idPrefix + parentId];
						if (n._mouseOver == true) {
							var j = a.menuModeTopLevelTimeoutTime
									- (new Date().getTime() - n._dynLoadTM);
							if (j > 1) {
								n._menuOpenTM = window.setTimeout(function() {
											a._showPolygon(l, o)
										}, j);
								h = true
							}
						}
					}
					if (!h) {
						a._showPolygon(l, o)
					}
				}
				a.itemPull[a.idPrefix + parentId]["loaded"] = "yes";
				if (a.loaderIcon == true) {
					a._updateLoaderIcon(a.idPrefix + parentId, false)
				}
			}
			this.destructor();
			a.callEvent("onXLE", [])
		} else {
			var m = this.getXMLTopNode("menu");
			a._buildMenu(m, null);
			a.init();
			if (!a.context) {
				a._redistribTopLevelPositions()
			}
			a.callEvent("onXLE", []);
			a._doOnLoad()
		}
	};
	this._xmlLoader = new dtmlXMLLoaderObject(this._xmlParser, window);
	this._showSubLevelItem = function(j, h) {
		if (document.getElementById("arrow_" + this.idPrefix + j) != null) {
			document.getElementById("arrow_" + this.idPrefix + j).style.display = (h
					? "none"
					: "")
		}
		if (document.getElementById("image_" + this.idPrefix + j) != null) {
			document.getElementById("image_" + this.idPrefix + j).style.display = (h
					? "none"
					: "")
		}
		if (document.getElementById(this.idPrefix + j) != null) {
			document.getElementById(this.idPrefix + j).style.display = (h
					? ""
					: "none")
		}
	};
	this._hideSubLevelItem = function(h) {
		this._showSubLevelItem(h, true)
	};
	this.idPrefix = this._genStr(12);
	dhtmlxEvent(document.body, "click", function(h) {
				if (a._unloaded) {
					return
				}
				if (a.context) {
					h = h || event;
					if (!_isOpera) {
						if (a.contextAutoHide) {
							a._hideContextMenu()
						}
					} else {
						if (a.contextAutoHide && !h.ctrlKey) {
							a._hideContextMenu()
						}
					}
				} else {
					a._clearAndHide()
				}
			});
	dhtmlxEvent(document.body, "contextmenu", function(k) {
				if (a._unloaded) {
					return
				}
				k = k || event;
				var j = (k.srcElement || k.target).className;
				if (j.search("dhtmlxMenu") != -1
						&& j.search("SubLevelArea") != -1) {
					return
				}
				var h = true;
				var l = k.target || k.srcElement;
				if (l.id != null) {
					if (a.isContextZone(l.id)) {
						h = false
					}
				}
				if (h) {
					a.hideContextMenu()
				}
			});
	this._UID = this._genStr(32);
	dhtmlxMenuObjectLiveInstances[this._UID] = this;
	dhtmlxEventable(this);
	return this
}
dhtmlXMenuObject.prototype.init = function() {
	if (this._isInited == true) {
		return
	}
	if (this.dLoad) {
		this.callEvent("onXLS", []);
		this._xmlLoader.loadXML(this.dLoadUrl + this.dLoadSign
				+ "action=loadMenu&etc=" + new Date().getTime())
	} else {
		this._initTopLevelMenu();
		this._isInited = true
	}
};
dhtmlXMenuObject.prototype._countVisiblePolygonItems = function(g) {
	var e = 0;
	for (var c in this.itemPull) {
		var d = this.itemPull[c]["parent"];
		var f = this.itemPull[c]["type"];
		if (this.idPull[c] != null) {
			if (d == g && (f == "item" || f == "radio" || f == "checkbox")
					&& this.idPull[c].style.display != "none") {
				e++
			}
		}
	}
	return e
};
dhtmlXMenuObject.prototype._redefineComplexState = function(c) {
	if (this.idPrefix + this.topId == c) {
		return
	}
	if ((this.idPull["polygon_" + c] != null) && (this.idPull[c] != null)) {
		var a = this._countVisiblePolygonItems(c);
		if ((a > 0) && (!this.itemPull[c]["complex"])) {
			this._updateItemComplexState(c, true, false)
		}
		if ((a == 0) && (this.itemPull[c]["complex"])) {
			this._updateItemComplexState(c, false, false)
		}
	}
};
dhtmlXMenuObject.prototype._updateItemComplexState = function(j, g, h) {
	if ((!this.context)
			&& (this._getItemLevelType(j.replace(this.idPrefix, "")) == "TopLevel")) {
		this.itemPull[j]["complex"] = g;
		return
	}
	if ((this.idPull[j] == null) || (this.itemPull[j] == null)) {
		return
	}
	this.itemPull[j]["complex"] = g;
	var a = null;
	if (j == this.idPrefix + this.topId) {
		return
	}
	for (var f = 0; f < this.idPull[j].childNodes.length; f++) {
		var e = this.idPull[j].childNodes[f];
		if (e.id != null) {
			if (e.id == "arrow_" + j) {
				a = e
			}
		}
	}
	if (this.itemPull[j]["complex"]) {
		if (a == null) {
			a = document.createElement("DIV");
			var d = "arrow" + (this._rtl ? "r" : "l") + "_"
					+ (this.itemPull[j]["state"] == "enabled" ? "en" : "dis");
			a.className = "dhtmlxMenu_SubLevelArea_Item_Arrow " + d;
			a.id = "arrow_" + j;
			this.idPull[j].appendChild(a)
		}
		if (this.dLoad && (this.itemPull[j]["loaded"] == "get")
				&& this.loaderIcon) {
			if (a.className != "dhtmlxMenu_SubLevelArea_Item_Arrow_Loading") {
				a.className = "dhtmlxMenu_SubLevelArea_Item_Arrow_Loading"
			}
		} else {
			var c = "arrow"
					+ (this._rtl ? "r" : "l")
					+ "_"
					+ (this.itemPull[j]["state"] == "enabled" ? (h
							? "over"
							: "en") : "dis");
			a.className = "dhtmlxMenu_SubLevelArea_Item_Arrow " + c
		}
		return
	}
	if ((!this.itemPull[j]["complex"]) && (a != null)) {
		this.idPull[j].removeChild(a);
		if (this.itemPull[j]["hotkey_backup"] != null && this.setHotKey) {
			this.setHotKey(j.replace(this.idPrefix, ""),
					this.itemPull[j]["hotkey_backup"])
		}
	}
};
dhtmlXMenuObject.prototype._getItemLevelType = function(a) {
	return (this.itemPull[this.idPrefix + a]["parent"] == this.idPrefix
			+ this.topId ? "TopLevel" : "SubLevelArea")
};
dhtmlXMenuObject.prototype._redistribTopLevelPositions = function() {
	if (this.context) {
		return
	}
	var a = this._topLevelOffsetLeft;
	if (this._align == "left") {
		for (var c = 0; c < this.base.childNodes.length; c++) {
			if (this.base.childNodes[c].tagName == "DIV") {
				if (String(this.base.childNodes[c].className)
						.search("TopLevel_Text") == -1) {
					if (!this._rtl) {
						this.base.childNodes[c].style.right = "";
						this.base.childNodes[c].style.left = a + "px"
					} else {
						this.base.childNodes[c].style.left = "";
						this.base.childNodes[c].style.right = a + "px"
					}
					a += this.base.childNodes[c].offsetWidth;
					if (this.skin == "dhx_skyblue"
							&& String(this.base.childNodes[c].className)
									.search("Separator") == -1) {
						a += 2
					}
				}
			}
		}
	} else {
		for (var c = this.base.childNodes.length - 1; c >= 0; c--) {
			if (String(this.base.childNodes[c].className)
					.search("TopLevel_Text") == -1) {
				this.base.childNodes[c].style.left = "";
				this.base.childNodes[c].style.right = a + "px";
				a += this.base.childNodes[c].offsetWidth
			}
		}
	}
};
dhtmlXMenuObject.prototype._redistribTopLevelSelection = function(e, c) {
	var a = this._getSubItemToDeselectByPolygon("parent");
	this._removeSubItemFromSelected(-1, -1);
	for (var d = 0; d < a.length; d++) {
		if (a[d] != e) {
			this._hidePolygon(a[d])
		}
		if ((this.idPull[a[d]] != null) && (a[d] != e)) {
			this.idPull[a[d]].className = this.idPull[a[d]].className.replace(
					/Selected/g, "Normal")
		}
	}
	if (this.itemPull[this.idPrefix + e]["state"] == "enabled") {
		this.idPull[this.idPrefix + e].className = "dhtmlxMenu_" + this.skin
				+ "_TopLevel_Item_Selected";
		this._addSubItemToSelected(this.idPrefix + e, "parent");
		this.menuSelected = (this.menuMode == "win" ? (this.menuSelected != -1
				? e
				: this.menuSelected) : e);
		if ((this.itemPull[this.idPrefix + e]["complex"])
				&& (this.menuSelected != -1)) {
			this._showPolygon(this.idPrefix + e, this.dirTopLevel)
		}
	}
};
dhtmlXMenuObject.prototype._initTopLevelMenu = function() {
	this.dirTopLevel = "bottom";
	this.dirSubLevel = (this._rtl ? "left" : "right");
	if (this.context) {
		this.idPull[this.idPrefix + this.topId] = new Array(0, 0);
		this._addSubMenuPolygon(this.idPrefix + this.topId, this.idPrefix
						+ this.topId);
		this._attachEvents()
	} else {
		var a = this._getMenuNodes(this.idPrefix + this.topId);
		for (var c = 0; c < a.length; c++) {
			if (this.itemPull[a[c]]["type"] == "item") {
				this._renderToplevelItem(a[c], null)
			}
			if (this.itemPull[a[c]]["type"] == "separator") {
				this._renderSeparator(a[c], null)
			}
		}
	}
};
dhtmlXMenuObject.prototype._attachEvents = function() {
	var a = this;
	dhtmlxEvent(document.body, "click", function(c) {
				if (a._unloaded) {
					return
				}
				c = c || event;
				if (_isOpera && c.ctrlKey == true) {
					return
				}
				if (a._isContextMenuVisible() && a.contextAutoHide) {
					a._hideContextMenu()
				}
			})
};
dhtmlXMenuObject.prototype._renderToplevelItem = function(h, g) {
	var c = this;
	var a = document.createElement("DIV");
	a.id = h;
	if (this.itemPull[h]["state"] == "enabled"
			&& this.itemPull[h]["cssNormal"] != null) {
		a.className = this.itemPull[h]["cssNormal"]
	} else {
		a.className = "dhtmlxMenu_"
				+ this.skin
				+ "_TopLevel_Item_"
				+ (this.itemPull[h]["state"] == "enabled"
						? "Normal"
						: "Disabled")
	}
	a.innerHTML = this.itemPull[h]["title"];
	if (this.itemPull[h]["tip"].length > 0) {
		a.title = this.itemPull[h]["tip"]
	}
	if ((this.itemPull[h]["imgen"] != "") || (this.itemPull[h]["imgdis"] != "")) {
		var e = this.itemPull[h][(this.itemPull[h]["state"] == "enabled")
				? "imgen"
				: "imgdis"];
		if (e) {
			var e = "<img id='image_" + h + "' src='" + this.imagePath + e
					+ "' class='dhtmlxMenu_TopLevel_Item_Icon_"
					+ (this._rtl ? "right" : "left") + "' border='0'>";
			a.innerHTML = e + a.innerHTML;
			a.style.paddingLeft = this.topLevelItemPaddingIconExists + "px"
		}
	}
	a.onselectstart = function(j) {
		j = j || event;
		j.returnValue = false;
		return false
	};
	a.oncontextmenu = function(j) {
		j = j || event;
		j.returnValue = false;
		return false
	};
	if (!this._rtl) {
		var d = 0;
		for (var f = 0; f < this.base.childNodes.length; f++) {
			if (String(this.base.childNodes[f].className)
					.search("TopLevel_Text") == -1) {
				if (!isNaN(this.base.childNodes[f].offsetWidth)) {
					d = d + this.base.childNodes[f].offsetWidth
				}
			}
		}
		a.style.left = d + "px"
	}
	if (g != null) {
		g++;
		if (g < 0) {
			g = 0
		}
		if (g > this.base.childNodes.length - 1) {
			g = null
		}
	}
	if (g != null) {
		this.base.insertBefore(a, this.base.childNodes[g]);
		this._redistribTopLevelPositions()
	} else {
		this.base.appendChild(a);
		if (this._rtl) {
			this._redistribTopLevelPositions()
		}
	}
	this.idPull[a.id] = a;
	if (this.itemPull[h]["complex"] && (!this.dLoad)) {
		this._addSubMenuPolygon(this.itemPull[h]["id"], this.itemPull[h]["id"])
	}
	a.onmouseover = function() {
		if (c.menuMode == "web") {
			window.clearTimeout(c.menuTimeoutHandler)
		}
		var j = c._getSubItemToDeselectByPolygon("parent");
		c._removeSubItemFromSelected(-1, -1);
		for (var m = 0; m < j.length; m++) {
			if (j[m] != this.id) {
				c._hidePolygon(j[m])
			}
			if ((c.idPull[j[m]] != null) && (j[m] != this.id)) {
				if (c.itemPull[j[m]]["cssNormal"] != null) {
					c.idPull[j[m]].className = c.itemPull[j[m]]["cssNormal"]
				} else {
					c.idPull[j[m]].className = c.idPull[j[m]].className
							.replace(/Selected/g, "Normal")
				}
			}
		}
		if (c.itemPull[this.id]["state"] == "enabled") {
			this.className = "dhtmlxMenu_" + c.skin + "_TopLevel_Item_Selected";
			c._addSubItemToSelected(this.id, "parent");
			c.menuSelected = (c.menuMode == "win" ? (c.menuSelected != -1
					? this.id
					: c.menuSelected) : this.id);
			if (c.dLoad && (c.itemPull[this.id]["loaded"] == "no")) {
				if (c.menuModeTopLevelTimeout && c.menuMode == "web"
						&& !c.context) {
					this._mouseOver = true;
					this._dynLoadTM = new Date().getTime()
				}
				var k = new dtmlXMLLoaderObject(c._xmlParser, window);
				c.itemPull[this.id]["loaded"] = "get";
				c.callEvent("onXLS", []);
				k.loadXML(c.dLoadUrl + c.dLoadSign
						+ "action=loadMenu&parentId="
						+ this.id.replace(c.idPrefix, "") + "&etc="
						+ new Date().getTime())
			}
			if ((!c.dLoad)
					|| (c.dLoad && (c.itemPull[this.id]["loaded"] == "yes"))) {
				if ((c.itemPull[this.id]["complex"]) && (c.menuSelected != -1)) {
					if (c.menuModeTopLevelTimeout && c.menuMode == "web"
							&& !c.context) {
						this._mouseOver = true;
						var l = this.id;
						this._menuOpenTM = window.setTimeout(function() {
									c._showPolygon(l, c.dirTopLevel)
								}, c.menuModeTopLevelTimeoutTime)
					} else {
						c._showPolygon(this.id, c.dirTopLevel)
					}
				}
			}
		}
		c._doOnTouchMenu(this.id.replace(c.idPrefix, ""))
	};
	a.onmouseout = function() {
		if (!((c.itemPull[this.id]["complex"]) && (c.menuSelected != -1))
				&& (c.itemPull[this.id]["state"] == "enabled")) {
			if (c.itemPull[this.id]["cssNormal"] != null) {
				a.className = c.itemPull[this.id]["cssNormal"]
			} else {
				a.className = "dhtmlxMenu_" + c.skin + "_TopLevel_Item_Normal"
			}
		}
		if (c.menuMode == "web") {
			window.clearTimeout(c.menuTimeoutHandler);
			c.menuTimeoutHandler = window.setTimeout(function() {
						c._clearAndHide()
					}, c.menuTimeoutMsec, "JavaScript")
		}
		if (c.menuModeTopLevelTimeout && c.menuMode == "web" && !c.context) {
			this._mouseOver = false;
			window.clearTimeout(this._menuOpenTM)
		}
	};
	a.onclick = function(m) {
		if (c.menuMode == "web") {
			window.clearTimeout(c.menuTimeoutHandler)
		}
		if (c.menuMode != "web" && c.itemPull[this.id]["state"] == "disabled") {
			return
		}
		m = m || event;
		m.cancelBubble = true;
		m.returnValue = false;
		if (c.menuMode == "win") {
			if (c.itemPull[this.id]["complex"]) {
				if (c.menuSelected == this.id) {
					c.menuSelected = -1;
					var l = false
				} else {
					c.menuSelected = this.id;
					var l = true
				}
				if (l) {
					c._showPolygon(this.id, c.dirTopLevel)
				} else {
					c._hidePolygon(this.id)
				}
			}
		}
		var j = (c.itemPull[this.id]["complex"] ? "c" : "-");
		var n = (c.itemPull[this.id]["state"] != "enabled" ? "d" : "-");
		var k = {
			ctrl : m.ctrlKey,
			alt : m.altKey,
			shift : m.shiftKey
		};
		c._doOnClick(this.id.replace(c.idPrefix, ""), j + n + "t", k);
		return false
	}
};
dhtmlXMenuObject.prototype.setImagePath = function() {
};
dhtmlXMenuObject.prototype.setIconsPath = function(a) {
	this.imagePath = a
};
dhtmlXMenuObject.prototype.setIconPath = dhtmlXMenuObject.prototype.setIconsPath;
dhtmlXMenuObject.prototype._updateItemImage = function(j, a) {
	var c = null;
	for (var g = 0; g < this.idPull[this.idPrefix + j].childNodes.length; g++) {
		var f = this.idPull[this.idPrefix + j].childNodes[g];
		if (f.id != null) {
			if (f.id == "image_" + this.idPrefix + j) {
				c = f
			}
		}
	}
	if (this.itemPull[this.idPrefix + j]["type"] == "radio") {
		var e = this.itemPull[this.idPrefix + j][(this.itemPull[this.idPrefix
				+ j]["state"] == "enabled" ? "imgen" : "imgdis")]
	} else {
		var e = this.itemPull[this.idPrefix + j][(this.itemPull[this.idPrefix
				+ j]["state"] == "enabled" ? "imgen" : "imgdis")]
	}
	if (e.length > 0) {
		if (c != null) {
			var h = this.itemPull[this.idPrefix + j]["type"];
			if (h == "checkbox" || h == "radio") {
				c.className = "dhtmlxMenu_SubLevelArea_Item_Icon " + e
			} else {
				if (String(c.className).search("dhtmlxMenu_TopLevel_Item_Icon") === 0) {
					c.src = this.imagePath + e
				} else {
					c.style.backgroundImage = "url('" + this.imagePath + e
							+ "')"
				}
			}
		} else {
			if (a == "TopLevel") {
				var d = document.createElement("IMG");
				d.className = "dhtmlxMenu_" + a + "_Item_Icon_"
						+ (this._rtl ? "right" : "left");
				d.src = this.imagePath + e;
				d.border = "0";
				d.id = "image_" + this.idPrefix + j;
				this.idPull[this.idPrefix + j].appendChild(d);
				this.idPull[this.idPrefix + j].style.paddingLeft = (this._rtl
						? this.topLevelItemPaddingIconNotExists
						: this.topLevelItemPaddingIconExists)
						+ "px";
				this.idPull[this.idPrefix + j].style.paddingRight = (this._rtl
						? this.topLevelItemPaddingIconExists
						: this.topLevelItemPaddingIconNotExists)
						+ "px"
			} else {
				var d = document.createElement("DIV");
				d.className = "dhtmlxMenu_" + a + "_Item_Icon";
				d.style.backgroundImage = "url('" + this.imagePath + e + "')";
				d.id = "image_" + this.idPrefix + j;
				this.idPull[this.idPrefix + j].appendChild(d)
			}
		}
	} else {
		if (c != null) {
			this.idPull[this.idPrefix + j].removeChild(c);
			if (a == "TopLevel") {
				this.idPull[this.idPrefix + j].style.paddingLeft = this.topLevelItemPaddingIconNotExists
						+ "px";
				this.idPull[this.idPrefix + j].style.paddingRight = this.topLevelItemPaddingIconNotExists
						+ "px"
			}
		}
	}
};
dhtmlXMenuObject.prototype.removeItem = function(g) {
	g = this.idPrefix + g;
	if (this.itemPull[g] == null) {
		return
	}
	var f = this.itemPull[g]["parent"];
	if (this.itemPull[g]["type"] == "separator") {
		this.idPull["separator_" + g].parentNode
				.removeChild(this.idPull["separator_" + g]);
		delete this.idPull["separator_" + g];
		delete this.itemPull[g]
	} else {
		if (this.itemPull[g]["complex"]) {
			var c = this._getAllParents(g);
			c[c.length] = g;
			var a = new Array();
			for (var e = 0; e < c.length; e++) {
				if (this.itemPull[c[e]]["type"] == "separator") {
					this.removeItem(c[e].replace(this.idPrefix, ""))
				} else {
					if (this.itemPull[c[e]]["complex"]) {
						a[a.length] = c[e]
					}
					this.idPull[c[e]].parentNode.removeChild(this.idPull[c[e]]);
					delete this.idPull[c[e]];
					delete this.itemPull[c[e]]
				}
			}
			for (var e = 0; e < a.length; e++) {
				this.idPull["polygon_" + a[e]].parentNode
						.removeChild(this.idPull["polygon_" + a[e]]);
				if (this._isIE6) {
					var d = "polygon_" + a[e] + "_ie6cover";
					if (this.idPull[d] != null) {
						document.body.removeChild(this.idPull[d]);
						delete this.idPull[d]
					}
				}
				delete this.idPull["polygon_" + a[e]];
				delete this.itemPull[a[e]]
			}
			if (!this.context) {
				this._redistribTopLevelPositions()
			}
		} else {
			this.idPull[g].parentNode.removeChild(this.idPull[g]);
			delete this.idPull[g];
			delete this.itemPull[g]
		}
	}
	if (this.idPull["polygon_" + f] != null) {
		if (this.idPull["polygon_" + f].childNodes.length == 0) {
			document.body.removeChild(this.idPull["polygon_" + f]);
			if (this._isIE6) {
				var d = "polygon_" + f + "_ie6cover";
				if (this.idPull[d] != null) {
					document.body.removeChild(this.idPull[d]);
					delete this.idPull[d]
				}
			}
			delete this.idPull["polygon_" + f];
			this._updateItemComplexState(f, false, false)
		}
	}
};
dhtmlXMenuObject.prototype._getAllParents = function(g) {
	var d = new Array();
	for (var c in this.itemPull) {
		if (this.itemPull[c]["parent"] == g) {
			d[d.length] = this.itemPull[c]["id"];
			if (this.itemPull[c]["complex"]) {
				var e = this._getAllParents(this.itemPull[c]["id"]);
				for (var f = 0; f < e.length; f++) {
					d[d.length] = e[f]
				}
			}
		}
	}
	return d
};
dhtmlXMenuObject.prototype.renderAsContextMenu = function() {
	this.context = true;
	if (this.base._autoSkinUpdate == true) {
		this.base.className = this.base.className.replace("dhtmlxMenu_"
						+ this.skin + "_Middle", "");
		this.base._autoSkinUpdate = false
	}
	if (this.addBaseIdAsContextZone != null) {
		this.addContextZone(this.addBaseIdAsContextZone)
	}
};
dhtmlXMenuObject.prototype.addContextZone = function(d) {
	var f = document.getElementById(d);
	var g = false;
	for (var e in this.contextZones) {
		g = g || (e == d) || (this.contextZones[e] == f)
	}
	if (g == true) {
		return false
	}
	this.contextZones[d] = f;
	var c = this;
	if (_isOpera) {
		f.attachEvent("mouseup", function(h) {
					for (var a in dhtmlxMenuObjectLiveInstances) {
						if (a != c._UID) {
							if (dhtmlxMenuObjectLiveInstances[a].context) {
								dhtmlxMenuObjectLiveInstances[a]
										._hideContextMenu()
							}
						}
					}
					h.cancelBubble = true;
					h.returnValue = false;
					if (h.button == 0 && h.ctrlKey == true) {
						c._doOnContextBeforeCall(h, this)
					}
					return false
				})
	} else {
		if (f.oncontextmenu != null) {
			f._oldContextMenuHandler = f.oncontextmenu
		}
		f.oncontextmenu = function(h) {
			for (var a in dhtmlxMenuObjectLiveInstances) {
				if (a != c._UID) {
					if (dhtmlxMenuObjectLiveInstances[a].context) {
						dhtmlxMenuObjectLiveInstances[a]._hideContextMenu()
					}
				}
			}
			h = h || event;
			h.cancelBubble = true;
			h.returnValue = false;
			c._doOnContextBeforeCall(h, this);
			return false
		}
	}
};
dhtmlXMenuObject.prototype.removeContextZone = function(a) {
	if (!this.isContextZone(a)) {
		return false
	}
	var c = this.contextZones[a];
	if (_isOpera) {
		c.onmouseup = null
	} else {
		c.oncontextmenu = (c._oldContextMenuHandler != null
				? c._oldContextMenuHandler
				: null)
	}
	delete this.contextZones[a];
	return true
};
dhtmlXMenuObject.prototype.isContextZone = function(a) {
	var c = false;
	if (this.contextZones[a] != null) {
		if (this.contextZones[a] == document.getElementById(a)) {
			c = true
		}
	}
	return c
};
dhtmlXMenuObject.prototype._isContextMenuVisible = function() {
	if (this.idPull["polygon_" + this.idPrefix + this.topId] == null) {
		return false
	}
	return (this.idPull["polygon_" + this.idPrefix + this.topId].style.display == "")
};
dhtmlXMenuObject.prototype._showContextMenu = function(c, d, a) {
	this._clearAndHide();
	if (this.idPull["polygon_" + this.idPrefix + this.topId] == null) {
		return false
	}
	window.clearTimeout(this.menuTimeoutHandler);
	this.idPull[this.idPrefix + this.topId] = new Array(c, d);
	this._showPolygon(this.idPrefix + this.topId, "bottom");
	this.callEvent("onContextMenu", [a])
};
dhtmlXMenuObject.prototype._hideContextMenu = function() {
	if (this.idPull["polygon_" + this.idPrefix + this.topId] == null) {
		return false
	}
	this._clearAndHide();
	this._hidePolygon(this.idPrefix + this.topId);
	this.zInd = this.zIndInit
};
dhtmlXMenuObject.prototype._doOnContextBeforeCall = function(g, j) {
	this.contextMenuZoneId = j.id;
	this._clearAndHide();
	this._hideContextMenu();
	var f = (g.srcElement || g.target);
	var c = (_isIE || _isOpera || _KHTMLrv ? g.offsetX : g.layerX);
	var a = (_isIE || _isOpera || _KHTMLrv ? g.offsetY : g.layerY);
	var h = getAbsoluteLeft(f) + c;
	var d = getAbsoluteTop(f) + a;
	if (this.checkEvent("onBeforeContextMenu")) {
		if (this.callEvent("onBeforeContextMenu", [j.id])) {
			if (this.contextAutoShow) {
				this._showContextMenu(h, d);
				this.callEvent("onAfterContextMenu", [j.id])
			}
		}
	} else {
		if (this.contextAutoShow) {
			this._showContextMenu(h, d);
			this.callEvent("onAfterContextMenu", [j.id])
		}
	}
};
dhtmlXMenuObject.prototype.showContextMenu = function(a, c) {
	this._showContextMenu(a, c, false)
};
dhtmlXMenuObject.prototype.hideContextMenu = function() {
	this._hideContextMenu()
};
dhtmlXMenuObject.prototype._autoDetectVisibleArea = function() {
	if (this._isVisibleArea) {
		return
	}
	this.menuX1 = document.body.scrollLeft;
	this.menuX2 = this.menuX1
			+ (window.innerWidth || document.body.clientWidth);
	this.menuY1 = Math.max((_isIE ? document.documentElement : document
					.getElementsByTagName("html")[0]).scrollTop,
			document.body.scrollTop);
	this.menuY2 = this.menuY1
			+ (_isIE ? Math.max(document.documentElement.clientHeight || 0,
					document.documentElement.offsetHeight || 0,
					document.body.clientHeight || 0) : window.innerHeight)
};
dhtmlXMenuObject.prototype.getItemPosition = function(f) {
	f = this.idPrefix + f;
	var e = -1;
	if (this.itemPull[f] == null) {
		return e
	}
	var a = this.itemPull[f]["parent"];
	var d = (this.idPull["polygon_" + a] != null
			? this.idPull["polygon_" + a]
			: this.base);
	for (var c = 0; c < d.childNodes.length; c++) {
		if (d.childNodes[c] == this.idPull["separator_" + f]
				|| d.childNodes[c] == this.idPull[f]) {
			e = c
		}
	}
	return e
};
dhtmlXMenuObject.prototype.setItemPosition = function(h, g) {
	h = this.idPrefix + h;
	if (this.idPull[h] == null) {
		return
	}
	var c = (this.itemPull[h]["parent"] == this.idPrefix + this.topId);
	var a = this.idPull[h];
	var e = this.getItemPosition(h.replace(this.idPrefix, ""));
	var d = this.itemPull[h]["parent"];
	var f = (this.idPull["polygon_" + d] != null
			? this.idPull["polygon_" + d]
			: this.base);
	f.removeChild(f.childNodes[e]);
	if (g < 0) {
		g = 0
	}
	if (c && g < 1) {
		g = 1
	}
	if (g < f.childNodes.length) {
		f.insertBefore(a, f.childNodes[g])
	} else {
		f.appendChild(a)
	}
	if (c) {
		this._redistribTopLevelPositions()
	}
};
dhtmlXMenuObject.prototype.getParentId = function(a) {
	a = this.idPrefix + a;
	if (this.itemPull[a] == null) {
		return null
	}
	return ((this.itemPull[a]["parent"] != null
			? this.itemPull[a]["parent"]
			: this.topId).replace(this.idPrefix, ""))
};
dhtmlXMenuObject.prototype.addNewSibling = function(e, f, a, c, d, j) {
	var h = this.idPrefix + (f != null ? f : this._genStr(24));
	var g = this.idPrefix + (e != null ? this.getParentId(e) : this.topId);
	this._addItemIntoGlobalStrorage(h, g, a, "item", c, d, j);
	if ((g == this.idPrefix + this.topId) && (!this.context)) {
		this._renderToplevelItem(h, this.getItemPosition(e));
		this._redistribTopLevelPositions()
	} else {
		this._renderSublevelItem(h, this.getItemPosition(e))
	}
};
dhtmlXMenuObject.prototype.addNewChild = function(h, g, e, a, c, d, f) {
	if (h == null) {
		if (this.context) {
			h = this.topId
		} else {
			this.addNewSibling(h, e, a, c, d, f);
			if (g != null) {
				this.setItemPosition(e, g)
			}
			return
		}
	}
	e = this.idPrefix + (e != null ? e : this._genStr(24));
	if (this.setHotKey) {
		this.setHotKey(h, "")
	}
	h = this.idPrefix + h;
	this._addItemIntoGlobalStrorage(e, h, a, "item", c, d, f);
	if (this.idPull["polygon_" + h] == null) {
		this._renderSublevelPolygon(h, h)
	}
	this._renderSublevelItem(e, g - 1);
	this._redefineComplexState(h)
};
dhtmlXMenuObject.prototype._addItemIntoGlobalStrorage = function(j, a, d, h, e,
		c, g) {
	var f = {
		id : j,
		title : d,
		imgen : (c != null ? c : ""),
		imgdis : (g != null ? g : ""),
		type : h,
		state : (e == true ? "disabled" : "enabled"),
		parent : a,
		complex : false,
		hotkey : "",
		tip : ""
	};
	this.itemPull[f.id] = f
};
dhtmlXMenuObject.prototype._addSubMenuPolygon = function(g, f) {
	var c = this._renderSublevelPolygon(g, f);
	var a = this._getMenuNodes(f);
	for (d = 0; d < a.length; d++) {
		if (this.itemPull[a[d]]["type"] == "separator") {
			this._renderSeparator(a[d], null)
		} else {
			this._renderSublevelItem(a[d], null)
		}
	}
	if (g == f) {
		var e = "topLevel"
	} else {
		var e = "subLevel"
	}
	for (var d = 0; d < a.length; d++) {
		if (this.itemPull[a[d]]["complex"]) {
			this._addSubMenuPolygon(g, this.itemPull[a[d]]["id"])
		}
	}
};
dhtmlXMenuObject.prototype._renderSublevelPolygon = function(d, c) {
	var a = document.createElement("DIV");
	a.className = "dhtmlxMenu_" + this.skin + "_SubLevelArea_Polygon_"
			+ (this._rtl ? "right" : "left");
	a.oncontextmenu = function(f) {
		f = f || event;
		f.returnValue = false;
		f.canceBubble = true;
		return false
	};
	a.id = "polygon_" + c;
	a.onclick = function(f) {
		f = f || event;
		f.cancelBubble = true
	};
	a.style.display = "none";
	document.body.insertBefore(a, document.body.firstChild);
	this.idPull[a.id] = a;
	if (this.sxDacProc != null) {
		this.idPull["sxDac_" + c] = new this.sxDacProc(a, a.className);
		if (_isIE) {
			this.idPull["sxDac_" + c]._setSpeed(this.dacSpeedIE);
			this.idPull["sxDac_" + c]._setCustomCycle(this.dacCyclesIE)
		} else {
			this.idPull["sxDac_" + c]._setSpeed(this.dacSpeed);
			this.idPull["sxDac_" + c]._setCustomCycle(this.dacCycles)
		}
	}
	return a
};
dhtmlXMenuObject.prototype._renderSublevelItem = function(c, g) {
	var a = this;
	var f = document.createElement("DIV");
	if (this.itemPull[c]["state"] == "enabled") {
		f.className = "dhtmlxMenu_" + this.skin + "_SubLevelArea_Item_Normal";
		var e = "arrow" + (this._rtl ? "r" : "l") + "_en";
		j_icon = this.itemPull[c]["imgen"]
	} else {
		f.className = "dhtmlxMenu_" + this.skin + "_SubLevelArea_Item_Disabled";
		var e = "arrow" + (this._rtl ? "r" : "l") + "_dis";
		j_icon = this.itemPull[c]["imgdis"]
	}
	if (this.itemPull[c]["complex"]) {
		var l = "<div class='dhtmlxMenu_SubLevelArea_Item_Arrow " + e
				+ "' id='arrow_" + this.itemPull[c]["id"] + "'>"
	} else {
		var l = ""
	}
	if (j_icon.length > 0) {
		var j = this.itemPull[c]["type"];
		if (j == "checkbox" || j == "radio") {
			j_icon = "<div id='image_" + this.itemPull[c]["id"]
					+ "' class='dhtmlxMenu_SubLevelArea_Item_Icon " + j_icon
					+ "'></div>"
		}
		if (!(j == "checkbox" || j == "radio")) {
			j_icon = "<div id='image_"
					+ this.itemPull[c]["id"]
					+ "' class='dhtmlxMenu_SubLevelArea_Item_Icon' style='background-image:url(\""
					+ this.imagePath + j_icon + "\");'></div>"
		}
	}
	f.innerHTML = j_icon + this.itemPull[c]["title"] + l;
	if (this.itemPull[c]["hotkey"].length > 0 && !this.itemPull[c]["complex"]) {
		var d = "";
		f.innerHTML += "<div class='dhtmlxMenu_SubLevelArea_Item_HotKey'>"
				+ this.itemPull[c]["hotkey"] + "</div>"
	}
	f.id = this.itemPull[c]["id"];
	f.parent = this.itemPull[c]["parent"];
	if (this.itemPull[c]["tip"].length > 0) {
		f.title = this.itemPull[c]["tip"]
	}
	f.onselectstart = function(k) {
		k = k || event;
		k.returnValue = false
	};
	f.onmouseover = function() {
		if (a.menuMode == "web") {
			window.clearTimeout(a.menuTimeoutHandler)
		}
		a._redistribSubLevelSelection(this.id, this.parent)
	};
	if (a.menuMode == "web") {
		f.onmouseout = function() {
			window.clearTimeout(a.menuTimeoutHandler);
			a.menuTimeoutHandler = window.setTimeout(function() {
						a._clearAndHide()
					}, a.menuTimeoutMsec, "JavaScript")
		}
	}
	f.onclick = function(m) {
		if (!a.checkEvent("onClick") && a.itemPull[this.id]["complex"]) {
			return
		}
		m = m || event;
		m.cancelBubble = true;
		m.returnValue = false;
		tc = (a.itemPull[this.id]["complex"] ? "c" : "-");
		td = (a.itemPull[this.id]["state"] == "enabled" ? "-" : "d");
		var k = {
			ctrl : m.ctrlKey,
			alt : m.altKey,
			shift : m.shiftKey
		};
		switch (a.itemPull[this.id]["type"]) {
			case "checkbox" :
				a._checkboxOnClickHandler(this.id.replace(a.idPrefix, ""), tc
								+ td + "n", k);
				break;
			case "radio" :
				a._radioOnClickHandler(this.id.replace(a.idPrefix, ""), tc + td
								+ "n", k);
				break;
			case "item" :
				a._doOnClick(this.id.replace(a.idPrefix, ""), tc + td + "n", k);
				break
		}
		return false
	};
	var h = this.idPull["polygon_" + this.itemPull[c]["parent"]];
	if (g != null) {
		g++;
		if (g < 0) {
			g = 0
		}
		if (g > h.childNodes.length - 1) {
			g = null
		}
	}
	if (g != null) {
		h.insertBefore(f, h.childNodes[g])
	} else {
		h.appendChild(f)
	}
	this.idPull[f.id] = f
};
dhtmlXMenuObject.prototype._renderSeparator = function(h, g) {
	var f = (this.context
			? "SubLevelArea"
			: (this.itemPull[h]["parent"] == this.idPrefix + this.topId
					? "TopLevel"
					: "SubLevelArea"));
	if (f == "topLevel" && this.context) {
		return
	}
	var a = this;
	var c = document.createElement("DIV");
	c.id = "separator_" + h;
	c.className = "dhtmlxMenu_" + this.skin + "_" + f + "_Separator";
	c.onselectstart = function(j) {
		j = j || event;
		j.returnValue = false
	};
	c.onclick = function(k) {
		k = k || event;
		k.cancelBubble = true;
		var j = {
			ctrl : k.ctrlKey,
			alt : k.altKey,
			shift : k.shiftKey
		};
		a._doOnClick(this.id.replace("separator_" + a.idPrefix, ""), "--s", j)
	};
	if (f == "TopLevel") {
		if (g != null) {
			g++;
			if (g < 0) {
				g = 0
			}
			if (this.base.childNodes[g] != null) {
				this.base.insertBefore(c, this.base.childNodes[g])
			} else {
				this.base.appendChild(c)
			}
			this._redistribTopLevelPositions()
		} else {
			var e = this.base.childNodes[this.base.childNodes.length - 1];
			if (String(e).search("TopLevel_Text") == -1) {
				this.base.appendChild(c)
			} else {
				this.base.insertBefore(c, e)
			}
		}
	} else {
		var d = this.idPull["polygon_" + this.itemPull[h]["parent"]];
		if (g != null) {
			g++;
			if (g < 0) {
				g = 0
			}
			if (g > d.childNodes.length - 1) {
				g = null
			}
		}
		if (g != null) {
			d.insertBefore(c, d.childNodes[g])
		} else {
			d.appendChild(c)
		}
	}
	this.idPull[c.id] = c
};
dhtmlXMenuObject.prototype.addNewSeparator = function(a, c) {
	c = this.idPrefix + (c != null ? c : this._genStr(24));
	var d = this.idPrefix + this.getParentId(a);
	this._addItemIntoGlobalStrorage(c, d, "", "separator", false, "", "");
	this._renderSeparator(c, this.getItemPosition(a))
};
dhtmlXMenuObject.prototype.hide = function() {
	this._clearAndHide()
};
dhtmlXMenuObject.prototype.clearAll = function() {
	for (var c in this.itemPull) {
		if (this.itemPull[c]["parent"] == this.idPrefix + this.topId) {
			this.removeItem(String(c).replace(this.idPrefix, ""))
		}
	}
	this._isInited = false
};
dhtmlXMenuObject.prototype.unload = function() {
	var k = this.idPull;
	for (var d in k) {
		var f = k[d];
		f.onmouseover = null;
		f.onmouseout = null;
		f.onclick = null;
		f.onselectstart = null;
		f.oncontextmenu = null;
		if (f.parentNode) {
			f.parentNode.removeChild(f)
		}
		f = null;
		k[d] = null;
		try {
			delete k[d]
		} catch (j) {
		}
	}
	this.idPull = null;
	for (var d in this.itemPull) {
		this.itemPull[d] = null
	}
	this.itemPull = null;
	if (this.base != null) {
		this.base.className = "";
		this.base.oncontextmenu = null;
		this.base.onselectstart = null;
		this.base = null
	}
	this.setSkin = null;
	var h = new Array("_enableDacSupport", "_selectedSubItems",
			"_openedPolygons", "_addSubItemToSelected",
			"_removeSubItemFromSelected", "_getSubItemToDeselectByPolygon",
			"_hidePolygon", "_showPolygon", "_redistribSubLevelSelection",
			"_doOnClick", "_doOnTouchMenu", "_searchMenuNode", "_getMenuNodes",
			"_genStr", "getItemType", "forEachItem", "_clearAndHide",
			"_doOnLoad", "loadXML", "loadXMLString", "_buildMenu",
			"_xmlParser", "_xmlLoader", "_showSubLevelItem",
			"_hideSubLevelItem", "init", "_countVisiblePolygonItems",
			"_redefineComplexState", "_updateItemComplexState",
			"_getItemLevelType", "_redistribTopLevelPositions",
			"_redistribTopLevelSelection", "_initTopLevelMenu", "hide",
			"_attachEvents", "_renderToplevelItem", "setImagePath",
			"setIconsPath", "setIconPath", "_updateItemImage", "removeItem",
			"_getAllParents", "renderAsContextMenu", "addContextZone",
			"removeContextZone", "isContextZone", "_isContextMenuVisible",
			"_showContextMenu", "_doOnContextBeforeCall", "showContextMenu",
			"hideContextMenu", "_autoDetectVisibleArea", "getItemPosition",
			"setItemPosition", "getParentId", "addNewSibling", "addNewChild",
			"_addItemIntoGlobalStrorage", "_addSubMenuPolygon",
			"_renderSublevelPolygon", "_renderSublevelItem", "clearAll",
			"_renderSeparator", "addNewSeparator", "attachEvent", "callEvent",
			"checkEvent", "eventCatcher", "detachEvent", "dhx_Event", "unload",
			"_hideContextMenu", "items", "radio", "dacCycles", "dacCyclesIE");
	for (var g = 0; g < h.length; g++) {
		this[h[g]] = null
	}
	h = null;
	var c = new Array("setItemEnabled", "setItemDisabled", "isItemEnabled",
			"_changeItemState", "getItemText", "setItemText", "loadFromHTML",
			"hideItem", "showItem", "isItemHidden", "_changeItemVisible",
			"setUserData", "getUserData", "setOpenMode", "setWebModeTimeout",
			"enableDynamicLoading", "_updateLoaderIcon", "getItemImage",
			"setItemImage", "clearItemImage", "setAutoShowMode",
			"setAutoHideMode", "setContextMenuHideAllMode",
			"getContextMenuHideAllMode", "setVisibleArea", "setTooltip",
			"getTooltip", "setHotKey", "getHotKey", "setItemSelected",
			"setTopText", "setRTL", "setAlign", "setHref", "clearHref",
			"getCircuit", "_clearAllSelectedSubItemsInPolygon",
			"_checkArrowsState", "contextZones", "_addUpArrow",
			"_addDownArrow", "_removeUpArrow", "_removeDownArrow",
			"_isArrowExists", "_doScrollUp", "_doScrollDown",
			"_countPolygonItems", "setOverflowHeight", "_getRadioImgObj",
			"_setRadioState", "_radioOnClickHandler", "userData",
			"getRadioChecked", "setRadioChecked", "addRadioButton",
			"_getCheckboxState", "_setCheckboxState", "_readLevel",
			"_updateCheckboxImage", "_checkboxOnClickHandler",
			"setCheckboxState", "getCheckboxState", "addCheckbox", "serialize");
	for (var g = 0; g < c.length; g++) {
		this[c[g]] = null
	}
	c = null;
	this.extendedModule = null;
	this._unloaded = true;
	dhtmlxMenuObjectLiveInstances[this._UID] = null;
	this._UID = null
};
var dhtmlxMenuObjectLiveInstances = {};
(function() {
	dhtmlx.extend_api("dhtmlXMenuObject", {
				_init : function(a) {
					return [a.parent, a.skin]
				},
				align : "setAlign",
				top_text : "setTopText",
				context : "renderAsContextMenu",
				icon_path : "setIconsPath",
				open_mode : "setOpenMode",
				rtl : "setRTL",
				skin : "setSkin",
				dynamic : "enableDynamicLoading",
				xml : "loadXML",
				items : "items",
				overflow : "setOverflowHeight"
			}, {
				items : function(a, e) {
					var g = 100000;
					var d = null;
					for (var c = 0; c < a.length; c++) {
						var f = a[c];
						if (f.type == "separator") {
							this.addNewSeparator(d, g, f.id);
							d = f.id
						} else {
							this.addNewChild(e, g, f.id, f.text, f.disabled,
									f.img, f.img_disabled);
							d = f.id;
							if (f.items) {
								this.items(f.items, f.id)
							}
						}
					}
				}
			})
})();
function dhtmlXWindowsSngl() {
}
function dhtmlXWindowsBtn() {
}
function dhtmlXWindows() {
	if (!dhtmlXContainer) {
		alert("dhtmlxcontainer.js is missed on the page");
		return
	}
	this.engine = "dhx";
	var e = "_" + this.engine + "_Engine";
	if (!this[e]) {
		alert("No dhtmlxWindows engine was found.");
		return
	} else {
		this[e]()
	}
	var d = this;
	this.pathPrefix = "dhxwins_";
	this.imagePath = dhtmlx.image_path || "codebase/imgs/";
	this.setImagePath = function(a) {
		this.imagePath = a
	};
	this.skin = "dhx_skyblue";
	this.skinParams = {
		standard : {
			header_height : 32,
			border_left_width : 6,
			border_right_width : 7,
			border_bottom_height : 6
		},
		aqua_dark : {
			header_height : 31,
			border_left_width : 3,
			border_right_width : 3,
			border_bottom_height : 3
		},
		aqua_orange : {
			header_height : 31,
			border_left_width : 3,
			border_right_width : 3,
			border_bottom_height : 3
		},
		aqua_sky : {
			header_height : 31,
			border_left_width : 3,
			border_right_width : 3,
			border_bottom_height : 3
		},
		clear_blue : {
			header_height : 32,
			border_left_width : 6,
			border_right_width : 6,
			border_bottom_height : 6
		},
		clear_green : {
			header_height : 32,
			border_left_width : 6,
			border_right_width : 6,
			border_bottom_height : 6
		},
		clear_silver : {
			header_height : 32,
			border_left_width : 6,
			border_right_width : 6,
			border_bottom_height : 6
		},
		glassy_blue : {
			header_height : 26,
			border_left_width : 4,
			border_right_width : 4,
			border_bottom_height : 4
		},
		glassy_blue_light : {
			header_height : 26,
			border_left_width : 3,
			border_right_width : 3,
			border_bottom_height : 3
		},
		glassy_caramel : {
			header_height : 26,
			border_left_width : 4,
			border_right_width : 4,
			border_bottom_height : 4
		},
		glassy_greenapple : {
			header_height : 26,
			border_left_width : 4,
			border_right_width : 4,
			border_bottom_height : 4
		},
		glassy_rainy : {
			header_height : 26,
			border_left_width : 4,
			border_right_width : 4,
			border_bottom_height : 4
		},
		glassy_raspberries : {
			header_height : 26,
			border_left_width : 4,
			border_right_width : 4,
			border_bottom_height : 4
		},
		glassy_yellow : {
			header_height : 26,
			border_left_width : 4,
			border_right_width : 4,
			border_bottom_height : 4
		},
		modern_black : {
			header_height : 39,
			border_left_width : 2,
			border_right_width : 2,
			border_bottom_height : 2
		},
		modern_blue : {
			header_height : 39,
			border_left_width : 2,
			border_right_width : 2,
			border_bottom_height : 2
		},
		modern_red : {
			header_height : 39,
			border_left_width : 2,
			border_right_width : 2,
			border_bottom_height : 2
		},
		web : {
			header_height : 21,
			border_left_width : 2,
			border_right_width : 2,
			border_bottom_height : 2
		},
		vista_blue : {
			header_height : 28,
			border_left_width : 8,
			border_right_width : 8,
			border_bottom_height : 8
		},
		dhx_black : {
			header_height : 21,
			border_left_width : 2,
			border_right_width : 2,
			border_bottom_height : 2
		},
		dhx_blue : {
			header_height : 21,
			border_left_width : 2,
			border_right_width : 2,
			border_bottom_height : 2
		},
		dhx_skyblue : {
			header_height : 21,
			border_left_width : 2,
			border_right_width : 2,
			border_bottom_height : 2
		}
	};
	this.setSkin = function(a) {
		this.skin = a;
		this._engineRedrawSkin()
	};
	this.isWindow = function(f) {
		var a = (this.wins[f] != null);
		return a
	};
	this.findByText = function(h) {
		var g = new Array();
		for (var f in this.wins) {
			if (this.wins[f].getText().search(h, "gi") >= 0) {
				g[g.length] = this.wins[f]
			}
		}
		return g
	};
	this.window = function(f) {
		var a = null;
		if (this.wins[f] != null) {
			a = this.wins[f]
		}
		return a
	};
	this.forEachWindow = function(g) {
		for (var f in this.wins) {
			g(this.wins[f])
		}
	};
	this.getBottommostWindow = function() {
		var f = this.getTopmostWindow();
		for (var g in this.wins) {
			if (this.wins[g].zi < f.zi) {
				f = this.wins[g]
			}
		}
		return (f.zi != 0 ? f : null)
	};
	this.getTopmostWindow = function(h) {
		var g = {
			zi : 0
		};
		for (var f in this.wins) {
			if (this.wins[f].zi > g.zi) {
				if (h == true && !this._isWindowHidden(this.wins[f])) {
					g = this.wins[f]
				}
				if (h != true) {
					g = this.wins[f]
				}
			}
		}
		return (g.zi != 0 ? g : null)
	};
	this.wins = {};
	for (var c in this.wins) {
		delete this.wins[c]
	}
	this.autoViewport = true;
	this._createViewport = function() {
		this.vp = document.body;
		this.vp._css = (String(this.vp.className).length > 0
				? this.vp.className
				: "");
		this.vp.className += " dhtmlx_skin_" + this.skin
				+ (this._r ? " dhx_wins_rtl" : "");
		this.modalCoverI = document.createElement("IFRAME");
		this.modalCoverI.frameBorder = "0";
		this.modalCoverI.className = "dhx_modal_cover_ifr";
		this.modalCoverI.setAttribute("src", "javascript:false;");
		this.modalCoverI.style.display = "none";
		this.modalCoverI.style.zIndex = 0;
		this.vp.appendChild(this.modalCoverI);
		this.modalCoverD = document.createElement("DIV");
		this.modalCoverD.className = "dhx_modal_cover_dv";
		this.modalCoverD.style.display = "none";
		this.modalCoverD.style.zIndex = 0;
		this.vp.appendChild(this.modalCoverD);
		this._vpcover = document.createElement("DIV");
		this._vpcover.className = "dhx_content_vp_cover";
		this._vpcover.style.display = "none";
		this.vp.appendChild(this._vpcover);
		this._carcass = document.createElement("DIV");
		this._carcass.className = "dhx_carcass_resmove";
		this._carcass.style.display = "none";
		if (_isIE) {
			this._carcass.innerHTML = "<iframe border=0 frameborder=0 style='filter: alpha(opacity=0); width: 100%; height:100%; position: absolute; top: 0px; left: 0px; width: 100%; height: 100%;'></iframe><div style='position: absolute; top: 0px; left: 0px; width: 100%; height: 100%;'></div>";
			this._carcass.childNodes[0]
					.setAttribute("src", "javascript:false;")
		}
		this._carcass.onselectstart = function(a) {
			a = a || event;
			a.returnValue = false
		};
		this.vp.appendChild(this._carcass)
	};
	this._autoResizeViewport = function() {
		for (var f in this.wins) {
			if (this.wins[f]._isFullScreened) {
				this.wins[f]._content.style.width = document.body.offsetWidth
						- (_isIE ? 4 : 0) + "px";
				if (document.body.offsetHeight == 0) {
					if (window.innerHeight) {
						this.wins[f]._content.style.height = window.innerHeight
								+ "px"
					} else {
						this.wins[f]._content.style.height = document.body.scrollHeight
								+ "px"
					}
				} else {
					this.wins[f]._content.style.height = document.body.offsetHeight
							- (_isIE ? 4 : 0) + "px"
				}
				if (this.wins[f].layout != null && _isOpera) {
					this.wins[f].layout._fixCellsContentOpera950()
				}
				this.wins[f].updateNestedObjects()
			}
			if (this.wins[f]._isMaximized
					&& this.wins[f].style.display != "none") {
				this._restoreWindow(this.wins[f]);
				this._maximizeWindow(this.wins[f])
			}
		}
		if (this.vp == document.body) {
			return
		}
		if (this.autoViewport == false) {
			return
		}
		this.vp.style.width = (_isIE
				? document.body.offsetWidth - 4
				: window.innerWidth)
				+ "px";
		this.vp.style.height = (_isIE
				? document.body.offsetHeight - 4
				: window.innerHeight)
				+ "px";
		for (var f in this.wins) {
			var k = this.wins[f];
			var j = false;
			var g = false;
			if (k.x > this.vp.offsetWidth - 10) {
				k.x = this.vp.offsetWidth - 10;
				j = true
			}
			var h = (k._skinParams != null
					? k._skinParams
					: this.skinParams[this.skin]);
			if (k.y + h.header_height > this.vp.offsetHeight) {
				k.y = this.vp.offsetHeight - h.header_height;
				g = true
			}
			if (j || g) {
				this._engineRedrawWindowPos(k)
			}
		}
	};
	this.enableAutoViewport = function(a) {
		if (this.vp != document.body) {
			return
		}
		this.autoViewport = a;
		if (a == false) {
			document.body.className = this.vp._css;
			this.vp = document.createElement("DIV");
			this.vp.autocreated = true;
			this.vp.className = "dhtmlx_winviewport dhtmlx_skin_" + this.skin
					+ (this._r ? " dhx_wins_rtl" : "");
			this.vp.style.left = "0px";
			this.vp.style.top = "0px";
			document.body.appendChild(this.vp);
			this.vp.ax = 0;
			this.vp.ay = 0;
			this._autoResizeViewport();
			this.vp.appendChild(this.modalCoverI);
			this.vp.appendChild(this.modalCoverD);
			this.vp.appendChild(this._carcass)
		}
	};
	this.attachViewportTo = function(a) {
		if (this.autoViewport == false) {
			if (this.vp != document.body) {
				this.vp.parentNode.removeChild(this.vp)
			}
			this.vp = document.getElementById(a);
			this.vp.autocreated = false;
			this.vp.className += " dhtmlx_skin_" + this.skin
					+ (this._r ? " dhx_wins_rtl" : "");
			this.vp.style.position = "relative";
			this.vp.ax = 0;
			this.vp.ay = 0;
			this.vp.appendChild(this.modalCoverI);
			this.vp.appendChild(this.modalCoverD);
			this.vp.appendChild(this._carcass)
		}
	};
	this.setViewport = function(f, j, g, a, h) {
		if (this.autoViewport == false) {
			this.vp.style.left = f + "px";
			this.vp.style.top = j + "px";
			this.vp.style.width = g + "px";
			this.vp.style.height = a + "px";
			if (h != null) {
				h.appendChild(this.vp)
			}
			this.vp.ax = getAbsoluteLeft(this.vp);
			this.vp.ay = getAbsoluteTop(this.vp)
		}
	};
	this._effects = {
		move : false,
		resize : false
	};
	this.setEffect = function(f, a) {
		if ((this._effects[f] != null) && (typeof(a) == "boolean")) {
			this._effects[f] = a
		}
	};
	this.getEffect = function(a) {
		return this._effects[a]
	};
	this.createWindow = function(n, g, m, j, f) {
		var l = document.createElement("DIV");
		l.className = "dhtmlx_window_inactive";
		l.dir = "ltr";
		for (var h in this.wins) {
			this.wins[h].zi += this.zIndexStep;
			this.wins[h].style.zIndex = this.wins[h].zi
		}
		l.zi = this.zIndexStep;
		l.style.zIndex = l.zi;
		l.active = false;
		l._isWindow = true;
		l.isWindow = true;
		l.w = j;
		l.h = f;
		l.x = g;
		l.y = m;
		l._isModal = false;
		l._allowResize = true;
		l.maxW = "auto";
		l.maxH = "auto";
		l.minW = 200;
		l.minH = 140;
		l.iconsPresent = true;
		l.icons = new Array(this.imagePath + this.pathPrefix + this.skin
						+ "/active/icon_normal.gif", this.imagePath
						+ this.pathPrefix + this.skin
						+ "/inactive/icon_normal.gif");
		l._allowMove = true;
		l._allowMoveGlobal = true;
		l._allowResizeGlobal = true;
		l._keepInViewport = false;
		var k = this.skinParams[this.skin];
		l.idd = n;
		this.vp.appendChild(l);
		this._engineSetWindowBody(l);
		this._engineRedrawWindowPos(l);
		this._engineRedrawWindowSize(l);
		this._engineUpdateWindowIcon(l, l.icons[0]);
		this._engineDiableOnSelectInWindow(l, true);
		this.wins[n] = l;
		dhtmlxEventable(l);
		this._engineGetWindowHeader(l).onmousedown = function(o) {
			var a = d.wins[this.idd];
			a.bringToTop();
			if (d._engineGetWindowHeaderState(a)) {
				return
			}
			o = o || event;
			if (!d._engineCheckHeaderMouseDown(a, o)) {
				return
			}
			if (!a._allowMove || !a._allowMoveGlobal) {
				return
			}
			a.oldMoveX = a.x;
			a.oldMoveY = a.y;
			a.moveOffsetX = a.x - o.clientX;
			a.moveOffsetY = a.y - o.clientY;
			d.movingWin = a;
			if (d._effects.move == false) {
				d._carcass.x = d.movingWin.x;
				d._carcass.y = d.movingWin.y;
				d._carcass.w = parseInt(d.movingWin.style.width)
						+ (_isIE ? 0 : -2);
				d._carcass.h = parseInt(d.movingWin.style.height)
						+ (_isIE ? 0 : -2);
				d._carcass.style.left = d._carcass.x + "px";
				d._carcass.style.top = d._carcass.y + "px";
				d._carcass.style.width = d._carcass.w + "px";
				d._carcass.style.height = d._carcass.h + "px";
				d._carcass.style.zIndex = d._getTopZIndex(true) + 1;
				d._carcass._keepInViewport = l._keepInViewport
			}
			d._blockSwitcher(true);
			d._vpcover.style.zIndex = d.movingWin.style.zIndex - 1;
			d._vpcover.style.display = "";
			o.returnValue = false;
			o.cancelBubble = true;
			a = null;
			return false
		};
		this._engineGetWindowHeader(l).ondblclick = function(o) {
			var a = d.wins[this.idd];
			if (!d._engineCheckHeaderMouseDown(a, o || event)) {
				return
			}
			if (a._allowResizeGlobal && !a._isParked) {
				if (a._isMaximized == true) {
					d._restoreWindow(a)
				} else {
					d._maximizeWindow(a)
				}
			}
		};
		l.setText = function(a) {
			d._engineGetWindowLabel(this).innerHTML = a
		};
		l.getText = function() {
			return d._engineGetWindowLabel(this).innerHTML
		};
		l.getId = function() {
			return this.idd
		};
		l.show = function() {
			d._showWindow(this)
		};
		l.hide = function() {
			d._hideWindow(this)
		};
		l.minimize = function() {
			d._restoreWindow(this)
		};
		l.maximize = function() {
			d._maximizeWindow(this)
		};
		l.close = function() {
			d._closeWindow(this)
		};
		l.park = function() {
			if (this._isParkedAllowed) {
				d._parkWindow(this)
			}
		};
		l.stick = function() {
			d._stickWindow(this)
		};
		l.unstick = function() {
			d._unstickWindow(this)
		};
		l.isSticked = function() {
			return this._isSticked
		};
		l.setIcon = function(o, a) {
			d._setWindowIcon(l, o, a)
		};
		l.getIcon = function() {
			return d._getWindowIcon(this)
		};
		l.clearIcon = function() {
			d._clearWindowIcons(this)
		};
		l.restoreIcon = function() {
			d._restoreWindowIcons(this)
		};
		l.keepInViewport = function(a) {
			this._keepInViewport = a
		};
		l.setModal = function(a) {
			if (a == true) {
				if (d.modalWin != null || d.modalWin == this) {
					return
				}
				d._setWindowModal(this, true)
			} else {
				if (d.modalWin != this) {
					return
				}
				d._setWindowModal(this, false)
			}
		};
		l.isModal = function() {
			return this._isModal
		};
		l.isHidden = function() {
			return d._isWindowHidden(this)
		};
		l.isMaximized = function() {
			return this._isMaximized
		};
		l.isParked = function() {
			return this._isParked
		};
		l.allowPark = function() {
			d._allowParking(this)
		};
		l.denyPark = function() {
			d._denyParking(this)
		};
		l.isParkable = function() {
			return this._isParkedAllowed
		};
		l.allowResize = function() {
			d._allowReszieGlob(this)
		};
		l.denyResize = function() {
			d._denyResize(this)
		};
		l.isResizable = function() {
			return this._allowResizeGlobal
		};
		l.allowMove = function() {
			if (!this._isMaximized) {
				this._allowMove = true
			}
			this._allowMoveGlobal = true
		};
		l.denyMove = function() {
			this._allowMoveGlobal = false
		};
		l.isMovable = function() {
			return this._allowMoveGlobal
		};
		l.bringToTop = function() {
			d._bringOnTop(this);
			d._makeActive(this)
		};
		l.bringToBottom = function() {
			d._bringOnBottom(this)
		};
		l.isOnTop = function() {
			return d._isWindowOnTop(this)
		};
		l.isOnBottom = function() {
			return d._isWindowOnBottom(this)
		};
		l.setPosition = function(a, o) {
			this.x = a;
			this.y = o;
			d._engineFixWindowPosInViewport(this);
			d._engineRedrawWindowPos(this)
		};
		l.getPosition = function() {
			return new Array(this.x, this.y)
		};
		l.setDimension = function(o, a) {
			if (o != null) {
				this.w = o
			}
			if (a != null) {
				this.h = a
			}
			d._fixWindowDimensionInViewport(this);
			d._engineFixWindowPosInViewport(this);
			d._engineRedrawWindowSize(this);
			this.updateNestedObjects()
		};
		l.getDimension = function() {
			return new Array(this.w, this.h)
		};
		l.setMaxDimension = function(o, a) {
			this.minW = "auto";
			this.minH = "auto";
			d._engineRedrawWindowSize(this)
		};
		l.getMaxDimension = function() {
			return new Array(this.maxW, this.maxH)
		};
		l.setMinDimension = function(a, o) {
			if (a != null) {
				this.minW = a
			}
			if (o != null) {
				this.minH = o
			}
			d._fixWindowDimensionInViewport(this);
			d._engineRedrawWindowPos(this)
		};
		l.getMinDimension = function() {
			return new Array(this.minW, this.minH)
		};
		l._adjustToContent = function(a, o) {
			d._engineAdjustWindowToContent(this, a, o)
		};
		l._doOnAttachMenu = function() {
			d._engineRedrawWindowSize(this);
			this.updateNestedObjects()
		};
		l._doOnAttachToolbar = function() {
			d._engineRedrawWindowSize(this);
			this.updateNestedObjects()
		};
		l._doOnAttachStatusBar = function() {
			d._engineRedrawWindowSize(this);
			this.updateNestedObjects()
		};
		l._doOnAttachURL = function(o) {
			if (!o) {
				d.callEvent("onContentLoaded", [this]);
				return
			}
			if (_isIE) {
				var a = this;
				var p = this._frame;
				p.onreadystatechange = function(q) {
					if (p.readyState == "complete") {
						try {
							p.contentWindow.document.body.onmousedown = function() {
								try {
									a.bringToTop()
								} catch (s) {
								}
							}
						} catch (r) {
						}
						try {
							d.callEvent("onContentLoaded", [a])
						} catch (r) {
						}
					}
				}
			} else {
				var a = this;
				var p = this._frame;
				p.onload = function() {
					try {
						p.contentWindow.onmousedown = function() {
							try {
								a.bringToTop()
							} catch (r) {
							}
						}
					} catch (q) {
					}
					d.callEvent("onContentLoaded", [a])
				}
			}
		};
		l.addUserButton = function(r, q, p, a) {
			var o = d._addUserButton(this, r, q, p, a);
			return o
		};
		l.removeUserButton = function(a) {
			if (!((a == "minmax1") || (a == "minmax2") || (a == "park")
					|| (a == "close") || (a == "stick") || (a == "unstick") || (a == "help"))) {
				if (btn != null) {
					d._removeUserButton(this, a)
				}
			}
		};
		l.progressOn = function() {
			d._engineSwitchWindowProgress(this, true)
		};
		l.progressOff = function() {
			d._engineSwitchWindowProgress(this, false)
		};
		l.setToFullScreen = function(a) {
			d._setWindowToFullScreen(this, a)
		};
		l.showHeader = function() {
			d._engineSwitchWindowHeader(this, true)
		};
		l.hideHeader = function() {
			d._engineSwitchWindowHeader(this, false)
		};
		l.progressOff();
		l.canStartResize = false;
		l.onmousemove = function(r) {
			r = r || event;
			var p = r.target || r.srcElement;
			if ((!this._allowResize) || (this._allowResizeGlobal == false)) {
				if (p.style.cursor != "") {
					p.style.cursor = ""
				}
				if (this.style.cursor != "") {
					this.style.cursor = ""
				}
				this.canStartResize = false;
				return false
			}
			if (d.resizingWin != null) {
				return
			}
			if (d.movingWin != null) {
				return
			}
			if (this._isParked) {
				return
			}
			var o = (_isIE || _isOpera ? r.offsetX : r.layerX);
			var a = (_isIE || _isOpera ? r.offsetY : r.layerY);
			var q = d._engineAllowWindowResize(l, p, o, a);
			if (q == null) {
				this.canStartResize = false;
				this.style.cursor = "";
				return
			}
			d.resizingDirs = q;
			switch (d.resizingDirs) {
				case "border_left" :
					p.style.cursor = "w-resize";
					this.resizeOffsetX = this.x - r.clientX;
					break;
				case "border_right" :
					p.style.cursor = "e-resize";
					this.resizeOffsetXW = this.x + this.w - r.clientX;
					break;
				case "border_top" :
					p.style.cursor = "n-resize";
					this.resizeOffsetY = this.y - r.clientY;
					break;
				case "border_bottom" :
					p.style.cursor = "n-resize";
					this.resizeOffsetYH = this.y + this.h - r.clientY;
					break;
				case "corner_left" :
					p.style.cursor = "sw-resize";
					this.resizeOffsetX = this.x - r.clientX;
					this.resizeOffsetYH = this.y + this.h - r.clientY;
					break;
				case "corner_up_left" :
					p.style.cursor = "nw-resize";
					this.resizeOffsetY = this.y - r.clientY;
					this.resizeOffsetX = this.x - r.clientX;
					break;
				case "corner_right" :
					p.style.cursor = "nw-resize";
					this.resizeOffsetXW = this.x + this.w - r.clientX;
					this.resizeOffsetYH = this.y + this.h - r.clientY;
					break;
				case "corner_up_right" :
					p.style.cursor = "sw-resize";
					this.resizeOffsetY = this.y - r.clientY;
					this.resizeOffsetXW = this.x + this.w - r.clientX;
					break
			}
			this.canStartResize = true;
			this.style.cursor = p.style.cursor;
			return false
		};
		l.onmousedown = function(a) {
			d._makeActive(this);
			d._bringOnTop(this);
			if (this.canStartResize) {
				d._blockSwitcher(true);
				d.resizingWin = this;
				if (!d._effects.resize) {
					d._carcass.x = d.resizingWin.x;
					d._carcass.y = d.resizingWin.y;
					d._carcass.w = d.resizingWin.w + (_isIE ? 0 : -2);
					d._carcass.h = d.resizingWin.h + (_isIE ? 0 : -2);
					d._carcass.style.left = d._carcass.x + "px";
					d._carcass.style.top = d._carcass.y + "px";
					d._carcass.style.width = d._carcass.w + "px";
					d._carcass.style.height = d._carcass.h + "px";
					d._carcass.style.zIndex = d._getTopZIndex(true) + 1;
					d._carcass.style.cursor = this.style.cursor;
					d._carcass._keepInViewport = this._keepInViewport;
					d._carcass.style.display = ""
				}
				d._vpcover.style.zIndex = d.resizingWin.style.zIndex - 1;
				d._vpcover.style.display = "";
				if (this.layout) {
					this.callEvent("_onBeforeTryResize", [this])
				}
				a = a || event;
				a.returnValue = false;
				a.cancelBubble = true;
				return false
			}
		};
		this._addDefaultButtons(l);
		l.button = function(o) {
			var a = null;
			if (this.btns[o] != null) {
				a = this.btns[o]
			}
			return a
		};
		l.center = function() {
			d._centerWindow(this, false)
		};
		l.centerOnScreen = function() {
			d._centerWindow(this, true)
		};
		l._attachContent("empty", null);
		l._redraw = function() {
			d._engineRedrawWindowSize(this)
		};
		l.bringToTop();
		this._engineRedrawWindowSize(l);
		return this.wins[n]
	};
	this.zIndexStep = 50;
	this._getTopZIndex = function(g) {
		var h = 0;
		for (var f in this.wins) {
			if (g == true) {
				if (this.wins[f].zi > h) {
					h = this.wins[f].zi
				}
			} else {
				if (this.wins[f].zi > h && !this.wins[f]._isSticked) {
					h = this.wins[f].zi
				}
			}
		}
		return h
	};
	this.movingWin = null;
	this._moveWindow = function(g) {
		if (this.movingWin != null) {
			if (!this.movingWin._allowMove || !this.movingWin._allowMoveGlobal) {
				return
			}
			if (this._effects.move == true) {
				if (this._engineGetWindowHeader(this.movingWin).style.cursor != "move") {
					this._engineGetWindowHeader(this.movingWin).style.cursor = "move"
				}
				this.movingWin.oldMoveX = this.movingWin.x;
				this.movingWin.oldMoveY = this.movingWin.y;
				this.movingWin.x = g.clientX + this.movingWin.moveOffsetX;
				this.movingWin.y = g.clientY + this.movingWin.moveOffsetY;
				this._engineFixWindowPosInViewport(this.movingWin);
				this._engineRedrawWindowPos(this.movingWin)
			} else {
				if (this._carcass.style.display != "") {
					this._carcass.style.display = ""
				}
				if (this._carcass.style.cursor != "move") {
					this._carcass.style.cursor = "move"
				}
				if (this._engineGetWindowHeader(this.movingWin).style.cursor != "move") {
					this._engineGetWindowHeader(this.movingWin).style.cursor = "move"
				}
				this._carcass.x = g.clientX + this.movingWin.moveOffsetX;
				this._carcass.y = g.clientY + this.movingWin.moveOffsetY;
				this._engineFixWindowPosInViewport(this._carcass);
				this._carcass.style.left = this._carcass.x + "px";
				this._carcass.style.top = this._carcass.y + "px"
			}
		}
		if (this.resizingWin != null) {
			if (!this.resizingWin._allowResize) {
				return
			}
			if (this.resizingDirs == "border_left"
					|| this.resizingDirs == "corner_left"
					|| this.resizingDirs == "corner_up_left") {
				if (this._effects.resize) {
					var f = g.clientX + this.resizingWin.resizeOffsetX;
					var a = (f > this.resizingWin.x ? -1 : 1);
					newW = this.resizingWin.w
							+ Math.abs(f - this.resizingWin.x) * a;
					if ((newW < this.resizingWin.minW) && (a < 0)) {
						this.resizingWin.x = this.resizingWin.x
								+ this.resizingWin.w - this.resizingWin.minW;
						this.resizingWin.w = this.resizingWin.minW
					} else {
						this.resizingWin.w = newW;
						this.resizingWin.x = f
					}
					this._engineRedrawWindowPos(this.resizingWin);
					this._engineRedrawWindowSize(this.resizingWin)
				} else {
					var f = g.clientX + this.resizingWin.resizeOffsetX;
					var a = (f > this._carcass.x ? -1 : 1);
					newW = this._carcass.w + Math.abs(f - this._carcass.x) * a;
					if ((newW < this.resizingWin.minW) && (a < 0)) {
						this._carcass.x = this._carcass.x + this._carcass.w
								- this.resizingWin.minW;
						this._carcass.w = this.resizingWin.minW
					} else {
						this._carcass.w = newW;
						this._carcass.x = f
					}
					this._carcass.style.left = this._carcass.x + "px";
					this._carcass.style.width = this._carcass.w + "px"
				}
			}
			if (this.resizingDirs == "border_right"
					|| this.resizingDirs == "corner_right"
					|| this.resizingDirs == "corner_up_right") {
				if (this._effects.resize) {
					var f = g.clientX
							- (this.resizingWin.x + this.resizingWin.w)
							+ this.resizingWin.resizeOffsetXW;
					newW = this.resizingWin.w + f;
					if (newW < this.resizingWin.minW) {
						newW = this.resizingWin.minW
					}
					this.resizingWin.w = newW;
					this._engineRedrawWindowPos(this.resizingWin);
					this._engineRedrawWindowSize(this.resizingWin)
				} else {
					var f = g.clientX - (this._carcass.x + this._carcass.w)
							+ this.resizingWin.resizeOffsetXW;
					newW = this._carcass.w + f;
					if (newW < this.resizingWin.minW) {
						newW = this.resizingWin.minW
					}
					this._carcass.w = newW;
					this._carcass.style.width = this._carcass.w + "px"
				}
			}
			if (this.resizingDirs == "border_bottom"
					|| this.resizingDirs == "corner_left"
					|| this.resizingDirs == "corner_right") {
				if (this._effects.resize) {
					var f = g.clientY
							- (this.resizingWin.y + this.resizingWin.h)
							+ this.resizingWin.resizeOffsetYH;
					newH = this.resizingWin.h + f;
					if (newH < this.resizingWin.minH) {
						newH = this.resizingWin.minH
					}
					this.resizingWin.h = newH;
					this._engineRedrawWindowPos(this.resizingWin);
					this._engineRedrawWindowSize(this.resizingWin)
				} else {
					var f = g.clientY - (this._carcass.y + this._carcass.h)
							+ this.resizingWin.resizeOffsetYH;
					newH = this._carcass.h + f;
					if (newH < this.resizingWin.minH) {
						newH = this.resizingWin.minH
					}
					this._carcass.h = newH;
					this._carcass.style.height = this._carcass.h + "px"
				}
			}
			if (this.resizingDirs == "border_top"
					|| this.resizingDirs == "corner_up_right"
					|| this.resizingDirs == "corner_up_left") {
				if (this._effects.resize) {
				} else {
					var f = g.clientY + this.resizingWin.resizeOffsetY;
					var a = (f > this.resizingWin.y ? -1 : 1);
					newH = this.resizingWin.h
							+ Math.abs(f - this.resizingWin.y) * a;
					if ((newH < this.resizingWin.minH) && (a < 0)) {
						this._carcass.y = this._carcass.y + this._carcass.h
								- this.resizingWin.minH;
						this._carcass.h = this.resizingWin.minH
					} else {
						this._carcass.h = newH + (_isIE ? 0 : -2);
						this._carcass.y = f
					}
					this._carcass.style.top = this._carcass.y + "px";
					this._carcass.style.height = this._carcass.h + "px"
				}
			}
		}
	};
	this._stopMove = function() {
		if (this.movingWin != null) {
			if (this._effects.move) {
				var a = this.movingWin;
				this.movingWin = null;
				this._blockSwitcher(false);
				this._engineGetWindowHeader(a).style.cursor = "";
				if (_isFF) {
					a.h++;
					d._engineRedrawWindowPos(a);
					a.h--;
					d._engineRedrawWindowPos(a)
				}
			} else {
				this._carcass.style.cursor = "";
				this._carcass.style.display = "none";
				var a = this.movingWin;
				this._engineGetWindowHeader(a).style.cursor = "";
				this.movingWin = null;
				this._blockSwitcher(false);
				a.setPosition(parseInt(this._carcass.style.left),
						parseInt(this._carcass.style.top))
			}
			this._vpcover.style.display = "none";
			if (!(a.oldMoveX == a.x && a.oldMoveY == a.y)) {
				if (a.checkEvent("onMoveFinish")) {
					a.callEvent("onMoveFinish", [a])
				} else {
					this.callEvent("onMoveFinish", [a])
				}
			}
		}
		if (this.resizingWin != null) {
			var a = this.resizingWin;
			this.resizingWin = null;
			this._blockSwitcher(false);
			if (!this._effects.resize) {
				this._carcass.style.display = "none";
				a.setPosition(this._carcass.x, this._carcass.y);
				a.setDimension(this._carcass.w + (_isIE ? 0 : 2),
						this._carcass.h + (_isIE ? 0 : 2))
			} else {
				a.updateNestedObjects()
			}
			if (a.layout) {
				a.layout.callEvent("onResize", [])
			}
			this._vpcover.style.display = "none";
			if (a.checkEvent("onResizeFinish")) {
				a.callEvent("onResizeFinish", [a])
			} else {
				this.callEvent("onResizeFinish", [a])
			}
		}
	};
	this._fixWindowDimensionInViewport = function(a) {
		if (a.w < a.minW) {
			a.w = a.minW
		}
		if (a._isParked) {
			return
		}
		if (a.h < a.minH) {
			a.h = a.minH
		}
	};
	this._bringOnTop = function(j) {
		var g = j.zi;
		var h = this._getTopZIndex(j._isSticked);
		for (var f in this.wins) {
			if (this.wins[f] != j) {
				if (j._isSticked || (!j._isSticked && !this.wins[f]._isSticked)) {
					if (this.wins[f].zi > g) {
						this.wins[f].zi = this.wins[f].zi - this.zIndexStep;
						this.wins[f].style.zIndex = this.wins[f].zi
					}
				}
			}
		}
		j.zi = h;
		j.style.zIndex = j.zi
	};
	this._makeActive = function(h, g) {
		for (var f in this.wins) {
			if (this.wins[f] == h) {
				var j = false;
				if (this.wins[f].className != "dhtmlx_window_active" && !g) {
					j = true
				}
				this.wins[f].className = "dhtmlx_window_active";
				this._engineUpdateWindowIcon(this.wins[f],
						this.wins[f].icons[0]);
				if (j == true) {
					if (h.checkEvent("onFocus")) {
						h.callEvent("onFocus", [h])
					} else {
						this.callEvent("onFocus", [h])
					}
				}
			} else {
				this.wins[f].className = "dhtmlx_window_inactive";
				this._engineUpdateWindowIcon(this.wins[f],
						this.wins[f].icons[1])
			}
		}
	};
	this._getActive = function() {
		var g = null;
		for (var f in this.wins) {
			if (this.wins[f].className == "dhtmlx_window_active") {
				g = this.wins[f]
			}
		}
		return g
	};
	this._centerWindow = function(h, a) {
		if (h._isMaximized == true) {
			return
		}
		if (h._isParked == true) {
			return
		}
		if (a == true) {
			var f = (_isIE ? document.body.offsetWidth : window.innerWidth);
			var k = (_isIE ? document.body.offsetHeight : window.innerHeight)
		} else {
			var f = (this.vp == document.body
					? document.body.offsetWidth
					: (Number(parseInt(this.vp.style.width))
							&& String(this.vp.style.width).search("%") == -1
							? parseInt(this.vp.style.width)
							: this.vp.offsetWidth));
			var k = (this.vp == document.body
					? document.body.offsetHeight
					: (Number(parseInt(this.vp.style.height))
							&& String(this.vp.style.height).search("%") == -1
							? parseInt(this.vp.style.height)
							: this.vp.offsetHeight))
		}
		var j = Math.round((f / 2) - (h.w / 2));
		var g = Math.round((k / 2) - (h.h / 2));
		h.x = j;
		h.y = g;
		this._engineFixWindowPosInViewport(h);
		this._engineRedrawWindowPos(h)
	};
	this._addDefaultButtons = function(m) {
		var l = this._engineGetWindowButton(m, "stick");
		l.title = "Stick";
		l.isVisible = false;
		l.style.display = "none";
		l._isEnabled = true;
		l.isPressed = false;
		l.label = "stick";
		l._doOnClick = function() {
			this.isPressed = true;
			d._stickWindow(m)
		};
		var h = this._engineGetWindowButton(m, "sticked");
		h.title = "Unstick";
		h.isVisible = false;
		h.style.display = "none";
		h._isEnabled = true;
		h.isPressed = false;
		h.label = "sticked";
		h._doOnClick = function() {
			this.isPressed = false;
			d._unstickWindow(m)
		};
		var f = this._engineGetWindowButton(m, "help");
		f.title = "Help";
		f.isVisible = false;
		f.style.display = "none";
		f._isEnabled = true;
		f.isPressed = false;
		f.label = "help";
		f._doOnClick = function() {
			d._needHelp(m)
		};
		var k = this._engineGetWindowButton(m, "park");
		k.titleIfParked = "Park Down";
		k.titleIfNotParked = "Park Up";
		k.title = k.titleIfNotParked;
		k.isVisible = true;
		k._isEnabled = true;
		k.isPressed = false;
		k.label = "park";
		k._doOnClick = function() {
			d._parkWindow(m)
		};
		var j = this._engineGetWindowButton(m, "minmax1");
		j.title = "Maximize";
		j.isVisible = true;
		j._isEnabled = true;
		j.isPressed = false;
		j.label = "minmax1";
		j._doOnClick = function() {
			d._maximizeWindow(m)
		};
		var g = this._engineGetWindowButton(m, "minmax2");
		g.title = "Restore";
		g.isVisible = false;
		g.style.display = "none";
		g._isEnabled = true;
		g.isPressed = false;
		g.label = "minmax2";
		g._doOnClick = function() {
			d._restoreWindow(m)
		};
		var o = this._engineGetWindowButton(m, "close");
		o.title = "Close";
		o.isVisible = true;
		o._isEnabled = true;
		o.isPressed = false;
		o.label = "close";
		o._doOnClick = function() {
			d._closeWindow(m)
		};
		var n = this._engineGetWindowButton(m, "dock");
		n.title = "Dock";
		n.style.display = "none";
		n.isVisible = false;
		n._isEnabled = true;
		n.isPressed = false;
		n.label = "dock";
		n._doOnClick = function() {
		};
		m._isSticked = false;
		m._isParked = false;
		m._isParkedAllowed = true;
		m._isMaximized = false;
		m._isDocked = false;
		m.btns = {};
		m.btns.stick = l;
		m.btns.sticked = h;
		m.btns.help = f;
		m.btns.park = k;
		m.btns.minmax1 = j;
		m.btns.minmax2 = g;
		m.btns.close = o;
		m.btns.dock = n;
		for (var p in m.btns) {
			this._attachEventsOnButton(m, m.btns[p])
		}
	};
	this._attachEventsOnButton = function(f, a) {
		a.onmouseover = function() {
			if (this._isEnabled) {
				this.className = "dhtmlx_wins_btns_button dhtmlx_button_"
						+ this.label + "_over_"
						+ (this.isPressed ? "pressed" : "default")
			} else {
				this.className = "dhtmlx_wins_btns_button dhtmlx_button_"
						+ this.label + "_disabled"
			}
		};
		a.onmouseout = function() {
			if (this._isEnabled) {
				this.isPressed = false;
				this.className = "dhtmlx_wins_btns_button dhtmlx_button_"
						+ this.label + "_default"
			} else {
				this.className = "dhtmlx_wins_btns_button dhtmlx_button_"
						+ this.label + "_disabled"
			}
		};
		a.onmousedown = function() {
			if (this._isEnabled) {
				this.isPressed = true;
				this.className = "dhtmlx_wins_btns_button dhtmlx_button_"
						+ this.label + "_over_pressed"
			} else {
				this.className = "dhtmlx_wins_btns_button dhtmlx_button_"
						+ this.label + "_disabled"
			}
		};
		a.onmouseup = function() {
			if (this._isEnabled) {
				var g = this.isPressed;
				this.isPressed = false;
				this.className = "dhtmlx_wins_btns_button dhtmlx_button_"
						+ this.label + "_over_default";
				if (g) {
					if (this.checkEvent("onClick")) {
						this.callEvent("onClick", [f, this])
					} else {
						this._doOnClick()
					}
				}
			} else {
				this.className = "dhtmlx_wins_btns_button dhtmlx_button_"
						+ this.label + "_disabled"
			}
		};
		a.show = function() {
			d._showButton(f, this.label)
		};
		a.hide = function() {
			d._hideButton(f, this.label)
		};
		a.enable = function() {
			d._enableButton(f, this.label)
		};
		a.disable = function() {
			d._disableButton(f, this.label)
		};
		a.isEnabled = function() {
			return this._isEnabled
		};
		a.isHidden = function() {
			return (!this.isVisible)
		};
		dhtmlxEventable(a)
	};
	this._parkWindow = function(f, g) {
		if (!f._isParkedAllowed) {
			return
		}
		if (this.enableParkEffect && f.parkBusy) {
			return
		}
		if (f._isParked) {
			if (this.enableParkEffect && !g) {
				f.parkBusy = true;
				this._doParkDown(f)
			} else {
				f.h = f.lastParkH;
				this._engineRedrawWindowSize(f);
				this._engineDoOnWindowParkDown(f);
				f.updateNestedObjects();
				f.btns.park.title = f.btns.park.titleIfNotParked;
				if (f._allowResizeGlobal == true) {
					this._enableButton(f, "minmax1");
					this._enableButton(f, "minmax2")
				}
				f._isParked = false;
				if (!g) {
					if (f.checkEvent("onParkDown")) {
						f.callEvent("onParkDown", [f])
					} else {
						this.callEvent("onParkDown", [f])
					}
				}
			}
		} else {
			if (this.enableParkEffect && !g) {
				f.lastParkH = (String(f.h).search(/\%$/) == -1
						? f.h
						: f.offsetHeight);
				if (f._allowResizeGlobal == true) {
					this._disableButton(f, "minmax1");
					this._disableButton(f, "minmax2")
				}
				if (this.enableParkEffect) {
					f.parkBusy = true;
					this._doParkUp(f)
				} else {
					var a = (f._skinParams != null
							? f._skinParams
							: this.skinParams[this.skin]);
					f.h = a.header_height + a.border_bottom_height;
					f.btns.park.title = f.btns.park.titleIfParked
				}
			} else {
				f.lastParkH = (String(f.h).search(/\%$/) == -1
						? f.h
						: f.offsetHeight);
				f.h = this._engineGetWindowParkedHeight(f);
				this._engineRedrawWindowSize(f);
				this._engineDoOnWindowParkUp(f);
				f.btns.park.title = f.btns.park.titleIfParked;
				f._isParked = true;
				if (!g) {
					if (f.checkEvent("onParkUp")) {
						f.callEvent("onParkUp", [f])
					} else {
						this.callEvent("onParkUp", [f])
					}
				}
			}
		}
	};
	this._allowParking = function(a) {
		a._isParkedAllowed = true;
		this._enableButton(a, "park")
	};
	this._denyParking = function(a) {
		a._isParkedAllowed = false;
		this._disableButton(a, "park")
	};
	this.enableParkEffect = false;
	this.parkStartSpeed = 80;
	this.parkSpeed = this.parkStartSpeed;
	this.parkTM = null;
	this.parkTMTime = 5;
	this._doParkUp = function(f) {
		if (String(f.h).search(/\%$/) != -1) {
			f.h = f.offsetHeight
		}
		f.h -= this.parkSpeed;
		var a = this._engineGetWindowParkedHeight(f);
		if (f.h <= a) {
			f.h = a;
			this._engineGetWindowButton(f, "park").title = this
					._engineGetWindowButton(f, "park").titleIfParked;
			f._isParked = true;
			f.parkBusy = false;
			this._engineRedrawWindowSize(f);
			this._engineDoOnWindowParkUp(f);
			if (f.checkEvent("onParkUp")) {
				f.callEvent("onParkUp", [f])
			} else {
				this.callEvent("onParkUp", [f])
			}
		} else {
			this._engineRedrawWindowSize(f);
			this.parkTM = window.setTimeout(function() {
						d._doParkUp(f)
					}, this.parkTMTime)
		}
	};
	this._doParkDown = function(a) {
		a.h += this.parkSpeed;
		if (a.h >= a.lastParkH) {
			a.h = a.lastParkH;
			this._engineGetWindowButton(a, "park").title = this
					._engineGetWindowButton(a, "park").titleIfNotParked;
			if (a._allowResizeGlobal == true) {
				this._enableButton(a, "minmax1");
				this._enableButton(a, "minmax2")
			}
			a._isParked = false;
			a.parkBusy = false;
			this._engineRedrawWindowSize(a);
			a.updateNestedObjects();
			this._engineDoOnWindowParkDown(a);
			if (a.checkEvent("onParkDown")) {
				a.callEvent("onParkDown", [a])
			} else {
				this.callEvent("onParkDown", [a])
			}
		} else {
			this._engineRedrawWindowSize(a);
			this.parkTM = window.setTimeout(function() {
						d._doParkDown(a)
					}, this.parkTMTime)
		}
	};
	this._enableButton = function(g, f) {
		var a = this._engineGetWindowButton(g, f);
		if (!a) {
			return
		}
		a._isEnabled = true;
		a.className = "dhtmlx_wins_btns_button dhtmlx_button_" + a.label
				+ "_default"
	};
	this._disableButton = function(g, f) {
		var a = this._engineGetWindowButton(g, f);
		if (!a) {
			return
		}
		a._isEnabled = false;
		a.className = "dhtmlx_wins_btns_button dhtmlx_button_"
				+ g.btns[f].label + "_disabled"
	};
	this._allowReszieGlob = function(a) {
		a._allowResizeGlobal = true;
		this._enableButton(a, "minmax1");
		this._enableButton(a, "minmax2")
	};
	this._denyResize = function(a) {
		a._allowResizeGlobal = false;
		this._disableButton(a, "minmax1");
		this._disableButton(a, "minmax2")
	};
	this._maximizeWindow = function(f) {
		if (f._allowResizeGlobal == false) {
			return
		}
		var a = f._isParked;
		if (a) {
			this._parkWindow(f, true)
		}
		f.lastMaximizeX = f.x;
		f.lastMaximizeY = f.y;
		f.lastMaximizeW = f.w;
		f.lastMaximizeH = f.h;
		f.x = 0;
		f.y = 0;
		f._isMaximized = true;
		f._allowMove = false;
		f._allowResize = false;
		f.w = (f.maxW == "auto" ? (this.vp == document.body
				? "100%"
				: (this.vp.style.width != ""
						&& String(this.vp.style.width).search("%") == -1
						? parseInt(this.vp.style.width)
						: this.vp.offsetWidth)) : f.maxW);
		f.h = (f.maxH == "auto" ? (this.vp == document.body
				? "100%"
				: (this.vp.style.height != ""
						&& String(this.vp.style.width).search("%") == -1
						? parseInt(this.vp.style.height)
						: this.vp.offsetHeight)) : f.maxH);
		this._hideButton(f, "minmax1");
		this._showButton(f, "minmax2");
		this._engineRedrawWindowPos(f);
		if (a) {
			this._parkWindow(f, true)
		} else {
			this._engineRedrawWindowSize(f);
			f.updateNestedObjects()
		}
		if (f.checkEvent("onMaximize")) {
			f.callEvent("onMaximize", [f])
		} else {
			this.callEvent("onMaximize", [f])
		}
	};
	this._restoreWindow = function(f) {
		if (f._allowResizeGlobal == false) {
			return
		}
		if (f.layout) {
			f.layout._defineWindowMinDimension(f)
		}
		var a = f._isParked;
		if (a) {
			this._parkWindow(f, true)
		}
		f.x = f.lastMaximizeX;
		f.y = f.lastMaximizeY;
		f.w = f.lastMaximizeW;
		f.h = f.lastMaximizeH;
		f._isMaximized = false;
		f._allowMove = f._allowMoveGlobal;
		f._allowResize = true;
		this._fixWindowDimensionInViewport(f);
		this._hideButton(f, "minmax2");
		this._showButton(f, "minmax1");
		this._engineRedrawWindowPos(f);
		if (a) {
			this._parkWindow(f, true)
		} else {
			this._engineRedrawWindowSize(f);
			f.updateNestedObjects()
		}
		if (f.checkEvent("onMinimize")) {
			f.callEvent("onMinimize", [f])
		} else {
			this.callEvent("onMinimize", [f])
		}
	};
	this._showButton = function(g, f) {
		var a = this._engineGetWindowButton(g, f);
		if (!a) {
			return
		}
		a.isVisible = true;
		a.style.display = "";
		this._engineRedrawWindowTitle(g)
	};
	this._hideButton = function(g, f) {
		var a = this._engineGetWindowButton(g, f);
		if (!a) {
			return
		}
		a.isVisible = false;
		a.style.display = "none";
		this._engineRedrawWindowTitle(g)
	};
	this._showWindow = function(f) {
		f.style.display = "";
		if (f.checkEvent("onShow")) {
			f.callEvent("onShow", [f])
		} else {
			this.callEvent("onShow", [f])
		}
		var a = this._getActive();
		if (a == null) {
			this._bringOnTop(f);
			this._makeActive(f)
		} else {
			if (this._isWindowHidden(a)) {
				this._bringOnTop(f);
				this._makeActive(f)
			}
		}
	};
	this._hideWindow = function(f) {
		f.style.display = "none";
		if (f.checkEvent("onHide")) {
			f.callEvent("onHide", [f])
		} else {
			this.callEvent("onHide", [f])
		}
		var a = this.getTopmostWindow(true);
		if (a != null) {
			this._bringOnTop(a);
			this._makeActive(a)
		}
	};
	this._isWindowHidden = function(f) {
		var a = (f.style.display == "none");
		return a
	};
	this._closeWindow = function(h) {
		if (h.checkEvent("onClose")) {
			if (!h.callEvent("onClose", [h])) {
				return
			}
		} else {
			if (!this.callEvent("onClose", [h])) {
				return
			}
		}
		this._removeWindowGlobal(h);
		var g = {
			zi : 0
		};
		for (var f in this.wins) {
			if (this.wins[f].zi > g.zi) {
				g = this.wins[f]
			}
		}
		if (g != null) {
			this._makeActive(g)
		}
	};
	this._needHelp = function(a) {
		if (a.checkEvent("onHelp")) {
			a.callEvent("onHelp", [a])
		} else {
			this.callEvent("onHelp", [a])
		}
	};
	this._setWindowIcon = function(g, f, a) {
		g.iconsPresent = true;
		g.icons[0] = this.imagePath + f;
		g.icons[1] = this.imagePath + a;
		this._engineUpdateWindowIcon(g, g.icons[g.isOnTop() ? 0 : 1])
	};
	this._getWindowIcon = function(a) {
		if (a.iconsPresent) {
			return new Array(a.icons[0], a.icons[1])
		} else {
			return new Array(null, null)
		}
	};
	this._clearWindowIcons = function(a) {
		a.iconsPresent = false;
		a.icons[0] = this.imagePath + this.pathPrefix + this.skin
				+ "/active/icon_blank.gif";
		a.icons[1] = this.imagePath + this.pathPrefix + this.skin
				+ "/inactive/icon_blank.gif";
		this._engineUpdateWindowIcon(a, a.icons[a.isOnTop() ? 0 : 1])
	};
	this._restoreWindowIcons = function(a) {
		a.iconsPresent = true;
		a.icons[0] = this.imagePath + this.pathPrefix + this.skin
				+ "/active/icon_normal.gif";
		a.icons[1] = this.imagePath + this.pathPrefix + this.skin
				+ "/inactive/icon_normal.gif";
		this._engineUpdateWindowIcon(a,
				a.icons[a.className == "dhtmlx_window_active" ? 0 : 1])
	};
	this._attachWindowContentTo = function(k, j, a, f) {
		var g = this._engineGetWindowContent(k).parentNode;
		g.parentNode.removeChild(g);
		k.hide();
		g.style.left = "0px";
		g.style.top = "0px";
		g.style.width = (a != null ? a : j.offsetWidth) + "px";
		g.style.height = (f != null ? f : j.offsetHeight) + "px";
		g.style.position = "relative";
		j.appendChild(g);
		this._engineGetWindowContent(k).style.width = g.style.width;
		this._engineGetWindowContent(k).style.height = g.style.height
	};
	this._setWindowToFullScreen = function(h, g) {
		if (g == true) {
			var f = h._content;
			f.parentNode.removeChild(f);
			h.hide();
			h._isFullScreened = true;
			f.style.left = "0px";
			f.style.top = "0px";
			f.style.width = document.body.offsetWidth - (_isIE ? 4 : 0) + "px";
			if (document.body.offsetHeight == 0) {
				if (window.innerHeight) {
					f.style.height = window.innerHeight + "px"
				} else {
					f.style.height = document.body.scrollHeight + "px"
				}
			} else {
				f.style.height = document.body.offsetHeight - (_isIE ? 4 : 0)
						+ "px"
			}
			f.style.position = "absolute";
			document.body.appendChild(f)
		} else {
			if (g == false) {
				var f = h.childNodes[0].childNodes[0].childNodes[1].childNodes[0].childNodes[0].childNodes[0].childNodes[0].childNodes[1];
				var a = h._content;
				document.body.removeChild(a);
				f.appendChild(a);
				h._isFullScreened = false;
				h.setDimension(h.w, h.h);
				h.show();
				h.bringToTop();
				h.center()
			}
		}
		h.updateNestedObjects()
	};
	this._isWindowOnTop = function(f) {
		var a = (this.getTopmostWindow() == f);
		return a
	};
	this._bringOnBottom = function(g) {
		for (var f in this.wins) {
			if (this.wins[f].zi < g.zi) {
				this.wins[f].zi += this.zIndexStep;
				this.wins[f].style.zIndex = this.wins[f].zi
			}
		}
		g.zi = 50;
		g.style.zIndex = g.zi;
		this._makeActive(this.getTopmostWindow())
	};
	this._isWindowOnBottom = function(h) {
		var g = true;
		for (var f in this.wins) {
			if (this.wins[f] != h) {
				g = g && (this.wins[f].zi > h.zi)
			}
		}
		return g
	};
	this._stickWindow = function(a) {
		a._isSticked = true;
		this._hideButton(a, "stick");
		this._showButton(a, "sticked");
		this._bringOnTop(a)
	};
	this._unstickWindow = function(a) {
		a._isSticked = false;
		this._hideButton(a, "sticked");
		this._showButton(a, "stick");
		this._bringOnTopAnyStickedWindows()
	};
	this._addUserButton = function(f, j, h, g) {
		var a = this._engineAddUserButton(f, j, h);
		a.title = g;
		a.isVisible = true;
		a._isEnabled = true;
		a.isPressed = false;
		a.label = j;
		f.btns[j] = a;
		a._doOnClick = function() {
		};
		this._attachEventsOnButton(f, a)
	};
	this._removeUserButton = function(f, a) {
		this._removeButtonGlobal(f, a)
	};
	this._blockSwitcher = function(g) {
		for (var f in this.wins) {
			if (g == true) {
				this.wins[f].showCoverBlocker()
			} else {
				this.wins[f].hideCoverBlocker()
			}
		}
	};
	this.resizingWin = null;
	this.modalWin = null;
	this.resizingDirs = "none";
	if (_isIE) {
		this._focusFixIE = document.createElement("INPUT");
		this._focusFixIE.className = "dhx_windows_ieonclosefocusfix";
		document.body.appendChild(this._focusFixIE)
	}
	this._createViewport();
	this._doOnMouseUp = function() {
		if (d != null) {
			d._stopMove()
		}
	};
	this._doOnMoseMove = function(a) {
		a = a || event;
		if (d != null) {
			d._moveWindow(a)
		}
	};
	this._resizeTM = null;
	this._resizeTMTime = 200;
	this._doOnResize = function() {
		window.clearTimeout(d._resizeTM);
		d._resizeTM = window.setTimeout(function() {
					d._autoResizeViewport()
				}, d._resizeTMTime)
	};
	this._doOnUnload = function() {
		d.unload()
	};
	this._doOnSelectStart = function(a) {
		a = a || event;
		if (d.movingWin != null || d.resizingWin != null) {
			a.returnValue = false
		}
	};
	if (_isIE) {
		document.body.attachEvent("onselectstart", this._doOnSelectStart)
	}
	dhtmlxEvent(window, "resize", this._doOnResize);
	dhtmlxEvent(document.body, "unload", this._doOnUnload);
	dhtmlxEvent(document.body, "mouseup", this._doOnMouseUp);
	dhtmlxEvent(this.vp, "mousemove", this._doOnMoseMove);
	dhtmlxEvent(this.vp, "mouseup", this._doOnMouseUp);
	this._setWindowModal = function(f, a) {
		if (a == true) {
			this._makeActive(f);
			this._bringOnTop(f);
			this.modalWin = f;
			f._isModal = true;
			this.modalCoverI.style.zIndex = f.zi - 2;
			this.modalCoverI.style.display = "";
			this.modalCoverD.style.zIndex = f.zi - 2;
			this.modalCoverD.style.display = ""
		} else {
			this.modalWin = null;
			f._isModal = false;
			this.modalCoverI.style.zIndex = 0;
			this.modalCoverI.style.display = "none";
			this.modalCoverD.style.zIndex = 0;
			this.modalCoverD.style.display = "none"
		}
	};
	this._bringOnTopAnyStickedWindows = function() {
		var h = new Array();
		for (var f in this.wins) {
			if (this.wins[f]._isSticked) {
				h[h.length] = this.wins[f]
			}
		}
		for (var g = 0; g < h.length; g++) {
			this._bringOnTop(h[g])
		}
		if (h.length == 0) {
			for (var f in this.wins) {
				if (this.wins[f].className == "dhtmlx_window_active") {
					this._bringOnTop(this.wins[f])
				}
			}
		}
	};
	this.unload = function() {
		this._clearAll()
	};
	this._removeButtonGlobal = function(g, f) {
		if (!g.btns[f]) {
			return
		}
		var a = g.btns[f];
		a.title = null;
		a.isVisible = null;
		a._isEnabled = null;
		a.isPressed = null;
		a.label = null;
		a._doOnClick = null;
		a.attachEvent = null;
		a.callEvent = null;
		a.checkEvent = null;
		a.detachEvent = null;
		a.disable = null;
		a.enable = null;
		a.eventCatcher = null;
		a.hide = null;
		a.isEnabled = null;
		a.isHidden = null;
		a.show = null;
		a.onmousedown = null;
		a.onmouseout = null;
		a.onmouseover = null;
		a.onmouseup = null;
		if (a.parentNode) {
			a.parentNode.removeChild(a)
		}
		a = null;
		g.btns[f] = null
	};
	this._removeWindowGlobal = function(h) {
		if (this.modalWin == h) {
			this._setWindowModal(h, false)
		}
		var j = h.idd;
		if (h._frame) {
			if (_isIE) {
				try {
					h._frame.onreadystatechange = null;
					h._frame.contentWindow.document.body.onmousedown = null;
					h._frame.onload = null
				} catch (g) {
				}
			} else {
				try {
					h._frame.contentWindow.onmousedown = null;
					h._frame.onload = null
				} catch (g) {
				}
			}
		}
		h.coverBlocker().onselectstart = null;
		h._dhxContDestruct();
		this._engineDiableOnSelectInWindow(h, false);
		this._engineGetWindowHeader(h).onmousedown = null;
		this._engineGetWindowHeader(h).ondblclick = null;
		this.movingWin = null;
		this.resizingWin = null;
		for (var f in h.btns) {
			this._removeButtonGlobal(h, f)
		}
		h.btns = null;
		h._adjustToContent = null;
		h._doOnAttachMenu = null;
		h._doOnAttachStatusBar = null;
		h._doOnAttachToolbar = null;
		h._doOnAttachURL = null;
		h._redraw = null;
		h.addUserButton = null;
		h.allowMove = null;
		h.allowPark = null;
		h.allowResize = null;
		h.attachEvent = null;
		h.bringToBottom = null;
		h.bringToTop = null;
		h.callEvent = null;
		h.center = null;
		h.centerOnScreen = null;
		h.checkEvent = null;
		h.clearIcon = null;
		h.close = null;
		h.denyMove = null;
		h.denyPark = null;
		h.denyResize = null;
		h.detachEvent = null;
		h.eventCatcher = null;
		h.getDimension = null;
		h.getIcon = null;
		h.getId = null;
		h.getMaxDimension = null;
		h.getMinDimension = null;
		h.getPosition = null;
		h.getText = null;
		h.hide = null;
		h.hideHeader = null;
		h.isHidden = null;
		h.isMaximized = null;
		h.isModal = null;
		h.isMovable = null;
		h.isOnBottom = null;
		h.isOnTop = null;
		h.isParkable = null;
		h.isParked = null;
		h.isResizable = null;
		h.isSticked = null;
		h.keepInViewport = null;
		h.maximize = null;
		h.minimize = null;
		h.park = null;
		h.progressOff = null;
		h.progressOn = null;
		h.removeUserButton = null;
		h.restoreIcon = null;
		h.setDimension = null;
		h.setIcon = null;
		h.setMaxDimension = null;
		h.setMinDimension = null;
		h.setModal = null;
		h.setPosition = null;
		h.setText = null;
		h.setToFullScreen = null;
		h.show = null;
		h.showHeader = null;
		h.stick = null;
		h.unstick = null;
		h.onmousemove = null;
		h.onmousedown = null;
		h.icons = null;
		h.button = null;
		h._dhxContDestruct = null;
		h.dhxContGlobal.obj = null;
		h.dhxContGlobal.setContent = null;
		h.dhxContGlobal.dhxcont = null;
		h.dhxContGlobal = null;
		if (h._frame) {
			while (h._frame.childNodes.length > 0) {
				h._frame.removeChild(h._frame.childNodes[0])
			}
			h._frame = null
		}
		this._parseNestedForEvents(h);
		h._content = null;
		h.innerHTML = "";
		h.parentNode.removeChild(h);
		h = null;
		this.wins[j] = null;
		delete this.wins[j];
		j = null
	};
	this._removeEvents = function(a) {
		a.onmouseover = null;
		a.onmouseout = null;
		a.onmousemove = null;
		a.onclick = null;
		a.ondblclick = null;
		a.onmouseenter = null;
		a.onmouseleave = null;
		a.onmouseup = null;
		a.onmousewheel = null;
		a.onmousedown = null;
		a.onselectstart = null;
		a.onfocus = null;
		a.style.display = ""
	};
	this._parseNestedForEvents = function(f) {
		this._removeEvents(f);
		for (var a = 0; a < f.childNodes.length; a++) {
			if (f.childNodes[a].tagName != null) {
				this._parseNestedForEvents(f.childNodes[a])
			}
		}
	};
	this._clearAll = function() {
		this._clearDocumentEvents();
		for (var f in this.wins) {
			this._removeWindowGlobal(this.wins[f])
		}
		this.wins = null;
		this._parseNestedForEvents(this._carcass);
		while (this._carcass.childNodes.length > 0) {
			this._carcass.removeChild(this._carcass.childNodes[0])
		}
		this._carcass.onselectstart = null;
		this._carcass.parentNode.removeChild(this._carcass);
		this._carcass = null;
		this._parseNestedForEvents(this._vpcover);
		this._vpcover.parentNode.removeChild(this._vpcover);
		this._vpcover = null;
		this._parseNestedForEvents(this.modalCoverD);
		this.modalCoverD.parentNode.removeChild(this.modalCoverD);
		this.modalCoverD = null;
		this._parseNestedForEvents(this.modalCoverI);
		this.modalCoverI.parentNode.removeChild(this.modalCoverI);
		this.modalCoverI = null;
		if (this.vp.autocreated == true) {
			this.vp.parentNode.removeChild(this.vp)
		}
		this.vp = null;
		for (var f in this.skinParams) {
			delete this.skinParams[f]
		}
		this.skinParams = null;
		this._effects = null;
		this._engineSkinParams = null;
		wins = null;
		this._addDefaultButtons = null;
		this._addUserButton = null;
		this._allowParking = null;
		this._allowReszieGlob = null;
		this._attachEventsOnButton = null;
		this._attachWindowContentTo = null;
		this._autoResizeViewport = null;
		this._blockSwitcher = null;
		this._bringOnBottom = null;
		this._bringOnTop = null;
		this._bringOnTopAnyStickedWindows = null;
		this._centerWindow = null;
		this._clearAll = null;
		this._clearDocumentEvents = null;
		this._clearWindowIcons = null;
		this._closeWindow = null;
		this._createViewport = null;
		this._denyParking = null;
		this._denyResize = null;
		this._dhx_Engine = null;
		this._disableButton = null;
		this._doOnMoseMove = null;
		this._doOnMouseUp = null;
		this._doOnResize = null;
		this._doOnSelectStart = null;
		this._doOnUnload = null;
		this._doParkDown = null;
		this._doParkUp = null;
		this._enableButton = null;
		this._engineAddUserButton = null;
		this._engineAdjustWindowToContent = null;
		this._engineAllowWindowResize = null;
		this._engineCheckHeaderMouseDown = null;
		this._engineDiableOnSelectInWindow = null;
		this._engineDoOnWindowParkDown = null;
		this._engineDoOnWindowParkUp = null;
		this._engineFixWindowPosInViewport = null;
		this._engineGetWindowButton = null;
		this._engineGetWindowContent = null;
		this._engineGetWindowHeader = null;
		this._engineGetWindowHeaderState = null;
		this._engineGetWindowLabel = null;
		this._engineGetWindowParkedHeight = null;
		this._engineRedrawSkin = null;
		this._engineRedrawWindowPos = null;
		this._engineRedrawWindowSize = null;
		this._engineRedrawWindowTitle = null;
		this._engineSetWindowBody = null;
		this._engineSwitchWindowHeader = null;
		this._engineSwitchWindowProgress = null;
		this._engineUpdateWindowIcon = null;
		this._fixWindowDimensionInViewport = null;
		this._genStr = null;
		this._getActive = null;
		this._getTopZIndex = null;
		this._getWindowIcon = null;
		this._hideButton = null;
		this._hideWindow = null;
		this._isWindowHidden = null;
		this._isWindowOnBottom = null;
		this._isWindowOnTop = null;
		this._makeActive = null;
		this._maximizeWindow = null;
		this._moveWindow = null;
		this._needHelp = null;
		this._parkWindow = null;
		this._parseNestedForEvents = null;
		this._removeButtonGlobal = null;
		this._removeEvents = null;
		this._removeUserButton = null;
		this._removeWindowGlobal = null;
		this._restoreWindow = null;
		this._restoreWindowIcons = null;
		this._setWindowIcon = null;
		this._setWindowModal = null;
		this._setWindowToFullScreen = null;
		this._showButton = null;
		this._showWindow = null;
		this._stickWindow = null;
		this._stopMove = null;
		this._unstickWindow = null;
		this.attachEvent = null;
		this.attachViewportTo = null;
		this.callEvent = null;
		this.checkEvent = null;
		this.createWindow = null;
		this.detachEvent = null;
		this.enableAutoViewport = null;
		this.eventCatcher = null;
		this.findByText = null;
		this.forEachWindow = null;
		this.getBottommostWindow = null;
		this.getEffect = null;
		this.getTopmostWindow = null;
		this.isWindow = null;
		this.setEffect = null;
		this.setImagePath = null;
		this.setSkin = null;
		this.setViewport = null;
		this.unload = null;
		this.window = null;
		d = null
	};
	this._clearDocumentEvents = function() {
		if (_isIE) {
			window.detachEvent("onresize", this._doOnResize);
			document.body.detachEvent("onselectstart", this._doOnSelectStart);
			document.body.detachEvent("onmouseup", this._doOnMouseUp);
			document.body.detachEvent("onunload", this._doOnUnload);
			this.vp.detachEvent("onmousemove", this._doOnMoseMove);
			this.vp.detachEvent("onmouseup", this._doOnMouseUp)
		} else {
			window.removeEventListener("resize", this._doOnResize, false);
			document.body.removeEventListener("mouseup", this._doOnMouseUp,
					false);
			document.body
					.removeEventListener("unload", this._doOnUnload, false);
			this.vp.removeEventListener("mousemove", this._doOnMoseMove, false);
			this.vp.removeEventListener("mouseup", this._doOnMouseUp, false)
		}
	};
	this._genStr = function(a) {
		var f = "";
		var h = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		for (var g = 0; g < a; g++) {
			f = f + h.charAt(Math.round(Math.random() * h.length))
		}
		return f
	};
	dhtmlxEventable(this);
	return this
}
dhtmlXWindows.prototype._dhx_Engine = function() {
	this._engineEnabled = true;
	this._engineName = "dhx";
	this._engineSkinParams = {
		dhx_blue : {
			hh : 21,
			lbw : 2,
			rbw : 2,
			lch : 2,
			lcw : 14,
			rch : 14,
			rcw : 14,
			bbh : 2,
			mnh : 23,
			tbh : 25,
			sbh : 20,
			noh_t : null,
			noh_h : null
		},
		dhx_black : {
			hh : 21,
			lbw : 2,
			rbw : 2,
			lch : 2,
			lcw : 14,
			rch : 14,
			rcw : 14,
			bbh : 2,
			mnh : 23,
			tbh : 25,
			sbh : 20,
			noh_t : null,
			noh_h : null
		},
		dhx_skyblue : {
			hh : 29,
			lbw : 2,
			rbw : 2,
			lch : 2,
			lcw : 14,
			rch : 14,
			rcw : 14,
			bbh : 2,
			mnh : 23,
			tbh : 25,
			sbh : 20,
			noh_t : 5,
			noh_h : -10
		}
	};
	this._engineSetWindowBody = function(a) {
		a.innerHTML = "<div iswin='1' class='dhtmlx_wins_body_outer'>"
				+ (_isIE
						? "<iframe frameborder='0' id='__dhtmlxwindowsiframe__' class='dhtmlx_wins_ie6_cover_fix' onload='this.contentWindow.document.body.style.overflow=\"hidden\";'></iframe>"
						: "")
				+ "<div class='dhtmlx_wins_icon'></div><div class='dhtmlx_wins_progress'></div><div class='dhtmlx_wins_title'>dhtmlxWindow</div><div class='dhtmlx_wins_btns'><div class='dhtmlx_wins_btns_button dhtmlx_button_dock_default'></div><div class='dhtmlx_wins_btns_button dhtmlx_button_close_default'></div><div class='dhtmlx_wins_btns_button dhtmlx_button_minmax1_default'></div><div class='dhtmlx_wins_btns_button dhtmlx_button_minmax2_default'></div><div class='dhtmlx_wins_btns_button dhtmlx_button_park_default'></div><div class='dhtmlx_wins_btns_button dhtmlx_button_help_default'></div><div class='dhtmlx_wins_btns_button dhtmlx_button_stick_default'></div><div class='dhtmlx_wins_btns_button dhtmlx_button_sticked_default'></div></div><div class='dhtmlx_wins_body_inner'></div><div winResT='yes' class='dhtmlx_wins_resizer_t' style='display:none;'></div><div winResL='yes' class='dhtmlx_wins_resizer_l'></div><div winResR='yes' class='dhtmlx_wins_resizer_r'></div><div winResB='yes' class='dhtmlx_wins_resizer_b'></div><div class='white_line'></div><div class='white_line2'></div></div>";
		a.dhxContGlobal = new dhtmlXContainer(a);
		if (this.skin == "dhx_skyblue") {
			a.dhxContGlobal.obj._offsetWidth = -10;
			a.dhxContGlobal.obj._offsetHeight = -5;
			a.dhxContGlobal.obj._offsetLeft = 5;
			a.dhxContGlobal.obj._offsetHeightSaved = a.dhxContGlobal.obj._offsetHeight
		}
		a.skin = this.skin;
		a.dhxContGlobal.setContent(a.childNodes[0].childNodes[(_isIE ? 5 : 4)]);
		a.coverBlocker().onselectstart = function(c) {
			c = c || event;
			c.returnValue = false;
			c.cancelBubble = true
		}
	};
	this._engineDiableOnSelectInWindow = function(e, d) {
		var c = new Array();
		c[0] = e.childNodes[0].childNodes[(_isIE ? 1 : 0)];
		c[1] = e.childNodes[0].childNodes[(_isIE ? 2 : 1)];
		c[2] = e.childNodes[0].childNodes[(_isIE ? 3 : 2)];
		c[3] = e.childNodes[0].childNodes[(_isIE ? 4 : 3)];
		c[4] = e.childNodes[0].childNodes[(_isIE ? 6 : 5)];
		c[5] = e.childNodes[0].childNodes[(_isIE ? 7 : 6)];
		c[6] = e.childNodes[0].childNodes[(_isIE ? 8 : 7)];
		c[7] = e.childNodes[0].childNodes[(_isIE ? 9 : 8)];
		for (var a = 0; a < c.length; a++) {
			c[a].onselectstart = (d ? function(f) {
				f = f || event;
				f.returnValue = false;
				return false
			} : null)
		}
	};
	this._engineGetWindowHeader = function(a) {
		a.childNodes[0].idd = a.idd;
		return a.childNodes[0]
	};
	this._engineRedrawWindowSize = function(e) {
		e.style.width = (String(e.w).search("%") == -1 ? e.w + "px" : e.w);
		e.style.height = (String(e.h).search("%") == -1 ? e.h + "px" : e.h);
		var a = e.childNodes[0];
		a.style.width = e.clientWidth + "px";
		a.style.height = e.clientHeight + "px";
		if (a.offsetWidth > e.clientWidth) {
			a.style.width = e.clientWidth * 2 - a.offsetWidth + "px"
		}
		if (a.offsetHeight > e.clientHeight) {
			var d = e.clientHeight * 2 - a.offsetHeight;
			if (d < 0) {
				d = 0
			}
			a.style.height = d + "px"
		}
		var c = (e._noHeader == true
				? e._hdrSize
				: this._engineSkinParams[this.skin]["hh"]);
		this._engineRedrawWindowTitle(e);
		e.adjustContent(a, c)
	};
	this._engineRedrawWindowPos = function(a) {
		if (a._isFullScreened) {
			return
		}
		a.style.left = a.x + "px";
		a.style.top = a.y + "px"
	};
	this._engineFixWindowPosInViewport = function(c) {
		var a = (c._noHeader == true
				? c._hdrSize
				: this._engineSkinParams[this.skin]["hh"]);
		if (c._keepInViewport) {
			if (c.x < 0) {
				c.x = 0
			}
			if (c.x + c.w > this.vp.offsetWidth) {
				c.x = this.vp.offsetWidth - c.w
			}
			if (c.y + c.h > this.vp.offsetHeight) {
				c.y = this.vp.offsetHeight - c.h
			}
			if (c.y < 0) {
				c.y = 0
			}
		} else {
			if (c.y + a > this.vp.offsetHeight) {
				c.y = this.vp.offsetHeight - a
			}
			if (c.y < 0) {
				c.y = 0
			}
			if (c.x + c.w - 10 < 0) {
				c.x = 10 - c.w
			}
			if (c.x > this.vp.offsetWidth - 10) {
				c.x = this.vp.offsetWidth - 10
			}
		}
	};
	this._engineCheckHeaderMouseDown = function(f, d) {
		var a = (_isIE || _isOpera ? d.offsetX : d.layerX);
		var g = (_isIE || _isOpera ? d.offsetY : d.layerY);
		var e = d.target || d.srcElement;
		var c = (f._noHeader == true
				? f._hdrSize
				: this._engineSkinParams[this.skin]["hh"]);
		if (e.className == "dhtmlx_wins_icon"
				|| e.className == "dhtmlx_wins_title") {
			return false
		}
		if (g <= c
				&& (e == f.childNodes[0]
						|| e == f.childNodes[0].childNodes[(_isIE ? 1 : 0)]
						|| e == f.childNodes[0].childNodes[(_isIE ? 3 : 2)] || e == f.childNodes[0].childNodes[(_isIE
						? 4
						: 3)])) {
			return true
		}
		return false
	};
	this._engineGetWindowContent = function(a) {
		alert("_engineGetWindowContent")
	};
	this._engineGetWindowButton = function(e, a) {
		var c = null;
		var g = "dhtmlx_button_" + String(a).toLowerCase() + "_";
		for (var d = 0; d < e.childNodes[0].childNodes[(_isIE ? 4 : 3)].childNodes.length; d++) {
			var f = e.childNodes[0].childNodes[(_isIE ? 4 : 3)].childNodes[d];
			if (String(f.className).search(g) != -1) {
				c = f
			}
		}
		return c
	};
	this._engineAddUserButton = function(f, a, e) {
		if (isNaN(e)) {
			e = 0
		}
		var d = document.createElement("DIV");
		d.className = "dhtmlx_wins_btns_button dhtmlx_button_" + a + "_default";
		var c = f.childNodes[0].childNodes[(_isIE ? 4 : 3)];
		e = c.childNodes.length - e;
		if (e < 0) {
			e = 0
		}
		if (e >= c.childNodes.length) {
			c.appendChild(d)
		} else {
			c.insertBefore(d, c.childNodes[e])
		}
		this._engineRedrawWindowTitle(f);
		return d
	};
	this._engineGetWindowParkedHeight = function(a) {
		return this._engineSkinParams[this.skin]["hh"] + 1
	};
	this._engineDoOnWindowParkDown = function(a) {
		a.childNodes[0].childNodes[(_isIE ? 6 : 5)].style.display = (a._noHeader == true
				? ""
				: "none");
		a.childNodes[0].childNodes[(_isIE ? 7 : 6)].style.display = "";
		a.childNodes[0].childNodes[(_isIE ? 8 : 7)].style.display = "";
		a.childNodes[0].childNodes[(_isIE ? 9 : 8)].style.display = ""
	};
	this._engineDoOnWindowParkUp = function(a) {
		a.childNodes[0].childNodes[(_isIE ? 6 : 5)].style.display = "none";
		a.childNodes[0].childNodes[(_isIE ? 7 : 6)].style.display = "none";
		a.childNodes[0].childNodes[(_isIE ? 8 : 7)].style.display = "none";
		a.childNodes[0].childNodes[(_isIE ? 9 : 8)].style.display = "none"
	};
	this._engineUpdateWindowIcon = function(c, a) {
		c.childNodes[0].childNodes[(_isIE ? 1 : 0)].style.backgroundImage = "url('"
				+ a + "')"
	};
	this._engineAllowWindowResize = function(g, f, e, c) {
		if (!f.getAttribute) {
			return
		}
		var a = this._engineSkinParams[this.skin];
		var d = (g._noHeader == true
				? g._hdrSize
				: this._engineSkinParams[this.skin]["hh"]);
		if (f.getAttribute("winResL") != null) {
			if (f.getAttribute("winResL") == "yes") {
				if (c >= d) {
					if (c >= g.h - a.lch) {
						return "corner_left"
					}
					if (c <= a.lch && g._noHeader == true) {
						return "corner_up_left"
					}
					return "border_left"
				}
			}
		}
		if (f.getAttribute("winResR") != null) {
			if (f.getAttribute("winResR") == "yes") {
				if (c >= d) {
					if (c >= g.h - a.rch) {
						return "corner_right"
					}
					if (c <= a.rch && g._noHeader == true) {
						return "corner_up_right"
					}
					return "border_right"
				}
			}
		}
		if (f.getAttribute("winResT") != null) {
			if (f.getAttribute("winResT") == "yes" && g._noHeader == true) {
				if (e <= a.lcw) {
					return "corner_up_left"
				}
				if (e >= g.w - a.rcw) {
					return "corner_up_right"
				}
				return "border_top"
			}
		}
		if (f.getAttribute("winResB") != null) {
			if (f.getAttribute("winResB") == "yes") {
				if (e <= a.lcw) {
					return "corner_left"
				}
				if (e >= g.w - a.rcw) {
					return "corner_right"
				}
				return "border_bottom"
			}
		}
		return null
	};
	this._engineAdjustWindowToContent = function(e, a, d) {
		var f = a + e.w - e.dhxcont.clientWidth;
		var c = d + e.h - e.dhxcont.clientHeight;
		e.setDimension(f, c)
	};
	this._engineRedrawSkin = function() {
		this.vp.className = "dhtmlx_winviewport dhtmlx_skin_" + this.skin
				+ (this._r ? " dhx_wins_rtl" : "");
		var d = this._engineSkinParams[this.skin];
		for (var c in this.wins) {
			if (this.skin == "dhx_skyblue") {
				this.wins[c].dhxContGlobal.obj._offsetTop = (this.wins[c]._noHeader
						? d.noh_t
						: null);
				this.wins[c].dhxContGlobal.obj._offsetWidth = -10;
				this.wins[c].dhxContGlobal.obj._offsetHeight = (this.wins[c]._noHeader
						? d.noh_h
						: -5);
				this.wins[c].dhxContGlobal.obj._offsetLeft = 5;
				this.wins[c].dhxContGlobal.obj._offsetHeightSaved = -5
			} else {
				this.wins[c].dhxContGlobal.obj._offsetWidth = null;
				this.wins[c].dhxContGlobal.obj._offsetHeight = null;
				this.wins[c].dhxContGlobal.obj._offsetLeft = null;
				this.wins[c].dhxContGlobal.obj._offsetTop = null;
				this.wins[c].dhxContGlobal.obj._offsetHeightSaved = null
			}
			this.wins[c].skin = this.skin;
			this._restoreWindowIcons(this.wins[c]);
			this._engineRedrawWindowSize(this.wins[c])
		}
	};
	this._engineSwitchWindowProgress = function(c, a) {
		if (a == true) {
			c.childNodes[0].childNodes[(_isIE ? 1 : 0)].style.display = "none";
			c.childNodes[0].childNodes[(_isIE ? 2 : 1)].style.display = ""
		} else {
			c.childNodes[0].childNodes[(_isIE ? 2 : 1)].style.display = "none";
			c.childNodes[0].childNodes[(_isIE ? 1 : 0)].style.display = ""
		}
	};
	this._engineSwitchWindowHeader = function(d, c) {
		if (!d._noHeader) {
			d._noHeader = false
		}
		if (c != d._noHeader) {
			return
		}
		d._noHeader = (c == true ? false : true);
		d._hdrSize = 0;
		d.childNodes[0].childNodes[(_isIE ? 5 : 4)].className = "dhtmlx_wins_body_inner"
				+ (d._noHeader ? " dhtmlx_wins_no_header" : "");
		d.childNodes[0].childNodes[(_isIE ? 6 : 5)].style.display = (d._noHeader
				? ""
				: "none");
		d.childNodes[0].childNodes[(_isIE ? 1 : 0)].style.display = (d._noHeader
				? "none"
				: "");
		d.childNodes[0].childNodes[(_isIE ? 3 : 2)].style.display = (d._noHeader
				? "none"
				: "");
		d.childNodes[0].childNodes[(_isIE ? 4 : 3)].style.display = (d._noHeader
				? "none"
				: "");
		var a = this._engineSkinParams[this.skin];
		if (d._noHeader) {
			d.dhxContGlobal.obj._offsetHeightSaved = d.dhxContGlobal.obj._offsetHeight;
			d.dhxContGlobal.obj._offsetHeight = a.noh_h;
			d.dhxContGlobal.obj._offsetTop = a.noh_t
		} else {
			d.dhxContGlobal.obj._offsetHeight = d.dhxContGlobal.obj._offsetHeightSaved;
			d.dhxContGlobal.obj._offsetTop = null
		}
		this._engineRedrawWindowSize(d)
	};
	this._engineGetWindowHeaderState = function(a) {
		return (a._noHeader ? true : false)
	};
	this._engineGetWindowLabel = function(a) {
		return a.childNodes[0].childNodes[(_isIE ? 3 : 2)]
	};
	this._engineRedrawWindowTitle = function(d) {
		if (d._noHeader !== true) {
			var c = d.childNodes[0].childNodes[(_isIE ? 1 : 0)].offsetWidth;
			var a = d.childNodes[0].childNodes[(_isIE ? 4 : 3)].offsetWidth;
			var e = d.offsetWidth - c - a - 24;
			if (e < 0) {
				e = "100%"
			} else {
				e += "px"
			}
			d.childNodes[0].childNodes[(_isIE ? 3 : 2)].style.width = e
		}
	}
};
(function() {
	dhtmlx.extend_api("dhtmlXWindows", {
				_init : function(c) {
					return []
				},
				_patch : function(c) {
					c.old_createWindow = c.createWindow;
					c.createWindow = function(f) {
						if (arguments.length > 1) {
							return this.old_createWindow.apply(this, arguments)
						}
						var e = this.old_createWindow(f.id, f.x, f.y, f.width,
								f.height);
						e.allowMoveA = function(g) {
							if (g) {
								this.allowMove()
							} else {
								this.denyMove()
							}
						};
						e.allowParkA = function(g) {
							if (g) {
								this.allowPark()
							} else {
								this.denyPark()
							}
						};
						e.allowResizeA = function(g) {
							if (g) {
								this.allowResize()
							} else {
								this.denyResize()
							}
						};
						for (var d in f) {
							if (a[d]) {
								e[a[d]](f[d])
							} else {
								if (d.indexOf("on") == 0) {
									e.attachEvent(d, f[d])
								}
							}
						}
						return e
					}
				},
				animation : "setEffect",
				image_path : "setImagePath",
				skin : "setSkin",
				viewport : "_viewport",
				wins : "_wins"
			}, {
				_viewport : function(c) {
					if (c.object) {
						this.enableAutoViewport(false);
						this.attachViewportTo(c.object)
					} else {
						this.enableAutoViewport(false);
						this.setViewport(c.left, c.top, c.width, c.height,
								c.parent)
					}
				},
				_wins : function(c) {
					for (var d = 0; d < c.length; d++) {
						var e = c[d];
						this.createWindow(e.id, e.left, e.top, e.width,
								e.height);
						if (e.text) {
							this.window(e.id).setText(e.text)
						}
						if (e.keep_in_viewport) {
							this.window(e.id).keepInViewport(true)
						}
						if (e.deny_resize) {
							this.window(e.id).denyResize()
						}
						if (e.deny_park) {
							this.window(e.id).denyPark()
						}
						if (e.deny_move) {
							this.window(e.id).denyMove()
						}
					}
				}
			});
	var a = {
		move : "allowMoveA",
		park : "allowParkA",
		resize : "allowResizeA",
		center : "center",
		modal : "setModal",
		caption : "setText",
		header : "showHeader"
	}
})();
jQuery.cookie = function(e, f, c) {
	if (arguments.length > 1 && (f === null || typeof f !== "object")) {
		c = jQuery.extend({}, c);
		if (f === null) {
			c.expires = -1
		}
		if (typeof c.expires === "number") {
			var h = c.expires, d = c.expires = new Date();
			d.setDate(d.getDate() + h)
		}
		return (document.cookie = [encodeURIComponent(e), "=",
				c.raw ? String(f) : encodeURIComponent(String(f)),
				c.expires ? "; expires=" + c.expires.toUTCString() : "",
				c.path ? "; path=" + c.path : "",
				c.domain ? "; domain=" + c.domain : "",
				c.secure ? "; secure" : ""].join(""))
	}
	c = f || {};
	var a, g = c.raw ? function(j) {
		return j
	} : decodeURIComponent;
	return (a = new RegExp("(?:^|; )" + encodeURIComponent(e) + "=([^;]*)")
			.exec(document.cookie)) ? g(a[1]) : null
};
/*
 * ! jQuery UI 1.8.16
 * 
 * Copyright 2011, AUTHORS.txt (http://jqueryui.com/about) Dual licensed under
 * the MIT or GPL Version 2 licenses. http://jquery.org/license
 * 
 * http://docs.jquery.com/UI
 */
(function(a, e) {
	a.ui = a.ui || {};
	if (a.ui.version) {
		return
	}
	a.extend(a.ui, {
				version : "1.8.16",
				keyCode : {
					ALT : 18,
					BACKSPACE : 8,
					CAPS_LOCK : 20,
					COMMA : 188,
					COMMAND : 91,
					COMMAND_LEFT : 91,
					COMMAND_RIGHT : 93,
					CONTROL : 17,
					DELETE : 46,
					DOWN : 40,
					END : 35,
					ENTER : 13,
					ESCAPE : 27,
					HOME : 36,
					INSERT : 45,
					LEFT : 37,
					MENU : 93,
					NUMPAD_ADD : 107,
					NUMPAD_DECIMAL : 110,
					NUMPAD_DIVIDE : 111,
					NUMPAD_ENTER : 108,
					NUMPAD_MULTIPLY : 106,
					NUMPAD_SUBTRACT : 109,
					PAGE_DOWN : 34,
					PAGE_UP : 33,
					PERIOD : 190,
					RIGHT : 39,
					SHIFT : 16,
					SPACE : 32,
					TAB : 9,
					UP : 38,
					WINDOWS : 91
				}
			});
	a.fn.extend({
				propAttr : a.fn.prop || a.fn.attr,
				_focus : a.fn.focus,
				focus : function(f, g) {
					return typeof f === "number" ? this.each(function() {
								var h = this;
								setTimeout(function() {
											a(h).focus();
											if (g) {
												g.call(h)
											}
										}, f)
							}) : this._focus.apply(this, arguments)
				},
				scrollParent : function() {
					var f;
					if ((a.browser.msie && (/(static|relative)/).test(this
							.css("position")))
							|| (/absolute/).test(this.css("position"))) {
						f = this.parents().filter(function() {
							return (/(relative|absolute|fixed)/).test(a.curCSS(
									this, "position", 1))
									&& (/(auto|scroll)/).test(a.curCSS(this,
											"overflow", 1)
											+ a.curCSS(this, "overflow-y", 1)
											+ a.curCSS(this, "overflow-x", 1))
						}).eq(0)
					} else {
						f = this.parents().filter(function() {
							return (/(auto|scroll)/).test(a.curCSS(this,
									"overflow", 1)
									+ a.curCSS(this, "overflow-y", 1)
									+ a.curCSS(this, "overflow-x", 1))
						}).eq(0)
					}
					return (/fixed/).test(this.css("position")) || !f.length
							? a(document)
							: f
				},
				zIndex : function(j) {
					if (j !== e) {
						return this.css("zIndex", j)
					}
					if (this.length) {
						var g = a(this[0]), f, h;
						while (g.length && g[0] !== document) {
							f = g.css("position");
							if (f === "absolute" || f === "relative"
									|| f === "fixed") {
								h = parseInt(g.css("zIndex"), 10);
								if (!isNaN(h) && h !== 0) {
									return h
								}
							}
							g = g.parent()
						}
					}
					return 0
				},
				disableSelection : function() {
					return this.bind((a.support.selectstart
									? "selectstart"
									: "mousedown")
									+ ".ui-disableSelection", function(f) {
								f.preventDefault()
							})
				},
				enableSelection : function() {
					return this.unbind(".ui-disableSelection")
				}
			});
	a.each(["Width", "Height"], function(h, f) {
				var g = f === "Width" ? ["Left", "Right"] : ["Top", "Bottom"], j = f
						.toLowerCase(), l = {
					innerWidth : a.fn.innerWidth,
					innerHeight : a.fn.innerHeight,
					outerWidth : a.fn.outerWidth,
					outerHeight : a.fn.outerHeight
				};
				function k(o, n, m, p) {
					a.each(g, function() {
								n -= parseFloat(a.curCSS(o, "padding" + this,
										true))
										|| 0;
								if (m) {
									n -= parseFloat(a.curCSS(o, "border" + this
													+ "Width", true))
											|| 0
								}
								if (p) {
									n -= parseFloat(a.curCSS(o,
											"margin" + this, true))
											|| 0
								}
							});
					return n
				}
				a.fn["inner" + f] = function(m) {
					if (m === e) {
						return l["inner" + f].call(this)
					}
					return this.each(function() {
								a(this).css(j, k(this, m) + "px")
							})
				};
				a.fn["outer" + f] = function(m, n) {
					if (typeof m !== "number") {
						return l["outer" + f].call(this, m)
					}
					return this.each(function() {
								a(this).css(j, k(this, m, true, n) + "px")
							})
				}
			});
	function d(h, f) {
		var l = h.nodeName.toLowerCase();
		if ("area" === l) {
			var k = h.parentNode, j = k.name, g;
			if (!h.href || !j || k.nodeName.toLowerCase() !== "map") {
				return false
			}
			g = a("img[usemap=#" + j + "]")[0];
			return !!g && c(g)
		}
		return (/input|select|textarea|button|object/.test(l)
				? !h.disabled
				: "a" == l ? h.href || f : f)
				&& c(h)
	}
	function c(f) {
		return !a(f).parents().andSelf().filter(function() {
			return a.curCSS(this, "visibility") === "hidden"
					|| a.expr.filters.hidden(this)
		}).length
	}
	a.extend(a.expr[":"], {
				data : function(h, g, f) {
					return !!a.data(h, f[3])
				},
				focusable : function(f) {
					return d(f, !isNaN(a.attr(f, "tabindex")))
				},
				tabbable : function(h) {
					var f = a.attr(h, "tabindex"), g = isNaN(f);
					return (g || f >= 0) && d(h, !g)
				}
			});
	a(function() {
				var f = document.body, g = f.appendChild(g = document
						.createElement("div"));
				a.extend(g.style, {
							minHeight : "100px",
							height : "auto",
							padding : 0,
							borderWidth : 0
						});
				a.support.minHeight = g.offsetHeight === 100;
				a.support.selectstart = "onselectstart" in g;
				f.removeChild(g).style.display = "none"
			});
	a.extend(a.ui, {
		plugin : {
			add : function(g, h, k) {
				var j = a.ui[g].prototype;
				for (var f in k) {
					j.plugins[f] = j.plugins[f] || [];
					j.plugins[f].push([h, k[f]])
				}
			},
			call : function(f, h, g) {
				var k = f.plugins[h];
				if (!k || !f.element[0].parentNode) {
					return
				}
				for (var j = 0; j < k.length; j++) {
					if (f.options[k[j][0]]) {
						k[j][1].apply(f.element, g)
					}
				}
			}
		},
		contains : function(g, f) {
			return document.compareDocumentPosition ? g
					.compareDocumentPosition(f)
					& 16 : g !== f && g.contains(f)
		},
		hasScroll : function(j, g) {
			if (a(j).css("overflow") === "hidden") {
				return false
			}
			var f = (g && g === "left") ? "scrollLeft" : "scrollTop", h = false;
			if (j[f] > 0) {
				return true
			}
			j[f] = 1;
			h = (j[f] > 0);
			j[f] = 0;
			return h
		},
		isOverAxis : function(g, f, h) {
			return (g > f) && (g < (f + h))
		},
		isOver : function(l, g, k, j, f, h) {
			return a.ui.isOverAxis(l, k, f) && a.ui.isOverAxis(g, j, h)
		}
	})
})(jQuery);
/*
 * ! jQuery UI Widget 1.8.16
 * 
 * Copyright 2011, AUTHORS.txt (http://jqueryui.com/about) Dual licensed under
 * the MIT or GPL Version 2 licenses. http://jquery.org/license
 * 
 * http://docs.jquery.com/UI/Widget
 */
(function(c, e) {
	if (c.cleanData) {
		var d = c.cleanData;
		c.cleanData = function(f) {
			for (var g = 0, h; (h = f[g]) != null; g++) {
				try {
					c(h).triggerHandler("remove")
				} catch (j) {
				}
			}
			d(f)
		}
	} else {
		var a = c.fn.remove;
		c.fn.remove = function(f, g) {
			return this.each(function() {
						if (!g) {
							if (!f || c.filter(f, [this]).length) {
								c("*", this).add([this]).each(function() {
											try {
												c(this)
														.triggerHandler("remove")
											} catch (h) {
											}
										})
							}
						}
						return a.call(c(this), f, g)
					})
		}
	}
	c.widget = function(g, j, f) {
		var h = g.split(".")[0], l;
		g = g.split(".")[1];
		l = h + "-" + g;
		if (!f) {
			f = j;
			j = c.Widget
		}
		c.expr[":"][l] = function(m) {
			return !!c.data(m, g)
		};
		c[h] = c[h] || {};
		c[h][g] = function(m, n) {
			if (arguments.length) {
				this._createWidget(m, n)
			}
		};
		var k = new j();
		k.options = c.extend(true, {}, k.options);
		c[h][g].prototype = c.extend(true, k, {
					namespace : h,
					widgetName : g,
					widgetEventPrefix : c[h][g].prototype.widgetEventPrefix
							|| g,
					widgetBaseClass : l
				}, f);
		c.widget.bridge(g, c[h][g])
	};
	c.widget.bridge = function(g, f) {
		c.fn[g] = function(k) {
			var h = typeof k === "string", j = Array.prototype.slice.call(
					arguments, 1), l = this;
			k = !h && j.length ? c.extend.apply(null, [true, k].concat(j)) : k;
			if (h && k.charAt(0) === "_") {
				return l
			}
			if (h) {
				this.each(function() {
							var m = c.data(this, g), n = m
									&& c.isFunction(m[k])
									? m[k].apply(m, j)
									: m;
							if (n !== m && n !== e) {
								l = n;
								return false
							}
						})
			} else {
				this.each(function() {
							var m = c.data(this, g);
							if (m) {
								m.option(k || {})._init()
							} else {
								c.data(this, g, new f(k, this))
							}
						})
			}
			return l
		}
	};
	c.Widget = function(f, g) {
		if (arguments.length) {
			this._createWidget(f, g)
		}
	};
	c.Widget.prototype = {
		widgetName : "widget",
		widgetEventPrefix : "",
		options : {
			disabled : false
		},
		_createWidget : function(g, h) {
			c.data(h, this.widgetName, this);
			this.element = c(h);
			this.options = c.extend(true, {}, this.options, this
							._getCreateOptions(), g);
			var f = this;
			this.element.bind("remove." + this.widgetName, function() {
						f.destroy()
					});
			this._create();
			this._trigger("create");
			this._init()
		},
		_getCreateOptions : function() {
			return c.metadata
					&& c.metadata.get(this.element[0])[this.widgetName]
		},
		_create : function() {
		},
		_init : function() {
		},
		destroy : function() {
			this.element.unbind("." + this.widgetName)
					.removeData(this.widgetName);
			this.widget().unbind("." + this.widgetName)
					.removeAttr("aria-disabled")
					.removeClass(this.widgetBaseClass
							+ "-disabled ui-state-disabled")
		},
		widget : function() {
			return this.element
		},
		option : function(g, h) {
			var f = g;
			if (arguments.length === 0) {
				return c.extend({}, this.options)
			}
			if (typeof g === "string") {
				if (h === e) {
					return this.options[g]
				}
				f = {};
				f[g] = h
			}
			this._setOptions(f);
			return this
		},
		_setOptions : function(g) {
			var f = this;
			c.each(g, function(h, j) {
						f._setOption(h, j)
					});
			return this
		},
		_setOption : function(f, g) {
			this.options[f] = g;
			if (f === "disabled") {
				this.widget()[g ? "addClass" : "removeClass"](this.widgetBaseClass
						+ "-disabled ui-state-disabled").attr("aria-disabled",
						g)
			}
			return this
		},
		enable : function() {
			return this._setOption("disabled", false)
		},
		disable : function() {
			return this._setOption("disabled", true)
		},
		_trigger : function(g, h, j) {
			var l = this.options[g];
			h = c.Event(h);
			h.type = (g === this.widgetEventPrefix ? g : this.widgetEventPrefix
					+ g).toLowerCase();
			j = j || {};
			if (h.originalEvent) {
				for (var f = c.event.props.length, k; f;) {
					k = c.event.props[--f];
					h[k] = h.originalEvent[k]
				}
			}
			this.element.trigger(h, j);
			return !(c.isFunction(l) && l.call(this.element[0], h, j) === false || h
					.isDefaultPrevented())
		}
	}
})(jQuery);
/*
 * ! jQuery UI Mouse 1.8.16
 * 
 * Copyright 2011, AUTHORS.txt (http://jqueryui.com/about) Dual licensed under
 * the MIT or GPL Version 2 licenses. http://jquery.org/license
 * 
 * http://docs.jquery.com/UI/Mouse
 * 
 * Depends: jquery.ui.widget.js
 */
(function(c, d) {
	var a = false;
	c(document).mouseup(function(f) {
				a = false
			});
	c.widget("ui.mouse", {
		options : {
			cancel : ":input,option",
			distance : 1,
			delay : 0
		},
		_mouseInit : function() {
			var e = this;
			this.element.bind("mousedown." + this.widgetName, function(f) {
						return e._mouseDown(f)
					}).bind("click." + this.widgetName, function(f) {
				if (true === c.data(f.target, e.widgetName
								+ ".preventClickEvent")) {
					c.removeData(f.target, e.widgetName + ".preventClickEvent");
					f.stopImmediatePropagation();
					return false
				}
			});
			this.started = false
		},
		_mouseDestroy : function() {
			this.element.unbind("." + this.widgetName)
		},
		_mouseDown : function(g) {
			if (a) {
				return
			}
			(this._mouseStarted && this._mouseUp(g));
			this._mouseDownEvent = g;
			var f = this, h = (g.which == 1), e = (typeof this.options.cancel == "string"
					&& g.target.nodeName ? c(g.target)
					.closest(this.options.cancel).length : false);
			if (!h || e || !this._mouseCapture(g)) {
				return true
			}
			this.mouseDelayMet = !this.options.delay;
			if (!this.mouseDelayMet) {
				this._mouseDelayTimer = setTimeout(function() {
							f.mouseDelayMet = true
						}, this.options.delay)
			}
			if (this._mouseDistanceMet(g) && this._mouseDelayMet(g)) {
				this._mouseStarted = (this._mouseStart(g) !== false);
				if (!this._mouseStarted) {
					g.preventDefault();
					return true
				}
			}
			if (true === c.data(g.target, this.widgetName
							+ ".preventClickEvent")) {
				c.removeData(g.target, this.widgetName + ".preventClickEvent")
			}
			this._mouseMoveDelegate = function(j) {
				return f._mouseMove(j)
			};
			this._mouseUpDelegate = function(j) {
				return f._mouseUp(j)
			};
			c(document).bind("mousemove." + this.widgetName,
					this._mouseMoveDelegate).bind("mouseup." + this.widgetName,
					this._mouseUpDelegate);
			g.preventDefault();
			a = true;
			return true
		},
		_mouseMove : function(e) {
			if (c.browser.msie && !(document.documentMode >= 9) && !e.button) {
				return this._mouseUp(e)
			}
			if (this._mouseStarted) {
				this._mouseDrag(e);
				return e.preventDefault()
			}
			if (this._mouseDistanceMet(e) && this._mouseDelayMet(e)) {
				this._mouseStarted = (this._mouseStart(this._mouseDownEvent, e) !== false);
				(this._mouseStarted ? this._mouseDrag(e) : this._mouseUp(e))
			}
			return !this._mouseStarted
		},
		_mouseUp : function(e) {
			c(document).unbind("mousemove." + this.widgetName,
					this._mouseMoveDelegate).unbind(
					"mouseup." + this.widgetName, this._mouseUpDelegate);
			if (this._mouseStarted) {
				this._mouseStarted = false;
				if (e.target == this._mouseDownEvent.target) {
					c.data(e.target, this.widgetName + ".preventClickEvent",
							true)
				}
				this._mouseStop(e)
			}
			return false
		},
		_mouseDistanceMet : function(e) {
			return (Math.max(Math.abs(this._mouseDownEvent.pageX - e.pageX),
					Math.abs(this._mouseDownEvent.pageY - e.pageY)) >= this.options.distance)
		},
		_mouseDelayMet : function(e) {
			return this.mouseDelayMet
		},
		_mouseStart : function(e) {
		},
		_mouseDrag : function(e) {
		},
		_mouseStop : function(e) {
		},
		_mouseCapture : function(e) {
			return true
		}
	})
})(jQuery);
(function(g, h) {
	g.ui = g.ui || {};
	var e = /left|center|right/, f = /top|center|bottom/, a = "center", c = g.fn.position, d = g.fn.offset;
	g.fn.position = function(k) {
		if (!k || !k.of) {
			return c.apply(this, arguments)
		}
		k = g.extend({}, k);
		var o = g(k.of), n = o[0], q = (k.collision || "flip").split(" "), p = k.offset
				? k.offset.split(" ")
				: [0, 0], m, j, l;
		if (n.nodeType === 9) {
			m = o.width();
			j = o.height();
			l = {
				top : 0,
				left : 0
			}
		} else {
			if (n.setTimeout) {
				m = o.width();
				j = o.height();
				l = {
					top : o.scrollTop(),
					left : o.scrollLeft()
				}
			} else {
				if (n.preventDefault) {
					k.at = "left top";
					m = j = 0;
					l = {
						top : k.of.pageY,
						left : k.of.pageX
					}
				} else {
					m = o.outerWidth();
					j = o.outerHeight();
					l = o.offset()
				}
			}
		}
		g.each(["my", "at"], function() {
					var r = (k[this] || "").split(" ");
					if (r.length === 1) {
						r = e.test(r[0]) ? r.concat([a]) : f.test(r[0]) ? [a]
								.concat(r) : [a, a]
					}
					r[0] = e.test(r[0]) ? r[0] : a;
					r[1] = f.test(r[1]) ? r[1] : a;
					k[this] = r
				});
		if (q.length === 1) {
			q[1] = q[0]
		}
		p[0] = parseInt(p[0], 10) || 0;
		if (p.length === 1) {
			p[1] = p[0]
		}
		p[1] = parseInt(p[1], 10) || 0;
		if (k.at[0] === "right") {
			l.left += m
		} else {
			if (k.at[0] === a) {
				l.left += m / 2
			}
		}
		if (k.at[1] === "bottom") {
			l.top += j
		} else {
			if (k.at[1] === a) {
				l.top += j / 2
			}
		}
		l.left += p[0];
		l.top += p[1];
		return this.each(function() {
			var v = g(this), x = v.outerWidth(), u = v.outerHeight(), w = parseInt(g
					.curCSS(this, "marginLeft", true))
					|| 0, s = parseInt(g.curCSS(this, "marginTop", true)) || 0, z = x
					+ w + (parseInt(g.curCSS(this, "marginRight", true)) || 0), A = u
					+ s + (parseInt(g.curCSS(this, "marginBottom", true)) || 0), y = g
					.extend({}, l), r;
			if (k.my[0] === "right") {
				y.left -= x
			} else {
				if (k.my[0] === a) {
					y.left -= x / 2
				}
			}
			if (k.my[1] === "bottom") {
				y.top -= u
			} else {
				if (k.my[1] === a) {
					y.top -= u / 2
				}
			}
			y.left = Math.round(y.left);
			y.top = Math.round(y.top);
			r = {
				left : y.left - w,
				top : y.top - s
			};
			g.each(["left", "top"], function(C, B) {
						if (g.ui.position[q[C]]) {
							g.ui.position[q[C]][B](y, {
										targetWidth : m,
										targetHeight : j,
										elemWidth : x,
										elemHeight : u,
										collisionPosition : r,
										collisionWidth : z,
										collisionHeight : A,
										offset : p,
										my : k.my,
										at : k.at
									})
						}
					});
			if (g.fn.bgiframe) {
				v.bgiframe()
			}
			v.offset(g.extend(y, {
						using : k.using
					}))
		})
	};
	g.ui.position = {
		fit : {
			left : function(j, k) {
				var m = g(window), l = k.collisionPosition.left
						+ k.collisionWidth - m.width() - m.scrollLeft();
				j.left = l > 0 ? j.left - l : Math.max(j.left
								- k.collisionPosition.left, j.left)
			},
			top : function(j, k) {
				var m = g(window), l = k.collisionPosition.top
						+ k.collisionHeight - m.height() - m.scrollTop();
				j.top = l > 0 ? j.top - l : Math.max(j.top
								- k.collisionPosition.top, j.top)
			}
		},
		flip : {
			left : function(k, m) {
				if (m.at[0] === a) {
					return
				}
				var o = g(window), n = m.collisionPosition.left
						+ m.collisionWidth - o.width() - o.scrollLeft(), j = m.my[0] === "left"
						? -m.elemWidth
						: m.my[0] === "right" ? m.elemWidth : 0, l = m.at[0] === "left"
						? m.targetWidth
						: -m.targetWidth, p = -2 * m.offset[0];
				k.left += m.collisionPosition.left < 0 ? j + l + p : n > 0 ? j
						+ l + p : 0
			},
			top : function(k, m) {
				if (m.at[1] === a) {
					return
				}
				var o = g(window), n = m.collisionPosition.top
						+ m.collisionHeight - o.height() - o.scrollTop(), j = m.my[1] === "top"
						? -m.elemHeight
						: m.my[1] === "bottom" ? m.elemHeight : 0, l = m.at[1] === "top"
						? m.targetHeight
						: -m.targetHeight, p = -2 * m.offset[1];
				k.top += m.collisionPosition.top < 0 ? j + l + p : n > 0 ? j
						+ l + p : 0
			}
		}
	};
	if (!g.offset.setOffset) {
		g.offset.setOffset = function(n, k) {
			if (/static/.test(g.curCSS(n, "position"))) {
				n.style.position = "relative"
			}
			var m = g(n), p = m.offset(), j = parseInt(
					g.curCSS(n, "top", true), 10)
					|| 0, o = parseInt(g.curCSS(n, "left", true), 10) || 0, l = {
				top : (k.top - p.top) + j,
				left : (k.left - p.left) + o
			};
			if ("using" in k) {
				k.using.call(n, l)
			} else {
				m.css(l)
			}
		};
		g.fn.offset = function(j) {
			var k = this[0];
			if (!k || !k.ownerDocument) {
				return null
			}
			if (j) {
				return this.each(function() {
							g.offset.setOffset(this, j)
						})
			}
			return d.call(this)
		}
	}
}(jQuery));
(function(a, c) {
	a.widget("ui.sortable", a.ui.mouse, {
		widgetEventPrefix : "sort",
		options : {
			appendTo : "parent",
			axis : false,
			connectWith : false,
			containment : false,
			cursor : "auto",
			cursorAt : false,
			dropOnEmpty : true,
			forcePlaceholderSize : false,
			forceHelperSize : false,
			grid : false,
			handle : false,
			helper : "original",
			items : "> *",
			opacity : false,
			placeholder : false,
			revert : false,
			scroll : true,
			scrollSensitivity : 20,
			scrollSpeed : 20,
			scope : "default",
			tolerance : "intersect",
			zIndex : 1000
		},
		_create : function() {
			var d = this.options;
			this.containerCache = {};
			this.element.addClass("ui-sortable");
			this.refresh();
			this.floating = this.items.length ? d.axis === "x"
					|| (/left|right/).test(this.items[0].item.css("float"))
					|| (/inline|table-cell/).test(this.items[0].item
							.css("display")) : false;
			this.offset = this.element.offset();
			this._mouseInit()
		},
		destroy : function() {
			this.element.removeClass("ui-sortable ui-sortable-disabled")
					.removeData("sortable").unbind(".sortable");
			this._mouseDestroy();
			for (var d = this.items.length - 1; d >= 0; d--) {
				this.items[d].item.removeData("sortable-item")
			}
			return this
		},
		_setOption : function(d, e) {
			if (d === "disabled") {
				this.options[d] = e;
				this.widget()[e ? "addClass" : "removeClass"]("ui-sortable-disabled")
			} else {
				a.Widget.prototype._setOption.apply(this, arguments)
			}
		},
		_mouseCapture : function(g, h) {
			if (this.reverting) {
				return false
			}
			if (this.options.disabled || this.options.type == "static") {
				return false
			}
			this._refreshItems(g);
			var f = null, e = this, d = a(g.target).parents().each(function() {
						if (a.data(this, "sortable-item") == e) {
							f = a(this);
							return false
						}
					});
			if (a.data(g.target, "sortable-item") == e) {
				f = a(g.target)
			}
			if (!f) {
				return false
			}
			if (this.options.handle && !h) {
				var j = false;
				a(this.options.handle, f).find("*").andSelf().each(function() {
							if (this == g.target) {
								j = true
							}
						});
				if (!j) {
					return false
				}
			}
			this.currentItem = f;
			this._removeCurrentsFromItems();
			return true
		},
		_mouseStart : function(g, h, d) {
			var j = this.options, e = this;
			this.currentContainer = this;
			this.refreshPositions();
			this.helper = this._createHelper(g);
			this._cacheHelperProportions();
			this._cacheMargins();
			this.scrollParent = this.helper.scrollParent();
			this.offset = this.currentItem.offset();
			this.offset = {
				top : this.offset.top - this.margins.top,
				left : this.offset.left - this.margins.left
			};
			this.helper.css("position", "absolute");
			this.cssPosition = this.helper.css("position");
			a.extend(this.offset, {
						click : {
							left : g.pageX - this.offset.left,
							top : g.pageY - this.offset.top
						},
						parent : this._getParentOffset(),
						relative : this._getRelativeOffset()
					});
			this.originalPosition = this._generatePosition(g);
			this.originalPageX = g.pageX;
			this.originalPageY = g.pageY;
			(j.cursorAt && this._adjustOffsetFromHelper(j.cursorAt));
			this.domPosition = {
				prev : this.currentItem.prev()[0],
				parent : this.currentItem.parent()[0]
			};
			if (this.helper[0] != this.currentItem[0]) {
				this.currentItem.hide()
			}
			this._createPlaceholder();
			if (j.containment) {
				this._setContainment()
			}
			if (j.cursor) {
				if (a("body").css("cursor")) {
					this._storedCursor = a("body").css("cursor")
				}
				a("body").css("cursor", j.cursor)
			}
			if (j.opacity) {
				if (this.helper.css("opacity")) {
					this._storedOpacity = this.helper.css("opacity")
				}
				this.helper.css("opacity", j.opacity)
			}
			if (j.zIndex) {
				if (this.helper.css("zIndex")) {
					this._storedZIndex = this.helper.css("zIndex")
				}
				this.helper.css("zIndex", j.zIndex)
			}
			if (this.scrollParent[0] != document
					&& this.scrollParent[0].tagName != "HTML") {
				this.overflowOffset = this.scrollParent.offset()
			}
			this._trigger("start", g, this._uiHash());
			if (!this._preserveHelperProportions) {
				this._cacheHelperProportions()
			}
			if (!d) {
				for (var f = this.containers.length - 1; f >= 0; f--) {
					this.containers[f]._trigger("activate", g, e._uiHash(this))
				}
			}
			if (a.ui.ddmanager) {
				a.ui.ddmanager.current = this
			}
			if (a.ui.ddmanager && !j.dropBehaviour) {
				a.ui.ddmanager.prepareOffsets(this, g)
			}
			this.dragging = true;
			this.helper.addClass("ui-sortable-helper");
			this._mouseDrag(g);
			return true
		},
		_mouseDrag : function(h) {
			this.position = this._generatePosition(h);
			this.positionAbs = this._convertPositionTo("absolute");
			if (!this.lastPositionAbs) {
				this.lastPositionAbs = this.positionAbs
			}
			if (this.options.scroll) {
				var j = this.options, d = false;
				if (this.scrollParent[0] != document
						&& this.scrollParent[0].tagName != "HTML") {
					if ((this.overflowOffset.top + this.scrollParent[0].offsetHeight)
							- h.pageY < j.scrollSensitivity) {
						this.scrollParent[0].scrollTop = d = this.scrollParent[0].scrollTop
								+ j.scrollSpeed
					} else {
						if (h.pageY - this.overflowOffset.top < j.scrollSensitivity) {
							this.scrollParent[0].scrollTop = d = this.scrollParent[0].scrollTop
									- j.scrollSpeed
						}
					}
					if ((this.overflowOffset.left + this.scrollParent[0].offsetWidth)
							- h.pageX < j.scrollSensitivity) {
						this.scrollParent[0].scrollLeft = d = this.scrollParent[0].scrollLeft
								+ j.scrollSpeed
					} else {
						if (h.pageX - this.overflowOffset.left < j.scrollSensitivity) {
							this.scrollParent[0].scrollLeft = d = this.scrollParent[0].scrollLeft
									- j.scrollSpeed
						}
					}
				} else {
					if (h.pageY - a(document).scrollTop() < j.scrollSensitivity) {
						d = a(document).scrollTop(a(document).scrollTop()
								- j.scrollSpeed)
					} else {
						if (a(window).height()
								- (h.pageY - a(document).scrollTop()) < j.scrollSensitivity) {
							d = a(document).scrollTop(a(document).scrollTop()
									+ j.scrollSpeed)
						}
					}
					if (h.pageX - a(document).scrollLeft() < j.scrollSensitivity) {
						d = a(document).scrollLeft(a(document).scrollLeft()
								- j.scrollSpeed)
					} else {
						if (a(window).width()
								- (h.pageX - a(document).scrollLeft()) < j.scrollSensitivity) {
							d = a(document).scrollLeft(a(document).scrollLeft()
									+ j.scrollSpeed)
						}
					}
				}
				if (d !== false && a.ui.ddmanager && !j.dropBehaviour) {
					a.ui.ddmanager.prepareOffsets(this, h)
				}
			}
			this.positionAbs = this._convertPositionTo("absolute");
			if (!this.options.axis || this.options.axis != "y") {
				this.helper[0].style.left = this.position.left + "px"
			}
			if (!this.options.axis || this.options.axis != "x") {
				this.helper[0].style.top = this.position.top + "px"
			}
			for (var f = this.items.length - 1; f >= 0; f--) {
				var g = this.items[f], e = g.item[0], k = this
						._intersectsWithPointer(g);
				if (!k) {
					continue
				}
				if (e != this.currentItem[0]
						&& this.placeholder[k == 1 ? "next" : "prev"]()[0] != e
						&& !a.ui.contains(this.placeholder[0], e)
						&& (this.options.type == "semi-dynamic" ? !a.ui
								.contains(this.element[0], e) : true)) {
					this.direction = k == 1 ? "down" : "up";
					if (this.options.tolerance == "pointer"
							|| this._intersectsWithSides(g)) {
						this._rearrange(h, g)
					} else {
						break
					}
					this._trigger("change", h, this._uiHash());
					break
				}
			}
			this._contactContainers(h);
			if (a.ui.ddmanager) {
				a.ui.ddmanager.drag(this, h)
			}
			this._trigger("sort", h, this._uiHash());
			this.lastPositionAbs = this.positionAbs;
			return false
		},
		_mouseStop : function(e, f) {
			if (!e) {
				return
			}
			if (a.ui.ddmanager && !this.options.dropBehaviour) {
				a.ui.ddmanager.drop(this, e)
			}
			if (this.options.revert) {
				var d = this;
				var g = d.placeholder.offset();
				d.reverting = true;
				a(this.helper).animate({
					left : g.left
							- this.offset.parent.left
							- d.margins.left
							+ (this.offsetParent[0] == document.body
									? 0
									: this.offsetParent[0].scrollLeft),
					top : g.top
							- this.offset.parent.top
							- d.margins.top
							+ (this.offsetParent[0] == document.body
									? 0
									: this.offsetParent[0].scrollTop)
				}, parseInt(this.options.revert, 10) || 500, function() {
					d._clear(e)
				})
			} else {
				this._clear(e, f)
			}
			return false
		},
		cancel : function() {
			var d = this;
			if (this.dragging) {
				this._mouseUp({
							target : null
						});
				if (this.options.helper == "original") {
					this.currentItem.css(this._storedCSS)
							.removeClass("ui-sortable-helper")
				} else {
					this.currentItem.show()
				}
				for (var e = this.containers.length - 1; e >= 0; e--) {
					this.containers[e]._trigger("deactivate", null, d
									._uiHash(this));
					if (this.containers[e].containerCache.over) {
						this.containers[e]._trigger("out", null, d
										._uiHash(this));
						this.containers[e].containerCache.over = 0
					}
				}
			}
			if (this.placeholder) {
				if (this.placeholder[0].parentNode) {
					this.placeholder[0].parentNode
							.removeChild(this.placeholder[0])
				}
				if (this.options.helper != "original" && this.helper
						&& this.helper[0].parentNode) {
					this.helper.remove()
				}
				a.extend(this, {
							helper : null,
							dragging : false,
							reverting : false,
							_noFinalSort : null
						});
				if (this.domPosition.prev) {
					a(this.domPosition.prev).after(this.currentItem)
				} else {
					a(this.domPosition.parent).prepend(this.currentItem)
				}
			}
			return this
		},
		serialize : function(f) {
			var d = this._getItemsAsjQuery(f && f.connected);
			var e = [];
			f = f || {};
			a(d).each(function() {
				var g = (a(f.item || this).attr(f.attribute || "id") || "")
						.match(f.expression || (/(.+)[-=_](.+)/));
				if (g) {
					e.push((f.key || g[1] + "[]") + "="
							+ (f.key && f.expression ? g[1] : g[2]))
				}
			});
			if (!e.length && f.key) {
				e.push(f.key + "=")
			}
			return e.join("&")
		},
		toArray : function(f) {
			var d = this._getItemsAsjQuery(f && f.connected);
			var e = [];
			f = f || {};
			d.each(function() {
						e.push(a(f.item || this).attr(f.attribute || "id")
								|| "")
					});
			return e
		},
		_intersectsWith : function(o) {
			var f = this.positionAbs.left, e = f + this.helperProportions.width, n = this.positionAbs.top, m = n
					+ this.helperProportions.height;
			var g = o.left, d = g + o.width, p = o.top, k = p + o.height;
			var q = this.offset.click.top, j = this.offset.click.left;
			var h = (n + q) > p && (n + q) < k && (f + j) > g && (f + j) < d;
			if (this.options.tolerance == "pointer"
					|| this.options.forcePointerForContainers
					|| (this.options.tolerance != "pointer" && this.helperProportions[this.floating
							? "width"
							: "height"] > o[this.floating ? "width" : "height"])) {
				return h
			} else {
				return (g < f + (this.helperProportions.width / 2)
						&& e - (this.helperProportions.width / 2) < d
						&& p < n + (this.helperProportions.height / 2) && m
						- (this.helperProportions.height / 2) < k)
			}
		},
		_intersectsWithPointer : function(f) {
			var g = a.ui.isOverAxis(this.positionAbs.top
							+ this.offset.click.top, f.top, f.height), e = a.ui
					.isOverAxis(this.positionAbs.left + this.offset.click.left,
							f.left, f.width), j = g && e, d = this
					._getDragVerticalDirection(), h = this
					._getDragHorizontalDirection();
			if (!j) {
				return false
			}
			return this.floating ? (((h && h == "right") || d == "down")
					? 2
					: 1) : (d && (d == "down" ? 2 : 1))
		},
		_intersectsWithSides : function(g) {
			var e = a.ui.isOverAxis(this.positionAbs.top
							+ this.offset.click.top, g.top + (g.height / 2),
					g.height), f = a.ui.isOverAxis(this.positionAbs.left
							+ this.offset.click.left, g.left + (g.width / 2),
					g.width), d = this._getDragVerticalDirection(), h = this
					._getDragHorizontalDirection();
			if (this.floating && h) {
				return ((h == "right" && f) || (h == "left" && !f))
			} else {
				return d && ((d == "down" && e) || (d == "up" && !e))
			}
		},
		_getDragVerticalDirection : function() {
			var d = this.positionAbs.top - this.lastPositionAbs.top;
			return d != 0 && (d > 0 ? "down" : "up")
		},
		_getDragHorizontalDirection : function() {
			var d = this.positionAbs.left - this.lastPositionAbs.left;
			return d != 0 && (d > 0 ? "right" : "left")
		},
		refresh : function(d) {
			this._refreshItems(d);
			this.refreshPositions();
			return this
		},
		_connectWith : function() {
			var d = this.options;
			return d.connectWith.constructor == String
					? [d.connectWith]
					: d.connectWith
		},
		_getItemsAsjQuery : function(d) {
			var n = this;
			var k = [];
			var g = [];
			var l = this._connectWith();
			if (l && d) {
				for (var f = l.length - 1; f >= 0; f--) {
					var m = a(l[f]);
					for (var e = m.length - 1; e >= 0; e--) {
						var h = a.data(m[e], "sortable");
						if (h && h != this && !h.options.disabled) {
							g
									.push([
											a.isFunction(h.options.items)
													? h.options.items
															.call(h.element)
													: a(h.options.items,
															h.element)
															.not(".ui-sortable-helper")
															.not(".ui-sortable-placeholder"),
											h])
						}
					}
				}
			}
			g.push([
					a.isFunction(this.options.items) ? this.options.items.call(
							this.element, null, {
								options : this.options,
								item : this.currentItem
							}) : a(this.options.items, this.element)
							.not(".ui-sortable-helper")
							.not(".ui-sortable-placeholder"), this]);
			for (var f = g.length - 1; f >= 0; f--) {
				g[f][0].each(function() {
							k.push(this)
						})
			}
			return a(k)
		},
		_removeCurrentsFromItems : function() {
			var f = this.currentItem.find(":data(sortable-item)");
			for (var e = 0; e < this.items.length; e++) {
				for (var d = 0; d < f.length; d++) {
					if (f[d] == this.items[e].item[0]) {
						this.items.splice(e, 1)
					}
				}
			}
		},
		_refreshItems : function(d) {
			this.items = [];
			this.containers = [this];
			var l = this.items;
			var r = this;
			var h = [[
					a.isFunction(this.options.items) ? this.options.items.call(
							this.element[0], d, {
								item : this.currentItem
							}) : a(this.options.items, this.element), this]];
			var n = this._connectWith();
			if (n) {
				for (var g = n.length - 1; g >= 0; g--) {
					var o = a(n[g]);
					for (var f = o.length - 1; f >= 0; f--) {
						var k = a.data(o[f], "sortable");
						if (k && k != this && !k.options.disabled) {
							h
									.push([
											a.isFunction(k.options.items)
													? k.options.items.call(
															k.element[0], d, {
																item : this.currentItem
															})
													: a(k.options.items,
															k.element), k]);
							this.containers.push(k)
						}
					}
				}
			}
			for (var g = h.length - 1; g >= 0; g--) {
				var m = h[g][1];
				var e = h[g][0];
				for (var f = 0, p = e.length; f < p; f++) {
					var q = a(e[f]);
					q.data("sortable-item", m);
					l.push({
								item : q,
								instance : m,
								width : 0,
								height : 0,
								left : 0,
								top : 0
							})
				}
			}
		},
		refreshPositions : function(d) {
			if (this.offsetParent && this.helper) {
				this.offset.parent = this._getParentOffset()
			}
			for (var f = this.items.length - 1; f >= 0; f--) {
				var g = this.items[f];
				if (g.instance != this.currentContainer
						&& this.currentContainer
						&& g.item[0] != this.currentItem[0]) {
					continue
				}
				var e = this.options.toleranceElement ? a(
						this.options.toleranceElement, g.item) : g.item;
				if (!d) {
					g.width = e.outerWidth();
					g.height = e.outerHeight()
				}
				var h = e.offset();
				g.left = h.left;
				g.top = h.top
			}
			if (this.options.custom && this.options.custom.refreshContainers) {
				this.options.custom.refreshContainers.call(this)
			} else {
				for (var f = this.containers.length - 1; f >= 0; f--) {
					var h = this.containers[f].element.offset();
					this.containers[f].containerCache.left = h.left;
					this.containers[f].containerCache.top = h.top;
					this.containers[f].containerCache.width = this.containers[f].element
							.outerWidth();
					this.containers[f].containerCache.height = this.containers[f].element
							.outerHeight()
				}
			}
			return this
		},
		_createPlaceholder : function(f) {
			var d = f || this, g = d.options;
			if (!g.placeholder || g.placeholder.constructor == String) {
				var e = g.placeholder;
				g.placeholder = {
					element : function() {
						var h = a(document
								.createElement(d.currentItem[0].nodeName))
								.addClass(e || d.currentItem[0].className
										+ " ui-sortable-placeholder")
								.removeClass("ui-sortable-helper")[0];
						if (!e) {
							h.style.visibility = "hidden"
						}
						return h
					},
					update : function(h, j) {
						if (e && !g.forcePlaceholderSize) {
							return
						}
						if (!j.height()) {
							j.height(d.currentItem.innerHeight()
									- parseInt(d.currentItem.css("paddingTop")
													|| 0, 10)
									- parseInt(d.currentItem
													.css("paddingBottom")
													|| 0, 10))
						}
						if (!j.width()) {
							j.width(d.currentItem.innerWidth()
									- parseInt(d.currentItem.css("paddingLeft")
													|| 0, 10)
									- parseInt(d.currentItem
													.css("paddingRight")
													|| 0, 10))
						}
					}
				}
			}
			d.placeholder = a(g.placeholder.element.call(d.element,
					d.currentItem));
			d.currentItem.after(d.placeholder);
			g.placeholder.update(d, d.placeholder)
		},
		_contactContainers : function(d) {
			var f = null, m = null;
			for (var h = this.containers.length - 1; h >= 0; h--) {
				if (a.ui.contains(this.currentItem[0],
						this.containers[h].element[0])) {
					continue
				}
				if (this._intersectsWith(this.containers[h].containerCache)) {
					if (f
							&& a.ui.contains(this.containers[h].element[0],
									f.element[0])) {
						continue
					}
					f = this.containers[h];
					m = h
				} else {
					if (this.containers[h].containerCache.over) {
						this.containers[h]._trigger("out", d, this
										._uiHash(this));
						this.containers[h].containerCache.over = 0
					}
				}
			}
			if (!f) {
				return
			}
			if (this.containers.length === 1) {
				this.containers[m]._trigger("over", d, this._uiHash(this));
				this.containers[m].containerCache.over = 1
			} else {
				if (this.currentContainer != this.containers[m]) {
					var l = 10000;
					var k = null;
					var e = this.positionAbs[this.containers[m].floating
							? "left"
							: "top"];
					for (var g = this.items.length - 1; g >= 0; g--) {
						if (!a.ui.contains(this.containers[m].element[0],
								this.items[g].item[0])) {
							continue
						}
						var n = this.items[g][this.containers[m].floating
								? "left"
								: "top"];
						if (Math.abs(n - e) < l) {
							l = Math.abs(n - e);
							k = this.items[g]
						}
					}
					if (!k && !this.options.dropOnEmpty) {
						return
					}
					this.currentContainer = this.containers[m];
					k ? this._rearrange(d, k, null, true) : this._rearrange(d,
							null, this.containers[m].element, true);
					this._trigger("change", d, this._uiHash());
					this.containers[m]
							._trigger("change", d, this._uiHash(this));
					this.options.placeholder.update(this.currentContainer,
							this.placeholder);
					this.containers[m]._trigger("over", d, this._uiHash(this));
					this.containers[m].containerCache.over = 1
				}
			}
		},
		_createHelper : function(e) {
			var f = this.options;
			var d = a.isFunction(f.helper) ? a(f.helper.apply(this.element[0],
					[e, this.currentItem])) : (f.helper == "clone"
					? this.currentItem.clone()
					: this.currentItem);
			if (!d.parents("body").length) {
				a(f.appendTo != "parent"
						? f.appendTo
						: this.currentItem[0].parentNode)[0].appendChild(d[0])
			}
			if (d[0] == this.currentItem[0]) {
				this._storedCSS = {
					width : this.currentItem[0].style.width,
					height : this.currentItem[0].style.height,
					position : this.currentItem.css("position"),
					top : this.currentItem.css("top"),
					left : this.currentItem.css("left")
				}
			}
			if (d[0].style.width == "" || f.forceHelperSize) {
				d.width(this.currentItem.width())
			}
			if (d[0].style.height == "" || f.forceHelperSize) {
				d.height(this.currentItem.height())
			}
			return d
		},
		_adjustOffsetFromHelper : function(d) {
			if (typeof d == "string") {
				d = d.split(" ")
			}
			if (a.isArray(d)) {
				d = {
					left : +d[0],
					top : +d[1] || 0
				}
			}
			if ("left" in d) {
				this.offset.click.left = d.left + this.margins.left
			}
			if ("right" in d) {
				this.offset.click.left = this.helperProportions.width - d.right
						+ this.margins.left
			}
			if ("top" in d) {
				this.offset.click.top = d.top + this.margins.top
			}
			if ("bottom" in d) {
				this.offset.click.top = this.helperProportions.height
						- d.bottom + this.margins.top
			}
		},
		_getParentOffset : function() {
			this.offsetParent = this.helper.offsetParent();
			var d = this.offsetParent.offset();
			if (this.cssPosition == "absolute"
					&& this.scrollParent[0] != document
					&& a.ui
							.contains(this.scrollParent[0],
									this.offsetParent[0])) {
				d.left += this.scrollParent.scrollLeft();
				d.top += this.scrollParent.scrollTop()
			}
			if ((this.offsetParent[0] == document.body)
					|| (this.offsetParent[0].tagName
							&& this.offsetParent[0].tagName.toLowerCase() == "html" && a.browser.msie)) {
				d = {
					top : 0,
					left : 0
				}
			}
			return {
				top : d.top
						+ (parseInt(this.offsetParent.css("borderTopWidth"), 10) || 0),
				left : d.left
						+ (parseInt(this.offsetParent.css("borderLeftWidth"),
								10) || 0)
			}
		},
		_getRelativeOffset : function() {
			if (this.cssPosition == "relative") {
				var d = this.currentItem.position();
				return {
					top : d.top - (parseInt(this.helper.css("top"), 10) || 0)
							+ this.scrollParent.scrollTop(),
					left : d.left
							- (parseInt(this.helper.css("left"), 10) || 0)
							+ this.scrollParent.scrollLeft()
				}
			} else {
				return {
					top : 0,
					left : 0
				}
			}
		},
		_cacheMargins : function() {
			this.margins = {
				left : (parseInt(this.currentItem.css("marginLeft"), 10) || 0),
				top : (parseInt(this.currentItem.css("marginTop"), 10) || 0)
			}
		},
		_cacheHelperProportions : function() {
			this.helperProportions = {
				width : this.helper.outerWidth(),
				height : this.helper.outerHeight()
			}
		},
		_setContainment : function() {
			var g = this.options;
			if (g.containment == "parent") {
				g.containment = this.helper[0].parentNode
			}
			if (g.containment == "document" || g.containment == "window") {
				this.containment = [
						0 - this.offset.relative.left - this.offset.parent.left,
						0 - this.offset.relative.top - this.offset.parent.top,
						a(g.containment == "document" ? document : window)
								.width()
								- this.helperProportions.width
								- this.margins.left,
						(a(g.containment == "document" ? document : window)
								.height() || document.body.parentNode.scrollHeight)
								- this.helperProportions.height
								- this.margins.top]
			}
			if (!(/^(document|window|parent)$/).test(g.containment)) {
				var e = a(g.containment)[0];
				var f = a(g.containment).offset();
				var d = (a(e).css("overflow") != "hidden");
				this.containment = [
						f.left
								+ (parseInt(a(e).css("borderLeftWidth"), 10) || 0)
								+ (parseInt(a(e).css("paddingLeft"), 10) || 0)
								- this.margins.left,
						f.top + (parseInt(a(e).css("borderTopWidth"), 10) || 0)
								+ (parseInt(a(e).css("paddingTop"), 10) || 0)
								- this.margins.top,
						f.left
								+ (d
										? Math
												.max(e.scrollWidth,
														e.offsetWidth)
										: e.offsetWidth)
								- (parseInt(a(e).css("borderLeftWidth"), 10) || 0)
								- (parseInt(a(e).css("paddingRight"), 10) || 0)
								- this.helperProportions.width
								- this.margins.left,
						f.top
								+ (d
										? Math.max(e.scrollHeight,
												e.offsetHeight)
										: e.offsetHeight)
								- (parseInt(a(e).css("borderTopWidth"), 10) || 0)
								- (parseInt(a(e).css("paddingBottom"), 10) || 0)
								- this.helperProportions.height
								- this.margins.top]
			}
		},
		_convertPositionTo : function(h, k) {
			if (!k) {
				k = this.position
			}
			var f = h == "absolute" ? 1 : -1;
			var g = this.options, e = this.cssPosition == "absolute"
					&& !(this.scrollParent[0] != document && a.ui.contains(
							this.scrollParent[0], this.offsetParent[0]))
					? this.offsetParent
					: this.scrollParent, j = (/(html|body)/i)
					.test(e[0].tagName);
			return {
				top : (k.top + this.offset.relative.top * f
						+ this.offset.parent.top * f - (a.browser.safari
						&& this.cssPosition == "fixed"
						? 0
						: (this.cssPosition == "fixed" ? -this.scrollParent
								.scrollTop() : (j ? 0 : e.scrollTop()))
								* f)),
				left : (k.left + this.offset.relative.left * f
						+ this.offset.parent.left * f - (a.browser.safari
						&& this.cssPosition == "fixed"
						? 0
						: (this.cssPosition == "fixed" ? -this.scrollParent
								.scrollLeft() : j ? 0 : e.scrollLeft())
								* f))
			}
		},
		_generatePosition : function(g) {
			var k = this.options, d = this.cssPosition == "absolute"
					&& !(this.scrollParent[0] != document && a.ui.contains(
							this.scrollParent[0], this.offsetParent[0]))
					? this.offsetParent
					: this.scrollParent, l = (/(html|body)/i)
					.test(d[0].tagName);
			if (this.cssPosition == "relative"
					&& !(this.scrollParent[0] != document && this.scrollParent[0] != this.offsetParent[0])) {
				this.offset.relative = this._getRelativeOffset()
			}
			var f = g.pageX;
			var e = g.pageY;
			if (this.originalPosition) {
				if (this.containment) {
					if (g.pageX - this.offset.click.left < this.containment[0]) {
						f = this.containment[0] + this.offset.click.left
					}
					if (g.pageY - this.offset.click.top < this.containment[1]) {
						e = this.containment[1] + this.offset.click.top
					}
					if (g.pageX - this.offset.click.left > this.containment[2]) {
						f = this.containment[2] + this.offset.click.left
					}
					if (g.pageY - this.offset.click.top > this.containment[3]) {
						e = this.containment[3] + this.offset.click.top
					}
				}
				if (k.grid) {
					var j = this.originalPageY
							+ Math.round((e - this.originalPageY) / k.grid[1])
							* k.grid[1];
					e = this.containment
							? (!(j - this.offset.click.top < this.containment[1] || j
									- this.offset.click.top > this.containment[3])
									? j
									: (!(j - this.offset.click.top < this.containment[1])
											? j - k.grid[1]
											: j + k.grid[1]))
							: j;
					var h = this.originalPageX
							+ Math.round((f - this.originalPageX) / k.grid[0])
							* k.grid[0];
					f = this.containment
							? (!(h - this.offset.click.left < this.containment[0] || h
									- this.offset.click.left > this.containment[2])
									? h
									: (!(h - this.offset.click.left < this.containment[0])
											? h - k.grid[0]
											: h + k.grid[0]))
							: h
				}
			}
			return {
				top : (e - this.offset.click.top - this.offset.relative.top
						- this.offset.parent.top + (a.browser.safari
						&& this.cssPosition == "fixed"
						? 0
						: (this.cssPosition == "fixed" ? -this.scrollParent
								.scrollTop() : (l ? 0 : d.scrollTop())))),
				left : (f - this.offset.click.left - this.offset.relative.left
						- this.offset.parent.left + (a.browser.safari
						&& this.cssPosition == "fixed"
						? 0
						: (this.cssPosition == "fixed" ? -this.scrollParent
								.scrollLeft() : l ? 0 : d.scrollLeft())))
			}
		},
		_rearrange : function(j, h, e, g) {
			e ? e[0].appendChild(this.placeholder[0]) : h.item[0].parentNode
					.insertBefore(this.placeholder[0],
							(this.direction == "down"
									? h.item[0]
									: h.item[0].nextSibling));
			this.counter = this.counter ? ++this.counter : 1;
			var f = this, d = this.counter;
			window.setTimeout(function() {
						if (d == f.counter) {
							f.refreshPositions(!g)
						}
					}, 0)
		},
		_clear : function(f, g) {
			this.reverting = false;
			var h = [], d = this;
			if (!this._noFinalSort && this.currentItem.parent().length) {
				this.placeholder.before(this.currentItem)
			}
			this._noFinalSort = null;
			if (this.helper[0] == this.currentItem[0]) {
				for (var e in this._storedCSS) {
					if (this._storedCSS[e] == "auto"
							|| this._storedCSS[e] == "static") {
						this._storedCSS[e] = ""
					}
				}
				this.currentItem.css(this._storedCSS)
						.removeClass("ui-sortable-helper")
			} else {
				this.currentItem.show()
			}
			if (this.fromOutside && !g) {
				h.push(function(j) {
							this._trigger("receive", j, this
											._uiHash(this.fromOutside))
						})
			}
			if ((this.fromOutside
					|| this.domPosition.prev != this.currentItem.prev()
							.not(".ui-sortable-helper")[0] || this.domPosition.parent != this.currentItem
					.parent()[0])
					&& !g) {
				h.push(function(j) {
							this._trigger("update", j, this._uiHash())
						})
			}
			if (!a.ui.contains(this.element[0], this.currentItem[0])) {
				if (!g) {
					h.push(function(j) {
								this._trigger("remove", j, this._uiHash())
							})
				}
				for (var e = this.containers.length - 1; e >= 0; e--) {
					if (a.ui.contains(this.containers[e].element[0],
							this.currentItem[0])
							&& !g) {
						h.push((function(j) {
									return function(k) {
										j._trigger("receive", k, this
														._uiHash(this))
									}
								}).call(this, this.containers[e]));
						h.push((function(j) {
									return function(k) {
										j._trigger("update", k, this
														._uiHash(this))
									}
								}).call(this, this.containers[e]))
					}
				}
			}
			for (var e = this.containers.length - 1; e >= 0; e--) {
				if (!g) {
					h.push((function(j) {
								return function(k) {
									j._trigger("deactivate", k, this
													._uiHash(this))
								}
							}).call(this, this.containers[e]))
				}
				if (this.containers[e].containerCache.over) {
					h.push((function(j) {
								return function(k) {
									j._trigger("out", k, this._uiHash(this))
								}
							}).call(this, this.containers[e]));
					this.containers[e].containerCache.over = 0
				}
			}
			if (this._storedCursor) {
				a("body").css("cursor", this._storedCursor)
			}
			if (this._storedOpacity) {
				this.helper.css("opacity", this._storedOpacity)
			}
			if (this._storedZIndex) {
				this.helper.css("zIndex", this._storedZIndex == "auto"
								? ""
								: this._storedZIndex)
			}
			this.dragging = false;
			if (this.cancelHelperRemoval) {
				if (!g) {
					this._trigger("beforeStop", f, this._uiHash());
					for (var e = 0; e < h.length; e++) {
						h[e].call(this, f)
					}
					this._trigger("stop", f, this._uiHash())
				}
				return false
			}
			if (!g) {
				this._trigger("beforeStop", f, this._uiHash())
			}
			this.placeholder[0].parentNode.removeChild(this.placeholder[0]);
			if (this.helper[0] != this.currentItem[0]) {
				this.helper.remove()
			}
			this.helper = null;
			if (!g) {
				for (var e = 0; e < h.length; e++) {
					h[e].call(this, f)
				}
				this._trigger("stop", f, this._uiHash())
			}
			this.fromOutside = false;
			return true
		},
		_trigger : function() {
			if (a.Widget.prototype._trigger.apply(this, arguments) === false) {
				this.cancel()
			}
		},
		_uiHash : function(e) {
			var d = e || this;
			return {
				helper : d.helper,
				placeholder : d.placeholder || a([]),
				position : d.position,
				originalPosition : d.originalPosition,
				offset : d.positionAbs,
				item : d.currentItem,
				sender : e ? e.element : null
			}
		}
	});
	a.extend(a.ui.sortable, {
				version : "1.8.16"
			})
})(jQuery);
(function(a, c) {
	var d = 0;
	a.widget("ui.autocomplete", {
		options : {
			appendTo : "body",
			autoFocus : false,
			delay : 300,
			minLength : 1,
			position : {
				my : "left top",
				at : "left bottom",
				collision : "none"
			},
			source : null
		},
		pending : 0,
		_create : function() {
			var e = this, g = this.element[0].ownerDocument, f;
			this.element.addClass("ui-autocomplete-input").attr("autocomplete",
					"off").attr({
						role : "textbox",
						"aria-autocomplete" : "list",
						"aria-haspopup" : "true"
					}).bind("keydown.autocomplete", function(h) {
						if (e.options.disabled
								|| e.element.propAttr("readOnly")) {
							return
						}
						f = false;
						var j = a.ui.keyCode;
						switch (h.keyCode) {
							case j.PAGE_UP :
								e._move("previousPage", h);
								break;
							case j.PAGE_DOWN :
								e._move("nextPage", h);
								break;
							case j.UP :
								e._move("previous", h);
								h.preventDefault();
								break;
							case j.DOWN :
								e._move("next", h);
								h.preventDefault();
								break;
							case j.ENTER :
							case j.NUMPAD_ENTER :
								if (e.menu.active) {
									f = true;
									h.preventDefault()
								}
							case j.TAB :
								if (!e.menu.active) {
									return
								}
								e.menu.select(h);
								break;
							case j.ESCAPE :
								e.element.val(e.term);
								e.close(h);
								break;
							default :
								clearTimeout(e.searching);
								e.searching = setTimeout(function() {
											if (e.term != e.element.val()) {
												e.selectedItem = null;
												e.search(null, h)
											}
										}, e.options.delay);
								break
						}
					}).bind("keypress.autocomplete", function(h) {
						if (f) {
							f = false;
							h.preventDefault()
						}
					}).bind("focus.autocomplete", function() {
						if (e.options.disabled) {
							return
						}
						e.selectedItem = null;
						e.previous = e.element.val()
					}).bind("blur.autocomplete", function(h) {
						if (e.options.disabled) {
							return
						}
						clearTimeout(e.searching);
						e.closing = setTimeout(function() {
									e.close(h);
									e._change(h)
								}, 150)
					});
			this._initSource();
			this.response = function() {
				return e._response.apply(e, arguments)
			};
			this.menu = a("<ul></ul>").addClass("ui-autocomplete").appendTo(a(
					this.options.appendTo || "body", g)[0]).mousedown(
					function(h) {
						var j = e.menu.element[0];
						if (!a(h.target).closest(".ui-menu-item").length) {
							setTimeout(function() {
										a(document).one("mousedown",
												function(k) {
													if (k.target !== e.element[0]
															&& k.target !== j
															&& !a.ui
																	.contains(
																			j,
																			k.target)) {
														e.close()
													}
												})
									}, 1)
						}
						setTimeout(function() {
									clearTimeout(e.closing)
								}, 13)
					}).menu({
				focus : function(j, k) {
					var h = k.item.data("item.autocomplete");
					if (false !== e._trigger("focus", j, {
								item : h
							})) {
						if (/^key/.test(j.originalEvent.type)) {
							e.element.val(h.value)
						}
					}
				},
				selected : function(k, l) {
					var j = l.item.data("item.autocomplete"), h = e.previous;
					if (e.element[0] !== g.activeElement) {
						e.element.focus();
						e.previous = h;
						setTimeout(function() {
									e.previous = h;
									e.selectedItem = j
								}, 1)
					}
					if (false !== e._trigger("select", k, {
								item : j
							})) {
						e.element.val(j.value)
					}
					e.term = e.element.val();
					e.close(k);
					e.selectedItem = j
				},
				blur : function(h, j) {
					if (e.menu.element.is(":visible")
							&& (e.element.val() !== e.term)) {
						e.element.val(e.term)
					}
				}
			}).zIndex(this.element.zIndex() + 1).css({
						top : 0,
						left : 0
					}).hide().data("menu");
			if (a.fn.bgiframe) {
				this.menu.element.bgiframe()
			}
		},
		destroy : function() {
			this.element.removeClass("ui-autocomplete-input")
					.removeAttr("autocomplete").removeAttr("role")
					.removeAttr("aria-autocomplete")
					.removeAttr("aria-haspopup");
			this.menu.element.remove();
			a.Widget.prototype.destroy.call(this)
		},
		_setOption : function(e, f) {
			a.Widget.prototype._setOption.apply(this, arguments);
			if (e === "source") {
				this._initSource()
			}
			if (e === "appendTo") {
				this.menu.element.appendTo(a(f || "body",
						this.element[0].ownerDocument)[0])
			}
			if (e === "disabled" && f && this.xhr) {
				this.xhr.abort()
			}
		},
		_initSource : function() {
			var e = this, g, f;
			if (a.isArray(this.options.source)) {
				g = this.options.source;
				this.source = function(j, h) {
					h(a.ui.autocomplete.filter(g, j.term))
				}
			} else {
				if (typeof this.options.source === "string") {
					f = this.options.source;
					this.source = function(j, h) {
						if (e.xhr) {
							e.xhr.abort()
						}
						e.xhr = a.ajax({
									url : f,
									data : j,
									dataType : "json",
									autocompleteRequest : ++d,
									success : function(l, k) {
										if (this.autocompleteRequest === d) {
											h(l)
										}
									},
									error : function() {
										if (this.autocompleteRequest === d) {
											h([])
										}
									}
								})
					}
				} else {
					this.source = this.options.source
				}
			}
		},
		search : function(f, e) {
			f = f != null ? f : this.element.val();
			this.term = this.element.val();
			if (f.length < this.options.minLength) {
				return this.close(e)
			}
			clearTimeout(this.closing);
			if (this._trigger("search", e) === false) {
				return
			}
			return this._search(f)
		},
		_search : function(e) {
			this.pending++;
			this.element.addClass("ui-autocomplete-loading");
			this.source({
						term : e
					}, this.response)
		},
		_response : function(e) {
			if (!this.options.disabled && e && e.length) {
				e = this._normalize(e);
				this._suggest(e);
				this._trigger("open")
			} else {
				this.close()
			}
			this.pending--;
			if (!this.pending) {
				this.element.removeClass("ui-autocomplete-loading")
			}
		},
		close : function(e) {
			clearTimeout(this.closing);
			if (this.menu.element.is(":visible")) {
				this.menu.element.hide();
				this.menu.deactivate();
				this._trigger("close", e)
			}
		},
		_change : function(e) {
			if (this.previous !== this.element.val()) {
				this._trigger("change", e, {
							item : this.selectedItem
						})
			}
		},
		_normalize : function(e) {
			if (e.length && e[0].label && e[0].value) {
				return e
			}
			return a.map(e, function(f) {
						if (typeof f === "string") {
							return {
								label : f,
								value : f
							}
						}
						return a.extend({
									label : f.label || f.value,
									value : f.value || f.label
								}, f)
					})
		},
		_suggest : function(e) {
			var f = this.menu.element.empty().zIndex(this.element.zIndex() + 1);
			this._renderMenu(f, e);
			this.menu.deactivate();
			this.menu.refresh();
			f.show();
			this._resizeMenu();
			f.position(a.extend({
						of : this.element
					}, this.options.position));
			if (this.options.autoFocus) {
				this.menu.next(new a.Event("mouseover"))
			}
		},
		_resizeMenu : function() {
			var e = this.menu.element;
			e.outerWidth(Math.max(e.width("").outerWidth(), this.element
							.outerWidth()))
		},
		_renderMenu : function(g, f) {
			var e = this;
			a.each(f, function(h, j) {
						e._renderItem(g, j)
					})
		},
		_renderItem : function(e, f) {
			return a("<li></li>").data("item.autocomplete", f)
					.append(a("<a></a>").text(f.label)).appendTo(e)
		},
		_move : function(f, e) {
			if (!this.menu.element.is(":visible")) {
				this.search(null, e);
				return
			}
			if (this.menu.first() && /^previous/.test(f) || this.menu.last()
					&& /^next/.test(f)) {
				this.element.val(this.term);
				this.menu.deactivate();
				return
			}
			this.menu[f](e)
		},
		widget : function() {
			return this.menu.element
		}
	});
	a.extend(a.ui.autocomplete, {
				escapeRegex : function(e) {
					return e.replace(/[-[\]{}()*+?.,\\^$|#\s]/g, "\\$&")
				},
				filter : function(g, e) {
					var f = new RegExp(a.ui.autocomplete.escapeRegex(e), "i");
					return a.grep(g, function(h) {
								return f.test(h.label || h.value || h)
							})
				}
			})
}(jQuery));
(function(a) {
	a.widget("ui.menu", {
		_create : function() {
			var c = this;
			this.element
					.addClass("ui-menu ui-widget ui-widget-content ui-corner-all")
					.attr({
								role : "listbox",
								"aria-activedescendant" : "ui-active-menuitem"
							}).click(function(d) {
								if (!a(d.target).closest(".ui-menu-item a").length) {
									return
								}
								d.preventDefault();
								c.select(d)
							});
			this.refresh()
		},
		refresh : function() {
			var d = this;
			var c = this.element.children("li:not(.ui-menu-item):has(a)")
					.addClass("ui-menu-item").attr("role", "menuitem");
			c.children("a").addClass("ui-corner-all").attr("tabindex", -1)
					.mouseenter(function(e) {
								d.activate(e, a(this).parent())
							}).mouseleave(function() {
								d.deactivate()
							})
		},
		activate : function(f, e) {
			this.deactivate();
			if (this.hasScroll()) {
				var g = e.offset().top - this.element.offset().top, c = this.element
						.scrollTop(), d = this.element.height();
				if (g < 0) {
					this.element.scrollTop(c + g)
				} else {
					if (g >= d) {
						this.element.scrollTop(c + g - d + e.height())
					}
				}
			}
			this.active = e.eq(0).children("a").addClass("ui-state-hover")
					.attr("id", "ui-active-menuitem").end();
			this._trigger("focus", f, {
						item : e
					})
		},
		deactivate : function() {
			if (!this.active) {
				return
			}
			this.active.children("a").removeClass("ui-state-hover")
					.removeAttr("id");
			this._trigger("blur");
			this.active = null
		},
		next : function(c) {
			this.move("next", ".ui-menu-item:first", c)
		},
		previous : function(c) {
			this.move("prev", ".ui-menu-item:last", c)
		},
		first : function() {
			return this.active && !this.active.prevAll(".ui-menu-item").length
		},
		last : function() {
			return this.active && !this.active.nextAll(".ui-menu-item").length
		},
		move : function(f, e, d) {
			if (!this.active) {
				this.activate(d, this.element.children(e));
				return
			}
			var c = this.active[f + "All"](".ui-menu-item").eq(0);
			if (c.length) {
				this.activate(d, c)
			} else {
				this.activate(d, this.element.children(e))
			}
		},
		nextPage : function(e) {
			if (this.hasScroll()) {
				if (!this.active || this.last()) {
					this.activate(e, this.element
									.children(".ui-menu-item:first"));
					return
				}
				var f = this.active.offset().top, d = this.element.height(), c = this.element
						.children(".ui-menu-item").filter(function() {
							var g = a(this).offset().top - f - d
									+ a(this).height();
							return g < 10 && g > -10
						});
				if (!c.length) {
					c = this.element.children(".ui-menu-item:last")
				}
				this.activate(e, c)
			} else {
				this.activate(e, this.element.children(".ui-menu-item")
								.filter(!this.active || this.last()
										? ":first"
										: ":last"))
			}
		},
		previousPage : function(d) {
			if (this.hasScroll()) {
				if (!this.active || this.first()) {
					this.activate(d, this.element
									.children(".ui-menu-item:last"));
					return
				}
				var e = this.active.offset().top, c = this.element.height();
				result = this.element.children(".ui-menu-item").filter(
						function() {
							var f = a(this).offset().top - e + c
									- a(this).height();
							return f < 10 && f > -10
						});
				if (!result.length) {
					result = this.element.children(".ui-menu-item:first")
				}
				this.activate(d, result)
			} else {
				this.activate(d, this.element.children(".ui-menu-item")
								.filter(!this.active || this.first()
										? ":last"
										: ":first"))
			}
		},
		hasScroll : function() {
			return this.element.height() < this.element[a.fn.prop
					? "prop"
					: "attr"]("scrollHeight")
		},
		select : function(c) {
			this._trigger("selected", c, {
						item : this.active
					})
		}
	})
}(jQuery));
(function(e, g) {
	var d = 0, c = 0;
	function f() {
		return ++d
	}
	function a() {
		return ++c
	}
	e.widget("ui.tabs", {
		options : {
			init : true,
			add : null,
			ajaxOptions : null,
			cache : false,
			cookie : null,
			collapsible : false,
			disable : null,
			disabled : [],
			enable : null,
			event : "click",
			fx : null,
			idPrefix : "ui-tabs-",
			load : null,
			panelTemplate : "<div></div>",
			remove : null,
			select : null,
			show : null,
			spinner : "<em>Loading&#8230;</em>",
			tabTemplate : "<li><a href='#{href}'><span>#{label}</span></a></li>"
		},
		_create : function() {
			this._tabify(this.options.init)
		},
		_setOption : function(h, j) {
			if (h == "selected") {
				if (this.options.collapsible && j == this.options.selected) {
					return
				}
				this.select(j)
			} else {
				this.options[h] = j;
				this._tabify()
			}
		},
		_tabId : function(h) {
			return h.title
					&& h.title.replace(/\s/g, "_").replace(
							/[^\w\u00c0-\uFFFF-]/g, "")
					|| this.options.idPrefix + f()
		},
		_sanitizeSelector : function(h) {
			return h.replace(/:/g, "\\:")
		},
		_cookie : function() {
			var h = this.cookie
					|| (this.cookie = this.options.cookie.name || "ui-tabs-"
							+ a());
			return e.cookie.apply(null, [h].concat(e.makeArray(arguments)))
		},
		_ui : function(j, h) {
			return {
				tab : j,
				panel : h,
				index : this.anchors.index(j)
			}
		},
		_cleanup : function() {
			this.lis.filter(".ui-state-processing")
					.removeClass("ui-state-processing")
					.find("span:data(label.tabs)").each(function() {
								var h = e(this);
								h.html(h.data("label.tabs"))
										.removeData("label.tabs")
							})
		},
		_tabify : function(v) {
			var w = this, k = this.options, j = /^#.+/;
			this.list = this.element.find("ol,ul").eq(0);
			this.lis = e(" > li:has(a[href])", this.list);
			this.anchors = this.lis.map(function() {
						return e("a", this)[0]
					});
			this.panels = e([]);
			this.anchors.each(function(y, o) {
				var x = e(o).attr("href");
				var z = x.split("#")[0], A;
				if (z
						&& (z === location.toString().split("#")[0] || (A = e("base")[0])
								&& z === A.href)) {
					x = o.hash;
					o.href = x
				}
				if (j.test(x)) {
					w.panels = w.panels.add(w.element.find(w
							._sanitizeSelector(x)))
				} else {
					if (x && x !== "#") {
						e.data(o, "href.tabs", x);
						e.data(o, "load.tabs", x.replace(/#.*$/, ""));
						var C = w._tabId(o);
						o.href = "#" + C;
						var B = w.element.find("#" + C);
						if (!B.length) {
							B = e(k.panelTemplate)
									.attr("id", C)
									.addClass("ui-tabs-panel ui-widget-content ui-corner-bottom")
									.insertAfter(w.panels[y - 1] || w.list);
							B.data("destroy.tabs", true)
						}
						w.panels = w.panels.add(B)
					} else {
						k.disabled.push(y)
					}
				}
			});
			if (v) {
				this.element
						.addClass("ui-tabs ui-widget ui-widget-content ui-corner-all");
				this.list
						.addClass("ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all");
				this.lis.addClass("ui-state-default ui-corner-top");
				this.panels
						.addClass("ui-tabs-panel ui-widget-content ui-corner-bottom");
				if (k.selected === g) {
					if (location.hash) {
						this.anchors.each(function(x, o) {
									if (o.hash == location.hash) {
										k.selected = x;
										return false
									}
								})
					}
					if (typeof k.selected !== "number" && k.cookie) {
						k.selected = parseInt(w._cookie(), 10)
					}
					if (typeof k.selected !== "number"
							&& this.lis.filter(".ui-tabs-selected").length) {
						k.selected = this.lis.index(this.lis
								.filter(".ui-tabs-selected"))
					}
					k.selected = k.selected || (this.lis.length ? 0 : -1)
				} else {
					if (k.selected === null) {
						k.selected = -1
					}
				}
				k.selected = ((k.selected >= 0 && this.anchors[k.selected]) || k.selected < 0)
						? k.selected
						: 0;
				k.disabled = e.unique(k.disabled.concat(e.map(this.lis
								.filter(".ui-state-disabled"), function(x, o) {
							return w.lis.index(x)
						}))).sort();
				if (e.inArray(k.selected, k.disabled) != -1) {
					k.disabled.splice(e.inArray(k.selected, k.disabled), 1)
				}
				this.panels.addClass("ui-tabs-hide");
				this.lis.removeClass("ui-tabs-selected ui-state-active");
				if (k.selected >= 0 && this.anchors.length) {
					w.element.find(w
							._sanitizeSelector(w.anchors[k.selected].hash))
							.removeClass("ui-tabs-hide");
					this.lis.eq(k.selected)
							.addClass("ui-tabs-selected ui-state-active");
					w.element.queue("tabs", function() {
						w
								._trigger(
										"show",
										null,
										w
												._ui(
														w.anchors[k.selected],
														w.element
																.find(w
																		._sanitizeSelector(w.anchors[k.selected].hash))[0]))
					});
					this.load(k.selected)
				}
				e(window).bind("unload", function() {
							w.lis.add(w.anchors).unbind(".tabs");
							w.lis = w.anchors = w.panels = null
						})
			} else {
				k.selected = this.lis.index(this.lis
						.filter(".ui-tabs-selected"))
			}
			this.element[k.collapsible ? "addClass" : "removeClass"]("ui-tabs-collapsible");
			if (k.cookie) {
				this._cookie(k.selected, k.cookie)
			}
			for (var n = 0, u; (u = this.lis[n]); n++) {
				e(u)[e.inArray(n, k.disabled) != -1
						&& !e(u).hasClass("ui-tabs-selected")
						? "addClass"
						: "removeClass"]("ui-state-disabled")
			}
			if (k.cache === false) {
				this.anchors.removeData("cache.tabs")
			}
			this.lis.add(this.anchors).unbind(".tabs");
			if (k.event !== "mouseover") {
				var m = function(x, o) {
					if (o.is(":not(.ui-state-disabled)")) {
						o.addClass("ui-state-" + x)
					}
				};
				var q = function(x, o) {
					o.removeClass("ui-state-" + x)
				};
				this.lis.bind("mouseover.tabs", function() {
							m("hover", e(this))
						});
				this.lis.bind("mouseout.tabs", function() {
							q("hover", e(this))
						});
				this.anchors.bind("focus.tabs", function() {
							m("focus", e(this).closest("li"))
						});
				this.anchors.bind("blur.tabs", function() {
							q("focus", e(this).closest("li"))
						})
			}
			var h, p;
			if (k.fx) {
				if (e.isArray(k.fx)) {
					h = k.fx[0];
					p = k.fx[1]
				} else {
					h = p = k.fx
				}
			}
			function l(o, x) {
				o.css("display", "");
				if (!e.support.opacity && x.opacity) {
					o[0].style.removeAttribute("filter")
				}
			}
			var r = p ? function(o, x) {
				e(o).closest("li").addClass("ui-tabs-selected ui-state-active");
				x.hide().removeClass("ui-tabs-hide").animate(p,
						p.duration || "normal", function() {
							l(x, p);
							w._trigger("show", null, w._ui(o, x[0]))
						})
			}
					: function(o, x) {
						e(o).closest("li")
								.addClass("ui-tabs-selected ui-state-active");
						x.removeClass("ui-tabs-hide");
						w._trigger("show", null, w._ui(o, x[0]))
					};
			var s = h ? function(x, o) {
				o.animate(h, h.duration || "normal", function() {
							w.lis
									.removeClass("ui-tabs-selected ui-state-active");
							o.addClass("ui-tabs-hide");
							l(o, h);
							w.element.dequeue("tabs")
						})
			}
					: function(x, o, y) {
						w.lis.removeClass("ui-tabs-selected ui-state-active");
						o.addClass("ui-tabs-hide");
						w.element.dequeue("tabs")
					};
			this.anchors.bind(k.event + ".tabs", function() {
				var x = this, z = e(x).closest("li"), o = w.panels
						.filter(":not(.ui-tabs-hide)"), y = w.element.find(w
						._sanitizeSelector(x.hash));
				if ((z.hasClass("ui-tabs-selected") && !k.collapsible)
						|| z.hasClass("ui-state-disabled")
						|| z.hasClass("ui-state-processing")
						|| w.panels.filter(":animated").length
						|| w._trigger("select", null, w._ui(this, y[0])) === false) {
					this.blur();
					return false
				}
				k.selected = w.anchors.index(this);
				w.abort();
				if (k.collapsible) {
					if (z.hasClass("ui-tabs-selected")) {
						k.selected = -1;
						if (k.cookie) {
							w._cookie(k.selected, k.cookie)
						}
						w.element.queue("tabs", function() {
									s(x, o)
								}).dequeue("tabs");
						this.blur();
						return false
					} else {
						if (!o.length) {
							if (k.cookie) {
								w._cookie(k.selected, k.cookie)
							}
							w.element.queue("tabs", function() {
										r(x, y)
									});
							w.load(w.anchors.index(this));
							this.blur();
							return false
						}
					}
				}
				if (k.cookie) {
					w._cookie(k.selected, k.cookie)
				}
				if (y.length) {
					if (o.length) {
						w.element.queue("tabs", function() {
									s(x, o)
								})
					}
					w.element.queue("tabs", function() {
								r(x, y)
							});
					w.load(w.anchors.index(this))
				} else {
					throw "jQuery UI Tabs: Mismatching fragment identifier."
				}
				if (e.browser.msie) {
					this.blur()
				}
			});
			this.anchors.bind("click.tabs", function() {
						return false
					})
		},
		_getIndex : function(h) {
			if (typeof h == "string") {
				h = this.anchors
						.index(this.anchors.filter("[href$=" + h + "]"))
			}
			return h
		},
		destroy : function() {
			var h = this.options;
			this.abort();
			this.element
					.unbind(".tabs")
					.removeClass("ui-tabs ui-widget ui-widget-content ui-corner-all ui-tabs-collapsible")
					.removeData("tabs");
			this.list
					.removeClass("ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all");
			this.anchors.each(function() {
						var j = e.data(this, "href.tabs");
						if (j) {
							this.href = j
						}
						var k = e(this).unbind(".tabs");
						e.each(["href", "load", "cache"], function(l, m) {
									k.removeData(m + ".tabs")
								})
					});
			this.lis.unbind(".tabs").add(this.panels).each(function() {
				if (e.data(this, "destroy.tabs")) {
					e(this).remove()
				} else {
					e(this).removeClass(["ui-state-default", "ui-corner-top",
							"ui-tabs-selected", "ui-state-active",
							"ui-state-hover", "ui-state-focus",
							"ui-state-disabled", "ui-tabs-panel",
							"ui-widget-content", "ui-corner-bottom",
							"ui-tabs-hide"].join(" "))
				}
			});
			if (h.cookie) {
				this._cookie(null, h.cookie)
			}
			return this
		},
		add : function(l, k, j) {
			if (j === g) {
				j = this.anchors.length
			}
			var h = this, n = this.options, q = e(n.tabTemplate.replace(
					/#\{href\}/g, l).replace(/#\{label\}/g, k)), p = !l
					.indexOf("#") ? l.replace("#", "") : this
					._tabId(e("a", q)[0]);
			q.addClass("ui-state-default ui-corner-top").data("destroy.tabs",
					true);
			var m = h.element.find("#" + p);
			if (!m.length) {
				m = e(n.panelTemplate).attr("id", p).data("destroy.tabs", true)
			}
			m
					.addClass("ui-tabs-panel ui-widget-content ui-corner-bottom ui-tabs-hide");
			if (j >= this.lis.length) {
				q.appendTo(this.list);
				m.appendTo(this.list[0].parentNode)
			} else {
				q.insertBefore(this.lis[j]);
				m.insertBefore(this.panels[j])
			}
			n.disabled = e.map(n.disabled, function(r, o) {
						return r >= j ? ++r : r
					});
			this._tabify();
			if (this.anchors.length == 1) {
				n.selected = 0;
				q.addClass("ui-tabs-selected ui-state-active");
				m.removeClass("ui-tabs-hide");
				this.element.queue("tabs", function() {
							h._trigger("show", null, h._ui(h.anchors[0],
											h.panels[0]))
						});
				this.load(0)
			}
			this._trigger("add", null, this
							._ui(this.anchors[j], this.panels[j]));
			return this
		},
		remove : function(h) {
			h = this._getIndex(h);
			var k = this.options, l = this.lis.eq(h).remove(), j = this.panels
					.eq(h).remove();
			if (l.hasClass("ui-tabs-selected") && this.anchors.length > 1) {
				this.select(h + (h + 1 < this.anchors.length ? 1 : -1))
			}
			k.disabled = e.map(e.grep(k.disabled, function(o, m) {
								return o != h
							}), function(o, m) {
						return o >= h ? --o : o
					});
			this._tabify();
			this._trigger("remove", null, this._ui(l.find("a")[0], j[0]));
			return this
		},
		enable : function(h) {
			h = this._getIndex(h);
			var j = this.options;
			if (e.inArray(h, j.disabled) == -1) {
				return
			}
			this.lis.eq(h).removeClass("ui-state-disabled");
			j.disabled = e.grep(j.disabled, function(l, k) {
						return l != h
					});
			this._trigger("enable", null, this._ui(this.anchors[h],
							this.panels[h]));
			return this
		},
		disable : function(j) {
			j = this._getIndex(j);
			var h = this, k = this.options;
			if (j != k.selected) {
				this.lis.eq(j).addClass("ui-state-disabled");
				k.disabled.push(j);
				k.disabled.sort();
				this._trigger("disable", null, this._ui(this.anchors[j],
								this.panels[j]))
			}
			return this
		},
		select : function(h) {
			h = this._getIndex(h);
			if (h == -1) {
				if (this.options.collapsible && this.options.selected != -1) {
					h = this.options.selected
				} else {
					return this
				}
			}
			this.anchors.eq(h).trigger(this.options.event + ".tabs");
			return this
		},
		load : function(l) {
			l = this._getIndex(l);
			var j = this, n = this.options, h = this.anchors.eq(l)[0], k = e
					.data(h, "load.tabs");
			this.abort();
			if (!k || this.element.queue("tabs").length !== 0
					&& e.data(h, "cache.tabs")) {
				this.element.dequeue("tabs");
				return
			}
			this.lis.eq(l).addClass("ui-state-processing");
			if (n.spinner) {
				var m = e("span", h);
				m.data("label.tabs", m.html()).html(n.spinner)
			}
			this.xhr = e.ajax(e.extend({}, n.ajaxOptions, {
						url : k,
						success : function(p, o) {
							j.element.find(j._sanitizeSelector(h.hash)).html(p);
							j._cleanup();
							if (n.cache) {
								e.data(h, "cache.tabs", true)
							}
							j._trigger("load", null, j._ui(j.anchors[l],
											j.panels[l]));
							try {
								n.ajaxOptions.success(p, o)
							} catch (q) {
							}
						},
						error : function(q, o, p) {
							j._cleanup();
							j._trigger("load", null, j._ui(j.anchors[l],
											j.panels[l]));
							try {
								n.ajaxOptions.error(q, o, l, h)
							} catch (p) {
							}
						}
					}));
			j.element.dequeue("tabs");
			return this
		},
		abort : function() {
			this.element.queue([]);
			this.panels.stop(false, true);
			this.element
					.queue("tabs", this.element.queue("tabs").splice(-2, 2));
			if (this.xhr) {
				this.xhr.abort();
				delete this.xhr
			}
			this._cleanup();
			return this
		},
		url : function(j, h) {
			this.anchors.eq(j).removeData("cache.tabs").data("load.tabs", h);
			return this
		},
		length : function() {
			return this.anchors.length
		}
	});
	e.extend(e.ui.tabs, {
				version : "1.8.16"
			});
	e.extend(e.ui.tabs.prototype, {
				rotation : null,
				rotate : function(k, m) {
					var h = this, n = this.options;
					var j = h._rotate || (h._rotate = function(o) {
						clearTimeout(h.rotation);
						h.rotation = setTimeout(function() {
									var p = n.selected;
									h.select(++p < h.anchors.length ? p : 0)
								}, k);
						if (o) {
							o.stopPropagation()
						}
					});
					var l = h._unrotate || (h._unrotate = !m ? function(o) {
						if (o.clientX) {
							h.rotate(null)
						}
					} : function(o) {
						t = n.selected;
						j()
					});
					if (k) {
						this.element.bind("tabsshow", j);
						this.anchors.bind(n.event + ".tabs", l);
						j()
					} else {
						clearTimeout(h.rotation);
						this.element.unbind("tabsshow", j);
						this.anchors.unbind(n.event + ".tabs", l);
						delete this._rotate;
						delete this._unrotate
					}
					return this
				}
			})
})(jQuery);
function createHiddenIframe(c, a) {
	var d;
	if (c) {
		d = document.getElementById(c);
		if (d) {
			return d
		}
	}
	if (window.ActiveXObject) {
		d = document
				.createElement('<iframe id="' + c + '" name="' + c + '" />');
		if (typeof a == "boolean") {
			d.src = "javascript:false"
		} else {
			if (typeof a == "string") {
				d.src = a
			}
		}
	} else {
		d = document.createElement("iframe");
		if (c) {
			d.id = c;
			d.name = c
		}
	}
	d.style.position = "absolute";
	d.style.top = "-1000px";
	d.style.left = "-1000px";
	document.body.appendChild(d);
	return d
}
function createHiddenForm(j, f, h) {
	var c = '<form  action="" method="POST"';
	if (j) {
		c += ' name="' + j + '" id="' + j + '"'
	}
	if (f) {
		c += ' enctype="multipart/form-data"'
	}
	c += "></form>";
	var d = jQuery(c);
	if (h) {
		for (var g in h) {
			var e = "";
			if (h[g]) {
				e = h[g].replace(/\"/g, "&quot;")
			}
			var a = jQuery('<input type="hidden" name="' + g + '" value="' + e
					+ '" />');
			a.appendTo(d)
		}
	}
	jQuery(d).css("position", "absolute");
	jQuery(d).css("top", "-1200px");
	jQuery(d).css("left", "-1200px");
	jQuery(d).appendTo("body");
	return d
}
jQuery.extend({
			createUploadIframe : function(e, c) {
				var a = "jUploadFrame" + e;
				var d = createHiddenIframe(a, c);
				return d
			},
			createUploadForm : function(j, c, g) {
				var f = "jUploadForm" + j;
				var a = "jUploadFile" + j;
				var e = createHiddenForm(f, "multipart/form-data", g);
				var d = jQuery("#" + c);
				var h = jQuery(d).clone();
				jQuery(d).attr("id", a);
				jQuery(d).before(h);
				jQuery(d).appendTo(e);
				return e
			},
			ajaxFileUpload : function(m) {
				m = jQuery.extend({}, jQuery.ajaxSettings, m);
				var a = m.fileElementId;
				var c = jQuery.createUploadForm(a, m.fileElementId, m.params);
				var k = jQuery.createUploadIframe(a, m.secureuri);
				var j = "jUploadFrame" + a;
				var l = "jUploadForm" + a;
				if (m.global && !jQuery.active++) {
					jQuery.event.trigger("ajaxStart")
				}
				var d = false;
				var g = {};
				if (m.global) {
					jQuery.event.trigger("ajaxSend", [g, m])
				}
				var f = function(n) {
					var r = document.getElementById(j);
					try {
						if (r.contentWindow) {
							g.responseText = r.contentWindow.document.body
									? r.contentWindow.document.body.innerHTML
									: null;
							g.responseXML = r.contentWindow.document.XMLDocument
									? r.contentWindow.document.XMLDocument
									: r.contentWindow.document
						} else {
							if (r.contentDocument) {
								g.responseText = r.contentDocument.document.body
										? r.contentDocument.document.body.innerHTML
										: null;
								g.responseXML = r.contentDocument.document.XMLDocument
										? r.contentDocument.document.XMLDocument
										: r.contentDocument.document
							}
						}
					} catch (q) {
						jQuery.handleError(m, g, null, q)
					}
					if (g || n == "timeout") {
						d = true;
						var o;
						try {
							o = n != "timeout" ? "success" : "error";
							if (o != "error") {
								var p = jQuery.uploadHttpData(g, m.dataType);
								if (p.error) {
									if (m.error) {
										m.error(p.error)
									}
								} else {
									if (m.success) {
										m.success(p, o)
									}
								}
								if (m.global) {
									jQuery.event.trigger("ajaxSuccess", [g, m])
								}
							} else {
								jQuery.handleError(m, g, o)
							}
						} catch (q) {
							o = "error";
							jQuery.handleError(m, g, o, q)
						}
						if (m.global) {
							jQuery.event.trigger("ajaxComplete", [g, m])
						}
						if (m.global && !--jQuery.active) {
							jQuery.event.trigger("ajaxStop")
						}
						if (m.complete) {
							m.complete(g, o)
						}
						jQuery(r).unbind();
						setTimeout(function() {
									try {
										jQuery(r).remove();
										jQuery(c).remove()
									} catch (s) {
										jQuery.handleError(m, g, null, s)
									}
								}, 100);
						g = null
					}
				};
				if (m.timeout > 0) {
					setTimeout(function() {
								if (!d) {
									f("timeout")
								}
							}, m.timeout)
				}
				try {
					var c = jQuery("#" + l);
					jQuery(c).attr("action", m.url);
					jQuery(c).attr("method", "POST");
					jQuery(c).attr("target", j);
					if (c.encoding) {
						c.encoding = "multipart/form-data"
					} else {
						c.enctype = "multipart/form-data"
					}
					jQuery(c).submit()
				} catch (h) {
					jQuery.handleError(m, g, null, h)
				}
				if (window.attachEvent) {
					document.getElementById(j).attachEvent("onload", f)
				} else {
					document.getElementById(j).addEventListener("load", f,
							false)
				}
				return {
					abort : function() {
					}
				}
			},
			uploadHttpData : function(r, type) {
				var data = !type;
				data = type == "xml" || data ? r.responseXML : r.responseText;
				if (type == "script") {
					jQuery.globalEval(data)
				}
				if (type == "json") {
					eval("data = " + data)
				}
				if (type == "html") {
					jQuery("<div>").html(data).evalScripts()
				}
				return data
			}
		});
var jaaulde = window.jaaulde || {};
jaaulde.utils = jaaulde.utils || {};
jaaulde.utils.cookies = (function() {
	var f, e, d, c, a = {
		expiresAt : null,
		path : "/",
		domain : null,
		secure : false
	};
	f = function(h) {
		var j, g;
		if (typeof h !== "object" || h === null) {
			j = a
		} else {
			j = {
				expiresAt : a.expiresAt,
				path : a.path,
				domain : a.domain,
				secure : a.secure
			};
			if (typeof h.expiresAt === "object" && h.expiresAt instanceof Date) {
				j.expiresAt = h.expiresAt
			} else {
				if (typeof h.hoursToLive === "number" && h.hoursToLive !== 0) {
					g = new Date();
					g.setTime(g.getTime() + (h.hoursToLive * 60 * 60 * 1000));
					j.expiresAt = g
				}
			}
			if (typeof h.path === "string" && h.path !== "") {
				j.path = h.path
			}
			if (typeof h.domain === "string" && h.domain !== "") {
				j.domain = h.domain
			}
			if (h.secure === true) {
				j.secure = h.secure
			}
		}
		return j
	};
	e = function(g) {
		g = f(g);
		return ((typeof g.expiresAt === "object" && g.expiresAt instanceof Date
				? "; expires=" + g.expiresAt.toGMTString()
				: "")
				+ "; path="
				+ g.path
				+ (typeof g.domain === "string" ? "; domain=" + g.domain : "") + (g.secure === true
				? "; secure"
				: ""))
	};
	d = function() {
		var p = {}, j, h, g, o, l = document.cookie.split(";"), k;
		for (j = 0; j < l.length; j = j + 1) {
			h = l[j].split("=");
			g = h[0].replace(/^\s*/, "").replace(/\s*$/, "");
			try {
				o = decodeURIComponent(h[1])
			} catch (n) {
				o = h[1]
			}
			if (typeof JSON === "object" && JSON !== null
					&& typeof JSON.parse === "function") {
				try {
					k = o;
					o = JSON.parse(o)
				} catch (m) {
					o = k
				}
			}
			p[g] = o
		}
		return p
	};
	c = function() {
	};
	c.prototype.get = function(k) {
		var g, j, h = d();
		if (typeof k === "string") {
			g = (typeof h[k] !== "undefined") ? h[k] : null
		} else {
			if (typeof k === "object" && k !== null) {
				g = {};
				for (j in k) {
					if (typeof h[k[j]] !== "undefined") {
						g[k[j]] = h[k[j]]
					} else {
						g[k[j]] = null
					}
				}
			} else {
				g = h
			}
		}
		return g
	};
	c.prototype.filter = function(g) {
		var k, h = {}, j = d();
		if (typeof g === "string") {
			g = new RegExp(g)
		}
		for (k in j) {
			if (k.match(g)) {
				h[k] = j[k]
			}
		}
		return h
	};
	c.prototype.set = function(k, h, g) {
		if (typeof g !== "object" || g === null) {
			g = {}
		}
		if (typeof h === "undefined" || h === null) {
			h = "";
			g.hoursToLive = -8760
		} else {
			if (typeof h !== "string") {
				if (typeof JSON === "object" && JSON !== null
						&& typeof JSON.stringify === "function") {
					h = JSON.stringify(h)
				} else {
					throw new Error("cookies.set() received non-string value and could not serialize.")
				}
			}
		}
		var j = e(g);
		document.cookie = k + "=" + encodeURIComponent(h) + j
	};
	c.prototype.del = function(k, j) {
		var g = {}, h;
		if (typeof j !== "object" || j === null) {
			j = {}
		}
		if (typeof k === "boolean" && k === true) {
			g = this.get()
		} else {
			if (typeof k === "string") {
				g[k] = true
			}
		}
		for (h in g) {
			if (typeof h === "string" && h !== "") {
				this.set(h, null, j)
			}
		}
	};
	c.prototype.test = function() {
		var h = false, g = "cT", j = "data";
		this.set(g, j);
		if (this.get(g) === j) {
			this.del(g);
			h = true
		}
		return h
	};
	c.prototype.setOptions = function(g) {
		if (typeof g !== "object") {
			g = null
		}
		a = f(g)
	};
	return new c()
})();
(function() {
	if (window.jQuery) {
		(function(c) {
			c.cookies = jaaulde.utils.cookies;
			var a = {
				cookify : function(d) {
					return this.each(function() {
								var f, h = ["name", "id"], e, j = c(this), g;
								for (f in h) {
									if (!isNaN(f)) {
										e = j.attr(h[f]);
										if (typeof e === "string" && e !== "") {
											if (j.is(":checkbox, :radio")) {
												if (j.attr("checked")) {
													g = j.val()
												}
											} else {
												if (j.is(":input")) {
													g = j.val()
												} else {
													g = j.html()
												}
											}
											if (typeof g !== "string"
													|| g === "") {
												g = null
											}
											c.cookies.set(e, g, d);
											break
										}
									}
								}
							})
				},
				cookieFill : function() {
					return this.each(function() {
								var j, d, g = ["name", "id"], e, h = c(this), f;
								d = function() {
									j = g.pop();
									return !!j
								};
								while (d()) {
									e = h.attr(j);
									if (typeof e === "string" && e !== "") {
										f = c.cookies.get(e);
										if (f !== null) {
											if (h.is(":checkbox, :radio")) {
												if (h.val() === f) {
													h
															.attr("checked",
																	"checked")
												} else {
													h.removeAttr("checked")
												}
											} else {
												if (h.is(":input")) {
													h.val(f)
												} else {
													h.html(f)
												}
											}
										}
										break
									}
								}
							})
				},
				cookieBind : function(d) {
					return this.each(function() {
								var e = c(this);
								e.cookieFill().change(function() {
											e.cookify(d)
										})
							})
				}
			};
			c.each(a, function(d) {
						c.fn[d] = this
					})
		})(window.jQuery)
	}
})();
(function(a) {
	a.fn.linkbutton = function(c) {
		function d(f) {
			a(f).addClass("l-btn");
			if (a.trim(a(f).html().replace(/&nbsp;/g, " ")) == "") {
				if (f.nodeName != "INPUT" && f.nodeName != "BUTTON") {
					a(f)
							.html("&nbsp;")
							.wrapInner('<span class="l-btn-left"><span class="l-btn-text"><span class="l-btn-empty"></span></span></span>');
					var e = a(f).attr("icon");
					if (e) {
						a(".l-btn-empty", f).addClass(e)
					}
				}
			} else {
				a(f)
						.wrapInner('<span class="l-btn-left"><span class="l-btn-text"></span></span>');
				var g = a(".l-btn-text", f);
				var e = a(f).attr("icon");
				if (e) {
					g.addClass(e).css("padding-left", "20px")
				}
			}
		}
		return this.each(function() {
					var g;
					var h = a.data(this, "linkbutton");
					if (h) {
						g = a.extend(h.options, c || {});
						h.options = g
					} else {
						d(this);
						g = a.extend({}, a.fn.linkbutton.defaults, c || {});
						if (a(this).attr("plain") == "true") {
							g.plain = true
						}
						if (a(this).attr("disabled")) {
							g.disabled = true;
							a(this).removeAttr("disabled")
						}
						h = {
							options : g
						}
					}
					if (h.options.disabled) {
						var e = a(this).attr("href");
						if (e) {
							h.href = e;
							a(this).removeAttr("href")
						}
						var f = a(this).attr("onclick");
						if (f) {
							h.onclick = f;
							a(this).attr("onclick", null)
						}
						a(this).addClass("l-btn-disabled")
					} else {
						if (h.href) {
							a(this).attr("href", h.href)
						}
						if (h.onclick) {
							this.onclick = h.onclick
						}
						a(this).removeClass("l-btn-disabled")
					}
					if (h.options.plain == true) {
						a(this).addClass("l-btn-plain")
					} else {
						a(this).removeClass("l-btn-plain")
					}
					a.data(this, "linkbutton", h)
				})
	};
	a.fn.linkbutton.defaults = {
		disabled : false,
		plain : false
	}
})(jQuery);
(function(a) {
	a.fn.resizable = function(e) {
		function d(n) {
			var m = n.data;
			var k = a.data(m.target, "resizable").options;
			if (m.dir.indexOf("e") != -1) {
				var l = m.startWidth + n.pageX - m.startX;
				l = Math.min(Math.max(l, k.minWidth), k.maxWidth);
				m.width = l
			}
			if (m.dir.indexOf("s") != -1) {
				var j = m.startHeight + n.pageY - m.startY;
				j = Math.min(Math.max(j, k.minHeight), k.maxHeight);
				m.height = j
			}
			if (m.dir.indexOf("w") != -1) {
				m.width = m.startWidth - n.pageX + m.startX;
				if (m.width >= k.minWidth && m.width <= k.maxWidth) {
					m.left = m.startLeft + n.pageX - m.startX
				}
			}
			if (m.dir.indexOf("n") != -1) {
				m.height = m.startHeight - n.pageY + m.startY;
				if (m.height >= k.minHeight && m.height <= k.maxHeight) {
					m.top = m.startTop + n.pageY - m.startY
				}
			}
		}
		function g(l) {
			var k = l.data;
			var j = k.target;
			if (a.boxModel == true) {
				a(j).css({
							width : k.width - k.deltaWidth,
							height : k.height - k.deltaHeight,
							left : k.left,
							top : k.top
						})
			} else {
				a(j).css({
							width : k.width,
							height : k.height,
							left : k.left,
							top : k.top
						})
			}
		}
		function c(j) {
			a.data(j.data.target, "resizable").options.onStartResize.call(
					j.data.target, j);
			return false
		}
		function f(j) {
			d(j);
			if (a.data(j.data.target, "resizable").options.onResize.call(
					j.data.target, j) != false) {
				g(j)
			}
			return false
		}
		function h(j) {
			d(j, true);
			g(j);
			a(document).unbind(".resizable");
			a.data(j.data.target, "resizable").options.onStopResize.call(
					j.data.target, j);
			return false
		}
		return this.each(function() {
					var k = null;
					var m = a.data(this, "resizable");
					if (m) {
						a(this).unbind(".resizable");
						k = a.extend(m.options, e || {})
					} else {
						k = a.extend({}, a.fn.resizable.defaults, e || {})
					}
					if (k.disabled == true) {
						return
					}
					a.data(this, "resizable", {
								options : k
							});
					var o = this;
					a(this).bind("mousemove.resizable", p).bind(
							"mousedown.resizable", n);
					function p(r) {
						var q = j(r);
						if (q == "") {
							a(o).css("cursor", "default")
						} else {
							a(o).css("cursor", q + "-resize")
						}
					}
					function n(s) {
						var q = j(s);
						if (q == "") {
							return
						}
						var r = {
							target : this,
							dir : q,
							startLeft : l("left"),
							startTop : l("top"),
							left : l("left"),
							top : l("top"),
							startX : s.pageX,
							startY : s.pageY,
							startWidth : a(o).outerWidth(),
							startHeight : a(o).outerHeight(),
							width : a(o).outerWidth(),
							height : a(o).outerHeight(),
							deltaWidth : a(o).outerWidth() - a(o).width(),
							deltaHeight : a(o).outerHeight() - a(o).height()
						};
						a(document).bind("mousedown.resizable", r, c);
						a(document).bind("mousemove.resizable", r, f);
						a(document).bind("mouseup.resizable", r, h)
					}
					function j(x) {
						var s = "";
						var u = a(o).offset();
						var q = a(o).outerWidth();
						var z = a(o).outerHeight();
						var r = k.edge;
						if (x.pageY > u.top && x.pageY < u.top + r) {
							s += "n"
						} else {
							if (x.pageY < u.top + z && x.pageY > u.top + z - r) {
								s += "s"
							}
						}
						if (x.pageX > u.left && x.pageX < u.left + r) {
							s += "w"
						} else {
							if (x.pageX < u.left + q
									&& x.pageX > u.left + q - r) {
								s += "e"
							}
						}
						var y = k.handles.split(",");
						for (var v = 0; v < y.length; v++) {
							var w = y[v].replace(/(^\s*)|(\s*$)/g, "");
							if (w == "all" || w == s) {
								return s
							}
						}
						return ""
					}
					function l(q) {
						var r = parseInt(a(o).css(q));
						if (isNaN(r)) {
							return 0
						} else {
							return r
						}
					}
				})
	};
	a.fn.resizable.defaults = {
		disabled : false,
		handles : "n, e, s, w, ne, se, sw, nw, all",
		minWidth : 10,
		minHeight : 10,
		maxWidth : 10000,
		maxHeight : 10000,
		edge : 5,
		onStartResize : function(c) {
		},
		onResize : function(c) {
		},
		onStopResize : function(c) {
		}
	}
})(jQuery);
(function(g) {
	function d(m) {
		var h = g.data(m.data.target, "draggable").options;
		var l = m.data;
		var k = l.startLeft + m.pageX - l.startX;
		var j = l.startTop + m.pageY - l.startY;
		if (h.deltaX != null && h.deltaX != undefined) {
			k = m.pageX + h.deltaX
		}
		if (h.deltaY != null && h.deltaY != undefined) {
			j = m.pageY + h.deltaY
		}
		if (m.data.parnet != document.body) {
			if (g.boxModel == true) {
				k += g(m.data.parent).scrollLeft();
				j += g(m.data.parent).scrollTop()
			}
		}
		if (h.axis == "h") {
			l.left = k
		} else {
			if (h.axis == "v") {
				l.top = j
			} else {
				l.left = k;
				l.top = j
			}
		}
	}
	function f(k) {
		var j = g.data(k.data.target, "draggable").options;
		var h = g.data(k.data.target, "draggable").proxy;
		if (h) {
			h.css("cursor", j.cursor)
		} else {
			h = g(k.data.target);
			g.data(k.data.target, "draggable").handle.css("cursor", j.cursor)
		}
		h.css({
					left : k.data.left,
					top : k.data.top
				})
	}
	function a(l) {
		var k = g.data(l.data.target, "draggable").options;
		var h = g(".droppable").filter(function() {
					return l.data.target != this
				}).filter(function() {
					var m = g.data(this, "droppable").options.accept;
					if (m) {
						return g(m).filter(function() {
									return this == l.data.target
								}).length > 0
					} else {
						return true
					}
				});
		g.data(l.data.target, "draggable").droppables = h;
		var j = g.data(l.data.target, "draggable").proxy;
		if (!j) {
			if (k.proxy) {
				if (k.proxy == "clone") {
					j = g(l.data.target).clone().insertAfter(l.data.target)
				} else {
					j = k.proxy()
				}
				g.data(l.data.target, "draggable").proxy = j
			} else {
				j = g(l.data.target)
			}
		}
		j.css("position", "absolute");
		d(l);
		f(l);
		k.onStartDrag.call(l.data.target, l);
		return false
	}
	function c(j) {
		d(j);
		if (g.data(j.data.target, "draggable").options.onDrag.call(
				j.data.target, j) != false) {
			f(j)
		}
		var h = j.data.target;
		g.data(j.data.target, "draggable").droppables.each(function() {
					var l = g(this);
					var k = g(this).offset();
					if (j.pageX > k.left && j.pageX < k.left + l.outerWidth()
							&& j.pageY > k.top
							&& j.pageY < k.top + l.outerHeight()) {
						if (!this.entered) {
							g(this).trigger("_dragenter", [h]);
							this.entered = true
						}
						g(this).trigger("_dragover", [h])
					} else {
						if (this.entered) {
							g(this).trigger("_dragleave", [h]);
							this.entered = false
						}
					}
				});
		return false
	}
	function e(m) {
		d(m);
		var h = g.data(m.data.target, "draggable").proxy;
		var k = g.data(m.data.target, "draggable").options;
		if (k.revert) {
			if (l() == true) {
				j();
				g(m.data.target).css({
							position : m.data.startPosition,
							left : m.data.startLeft,
							top : m.data.startTop
						})
			} else {
				if (h) {
					h.animate({
								left : m.data.startLeft,
								top : m.data.startTop
							}, function() {
								j()
							})
				} else {
					g(m.data.target).animate({
								left : m.data.startLeft,
								top : m.data.startTop
							}, function() {
								g(m.data.target).css("position",
										m.data.startPosition)
							})
				}
			}
		} else {
			g(m.data.target).css({
						position : "absolute",
						left : m.data.left,
						top : m.data.top
					});
			j();
			l()
		}
		k.onStopDrag.call(m.data.target, m);
		function j() {
			if (h) {
				h.remove()
			}
			g.data(m.data.target, "draggable").proxy = null
		}
		function l() {
			var n = false;
			g.data(m.data.target, "draggable").droppables.each(function() {
				var p = g(this);
				var o = g(this).offset();
				if (m.pageX > o.left && m.pageX < o.left + p.outerWidth()
						&& m.pageY > o.top && m.pageY < o.top + p.outerHeight()) {
					if (k.revert) {
						g(m.data.target).css({
									position : m.data.startPosition,
									left : m.data.startLeft,
									top : m.data.startTop
								})
					}
					g(this).trigger("_drop", [m.data.target]);
					n = true;
					this.entered = false
				}
			});
			return n
		}
		g(document).unbind(".draggable");
		return false
	}
	g.fn.draggable = function(h) {
		if (typeof h == "string") {
			switch (h) {
				case "options" :
					return g.data(this[0], "draggable").options;
				case "proxy" :
					return g.data(this[0], "draggable").proxy;
				case "enable" :
					return this.each(function() {
								g(this).draggable({
											disabled : false
										})
							});
				case "disable" :
					return this.each(function() {
								g(this).draggable({
											disabled : true
										})
							})
			}
		}
		return this.each(function() {
					var k;
					var m = g.data(this, "draggable");
					if (m) {
						m.handle.unbind(".draggable");
						k = g.extend(m.options, h)
					} else {
						k = g.extend({}, g.fn.draggable.defaults, h || {})
					}
					if (k.disabled == true) {
						g(this).css("cursor", "default");
						return
					}
					var l = null;
					if (typeof k.handle == "undefined" || k.handle == null) {
						l = g(this)
					} else {
						l = (typeof k.handle == "string"
								? g(k.handle, this)
								: l)
					}
					g.data(this, "draggable", {
								options : k,
								handle : l
							});
					l.bind("mousedown.draggable", {
								target : this
							}, n);
					l.bind("mousemove.draggable", {
								target : this
							}, o);
					function n(r) {
						if (j(r) == false) {
							return
						}
						var p = g(r.data.target).position();
						var q = {
							startPosition : g(r.data.target).css("position"),
							startLeft : p.left,
							startTop : p.top,
							left : p.left,
							top : p.top,
							startX : r.pageX,
							startY : r.pageY,
							target : r.data.target,
							parent : g(r.data.target).parent()[0]
						};
						g(document).bind("mousedown.draggable", q, a);
						g(document).bind("mousemove.draggable", q, c);
						g(document).bind("mouseup.draggable", q, e)
					}
					function o(p) {
						if (j(p)) {
							g(this).css("cursor", k.cursor)
						} else {
							g(this).css("cursor", "default")
						}
					}
					function j(x) {
						var y = g(l).offset();
						var v = g(l).outerWidth();
						var q = g(l).outerHeight();
						var u = x.pageY - y.top;
						var w = y.left + v - x.pageX;
						var p = y.top + q - x.pageY;
						var s = x.pageX - y.left;
						return Math.min(u, w, p, s) > k.edge
					}
				})
	};
	g.fn.draggable.defaults = {
		proxy : null,
		revert : false,
		cursor : "move",
		deltaX : null,
		deltaY : null,
		handle : null,
		disabled : false,
		edge : 0,
		axis : null,
		onStartDrag : function(h) {
		},
		onDrag : function(h) {
		},
		onStopDrag : function(h) {
		}
	}
})(jQuery);
(function($) {
	function removeNode(node) {
		node.each(function() {
					$(this).remove();
					if ($.browser.msie) {
						this.outerHTML = ""
					}
				})
	}
	function setSize(target, param) {
		var opts = $.data(target, "panel").options;
		var panel = $.data(target, "panel").panel;
		var pheader = panel.find(">div.panel-header");
		var pbody = panel.find(">div.panel-body");
		if (param) {
			if (param.width) {
				opts.width = param.width
			}
			if (param.height) {
				opts.height = param.height
			}
			if (param.left != null) {
				opts.left = param.left
			}
			if (param.top != null) {
				opts.top = param.top
			}
		}
		if (opts.fit == true) {
			var p = panel.parent();
			opts.width = p.width();
			opts.height = p.height()
		}
		panel.css({
					left : opts.left,
					top : opts.top
				});
		panel.css(opts.style);
		panel.addClass(opts.cls);
		pheader.addClass(opts.headerCls);
		pbody.addClass(opts.bodyCls);
		if (!isNaN(opts.width)) {
			if ($.boxModel == true) {
				panel.width(opts.width - (panel.outerWidth() - panel.width()));
				pheader.width(panel.width()
						- (pheader.outerWidth() - pheader.width()));
				pbody.width(panel.width()
						- (pbody.outerWidth() - pbody.width()))
			} else {
				panel.width(opts.width);
				pheader.width(panel.width());
				pbody.width(panel.width())
			}
		} else {
			panel.width("auto");
			pbody.width("auto")
		}
		if (!isNaN(opts.height)) {
			if ($.boxModel == true) {
				panel.height(opts.height
						- (panel.outerHeight() - panel.height()));
				pbody.height(panel.height() - pheader.outerHeight()
						- (pbody.outerHeight() - pbody.height()))
			} else {
				panel.height(opts.height);
				pbody.height(panel.height() - pheader.outerHeight())
			}
		} else {
			pbody.height("auto")
		}
		panel.css("height", null);
		opts.onResize.apply(target, [opts.width, opts.height]);
		panel.find(">div.panel-body>div").triggerHandler("_resize")
	}
	function movePanel(target, param) {
		var opts = $.data(target, "panel").options;
		var panel = $.data(target, "panel").panel;
		if (param) {
			if (param.left != null) {
				opts.left = param.left
			}
			if (param.top != null) {
				opts.top = param.top
			}
		}
		panel.css({
					left : opts.left,
					top : opts.top
				});
		opts.onMove.apply(target, [opts.left, opts.top])
	}
	function wrapPanel(target) {
		var panel = $(target).addClass("panel-body")
				.wrap('<div class="panel"></div>').parent();
		panel.bind("_resize", function() {
					var opts = $.data(target, "panel").options;
					if (opts.fit == true) {
						setSize(target)
					}
					return false
				});
		return panel
	}
	function addHeader(target) {
		var opts = $.data(target, "panel").options;
		var panel = $.data(target, "panel").panel;
		removeNode(panel.find(">div.panel-header"));
		if (opts.title) {
			var header = $('<div class="panel-header"><div class="panel-title">'
					+ opts.title + "</div></div>").prependTo(panel);
			if (opts.iconCls) {
				header.find(".panel-title").addClass("panel-with-icon");
				$('<div class="panel-icon"></div>').addClass(opts.iconCls)
						.appendTo(header)
			}
			var tool = $('<div class="panel-tool"></div>').appendTo(header);
			if (opts.closable) {
				$('<div class="panel-tool-close">&nbsp;&nbsp;&nbsp;&nbsp;</div>')
						.appendTo(tool).bind("click", onClose)
			}
			if (opts.maximizable) {
				$('<div class="panel-tool-max"></div>').appendTo(tool).bind(
						"click", onMax)
			}
			if (opts.minimizable) {
				$('<div class="panel-tool-min"></div>').appendTo(tool).bind(
						"click", onMin)
			}
			if (opts.collapsible) {
				$('<div class="panel-tool-collapse"></div>').appendTo(tool)
						.bind("click", onToggle)
			}
			if (opts.tools) {
				for (var i = opts.tools.length - 1; i >= 0; i--) {
					var t = $("<div></div>").addClass(opts.tools[i].iconCls)
							.appendTo(tool);
					if (opts.tools[i].handler) {
						t.bind("click", eval(opts.tools[i].handler))
					}
				}
			}
			tool.find("div").hover(function() {
						$(this).addClass("panel-tool-over")
					}, function() {
						$(this).removeClass("panel-tool-over")
					});
			panel.find(">div.panel-body").removeClass("panel-body-noheader")
		} else {
			panel.find(">div.panel-body").addClass("panel-body-noheader")
		}
		function onToggle() {
			if ($(this).hasClass("panel-tool-expand")) {
				expandPanel(target, true)
			} else {
				collapsePanel(target, true)
			}
			return false
		}
		function onMin() {
			minimizePanel(target);
			return false
		}
		function onMax() {
			if ($(this).hasClass("panel-tool-restore")) {
				restorePanel(target)
			} else {
				maximizePanel(target)
			}
			return false
		}
		function onClose() {
			closePanel(target);
			return false
		}
	}
	function loadData(target) {
		var state = $.data(target, "panel");
		if (state.options.href && !state.isLoaded) {
			state.isLoaded = false;
			var pbody = state.panel.find(">.panel-body");
			pbody.html($('<div class="panel-loading"></div>')
					.html(state.options.loadingMessage));
			pbody.load(state.options.href, null, function() {
						if ($.parser) {
							$.parser.parse(pbody)
						}
						state.options.onLoad.apply(target, arguments);
						state.isLoaded = true
					})
		}
	}
	function openPanel(target, forceOpen) {
		var opts = $.data(target, "panel").options;
		var panel = $.data(target, "panel").panel;
		if (forceOpen != true) {
			if (opts.onBeforeOpen.call(target) == false) {
				return
			}
		}
		panel.show();
		opts.closed = false;
		opts.onOpen.call(target);
		if (opts.maximized == true) {
			maximizePanel(target)
		}
		if (opts.minimized == true) {
			minimizePanel(target)
		}
		if (opts.collapsed == true) {
			collapsePanel(target)
		}
	}
	function closePanel(target, forceClose) {
		var opts = $.data(target, "panel").options;
		var panel = $.data(target, "panel").panel;
		if (forceClose != true) {
			if (opts.onBeforeClose.call(target) == false) {
				return
			}
		}
		panel.hide();
		opts.closed = true;
		opts.onClose.call(target)
	}
	function destroyPanel(target, forceDestroy) {
		var opts = $.data(target, "panel").options;
		var panel = $.data(target, "panel").panel;
		if (forceDestroy != true) {
			if (opts.onBeforeDestroy.call(target) == false) {
				return
			}
		}
		removeNode(panel);
		opts.onDestroy.call(target)
	}
	function collapsePanel(target, animate) {
		var opts = $.data(target, "panel").options;
		var panel = $.data(target, "panel").panel;
		var body = panel.find(">div.panel-body");
		var tool = panel.find(">div.panel-header .panel-tool-collapse");
		if (tool.hasClass("panel-tool-expand")) {
			return
		}
		body.stop(true, true);
		if (opts.onBeforeCollapse.call(target) == false) {
			return
		}
		tool.addClass("panel-tool-expand");
		if (animate == true) {
			body.slideUp("normal", function() {
						opts.collapsed = true;
						opts.onCollapse.call(target)
					})
		} else {
			body.hide();
			opts.collapsed = true;
			opts.onCollapse.call(target)
		}
	}
	function expandPanel(target, animate) {
		var opts = $.data(target, "panel").options;
		var panel = $.data(target, "panel").panel;
		var body = panel.find(">div.panel-body");
		var tool = panel.find(">div.panel-header .panel-tool-collapse");
		if (!tool.hasClass("panel-tool-expand")) {
			return
		}
		body.stop(true, true);
		if (opts.onBeforeExpand.call(target) == false) {
			return
		}
		tool.removeClass("panel-tool-expand");
		if (animate == true) {
			body.slideDown("normal", function() {
						opts.collapsed = false;
						opts.onExpand.call(target)
					})
		} else {
			body.show();
			opts.collapsed = false;
			opts.onExpand.call(target)
		}
	}
	function maximizePanel(target) {
		var opts = $.data(target, "panel").options;
		var panel = $.data(target, "panel").panel;
		var tool = panel.find(">div.panel-header .panel-tool-max");
		if (tool.hasClass("panel-tool-restore")) {
			return
		}
		tool.addClass("panel-tool-restore");
		$.data(target, "panel").original = {
			width : opts.width,
			height : opts.height,
			left : opts.left,
			top : opts.top,
			fit : opts.fit
		};
		opts.left = 0;
		opts.top = 0;
		opts.fit = true;
		setSize(target);
		opts.minimized = false;
		opts.maximized = true;
		opts.onMaximize.call(target)
	}
	function minimizePanel(target) {
		var opts = $.data(target, "panel").options;
		var panel = $.data(target, "panel").panel;
		panel.hide();
		opts.minimized = true;
		opts.maximized = false;
		opts.onMinimize.call(target)
	}
	function restorePanel(target) {
		var opts = $.data(target, "panel").options;
		var panel = $.data(target, "panel").panel;
		var tool = panel.find(">div.panel-header .panel-tool-max");
		if (!tool.hasClass("panel-tool-restore")) {
			return
		}
		panel.show();
		tool.removeClass("panel-tool-restore");
		var original = $.data(target, "panel").original;
		opts.width = original.width;
		opts.height = original.height;
		opts.left = original.left;
		opts.top = original.top;
		opts.fit = original.fit;
		setSize(target);
		opts.minimized = false;
		opts.maximized = false;
		opts.onRestore.call(target)
	}
	function setBorder(target) {
		var opts = $.data(target, "panel").options;
		var panel = $.data(target, "panel").panel;
		if (opts.border == true) {
			panel.find(">div.panel-header")
					.removeClass("panel-header-noborder");
			panel.find(">div.panel-body").removeClass("panel-body-noborder")
		} else {
			panel.find(">div.panel-header").addClass("panel-header-noborder");
			panel.find(">div.panel-body").addClass("panel-body-noborder")
		}
	}
	function setTitle(target, title) {
		$.data(target, "panel").options.title = title;
		$(target).panel("header").find("div.panel-title").html(title)
	}
	$.fn.panel = function(options, param) {
		if (typeof options == "string") {
			switch (options) {
				case "options" :
					return $.data(this[0], "panel").options;
				case "panel" :
					return $.data(this[0], "panel").panel;
				case "header" :
					return $.data(this[0], "panel").panel
							.find(">div.panel-header");
				case "body" :
					return $.data(this[0], "panel").panel
							.find(">div.panel-body");
				case "setTitle" :
					return this.each(function() {
								setTitle(this, param)
							});
				case "open" :
					return this.each(function() {
								openPanel(this, param)
							});
				case "close" :
					return this.each(function() {
								closePanel(this, param)
							});
				case "destroy" :
					return this.each(function() {
								destroyPanel(this, param)
							});
				case "refresh" :
					return this.each(function() {
								$.data(this, "panel").isLoaded = false;
								loadData(this)
							});
				case "resize" :
					return this.each(function() {
								setSize(this, param)
							});
				case "move" :
					return this.each(function() {
								movePanel(this, param)
							});
				case "maximize" :
					return this.each(function() {
								maximizePanel(this)
							});
				case "minimize" :
					return this.each(function() {
								minimizePanel(this)
							});
				case "restore" :
					return this.each(function() {
								restorePanel(this)
							});
				case "collapse" :
					return this.each(function() {
								collapsePanel(this, param)
							});
				case "expand" :
					return this.each(function() {
								expandPanel(this, param)
							})
			}
		}
		options = options || {};
		return this.each(function() {
			var state = $.data(this, "panel");
			var opts;
			if (state) {
				opts = $.extend(state.options, options)
			} else {
				var t = $(this);
				opts = $.extend({}, $.fn.panel.defaults, {
					width : (parseInt(t.css("width")) || undefined),
					height : (parseInt(t.css("height")) || undefined),
					left : (parseInt(t.css("left")) || undefined),
					top : (parseInt(t.css("top")) || undefined),
					title : t.attr("title"),
					iconCls : t.attr("icon"),
					cls : t.attr("cls"),
					headerCls : t.attr("headerCls"),
					bodyCls : t.attr("bodyCls"),
					href : t.attr("href"),
					fit : (t.attr("fit") ? t.attr("fit") == "true" : undefined),
					border : (t.attr("border")
							? t.attr("border") == "true"
							: undefined),
					collapsible : (t.attr("collapsible") ? t
							.attr("collapsible") == "true" : undefined),
					minimizable : (t.attr("minimizable") ? t
							.attr("minimizable") == "true" : undefined),
					maximizable : (t.attr("maximizable") ? t
							.attr("maximizable") == "true" : undefined),
					closable : (t.attr("closable")
							? t.attr("closable") == "true"
							: undefined),
					collapsed : (t.attr("collapsed")
							? t.attr("collapsed") == "true"
							: undefined),
					minimized : (t.attr("minimized")
							? t.attr("minimized") == "true"
							: undefined),
					maximized : (t.attr("maximized")
							? t.attr("maximized") == "true"
							: undefined),
					closed : (t.attr("closed")
							? t.attr("closed") == "true"
							: undefined)
				}, options);
				t.attr("title", "");
				state = $.data(this, "panel", {
							options : opts,
							panel : wrapPanel(this),
							isLoaded : false
						})
			}
			addHeader(this);
			setBorder(this);
			loadData(this);
			if (opts.doSize == true) {
				state.panel.css("display", "block");
				setSize(this)
			}
			if (opts.closed == true) {
				state.panel.hide()
			} else {
				openPanel(this)
			}
		})
	};
	$.fn.panel.defaults = {
		title : null,
		iconCls : null,
		width : "auto",
		height : "auto",
		left : null,
		top : null,
		cls : null,
		headerCls : null,
		bodyCls : null,
		style : {},
		fit : false,
		border : true,
		doSize : true,
		collapsible : false,
		minimizable : false,
		maximizable : false,
		closable : false,
		collapsed : false,
		minimized : false,
		maximized : false,
		closed : false,
		tools : [],
		href : null,
		loadingMessage : "Loading...",
		onLoad : function() {
		},
		onBeforeOpen : function() {
		},
		onOpen : function() {
		},
		onBeforeClose : function() {
		},
		onClose : function() {
		},
		onBeforeDestroy : function() {
		},
		onDestroy : function() {
		},
		onResize : function(width, height) {
		},
		onMove : function(left, top) {
		},
		onMaximize : function() {
		},
		onRestore : function() {
		},
		onMinimize : function() {
		},
		onBeforeCollapse : function() {
		},
		onBeforeExpand : function() {
		},
		onCollapse : function() {
		},
		onExpand : function() {
		}
	}
})(jQuery);
(function(c) {
	function f(g, h) {
		c(g).panel("resize")
	}
	function e(n, h) {
		var l = c.data(n, "window");
		var k;
		if (l) {
			k = c.extend(l.opts, h)
		} else {
			k = c.extend({}, c.fn.window.defaults, {
						title : c(n).attr("title"),
						collapsible : (c(n).attr("collapsible") == "false"
								? false
								: true),
						minimizable : (c(n).attr("minimizable") == "false"
								? false
								: true),
						maximizable : (c(n).attr("maximizable") == "false"
								? false
								: true),
						closable : (c(n).attr("closable") == "false"
								? false
								: true),
						closed : c(n).attr("closed") == "true",
						shadow : (c(n).attr("shadow") == "false" ? false : true),
						modal : c(n).attr("modal") == "true"
					}, h);
			c(n).attr("title", "");
			l = c.data(n, "window", {})
		}
		var m = c(n).panel(c.extend({}, k, {
					border : false,
					doSize : true,
					closed : true,
					cls : "window",
					headerCls : "window-header",
					bodyCls : "window-body",
					onBeforeDestroy : function() {
						if (k.onBeforeDestroy) {
							if (k.onBeforeDestroy.call(n) == false) {
								return false
							}
						}
						var o = c.data(n, "window");
						if (o.shadow) {
							o.shadow.remove()
						}
						if (o.mask) {
							o.mask.remove()
						}
					},
					onClose : function() {
						var o = c.data(n, "window");
						if (o.shadow) {
							o.shadow.hide()
						}
						if (o.mask) {
							o.mask.hide()
						}
						if (k.onClose) {
							k.onClose.call(n)
						}
					},
					onOpen : function() {
						var o = c.data(n, "window");
						if (o.shadow) {
							o.shadow.css({
										display : "block",
										left : o.options.left,
										top : o.options.top,
										width : o.window.outerWidth(),
										height : o.window.outerHeight()
									})
						}
						if (o.mask) {
							o.mask.show()
						}
						if (k.onOpen) {
							k.onOpen.call(n)
						}
					},
					onResize : function(p, o) {
						var q = c.data(n, "window");
						if (q.shadow) {
							q.shadow.css({
										left : q.options.left,
										top : q.options.top,
										width : q.window.outerWidth(),
										height : q.window.outerHeight()
									})
						}
						if (k.onResize) {
							k.onResize.call(n, p, o)
						}
					},
					onMove : function(q, p) {
						var o = c.data(n, "window");
						if (o.shadow) {
							o.shadow.css({
										left : o.options.left,
										top : o.options.top
									})
						}
						if (k.onMove) {
							k.onMove.call(n, q, p)
						}
					},
					onMinimize : function() {
						var o = c.data(n, "window");
						if (o.shadow) {
							o.shadow.hide()
						}
						if (o.mask) {
							o.mask.hide()
						}
						if (k.onMinimize) {
							k.onMinimize.call(n)
						}
					},
					onBeforeCollapse : function() {
						if (k.onBeforeCollapse) {
							if (k.onBeforeCollapse.call(n) == false) {
								return false
							}
						}
						var o = c.data(n, "window");
						if (o.shadow) {
							o.shadow.hide()
						}
					},
					onExpand : function() {
						var o = c.data(n, "window");
						if (o.shadow) {
							o.shadow.show()
						}
						if (k.onExpand) {
							k.onExpand.call(n)
						}
					}
				}));
		l.options = m.panel("options");
		l.opts = k;
		l.window = m.panel("panel");
		if (l.mask) {
			l.mask.remove()
		}
		if (k.modal == true) {
			l.mask = c('<div class="window-mask"></div>').appendTo("body");
			l.mask.css({
						zIndex : c.fn.window.defaults.zIndex++,
						width : d().width,
						height : d().height,
						display : "none"
					})
		}
		if (l.shadow) {
			l.shadow.remove()
		}
		if (k.shadow == true) {
			l.shadow = c('<div class="window-shadow"></div>')
					.insertAfter(l.window);
			l.shadow.css({
						zIndex : c.fn.window.defaults.zIndex++,
						display : "none"
					})
		}
		l.window.css("z-index", c.fn.window.defaults.zIndex++);
		if (l.options.left == null) {
			var j = l.options.width;
			if (isNaN(j)) {
				j = l.window.outerWidth()
			}
			l.options.left = (c(window).width() - j) / 2
					+ c(document).scrollLeft()
		}
		if (l.options.top == null) {
			var g = l.window.height;
			if (isNaN(g)) {
				g = l.window.outerHeight()
			}
			l.options.top = (c(window).height() - g) / 2
					+ c(document).scrollTop()
		}
		m.window("move");
		if (l.opts.closed == false) {
			m.window("open")
		}
	}
	function a(h) {
		var g = c.data(h, "window");
		g.window.draggable({
			handle : ">div.panel-header",
			disabled : g.options.draggable == false,
			onStartDrag : function(j) {
				if (g.mask) {
					g.mask.css("z-index", c.fn.window.defaults.zIndex++)
				}
				if (g.shadow) {
					g.shadow.css("z-index", c.fn.window.defaults.zIndex++)
				}
				g.window.css("z-index", c.fn.window.defaults.zIndex++);
				g.proxy = c('<div class="window-proxy"></div>')
						.insertAfter(g.window);
				g.proxy.css({
					display : "none",
					zIndex : c.fn.window.defaults.zIndex++,
					left : j.data.left,
					top : j.data.top,
					width : (c.boxModel == true
							? (g.window.outerWidth() - (g.proxy.outerWidth() - g.proxy
									.width()))
							: g.window.outerWidth()),
					height : (c.boxModel == true
							? (g.window.outerHeight() - (g.proxy.outerHeight() - g.proxy
									.height()))
							: g.window.outerHeight())
				});
				setTimeout(function() {
							g.proxy.show()
						}, 500)
			},
			onDrag : function(j) {
				g.proxy.css({
							display : "block",
							left : j.data.left,
							top : j.data.top
						});
				return false
			},
			onStopDrag : function(j) {
				g.options.left = j.data.left;
				g.options.top = j.data.top;
				c(h).window("move");
				g.proxy.remove()
			}
		});
		g.window.resizable({
			disabled : g.options.resizable == false,
			onStartResize : function(j) {
				g.proxy = c('<div class="window-proxy"></div>')
						.insertAfter(g.window);
				g.proxy.css({
					zIndex : c.fn.window.defaults.zIndex++,
					left : j.data.left,
					top : j.data.top,
					width : (c.boxModel == true ? (j.data.width - (g.proxy
							.outerWidth() - g.proxy.width())) : j.data.width),
					height : (c.boxModel == true ? (j.data.height - (g.proxy
							.outerHeight() - g.proxy.height())) : j.data.height)
				})
			},
			onResize : function(j) {
				g.proxy.css({
					left : j.data.left,
					top : j.data.top,
					width : (c.boxModel == true ? (j.data.width - (g.proxy
							.outerWidth() - g.proxy.width())) : j.data.width),
					height : (c.boxModel == true ? (j.data.height - (g.proxy
							.outerHeight() - g.proxy.height())) : j.data.height)
				});
				return false
			},
			onStopResize : function(j) {
				g.options.left = j.data.left;
				g.options.top = j.data.top;
				g.options.width = j.data.width;
				g.options.height = j.data.height;
				f(h);
				g.proxy.remove()
			}
		})
	}
	function d() {
		if (document.compatMode == "BackCompat") {
			return {
				width : Math.max(document.body.scrollWidth,
						document.body.clientWidth),
				height : Math.max(document.body.scrollHeight,
						document.body.clientHeight)
			}
		} else {
			return {
				width : Math.max(document.documentElement.scrollWidth,
						document.documentElement.clientWidth),
				height : Math.max(document.documentElement.scrollHeight,
						document.documentElement.clientHeight)
			}
		}
	}
	c(window).resize(function() {
				c(".window-mask").css({
							width : c(window).width(),
							height : c(window).height()
						});
				setTimeout(function() {
							c(".window-mask").css({
										width : d().width,
										height : d().height
									})
						}, 50)
			});
	c.fn.window = function(g, h) {
		if (typeof g == "string") {
			switch (g) {
				case "options" :
					return c.data(this[0], "window").options;
				case "window" :
					return c.data(this[0], "window").window;
				case "open" :
					return this.each(function() {
								c(this).panel("open", h)
							});
				case "close" :
					return this.each(function() {
								c(this).panel("close", h)
							});
				case "destroy" :
					return this.each(function() {
								c(this).panel("destroy", h)
							});
				case "refresh" :
					return this.each(function() {
								c(this).panel("refresh")
							});
				case "resize" :
					return this.each(function() {
								c(this).panel("resize", h)
							});
				case "move" :
					return this.each(function() {
								c(this).panel("move", h)
							})
			}
		}
		g = g || {};
		return this.each(function() {
					e(this, g);
					a(this)
				})
	};
	c.fn.window.defaults = {
		zIndex : 9000,
		draggable : true,
		resizable : true,
		shadow : true,
		modal : false,
		title : "New Window",
		collapsible : true,
		minimizable : true,
		maximizable : true,
		closable : true,
		closed : false
	}
})(jQuery);
(function($) {
	function getIcon(iconName) {
		var content = "";
		switch (iconName) {
			case "error" :
				content = '<div class="messager-icon messager-error"></div>';
				break;
			case "info" :
				content = '<div class="messager-icon messager-info"></div>';
				break;
			case "question" :
				content = '<div class="messager-icon messager-question"></div>';
				break;
			case "warning" :
				content = '<div class="messager-icon messager-warning"></div>';
				break;
			case "success" :
				content = '<div class="messager-icon messager-success"></div>';
				break
		}
		return content
	}
	function show(win, type, speed, timeout) {
		if (!win) {
			return
		}
		switch (type) {
			case null :
				win.show();
				break;
			case "slide" :
				win.slideDown(speed);
				break;
			case "fade" :
				win.fadeIn(speed);
				break;
			case "show" :
				win.show(speed);
				break
		}
		var timer = null;
		if (timeout > 0) {
			timer = setTimeout(function() {
						hide(win, type, speed)
					}, timeout)
		}
		win.hover(function() {
					if (timer) {
						clearTimeout(timer)
					}
				}, function() {
					if (timeout > 0) {
						timer = setTimeout(function() {
									hide(win, type, speed)
								}, timeout)
					}
				})
	}
	function hide(win, type, speed) {
		if (!win) {
			return
		}
		switch (type) {
			case null :
				win.hide();
				break;
			case "slide" :
				win.slideUp(speed);
				break;
			case "fade" :
				win.fadeOut(speed);
				break;
			case "show" :
				win.hide(speed);
				break
		}
		setTimeout(function() {
					win.remove()
				}, speed)
	}
	function createDialog(title, content, buttons) {
		var win = $('<div class="messager-body"></div>').appendTo("body");
		win.append(content);
		if (buttons) {
			var tb = $('<div class="messager-button"></div>').appendTo(win);
			for (var label in buttons) {
				$("<a></a>").attr("href", "javascript:void(0)").text(label)
						.css("margin-left", 10).bind("click",
								eval(buttons[label])).appendTo(tb).linkbutton()
			}
		}
		win.window({
					title : title,
					width : 350,
					height : "auto",
					modal : true,
					collapsible : false,
					minimizable : false,
					maximizable : false,
					resizable : false,
					onClose : function() {
						setTimeout(function() {
									win.window("destroy")
								}, 100)
					}
				});
		var inputObj = $(":text:visible", win);
		if (inputObj.length < 1) {
			win
					.append('<input style="position:absolute;z-index:-1;width:0px;height:0px;" id="focusinput"/>');
			$("#focusinput", win).focus()
		} else {
			inputObj[0].focus()
		}
		win.bind("keydown", function(ev) {
					if (ev.keyCode == 13) {
						if (buttons[$.messager.defaults.ok]) {
							buttons[$.messager.defaults.ok].call()
						}
					} else {
					}
				});
		return win
	}
	$.messager = {
		show : function(options) {
			var opts = $.extend({
						showType : "slide",
						showSpeed : 600,
						width : 250,
						height : "auto",
						msg : "",
						title : "",
						icon : "",
						timeout : 4000
					}, options || {});
			var content = getIcon(opts.icon)
					+ '<div style="word-break:break-all">' + opts.msg
					+ "</div>";
			content += '<div style="clear:both;"/>';
			var win = $('<div class="messager-body"  style="text-align:left"></div>')
					.html(content).appendTo("body");
			win.window({
						title : opts.title,
						width : opts.width,
						height : opts.height,
						collapsible : false,
						minimizable : false,
						maximizable : false,
						shadow : false,
						draggable : false,
						resizable : false,
						closed : true,
						onBeforeOpen : function() {
							show($(this).window("window"), opts.showType,
									opts.showSpeed, opts.timeout);
							return false
						},
						onBeforeClose : function() {
							hide(win.window("window"), opts.showType,
									opts.showSpeed);
							return false
						}
					});
			win.window("window").css({
				left : null,
				top : null,
				right : 0,
				bottom : -document.body.scrollTop
						- document.documentElement.scrollTop
			});
			win.window("open")
		},
		alert : function(title, msg, icon, fn) {
			setTimeout(function() {
				msg = msg.replace(/\r/g, "");
				msg = msg.replace(/\n/g, "<br/>");
				var content = getIcon(icon)
						+ '<div style="word-break:break-all">' + msg + "</div>";
				content += '<div style="clear:both;"/>';
				var buttons = {};
				buttons[$.messager.defaults.ok] = function() {
					if (fn) {
						fn();
						win.window("destroy");
						return false
					}
					win.window("destroy")
				};
				var win = createDialog(title, content, buttons)
			}, 100)
		},
		confirm : function(title, msg, fn, hideImg) {
			var imgHtml = '<div class="messager-icon messager-question"></div>';
			if (hideImg) {
				imgHtml = ""
			}
			var content = imgHtml + "<div>" + msg
					+ '</div><div style="clear:both;"/>';
			var buttons = {};
			buttons[$.messager.defaults.ok] = function() {
				if (fn) {
					fn(true);
					win.window("destroy");
					return false
				}
				win.window("destroy")
			};
			buttons[$.messager.defaults.cancel] = function() {
				if (fn) {
					fn(false);
					win.window("destroy");
					return false
				}
				win.window("destroy")
			};
			var win = createDialog(title, content, buttons)
		},
		prompt : function(title, msg, fn, defVal) {
			if (!defVal) {
				defVal = ""
			}
			var content = '<div class="messager-icon messager-question"></div><div>'
					+ msg
					+ '</div><br/><input class="messager-input" type="text" value="'
					+ defVal + '"/><div style="clear:both;"/>';
			var buttons = {};
			buttons[$.messager.defaults.ok] = function() {
				if (fn) {
					fn($(".messager-input", win).val());
					win.window("destroy");
					return false
				}
				win.window("destroy")
			};
			buttons[$.messager.defaults.cancel] = function() {
				if (fn) {
					fn();
					win.window("destroy");
					return false
				}
				win.window("destroy")
			};
			var win = createDialog(title, content, buttons)
		},
		dialog : function(title, fieldArrObj, fn, label_direction) {
			var content = '<div class="messager-icon messager-question"></div>';
			content += '<div><table width="266" border="0" cellpadding="0" cellspacing="0">';
			var fields = {};
			for (var i = 0; i < fieldArrObj.length; i++) {
				var fieldObj = fieldArrObj[i];
				content += "<tr>";
				if (!label_direction || label_direction == "left") {
					content += '<td class="label">' + fieldObj.name + ":</td>"
				}
				var type = fieldObj.type;
				if (!type) {
					type = "text"
				}
				if (label_direction == "right") {
					content += '<td class="content_right">'
				} else {
					content += "<td>"
				}
				content += '<input type="' + type + '" name="' + fieldObj.id
						+ '"';
				if (fieldObj.value) {
					content += ' value="' + fieldObj.value + '"'
				}
				if (fieldObj.checked) {
					content += ' checked="checked"'
				}
				content += " /></td>";
				if (label_direction == "right") {
					content += '<td class="label_right">' + fieldObj.name
							+ "</td>"
				}
				content += "</tr>";
				if (!fields[fieldObj.id]) {
					fields[fieldObj.id] = fieldObj
				}
			}
			content += "</table></div>";
			content += '<div style="clear:both;"/>';
			var buttons = {};
			buttons[$.messager.defaults.ok] = function() {
				win.window("close");
				if (fn) {
					var data = {};
					for (var p in fields) {
						var fieldObj = fields[p];
						var $input = $(":input[name=" + fieldObj.id + "]", win);
						if (fieldObj.type == "radio"
								|| fieldObj.type == "checkbox") {
							if ($input.size() > 1) {
								var valArr = [];
								$input.each(function(index, element) {
											if (element.checked) {
												valArr.push(element.value)
											}
										});
								data[fieldObj.id] = valArr
							} else {
								data[fieldObj.id] = $input.val()
							}
						} else {
							if ($input.size() > 1) {
								var valArr = [];
								$input.each(function(index, element) {
											valArr.push(element.value)
										});
								data[fieldObj.id] = valArr
							} else {
								data[fieldObj.id] = $input.val()
							}
						}
					}
					fn(data);
					return false
				}
			};
			buttons[$.messager.defaults.cancel] = function() {
				win.window("close")
			};
			var win = createDialog(title, content, buttons)
		}
	};
	$.messager.defaults = {
		ok : "确定",
		cancel : "取消"
	}
})(jQuery);
(function($) {
	var __commonData__ = {};
	var getTargetValue = function(targetCode, current) {
		var targetValue;
		if (targetCode.indexOf(".") > 0) {
			var codeArr = targetCode.split(".");
			var rowData = table.getTableRowData(codeArr[0], current);
			targetValue = rowData[codeArr[1]]
		} else {
			targetValue = getValue(targetCode)
		}
		return targetValue
	};
	__commonData__.rules = {
		email : {
			validator : function(value) {
				return /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i
						.test(value)
			},
			message : "请输入一个有效的邮件地址"
		},
		url : {
			validator : function(value) {
				return /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i
						.test(value)
			},
			message : "请输入一个有效的URL"
		},
		length : {
			validator : function(value, param) {
				var len = 0;
				for (var i = value.length; i >= 1; i--) {
					var tmp = value.substring(i - 1, i);
					tmp = tmp.toString();
					var oneChar = tmp.charCodeAt(0);
					if (oneChar > 126) {
						len += 2
					} else {
						len++
					}
				}
				return len >= param[0] && len <= param[1]
			},
			message : function(value, param) {
				if (param[0] == param[1]) {
					if (this.validTitle && this.validTitle != "") {
						return this.validTitle + "的长度应该为{0}"
					}
					return "请输入长度为{0}"
				} else {
					if (this.validTitle && this.validTitle != "") {
						return this.validTitle + "的长度应该在{0}到{1}之间"
					}
					return "请输入长度在{0}到{1}之间"
				}
			}
		},
		range : {
			validator : function(value, param) {
				if (value) {
					var cls = $(this).attr("class");
					if (cls) {
						if (cls.indexOf("easyui-moneybox") > -1
								|| cls.indexOf("easyui-numberbox") > -1
								|| cls.indexOf("easyui-percentbox") > -1) {
							value = ("" + value).replace(/,/g, "").replace("%",
									"");
							value = value - 0;
							if (!value) {
								value = 0
							}
						}
					}
					if (param.length == 1 || !(param[1])) {
						return value >= param[0]
					} else {
						if (!(param[0]) && param[0] != 0) {
							return value <= param[1]
						} else {
							return value >= param[0] && value <= param[1]
						}
					}
				} else {
					return true
				}
			},
			message : function(value, param) {
				var name = "输入的信息";
				if (this.validTitle && this.validTitle != "") {
					name = this.validTitle
				}
				if (param.length == 1 || !(param[1])) {
					return this.validTitle + "不能小于{0}"
				} else {
					if (!(param[0]) && param[0] != 0) {
						return this.validTitle + "不能大于{1}"
					} else {
						return this.validTitle + "应该在{0}到{1}之间"
					}
				}
			}
		},
		custom : {
			validator : function(value, param) {
				if (value) {
					if (typeof(param[1]) == "function") {
						return param[1].call(this, value)
					} else {
						return eval(param[1])
					}
				} else {
					return true
				}
			},
			message : "{0}"
		},
		regexp : {
			validator : function(value, param) {
				if (value) {
					return param[1].test(value)
				} else {
					return true
				}
			},
			message : "{0}"
		},
		greater : {
			validator : function(value, param) {
				if (value) {
					var targetValue = getTargetValue(param[0], this);
					var cls = $(this).attr("class");
					if (cls) {
						if (cls.indexOf("easyui-moneybox") > -1
								|| cls.indexOf("easyui-numberbox") > -1
								|| cls.indexOf("easyui-percentbox") > -1) {
							value = value - 0;
							targetValue = targetValue - 0
						}
					}
					return value > targetValue
				} else {
					return true
				}
			},
			message : function(value, param) {
				var name = "输入的信息";
				if (this.validTitle && this.validTitle != "") {
					name = this.validTitle
				}
				return name + "应该大于{1}"
			}
		},
		greaterEqual : {
			validator : function(value, param) {
				if (value) {
					var targetValue = getTargetValue(param[0], this);
					var cls = $(this).attr("class");
					if (cls) {
						if (cls.indexOf("easyui-moneybox") > -1
								|| cls.indexOf("easyui-numberbox") > -1
								|| cls.indexOf("easyui-percentbox") > -1) {
							value = value - 0;
							targetValue = targetValue - 0
						}
					}
					return value >= targetValue
				} else {
					return true
				}
			},
			message : function(value, param) {
				var name = "输入的信息";
				if (this.validTitle && this.validTitle != "") {
					name = this.validTitle
				}
				return name + "不能小于{1}"
			}
		},
		less : {
			validator : function(value, param) {
				if (value) {
					var targetValue = getTargetValue(param[0], this);
					var cls = $(this).attr("class");
					if (cls) {
						if (cls.indexOf("easyui-moneybox") > -1
								|| cls.indexOf("easyui-numberbox") > -1
								|| cls.indexOf("easyui-percentbox") > -1) {
							value = value - 0;
							targetValue = targetValue - 0
						}
					}
					return value < targetValue
				} else {
					return true
				}
			},
			message : function(value, param) {
				var name = "输入的信息";
				if (this.validTitle && this.validTitle != "") {
					name = this.validTitle
				}
				return name + "应该小于{1}"
			}
		},
		lessEqual : {
			validator : function(value, param) {
				if (value) {
					var targetValue = getTargetValue(param[0], this);
					var cls = $(this).attr("class");
					if (cls) {
						if (cls.indexOf("easyui-moneybox") > -1
								|| cls.indexOf("easyui-numberbox") > -1
								|| cls.indexOf("easyui-percentbox") > -1) {
							value = value - 0;
							targetValue = targetValue - 0
						}
					}
					return value <= targetValue
				} else {
					return true
				}
			},
			message : function(value, param) {
				var name = "输入的信息";
				if (this.validTitle && this.validTitle != "") {
					name = this.validTitle
				}
				return name + "不能大于{1}"
			}
		}
	};
	$.commondata = function(param, valKey, val) {
		if (typeof param == "string") {
			var obj = __commonData__;
			var key = valKey;
			if (typeof valKey == "string") {
				var keys = valKey.split(".");
				var keysLen = keys.length;
				if (keysLen > 1) {
					var i = 0;
					while (i < keysLen - 1) {
						if (!obj[keys[i]]) {
							obj[keys[i]] = {
								___null___ : ""
							};
							obj = obj[keys[i]]
						} else {
							obj = obj[keys[i]]
						}
						i++
					}
				}
				key = keys[keysLen - 1]
			} else {
				if (key.id) {
					key = key.id
				} else {
					if (key.name) {
						key = key.name
					}
				}
			}
			switch (param) {
				case "get" :
					if (!obj) {
						return null
					} else {
						return obj[key]
					}
				case "add" :
					if (obj) {
						if (obj[key] && typeof(val) == "object") {
							var tempObj = obj[key];
							for (tkey in val) {
								tempObj[tkey] = val[tkey]
							}
						} else {
							obj[key] = val
						}
						return true
					} else {
						return false
					}
				case "remove" :
					if (obj) {
						obj[key] = null;
						return true
					} else {
						return false
					}
				default :
					return null
			}
		}
		return this
	}
})(jQuery);
(function($) {
	function init(target) {
		$(target).addClass("validatebox-text")
	}
	function destroyBox(target) {
		var tip = $.data(target, "validatebox").tip;
		if (tip) {
			tip.remove()
		}
		$(target).remove()
	}
	function bindEvents(target) {
		var box = $(target);
		var tip = $.data(target, "validatebox").tip;
		if (box.hasClass("span-code-checkbox")) {
			box = $(".input", box)
		}
		var time = null;
		box.unbind(".validatebox").bind("focus.validatebox", function() {
					if (time) {
						clearInterval(time)
					}
					time = setInterval(function() {
								validate(target, true)
							}, 200);
					setTimeout(function() {
								box.removeClass("validatebox-invalid");
								hideTip(target)
							}, 1000)
				}).bind("blur.validatebox", function() {
					clearInterval(time);
					time = null;
					hideTip(target)
				}).bind("mouseover.validatebox", function() {
					if (box.hasClass("validatebox-invalid")) {
						showTip(target)
					}
				}).bind("mouseout.validatebox", function() {
					hideTip(target)
				})
	}
	function showTip(target) {
		var box = $(target);
		var msg = $.data(target, "validatebox").message;
		var tip = $.data(target, "validatebox").tip;
		if (!tip) {
			tip = $('<div class="validatebox-tip"><span class="validatebox-tip-content"></span><span class="validatebox-tip-pointer"></span></div>')
					.appendTo("body");
			$.data(target, "validatebox").tip = tip
		}
		tip.find(".validatebox-tip-content").html(msg);
		tip.css({
					display : "block",
					left : box.offset().left + box.outerWidth(),
					top : box.offset().top
				})
	}
	function hideTip(target) {
		var tip = $.data(target, "validatebox").tip;
		if (tip) {
			tip.remove();
			$.data(target, "validatebox").tip = null
		}
	}
	function hideTipByTimeout() {
		if (typeof(setTimeoutId) != "undefined") {
			clearTimeout(setTimeoutId)
		}
		setTimeoutId = setTimeout(function() {
					var targetList = $.commondata("get", "validateTargetList");
					$.each(targetList, function(i, target) {
								$(target).removeClass("validatebox-invalid");
								hideTip(target)
							});
					$.commondata("remove", "validateTargetList")
				}, 3000)
	}
	function addValidateTargetList(target) {
		var targetList = $.commondata("get", "validateTargetList");
		if (!targetList) {
			targetList = []
		}
		targetList.push(target);
		$.commondata("add", "validateTargetList", targetList)
	}
	function validate(target) {
		var hideData = $.data(target, "validatebox");
		if (!hideData) {
			return
		}
		if (target.tagName == "SELECT") {
			return
		}
		var opts = hideData.options;
		var tip = hideData.tip;
		var box = $(target);
		var value = box.val();
		if (box.hasClass("span-code-checkbox")) {
			value = "";
			$(".input:checked", box).each(function() {
						if (value != "") {
							value += ","
						}
						value += this.value
					})
		}
		function setTipMessage(msg) {
			$.data(target, "validatebox").message = msg
		}
		if (target.type == "checkbox") {
			alert(value)
		}
		var disabled = box.attr("disabled");
		if (disabled == true || disabled == "true") {
			return true
		}
		var validTitle = $(target).attr("validTitle");
		if (opts.required) {
			if (value == "") {
				box.addClass("validatebox-invalid");
				setTipMessage(opts.missingMessage);
				showTip(target);
				if (invalidMessage && validTitle) {
					invalidMessage.push(validTitle + "：" + opts.missingMessage)
				}
				addValidateTargetList(target);
				hideTipByTimeout();
				return false
			}
		}
		if (opts.validType) {
			var validArr = opts.validType.split(":");
			for (var i = 0; i < validArr.length; i++) {
				var result = /([a-zA-Z_]+)(.*)/.exec(validArr[i]);
				var rules = $.commondata("get", "rules");
				var rule = rules[result[1]];
				if (value && rule) {
					var param = eval(result[2]);
					if (!rule.validator.call(target, value, param)) {
						box.addClass("validatebox-invalid");
						var message = rule.message;
						if (typeof(message) == "function") {
							message = message.call(target, value, param)
						}
						if (param) {
							for (var i = 0; i < param.length; i++) {
								message = message
										.replace(new RegExp("\\{" + i + "\\}",
														"g"), param[i])
							}
						}
						setTipMessage(opts.invalidMessage || message);
						showTip(target);
						if (invalidMessage && validTitle) {
							invalidMessage.push(validTitle + "：" + message)
						}
						addValidateTargetList(target);
						hideTipByTimeout();
						return false
					}
				}
			}
		}
		box.removeClass("validatebox-invalid");
		hideTip(target);
		return true
	}
	var invalidMessage = [];
	$.fn.validatebox = function(options) {
		if (typeof options == "string") {
			switch (options) {
				case "destroy" :
					return this.each(function() {
								destroyBox(this)
							});
				case "validate" :
					invalidMessage = [];
					return this.each(function() {
								validate(this)
							});
				case "getMsg" :
					return invalidMessage;
				case "isValid" :
					return validate(this[0])
			}
		}
		options = options || {};
		return this.each(function() {
			var state = $.data(this, "validatebox");
			if (state) {
				$.extend(state.options, options)
			} else {
				init(this);
				var t = $(this);
				var validType = t.attr("validType");
				if (!validType) {
					var maxlength = parseInt(t.attr("maxlength"));
					if (maxlength && maxlength > 0 && maxlength < 10000) {
						validType = "length[0, " + maxlength + "]"
					}
				}
				if (!validType) {
					var minVal = t.attr("min");
					var maxVal = t.attr("max");
					if (minVal || maxVal) {
						if (!(minVal)) {
							minVal = "null"
						}
						if (!(maxVal)) {
							maxVal = "null"
						}
						validType = "range[" + minVal + "," + maxVal + "]"
					}
				}
				var required = t.attr("required")
						? (t.attr("required") == "true" || t.attr("required") == true)
						: undefined;
				var missingMessage = t.attr("missingMessage");
				if (!(missingMessage)
						&& (t.hasClass("dhx_combo_input") || t
								.hasClass("span-code-checkbox"))) {
					missingMessage = "请选择内容"
				}
				state = $.data(this, "validatebox", {
					options : $.extend({}, $.fn.validatebox.defaults, {
						required : required,
						validType : validType || undefined,
						missingMessage : missingMessage || undefined,
						invalidMessage : (t.attr("invalidMessage") || undefined)
					}, options)
				})
			}
			bindEvents(this)
		})
	};
	$.fn.validatebox.defaults = {
		required : false,
		validType : null,
		missingMessage : "请输入内容",
		invalidMessage : null,
		rules : {
			email : {
				validator : function(value) {
					return /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i
							.test(value)
				},
				message : "Please enter a valid email address."
			},
			url : {
				validator : function(value) {
					return /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i
							.test(value)
				},
				message : "Please enter a valid URL."
			},
			length : {
				validator : function(value, param) {
					var len = $.trim(value).length;
					return len >= param[0] && len <= param[1]
				},
				message : "Please enter a value between {0} and {1}."
			}
		}
	}
})(jQuery);
(function(a) {
	function d(g) {
		var f = a.data(g, "numberbox").options;
		var h = a(g).val();
		var e = h.indexOf(".");
		if (e > 0) {
			if (f.precision) {
				h = h.substr(0, e + f.precision + 1)
			} else {
				h = h.substr(0, e)
			}
		} else {
			h = parseFloat(h).toFixed(f.precision)
		}
		if (isNaN(h)) {
			a(g).val("");
			return
		}
		if (f.min && h < f.min) {
			a(g).val(f.min.toFixed(f.precision))
		} else {
			if (f.max && h > f.max) {
				a(g).val(f.max.toFixed(f.precision))
			} else {
				a(g).val(h)
			}
		}
	}
	function c(h) {
		if (h.length) {
			var g = parseInt(h.length);
			var e = 0;
			if (h.precision) {
				e = parseInt(h.precision)
			}
			var f = Math.pow(10, g - e) - 1;
			h.maxIntValue = f
		}
		a(h).unbind(".numberbox");
		a(h).bind("keypress.numberbox", function(j) {
			if (this.maxIntValue) {
				this.tmpValue = this.value
			}
			if (j.which == 45) {
				return true
			}
			if (j.which == 46) {
				return true
			} else {
				if ((j.which >= 48 && j.which <= 57 && j.ctrlKey == false && j.shiftKey == false)
						|| j.which == 0 || j.which == 8) {
					return true
				} else {
					if (j.ctrlKey == true && (j.which == 99 || j.which == 118)) {
						return true
					} else {
						return false
					}
				}
			}
		}).bind("keyup.numberbox", function() {
					if (this.maxIntValue) {
						var j = Math.abs(parseInt(this.value));
						if (j > this.maxIntValue) {
							this.value = this.tmpValue
						}
					}
				}).bind("paste.numberbox", function() {
					if (window.clipboardData) {
						var j = clipboardData.getData("text");
						if (!/\D/.test(j)) {
							return true
						} else {
							return false
						}
					} else {
						return false
					}
				}).bind("dragenter.numberbox", function() {
					return false
				}).bind("blur.numberbox", function() {
					d(h)
				})
	}
	a.fn.numberbox = function(e) {
		e = e || {};
		return this.each(function() {
			a(this).css("text-align", "right");
			var f = a.data(this, "numberbox");
			if (f) {
				a.extend(f.options, e)
			} else {
				a.data(this, "numberbox", {
					options : a.extend({}, a.fn.numberbox.defaults, {
						min : (parseFloat(a(this).attr("min")) || undefined),
						max : (parseFloat(a(this).attr("max")) || undefined),
						precision : (parseInt(a(this).attr("precision")) || undefined)
					}, e)
				});
				a(this).css({
							imeMode : "disabled"
						})
			}
			d(this);
			c(this)
		})
	};
	a.fn.numberbox.defaults = {
		min : null,
		max : null,
		precision : 0
	}
})(jQuery);
function fixValue(h, n) {
	var a = $.data(h, "moneybox").options;
	var e = $.trim($(h).val()).replace(/,/g, "");
	var d = 100;
	if (a.max) {
		d = ("" + a.max).length
	}
	if (e.indexOf("-") == 0) {
		d++
	}
	if (e.indexOf(".") > 0) {
		d++
	}
	if (e.length > d) {
		e = e.substr(0, d)
	}
	var g = e.indexOf(".");
	if (g > 0) {
		e = e.substr(0, g + a.precision + 1);
		var l = e.substr(g + 1);
		if (l.length < a.precision) {
			var c = parseInt(a.precision) - parseInt(l.length);
			for (var f = 1; f <= c; f++) {
				e = e + "0"
			}
		}
	} else {
		e = parseFloat(e).toFixed(a.precision)
	}
	if (isNaN(e)) {
		e = ""
	} else {
		if ("" + e != "") {
			if (n) {
				var m = e.split(".");
				var k = m[0];
				k += ",";
				var p = /(\d)(\d{3},)/;
				while (p.test(k)) {
					k = k.replace(p, "$1,$2")
				}
				k = k.replace(/,$/, "");
				m[0] = k;
				e = m.join(".")
			}
		}
	}
	$(h).val(e);
	var o = parseFloat(e.replace(/,/g, "")).toFixed(a.precision);
	if (isNaN(o)) {
		o = ""
	}
	_$getHideControl(h).val(o)
}
function _$getHideControl(c) {
	var a = $(c).attr("id");
	var d = "";
	if (a.indexOf("show__") < 0) {
		d = "hid__" + a
	} else {
		d = a.replace("show__", "hid__")
	}
	return $("#" + d)
}
function _$getShowControl(c) {
	var d = "" + $(c).attr("id");
	var a = d.replace("hid__", "");
	if ($("#" + a)[0]) {
		return $("#" + a)
	} else {
		a = d.replace("hid__", "show__");
		return $("#" + a)
	}
}
function bindEvents(e) {
	if (e.length) {
		var d = parseInt(e.length);
		var a = 0;
		if (e.precision) {
			a = parseInt(e.precision)
		}
		var c = Math.pow(10, d - a) - 1;
		e.maxIntValue = c
	}
	$(e).unbind(".moneybox");
	$(e).bind({
		"keypress.moneybox" : function(f) {
			if (this.maxIntValue) {
				this.tmpValue = this.value
			}
			if (f.which == 45) {
				return true
			} else {
				if (f.which == 46) {
					return true
				} else {
					if ((f.which >= 48 && f.which <= 57 && f.ctrlKey == false && f.shiftKey == false)
							|| f.which == 0 || f.which == 8) {
						return true
					} else {
						if (f.ctrlKey == true
								&& (f.which == 99 || f.which == 118)) {
							return true
						} else {
							return false
						}
					}
				}
			}
		},
		"keyup.moneybox" : function() {
			if (this.maxIntValue) {
				var f = Math.abs(parseInt(this.value));
				if (f > this.maxIntValue) {
					this.value = this.tmpValue
				}
			}
		},
		"paste.moneybox" : function() {
			if (window.clipboardData) {
				var f = clipboardData.getData("text");
				if (!/\D/.test(f)) {
					return true
				} else {
					return false
				}
			} else {
				return false
			}
		},
		"dragenter.moneybox" : function() {
			return false
		},
		"blur.moneybox" : function() {
			fixValue(e, true)
		},
		"focus.moneybox" : function() {
			fixValue(e, false)
		}
	})
}
function hidMoneyOnChange(a, c) {
	var e = a.value;
	var d = $("#" + c);
	d.val(e);
	d.blur();
	d.change()
}
(function(a) {
	a.fn.moneybox = function(c, d) {
		if (typeof c == "string") {
			switch (c) {
				case "setDisable" :
					return this.each(function() {
								var g = a(this).attr("class");
								if (("" + g).indexOf("hid_moneybox") < 0) {
									var f = _$getHideControl(this);
									a(this).attr("disabled", d);
									f.attr("disabled", d)
								} else {
									var e = _$getShowControl(this);
									a(this).attr("disabled", d);
									e.attr("disabled", d)
								}
							});
					break;
				case "setPrecision" :
					if ((!isNaN(d) && d != "") || d == 0) {
						return this.each(function() {
									var e = a.data(this, "moneybox");
									e.options.precision = d - 0;
									a.data(this, "moneybox", e);
									fixValue(this, true)
								})
					}
					break;
				case "setReadOnly" :
					return this.each(function() {
								var g = a(this).attr("class");
								if (("" + g).indexOf("hid_moneybox") < 0) {
									var f = _$getHideControl(this);
									a(this).attr("disabled", false);
									a(this).attr("readOnly", d);
									f.attr("disabled", false)
								} else {
									var e = _$getShowControl(this);
									a(this).attr("disabled", false);
									e.attr("readOnly", d);
									e.attr("disabled", false)
								}
							});
					break;
				default :
					return null
			}
			return
		}
		c = c || {};
		return this.each(function() {
			if (a(this).attr("isMoneybox") == "true") {
				return
			}
			a(this).css("text-align", "right");
			var l = a.data(this, "moneybox");
			if (l) {
				a.extend(l.options, c)
			} else {
				a.data(this, "moneybox", {
					options : a.extend({}, a.fn.moneybox.defaults, {
						min : (parseFloat(a(this).attr("min")) || undefined),
						max : (parseFloat(a(this).attr("max")) || undefined),
						precision : (parseInt(a(this).attr("precision")) || undefined)
					}, c)
				});
				a(this).css({
							imeMode : "disabled"
						})
			}
			var k = a(this).attr("name");
			var e = a(this).attr("class");
			if (k) {
				var f = null;
				var m = null;
				if (!c.createName) {
					f = a(this).attr("id");
					m = f
				}
				var h = (new Date()).getTime()
						+ parseInt(Math.random() * 100000, 10);
				if (!f) {
					m = "show__" + h;
					a(this).attr("id", m);
					f = h
				}
				a(this).removeAttr("name");
				a(this).after("<input class='hid_moneybox' id='hid__" + f
						+ "' type='hidden'  onblur=\"hidMoneyOnChange(this,'"
						+ m + "')\" onchange=\"hidMoneyOnChange(this,'" + m
						+ "')\" name='" + k + "'/>")
			}
			fixValue(this, true);
			bindEvents(this);
			if (this.onchange) {
				var g = this.onchange;
				var j = this;
				a(this).removeAttr("onchange");
				a(this).change(function() {
							setTimeout(function() {
										g.call(j)
									}, 200)
						})
			}
			a(this).attr("isMoneybox", "true")
		})
	};
	a.fn.moneybox.defaults = {
		min : null,
		max : null,
		precision : 0,
		createName : false
	}
})(jQuery);
function hidPercentboxOnChange(a, c) {
	var e = a.value;
	e = (e - 0) * 100;
	var d = $("#" + c);
	d.val(e);
	d.blur()
}
(function(c) {
	function f(l, n) {
		var k = c.data(l, "percentbox").options;
		var m = c.trim(c(l).val()).replace("%", "");
		var h = 100;
		if (k.max) {
			h = ("" + k.max).length
		}
		if (m.length > h) {
			m = m.substr(0, h)
		}
		m = parseFloat(m);
		var g = k.precision;
		var j = "";
		if (isNaN(m)) {
			m = ""
		} else {
			if ("" + m != "") {
				if ((m - 0).toFixed) {
					j = (m - 0) / 100;
					j = j.toFixed(g)
				}
				if (n) {
					if (g > 1) {
						m = (j * 100).toFixed(g - 2) + "%"
					} else {
						m = (j * 100).toFixed(0) + "%"
					}
				}
			}
		}
		c(l).val(m);
		if (isNaN(j)) {
			j = ""
		}
		a(l).val(j)
	}
	function a(h) {
		var g = c(h).attr("id");
		var j = "";
		if (g.indexOf("show__") < 0) {
			j = "hid__" + g
		} else {
			j = g.replace("show__", "hid__")
		}
		return c("#" + j)
	}
	function d(h) {
		var j = "" + c(h).attr("id");
		var g = j.replace("hid__", "");
		if (c("#" + g)[0]) {
			return c("#" + g)
		} else {
			g = j.replace("hid__", "show__");
			return c("#" + g)
		}
	}
	function e(k) {
		if (k.length) {
			var j = parseInt(k.length) + 2;
			var g = 0;
			if (k.precision) {
				g = parseInt(k.precision)
			}
			var h = Math.pow(10, j - g) - 1;
			k.maxIntValue = h
		}
		c(k).unbind(".percentbox");
		c(k).bind("keypress.percentbox", function(l) {
			if (this.maxIntValue) {
				this.tmpValue = this.value
			}
			if (l.which == 45) {
				return true
			} else {
				if (l.which == 46) {
					return true
				} else {
					if ((l.which >= 48 && l.which <= 57 && l.ctrlKey == false && l.shiftKey == false)
							|| l.which == 0 || l.which == 8) {
						return true
					} else {
						if (l.ctrlKey == true
								&& (l.which == 99 || l.which == 118)) {
							return true
						} else {
							return false
						}
					}
				}
			}
		}).bind("keyup.percentbox", function() {
					if (this.maxIntValue) {
						var l = Math.abs(parseInt(this.value));
						if (l > this.maxIntValue) {
							this.value = this.tmpValue
						}
					}
				}).bind("paste.percentbox", function() {
					if (window.clipboardData) {
						var l = clipboardData.getData("text");
						if (!/\D/.test(l)) {
							return true
						} else {
							return false
						}
					} else {
						return false
					}
				}).bind("dragenter.percentbox", function() {
					return false
				}).bind("blur.percentbox", function() {
					f(k, true)
				}).bind("focus.percentbox", function() {
					f(k, false)
				})
	}
	c.fn.percentbox = function(g, h) {
		if (typeof g == "string") {
			switch (g) {
				case "setDisable" :
					return this.each(function() {
								var l = c(this).attr("class");
								if (("" + l).indexOf("hid_percentbox") < 0) {
									var k = a(this);
									c(this).attr("disabled", h);
									k.attr("disabled", h)
								} else {
									var j = d(this);
									c(this).attr("disabled", h);
									j.attr("disabled", h)
								}
							});
					break;
				case "setReadOnly" :
					return this.each(function() {
								var l = c(this).attr("class");
								if (("" + l).indexOf("hid_percentbox") < 0) {
									var k = a(this);
									c(this).attr("disabled", false);
									c(this).attr("readOnly", h);
									k.attr("disabled", false)
								} else {
									var j = d(this);
									c(this).attr("disabled", false);
									j.attr("readOnly", h);
									j.attr("disabled", false)
								}
							});
					break;
				default :
					return null
			}
			return
		}
		g = g || {};
		return this.each(function() {
			if (c(this).attr("ispercentbox") == "true") {
				return
			}
			c(this).css("text-align", "right");
			var p = c.data(this, "percentbox");
			if (p) {
				c.extend(p.options, g)
			} else {
				c.data(this, "percentbox", {
					options : c.extend({}, c.fn.percentbox.defaults, {
						min : (parseFloat(c(this).attr("min")) || undefined),
						max : (parseFloat(c(this).attr("max")) || undefined),
						precision : (parseInt(c(this).attr("precision")) || undefined)
					}, g)
				});
				c(this).css({
							imeMode : "disabled"
						})
			}
			var o = c(this).attr("name");
			var j = c(this).attr("class");
			if (o) {
				var k = null;
				var q = null;
				if (!g.createName) {
					k = c(this).attr("id");
					q = k
				}
				var m = (new Date()).getTime()
						+ parseInt(Math.random() * 100000, 10);
				if (!k) {
					q = "show__" + m;
					c(this).attr("id", q);
					k = m
				}
				c(this).removeAttr("name");
				c(this)
						.after("<input class='hid_percentbox' id='hid__"
								+ k
								+ "' type='hidden' onblur=\"hidPercentboxOnChange(this,'"
								+ q
								+ "')\" onchange=\"hidPercentboxOnChange(this,'"
								+ q + "')\" name='" + o + "'/>")
			}
			f(this, true);
			e(this);
			if (this.onchange) {
				var l = this.onchange;
				var n = this;
				c(this).removeAttr("onchange");
				c(this).change(function() {
							setTimeout(function() {
										l.call(n)
									}, 200)
						})
			}
			c(this).attr("ispercentbox", "true")
		})
	};
	c.fn.percentbox.defaults = {
		min : null,
		max : null,
		precision : 2,
		createName : true
	}
})(jQuery);
(function(a) {
	window.dhx_globalImgPath = contextPath
			+ "/front-ui/script/dhtmlx/combo/imgs/";
	a.fn.combobox = function(d, g, f) {
		if (typeof d == "string") {
			switch (d) {
				case "setDisable" :
					return this.each(function() {
								var h = a.data(this, "combobox");
								if (h) {
									h.disable(g)
								}
							});
					break;
				case "setReadOnly" :
					return this.each(function() {
								var h = a.data(this, "combobox");
								if (h) {
									h.setReadOnly(g)
								}
							});
					break;
				case "setValue" :
					return this.each(function() {
								var h = a.data(this, "combobox");
								if (h) {
									h.setValue(g)
								}
							});
					break;
				case "getValue" :
					var e = this[0];
					if (!e) {
						return null
					}
					var c = a.data(e, "combobox");
					if (c) {
						return c.getValue()
					} else {
						return null
					}
					break;
				case "getText" :
					var e = this[0];
					if (!e) {
						return null
					}
					var c = a.data(e, "combobox");
					if (c) {
						return c.getText()
					} else {
						return null
					}
					break;
				case "execChnage" :
					var e = this[0];
					if (!e) {
						return null
					}
					var c = a.data(e, "combobox");
					if (c) {
						c.execChange()
					}
					break;
				case "loadJsonData" :
					return this.each(function() {
								var h = a.data(this, "combobox");
								if (g && g.length > 0) {
									h.loadJsonData(g);
									if (h.tempVal) {
										h.setValue(h.tempVal)
									}
								}
							});
					break;
				case "remove" :
					var e = this[0];
					if (!e) {
						return false
					}
					var c = a.data(e, "combobox");
					if (c && c.DOMelem_hidden_input) {
						c.destructor();
						e.parentNode.removeChild(e);
						a.data(e, "combobox", null);
						return true
					} else {
						return false
					}
					break;
				case "hide" :
					var e = this[0];
					if (!e) {
						return
					}
					var c = a.data(e, "combobox");
					if (c && c.show) {
						c.show(false)
					}
					return;
				case "show" :
					var e = this[0];
					if (!e) {
						return
					}
					var c = a.data(e, "combobox");
					if (c && c.show) {
						c.show(true)
					}
					return;
				case "addChangeEvent" :
					return this.each(function() {
								var h = this;
								setTimeout(function() {
											var j = a.data(h, "combobox");
											if (!f) {
												f = []
											}
											j.attachEvent("onChange",
													function() {
														g.apply(j, f)
													})
										}, 200)
							});
					break;
				case "addOption" :
					return this.each(function() {
								var h = a.data(this, "combobox");
								if (h) {
									h.addOption(g, f)
								}
							});
					break;
				case "deleteOption" :
					return this.each(function() {
								var h = a.data(this, "combobox");
								if (h) {
									h.deleteOption(g)
								}
							});
					break;
				case "clearAll" :
					return this.each(function() {
								var h = a.data(this, "combobox");
								if (h) {
									h.clearAll()
								}
							});
					break;
				default :
					return null
			}
			return
		}
		d = d || {};
		return this.each(function() {
					var h = dhtmlXComboFromSelect(this);
					a(h.DOMelem_input).validatebox();
					h.attachEvent("onChange", function() {
								a(h.DOMelem_input).validatebox("validate")
							});
					var j = this.disabled;
					var k = false;
					k = a(this).attr("readonly");
					if (k) {
						k = k.toLowerCase();
						k = (k == "readonly" || k == "true") ? true : false
					}
					if (k) {
						h.setReadOnly(k)
					} else {
						if (j) {
							h.disable(true)
						}
					}
					a.data(h.DOMelem_hidden_input, "combobox", h)
				})
	};
	a.fn.combobox.defaults = {}
})(jQuery);
(function(a) {
	a.fn.treeview = function(c, d) {
		if (typeof c == "string") {
			switch (c) {
				case "options" :
			}
		}
		var c = c || {};
		return this.each(function() {
					var e = dhtmlXTreeFromHTML(this);
					a.data(this, "treeview", e)
				})
	};
	a.fn.treeview.defaults = {}
})(jQuery);
(function(c) {
	function d(f) {
		var g = f.isOpen ? "none" : "block";
		for (var e = 0; e < f.children.length; e++) {
			var h = f.children[e];
			h.getCurrentRow().style.display = g;
			if (h.type == "node" && !h.isClicked) {
				d(h, "none")
			}
		}
		f.changeClickImg();
		$table = c(f.link.parentNode.parentNode.parentNode);
		$table.find("tr:visible:even").removeClass("odd").addClass("even");
		$table.find("tr:visible:odd").removeClass("even").addClass("odd")
	}
	var a = {
		create : function() {
			return function() {
				this.initialize.apply(this, arguments)
			}
		}
	};
	c.fn.treetable = function(h) {
		var j = {
			id_col : 0,
			parent_col : 1,
			handle_col : 2,
			order_col : -1,
			sum_col : -1,
			expanded : true,
			hasCheckbox : false
		};
		var g = c.extend(j, h);
		this.options = g;
		var e = this;
		c("form").submit(function() {
					if (e.options.hasCheckbox) {
						var k = c(e).find("tr")
								.has("td :checkbox[checked=false]");
						k.attr("disabled", "true")
					}
				});
		this.find("tr").find("td:eq(" + g.id_col + ")").hide();
		this.find("tr").find("td:eq(" + g.parent_col + ")").hide();
		this.find("tr:eq(0)").find("th:eq(" + g.id_col + ")").hide();
		this.find("tr:eq(0)").find("th:eq(" + g.parent_col + ")").hide();
		c(this).data("treehander", g.id_col + "," + g.parent_col);
		this.find("tr").find("td:eq(" + g.handle_col + ")")
				.removeClass("text_left").addClass("text_left");
		var f = a.create();
		f.prototype = {
			initialize : function(n, o, m, k, l) {
				if (n) {
					this.name = n.innerText;
					this.id;
					this.link = n;
					this.type = "leaf";
					this.level = o;
					this.isOpen = g.expanded;
					this.isClicked = !g.expanded;
					this.root;
					this.img;
					this.parent;
					this.rowObj = l;
					this.children = new Array();
					this.getChildren(m, k)
				}
			},
			getChildren : function(p, s) {
				var n = p[s];
				if (n) {
					this.type = "node";
					var w = this.rowObj;
					c(this.rowObj).attr("trNodeType", "node");
					for (var o = 0; o < n.length; o++) {
						var v = n[o];
						var l = v.obj;
						var r = l[0].childNodes[g.handle_col];
						var k = this.level + 1;
						var m = v.id;
						var q = v.parent;
						l.attr("id", "tr_" + m);
						l.attr("pid", "tr_" + q);
						if (!g.expanded) {
							l[0].style.display = "none"
						}
						var u = new f(r, k, p, m, l[0]);
						u.parent = this;
						u.id = k + "_" + o;
						this.children.push(u)
					}
				}
			},
			getNext : function() {
				var l = null;
				if (this.parent) {
					for (var k = 0; k < this.parent.children.length; k++) {
						if (this.parent.children[k] == this
								&& k < (this.parent.children.length - 1)) {
							l = this.parent.children[k + 1];
							break
						}
					}
				}
				return l
			},
			getCurrentRow : function() {
				return this.link.parentNode
			},
			changeClickImg : function() {
				if (this.isOpen) {
					this.img.src = this.img.src.replace("minus", "plus")
				} else {
					this.img.src = this.img.src.replace("plus", "minus")
				}
				this.isOpen = this.isOpen ? false : true
			},
			getInnerHTML : function() {
				var n = document.createDocumentFragment();
				for (var u = this.level - 1; u > 0; u--) {
					var r = document.createElement("img");
					var q = this.parent;
					for (var o = 1; o < u; o++) {
						q = q.parent
					}
					if (q.getNext()) {
						r.src = contextPath + "/front-ui/image/treegrid/I.gif"
					} else {
						r.src = contextPath
								+ "/front-ui/image/treegrid/blank.gif"
					}
					r.align = "absbottom";
					n.appendChild(r)
				}
				var p = document.createElement("img");
				var y;
				var w = "plus";
				if (g.expanded) {
					w = "minus"
				}
				if (this.type == "node") {
					if (this.level == 0) {
						y = contextPath + "/front-ui/image/treegrid/" + w
								+ ".gif"
					} else {
						if (this.children.length > 0) {
							if (this.getNext()) {
								y = contextPath + "/front-ui/image/treegrid/T"
										+ w + ".gif"
							} else {
								y = contextPath + "/front-ui/image/treegrid/L"
										+ w + ".gif"
							}
						} else {
							if (this.getNext()) {
								y = contextPath
										+ "/front-ui/image/treegrid/T.gif"
							} else {
								y = contextPath
										+ "/front-ui/image/treegrid/L.gif"
							}
						}
					}
				} else {
					if (this.getNext()) {
						y = contextPath + "/front-ui/image/treegrid/T.gif"
					} else {
						if (this.level == 0) {
							y = contextPath
									+ "/front-ui/image/treegrid/minus.gif"
						} else {
							y = contextPath + "/front-ui/image/treegrid/L.gif"
						}
					}
				}
				p.src = y;
				p.align = "absbottom";
				p.style.cursor = "pointer";
				this.img = p;
				var m = this;
				var x = function() {
					d(m);
					m.isClicked = m.isClicked ? false : true
				};
				p.onclick = x;
				n.appendChild(p);
				if (g.hasCheckbox && this.type == "leaf") {
					var v = document.createElement("input");
					v.type = "checkbox";
					n.appendChild(v)
				}
				if (!this || !this.link) {
					return
				}
				var k = this.link.childNodes;
				var s = k.length;
				for (var o = 0; o < s; o++) {
					if (k[o]) {
						n.appendChild(k[o]);
						o--
					}
				}
				var l = document.createElement("div");
				l.setAttribute("id", this.id);
				l.className = (this.type == "node") ? "node" : "leaf";
				if (this.level > 0) {
					l.style.marginLeft = "10px"
				}
				l.appendChild(n);
				return l
			}
		};
		this.each(function() {
		(function(E) {
				if (c(E).attr("treetable") == "true") {
					return
				}
				var r = new Array();
				function F(G) {
					if (G && G.children) {
						for (var o = 0; o < G.children.length; o++) {
							r.push(G.children[o]);
							if (G.children[o].children.length > 0) {
								F(G.children[o])
							}
						}
					}
				}
				var q = null;
				if (E.nodeName == "TBODY") {
					q = E.getElementsByTagName("TR")
				} else {
					q = E.getElementsByTagName("TBODY")[0]
							.getElementsByTagName("TR")
				}
				var y = new Array();
				for (var A = 0; A < q.length; A++) {
					var s = c(q[A]);
					var u = s.find("td:eq(" + g.id_col + ")").text();
					var B = s.find("td:eq(" + g.parent_col + ")").text();
					var p = s.find("td:eq(" + g.handle_col + ")").text();
					y.push({
								id : u,
								parent : B,
								name : p,
								level : 0,
								node : "leaf",
								expanded : true,
								obj : s
							})
				}
				if (g.order_col >= 0) {
					y.sort(function(H, G) {
						var o = 0;
						if (g.order_col == g.id_col) {
							a1 = H.id;
							b1 = G.id
						} else {
							if (g.order_col == g.parent_col) {
								a1 = H.parent;
								b1 = G.parent
							} else {
								if (g.order_col == g.handle_col) {
									a1 = H.name;
									b1 = G.name
								} else {
									a1 = H.obj[0].childNodes[g.order_col].innerText;
									b1 = G.obj[0].childNodes[g.order_col].innerText
								}
							}
						}
						if (!a1 && b1) {
							o = -1
						} else {
							if (a1 && !b1) {
								o = 1
							} else {
								if (a1 > b1) {
									o = 1
								} else {
									if (a1 < b1) {
										o = -1
									}
								}
							}
						}
						return o
					})
				}
				var x = m(y);
				var D = x[-1];
				c.each(D, function(H, J) {
							var M = J.obj[0].childNodes[g.handle_col];
							var L = J.level;
							var I = J.node;
							var K = J.id;
							var G = J.obj;
							G.attr("id", "tr_" + K);
							var o = new f(M, 0, x, K, G[0]);
							o.parent = null;
							o.id = "root_" + H;
							r.push(o);
							F(o)
						});
				var n = new Array();
				k(x, -1, n);
				var C = c(E);
				for (var z = n.length - 1; z > -1; z--) {
					var v = n[z];
					var w = v.obj.find("td:eq(" + g.handle_col + ")");
					w.prepend(r[z].getInnerHTML());
					C.prepend(v.obj)
				}
				C.find("tr:visible:even").removeClass("odd").addClass("even");
				C.find("tr:visible:odd").removeClass("even").addClass("odd");
				C.attr("treetable", true)
			})(this);
			function m(v) {
				var n = [];
				var o = {};
				for (var r = 0; r < v.length; r++) {
					var w = v[r];
					var u = w.id;
					var q = w.parent;
					if (!q) {
						var p = o[-1];
						if (!p || p.length < 1) {
							p = []
						}
						p.push(w);
						o[-1] = p
					} else {
						var p = o[q];
						if (!p) {
							p = []
						}
						p.push(w);
						o[q] = p
					}
					n.push(u)
				}
				var s = "," + n.join(",") + ",";
				for (var r = 0; r < v.length; r++) {
					var w = v[r];
					var q = w.parent;
					if (q) {
						if (s.indexOf("," + q + ",") < 0) {
							w.parent = "";
							var p = o[-1];
							if (!p || p.length < 1) {
								p = []
							}
							p.push(w);
							o[-1] = p
						}
					}
				}
				return o
			}
			function k(o, q, n) {
				var s = o[q];
				if (s) {
					for (var p = 0; p < s.length; p++) {
						var r = s[p];
						n.push(r);
						k(o, r.id, n)
					}
				}
			}
			function l(q, p, n) {
				if (q.addEventListener) {
					q.addEventListener(p, n, true);
					return true
				} else {
					if (q.attachEvent) {
						var o = q.attachEvent("on" + p, n);
						return o
					} else {
						return false
					}
				}
			}
		})
	}
})(jQuery);
(function($) {
	function getTreeDataFromSelObj(selObj) {
		var selVal = "," + $(selObj).attr("selectedVal") + ",";
		var array = [];
		var objOpts = selObj.options;
		for (var i = 0; i < objOpts.length; i++) {
			var opt = objOpts[i];
			var optTxt = opt.text;
			var optId = opt.value || opt.id;
			var optSelected = opt.selected;
			var optpId = $(opt).attr("pId");
			if (!optpId || optId == optpId) {
				optpId = ""
			}
			var selected = "";
			if (optSelected
					|| (selVal && selVal.indexOf("," + optId + ",") > -1)) {
				selected = true;
				opt.selected = true
			}
			array.push({
						name : optTxt,
						pId : optpId,
						id : optId,
						selected : selected
					})
		}
		return array
	}
	function getTree(treeParent, isMultiple) {
		var tree = new dhtmlXTreeObject(treeParent, "99%", "99%",
				"root_combotree_0");
		if (isMultiple && ("" + isMultiple).toLowerCase() == "true") {
			tree.setOnCheckHandler(checkStateChange);
			tree.enableCheckBoxes(true)
		}
		tree.setOnOpenEndHandler(onOpenEnd);
		var contextPath = top.contextPath;
		tree.setImagePath(contextPath + "/front-ui/script/dhtmlx/tree/imgs/");
		return tree
	}
	function onOpenEnd(pId, openState) {
		changeTreeSize(this)
	}
	function loadTreeData(tree, dataArray, afterCall, isLoadDate) {
		if (typeof(dataArray) != "object") {
			dataArray = eval("(" + dataArray + ")")
		}
		var itemIds = [];
		var itemMap = {};
		var selOptions = [];
		for (var i = 0; i < dataArray.length; i++) {
			var item = dataArray[i];
			var itemTxt = item.name;
			var itemId = item.id;
			var itemParentId = item.pId;
			var selected = item.selected;
			if (!selected) {
				selected = ""
			}
			if (isLoadDate) {
				var option = new Option(item.name, item.id);
				if (selected) {
					option.selected = true
				}
				selOptions.push(option)
			}
			if (!itemParentId) {
				var children = itemMap[-1];
				if (!children || children.length < 1) {
					children = []
				}
				children.push({
							name : itemTxt,
							pId : itemParentId,
							id : itemId,
							checked : selected
						});
				itemMap[-1] = children
			} else {
				var children = itemMap[itemParentId];
				if (!children) {
					children = []
				}
				children.push({
							name : itemTxt,
							pId : itemParentId,
							id : itemId,
							checked : selected
						});
				itemMap[itemParentId] = children
			}
			itemIds.push(itemId)
		}
		var itemIdsStr = "," + itemIds.join(",") + ",";
		for (var i = 0; i < dataArray.length; i++) {
			var item = dataArray[i];
			var itemParentId = item.pId;
			if (itemParentId) {
				var selected = item.selected;
				if (!selected) {
					selected = ""
				}
				if (itemIdsStr.indexOf("," + itemParentId + ",") < 0) {
					var children = itemMap[-1];
					if (!children || children.length < 1) {
						children = []
					}
					children.push({
								name : item.name,
								pId : itemParentId,
								id : item.id,
								checked : selected
							});
					itemMap[-1] = children
				}
			}
		}
		var xmlStrItems = [];
		getItemXmlStr(itemMap, -1, xmlStrItems);
		tree.loadXMLString("<tree id='" + tree.rootId + "'>"
						+ xmlStrItems.join("") + "</tree>", afterCall);
		var combotree = tree.root;
		var selObj = combotree.selSource;
		if (isLoadDate) {
			selObj.options.length = 0;
			for (var i = 0; i < selOptions.length; i++) {
				selObj.options.add(selOptions[i])
			}
		}
		setTimeout(function() {
					var userWidth = false;
					var width = $(selObj).attr("width");
					if (width) {
						width = width.replace("px", "");
						userWidth = true
					} else {
						selObj.style.display = "block";
						width = selObj.scrollWidth;
						selObj.style.display = "none"
					}
					combotree.setSize(width, userWidth)
				}, 100)
	}
	function getItemXmlStr(itemMap, pId, itemArray) {
		var nodeArray = itemMap[pId];
		if (nodeArray) {
			for (var i = 0; i < nodeArray.length; i++) {
				var item = nodeArray[i];
				itemArray.push("<item checked='" + item.checked + "' select='"
						+ item.checked + "' id='" + item.id + "' text='"
						+ item.name + "'>");
				getItemXmlStr(itemMap, item.id, itemArray);
				itemArray.push("</item>")
			}
		}
	}
	function dhtmlXComboTreeFromSelect(source) {
		if (typeof(source) == "string") {
			source = document.getElementById(source)
		}
		var divCon = document.createElement("DIV");
		divCon.className = "dhx_combo_box";
		source.parentNode.insertBefore(divCon, source);
		source.style.display = "none";
		var comboTree = new dhtmlXComboTree(source, divCon, source.id,
				source.name, source.tabIndex);
		source.removeAttribute("name");
		return comboTree
	}
	function dhtmlXComboTree(source, parent, id, name, tabIndex) {
		var isReadonly = false;
		var isDisabled = false;
		if (source) {
			isReadonly = $(source).attr("readonly");
			isDisabled = source.disabled;
			this.name = source.name
		} else {
			var $divCon = $(parent);
			this.TreeValInput = $divCon.find(".dhx_combo_tree")[0];
			this.TreeValInput._self = this;
			this.TreeTxtInput = $divCon.find(".dhx_combo_input")[0];
			this.TreeBtn = $divCon.find(".dhx_combo_img")[0];
			this.TreeTxtInput.content = this;
			this.TreeBtn.content = this;
			id = this.TreeValInput.id;
			name = this.TreeValInput.name;
			tabIndex = this.TreeTxtInput.tabIndex;
			isReadonly = $(this.TreeTxtInput).attr("readonly");
			isDisabled = this.TreeTxtInput.disabled;
			this.name = name
		}
		if (isReadonly) {
			isReadonly = isReadonly.toLowerCase();
			isReadonly = (isReadonly == "readonly" || isReadonly == "true")
					? true
					: false
		}
		this.value = "";
		this.text = "";
		if (source) {
			this.TreeValInput = document.createElement("INPUT");
			var vAttribute = {
				name : name,
				type : "hidden",
				className : "dhx_combo_tree"
			};
			$(this.TreeValInput).attr(vAttribute);
			this.TreeValInput._self = this;
			parent.appendChild(this.TreeValInput);
			var selClass = $(source).attr("class");
			$(source).removeAttr("class");
			if (selClass) {
				selClass = selClass.replace("easyui-combotree", "");
				$(source).removeClass("easyui-combotree")
			}
			var required = $(source).attr("required");
			var validTitle = $(source).attr("validTitle");
			var comboIptShow = $("<input type='text' validTitle='" + validTitle
					+ "' required='" + required
					+ "' readonly='readonly' tabIndex='" + tabIndex
					+ "' class='input dhx_combo_input " + selClass + "'/>")[0];
			this.TreeTxtInput = comboIptShow;
			comboIptShow.content = this;
			$(comboIptShow).validatebox();
			parent.appendChild(comboIptShow);
			this.TreeBtn = document.createElement("img");
			var treeBtn = this.TreeBtn;
			treeBtn.content = this;
			treeBtn.className = "dhx_combo_img";
			if (dhtmlx.image_path) {
				dhx_globalImgPath = dhtmlx.image_path
			}
			treeBtn.src = (window.dhx_globalImgPath ? dhx_globalImgPath : "")
					+ "combo_select" + (dhtmlx.skin ? "_" + dhtmlx.skin : "")
					+ ".gif";
			parent.appendChild(treeBtn)
		} else {
			source = parent.nextSibling
		}
		this.rootParent = parent;
		if (isReadonly || isDisabled) {
			this.TreeTxtInput.disabled = true;
			treeBtn.disabled = true
		}
		$(this.TreeTxtInput).bind("click", function() {
					toggleOptionTree(this.content);
					return false
				});
		$(this.TreeTxtInput).bind("keypress", function(ev) {
					if (ev.keyCode == 32) {
						toggleOptionTree(this.content);
						return false
					}
				});
		$(this.TreeBtn).bind("click", function() {
					toggleOptionTree(this.content);
					return false
				});
		var treeParent = $("<div class='comboTreeList'>")[0];
		this.treeParent = treeParent;
		var ifa = document.createElement("IFRAME");
		ifa.frameBorder = "no";
		ifa.className = "comboTreeIfa";
		treeParent.appendChild(ifa);
		this.iframe = ifa;
		document.body.insertBefore(treeParent, document.body.firstChild);
		var isMultiple = $(source).attr("multiple");
		var tree = getTree(treeParent, isMultiple);
		tree.root = this;
		tree.tparent = treeParent;
		tree.backFra = ifa;
		this.selSource = source;
		this.tree = tree;
		tree.setOnClickHandler(treeclick);
		tree.onchange = function() {
			if (source.onchange) {
				source.onchange.call(this, arguments)
			}
			$(".easyui-validatebox", parent).validatebox("validate")
		};
		var asyncFlg = $(source).attr("asyncFlg");
		if (("" + asyncFlg).toUpperCase() == "TRUE") {
			var codeSetId = $(source).attr("codeSetId");
			var selectedVal = $(source).attr("selectedVal");
			if (!selectedVal) {
				selectedVal = ""
			}
			this.tree.selectedVal = selectedVal;
			var contextPath = top.contextPath;
			if (selectedVal == "") {
				setTimeout(function() {
							tree.setXMLAutoLoading(contextPath
									+ "/combotree?codeSetId=" + codeSetId);
							tree.loadXML(contextPath + "/combotree?codeSetId="
											+ codeSetId + "&rootid="
											+ tree.rootId + "&selectedVal="
											+ selectedVal, treeLoadAfter)
						}, 1)
			} else {
				tree.setXMLAutoLoading(contextPath + "/combotree?codeSetId="
						+ codeSetId);
				tree.loadXML(contextPath + "/combotree?codeSetId=" + codeSetId
								+ "&rootid=" + tree.rootId + "&selectedVal="
								+ selectedVal, treeLoadAfter)
			}
			var userWidth = false;
			var width = $(source).attr("width");
			if (width) {
				width = width.replace("px", "");
				userWidth = true
			} else {
				width = 153
			}
			this.setSize(width, userWidth)
		} else {
			loadTreeData(tree, getTreeDataFromSelObj(source), treeLoadAfter)
		}
		$(document.body).bind("click", function() {
					treeParent.style.display = "none";
					if (tree._changeFlg && tree.onchange) {
						tree._changeFlg = false;
						tree.onchange.call(tree.root)
					}
				});
		return this
	}
	dhtmlXComboTree.prototype.setSize = function(size, userWidth) {
		if (!isNaN(size)) {
			if (!userWidth) {
				if (size < 153) {
					size = 153
				}
			}
			if (size < 20) {
				size = 40
			}
			this.rootParent.style.width = (size) + "px";
			this.TreeTxtInput.style.width = (size - 17) + "px";
			this.treeParent.style.width = (size) + "px";
			this.iframe.style.width = (size) + "px";
			this.tree.allTree.style.width = (size) + "px";
			this.tree.allTree.style.height = this.treeParent.style.height
		}
	};
	dhtmlXComboTree.prototype.setValue = function(param) {
		var tree = this.tree;
		if (param) {
			this.TreeTxtInput.value = "";
			this.TreeValInput.value = "";
			if (tree.checkBoxOff) {
				tree.setSubChecked(tree.rootId, false);
				param = param.split(",");
				for (var i = 0; i < param.length; i++) {
					tree.selectItem(param[i], true)
				}
			} else {
				tree.selectItem(param, true)
			}
		} else {
			tree.selectItem("", true)
		}
	};
	dhtmlXComboTree.prototype.getValue = function() {
		return this.TreeValInput.value
	};
	dhtmlXComboTree.prototype.getText = function() {
		return this.TreeTxtInput.value
	};
	dhtmlXComboTree.prototype.loadJsonData = function(param) {
		if (param) {
			var tree = this.tree;
			tree.deleteChildItems(tree.rootId);
			loadTreeData(tree, param, treeLoadAfter, true)
		}
	};
	dhtmlXComboTree.prototype.loadXMLString = function(param) {
		if (param) {
			var tree = this.tree;
			tree.loadXMLString(param)
		}
	};
	dhtmlXComboTree.prototype.disable = function(mode) {
		this.TreeTxtInput.disabled = mode;
		this.TreeValInput.disabled = mode;
		this.TreeBtn.disabled = mode
	};
	dhtmlXComboTree.prototype.readOnly = function(mode) {
		this.TreeTxtInput.disabled = mode;
		this.TreeBtn.disabled = mode
	};
	dhtmlXComboTree.prototype.destructor = function() {
		var tree = this.tree;
		var treeParent = this.treeParent;
		var selObj = this.selSource;
		var rootParent = this.rootParent;
		treeParent.parentNode.removeChild(treeParent);
		tree.destructor();
		rootParent.parentNode.removeChild(rootParent);
		selObj.parentNode.removeChild(selObj)
	};
	function treeLoadAfter(tree) {
		if (tree.selectedVal) {
			tree.root.setValue(tree.selectedVal);
			tree.selectedVal = null
		}
		checkStateChange(null, null, tree, true);
		tree._changeFlg = false
	}
	function treeclick(id, lastId, treeObj) {
		var selTxt = treeObj.getItemText(id);
		var treeroot = treeObj.root;
		if (treeObj.checkBoxOff) {
			treeObj.setCheck(id, true)
		}
		checkStateChange(id, null, treeObj);
		treeObj._changeFlg = false;
		treeroot.treeParent.style.display = "none";
		if (treeObj.onchange) {
			treeObj.onchange.call(treeroot)
		}
		if (window.event) {
			event.cancelBubble = true
		}
	}
	function toggleOptionTree(comboTree) {
		if (comboTree && comboTree.treeParent) {
			var treeParent = comboTree.treeParent;
			var pos = getPosition(comboTree.TreeTxtInput);
			treeParent.style.top = pos[1]
					+ (comboTree.TreeTxtInput.offsetHeight) + "px";
			treeParent.style.left = pos[0] + "px";
			var display = treeParent.style.display == "block"
					? "none"
					: "block";
			treeParent.style.display = display
		}
	}
	function hideOptionTree(param) {
		var id = param.data.id;
		if (id) {
			var combotree = $.data($("#" + id)[0], "comboboxtree");
			if (combotree && combotree.treeParent) {
				combotree.treeParent.style.display = "none"
			}
		}
	}
	function checkStateChange(itemId, checkState, tree, isAfterLoad) {
		if (tree) {
			tree._changeFlg = true
		}
		var comboTree = tree.root;
		var itemTxtAndVal = {};
		if (tree.checkBoxOff) {
			itemTxtAndVal = tree.getAllChecked()
		} else {
			if (itemId != null) {
				itemTxtAndVal.val = itemId
			} else {
				itemTxtAndVal.val = ""
			}
			itemTxtAndVal.val = tree.getSelectedItemId();
			itemTxtAndVal.txt = tree.getSelectedItemText()
		}
		var tempVals = "";
		if (itemTxtAndVal) {
			var txt = itemTxtAndVal.txt;
			var val = itemTxtAndVal.val;
			txt = txt.replace(/&amp;/g, "&");
			comboTree.TreeValInput.value = val;
			comboTree.value = val;
			comboTree.TreeTxtInput.value = txt;
			comboTree.text = txt;
			comboTree.TreeTxtInput.title = txt;
			tempVals = val
		} else {
			comboTree.TreeValInput.value = "";
			comboTree.value = "";
			comboTree.TreeTxtInput.value = "";
			comboTree.text = "";
			comboTree.TreeTxtInput.title = ""
		}
		var selObj = comboTree.selSource;
		if (selObj) {
			var selOptions = selObj.options;
			tempVals = "," + tempVals + ",";
			for (var x = 0; x < selOptions.length; x++) {
				var opVal = selOptions[x].value;
				if (tempVals.indexOf("," + opVal + ",") > -1) {
					selOptions[x].selected = true
				} else {
					selOptions[x].selected = false
				}
			}
		}
		if (isAfterLoad) {
			var treeParent = tree.tparent;
			changeTreeSize(tree);
			treeParent.style.display = "none"
		}
	}
	function changeTreeSize(tree) {
		var maxHeight = 300;
		var minHeight = 100;
		var maxWidth = 300;
		var minWidth = 153;
		var treeParent = tree.tparent;
		var treeFra = tree.backFra;
		var allTree = tree.allTree;
		treeParent.style.display = "block";
		var scrollHeight = allTree.scrollHeight + 20;
		var setHeight = 0;
		setHeight = Math.max(minHeight, scrollHeight);
		if (scrollHeight >= minHeight) {
			setHeight = Math.min(maxHeight, scrollHeight)
		}
		treeParent.style.height = setHeight + "px";
		allTree.style.height = setHeight + "px";
		treeFra.style.height = setHeight + "px"
	}
	function getPosition(oNode, pNode) {
		if (_isChrome) {
			if (!pNode) {
				var pNode = document.body
			}
			var oCurrentNode = oNode;
			var iLeft = 0;
			var iTop = 0;
			while ((oCurrentNode) && (oCurrentNode != pNode)) {
				iLeft += oCurrentNode.offsetLeft - oCurrentNode.scrollLeft;
				iTop += oCurrentNode.offsetTop - oCurrentNode.scrollTop;
				oCurrentNode = oCurrentNode.offsetParent
			}
			if (pNode == document.body) {
				if (_isIE && _isIE < 8) {
					if (document.documentElement.scrollTop) {
						iTop += document.documentElement.scrollTop
					}
					if (document.documentElement.scrollLeft) {
						iLeft += document.documentElement.scrollLeft
					}
				} else {
					if (!_isFF) {
						iLeft += document.body.offsetLeft;
						iTop += document.body.offsetTop
					}
				}
			}
			return new Array(iLeft, iTop)
		}
		var pos = getOffset(oNode);
		return [pos.left, pos.top]
	}
	$.fn.combotree = function(options, param) {
		if (typeof options == "string") {
			switch (options) {
				case "setValue" :
					return this.each(function() {
								var combotree = $.data(this, "comboboxtree");
								combotree.setValue(param)
							});
				case "getValue" :
					var selObj = this[0];
					if (!selObj) {
						return null
					}
					var combotree = $.data(selObj, "comboboxtree");
					if (combotree) {
						return combotree.getValue()
					} else {
						return null
					}
				case "getText" :
					var selObj = this[0];
					if (!selObj) {
						return null
					}
					var combotree = $.data(selObj, "comboboxtree");
					if (combotree) {
						return combotree.getText()
					} else {
						return null
					}
				case "setReadOnly" :
				case "readOnly" :
					var selObj = this[0];
					if (!selObj) {
						return null
					}
					var combotree = $.data(selObj, "comboboxtree");
					if (combotree) {
						combotree.readOnly(param);
						return true
					} else {
						return false
					}
				case "setDisable" :
				case "disable" :
					var selObj = this[0];
					if (!selObj) {
						return null
					}
					var combotree = $.data(selObj, "comboboxtree");
					if (combotree) {
						combotree.disable(param);
						return true
					} else {
						return false
					}
				case "loadJsonData" :
					return this.each(function() {
								var combotree = $.data(this, "comboboxtree");
								if (combotree) {
									combotree.loadJsonData(param)
								}
							});
				case "loadXMLString" :
					return this.each(function() {
								var combotree = $.data(this, "comboboxtree");
								if (combotree) {
									combotree.loadXMLString(param)
								}
							});
				case "remove" :
					var selObj = this[0];
					if (!selObj) {
						return false
					}
					var combotree = $.data(selObj, "comboboxtree");
					if (combotree) {
						$(document.body).unbind("click", hideOptionTree);
						combotree.destructor();
						$.data(selObj, "comboboxtree", null);
						return true
					} else {
						return false
					}
			}
		}
		options = options || {};
		return this.each(function() {
					var combotree = null;
					if (this.nodeName == "SELECT") {
						combotree = dhtmlXComboTreeFromSelect(this)
					} else {
						if (this.nodeName == "DIV") {
							combotree = new dhtmlXComboTree(null, this)
						}
					}
					if (combotree) {
						$.data(this, "comboboxtree", combotree)
					}
				})
	};
	$.fn.combotree.defaults = {
		width : "auto",
		treeWidth : null,
		treeHeight : 200,
		url : null,
		onSelect : function(node) {
		},
		onChange : function(newValue, oldValue) {
		}
	}
})(jQuery);
(function($) {
	var __commonData__ = {};
	var getTargetValue = function(targetCode, current) {
		var targetValue;
		if (targetCode.indexOf(".") > 0) {
			var codeArr = targetCode.split(".");
			var rowData = table.getTableRowData(codeArr[0], current);
			targetValue = rowData[codeArr[1]]
		} else {
			targetValue = getValue(targetCode)
		}
		return targetValue
	};
	__commonData__.rules = {
		email : {
			validator : function(value) {
				return /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i
						.test(value)
			},
			message : "请输入一个有效的邮件地址"
		},
		url : {
			validator : function(value) {
				return /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i
						.test(value)
			},
			message : "请输入一个有效的URL"
		},
		length : {
			validator : function(value, param) {
				var len = 0;
				for (var i = value.length; i >= 1; i--) {
					var tmp = value.substring(i - 1, i);
					tmp = tmp.toString();
					var oneChar = tmp.charCodeAt(0);
					if (oneChar > 126) {
						len += 2
					} else {
						len++
					}
				}
				return len >= param[0] && len <= param[1]
			},
			message : function(value, param) {
				if (param[0] == param[1]) {
					if (this.validTitle && this.validTitle != "") {
						return this.validTitle + "的长度应该为{0}"
					}
					return "请输入长度为{0}"
				} else {
					if (this.validTitle && this.validTitle != "") {
						return this.validTitle + "的长度应该在{0}到{1}之间"
					}
					return "请输入长度在{0}到{1}之间"
				}
			}
		},
		range : {
			validator : function(value, param) {
				if (value) {
					var cls = $(this).attr("class");
					if (cls) {
						if (cls.indexOf("easyui-moneybox") > -1
								|| cls.indexOf("easyui-numberbox") > -1
								|| cls.indexOf("easyui-percentbox") > -1) {
							value = ("" + value).replace(/,/g, "").replace("%",
									"");
							value = value - 0;
							if (!value) {
								value = 0
							}
						}
					}
					if (param.length == 1 || !(param[1])) {
						return value >= param[0]
					} else {
						if (!(param[0]) && param[0] != 0) {
							return value <= param[1]
						} else {
							return value >= param[0] && value <= param[1]
						}
					}
				} else {
					return true
				}
			},
			message : function(value, param) {
				var name = "输入的信息";
				if (this.validTitle && this.validTitle != "") {
					name = this.validTitle
				}
				if (param.length == 1 || !(param[1])) {
					return this.validTitle + "不能小于{0}"
				} else {
					if (!(param[0]) && param[0] != 0) {
						return this.validTitle + "不能大于{1}"
					} else {
						return this.validTitle + "应该在{0}到{1}之间"
					}
				}
			}
		},
		custom : {
			validator : function(value, param) {
				if (value) {
					if (typeof(param[1]) == "function") {
						return param[1].call(this, value)
					} else {
						return eval(param[1])
					}
				} else {
					return true
				}
			},
			message : "{0}"
		},
		regexp : {
			validator : function(value, param) {
				if (value) {
					return param[1].test(value)
				} else {
					return true
				}
			},
			message : "{0}"
		},
		greater : {
			validator : function(value, param) {
				if (value) {
					var targetValue = getTargetValue(param[0], this);
					var cls = $(this).attr("class");
					if (cls) {
						if (cls.indexOf("easyui-moneybox") > -1
								|| cls.indexOf("easyui-numberbox") > -1
								|| cls.indexOf("easyui-percentbox") > -1) {
							value = value - 0;
							targetValue = targetValue - 0
						}
					}
					return value > targetValue
				} else {
					return true
				}
			},
			message : function(value, param) {
				var name = "输入的信息";
				if (this.validTitle && this.validTitle != "") {
					name = this.validTitle
				}
				return name + "应该大于{1}"
			}
		},
		greaterEqual : {
			validator : function(value, param) {
				if (value) {
					var targetValue = getTargetValue(param[0], this);
					var cls = $(this).attr("class");
					if (cls) {
						if (cls.indexOf("easyui-moneybox") > -1
								|| cls.indexOf("easyui-numberbox") > -1
								|| cls.indexOf("easyui-percentbox") > -1) {
							value = value - 0;
							targetValue = targetValue - 0
						}
					}
					return value >= targetValue
				} else {
					return true
				}
			},
			message : function(value, param) {
				var name = "输入的信息";
				if (this.validTitle && this.validTitle != "") {
					name = this.validTitle
				}
				return name + "不能小于{1}"
			}
		},
		less : {
			validator : function(value, param) {
				if (value) {
					var targetValue = getTargetValue(param[0], this);
					var cls = $(this).attr("class");
					if (cls) {
						if (cls.indexOf("easyui-moneybox") > -1
								|| cls.indexOf("easyui-numberbox") > -1
								|| cls.indexOf("easyui-percentbox") > -1) {
							value = value - 0;
							targetValue = targetValue - 0
						}
					}
					return value < targetValue
				} else {
					return true
				}
			},
			message : function(value, param) {
				var name = "输入的信息";
				if (this.validTitle && this.validTitle != "") {
					name = this.validTitle
				}
				return name + "应该小于{1}"
			}
		},
		lessEqual : {
			validator : function(value, param) {
				if (value) {
					var targetValue = getTargetValue(param[0], this);
					var cls = $(this).attr("class");
					if (cls) {
						if (cls.indexOf("easyui-moneybox") > -1
								|| cls.indexOf("easyui-numberbox") > -1
								|| cls.indexOf("easyui-percentbox") > -1) {
							value = value - 0;
							targetValue = targetValue - 0
						}
					}
					return value <= targetValue
				} else {
					return true
				}
			},
			message : function(value, param) {
				var name = "输入的信息";
				if (this.validTitle && this.validTitle != "") {
					name = this.validTitle
				}
				return name + "不能大于{1}"
			}
		}
	};
	$.commondata = function(param, valKey, val) {
		if (typeof param == "string") {
			var obj = __commonData__;
			var key = valKey;
			if (typeof valKey == "string") {
				var keys = valKey.split(".");
				var keysLen = keys.length;
				if (keysLen > 1) {
					var i = 0;
					while (i < keysLen - 1) {
						if (!obj[keys[i]]) {
							obj[keys[i]] = {
								___null___ : ""
							};
							obj = obj[keys[i]]
						} else {
							obj = obj[keys[i]]
						}
						i++
					}
				}
				key = keys[keysLen - 1]
			} else {
				if (key.id) {
					key = key.id
				} else {
					if (key.name) {
						key = key.name
					}
				}
			}
			switch (param) {
				case "get" :
					if (!obj) {
						return null
					} else {
						return obj[key]
					}
				case "add" :
					if (obj) {
						if (obj[key] && typeof(val) == "object") {
							var tempObj = obj[key];
							for (tkey in val) {
								tempObj[tkey] = val[tkey]
							}
						} else {
							obj[key] = val
						}
						return true
					} else {
						return false
					}
				case "remove" :
					if (obj) {
						obj[key] = null;
						return true
					} else {
						return false
					}
				default :
					return null
			}
		}
		return this
	}
})(jQuery);
(function(e) {
	function h(o, j) {
		e(o).appendTo("body");
		e(o).addClass("menu-top");
		var m = [];
		n(e(o));
		for (var l = 0; l < m.length; l++) {
			var p = m[l];
			k(p);
			p.find(">div.menu-item").each(function() {
						q(e(this))
					});
			p.find("div.menu-item").click(function() {
						if (!this.submenu) {
							d(o)
						}
						if (!j) {
							return false
						}
					})
		}
		function n(r) {
			m.push(r);
			r.find(">div").each(function() {
						var u = e(this);
						var s = u.find(">div");
						if (s.length) {
							s.insertAfter(o);
							u[0].submenu = s;
							n(s)
						}
					})
		}
		function q(r) {
			r.hover(function() {
						r.siblings().each(function() {
									if (this.submenu) {
										a(this.submenu)
									}
									e(this).removeClass("menu-active")
								});
						r.addClass("menu-active");
						var s = r[0].submenu;
						if (s) {
							var u = r.offset().left + r.outerWidth() - 2;
							if (u + s.outerWidth() > e(window).width()) {
								u = r.offset().left - s.outerWidth() + 2
							}
							c(s, {
										left : u,
										top : r.offset().top - 3
									})
						}
					}, function(u) {
						r.removeClass("menu-active");
						var s = r[0].submenu;
						if (s) {
							if (u.pageX >= parseInt(s.css("left"))) {
								r.addClass("menu-active")
							} else {
								a(s)
							}
						} else {
							r.removeClass("menu-active")
						}
					})
		}
		function k(r) {
			r.addClass("menu").find(">div").each(function() {
				var v = e(this);
				if (v.hasClass("menu-sep")) {
					v.html("&nbsp;")
				} else {
					var w = v.addClass("menu-item").html();
					v.empty()
							.append(e('<div class="menu-text"></div>').html(w));
					var u = v.attr("icon");
					if (u) {
						e('<div class="menu-icon"></div>').addClass(u)
								.appendTo(v)
					}
					if (v[0].submenu) {
						e('<div class="menu-rightarrow"></div>').appendTo(v)
					}
					if (e.boxModel == true) {
						var s = v.height();
						v.height(s - (v.outerHeight() - v.height()))
					}
				}
			});
			r.hide()
		}
	}
	function g(k) {
		var j = k.data;
		d(j);
		return false
	}
	function d(k) {
		var j = e.data(k, "menu").options;
		a(e(k));
		e(document).unbind(".menu");
		j.onHide.call(k);
		return false
	}
	function f(k, l) {
		var j = e.data(k, "menu").options;
		if (l) {
			j.left = l.left;
			j.top = l.top
		}
		c(e(k), {
					left : j.left,
					top : j.top
				}, function() {
					e(document).bind("click.menu", k, g);
					j.onShow.call(k)
				})
	}
	function c(j, l, k) {
		if (!j) {
			return
		}
		if (l) {
			j.css(l)
		}
		j.show(1, function() {
					if (!j[0].shadow) {
						j[0].shadow = e('<div class="menu-shadow"></div>')
								.insertAfter(j)
					}
					j[0].shadow.css({
								display : "block",
								zIndex : e.fn.menu.defaults.zIndex++,
								left : j.css("left"),
								top : j.css("top"),
								width : j.outerWidth(),
								height : j.outerHeight()
							});
					j.css("z-index", e.fn.menu.defaults.zIndex++);
					if (k) {
						k()
					}
				})
	}
	function a(k) {
		if (!k) {
			return
		}
		j(k);
		k.find("div.menu-item").each(function() {
					if (this.submenu) {
						a(this.submenu)
					}
					e(this).removeClass("menu-active")
				});
		function j(l) {
			if (l[0].shadow) {
				l[0].shadow.hide()
			}
			l.hide()
		}
	}
	e.fn.menu = function(j, k) {
		if (typeof j == "string") {
			switch (j) {
				case "show" :
					return this.each(function() {
								f(this, k)
							});
				case "hide" :
					return this.each(function() {
								d(this)
							})
			}
		}
		j = j || {};
		return this.each(function() {
					var l = e.data(this, "menu");
					if (l) {
						e.extend(l.options, j)
					} else {
						l = e.data(this, "menu", {
									options : e.extend({}, e.fn.menu.defaults,
											j)
								});
						h(this, j.multiple)
					}
					e(this).css({
								left : l.options.left,
								top : l.options.top
							})
				})
	};
	e.fn.menu.defaults = {
		zIndex : 110000,
		left : 0,
		top : 0,
		onShow : function() {
		},
		onHide : function() {
		}
	}
})(jQuery);
(function(a) {
	function c(h) {
		var f = a.data(h, "menubutton").options;
		var e = a(h);
		e.removeClass("m-btn-active m-btn-plain-active");
		e.linkbutton(f);
		if (f.menu) {
			a(f.menu).menu({
				onShow : function() {
					e.addClass((f.plain == true)
							? "m-btn-plain-active"
							: "m-btn-active")
				},
				onHide : function() {
					e.removeClass((f.plain == true)
							? "m-btn-plain-active"
							: "m-btn-active")
				}
			})
		}
		e.unbind(".menubutton");
		if (f.disabled == false && f.menu) {
			e.bind("click.menubutton", function() {
						d();
						return false
					});
			var g = null;
			if (h.nodeName != "INPUT" && h.nodeName != "BUTTON") {
				e.bind("mouseenter.menubutton", function() {
							g = setTimeout(function() {
										d()
									}, f.duration);
							return false
						})
			}
			e.bind("mouseleave.menubutton", function() {
						if (g) {
							clearTimeout(g)
						}
					})
		}
		function d() {
			var j = e.offset().left;
			if (j + a(f.menu).outerWidth() + 5 > a(window).width()) {
				j = a(window).width() - a(f.menu).outerWidth() - 5
			}
			a(".menu-top").menu("hide");
			a(f.menu).menu("show", {
						left : j,
						top : e.offset().top + e.outerHeight()
					});
			e.blur()
		}
	}
	a.fn.menubutton = function(d) {
		d = d || {};
		return this.each(function() {
			var e = a.data(this, "menubutton");
			if (e) {
				a.extend(e.options, d)
			} else {
				a.data(this, "menubutton", {
					options : a.extend({}, a.fn.menubutton.defaults, {
								disabled : a(this).attr("disabled") == "true",
								plain : (a(this).attr("plain") == "false"
										? false
										: true),
								menu : a(this).attr("menu"),
								duration : (parseInt(a(this).attr("duration")) || 100)
							}, d)
				});
				a(this).removeAttr("disabled");
				if (this.nodeName != "INPUT" && this.nodeName != "BUTTON") {
					a(this)
							.append('<span class="m-btn-downarrow">&nbsp;</span>')
				}
			}
			c(this)
		})
	};
	a.fn.menubutton.defaults = {
		disabled : false,
		plain : true,
		menu : null,
		duration : 100
	}
})(jQuery);
(function(a) {
	var c = contextPath + "/front-ui/script/easyui/themes/sjhr/images/";
	a.extend({
		progressBar : new function() {
			this.defaults = {
				steps : 20,
				stepDuration : 20,
				max : 100,
				showText : true,
				textFormat : "percentage",
				width : 240,
				height : 20,
				callback : null,
				boxImage : c + "progressbar.gif",
				barImage : {
					0 : c + "progressbg_red.gif",
					33 : c + "progressbg_orange.gif",
					66 : c + "progressbg_green.gif"
				},
				running_value : 0,
				value : 0,
				image : null
			};
			this.construct = function(e, d) {
				var f = null;
				var g = null;
				if (e != null) {
					if (!isNaN(e)) {
						f = e;
						if (d != null) {
							g = d
						}
					} else {
						g = e
					}
				}
				return this.each(function(h) {
					var r = this;
					var k = this.config;
					if (f != null && this.bar != null && this.config != null) {
						this.config.value = parseInt(f);
						if (g != null) {
							r.config = a.extend(this.config, g)
						}
						k = r.config
					} else {
						var p = a(this);
						var k = a.extend({}, a.progressBar.defaults, g);
						k.id = p.attr("id") ? p.attr("id") : Math.ceil(Math
								.random()
								* 100000);
						if (f == null) {
							f = p.html().replace("%", "")
						}
						k.value = parseInt(f);
						k.running_value = 0;
						k.image = o(k);
						var s = ["steps", "stepDuration", "max", "width",
								"height", "running_value", "value"];
						for (var l = 0; l < s.length; l++) {
							k[s[l]] = parseInt(k[s[l]])
						}
						p.html("");
						var n = document.createElement("img");
						var v = document.createElement("span");
						var j = a(n);
						var q = a(v);
						r.bar = j;
						j.attr("id", k.id + "_pbImage");
						q.attr("id", k.id + "_pbText");
						q.html(u(k));
						j.attr("title", u(k));
						j.attr("alt", u(k));
						j.attr("src", k.boxImage);
						j.attr("width", k.width);
						j.css("width", k.width + "px");
						j.css("height", k.height + "px");
						j.css("background-image", "url(" + k.image + ")");
						j.css("background-position", ((k.width * -1))
										+ "px 50%");
						j.css("padding", "0");
						j.css("margin", "0");
						p.append(j);
						p.append(q)
					}
					function m(x) {
						return x.running_value * 100 / x.max
					}
					function o(x) {
						var z = x.barImage;
						if (typeof(x.barImage) == "object") {
							for (var y in x.barImage) {
								if (x.running_value >= parseInt(y)) {
									z = x.barImage[y]
								} else {
									break
								}
							}
						}
						return z
					}
					function u(x) {
						if (x.showText) {
							if (x.textFormat == "percentage") {
								return " " + Math.round(x.running_value) + "%"
							} else {
								if (x.textFormat == "fraction") {
									return " " + x.running_value + "/" + x.max
								}
							}
						}
					}
					k.increment = Math.round((k.value - k.running_value)
							/ k.steps);
					if (k.increment < 0) {
						k.increment *= -1
					}
					if (k.increment < 1) {
						k.increment = 1
					}
					var w = setInterval(function() {
								var A = k.width / 100;
								if (k.running_value > k.value) {
									if (k.running_value - k.increment < k.value) {
										k.running_value = k.value
									} else {
										k.running_value -= k.increment
									}
								} else {
									if (k.running_value < k.value) {
										if (k.running_value + k.increment > k.value) {
											k.running_value = k.value
										} else {
											k.running_value += k.increment
										}
									}
								}
								if (k.running_value == k.value) {
									clearInterval(w)
								}
								var z = a("#" + k.id + "_pbImage");
								var x = a("#" + k.id + "_pbText");
								var y = o(k);
								if (y != k.image) {
									z.css("background-image", "url(" + y + ")");
									k.image = y
								}
								z.css("background-position",
										(((k.width * -1)) + (m(k) * A))
												+ "px 50%");
								z.attr("title", u(k));
								x.html(u(k));
								if (k.callback != null
										&& typeof(k.callback) == "function") {
									k.callback(k)
								}
								r.config = k
							}, k.stepDuration)
				})
			}
		}
	});
	a.fn.extend({
				progressBar : a.progressBar.construct
			})
})(jQuery);
function tablerowMouseOver(c, a) {
	$("input", c).css("background-color", a);
	$(c).css("background-color", a)
}
function tablerowMouseOut(a) {
	$("input", a).css("background-color", "");
	$(a).css("background-color", "")
}
(function(a) {
	a.fn.tableRowColor = function(c) {
		c = c || {};
		return this.each(function() {
			var f = a.data(this, "tableRowColor");
			if (f) {
				c = a.extend(f.options, c)
			} else {
				c = a.extend({}, a.fn.numberbox.defaults, {
							overcolor : (a(this).attr("overcolor") || undefined)
						}, c)
			}
			var e = c.overcolor;
			var d = a(this).find("tr:visible");
			d.unbind(".tablerowcolor").bind({
						"mouseover.tablerowcolor" : function() {
							tablerowMouseOver(this, e)
						},
						"mouseout.tablerowcolor" : function() {
							tablerowMouseOut(this)
						}
					});
			a.data(this, "tableRowColor", {
						options : c
					})
		})
	};
	a.fn.numberbox.defaults = {
		overcolor : "#FFFFB4"
	}
})(jQuery);
(function(a) {
	a.parser = {
		parse : function(f) {
			a(".required", f).each(function(k) {
						a(this)
								.prepend("<span class='required_star'>* </span>")
					});
			var j = [];
			a("*", f).each(function(o, n) {
				if (n.type != "hidden" && !n.disabled) {
					var k = a(n);
					var s = k.attr("class");
					if (s) {
						s = " " + s + " ";
						if (!c(s, " easyui")) {
							return
						}
						if (c(s, " easyui-combobox ")) {
							k.combobox()
						}
						if (c(s, " easyui-combotree ")) {
							k.combotree()
						}
						if (c(s, " easyui-validatebox ")) {
							k.validatebox()
						}
						if (c(s, " easyui-moneybox ")) {
							k.moneybox()
						}
						if (c(s, " easyui-percentbox ")) {
							k.percentbox()
						}
						if (c(s, " easyui-numberbox ")) {
							k.numberbox()
						}
						if (c(s, " easyui-autocomplete ")) {
							k.autocomplete()
						}
						if (c(s, " easyui-datebox ")) {
							k.datebox()
						}
						if (c(s, " easyui-linkbutton ")) {
							k.linkbutton()
						}
						if (c(s, " easyui-accordion ")) {
							k.accordion()
						}
						if (c(s, " easyui-menu ")) {
							k.menu()
						}
						if (c(s, " easyui-menubutton ")) {
							k.menubutton()
						}
						if (c(s, " easyui-splitbutton ")) {
							k.splitbutton()
						}
						if (c(s, " easyui-layout ")) {
							k.layout()
						}
						if (c(s, " easyui-panel ")) {
							k.panel()
						}
						if (c(s, " easyui-tree ")) {
							k.panel()
						}
						if (c(s, " easyui-main-tabs ")) {
							k.tabs()
						}
						if (c(s, " easyui-window ")) {
							k.window()
						}
						if (c(s, " easyui-datagrid ")) {
							k.datagrid()
						}
						if (c(s, " easyui-treetable ")) {
							var p = {
								expanded : true
							};
							if (k.attr("treesState") == "close") {
								p.expanded = false
							}
							k.treetable(p)
						}
						if (c(s, " easyui-checkboxtreetable ")) {
							k.treetable({
										hasCheckbox : true
									})
						}
						if (c(s, " easyui-tabs ")) {
							var u = false;
							if (k.attr("collapsible") == "true") {
								u = true
							}
							var r = !c(s, " ui-tabs");
							k.tabs({
										collapsible : u,
										init : r
									});
							var m = k.attr("selectId");
							if (!(m) || m == "") {
								m = "0"
							} else {
								if (r && m != "0") {
									k.tabs("select", parseInt(m))
								}
							}
							var l = k.attr("id");
							k.bind("tabsshow", function(x, y) {
								var w = a(this).tabs("option", "selected");
								if (u) {
									var v = a(this)
											.find(".ui-tabs-nav-icon:first");
									v
											.attr(
													"src",
													contextPath
															+ "/front-ui/image/panel/expand.png");
									v.attr("selectId", w)
								}
								if (l != "") {
									a(":input[name=" + l + "]").val(w)
								}
								repaintListLayout();
								x.stopPropagation()
							});
							if (u) {
								k.bind("tabsselect", function(w, x) {
									var v = a(this)
											.find(".ui-tabs-nav-icon:first");
									v
											.attr(
													"src",
													contextPath
															+ "/front-ui/image/panel/collapse.png");
									w.stopPropagation()
								});
								var q = a(this);
								q.find(".ui-tabs-nav-icon:first").bind("click",
										function(v) {
											toggleTabsPanel(q, this.expand,
													this.collapse);
											v.stopPropagation()
										}).attr("selectId", m);
								if (q.find(".ui-tabs-nav-icon:first")
										.attr("status") == "collapse") {
									q.find(".ui-tabs-nav-icon:first")
											.trigger("click")
								}
							}
						}
						if (c(s, " easyui-sortable ")) {
							k.sortable()
						}
						if (c(s, " easyui-ckeditor ")) {
							j.push(this)
						}
					}
				}
			});
			var g = j.length;
			if (g > 0) {
				var e = contextPath + "/ckeditor/ckeditor.js";
				var d = contextPath + "/ckfinder/ckfinder.js";
				loadScript(e, function() {
					loadScript(d, function() {
						var l = {
							skin : "v3",
							toolbar : [["FontSize", "-", "Bold", "Italic",
									"Underline", "TextColor", "-",
									"NumberedList", "BulletedList", "-",
									"JustifyLeft", "JustifyCenter", "-",
									"Image", "Flash", "Link", "Unlink", "-",
									"Source"]],
							filebrowserBrowseUrl : contextPath
									+ "/ckfinder/ckfinder.html",
							filebrowserImageBrowseUrl : contextPath
									+ "/ckfinder/ckfinder.html?type=Images",
							filebrowserFlashBrowseUrl : contextPath
									+ "/ckfinder/ckfinder.html?type=Flash",
							filebrowserUploadUrl : contextPath
									+ "/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files",
							filebrowserImageUploadUrl : contextPath
									+ "/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images",
							filebrowserFlashUploadUrl : contextPath
									+ "/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash"
						};
						for (var m = 0; m < g; m++) {
							var k = j[m];
							if (k.width) {
								l.width = k.width
							}
							if (k.height) {
								l.height = k.height
							}
							var n = CKEDITOR.replace(k, l);
							CKFinder.setupCKEditor(n, "../");
							a.data(k, "ckeditor", n)
						}
					})
				})
			}
			var h = a(".list_layout table", f);
			if (h.length) {
				h.tableRowColor()
			}
		}
	};
	a.parser.defaults = {
		auto : true
	};
	function c(d, e) {
		if (d.indexOf(e) < 0) {
			return false
		}
		return true
	}
	a.objFilter = function(d) {
		var e = d.filter(function(f) {
					return a(this).parent().attr("class") != "cellhtml"
				});
		return e
	};
	a(function() {
				a.parser.parse()
			})
})(jQuery);
(function(f) {
	function e(m, q) {
		q = q || {};
		if (q.onSubmit) {
			if (q.onSubmit.call(m) == false) {
				return
			}
		}
		var h = f(m);
		if (q.url) {
			h.attr("action", q.url)
		}
		var n = "easyui_frame_" + (new Date().getTime());
		var j = f("<iframe id=" + n + " name=" + n + "></iframe>").attr("src",
				window.ActiveXObject ? "javascript:false" : "about:blank").css(
				{
					position : "absolute",
					top : -1000,
					left : -1000
				});
		var p = h.attr("target"), o = h.attr("action");
		h.attr("target", n);
		try {
			j.appendTo("body");
			j.bind("load", k);
			h[0].submit()
		} finally {
			h.attr("action", o);
			p ? h.attr("target", p) : h.removeAttr("target")
		}
		var l = 10;
		function k() {
			j.unbind();
			var r = f("#" + n).contents().find("body");
			var u = r.html();
			if (u == "") {
				if (--l) {
					setTimeout(k, 100);
					return
				}
				return
			}
			var s = r.find(">textarea");
			if (s.length) {
				u = s.value()
			} else {
				var v = r.find(">pre");
				if (v.length) {
					u = v.html()
				}
			}
			if (q.success) {
				q.success(u)
			}
			setTimeout(function() {
						j.unbind();
						j.remove()
					}, 100)
		}
	}
	function d(k, j) {
		if (typeof j == "string") {
			f.ajax({
						url : j,
						dataType : "json",
						success : function(l) {
							h(l)
						}
					})
		} else {
			h(j)
		}
		function h(n) {
			var m = f(k);
			for (var l in n) {
				var o = n[l];
				f("input[name=" + l + "]", m).val(o);
				f("textarea[name=" + l + "]", m).val(o);
				f("select[name=" + l + "]", m).val(o)
			}
		}
	}
	function c(h) {
		f("input,select,textarea", h).each(function() {
					var k = this.type, j = this.tagName.toLowerCase();
					if (k == "text" || k == "password" || j == "textarea") {
						this.value = ""
					} else {
						if (k == "checkbox" || k == "radio") {
							this.checked = false
						} else {
							if (j == "select") {
								this.selectedIndex = -1
							}
						}
					}
				})
	}
	function a(k) {
		var h = f.data(k, "form").options;
		var j = f(k);
		j.unbind(".form").bind("submit.form", function() {
					e(k, h);
					return false
				})
	}
	function g(j) {
		if (f.fn.validatebox) {
			var k = f(".validatebox-text:visible", j);
			if (k.length) {
				k.validatebox("validate");
				k.trigger("blur");
				var l = k.validatebox("getMsg");
				var h = f(".validatebox-invalid:first", j).focus();
				return {
					success : h.length == 0,
					msg : l
				}
			}
		}
		return {
			success : true,
			msg : ""
		}
	}
	f.fn.form = function(h, j) {
		if (typeof h == "string") {
			switch (h) {
				case "submit" :
					return this.each(function() {
								e(this, f.extend({}, f.fn.form.defaults, j
														|| {}))
							});
				case "load" :
					return this.each(function() {
								d(this, j)
							});
				case "clear" :
					return this.each(function() {
								c(this)
							});
				case "validate" :
					return g(this[0])
			}
		}
		h = h || {};
		return this.each(function() {
					if (!f.data(this, "form")) {
						f.data(this, "form", {
									options : f.extend({}, f.fn.form.defaults,
											h)
								})
					}
					a(this)
				})
	};
	f.fn.form.defaults = {
		url : null,
		onSubmit : function() {
		},
		success : function(h) {
		}
	}
})(jQuery);
function MapData() {
}
function ListData() {
}
function TreeData() {
}
function DataModel() {
}
var model = new DataModel();