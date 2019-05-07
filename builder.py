#!/usr/bin/env python3

import sys, os, jinja2, shutil

LANGS = {"en", "pl"}
meroundinheadersrc = "xV1oNAGq_400_100.jpg"
statics = (
    ".", ("favicon.ico", "styles.css"),
    "pfp", (meroundinheadersrc,),
    "pgp", ("ArchieT.key", "ArchieT.old.key"),
    "tinyicons", ())

assert len(sys.argv)>=2
assert len(sys.argv)==2
lang = sys.argv[1]
assert lang in LANGS

context = {
    "lang": lang,
    "meroundinheadersrc": meroundinheadersrc,
    "sitetitle": "MKF",
}

target_dir = "target"
assert os.path.exists("builder.py")
assert os.path.exists("pylib4aarchiet")
try:
    os.mkdir(target_dir)
except FileExistsError:
    shutil.rmtree(target_dir)
    os.mkdir(target_dir)
loader = jinja2.FileSystemLoader("templ")
environment = jinja2.Environment(loader=loader)
with open(os.path.join(target_dir, "index.html"), "w") as dest_index:
    dest_index.write(environment.get_template("index.html.j2").render())
subdirs_statics = statics[::2]
in_subdirs_statics = statics[1::2]
assert len(subdirs_statics) == len(in_subdirs_statics)
zipped_statics = zip(subdirs_statics, in_subdirs_statics)
for (sdir,sfils) in zipped_statics:
    for sfil in sfils:
        dpath = os.path.join(target_dir, sfil)
        assert not os.path.exists(dpath)
        shutil.copy2(os.path.join("static", sdir, sfil), dpath)
sys.exit(0)