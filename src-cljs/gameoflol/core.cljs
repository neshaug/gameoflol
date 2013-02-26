(ns gameoflol.core
    (:require
     [clojure.browser.repl :as repl]
     [jayq.core :refer [$ append attr on off document-ready]]))

(defn neighbours
  [[x y]]
  (for [dx [-1 0 1] dy [-1 0 1] :when (not= 0 dx dy)]
       [(+ dx x) (+ dy y)]))

(defn step
  [cells]
  (set (for [[loc n] (frequencies (mapcat neighbours cells))
             :when (or (= n 3) (and (= n 2) (cells loc)))]
         loc)))

(defn init-canvas
  [w h]
  (let [canvas (first ($ "<canvas></canvas>"))]
    (attr ($ canvas) "width" w)
    (attr ($ canvas) "height" h)
    (append ($ :body) canvas)
    canvas))

(def surface
     ((fn [w h]
       [(.getContext (init-canvas w h) "2d") w h]) 640 480))

(defn fill-rect
     [[ctx] [x y w h] [r g b]]
     (set! (.-fillStyle ctx) 
           (str "rgb(" r "," g "," b ")"))
     (.fillRect ctx x y w h))

(defn clear
  [[ctx w h]]
  (fill-rect [ctx] [0 0 w h] [0 0 0]))

(defn locs-printer
  [col]
  (if (empty? col)
      (js/console.log "no locs to print")
      (doseq [s (map (fn [[x y]] (str x " " y)) col)]
             (js/console.log s))))

(defn draw-locs
  [surface locs scale]
  (doseq [[x y] locs]
    (fill-rect surface [(* x scale) (* y scale) scale scale] [255 255 255])))

(defn tick
  [surface locs]
  (clear surface)
  (draw-locs surface locs 10)
  (js/setTimeout (fn [] (tick surface (step locs))) 100))

(defn ^:export connect
  []
  (repl/connect "http://localhost:9000/repl"))