SUMMARY = "garage-sign"
DESCRIPTION = "Metadata signing tool for ATS Garage"
HOMEPAGE = "https://ats-tuf-cli-releases.s3-eu-central-1.amazonaws.com/index.html"
SECTION = "base"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://${S}/docs/LICENSE;md5=3025e77db7bd3f1d616b3ffd11d54c94"
DEPENDS = ""

PV = "0.2.0-48-g7ee8146"

SRC_URI = " \
  https://ats-tuf-cli-releases.s3-eu-central-1.amazonaws.com/cli-${PV}.tgz \
  "

SRC_URI[md5sum] = "0691f36c5b58acc1ca9c23ffbfaae1f3"
SRC_URI[sha256sum] = "9f230944643088a1e6a77663baa06dfa64d52885e66bd48a7cb1ed1c70936cfa"

S = "${WORKDIR}/${BPN}"

BBCLASSEXTEND =+ "native"

do_install() {
    install -d ${D}${bindir}
    install -m "0755" -t ${D}${bindir} ${S}/bin/*
    install -d ${D}${libdir}
    install -m "0644" -t ${D}${libdir} ${S}/lib/*
}

FILES_${PN} = " \
  ${bindir}/garage-sign.bat \
  ${bindir}/garage-sign \
  ${libdir}/* \
  "
