FROM openjdk:17-jdk-slim AS dev

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY ./src ./src

CMD ["./mvnw", "spring-boot:run"]

FROM openjdk:17-jdk-slim AS builder
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean install -DskipTests

FROM eclipse-temurin:17-jre AS release

LABEL maintainer="LeNelSoN"
WORKDIR /app

COPY --from=builder /app/target/*.jar /app/app.jar

RUN addgroup --system app && adduser -S -s /bin/false -G app app
RUN chown -R app:app /app

USER app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
