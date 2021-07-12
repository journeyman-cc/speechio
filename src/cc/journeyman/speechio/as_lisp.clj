(ns cc.journeyman.speechio.as-lisp
  "Translate various Stanford NLP objects into objects more easily handled
   by Lisp functions."
  (:require [clojure.walk :refer [postwalk]])
  (:import [clojure.lang Keyword PersistentArrayMap Ratio]
           [edu.stanford.nlp.ling CoreLabel]
           [edu.stanford.nlp.trees Tree]
           [java.lang Double Long String]))

(defprotocol AsLisp
  (as-lisp [o] "Render object `o` as a Lisp structure."))

(extend Tree
  AsLisp
  {:as-lisp (fn [o]
              {:type :tree
               :object o
               :label (when (.label o) (as-lisp (.label o)))
               :children (mapv as-lisp (.getChildrenAsList o))})})

(extend Double
  AsLisp
  {:as-lisp (fn [o] o)})

(extend Keyword
  AsLisp
  {:as-lisp (fn [o] o)})

(extend CoreLabel
  AsLisp
  {:as-lisp (fn [o] (keyword (.value o)))})

(extend Long
  {:as-lisp (fn [o] o)})

(extend PersistentArrayMap
  AsLisp {:as-lisp (fn [o] o)})

(extend Ratio
  {:as-lisp (fn [o] o)})

(extend String
  {:as-lisp (fn [o] o)})

(defn clean
  "Produce a simplified lisp structure representing the aspects of object `o` 
   which are likely to interest us."
  [o]
  (let [tree (as-lisp o)]
    (postwalk
     #(if (map? %) (dissoc % :object :type) %)
     tree)))