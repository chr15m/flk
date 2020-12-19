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

(deftest "str-join"
  (testing "add separator, csv"
    (is (= (str-join "," "banana" "apples" "orange") "banana,apples,orange")))

  (testing "add separator, '; '"
    (is (= (str-join "; " "banana" "apples" "orange") "banana; apples; orange"))))

(def s "fleck!")

(deftest "str-subs"
  (testing "char 0 to 3"
    (is (= (str-subs s 0 3) "fle")))

  (testing "char 3 to 5"
    (is (= (str-subs s 3 5) "ck!")))

  (testing "from char 3"
    (is (= (str-subs s 3) "ck!")))

  (testing "reverse"
    (is (= (str-subs s -2) "k!"))))

(deftest "str-indent"
  (testing "indent with 2 spaces (default)"
    (is (= (str-indent s) "  fleck!")))

  (testing "indent with 1 spaces"
    (is (= (str-indent s 1) " fleck!")))

  (testing "indent with 4 spaces"
    (is (= (str-indent s 4) "    fleck!"))))

(def f (str "/tmp/patches-" (time-ms)))

(deftest "file-write"

  (testing "create a file"
    (is (= (file-write f "hello world!") nil)))

  (testing "checking file"
    (is (= (slurp f) "hello world!")))

  (testing "rewrite a file"
    (is (= (file-write f "rewrite!") nil)))

  (testing "checking file rewrited"
    (is (= (slurp f) "rewrite!")))

  (testing "append a file"
    (is (= (file-write f "append!" true) nil)))

  (testing "checking file rewrited"
    (is (= (slurp f) "rewrite!append!"))))

(deftest "unlink"
  (testing "remove file"
    (is (= (unlink f) nil)))

  (testing "check if file was removed"
    (is (= (file-exists? f) false))))

(deftest "unset"
  (testing "set var __FLECK"
    (is (= (env "__FLECK" "fleck!") "fleck!")))

  (testing "unset var __FLECK"
    (is (= (unset "__FLECK") nil)))

  (testing "check if var not exist"
    (is (= (env "__FLECK") nil))))

(def f (str "/tmp/patches-trap-" (time-ms)))

(deftest "trap!"
  (testing (str "touch a file at: " f)
    (is (= (sequential? (trap! (str "{ touch " f " ; }") "EXIT")) true))))

(deftest "println-stderr"
  (testing "print on stderror"
    (is (= (println-stderr "hello world!") nil))))

    ; there is no way to test exit without exit :(
(exit! 0)