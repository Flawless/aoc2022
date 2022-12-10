(ns aoc.day-5
  (:require
   [aoc.util :as u]
   [clojure.java.io :as io]
   [clojure.string :as str]))

(defn drop-nth [n coll]
  (lazy-seq
    (when-let [s (seq coll)]
      (concat (take (dec n) (rest s))
              (drop-nth n (drop n s))))))

(defn parse [input]
  (let [[stacks commands] (str/split input #"\R\R")
        stacks (str/split-lines stacks)
        stack-line-length (count (last (butlast stacks)))]
    {:stacks (->> stacks
                  (butlast)
                  (mapcat #(u/pad % \space (inc stack-line-length)))
                  (drop 1)
                  (take-nth 4)
                  (map #(when-not (= \space %) (str %)))
                  (map keyword)
                  (partition (parse-long (last (str/split (last stacks) #" "))))
                  (apply map vector)
                  (map (partial remove nil?))
                  (map reverse)
                  (map vec)
                  (map #(-> [%1 %2]) (range))
                  (reduce (fn [acc [idx v]]
                            (assoc acc (inc idx) v))
                          {}))
     :commands (->> (str/split commands #" |\R")
                    (drop 1)
                    (take-nth 2)
                    (map parse-long)
                    (partition 3)
                    (mapv (fn [[count from to]]
                           {:count count
                            :from from
                            :to to})))}))

(defn step
  [stacks {:keys [count from to] :as command}]
  (if (< 0 count)
    (recur (-> stacks
               (update from pop)
               (update to conj (peek (get stacks from))))
           (update command :count dec))
    stacks))

(defn step-2
  [stacks {:keys [count from to] :as command}]
  (let [from-count (clojure.core/count (get stacks from))]
    (-> stacks
        (update from subvec 0 (- from-count count))
        (update to into (subvec (get stacks from) (- from-count count))))))

(defn solve-1 [{:keys [stacks commands]}]
  (->> commands
       (reduce step stacks)
       (sort-by key)
       (map (comp last second))
       (map name)
       (str/join)))

(defn solve-2 [{:keys [stacks commands]}]
  (->> commands
       (reduce step-2 stacks)
       (sort-by key)
       (map (comp last second))
       (map name)
       (str/join)))

(comment
  (pop (list :A :B :C))
  ;; => (:B :C)
  (pop [:A :B :C])
  ;; => [:A :B]
  (peek [:A :B :C])
  ;; => :C
  (conj [:A :B] :C)
  ;; => [:A :B :C]
  (conj '(:A :B) :C)
  ;; => (:C :A :B)
  (-> (io/resource "day-5")
      (slurp)
      (parse)
      (solve-2))
  ;; => "TDGJQTZSL"

  (-> (io/resource "day-5")
      (slurp)
      (parse)
      (solve-1))
  ;; => "BZLVHBWQF"


  ;; => Execution error (NullPointerException) at aoc.day-5/step (day_5.clj:49).
  ;;    Cannot invoke "Object.getClass()" because "x" is null

  ;; => "TBSHPZJWB"
  ;; => "JTHZSBBPW"

  )
