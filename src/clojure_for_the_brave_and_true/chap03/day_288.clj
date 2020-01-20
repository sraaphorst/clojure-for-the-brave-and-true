(ns clojure-for-the-brave-and-true.chap03.day-288)

;; Convert an int into a list of Ints
(defn int-to-list
  [x]
  "Convert an integer into a list of digits."
  (map #(Integer. %) (clojure.string/split (.toString x) #"")))

(defn list-to-int
  [coll]
  "Convert a list of digits to an integer."
  (Integer. (clojure.string/join "" coll)))

(defn no-more-than-two?
  [coll]
  "Determine if a list contains three or more of the same element."
  (empty? (filter #(>= % 3)  (vals (frequencies coll)))))

(defn legal?
  [x]
  "Determine if a number is legal."
  (let [coll (int-to-list x)]
    (and (no-more-than-two? coll) (<= (count coll) 4))))

(defn to-smaller
  [x]
  "Take a number, sort its digits, and recombine it so that it is smaller number."
    (list-to-int (sort (int-to-list x))))

(defn to-bigger
  [x]
  "Take a number, sort its digits, and recombine it so that it is the larger number."
  (list-to-int(reverse(sort (int-to-list x)))))

;(defn check-kaprekar
;  [x]
;  (if (not (legal? x))
;    nil
;    (loop [steps 0 num x]
;      (if (= 6174 num)
;        steps
;        (loop (inc steps) (- (to-bigger num) (to-smaller num)))))))

;(defn check-kaprekar
;  [x]
;  "Count the number of Kraprekar steps"
;  (if (not (legal? x))
;    nil
;    (loop [steps 0
;           num x]
;      (if (= 6174 num)
;        steps
;        (let [bigger (to-bigger num)
;              smaller (to-smaller num)]
;              (loop (inc steps) (- bigger smaller)))))))

;;;; Works

;(defn -main
;  []
;  (let [num 5187]
;    (do
;    (println (to-bigger num))
;    (println (to-smaller num)))))

;;;; Works

;(defn check-kaprekar
;  [x]
;  (max (to-bigger x) (to-smaller x)))


;(defn -main
;  []
;  (println (check-kaprekar 5187)))


;;;;; Works

;(defn check-kaprekar
;  [x]
;  (if (not (legal? x))
;    nil
;    x))
;
;(defn -main
;  []
;  (println (check-kaprekar 3729)))

(defn check-kaprekar
  [x]
  (if (not (legal? x))
    nil
    (loop [steps 0 num x]
      (if (= 6174 num)
        steps
        (loop (inc steps) (- (to-bigger num) (to-smaller num)))))))

(defn -main
  (println check-kaprekar 5187))

