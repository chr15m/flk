(println "split:" (pr-str (str-split "these:are:some:words:yay" ":")))

(println "replace:" (pr-str (str-replace "hello goodbye fufwf yes" "bye" "[day]")))

(println "replace:" (pr-str (str-replace "hello good'bye fufwf yes" "bye" "[day]")))

(println "replace:" (pr-str (str-replace "hello good'bye \"fufwf yes" "bye" "[day]")))

(println "replace:" (pr-str (str-replace "hello goodbye fufwf yes" "bye" "/[day]*!#$#")))

(println "str-upper-case:" (pr-str (str-upper-case "foo")))

(println "str-lower-case:" (pr-str (str-lower-case "BAR")))

(println "str-capitalize:" (pr-str (str-capitalize "foo BAR")))