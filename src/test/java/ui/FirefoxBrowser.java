package ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import support.ConfigHelper;

import java.net.URL;

class FirefoxBrowser {

    WebDriver createFirefoxBrowser()  {
        try {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            if (ConfigHelper.valueFor("RunInDocker").equals("true")) {
                return new RemoteWebDriver(new URL(ConfigHelper.valueFor("HubURL")), firefoxOptions);

            } else {

                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver(firefoxOptions);
            }
        }catch (Throwable throwable){
            throwable.printStackTrace();
            return null;
        }
    }
}
