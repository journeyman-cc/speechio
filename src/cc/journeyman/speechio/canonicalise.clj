(ns cc.journeyman.speechio.canonicalise
  "Replace words and phrases in provided input with preferred equivalents 
 drawn from a thesaurus. 
 
 Broadly, the problem with allowing unconstrained
 input is that the words that users choose to use for things we actually 
 know about may not be our preferred words. This namespace is intended to
 provide a mechanism to canonicalise phrases and words in the input.
 
 The input is expected to be a lispified Stanford NLP parse tree; the 
 output is expected to be a new parse tree, with the difference being that
 the returned tree may have nodes marked :AMBIG, whose children are different 
 possible canonical interpretations."
  (:import [clojure.lang Atom PersistentArrayMap]))

(defprotocol Thesaurus
  (lookup [this speech-part] "Return the canonical expression equivalent to
                         this `speech-part`, or just this `speech-part`
                         if none are found. `speech-part` is expected to 
                         be a lispified (see `as-lisp`) Stanford NLP parse
                         tree.
                         
                         If multiple canonical equivalents are found, return
                         a new node whose `:label` value is `:AMBIG` and whose
                         `:children` value is a list of the possible 
                         equivalents."))

(extend Atom
  Thesaurus
  ;; It's highly likely that in practice we're going to want to add to 
  ;; thesauri, and, at least during development, to do so without restarting.
  ;; So it's probably best to be able to look 'through' an atom, without
  ;; worrying too much about what that atom is bound to.
  {:lookup (fn [this speechpart]
             (lookup (deref this) speechpart))})

(extend PersistentArrayMap
  Thesaurus
  {:lookup (fn [this speechpart]
             (let [k (if (keyword? speechpart) speechpart (str speechpart))
                   l (:label speechpart)
                   v (this k)]
               (cond v v
                     (map? speechpart)
                     (assoc speechpart
                            :label (or (this l) l)  
                            :children (mapv #(lookup this %)
                                           (:children speechpart)))
                     :else speechpart)))})

(def default-thesaurus-content
  "For testing and development only!"
  {;; we'll assume that when a user talks about a smith without other 
  ;; qualification they mean a sword smith.
   :smith :swordsmith
  ;; we'll assume that any blacksmith can make a sword (although perhaps not
  ;; a good one). This is obviously not a good assumption if the user wants
  ;; their horse shod, but this thesaurus is just to play with ideas.
   :blacksmith :swordsmith
  ;; the canonical form of `swordsmith` is not hyphenated.
   :sword-smith :swordsmith
   ;; if the user says "the sword smith", we'll get a noun phrase
   "{:label :NP, :children [{:label :DT, :children [{:label :the, :children []}]} {:label :NN, :children [{:label :sword, :children []}]} {:label :NN, :children [{:label :smith, :children []}]}]}" :swordsmith})

(def test-thesaurus (atom default-thesaurus-content))

