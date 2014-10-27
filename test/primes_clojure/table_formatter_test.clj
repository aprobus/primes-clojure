(ns primes-clojure.table-formatter-test
  (:require [clojure.test :refer :all]
            [primes-clojure.multiplication-table :refer :all]
            [primes-clojure.table-formatter :refer :all]))

(deftest test-format-table
  (def basic-table (new-table '(1 2 3)))
  (testing "Formats the table"
    (is (= "  1 2 3\n1 1 2 3\n2 2 4 6\n3 3 6 9" (format-table basic-table)))))

