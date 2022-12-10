(ns aoc.day-5-test
  (:require
   [aoc.day-5 :as sut]
   [clojure.test :refer [deftest is]]))

(deftest parse-test
  (let [input "    [D]
[N] [C]
[Z] [M] [P]
 1   2   3

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2"
        expected {:stacks {1 [:Z :N]
                           2 [:M :C :D]
                           3 [:P]}
                  :commands [{:count 1
                              :from  2
                              :to    1}
                             {:count 3
                              :from  1
                              :to    3}
                             {:count 2
                              :from  2
                              :to    1}
                             {:count 1
                              :from  1
                              :to    2}]}
        actual (sut/parse input)]
    (is (= expected actual))
    (is (vector? (-> actual :stacks first)))))

(deftest solve-1-test
  (let [input {:stacks {1 [:Z :N]
                        2 [:M :C :D]
                        3 [:P]}
               :commands [{:count 1
                           :from 2
                           :to 1}
                          {:count 3
                           :from 1
                           :to 3}
                          {:count 2
                           :from 2
                           :to 1}
                          {:count 1
                           :from 1
                           :to 2}]}
        expected "CMZ"]
    (is (= expected (sut/solve-1 input)))))
