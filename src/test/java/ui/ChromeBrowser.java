package ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import support.ConfigHelper;

import java.net.URL;

class ChromeBrowser {

    private String ZAP_HOST_URL = ConfigHelper.valueFor("ZapProxyUrl");
    private Boolean ENABLE_ZAP_SCAN = Boolean.valueOf(ConfigHelper.valueFor("RunWithZap"));


    WebDriver createChromeBrowser() {
        ChromeOptions options = getChromeOptions();
        if (ENABLE_ZAP_SCAN) options.setProxy(getZapProxy());
        try {

            if (ConfigHelper.valueFor("RunInDocker").equals("true")) {
                return new RemoteWebDriver(new URL(ConfigHelper.valueFor("HubURL")), options);
            } else {
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver(options);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private Proxy getZapProxy() {
        return new Proxy()
                .setHttpProxy(ZAP_HOST_URL)
                .setSslProxy(ZAP_HOST_URL);
    }

    private ChromeOptions getChromeOptions() {
        if (ENABLE_ZAP_SCAN)
            return new ChromeOptions()
                    .addArguments("--ignore-certificate-errors")
                    .addArguments("--allow-running-insecure-content");
        else return new ChromeOptions();
    }

}
