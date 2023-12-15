FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/app-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app-0.0.1-SNAPSHOT.jar"]