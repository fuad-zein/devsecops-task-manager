# Stage 1: Build
FROM maven:3.9.9-eclipse-temurin-17-alpine AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -q 2>/dev/null || true
COPY src ./src
RUN mvn clean package -DskipTests -q

# Stage 2: Runtime minimal (hanya JRE)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
ENV TZ=Asia/Jakarta
RUN addgroup -S appgroup && adduser -S appuser -G appgroup && apk add --no-cache wget
COPY --from=builder /app/target/*.jar app.jar
RUN chown appuser:appgroup app.jar
USER appuser
EXPOSE 8080
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD wget -q --spider http://localhost:8080/actuator/health || exit 1
ENTRYPOINT ["java", "-jar", "app.jar"]
