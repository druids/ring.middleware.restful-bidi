(ns ring.middleware.restful-bidi
  (:require
    [clojure.string :refer [upper-case]]
    [bidi.bidi :as bidi]))


(defn- compose-route
  [routes host response route]
  (let [params (->> route
                    :path
                    flatten
                    (filter keyword?)
                    drop-last)]
    (merge
      {:href (if (empty? params)
               (str host (bidi/path-for routes (:handler route)))
               (str host
                    (apply bidi/path-for
                           (concat [routes (:handler route)]
                                   (->> params
                                        (map #(vector % (get-in response [:body %] (str "{" (name %) "}"))))
                                        flatten)))))
       :type (->> route :path last name upper-case)}
      (when-not (empty? params)
        {:templated true}))))


(defn- wrap-with-links
  [routes host model {:keys [uri request-method bidi-route]} response]
  (let [related-resources (get model bidi-route)
        links (merge (->> routes
                          bidi/route-seq
                          (filter #(contains? related-resources (:handler %)))
                          (reduce (fn [acc route]
                                    (assoc acc
                                           (-> route :handler name keyword)
                                           (compose-route routes host response route)))
                                  {}))
                     {:self {:href (if (= :post request-method)
                                     (str host uri "/" (-> response :body :id))
                                     (str host uri))
                             :type "GET"}})]
    (update response :body assoc :_links links)))


(defn wrap-bidi-restful
  ([handler routes host model]
   (wrap-bidi-restful handler routes host model "application/hal+json"))
  ([handler routes host model content-type]
   (fn [request]
     (let [response (handler request)]
       (if (contains? #{200 201} (:status response))
         (wrap-with-links routes host model request (assoc-in response [:headers "Content-Type"] content-type))
         response)))))
