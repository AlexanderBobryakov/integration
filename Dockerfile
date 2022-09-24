FROM openjdk:8-jdk-alpine
MAINTAINER bobryakov.com
COPY target/spring_integration-1.0-SNAPSHOT.jar spring_integration.jar
ENTRYPOINT ["java","-jar","/spring_integration.jar"]