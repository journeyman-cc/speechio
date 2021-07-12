(ns cc.journeyman.speechio.vosk
  "Thin wrapper around `com.alphacephei/vosk`"
  (:require [clojure.data.json :as json]
            [clojure.java.io :refer [as-file input-stream resource]]
            [clojure.string :as s]
            [clojure.walk :refer [keywordize-keys]]
            [environ.core :refer [env]]
            [taoensso.timbre :refer [debug error log trace]])
  (:import [clojure.lang Associative]
           [java.io BufferedInputStream InputStream IOException]
           [javax.sound.sampled AudioSystem UnsupportedAudioFileException]
           [org.vosk Model Recognizer]))

(defn- log-and-return-text
  [vosk-result]
  (let [r (keywordize-keys (json/read-str vosk-result))]
    (debug "Read: " r)
    (:text r)))

(defn- assemble-string
  "Form a new string from `body` and `extension`, adding a space between them
   when `last-empty?` is false."
  [body extension]
  (s/join
   " "
   [body (log-and-return-text extension)]))

(defn- listen-frag
  "Listen to the fragment of sound represented by the first `nbytes` of these
   `bytes` using this `regogniser` to build on this text `result`."
  [nbytes bytes recogniser to-extend]
  (assemble-string
   to-extend
   (cond (neg? nbytes) (.getFinalResult recogniser)
         (.acceptWaveForm recogniser bytes nbytes) (.getResult recogniser)
         :else (.getPartialResult recogniser))))

(defn vosk-listener
  "A listener function using `vosk` as the sound-to-text stage. Listen on this
   `stream` and interpret the input using the model indicated by the
   `:model-path` value in this `config`. If no config is supplied, will look in
   the environment for an environment variable `VOSK_MODEL`.
   
   Heavily cribbed from the DecoderDemo given
   [here](https://github.com/alphacep/vosk-api/blob/master/java/demo/src/main/java/org/vosk/demo/DecoderDemo.java)."
  ([^InputStream stream ^Associative config]
   (if (and (string? (:model-path config))
            (.exists (as-file (:model-path config)))
            (.isDirectory (as-file (:model-path config))))
     (try
       (let [ais (AudioSystem/getAudioInputStream
                  (BufferedInputStream. stream))
             bytes (make-array Byte/TYPE 4096);; (byte-array 4096)
             recogniser (Recognizer. (Model. (:model-path config)) 16000)]
         (loop [nbytes (.read ais bytes)
                result ""]
           (let [result' (listen-frag nbytes bytes recogniser result)]
             (if (neg? nbytes)
               (s/trim (s/replace result' #" +" " "))
               (recur (.read ais bytes) result')))))
       (catch UnsupportedAudioFileException e
         (error "Audio file problem?" e)
         (throw e))
       (catch IOException f
         (error "IO problem?" f)
         (throw f))
       (catch Exception any
         (error "Unexpected problem" any)
         (throw any)))
     (throw (IllegalArgumentException. "Config for `vosk-listener` must be a map
                                       with a key `:model-path` which must be a 
                                       string and must point to a directory."))))
  ([^InputStream stream]
   (if (env :vosk-model)
     (vosk-listener stream {:model-path (env :vosk-model)})
     (throw
      (Exception.
       "No config supplied and no `VOSK_MODEL` found in environment.")))))

(defn decoder-demo
  "Demo using the `test.wav` file supplied with the vosk distribution. To use 
   this (unless your machine is set up exactly like mine), pass in a path to 
   where you have unpacked your voice model. Models can be downloaded from
   [here](https://alphacephei.com/vosk/models)."
  ([] (decoder-demo "/home/simon/tmp/vosk-model-en-us-daanzu-20200905"))
  ([model-path]
   (vosk-listener (input-stream (resource "sounds/test.wav"))
                  {:model-path model-path})))