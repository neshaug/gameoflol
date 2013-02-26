(ns gameoflol.routes
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]))

(defroutes app-routes
  (route/resources "/"))

(def handler
  (handler/site app-routes))
