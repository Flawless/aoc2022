(ns aoc.day-1
  (:require
   [aoc.util :as u]
   [clojure.java.io :as io]
   [clojure.string :as str]))

(defn exec
  "Invoke me with clojure -X aoc.day-1/exec"
  [opts]
  (println "exec with" opts))

(defn part-1-parse
  [input-str]
  (->> (str/split-lines input-str)
       (map parse-long)
       (u/excluding-partition-by nil?)))

(defn part-1
  [input]
  (let [sum (partial reduce +)]
    (transduce (map sum)
               max
               0
               input)))

(defn part-2
  [input]
  (let [sum (partial reduce +)]
    (transduce (comp (map sum)
                     (u/xf-sort >)
                     (take 3))
               +
               input)))

(defn -main
  "Invoke me with clojure -M -m aoc.day-1"
  [& args]
  (println :part-1 (-> (io/resource "day-1")
                       slurp
                       part-1-parse
                       part-1))
  (println :part-2 (-> (io/resource "day-1")
                       slurp
                       part-1-parse
                       part-2)))

(comment
  (-main)
  ;;
  )
