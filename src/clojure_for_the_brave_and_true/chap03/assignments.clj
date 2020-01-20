(ns clojure-for-the-brave-and-true.chap03.assignments)

(defn -main
  []
  ;; 1. Use the str, vector, hash-map, and hash-set functions
  (let [my-favorite-cat (str "yag" "ungle")
        collection [1 2 3]
        cat-rating (hash-map :yagungle 10 :oliver 5 :kali 3 :max 1)
        cats (hash-set :yagungle :oliver :kali :max)]
    (println "Cats:")
    (loop [cats-left (keys cat-rating)]
      (if (empty? cats-left)
        false
        (do (println (first cats-left) ":" (get cat-rating (first cats-left)))
            (recur (rest cats-left)))))))


