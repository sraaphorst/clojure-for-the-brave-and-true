(ns clojure-for-the-brave-and-true.chap03.and-or)

;; AND / OR

;; AND
;; and returns first false thing or last true thing in list of things.
(defn jedi-elf-sorcerer?
  [x]
  (and (re-find #"jedi" x) (re-find #"elf" x) (re-find #"sorcerer" x) true))


;; OR
;; or returns first true thing in list of things
(defn sword-or-shield?
  [x]
  (or (= x "sword") (= x "shield") "neither sword nor shield"))

(defn -main
  []
  (println (sword-or-shield? "mace"))
  (println (jedi-elf-sorcerer? "jedi-elf-sorcerer"))
  (println (jedi-elf-sorcerer? "jedi-milf-sorcerer"))
  (println ((or + -) 2 3))
  )