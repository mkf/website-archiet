(ns pl.edu.platinum.archiet.website.index
  (:require [hiccup.element :refer [javascript-tag]]
            [pl.edu.platinum.archiet.website.site :refer [base]]))

(def badges
  {:twitter {:url "https://twitter.com/feilermichal"
             :img "socba/twitter-xl.png" :alt "Twitter"
             :title "Twitter is a social networking service which is about daily short burst messages called tweets restricted to 140 characters."}
   :github {:url "https://github.com/ArchieT"
            :img "http://cdn.flaticon.com/png/256/25231.png"
            :alt "Github" :title "Github is an online project hosting using Git"}
   :linkedin {:url "https://linkedin.com/in/feilermichal"
              :img "socba/LinkedIn-Badge.png" :alt "LinkedIn"
              :title "Linkedin is a business and employment-oriented social networking service. LinkedIn profiles are often treated as personal résumés / CVs"}
   :goldenline {:url "https://www.goldenline.pl/michal-krzysztof-feiler/"
                :img "socba/goldenline.png" :alt "GoldenLine"
                :title "GoldenLine is a Polish business and employment-oriented social networking service. GoldenLine profiles are made to resemble personal résumés / CVs"}
   :keybase {:url "https://keybase.io/ArchieT"
             :img "socba/keybase_logo_no_text.png" :alt "Keybase"
             :title "Keybase is a public directory of people's identities with their public keys being connected to identity proofs on social sites"}
   :google+ {:url "https://plus.google.com/u/0/100176969216547798972?prsrc=3"
             :img "http://ssl.gstatic.com/images/icons/gplus-64.png" :alt "Google+"
             :title "Google Plus is a social network from Google"}
   :tumblr {:url "https://archietor.tumblr.com" :alt "Tumblr"
            :img "socba/tumblr128.png"
            :title "Tumblr is a microblogging and social networking website allowing to post multimedia and other content to a short-form blog."}
   :medium {:url "https://medium.com/@ArchieT" :alt "Medium"
            :img "https://cdn-static-1.medium.com/_/fp/icons/favicon-new.TAS6uQ-Y7kcKgi0xjcYHXw.ico"
            :title "Medium is an online publishing platform"}
   :facebook {:url "https://facebook.com/feilermichal"
              :img "socba/icon-facebook-64.png" :alt "Facebook"
              :title "Facebook is a popular social networking site which is not good as a primary source of information about me"}
   :wakatime {:url "https://wakatime.com/@ArchieT"
              :img "socba/wakatime_311437_th3.jpg" :alt "WakaTime"
              :title"Wak
aTime is a service for measuring time spent in text editors, gathering information about project name, languages and editors used."}
   :lastfm {:url "https://www.last.fm/user/feilermichal"
            :img "socba/lastfm.png" :alt "Last.fm"
            :title "Last.fm is a music scrobbling website, keeping track of the music you listen to."}})

(defn badge ([kw] (let [{:keys [url img alt title]} (badges kw)]
                    (badge kw url img alt title)))
  ([kw url img alt title]
   [:a.prof {:href url :target "_blank"}
    [:img.prof (merge {:width 64 :src img :alt alt :title title}
                      (if (= kw :google+) {:rel "publisher"} {}))] "  "]))

(defn waka ([num-of-days-lang] (waka num-of-days-lang false))
  ([num-of-days ca-not-lang?]
   (let [lang-not-ca? (not ca-not-lang?)]
     [:h5.waka (str (if lang-not-ca? "Languages" "Coding Activity")
                    " over "
                    (if (zero? num-of-days) "All Time"
                        (str "Last " num-of-days " Days"))
                    " (Powered by ")
      [:a {:href "http://wakatime.com" :target "_blank"} "wakatime.com"]])))

(defn render [{global :meta entry :entry}]
  (base
   [:span.headright]
   [:h1 "Michał Krzysztof Feiler"]
   [:side "Absolwent liceum, programista, fan wolnego oprogramowania."]
   [:img.meright
    {:src "DSCF7629-bfab-fixed-squared-666px.png" :height "333px"
     :alt "Michał Krzysztof Feiler" :title "Michał Krzysztof Feiler w 2012"}]
   [:br] [:hr] [:h2 "Kontakt"]
   [:h3 "Mój klucz publiczny OpenPGP"]
   [:a {:href "ArchieT.key"}
    [:span.pgp "6ACF 4FCE E9BC DE97 5C81  4A49 599D 6671 880D 22D6"]]
   [:br]
   [:a {:href "ArchieT.old.key"}
    [:span.pgprev "72A3 D939 ECA2 67FB 0096  8F4B 8179 43C5 88A5 0062"]]
   [:h3 "Adresy"]
   [:h4 "Email"]
   [:p
    [:a {:href"mailto:Micha%C5%82%20Krzysztof%20Feiler<archiet@platinum.edu.pl>"}
     "Michał Krzysztof Feiler &lt;archiet@platinum.edu.pl&gt;"] [:br]
    [:script {:type "text/javascript" :src "jquery-2.2.3.min.js"}]
    [:code#mailfinger.mailfinger]
    (javascript-tag "
jQuery.get({
    url: 'http://archiet.platinum.edu.pl/mailfinger',
    success: function (data) {
        $('code.mailfinger').html(data)
    }
})")]
   [:h4 "Jabber/XMPP"]
   [:p [:a {:href "xmpp:archiet@dukgo.com"} "xmpp:archiet@dukgo.com"]]
   [:br {:clear "all"}]
   [:h3 "Profile w sieci"
    [:p.hoverfordesc " najedź kursorem lub dotknij długo by zobaczyć opis"]]
   (badge :twitter)
   (badge :github)
   (badge :linkedin)
   (badge :goldenline)
   (badge :keybase)
   (badge :google+)
   (badge :tumblr)
   (badge :medium)
   (badge :facebook)
   (badge :wakatime)
   (badge :lastfm)
   [:hr]
   [:blockquote.blockquote-reverse {:cite "http://fokus.dn.se/edward-snowden-english/"}
    [:cite.snowdencit
     "\"— Arguing that you don’t care about the right to privacy because you have
       nothing to hide, is no different than saying you don’t care about free speech
       because you have nothing to say, or the freedom of press because you’re not a
       journalist, or the freedom of religion because you’re not a Christian. Rights
       in societies are collective, and individual. You can’t give away the rights of
       a minority, even if you vote as a majority. Rights are inherent to our nature,
       they’re not granted by governments, they’re guaranteed by governments. They’re
       protected by governments.\""]
    [:footer "Edward Snowden"]]
   [:hr]
   [:script {:type "text/javascript" :src "https://www.gstatic.com/charts/loader.js"}]
   [:script {:type "text/javascript" :src "wakatimeget.js"}]
   (waka 30 :codingactivity) [:div#actiwaka.actiwaka]
   (waka 7) [:div#7d.piewaka]
   (waka 30) [:div#30d.piewaka]
   (waka 0) [:div#alltime.piewaka]
   ))
