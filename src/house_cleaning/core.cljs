(ns house-cleaning.core
  (:require [rum.core :as rum]
            [reitit.frontend :as rf]
            [reitit.frontend.easy :as rfe]
            [reitit.coercion.spec :as rss]
            [spec-tools.data-spec :as ds]
            [fipp.edn :as fedn]))

(enable-console-print!)

(defonce app-state (atom {:text "Hello world!"}))
(defonce url (atom nil))

(rum/defc admin-page []
          [:div
           [:h1 "Admin"]])

(rum/defc dashboard-page []
          [:div
           [:h1 "Dashboard"]])
;
(def routes
  [["/admin"
    {:name ::admin
     :view admin-page}]

   ["/dashboard"
    {:name ::dashboard
     :view dashboard-page}]])

(defonce counter (atom 0))
;
; (js/setInterval
;  #(swap! counter inc)
;  1000)
;
; (js/setInterval
;  #(println @counter)
;  1000)
;

(rum/defc simple-component [] [:div "Simple component"])

(rum/defc current-page < rum/reactive []
          [:div
           [:ul
            [:li [:a {:href (rfe/href ::admin)} "Admin"]]
            [:li [:a {:href (rfe/href ::dashboard)} "Dashboard"]]]
           (if @url
             (let [view (:view (:data (rum/react url)))]
               (view)))])
           ; [:pre (with-out-str (fedn/pprint (get-in @url [:data :view])))]])

;

(defn init! []
  (rfe/start!
   (rf/router routes {:data {:coercion rss/coercion}})
   (fn [m] (reset! url m))
    ;; set to false to enable HistoryAPI
   {:use-fragment true})
  (rum/mount (current-page) (. js/document (getElementById "app"))))

(init!)

;

(defn on-js-reload [])
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
