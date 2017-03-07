(ns plain-counter.main
  (:gen-class)
  (:require
    [compojure.core :as compojure :refer [GET POST]]
    [compojure.route :as route]
    [com.stuartsierra.component :as component]
    [org.httpkit.server :as server]))

;;;;
;; STORE
;;;;

(def store (atom 0))

;;;;
;; ROUTES
;;;;

(defn routes []
  (compojure/routes
    (GET "/" [] (str "Store value: " @store))
    (POST "/inc" [] (fn [req] (let [new-store-val (swap! store inc)]
                                (str "New store value: " new-store-val))))
    (route/not-found "Not found. Have you tried a GET to / ?")
    ))

(defrecord Routes []
  component/Lifecycle
  (start [this]
    (if (:routes this)
     this
     (do
       (println ";: Starting routes")
       (assoc this :routes (routes)))))

  (stop [this]
    (if (:routes this)
      (do
        (println ";; Stopping routes")
        (dissoc this :routes))
      this)))

(defn make-routes []
  (map->Routes {}))

;;;;
;; SERVER
;;;;

(defrecord Server [routes]
  component/Lifecycle
  (start [this]
    (if (:server this)
      this
      (do
        (println ";; Starting server")
        (assoc this :server (server/run-server (:routes routes) {:port 3001})))))

  (stop [this]
    (if-let [instance (:server this)]
      (do
        (println ";; Stopping server")
        (instance) ;; This stops the http-kit server; differs from Jetty etc.
        (dissoc this :server))
      this)))

(defn make-server
  []
  (map->Server {}))

;;;;
;; SYSTEM
;;;;

(defn system
  []
  (component/system-map
    :routes (make-routes)
    :server (component/using
              (make-server)
              [:routes])))

;;;;
;; ENTRYPOINT
;;;;

(defn -main [& args]
  (component/start (system)))

