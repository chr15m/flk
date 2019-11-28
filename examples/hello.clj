(let [t 5]
  (println (str "Hello: " t)))

(println "This is" (sh* "hostname"))
(println "Uptime is" (sh* "uptime"))
