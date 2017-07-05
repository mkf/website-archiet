(ns pl.edu.platinum.archiet.website.portfolio
  (:require [pl.edu.platinum.archiet.website.site :refer [base]]))

(defn render [{global :meta entry :entry}]
  (base
   [:div#accordion.panel-group
    [:div.panel.panel-default
     [:div.panel-body.portfproj
      [:img.portproj {:src "http://archiet.platinum.edu.pl/3manchess/res/obrazekcanvasa.png"
                      :alt "3manchess" :title "3manchess"}]
      [:h2.projname [:a {:href "http://archiet.platinum.edu.pl/3manchess"} "3manchess"]]
      [:h3.projline "online multiplayer version of " [:a {:href "http://3manchess.com"}
                                                      "3 Man Chess: In The Round"]]
      [:div.projlang
       [:span.projlang "Go"]
       [:span.projlang "JavaScript"]]
      [:div.projdesc "Gameserver (HTTP RESTful API), JS client with frontend, Go client, client bots"]
      [:div.projcollab [:ul.projcollab
                        [:li.projcollab.me.owner
                         [:span.clbname "Michał Krzysztof Feiler"] " — engine, server, Go client, JS client backend"]
                        [:li.projcollab
                         [:span.clbname "Paweł Zacharek"] " — engine, bots, some help with JS client frontend"]
                        [:li.projcollab
                         [:span.clbname "Patryk Kisielewski"] " — JS client frontend"]
                        [:li.projcollab
                         [:span.clbname [:span.clbntitl "mgr inż."] " Damian Kurpiewski"]
                         " — JS client help (our CS teacher)"]]]]]]
   [:p "Find more " [:a {:href "https://github.com/ArchieT"} "on my GitHub profile page"]]))
