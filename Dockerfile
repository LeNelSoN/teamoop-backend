FROM openjdk:17-jdk-slim AS builder
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean install -DskipTests

FROM eclipse-temurin:17-jre-alpine

LABEL maintainer="LeNelSoN"

RUN addgroup -S app && adduser -S -G app app
USER app
WORKDIR /app

COPY --from=builder /app/target/*.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
