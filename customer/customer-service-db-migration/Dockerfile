
FROM openjdk:21-jdk-slim
EXPOSE 7005

RUN mkdir -p /usr/app
WORKDIR /usr/app

COPY build/libs/customer-service-db-migration-0.0.1-SNAPSHOT.jar /usr/app/

ENV JAVA_OPTS="-Xmx32m"

CMD exec java $JAVA_OPTS -jar customer-service-db-migration-0.0.1-SNAPSHOT.jar
