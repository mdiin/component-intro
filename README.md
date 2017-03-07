# Introduction to Component and the reloaded workflow

This is my introduction to Stuart Sierra's Component library and the reloaded
workflow, given at the aarhus.clj meetup #3.

## Beginning

This is where we left off after mettup #2; a functioning webservice with an
internal storage to hold integers.

We will convert this example to use the component library in four steps, with a
bonus fifth step. Each step isolates one part of the service and plugs it into
what will become the final system.

## Step 1: In which we wrap the `run-server` function in a component

Since the `run-server` function is at the heart of our little webservice, this is
where we begin.

## Step 2: In which the `handler` routes are wrapped in a component

Because of the operations on the store found inside the call to `defroutes` we
know the routes have to be componentified before the store.

## Step 3: Where a store changes place

Finally we can integrate the store into our component system. This also concludes
the rewriting of our actual application logic.

## Step 4: Introducing custom profiles

In order to use all the work we did in the first three steps for anything useful,
we need to inject some code in our REPL. This is done by adding a file containing
the namespace `user` to the source path.

## Step 5: Frosting the cake with proper shutdown

When developing component systems, individual parts often acquire connections to
external services. Such connections may need to be terminated properly to let the
external service know your system is no longer using it.

