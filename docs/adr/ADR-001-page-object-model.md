# ADR-001: Page Object Model for UI Abstraction

**Status:** Accepted | **Date:** 2026-01-30

---

## Context

SauceDemo has multiple pages (Login, Products, Cart, Checkout). Without abstraction, locators would be duplicated across tests, and any UI change would break multiple files. Need a pattern that isolates element locators from test logic.

---

## Decision

Implement Page Object Model (POM) with a BasePage class containing common actions (click, type, waits). Each application page gets its own class. Tests never call Selenium directly.

---

## Alternatives Considered

| Option | Pros | Cons | Verdict |
|--------|------|------|---------|
| **POM with BasePage** | Industry standard, single point of change, tests read like user stories | More initial setup, more files | ✅ Chosen |
| **Screenplay Pattern** | Higher abstraction, great for BDD | Overkill for 17 tests, steep learning curve, niche | ❌ Over-engineering |
| **Page Factory (@FindBy)** | Less boilerplate | Implicit wait issues, deprecated in Selenium 4 | ❌ Less control |

---

## Code Example: POM vs Raw Selenium

```java
// ❌ Raw Selenium in test (Don't do this)
driver.findElement(By.id("user-name")).sendKeys("standard_user");
driver.findElement(By.id("password")).sendKeys("secret_sauce");
driver.findElement(By.id("login-button")).click();

// ✅ Page Object (Do this)
loginPage.login("standard_user", "secret_sauce");
```

The test reads like a user story. Locator changes affect only LoginPage.java.

---

## Consequences

**Positive:**
- Locator changes isolated to single file
- Tests read like user workflows
- Industry-standard pattern

**Trade-offs:**
- ~2 hours setup before first test
- 5 page classes + 4 test classes to navigate

---

**References:** [Selenium POM Docs](https://www.selenium.dev/documentation/test_practices/encouraged/page_object_models/) | [Martin Fowler](https://martinfowler.com/bliki/PageObject.html)

**Last Updated:** 2026-02-01
