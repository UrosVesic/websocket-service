# Stage 1: Build the application
FROM maven:3.6.3-openjdk-17 as build
COPY src /usr/home/websocket-service/src
COPY ./pom.xml /usr/home/websocket-service
RUN mvn -f /usr/home/websocket-service/pom.xml clean package -DskipTests

# Stage 2: Package the application
FROM openjdk:17-jdk
COPY --from=build /usr/home/websocket-service/target/*.jar /websocket-service.jar
EXPOSE 8084
ENTRYPOINT ["java","-jar","/websocket-service.jar"]