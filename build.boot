(set-env!
 :source-paths #{"src"}
 :resource-paths #{"resources"}
 :dependencies '[[org.clojure/clojure "1.8.0"]
                 [hiccup "1.0.5"]
                 [perun "0.4.2-SNAPSHOT"]
                 [clj-time "0.13.0"]
                 ;;[boot-ssh "0.1.1-SNAPSHOT"]
                 ])

(task-options!
 pom {:project 'pl.torun.uni.mat.archiet.website :version "0.1.0"})

(require '[io.perun :refer :all])

(deftask build
  "Build the site."
  []
  (comp (markdown)
        (permalink)
        (static :page "index.html" :renderer 'pl.torun.uni.mat.archiet.website.index/render)
        ))
