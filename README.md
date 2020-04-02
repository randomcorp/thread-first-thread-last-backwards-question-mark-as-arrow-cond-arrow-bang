# ->->><?as->cond->!


_As seen in [Every Clojure Talk Ever](https://www.youtube.com/watch?v=jlPaby7suOc) by [Amperity](https://github.com/amperity)_

## usage

Import in your project

```clojure
randomcorp/->->><?as->cond->! {:git/url "https://github.com/randomcorp/thread-first-thread-last-backwards-question-mark-as-arrow-cond-arrow-bang"
                               :sha     "b62075408207d75d5ece3e935e2f2ac54b84455b"}
```

Then require in you core
```clojure
(ns com.my-corp.my-mapp.core
  (:require [randomcorp.->->><?as->cond->! :refer [->->><?as->cond->!]]))
```

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

## License

Free, as in beer.
