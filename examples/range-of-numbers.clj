(map read-string (str-split (sh* "for x in {1..100}; do echo ${x}; done") "\n"))
