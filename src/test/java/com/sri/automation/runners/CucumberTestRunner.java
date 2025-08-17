package com.sri.automation.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"com.sri.automation.stepdefinitions"},
    tags = "@smoke",
    plugin = {
        "pretty",
        "html:test-output/cucumber-reports",
        "json:test-output/cucumber-reports/Cucumber.json",
        "junit:test-output/cucumber-reports/Cucumber.xml",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
    },
    monochrome = true,
    publish = true
)
public class CucumberTestRunner extends AbstractTestNGCucumberTests {
    
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
