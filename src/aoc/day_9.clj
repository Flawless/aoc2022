(ns aoc.day-9
  (:require [aoc.util :as util]
            [clojure.string :as str]
            [clojure.math :as math]))

(def input (util/read-input 9))
(def test-input "R 4
U 4
L 3
D 1
R 4
D 1
L 5
R 2")
(def test-input2 "R 5
U 8
L 8
D 3
R 17
D 10
L 25
U 20")

(defn move [[x y]
            [d-x d-y]]
  [(+ x d-x)
   (+ y d-y)])

(defn move-head [pos dir]
  (case dir
    "U" (move pos [1 0])
    "D" (move pos [-1 0])
    "L" (move pos [0 -1])
    "R" (move pos [0 1])))

(defn trim [v]
  (mapv (fn [x] (if (zero? x)
                  x
                  (/ x (abs x)))) v))

(defn move-tail [tail head]
  (let [offset (map - head tail)]
    (if (every? (partial >= 1) (map abs offset))
      tail
      (move tail (trim offset)))))

(defn move-by-command [{:keys [wire walked-points]} [dir dist]]
  (if (zero? dist)
    {:wire wire
     :walked-points walked-points}
    (let [wire (first (reduce (fn [[wire idx] knot]
                                [(update wire idx move-tail (nth wire (dec idx)))
                                 (inc idx)])
                              [(update wire 0 move-head dir) 1]
                              (drop 1 wire)))]
      (recur {:wire wire :walked-points (conj walked-points (last wire))} [dir (dec dist)]))))

(defn solve [length commands]
  (:walked-points
   (reduce move-by-command {:wire (vec (repeat length [0 0])) :walked-points #{}} commands)))

(comment
  (->> input
       (str/split-lines)
       (map (fn [command] (-> command
                              (str/split #" ")
                              (update 1 parse-long))))
       (solve 10)
       (count))

  (->> test-input2
       (str/split-lines)
       (map (fn [command] (-> command
                              (str/split #" ")
                              (update 1 parse-long))))
       (solve 10)
       (count)
       )
  ;;
  )
