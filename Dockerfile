FROM eclipse-temurin:17.0.8.1_1-jdk-jammy
VOLUME /tmp
COPY . .
RUN ./mvnw clean install -DskipTests
ENTRYPOINT [ "java", "--jar","target/personal-gym-api-0.0.1.jar" ]