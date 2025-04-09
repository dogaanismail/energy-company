
FROM openjdk:21-jdk-slim
EXPOSE 5006

RUN mkdir -p /usr/app
WORKDIR /usr/app

COPY build/libs/consumption-service-0.0.1-SNAPSHOT.jar /usr/app/

ENV JAVA_OPTS="-Xmx512m"

CMD exec java $JAVA_OPTS -jar consumption-service-0.0.1-SNAPSHOT.jar
