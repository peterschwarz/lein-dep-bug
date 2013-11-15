# lein-dep-bug

A demonstration of a dependency bug in Leiningen - issue [#1370](https://github.com/technomancy/leiningen/issues/1370)

With a certain depth of dependencies, if there is an a conflicting library version, it does not show up in the dependency listing 
via `lein deps :tree`.  This makes tracking down the offending library very difficult.  One needs to view the project dependencies manually of each project by examining their project.clj files (thankfully they're opensource).

In this particular instance, the bug is caused by an dependency of clj-http (`[org.clojure/tools.reader "0.7.7"]`) and clojurescript 0.0-2030 (`[org.clojure/tools.reader "0.7.10"]`).  Running `lein cljsbuild once` will fail with the following 

    Exception in thread "main" java.lang.RuntimeException: Unable to resolve var: reader/*alias-map* in this context, compiling:(cljs/analyzer.clj:1219:9)
    at clojure.lang.Compiler.analyzeSeq(Compiler.java:6567)
    at clojure.lang.Compiler.analyze(Compiler.java:6361)
    ...


 Only the following dependencies are displayed:

    > lein deps :tree
     [clj-http "0.7.7"]
       [cheshire "5.2.0"]
         [com.fasterxml.jackson.core/jackson-core "2.2.1"]
         [com.fasterxml.jackson.dataformat/jackson-dataformat-smile "2.2.1"]
         [tigris "0.1.1"]
       [commons-codec "1.8"]
       [commons-io "2.4"]
       [crouton "0.1.1" :exclusions [[org.clojure/clojure]]]
         [org.jsoup/jsoup "1.7.1"]
       [org.apache.httpcomponents/httpclient "4.3"]
         [commons-logging "1.1.3"]
       [org.apache.httpcomponents/httpcore "4.3"]
       [org.apache.httpcomponents/httpmime "4.3"]
       [slingshot "0.10.3" :exclusions [[org.clojure/clojure]]]
     [clojure-complete "0.2.3" :exclusions [[org.clojure/clojure]]]
     [org.clojure/clojure "1.5.1"]  
     [org.clojure/clojurescript "0.0-2030" :exclusions [[org.apache.ant/ant]]]
       [com.google.javascript/closure-compiler "v20130603"]
         [args4j "2.0.16"]
         [com.google.code.findbugs/jsr305 "1.3.9"]
         [com.google.guava/guava "14.0.1"]
         [com.google.protobuf/protobuf-java "2.4.1"]
         [org.json/json "20090211"]
       [org.clojure/data.json "0.2.3"]
       [org.clojure/google-closure-library "0.0-20130212-95c19e7f0f5f"]
         [org.clojure/google-closure-library-third-party "0.0-20130212-95c19e7f0f5f"]
       [org.clojure/tools.reader "0.7.10"]  ; <-- Only listing of tools.reader
       [org.mozilla/rhino "1.7R4"]
     [org.clojure/tools.nrepl "0.2.3" :exclusions [[org.clojure/clojure]]]


If a correction is made to either exclude tools.reader (commented out in the project file) from clj-http or reordering the 
clj-http dependency before clojurescript, it will work.
                    