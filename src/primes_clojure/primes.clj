(ns primes-clojure.primes)

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
