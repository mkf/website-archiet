(ns pl.torun.uni.mat.archiet.website.site
  (:require [hiccup.core :refer [html]]
            [hiccup.page :refer [html5 include-css]]))

(def overtitle "Strona Michała Krzysztofa Feilera na serwerze Platinum")

(def title-order [:index])
(def titles {:index {:title nil :nav "O mnie" :url "index.html"}
             })

(defn navbar-list [whichtitle]
  (into [:ul.nav.navbar-nav]
        (keep #(when (or (= % whichtitle)
                         (not (get-in titles [% :hidden-in-nav])))
                 [(if (= % whichtitle) :li.active :li)
                  [:a {:href (get-in titles [% :url])} (get-in titles [% :nav])]])
              title-order)))

(defn base [whichtitle & contents]
  (html5
   (:lang "pl")
   [:head
    [:title (let [title (get-in titles [whichtitle :title])]
              (str title (when-not (nil? title) " :: ") overtitle))]
    [:meta {:charset "UTF-8"}]
    [:link {:rel "stylesheet" :href "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
            :integrity "sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7"
            :crossorigin "anonymous"}]
    [:link {:rel "stylesheet" :href "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css"
            :integrity "sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r"
            :crossorigin "anonymous"}]
    (include-css "/styles.css")
    [:link {:rel "shortcut icon" :href "favicon.ico" :type "image/x-icon"}]
    [:link {:rel "icon" :type "image/png" :href "DSCF7629-bfab-fixed-squared-666px.png"}]
    [:link {:rel "icon" :href "favicon.ico" :type "image/x-icon"}]]
   [:body.website
    [:noscript [:iframe {:src "//www.googletagmanager.com/ns.html?id=GTM-KP8WW2"
                         :height "0" :width "0" :style "display:none;visibility:hidden"}]]
    [:script {:type "text/javascript" :src "naszgtm.js"}]
    [:div.rightmenu
     [:nav.navbar.navbar-default
      [:div.container-fluid
       [:div.navbar-header
        [:a.navbar-brand {:href "index.html"}
         [:img {:src "DSCF7629-bfab-fixed-squared-666px.png" :height "11px"
                :alt "Strona domowa Michała Krzysztofa Feilera"}]]]
       [:div#bs-navbar-collapse-1.collapse.navbar-collapse.navbar-top
        (navbar-list whichtitle)]]]]
    contents
    ]))
