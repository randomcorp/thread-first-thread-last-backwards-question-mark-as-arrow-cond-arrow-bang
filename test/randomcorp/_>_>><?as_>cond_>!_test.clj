(ns randomcorp.->->><?as->cond->!-test
  (:require
    [clojure.test :refer :all]
    [randomcorp.->->><?as->cond->! :refer [->->><?as->cond->!]]))


(deftest ->->><?as->cond->!-test
  (is (= {:vec-of-numbers [2 3 4]
          :other-key "other-val"}
         (->->><?as->cond->!
           []
           :-> (conj 1 2 3)
           :->> (mapv inc)
           :as-> v {:vec-of-numbers v}
           :cond-> true (assoc :other-key "other-val")
           :<?
           :! map?))))
