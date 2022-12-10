(ns aoc.day-2-test
  (:require
   [aoc.day-2 :as sut]
   [clojure.test :refer [deftest is]]))

(deftest solve-1-test
  (let [input [[:rock :paper]
               [:paper :rock]
               [:scissors :scissors]]
        expected 15]
      (is (= expected (sut/solve-1 input)))))
