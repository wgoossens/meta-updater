SUMMARY = "Mapbox map viewer"

DESCRIPTION = "Web application to view map tiles from \
a local tile server Mapbox serving .mbtiles files on :80"

LICENSE = "CLOSED"

DEPENDS = "nginx"

SRC_URI = " \
  file://index.html \
  file://mapbox-gl.css \
  file://mapbox-gl.js \
  "

WWWDIR ?= "/usr/lib/www/"

do_install() {
  install -d                               ${D}${WWWDIR}
  install -m 0755 ${WORKDIR}/index.html    ${D}${WWWDIR}
  install -m 0755 ${WORKDIR}/mapbox-gl.css ${D}${WWWDIR}
  install -m 0755 ${WORKDIR}/mapbox-gl.js  ${D}${WWWDIR}
}

FILES_${PN} += " \
    ${WWWDIR} \
"
