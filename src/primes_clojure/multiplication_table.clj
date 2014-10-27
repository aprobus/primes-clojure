(ns primes-clojure.multiplication-table
  (:gen-class))

(defprotocol Matrix
  (cell [this row column])
  (num-rows [this])
  (num-columns [this]))

(deftype MultiplicationTable [values]
  Matrix
  (cell [this row column]
    (* (nth (.values this) row) (nth (.values this) column)))
  (num-rows [this]
    (count (.values this)))
  (num-columns [this]
    (count (.values this))))

(defn new-table [values]
  (MultiplicationTable. values))
