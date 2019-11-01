package com.afour.automation.utilities;

/**
 * @author Afourtech
 *
 */
public class SuiteConfiguration {

	public static String currentDir = System.getProperty("user.dir");
	public static BrowserType getBrowser(String browser) {
		if (browser.equals("CHROME")) {
			return BrowserType.CHROME;
		} else if (browser.equals("FIREFOX")) {
			return BrowserType.FIREFOX;
		} else if (browser.equals("IE")) {
			return BrowserType.IE;
		} else {
			return null;
		}

	}

}
