# Test Strategy

## Overview

This document outlines the test strategy for the SauceDemo Selenium Framework, explaining the risk-based approach to test selection and coverage decisions.

## Application Under Test

**SauceDemo** is a sample e-commerce application by Sauce Labs, designed for practicing test automation. It includes:
- User authentication with multiple user types
- Product catalog with sorting/filtering
- Shopping cart functionality
- Checkout flow with form validation

## Risk-Based Test Selection

### High Risk Areas (Prioritized)

| Area | Risk | Test Coverage |
|------|------|---------------|
| **Authentication** | Users unable to access application | Login success/failure, locked users, session handling |
| **Cart Calculation** | Incorrect totals = revenue loss | Add/remove items, quantity changes, price accuracy |
| **Checkout Flow** | Abandoned carts = lost sales | Complete flow, form validation, error recovery |

### Medium Risk Areas

| Area | Risk | Test Coverage |
|------|------|---------------|
| **Product Display** | Poor UX, wrong info shown | Image loading, correct details, inventory status |
| **Sorting/Filtering** | Users can't find products | All sort options, filter combinations |

### Lower Risk Areas (Deferred)

| Area | Why Deferred |
|------|--------------|
| **Performance** | Requires separate tooling (k6, JMeter), different skill set |
| **Accessibility** | Important but requires specialized tooling (axe, WAVE) |
| **Mobile Responsiveness** | SauceDemo is primarily desktop-focused |

## User Types Strategy

SauceDemo provides multiple user types that exhibit different behaviors:

| User | Behavior | Test Purpose |
|------|----------|--------------|
| `standard_user` | Normal functionality | Happy path testing |
| `locked_out_user` | Cannot log in | Error handling, clear messaging |
| `problem_user` | Broken images, wrong data | Graceful degradation, edge cases |
| `error_user` | Triggers errors | Error state handling |
| `performance_glitch_user` | Slow responses | Timeout handling, loading states |

**Strategy:** Test critical flows with `standard_user`, then verify error handling with edge case users.

## Test Categories

### Smoke Tests (P0)
- Application accessible
- Login works for standard user
- Can add item to cart

Run on every commit. Must pass before other tests.

### Core Functionality (P1)
- Complete purchase flow
- All user type authentication
- Cart operations

Run on every PR. Coverage goal: 100%.

### Extended Coverage (P2)
- Sorting variations
- Boundary conditions
- Recovery scenarios

Run nightly or before release.

## Coverage Gaps & Future Work

### Not Currently Tested

| Gap | Reason | Future Plan |
|-----|--------|-------------|
| **API layer** | No documented API | Add if API becomes available |
| **Database state** | No direct DB access | Mock or stub if needed |
| **Payment processing** | SauceDemo uses fake checkout | N/A for this application |
| **Cross-browser** | Time constraint | Add Firefox, Safari in future |

### Acknowledged Limitations

1. **No visual regression** — Would add Applitools or Percy in production
2. **No load testing** — SauceDemo is a demo app, not production
3. **Limited negative testing** — Focus on most common error scenarios

## Test Environment

| Environment | URL | Purpose |
|-------------|-----|---------|
| Production | www.saucedemo.com | Primary test target |
| N/A | N/A | No staging/dev environments available |

## Test Data Management

- **Credentials:** Environment variables or system properties (GitHub Secrets for CI)
- **Product Data:** Dynamic — read from application, no hardcoded product names
- **User Data:** Enum `TestUsers` with all user types

## CI/CD Integration

- **Trigger:** Every push to `main`, all PRs
- **Runtime:** Java 21
- **Artifacts:** Allure reports, failure screenshots
- **Success Criteria:** 100% pass rate

## Metrics & Reporting

| Metric | Target | Measurement |
|--------|--------|-------------|
| Pass Rate | 100% | Allure report |
| Flakiness | 0% | Re-run analysis |
| Execution Time | < 8 min | CI logs |
| Coverage | All P0/P1 scenarios | Manual review |

---

**Last Updated:** 2026-02-01
