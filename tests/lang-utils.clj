(load-file-without-hashbang "tests/test.clj")
(load-file-without-hashbang "src/lang-utils.clj")

(deftest "or"
  (testing "first true"
    (is (= (or true false) true)))

  (testing "last true"
    (is (= (or false true) true)))

  (testing "multi list"
    (is (= (or false "or") "or")))

  (testing "false"
    (is (= (or false false) false)))

  (testing "nil"
    (is (= (or false nil) nil)))

  (testing "short circuit false"
    (is (= (or false 1 false "ola") 1))))
  

(deftest "and"
  (testing "first true"
    (is (= (and true false) false)))

  (testing "last true"
    (is (= (and false true) false)))

  (testing "multi list"
    (is (= (and false "or") false)))

  (testing "false"
    (is (= (and false false) false)))

  (testing "nil"
    (is (= (and false nil) false)))

  (testing "short circuit"
    (is (= (and false 1 false "ola") false)))

  (testing "true list"
    (is (= (and true true true) true)))

  (testing "true list mixed"
    (is (= (and 1 true "ok") "ok"))))

(def m (hashmap-list {:a 1 :b 2 :c 3}))

(deftest "hashmap-list"
  (testing "return sequence"
    (is (= (sequential? m) true)))

  (testing "return vector tuples"
    (is (= (map (fn [x] (count x)) m) (list 2 2 2))))

  (testing "key match"
    (is (= (keyword? (first (first m))) true)))

  (testing "value match"
    (is (= (number? (last (first m))) true))))

(deftest "merge"
  (testing "regular merge"
    (is (= (merge {:a 1} {:b 2}) {:a 1 :b 2})))

  (testing "replace merge"
    (is (= (merge {:a 1} {:a 2}) {:a 2}))))

(deftest "key-name"
  (testing "return key as string"
    (is (= (key-name :hello) "hello")))

  (testing "bypass value if a not keyworkd"
    (is (= (key-name 1) 1))))

(defn foo [x] (str x))

(deftest "call"
  (testing "return function from string"
    (is (= (fn? (call "foo")) true)))

  (testing "spawn call"
    (is (= ((call "foo") "x") "x"))))

(deftest "callable?"
  (testing "check if function exist"
    (is (= (callable? "foo") true)))

  (testing "check if function not exist"
    (is (= (callable? "bar") false))))

(def m {:a 1 :b {:x 2} :c {:x {:y 3}}})

(deftest "get-in"
  (testing "flat values"
    (is (= (get-in m [:a]) 1)))

  (testing "nested values"
    (is (= (get-in m [:c :x :y]) 3)))

  (testing "not found nested values"
    (is (= (get-in m [:c :x :z]) nil))))

(deftest "even?"
  (testing "positive"
    (is (= (even? 2) true)))

  (testing "zero"
    (is (= (even? 0) true)))

  (testing "negative"
    (is (= (even? -2) true)))

  (testing "odd"
    (is (= (even? 1) false))))

(deftest "odd?"
  (testing "positive"
    (is (= (odd? 1) true)))

  (testing "zero"
    (is (= (odd? 0) false)))

  (testing "negative"
    (is (= (odd? -1) true)))

  (testing "even"
    (is (= (odd? 2) false))))

(def f (str "/tmp/lang-utils-" (time-ms)))

(deftest "spit"

  (testing "create a file"
    (is (= (spit f "hello world!") nil)))

  (testing "checking file"
    (is (= (slurp f) "hello world!")))

  (testing "rewrite a file"
    (is (= (spit f "rewrite!") nil)))

  (testing "checking file rewrited"
    (is (= (slurp f) "rewrite!")))

  (testing "append a file"
    (is (= (spit f "append!" {:append true}) nil)))

  (testing "checking file rewrited"
    (is (= (slurp f) "rewrite!append!"))))