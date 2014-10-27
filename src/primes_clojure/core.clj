(ns primes-clojure.core)

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

(def known-primes (ref [2]))

(defn prime-divisors
  "Returns the collection of numbers needed to test if n is a prime number"
  [n]
  (let [max-factor (int (java.lang.Math/sqrt n))]
    (take-while (partial >= max-factor) @known-primes)))

(defn prime?
  "Returns true if the n is prime, false otherwise"
  [n]
  (not-any? #(zero? (mod n %1)) (prime-divisors n)))

(defn find-first
  "Returns the first element in the collection matching the predicate"
  [p coll]
  (first (filter p coll)))

(defn next-odd
  "Returns the first odd number greater than n"
  [n]
  (if (odd? n)
    (+ 2 n)
    (+ 1 n))
  )

(defn next-prime
  "Returns the first prime number greater than n"
  [n]
  (if (< n (last @known-primes))
    (find-first (partial < n) @known-primes)
    (let [prime (find-first prime? (iterate (partial + 2) (next-odd n)))]
      (dosync (alter known-primes #(conj %1 %2) prime))
      (identity prime))))

(defn primes
  "A sequence of prime numbers"
  []
  (iterate next-prime 2))

(defn format-number
  "Converts a number to a string with the space padded width"
  [width number]
  (format (str "%" width "d") number))

(defn table-header
  ""
  [table cell-width]
  (str
    (apply str (repeat cell-width " "))
    " "
    (clojure.string/join
      " "
      (map
        (partial format-number cell-width)
        (.values table)))))

(defn table-row
  ""
  [table row cell-width]
  (str
    (format-number cell-width (nth (.values table) row))
    " "
    (clojure.string/join " "
      (map
        (partial format-number cell-width)
           (for [column (range 0 (.num-columns table))]
            (.cell table row column))))))

; TODO: Iterate over all values in matrix?
(defn max-cell-width
  ""
  [table]
  (count
    (str
      (.cell table
             (dec (.num-rows table))
             (dec (.num-columns table))))))

(defn format-table
  "Converts a matrix to a formatted string"
  [table]
  (let [cell-width (max-cell-width table)]
    (clojure.string/join "\n"
      (cons
        (table-header table cell-width)
        (for
          [i (range (.num-rows table))]
          (table-row table i cell-width))))))

(defn -main
  "Main entry point"
  [& args]
  (let [table (MultiplicationTable. (take 1000 (primes)))]
    (println (format-table table))))

