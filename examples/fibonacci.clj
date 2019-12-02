; from gherkin: https://github.com/alandipert/gherkin/blob/master/fib.gk
      
(defn fib* (a b n)
  (if (> n 0)
    (fib* b (+ a b) (- n 1))
    a))

(def fib (partial fib* 0 1))
 
(println (fib 60))
