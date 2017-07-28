#!/bin/sh

map_serial=$(cat /var/sota/ecus | grep 'map-partition' | awk '{ print $1 }')
/usr/bin/sota-installer --level debug --oneshot --config /var/sota/${map_serial}.toml overwrite --path /var/maps/map.mbtiles
