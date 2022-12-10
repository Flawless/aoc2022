(ns aoc.day-2
  (:require
   [clojure.core.logic :refer [!= == fresh run*]]
   [clojure.core.logic.fd :as fd]
   [clojure.java.io :as io]
   [clojure.string :as str]))

(run* [q]
  (!= q 1))

(run* [q]
  (fresh [x y]
    (!= [1 x] [y 2])
    (== q [x y])))

(run* [q]
  (fresh [x y]
    (fd/in x y [:rock :paper :scissors])))

(defn decode [coded]
  (case coded
    ("A" "X") :rock
    ("B" "Y") :paper
    ("C" "Z") :scissors))

(def decode-2
  {"A" :rock "X" :loose
   "B" :paper "Y" :draw
   "C" :scissors "Z" :win})

(defn parse [input decode]
  (->> (str/split input #"\R| ")
       (map decode)
       (partition 2)))

(defn arbitration [x y]
  (case [x y]
    ([:rock :paper]
     [:paper :scissors]
     [:scissors :rock]) :win

    ([:paper :rock]
     [:scissors :paper]
     [:rock :scissors]) :loose

    ([:rock :rock]
     [:paper :paper]
     [:scissors :scissors]) :draw))

(def sign->score
  {:rock 1
   :paper 2
   :scissors 3})

(def result->score
  {:win 6
   :draw 3
   :loose 0})

(defn process-round
  [[x y]]
  ()
  (+ (result->score (arbitration x y))
     (sign->score y)))

(defn solve-1 [input]
  (transduce (map process-round) + input))

(defn solve-2 [input])

(defn -main [& args]
  (println :part-1 (-> (io/resource "day-2")
                       (slurp)
                       (parse decode)
                       (solve-1)))
  (println :part-2 (-> (io/resource "day-2")
                       (slurp)
                       (parse decode-2)
                       (solve-2))))

(comment
  (-main)
  ;;
  )
