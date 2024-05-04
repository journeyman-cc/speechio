# Comprehend

**NOTE THAT** the comprehend stage is sort of awkwardly shared between client side (in our case speechio) and server side (in our case conversationalist). In order to parse sentences, it will be useful for speechio to know what pronouns should be assumed to refer to, but it is (partly) conversationalist which can work out what in the game world pronouns refer to. So in the package passed back from conversationalist to speechio, we need pronoun bindings.

## General design

The Comprehend stage in [the pipeline](intro.html) takes sentences or partial sentences as strings from the [listen](listen.html) stage, and delivers them as semantic structures to the distribute stage (not part of `speechio`).

**NOTE THAT** where a speech act from the user cannot be comprehended into 
anything useful, we do *not* throw an exception from this stage; rather we pass
on a special semantic structure explaining what could not be understood. It is
for either the distribute or the search phase to short-circuit and deliver a
request for clarification back to the user if that's needed. 

## State

The comprehension stage must hold onto some state. This must include, but is not
limited to

1. The most recent person mentioned, of each gender;
2. The most recent group of people mentioned;
3. The most recent object mentioned;
4. The most recent place mentioned;

And so on. This allows us to substitute in for 'him', 'her', 'them', 'it', 'there'.

The state cache needs to be cleared down at the beginning of a new conversation 
with an NPC who was not within listening range of the previous conversation.