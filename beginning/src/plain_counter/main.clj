(ns plain-counter.main
  (:gen-class)
  (:require
    [compojure.core :as compojure :refer [GET POST]]
    [compojure.route :as route]
    [org.httpkit.server :as server]))

;;;;
;; STORE
;;;;

(def store (atom 0))

;;;;
;; ROUTES
;;;;

(compojure/defroutes handler
  (GET "/" [] (str "Store value: " @store))
  (POST "/inc" [] (fn [req] (let [new-store-val (swap! store inc)]
                              (str "New store value: " new-store-val))))
  (route/not-found "Not found. Have you tried a GET to / ?")
  )

;;;;
;; SERVER
;;;;

(defn run-server
  []
  (server/run-server handler {:port 3001}))

;;;;
;; ENTRYPOINT
;;;;

(defn -main [& args]
  (run-server))

