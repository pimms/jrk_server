FROM anapsix/alpine-java:8

RUN apk add ffmpeg

WORKDIR /jrk/
COPY target/jrkserver-1.0-SNAPSHOT.jar .

CMD java -jar jrkserver-1.0-SNAPSHOT.jar --security.require-ssl=true \
                                         --server.ssl.key-store=/cert/keystore.p12 \
                                         --server.ssl.key-store-password=passwordlol \
                                         --server.ssl.keyStoreType=PKCS12 \
                                         --server.ssl.keyAlias=tomcat
ENTRYPOINT []

EXPOSE 8080
