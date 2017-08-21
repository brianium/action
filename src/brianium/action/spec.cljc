(ns brianium.action.spec
  (:require #?(:clj  [clojure.spec.alpha :as s]
               :cljs [cljs.spec.alpha :as s])
            [brianium.action :as action]))


(defn ex-info?
  [val]
  #?(:clj  (instance? clojure.lang.ExceptionInfo val)
     :cljs (instance? cljs.core.ExceptionInfo val)))


(s/def ::type keyword?)
(s/def ::payload any?)
(s/def ::error? boolean?)
(s/def ::meta any?)
(s/def ::ex-info? ex-info?)
(s/def ::action (s/keys :req-un [::type]
                        :opt-un [::payload ::error? ::meta]))


(s/fdef action/make-action
  :args (s/cat :action-type ::type
               :payload     (s/? ::payload)
               :error?      (s/? ::error?))
  :ret  ::action)


(s/fdef action/make-error
  :args (s/cat :action-type    ::type
               :exception-info ::ex-info?)
  :ret  ::action)


(s/fdef action/error?
  :args (s/cat :action ::action)
  :ret  ::error?)
