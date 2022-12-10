(ns aoc.day-8-test
  (:require
   [aoc.day-8 :as sut]
   [clojure.test :refer [deftest is]]))

(deftest visible?-test
  (is (sut/visible? 5 [3 2 1]))
  (is (not (sut/visible? 5 [3 5])))
  (is (sut/visible? 5 [])))

(deftest visibility-test
  (is (= [false false false true true]
         (sut/visibility [false false false false false]
                         [3 5 3 5 3])))
  (is (= [true true false true true]
         (sut/visibility [false false false false false]
                         [6 5 3 3 2])))
  (is (= [false true true]
         (sut/visibility [false false false]
                         [3 4 1]))))

(deftest solve-test
  (let [input [[3 0 3 7 3]
               [2 5 5 1 2]
               [6 5 3 3 2]
               [3 3 5 4 9]
               [3 5 3 9 0]]
        expected 21]
    (is (= expected (sut/solve input)))))

(deftest rotate-test
  (let [input [[3 0 3 7 3]
               [2 5 5 1 2]
               [6 5 3 3 2]
               [3 3 5 4 9]
               [3 5 3 9 0]]]
    (is (= input (-> input sut/rotate sut/rotate sut/rotate sut/rotate)))))
