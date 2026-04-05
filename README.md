# NorId

A Kotlin Multiplatform library for working with Norwegian national identity numbers (fødselsnummer). One codebase, two targets: **JVM** and **JavaScript** with auto-generated TypeScript definitions.

## Why this exists

Norwegian identity numbers follow a deceptively complex specification. The 11-digit format encodes date of birth, gender, and century — validated by a weighted checksum algorithm. Century determination alone has three overlapping rules depending on the individual number range and two-digit year.

Most libraries solve this for one platform. NorId solves it once, in shared Kotlin, and compiles to both JVM bytecode and ES modules with TypeScript types. The same validation logic, the same generation algorithm, the same test suite — running natively on backend and frontend.

## Features

- **Validate** any 11-digit fødselsnummer against the official checksum specification
- **Generate** valid fødselsnummer by age, birth year, or exact date of birth
- **Extract** gender, date of birth, and current age from existing numbers
- **Cross-platform** — single `commonMain` source compiled to JVM and JS/TS targets
- **Type-safe** — auto-generated `.d.ts` definitions for TypeScript consumers

## Quick start

### Kotlin / Java

```kotlin
// Validate
isValidSsn("05079426778") // true

// Generate
val ssn = Ssn.forAge(30)                        // random valid SSN for a 30-year-old
val ssn = Ssn.forYear(1994, Gender.Male)         // random valid SSN born in 1994
val ssn = Ssn.forDateOfBirth(dob, Gender.Female) // specific date of birth

// Extract
val ssn = Ssn("05079426778")
ssn.gender()      // Gender.Male
ssn.dateOfBirth() // "050794"
ssn.age()         // 31
```

### TypeScript / JavaScript

The JS target produces ES modules with full TypeScript definitions — same API, same guarantees.

## How it works

A Norwegian fødselsnummer is structured as `DDMMYYIIIKK`:

| Segment | Digits | Purpose |
|---------|--------|---------|
| `DD` | 1-2 | Day of birth |
| `MM` | 3-4 | Month of birth |
| `YY` | 5-6 | Two-digit birth year |
| `III` | 7-9 | Individual number (encodes gender and century) |
| `KK` | 10-11 | Control digits (weighted checksum) |

**Gender** is derived from the individual number: odd = male, even = female.

**Century** is determined by the individual number range combined with the two-digit year:

| Individual number | Year digits | Century |
|-------------------|-------------|---------|
| 000-499 | * | 1900s |
| 500-749 | 55-99 | 1800s |
| 500-749 | 00-54 | 2000s |
| 750-999 | 41-99 | 1900s |
| 750-999 | 00-40 | 2000s |

**Control digits** are calculated using two weighted sums (mod 11) across the first nine digits — a scheme similar to Luhn but with fixed weight sequences `[3,7,6,1,8,9,4,5,2]` and `[5,4,3,2,7,6,5,4,3,2]`.

Supported birth year range: **1855 - 2039**.

## Architecture

```
src/
  commonMain/kotlin/
    Gender.kt              # Gender enum with parity-based derivation
    public.kt              # Top-level API: isValidSsn, getGender, ssnFromAge, ssnFromYear
    ssn/
      Ssn.kt               # Core class — validation, generation, info extraction
      DateOfBirth.kt        # Date parsing, age calculation, century-aware construction
      IndividualNumber.kt   # 3-digit segment: gender derivation, century lookup, range-based generation
      ControlDigits.kt      # Weighted checksum calculation and verification
      Utils.kt              # SSN string parsing, date helpers
  commonTest/kotlin/
    ssn/
      SsnTest.kt            # Generation across full year range (1855-2039), gender, age
      ControlDigitsTest.kt  # Checksum verification against 29 real-format SSNs
      IndividualNumberTest.kt
      UtilsTest.kt
```

All source lives in `commonMain` — no platform-specific code. The Kotlin Multiplatform Gradle plugin compiles to JVM (with JUnit Platform) and JS (ES modules + TypeScript definitions).

## Build

```bash
./gradlew build    # builds both targets, runs all tests
```

## Tech stack

- **Kotlin Multiplatform** 2.1.20
- **kotlinx-datetime** for cross-platform date arithmetic
- **Gradle** with Kotlin DSL
- **GitHub Actions** CI on every push
