# Resident-Classification-project

A property classification system: parse property records from a text file
or REST API, classify them by type, persist them to a local SQLite
database, and query/manage them through a REST interface.

Originally a BlueJ exam-prep exercise, now a Spring Boot + Maven
application.

## Overview

The system models two kinds of property:

- **Sell properties** — a property listed at a fixed sale price
- **Rent properties** — a property listed with a monthly rent and a lease
  duration (in months)

## Project structure

```
resident-classification/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/com/nkululeko/residentclassification/
    │   │   ├── ResidentClassificationApplication.java   (entry point)
    │   │   ├── model/
    │   │   │   ├── Property.java       (abstract base)
    │   │   │   ├── SellProperty.java
    │   │   │   ├── RentProperty.java
    │   │   │   └── Calcable.java       (calculation interface)
    │   │   ├── repository/
    │   │   │   └── PropertyRepository.java   (JdbcTemplate + SQLite)
    │   │   ├── service/
    │   │   │   └── ImportService.java  (text file to database)
    │   │   └── controller/
    │   │       └── PropertyController.java   (REST endpoints)
    │   └── resources/
    │       └── application.properties  (SQLite datasource config)
    └── test/
        ├── java/com/nkululeko/residentclassification/
        │   ├── model/PropertyModelTest.java
        │   ├── service/ImportServiceTest.java
        │   └── repository/PropertyRepositoryTest.java
        └── resources/application.properties  (test database config)
```

## Data format

`propertydata.txt` (placed at the project root) uses `#` as a field
delimiter. The first character of `code` decides the type:

```
code#agentName#price              -> SellProperty   (code starts with 1)
code#agentName#rent#duration      -> RentProperty    (code starts with 2)
```

## Database

`PropertyRepository` stores properties in a single SQLite table
(`properties.db`, created automatically on first run):

```sql
CREATE TABLE IF NOT EXISTS properties (
    code TEXT PRIMARY KEY,
    agentName TEXT,
    propertyType TEXT,
    price REAL,
    rent REAL,
    duration INTEGER
);
```

Sell and rent records share one table; unused columns are `NULL`.
`propertyType` records which subtype a row represents so it can be
rebuilt correctly on read.

## REST API

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/properties` | All properties |
| GET | `/api/properties/sell` | Sell properties only |
| GET | `/api/properties/rent` | Rent properties only |
| POST | `/api/properties/sell` | Add a sell property (JSON body) |
| POST | `/api/properties/rent` | Add a rent property (JSON body) |
| POST | `/api/properties/import?file=propertydata.txt` | Import from a text file |
| PUT | `/api/properties/sell/{code}/price?price=X` | Update a sale price |
| PUT | `/api/properties/rent/{code}/rent?rent=X` | Update a monthly rent |
| DELETE | `/api/properties/{code}` | Delete a property by code |

## Running the project

Requires JDK 17+ and Maven.

```bash
mvn clean install      # first run - downloads dependencies, compiles, runs tests
mvn spring-boot:run    # starts the app on http://localhost:8080
```

To import the sample data once running:

```bash
curl -X POST "http://localhost:8080/api/properties/import?file=propertydata.txt"
curl http://localhost:8080/api/properties
```

Run tests on their own with:

```bash
mvn test
```

## Status

- [x] OOP model (Property/SellProperty/RentProperty, Calcable interface)
- [x] File parsing (ImportService)
- [x] Database persistence (PropertyRepository, SQLite via JdbcTemplate)
- [x] REST API (PropertyController)
- [x] Unit tests for model and parsing logic
- [x] Integration tests for the repository (Spring context + real SQLite)
- [ ] Input validation on the REST layer (currently trusts the request body)
- [ ] Search/filter beyond type (e.g. by agent, price range)

## Author

Nkululeko Khalishwayo
