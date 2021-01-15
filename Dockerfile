FROM openjdk:8-jdk-alpine
VOLUME /env
ADD target/Client-Engagement.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 9011