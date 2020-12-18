(load-file-without-hashbang "tests/test.clj")

(deftest "range"
  (testing "single parameter"
    (is (= (range 5) (list 0 1 2 3 4))))

  (testing "dual parameter"
    (is (= (range 5 10) (list 5 6 7 8 9))))

  (testing "step parameter"
    (is (= (range 1 10 2) (list 1 3 5 7 9))))

  (testing "reverse list"
    (is (= (range 5 0 -1) (list 5 4 3 2 1))))

  (testing "negative list"
  (is (= (range -5 1) (list -5 -4 -3 -2 -1 0)))))


(deftest "mod"
  (testing "even"
    (is (= (mod 10 2) 0)))

  (testing "four"
   (is (= (mod 10 6) 4))))

(deftest "file-exists"
  (testing "exists: /etc/hosts"
    (is (= (file-exists? "/etc/hosts") true)))

  (testing "not exists: /etc/not-exists"
    (is (= (file-exists? "/etc/not-exists") false))))

; filter
(def i (range 1 10))

(deftest "filter"
  (testing "> 5"
    (is (= (filter (fn [x] (> x 5)) i) (range 6 10))))

  (testing "even"
    (is (= (filter (fn [x] (= 0 (mod x 2))) i) (range 2 10 2)))))

; pmap
(defn fib* [a b n]
  (if (> n 0)
    (fib* b (+ a b) (- n 1))
    a))

(def fib (partial fib* 0 1))

; calc fast as possible
(def fc (pmap (fn [x] (fib x)) (range 30)))

(deftest "pmap"
  (testing "ten \"thread\""
    (is (= (pmap (fn [x] (do (sh! "sleep 1") x)) (range 10)) (range 10))))

  (testing "single fib"
    (is (= (map (fn [x] (fib x)) (range 30)) fc)))

  (testing "parallel fib"
    (is (= (pmap (fn [x] (fib x)) (range 30)) fc))))

