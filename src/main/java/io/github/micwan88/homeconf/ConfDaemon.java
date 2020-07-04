package io.github.micwan88.homeconf;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.micwan88.helperclass4j.AppPropertiesUtil;

public class ConfDaemon {
	private static final Logger myLogger = LogManager.getLogger(ConfDaemon.class);
	
	private String browserPath = null;
	private String webDriverPath = null;
	
	public static void main(String[] args) {
		ConfDaemon confDaemon = new ConfDaemon();
		confDaemon.startProcess();
	}
	
	public void startProcess() {
		myLogger.debug("ConfDaemon start");
		
		AppPropertiesUtil appPropertyUtil = new AppPropertiesUtil();
		Properties appProperties = appPropertyUtil.getAppProperty();
		if (appProperties == null) {
			myLogger.error("Cannot load appProperties: {}", AppPropertiesUtil.APP_PROPERTY_FILE);
			System.exit(-1);
		}
		
		int returnCode = initParams(appProperties);
		
		if (returnCode != 0)
			System.exit(-2);
		
		ChromeDriver chromeDriver = initializeDriver(browserPath, webDriverPath);
		
		myLogger.debug("Sleep for 5 sec before quit the chrome ...");
		try {
			Thread.sleep(50000);
		} catch (InterruptedException e) {
			//Do nothing
		}
		
		if (chromeDriver != null)
			chromeDriver.quit();
		
		myLogger.debug("ConfDaemon end");
	}
	
	private ChromeDriver initializeDriver(String browserPath, String webDriverPath) {
		System.setProperty(ConfConst.WEBDRIVER_CHROMEDRIVER_PATH, webDriverPath);
		
		ChromeOptions chromeOptions = new ChromeOptions();
		if (browserPath != null && !browserPath.trim().equals(""))
			chromeOptions.setBinary(browserPath);
		
		ChromeDriver chromeDriver = null;
		
		try {
			chromeDriver = new ChromeDriver(chromeOptions);
		} catch (Exception e) {
			myLogger.error("Cannot create chrome session", e);
		}
		
		return chromeDriver;
	}
	
	public int initParams(Properties appProperties) {
		myLogger.debug("Init Params ...");
		
		String tempStr = appProperties.getProperty(ConfConst.APP_PROPERTIES_KEY_WEBDRIVERPATH);
		if (tempStr != null && !tempStr.trim().equals(""))
			webDriverPath = tempStr.trim();
		
		tempStr = appProperties.getProperty(ConfConst.APP_PROPERTIES_KEY_BROWSERPATH);
		if (tempStr != null && !tempStr.trim().equals(""))
			browserPath = tempStr.trim();
		
		myLogger.debug("AppProp - {}: {}" , ConfConst.APP_PROPERTIES_KEY_BROWSERPATH, browserPath);
		myLogger.debug("AppProp - {}: {}" , ConfConst.APP_PROPERTIES_KEY_WEBDRIVERPATH, webDriverPath);
		
		if (webDriverPath == null) {
			myLogger.error("{} cannot be empty", ConfConst.APP_PROPERTIES_KEY_WEBDRIVERPATH);
			return -1;
		}
		
		return 0;
	}
}
