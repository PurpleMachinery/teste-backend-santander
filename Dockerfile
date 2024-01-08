FROM maven:3.9.6-eclipse-temurin-21-jammy AS build
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-jammy
COPY --from=build /target/teste-backend-0.0.1-SNAPSHOT.jar teste-backend.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "teste-backend.jar"]