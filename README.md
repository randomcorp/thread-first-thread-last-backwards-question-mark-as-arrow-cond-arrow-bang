# ->->><?as->cond->!

This is the bread and butter of randomcorp's Clojure code and we're
excited to share it with the world. Have you been frustrated with
always needing to compose the various types of threading macros?

```clojure
(-> []
    (conj 1 2 3)
    (->> (mapv inc))
    (as-> v
      {:vec-of-numbers v})
    (cond->
      (some-pred) (assoc :other-key "other-val")))
```

It would be nice if there were one single macro to do all that for
you. Well, now there is!

```clojure
(->->><?as->cond->!
  []
  :-> (conj 1 2 3)
  :->> (mapv inc)
  :as-> v {:vec-of-numbers v}
  :cond-> (some-pred) (assoc :other-key "other-val"))
```

To short-circuit on nil values (a la `some->`), use `:<?`:

```clojure
(->->><?as->cond->!
  []
  :-> not-empty
  :<?
  :-> (conj 1))

;; => nil
```

To make assertions about the values during the threading, use `:!`:

```clojure
(->->><?as->cond->!
  2
  :-> inc
  :! even?)

;; => Error!
```
