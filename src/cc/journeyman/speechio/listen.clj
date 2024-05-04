(ns cc.journeyman.speechio.listen
  "A consistent interface for things which listen, in order that we can plug and 
   unplug them easily.
   
   This is not yet quite right; rather than listening to an entire stream and 
   delivering one monolithic string at the end of it, we need to output a lazy
   sequence of sentence-like strings, which we can delimit by pauses in the
   speech, which (at least with the vosk listener) we can already detect."
  (:require [cc.journeyman.speechio.listeners.vosk :refer [vosk-listener]]
            [environ.core :refer [env]])
  (:import [clojure.lang AFn]
           [java.io InputStream]))

(def ^:dynamic *listener-fn* 
  "The function which we will use to listen for speech on streams. The idea here
   is that a listener function is a function of one argument, an input stream with 
   audio data, which returns as a string any text detected. This should make 
   listeners pluggable. This is the  socket into which pluggable listeners are 
   plugged."
  (fn [^InputStream stream] 
    (vosk-listener stream {:model-path (env :vosk-model)})))

(defn listen
  "Listen on this `stream` using this `function` (or the value of
   `*listener-fn*`, if no `function` supplied) and return, as text, any speech 
   recognised. **NOTE:** idiomatic use *should not* pass the `function` argument
   explicitly, but *should* instead bind `*listener-fn*`."
  ([^InputStream stream ^AFn function]
  (apply function (list stream)))
  ([^InputStream stream]
   (if (fn? *listener-fn*)
     (listen stream *listener-fn*)
     (throw (Exception. "No valid listener function")))))