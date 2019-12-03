(def! foldr (fn* [f init xs]
  (if (empty? xs) init (f (first xs) (foldr f init (rest xs))))))

(def! reduce (fn* (f init xs)
  (if (empty? xs) init (reduce f (f init (first xs)) (rest xs)))))
