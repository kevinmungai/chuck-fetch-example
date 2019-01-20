(ns chuck-fetch-example.views
  (:require
   [re-frame.core :as re-frame]
   [chuck-fetch-example.subs :as subs]
   [chuck-fetch-example.events :as events]
   ))


;; home

(defn home-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1 (str "Hello from " @name ". This is the Home Page.")]

     [:div
      [:a {:href "#/about"}
       "go to About Page"]]
     ]))


;; about

(defn about-panel []
  [:div
   [:h1 "This is the About Page."]

   [:div
    [:a {:href "#/"}
     "go to Home Page"]]])


;; chuck panel
(defn chuck-panel []
  (let [quote (re-frame/subscribe [::subs/quote])]
    (fn []
      [:div
       [:h1 "Welcome to Chuck Norris Panel"]
       [:button {:on-click #(re-frame/dispatch [::events/get-chuck-quote])}
        "Get Chuck Quote"]
       [:h3 @quote]])))


;; main

(defn- panels [panel-name]
  (case panel-name
    :home-panel [home-panel]
    :about-panel [about-panel]
    :chuck-panel [chuck-panel]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [show-panel @active-panel]))
