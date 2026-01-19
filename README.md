# Table Entry Manager - POC

A single-page application POC with a React frontend and Quarkus backend.

## Project Structure

```
├── table-entry-service/    # Quarkus backend (Java 21)
└── entry-ui/               # React frontend (TypeScript)
```

## Prerequisites

- Java 21+
- Maven 3.9+
- Node.js 18+

## Running the Application

### 1. Start the Backend

```bash
cd table-entry-service
./mvnw quarkus:dev
```

Backend runs at `http://localhost:8080`

### 2. Start the Frontend

```bash
cd entry-ui
npm install
npm run dev
```

Frontend runs at `http://localhost:5173`

## API Endpoints

| Method | Endpoint           | Description       |
|--------|-------------------|-------------------|
| GET    | `/api/entries`     | Get all entries   |
| GET    | `/api/entries/{id}`| Get entry by ID   |
| POST   | `/api/entries`     | Create new entry  |
| PUT    | `/api/entries/{id}`| Update entry      |
| DELETE | `/api/entries/{id}`| Delete entry      |

## Tech Stack

**Backend:**
- Quarkus
- Hibernate ORM with Panache
- H2 Database (in-memory)

**Frontend:**
- React 18 + TypeScript
- Redux Toolkit
- Material UI
