# SriAI Demo Project - Flipkart Automation

A comprehensive Maven-based automation framework for testing Flipkart website using Selenium WebDriver, Cucumber BDD, TestNG, and advanced reporting capabilities.

## 🚀 Features

- **Selenium WebDriver** integration with automatic driver management
- **Cucumber BDD** framework for readable test scenarios
- **TestNG** for test execution and parallel testing
- **Extent Reports** for beautiful HTML reporting
- **Word Document Generation** with step-by-step test documentation
- **Screenshot Capture** on test pass/fail
- **CI/CD Pipeline** ready with GitHub Actions
- **Cross-browser Testing** (Chrome, Firefox, Edge)
- **Parallel Test Execution**

## 📁 Project Structure

```
SriAIDemoProject/
├── src/
│   ├── main/
│   │   ├── java/com/sri/automation/
│   │   │   ├── listeners/          # TestNG listeners
│   │   │   ├── pages/              # Page Object Model classes
│   │   │   └── utils/              # Utility classes
│   │   └── resources/
│   │       └── log4j2.xml          # Logging configuration
│   └── test/
│       ├── java/com/sri/automation/
│       │   ├── runners/            # Cucumber test runners
│       │   └── stepdefinitions/    # Cucumber step definitions
│       └── resources/
│           ├── features/           # Cucumber feature files
│           ├── extent.properties   # Extent report configuration
│           └── extent-config.xml   # Extent report styling
├── .github/workflows/
│   └── ci-cd.yml                   # GitHub Actions pipeline
├── pom.xml                         # Maven dependencies
├── testng.xml                      # TestNG configuration
└── README.md
```

## 🛠️ Prerequisites

- Java 11 or higher
- Maven 3.6+
- Chrome/Firefox/Edge browser

## 📦 Dependencies

- **Selenium WebDriver** 4.15.0
- **TestNG** 7.8.0
- **Cucumber** 7.14.0
- **Extent Reports** 5.0.9
- **Apache POI** 5.2.4 (for Word documents)
- **WebDriverManager** 5.5.3
- **Log4j** 2.20.0

## 🚀 Getting Started

### 1. Clone the Repository
```bash
git clone <repository-url>
cd SriAIDemoProject
```

### 2. Install Dependencies
```bash
mvn clean install
```

### 3. Run Tests

#### Run Smoke Tests (Local)
```bash
mvn clean test -Dbrowser=chrome -Denvironment=local
```

#### Run Regression Tests
```bash
mvn clean test -Dbrowser=chrome -Denvironment=local -Dtest=CucumberRegressionRunner
```

#### Run Tests in Headless Mode (CI/CD)
```bash
mvn clean test -Dbrowser=chrome-headless -Denvironment=cicd -Pcicd
```

### 4. View Reports

After test execution, reports are generated in:
- **Extent Reports**: `test-output/extent-reports/`
- **Cucumber Reports**: `test-output/cucumber-reports/`
- **Word Documents**: `test-output/word-reports/`
- **Screenshots**: `test-output/screenshots/`
- **Logs**: `test-output/logs/`

## 🧪 Test Scenarios

### Smoke Tests (@smoke)
- Product search functionality
- Category navigation
- Product filters

### Regression Tests (@regression)
- Add to cart functionality
- User login scenarios
- End-to-end workflows

## 📊 Reporting Features

### 1. Extent Reports
- Beautiful HTML reports with charts and graphs
- Screenshot attachment on test failures
- Test execution timeline
- System information

### 2. Word Document Reports
- Step-by-step test execution documentation
- Pass/Fail status for each step
- Screenshots embedded in document
- Test summary with statistics

### 3. Screenshot Capture
- Automatic screenshot on test failure
- Screenshot on test success (configurable)
- Organized in timestamped folders

## 🔧 Configuration

### Browser Configuration
Set browser via system property:
```bash
-Dbrowser=chrome          # Chrome browser
-Dbrowser=chrome-headless # Chrome headless
-Dbrowser=firefox         # Firefox browser
-Dbrowser=edge           # Edge browser
```

### Environment Configuration
```bash
-Denvironment=local      # Local execution
-Denvironment=cicd       # CI/CD execution
```

### TestNG Configuration
Edit `testng.xml` to:
- Enable/disable test suites
- Configure parallel execution
- Set test parameters

## 🚀 CI/CD Pipeline

The project includes a GitHub Actions workflow that:
- Runs tests on multiple browsers
- Generates and uploads test reports
- Captures screenshots on failures
- Deploys reports to GitHub Pages

### Trigger Pipeline
- Push to `main` or `develop` branch
- Create pull request to `main`
- Daily scheduled run at 2 AM

## 📝 Writing New Tests

### 1. Add Feature File
Create `.feature` file in `src/test/resources/features/`:
```gherkin
Feature: New Feature
  Scenario: New Test Scenario
    Given I am on the homepage
    When I perform an action
    Then I should see expected result
```

### 2. Implement Step Definitions
Create step definition class in `src/test/java/com/sri/automation/stepdefinitions/`:
```java
@Given("I am on the homepage")
public void i_am_on_the_homepage() {
    // Implementation
}
```

### 3. Add Page Objects
Create page class in `src/main/java/com/sri/automation/pages/`:
```java
public class NewPage {
    // Page elements and methods
}
```

## 🐛 Troubleshooting

### Common Issues

1. **WebDriver Issues**
   - Ensure WebDriverManager is properly configured
   - Check browser compatibility

2. **Test Failures**
   - Check screenshots in `test-output/screenshots/`
   - Review logs in `test-output/logs/`
   - Verify element locators

3. **Report Generation**
   - Ensure proper listeners are configured
   - Check file permissions for report directories

## 📞 Support

For issues and questions:
1. Check the logs in `test-output/logs/`
2. Review the Word document reports for detailed step information
3. Examine screenshots for visual debugging

## 🤝 Contributing

1. Fork the repository
2. Create feature branch
3. Add tests for new functionality
4. Ensure all tests pass
5. Submit pull request

## 📄 License

This project is licensed under the MIT License.
