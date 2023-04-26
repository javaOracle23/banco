FROM openjdk:8-alpine
COPY "./target/ApiBanco-0.0.1-SNAPSHOT.jar" "app.jar"
EXPONE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]