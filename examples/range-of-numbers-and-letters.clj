(let [letters (seq "abcdefghijklmnopqrstuvwxyz")
      numbers (str-split (sh* "echo {0..25}") " ")]
  (map (fn [x]
         (println x (nth letters x)))
       numbers))
