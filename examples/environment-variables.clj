#!/usr/bin/env flk

; access a hash-map of all environment variables
; like {"SHELL" "/bin/bash"}
(println "Environment:" (pr-str (env)))

; get a single one
(println "SHELL:" (pr-str (get (env) "SHELL")))

; check a non-existent one
(println "GOOBER:" (pr-str (env "GOOBER")))

; set some environment variable (converts eveyrthing to string)
(println "GOOBER:" (pr-str (env "GOOBER" 42)))

; get some environment variable
(println "GOOBER:" (pr-str (env "GOOBER")))
