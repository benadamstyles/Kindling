(ns kindling.core
    (:require [reagent.core :as reagent :refer [atom]]
              [reagent.session :as session]
              [secretary.core :as secretary :include-macros true]
              [goog.events :as events]
              [goog.history.EventType :as EventType]
              [clojure.string :as str]
              [kindling.components.email-input :as new-email-input :refer [email-input]]

              )
    (:import goog.History))

;; -------------------------
;; Utility functions
(defn remove-whitespace [string]
  (str/replace string #"\s+" ""))

;; -------------------------
(defn paste-box [pasted]
  [:textarea {
    :rows 5
    :placeholder "paste your text here"
    :value @pasted
    :on-change (fn [event]
      (reset! pasted (-> event .-target .-value)))}])
      ; `-> event` is like _.chain(event)
      ; `.-` is a way to access JS properties

;; -------------------------
;; Views
(defn home-page []
  (let [
    email (atom "test@example.com")
    pasted (atom "Lorem ipsum")]

    (fn []
      [:div
        [:h1 "Welcome to kindling"]
        [:p (str "Your email address is " @email)]
        [email-input email]
        [paste-box pasted]
        [:p (str
          "Your pasted text is "
          (.-length @pasted)
          " characters long ("
          (.-length (remove-whitespace @pasted))
          " excluding whitespace)")]
        [:button {:id "submit-button"} (str "Send!")]
        [:div [:a {:href "#/about"} "go to about page"]]])))

(defn about-page []
  [:div
    [:h2 "About kindling"]
    [:div [:a {:href "#/"} "go to the home page"]]])

(defn current-page []
  [:div [(session/get :current-page)]])

;; -------------------------
;; Routes
(secretary/set-config! :prefix "#")

(secretary/defroute "/" []
  (session/put! :current-page #'home-page))

(secretary/defroute "/about" []
  (session/put! :current-page #'about-page))

;; -------------------------
;; History
;; must be called after routes have been defined
(defn hook-browser-navigation! []
  (doto (History.)
    (events/listen
     EventType/NAVIGATE
     (fn [event]
       (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

;; -------------------------
;; Initialize app
(defn mount-root []
  (reagent/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (hook-browser-navigation!)
  (mount-root))
