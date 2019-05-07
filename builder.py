#!/usr/bin/env python3

import jinja2
import os
import shutil
import sys
import logging
from itertools import chain

LANGS = {"en", "pl"}
meroundinheaderimgsrc = "xV1oNAGq_400_100.jpg"
statics = (
    ".", ("favicon.ico", "styles.css"),
    "pfp", (meroundinheaderimgsrc,),
    "pgp", ("ArchieT.key", "ArchieT.old.key"),
    "tinyicons", (),
)
subdirs_statics = statics[::2]  # type: tuple
in_subdirs_statics = statics[1::2]
# noinspection PyTypeChecker
assert (len(subdirs_statics) == len(in_subdirs_statics))
zipped_statics = zip(subdirs_statics, in_subdirs_statics)

logger = logging
logging.basicConfig(level=logging.INFO)
# logger.setLevel(logging.DEBUG)
logger.info("logger started")

assert (len(sys.argv) >= 2)
# noinspection PyTypeChecker
assert (len(sys.argv) == 2)
lang = os.getenv('SITELANG')
assert lang in LANGS

action = sys.argv[1]
assert action in {'build', 'watch'}

context = {
    "lang": lang,
    "meroundinheaderimgsrc": "/" + meroundinheaderimgsrc,
    "sitetitle": "MKF",
}

list_of_just_renders = (
    "index.html.j2", "index.html", ("baseof.html.j2", "favicons.html"),
)
list_of_just_renders_templs = list_of_just_renders[::3]  # type: tuple
list_of_just_renders_dests = list_of_just_renders[1::3]
list_of_just_renders_otherdeps = list_of_just_renders[2::3]
# noinspection PyTypeChecker
assert len(list_of_just_renders_dests) == len(list_of_just_renders_templs)
# noinspection PyTypeChecker
assert len(list_of_just_renders_dests) == len(list_of_just_renders_otherdeps)
zipped_list_of_just_renders = zip(list_of_just_renders_templs, list_of_just_renders_dests)
zipped_list_of_just_renders_with_otherdeps = zip(list_of_just_renders_templs,
                                                 list_of_just_renders_dests,
                                                 list_of_just_renders_otherdeps)


def otherdeps_gen_iter():
    for (ftempl, tdest, o) in zipped_list_of_just_renders_with_otherdeps:
        for i in o:
            yield (i, ftempl, tdest)
    return


otherdeps = tuple(otherdeps_gen_iter())
list_of_deps_of_renders = chain(
    ((ftempl, ftempl, todest) for (ftempl, todest) in zipped_list_of_just_renders),
    otherdeps)

target_dir = "target"
assert os.path.exists("builder.py")
assert os.path.exists("pylib4aarchiet")


def make_new_clean_dir(dirname):
    try:
        os.mkdir(dirname)
        logger.info("created target_dir")
    except FileExistsError:
        shutil.rmtree(dirname)
        os.mkdir(dirname)
        logger.info("created target_dir after removing previous one")


if action in {"build", "watch"}:
    make_new_clean_dir(target_dir)
    loader = jinja2.FileSystemLoader("templ")
    environment = jinja2.Environment(loader=loader)


    def just_render(from_template, to_dest):
        logger.info("preparing for generating from " + from_template + " to " + to_dest + " .")
        with open(os.path.join(target_dir, to_dest), "w") as dest_index:
            dest_index.write(environment.get_template(from_template).render(context))
        logger.info("finished generating from " + from_template + " to " + to_dest + " .")


    for (ftempl, tdest) in zipped_list_of_just_renders:
        just_render(ftempl, tdest)


    def just_static(sdir, sfil):
        dpath = os.path.join(target_dir, sfil)
        assert not os.path.exists(dpath)
        logger.info("about to copy " + sfil + " from " + sdir + " to " + dpath + " .")
        shutil.copy2(os.path.join("static", sdir, sfil), dpath)
        logger.info("copied " + sfil + " from " + sdir + " to " + dpath + " .")


    for (sdir, sfils) in zipped_statics:
        for sfil in sfils:
            just_static(sdir, sfil)
    if action == "watch":
        logger.info("we begin watching now")
        import asyncio
        import pyinotify


        # dict_of_just_renders = dict(zipped_list_of_just_renders)

        def gen_srcdest_of_statics():
            for (sdir, sfils) in zipped_statics:
                for sfil in sfils:
                    yield (os.path.join(sdir, sfil), (sdir, sfil))
            return


        # srcdest_of_statics = tuple(gen_srcdest_of_statics())
        # dict_of_statics = dict(srcdest_of_statics)
        what = chain(((dpath, lambda x: print(x,just_static(sdir, sfil)))
                      for (dpath, (sdir, sfil))
                      in gen_srcdest_of_statics()),
                     ((os.path.join("templ", templname),
                       lambda x: print(x,just_render(templname, tdest)))
                      for (templname, tdest)
                      in zipped_list_of_just_renders))
        loop = asyncio.get_event_loop()
        que = []
        for (fpath, lamb) in what:
            wm = pyinotify.WatchManager()
            notifier = pyinotify.AsyncioNotifier(wm, loop, callback=lamb)
            wm.add_watch(fpath, pyinotify.IN_MODIFY | pyinotify.IN_MOVE_SELF)
            que.append((wm, notifier))
        try:
            logger.info("watching from now on")
            loop.run_forever()
        except KeyboardInterrupt:
            pass
        finally:
            loop.stop()
            loop.close()
            for i in que:
                i[1].stop()
    else:
        sys.exit(0)
