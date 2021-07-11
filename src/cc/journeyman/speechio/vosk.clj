(ns cc.journeyman.speechio.vosk
  "Thin wrapper around `com.alphacephei/vosk`"
  (:require [clojure.data.json :as json]
            [clojure.java.io :refer [input-stream resource]]
            [clojure.string :as s]
            [clojure.walk :refer [keywordize-keys]]
            [taoensso.timbre :refer [debug error log trace]])
  (:import [org.vosk LibVosk LogLevel Model Recognizer]
           [java.io BufferedInputStream IOException]
           [javax.sound.sampled AudioSystem UnsupportedAudioFileException]))


(defn log-and-return-text
  [vosk-result]
  (let [r (keywordize-keys (json/read-str vosk-result))]
    (debug "Read: " r)
    (:text r)))

(defn decoder-demo
  "This is intended to be a simple transcription of the DecoderDemo given
   [here](https://github.com/alphacep/vosk-api/blob/master/java/demo/src/main/java/org/vosk/demo/DecoderDemo.java)."
  ([sound-source]
   (try
     (let [model (Model. "/home/simon/tmp/vosk-model-en-us-daanzu-20200905")
           ais (AudioSystem/getAudioInputStream
                (BufferedInputStream.
                 (input-stream sound-source)))
           bytes (make-array Byte/TYPE 4096);; (byte-array 4096)
           recogniser (Recognizer. model 16000)]
       (loop [nbytes (.read ais bytes)
              result ""]
         (debug (str "read " nbytes " bytes: " ))
         
         (cond
           (neg? nbytes) (subs
                          (s/replace
                           (s/join " " [result (log-and-return-text
                                                (.getFinalResult recogniser))])
                           #" +" " ")
                          1)
           (.acceptWaveForm recogniser bytes nbytes) (recur (.read ais bytes)
                                                            (s/join " " [result 
                                                                 (log-and-return-text 
                                                                  (.getResult recogniser))]))
           :else (recur (.read ais bytes)
                        (s/join " " [ result (log-and-return-text
                                     (.getPartialResult recogniser))])))))
     (catch UnsupportedAudioFileException e
       (error "Audio file problem?" e)
       (throw e))
     (catch IOException f
       (error "IO problem?" f)
       (throw f))
     (catch Exception any
       (error "Unexpected problem" any)
       (throw any))))
  ([] (decoder-demo (resource "sounds/test.wav"))))

