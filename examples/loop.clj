;; loop through a vector
(println " -> map 1")
(map
  (fn [x]
    (println x))
  [3 1 4])

;; loop through a vector of strings
(println " -> map 2")
(map
  (fn [l]
    (println l))
  ["Hello" "cool" "ones"])

;; loop through a character sequence
(println " -> map 3")
(map
  (fn [l]
    (println l))
  (seq "Hello"))

;; add a bunch of numbers together
(println " -> reduce"
  (reduce (fn [a x] (+ a x))
          0
          (map (fn [y] (read-string y)) [1 2 3 4 5 6])))

;; recursive function call
(defn rec [x]
  (if
    (> x 5)
    (do (println x)
        (rec (- x 1)))))

(println " -> recurse")
(rec 10)
