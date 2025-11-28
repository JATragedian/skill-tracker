# ============================
#     STAGE 1 — BUILDER
# ============================
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

COPY gradlew .
COPY gradle ./gradle
COPY settings.gradle .
COPY build.gradle .

COPY skilltracker-app ./skilltracker-app
COPY skilltracker-webflux ./skilltracker-webflux

RUN ./gradlew dependencies --no-daemon || true

RUN ./gradlew :skilltracker-app:bootJar --no-daemon

# ============================
#     STAGE 2 — RUNTIME
# ============================
FROM gcr.io/distroless/java21:nonroot

WORKDIR /app
COPY --from=builder /app/skilltracker-app/build/libs/*.jar app.jar

EXPOSE 8080
CMD ["app.jar"]
