(ns primes-clojure.primes-test
  (:require [clojure.test :refer :all]
            [primes-clojure.primes :refer :all]))

(deftest test-primes
  (testing "First prime"
    (is (= 2 (first (primes)))))
  (testing "Result is a sequence"
    (is (seq? (primes))))
  (testing "Has first 5 primes"
    (is (= '(2 3 5 7 11) (take 5 (primes))))))
