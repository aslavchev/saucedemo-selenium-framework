# ADR-004: Allure for Test Reporting

**Status:** Accepted | **Date:** 2026-01-30

---

## Context

Need visual test reports for CI artifacts and stakeholder visibility. Reports must show pass/fail status, screenshots on failure, and be accessible from GitHub Actions. A hiring manager cloning the repo should see professional reporting.

---

## Decision

Use Allure Framework for test reporting with TestNG integration. Reports generated via `mvn allure:report` and stored as CI artifacts. Screenshots captured automatically on test failure.

---

## Alternatives Considered

| Option | Pros | Cons | Verdict |
|--------|------|------|---------|
| **Allure** | Rich visuals, CI integration, trend charts, @Step annotations | Requires CLI for local viewing | ✅ Chosen |
| **ExtentReports** | Good visuals, popular | Less modern, weaker CI integration | ❌ Allure superior |
| **TestNG HTML** | Built-in, no dependencies | Too basic, no screenshots, looks amateur | ❌ Unprofessional |
| **ReportPortal** | Full platform, dashboards | Requires server setup, overkill for portfolio | ❌ Over-engineering |

---

## Code Example: Allure Annotations

```java
@Description("Verify complete checkout flow from login to order confirmation")
@Test(groups = {"e2e", "smoke"})
public void checkoutShowsConfirmationMessage() {
    // Test steps automatically captured in report
}

// In ScreenshotUtils - attach screenshot on failure
byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
Allure.addAttachment(testName, "image/png", new ByteArrayInputStream(screenshot), ".png");
```

---

## CI Integration

```yaml
# GitHub Actions - store report as artifact
- name: Generate Allure Report
  run: mvn allure:report

- name: Upload Report
  uses: actions/upload-artifact@v4
  with:
    name: allure-report
    path: target/site/allure-maven-plugin/
```

---

## Consequences

**Positive:**
- Rich visual reports with screenshots
- Historical trend tracking (when deployed to GitHub Pages)
- @Step and @Description annotations document test intent
- Professional appearance in portfolio

**Trade-offs:**
- Additional dependency (~5MB)
- Requires `allure serve` or CI artifact for viewing

---

**References:** [Allure TestNG](https://docs.qameta.io/allure/#_testng) | [Allure GitHub Action](https://github.com/simple-elf/allure-report-action)

**Last Updated:** 2026-02-01
