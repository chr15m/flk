<p align="center">
  <img src="docs/wordmark.svg?sanitize=true" alt="Fleck wordmark"><br/>
  Fleck is a Clojure-like LISP that runs wherever Bash does.
</p>

<p align="center"><img src="docs/screencast.svg?sanitize=true" alt="Fleck screencast"></p>

# Get it

```shell
curl -s https://chr15m.github.io/flk/flk > flk && chmod 755 flk
./flk
```

### [Examples](./examples) | [FAQ](#faq)

# What?

```
$ echo '(println "Hello world!") (println "Hostname:" (sh* "hostname")))' > example.clj
$ ./flk example.clj
Hello world!
Hostname: diziet
```

# Why?

Now you can use a humble LISP to do Bash things. Bash as a scripting language has many edges, but it is everywhere. This rounds off the edges.

# How?

Almost all of this code is from the [make-a-LISP](https://github.com/kanaka/mal/) project. All I've done is put together a simple Makefile to package it up into an easily deployable single-file bash script.

# FAQ

Think of this as homoiconic Bash rather than Clojure, and code as if you're in Bash.

### Will my favourite piece of Clojure run in this?

No, it's bash.

### Why can't I add more than 2 numbers together?

It's bash. Try invoking bc: `(sh* "bc <<< '1 + 2 + 3 + 4'")`

### Where are the floating point numbers?

It's bash. Try invoking bc: `(sh* "bc <<< '3 + 0.1415926'")`

### Why can't I iterate on a string?

It's bash. Try `(seq "somestring")`.
