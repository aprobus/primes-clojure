(ns primes-clojure.core
  (:require [primes-clojure.multiplication-table :refer (new-table)]
            [primes-clojure.primes :refer (primes)]
            [primes-clojure.table-formatter :refer (format-table)]))

(defn table-size [args]
  (if-not (empty? args)
    (java.lang.Integer/parseInt (first args) 10)
    10))

(defn -main
  "Main entry point"
  [& args]
  (let [mult-table (new-table (take (table-size args) (primes)))]
    (println (format-table mult-table))))

