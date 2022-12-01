(ns aoc.day-1-test
  (:require
   [aoc.day-1 :as sut]
   [clojure.test :as t :refer [deftest is testing]]))

(deftest part-1-parse-test
  (let [input "1\n2\n\n3"
        expected [[1 2] [3]]]
    (is (= expected (sut/part-1-parse input)))))

(deftest part-1-test
  (testing "part-1"
    (let [input [[1000 2000 3000] [4000] [5000 6000] [7000 8000 9000] [10000]]
          expected 24000]
      (is (= expected (sut/part-1 input))))))

(deftest part-2-test
  (testing "part-2"
    (let [input [[1000 2000 3000] [4000] [5000 6000] [7000 8000 9000] [10000]]
          expected 45000]
      (is (= expected (sut/part-2 input))))))
