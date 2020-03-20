package stepdefs;

import com.google.inject.Inject;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.BasePage;

public class CommonSteps {
    private static final Logger logger = LoggerFactory.getLogger(CommonSteps.class);


    private BasePage basePage;

    @Inject
    public CommonSteps(BasePage basePage) {
        this.basePage = basePage;
    }

    @Given("^I open browser and navigate to \"(.*)\"$")
    public void openBrowser(String path) {
        basePage.open(path);
    }

    @And("^I type \"(.*)\" in input field of (.*)=\"(.*)\"$")
    public void iTypeInInputFieldOfType(String text, String attribute, String inputType) {
        basePage.inputField(attribute, inputType).sendKeys(text);
    }

    @And("^I click on input field of (.*)=\"(.*)\"$")
    public void iClickOnInputFieldOfType(String attribute, String inputType) {
        basePage.inputField(attribute, inputType).click();
    }

    @After
    public void quitDriver(Scenario scenario) {
        try {
            basePage.takeScreenshotOnFailure(scenario, true);
            if (basePage.getDriver() != null) {
                logger.info("Quitting the driver - " + scenario.getName());
                basePage.getDriver().quit();

            } else {
                logger.error("Failed to Quit the driver since driver is null - " + scenario.getName());
            }
        } catch (Exception e) {
            logger.error("Failed to Quit the driver due to exception - " + scenario.getName());
            e.printStackTrace();
        }
    }

}
