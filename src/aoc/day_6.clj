(ns aoc.day-6
  (:require [clojure.java.io :as io]))

(defn solve [input n]
  (->> input
       (partition n 1)
       (reduce (fn [idx x]
                 (if (= n (-> x set count))
                   (reduced idx)
                   (inc idx)))
               n)))

(comment
  (-> (io/resource "day-6")
      (slurp)
      (solve 4))
  ;; => 1582

  (-> (io/resource "day-6")
      (slurp)
      (solve 14))
  ;; => 3588

  (-> "mjqjpqmgbljsphdztnvjfqwrcgsmlb"
      (solve 14)))
