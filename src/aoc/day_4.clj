(ns aoc.day-4
  (:require
   [clojure.java.io :as io]
   [clojure.string :as str]))

(defn parse [input]
  (->> (str/split input #"\R|,")
       (map #(str/split % #"-"))
       (map (fn [[x y]]
              (range (parse-long x) (inc (parse-long y)))))
       (map set)
       (partition 2)))

(defn solve-pair [compare [x y]]
  (or (compare true? (map (partial contains? x) y))
      (compare true? (map (partial contains? y) x))))


(defn solve-1 [pairs]
  (count (filter (partial solve-pair every?) pairs)))

(defn solve-2 [pairs]
  (count (filter (partial solve-pair some) pairs)))

(comment
  (->> (io/resource "day-4")
       (slurp)
       (parse)
       (solve-1))
  ;; => 526

  (->> (io/resource "day-4")
       (slurp)
       (parse)
       (solve-2))
  ;; => 886
  )
