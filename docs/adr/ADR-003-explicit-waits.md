# ADR-003: Explicit Waits over Implicit Waits

**Status:** Accepted | **Date:** 2026-01-30

---

## Context

SauceDemo has dynamic elements that load asynchronously (product images, cart updates, checkout confirmations). Need a wait strategy that handles timing without causing flaky tests. Wrong choice here is the #1 cause of test flakiness.

---

## Decision

Use explicit waits (`WebDriverWait` + `ExpectedConditions`) exclusively. No implicit waits. No `Thread.sleep()`. Waits are encapsulated in BasePage methods so tests never deal with timing.

---

## Alternatives Considered

| Option | Pros | Cons | Verdict |
|--------|------|------|---------|
| **Explicit waits** | Precise control, clear timeouts, no hidden interactions | More verbose | ✅ Chosen |
| **Implicit waits** | Less code, global timeout | Mixes unpredictably with explicit waits, harder to debug | ❌ Debugging nightmare |
| **Thread.sleep()** | Simple to write | Flaky, slow, unprofessional, wastes CI time | ❌ Anti-pattern |
| **Fluent waits** | Custom polling intervals | Explicit waits sufficient for this scope | ❌ Over-engineering |

---

## Code Example: Right vs Wrong

```java
// ❌ Thread.sleep (Don't do this)
driver.findElement(By.id("login-button")).click();
Thread.sleep(3000);  // Wastes 3 seconds EVERY time, still flaky
driver.findElement(By.id("inventory_container"));

// ❌ Implicit wait (Don't do this)
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
// Now EVERY findElement waits 10s on failure - slow and unpredictable

// ✅ Explicit wait (Do this - in BasePage)
protected WebElement waitForVisible(By locator) {
    return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
}
// Waits only as long as needed, fails fast with clear message
```

---

## Consequences

**Positive:**
- Zero flaky tests from timing issues
- Clear timeout expectations in code
- Fast tests (waits only as long as needed)
- Demonstrates professional practice

**Trade-offs:**
- More verbose than implicit waits
- Must remember to use BasePage methods (enforced by code review)

---

**References:** [Selenium Waits](https://www.selenium.dev/documentation/webdriver/waits/) | [Flaky Test Prevention](https://testing.googleblog.com/2020/12/test-flakiness-one-of-main-challenges.html)

**Last Updated:** 2026-02-01
