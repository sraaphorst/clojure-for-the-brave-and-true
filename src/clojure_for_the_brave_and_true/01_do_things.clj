(ns clojure-for-the-brave-and-true.01-do-things)

;(defn isEven?
;  ^Boolean
;  "Check if an integer is even."
;  [^Integer number]
;  ((zero? (% number 2))))

(defn
  ^Boolean isEven? [^Integer x]
  "Determines if a number is even."
  (zero? (mod x 2)))


(defn hammer-time
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

;; or returns first true thing in list of things
(defn sword-or-shield?
  [hand]
  (or (= hand "sword") (= hand "shield") "neither sword nor shield"))

;; and returns first false thing or last true thing in list of things.
(defn jedi-elf-sorcerer
  [whaaaa]
  (and (= whaaaa "jedi") (= whaaaa "elf") (= whaaaa "sorcerer")))

;; def names things
(def failed-explorers
  ["Larry Potter""Doreen the Explorer" "The Incredible Bulk"])

(defn error-message
  [severity]
  (str "ER MAH GERD! It's an error message! We're"
    (if (= severity :mild) "mildly inconvenienced." "DOOMED!")))

;; MAPS
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


;; VECTORS: Arrays
(def myVector [1 2 3])
;; or
(vector 1 2 3)

;; conj adds to end of vector
(conj myVector 4)                                           ;; [1 2 3 4]

;; get retrieves by index
(get [1 2 3] 0)                                             ;; 1


;; LISTS: Linked list
(def myList '(0 1 2 3))
;; or
(list 0 1 2 3)

;; conj adds to beginning
(conj myList -1)                                            ;; (-1 0 1 2 3)

;; nth gets nth element
(nth myList 0)                                              ;; 0


;; SETS
(def mySet #{1, 3, 5, 4})
;; or
(hash-set 1 3 5 4)

;; conj adds
(conj mySet 2)

;; check containment with contains?
(contains? mySet 6)



;; FUNCTIONS with multiple arity
(defn say-hello
  ([] (say-hello "World"))
  ([name] (str "Hello, " name "!")))


;; Variadic functions with &
(defn codger-communication
  [whippersnapper]
  (str "Get off my lawn, " whippersnapper "!!!"))
(defn codger
  [& whippersnappers]
  (map codger-communication whippersnappers))

(defn favourite-things
  [name & things]
  (str "Hello, " name ". Here are my favourite things: " (clojure.string/join ", " things) "."))


;; VECTOR DESTRUCTURING
(defn my-first
  [[first-thing]]
  first-thing)

(defn chooser
  [[first-choice second-choice & unimportant-choices]]
  (println (str "Your first choice is: " first-choice))
  (println (str "Your second choice is: " second-choice))
  (println (str "We're ignoring the rest of your choices. "
                "Here they are in case you need to cry over them: "
                (clojure.string/join ", " unimportant-choices))))


;; MAP DESTRUCTURING
(defn treasure
  [{lat :lat lng :lng}]
  (str "The treasure is at lat " lat ", long " lng "."))


;; Can just break keys out:
(defn trap
  [{:keys [lat lng]}]
  (str "The trap is at lat " lat ", long " lng "."))


;; ANONYMOUS FUNCTIONS / LAMBDAS
(map (fn [x] (* 2)) [1 2 3])

;; These two are equivalent:
(def my-fn (fn [x y] (+ x y)))

(defn my-fn2 [x y] (+ x y))

;; Short hand form:
(#(* % 3) 8)                                                ; 24
(#(* %1 %2) 8 2)                                            ; 48

;; Rest:
(#(identity %&) 1 "blarg" :yip)                             ; (1 "blarg" :yip)


;; RETURNING FUNCTIONS
(defn inc-maker
  "Create a custom incrementer"
  [inc-by]
  #(+ % inc-by))



;; HOBBIT-PROJECT: vector of maps
(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "left-shoulder" :size 3}
                             {:name "left-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "left-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "left-kidney" :size 1}
                             {:name "left-hand" :size 2}
                             {:name "left-knee" :size 2}
                             {:name "left-thigh" :size 4}
                             {:name "left-lower-leg" :size 3}
                             {:name "left-achilles" :size 1}
                             {:name "left-foot" :size 2}])

;; Create the matching right side.
(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

(defn symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-parts]
  (loop [remaining-asym-parts asym-parts
         final-parts []]
    (if (empty? remaining-asym-parts)
      final-parts
      (let [[part & remaining] remaining-asym-parts]
        (recur remaining
               (into final-parts
                     (set [part (matching-part part)])))))))

(def full-hobbit (symmetrize-body-parts asym-hobbit-body-parts))


;; LET: binds values to names
(let [x 3] x)                                               ; 3

(def dalmation-list ["Pongo" "Perdita" "Pup1" "Pup2"])
(let [dalmations (take 2 dalmation-list)] dalmations)       ; ("Pongo", "Perdita")


;; LOOPS AND RECURSION
;; prints Iter#0, ... #Iter10, Done.
;(loop [iter 0]
;  (println (str "Iter#" iter))
;  (if (>= iter 10)
;    (println "Done!")
;    (recur (inc iter))))

;; Using a normal function definition.
(defn recursive-printer
  ([]
   (recursive-printer 0))
  ([iter]
   (println (str "Iter#" iter))
   (if (>= iter 10)
     (println "Done!")
     (recursive-printer (inc iter)))))

;; Count from init to final inclusive by step.
(defn counter
  [init final step]
  (loop [iter init]
    (println (str "Iter#" iter))
    (if (>= iter final)
      (println "Done!")
      (recur (#(+ % step) iter)))))


;; REDUCE: opt-initial-value fn coll
(def sum25 (reduce + 15 [1 2 3 4]))

;; possible implementation
(defn my-reduce
  ;; initial value init
  ([f init coll]
   (loop [result init remain coll]
     (if (empty? remain)
       result
       ;; Not quite sure what is happening here.
       (recur (f result (first remain)) (rest remain)))))
  ;; no initial value: use head for init value
  ([f [head & tail]]
   (my-reduce f head tail)))

;; A better symmetrizer
(defn better-symmetrize-parts
  "Expects a seq of maps that have a :name and a :size"
  [asym-parts]
  (reduce (fn [final-parts part]
            (into final-parts (set [part (matching-part part)])))
          []
          asym-parts))

(def better-hobbit (better-symmetrize-parts asym-hobbit-body-parts))


;; HOBBIT VIOLENCE
;; The let statement:
;; 1. symmetrizes the body parts and binds to sym-parts.
;; 2. maps the parts to their sizes and sums them, binding to body-part-size-sum.
;; 3. generates a random number that corresponds to a body part and binds to target.
(defn hit
  (let [sym-parts (better-symmetrize-parts asym-hobbit-body-parts)
        body-part-size-sum (reduce + (map :size sym-parts))
        target (rand body-part-size-sum)]
    ;; loop retrieves the actual body part.
    ;; Initially eels the first part off, leaving remaining, from sym-parts using decomposition.
    ;; Takes an accumulated-size, initially bonded to the size of the first part.
    (loop [[part & remaining] sym-parts
           accumulated-size (:size part)]
      ;; If this is the corresponding body part, return it
      (if (> accumulated-size target)
        part
        ;; Otherwise recur, passing in the remaining parts and the accumulated size to the loop.
        (recur remaining (+ accumulated-size (:size (first remaining))))))))