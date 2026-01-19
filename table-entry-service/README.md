# Table Entry Service

A Quarkus-based REST API for managing table entries with H2 in-memory database.

## Prerequisites

- Java 21+
- Maven 3.9+

## Running the Application

### Development Mode

Run with hot-reload enabled:

```bash
./mvnw quarkus:dev
```

The server starts at `http://localhost:8080`

### Production Mode

Build and run:

```bash
./mvnw package
java -jar target/quarkus-app/quarkus-run.jar
```

## API Endpoints

| Method | Endpoint           | Description       |
|--------|-------------------|-------------------|
| GET    | `/api/entries`     | Get all entries   |
| GET    | `/api/entries/{id}`| Get entry by ID   |
| POST   | `/api/entries`     | Create new entry  |
| PUT    | `/api/entries/{id}`| Update entry      |
| DELETE | `/api/entries/{id}`| Delete entry      |

## Example Request

```bash
curl -X POST http://localhost:8080/api/entries \
  -H "Content-Type: application/json" \
  -d '{"numberValue": 42, "selectorValue": "Option A", "freeText": "Sample text"}'
```

## Running Tests

```bash
./mvnw test
```
