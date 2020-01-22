(ns clojure-for-the-brave-and-true.chap03.assignments.assignment4)

;; Create a mapset function that maps into a set.
(defn mapset
  [f coll]
  (loop [remaining coll
         results #{}]
    (if (empty? remaining)
      results
      (let [[x & xs] remaining]
        (recur xs
               (into results [(f x)]))))))

(defn -main
  []
  (println (mapset inc [1 1 1 2 2 4])))
