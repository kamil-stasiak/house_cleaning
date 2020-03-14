(ns house-cleaning.core
  (:require [rum.core :as rum]))

(enable-console-print!)

(defonce app-state (atom {:text "Hello world!"}))

(rum/defc hello-world []
          [:div
           [:h1 (:text @app-state)]
           [:h3 "My message"]])

(rum/mount (hello-world)
           (. js/document (getElementById "app")))

(defn on-js-reload [])
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
