(ns user
  (:require
    [plain-counter.main :as main]

    [clojure.repl :refer :all]
    [clojure.tools.namespace.repl :refer [refresh-all]]
    [com.stuartsierra.component :as component]))

(def app nil)

(defn go []
  (alter-var-root #'app (constantly (main/system)))
  (alter-var-root #'app component/start))

(defn stop []
  (alter-var-root #'app (fn [s]
                          (when s (component/stop s)))))

(defn restart []
  (stop)
  (refresh-all :after 'user/go))

