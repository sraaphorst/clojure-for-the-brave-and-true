(ns fwpd.core
  (:require [clojure.string :as s]))

(def filename "/Users/vorpal/Development/clojure/clojure_for_the_brave_and_true/fwpd/suspects.csv")

(def vamp-keys [:name :glitter-index])

(def yagung {:name "Yagungles" :glitter-index 9000})

(defn str->int [str]
  (Integer. str))

(def conversions {:name identity :glitter-index str->int})

(defn convert [vamp-key value]
   ((get conversions vamp-key) value))

;; BE CAREFUL: contains? does not do what you think it does.
;; It works by index on most collections. You probably want some instead. This is a handy
;; convenience function to do what you expect contains to do.
(defn contains-value? [element coll]
  (boolean (some #(= element %) coll)))

(defn validate [keywords record]
  "Validate whether the record's keys are the coll keywords."
  ;(every? #(contains-value? % keywords) (keys record)))
  ;; We probably want this instead: otherwise it could be possible that record's keywords are a subset.
  (= (keys record) keywords))

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))

;;(parse (slurp filename))

;; map transforms each row - vectors like ["Bella Swan" 0] into a map by using reduce in a manner
;; similar to earlier.
;; First map creates a seq of k-v pairs like ([:name "Bella Swan"] [:glitter index 0]).
;; Then, reduce builds up a map by associating a vamp key with a converted vamp value into
;; row-map.
(defn mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

;; Here's the first row mapified:
;;(first (mapify (parse (slurp filename))))

;; Finally, add this glitter-filter function:
(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= :glitter-index %) minimum-glitter) records)

(defn glitter-filter-to-names [results]
  " Take the names of output from the glitter-filter and extract the names"
  (map :name results))

(defn append-suspect [suspect suspects]
  "Append a new suspect to the glitter index."
  (when (validate vamp-keys suspect)
    (conj suspects suspect)))

(defn collect
  "Creates a csv string from list of maps"
  [records]
  (s/join "\n"
          (map #(s/join
                   "," (list (:name %)
                             (:glitter-index %)))
               records)))

(defn -main []
  (println "*** START ***")
  (println (contains-value? :glitter-index vamp-keys))
  (println (validate vamp-keys yagung))
  (println (mapify (parse (slurp filename))))
  (println (append-suspect {:name "Yagungles" :glitter-index 9001} (mapify (parse (slurp filename)))))
  (println (collect (append-suspect {:name "Yagungles" :glitter-index 9001} (mapify (parse (slurp filename))))))
  (println (count (glitter-filter 3 (mapify (parse (slurp filename))))))
  (println (glitter-filter-to-names (glitter-filter 3 (mapify (parse (slurp filename)))))))
