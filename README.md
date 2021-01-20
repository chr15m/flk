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

### [Examples](./examples) | [Reference](#reference) | [Contributing](#contributing) | [FAQ](#faq) | [make-a-lisp](https://github.com/kanaka/mal)

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

`def!` | `defmacro!` | `if` | `do` | `fn*` | `try*` | `sh*` | `let*` | `quote` | `quasiquote` | `macroexpand` | `type` | `=` | `throw` | `nil?` | `true?` | `false?` | `string?` | `symbol` | `symbol?` | `keyword` | `keyword?` | `number?` | `fn?` | `macro?` | `pr-str` | `str` | `prn` | `println` | `readline` | `read-string` | `slurp` | `<` | `<=` | `>` | `>=` | `+` | `-` | `*` | `/` | `time-ms` | `list` | `list?` | `vector` | `vector?` | `hash-map` | `map?` | `assoc` | `dissoc` | `get` | `contains?` | `keys` | `vals` | `sequential?` | `cons` | `concat` | `nth` | `first` | `last` | `rest` | `empty?` | `count` | `apply` | `map` | `conj` | `seq` | `with-meta` | `meta` | `atom` | `atom?` | `deref` | `reset!` | `swap!`

 * `*ARGV*` - list of arguments passed on the command line.

## Aliases

These are wrappers around the limited make-a-lisp versions and are much more limited than the Clojure equivalents.

`let` | `when` | `def` | `fn` | `defn`

## Mal extras

These functions are pulled from a selection of `mal/lib/*.mal`.

`partial` | `inc` | `dec` | `zero` | `identity` | `reduce` | `foldr`

## Fleck extras

These functions are hand crafted Fleck specials designed to make common shell scripting tasks easier.

 * `(str-replace STRING FIND REPLACE)` - Replace all occurrences of the string `FIND` in `STRING` with the string `REPLACE`.
 * `(str-split STRING SPLIT-CHARACTER)` - Split `STRING` into a list of strings on the single characters `SPLIT-CHARACTER`.
 * `(str-pos HAYSTACK NEEDLE)` - Returns the position of string `NEEDLE` in string `HAYSTACK` or -1 if not found.
 * `(str-upper-case STRING)` - Converts string to all upper-case.
 * `(str-lower-case STRING)` - Converts string to all lower-case.
 * `(str-capitalize STRING)` - Converts first character of the string to upper-case, all other characters to lower-case.
 * `(dc OPERATOR ARRAY-OF-NUMBERS)` - Wraps the `dc` command to do decimal math. E.g. `(dc '+ [1 2 3])` yeilds `6`.
 * `(env [KEY] [VALUE])` - Returns a `hash-map` of environment variables. Returns the value of `KEY` if present. Sets the value of `KEY` to `VAL` if the latter is present.
 * `(sh! COMMAND ARGS)` - Run a bash command with arguments in a subshell. Returns `[stdout stderr return-code]` from the resulting call.
 * `(sh-env COMMAND)` - Run a bash command string in the current shell, modifying current env, and return the stdout result (useful for `export`, `source` etc.).

## Interop

 * `(env [KEY] [VALUE])` - See above section.
 * `(sh! COMMAND [ARGS])` - See above section.
 * `(sh-env COMMAND)` - See above section.
 * `(sh* COMMAND)` - Run arbitrary bash strings in a subshell and return the stdout result.

For examples of writing your own Fleck functions in Bash see [src/extras.sh](./src/extras.sh).
Functions should set the special return value `r` and use [mal](https://github.com/kanaka/mal) type casting functions like `_string` to wrap the result in a reference.
Internal Fleck functions such as `_string` automatically do this and can be used bare.
Use `_fref` to make your function available to the Fleck namespace e.g. `_fref "my-bash-function" _my_bash_function`.

# Compile

To compile `flk` itself run `make`. This combines the original `mal` scripts with various bash and flk functions into a single binary.

You can make a pure bash script from your Fleck script by bundling your script and Fleck together into a new script.

Say you have a Fleck script called `wow.clj`, you can bundle it as follows:

```
make DEST=wow INSERT=./wow.clj NOREPL=1
```

This will produce a new standalone script called `wow` with Fleck + `wow.clj` bundled together.

When you run `wow` the embedded `wow.clj` will be run by the embedded Fleck.

# Contributing

Flk is built from the [mal](https://github.com/kanaka/mal) sources and uses its test framework.

To contribute please follow these guidelines:

 * Add any new bash functions to [extras.sh](./src/extras.sh) as this is merged into `mal` at build time.
 * Add new flk functions into `src` in a new `.clj` file, and then add the file name to the `LOCALMALS` list in the [Makefile](./Makefile) so it gets included in the build.
 * Any changes, bugfixes, etc. to `mal` itself should be submitted upstream.
 * Document any new functions in this README.
 * Put unit tests for new functions in a `.mal` file in the `tests` folder, and to the [Makefile](./Makefile) `test` clause.

# FAQ

Think of this as homoiconic Bash rather than Clojure, and code as if you're in Bash.

### Will my favourite piece of Clojure run in this?

No, it's bash.

Some subset of Clojure-like code will run.
See the [documentation](#reference) and [examples](./examples).

### How do I access command line arguments?

Use the special global list `*ARGV*`.

### How do I access and modify environment variables?

Check the [`(env)` function above](#fleck-extras).
See also [examples/environment-variables.clj](./examples/environment-variables.clj).

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

`dc` is set to keep four fractional digits in its results.

### How do I cast a string to a number?

Try `(read-str "42")` but also Bash doesn't care and `(+ "1" 1)` will yeild `2`.

### Why can't I iterate on a string?

Try `(seq "somestring")`.

### How do I do destructuring?

You can't.

### How do I use a key/`hash-map` as a function in lookups?

You can't. You'll get an error with something like `(:a {:a 12})` or `({:a 12} :a)`.

Instead you must use `get` like this: `(get {:a 12} :a)`.

### Can I use anything as a `hash-map` key?

Seems unlikely. Better stick to strings.

### This is even slower than Python!

Yes.

PS That is not actually a question.

### Haven't I seen this before somewhere?

You're probably thinking of [Gherkin, the original Clojure-like LISP in Bash by Alan Dipert](https://github.com/alandipert/gherkin/).
Gherkin helped kick off the [make-a-lisp](https://github.com/kanaka/mal/) revolution.
You might also be thinking of [babashka](https://github.com/borkdude/babashka) which is a bare-metal solution using real Clojure.

### Why is it called Fleck?

At `36k` and running on any machine with Bash 4, the name seemed appropriate.

```
 fleck

    n. A tiny mark or spot.
    n. A small bit or flake.
```
