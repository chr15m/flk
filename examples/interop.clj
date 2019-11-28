;; Testing basic bash interop
;; from mal/bash/tests/stepA_mal.mal

(println (sh* "echo 7"))

(println (sh* "echo >&2 hello"))

(println (sh* "foo=8; echo ${foo}"))

(println (sh* "for x in a b c; do echo -n \"X${x}Y \"; done; echo"))

(println (sh* "for x in 1 2 3; do echo -n \"$((1+$x)) \"; done; echo"))
