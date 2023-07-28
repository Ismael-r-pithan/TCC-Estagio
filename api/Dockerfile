# FROM adoptopenjdk/openjdk11:alpine

# RUN echo ${TZ} > /etc/timezone

# EXPOSE 80

# ADD /target/moscou-api-0.0.1-SNAPSHOT.jar moscou-api.jar

# ENTRYPOINT ["java","-jar","moscou-api.jar", "--server.port=80"] 

FROM maven:3.6.0-jdk-11-slim AS build

WORKDIR /app

COPY src /app/src
COPY pom.xml /app

RUN mvn -f /app/pom.xml clean package -DskipTests=true

FROM openjdk:11-jre-slim

COPY --from=build /app/target/moscou-api-0.0.1-SNAPSHOT.jar /usr/local/lib/moscou-api.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "/usr/local/lib/moscou-api.jar" ]