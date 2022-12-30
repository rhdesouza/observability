FROM openjdk:17-jdk-alpine
COPY target/observability-0.0.1-SNAPSHOT.jar observability-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/app.jar","/observability-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080