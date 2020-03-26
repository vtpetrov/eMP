package emp.codemonster.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Logger logger = LogManager.getLogger(Config.class.getSimpleName());
    public static Properties config;


    public static void LoadPropertyFiles() {

        config = new Properties();
        InputStream configStream;

        try {

            configStream = new FileInputStream("src/test/resources/Config.properties");
            // Load property file:
            Config.config.load(configStream);

            logger.info("Property file loading - Done");
            logger.debug("Property list: {}", config);


            // Close the input streams
            configStream.close();
            logger.info("Input stream closing - Done");


        } catch (IOException ex) {

            ex.printStackTrace();

        }

    }
}
