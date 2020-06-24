var $jscomp = $jscomp || {};
$jscomp.scope = {};
$jscomp.ASSUME_ES5 = !1;
$jscomp.ASSUME_NO_NATIVE_MAP = !1;
$jscomp.ASSUME_NO_NATIVE_SET = !1;
$jscomp.defineProperty = $jscomp.ASSUME_ES5 || "function" == typeof Object.defineProperties ? Object.defineProperty : function (a, f, m) {
    a != Array.prototype && a != Object.prototype && (a[f] = m.value)
};
$jscomp.getGlobal = function (a) {
    return "undefined" != typeof window && window === a ? a : "undefined" != typeof global && null != global ? global : a
};
$jscomp.global = $jscomp.getGlobal(this);
$jscomp.SYMBOL_PREFIX = "jscomp_symbol_";
$jscomp.initSymbol = function () {
    $jscomp.initSymbol = function () {
    };
    $jscomp.global.Symbol || ($jscomp.global.Symbol = $jscomp.Symbol)
};
$jscomp.Symbol = function () {
    var a = 0;
    return function (f) {
        return $jscomp.SYMBOL_PREFIX + (f || "") + a++
    }
}();
$jscomp.initSymbolIterator = function () {
    $jscomp.initSymbol();
    var a = $jscomp.global.Symbol.iterator;
    a || (a = $jscomp.global.Symbol.iterator = $jscomp.global.Symbol("iterator"));
    "function" != typeof Array.prototype[a] && $jscomp.defineProperty(Array.prototype, a, {
        configurable: !0,
        writable: !0,
        value: function () {
            return $jscomp.arrayIterator(this)
        }
    });
    $jscomp.initSymbolIterator = function () {
    }
};
$jscomp.initSymbolAsyncIterator = function () {
    $jscomp.initSymbol();shi
    var a = $jscomp.global.Symbol.asyncIterator;
    a || (a = $jscomp.global.Symbol.asyncIterator = $jscomp.global.Symbol("asyncIterator"));
    $jscomp.initSymbolAsyncIterator = function () {
    }
};
$jscomp.arrayIterator = function (a) {
    var f = 0;
    return $jscomp.iteratorPrototype(function () {
        return f < a.length ? {done: !1, value: a[f++]} : {done: !0}
    })
};
$jscomp.iteratorPrototype = function (a) {
    $jscomp.initSymbolIterator();
    a = {next: a};
    a[$jscomp.global.Symbol.iterator] = function () {
        return this
    };
    return a
};
$jscomp.makeIterator = function (a) {
    $jscomp.initSymbolIterator();
    var f = a[Symbol.iterator];
    return f ? f.call(a) : $jscomp.arrayIterator(a)
};
$jscomp.polyfill = function (a, f, m, n) {
    if (f) {
        m = $jscomp.global;
        a = a.split(".");
        for (n = 0; n < a.length - 1; n++) {
            var g = a[n];
            g in m || (m[g] = {});
            m = m[g]
        }
        a = a[a.length - 1];
        n = m[a];
        f = f(n);
        f != n && null != f && $jscomp.defineProperty(m, a, {configurable: !0, writable: !0, value: f})
    }
};
$jscomp.FORCE_POLYFILL_PROMISE = !1;
$jscomp.polyfill("Promise", function (a) {
    function f() {
        this.batch_ = null
    }

    function m(a) {
        return a instanceof g ? a : new g(function (f, w) {
            f(a)
        })
    }

    if (a && !$jscomp.FORCE_POLYFILL_PROMISE) return a;
    f.prototype.asyncExecute = function (a) {
        null == this.batch_ && (this.batch_ = [], this.asyncExecuteBatch_());
        this.batch_.push(a);
        return this
    };
    f.prototype.asyncExecuteBatch_ = function () {
        var a = this;
        this.asyncExecuteFunction(function () {
            a.executeBatch_()
        })
    };
    var n = $jscomp.global.setTimeout;
    f.prototype.asyncExecuteFunction = function (a) {
        n(a,
            0)
    };
    f.prototype.executeBatch_ = function () {
        for (; this.batch_ && this.batch_.length;) {
            var a = this.batch_;
            this.batch_ = [];
            for (var f = 0; f < a.length; ++f) {
                var g = a[f];
                a[f] = null;
                try {
                    g()
                } catch (B) {
                    this.asyncThrow_(B)
                }
            }
        }
        this.batch_ = null
    };
    f.prototype.asyncThrow_ = function (a) {
        this.asyncExecuteFunction(function () {
            throw a;
        })
    };
    var g = function (a) {
        this.state_ = 0;
        this.result_ = void 0;
        this.onSettledCallbacks_ = [];
        var f = this.createResolveAndReject_();
        try {
            a(f.resolve, f.reject)
        } catch (F) {
            f.reject(F)
        }
    };
    g.prototype.createResolveAndReject_ =
        function () {
            function a(a) {
                return function (w) {
                    g || (g = !0, a.call(f, w))
                }
            }

            var f = this, g = !1;
            return {resolve: a(this.resolveTo_), reject: a(this.reject_)}
        };
    g.prototype.resolveTo_ = function (a) {
        if (a === this) this.reject_(new TypeError("A Promise cannot resolve to itself")); else if (a instanceof g) this.settleSameAsPromise_(a); else {
            a:switch (typeof a) {
                case "object":
                    var f = null != a;
                    break a;
                case "function":
                    f = !0;
                    break a;
                default:
                    f = !1
            }
            f ? this.resolveToNonPromiseObj_(a) : this.fulfill_(a)
        }
    };
    g.prototype.resolveToNonPromiseObj_ = function (a) {
        var f =
            void 0;
        try {
            f = a.then
        } catch (F) {
            this.reject_(F);
            return
        }
        "function" == typeof f ? this.settleSameAsThenable_(f, a) : this.fulfill_(a)
    };
    g.prototype.reject_ = function (a) {
        this.settle_(2, a)
    };
    g.prototype.fulfill_ = function (a) {
        this.settle_(1, a)
    };
    g.prototype.settle_ = function (a, f) {
        if (0 != this.state_) throw Error("Cannot settle(" + a + ", " + f + "): Promise already settled in state" + this.state_);
        this.state_ = a;
        this.result_ = f;
        this.executeOnSettledCallbacks_()
    };
    g.prototype.executeOnSettledCallbacks_ = function () {
        if (null != this.onSettledCallbacks_) {
            for (var a =
                0; a < this.onSettledCallbacks_.length; ++a) t.asyncExecute(this.onSettledCallbacks_[a]);
            this.onSettledCallbacks_ = null
        }
    };
    var t = new f;
    g.prototype.settleSameAsPromise_ = function (a) {
        var f = this.createResolveAndReject_();
        a.callWhenSettled_(f.resolve, f.reject)
    };
    g.prototype.settleSameAsThenable_ = function (a, f) {
        var g = this.createResolveAndReject_();
        try {
            a.call(f, g.resolve, g.reject)
        } catch (B) {
            g.reject(B)
        }
    };
    g.prototype.then = function (a, f) {
        function m(a, f) {
            return "function" == typeof a ? function (f) {
                    try {
                        n(a(f))
                    } catch (H) {
                        w(H)
                    }
                } :
                f
        }

        var n, w, t = new g(function (a, f) {
            n = a;
            w = f
        });
        this.callWhenSettled_(m(a, n), m(f, w));
        return t
    };
    g.prototype.catch = function (a) {
        return this.then(void 0, a)
    };
    g.prototype.callWhenSettled_ = function (a, f) {
        function g() {
            switch (m.state_) {
                case 1:
                    a(m.result_);
                    break;
                case 2:
                    f(m.result_);
                    break;
                default:
                    throw Error("Unexpected state: " + m.state_);
            }
        }

        var m = this;
        null == this.onSettledCallbacks_ ? t.asyncExecute(g) : this.onSettledCallbacks_.push(g)
    };
    g.resolve = m;
    g.reject = function (a) {
        return new g(function (f, g) {
            g(a)
        })
    };
    g.race = function (a) {
        return new g(function (f,
                               g) {
            for (var n = $jscomp.makeIterator(a), t = n.next(); !t.done; t = n.next()) m(t.value).callWhenSettled_(f, g)
        })
    };
    g.all = function (a) {
        var f = $jscomp.makeIterator(a), n = f.next();
        return n.done ? m([]) : new g(function (a, g) {
            function t(f) {
                return function (g) {
                    w[f] = g;
                    y--;
                    0 == y && a(w)
                }
            }

            var w = [], y = 0;
            do w.push(void 0), y++, m(n.value).callWhenSettled_(t(w.length - 1), g), n = f.next(); while (!n.done)
        })
    };
    return g
}, "es6", "es3");
(function () {
    window.__abbaidu_2008_cb = function (a) {
        var f = !1;
        -1 != document.cookie.indexOf("__yjsv5_shitong") && (f = !0);
        var n = new Date;
        n.setTime(n.getTime() + 1728E5);
        var g = JSON.parse(a);
        a = Array(7);
        a[0] = g.data.ver;
        a[1] = g.key_id;
        a[2] = g.data.lid;
        a[3] = g.data.ret_code;
        a[4] = g.data.server_time;
        a[5] = g.data.ip;
        a[6] = g.sign;
        n = ";expires=" + n.toGMTString() + ";path=/;domain=";
        g = document.domain;
        g = psl.parse(g).domain || g;
        n += g;
        a = encodeURIComponent(a.join("_")) + n;
        document.cookie = encodeURIComponent("__yjsv5_shitong") + "=" + a;
        f ||
        (f = new XMLHttpRequest, f.open("GET", "/favicon.ico", !0), f.send(null))
    };
    var a = 'substr;userAgent;createElement;getContext;textBaseline;fillStyle;font;arc;mimeTypes;language;canPlayType;ogg;hls;devicePixelRatio;window;productSub;appCodeName;msMaxTouchPoints;ontouchstart;webgl;prototype;phantom;callSelenium;_selenium;__webdriver_script_fn;__driver_unwrapped;driver;push;slice;join;hex;toLowerCase;toString;key;name;_lastCipherblock;invalid key size (must be 16, 24 or 32 bytes);_Ke;_Kd;encrypt;_aes;PKCS#7 padding byte out of range;__aes_decrypt;rotationRate;chargingTime;appid;type;nodeType;id(";class;[@class=";previousSibling;localName;unshift;isTrusted;clientHeight;exec;mozRTCPeerConnection;webkitRTCPeerConnection;stun:stun.services.mozilla.com;setLocalDescription;createOffer;localDescription;stringify;/abdr;responseText;signSendStartTime;send;interactive;signInitStartTime;//anti-bot.baidu.com;2.3.0;concat;_extra_datagetf;__abbaidu_20180315_cidcb;getElementById;length;fromCharCode;core_sha1;safe_add;chrsz;0123456789abcdef;navigator;screen'.split(";");
    (function (a, m) {
        for (m = ++m; --m;) a.push(a.shift())
    })(a, 238);
    !function () {
        function f(a, c) {
            try {
                return a && a.apply(window, c || []) || ""
            } catch (d) {
                return ""
            }
        }

        function m(a, c) {
            for (var b in c) a[b] = c[b]
        }

        function n(a, c, d) {
            a.addEventListener ? a.addEventListener(c, d, !0) : a.attachEvent && a.attachEvent("on" + c, d, !0)
        }

        function g(a, c, d) {
            a.addEventListener ? a.removeEventListener(c, d, !0) : a.attachEvent && a.detachEvent("on" + c, d, !0)
        }

        function t() {
            return (new Date).getTime()
        }

        function w(b) {
            var c = document[a[5]]("__sdk_log_data");
            if (c) {
                var d =
                    JSON.parse(c.innerHTML || "[]");
                d.push(b);
                c.innerHTML = JSON.stringify(d, null, 4)
            }
        }

        function va() {
            this.encode = function (b) {
                var c, d = "", e = 0;
                b = b.replace(/\r\n/g, "\n");
                var h = "";
                for (c = 0; c < b[a[6]]; c++) {
                    var f = b.charCodeAt(c);
                    128 > f ? h += String[a[7]](f) : (127 < f && 2048 > f ? h += String[a[7]](f >> 6 | 192) : (h += String.fromCharCode(f >> 12 | 224), h += String.fromCharCode(f >> 6 & 63 | 128)), h += String.fromCharCode(63 & f | 128))
                }
                for (b = h; e < b.length;) {
                    h = (c = b.charCodeAt(e++)) >> 2;
                    var g = (3 & c) << 4 | (c = b.charCodeAt(e++)) >> 4;
                    var k = (15 & c) << 2 | (f = b.charCodeAt(e++)) >>
                        6;
                    var l = 63 & f;
                    isNaN(c) ? k = l = 64 : isNaN(f) && (l = 64);
                    d = d + "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".charAt(h) + "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".charAt(g) + "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".charAt(k) + "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".charAt(l)
                }
                return d
            }
        }

        function F(b, c) {
            for (var d = "", e = b.length, h = 0; h < e; h++) d += "," + encodeURIComponent(b[h][c]);
            return d[a[14]](1)
        }

        function B() {
            var b = function () {
                    var b =
                        l[a[16]]("canvas");
                    try {
                        return !(!b.getContext || !b[a[17]]("2d")) + 0
                    } catch (Sa) {
                        return 0
                    }
                }(), c = b ? function (b, c, e) {
                    b = [];
                    e = l.createElement("canvas");
                    e.width = 2E3;
                    e.height = 200;
                    e.style.display = "inline";
                    var d = e.getContext("2d"), h = 2 * Math.PI;
                    return d.rect(0, 0, 10, 10), d.rect(2, 2, 6, 6), d.isPointInPath && b.push("canvas winding:" + (!1 === d.isPointInPath(5, 5, "evenodd") ? "yes" : "no")), d[a[18]] = "alphabetic", d[a[19]] = "#f60", d.fillRect(125, 1, 62, 20), d[a[19]] = "#069", d[a[20]] = "11pt Arial", d.fillText(c, 2, 15), d.fillStyle = "rgba(102, 204, 0, 0.2)",
                        d.font = "18pt Arial", d.fillText(c, 4, 45), d.globalCompositeOperation = "multiply", d.fillStyle = "rgb(255,0,255)", d.beginPath(), d.arc(50, 50, 50, 0, h, !0), d.closePath(), d.fill(), d[a[19]] = "rgb(0,255,255)", d.beginPath(), d[a[21]](100, 50, 50, 0, h, !0), d.closePath(), d.fill(), d.fillStyle = "rgb(255,255,0)", d.beginPath(), d.arc(75, 100, 50, 0, h, !0), d.closePath(), d.fill(), d.fillStyle = "rgb(255,0,255)", d.arc(75, 75, 75, 0, h, !0), d.arc(75, 75, 25, 0, h, !0), d.fill("evenodd"), b.push("canvas fp:" + e.toDataURL()), q.hex_sha1(b.join("~"))
                }(0,
                "antifraud") : "", d = (E.colorDepth || "") + "", e = E.width + "x" + E.height,
                h = E.availWidth + "x" + E.availHeight, g = [E.deviceXDPI || "", E.deviceYDPI || ""].join(),
                wa = F(p.plugins, "name"), n = F(p[a[22]], "description");
            try {
                var m = !!k.localStorage + 0
            } catch (ia) {
                m = 1
            }
            m += "";
            try {
                var I = !!k.sessionStorage + 0
            } catch (ia) {
                I = 1
            }
            I += "";
            var v = (p.cookieEnabled || "") + "", C = (new Date).getTimezoneOffset() + "", r = p[a[23]] || "",
                u = p.systemLanguage || "", w = function () {
                    function b(a, b) {
                        return a.canPlayType(b).replace(/^no$/, "0").replace(/^probably$/, "1").replace(/^maybe$/,
                            "1")
                    }

                    var c = l.createElement("video"), d = !1;
                    return f(function () {
                        (d = !!c[a[24]]) && ((d = new Boolean(d))[a[25]] = b(c, 'video/ogg; codecs="theora"'), d.h264 = b(c, 'video/mp4; codecs="avc1.42E01E"'), d.webm = b(c, 'video/webm; codecs="vp8, vorbis"'), d.vp9 = b(c, 'video/webm; codecs="vp9"'), d.hls = b(c, 'application/x-mpegURL; codecs="avc1.42E01E"'))
                    }), [!!d + 0, d.ogg || "0", d.h264 || "0", d.webm || "0", d.vp9 || "0", d[a[26]] || "0"].join()
                }(), U = (k[a[27]] || "") + "", x = (p.hardwareConcurrency || "") + "", y = function () {
                    var a = l.createElement("div");
                    a.innerHTML = "&nbsp;";
                    var b = !(a.className = "adsbox");
                    try {
                        var c = l.body;
                        c.appendChild(a);
                        b = (0 === l.getElementsByClassName("adsbox")[0].offsetHeight) + 0;
                        c.removeChild(a)
                    } catch (Ta) {
                        b = !1
                    }
                    return b
                }() + "", z = p.doNotTrack || p.msDoNotTrack || k[a[28]].doNotTrack || "",
                A = [p.product, p[a[29]], p.vendor, p.vendorSub, p[a[30]], p.appName, p.platform, eval.toString().length, p.cpuClass || ""].join(),
                D = function () {
                    var b = 0, c = 0;
                    return void 0 !== p.maxTouchPoints ? b = p.maxTouchPoints : void 0 !== p.msMaxTouchPoints && (b = p[a[31]]), f(function () {
                        l.createEvent("TouchEvent");
                        c = 1
                    }), [b, c, (a[32] in k) + 0].join()
                }();
            try {
                var B = !!k.indexedDB + 0
            } catch (ia) {
                B = 1
            }
            J = {
                1: b + "",
                3: c,
                4: d,
                5: e,
                6: h,
                7: g,
                8: wa,
                9: n,
                11: m,
                12: I,
                13: v,
                14: C,
                15: r,
                16: u,
                17: w,
                18: U,
                19: x,
                20: y,
                21: z,
                22: A,
                23: D,
                24: B + "",
                25: function () {
                    try {
                        var b = l.createElement("canvas"),
                            c = b.getContext(a[33]) || b.getContext("experimental-webgl");
                        if (0 <= c.getSupportedExtensions().indexOf("WEBGL_debug_renderer_info")) {
                            var d = c.getExtension("WEBGL_debug_renderer_info");
                            var e = c.getParameter(d.UNMASKED_VENDOR_WEBGL);
                            var h = c.getParameter(d.UNMASKED_RENDERER_WEBGL)
                        } else h =
                            e = "Not supported";
                        return [e, h].join()
                    } catch (Ua) {
                        return ","
                    }
                }(),
                26: null,
                27: p[a[15]] || "",
                28: [!!l.getBoxObjectFor, !!k.opera].join(),
                29: function () {
                    var b = k.eval;
                    return [void 0 === b[a[34]], "eval" === b.name, -1 != b.toString().indexOf("native")].join()
                }(),
                32: "0",
                34: p.platform || "",
                35: [!!(p.battery || p.mozBattery || p.webkitBattery), !!p.getBattery].join(),
                60: !!(k.callPhantom || k._phantom || k[a[35]]),
                61: !!k.__nightmare,
                62: function () {
                    var b = l.documentElement;
                    return "webdriver" in k || "_Selenium_IDE_Recorder" in k || a[36] in k ||
                        a[37] in k || a[38] in l || "__driver_evaluate" in l || "__webdriver_evaluate" in l || "__selenium_evaluate" in l || "__fxdriver_evaluate" in l || a[39] in l || "__webdriver_unwrapped" in l || "__selenium_unwrapped" in l || "__fxdriver_unwrapped" in l || "__webdriver_script_func" in l || null !== b.getAttribute("selenium") || null !== b.getAttribute("webdriver") || null !== b.getAttribute(a[40])
                }(),
                63: !!p.webdriver,
                64: p.webdriver || !1,
                65: !!k.chrome,
                101: "",
                103: t() + "",
                104: "",
                106: "",
                107: V,
                108: l.URL,
                109: l.referrer,
                110: "",
                200: "1"
            }
        }

        function S(a) {
            var b =
                J, d = M;
            D && !a || (D = q.hex_sha1("anti-bot-df" + Math.random() + b[108] + b[109] + b[27] + d));
            f(k[xa], [D])
        }

        function ha(b) {
            if ("number" == typeof b) {
                for (var c = [], d = 0; d < b; d++) c[a[41]](0);
                return c
            }
            for (d = 0; d < b.length; d++) if (0 > b[d] || 256 <= b[d] || "number" != typeof b[d]) throw Error("invalid byte (" + b[d] + ":" + d + ")");
            if (b[a[42]]) return b.slice(0);
            c = [];
            for (d = 0; d < b.length; d++) c[a[41]](b[d]);
            return c
        }

        function T(a) {
            for (var b = [], d = 0; d < a.length; d += 4) b.push(a[d] << 24 | a[d + 1] << 16 | a[d + 2] << 8 | a[d + 3]);
            return b
        }

        function y(b) {
            if (!(this instanceof
                y)) throw Error("AES must be instanitated with `new`");
            this[a[47]] = r(b);
            this._prepare()
        }

        function G(b, c) {
            if (!(this instanceof G)) throw Error("AES must be instanitated with `new`");
            if (this.description = "Cipher Block Chaining", this[a[48]] = "cbc", c) {
                if (16 != c.length) throw Error("invalid initialation vector size (must be 16 bytes)");
            } else c = r([0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]);
            this[a[49]] = r(c);
            this._aes = new y(b)
        }

        function H(b, c, d) {
            c = z.convertStringToBytes(c, "utf8");
            d = z.convertStringToBytes(d, "utf8");
            "object" ==
            typeof b && (b = JSON.stringify(b));
            b = z.convertStringToBytes(b, "utf8");
            b = ja.pkcs7.pad(b);
            c = (new G(c, d))[a[53]](b);
            return z.convertBytesToString(c, "hex")
        }

        function ka(b) {
            var c = b.accelerationIncludingGravity;
            c && (W = c.x || 0, X = c.y || 0, Y = c.z || 0);
            (c = b.acceleration) && (N = c.x || 0, O = c.y || 0, P = c.z || 0);
            (b = b[a[57]]) && (Z = b.alpha || 0, aa = b.beta || 0, ba = b.gamma || 0);
            0 !== K || 0 === N && 0 === O && 0 === P || (K = t());
            ca(!1)
        }

        function ca(b) {
            if (!la) {
                var c = {gotTime: ma, lastIp: na}, d = c.gotTime;
                c = c.lastIp;
                var e = !1;
                if (b || 0 === d || 0 === K || (e = !0), b && (e = !0),
                    e) {
                    g(k, "devicemotion", ka);
                    la = !0;
                    S();
                    b = oa;
                    e = L;
                    var h = M;
                    pa({
                        1: N + "",
                        2: O + "",
                        3: P + "",
                        4: W + "",
                        5: X + "",
                        6: Y + "",
                        7: Z + "",
                        8: aa + "",
                        9: ba + "",
                        10: 0 !== K ? K - h + "" : "-1",
                        11: c,
                        12: 0 !== d ? d - h + "" : "-1",
                        13: b.harging,
                        14: b[a[58]],
                        15: b.dischargingTime,
                        16: b.level,
                        101: D,
                        103: t() + "",
                        104: e.xuid,
                        106: e[a[59]],
                        107: V,
                        108: l.URL,
                        109: l.referrer,
                        110: p[a[15]] || "",
                        200: "3"
                    })
                }
            }
        }

        function qa(b) {
            S();
            var c = D, d = L, e = M, h = t();
            e = h - e + "";
            h += "";
            var f = b[a[60]], m = b.clientX + "", n = b.clientY + "", q = b.pageX + "", I = b.pageY + "";
            a:{
                var v = b.target;
                for (var C = []; v && 1 === v[a[61]]; v =
                    v.parentNode) {
                    if (v.hasAttribute("id")) {
                        v = (C.unshift(a[62] + v.getAttribute("id") + '")'), C.join("/"));
                        break a
                    }
                    if (v.hasAttribute(a[63])) C.unshift(v.localName.toLowerCase() + a[64] + v.getAttribute(a[63]) + '"]'); else {
                        for (var r = 1, u = v[a[65]]; u; u = u.previousSibling) u.localName === v[a[66]] && r++;
                        C[a[67]](v[a[66]].toLowerCase() + "[" + r + "]")
                    }
                }
                v = C.length ? "/" + C[a[43]]("/") : null
            }
            b = {
                1: f,
                2: m,
                3: n,
                4: q,
                5: I,
                6: v,
                7: h,
                8: N + "",
                9: O + "",
                10: P + "",
                11: void 0 === b[a[68]] ? "2" : b.isTrusted ? "1" : "0",
                12: "0",
                13: W + "",
                14: X + "",
                15: Y + "",
                16: Z + "",
                17: aa + "",
                18: ba + "",
                19: (k.innerWidth || l.documentElement.clientWidth || l.body.clientWidth) + "x" + (k.innerHeight || l.documentElement[a[69]] || l.body.clientHeight),
                20: e,
                101: c,
                103: h,
                104: d.xuid,
                106: d.appid,
                107: V,
                108: l.URL,
                109: l.referrer,
                110: p[a[15]] || "",
                200: "2"
            };
            g(l, "click", qa);
            pa(b)
        }

        function pa(a) {
            w(a);
            (new Image).src = da + "/abdr?data=" + encodeURIComponent(ra(JSON.stringify({
                data: H(a, "0fc0d47746054969", "636014d173e04409"),
                key_id: 7
            }))) + "&_=" + Math.random()
        }

        function Q() {
            var a = t();
            M = a;
            !0;
            B();
            var c = f(k[ya]), d = f(k[za]), e = f(k[Aa]),
                h = f(k[Ba]);
            m(J, {32: t() - a + "", 112: c, 113: d, 114: e, 115: h})
        }

        function ea() {
            a[82] !== l.readyState && "complete" !== l.readyState || sa || (window.signInitStartTime && window[a[83]](), sa = 1, (new Q).init({
                appid: 2008,
                xuid: ""
            }))
        }

        var k = window, l = k.document, da = window.location.protocol + a[0], V = a[1],
            ya = "__abbaidu_".concat(2008, "_zidgetf"), za = "__abbaidu_"[a[2]](2008, "_bidgetf"),
            Aa = "__abbaidu_".concat(2008, "_subidgetf"), Ba = "__abbaidu_"[a[2]](2008, a[3]), xa = a[4],
            Ca = "__abbaidu_".concat(2008, "_cb"), ra = k.btoa || function (a) {
                return (new va).encode(a)
            },
            q = {
                hexcase: 0, b64pad: "", chrsz: 8, hex_sha1: function (b) {
                    return q.binb2hex(q[a[8]](q.str2binb(b), b[a[6]] * q.chrsz))
                }, core_sha1: function (b, c) {
                    b[c >> 5] |= 128 << 24 - c % 32;
                    b[15 + (c + 64 >> 9 << 4)] = c;
                    c = Array(80);
                    for (var d = 1732584193, e = -271733879, h = -1732584194, f = 271733878, g = -1009589776, k = 0; k < b[a[6]]; k += 16) {
                        for (var l = d, m = e, n = h, p = f, r = g, u = 0; 80 > u; u++) {
                            c[u] = 16 > u ? b[k + u] : q.rol(c[u - 3] ^ c[u - 8] ^ c[u - 14] ^ c[u - 16], 1);
                            var t = q[a[9]](q.safe_add(q.rol(d, 5), q.sha1_ft(u, e, h, f)), q.safe_add(q.safe_add(g, c[u]), q.sha1_kt(u)));
                            g = f;
                            f = h;
                            h = q.rol(e,
                                30);
                            e = d;
                            d = t
                        }
                        d = q.safe_add(d, l);
                        e = q.safe_add(e, m);
                        h = q.safe_add(h, n);
                        f = q.safe_add(f, p);
                        g = q.safe_add(g, r)
                    }
                    return [d, e, h, f, g]
                }, sha1_ft: function (a, c, d, e) {
                    return 20 > a ? c & d | ~c & e : 40 > a ? c ^ d ^ e : 60 > a ? c & d | c & e | d & e : c ^ d ^ e
                }, sha1_kt: function (a) {
                    return 20 > a ? 1518500249 : 40 > a ? 1859775393 : 60 > a ? -1894007588 : -899497514
                }, safe_add: function (a, c) {
                    var b = (65535 & a) + (65535 & c);
                    return (a >> 16) + (c >> 16) + (b >> 16) << 16 | 65535 & b
                }, rol: function (a, c) {
                    return a << c | a >>> 32 - c
                }, str2binb: function (b) {
                    for (var c = [], d = (1 << q.chrsz) - 1, e = 0; e < b.length * q.chrsz; e += q[a[10]]) c[e >>
                    5] |= (b.charCodeAt(e / q.chrsz) & d) << 24 - e % 32;
                    return c
                }, binb2hex: function (b) {
                    for (var c = a[11], d = "", e = 0; e < 4 * b.length; e++) d += c.charAt(b[e >> 2] >> 8 * (3 - e % 4) + 4 & 15) + c.charAt(b[e >> 2] >> 8 * (3 - e % 4) & 15);
                    return d
                }
            }, p = k[a[12]], E = k[a[13]], J = null, M = 0, D = "",
            oa = {charging: "", chargingTime: "", dischargingTime: "", level: ""}, ma = 0, na = "",
            L = {appid: "", xuid: "", key: ""}, r = null, A = null, fa = null;
        if ("undefined" == typeof Buffer) {
            r = ha;
            A = function (a, c, d, e, h) {
                null == d && (d = 0);
                null == e && (e = 0);
                for (null == h && (h = a.length); e < h; e++) c[d++] = a[e]
            };
            fa = function (b,
                           c) {
                if (null == c || "utf8" == c.toLowerCase().replace(/ |-/g, "")) {
                    c = [];
                    var d = 0;
                    for (b = encodeURI(b); d < b.length;) {
                        var e = b.charCodeAt(d++);
                        37 === e ? (c[a[41]](parseInt(b.substr(d, 2), 16)), d += 2) : c.push(e)
                    }
                    return c
                }
                if ("hex" != c.toLowerCase()) return null;
                c = [];
                for (d = 0; d < b.length; d += 2) c.push(parseInt(b.substr(d, 2), 16));
                return c
            };
            var ta = a[11];
            var ua = function (b, c) {
                if (null == c || "utf8" == c.toLowerCase().replace(/ |-/g, "")) {
                    var d = [];
                    for (c = 0; c < b[a[6]];) {
                        var e = b[c];
                        128 > e ? (d.push(String.fromCharCode(e)), c++) : 191 < e && 224 > e ? (d.push(String.fromCharCode((31 &
                            e) << 6 | 63 & b[c + 1])), c += 2) : (d.push(String.fromCharCode((15 & e) << 12 | (63 & b[c + 1]) << 6 | 63 & b[c + 2])), c += 3)
                    }
                    return d[a[43]]("")
                }
                if (a[44] != c[a[45]]()) return d;
                d = [];
                for (c = 0; c < b.length; c++) e = b[c], d.push(ta[(240 & e) >> 4] + ta[15 & e]);
                return d[a[43]]("")
            }
        } else r = function (a) {
            return new Buffer(a)
        }, A = function (a, c, d, e, h) {
            a.copy(c, d, e, h)
        }, fa = function (a, c) {
            return new Buffer(a, c)
        }, ua = function (b, c) {
            return (new Buffer(b))[a[46]](c)
        };
        var Da = {16: 10, 24: 12, 32: 14},
            Ea = [1, 2, 4, 8, 16, 32, 64, 128, 27, 54, 108, 216, 171, 77, 154, 47, 94, 188, 99, 198, 151,
                53, 106, 212, 179, 125, 250, 239, 197, 145],
            x = [99, 124, 119, 123, 242, 107, 111, 197, 48, 1, 103, 43, 254, 215, 171, 118, 202, 130, 201, 125, 250, 89, 71, 240, 173, 212, 162, 175, 156, 164, 114, 192, 183, 253, 147, 38, 54, 63, 247, 204, 52, 165, 229, 241, 113, 216, 49, 21, 4, 199, 35, 195, 24, 150, 5, 154, 7, 18, 128, 226, 235, 39, 178, 117, 9, 131, 44, 26, 27, 110, 90, 160, 82, 59, 214, 179, 41, 227, 47, 132, 83, 209, 0, 237, 32, 252, 177, 91, 106, 203, 190, 57, 74, 76, 88, 207, 208, 239, 170, 251, 67, 77, 51, 133, 69, 249, 2, 127, 80, 60, 159, 168, 81, 163, 64, 143, 146, 157, 56, 245, 188, 182, 218, 33, 16, 255, 243, 210, 205, 12,
                19, 236, 95, 151, 68, 23, 196, 167, 126, 61, 100, 93, 25, 115, 96, 129, 79, 220, 34, 42, 144, 136, 70, 238, 184, 20, 222, 94, 11, 219, 224, 50, 58, 10, 73, 6, 36, 92, 194, 211, 172, 98, 145, 149, 228, 121, 231, 200, 55, 109, 141, 213, 78, 169, 108, 86, 244, 234, 101, 122, 174, 8, 186, 120, 37, 46, 28, 166, 180, 198, 232, 221, 116, 31, 75, 189, 139, 138, 112, 62, 181, 102, 72, 3, 246, 14, 97, 53, 87, 185, 134, 193, 29, 158, 225, 248, 152, 17, 105, 217, 142, 148, 155, 30, 135, 233, 206, 85, 40, 223, 140, 161, 137, 13, 191, 230, 66, 104, 65, 153, 45, 15, 176, 84, 187, 22],
            R = [82, 9, 106, 213, 48, 54, 165, 56, 191, 64, 163, 158, 129, 243,
                215, 251, 124, 227, 57, 130, 155, 47, 255, 135, 52, 142, 67, 68, 196, 222, 233, 203, 84, 123, 148, 50, 166, 194, 35, 61, 238, 76, 149, 11, 66, 250, 195, 78, 8, 46, 161, 102, 40, 217, 36, 178, 118, 91, 162, 73, 109, 139, 209, 37, 114, 248, 246, 100, 134, 104, 152, 22, 212, 164, 92, 204, 93, 101, 182, 146, 108, 112, 72, 80, 253, 237, 185, 218, 94, 21, 70, 87, 167, 141, 157, 132, 144, 216, 171, 0, 140, 188, 211, 10, 247, 228, 88, 5, 184, 179, 69, 6, 208, 44, 30, 143, 202, 63, 15, 2, 193, 175, 189, 3, 1, 19, 138, 107, 58, 145, 17, 65, 79, 103, 220, 234, 151, 242, 207, 206, 240, 180, 230, 115, 150, 172, 116, 34, 231, 173, 53, 133, 226, 249,
                55, 232, 28, 117, 223, 110, 71, 241, 26, 113, 29, 41, 197, 137, 111, 183, 98, 14, 170, 24, 190, 27, 252, 86, 62, 75, 198, 210, 121, 32, 154, 219, 192, 254, 120, 205, 90, 244, 31, 221, 168, 51, 136, 7, 199, 49, 177, 18, 16, 89, 39, 128, 236, 95, 96, 81, 127, 169, 25, 181, 74, 13, 45, 229, 122, 159, 147, 201, 156, 239, 160, 224, 59, 77, 174, 42, 245, 176, 200, 235, 187, 60, 131, 83, 153, 97, 23, 43, 4, 126, 186, 119, 214, 38, 225, 105, 20, 99, 85, 33, 12, 125],
            Fa = [3328402341, 4168907908, 4000806809, 4135287693, 4294111757, 3597364157, 3731845041, 2445657428, 1613770832, 33620227, 3462883241, 1445669757, 3892248089,
                3050821474, 1303096294, 3967186586, 2412431941, 528646813, 2311702848, 4202528135, 4026202645, 2992200171, 2387036105, 4226871307, 1101901292, 3017069671, 1604494077, 1169141738, 597466303, 1403299063, 3832705686, 2613100635, 1974974402, 3791519004, 1033081774, 1277568618, 1815492186, 2118074177, 4126668546, 2211236943, 1748251740, 1369810420, 3521504564, 4193382664, 3799085459, 2883115123, 1647391059, 706024767, 134480908, 2512897874, 1176707941, 2646852446, 806885416, 932615841, 168101135, 798661301, 235341577, 605164086, 461406363, 3756188221,
                3454790438, 1311188841, 2142417613, 3933566367, 302582043, 495158174, 1479289972, 874125870, 907746093, 3698224818, 3025820398, 1537253627, 2756858614, 1983593293, 3084310113, 2108928974, 1378429307, 3722699582, 1580150641, 327451799, 2790478837, 3117535592, 0, 3253595436, 1075847264, 3825007647, 2041688520, 3059440621, 3563743934, 2378943302, 1740553945, 1916352843, 2487896798, 2555137236, 2958579944, 2244988746, 3151024235, 3320835882, 1336584933, 3992714006, 2252555205, 2588757463, 1714631509, 293963156, 2319795663, 3925473552, 67240454, 4269768577,
                2689618160, 2017213508, 631218106, 1269344483, 2723238387, 1571005438, 2151694528, 93294474, 1066570413, 563977660, 1882732616, 4059428100, 1673313503, 2008463041, 2950355573, 1109467491, 537923632, 3858759450, 4260623118, 3218264685, 2177748300, 403442708, 638784309, 3287084079, 3193921505, 899127202, 2286175436, 773265209, 2479146071, 1437050866, 4236148354, 2050833735, 3362022572, 3126681063, 840505643, 3866325909, 3227541664, 427917720, 2655997905, 2749160575, 1143087718, 1412049534, 999329963, 193497219, 2353415882, 3354324521, 1807268051,
                672404540, 2816401017, 3160301282, 369822493, 2916866934, 3688947771, 1681011286, 1949973070, 336202270, 2454276571, 201721354, 1210328172, 3093060836, 2680341085, 3184776046, 1135389935, 3294782118, 965841320, 831886756, 3554993207, 4068047243, 3588745010, 2345191491, 1849112409, 3664604599, 26054028, 2983581028, 2622377682, 1235855840, 3630984372, 2891339514, 4092916743, 3488279077, 3395642799, 4101667470, 1202630377, 268961816, 1874508501, 4034427016, 1243948399, 1546530418, 941366308, 1470539505, 1941222599, 2546386513, 3421038627, 2715671932,
                3899946140, 1042226977, 2521517021, 1639824860, 227249030, 260737669, 3765465232, 2084453954, 1907733956, 3429263018, 2420656344, 100860677, 4160157185, 470683154, 3261161891, 1781871967, 2924959737, 1773779408, 394692241, 2579611992, 974986535, 664706745, 3655459128, 3958962195, 731420851, 571543859, 3530123707, 2849626480, 126783113, 865375399, 765172662, 1008606754, 361203602, 3387549984, 2278477385, 2857719295, 1344809080, 2782912378, 59542671, 1503764984, 160008576, 437062935, 1707065306, 3622233649, 2218934982, 3496503480, 2185314755, 697932208,
                1512910199, 504303377, 2075177163, 2824099068, 1841019862, 739644986],
            Ga = [2781242211, 2230877308, 2582542199, 2381740923, 234877682, 3184946027, 2984144751, 1418839493, 1348481072, 50462977, 2848876391, 2102799147, 434634494, 1656084439, 3863849899, 2599188086, 1167051466, 2636087938, 1082771913, 2281340285, 368048890, 3954334041, 3381544775, 201060592, 3963727277, 1739838676, 4250903202, 3930435503, 3206782108, 4149453988, 2531553906, 1536934080, 3262494647, 484572669, 2923271059, 1783375398, 1517041206, 1098792767, 49674231, 1334037708, 1550332980,
                4098991525, 886171109, 150598129, 2481090929, 1940642008, 1398944049, 1059722517, 201851908, 1385547719, 1699095331, 1587397571, 674240536, 2704774806, 252314885, 3039795866, 151914247, 908333586, 2602270848, 1038082786, 651029483, 1766729511, 3447698098, 2682942837, 454166793, 2652734339, 1951935532, 775166490, 758520603, 3000790638, 4004797018, 4217086112, 4137964114, 1299594043, 1639438038, 3464344499, 2068982057, 1054729187, 1901997871, 2534638724, 4121318227, 1757008337, 0, 750906861, 1614815264, 535035132, 3363418545, 3988151131, 3201591914,
                1183697867, 3647454910, 1265776953, 3734260298, 3566750796, 3903871064, 1250283471, 1807470800, 717615087, 3847203498, 384695291, 3313910595, 3617213773, 1432761139, 2484176261, 3481945413, 283769337, 100925954, 2180939647, 4037038160, 1148730428, 3123027871, 3813386408, 4087501137, 4267549603, 3229630528, 2315620239, 2906624658, 3156319645, 1215313976, 82966005, 3747855548, 3245848246, 1974459098, 1665278241, 807407632, 451280895, 251524083, 1841287890, 1283575245, 337120268, 891687699, 801369324, 3787349855, 2721421207, 3431482436, 959321879,
                1469301956, 4065699751, 2197585534, 1199193405, 2898814052, 3887750493, 724703513, 2514908019, 2696962144, 2551808385, 3516813135, 2141445340, 1715741218, 2119445034, 2872807568, 2198571144, 3398190662, 700968686, 3547052216, 1009259540, 2041044702, 3803995742, 487983883, 1991105499, 1004265696, 1449407026, 1316239930, 504629770, 3683797321, 168560134, 1816667172, 3837287516, 1570751170, 1857934291, 4014189740, 2797888098, 2822345105, 2754712981, 936633572, 2347923833, 852879335, 1133234376, 1500395319, 3084545389, 2348912013, 1689376213, 3533459022,
                3762923945, 3034082412, 4205598294, 133428468, 634383082, 2949277029, 2398386810, 3913789102, 403703816, 3580869306, 2297460856, 1867130149, 1918643758, 607656988, 4049053350, 3346248884, 1368901318, 600565992, 2090982877, 2632479860, 557719327, 3717614411, 3697393085, 2249034635, 2232388234, 2430627952, 1115438654, 3295786421, 2865522278, 3633334344, 84280067, 33027830, 303828494, 2747425121, 1600795957, 4188952407, 3496589753, 2434238086, 1486471617, 658119965, 3106381470, 953803233, 334231800, 3005978776, 857870609, 3151128937, 1890179545,
                2298973838, 2805175444, 3056442267, 574365214, 2450884487, 550103529, 1233637070, 4289353045, 2018519080, 2057691103, 2399374476, 4166623649, 2148108681, 387583245, 3664101311, 836232934, 3330556482, 3100665960, 3280093505, 2955516313, 2002398509, 287182607, 3413881008, 4238890068, 3597515707, 975967766],
            Ha = [1671808611, 2089089148, 2006576759, 2072901243, 4061003762, 1807603307, 1873927791, 3310653893, 810573872, 16974337, 1739181671, 729634347, 4263110654, 3613570519, 2883997099, 1989864566, 3393556426, 2191335298, 3376449993, 2106063485,
                4195741690, 1508618841, 1204391495, 4027317232, 2917941677, 3563566036, 2734514082, 2951366063, 2629772188, 2767672228, 1922491506, 3227229120, 3082974647, 4246528509, 2477669779, 644500518, 911895606, 1061256767, 4144166391, 3427763148, 878471220, 2784252325, 3845444069, 4043897329, 1905517169, 3631459288, 827548209, 356461077, 67897348, 3344078279, 593839651, 3277757891, 405286936, 2527147926, 84871685, 2595565466, 118033927, 305538066, 2157648768, 3795705826, 3945188843, 661212711, 2999812018, 1973414517, 152769033, 2208177539, 745822252,
                439235610, 455947803, 1857215598, 1525593178, 2700827552, 1391895634, 994932283, 3596728278, 3016654259, 695947817, 3812548067, 795958831, 2224493444, 1408607827, 3513301457, 0, 3979133421, 543178784, 4229948412, 2982705585, 1542305371, 1790891114, 3410398667, 3201918910, 961245753, 1256100938, 1289001036, 1491644504, 3477767631, 3496721360, 4012557807, 2867154858, 4212583931, 1137018435, 1305975373, 861234739, 2241073541, 1171229253, 4178635257, 33948674, 2139225727, 1357946960, 1011120188, 2679776671, 2833468328, 1374921297, 2751356323, 1086357568,
                2408187279, 2460827538, 2646352285, 944271416, 4110742005, 3168756668, 3066132406, 3665145818, 560153121, 271589392, 4279952895, 4077846003, 3530407890, 3444343245, 202643468, 322250259, 3962553324, 1608629855, 2543990167, 1154254916, 389623319, 3294073796, 2817676711, 2122513534, 1028094525, 1689045092, 1575467613, 422261273, 1939203699, 1621147744, 2174228865, 1339137615, 3699352540, 577127458, 712922154, 2427141008, 2290289544, 1187679302, 3995715566, 3100863416, 339486740, 3732514782, 1591917662, 186455563, 3681988059, 3762019296, 844522546,
                978220090, 169743370, 1239126601, 101321734, 611076132, 1558493276, 3260915650, 3547250131, 2901361580, 1655096418, 2443721105, 2510565781, 3828863972, 2039214713, 3878868455, 3359869896, 928607799, 1840765549, 2374762893, 3580146133, 1322425422, 2850048425, 1823791212, 1459268694, 4094161908, 3928346602, 1706019429, 2056189050, 2934523822, 135794696, 3134549946, 2022240376, 628050469, 779246638, 472135708, 2800834470, 3032970164, 3327236038, 3894660072, 3715932637, 1956440180, 522272287, 1272813131, 3185336765, 2340818315, 2323976074, 1888542832,
                1044544574, 3049550261, 1722469478, 1222152264, 50660867, 4127324150, 236067854, 1638122081, 895445557, 1475980887, 3117443513, 2257655686, 3243809217, 489110045, 2662934430, 3778599393, 4162055160, 2561878936, 288563729, 1773916777, 3648039385, 2391345038, 2493985684, 2612407707, 505560094, 2274497927, 3911240169, 3460925390, 1442818645, 678973480, 3749357023, 2358182796, 2717407649, 2306869641, 219617805, 3218761151, 3862026214, 1120306242, 1756942440, 1103331905, 2578459033, 762796589, 252780047, 2966125488, 1425844308, 3151392187, 372911126],
            Ia = [1667474886, 2088535288, 2004326894, 2071694838, 4075949567, 1802223062, 1869591006, 3318043793, 808472672, 16843522, 1734846926, 724270422, 4278065639, 3621216949, 2880169549, 1987484396, 3402253711, 2189597983, 3385409673, 2105378810, 4210693615, 1499065266, 1195886990, 4042263547, 2913856577, 3570689971, 2728590687, 2947541573, 2627518243, 2762274643, 1920112356, 3233831835, 3082273397, 4261223649, 2475929149, 640051788, 909531756, 1061110142, 4160160501, 3435941763, 875846760, 2779116625, 3857003729, 4059105529, 1903268834, 3638064043,
                825316194, 353713962, 67374088, 3351728789, 589522246, 3284360861, 404236336, 2526454071, 84217610, 2593830191, 117901582, 303183396, 2155911963, 3806477791, 3958056653, 656894286, 2998062463, 1970642922, 151591698, 2206440989, 741110872, 437923380, 454765878, 1852748508, 1515908788, 2694904667, 1381168804, 993742198, 3604373943, 3014905469, 690584402, 3823320797, 791638366, 2223281939, 1398011302, 3520161977, 0, 3991743681, 538992704, 4244381667, 2981218425, 1532751286, 1785380564, 3419096717, 3200178535, 960056178, 1246420628, 1280103576, 1482221744,
                3486468741, 3503319995, 4025428677, 2863326543, 4227536621, 1128514950, 1296947098, 859002214, 2240123921, 1162203018, 4193849577, 33687044, 2139062782, 1347481760, 1010582648, 2678045221, 2829640523, 1364325282, 2745433693, 1077985408, 2408548869, 2459086143, 2644360225, 943212656, 4126475505, 3166494563, 3065430391, 3671750063, 555836226, 269496352, 4294908645, 4092792573, 3537006015, 3452783745, 202118168, 320025894, 3974901699, 1600119230, 2543297077, 1145359496, 387397934, 3301201811, 2812801621, 2122220284, 1027426170, 1684319432, 1566435258,
                421079858, 1936954854, 1616945344, 2172753945, 1330631070, 3705438115, 572679748, 707427924, 2425400123, 2290647819, 1179044492, 4008585671, 3099120491, 336870440, 3739122087, 1583276732, 185277718, 3688593069, 3772791771, 842159716, 976899700, 168435220, 1229577106, 101059084, 606366792, 1549591736, 3267517855, 3553849021, 2897014595, 1650632388, 2442242105, 2509612081, 3840161747, 2038008818, 3890688725, 3368567691, 926374254, 1835907034, 2374863873, 3587531953, 1313788572, 2846482505, 1819063512, 1448540844, 4109633523, 3941213647, 1701162954,
                2054852340, 2930698567, 134748176, 3132806511, 2021165296, 623210314, 774795868, 471606328, 2795958615, 3031746419, 3334885783, 3907527627, 3722280097, 1953799400, 522133822, 1263263126, 3183336545, 2341176845, 2324333839, 1886425312, 1044267644, 3048588401, 1718004428, 1212733584, 50529542, 4143317495, 235803164, 1633788866, 892690282, 1465383342, 3115962473, 2256965911, 3250673817, 488449850, 2661202215, 3789633753, 4177007595, 2560144171, 286339874, 1768537042, 3654906025, 2391705863, 2492770099, 2610673197, 505291324, 2273808917, 3924369609,
                3469625735, 1431699370, 673740880, 3755965093, 2358021891, 2711746649, 2307489801, 218961690, 3217021541, 3873845719, 1111672452, 1751693520, 1094828930, 2576986153, 757954394, 252645662, 2964376443, 1414855848, 3149649517, 370555436],
            Ja = [1374988112, 2118214995, 437757123, 975658646, 1001089995, 530400753, 2902087851, 1273168787, 540080725, 2910219766, 2295101073, 4110568485, 1340463100, 3307916247, 641025152, 3043140495, 3736164937, 632953703, 1172967064, 1576976609, 3274667266, 2169303058, 2370213795, 1809054150, 59727847, 361929877, 3211623147,
                2505202138, 3569255213, 1484005843, 1239443753, 2395588676, 1975683434, 4102977912, 2572697195, 666464733, 3202437046, 4035489047, 3374361702, 2110667444, 1675577880, 3843699074, 2538681184, 1649639237, 2976151520, 3144396420, 4269907996, 4178062228, 1883793496, 2403728665, 2497604743, 1383856311, 2876494627, 1917518562, 3810496343, 1716890410, 3001755655, 800440835, 2261089178, 3543599269, 807962610, 599762354, 33778362, 3977675356, 2328828971, 2809771154, 4077384432, 1315562145, 1708848333, 101039829, 3509871135, 3299278474, 875451293, 2733856160,
                92987698, 2767645557, 193195065, 1080094634, 1584504582, 3178106961, 1042385657, 2531067453, 3711829422, 1306967366, 2438237621, 1908694277, 67556463, 1615861247, 429456164, 3602770327, 2302690252, 1742315127, 2968011453, 126454664, 3877198648, 2043211483, 2709260871, 2084704233, 4169408201, 0, 159417987, 841739592, 504459436, 1817866830, 4245618683, 260388950, 1034867998, 908933415, 168810852, 1750902305, 2606453969, 607530554, 202008497, 2472011535, 3035535058, 463180190, 2160117071, 1641816226, 1517767529, 470948374, 3801332234, 3231722213,
                1008918595, 303765277, 235474187, 4069246893, 766945465, 337553864, 1475418501, 2943682380, 4003061179, 2743034109, 4144047775, 1551037884, 1147550661, 1543208500, 2336434550, 3408119516, 3069049960, 3102011747, 3610369226, 1113818384, 328671808, 2227573024, 2236228733, 3535486456, 2935566865, 3341394285, 496906059, 3702665459, 226906860, 2009195472, 733156972, 2842737049, 294930682, 1206477858, 2835123396, 2700099354, 1451044056, 573804783, 2269728455, 3644379585, 2362090238, 2564033334, 2801107407, 2776292904, 3669462566, 1068351396, 742039012,
                1350078989, 1784663195, 1417561698, 4136440770, 2430122216, 775550814, 2193862645, 2673705150, 1775276924, 1876241833, 3475313331, 3366754619, 270040487, 3902563182, 3678124923, 3441850377, 1851332852, 3969562369, 2203032232, 3868552805, 2868897406, 566021896, 4011190502, 3135740889, 1248802510, 3936291284, 699432150, 832877231, 708780849, 3332740144, 899835584, 1951317047, 4236429990, 3767586992, 866637845, 4043610186, 1106041591, 2144161806, 395441711, 1984812685, 1139781709, 3433712980, 3835036895, 2664543715, 1282050075, 3240894392, 1181045119,
                2640243204, 25965917, 4203181171, 4211818798, 3009879386, 2463879762, 3910161971, 1842759443, 2597806476, 933301370, 1509430414, 3943906441, 3467192302, 3076639029, 3776767469, 2051518780, 2631065433, 1441952575, 404016761, 1942435775, 1408749034, 1610459739, 3745345300, 2017778566, 3400528769, 3110650942, 941896748, 3265478751, 371049330, 3168937228, 675039627, 4279080257, 967311729, 135050206, 3635733660, 1683407248, 2076935265, 3576870512, 1215061108, 3501741890],
            Ka = [1347548327, 1400783205, 3273267108, 2520393566, 3409685355, 4045380933,
                2880240216, 2471224067, 1428173050, 4138563181, 2441661558, 636813900, 4233094615, 3620022987, 2149987652, 2411029155, 1239331162, 1730525723, 2554718734, 3781033664, 46346101, 310463728, 2743944855, 3328955385, 3875770207, 2501218972, 3955191162, 3667219033, 768917123, 3545789473, 692707433, 1150208456, 1786102409, 2029293177, 1805211710, 3710368113, 3065962831, 401639597, 1724457132, 3028143674, 409198410, 2196052529, 1620529459, 1164071807, 3769721975, 2226875310, 486441376, 2499348523, 1483753576, 428819965, 2274680428, 3075636216, 598438867,
                3799141122, 1474502543, 711349675, 129166120, 53458370, 2592523643, 2782082824, 4063242375, 2988687269, 3120694122, 1559041666, 730517276, 2460449204, 4042459122, 2706270690, 3446004468, 3573941694, 533804130, 2328143614, 2637442643, 2695033685, 839224033, 1973745387, 957055980, 2856345839, 106852767, 1371368976, 4181598602, 1033297158, 2933734917, 1179510461, 3046200461, 91341917, 1862534868, 4284502037, 605657339, 2547432937, 3431546947, 2003294622, 3182487618, 2282195339, 954669403, 3682191598, 1201765386, 3917234703, 3388507166, 0, 2198438022,
                1211247597, 2887651696, 1315723890, 4227665663, 1443857720, 507358933, 657861945, 1678381017, 560487590, 3516619604, 975451694, 2970356327, 261314535, 3535072918, 2652609425, 1333838021, 2724322336, 1767536459, 370938394, 182621114, 3854606378, 1128014560, 487725847, 185469197, 2918353863, 3106780840, 3356761769, 2237133081, 1286567175, 3152976349, 4255350624, 2683765030, 3160175349, 3309594171, 878443390, 1988838185, 3704300486, 1756818940, 1673061617, 3403100636, 272786309, 1075025698, 545572369, 2105887268, 4174560061, 296679730, 1841768865,
                1260232239, 4091327024, 3960309330, 3497509347, 1814803222, 2578018489, 4195456072, 575138148, 3299409036, 446754879, 3629546796, 4011996048, 3347532110, 3252238545, 4270639778, 915985419, 3483825537, 681933534, 651868046, 2755636671, 3828103837, 223377554, 2607439820, 1649704518, 3270937875, 3901806776, 1580087799, 4118987695, 3198115200, 2087309459, 2842678573, 3016697106, 1003007129, 2802849917, 1860738147, 2077965243, 164439672, 4100872472, 32283319, 2827177882, 1709610350, 2125135846, 136428751, 3874428392, 3652904859, 3460984630, 3572145929,
                3593056380, 2939266226, 824852259, 818324884, 3224740454, 930369212, 2801566410, 2967507152, 355706840, 1257309336, 4148292826, 243256656, 790073846, 2373340630, 1296297904, 1422699085, 3756299780, 3818836405, 457992840, 3099667487, 2135319889, 77422314, 1560382517, 1945798516, 788204353, 1521706781, 1385356242, 870912086, 325965383, 2358957921, 2050466060, 2388260884, 2313884476, 4006521127, 901210569, 3990953189, 1014646705, 1503449823, 1062597235, 2031621326, 3212035895, 3931371469, 1533017514, 350174575, 2256028891, 2177544179, 1052338372,
                741876788, 1606591296, 1914052035, 213705253, 2334669897, 1107234197, 1899603969, 3725069491, 2631447780, 2422494913, 1635502980, 1893020342, 1950903388, 1120974935],
            La = [2807058932, 1699970625, 2764249623, 1586903591, 1808481195, 1173430173, 1487645946, 59984867, 4199882800, 1844882806, 1989249228, 1277555970, 3623636965, 3419915562, 1149249077, 2744104290, 1514790577, 459744698, 244860394, 3235995134, 1963115311, 4027744588, 2544078150, 4190530515, 1608975247, 2627016082, 2062270317, 1507497298, 2200818878, 567498868, 1764313568, 3359936201,
                2305455554, 2037970062, 1047239E3, 1910319033, 1337376481, 2904027272, 2892417312, 984907214, 1243112415, 830661914, 861968209, 2135253587, 2011214180, 2927934315, 2686254721, 731183368, 1750626376, 4246310725, 1820824798, 4172763771, 3542330227, 48394827, 2404901663, 2871682645, 671593195, 3254988725, 2073724613, 145085239, 2280796200, 2779915199, 1790575107, 2187128086, 472615631, 3029510009, 4075877127, 3802222185, 4107101658, 3201631749, 1646252340, 4270507174, 1402811438, 1436590835, 3778151818, 3950355702, 3963161475, 4020912224, 2667994737,
                273792366, 2331590177, 104699613, 95345982, 3175501286, 2377486676, 1560637892, 3564045318, 369057872, 4213447064, 3919042237, 1137477952, 2658625497, 1119727848, 2340947849, 1530455833, 4007360968, 172466556, 266959938, 516552836, 0, 2256734592, 3980931627, 1890328081, 1917742170, 4294704398, 945164165, 3575528878, 958871085, 3647212047, 2787207260, 1423022939, 775562294, 1739656202, 3876557655, 2530391278, 2443058075, 3310321856, 547512796, 1265195639, 437656594, 3121275539, 719700128, 3762502690, 387781147, 218828297, 3350065803, 2830708150,
                2848461854, 428169201, 122466165, 3720081049, 1627235199, 648017665, 4122762354, 1002783846, 2117360635, 695634755, 3336358691, 4234721005, 4049844452, 3704280881, 2232435299, 574624663, 287343814, 612205898, 1039717051, 840019705, 2708326185, 793451934, 821288114, 1391201670, 3822090177, 376187827, 3113855344, 1224348052, 1679968233, 2361698556, 1058709744, 752375421, 2431590963, 1321699145, 3519142200, 2734591178, 188127444, 2177869557, 3727205754, 2384911031, 3215212461, 2648976442, 2450346104, 3432737375, 1180849278, 331544205, 3102249176,
                4150144569, 2952102595, 2159976285, 2474404304, 766078933, 313773861, 2570832044, 2108100632, 1668212892, 3145456443, 2013908262, 418672217, 3070356634, 2594734927, 1852171925, 3867060991, 3473416636, 3907448597, 2614737639, 919489135, 164948639, 2094410160, 2997825956, 590424639, 2486224549, 1723872674, 3157750862, 3399941250, 3501252752, 3625268135, 2555048196, 3673637356, 1343127501, 4130281361, 3599595085, 2957853679, 1297403050, 81781910, 3051593425, 2283490410, 532201772, 1367295589, 3926170974, 895287692, 1953757831, 1093597963, 492483431,
                3528626907, 1446242576, 1192455638, 1636604631, 209336225, 344873464, 1015671571, 669961897, 3375740769, 3857572124, 2973530695, 3747192018, 1933530610, 3464042516, 935293895, 3454686199, 2858115069, 1863638845, 3683022916, 4085369519, 3292445032, 875313188, 1080017571, 3279033885, 621591778, 1233856572, 2504130317, 24197544, 3017672716, 3835484340, 3247465558, 2220981195, 3060847922, 1551124588, 1463996600],
            Ma = [4104605777, 1097159550, 396673818, 660510266, 2875968315, 2638606623, 4200115116, 3808662347, 821712160, 1986918061, 3430322568, 38544885,
                3856137295, 718002117, 893681702, 1654886325, 2975484382, 3122358053, 3926825029, 4274053469, 796197571, 1290801793, 1184342925, 3556361835, 2405426947, 2459735317, 1836772287, 1381620373, 3196267988, 1948373848, 3764988233, 3385345166, 3263785589, 2390325492, 1480485785, 3111247143, 3780097726, 2293045232, 548169417, 3459953789, 3746175075, 439452389, 1362321559, 1400849762, 1685577905, 1806599355, 2174754046, 137073913, 1214797936, 1174215055, 3731654548, 2079897426, 1943217067, 1258480242, 529487843, 1437280870, 3945269170, 3049390895, 3313212038,
                923313619, 679998E3, 3215307299, 57326082, 377642221, 3474729866, 2041877159, 133361907, 1776460110, 3673476453, 96392454, 878845905, 2801699524, 777231668, 4082475170, 2330014213, 4142626212, 2213296395, 1626319424, 1906247262, 1846563261, 562755902, 3708173718, 1040559837, 3871163981, 1418573201, 3294430577, 114585348, 1343618912, 2566595609, 3186202582, 1078185097, 3651041127, 3896688048, 2307622919, 425408743, 3371096953, 2081048481, 1108339068, 2216610296, 0, 2156299017, 736970802, 292596766, 1517440620, 251657213, 2235061775, 2933202493,
                758720310, 265905162, 1554391400, 1532285339, 908999204, 174567692, 1474760595, 4002861748, 2610011675, 3234156416, 3693126241, 2001430874, 303699484, 2478443234, 2687165888, 585122620, 454499602, 151849742, 2345119218, 3064510765, 514443284, 4044981591, 1963412655, 2581445614, 2137062819, 19308535, 1928707164, 1715193156, 4219352155, 1126790795, 600235211, 3992742070, 3841024952, 836553431, 1669664834, 2535604243, 3323011204, 1243905413, 3141400786, 4180808110, 698445255, 2653899549, 2989552604, 2253581325, 3252932727, 3004591147, 1891211689,
                2487810577, 3915653703, 4237083816, 4030667424, 2100090966, 865136418, 1229899655, 953270745, 3399679628, 3557504664, 4118925222, 2061379749, 3079546586, 2915017791, 983426092, 2022837584, 1607244650, 2118541908, 2366882550, 3635996816, 972512814, 3283088770, 1568718495, 3499326569, 3576539503, 621982671, 2895723464, 410887952, 2623762152, 1002142683, 645401037, 1494807662, 2595684844, 1335535747, 2507040230, 4293295786, 3167684641, 367585007, 3885750714, 1865862730, 2668221674, 2960971305, 2763173681, 1059270954, 2777952454, 2724642869, 1320957812,
                2194319100, 2429595872, 2815956275, 77089521, 3973773121, 3444575871, 2448830231, 1305906550, 4021308739, 2857194700, 2516901860, 3518358430, 1787304780, 740276417, 1699839814, 1592394909, 2352307457, 2272556026, 188821243, 1729977011, 3687994002, 274084841, 3594982253, 3613494426, 2701949495, 4162096729, 322734571, 2837966542, 1640576439, 484830689, 1202797690, 3537852828, 4067639125, 349075736, 3342319475, 4157467219, 4255800159, 1030690015, 1155237496, 2951971274, 1757691577, 607398968, 2738905026, 499347990, 3794078908, 1011452712, 227885567,
                2818666809, 213114376, 3034881240, 1455525988, 3414450555, 850817237, 1817998408, 3092726480],
            Na = [0, 235474187, 470948374, 303765277, 941896748, 908933415, 607530554, 708780849, 1883793496, 2118214995, 1817866830, 1649639237, 1215061108, 1181045119, 1417561698, 1517767529, 3767586992, 4003061179, 4236429990, 4069246893, 3635733660, 3602770327, 3299278474, 3400528769, 2430122216, 2664543715, 2362090238, 2193862645, 2835123396, 2801107407, 3035535058, 3135740889, 3678124923, 3576870512, 3341394285, 3374361702, 3810496343, 3977675356, 4279080257,
                4043610186, 2876494627, 2776292904, 3076639029, 3110650942, 2472011535, 2640243204, 2403728665, 2169303058, 1001089995, 899835584, 666464733, 699432150, 59727847, 226906860, 530400753, 294930682, 1273168787, 1172967064, 1475418501, 1509430414, 1942435775, 2110667444, 1876241833, 1641816226, 2910219766, 2743034109, 2976151520, 3211623147, 2505202138, 2606453969, 2302690252, 2269728455, 3711829422, 3543599269, 3240894392, 3475313331, 3843699074, 3943906441, 4178062228, 4144047775, 1306967366, 1139781709, 1374988112, 1610459739, 1975683434, 2076935265,
                1775276924, 1742315127, 1034867998, 866637845, 566021896, 800440835, 92987698, 193195065, 429456164, 395441711, 1984812685, 2017778566, 1784663195, 1683407248, 1315562145, 1080094634, 1383856311, 1551037884, 101039829, 135050206, 437757123, 337553864, 1042385657, 807962610, 573804783, 742039012, 2531067453, 2564033334, 2328828971, 2227573024, 2935566865, 2700099354, 3001755655, 3168937228, 3868552805, 3902563182, 4203181171, 4102977912, 3736164937, 3501741890, 3265478751, 3433712980, 1106041591, 1340463100, 1576976609, 1408749034, 2043211483,
                2009195472, 1708848333, 1809054150, 832877231, 1068351396, 766945465, 599762354, 159417987, 126454664, 361929877, 463180190, 2709260871, 2943682380, 3178106961, 3009879386, 2572697195, 2538681184, 2236228733, 2336434550, 3509871135, 3745345300, 3441850377, 3274667266, 3910161971, 3877198648, 4110568485, 4211818798, 2597806476, 2497604743, 2261089178, 2295101073, 2733856160, 2902087851, 3202437046, 2968011453, 3936291284, 3835036895, 4136440770, 4169408201, 3535486456, 3702665459, 3467192302, 3231722213, 2051518780, 1951317047, 1716890410, 1750902305,
                1113818384, 1282050075, 1584504582, 1350078989, 168810852, 67556463, 371049330, 404016761, 841739592, 1008918595, 775550814, 540080725, 3969562369, 3801332234, 4035489047, 4269907996, 3569255213, 3669462566, 3366754619, 3332740144, 2631065433, 2463879762, 2160117071, 2395588676, 2767645557, 2868897406, 3102011747, 3069049960, 202008497, 33778362, 270040487, 504459436, 875451293, 975658646, 675039627, 641025152, 2084704233, 1917518562, 1615861247, 1851332852, 1147550661, 1248802510, 1484005843, 1451044056, 933301370, 967311729, 733156972, 632953703,
                260388950, 25965917, 328671808, 496906059, 1206477858, 1239443753, 1543208500, 1441952575, 2144161806, 1908694277, 1675577880, 1842759443, 3610369226, 3644379585, 3408119516, 3307916247, 4011190502, 3776767469, 4077384432, 4245618683, 2809771154, 2842737049, 3144396420, 3043140495, 2673705150, 2438237621, 2203032232, 2370213795],
            Oa = [0, 185469197, 370938394, 487725847, 741876788, 657861945, 975451694, 824852259, 1483753576, 1400783205, 1315723890, 1164071807, 1950903388, 2135319889, 1649704518, 1767536459, 2967507152, 3152976349, 2801566410,
                2918353863, 2631447780, 2547432937, 2328143614, 2177544179, 3901806776, 3818836405, 4270639778, 4118987695, 3299409036, 3483825537, 3535072918, 3652904859, 2077965243, 1893020342, 1841768865, 1724457132, 1474502543, 1559041666, 1107234197, 1257309336, 598438867, 681933534, 901210569, 1052338372, 261314535, 77422314, 428819965, 310463728, 3409685355, 3224740454, 3710368113, 3593056380, 3875770207, 3960309330, 4045380933, 4195456072, 2471224067, 2554718734, 2237133081, 2388260884, 3212035895, 3028143674, 2842678573, 2724322336, 4138563181, 4255350624,
                3769721975, 3955191162, 3667219033, 3516619604, 3431546947, 3347532110, 2933734917, 2782082824, 3099667487, 3016697106, 2196052529, 2313884476, 2499348523, 2683765030, 1179510461, 1296297904, 1347548327, 1533017514, 1786102409, 1635502980, 2087309459, 2003294622, 507358933, 355706840, 136428751, 53458370, 839224033, 957055980, 605657339, 790073846, 2373340630, 2256028891, 2607439820, 2422494913, 2706270690, 2856345839, 3075636216, 3160175349, 3573941694, 3725069491, 3273267108, 3356761769, 4181598602, 4063242375, 4011996048, 3828103837, 1033297158,
                915985419, 730517276, 545572369, 296679730, 446754879, 129166120, 213705253, 1709610350, 1860738147, 1945798516, 2029293177, 1239331162, 1120974935, 1606591296, 1422699085, 4148292826, 4233094615, 3781033664, 3931371469, 3682191598, 3497509347, 3446004468, 3328955385, 2939266226, 2755636671, 3106780840, 2988687269, 2198438022, 2282195339, 2501218972, 2652609425, 1201765386, 1286567175, 1371368976, 1521706781, 1805211710, 1620529459, 2105887268, 1988838185, 533804130, 350174575, 164439672, 46346101, 870912086, 954669403, 636813900, 788204353,
                2358957921, 2274680428, 2592523643, 2441661558, 2695033685, 2880240216, 3065962831, 3182487618, 3572145929, 3756299780, 3270937875, 3388507166, 4174560061, 4091327024, 4006521127, 3854606378, 1014646705, 930369212, 711349675, 560487590, 272786309, 457992840, 106852767, 223377554, 1678381017, 1862534868, 1914052035, 2031621326, 1211247597, 1128014560, 1580087799, 1428173050, 32283319, 182621114, 401639597, 486441376, 768917123, 651868046, 1003007129, 818324884, 1503449823, 1385356242, 1333838021, 1150208456, 1973745387, 2125135846, 1673061617,
                1756818940, 2970356327, 3120694122, 2802849917, 2887651696, 2637442643, 2520393566, 2334669897, 2149987652, 3917234703, 3799141122, 4284502037, 4100872472, 3309594171, 3460984630, 3545789473, 3629546796, 2050466060, 1899603969, 1814803222, 1730525723, 1443857720, 1560382517, 1075025698, 1260232239, 575138148, 692707433, 878443390, 1062597235, 243256656, 91341917, 409198410, 325965383, 3403100636, 3252238545, 3704300486, 3620022987, 3874428392, 3990953189, 4042459122, 4227665663, 2460449204, 2578018489, 2226875310, 2411029155, 3198115200, 3046200461,
                2827177882, 2743944855],
            Pa = [0, 218828297, 437656594, 387781147, 875313188, 958871085, 775562294, 590424639, 1750626376, 1699970625, 1917742170, 2135253587, 1551124588, 1367295589, 1180849278, 1265195639, 3501252752, 3720081049, 3399941250, 3350065803, 3835484340, 3919042237, 4270507174, 4085369519, 3102249176, 3051593425, 2734591178, 2952102595, 2361698556, 2177869557, 2530391278, 2614737639, 3145456443, 3060847922, 2708326185, 2892417312, 2404901663, 2187128086, 2504130317, 2555048196, 3542330227, 3727205754, 3375740769, 3292445032, 3876557655,
                3926170974, 4246310725, 4027744588, 1808481195, 1723872674, 1910319033, 2094410160, 1608975247, 1391201670, 1173430173, 1224348052, 59984867, 244860394, 428169201, 344873464, 935293895, 984907214, 766078933, 547512796, 1844882806, 1627235199, 2011214180, 2062270317, 1507497298, 1423022939, 1137477952, 1321699145, 95345982, 145085239, 532201772, 313773861, 830661914, 1015671571, 731183368, 648017665, 3175501286, 2957853679, 2807058932, 2858115069, 2305455554, 2220981195, 2474404304, 2658625497, 3575528878, 3625268135, 3473416636, 3254988725, 3778151818,
                3963161475, 4213447064, 4130281361, 3599595085, 3683022916, 3432737375, 3247465558, 3802222185, 4020912224, 4172763771, 4122762354, 3201631749, 3017672716, 2764249623, 2848461854, 2331590177, 2280796200, 2431590963, 2648976442, 104699613, 188127444, 472615631, 287343814, 840019705, 1058709744, 671593195, 621591778, 1852171925, 1668212892, 1953757831, 2037970062, 1514790577, 1463996600, 1080017571, 1297403050, 3673637356, 3623636965, 3235995134, 3454686199, 4007360968, 3822090177, 4107101658, 4190530515, 2997825956, 3215212461, 2830708150, 2779915199,
                2256734592, 2340947849, 2627016082, 2443058075, 172466556, 122466165, 273792366, 492483431, 1047239E3, 861968209, 612205898, 695634755, 1646252340, 1863638845, 2013908262, 1963115311, 1446242576, 1530455833, 1277555970, 1093597963, 1636604631, 1820824798, 2073724613, 1989249228, 1436590835, 1487645946, 1337376481, 1119727848, 164948639, 81781910, 331544205, 516552836, 1039717051, 821288114, 669961897, 719700128, 2973530695, 3157750862, 2871682645, 2787207260, 2232435299, 2283490410, 2667994737, 2450346104, 3647212047, 3564045318, 3279033885,
                3464042516, 3980931627, 3762502690, 4150144569, 4199882800, 3070356634, 3121275539, 2904027272, 2686254721, 2200818878, 2384911031, 2570832044, 2486224549, 3747192018, 3528626907, 3310321856, 3359936201, 3950355702, 3867060991, 4049844452, 4234721005, 1739656202, 1790575107, 2108100632, 1890328081, 1402811438, 1586903591, 1233856572, 1149249077, 266959938, 48394827, 369057872, 418672217, 1002783846, 919489135, 567498868, 752375421, 209336225, 24197544, 376187827, 459744698, 945164165, 895287692, 574624663, 793451934, 1679968233, 1764313568, 2117360635,
                1933530610, 1343127501, 1560637892, 1243112415, 1192455638, 3704280881, 3519142200, 3336358691, 3419915562, 3907448597, 3857572124, 4075877127, 4294704398, 3029510009, 3113855344, 2927934315, 2744104290, 2159976285, 2377486676, 2594734927, 2544078150],
            Qa = [0, 151849742, 303699484, 454499602, 607398968, 758720310, 908999204, 1059270954, 1214797936, 1097159550, 1517440620, 1400849762, 1817998408, 1699839814, 2118541908, 2001430874, 2429595872, 2581445614, 2194319100, 2345119218, 3034881240, 3186202582, 2801699524, 2951971274, 3635996816, 3518358430,
                3399679628, 3283088770, 4237083816, 4118925222, 4002861748, 3885750714, 1002142683, 850817237, 698445255, 548169417, 529487843, 377642221, 227885567, 77089521, 1943217067, 2061379749, 1640576439, 1757691577, 1474760595, 1592394909, 1174215055, 1290801793, 2875968315, 2724642869, 3111247143, 2960971305, 2405426947, 2253581325, 2638606623, 2487810577, 3808662347, 3926825029, 4044981591, 4162096729, 3342319475, 3459953789, 3576539503, 3693126241, 1986918061, 2137062819, 1685577905, 1836772287, 1381620373, 1532285339, 1078185097, 1229899655, 1040559837,
                923313619, 740276417, 621982671, 439452389, 322734571, 137073913, 19308535, 3871163981, 4021308739, 4104605777, 4255800159, 3263785589, 3414450555, 3499326569, 3651041127, 2933202493, 2815956275, 3167684641, 3049390895, 2330014213, 2213296395, 2566595609, 2448830231, 1305906550, 1155237496, 1607244650, 1455525988, 1776460110, 1626319424, 2079897426, 1928707164, 96392454, 213114376, 396673818, 514443284, 562755902, 679998E3, 865136418, 983426092, 3708173718, 3557504664, 3474729866, 3323011204, 4180808110, 4030667424, 3945269170, 3794078908, 2507040230,
                2623762152, 2272556026, 2390325492, 2975484382, 3092726480, 2738905026, 2857194700, 3973773121, 3856137295, 4274053469, 4157467219, 3371096953, 3252932727, 3673476453, 3556361835, 2763173681, 2915017791, 3064510765, 3215307299, 2156299017, 2307622919, 2459735317, 2610011675, 2081048481, 1963412655, 1846563261, 1729977011, 1480485785, 1362321559, 1243905413, 1126790795, 878845905, 1030690015, 645401037, 796197571, 274084841, 425408743, 38544885, 188821243, 3613494426, 3731654548, 3313212038, 3430322568, 4082475170, 4200115116, 3780097726, 3896688048,
                2668221674, 2516901860, 2366882550, 2216610296, 3141400786, 2989552604, 2837966542, 2687165888, 1202797690, 1320957812, 1437280870, 1554391400, 1669664834, 1787304780, 1906247262, 2022837584, 265905162, 114585348, 499347990, 349075736, 736970802, 585122620, 972512814, 821712160, 2595684844, 2478443234, 2293045232, 2174754046, 3196267988, 3079546586, 2895723464, 2777952454, 3537852828, 3687994002, 3234156416, 3385345166, 4142626212, 4293295786, 3841024952, 3992742070, 174567692, 57326082, 410887952, 292596766, 777231668, 660510266, 1011452712,
                893681702, 1108339068, 1258480242, 1343618912, 1494807662, 1715193156, 1865862730, 1948373848, 2100090966, 2701949495, 2818666809, 3004591147, 3122358053, 2235061775, 2352307457, 2535604243, 2653899549, 3915653703, 3764988233, 4219352155, 4067639125, 3444575871, 3294430577, 3746175075, 3594982253, 836553431, 953270745, 600235211, 718002117, 367585007, 484830689, 133361907, 251657213, 2041877159, 1891211689, 1806599355, 1654886325, 1568718495, 1418573201, 1335535747, 1184342925];
        y.prototype._prepare = function () {
            var b = Da[this.key[a[6]]];
            if (null ==
                b) throw Error(a[50]);
            this[a[51]] = [];
            this._Kd = [];
            for (var c = 0; c <= b; c++) this._Ke.push([0, 0, 0, 0]), this._Kd.push([0, 0, 0, 0]);
            var d = 4 * (b + 1), e = this.key.length / 4, h = T(this.key);
            for (c = 0; c < e; c++) {
                var f = c >> 2;
                this._Ke[f][c % 4] = h[c];
                this._Kd[b - f][c % 4] = h[c]
            }
            var g;
            f = 0;
            for (var k = e; k < d;) {
                if (g = h[e - 1], h[0] = h[0] ^ x[g >> 16 & 255] << 24 ^ x[g >> 8 & 255] << 16 ^ x[255 & g] << 8 ^ x[g >> 24 & 255] ^ Ea[f] << 24, f += 1, 8 != e) c = 1; else {
                    for (c = 1; c < e / 2; c++) h[c] ^= h[c - 1];
                    g = h[e / 2 - 1];
                    h[e / 2] = h[e / 2] ^ x[255 & g] ^ x[g >> 8 & 255] << 8 ^ x[g >> 16 & 255] << 16 ^ x[g >> 24 & 255] << 24;
                    c = e / 2 + 1
                }
                for (; c <
                       e; c++) h[c] ^= h[c - 1];
                for (c = 0; c < e && k < d;) l = k >> 2, m = k % 4, this._Ke[l][m] = h[c], this._Kd[b - l][m] = h[c++], k++
            }
            for (var l = 1; l < b; l++) for (var m = 0; 4 > m; m++) g = this._Kd[l][m], this[a[52]][l][m] = Na[g >> 24 & 255] ^ Oa[g >> 16 & 255] ^ Pa[g >> 8 & 255] ^ Qa[255 & g]
        };
        y[a[34]].encrypt = function (a) {
            if (16 != a.length) throw Error("invalid plaintext size (must be 16 bytes)");
            var b = this._Ke.length - 1, d = [0, 0, 0, 0];
            a = T(a);
            for (var e = 0; 4 > e; e++) a[e] ^= this._Ke[0][e];
            for (var f = 1; f < b; f++) {
                for (e = 0; 4 > e; e++) d[e] = Fa[a[e] >> 24 & 255] ^ Ga[a[(e + 1) % 4] >> 16 & 255] ^ Ha[a[(e +
                    2) % 4] >> 8 & 255] ^ Ia[255 & a[(e + 3) % 4]] ^ this._Ke[f][e];
                a = d.slice(0)
            }
            f = r(16);
            for (e = 0; 4 > e; e++) d = this._Ke[b][e], f[4 * e] = 255 & (x[a[e] >> 24 & 255] ^ d >> 24), f[4 * e + 1] = 255 & (x[a[(e + 1) % 4] >> 16 & 255] ^ d >> 16), f[4 * e + 2] = 255 & (x[a[(e + 2) % 4] >> 8 & 255] ^ d >> 8), f[4 * e + 3] = 255 & (x[255 & a[(e + 3) % 4]] ^ d);
            return f
        };
        y.prototype.decrypt = function (b) {
            if (16 != b.length) throw Error("invalid ciphertext size (must be 16 bytes)");
            var c = this._Kd.length - 1, d = [0, 0, 0, 0];
            b = T(b);
            for (var e = 0; 4 > e; e++) b[e] ^= this._Kd[0][e];
            for (var f = 1; f < c; f++) {
                for (e = 0; 4 > e; e++) d[e] = Ja[b[e] >>
                24 & 255] ^ Ka[b[(e + 3) % 4] >> 16 & 255] ^ La[b[(e + 2) % 4] >> 8 & 255] ^ Ma[255 & b[(e + 1) % 4]] ^ this._Kd[f][e];
                b = d.slice(0)
            }
            f = r(16);
            for (e = 0; 4 > e; e++) d = this[a[52]][c][e], f[4 * e] = 255 & (R[b[e] >> 24 & 255] ^ d >> 24), f[4 * e + 1] = 255 & (R[b[(e + 3) % 4] >> 16 & 255] ^ d >> 16), f[4 * e + 2] = 255 & (R[b[(e + 2) % 4] >> 8 & 255] ^ d >> 8), f[4 * e + 3] = 255 & (R[255 & b[(e + 1) % 4]] ^ d);
            return f
        };
        G[a[34]][a[53]] = function (b) {
            if (0 != b[a[6]] % 16) throw Error("invalid plaintext size (must be multiple of 16 bytes)");
            for (var c = r(b.length), d = r(16), e = 0; e < b.length; e += 16) {
                A(b, d, 0, e, e + 16);
                for (var f = 0; 16 >
                f; f++) d[f] ^= this._lastCipherblock[f];
                this._lastCipherblock = this[a[54]].encrypt(d);
                A(this[a[49]], c, e, 0, 16)
            }
            return c
        };
        G.prototype.decrypt = function (b) {
            if (0 != b.length % 16) throw Error("invalid ciphertext size (must be multiple of 16 bytes)");
            for (var c = r(b.length), d = r(16), e = 0; e < b.length; e += 16) {
                A(b, d, 0, e, e + 16);
                d = this._aes.decrypt(d);
                for (var f = 0; 16 > f; f++) c[e + f] = d[f] ^ this[a[49]][f];
                A(b, this[a[49]], 0, e, e + 16)
            }
            return c
        };
        var ja = {
            pkcs7: {
                pad: function (b) {
                    var c = 16 - (b = r(b)).length % 16, d = r(b[a[6]] + c);
                    A(b, d);
                    for (b = b.length; b <
                    d.length; b++) d[b] = c;
                    return d
                }, strip: function (b) {
                    if (16 > (b = r(b)).length) throw Error("PKCS#7 invalid length");
                    var c = b[b.length - 1];
                    if (16 < c) throw Error(a[55]);
                    for (var d = b.length - c, e = 0; e < c; e++) if (b[d + e] !== c) throw Error("PKCS#7 invalid padding byte");
                    c = r(d);
                    return A(b, c, 0, 0, d), c
                }
            }
        }, z = {convertBytesToString: ua, convertStringToBytes: fa, _slowCreateBuffer: ha};
        window.__aes_encrypt = H;
        window[a[56]] = function (a, c, d) {
            if ("string" != typeof a) return "text must be string.";
            a = (new G(z.convertStringToBytes(c, "utf8"), z.convertStringToBytes(d,
                "utf8"))).decrypt(z.convertStringToBytes(a, "hex"));
            return a = ja.pkcs7.strip(a), z.convertBytesToString(a, "utf8")
        };
        var W = 0, X = 0, Y = 0, N = 0, O = 0, P = 0, Z = 0, aa = 0, ba = 0, K = 0, la = !1, sa;
        Q.prototype.init = function (b) {
            var c = b[a[59]], d = b.xuid;
            S();
            m(J, {106: c, 104: d || "", 101: D});
            (function (b) {
                L.appid = b.appid;
                L.xuid = b.xuid;
                L.key = b[a[47]]
            })(b);
            this.sendAttr();
            f(function () {
                k.navigator.getBattery && k.navigator.getBattery().then(function (b) {
                    b && m(oa, {
                        charging: b.charging,
                        chargingTime: b[a[58]],
                        dischargingTime: b.dischargingTime,
                        level: b.level
                    })
                })
            });
            (function (b) {
                try {
                    (new Promise(function (c, d) {
                        try {
                            var e = function () {
                            }, g = function (b) {
                                if (b = p[a[70]](b)) b = b[1], "0.0.0.0" !== b && (void 0 === m[b] && l.push(b), m[b] = !0)
                            }, h = k.RTCPeerConnection || k[a[71]] || k[a[72]];
                            if (!h) return void c("");
                            var l = [], m = {},
                                n = new h({iceServers: [{urls: a[73]}]}, {optional: [{RtpDataChannels: !0}]}),
                                p = /([0-9]{1,3}(\.[0-9]{1,3}){3}|[a-f0-9]{1,4}(:[a-f0-9]{1,4}){7})/;
                            n.onicecandidate = function (a) {
                                a.candidate && g(a.candidate.candidate)
                            };
                            n.createDataChannel("");
                            f(function () {
                                n.createOffer().then(function (b) {
                                    n[a[74]](b,
                                        e, e)
                                }, e)
                            });
                            f(function () {
                                n[a[75]](function (a) {
                                    n.setLocalDescription(a, e, e)
                                }, e)
                            });
                            var q = 0, r = setInterval(function () {
                                try {
                                    n[a[76]].sdp.split("\n").forEach(function (a) {
                                        0 !== a.indexOf("a=candidate:") && 0 !== a.indexOf("c=IN") || g(a)
                                    }), (0 < l.length || 2 < ++q) && (c(l.join(",")), clearInterval(r))
                                } catch (U) {
                                    c(l[a[43]](",")), clearInterval(r)
                                }
                            }, 1E3)
                        } catch (U) {
                            b("")
                        }
                    })).then(function (a) {
                        b(a)
                    })
                } catch (h) {
                    b(null)
                }
            })(function (a) {
                if (a) {
                    var b = t();
                    a && (na = a);
                    b && (ma = b);
                    ca(!1)
                }
            });
            n(k, "devicemotion", ka);
            n(l, "click", qa);
            setTimeout(function () {
                    ca(!0)
                },
                2E3)
        };
        Q[a[34]].sendAttr = function () {
            var b = this, c = J, d = {data: H(c, "0fc0d47746054969", "636014d173e04409"), key_id: 7},
                e = ra(JSON[a[77]](d));
            w(c);
            k.XDomainRequest ? f(function () {
                var c = new XDomainRequest;
                c.open("post", da + a[78], !0);
                c.withCredentials = !0;
                c.onload = function () {
                    b.handleResponse(c[a[79]])
                };
                setTimeout(function () {
                    k.signSendStartTime && k.signSendStartTime();
                    c.send(e)
                }, 0)
            }) : f(function () {
                var c = new XMLHttpRequest;
                c.open("POST", da + "/abdr", !0);
                c.withCredentials = !0;
                c.onreadystatechange = function (d) {
                    4 === c.readyState &&
                    200 === c.status && b.handleResponse(c[a[79]])
                };
                k.signSendStartTime && k[a[80]]();
                c[a[81]](e)
            })
        };
        Q.prototype.handleResponse = function (a) {
            try {
                f(k[Ca], [a])
            } catch (e) {
                var b = a, d = null;
                -1 !== a.indexOf("|") && (d = a.split("|"), b = d[0], d = d[1]);
                f(k.__abbaidu_20180306_idcb, [b]);
                f(k.__abbaidu_20180315_scorecb, [d]);
                f(k.__abbaidu_20180315_lid_score_cb, [b, d]);
                f(k.__abbaidu_20190124_cb, [a])
            }
        };
        n(k, "load", function () {
            ea()
        });
        var Ra = l.onreadystatechange;
        l.onreadystatechange = function () {
            f(ea);
            f(Ra)
        };
        f(ea)
    }()
})();
