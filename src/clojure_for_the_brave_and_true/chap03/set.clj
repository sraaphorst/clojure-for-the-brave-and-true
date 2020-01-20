(ns clojure-for-the-brave-and-true.chap03.set)

;; SET

(def mySet #{1, 3, 5, 4})
;; or
(hash-set 1 3 5 4)

;; conj adds
(conj mySet 2)

;; check containment with contains?
(contains? mySet 6)