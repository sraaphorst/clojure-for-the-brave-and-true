(ns clojure-for-the-brave-and-true.chap03.def)

;; DEF

;; def names things
(def failed-explorers
  ["Larry Potter""Doreen the Explorer" "The Incredible Bulk"])

(defn error-message
  [severity]
  (str "ER MAH GERD! It's an error message! We're "
       (if (= severity :mild) "mildly inconvenienced." "DOOMED!")))

(defn -main
  []
  (println failed-explorers)
  (println (error-message :mild)))
