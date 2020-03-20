package support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigHelper {

    private static ConfigHelper configHelper;
    private static String  envName;
    private static String  regionId;
    private static Properties properties;
    private static final Logger logger = LoggerFactory.getLogger(ConfigHelper.class);


    public static  String valueFor(final String keyName){
        String property = null;
        try {
            property = getInstance().getProperty(keyName);
        }catch (Throwable e){
            logger.info(e.getMessage());
        }
        return property;
    }

    private static ConfigHelper getInstance() throws Throwable{
        if(configHelper == null){
            properties = new Properties();
            envName = System.getProperty("ENV", "dev");
            populateEnvProperties(envName);
            configHelper = new ConfigHelper();
        }
        return  configHelper;
    }

    public static void populateEnvProperties(final String requiredEnvName) throws Throwable {
        String propertiesFilePath = String.format("src/test/resources/config/%s.properties", envName);
        File propertiesFile = new File(propertiesFilePath);
        try{
            InputStream inputStream = new FileInputStream(propertiesFile);
            properties.load(inputStream);
            inputStream.close();

        }catch (FileNotFoundException e){
            logger.info(e.getMessage());

        }


    }

    private String getProperty(final String key) {
        String value = properties.getProperty(key);
        if(value == null){
            throw new Error(String.format("Key %s not configured for environment %s", key,envName));
        }
        return value.replaceAll("REGION_ID", regionId);
    }
}
