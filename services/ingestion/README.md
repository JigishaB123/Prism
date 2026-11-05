# Ingestion service

The entry point to the Prism pipeline: it accepts content submissions. Right now it is a
scaffold that starts up, connects to Postgres, runs Flyway migrations (none yet), and
reports health. The submission endpoint comes next.

## Build and test

```sh
mvn clean install   # needs Docker running: tests start Postgres with Testcontainers
mvn test
```

## Run

From the repo root:

```sh
cp deploy/.env.example deploy/.env
docker compose -f deploy/docker-compose.yml up --build
curl localhost:8080/actuator/health
```

## Configuration

| Env var | Purpose |
|---|---|
| `SPRING_DATASOURCE_URL` | JDBC URL for Postgres |
| `SPRING_DATASOURCE_USERNAME` | Postgres user |
| `SPRING_DATASOURCE_PASSWORD` | Postgres password |
