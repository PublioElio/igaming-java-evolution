# iGaming Java Evolution

[![Java](https://img.shields.io/badge/Java-26-orange?logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/26/)
[![Gradle](https://img.shields.io/badge/Gradle-9.6.1-02303A?logo=gradle&logoColor=white)](https://gradle.org/)
[![JUnit 5](https://img.shields.io/badge/JUnit-5-25A162?logo=junit5&logoColor=white)](https://junit.org/junit5/)

A **didactic, vanilla Java** project (no frameworks) that showcases, in a practical
way, the major evolutions of the language from **Java 8 to Java 26**.

Instead of toy snippets, the features are put to work inside a realistic — but
deliberately bounded — **iGaming / sports-betting domain**: odds, stakes, betting
tickets and the calculation of potential winnings.

> **Why this exists:** most "what's new in Java X" resources are isolated one-liners.
> This repo is the opposite: one coherent domain, built with **modern Java only**,
> where every major language feature is used where it naturally fits and is marked
> with a comment explaining **which version introduced it** and **what benefit** it
> brings.

---

## Design principles

- **Modern Java only.** No legacy / before-after code. The idiomatic modern form is
  the only form shown. Each major feature carries a didactic comment.
- **Vanilla.** No Spring, no framework. Logging via the JDK's `System.Logger`
  (zero dependencies). Third-party libraries are allowed only when they add real value.
- **Correct by domain.** Money is modeled with `BigDecimal`, not `double`, to avoid
  floating-point rounding errors — as any real financial system must.
- **Verifiable.** Every type is covered by JUnit 5 tests; each module will also expose
  a runnable `main` demo.

---

## Java features showcased

Features are introduced progressively, as the domain needs them.

| Feature | Version | Where it is used |
|---------|---------|------------------|
| `var` (local type inference) | Java 10 | local variables in calculations, tests and demos |
| Text blocks | Java 15 | multi-line ticket "receipt" in the demo *(planned)* |
| **Records** | Java 16 | `Odds`, `Stake`, `Selection`, and the bet types |
| Pattern matching for `instanceof` | Java 16 | targeted validations *(planned)* |
| Sealed interfaces | Java 17 | `Bet` and `TicketStatus` closed hierarchies *(planned)* |
| Pattern matching for `switch` | Java 21 | winnings calculation by bet type *(planned)* |
| Record patterns (deconstruction) | Java 21 | destructuring records inside the `switch` *(planned)* |

> Minor, isolated API tweaks are deliberately omitted — the focus is the *major*
> language evolutions.

---

## Requirements

- **JDK 26** (the Gradle toolchain will provision/validate it).
- No global Gradle install needed — use the bundled wrapper (`./gradlew`).

---

## Getting started

```bash
# Run the tests
./gradlew test

# Run the application entry point
./gradlew run
```

---

## Project structure

Single Gradle module, one package per domain area.

```
src/
├── main/java/com/adrianodiaz/igamingjavaevolution/
│   ├── IgamingJavaEvolutionApplication.java   # entry point
│   ├── shared/
│   │   └── Messages.java                       # centralized string constants
│   └── betengine/                              # first domain module
│       ├── Odds.java                           # record + validation (BigDecimal)
│       └── Stake.java                          # record + validation (BigDecimal)
└── test/java/com/adrianodiaz/igamingjavaevolution/
    └── betengine/
        ├── OddsTest.java
        └── StakeTest.java

docs/
└── design/
    └── 2026-07-23-bet-engine-design.md         # full design of the bet-engine module
```

---

## Roadmap

The project grows module by module. The first module, `bet-engine`, is where the
core value types and the winnings logic live.

**`bet-engine` — in progress**

- [x] `Odds` — decimal odds value object (record, compact-constructor validation)
- [x] `Stake` — wagered amount value object (record, `BigDecimal`)
- [ ] `Selection` — a pick composing `Odds`
- [ ] `Bet` — sealed interface: `SingleBet`, `AccumulatorBet`, `SystemBet`
- [ ] `TicketStatus` — sealed interface: `Placed`, `Settled`, `Cancelled`
- [ ] `BettingTicket` — aggregate record
- [ ] `WinningsCalculator` — `switch` pattern matching + record patterns
- [ ] `BetEngineDemo` — runnable `main` with a text-block receipt

**Planned next**

- Web playground (JDK `com.sun.net.httpserver.HttpServer` + plain HTML/JS, no
  framework) to exercise the engine from a browser.
- Further domain modules exploring more language features.

See [`docs/design/2026-07-23-bet-engine-design.md`](docs/design/2026-07-23-bet-engine-design.md)
for the detailed design and step-by-step build guide.

---

## License

Educational project — free to read, learn from, and adapt.
