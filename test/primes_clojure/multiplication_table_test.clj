(ns primes-clojure.multiplication-table-test
  (:require [clojure.test :refer :all]
            [primes-clojure.multiplication-table :refer :all]))

(deftest test-cell
  (def basic-table (new-table '(1 2 3)))
  (testing "0, 0 is first cell"
    (is (= 1 (.cell basic-table 0 0))))
  (testing "max column"
    (is (= 3 (.cell basic-table 0 2))))
  (testing "max row"
    (is (= 3 (.cell basic-table 2 0))))
  (testing "max row and column"
    (is (= 9 (.cell basic-table 2 2)))))
