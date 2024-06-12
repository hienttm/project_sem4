(function () {
  var el = document.getElementById("ckeditor-init-script");
  if (el && !window.CKEDITOR_BASEPATH) {
    window.CKEDITOR_BASEPATH = el.getAttribute("data-ckeditor-basepath");
  }
  function runInitialisers() {
    if (!window.CKEDITOR) {
      setTimeout(runInitialisers, 100);
      return;
    }
    initialiseCKEditor();
    initialiseCKEditorInInlinedForms();
  }
  if (document.readyState != "loading" && document.body) {
    document.addEventListener("DOMContentLoaded", initialiseCKEditor);
    runInitialisers();
  } else {
    document.addEventListener("DOMContentLoaded", runInitialisers);
  }
  var filterStyles = {
    attributes: {
      style: function (value, element) {
        value = CKEDITOR.tools.parseCssText(value, 1);
        if (value.color == "#000000") return false;
      },
    },
  };
  function initialiseCKEditor() {
    var textareas = Array.prototype.slice.call(
      document.querySelectorAll("textarea[data-type=ckeditortype]")
    );
    for (var i = 0; i < textareas.length; ++i) {
      var t = textareas[i];
      if (
        t.getAttribute("data-processed") == "0" &&
        t.id.indexOf("__prefix__") == -1
      ) {
        t.setAttribute("data-processed", "1");
        var ext = JSON.parse(t.getAttribute("data-external-plugin-resources"));
        for (var j = 0; j < ext.length; ++j) {
          CKEDITOR.plugins.addExternal(ext[j][0], ext[j][1], ext[j][2]);
        }
        var conf = JSON.parse(t.getAttribute("data-config"));
        conf.on = {
          instanceReady: function (evt) {
            evt.editor.pasteFilter = evt.editor.activeFilter.clone();
            evt.editor.pasteFilter.disallow(
              "span{font-weight,font-size,font-family,text-decoration}"
            );
          },
          paste: function (evt) {
            var pasteFilter = evt.editor.pasteFilter;
            var fragment = CKEDITOR.htmlParser.fragment.fromHtml(
                evt.data.dataValue
              ),
              writer = new CKEDITOR.htmlParser.basicWriter();
            pasteFilter.applyTo(fragment);
            fragment.writeHtml(writer);
            evt.data.dataValue = writer.getHtml();
            this.dataProcessor.dataFilter.addRules(filterStyles);
            this.dataProcessor.htmlFilter.addRules(filterStyles);
          },
        };
        CKEDITOR.replace(t.id, conf);
      }
    }
  }
  function initialiseCKEditorInInlinedForms() {
    if (typeof django === "object" && django.jQuery) {
      django.jQuery(document).on("formset:added", initialiseCKEditor);
    }
  }
  window.initialiseCKEditor = initialiseCKEditor;
  window.initialiseCKEditorInInlinedForms = initialiseCKEditorInInlinedForms;
})();
