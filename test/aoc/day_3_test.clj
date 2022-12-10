(ns aoc.day-3-test
  (:require
   [aoc.day-3 :as sut]
   [clojure.test :refer [deftest is]]))

(deftest solve-1-test
  (let [input ["vJrwpWtwJgWrhcsFMMfFFhFp"
               "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL"
               "PmmdzqPrVvPwwTWBwg"
               "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn"
               "ttgJtRGJQctTZtZT"
               "CrZsJsPPZsGzwwsLwLmpwMDw"]
        expected 157]
    (is expected (sut/solve-1 input))))
