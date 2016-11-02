
(function() {
	var e = window.location,
		d,
		b;
	function c(f) {
		return decodeURIComponent(f.replace(/\+/g, " "))
	}
	function a(j) {
		var h = j.match(/([^\?#&]+)=([^#&]*)/g) || [],
			l = {},
			g,
			f,
			k;
		for (g = 0, f = h.length; g < f; ++g) {
			k = h[g].split("=");
			l[c(k[0])] = c(k[1])
		}
		return l
	}
	if (window.navigator && window.navigator.userAgent.match(/Gecko\//)) {
		d = /#.*$/.exec(e.href);
		d = d && d[0] ? d[0] : ""
	} else {
		d = e.hash
	}
	b = a(d);
	if (b.sd) {
		document.getElementById("yschsp").value = b.sd
	} else {
		if (b._sd) {
			document.getElementById("yschsp").value = ""
		}
	}
})();(function() {
	YUI = {
		Env : {
			mods : {},
			add : function(n, m, l, d) {
				if (n && n.addEventListener) {
					n.addEventListener(m, l, d)
				} else {
					if (n && n.attachEvent) {
						n.attachEvent("on" + m, l)
					}
				}
			},
			remove : function(o, n, m, d) {
				if (o && o.removeEventListener) {
					try {
						o.removeEventListener(n, m, d)
					} catch (l) {}
				} else {
					if (o && o.detachEvent) {
						o.detachEvent("on" + n, m)
					}
				}
			}
		},
		add : function(l, n, d, m) {
			YUI.Env.mods[l] = {
				name : l,
				fn : n,
				version : d,
				details : m || {}
			}
		}
	};
	Y = {
		_pending : [],
		use : function() {
			Y._pending.push(arguments)
		},
		Search : {}
	};
	var i = window,
		g = document,
		k = YUI.Env.add,
		f = YUI.Env.remove,
		b = (function() {
			var d = [];
			function l() {
				setTimeout(function() {
					var n = 0,
						m = d.length;
					for (; n < m; n++) {
						d[n]()
					}
					f(i, "load", l)
				}, 0)
			}
			k(i, "load", l);return {
				add : function(m) {
					d.push(m)
				}
			}
		}()),
		a = (function() {
			var d = navigator.userAgent.match(/MSIE\s([^;]*)/);
			if (d) {
				return parseFloat(d[1])
			}
			return 0
		}());
	function c(d) {
		return d.replace(/^\s+|\s+$/g, "")
	}
	function e(m, d) {
		var l = new RegExp("(\\s|^)" + d + "(\\s|$)");
		return m.className.match(l)
	}
	function j(m, d) {
		var l = new RegExp("(\\s|^)" + d + "(\\s|$)");
		m.className = c(m.className.replace(l, " "))
	}
	function h(l) {
		var d = g.getElementById(l);
		if (d) {
			d.reset()
		}
	}
	Y.Search.Loader = (function() {
		var l = [],
			m = false;
		function o(q, p) {
			var s = g.createElement(q),
				r,
				t;
			for (r in p) {
				if (p.hasOwnProperty(r)) {
					t = p[r];s.setAttribute(r, t)
				}
			}
			return s
		}
		function d() {
			var r = 0,
				p = l.length,
				s,
				t,
				q = g.getElementsByTagName("head")[0];
			for (; r < p; r++) {
				s = l[r];
				if (s.type === "css") {
					t = o("link", {
						href : s.url,
						rel : "stylesheet",
						type : "text/css"
					})
				} else {
					if (s.type === "js") {
						t = o("script", {
							src : s.url
						})
					} else {
						continue
					}
				}
				q.appendChild(t)
			}
			m = true;
			l = []
		}
		function n() {
			var r = arguments[0],
				s = Array.prototype.slice.call(arguments, 1),
				q = 0,
				p = s.length;
			for (; q < p; q++) {
				l.push({
					type : r,
					url : s[q]
				})
			}
			if (m) {
				d()
			}
		}
		b.add(d);return {
			js : function() {
				var p = Array.prototype.slice.call(arguments);
				n.apply(null, [ "js" ].concat(p))
			},
			css : function() {
				var p = Array.prototype.slice.call(arguments);
				n.apply(null, [ "css" ].concat(p))
			}
		}
	}());(function() {
		var d = i.performance || i.webkitPerformance || i.msPerformance || i.mozPerformance || i.Performance;
		if (d) {
			d.now = d.now || d.webkitNow || d.msNow || d.mozNow;
			if (typeof d.now === "function") {
				Y.Search.performance = d
			}
		}
	}());(function() {
		var l = 0;
		function o() {
			if (--l === 0 && typeof rt_AddTime === "function") {
				rt_AddTime("t2")
			}
			if (l === 0 && typeof T !== "undefined") {
				T.stamp("img")
			}
		}
		function p() {
			var C = this,
				B = C.parentNode.parentNode,
				v,
				z,
				y,
				A,
				u;
			o();
			if (C.getAttribute("allowResize") === "true" || e(C, "sm-thumb")) {
				j(B, "sm-hide");
				if (a && e(B, "sm-media")) {
					v = B.offsetWidth;
					z = B.offsetHeight;
					y = C.width;
					A = C.height;
					u = Math.max(y / v, A / z);
					if (u && y && A) {
						C.style.width = (y / u) + "px";
						C.style.height = (A / u) + "px"
					}
				}
			}
		}
		function d() {
			o();
			this.style.display = "none"
		}
		var m = [],
			s = [];
		function r(x) {
			var u = i,
				w = x.getBoundingClientRect(),
				y = g.documentElement,
				v = g.getElementsByTagName("body")[0];
			while (w.height === 0 && w.width === 0 && x.parentNode && typeof x.parentNode.getBoundingClientRect === "function") {
				x = x.parentNode;
				w = x.getBoundingClientRect()
			}
			return {
				v : w.height && w.width && w.top && w.left && w.bottom <= (u.innerHeight || y.clientHeight || v.clientHeight) && w.right <= (u.innerWidth || y.clientWidth || v.clientWidth),
				d : w.top * w.top + w.left * w.left,
				a : w.height && w.width
			}
		}
		function q(u) {
			if (window.devicePixelRatio > 1 && u.hasAttribute("data-src2")) {
				return u.getAttribute("data-src2")
			} else {
				return u.getAttribute("data-src") || u.getAttribute("__src") || u.getAttribute("_src")
			}
		}
		function t(u) {
			var v = q(u);
			if (v) {
				u.onload = p;
				u.onerror = d;u.setAttribute("src", v);u.removeAttribute("data-src2");u.removeAttribute("data-src");u.removeAttribute("__src");u.removeAttribute("_src");j(u, "hidden")
			}
		}
		function n() {
			var u = g.images,
				x = u.length,
				y,
				z;
			for (var w = 0; w < x; w++) {
				y = u[w];
				z = q(y);
				if (z) {
					var v = r(y);
					v.i = y;
					if (v.v) {
						m.push(v)
					} else {
						s.push(v)
					}
				}
			}
			m.sort(function(B, A) {
				return B.d - A.d
			});s.sort(function(B, A) {
				return B.d - A.d
			});
			atfCount = m.length;
			while (m.length) {
				var v = m.shift();
				t(v.i)
			}
			while (s.length) {
				var v = s.shift();
				t(v.i)
			}
		}
		b.add(n)
	}());b.add(function() {
		var l = g.getElementById("yschsp"),
			d = /(^|\s)sd(\s|$)/;
		if (l && d.test(l.parentNode.className)) {
			return
		}
		if (a && (!l || !l.getAttribute("dirty"))) {
			h("sf");h("sB")
		}
	});(function() {
		var q = g.getElementById("web"),
			l = g.getElementById("srp"),
			m = (q) ? q.getElementsByTagName("a") : [],
			m = (l) ? l.getElementsByTagName("a") : m,
			s = /(?:^|\s+)yschttl(?:\s+|$)/,
			o = m.length,
			n,
			r = (function() {
				if (a && a < 7) {
					return function(w, u) {
						var v = new RegExp("^<a.*\\s" + u + '="([^"]*)"', "i"),
							t = v.exec(w.outerHTML);
						return t ? t[1] : null
					}
				} else {
					return function(u, t) {
						return u.getAttribute(t)
					}
				}
			}());
		function p(u) {
			var x = u || i.event,
				v = x.target || x.srcElement,
				w;
			while (v) {
				if (v.tagName && v.tagName.toLowerCase() === "a" && v.getAttribute("dirtyhref")) {
					w = r(v, "dirtyhref");
					if (w) {
						v.setAttribute("href", w)
					}
					break
				}
				v = v.parentNode
			}
		}
		k(g, "mousedown", p);k(g, "click", p);
		function d(w) {
			var t,
				y,
				x,
				v,
				u;
			t = r(w, "href");
			if (!t) {
				return
			}
			y = r(w, "ourl");
			if (!y) {
				x = t.match(/\/RU=([^\/]+)\//);
				if (!x || !x[1]) {
					return
				}
				y = decodeURIComponent(x[1])
			}
			v = w.cloneNode(true);v.setAttribute("href", y);v.setAttribute("dirtyhref", t);
			if (a) {
				u = v.innerHTML;
				t = r(v, "dirtyhref");v.setAttribute("href", t);
				if (v.innerHTML !== u) {
					return
				}
				v.setAttribute("href", y)
			}
			w.parentNode.replaceChild(v, w)
		}
		while (o--) {
			n = m[o];
			if (s.test(n.className)) {
				d(n)
			}
		}
	}())
}());(function() {
	var d = document;
	window.onload = function() {
		var h = d.getElementsByTagName("head")[0],
			o = d.createElement("script");
		o.src = "https://s.yimg.com/pv/static/lib/syc-purple_d1b558d5cfbafcb5a4704fe3e3f8c2f0.js";
		YUI_config = {
			"spaceid" : "2114708002",
			"pvid" : "vOliKTk4LjHa0n9RVfkd_gg2MTgyLgAAAABTIRHA",
			"testid" : "",
			"comboBase" : "https:\/\/s.yimg.com\/zz\/combo?",
			"combine" : true,
			"injected" : true,
			"root" : "yui:3.10.0\/build\/",
			"beacon" : "https:\/\/search.yahoo.com\/beacon\/b.gif",
			"core" : [ "get", "features", "intl-base", "yui-later", "loader-base", "loader-rollup", "loader-yui3" ],
			"debug" : false,
			"sa" : {
				"property" : "web",
				"searchBox" : "#yschsp",
				"hostNodeToAttach" : "#sbq-wrap",
				"maxSuggests" : 10,
				"anyKeySearch" : false,
				"enableTrending" : true,
				"autoAlign" : false,
				"cancelButton" : true,
				"tcpPreConnect" : false,
				"quietMode" : false,
				"ariaPlug" : true,
				"stream" : false,
				"webHistory" : {
					"bcrumb" : "pCQfC4sHTiG",
					"crumb" : "UA5bWBsTJH4",
					"highlightHistory" : false
				},
				"smw" : false,
				"reverseHighlight" : true,
				"subAssist" : true,
				"device" : {
					"tablet" : {
						"skin" : "desktop"
					}
				},
				"dataSrc" : {
					"url" : "https:\/\/sg.search.yahoo.com\/sugg\/gossip\/gossip-sg-ura\/",
					"supportsEmptyQuery" : true
				},
				"lang" : "en-SG",
				"ult" : {
					"spaceId" : "2114708002",
					"csrcpvid" : "vOliKTk4LjHa0n9RVfkd_gg2MTgyLgAAAABTIRHA",
					"vtestid" : "",
					"mtestid" : ""
				},
				"extraUltParams" : {
					"fr" : "",
					"n_rslt" : 0
				},
				"yltCustom" : "_ylt=AwrBTzGPlBlYs98Abvgi4gt.",
				"geo" : [],
				"gossipParams" : {
					"f" : 1
				},
				"https" : true
			},
			"uh" : "https:\/\/s.yimg.com\/zz\/combo?kx\/yucs\/uh3\/uh\/1078\/js\/uh-min.js\u0026kx\/yucs\/uh3\/uh\/1134\/js\/menu_utils_v3-min.js\u0026kx\/yucs\/uhc\/meta\/66\/js\/meta-min.js\u0026kx\/yucs\/uh3\/disclaimer\/388\/js\/disclaimer_seed-min.js\u0026kx\/yucs\/uh3\/top-bar\/321\/js\/top_bar_v3-min.js",
			"fp" : false
		};h.appendChild(o);
	};
}());