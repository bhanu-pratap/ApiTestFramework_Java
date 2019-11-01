package com.afour.automation.scripts;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.afour.automation.pages.MakeMyTripPage;

/**
 * @author Afourtech
 *
 */
public class TC_1 extends Base {
	MakeMyTripPage page;

	@BeforeTest
	public void tests() {
		page = new MakeMyTripPage();
	}

	@Test
	public void clickLink() {
		page.clickLink();
	}

}
