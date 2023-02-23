FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ratings-data-service.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=docker","/ratings-data-service.jar"]
