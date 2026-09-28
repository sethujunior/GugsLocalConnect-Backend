# GugsLocalConnect Backend

Spring Boot + PostgreSQL API, aligned with the architecture diagram.
See `CHANGELOG.md` for exactly what changed from the original version and why.

## Prerequisites

- Java 21
- Maven
- PostgreSQL running locally

## Setup

1. Create the database:
   ```sql
   CREATE DATABASE gugslocalconnect_db;
   ```
2. Check `src/main/resources/application.properties` — update the
   `spring.datasource.username` / `password` to match your local Postgres
   install if they're not `postgres`/`postgres`.
3. Run it:
   ```bash
   mvn spring-boot:run
   ```
   Or open the project in IntelliJ and run `Main.java`.
4. It starts on **http://localhost:8080**. On first run, `DataSeeder`
   automatically creates the default categories (Hair & Beauty, Food &
   Catering, Trades, Tutoring, Transport, Spaza) so business signup works
   immediately.

## Connecting the Angular frontend

The frontend's `src/environments/environment.ts` should have:
```ts
apiUrl: 'http://localhost:8080/api'
```
CORS is already configured to allow `http://localhost:4200`.

## API reference

### Auth (public)
```
POST /api/auth/register/customer   { name, email, password, phone }
POST /api/auth/register/business   { name, email, password, phone, businessName, category, location, description }
POST /api/auth/login/customer      { email, password }
POST /api/auth/login/business      { email, password }
```
All four return `{ token, user }`.

### Categories (public)
```
GET /api/categories
```

### Search (public)
```
GET /api/search?q=&category=&area=
```

### Businesses
```
GET  /api/businesses/getAll              (public)
GET  /api/businesses/read/{id}           (public)
GET  /api/businesses/me                  (auth required — business owner's own profile)
PUT  /api/businesses/me                  (auth required)
```

### Services
```
GET    /api/businesses/me/services       (auth required)
POST   /api/businesses/me/services       (auth required)
DELETE /api/businesses/me/services/{id}  (auth required)
```

### Bookings (auth required)
```
POST  /api/bookings                { businessId, serviceId, date }
GET   /api/bookings/me
PATCH /api/bookings/{id}/status    { status }   (PENDING | CONFIRMED | CANCELLED | COMPLETED)
```

### Messages (auth required)
```
GET  /api/messages
GET  /api/messages/with/{userId}
POST /api/messages                 { receiverId, content }
```

### Reviews
```
GET  /api/reviews/business/{businessId}   (public)
POST /api/reviews                          { businessId, rating, comment }   (auth required)
```

## Sending the JWT

After login/register, include the token on every subsequent request:
```
Authorization: Bearer <token>
```
The Angular app's `authInterceptor` already does this automatically.
