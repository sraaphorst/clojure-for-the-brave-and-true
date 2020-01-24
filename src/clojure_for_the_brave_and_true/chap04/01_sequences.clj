(ns clojure-for-the-brave-and-true.chap04.01-sequences)

;; A sequence has operations first, rest, and cons.
;; An abstraction is a collection of operations. If an object supports those operations,
;; then it is an instance of that abstraction.

;; vector, list, set, and map are all sequences, so you can map over them:
(defn titleize
  ([] (titleize "Clojure"))
  ([topic] (str topic " for the Brave and True")))

(defn -main [])

(println (map titleize ["Hamsters" "Clojure"]))

(println (map titleize '("Empathy" "Decorating")))

(println (map titleize #{"Elbows", "Soap Carving"}))

(println (map #(titleize (second %)) {:uncomfortable-thing "Winking"}))

;; When Clojure expects a sequence, it calls the seq function on the parameter to allow for a data structure
;; that supplies implementations of first, rest, and cons.

(println (seq '(1 2 3)))
(println (seq [1 2 3]))
(println (seq #{1 2 3}))
(println (seq {:name "Bill Compton" :occupation "Dead Mopey Guy"}))

;; seq treats maps like lists of vectors.
;; To get the inverse behaviour, you can use INTO to stick resultss into an empty map (or even a map with things
;; already in it, like below).
(println (into {:d 4} (seq {:a 1 :b 2 :c 3})))

;; Takeaway: what is important is what we can DO with a data structure, and not its implementation.
