# Prism

A distributed content intelligence pipeline. Submitted content flows through a chain of
microservices that clean, enrich, classify, summarize, and index it. Users can then search
it and ask questions about it through RAG and AI agents. Services communicate through
Apache Kafka.

> **Status:** early development. The ingestion service is in progress. See the
> [roadmap](docs/ROADMAP.md).

## Services

| Service | Purpose |
|---|---|
| ingestion | Accepts content submissions |
| cleaning | Normalizes and deduplicates text |
| enrichment | Classifies, summarizes, and chunks content with an LLM |
| indexing | Embeds chunks and stores them for search |
| query | Search, RAG question answering, and AI agents |

## Tech stack

Java 21, Spring Boot 3, Kafka, Postgres (pgvector), Redis, Docker, Kubernetes.

## Getting started

Requires Docker, Java 21, and Maven.

```sh
cp deploy/.env.example deploy/.env
docker compose -f deploy/docker-compose.yml up --build
curl localhost:8080/actuator/health
```

Run tests (Docker must be running):

```sh
cd services/ingestion && mvn test
```

## Project structure

```
services/   microservices
deploy/     Docker Compose configuration
docs/       roadmap and design notes
```
