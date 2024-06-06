FROM openjdk:17-jdk-slim

COPY . .

RUN ./mvnw clean install -DskipTests

ENTRYPOINT [ "java", "--jar","personal-gym-api-0.0.1.jar" ]