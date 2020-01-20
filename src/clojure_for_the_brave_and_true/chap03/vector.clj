(ns clojure-for-the-brave-and-true.chap03.vector)

;; VECTOR: arrays

(def myVector [1 2 3])
;; or
(vector 1 2 3)

;; conj adds to end of vector
(conj myVector 4)                                           ;; [1 2 3 4]

;; get retrieves by index
(get [1 2 3] 0)                                             ;; 1
