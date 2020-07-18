package io.github.micwan88.homeconf;

public class ConfConst {
	public static final String SYSPROP_WEBDRIVER_CHROMEDRIVER_PATH = "webdriver.chrome.driver";
	public static final String SYSPROP_WEBDRIVER_CHROMEDRIVER_LOGFILE = "webdriver.chrome.logfile";
	public static final String SYSPROP_WEBDRIVER_CHROMEDRIVER_VERBOSELOG = "webdriver.chrome.verboseLogging";
	
	/**
	 * Chrome argument switch
	 * https://peter.sh/experiments/chromium-command-line-switches/
	 */
	public static final String CHROMEDRIVER_ARG_SWITCH_USRDIR = "user-data-dir";
	public static final String CHROMEDRIVER_ARG_SWITCH_DISABLE_PRESENTATION_API = "disable-presentation-api";
	public static final String CHROMEDRIVER_ARG_SWITCH_DISABLE_BG_NETWORK = "disable-background-networking";
	public static final String CHROMEDRIVER_ARG_SWITCH_DISABLE_DEFAULT_APP = "disable-default-apps";
	/**
	 * Chromedriver Options
	 * https://chromedriver.chromium.org/capabilities
	 */
	public static final String CHROMEDRIVER_OPTION_EXCLUDE_SWITCH = "excludeSwitches";
	
	public static final String APP_PROPERTIES_KEY_BROWSERPATH = "homeConf.browserPath";
	public static final String APP_PROPERTIES_KEY_WEBDRIVERPATH = "homeConf.webDriverPath";
	public static final String APP_PROPERTIES_KEY_CONF_URL = "homeConf.confUrl";
	public static final String APP_PROPERTIES_KEY_CAST_DEVICE_NAME = "homeConf.castDeviceName";
	public static final String APP_PROPERTIES_KEY_CHROME_USR_PROFILE_PATH = "homeConf.chromeUserProfilePath";
}
