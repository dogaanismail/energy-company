
FROM openjdk:21-jdk-slim
EXPOSE 7006

RUN mkdir -p /usr/app
WORKDIR /usr/app

COPY build/libs/consumption-service-db-migration-0.0.1-SNAPSHOT.jar /usr/app/

ENV JAVA_OPTS="-Xmx32m"

CMD exec java $JAVA_OPTS -jar consumption-service-db-migration-0.0.1-SNAPSHOT.jar
