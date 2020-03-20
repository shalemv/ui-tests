package support.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class LanguageHelper {

    private static HashMap<String, Properties> propertyMap = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(LanguageHelper.class);


    public static  String valueFor(final String lang, final String keyName){
        String property = null;
        try {
            property = getInstance(lang).getProperty(keyName);
        }catch (Throwable e){
            logger.info(e.getMessage());
        }
        return property;
    }

    private static Properties getInstance(String lang) throws Throwable {
        if(propertyMap.get(lang) == null){
            populateEnvProperties(lang);
        }
        return propertyMap.get(lang);
    }

    private static void populateEnvProperties(final String language) throws Throwable {
        Properties properties = new Properties();
        String propertiesFilePath = String.format("src/test/resources/config/locale/%s.properties", language);
        File propertiesFile = new File(propertiesFilePath);
        try{
            InputStream inputStream = new FileInputStream(propertiesFile);
            properties.load(inputStream);
            inputStream.close();
            propertyMap.put(language, properties);

        }catch (FileNotFoundException e){
            logger.info(e.getMessage());

        }
    }
}
