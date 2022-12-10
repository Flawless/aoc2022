(ns aoc.day-8
  (:require
   [clojure.java.io :as io]
   [clojure.string :as str]))

(def input
 "30373
25512
65332
33549
35390")

(def rseqv (comp vec rseq))

(defn rotate [m]
  (->> m
       (apply mapv vector)
       (mapv rseqv)))

(defn shape [h w init]
  (vec (repeat h (vec (repeat w init)))))

(defn parse [input]
  (->> (str/split-lines input)
       (mapv (partial mapv #(- (int %) (int \0))))))

(defn visibility-from-tree [tree neighbors]
  (reduce (fn [acc neighbor]
            (if (>= neighbor tree)
              (reduced (inc acc))
              (inc acc)))
          0 neighbors))

(defn acc-trees-visible-from-tree [acc tree neighbors]
  (* acc (visibility-from-tree tree neighbors)))

(defn acc-visible? [acc tree neighbors]
  (or (every? (partial > tree) neighbors) acc))

(defn tree-visibility [f visibility row]
  (loop [visibility (rseqv visibility)
         [tree & neighbors] row]
    (if (some? tree)
      (recur (update visibility (count neighbors) f tree neighbors)
             neighbors)
      (rseqv visibility))))

(defn matrix [m f]
  (let [heigh (count m)
        width (-> m first count)]
    (loop [trees m
           result (shape heigh width 1)]
      (let [acc (->> trees (map f result) (rotate))]
        (if (= m (rotate trees))
          acc
          (recur (rotate trees) acc))))))

(defn calc [visibility]
  (->> visibility
       flatten
       (filter true?)
       count))

(defn calc-2 [visibility]
  (->> visibility
       flatten
       (reduce max 0)))

(comment
  (-> (io/resource "day-8")
      (slurp)
      (parse)
      (matrix (partial tree-visibility acc-trees-visible-from-tree))
      (calc-2))

  (-> input
      (parse)
      (matrix (partial tree-visibility acc-visible?))
      (calc))

  (->> input
      (parse)
      (tree-visibility-matrix)
      (calc-2))
  ;; => [[0 0 0 0 0] [0 0 0 0 0] [0 0 0 0 0] [0 0 2 0 0] [0 0 0 0 0]]


  (-> [[3 0 1 7]
       [1 5 1 3]
       [3 2 0 3]
       [1 1 1 1]]
      (visibility-matrix)
      #_(solve))
  ;; => [[true true false] [true false false] [true true false]]

  ;; => [[true true true true true]
  ;;     [true true true false true]
  ;;     [true true false true true]
  ;;     [true false true false true]
  ;;     [true true false true true]]

  ;; => [[true true true true true]
  ;;     [true true true false true]
  ;;     [true true false true true]
  ;;     [true false true false true]
  ;;     [true true false true true]]

  ;; => 1643

  (->> [[3 0 3 7 3]
        [2 5 5 1 2]
        [6 5 3 3 2]
        [3 3 5 4 9]
        [3 5 3 9 0]]
       rotate
      #_ rotate))
