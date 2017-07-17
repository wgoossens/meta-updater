# Prerequisites
- download a tiles file from (OpenMapTiles)[www.openmaptiles.org/download]
- caveat: files >10Mb currently can take quite long to transfer to the client
  (several minutes)

# Build a map-enabled Yocto image
- checkout branch `map-poc` in meta-updater in the Yocto source tree
- create a file `secondary_ecu` with:

```
echo "$(uuidgen) map-partition" > /tmp/secondary_ecu
```

- modify the build config (`local.conf` or `site.conf`):

```
SOTA_CLIENT = "sota-client sota-launcher sota-installer"
SOTA_SECONDARY_ECUS = "/tmp/secondary_ecu"

IMAGE_INSTALL_append = " gettext "
IMAGE_INSTALL_append = " nginx "
IMAGE_INSTALL_append = " map-viewer "
```

- build the image

# Start a tiles renderer
- run the provided script `recipes-maps/map-viewer/support/run-tileserver.sh
  <host> <port>`;
- for QEMU-based builds: you have to forward port 80 on the client to a free
  port on your host machine, say `8080`): `run-tileserver.sh localhost 8080`
- for RaspberryPi-based builds: `run-tileserver.sh <ip of your rasberry> 80`

# Start the client
- make sure that the client appears in the web app and properly reports it's
  secondary ECU (`map-partition`)

# Create a map package
- in `Packages` add the downloaded file above, with `Secured` enabled and
  `map-partition` as hardware ID

# Create a campaign
- add the device to a group
- create a campaign for that group
- launch it

# Displaying the map
- on your host machine, navigate to `http://localhost:8080` (`http:/<raspberry
  ip>` respectively), you should see after a short period a world map with
  details for the uploaded region
- NOTE: the tiles renderer (`localhost:9000`) must be running on the same
  machine as the maps displayer
