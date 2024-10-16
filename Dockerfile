FROM maven:3.9.7-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk
WORKDIR /app
COPY --from=build /app/target/asms-0.0.1-SNAPSHOT.jar asms.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","asms.jar"]
