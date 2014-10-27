(ns primes-clojure.table-formatter)

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
