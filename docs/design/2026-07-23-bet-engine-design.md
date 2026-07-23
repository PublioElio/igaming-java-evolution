# Design: `bet-engine` Module

> First module of the **iGaming Java Evolution** project: a didactic **vanilla Java**
> project (no frameworks) that showcases, in a practical way, the major evolutions of
> the language from Java 8 to Java 26.

Date: 2026-07-23

---

## 1. Context and goal

`bet-engine` models the core betting domain: tickets, selections, odds and the
calculation of potential winnings. It is the first module because the rest of the
system (`game-rules`, `wallet-service`, etc.) will build on these types.

The goal is **not** a complete betting engine, but to **put the major Java changes
into practice** by modeling a realistic yet bounded domain.

## 2. Design decisions (project-wide)

- **Didactic approach:** modern Java only. No legacy code. Every *major change* is
  marked with a comment stating **which version** introduced it and **what benefit**
  it brings.
- **Vanilla:** no Spring. Libraries such as Guava are allowed if they add value.
  Logging via `System.Logger` (JDK, zero dependencies).
- **Structure:** a single Gradle module, one package per domain area.
- **Verification:** runnable demos (`main`) + JUnit 5 tests per module.
- **Sequence:** `bet-engine` first; the web frontend (JDK `HttpServer` + plain
  HTML/JS) as a later module.

## 3. Java features this module showcases

| Feature | Version | Where it is used |
|---------|---------|------------------|
| **`var`** (local type inference) | Java 10 | local variables in calculations and demo |
| **Text blocks** | Java 15 | multi-line ticket "receipt" in the demo |
| **Records** | Java 16 | `Odds`, `Stake`, `Selection` and the bet types |
| **Pattern matching for `instanceof`** | Java 16 | targeted validations |
| **Sealed interfaces** | Java 17 | `Bet` and `TicketStatus` (closed hierarchies) |
| **Pattern matching for `switch`** | Java 21 | winnings calculation by bet type |
| **Record patterns** (deconstruction) | Java 21 | destructuring records inside the `switch` |

> Minor changes (e.g. isolated API tweaks) are deliberately omitted.

## 4. Domain model

Base package: `com.adrianodiaz.igamingjavaevolution.betengine`

### Value types (records)

- **`Odds`** — European decimal odds. Validation in the compact constructor: must be
  `> 1.0`. Feature: *record* (Java 16), *compact constructor*.
- **`Stake`** — the amount wagered. Validation: `> 0`. Feature: *record*.
- **`Selection`** — a pick: `String description` + `Odds odds`.

### Bet hierarchy (sealed)

- **`Bet`** — a `sealed interface` that **permits only** three implementations:
  - **`SingleBet`** (record) — a single `Selection`.
  - **`AccumulatorBet`** (record) — `List<Selection>`; all must win and the odds are
    multiplied together.
  - **`SystemBet`** (record) — `List<Selection>` + `int minCorrect`; a **simplified**
    version of a system bet (combinations of size `minCorrect`).

### Ticket state (sealed)

- **`TicketStatus`** — a `sealed interface` permitting:
  - **`Placed`** (record) — just placed.
  - **`Settled`** (record) — resolved; carries the computed payout (`double payout`).
  - **`Cancelled`** (record) — cancelled; carries a reason (`String reason`).

### Aggregate

- **`BettingTicket`** (record) — `Stake stake` + `Bet bet` + `TicketStatus status`.

### Logic

- **`WinningsCalculator`** — class with the method
  `double potentialWinnings(Bet bet, Stake stake)`. Computes the **potential
  winnings** (maximum payout if everything wins) using a `switch` with pattern
  matching + record patterns. This is the didactic heart of the module.

### Demo

- **`BetEngineDemo`** — `main` that builds example tickets, computes winnings and
  prints them (with a text block as a receipt).

## 5. Potential winnings calculation

`WinningsCalculator.potentialWinnings(bet, stake)` decides with a modern `switch`:

- **`SingleBet(selection)`** → `stake * selection.odds().value()`
- **`AccumulatorBet(selections)`** → `stake * (product of all odds)`
- **`SystemBet(selections, minCorrect)`** → simplified version: the `stake` is split
  across the number of combinations `C(n, minCorrect)`, and the potential winnings
  (if everything wins) is the sum, over each combination, of
  `(stake / numCombinations) * (product of that combination's odds)`.

The `switch` uses **record patterns** to destructure directly in the `case`
(e.g. `case SingleBet(Selection s) -> ...`), and is **exhaustive without a
`default`** because `Bet` is `sealed` (the compiler knows there are only 3 types).

## 6. Step-by-step guide (implementation)

> You write it; I explain each class before every step.

1. **`Odds` (record)** — the simplest value type. We learn: records, automatic
   fields, compact constructor with validation.
2. **`Stake` (record)** — repeats the `Odds` pattern. Reinforces the concept.
3. **`Selection` (record)** — a record that **composes** another record (`Odds`).
4. **`Bet` (sealed interface)** + the 3 records that implement it — introduces
   `sealed` / `permits` and `record ... implements`.
5. **`TicketStatus` (sealed interface)** + `Placed` / `Settled` / `Cancelled`.
6. **`BettingTicket` (record)** — the aggregate that ties everything together.
7. **`WinningsCalculator`** — the `switch` with pattern matching and record patterns.
   The step richest in Java 21 features.
8. **JUnit tests** — one per bet type (single, accumulator, system) plus record
   validations (invalid odds throws, etc.).
9. **`BetEngineDemo`** — the `main` with runnable examples and the text-block receipt.

Each class will carry comments such as:

```java
// Java 16+: 'record' generates the constructor, getters, equals, hashCode and toString.
// Previously all of that boilerplate had to be written by hand.
```

## 7. Resulting file structure

```
src/
├── main/java/com/adrianodiaz/igamingjavaevolution/
│   └── betengine/
│       ├── Odds.java
│       ├── Stake.java
│       ├── Selection.java
│       ├── Bet.java              (sealed) + SingleBet, AccumulatorBet, SystemBet
│       ├── TicketStatus.java     (sealed) + Placed, Settled, Cancelled
│       ├── BettingTicket.java
│       ├── WinningsCalculator.java
│       └── BetEngineDemo.java
└── test/java/com/adrianodiaz/igamingjavaevolution/
    └── betengine/
        └── WinningsCalculatorTest.java
```

## 8. Out of scope (for now)

- Persistence, database, networking.
- The web frontend (later module).
- Real settlement of sporting events (we use "potential winnings", not settlement
  against real results).
- The other epic modules (`game-rules`, `wallet-service`, etc.).
