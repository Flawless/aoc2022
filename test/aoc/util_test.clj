(ns aoc.util-test
  (:require
   [aoc.util :as sut]
   [clojure.test :refer [deftest is]]))


(deftest excluding-partition-by-test
  (is (= [[1 2 3] [4 5 6]] (sut/excluding-partition-by nil? [1 2 3 nil 4 5 6]))))

(deftest pad-test
  (let [expected (vec "xxx   ")
        actual (sut/pad "xxx" \space 6)]
    (is (= expected actual))
    (is (= 6 (count actual)))))
