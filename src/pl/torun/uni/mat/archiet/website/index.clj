(ns pl.torun.uni.mat.archiet.website.index
  (:require [hiccup.element :refer [javascript-tag]]
            [pl.torun.uni.mat.archiet.website.site :refer [base]]))

(defn an-email [address]
  [:a {:href (str "mailto:Micha%C5%82%20Krzysztof%20Feiler<" address ">")}
    (str "Michał Krzysztof Feiler &lt;" address "&gt;")])

(defn emails [& addresses]
  (vec (cons :p ((comp vec conj vec) (interpose [:br] (map an-email addresses))))))

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
   (emails "archiet@mat.umk.pl" "archiet@platinum.edu.pl")
   [:h4 "Jabber/XMPP"]
   [:p [:a {:href "xmpp:archiet@dukgo.com"} "xmpp:archiet@dukgo.com"]]
   [:br {:clear "all"}]
   [:hr]
   ))
