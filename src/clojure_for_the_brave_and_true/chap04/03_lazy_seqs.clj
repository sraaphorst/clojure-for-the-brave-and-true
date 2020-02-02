(ns clojure-for-the-brave-and-true.chap04.03-lazy-seqs)

(defn -main [])

;; Clojure returns lazy seqs with map, filter, etc.
;; You are a modern day task force to identify vampired.
(def vampire-database
  {0 {:makes-blood-puns? false, :has-pulse? true :name "McFishwich"}
   1 {:makes-blood-puns? false, :has-pulse? true :name "McMackson"}
   2 {:makes-blood-puns? true, :has-pulse? false :name "Damon Salvatore"}
   3 {:makes-blood-puns? true, :has-pulse? true :name "Mickey Mouse"}})

(defn vampire-related-details
  [social-security-number]
  (Thread/sleep 100)
  (get vampire-database social-security-number))

(defn vampire?
  [record]
  (and (:makes-blood-puns? record)
       (not (:has-pulse? record))
       record))

(defn identify-vampire
  [social-security-numbers]
  ;; first here only applies filter until gets first value.
  (first (filter vampire? (map vampire-related-details social-security-numbers))))

(println (time (vampire-related-details 0)))

;; range returns a lazy sequence 0, ..., 999,999.
;; map returns a lazy sequence that is associated with the name mapped-details
;; note that map didn't actually do the application: it just set it up to be perfomred lazily.
(time (def mapped-details (map vampire-related-details (range 0 1000000))))

;; takes about 3.2 seconds
;; This is because Clojure CHUNKS its lazy sequences: whenever Clojure has to realize an element, it preemptively
;; realizes some of the next elements as well. We only wanted the first elemeent of mapped-details, but Clojure
;; prepared the next 3.1 as well (the Thread/sleep call is what caused the time lag).
(println (time (first mapped-details)))

;; Lazy seqs only need to be realized once. Doing it again takes almost no time.
(println (time (first mapped-details)))

;; With this knowledge, you can efficiently mine the vampire database:
(println (time (identify-vampire (range 0 1000000))))





;;;;; REPEAT ;;;;;
(println (concat (take 8 (repeat "na")) ["Batman!"]))
(println (concat (repeat 8 "na") ["Batman!"]))




;;;;; REPEATEDLY ;;;;;
;; Call a function repeatedly.
(println (take 10 (repeatedly #(rand-int 100))))




;;;;; EVEN NUMBERS ;;;;;
;; Construct our own infinite sequence:
(defn even-numbers
  ;; cons to a lazy list
  ([] (even-numbers 0))
  ([n] (cons n (lazy-seq (even-numbers (+ n 2))))))
(println (take 10 (even-numbers)))




;;;;; LAZY-SEQ ;;;;;
;; Only evaluates first time.
(defn rand-numbers
  ([] (rand-numbers (rand-int 10)))
  ([n] (cons n (lazy-seq (rand-numbers (rand-int 10))))))
(println (take 10 (rand-numbers)))
(println (take 10 (rand-numbers)))
(println (take 10 (rand-numbers)))





;;;;; INTO ;;;;;
(println (into ["Already here"] ["Now I have a friend"]))





;;;;; CONJ ;;;;;
(println (conj [0] 1 2 3 4 5))

(defn my-conj [target & additions]
  (into target additions))
(println (my-conj [[1, 2]] [3] [4] [5,6]))




;;;;; APPLY ;;;;;
;; Explodes a seqqable data structure so it can be passed to rest params.
(println (max 0 1 2))
(println (max [0 1 2]))
(println (apply max [0 1 2]))

(defn my-into
  [target additions]
  (apply conj target additions))




;;;;; PARTIAL ;;;;;
;; Takes fn with any # args and returns new fun.
;; When called, it calls original with original args, along with new args.
(def add10 (partial + 10))
(println (add10 5))
(println (add10 5 5 10))

;; Here's how you might define my-partial.
(defn my-partial
  [partialized-fn & args]
  (fn [& more-args]
    (apply partialized-fn (into args more-args))))

(def add20 (my-partial + 20))
(println (add20 3))