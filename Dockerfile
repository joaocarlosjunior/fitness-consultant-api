FROM openjdk:17-jdk-slim

COPY . .

RUN ./mvnw clean install -DskipTests

COPY --from=build /target/personal-gym-api-0.0.1.jar app.jar

ENTRYPOINT [ "java", "--jar","app.jar" ]