;; loop through a vector
(map
  (fn [x]
    (println x))
  [3 1 4])

;; loop through a vector of strings
(map
  (fn [l]
    (println l))
  ["Hello" "cool" "ones"])

;; loop through a character sequence
(map
  (fn [l]
    (println l))
  (seq "Hello"))

;; recursive function call
(defn rec [x]
  (if
    (> x 5)
    (do (println x)
        (rec (- x 1)))))

(rec 10)
