# SauceDemo Selenium Framework

[![Selenium Tests](https://github.com/aslavchev/saucedemo-selenium-framework/actions/workflows/tests.yml/badge.svg)](https://github.com/aslavchev/saucedemo-selenium-framework/actions/workflows/tests.yml)
[![Live Report](https://img.shields.io/badge/Allure-Live%20Report-blueviolet)](https://aslavchev.github.io/saucedemo-selenium-framework/)
[![Java](https://img.shields.io/badge/Java-21-orange)](https://openjdk.org/)
[![Selenium](https://img.shields.io/badge/Selenium-4.40-green)](https://www.selenium.dev/)
[![TestNG](https://img.shields.io/badge/TestNG-7.12-blue)](https://testng.org/)

Test automation framework for [SauceDemo](https://www.saucedemo.com) e-commerce application.

## Quick Start

```bash
# Clone repository
git clone https://github.com/aslavchev/saucedemo-selenium-framework.git
cd saucedemo-selenium-framework

# Run tests
mvn clean test

# Run smoke tests only
mvn test -Dgroups=smoke

# Run headless
mvn test -Dheadless=true

# Generate Allure report
mvn allure:serve
```

## Project Structure

```
src/
├── main/java/com/saucedemo/
│   ├── config/
│   │   └── TestConfig.java       # Environment configuration
│   ├── pages/
│   │   └── BasePage.java         # Base page object with common methods
│   └── utils/
│       └── ScreenshotUtils.java  # Screenshot capture utility
│
└── test/java/com/saucedemo/
    ├── tests/
    │   └── BaseTest.java         # Test setup/teardown
    ├── listeners/
    │   └── TestListener.java     # Screenshot on failure
    └── testdata/
        └── TestUsers.java        # Test user credentials
```

## Tech Stack

| Technology | Purpose |
|------------|---------|
| Java 21 | Language |
| Selenium WebDriver 4.40 | Browser automation |
| TestNG | Test framework |
| Allure | Test reporting |
| WebDriverManager | Driver management |
| Maven | Build tool |
| GitHub Actions | CI/CD |

## Configuration

Create a `.env` file (optional — defaults work for SauceDemo):

```env
SAUCE_USERNAME=standard_user
SAUCE_PASSWORD=secret_sauce
BASE_URL=https://www.saucedemo.com
BROWSER=chrome
HEADLESS=false
```

Or use environment variables / Maven properties:

```bash
mvn test -Dheadless=true -Dbrowser=firefox
```

## Test Users

| User | Behavior |
|------|----------|
| `standard_user` | Normal functionality |
| `locked_out_user` | Cannot log in |
| `problem_user` | Broken images, wrong data |
| `error_user` | Triggers errors |
| `performance_glitch_user` | Slow responses |

## CI/CD

Tests run automatically on:
- Push to `main` or `develop`
- Pull requests to `main`

Artifacts: Allure reports, failure screenshots

---

**Author:** Alex Slavchev
**Website:** [aslavchev.com](https://aslavchev.com)
