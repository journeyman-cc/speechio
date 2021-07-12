# speechio

A Clojure library designed to handle audio natural language input and output, primarily for [The Great Game](https://github.com/simon-brooke/the-great-game), q.v.

## Awful Warning

Pre-alpha code. Nothing works yet.

## Introduction

The core ideas behind The Great Game have always been

1. Users should be able to speak to non-player characters just by speaking to them, in their normal voice, and saying, in so far as that's possible, just whatever they want to say.
2. The ability for users to 'go off piste', to interact with non-player characters in ways not predicted by the developers, should be embraced as a way of adding richness and replayability to the experience.

The core essay to read to understand this sub-project is [Voice Acting Considered Harmful](https://simon-brooke.github.io/the-great-game/codox/Voice-acting-considered-harmful.html)

The working principle of The Great Game's interaction is intended to be

1. Alexa/Siri style speech interpretation;
2. A decision on whether to co-operate based on the particular NPC's general demeanor and particular attitude to the player;
3. A search of the game state and lore for relevant information;
4. A filtering of the results based on what the particular NPC can be expected to know;
5. Generation of a textual response from those results based on a library of templates which defines the particular NPC's dialect and style of speech;
6. Production of audio using a [Lyrebird]{https://www.descript.com/overdub?lyrebird=true) style generated voice.

The objective of the `speechio` library is to provide steps 1, 5 and 6 of that flow.

## State of play: NOT STABLE

### Listening

The [Vosk listener](https://github.com/journeyman-cc/speechio/blob/master/src/cc/journeyman/speechio/vosk.clj) 
seems to work correctly. Currently I'm having trouble generating .WAV files that Vosk can hear, but I think that's a problem with my sound recording, not with Vosk.

In any case, the listener function currently consumes a whole audio input stream, 
and then returns a monolithic string. This is wrong. We need to return a lazy
sequence of sentence-like chunks, delimited by pauses in the audio.

### Comprehension

We're getting useful-ish parse trees of text; but we need to do transforms on them 
before they're really useful to us. For example, given as input 

> 'the nearest fair haired swordsmith'

we're getting as interpretation

```clojure
       {:label :NP,
         :children
         [{:label :DT, :children [{:label :the, :children []}]}
          {:label :JJS, :children [{:label :nearest, :children []}]}
          {:label :NN, :children [{:label :fair, :children []}]}
          {:label :JJ, :children [{:label :haired, :children []}]}
          {:label :NN,
           :children [{:label :swordsmith, :children []}]}
```

We need to be able to identify 'fair haired' as an adjectival phrase, and that 
both 'fair-haired' and 'nearest' are qualifiers on the smith we're seeking. This 
is presumably a matter of training the tagger.  

### Generation

Not yet begun.

## Usage

Your guess is as good as mine.

## License

Copyright (c) 2021 Simon Brooke; licenced under the
[GNU General Public Licence](https://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html), either version 2 or, at your option, any later version.

**Note that** I'm open to discussing alternative commercial licensing, for commercial projects.
