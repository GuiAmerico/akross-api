FROM maven:3.8.1-openjdk-17 AS build
COPY . /app
WORKDIR /app
RUN mvn package -DskipTests

FROM openjdk:17-jdk-slim
COPY --from=build /app/target/AkrossAPI-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]