(ns clojure-for-the-brave-and-true.chap03.assignments.ollie)

(defn word-count [phrase]
  (let [word-frequencies (frequencies (clojure.string/split phrase #" "))
        words (keys word-frequencies)]
    (loop [remaining-words words]
      (when (not (empty? remaining-words))
        (let [[first-word & rest-words] remaining-words]
          (println (str first-word ": " (get word-frequencies first-word)))
          (recur rest-words))))))

(defn -main
  []
  (word-count "ollie ollie oxen free"))