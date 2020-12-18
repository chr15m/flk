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
