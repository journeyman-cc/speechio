# Listen

This document contains design notes on listeners.

## The ideal

The ideal is that the user plays the game, and talks when they want to. As 
they talk, the speech is collected in 'sentences' by the listener, and passed 
to the comprehend stage from which it is passed either to the single NPC we 
know is being addressed, or, if we don't know how is being addressed, each of
the NPCs in reasonable listening distance.

This implies a listener function delivering a single lazy sequence of speech
acts as long as the game is being played.

That's the ideal, but it may be hard to do.

## Compromises

1. It will be acceptable for the player to have to indicate (e.g. by mouse-
clicking on) the non-player character they want to speak to; and that this
action may be necessary to start the listener if none is already running. This
isn't desirable because it doesn't modify general calls like **"help"**, but
it's acceptable.
2. It is acceptable for the listener process to have to be explicitly started
by the player interacting with some on screen widget, and to terminate after
a period of silence of more than a few seconds.
3. It is (more) acceptable for the listener process to terminate after a silence,
and to be restated when the player comes within listening range of a non-player
character.

Clearly, there should only be one listener process running at any one time.

