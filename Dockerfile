FROM openjdk:8u212

USER root
RUN apt-get install -y openssl

COPY --chown=java target/example-1-jar-with-dependencies.jar /usr/local

WORKDIR /usr/local

CMD ["java", "-jar", "example-1-jar-with-dependencies.jar"]

