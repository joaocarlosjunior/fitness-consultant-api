FROM openjdk:17-jdk-slim
COPY . .
RUN ./mvnw clean install -DskipTests
RUN java --version
ENTRYPOINT ["java", "--jar","target/personal-gym-api-0.0.1.jar"]