package com.afour.automation.pages;

import java.util.HashMap;

import com.afour.automation.actions.Driver;
import com.afour.automation.utilities.ElementsRepositoryHandler;

/**
 * @author Afourtech
 *
 */
public class MakeMyTripPage {
	HashMap<String, HashMap<String, String>> repository = null;
	ElementsRepositoryHandler efr = null;
	// public static WebDriver webDriver = null;
	public Driver driver;

	public MakeMyTripPage() {
		driver = new Driver();
		efr = new ElementsRepositoryHandler();
		try {
			repository = efr.readXml("Repository");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickLink() {
		driver.click(repository.get("YourPackage"));
	}

}
