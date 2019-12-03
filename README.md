<p align="center">
  <img src="docs/wordmark.svg?sanitize=true" alt="Fleck wordmark"><br/>
  Fleck is a Clojure-like LISP that runs wherever Bash is.
</p>

<p align="center"><img src="docs/screencast.svg?sanitize=true" alt="Fleck screencast"></p>

# Get it

```shell
curl -s https://chr15m.github.io/flk/flk > flk && chmod 755 flk
./flk
```

### [Examples](./examples) | [Reference](#reference) | [FAQ](#faq) | [make-a-lisp](https://github.com/kanaka/mal)

# What?

```
$ echo '(println "Hello world!") (println "Hostname:" (sh* "hostname")))' > example.clj
$ ./flk example.clj
Hello world!
Hostname: diziet
```

# Why?

Now you can use a humble LISP to do Bash things.
Bash as a scripting language has many edges, but it is everywhere.
Fleck attempts to round off the edges.

Fleck runs on Bash 4 and higher.

# How?

Almost all of this code is from the [make-a-LISP](https://github.com/kanaka/mal/) project. All I've done is put together a simple Makefile to package it up into an easily deployable single-file bash script.

# Reference

A list of variables, macros and functions that are present in Fleck.

## Built-ins

This is the set of built-ins from the make-a-lisp project.
These more or less work but are generally more limited in functionality than their Clojure equivalents.
For example the addition function `(+)` can only add two integers at a time.

`def!` | `defmacro!` | `if` | `do` | `fn*` | `try*` | `sh*` | `let*` | `quote` | `quasiquote` | `macroexpand` | `type` | `=` | `throw` | `nil?` | `true?` | `false?` | `string?` | `symbol` | `symbol?` | `keyword` | `keyword?` | `number?` | `fn?` | `macro?` | `pr-str` | `str` | `prn` | `println` | `readline` | `read-string` | `slurp` | `<` | `<=` | `>` | `>=` | `+` | `-` | `*` | `/` | `time-ms` | `list` | `list?` | `vector` | `vector?` | `hash-map` | `map?` | `assoc` | `dissoc` | `get` | `contains?` | `keys` | `vals` | `sequential?` | `cons` | `concat` | `nth` | `first` | `rest` | `empty?` | `count` | `apply` | `map` | `conj` | `seq` | `with-meta` | `meta` | `atom` | `atom?` | `deref` | `reset!` | `swap!`

 * `*ARGV*` - list of arguments passed on the command line.

## Aliases

These are wrappers around the limited make-a-lisp versions and are much more limited than the Clojure equivalents.

`let` | `when` | `def` | `fn` | `defn`

## Mal extras

These functions are pulled from a selection of `mal/lib/*.mal`.

`partial` | `inc` | `dec` | `zero` | `identity`

## Fleck extras

These functions are hand crafted Fleck specials designed to make common shell scripting tasks easier.

 * `(str-replace STRING FIND REPLACE)` - Replace all occurrences of the string `FIND` in `STRING` with the string `REPLACE`.
 * `(str-split STRING SPLIT-CHARACTER)` - Split `STRING` into a list of strings on the single characters `SPLIT-CHARACTER`.
 * `(dc OPERATOR ARRAY-OF-NUMBERS)` - Wraps the `dc` command to do decimal math. E.g. `(dc '+ [1 2 3])` yeilds `6`.
 * `(env [KEY] [VALUE])` - Returns a `hash-map` of environment variables. Returns the value of `KEY` if present. Sets the value of `KEY` to `VAL` if the latter is present.

## Interop

 * `(sh* COMMAND)` - Run arbitrary bash strings and return the stdout result.
 * `(env [KEY] [VALUE])` - See above section.

For examples of writing your own Fleck functions in Bash see [src/extras.sh](./src/extras.sh).
Functions should set the special return value `r` and use Fleck type casting functions like `_string` to wrap the result in a reference.
Internal Fleck functions such as `_string` automatically do this and can be used bare.
Use `_fref` to make your function available to the Fleck namespace e.g. `_fref "my-bash-function" _my_bash_function`.

# Compile

You can make a pure bash script from your Fleck script by bundling your script and Fleck together into a new script.

Say you have a Fleck script called `wow.clj`, you can bundle it as follows:

```
make DEST=wow INSERT=./wow.clj NOREPL=1
```

This will produce a new standalone script called `wow` with Fleck + `wow.clj` bundled together.

When you run `wow` the embedded `wow.clj` will be run by the embedded Fleck.

# FAQ

Think of this as homoiconic Bash rather than Clojure, and code as if you're in Bash.

### Will my favourite piece of Clojure run in this?

No, it's bash.

Some subset of Clojure-like code will run. See the [documentation](#reference) and [examples](./examples).

### How do I access command line arguments?

Use the special global list `*ARGV*`.

### How do I access and modify environment variables?

Check the [`(env)` function above](#fleck-extras). See also [examples/environment-variables.clj](./examples/environment-variables.clj).

### How can I execute a one-liner of Fleck code?

Either of these methods will work:

```shell
flk <<< '(println "hi")'
echo '(println "hi")' | flk
```

### Why can't I add more than 2 numbers together?

It's bash. Try the `dc` function: `(dc '+ [1 2 3 4])`

### Where are the floating point numbers?

It's bash. Try the `dc` function for decimals: `(dc '* [8.2 3.5])`

Note that dc's divide seems to only output integers. ¯\\_(ツ)_/¯

### Why can't I iterate on a string?

Try `(seq "somestring")`.

### How do I do destructuring?

You can't.

### Can I use anything as a `hash-map` key?

Seems unlikely. Better stick to strings.

### Why is it called Fleck?

At `36k` and running on any machine with Bash 4, the name seemed appropriate.

```
 fleck

    n. A tiny mark or spot.
    n. A small bit or flake.
```
