(ns clojure-for-the-brave-and-true.chap03.assignments.assignment2)

(def add100-1 #(+ 100 %))

(defn add100-2
  [x]
  (+ 100 x))

;; Compare the functions add100-1 and add100-2 for a range of 0 to 99 to make sure they are equivalent.
(defn -main
  []
  (let [counter (take 100 (range))]
    (loop [nums counter]
      (if (empty? nums)
        false
        (do
          (let [num (first nums)
                num100-1 (add100-1 num)
                num100-2 (add100-2 num)]
            (println (str num " => " num100-1 " == " num100-2 ": " (== num100-1 num100-2))))
          (recur (rest nums)))))))
