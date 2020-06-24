var ou = function (a) {
    return function () {
        return a
    }
};

var pu = function (a, b) {
    for (var c = 0; c < b.length - 2; c += 3) {
        var d = b.charAt(c + 2);
        d = "a" <= d ? d.charCodeAt(0) - 87 : Number(d);
        d = "+" === b.charAt(c + 1) ? a >>> d : a << d;
        a = "+" === b.charAt(c) ? a + d & 4294967295 : a ^ d
    }
    return a
};

var df = Object.freeze ? Object.freeze([]) : [], ef = function (a) {
    var b = a.h + a.g;
    a.b[b] ||
    (a.c = a.b[b] = {})
}, bf = function () {
}, cf = "function" == typeof Uint8Array, ff = function (a, b, c, d) {
    a.a = null;
    b || (b = []);
    a.C = void 0;
    a.g = -1;
    a.b = b;
    a:{
        var e = a.b.length;
        b = -1;
        if (e && (b = e - 1, e = a.b[b], !(null === e || "object" != typeof e || Array.isArray(e) || cf && e instanceof Uint8Array))) {
            a.h = b - a.g;
            a.c = e;
            break a
        }
        -1 < c ? (a.h = Math.max(c, b + 1 - a.g), a.c = null) : a.h = Number.MAX_VALUE
    }
    a.m = {};
    if (d) for (c = 0; c < d.length; c++) b = d[c], b < a.h ? (b += a.g, a.b[b] = a.b[b] || df) : (ef(a), a.c[b] = a.c[b] || df)
};

var  window = {
    "TKK": "442495.623523857"
};
var qu = null;
var ru = function (a) {
    if (null !== qu) var b = qu; else {
        b = ou(String.fromCharCode(84));
        var c = ou(String.fromCharCode(75));
        b = [b(), b()];
        b[1] = c();
        b = (qu = window[b.join(c())] || "") || ""
    }
    var d = ou(String.fromCharCode(116));
    c = ou(String.fromCharCode(107));
    d = [d(), d()];
    d[1] = c();
    c = "&" + d.join("") +
        "=";
    d = b.split(".");
    b = Number(d[0]) || 0;
    for (var e = [], f = 0, g = 0; g < a.length; g++) {
        var h = a.charCodeAt(g);
        128 > h ? e[f++] = h : (2048 > h ? e[f++] = h >> 6 | 192 : (55296 === (h & 64512) && g + 1 < a.length && 56320 === (a.charCodeAt(g + 1) & 64512) ? (h = 65536 + ((h & 1023) << 10) + (a.charCodeAt(++g) & 1023), e[f++] = h >> 18 | 240, e[f++] = h >> 12 & 63 | 128) : e[f++] = h >> 12 | 224, e[f++] = h >> 6 & 63 | 128), e[f++] = h & 63 | 128)
    }
    a = b;
    for (f = 0; f < e.length; f++) a += e[f], a = pu(a, "+-a^+6");
    a = pu(a, "+-3^+b+-f");
    a ^= Number(d[1]) || 0;
    0 > a && (a = (a & 2147483647) + 2147483648);
    a %= 1E6;
    return c + (a.toString() + "." +
        (a ^ b))
};