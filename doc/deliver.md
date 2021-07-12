# Deliver

The deliver phase of [the pipeline](intro.html) is about producing audio from (optionally marked up) text. The objective is to produce reasonably expressive
audio, and to associate different voices with different NPCs. Technology to
produce widely different spoken voices is evolving fast, so what is needed here
is simply a framework.

I think that the input to this phase is likely to be a pair comprising the text 
of the composed answer as [hiccup](https://github.com/weavejester/hiccup) formatted
[Speech Synthesis Markup Language](https://www.w3.org/TR/speech-synthesis/),
and a voice object of some kind provided by a non-player character. The output is 
likely to be either just an audio file, or, preferably, a combination of audio and a stream of information to a facial animator such as [JALI](http://jaliresearch.com/).