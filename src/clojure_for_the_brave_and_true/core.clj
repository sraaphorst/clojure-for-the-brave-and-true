(ns clojure-for-the-brave-and-true.core (:use clojure-for-the-brave-and-true.01-do-things))

(defn -main
  []
  (if (isEven? 3) (println "Hello, world!") (println "Goodbye, cruel world!"))
  (hammer-time "hammer")
  ; If this is not sword, it does not do anything.
  (use-sword? "sword")
  (println (sword-or-shield? "cape"))
  (println (perform-op :plus 5 6))
  (println (get-creature-name :pig))
  (println ho-hum)
  (println (say-hello))
  ;; I don't understand why map doesn't work here. Works in REPL.
  (println (codger "Billy" "Jean" "Martin"))
  (println (favourite-things "Steven" "Zed" "Dex" "Clojure"))
  (chooser ["raindrops" "bunnies" "all of the crap" "Kotlin"])
  (println (treasure {:lat 45 :lng 82.6}))
  (println (trap {:lat 44.9 :lng 82.7}))
  (println ((inc-maker 3) 5))
  (println (str "Hobbit parts: " full-hobbit))
  (counter 1 9 3)
  (println (str "Better hobbit parts: " better-hobbit))
  )