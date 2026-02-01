# What We Chose NOT to Automate

Strategic testing decisions include not only what to build, but what to deliberately exclude. These decisions demonstrate risk-based thinking over "coverage theater."

---

## Decision Framework

Before choosing NOT to automate, we evaluated:
1. **Risk Level**: What's the business impact if this breaks?
2. **ROI Analysis**: Dev hours vs. bugs caught
3. **Maintenance Cost**: Long-term upkeep burden
4. **Alternative Strategy**: How do we mitigate risk without automation?

---

## Decision 1: 48 of 60 Test Cases

### Context
We had 60+ manual test cases documented. We automated 17.

### Why We Could Automate All
- Technical feasibility: All 60 tests are automatable
- Framework supports it: Page objects and test data ready
- Time available: Could add more tests

### Why We Chose NOT To
**ROI Analysis**: Diminishing returns after critical paths

| Category | Test Cases | Automated | Why |
|----------|------------|-----------|-----|
| Login critical paths | 3 | 3 | Blocks all functionality |
| Checkout flow | 5 | 5 | Revenue-critical |
| Cart operations | 4 | 4 | Core user journey |
| Product sorting | 1 | 1 | Dropdown interaction pattern |
| Edge case users | 2 | 2 | Demonstrates defect detection |
| UI cosmetic tests | 10+ | 0 | Low risk, manual sufficient |
| Boundary tests | 15+ | 0 | Similar patterns, diminishing returns |
| Security tests (XSS, SQL) | 6 | 0 | Demo site, not production |

**The Trade-off**:
17 stable, maintainable tests > 60 tests with maintenance burden

### Alternative Strategy
Manual testing for cosmetics; security risk accepted for demo site; remaining tests in backlog.

---

## Decision 2: Visual Regression Testing

### Why We Could Automate This
- Tools available: Percy, Applitools, BackstopJS
- Screenshots already captured via Allure
- Effort: ~20 hours setup

### Why We Chose NOT To
**ROI Analysis**: Negative ROI for demo site

- SauceDemo has static, unchanging design
- Estimated visual bugs: ~0/year (it's a demo)
- Cost: 20h setup + false positive management
- Benefit: Near zero for portfolio demonstration

**The Trade-off**:
Prove stability in functional tests > add flaky visual tests

### Alternative Strategy
None needed - SauceDemo design is frozen.

---

## Decision 3: Performance Testing

### Why We Could Automate This
- Tools available: JMeter, k6
- API layer exists
- Effort: ~8 hours

### Why We Chose NOT To
**ROI Analysis**: Not applicable

- SauceDemo is external demo site (not our infrastructure)
- Performance is outside our control
- No production users to protect

**The Trade-off**:
Focus on demonstrating reliability patterns > testing external infrastructure

### Alternative Strategy
Would implement for production apps where we control infrastructure.

---

## Decision 4: Cross-Browser Beyond Chrome

### Why We Could Automate This
- Selenium supports all browsers
- CI matrix builds possible
- Effort: ~4 hours

### Why We Chose NOT To (Phase 1)
**Priority Decision**: Stability first

- SauceDemo works identically across browsers (verified manually)
- Chrome covers 65%+ of real users
- Cross-browser adds CI time without catching SauceDemo bugs

**The Trade-off**:
Fast, reliable Chrome tests > slow multi-browser runs for zero benefit

### Alternative Strategy
Firefox via Selenium Grid in backlog; capability ready when needed.

---

## Decision 5: Accessibility Testing

### Why We Could Automate This
- Tools available: axe-core, pa11y, Lighthouse
- Selenium integration possible via axe-selenium
- Effort: ~12 hours setup + learning curve

### Why We Chose NOT To
**ROI Analysis**: Zero ROI for demo site

- SauceDemo is a demo site with no real users
- No users with accessibility needs depend on it
- Accessibility fixes would go to Sauce Labs, not us
- Portfolio value: minimal (doesn't demonstrate QA judgment)

**The Trade-off**:
Focus on functional test design skills > add accessibility checks we can't act on

### Alternative Strategy
Would implement with axe-core for production apps with real users.

---

## Summary: Automation Philosophy

### What We Automate
- High-risk user journeys (login, checkout, cart)
- Edge case users that reveal defects (problem_user, error_user)
- Interactions that demonstrate framework capability (sort dropdown)

### What We Don't Automate
- Low-risk UI cosmetics (button colors, fonts)
- Diminishing-return boundary tests (50-char name vs 51-char)
- External infrastructure (performance of demo site)
- Security on demo sites (XSS/SQL injection)
- Accessibility on demo sites (no real users affected)

### The Core Principle: Invert the Question

| ❌ Wrong Question | ✅ Right Question |
|-------------------|-------------------|
| "Can we automate this?" | "Can we afford NOT to automate this?" |

The wrong question leads to coverage theater — automating everything possible.
The right question leads to risk-based selection — automating what matters.

**Decision Framework:**

| Answer | Action | Example |
|--------|--------|---------|
| "No, this breaks user access" | Automate | Login tests |
| "No, this breaks revenue" | Automate | Checkout tests |
| "Yes, manual is acceptable" | Don't automate | UI cosmetics |
| "Not applicable to us" | Skip | Performance on external site |

---

## References
- [ADR-005: Test Design Standards](adr/ADR-005-test-design-standards.md)

---

**Last Updated:** 2026-02-01
