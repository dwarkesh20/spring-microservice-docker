# Build Stage
FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /
COPY . .
RUN mvn clean install -DskipTests


# Final Stage
FROM openjdk:17
WORKDIR /
COPY --from=builder /target/eureka-server.jar .
CMD ["java", "-jar", "eureka-server.jar"]
