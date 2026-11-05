# --- Build stage: full JDK + Maven, discarded after the jar is built ---
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /build

# Copy only the pom first so the dependency download is cached as its own layer
# and is reused until pom.xml changes.
COPY pom.xml .
RUN mvn -B -q dependency:go-offline

COPY src ./src
# Tests run outside the image build: Testcontainers needs a Docker daemon.
RUN mvn -B -q package -DskipTests

# --- Runtime stage: JRE only ---
FROM eclipse-temurin:21-jre
WORKDIR /app

RUN groupadd --system app && useradd --system --gid app app
COPY --from=build /build/target/ingestion.jar app.jar
USER app

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
