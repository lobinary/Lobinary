
var a = 0;
var b = 0;
var r = 0;

var TKK = ((function() {
	return r + '.' + (a + b);
})());

/**
 * 获取秘钥
 */
function getKey(at,bt,rt,q){
	a=at;
	b=bt;
	r=rt;
	TKK = ((function() {
		return r + '.' + (a + b);
	})());
	return tk(q);
}


function secondSec(a, b) {
//	console.info("input:[" + a + "],[" + b + "]");
	for (var d = 0; d < b.length - 2; d += 3) {
		//console.info(b.charAt(d + 2));
		var c = b.charAt(d + 2);
		//console.info("1c:"+c);
		c = "a" <= c ? c.charCodeAt(0) - 87 : Number(c);
		//	console.info("2c:"+c);
		//console.info("2a:"+a);
		//console.info("2b:"+b.charAt(d+1));
		//console.info("booleanV:"+("+" == b.charAt(d + 1)));
		c = "+" == b.charAt(d + 1) ? a >>> c : a << c;
		//console.info("3c:"+c);
		a = "+" == b.charAt(d) ? a + c & 4294967295 : a ^ c;
	//console.info("4c:"+c)
	}
//	console.info("output:" + a);
	return a
}

function tk(a) {
	for (var e = TKK.split("."), h = Number(e[0]) || 0, g = [], d = 0, f = 0; f < a.length; f++) {
		var c = a.charCodeAt(f);
		128 > c ? g[d++] = c : (2048 > c ? g[d++] = c >> 6 | 192 : (55296 == (c & 64512) && f + 1 < a.length && 56320 == (a.charCodeAt(f + 1) & 64512) ? (c = 65536 + ((c & 1023) << 10) + (a.charCodeAt(++f) & 1023), g[d++] = c >> 18 | 240, g[d++] = c >> 12 & 63 | 128) : g[d++] = c >> 12 | 224, g[d++] = c >> 6 & 63 | 128), g[d++] = c & 63 | 128)
	}
	a = h;
//	console.info(g.length);
//	console.info(g[0] + ',' + g[1] + ',' + g[2] + ',' + g[3] + ',' + g[4] + ',' + g[5] + ',' + g[6] + ',' + g[7] + ',' + g[8])
	for (d = 0; d < g.length; d++) a += g[d], a = secondSec(a, "+-a^+6");
	a = secondSec(a, "+-3^+b+-f");
	var t = Number(e[1]);
//	console.info(t)	;
	a ^= t || 0;
	0 > a && (a = (a & 2147483647) + 2147483648);
	a %= 1E6;
	return a.toString() + "." + (a ^ h)
}