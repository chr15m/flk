(println "split:" (pr-str (str-split "these:are:some:words:yay" ":")))

(println "replace:" (pr-str (str-replace "hello goodbye fufwf yes" "bye" "[day]")))

(println "replace:" (pr-str (str-replace "hello good'bye fufwf yes" "bye" "[day]")))

(println "replace:" (pr-str (str-replace "hello good'bye \"fufwf yes" "bye" "[day]")))

(println "replace:" (pr-str (str-replace "hello goodbye fufwf yes" "bye" "/[day]*!#$#")))

