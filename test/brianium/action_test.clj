(ns brianium.action-test
  (:require [clojure.test :refer :all]
            [clojure.test.check]
            [clojure.spec.alpha :as s]
            [clojure.spec.test.alpha :as st]
            [brianium.action :as action]
            [brianium.action.spec :as action-spec]))


(deftest test-make-action
  (testing "creating with all args"
    (let [act (action/make-action :test "payload" true)]
      (is (= act {:type :test :error? true :payload "payload"}))))
  (testing "creating with two args"
    (let [act (action/make-action :test "payload")]
      (is (= act {:type :test :error? false :payload "payload"}))))
  (testing "creating with one args"
    (let [act (action/make-action :test)]
      (is (= act {:type :test :error? false :payload {}})))))


(deftest test-make-error
  (let [act  (action/make-error :test (ex-info "oh no" {:message "zounds!"}))
        data (ex-data (:payload act))]
    (is (= data {:message "zounds!"}))))


(def gen-overrides {::action-spec/ex-info? #(s/gen #{(ex-info "test" {:data "hi"})})})


(deftest generated-tests
  (doseq [test-output (st/check
                        (st/enumerate-namespace 'brianium.action)
                        {:gen gen-overrides})]
    (testing (-> test-output :sym name)
      (is (true? (-> test-output :clojure.spec.test.check/ret :result)) test-output))))
