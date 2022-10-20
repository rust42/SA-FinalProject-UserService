FROM maven:latest as builder
WORKDIR src
COPY . .
RUN mvn clean install -DskipTests

FROM openjdk:latest as deployer
WORKDIR app
COPY --from=builder src/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]