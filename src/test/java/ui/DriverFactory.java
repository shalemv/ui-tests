package ui;

import com.google.inject.Provider;
import org.openqa.selenium.WebDriver;

public class DriverFactory implements Provider<WebDriver> {

    @Override
    public WebDriver get() {
        BrowserFactory browserFactory = new BrowserFactory();
        WebDriver driver = browserFactory.getBrowser();
        if (driver != null) {
            driver.manage().deleteAllCookies();
            driver.manage().window().fullscreen();
        } else{
            throw new RuntimeException("Could not create a browser instance");
        }
        return driver;
    }
}