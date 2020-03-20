package support;

import courgette.api.CourgetteOptions;
import courgette.api.CourgetteRunLevel;
import courgette.api.junit.Courgette;
import cucumber.api.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Courgette.class)
@CourgetteOptions(
    threads = 10,
    runLevel = CourgetteRunLevel.SCENARIO,
    rerunFailedScenarios = true,
    rerunAttempts = 1,
    showTestOutput = true,
    reportTargetDir = "build",
    cucumberOptions = @CucumberOptions(
        features = "src/test/resources/features",
        tags = {"not @wip"},
        glue = {"stepdefs", "steps"},
        strict = true,
        monochrome = false,
        plugin = {
                "pretty",
                "json:build/cucumber/cucumber.json",
                "junit:build/cucumber/cucumber.xml",
                "html:build/cucumber/cucumber.html"
        }

    ))
public class TestSuite {
}
