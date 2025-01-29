FROM openjdk:17-jdk-slim AS builder
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean install -DskipTests

FROM eclipse-temurin:17-jre-alpine

LABEL maintainer="LeNelSoN"
WORKDIR /app

COPY --from=builder /app/target/*.jar /app/app.jar

RUN addgroup -S app && adduser -S -G app app
RUN chown -R app:app /app

USER app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
