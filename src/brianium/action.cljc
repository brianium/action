(ns brianium.action)


(defn make-action
  "Creates a new action"
  ([action-type payload error?]
   {:type action-type
    :payload payload
    :error? error?})
  ([action-type payload]
   (make-action action-type payload false))
  ([action-type]
   (make-action action-type {} false)))


(defn make-error
  "Creates an error action. Sets the error flag to true and expects
  the payload to be an instance of ExceptionInfo"
  [action-type exception-info]
  (make-action action-type exception-info true))


(defn error?
  "Check if the given action represents an error"
  [action]
  (boolean (:error? action)))
