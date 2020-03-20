package pages;

import com.google.inject.Inject;
import cucumber.api.Scenario;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import support.ConfigHelper;
import support.helpers.LanguageHelper;

public class BasePage {


    private final WebDriver driver;
    private final WebDriverWait wait;
    private final LanguageHelper languageHelper;
    private static final String BASE_URL = ConfigHelper.valueFor("BaseUrl");

    @Inject
    public BasePage(WebDriver driver, LanguageHelper languageHelper) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 30L);
        this.languageHelper = languageHelper;
        PageFactory.initElements(driver, this);
    }

    public void open(String path) {
        driver.get(BASE_URL + path);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void takeScreenshotOnFailure(Scenario scenario, boolean onlyOnFailure) {
        if (driver != null && (scenario.isFailed() || !onlyOnFailure)) {
            try {
                byte[] screenshot = ((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.BYTES);
                scenario.embed(screenshot, "image/png");
            } catch (Exception couldNotTakeScreenshot) {
                couldNotTakeScreenshot.printStackTrace();
            }
        }
    }

    public WebElement inputField(String attribute, String inputType) {
        String inputXpath = String.format("//input[@%s='%s']", attribute, inputType);
        return getElement(inputXpath);
    }

    private WebElement getElement(String xpath) {
        return getElement("xpath", xpath);
    }

    private WebElement getElement(String using, String locator) {
        By by;
        switch (using) {
            case "css":
                by = By.cssSelector(locator);
                break;
            case "xpath":
                by = By.xpath(locator);
                break;
            default:
                return null;
        }
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
        return driver.findElement(by);
    }

    public WebElement waitUntilClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public WebElement waitUntilVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public Boolean assertUrlIs(String url) {
        return wait.until(ExpectedConditions.urlToBe(url));
    }

    public Boolean assertTextIs(WebElement element, String elementText) {
        return wait.until(ExpectedConditions.textToBePresentInElement(element, elementText));
    }

    public Boolean assertUrlContains(String url) {
        return wait.until(ExpectedConditions.urlContains(url));
    }
}
