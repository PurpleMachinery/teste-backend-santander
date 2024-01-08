FROM maven:3.8.5-amazoncorreto-21 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM amazoncorretto:21-al2-generic-jdk
COPY --from=build /target/teste-backend-0.0.1-SNAPSHOT.jar teste-backend.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "teste-backend.jar"]