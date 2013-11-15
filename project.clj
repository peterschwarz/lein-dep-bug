(defproject dep-bug "0.1.0-SNAPSHOT"
  :description "Demo of a lein dependency bug"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]

                 [org.clojure/clojurescript "0.0-2030"
                    :exclusions [org.apache.ant/ant]]

                 [clj-http "0.7.7"] ; doesn't work here; does work if before the clojurescript dep
                 ; [clj-http "0.7.7" :exclusions [org.clojure/tools.reader]] ; works

                    ]

  :plugins [[lein-cljsbuild "1.0.0-alpha2"]]
  :cljsbuild { :builds
                [{:source-paths ["src-cljs"],
                  :compiler {:pretty-print true,
                             :output-to "resources/public/js/application.js",
                             :optimizations :whitespace}}]})