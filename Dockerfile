FROM openjdk:8-jdk-alpine

LABEL maintainer="ayman2243@gmail.com"

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=target/mondiamedia-tech-task-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} mondiamedia-tech-task.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/mondiamedia-tech-task.jar"]