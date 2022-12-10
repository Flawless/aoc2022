(ns aoc.day-3
  (:require
   [clojure.java.io :as io]
   [clojure.set :as cset]
   [clojure.string :as str]))

(defn parse [input]
  (str/split-lines input))

(defn char-val [ch]
  (-> (if (> (int ch) (int \Z))
        (- (int ch) (int \a))
        (+ (char-val \z) (- (int ch) (int \A))))
      inc))

(defn ruksack-score [x]
  (transduce (map char-val) + (apply cset/intersection (map set (partition (/ (count x) 2) x)))))

(defn group-score [x]
  (transduce (map char-val) + (apply cset/intersection (map set x))))

(defn solve-1 [input]
  (transduce (map ruksack-score) + input))

(defn solve-2 [input]
  (transduce (map group-score) + (partition 3 input)))

(comment
  (-> (io/resource "day-3")
      (slurp)
      (parse)
      (solve-1))
  ;; => 7967
  (-> (io/resource "day-3")
      (slurp)
      (parse)
      (solve-2)
      ;; => Execution error (ArityException) at aoc.day-3/eval15040 (REPL:37).
      ;;    Wrong number of args (0) passed to: aoc.day-3/solve-2)
  )

(reduce cset/intersection [#{:a :b :c} #{:c :d :a} #{:c :r :z}])

((juxt inc dec) 1)
