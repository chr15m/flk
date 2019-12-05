#!/usr/bin/env flk

; a Fleck script which bootstraps the currently
; running version of Fleck onto a server
(if (< (count *ARGV*) 1)
  (println "Usage: bootstrap.clj USER@SERVER:~/dir")
  (let [me (sh* "echo $0")
        remote (first *ARGV*)
        cmd (str "scp " me " " remote)]
    (println cmd)
    (println (sh* (str cmd " 2>&1")))))
