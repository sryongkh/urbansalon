# UrbanSalon

UrbanSalon is an early Spring Boot backend for a salon booking platform.

## Project status

| Component | Current state |
| --- | --- |
| User service | Basic user API |
| Salon service | Create, update, list, and search salons |
| Category service | List, create, and delete salon categories |
| Service offering | Spring Boot scaffold |
| Booking and customer-facing UI | Pending |
| Keycloak authentication and owner authorization | Pending. Salon and category writes use fixed development IDs |
| Payments and real-time notifications | Pending |

## Run locally

Install JDK 17. The Maven wrapper downloads Maven on the first run. To start the salon API with a temporary H2 database, run:

```sh
cd backend/microservices/salon-service
./mvnw spring-boot:run -Dspring-boot.run.profiles=demo
```

The `demo` profile binds to loopback. Its data resets when the application stops. Keep these development APIs on a trusted machine while authentication is pending.

In another terminal, create and list a salon:

```sh
curl -fsS http://127.0.0.1:5002/api/salons \
  -H 'Content-Type: application/json' \
  -d '{"name":"UrbanSalon","address":"10 Main Road","phoneNumber":"020000000","email":"salon@example.com","city":"Bangkok","openTime":"09:00","closeTime":"18:00"}'

curl -fsS http://127.0.0.1:5002/api/salons
```

Use the same Maven command from the user or category service directory to start those APIs on ports 5001 and 5003. With the default profile, these three services expect MySQL on localhost. Create the schemas listed in their `application.properties` files and set `DB_PASSWORD` and, if needed, `DB_USERNAME`.

Run `./mvnw test` from any service directory. The application tests use the local database profile.
