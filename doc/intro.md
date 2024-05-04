# Speechio: Introduction

`Speechio` is a library for speech input/output, focussed on creating better
interactivity for video games, but (if I do it well), likely to be adaptable
to other uses. It listens to the user and converts speech into text, passes that text (packaged as EDN) over an API to a server, receives the response as text (packaged as EDN).

The point, obviously, is that the server is pluggable, and that consequently the wire protocol must be designed to be flexible; but the intended server is **conversationalist**.

## The Pipeline

The 'speechio' library provides some of the components of the interaction 
pipeline of 
[The Great Game](https://simon-brooke.github.io/the-great-game/codox/index.html), but that pipeline of the interaction is not 
entirely within the orbit of the `speechio` library. Essentially the pipeline is:

1. **[Listen](listen.html)** to speech from the user (client side);
2. **[Comprehend](comprehend.html)** that speech into semantic structures (client side);
3. *Distribute* the semantic structures to non-player characters within range (server side?);
4. *Search* the game data for responses relevant to the semantic structures (server side);
5. *Filter* the search results to eliminate things not know to the NPC (server side);
6. *Select* the response to give as semantic structures (probably Toulmin 
structures);
7. *[Compose](compose.html)* the selected semantic structure into an idiomatic reply (server side);
8. **[Deliver](deliver.html)** deliver the reply as audio in a voice appropriate to the NPC (client side).

Only those stages shown **in bold** are handled by `speechio`.

## Architecture

As far as is possible, the functions provided by `speechio` are thin wrappers 
around third party libraries, and are designed to allow such libraries to be
pluggable. This is because:

1. All this technology is developing fast, and better text to speech, 
comprehension, and vocalisation libraries are likely to become available.
2. If the `speechio` subsystem is adopted by commercial games companies,
they will be able to afford to licence commercial libraries that I cannot
or do not choose to use.