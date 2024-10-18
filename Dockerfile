FROM openjdk:8u212

USER root
RUN apt-get install -y openssl

COPY --chown=java target/example-1-jar-with-dependencies.jar /usr/local

WORKDIR /usr/local

ENV MYSQL_HOST=${MYSQL_HOST:-localhost}
ENV MYSQL_ADMIN_USER=${MYSQL_ADMIN_USER:-user}
ENV MYSQL_ADMIN_PASSWORD=${MYSQL_ADMIN_PASSWORD:-password}

CMD ["java", "-jar", "example-1-jar-with-dependencies.jar"]
