(ns pl.torun.uni.mat.archiet.website.site
  (:require [hiccup.core :refer [html]]
            [hiccup.page :refer [html5 include-css]]
            [pl.torun.uni.mat.archiet.website.index :as index-index]
            [clj-time.core :as clj-time]
            [clj-time.coerce :as clj-time-coerce]
            [clj-time.format :as clj-time-format]
            [clojure.string :as str]))

(def overtitle "Michał K. Feiler, student WMiI UMK")

(def ^:dynamic root "/~archiet/")

(def specials-order [:index])
(def the-specials {:index {:title nil :nav [:b "Strona główna"] :url (str root "index.html")}})
(def list-of-specials (mapv #(assoc (% the-specials) :key %) specials-order))

(defn reformat-datestr [date-str initial-format final-format]
  (let [date (clj-time-format/parse (clj-time-format/formatter initial-format) date-str)]
        (clj-time-format/unparse (clj-time-format/formatter final-format) date)))

(defn reformat-date [date final-format]
  (clj-time-format/unparse (clj-time-format/formatter final-format) (clj-time-coerce/from-date date)))

(defn str-to-date [string]
  (clj-time-coerce/to-date (clj-time-format/parse string)))

(defn entry-nav-li [what-key {:keys [key url nav]}]
  (vector (if (= key what-key) :li.active :li)
          [:a {:href url} nav]))
(defn entries-nav [what-key entries]
  (mapv (partial entry-nav-li what-key) entries))

(defn specials-nav [what-key]
  (entries-nav what-key list-of-specials))

(defn minime []
  [:img {:src (str root "DSCF7629-bfab-fixed-squared-666px.png") :height 30}])

(defn the-nav [what-key entries]
  [:div.navmenu.navmenu-default.navmenu-fixed-left.offcanvas-sm
   [:a.navmenu-brand.visible-md.visible-lg {:href (str root "index.html")} (minime) "Michał Krzysztof Feiler"]
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

(defn mininavbar []
  [:div.navbar.navbar-default.navbar-fixed-top.hidden-md.hidden-lg
   [:button.navbar-toggle {:type :button :data-toggle :offcanvas :data-target ".navmenu"}
    [:span.icon-bar] [:span.icon-bar] [:span.icon-bar]]
   [:a.navbar-brand {:href (str root "index.html")} (minime)]])

(defn base [title add-head contents]
  (html5
   (:lang "pl")
   (into
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
     (include-css "/~archiet/styles.css")
     [:link {:rel "shortcut icon" :href (str root "favicon.ico") :type "image/x-icon"}]
     [:link {:rel "icon" :type "image/png" :href (str root "DSCF7629-bfab-fixed-squared-666px.png")}]
     [:link {:rel "icon" :href (str root "favicon.ico") :type "image/x-icon"}]]
    add-head)
   (into [:body
          #_[:noscript [:iframe {:src "//www.googletagmanager.com/ns.html?id=GTM-KP8WW2"
                               :height "0" :width "0" :style "display:none;visibility:hidden"}]]
          #_[:script {:type "text/javascript" :src (str root "naszgtm.js")}]]
         (into
          (vec contents)
          [[:script {:src "https://code.jquery.com/jquery-1.10.2.min.js"}]
           [:script {:src "https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"}]
           [:script {:src "https://cdnjs.cloudflare.com/ajax/libs/jasny-bootstrap/3.1.3/js/jasny-bootstrap.min.js"}]
            ])
         )))
(defn index-base [title contents]
  (base
   title
   []
   contents))

(defn post-base [title
                 {:keys [date-created date-modified date-published]}
                 contents])

(defn entry-html-filename [{:keys [short-filename]}]
  (str short-filename ".html"))

(def entries-dir "entries")
(defn entry-url [{:keys [short-filename]}]
  (str root entries-dir "/" short-filename ".html"))

(def tags-dir "tags")
(defn dir-path [name] (str root name))

(def index-top index-index/render)

(defn a-href-tag [tag]
  [:a {:href (str root tags-dir "/" %)} %])

(defn entry-rend [{:keys [title date-published content ttr tags] :as entry}]
  [:div.whole-post [:hr]
   [:h2.blogpost [:a {:href (entry-url entry)} title]]
   [:p {:align :right} [:font {:color "#777"}
                        (str "Reading time: " ttr " mins. "
                         "Date published: "date-published)]]
   [:div.post-content content]
   [:p (into [:font {:color "#777"} "Tags: "]
             (->> tags (map a-href-tag) (interpose ", ")))]
   [:hr]])
(defn entries-rend [entries]
  (into [:div.container] (map entry-rend entries)))

(defn entry-nav-map [{:keys [date-published title short-filename] :as entry}]
  {:key short-filename
   :nav (str (-> date-published (clojure.string/split #" "))
             (-> title))
   :url (entry-url entry)})

(defn index-renderer [{:keys [entries]}]
  (index-base
   nil
   (let [;;_ (println (pr-str entries))
         entries-nav (mapv entry-nav-map entries)]
     [(the-nav :index entries-nav)
      (mininavbar)
      (index-top)
      (entries-rend entries)])))

(defn tag-renderer [{:keys [name entries]}] nil)
