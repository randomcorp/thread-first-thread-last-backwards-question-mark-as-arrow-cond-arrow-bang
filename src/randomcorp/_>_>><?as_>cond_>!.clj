(ns randomcorp.->->><?as->cond->!
  (:require
    [clojure.spec.alpha :as s]))


(s/def ::clauses
  (s/*
    (s/alt
      :-> (s/cat :sym #{:->}
                 :form any?)
      :->> (s/cat :sym #{:->>}
                  :form any?)
      :<? (s/cat :sym #{:<?})
      :as-> (s/cat :sym #{:as->}
                   :as simple-symbol?
                   :form any?)
      :cond-> (s/cat :sym #{:cond->}
                     :pred any?
                     :form any?)
      :! (s/cat :sym #{:!}
                :pred any?))))


(defn construct-threading
  [current-result clauses]
  (if (empty? clauses)
    current-result
    (let [[clause-type arg-map] (first clauses)]
      (case clause-type
        :-> (recur `(-> ~current-result ~(:form arg-map)) (rest clauses))
        :->> (recur `(->> ~current-result ~(:form arg-map)) (rest clauses))
        :<? (let [res (gensym "res")]
              `(when-let [~res ~current-result]
                 ~(construct-threading res (rest clauses))))
        :as-> (recur `(as-> ~current-result ~(:as arg-map) ~(:form arg-map)) (rest clauses))
        :cond-> (recur `(cond-> ~current-result ~(:pred arg-map) ~(:form arg-map)) (rest clauses))
        :! (recur `(doto ~current-result (-> ~(:pred arg-map) assert)) (rest clauses))))))


(defmacro ->->><?as->cond->!
  "A very cool and useful threading macro that everyone totally
  needs. Takes an initial form, and threads it through a variety of
  possible threading clauses, each of which is specified with a keyword.

  - `:->` - threads through the first argument of a form.
  - `:->>` - threads through the last argument of a form.
  - `:<?` - checks that the last result was non-nil, otherwise short-circuits
            the whole form with nil.
  - `:as->` - binds the last result to a symbol when evaluating the next form.
  - `:cond->` - when the following predicate is true, threads as the first
                argument through a form.
  - `:!` - asserts that the following predicate, when the last result is threaded
           as its first argument, is true, otherwise throws an error."
  [start & clauses]
  (when-not (s/valid? ::clauses clauses)
    (throw (IllegalArgumentException.
             (str "Malformed clauses: " (s/explain-str ::clauses clauses)))))
  (construct-threading
    start
    (s/conform ::clauses clauses)))
