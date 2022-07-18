FROM registry.aspaku.com/skynet/server

COPY target/arena-api-0.1-SNAPSHOT.jar /plugins/

COPY assets/Arenas.zip /config/
COPY assets/Spawn.zip /config/