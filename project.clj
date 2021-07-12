(defproject journeyman-cc/speechio "0.1.0-SNAPSHOT"
  :cloverage {:output "docs/cloverage"}
  :codox {:metadata {:doc "**TODO**: write docs"
                     :doc/format :markdown}
          :output-path "docs/codox"
          :source-uri "https://github.com/journeyman-cc/speechio/blob/master/{filepath}#L{line}"}
  :description "Speech input/output library; primarily for The Great Game."
  :dependencies [[aysylu/loom "1.0.0"]
                 [cc.journeyman/errata "0.1.0"]
                 [com.alphacephei/vosk "0.3.21"]
                 [com.fzakaria/slf4j-timbre "0.3.21"]
                 [com.taoensso/timbre "5.1.2"]
                 [environ "1.2.0"]
                 [net.java.dev.jna/jna "5.7.0"]
                 [org.clojure/clojure "1.10.1"]
                 [org.clojure/data.json "2.3.1"]
                 [org.clojurenlp/core "3.7.0"]]
  :license {:name "GNU General Public License,version 2.0 or (at your option) any later version"
            :url "https://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html"}
  :plugins [[lein-cloverage "1.2.2"]
            [lein-codox "0.10.7"]
            [lein-environ "1.2.0"]]
  :repl-options {:init-ns cc.journeyman.speechio.core}
  :repositories {"alphacephei" {:url "https://alphacephei.com/maven/"}}
  :url "http://example.com/FIXME")
