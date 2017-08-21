# action

[![Clojars Project](https://img.shields.io/clojars/v/brianium/action.svg)](https://clojars.org/brianium/action)

A simple message format modeled after [flux standard actions](https://github.com/acdlite/flux-standard-action).

For Clojure and ClojureScript.

## Usage

The spec should sort out what an action is :)

```clojure
(s/def ::type keyword?)
(s/def ::payload any?)
(s/def ::error? boolean?)
(s/def ::meta any?)
(s/def ::action (s/keys :req-un [::type]
                        :opt-un [::payload ::error? ::meta]))
```

An example of an action:

```clojure
{:type    :create/user
 :error?  false
 :paylaod {:name "Brian" :title "Computer"}}
```

A couple of functions are bundled to facilitate use of this format.

```clojure
(require '[brianium.action :refer [make-action make-error]])

(make-action :create/user {:name "Brian" :title "Computer"} false)
;; => {:type :create/user
       :payload {:name "Brian" :title "Computer"}
	   :error? false}
	
(make-action :create/user {:name "Brian" :title "Computer"})
;; => {:type :create/user
       :payload {:name "Brian" :title "Computer"}
	   :error? false}
	   
(make-action :cache-clear)
;; => {:type :cache-clear
       :payload {}
	   :error? false}
	   
(make-error :create/user (ex-info "User creation error" {:reason "email exists"}))
;; => {:type :create/user
       :payload ,, (IExceptionInfo instance)
	   :error? true}
```

## Testing

```
lein test
```
