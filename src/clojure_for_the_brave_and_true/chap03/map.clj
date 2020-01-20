(ns clojure-for-the-brave-and-true.chap03.map)

;; MAP

(def myMap {:first-name "Sebastian" :last-name "Raaphorst"})
;; or
(hash-map :first-name "Oink" :last-name "McGee")

;; Can even store functions
(def ops {:plus + :minus - :times * :divide /})

;; Specify an op and perform it on operands
(defn perform-op
  [op x y]
  ((get ops op) x y))

;; Can specify last entry to get as a default
(def creatures {:ogre "ogre" :goblin "goblin" :yagungle "yagungle"})
(defn get-creature-name
  [creature]
  (get creatures creature "OYNK"))

;; get-in for nested maps
(def ho-hum (get-in {:a 0 :b {:c "ho hum"}} [:b :c]))

;; These are all equivalent, where "Nope" is optional default parameter, otherwise nil.
(def myMap {:a 1 :b 2 :c 3})
(def a-val (get myMap :a "Nope"))
(def b-val (:b myMap "Nope"))
(def c-val (myMap :c "Nope"))
(def default-val (myMap :d "Nope"))


(defn -main
  []
  (println (perform-op :plus 5 6))
  (println (get-creature-name :pig)))