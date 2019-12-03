#!/usr/bin/env flk

(if (= (count *ARGV*) 0)
  (println "No arguments supplied.")
  (do
    (println "Arguments:")
    (map
      (fn [a]
        (println " * " a))
      *ARGV*)))
