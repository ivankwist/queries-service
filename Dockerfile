#
# Build stage
#
FROM maven:3.6.3-jdk-8-slim AS build
COPY pom.xml /usr/src/app/pom.xml
RUN mvn -f /usr/src/app/pom.xml dependency:go-offline -B
COPY src /usr/src/app/src
RUN mvn -f /usr/src/app/pom.xml clean package -Dmaven.test.skip=true

#
# Package stage
#
FROM openjdk:8-jre-alpine
RUN addgroup -S spring && adduser -S spring -G spring
COPY --from=build /usr/src/app/target/queries-service-0.0.1-SNAPSHOT.jar /usr/app/queries-service.jar
USER spring:spring
ENTRYPOINT ["java","-jar","/usr/app/queries-service.jar"]
