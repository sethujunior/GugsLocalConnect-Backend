# Changelog — aligning the backend with the architecture diagram

This documents every change made to the backend your team already built,
and why. Nothing here was rewritten from scratch — existing entities,
repositories, and business logic were kept; the changes below fix what
didn't compile, then build out what the architecture diagram called for
but wasn't implemented yet.

**I couldn't compile this locally to double-check it (no Maven Central
access in the environment I worked in), so please run `mvn compile` or
open it in your IDE before relying on it, and let me know if anything
doesn't build.**

## 1. Fixed — compile errors (unresolved merge conflicts)

These 7 files had two versions of the same code pasted one after another
instead of merged, which doesn't compile. Kept the more complete version
in each case, deleted the duplicate:

- `domain/User.java` — constructor/Builder.copy() referenced a `passwordHash`
  field that didn't exist on the Builder.
- `controller/UserController.java` — extra methods pasted after the class's
  closing brace.
- `service/UserService.java` — two `login(String, String)` methods with
  identical signatures.
- `controller/BusinessProfileController.java` — extra method pasted after
  the closing brace.
- `service/BusinesProfileService.java` — a field and a whole second
  constructor were accidentally declared *inside* another method's body.
  Between the two conflicting constructors (one via `UserService`, one via
  `UserRepository` directly), kept the `UserService`-based one — it reuses
  UserService's email-uniqueness check and password hashing instead of
  duplicating that logic.
- `dto/BusinessSignupRequest.java` — all fields/getters duplicated outside
  the class body.
- `repository/CategoryRepository.java` — interface closed early, then a
  conflicting `findByName` method (different return type) pasted after.

Also fixed while in there:
- `dto/LoginRequest.java` had no setters, only getters — Jackson (the JSON
  library) can't populate private fields without setters, so login would
  have always received `email=null, password=null`.
- `@RequestBody User user` in the old signup endpoint wouldn't have worked
  either — `User` only exposes a Builder, no setters, same problem. Added
  `dto/CustomerSignupRequest.java` as a proper request DTO instead of
  binding directly to the JPA entity (binding request bodies straight to
  entities is generally avoided anyway).

## 2. Changed — database: MySQL → PostgreSQL

Per the architecture diagram's Data Layer. Changed:
- `pom.xml` — swapped `mysql-connector-j` for `org.postgresql:postgresql`
- `application.properties` — new datasource URL/driver, PostgreSQL Hibernate
  dialect

**You'll need to create the database yourself first** — unlike the old
MySQL setup, Postgres doesn't auto-create it:
```sql
CREATE DATABASE gugslocalconnect_db;
```
Then update the username/password in `application.properties` to match
your local Postgres install (defaults are `postgres`/`postgres`).

## 3. Added — real JWT authentication

Per "JWT-based auth" in the diagram. New files, all under `security/`:
- `JwtUtil.java` — issues and validates tokens (HS256)
- `JwtAuthFilter.java` — reads the `Authorization: Bearer <token>` header
  on every request, sets the authenticated user on the security context
- `SecurityConfig.java` — wires it all up: which routes are public
  (browsing/search/auth) vs. require login (dashboards, bookings,
  messaging, managing your own business), CORS, password hashing bean
- Replaced the old `config/CorsConfig.java` (only allowed port 5500,
  wouldn't have worked with the Angular app on port 4200 anyway) — CORS is
  now defined inside `SecurityConfig` since Spring Security needs to know
  about it directly.
- `UserService` now hashes passwords with BCrypt instead of storing them
  in plain text, and login checks the hash instead of a direct string
  comparison.
- New `controller/AuthController.java` handles `/api/auth/register/*` and
  `/api/auth/login/*`, returning `{ token, user }` — matches what the
  Angular `AuthService` expects. `UserController` is now plain admin CRUD.

**Change this before deploying anywhere real:** `app.jwt.secret` in
`application.properties` is a placeholder. Generate a real random secret.

## 4. Added — Booking Management endpoints

`controller/BookingServiceController.java` was an empty class — the
domain/repository/service layer for bookings all existed, nothing was
ever exposed over HTTP. Added:
- `POST /api/bookings` — create a booking request
- `GET /api/bookings/me` — bookings for the logged-in user (works for
  both customer and business-owner dashboards)
- `PATCH /api/bookings/{id}/status` — update a booking's status

Also added a `requestedDate` field to the `Booking` entity — the existing
`requestedAt` is an auto-set audit timestamp of when the request was
made, but there was nowhere to store the date the customer actually wants
the service performed, which the Angular booking form collects.

## 5. Added — Search & Discovery

Didn't exist at all before. `controller/SearchController.java` +
`BusinesProfileService.search()` — simple filter by keyword/category over
business profiles.

## 6. Changed — routes now match what the Angular frontend expects

All endpoints moved under `/api/...` with the exact paths the Angular
services call (full list in this README below), and several DTOs got
`@JsonProperty` aliases so JSON field names line up (e.g. `businessProfileID`
→ `id`, `businessName` → `name`) without renaming any existing Java fields.

## Known simplifications (flagged honestly, not hidden)

- `BusinessProfile`'s `rating`, `reviews` (count), `image`, and `area`
  fields are placeholders (0 / null / same as location) — real values need
  a review-aggregation query and an image upload/storage feature (the
  diagram's "File Storage" box), neither of which exist yet.
- Booking status updates aren't restricted to the business owner who owns
  that booking — anyone with a valid token can currently PATCH any
  booking's status. Worth tightening before this goes further.
- No `mvn package`-produced runnable jar — the original `pom.xml` never
  had a `spring-boot-maven-plugin` build section either, so this isn't a
  regression, but worth adding if you need a standalone jar rather than
  running via IDE / `mvn spring-boot:run`.
