#!/bin/sh

electron-packager --electron-version 6.0.1 --platform linux --arch armv7l --overwrite electron/target/scala-2.12/classes/ scalajs-electron-slideshow
tar zcf ses.tar.gz scalajs-electron-slideshow-linux-armv7l/
