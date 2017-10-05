(ns pl.torun.uni.mat.archiet.website.index
  (:require [hiccup.element :refer [javascript-tag]]
            [pl.torun.uni.mat.archiet.website.site :refer [base]]))

(defn render [{global :meta entry :entry}]
  (base :index
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
   ))
