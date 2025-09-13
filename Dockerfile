#use a base image with java
FROM eclipse-temurin:17-jdk

LABEL version="1.0"

EXPOSE 8080

#set the working directory inside the container
WORKDIR /app

COPY target/traffic_management_system-0.0.1-SNAPSHOT.jar /app/traffic_management_system.jar

ENTRYPOINT ["java","-jar","/app/traffic_management_system.jar"]