(ns clojure-for-the-brave-and-true.chap04.02-seq-function-examples)

(defn -main [])

;;;;; MAP ;;;;;

;; Simplistic map operation
(println (map inc [1 2 3]))

;; zipping strings together
(println (map str ["a" "b" "c"] '("A", "B", "C")))

;; It's as if map does:
(println (list (str "a" "A") (str "b" "B") (str "c" "C")))

;; You can pass as many collections as you want, as long as the mapping fn can handle that many arguments.
;; In this case, the "D" is ignored because it can't be mapped with anything else.
(println (map list '("A" "B" "C", "D") '("a" "b" "c") '(1 2 3) '(:y :a :g)))
;; -> ((A a 1 :y) (B b 2 :a) (C c 3 :g))

;; Here we make a list of maps for a vampire trying to avoid eating people.
(def human-consumption [8.1 7.3 6.6 5.0])
(def critter-consumption [0.0 0.2 0.3 1.1])

(defn unify-diet-data [human critter]
  {:human human :critter critter})

(println (map unify-diet-data human-consumption critter-consumption))

;; You can pass map a collection of functions.
(def sum #(reduce + %))
(def avg #(/ (sum %) (count %)))
(defn stats [numbers]
  (map #(% numbers) [sum count avg]))

;; calls sum, count, and then avg on numbers, the parameter.
(println (stats [3 4 10]))

;; map can be used to retrieve the value associated with a keyword from a collection of map structures.
;; Because keywords can be used as functions, you can do this succinctly:
(def cats
  [{:name "Duncan" :nickname "Yagungles" :age 10 :weight 8}
   {:name "Max" :nickname "Fattypuss" :age 14 :weight 12}
   {:name "Kali" :nickname "Princesa" :age 3 :weight 3}
   {:name "Oliver" :nickname "BBMM" :age 2 :weight 4}])

(println (map :name cats))
(println (map :nickname cats))



;;;;; REDUCE ;;;;;

;; 1. Transform a map's values while maintaining its keys.
;; reduce treats the argument {:max 30 :min 10} as a sequence of vectors:
;;   ([:max 30] [:min 10]).
;; Then it starts with the second argument and builds it up using the first argument.
(println (reduce (fn [new-map [key val]]
                   (assoc new-map key (inc val)))
                 {:what 5}
                 {:max 30 :min 10}))

;; 2. Filter out keys from a map based on their values.
(println (reduce (fn [new-map [key val]]
                   (if (> val 4)
                     (assoc new-map key val)
                     new-map))                              ; need to return new-map here to accumulate
                 {:buckets-of-shame 99}
                 {:human 4.1 :critter 3.9 :gato 0 :perro 852}))

(println (reduce (fn [new-map [key val]]
                   (when (> val 4)
                     (assoc new-map key val)))              ; using when doesn't work: only gives last value
                 {:buckets-of-shame 99}
                 {:human 4.1 :critter 3.9 :gato 0 :perro 852}))


;; Implementing map using reduce
(defn map' [f coll]
  (reduce #(cons (f %2) %1) '() (reverse coll)))




;;;;; TAKE, DROP, TAKE-WHILE, DROP-WHILE ;;;;;
(println (drop 3 (take 10 (range))))


(def food-journal
  [{:month 1 :day 1 :human 5.3 :critter 2.3}
   {:month 1 :day 2 :human 5.1 :critter 2.0}
   {:month 2 :day 1 :human 4.9 :critter 2.1}
   {:month 2 :day 2 :human 5.0 :critter 2.5}
   {:month 3 :day 1 :human 4.2 :critter 3.3}
   {:month 3 :day 2 :human 4.0 :critter 3.8}
   {:month 4 :day 1 :human 3.7 :critter 3.9}
   {:month 4 :day 2 :human 3.7 :critter 3.6}])

;; All the days more than 4 humans were eaten.
(println (take-while #(> (:human %) 4) food-journal))

;; Info for Jan and Feb.
(println (take-while #(< (:month %) 3) food-journal))

;; Info for March on.
(println (drop-while #(< (:month %) 3) food-journal))

;; Info for Feb and March
(println (take-while #(< (:month %) 4) (drop-while #(< (:month %) 2) food-journal)))




;;;;; FILTER ;;;;;
;; Processes whole list, so use take-while and drop-while when possible.
(println (filter #(< (:age %) 10) cats))




;;;;; SOME ;;;;;
;; Like exists: returns first value that matches predicate and is truthy.
;; The anonymous functions here return true or nil, so that is what we will get.
(println (some #(> (:weight %) 10) cats))
(println (some #(< (:weight %) 2) cats))

;; To get the actual value, use and:
(println (some #(and (> (:weight %) 10) %) cats))




;;;;; SORT, SORT-BY ;;;;;
;; Sorts in non-decreasing order
(println (sort [6 4 2 1 3 5]))
(println (sort-by count ["aaa", "bb", "c"]))
(println (sort-by #(:age %) cats))





;;;;; CONCAT ;;;;;
;; Just concatenates sequences.
(println (concat [1 2] [3 4] [5 6 7 8]))
