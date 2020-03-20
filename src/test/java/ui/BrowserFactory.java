package ui;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.ConfigHelper;

class BrowserFactory {

    private static final Logger logger = LoggerFactory.getLogger(BrowserFactory.class);

    WebDriver getBrowser() {
        String desiredBrowserName = ConfigHelper.valueFor("Browser");
        int retryCount = 0;
        WebDriver driver = null;
        while (retryCount <= 5 && driver == null) {
            logger.info("Trying to create browser instance");
            try {
                switch (desiredBrowserName) {
                    case "firefox":
                        FirefoxBrowser firefoxBrowser = new FirefoxBrowser();
                        driver = firefoxBrowser.createFirefoxBrowser();
                        break;
                    case "chrome":
                        ChromeBrowser chromeBrowser = new ChromeBrowser();
                        driver = chromeBrowser.createChromeBrowser();
                        break;

                }

                logger.info("Created browser instance");
            } catch (Exception e) {
                logger.error(e.getMessage());
                logger.error("Retrying browser creation...");
            }

            retryCount++;
        }

        return driver;
    }

}
