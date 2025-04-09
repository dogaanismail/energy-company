
FROM openjdk:21-jdk-slim
EXPOSE 4000

RUN mkdir -p /usr/app
WORKDIR /usr/app

COPY build/libs/api-gateway-0.0.1-SNAPSHOT.jar /usr/app/

ENV JAVA_OPTS="-Xmx512m"

CMD exec java $JAVA_OPTS -jar api-gateway-0.0.1-SNAPSHOT.jar
