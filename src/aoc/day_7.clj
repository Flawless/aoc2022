(ns aoc.day-7
  (:require
   [clojure.core :refer [=]]
   [clojure.java.io :as io]
   [clojure.string :as str]))

(def listing
 "$ cd /
$ ls
dir a
14848514 b.txt
8504156 c.dat
dir d
$ cd a
$ ls
dir e
29116 f
2557 g
62596 h.lst
$ cd e
$ ls
584 i
$ cd ..
$ cd ..
$ cd d
$ ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k")

(defn parse-line [line]
  (let [line (vec (str/split line #" "))
        command? (= (first line) "$")
        dir? (= (first line) "dir")
        file? (not (or dir? command?))]
    (cond
      command? (-> line (subvec 1) (update 0 keyword))
      dir? [:dir (second line)]
      file? [:file (parse-long (first line))])))


(defn parse [input]
  (->> (str/split-lines input)
       (map parse-line)))

(defn update-all-sizes [fs pointer size]
  (loop [fs fs
         pointer pointer]
    (if (seq pointer)
      (recur (update fs pointer + size)
             (butlast pointer))
      fs)))

(defn assoc-if-nil
  [m k v]
  (if (nil? (get m k))
    (assoc m k v)
    m))

(defn process-line [{:keys [fs pointer] :as state} [disp v]]
  (case disp
    :cd (if (= ".." v)
          (update state :pointer (comp vec butlast))
          (update state :pointer conj v))
    :ls state
    :dir (update state :fs assoc-if-nil (conj pointer v) 0)
    :file (update state :fs update-all-sizes pointer v)))

(defn process [input]
  (->> input
       (reduce process-line {:fs {["/"] 0} :pointer []})
       :fs
       (transduce (comp (map second)
                        (filter #(>= 100000 %)))
                  +)))
(defn process-2 [input]
  (let [fs (->> input
                (reduce process-line {:fs {["/"] 0} :pointer []})
                :fs)
        required (- 30000000 (- 70000000 (get fs ["/"])))]
    (transduce (comp (map second)
                     (filter #(<= required %)))
               min
               ##Inf
               fs)))

(comment
  (-> listing
      parse
      process)

  (-> (io/resource "day-7")
      slurp
      parse
      process-2)
  ;; => 366028

  ;; => 32176599

  (-> (io/resource "day-7")
      slurp
      parse
      process)
  ;; => 1086293

  ;; => ([:cd "/"]
  ;;     [:ls]
  ;;     [:dir "a"]
  ;;     [:file ["14848514" "b.txt"]]
  ;;     [:file ["8504156" "c.dat"]]
  ;;     [:dir "d"]
  ;;     [:cd "a"]
  ;;     [:ls]
  ;;     [:dir "e"]
  ;;     [:file ["29116" "f"]]
  ;;     [:file ["2557" "g"]]
  ;;     [:file ["62596" "h.lst"]]
  ;;     [:cd "e"]
  ;;     [:ls]
  ;;     [:file ["584" "i"]]
  ;;     [:cd ".."]
  ;;     [:cd ".."]
  ;;     [:cd "d"]
  ;;     [:ls]
  ;;     [:file ["4060174" "j"]]
  ;;     [:file ["8033020" "d.log"]]
  ;;     [:file ["5626152" "d.ext"]]
  ;;     [:file ["7214296" "k"]])

  (->> (io/resource "day-7")
       (slurp)
       (str/split-lines)
       (map #(str/split % #" "))
       (filter #(= (first %) "dir"))
       (distinct)
       (count))
  ;;
  )
