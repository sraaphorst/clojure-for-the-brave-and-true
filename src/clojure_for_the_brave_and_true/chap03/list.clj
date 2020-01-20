(ns clojure-for-the-brave-and-true.chap03.list)

;; LIST: linked list

(def myList '(0 1 2 3))
;; or
(list 0 1 2 3)

;; conj adds to beginning
(conj myList -1)                                            ;; (-1 0 1 2 3)

;; nth gets nth element
(nth myList 0)                                              ;; 0
