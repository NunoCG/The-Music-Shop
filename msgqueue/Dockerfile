FROM maven:3.8-openjdk-11 AS build
WORKDIR /usr/local/src
COPY . .
RUN mvn clean install -DskipTests=true

FROM openjdk:11
COPY --from=build /usr/local/src/target/msgqueue-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
