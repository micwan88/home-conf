package io.github.micwan88.homeconf;

import java.io.File;
import java.util.Arrays;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.micwan88.helperclass4j.AppPropertiesUtil;

public class ConfDaemon {
	private static final Logger myLogger = LogManager.getLogger(ConfDaemon.class);
	
	private ChromeDriverService chromeService;
	private ChromeDriver chromeDriver;
	private String browserPath = null;
	private String webDriverPath = null;
	private String confUrl = null;
	private String castDeviceName = null;
	private String chromeUserProfilePath = null;
	
	public static void main(String[] args) {
		ConfDaemon confDaemon = new ConfDaemon();
		
		try {
			confDaemon.startProcess();
		} catch (Exception e) {
			myLogger.debug("Error", e);
		}
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
			System.exit(returnCode);
		
		initializeDriver(browserPath, webDriverPath, chromeUserProfilePath);
		
		myLogger.debug("Sleep for 15 sec ...");
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			//Do nothing
		}
		
		chromeDriver.get("confUrl");
		
		if (chromeUserProfilePath != null && castDeviceName != null
				&& !chromeUserProfilePath.trim().equals("") && !castDeviceName.trim().equals(""))
		chromeDriver.selectCastSink(castDeviceName);
		
		myLogger.debug("Sleep for 25 sec before quit the chrome ...");
		try {
			Thread.sleep(25000);
		} catch (InterruptedException e) {
			//Do nothing
		}
		
		if (chromeDriver != null) {
			chromeDriver.close();
			chromeService.stop();
		}
		
		myLogger.debug("ConfDaemon end");
	}
	
	private void initializeDriver(String browserPath, String webDriverPath, String userProfilePath) {
		ChromeDriverService.Builder chromeServiceBuilder = new ChromeDriverService.Builder();
		chromeService = chromeServiceBuilder
			.usingDriverExecutable(new File(webDriverPath))
			.usingAnyFreePort()
			.withWhitelistedIps("").build();
		
		ChromeOptions chromeOptions = new ChromeOptions();
		
		if (browserPath != null && !browserPath.trim().equals(""))
			chromeOptions.setBinary(browserPath);
		
		if (userProfilePath != null && !userProfilePath.trim().equals(""))
			chromeOptions.addArguments(ConfConst.CHROMEDRIVER_ARG_SWITCH_USRDIR + "=" + userProfilePath);
		
		/**
		 * Chromedriver default will disable some feature of chrome just as popup-blocking
		 * We need to exclude some of the options, for checking what options have been disabled
		 * Please check with url of chrome://version
		 */
		chromeOptions.setExperimentalOption(ConfConst.CHROMEDRIVER_OPTION_EXCLUDE_SWITCH, 
				Arrays.asList(ConfConst.CHROMEDRIVER_ARG_SWITCH_DISABLE_PRESENTATION_API, ConfConst.CHROMEDRIVER_ARG_SWITCH_DISABLE_BG_NETWORK, 
						ConfConst.CHROMEDRIVER_ARG_SWITCH_DISABLE_DEFAULT_APP));
		try {
			chromeDriver = new ChromeDriver(chromeService, chromeOptions);
		} catch (Exception e) {
			myLogger.error("Cannot create chrome session", e);
		}
	}
	
	public int initParams(Properties appProperties) {
		myLogger.debug("Init Params ...");
		
		String tempStr = appProperties.getProperty(ConfConst.APP_PROPERTIES_KEY_WEBDRIVERPATH);
		if (tempStr != null && !tempStr.trim().equals(""))
			webDriverPath = tempStr.trim();
		
		tempStr = appProperties.getProperty(ConfConst.APP_PROPERTIES_KEY_BROWSERPATH);
		if (tempStr != null && !tempStr.trim().equals(""))
			browserPath = tempStr.trim();
		
		tempStr = appProperties.getProperty(ConfConst.APP_PROPERTIES_KEY_CONF_URL);
		if (tempStr != null && !tempStr.trim().equals(""))
			confUrl = tempStr.trim();
		
		tempStr = appProperties.getProperty(ConfConst.APP_PROPERTIES_KEY_CAST_DEVICE_NAME);
		if (tempStr != null && !tempStr.trim().equals(""))
			castDeviceName = tempStr.trim();
		
		tempStr = appProperties.getProperty(ConfConst.APP_PROPERTIES_KEY_CHROME_USR_PROFILE_PATH);
		if (tempStr != null && !tempStr.trim().equals(""))
			chromeUserProfilePath = tempStr.trim();
		
		myLogger.debug("AppProp - {}: {}" , ConfConst.APP_PROPERTIES_KEY_BROWSERPATH, browserPath);
		myLogger.debug("AppProp - {}: {}" , ConfConst.APP_PROPERTIES_KEY_WEBDRIVERPATH, webDriverPath);
		myLogger.debug("AppProp - {}: {}" , ConfConst.APP_PROPERTIES_KEY_CONF_URL, confUrl);
		myLogger.debug("AppProp - {}: {}" , ConfConst.APP_PROPERTIES_KEY_CAST_DEVICE_NAME, castDeviceName);
		myLogger.debug("AppProp - {}: {}" , ConfConst.APP_PROPERTIES_KEY_CHROME_USR_PROFILE_PATH, chromeUserProfilePath);
		
		if (webDriverPath == null) {
			myLogger.error("{} cannot be empty", ConfConst.APP_PROPERTIES_KEY_WEBDRIVERPATH);
			return -1;
		}
		
		if (confUrl == null) {
			myLogger.error("{} cannot be empty", ConfConst.APP_PROPERTIES_KEY_CONF_URL);
			return -2;
		}
		
		return 0;
	}
}
