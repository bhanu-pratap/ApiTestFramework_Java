/**
 * 
 */
package com.afour.automation.utilities;

import java.io.File;

/**
 * @author Afourtech
 *
 */
public interface Reporting {

	// void init(WebDriver driver);
	public static String currentDir = System.getProperty("user.dir");
	public static File logFile = new File(currentDir + "/AutomationTestNG.log");

	void log(String stepDescription, LogLevel logLevel);

}
