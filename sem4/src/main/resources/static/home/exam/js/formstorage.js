!(function (e) {
  "object" == typeof exports && "undefined" != typeof module
    ? (module.exports = e())
    : "function" == typeof define && define.amd
    ? define([], e)
    : (("undefined" != typeof window
        ? window
        : "undefined" != typeof global
        ? global
        : "undefined" != typeof self
        ? self
        : this
      ).FormStorage = e());
})(function () {
  return (function n(o, i, c) {
    function a(t, e) {
      if (!i[t]) {
        if (!o[t]) {
          var r = "function" == typeof require && require;
          if (!e && r) return r(t, !0);
          if (u) return u(t, !0);
          throw (
            (((r = new Error("Cannot find module '" + t + "'")).code =
              "MODULE_NOT_FOUND"),
            r)
          );
        }
        (r = i[t] = { exports: {} }),
          o[t][0].call(
            r.exports,
            function (e) {
              return a(o[t][1][e] || e);
            },
            r,
            r.exports,
            n,
            o,
            i,
            c
          );
      }
      return i[t].exports;
    }
    for (
      var u = "function" == typeof require && require, e = 0;
      e < c.length;
      e++
    )
      a(c[e]);
    return a;
  })(
    {
      1: [
        function (e, t, r) {
          "use strict";
          var n = "%[a-f0-9]{2}",
            o = new RegExp(n, "gi"),
            a = new RegExp("(" + n + ")+", "gi");
          function u(t) {
            try {
              return decodeURIComponent(t);
            } catch (e) {
              for (var r = t.match(o), n = 1; n < r.length; n++)
                r = (t = (function e(t, r) {
                  try {
                    return decodeURIComponent(t.join(""));
                  } catch (e) {}
                  if (1 === t.length) return t;
                  r = r || 1;
                  var n = t.slice(0, r),
                    r = t.slice(r);
                  return Array.prototype.concat.call([], e(n), e(r));
                })(r, n).join("")).match(o);
              return t;
            }
          }
          t.exports = function (t) {
            if ("string" != typeof t)
              throw new TypeError(
                "Expected `encodedURI` to be of type `string`, got `" +
                  typeof t +
                  "`"
              );
            try {
              return (t = t.replace(/\+/g, " ")), decodeURIComponent(t);
            } catch (e) {
              return (function (e) {
                for (
                  var t = { "%FE%FF": "��", "%FF%FE": "��" }, r = a.exec(e);
                  r;

                ) {
                  try {
                    t[r[0]] = decodeURIComponent(r[0]);
                  } catch (e) {
                    var n = u(r[0]);
                    n !== r[0] && (t[r[0]] = n);
                  }
                  r = a.exec(e);
                }
                t["%C2"] = "�";
                for (var o = Object.keys(t), i = 0; i < o.length; i++) {
                  var c = o[i];
                  e = e.replace(new RegExp(c, "g"), t[c]);
                }
                return e;
              })(t);
            }
          };
        },
        {},
      ],
      2: [
        function (e, t, r) {
          Element.prototype.matches ||
            (Element.prototype.matches =
              Element.prototype.matchesSelector ||
              Element.prototype.mozMatchesSelector ||
              Element.prototype.msMatchesSelector ||
              Element.prototype.oMatchesSelector ||
              Element.prototype.webkitMatchesSelector ||
              function (e) {
                for (
                  var t = (
                      this.document || this.ownerDocument
                    ).querySelectorAll(e),
                    r = t.length;
                  0 <= --r && t.item(r) !== this;

                );
                return -1 < r;
              });
        },
        {},
      ],
      3: [
        function (e, t, r) {
          var h = /^(?:submit|button|image|reset|file)$/i,
            m = /^(?:input|select|textarea|keygen)/i,
            o = /(\[[^\[\]]*\])/g;
          function v(e, t, r) {
            var n;
            return (
              t.match(o)
                ? (function e(t, r, n) {
                    if (0 === r.length) return (t = n);
                    var o,
                      i = r.shift(),
                      c = i.match(/^\[(.+?)\]$/);
                    return (
                      "[]" === i
                        ? ((t = t || []),
                          Array.isArray(t)
                            ? t.push(e(null, r, n))
                            : ((t._values = t._values || []),
                              t._values.push(e(null, r, n))))
                        : c
                        ? ((c = +(o = c[1])),
                          isNaN(c)
                            ? ((t = t || {})[o] = e(t[o], r, n))
                            : ((t = t || [])[c] = e(t[c], r, n)))
                        : (t[i] = e(t[i], r, n)),
                      t
                    );
                  })(
                    e,
                    (function (e) {
                      var t = [],
                        r = new RegExp(o),
                        n = /^([^\[\]]*)/.exec(e);
                      for (n[1] && t.push(n[1]); null !== (n = r.exec(e)); )
                        t.push(n[1]);
                      return t;
                    })(t),
                    r
                  )
                : (n = e[t])
                ? (Array.isArray(n) || (e[t] = [n]), e[t].push(r))
                : (e[t] = r),
              e
            );
          }
          function b(e, t, r) {
            return (
              (r = r.replace(/(\r)?\n/g, "\r\n")),
              (r = (r = encodeURIComponent(r)).replace(/%20/g, "+")),
              e + (e ? "&" : "") + encodeURIComponent(t) + "=" + r
            );
          }
          t.exports = function (e, t) {
            "object" != typeof t
              ? (t = { hash: !!t })
              : void 0 === t.hash && (t.hash = !0);
            for (
              var r = t.hash ? {} : "",
                n = t.serializer || (t.hash ? v : b),
                o = e && e.elements ? e.elements : [],
                i = Object.create(null),
                c = 0;
              c < o.length;
              ++c
            ) {
              var a = o[c];
              if (
                (t.disabled || !a.disabled) &&
                a.name &&
                m.test(a.nodeName) &&
                !h.test(a.type)
              ) {
                var u = a.name,
                  l = a.value;
                if (
                  (("checkbox" !== a.type && "radio" !== a.type) ||
                    a.checked ||
                    (l = void 0),
                  t.empty)
                ) {
                  if (
                    ("checkbox" !== a.type || a.checked || (l = ""),
                    "radio" === a.type &&
                      (i[a.name] || a.checked
                        ? a.checked && (i[a.name] = !0)
                        : (i[a.name] = !1)),
                    null == l && "radio" == a.type)
                  )
                    continue;
                } else if (!l) continue;
                if ("select-multiple" !== a.type) r = n(r, u, l);
                else {
                  l = [];
                  for (var f = a.options, s = !1, p = 0; p < f.length; ++p) {
                    var y = f[p],
                      d = t.empty && !y.value,
                      d = y.value || d;
                    y.selected &&
                      d &&
                      ((s = !0),
                      (r =
                        t.hash && "[]" !== u.slice(u.length - 2)
                          ? n(r, u + "[]", y.value)
                          : n(r, u, y.value)));
                  }
                  !s && t.empty && (r = n(r, u, ""));
                }
              }
            }
            if (t.empty) for (var u in i) i[u] || (r = n(r, u, ""));
            return r;
          };
        },
        {},
      ],
      4: [
        function (e, t, r) {
          "use strict";
          var f = function (e, t) {
              if (Array.isArray(e)) return e;
              if (Symbol.iterator in Object(e))
                return (function (e, t) {
                  var r = [],
                    n = !0,
                    o = !1,
                    i = void 0;
                  try {
                    for (
                      var c, a = e[Symbol.iterator]();
                      !(n = (c = a.next()).done) &&
                      (r.push(c.value), !t || r.length !== t);
                      n = !0
                    );
                  } catch (e) {
                    (o = !0), (i = e);
                  } finally {
                    try {
                      !n && a.return && a.return();
                    } finally {
                      if (o) throw i;
                    }
                  }
                  return r;
                })(e, t);
              throw new TypeError(
                "Invalid attempt to destructure non-iterable instance"
              );
            },
            s =
              "function" == typeof Symbol && "symbol" == typeof Symbol.iterator
                ? function (e) {
                    return typeof e;
                  }
                : function (e) {
                    return e &&
                      "function" == typeof Symbol &&
                      e.constructor === Symbol &&
                      e !== Symbol.prototype
                      ? "symbol"
                      : typeof e;
                  },
            n = function (e) {
              return n(e).replace(/[!'()*]/g, function (e) {
                return "%" + e.charCodeAt(0).toString(16).toUpperCase();
              });
            },
            o = e("decode-uri-component");
          function p(e, t) {
            return t.encode ? (t.strict ? strictUriEncode : n)(e) : e;
          }
          function y(e, t) {
            return t.decode ? o(e) : e;
          }
          function i(e) {
            var t = e.indexOf("?");
            return -1 === t ? "" : e.slice(t + 1);
          }
          function c(e, t) {
            var r = (function (e) {
                var n = void 0;
                switch (e.arrayFormat) {
                  case "index":
                    return function (e, t, r) {
                      (n = /\[(\d*)\]$/.exec(e)),
                        (e = e.replace(/\[\d*\]$/, "")),
                        n
                          ? (void 0 === r[e] && (r[e] = {}), (r[e][n[1]] = t))
                          : (r[e] = t);
                    };
                  case "bracket":
                    return function (e, t, r) {
                      (n = /(\[\])$/.exec(e)),
                        (e = e.replace(/\[\]$/, "")),
                        n
                          ? void 0 !== r[e]
                            ? (r[e] = [].concat(r[e], t))
                            : (r[e] = [t])
                          : (r[e] = t);
                    };
                  default:
                    return function (e, t, r) {
                      void 0 !== r[e]
                        ? (r[e] = [].concat(r[e], t))
                        : (r[e] = t);
                    };
                }
              })((t = Object.assign({ decode: !0, arrayFormat: "none" }, t))),
              n = Object.create(null);
            if ("string" != typeof e) return n;
            if (!(e = e.trim().replace(/^[?#&]/, ""))) return n;
            var o = !0,
              i = !1,
              c = void 0;
            try {
              for (
                var a = e.split("&")[Symbol.iterator]();
                !(o = (l = a.next()).done);
                o = !0
              ) {
                var u = l.value.replace(/\+/g, " ").split("="),
                  l = f(u, 2),
                  u = l[0],
                  l = void 0 === (l = l[1]) ? null : y(l, t);
                r(y(u, t), l, n);
              }
            } catch (e) {
              (i = !0), (c = e);
            } finally {
              try {
                !o && a.return && a.return();
              } finally {
                if (i) throw c;
              }
            }
            return Object.keys(n)
              .sort()
              .reduce(function (e, t) {
                var r = n[t];
                return (
                  Boolean(r) &&
                  "object" === (void 0 === r ? "undefined" : s(r)) &&
                  !Array.isArray(r)
                    ? (e[t] = (function e(t) {
                        return Array.isArray(t)
                          ? t.sort()
                          : "object" === (void 0 === t ? "undefined" : s(t))
                          ? e(Object.keys(t))
                              .sort(function (e, t) {
                                return Number(e) - Number(t);
                              })
                              .map(function (e) {
                                return t[e];
                              })
                          : t;
                      })(r))
                    : (e[t] = r),
                  e
                );
              }, Object.create(null));
          }
          (r.extract = i),
            (r.parse = c),
            (r.stringify = function (u, l) {
              !1 ===
                (l = Object.assign(
                  { encode: !0, strict: !0, arrayFormat: "none" },
                  l
                )).sort && (l.sort = function () {});
              var f = (function (n) {
                switch (n.arrayFormat) {
                  case "index":
                    return function (e, t, r) {
                      return (
                        null === t
                          ? [p(e, n), "[", r, "]"]
                          : [p(e, n), "[", p(r, n), "]=", p(t, n)]
                      ).join("");
                    };
                  case "bracket":
                    return function (e, t) {
                      return (
                        null === t ? [p(e, n), "[]"] : [p(e, n), "[]=", p(t, n)]
                      ).join("");
                    };
                  default:
                    return function (e, t) {
                      return null === t
                        ? p(e, n)
                        : [p(e, n), "=", p(t, n)].join("");
                    };
                }
              })(l);
              return u
                ? Object.keys(u)
                    .sort(l.sort)
                    .map(function (e) {
                      var t = u[e];
                      if (void 0 === t) return "";
                      if (null === t) return p(e, l);
                      if (Array.isArray(t)) {
                        var r = [],
                          n = !0,
                          o = !1,
                          i = void 0;
                        try {
                          for (
                            var c = t.slice()[Symbol.iterator]();
                            !(n = (a = c.next()).done);
                            n = !0
                          ) {
                            var a = a.value;
                            void 0 !== a && r.push(f(e, a, r.length));
                          }
                        } catch (e) {
                          (o = !0), (i = e);
                        } finally {
                          try {
                            !n && c.return && c.return();
                          } finally {
                            if (o) throw i;
                          }
                        }
                        return r.join("&");
                      }
                      return p(e, l) + "=" + p(t, l);
                    })
                    .filter(function (e) {
                      return 0 < e.length;
                    })
                    .join("&")
                : "";
            }),
            (r.parseUrl = function (e, t) {
              return { url: e.split("?")[0] || "", query: c(i(e), t) };
            });
        },
        { "decode-uri-component": 1 },
      ],
      5: [
        function (e, t, r) {
          "use strict";
          Object.defineProperty(r, "__esModule", { value: !0 });
          var n = function (e, t, r) {
            return t && o(e.prototype, t), r && o(e, r), e;
          };
          function o(e, t) {
            for (var r = 0; r < t.length; r++) {
              var n = t[r];
              (n.enumerable = n.enumerable || !1),
                (n.configurable = !0),
                "value" in n && (n.writable = !0),
                Object.defineProperty(e, n.key, n);
            }
          }
          e("element-matches-polyfill");
          var i = c(e("form-serialize")),
            l = c(e("query-string-es5"));
          function c(e) {
            return e && e.__esModule ? e : { default: e };
          }
          var a = { name: "form", ignores: [], includes: [], checkbox: null },
            n =
              (n(u, [
                {
                  key: "save",
                  value: function () {
                    var e = (0, i.default)(this.ele);
                    window.localStorage.setItem(this.opt.name, e);
                  },
                },
                {
                  key: "clear",
                  value: function () {
                    window.localStorage.removeItem(this.opt.name);
                  },
                },
                {
                  key: "setCheckbox",
                  value: function () {
                    var e = this;
                    this.ele.addEventListener("submit", function () {
                      e.checkbox.checked ? e.save() : e.clear();
                    });
                  },
                },
                {
                  key: "getState",
                  value: function () {
                    return (0, i.default)(this.ele);
                  },
                },
                {
                  key: "applyState",
                  value: function (e) {
                    var t,
                      i = this,
                      r = this.opt,
                      c = r.ignores,
                      a = r.includes,
                      u = l.default.parse(e.replace(/^"(.*)"$/, "$1"));
                    for (t in u)
                      (function (e) {
                        var t = !1,
                          r = i.ele.querySelector('[name="' + e + '"]'),
                          n = i.ele.querySelectorAll('[name="' + e + '"]');
                        if (!r) return;
                        if (
                          (c.forEach(function (e) {
                            if (r.matches(e)) return !(t = !0);
                          }),
                          t)
                        )
                          return;
                        if (
                          0 < a.length &&
                          ((t = !0),
                          a.forEach(function (e) {
                            if (r.matches(e)) return (t = !1);
                          }),
                          t)
                        )
                          return;
                        if (n && 1 < n.length) {
                          var o = u[e];
                          return [].forEach.call(n, function (t, e) {
                            "checkbox" === t.type
                              ? o.forEach
                                ? o.forEach(function (e) {
                                    e === t.value && (t.checked = !0);
                                  })
                                : o === t.value && (t.checked = !0)
                              : "radio" === t.type &&
                                t.value === o &&
                                (t.checked = !0);
                          });
                        }
                        "radio" === r.type || "checkbox" === r.type
                          ? u[e] === r.value && (r.checked = !0)
                          : (r.value = u[e]);
                      })(t);
                  },
                },
                {
                  key: "apply",
                  value: function () {
                    var e = window.localStorage.getItem(this.opt.name);
                    e && this.applyState(e);
                  },
                },
              ]),
              u);
          function u(e, t) {
            !(function (e) {
              if (!(e instanceof u))
                throw new TypeError("Cannot call a class as a function");
            })(this),
              (this.ele = document.querySelector(e)),
              (this.opt = Object.assign({}, a, t)),
              this.opt.checkbox &&
                ((this.checkbox = document.querySelector(this.opt.checkbox)),
                this.setCheckbox(),
                this.apply());
          }
          (r.default = n), (t.exports = r.default);
        },
        {
          "element-matches-polyfill": 2,
          "form-serialize": 3,
          "query-string-es5": 4,
        },
      ],
    },
    {},
    [5]
  )(5);
});
