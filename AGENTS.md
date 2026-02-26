# AGENTS.md

## Cursor Cloud specific instructions

### Project Overview

Java/Maven API test suite that validates endpoints of the BPDTS Test App REST API. No services to run locally — this is a pure test automation project.

### Prerequisites (already installed in VM snapshot)

- **JDK 17** (`openjdk-17-jdk`)
- **Apache Maven 3.8+** (`maven` system package)

### Key Commands

| Action | Command |
|--------|---------|
| Compile | `mvn compile` |
| Compile all (incl. tests) | `mvn test-compile` |
| Run tests | `mvn test -Dtest=BpdtsApiTest` |
| Resolve dependencies | `mvn dependency:resolve` |

### Important Caveats

- **External API is down.** The primary API under test (`http://bpdts-test-app-v2.herokuapp.com`) was hosted on Heroku's free tier and has been decommissioned. All 5 tests in `BpdtsApiTest` will fail with 404 or ClassCastException (HTML response parsed as JSON). This is expected and not a code issue.
- **`MyTest.java`** targets `localhost:8080/challenge/v0` which is a separate unrelated API — ignore unless specifically needed.
- **No `MAVEN_OPTS` workaround needed.** With the updated dependencies (Lombok 1.18.26+, compiler target 17), compilation works directly on JDK 17 without `--add-opens` flags.
