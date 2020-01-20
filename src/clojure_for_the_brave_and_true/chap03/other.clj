(ns clojure-for-the-brave-and-true.chap03.other)

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
  [asym-body-parts]
  (let [sym-parts (better-symmetrize-parts asym-body-parts)
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


(defn n-hits
  [n asym-body-parts]
  (loop [idx n parts []]
    (if (zero? idx)
      parts
      (recur (dec idx) (into parts [(hit asym-body-parts)])))))



(defn -main
  []
  (println (say-hello))
  (println (codger "Billy" "Jean" "Martin"))
  (println (favourite-things "Steven" "Zed" "Dex" "Clojure"))
  (chooser ["raindrops" "bunnies" "all of the crap" "Kotlin"])
  (println (treasure {:lat 45 :lng 82.6}))
  (println (trap {:lat 44.9 :lng 82.7}))
  (println ((inc-maker 3) 5))
  (println (str "Hobbit parts: " full-hobbit))
  (counter 1 9 3)
  (println (str "Better hobbit parts: " better-hobbit))
  (println (str "HIT: " (clojure.string/join ", " (map :name (n-hits 5 asym-hobbit-body-parts)))))
  )
