# ADR-002: TestNG over JUnit 5

**Status:** Accepted | **Date:** 2026-01-30

---

## Context

Need a test framework for organizing and running Selenium tests. Must support parallel execution, data-driven testing, and test grouping for CI/CD optimization. Both TestNG and JUnit 5 are viable for Java automation.

---

## Decision

Use TestNG for test organization and execution. Leverage built-in features: `@DataProvider` for data-driven tests, `groups` for smoke/regression separation, and native parallel execution support.

---

## Alternatives Considered

| Option | Pros | Cons | Verdict |
|--------|------|------|---------|
| **TestNG** | Native parallelization, @DataProvider, groups, enterprise standard | Slightly older than JUnit 5 | ✅ Chosen |
| **JUnit 5** | Modern, good community, @ParameterizedTest | Parallel execution requires config, less common in SDET roles | ❌ Less enterprise adoption |
| **Cucumber** | BDD layer, readable by non-technical | Adds complexity, no BA stakeholders for this project | ❌ Overhead without value |

**Note:** TestNG has stronger enterprise adoption for UI automation. JUnit 5 is more common for unit testing.

---

## Code Example: TestNG Groups

```java
// Smoke tests run on every PR (~2 min)
@Test(groups = {"smoke", "regression"})
public void loginSucceedsWithStandardUser() { }

// Full regression runs nightly (~5 min)
@Test(groups = {"regression"})
public void problemUserHasBrokenProductImages() { }
```

```bash
mvn test -Dgroups=smoke      # Fast PR feedback
mvn test -Dgroups=regression # Full nightly run
```

---

## Consequences

**Positive:**
- Native parallel execution (no extra dependencies)
- Flexible test grouping for CI/CD optimization
- @DataProvider for data-driven tests without external files
- Common in enterprise environments (interview relevance)

**Trade-offs:**
- XML configuration can be verbose (mitigated: use Maven surefire instead)
- Slightly older ecosystem than JUnit 5

---

**References:** [TestNG Documentation](https://testng.org/) | [Selenium Test Practices](https://www.selenium.dev/documentation/test_practices/)

**Last Updated:** 2026-02-01
