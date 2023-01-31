FROM openjdk:17-jdk-alpine
COPY target/observability-api-0.0.1-SNAPSHOT.jar /app/observability-api-0.0.1-SNAPSHOT.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "observability-api-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080