(ns cc.journeyman.speechio.vosk-test
  (:require [clojure.test :refer [deftest is testing]]
            [clojure.java.io :refer [input-stream resource]]
            [cc.journeyman.speechio.vosk :refer [vosk-listener]]))

(deftest numbers-test
  (testing "Currently, the Vosk project's own `test.wav` file is the only one
          I have which works. A vosk model is also required, and this test
          will fail if an environment variable `VOSK_MODEL` is not
          provided.")
  (is (= (vosk-listener (input-stream (resource "sounds/test.wav")))
         "one zero zero zero one nine oh two one oh zero one eight zero three")))