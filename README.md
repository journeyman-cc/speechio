# speechio

A Clojure library designed to handle audio natural language input and output, primarily for [The Great Game](https://github.com/simon-brooke/the-great-game), q.v.

## Awful Warning

Pre-alpha code. Nothing works yet.

## Introduction

The core ideas behind The Great Game have always been

1. Users should be able to speak to non-player characters just by speaking to them, in their normal voice, and saying, in so far as that's possible, just whatever they want to say.
2. The ability for users to 'go off piste', to interact with non-player characters in ways not predicted by the developers, should be embraced as a way of adding richness and replayability to the experience.

The core essay to read to understand this sub-project is [Voice Acting Considered Harmful](https://simon-brooke.github.io/the-great-game/codox/Voice-acting-considered-harmful.html)

## State of play

We're getting useful-ish parse trees; but we need to do transforms on them before
they're really useful to us. For example, given as input 

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

## Usage

Your guess is as good as mine.

## License

Copyright (c) 2021 Simon Brooke; licenced under the
[GNU General Public Licence](https://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html), either version 2 or, at your option, any later version.

