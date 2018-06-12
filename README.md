ring.middleware.restful-bidi
============================

A simple Ring's middleware that generates RESTful links for resources from [Bidi](https://github.com/juxt/bidi) routes.

[![Dependencies Status](https://jarkeeper.com/druids/ring.middleware.restful-bidi/status.png)](https://jarkeeper.com/druids/ring.middleware.restful-bidi)
[![License](https://img.shields.io/badge/MIT-Clause-blue.svg)](https://opensource.org/licenses/MIT)


Leiningen/Boot
--------------

```clojure
[ring.middleware.restful-bidi "0.2.0"]
```

Documentation
-------------

```clojure
(require '[ring.middleware.restful-bidi :refer [wrap-bidi-restful]])


(def restful-model
  {:api/entry-point #{:api/users}})


(def routes
  ["" {"/" {:get :api/entry-point}
       "/users" {:get :api/users}}])


(wrap-bidi-restful
    app-handler
    routes
    "http://localhost:3000"
    restful-model)
```

A response from `:api/entry-point` `"/"` resource will look like:

```json
{
  "_links": {
    "self": {
      "href": "http://localhost:3000/",
      "type": "GET"
    },
    "users": {
      "href": "http://localhost:3000/users",
      "type": "GET"
    }
}
```

A response from `:api/users` `"/users"` resource will look like:

```json
{
  "data": [{"username": "jdoe"}],
  "_links": {
    "self": {
      "href": "http://localhost:3000/users",
      "type": "GET"
    }
}
```
