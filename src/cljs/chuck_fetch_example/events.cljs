(ns chuck-fetch-example.events
  (:require
   [re-frame.core :as re-frame]
   [chuck-fetch-example.db :as db]
   [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]
   ))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
            db/default-db))

(re-frame/reg-event-db
 ::set-active-panel
 (fn-traced [db [_ active-panel]]
            (assoc db :active-panel active-panel)))

(re-frame/reg-event-fx
 ::get-chuck-quote
 (fn-traced [_ _]
            {:fetcher "https://api.chucknorris.io/jokes/random"}))

(re-frame/reg-fx
 :fetcher
 (fn [url]
   (-> (js/fetch url)
       (.then #(.json %))
       (.then #(re-frame/dispatch [::chuck-quote (:value (js->clj % :keywordize-keys true))])))))

(re-frame/reg-event-db
 ::chuck-quote
 (fn-traced [db [_ quote]]
            (assoc db :quote quote)))
