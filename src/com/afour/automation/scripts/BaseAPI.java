package com.afour.automation.scripts;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.afour.automation.actions.Driver;
import com.afour.automation.utilities.APITestDataLoader;
import com.afour.automation.utilities.Log4J;
import com.afour.automation.utilities.Reporting;
import com.afour.automation.utilities.TestDataHandler;

public class BaseAPI {
	
	public static String testName = null;
	private String testCaseName = null;
	public Driver driver;
	static int count = 0;
	public static String currentDir = System.getProperty("user.dir");
	private static Reporting report;
	
	public BaseAPI() {
		report = new Log4J();
	}
	
	@BeforeSuite
	public void init() {

	}
	
	public String getTestCaseName() {
		testCaseName = this.getClass().getSimpleName();
		return testCaseName;
	}

	@DataProvider(name = "csvData")
	public Object[][] csvData() {

		TestDataHandler handler = new TestDataHandler("");
		return handler.getDataMatrix(getTestCaseName() + ".csv");

	}
	
	public HashMap<String, String> jsonData(){
		APITestDataLoader handler =new APITestDataLoader();
       return handler.testDataLoader(currentDir +"/TestData/"+getTestCaseName()+".json");
	
    }


	// Setter for TestCaseName
	@BeforeMethod
	public void setTestName(Method method) {
		testName = method.getName();
		

	}
	
	
	
	}

