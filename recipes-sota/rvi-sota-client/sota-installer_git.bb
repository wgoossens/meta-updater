require rvi-sota-client.inc


SYSTEMD_SERVICE_${PN} = "sota-installer.service"

DEPENDS += " rvi-sota-client "

FILES_${PN} = " \
${bindir}/sota-installer \
${@bb.utils.contains('DISTRO_FEATURES', 'systemd', '${systemd_unitdir}/system/sota-installer.service', '', d)} \
"

do_compile_prepend() {
  cd sota-installer
}

export SOTA_SECONDARY_ECUS

do_install() {
  install -d ${D}${bindir}
  install -m 0755 target/${TARGET_SYS}/release/sota-installer ${D}${bindir}

  if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
    install -d ${D}/${systemd_unitdir}/system
    export SOTA_SECONDARY_ECU_SERIAL=$(cat ${SOTA_SECONDARY_ECUS} | grep "map-partition" | awk '{ print $1 }')
    cat ${WORKDIR}/sota-installer.service | envsubst > ${D}/${systemd_unitdir}/system/sota-installer.service
    chmod 0644 ${D}/${systemd_unitdir}/system/sota-installer.service
  fi
}
