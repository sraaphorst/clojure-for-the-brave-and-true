(ns clojure-for-the-brave-and-true.chap03.if)

;; IF

(defn
  ^Boolean isEven? [^Integer x]
  "Determines if a number is even."
  (zero? (mod x 2)))

(defn hammer-time?
  [weapon]
  "If a hammer, hammer-time!"
  (if (= weapon "hammer")
    (do (println "It's hammer time!")
        true)
    (do (println "You only have a measly" weapon)
        false)))

;; Everything evaluates to true except nil and false
(defn use-sword?
  [weapon]
  (when (= "sword" weapon)
    (println "I see you prefer the blade...")
    (println "How noble of you.")
    true))

(defn -main
  []
  (if (isEven? 3) (println "Hello, world!") (println "Goodbye, cruel world!"))
  (println (hammer-time? "hammer"))
  (println (hammer-time? "wooden spoon"))
  (use-sword? "sword"))
