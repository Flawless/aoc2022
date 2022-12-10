(ns aoc.day-10
  (:require [aoc.util :as util]
            [clojure.string :as str]))

(def input (util/read-input 10))

(def test-input
  "addx 15
addx -11
addx 6
addx -3
addx 5
addx -1
addx -8
addx 13
addx 4
noop
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx -35
addx 1
addx 24
addx -19
addx 1
addx 16
addx -11
noop
noop
addx 21
addx -15
noop
noop
addx -3
addx 9
addx 1
addx -3
addx 8
addx 1
addx 5
noop
noop
noop
noop
noop
addx -36
noop
addx 1
addx 7
noop
noop
noop
addx 2
addx 6
noop
noop
noop
noop
noop
addx 1
noop
noop
addx 7
addx 1
noop
addx -13
addx 13
addx 7
noop
addx 1
addx -33
noop
noop
noop
addx 2
noop
noop
noop
addx 8
noop
addx -1
addx 2
addx 1
noop
addx 17
addx -9
addx 1
addx 1
addx -3
addx 11
noop
noop
addx 1
noop
addx 1
noop
noop
addx -13
addx -19
addx 1
addx 3
addx 26
addx -30
addx 12
addx -1
addx 3
addx 1
noop
noop
noop
addx -9
addx 18
addx 1
addx 2
noop
noop
addx 9
noop
noop
noop
addx -1
addx 2
addx -37
addx 1
addx 3
noop
addx 15
addx -21
addx 22
addx -6
addx 1
noop
addx 2
addx 1
noop
addx -10
noop
noop
addx 20
addx 1
addx 2
addx 2
addx -6
addx -11
noop
noop
noop")

(def sprite-size 3)

(defn parse [input]
  (->> (str/split-lines input)
       (map #(str/split % #" "))))

(defmulti process (fn [command _] (first command)))

(defmethod process "noop"
  [_ acc]
  (conj acc (peek acc)))

(defmethod process "addx"
  [[_ x] acc]
  (conj acc (peek acc) (+ (peek acc) (parse-long x))))

(defn register-state [input]
  (reduce (fn [acc command] (process command acc)) [1] input))

(defn solve [input]
  (->> input
       (drop 19)
       (take-nth 40)
       (take 6)
       (map-indexed (fn [idx x] (* x (+ (* idx 40) 20))))
       (reduce + 0)))

(defn make-sprite [pos]
  (set (range (- pos 1) (+ pos sprite-size -1))))

(defn draw-pix [crt sprite]
  (let [crt (rem crt 40)]
    (if (contains? sprite crt)
      "#"
      ".")))

(comment
 (def process nil)
 (-> input
     parse
     register-state
     solve)
 (->> test-input
     parse
     register-state
     (map make-sprite)
     (map-indexed draw-pix)
     (partition 40)
     (map println))
 (->> input
     parse
     register-state
     (map make-sprite)
     (map-indexed draw-pix)
     (partition 40)
     (map println)))
