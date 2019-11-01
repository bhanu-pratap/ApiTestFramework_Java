package com.afour.automation.scripts;

import java.io.File;
import java.lang.reflect.Method;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.afour.automation.actions.Driver;
import com.afour.automation.utilities.SuiteConfiguration;
import com.afour.automation.utilities.TestDataHandler;

/**
 * @author Afourtech
 *
 */
public class Base {

	public static String testName = null;
	private String testCaseName = null;
	public Driver driver;
	static int count = 0;

	public Base() {
		driver = new Driver();
	}

	@BeforeSuite
	public void init() {

	}

	public void deleteFile(File file) {

		File[] f = file.listFiles();
		if (null != f) {

			for (int i = 0; i < f.length; i++) {
				System.out.println(f[i].getName());
				if (f[i].isDirectory()) {
					deleteFile(f[i]);
				} else {
					f[i].delete();
				}
			}
		}

	}

	// Getter for test caseName
	public String getTestCaseName() {
		testCaseName = this.getClass().getSimpleName();
		return testCaseName;
	}

	@DataProvider(name = "csvData")
	public Object[][] csvData() {

		TestDataHandler handler = new TestDataHandler("");
		return handler.getDataMatrix(getTestCaseName() + ".csv");

	}

	// Setter for TestCaseName
	@BeforeMethod
	public void setTestCaseName(Method method) {
		testName = method.getName();
		driver = new Driver();

	}

	@BeforeTest()
	public void open() throws Exception {
		driver.openBrowser(SuiteConfiguration.getBrowser(driver.map.get("Browser")), driver.map.get("url"));
	}

	@AfterTest()
	public void close() throws Exception {
		driver.closeBrowser();
	}

}
