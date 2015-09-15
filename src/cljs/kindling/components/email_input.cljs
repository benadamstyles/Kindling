(ns kindling.components.email-input)

(js/console.log "hi")

(defn email-input [email]
      [:input {
               :type "email"
               :placeholder "your email address"
               :value @email ; @ is sugar for deref, which gets the value of an atom
               :on-change (fn [event]
                              (reset! email (-> event .-target .-value)))}])

(js/console.log email-input)
