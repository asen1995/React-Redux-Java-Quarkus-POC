# Table Entry Manager - POC

A single-page application POC with a React frontend and Quarkus backend.

## Project Structure

```
├── table-entry-service/    # Quarkus backend (Java 21)
└── entry-ui/               # React frontend (TypeScript)
```

## Prerequisites

### Local Development
- Java 21+
- Maven 3.9+
- Node.js 18+

### Docker
- Docker
- Docker Compose

## Running the Application

### Option 1: Using Docker (Recommended)

Start both applications with a single command:

```bash
docker-compose up --build
```

- Frontend: `http://localhost:5173`
- Backend API: `http://localhost:8080`

Stop the applications:

```bash
docker-compose down
```

Rebuild after code changes:

```bash
docker-compose up --build
```

### Option 2: Local Development

#### 1. Start the Backend

```bash
cd table-entry-service
./mvnw quarkus:dev
```

Backend runs at `http://localhost:8080`

#### 2. Start the Frontend

```bash
cd entry-ui
npm install
npm run dev
```

Frontend runs at `http://localhost:5173`

## API Endpoints

| Method | Endpoint           | Description       |
|--------|-------------------|-------------------|
| GET    | `/api/entries`     | Get all entries (paginated & sorted) |
| GET    | `/api/entries/{id}`| Get entry by ID   |
| POST   | `/api/entries`     | Create new entry  |
| PUT    | `/api/entries/{id}`| Update entry      |
| DELETE | `/api/entries/{id}`| Delete entry      |

### Query Parameters for GET /api/entries

| Parameter      | Default    | Description                              |
|---------------|------------|------------------------------------------|
| page          | 0          | Page number (0-indexed)                  |
| size          | 10         | Page size (max 50)                       |
| sortBy        | createdAt  | Sort field (createdAt, numberValue, selectorValue, freeText) |
| sortDirection | desc       | Sort direction (asc, desc)               |

### API Documentation

HTTP request files for testing are available in `table-entry-service/api-docs/`.

## Tech Stack

**Backend:**
- Quarkus
- Hibernate ORM with Panache
- H2 Database (in-memory)

**Frontend:**
- React 18 + TypeScript
- Redux Toolkit
- Material UI
