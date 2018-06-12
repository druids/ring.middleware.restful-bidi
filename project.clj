(defproject ring.middleware.restful-bidi "0.0.0"
  :description "A simple Ring middleware that generates RESTful links for resources"
  :url "https://github.com/druids/ring-middleware-case-format"
  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"}

  :dependencies [[bidi "2.1.3"]]

  :profiles {:dev {:plugins [[lein-cloverage "1.0.10"]
                             [lein-kibit "0.1.6"]
                             [jonase/eastwood "0.2.6"]]
                   :dependencies [[org.clojure/clojure "1.9.0"]
                                  [ring/ring-core "1.6.3"]]}})
