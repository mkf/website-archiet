(ns pl.torun.uni.mat.archiet.website.site
  (:require [hiccup.core :refer [html]]
            [hiccup.page :refer [html5 include-css]]))

(def overtitle "Michał K. Feiler, student WMiI UMK")

(def specials-order [:index])
(def the-specials {:index {:title nil :nav [:b "O mnie"] :url "/index.html"}})
(def list-of-specials (mapv #(assoc (% the-specials) :key %) specials-order))

(defn entry-nav-li [what-key {:keys [key url nav]}]
  (vector (if (= key what-key) :li.active :li)
          [:a {:href url} nav]))
(defn entries-nav [what-key entries]
  (mapv (partial entry-nav-li what-key) entries))

(defn specials-nav [what-key]
  (entries-nav what-key list-of-specials))

(defn the-nav [what-key entries]
  [:div.navmenu.navmenu-default.navmenu-fixed-left.offcanvas-sm
   [:a.navmenu-brand.visible-md.visible-lg {:href "/index.html"} "Michał K. Feiler"]
   (into [:ul.nav.navmenu-nav] (specials-nav what-key))
   (into [:ul.nav.navmenu-nav "Wpisy"] (entries-nav what-key entries))
   #_(into [:ul.nav.navmenu-nav]
         (into (specials-nav what-key)
               [:li.dropdown
                [:a.dropdown-toggle {:data-toggle "dropdown"}
                 "Wpisy" [:b.caret]]
                (into [:ul.dropdown-menu.navmenu-nav]
                      (entries-nav what-key entries))]))
   #_[:div.our-scrollable-nav
    (into [:ul.nav.navmenu-nav]
          (entries-nav what-key entries))]])

(comment defn navbar-list [whichtitle]
  (into [:ul.nav.navbar-nav]
        (keep #(when (or (= % whichtitle)
                         (not (get-in titles [% :hidden-in-nav])))
                 [(if (= % whichtitle) :li.active :li)
                  [:a {:href (get-in titles [% :url])} (get-in titles [% :nav])]])
              title-order)))

(defn base [title & contents]
  (html5
   (:lang "pl")
   [:head
    [:title (str title (when-not (nil? title) " :: ") overtitle)]
    [:meta {:charset "UTF-8"}]
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
    [:link {:rel "stylesheet"
            :href "https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css"
            :crossorigin "anonymous"}]
    [:link {:rel "stylesheet"
            :href "https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap-theme.min.css"
            :crossorigin "anonymous"}]
    [:link {:rel "stylesheet"
            :href "https://cdnjs.cloudflare.com/ajax/libs/jasny-bootstrap/3.1.3/css/jasny-bootstrap.min.css"
            :crossorigin "anonymous"}]
    [:script {:src "https://cdnjs.cloudflare.com/ajax/libs/jasny-bootstrap/3.1.3/js/jasny-bootstrap.min.js"}]
    (include-css "/styles.css")
    [:link {:rel "shortcut icon" :href "favicon.ico" :type "image/x-icon"}]
    [:link {:rel "icon" :type "image/png" :href "DSCF7629-bfab-fixed-squared-666px.png"}]
    [:link {:rel "icon" :href "favicon.ico" :type "image/x-icon"}]]
   (into [:body.website
          [:noscript [:iframe {:src "//www.googletagmanager.com/ns.html?id=GTM-KP8WW2"
                               :height "0" :width "0" :style "display:none;visibility:hidden"}]]
          [:script {:type "text/javascript" :src "naszgtm.js"}]]
         contents
         )))

(defn entry-html-filename [{:keys [short-filename]}]
  (str short-filename ".html"))

(def entries-dir "entries")
(defn entry-url [{:keys [short-filename]}]
  (str entries-dir "/" short-filename ".html"))

(defn index-top []
  [:div.about-me [:hr] "O mnie" [:hr]])

(defn entry-rend [{:keys [title short-filename content]}]
  [:div.whole-post
   [:h1 title]
   [:h2 short-filename]
   [:div.post-content content]])
(defn entries-rend [entries]
  (into [:div.entries] (map entry-rend entries)))

(defn index-renderer [entries]
  (base
   nil
   (let [_ (println (pr-str entries))
         entries-nav (mapv #(hash-map
                            :key (:short-filename %)
                            :nav (:title %)
                            :url (entry-url %)) entries)]
     [(the-nav :index entries-nav)
      (index-top)
      (entries-rend entries)]
     )))
