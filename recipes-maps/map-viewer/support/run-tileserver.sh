#!/bin/sh

if [ -z $1 ]
then
  echo "usage: $(basename $0) <host> [<port>]"
  exit -1
fi

host=$1
port=${2-80}
artifact="$host:$port/maps/map.mbtiles"
dir=$(mktemp -d)
timestamp="$dir/timestamp"

# start tileserver
docker rm -f tileserver
docker run -v $dir:/data -p 9000:80 -d --name tileserver klokantech/tileserver-gl-light

while true
do
  # checking for freshness
  current=$(cat $timestamp 2>/dev/null)
  last_modified=$(curl -I $artifact 2>&1 | grep Last-Modified)

  if [ "$current" != "$last_modified" ]
  then
    echo "restarting tileserver"
    sleep 10

    # fetch new tiles if neccessary
    wget $artifact -O $dir/map.mbtiles

    # restart tileserver
    docker restart tileserver
  fi

  echo $last_modified > $timestamp

  sleep 10
done
