# ADR-005: Test Design Standards

**Status:** Accepted | **Date:** 2026-01-30

---

## Context

The SauceDemo test framework needs standards for:
1. Which tests to automate (from 60+ manual test cases)
2. How to name test methods consistently
3. How to structure commit messages

Resources are limited, so selection must be risk-based. Naming must be consistent and readable.

---

## Decision

### Test Selection: Risk-Based Phases

Automate tests in phases based on business risk, not coverage percentage.

| Phase | Tests | Focus | Status |
|-------|-------|-------|--------|
| **Phase 1** | 12 | Critical paths: login, cart, checkout | ✅ Done |
| **Phase 2** | 5 | Edge cases: sort, problem_user, error_user | ✅ Done |
| **Phase 3** | Backlog | Logout, empty validation, other sorts | Deferred |

**Selection Criteria:**

| Factor | Weight | Examples |
|--------|--------|----------|
| Revenue impact | High | Cart, Checkout |
| User access blocking | High | Login |
| Data integrity | Medium | Cart totals |
| UX/Navigation | Low | Back buttons |

### Naming Convention: featureActionCondition

```
featureActionCondition (camelCase)
```

| Name | Breakdown |
|------|-----------|
| `loginSucceedsWithStandardUser` | feature: login, action: succeeds, condition: with standard user |
| `checkoutFailsWithEmptyPostalCode` | feature: checkout, action: fails, condition: with empty postal code |
| `cartEmptiesAfterRemovingItem` | feature: cart, action: empties, condition: after removing item |

**Rules:**
- Start with feature: `login`, `cart`, `checkout`, `products`
- Follow with action: `Succeeds`, `Fails`, `Displays`, `Updates`
- End with condition: `WithStandardUser`, `AfterRemovingItem`
- Avoid: `Button`, `Page`, `Test`, `Verify`, underscores

### Commit Convention: Conventional Commits

| Prefix | Use For |
|--------|---------|
| `feat:` | New feature or test |
| `fix:` | Bug fix |
| `refactor:` | Code change (no new feature/fix) |
| `test:` | Adding or updating tests |
| `docs:` | Documentation only |
| `chore:` | Maintenance (deps, config) |

---

## Alternatives Considered

| Option | Verdict | Reason |
|--------|---------|--------|
| Automate all 60+ tests | ❌ Rejected | Diminishing returns, maintenance burden |
| Automate only smoke tests | ❌ Rejected | Insufficient regression coverage |
| Random selection | ❌ Rejected | No traceability, no risk alignment |
| Risk-based phases | ✅ Chosen | Quality over quantity, maintainable |

---

## Consequences

**Positive:**
- Clear prioritization tied to business risk
- Consistent, readable test names
- Phased approach allows iterative expansion

**Trade-offs:**
- Some edge cases deferred to manual testing
- Phase 2/3 tests require additional page object methods

---

## Future Enhancements

Parallel execution, data providers, retry mechanism, and Allure history tracked separately.

---

**References:** [WHAT-WE-CHOSE-NOT-TO-AUTOMATE.md](../WHAT-WE-CHOSE-NOT-TO-AUTOMATE.md) | [TEST-STRATEGY.md](../TEST-STRATEGY.md)

**Last Updated:** 2026-02-01
