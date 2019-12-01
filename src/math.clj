;; wrap dc to get floating point math
(defn dc [op args]
  (let [calcstr (str "dc <<< '"
                     (first args)
                     (apply str (map
                                  (fn [a] (str " " a " " op))
                                  (rest args)))
                     " p'")]
    (read-string
      (sh* calcstr))))
