(ns clojure-for-the-brave-and-true.chap03.assignments.assignment3)

;; Create a decrementer.
(defn dec-maker
  [x]
  (fn [y] (- y x)))

;; Count down from 100 by 3 until below 0.
(defn -main
  []
  (let [dec3 (dec-maker 3)]
    (loop [counter 100]
      (if (<= counter 0)
        false
        (do
          (println counter)
          (recur (dec3 counter)))))))