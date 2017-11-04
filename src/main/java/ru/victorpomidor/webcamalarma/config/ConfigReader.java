package ru.victorpomidor.webcamalarma.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigReader {
    private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);

    public static Config readConfig(String configFile) {
        Properties prop = new Properties();
        InputStream input;

        try {
            input = new FileInputStream(configFile);
            prop.load(input);

            return new Config(
                    Integer.parseInt(prop.getProperty("shots_frequency")),
                    Boolean.parseBoolean(prop.getProperty("allow_telegram_notification")),
                    prop.getProperty("telegram_user_id"));
        }  catch (IOException e){
            logger.error("can't read config file");
            return new Config(200, false);
        }
    }
}
