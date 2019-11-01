package com.afour.automation.actions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.afour.automation.utilities.BrowserType;
import com.afour.automation.utilities.IdentifierMethod;
import com.afour.automation.utilities.Log4J;
import com.afour.automation.utilities.LogLevel;
import com.afour.automation.utilities.Reporting;

import testData.TestDataLoader;

/**
 * @author Afourtech
 *
 */
public class Driver {
	private static WebDriver webDriver = null;
	public static WebDriverWait wait = null;
	public HashMap<String, String> map = null;
	private static Reporting report;

	public Driver() {
		map = getConfigData();
		report = new Log4J();
	}

	/**
	 * Gets Config data from Config.properties file
	 * 
	 * @return Hashtable
	 */
	public HashMap<String, String> getConfigData() {
		HashMap<String, String> hashMap = null;
		hashMap = new TestDataLoader().testDataLoader("Config.properties");
		return hashMap;
	}

	/**
	 * Sets up the browser to be used for running automation
	 * 
	 * @param browser
	 *            : See {@link BrowserType}
	 */
	public void setWebDriver(BrowserType browser) {
		DesiredCapabilities desiredCapabilities = null;
		switch (browser) {
		case FIREFOX:
			desiredCapabilities = DesiredCapabilities.firefox();
			break;
		case CHROME:
			desiredCapabilities = DesiredCapabilities.chrome();
			break;
		case IE:
			desiredCapabilities = DesiredCapabilities.internetExplorer();
			break;
		case IEEDGE:
			throw new NotImplementedException("IEEDGE has not yet been implemented");
		default:
			throw new NotImplementedException(browser + " Browser has not been Implemented");
		}
		setWebDriver(browser, desiredCapabilities);
	}

	/**
	 * Sets up the Webdriver for running automation using the supplied browser
	 * type and desired capabilities
	 * 
	 * @param browser
	 * @param desiredCapabilities
	 */
	public void setWebDriver(BrowserType browser, DesiredCapabilities desiredCapabilities) {
		// This code will be implemented if Selenium Grid needs to be
		// implemented
		/*
		 * if (SuiteConfiguration.useGrid){ URL remoteDriverUrl = null; try {
		 * remoteDriverUrl = new URL(SuiteConfiguration.remoteDriverUrlString);
		 * } catch (MalformedURLException e) { e.printStackTrace(); } webDriver
		 * = new RemoteWebDriver(remoteDriverUrl,desiredCapabilities); }
		 */
		switch (browser) {
		case FIREFOX:
			webDriver = new FirefoxDriver(desiredCapabilities);
			break;
		case IE:
			System.setProperty("webdriver.ie.driver", "Drivers/IEDriverServer.exe");
			webDriver = new InternetExplorerDriver(desiredCapabilities);
			break;
		case CHROME:
			System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver.exe");
			webDriver = new ChromeDriver(desiredCapabilities);
			break;
		case IEEDGE:
			throw new NotImplementedException("IEEDGE has not yet been implemented");
		default:
			throw new NotImplementedException(browser + " Browser has not been Implemented");
		}
		wait = new WebDriverWait(getWebDriver(), Integer.parseInt(map.get("TimeOut")));
		webDriver.manage().timeouts().implicitlyWait(Long.parseLong(map.get("ImplicitWait")), TimeUnit.SECONDS);
		webDriver.manage().window().maximize();
	}

	/**
	 * Gets the current instance of webDriver
	 * 
	 * @return WebDriver
	 */
	public WebDriver getWebDriver() {
		return webDriver;
	}

	/**
	 * Opens the browser
	 * 
	 * @param browser
	 * @param url
	 */
	public void openBrowser(BrowserType browser, String url) {
		setWebDriver(browser);
		getWebDriver().get(url);
	}

	/**
	 * Clicks on element
	 * 
	 * @param elementIdentifier
	 * @throws Exception
	 */
	public void click(HashMap<String, String> elementIdentifier) {

		WebElement element = find(elementIdentifier);
		Assert.assertTrue(element.isDisplayed() && element.isEnabled());
		element.click();

	}

	/**
	 * Closes the browser
	 * 
	 */
	public void closeBrowser() {
		getWebDriver().quit();
	}

	/**
	 * Closes the window
	 * 
	 */
	public void closeWindow() {
		getWebDriver().close();
	}

	/**
	 * Deletes all the cookies
	 * 
	 */
	public void deleteAllCookies() {
		getWebDriver().manage().deleteAllCookies();
	}

	/**
	 * Deletes the given cookie
	 * 
	 * @param cookieName
	 */
	public void deleteCookie(Cookie cookieName) {
		getWebDriver().manage().deleteCookie(cookieName);
	}

	/**
	 * Double clicks on the identified web element
	 *
	 * @param elementIdentifier
	 * @throws Exception
	 */
	public void doubleClick(HashMap<String, String> elementIdentifier) throws Exception {
		WebElement element = find(elementIdentifier);
		Actions action = new Actions(getWebDriver());
		Assert.assertTrue(element.isDisplayed() & element.isEnabled());
		action.doubleClick(element).perform();
	}

	/**
	 * Drags the source element to target element
	 * 
	 * @param sourceIdentifier
	 * @param targetIdentifier
	 */
	public void dragAndDrop(HashMap<String, String> sourceIdentifier, HashMap<String, String> targetIdentifier)
			throws Exception {
		WebElement sourceElement = find(sourceIdentifier);
		WebElement targetElement = find(targetIdentifier);
		Actions action = new Actions(getWebDriver());
		Assert.assertTrue(sourceElement.isDisplayed() & sourceElement.isEnabled() & targetElement.isDisplayed()
				& targetElement.isEnabled());
		action.clickAndHold(sourceElement).moveToElement(targetElement).perform();
		waitForTime(2);
		action.release(targetElement).build().perform();
	}

	/**
	 * Clicks on web element using javascript
	 * 
	 * @param elementIdentifier
	 * @throws Exception
	 */
	public void clickJScript(HashMap<String, String> elementIdentifier) throws Exception {
		WebElement element = find(elementIdentifier);
		JavascriptExecutor js = (JavascriptExecutor) getWebDriver();
		Assert.assertTrue(element.isDisplayed() & element.isEnabled());
		js.executeScript("arguemts[0].click();", element);
	}

	/**
	 * Executes javascript on identified web element
	 * 
	 * @param elementIdentifier
	 * @param script
	 * @throws Exception
	 */
	public void executeJavaScript(HashMap<String, String> elementIdentifier, String script) throws Exception {
		WebElement element = find(elementIdentifier);
		JavascriptExecutor js = (JavascriptExecutor) getWebDriver();
		Assert.assertTrue(element.isDisplayed() & element.isEnabled());
		js.executeScript(script, element);
	}

	/**
	 * Finds all WebElements
	 *
	 * @param elementIdentifier
	 * @return List<WebElement>
	 */
	public List<WebElement> findAll(HashMap<String, String> elementIdentifier) {
		List<WebElement> foundElements = null;
		IdentifierMethod[] identifierMethods = IdentifierMethod.values();
		Set<IdentifierMethod> method = new HashSet<>();
		for (IdentifierMethod value : identifierMethods) {
			method.add(value);
		}
		Iterator<IdentifierMethod> iterator = method.iterator();
		IdentifierMethod locator = null;
		do {
			locator = iterator.next();
			if (elementIdentifier.get(locator.toString()) != null) {
				foundElements = findAll(elementIdentifier, locator);
			}
		} while (foundElements == null && iterator.hasNext());
		if (foundElements == null) {
			report.log("No such element could be found with existing repository locators " + elementIdentifier,
					LogLevel.ERROR);
			throw new NoSuchElementException("No such element could be found with existing repository locators");
		}
		return foundElements;
	}

	/**
	 * Finds all WebElements using locators
	 * 
	 * @param elementIdentifier
	 * @param method
	 * @return List<WebElement>
	 */
	private List<WebElement> findAll(HashMap<String, String> elementIdentifier, IdentifierMethod method) {
		List<WebElement> foundElements = null;
		try {
			switch (method) {
			case ID:
				foundElements = getWebDriver().findElements(By.id(elementIdentifier.get(method.toString())));
				break;
			case XPATH:
				foundElements = getWebDriver().findElements(By.xpath(elementIdentifier.get(method.toString())));
				break;
			case NAME:
				foundElements = getWebDriver().findElements(By.name(elementIdentifier.get(method.toString())));
				break;
			case LINKTEXT:
				foundElements = getWebDriver().findElements(By.linkText(elementIdentifier.get(method.toString())));
				break;
			case PARTIALLINKTEXT:
				foundElements = getWebDriver()
						.findElements(By.partialLinkText(elementIdentifier.get(method.toString())));
				break;
			case CLASSNAME:
				foundElements = getWebDriver().findElements(By.className(elementIdentifier.get(method.toString())));
				break;
			case CSSSELECTOR:
				foundElements = getWebDriver().findElements(By.cssSelector(elementIdentifier.get(method.toString())));
				break;
			case TAGNAME:
				foundElements = getWebDriver().findElements(By.tagName(elementIdentifier.get(method.toString())));
				break;
			default:
				find(elementIdentifier);
			}
		} catch (Exception e) {
			report.log("Element not available with " + method + " : " + elementIdentifier.get(method.toString()) + "\n"
					+ e.getMessage(), LogLevel.WARN);
		}
		return foundElements;
	}

	/**
	 * Find element
	 * 
	 * @param elementIdentifier
	 *            present in element repository
	 * @return WebElement
	 * @throws Exception
	 */
	public WebElement find(HashMap<String, String> elementIdentifier) {
		WebElement foundElement = null;
		IdentifierMethod[] identifierMethods = IdentifierMethod.values();
		Set<IdentifierMethod> method = new HashSet<>();
		for (IdentifierMethod value : identifierMethods) {
			method.add(value);
		}
		Iterator<IdentifierMethod> iterator = method.iterator();
		IdentifierMethod locator = null;

		do {
			locator = iterator.next();
			if (elementIdentifier.get(locator.toString()) != null) {
				foundElement = find(elementIdentifier, locator);
			}
		} while (foundElement == null && iterator.hasNext());

		if (foundElement == null) {
			report.log("No such element could be found with existing repository locators " + elementIdentifier,
					LogLevel.ERROR);
			throw new NoSuchElementException("No such element could be found with existing repository locators");
		}

		return foundElement;
	}

	/**
	 * Find element using locators
	 * 
	 * @param IdentifierMethod
	 *            : See {@link IdentifierMethod}
	 * 
	 * @param elementIdentifier
	 * @return WebElement
	 */
	private WebElement find(HashMap<String, String> elementIdentifier, IdentifierMethod method) {
		WebElement foundElement = null;
		try {
			switch (method) {
			case ID:
				foundElement = getWebDriver().findElement(By.id(elementIdentifier.get(method.toString())));
				break;
			case XPATH:
				foundElement = getWebDriver().findElement(By.xpath(elementIdentifier.get(method.toString())));
				break;
			case NAME:
				foundElement = getWebDriver().findElement(By.name(elementIdentifier.get(method.toString())));
				break;
			case LINKTEXT:
				foundElement = getWebDriver().findElement(By.linkText(elementIdentifier.get(method.toString())));
				break;
			case PARTIALLINKTEXT:
				foundElement = getWebDriver().findElement(By.partialLinkText(elementIdentifier.get(method.toString())));
				break;
			case CLASSNAME:
				foundElement = getWebDriver().findElement(By.className(elementIdentifier.get(method.toString())));
				break;
			case CSSSELECTOR:
				foundElement = getWebDriver().findElement(By.cssSelector(elementIdentifier.get(method.toString())));
				break;
			case TAGNAME:
				foundElement = getWebDriver().findElement(By.tagName(elementIdentifier.get(method.toString())));
				break;
			default:
				find(elementIdentifier);
			}
		} catch (Exception e) {
			report.log("Element not available with " + method + " : " + elementIdentifier.get(method.toString()) + "\n"
					+ e.getMessage(), LogLevel.WARN);
		}
		return foundElement;
	}

	/**
	 * Gets the text on the web element
	 * 
	 * @param elementIdentifier
	 * @return String
	 * @throws Exception
	 */
	public String getText(HashMap<String, String> elementIdentifier) throws Exception {
		WebElement element = find(elementIdentifier);
		return element.getText();
	}

	/**
	 * Navigates to previous page in browser history
	 * 
	 */
	public void goBack() {
		getWebDriver().navigate().back();
	}

	/**
	 * Navigates to next page in browser history
	 * 
	 */
	public void goForward() {
		getWebDriver().navigate().forward();
	}

	/**
	 * Takes the mouse curser to identified web element
	 * 
	 * @param identifier
	 * @throws Exception
	 */
	public void mouseHover(HashMap<String, String> elementIdentifier) throws Exception {
		WebElement element = find(elementIdentifier);
		Actions action = new Actions(getWebDriver());
		Assert.assertTrue(element.isDisplayed());
		action.moveToElement(element).perform();
	}

	/**
	 * Navigates to given URL
	 * 
	 * @param URL
	 */
	public void navigateToURL(String URL) {
		getWebDriver().navigate().to(URL);
	}

	/**
	 * Refreshes the current page
	 * 
	 */
	public void refresh() {
		getWebDriver().navigate().refresh();
	}

	/**
	 * Right clicks on identified web element
	 * 
	 * @param elementIdentifier
	 * @param identifier
	 */
	public void rightClick(HashMap<String, String> elementIdentifier) {
		WebElement element = find(elementIdentifier);
		Actions action = new Actions(getWebDriver());
		action.contextClick(element).perform();
	}

	/**
	 * Selects element in dropdown by visible text
	 * 
	 * @param elementIdentifier
	 * @param visibleText
	 */
	public void selectItemByVisibleText(HashMap<String, String> elementIdentifier, String visibleText)
			throws Exception {
		WebElement element = find(elementIdentifier);
		Assert.assertTrue(element.isDisplayed() & element.isEnabled());
		new Select(element).selectByVisibleText(visibleText);
	}

	/**
	 * Selects element in dropdown by index
	 * 
	 * @param elementIdentifier
	 * @param index
	 */
	public void selectItemByIndex(HashMap<String, String> elementIdentifier, int index) throws Exception {
		WebElement element = find(elementIdentifier);
		Assert.assertTrue(element.isDisplayed() & element.isEnabled());
		new Select(element).selectByIndex(index);
	}

	/**
	 * Selects element in dropdown by value
	 * 
	 * @param elementIdentifier
	 * @param value
	 */
	public void selectItemByValue(HashMap<String, String> elementIdentifier, String value) throws Exception {
		WebElement element = find(elementIdentifier);
		Assert.assertTrue(element.isDisplayed() & element.isEnabled());
		new Select(element).selectByValue(value);
	}

	/**
	 * Sets text on the web element
	 * 
	 * @param elementIdentifier
	 * @param text
	 */
	public void setText(HashMap<String, String> elementIdentifier, String text) {
		WebElement element = find(elementIdentifier);
		Assert.assertTrue(element.isDisplayed() & element.isEnabled());
		element.clear();
		element.sendKeys(text);
	}

	/**
	 * Checks the checkbox or radiobutton
	 * 
	 * @param elementIdentifier
	 */
	public void check(HashMap<String, String> elementIdentifier) {
		WebElement element = find(elementIdentifier);
		Assert.assertTrue(element.isDisplayed() & element.isEnabled() & !element.isSelected());
	}

	/**
	 * Unchecks the checkbox or radiobutton
	 * 
	 * @param elementIdentifier
	 */
	public void uncheck(HashMap<String, String> elementIdentifier) {
		WebElement element = find(elementIdentifier);
		Assert.assertTrue(element.isDisplayed() & element.isEnabled() & element.isSelected());
		element.click();
	}

	/**
	 * Submits the web element
	 * 
	 * @param elementIdentifier
	 */
	public void submit(HashMap<String, String> elementIdentifier) {
		WebElement element = find(elementIdentifier);
		Assert.assertTrue((element.isDisplayed() & element.isEnabled()));
		element.submit();
	}

	/**
	 * Switches to default content
	 * 
	 */
	public void switchToDefaultContent() {
		getWebDriver().switchTo().defaultContent();
	}

	/**
	 * Switches to identified frame
	 * 
	 * @param elementIdentifier
	 */
	public void switchToFrame(HashMap<String, String> elementIdentifier) {
		WebElement element = find(elementIdentifier);
		Assert.assertTrue(element.isDisplayed() & element.isEnabled());
		getWebDriver().switchTo().frame(element);
	}

	/**
	 * Switches to given window title
	 * 
	 * @param windowTitle
	 */
	public void switchToWindow(String windowTitle) {
		Set<String> handles = getWebDriver().getWindowHandles();
		for (Iterator<String> it = handles.iterator(); it.hasNext();) {
			getWebDriver().switchTo().window(windowTitle);
			Assert.assertEquals(getWebDriver().getTitle(), windowTitle);
		}
	}

	/**
	 * Verifies whether checkbox is selected or not
	 * 
	 * @param elementIdentifier
	 * @return boolean
	 */
	public boolean verifyCheckbox(HashMap<String, String> elementIdentifier) {
		WebElement element = find(elementIdentifier);
		Assert.assertTrue(element.isDisplayed() & element.isEnabled() & element.isSelected());
		return true;
	}

	/**
	 * Verifies the given page title
	 * 
	 * @param pageTitle
	 * @return boolean
	 */
	public boolean verifyPageTitle(String pageTitle) {
		Assert.assertEquals(getWebDriver().getTitle(), pageTitle);
		return true;
	}

	/**
	 * Verifies the text of identified web element
	 * 
	 * @param elementIdentifier
	 * @param text
	 * @return boolean
	 */
	public boolean verifyText(HashMap<String, String> elementIdentifier, String text) {
		WebElement element = find(elementIdentifier);
		Assert.assertTrue(element.isDisplayed());
		Assert.assertEquals(element.getText(), text);
		return true;
	}

	/**
	 * Verifies the current URL
	 * 
	 * @param URL
	 * @return boolean
	 */
	public boolean verifyURL(String URL) {
		Assert.assertEquals(getWebDriver().getCurrentUrl(), URL);
		return true;
	}

	/**
	 * Clicks on web element when element is clickable
	 * 
	 *
	 * @param identifier
	 */
	public void clickWhenReady(HashMap<String, String> elementIdentifier) {
		WebElement element = find(elementIdentifier);
		element = wait.until(ExpectedConditions.elementToBeClickable(element));
		element.click();
	}

	/**
	 * Waits for partiular element for specified amount of time
	 * 
	 * @param elementIdentifier
	 * @param timeout
	 * @return WebElement
	 */
	public WebElement waitForElementExplicitly(HashMap<String, String> elementIdentifier, int timeout) {
		WebElement element = null;
		if (wait == null) {
			wait = new WebDriverWait(getWebDriver(), Integer.parseInt(map.get("TimeOut")));
		}
		if (elementIdentifier.get("ID") != null) {
			element = wait.until(ExpectedConditions.elementToBeClickable(By.id(elementIdentifier.get("ID"))));
		} else if (elementIdentifier.get("XPATH") != null) {
			element = wait.until(ExpectedConditions.elementToBeClickable(By.id(elementIdentifier.get("XPATH"))));
		} else if (elementIdentifier.get("NAME") != null) {
			element = wait.until(ExpectedConditions.elementToBeClickable(By.id(elementIdentifier.get("NAME"))));
		} else if (elementIdentifier.get("LINKTEXT") != null) {
			element = wait.until(ExpectedConditions.elementToBeClickable(By.id(elementIdentifier.get("LINKTEXT"))));
		} else if (elementIdentifier.get("PARTIALLINKTEXT") != null) {
			element = wait
					.until(ExpectedConditions.elementToBeClickable(By.id(elementIdentifier.get("PARTIALLINKTEXT"))));
		} else if (elementIdentifier.get("CLASSNAME") != null) {
			element = wait.until(ExpectedConditions.elementToBeClickable(By.id(elementIdentifier.get("CLASSNAME"))));
		} else if (elementIdentifier.get("TAGNAME") != null) {
			element = wait.until(ExpectedConditions.elementToBeClickable(By.id(elementIdentifier.get("TAGNAME"))));
		} else if (elementIdentifier.get("CSSSELECTOR") != null) {
			element = wait.until(ExpectedConditions.elementToBeClickable(By.id(elementIdentifier.get("CSSSELECTOR"))));
		} else {
			// log ----not in repository
			System.out.println("Element identifier not in repository");
		}
		return element;

	}

	/**
	 * Verifies element is visible or not
	 * 
	 * @param elementIdentifier
	 * @return boolean
	 */
	public boolean isElementDisplayed(HashMap<String, String> elementIdentifier) {
		WebElement element = find(elementIdentifier);
		return element.isDisplayed();
	}

	/**
	 * Verifies element is selected or not
	 * 
	 * @param elementIdentifier
	 * @return boolean
	 */
	public boolean isElementSelected(HashMap<String, String> elementIdentifier) {
		WebElement element = find(elementIdentifier);
		return element.isSelected();
	}

	/**
	 * Verifies element is enabled or not
	 * 
	 * @param elementIdentifier
	 * @return boolean
	 */
	public boolean isElementEnabled(HashMap<String, String> elementIdentifier) {
		WebElement element = find(elementIdentifier);
		return element.isEnabled();
	}

	/**
	 * Switches to identified Alert
	 * 
	 * @return Alert
	 */
	public Alert switchToAlert() {
		Alert alert = getWebDriver().switchTo().alert();
		return alert;
	}

	/**
	 * Waits for specified amount of time in seconds
	 * 
	 * @param timeout
	 * @throws InterruptedException
	 */
	public void waitForTime(int timeout) throws InterruptedException {
		Thread.sleep(timeout * 1000);
	}

	/**
	 * Verify Alert Message
	 * 
	 * @param String
	 * @return boolean
	 * 
	 */
	public boolean verifyAlertMessage(String messageText) {
		Alert alert = getWebDriver().switchTo().alert();
		Assert.assertTrue(alert.getText().equals(messageText));
		return true;
	}

	/**
	 * Gets the text on the web element
	 * 
	 * @param elementIdentifier
	 * @param attribute
	 * @return String
	 */
	public String getTextByAttribute(HashMap<String, String> elementIdentifier, String attribute) {
		WebElement element = find(elementIdentifier);
		Assert.assertTrue(element.isDisplayed());
		return element.getAttribute(attribute);
	}

	/**
	 * Verifies the text of identified web element
	 * 
	 * @param elementIdentifier
	 * @param text
	 * @return boolean
	 * @throws Exception
	 */
	public boolean verifyTextAttribute(HashMap<String, String> elementIdentifier, String text) throws Exception {
		WebElement element = find(elementIdentifier);
		Assert.assertTrue(element.isDisplayed());
		Assert.assertEquals(element.getAttribute("value"), text);
		return true;
	}
}
