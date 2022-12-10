(ns aoc.util
  (:require [clojure.java.io :as io]))

(defn read-input [day-number]
  (-> (io/resource (str "day-" day-number))
      (slurp)))

(defn pad [string pad-char desired-length]
  (apply conj (vec string) (take (- desired-length (count string)) (repeat pad-char))))

(defn xf-sort
  "A sorting transducer. Mostly a syntactic improvement to allow composition of
  sorting with the standard transducers, but also provides a slight performance
  increase over transducing, sorting, and then continuing to transduce."
  ([]
   (xf-sort compare))
  ([cmp]
   (fn [rf]
     (let [temp-list (java.util.ArrayList.)]
       (fn
         ([]
          (rf))
         ([xs]
          (reduce rf xs (sort cmp (vec (.toArray temp-list)))))
         ([xs x]
          (.add temp-list x)
          xs))))))

(defn excluding-partition-by [f coll]
  (->> coll
       (partition-by f)
       (remove #(every? identity (map f %)))))
