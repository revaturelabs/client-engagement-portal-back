FROM openjdk:8-jdk-alpine
MAINTAINER experto.com
VOLUME /tmp
ADD target/cryptotracker.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 8080