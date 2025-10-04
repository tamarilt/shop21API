FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml ./
COPY src ./src
RUN mvn -B -DskipTests clean package

FROM openjdk:21-jdk-slim

RUN addgroup --system spring && adduser --system --ingroup spring spring

WORKDIR /app
COPY --from=build /workspace/target/*.jar app.jar
RUN chown -R spring:spring /app
USER spring

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]