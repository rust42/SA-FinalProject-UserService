FROM maven:alpine as builder
WORKDIR src
COPY . .
RUN mvn clean install -DskipTests

FROM openjdk:17-alpine as deployer
WORKDIR app
COPY --from=builder src/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]