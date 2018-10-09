package com;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DynamicPropertyReader {

    private static final Logger LOGGER = LogManager.getLogger("DynamicPropertyReader");

    private PropertiesConfiguration configuration;

    public void notifyChanges() {
        String serverPath = null;
        if (isWindows()) {
            serverPath = "C:\\Users\\Machunagendradurgp\\Documents//application-uj1.properties";
        } else {
            serverPath = "/home/bitnami/application-uj1.properties";
        }
        try {
                configuration = new PropertiesConfiguration(serverPath);
            } catch (ConfigurationException ce) {
                ce.printStackTrace();
            }

            FileChangedReloadingStrategy fileChangedReloadingStrategy = new FileChangedReloadingStrategy();
            fileChangedReloadingStrategy.setRefreshDelay(1000);
            configuration.setReloadingStrategy(fileChangedReloadingStrategy);
    }

    public String getProperty(String key) {
        return (String)configuration.getProperty(key);
    }

    /**
     * @return
     */
    public static boolean isWindows() {
        String os = System.getProperty("os.name").toLowerCase();
        return (os.indexOf("win") >= 0);
    }
}
