package com.sri.automation.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"com.sri.automation.stepdefinitions"},
    tags = "@regression",
    plugin = {
        "pretty",
        "html:test-output/cucumber-reports/regression",
        "json:test-output/cucumber-reports/CucumberRegression.json",
        "junit:test-output/cucumber-reports/CucumberRegression.xml",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
    },
    monochrome = true,
    publish = true
)
public class CucumberRegressionRunner extends AbstractTestNGCucumberTests {
    
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
