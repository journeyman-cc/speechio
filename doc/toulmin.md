# The Toulmin Schema

Use of the Toulmin schema is not an expression of admiration of Toulmin as a 
philosopher generally. The schema is good irrespective of the arguments it 
was designed to support.

Basically the schema comprises the following elements

* **Data**, a proposition about the world;
* **Warrant**, a reason for believing that proposition true;
* **Backing**, a general rule which associates the warrant with the data;
* **Claim**, a proposition claimed to be consequent on the data;
* **Qualifier**, a qualification of the claim (e.g. 'probably'); and finally
* **Rebuttal**, evidence casting doubt on the claim.

This can be diagrammed:

```
data -------------> So, qualifier, claim
          |          |
          |          |
        Since       Unless
        warrant     rebuttal
          |
          |
    On account of
       backing
```

*(after Toulmin, S. (2003). The Uses of Argument (2nd ed.). Cambridge: Cambridge University Press. doi:10.1017/CBO9780511840005)*

Note that in this, warrant, backing, claim and rebuttal can all be the claims 
of further Toulmin structures.

For our purposes, a claim shall be a tuple comprising a verb and several optionally
qualified nouns; this allows us to do a form of predicate caluculus on claims.
The full detail of this implementation requires work.

Toulmin structures are not necessarily -- indeed may not be -- the most convenient representation
for the [comprehend](comprehend.html) stage of the pipeline; but they are likely 
to be the structures used in search and filter phases and thus delivered back to
[compose](compose.html), and so if queries can be represented as partially 
completed toulmin structures.