; super small framework for unit test

(defn deftest [title & l]
  (let [total (count l)
        success (reduce (fn [a b] (+ a b)) 0 (map (fn [x] (if (= (get x :result) true) 1 0)) l))
        total-time (reduce (fn [a b] (+ a b)) 0 (map (fn [x] (get x :time-elapsed)) l))]
    (do
      (println "[" title "]" (str success "/" total) ":total-time" total-time "ms\n")
      (if (= success total) l (exit! 1)))))

(defmacro! testing
  (fn* [name body]
       `(let [start (time-ms)
              result ~body
              end (time-ms)
              time-elapsed (- end start)]
          (do
            (cond
              (= result true)  (println "\033[32m[   ok   ]\033[0m " ~name ":time" time-elapsed "ms")
              (= result false) (println "\033[31m[ failed ]\033[0m " ~name ":time" time-elapsed "ms" "\n  " '~body))
            (hash-map :name ~name :start start :result result :end end :time-elapsed time-elapsed)))))

(defn is [comparison]
  (= comparison true))