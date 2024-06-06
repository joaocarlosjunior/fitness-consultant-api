FROM maven:3-eclipse-temurin-17 AS build

COPY . .

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-jammy

COPY --from=build /target/personal-gym-api-0.0.1.jar app.jar

ENTRYPOINT [ "java", "--jar","app.jar" ]