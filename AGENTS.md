# AGENTS.md

## Cursor Cloud specific instructions

### Project Overview

Java/Maven API test suite that validates endpoints of the BPDTS Test App REST API. No services to run locally — this is a pure test automation project.

### Prerequisites (already installed in VM snapshot)

- **JDK 17** (`openjdk-17-jdk`) — project targets source/target 14 but JDK 17 works with `--add-opens` flags.
- **Apache Maven 3.8+** (`maven` system package)
- **MAVEN_OPTS** must include `--add-opens` flags for Lombok compatibility (configured in `~/.bashrc`).

### Key Commands

| Action | Command |
|--------|---------|
| Compile | `mvn compile` |
| Compile all (incl. tests) | `mvn test-compile` |
| Run tests | `mvn test -Dtest=BpdtsApiTest` |
| Resolve dependencies | `mvn dependency:resolve` |

### Important Caveats

- **External API is down.** The primary API under test (`http://bpdts-test-app-v2.herokuapp.com`) was hosted on Heroku's free tier and has been decommissioned. All 5 tests in `BpdtsApiTest` will fail with 404 or ClassCastException (HTML response parsed as JSON). This is expected and not a code issue.
- **Lombok + JDK 17.** Lombok 1.18.12 (transitive dependency via rest-assured) requires `--add-opens` JVM flags to compile on JDK 17+. These are set in `MAVEN_OPTS` in `~/.bashrc`. If you see `IllegalAccessError` from `lombok.javac.apt.LombokProcessor`, ensure `MAVEN_OPTS` is exported (run `source ~/.bashrc`).
- **pom.xml version fixes.** The original `pom.xml` had incorrect dependency versions (`commons-io:2.8` and `testng:7.8`). These were corrected to `2.8.0` and `7.8.0` respectively, as the original versions don't exist on Maven Central.
- **`MyTest.java`** targets `localhost:8080/challenge/v0` which is a separate unrelated API — ignore unless specifically needed.
