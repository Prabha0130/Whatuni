package com.qa.whatuni.util;

import static org.testng.Assert.assertEquals;

import javax.swing.text.Element;

import org.jsoup.Connection.Base;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.qa.whatuni.base.BasePage;

/**
 * @author Prabhakaran.P
 * 
 *
 */
public class ElementUtils extends BasePage {
	public WebDriver driver;
	public JavascriptExecutor js;
	public WebDriverWait wait;
	public Actions action;
	public JavaScriptUtil javaScriptUtil;

	public ElementUtils(WebDriver driver) {
		this.driver = driver;
		action = new Actions(driver);
	}

	public WebElement findElement(By locator) {
		WebElement element;

		try {
			if (locator != null) {
				element = driver.findElement(locator);
				return element;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void sendKeys(By locator, String value, String fieldName) {
		try {
			findElement(locator).sendKeys(value);
			BasePage.logger.pass(value + "is entered in " + fieldName);

		} catch (Exception e) {
			BasePage.logger.fail("Unable to perform sendKeys" + fieldName,
					MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot()).build());
			e.printStackTrace();
		}
	}

	public void clickElement(By locator, String ElementName) {
		try {
			findElement(locator).click();
			BasePage.logger.pass(ElementName + " is clicked");
		} catch (Exception e) {
			BasePage.logger.fail(ElementName + " is not clickable",
					MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot()).build());
			// Assert.fail();

		}
	}

	public void VerifyCurrentPageTitle(String expectedTitle, String PageName) {
		String actualTitle;
		try {
			waitUntilTitleContains(expectedTitle, 20);
			actualTitle = driver.getTitle();
			if (actualTitle.equals(expectedTitle)) {
				BasePage.logger.pass(
						" Successfully navigated to " + PageName + " current url is:" + getCurrentPageURL(),
						MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot()).build());
			}
		}

		catch (Exception e) {
			BasePage.logger
					.fail(PageName + " Navigation Failed, Page title not matched",
							MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot()).build())
					.fail(e.getStackTrace().toString());
			BasePage.logger.generateLog(Status.FAIL, e.getStackTrace().toString());
			Assert.fail(PageName + "Navigation Failed title not matched");
		}

	}

	public String getCurrentPageURL() {
		try {
			String url = driver.getCurrentUrl();
			return url;
		} catch (Exception e) {
			BasePage.logger.info("Unable to fetch current page URL");
		}
		return null;
	}

	public void verifyElementText(By locator, String expectedString, String ElementName) {
		try {
			if (getText(locator).equals(expectedString)) {
				BasePage.logger.pass(ElementName + "Matches with expected String");
				Assert.assertEquals(getText(locator), expectedString);
			}

		} catch (Exception e) {
			BasePage.logger.fail("Actual result matched with expected for " + ElementName);
			BasePage.logger.info("Exception" + e);
		}

	}

	/*
	 * Add this in Jsutil
	 */
	public void clickByJs(By locator, String elementName) {

		try {
			javaScriptUtil = new JavaScriptUtil(driver);
			javaScriptUtil.clickByJs(findElement(locator));
			BasePage.logger.pass(elementName + " is clicked");
		} catch (Exception e) {
			BasePage.logger.fail(elementName + " is not clickable");
			// Assert.fail();

		}
	}

	public boolean isDisplayed(By locator) {
		return findElement(locator).isDisplayed();

	}

	public String getText(By locator) {
		try{
		String text = findElement(locator).getText();
		findElement(locator).getAttribute("value");
		return text;
		}catch(Exception e){
			e.printStackTrace();
		}return null;
	}

	public String getAttributeValue(By locator) {
		String text = findElement(locator).getAttribute("value");
		return text;
	}

	public void waitUntilElementClickable(By locator, long Seconds) {
		wait = new WebDriverWait(driver, Seconds);
		try {
			wait.until(ExpectedConditions.elementToBeClickable(locator));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void waitUntilTitleContains(String title, long Seconds) {
		wait = new WebDriverWait(driver, Seconds);
		try {
			wait.until(ExpectedConditions.titleContains(title));
			BasePage.logger.info("Page Title contains expected title " + title);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void waitUntilElementPresent(By locator, long seconds) {
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}

	public void waitUntilElementVisible(By locator, long Seconds) {
		wait = new WebDriverWait(driver, Seconds);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Scroll() {
		js = (JavascriptExecutor) driver;
		js.executeScript("scroll(0, 500);");

	}

	public void sendKeysWithAction(By locator, String value, String fieldName) {

		try {
			action.sendKeys(findElement(locator), value).build().perform();
			BasePage.logger.pass(value + " is entered in " + fieldName);

		} catch (Exception e) {
			BasePage.logger.fail(" Unable to perform sendKeys with action on" + fieldName);
			e.printStackTrace();
		}
	}

	public void ClickWithAction(By locator, String elementName) {

		try {
			System.out.println("Inside Action click" + locator);
			action.moveToElement(findElement(locator)).click().build().perform();
			BasePage.logger.pass(elementName + " is clicked");
		} catch (Exception e) {
			BasePage.logger.fail(elementName + " is not clickable");
			Assert.fail();
		}

	}

	public void moveToElement(By locator) {
		waitUntilElementClickable(locator, 60);
		action.moveToElement(findElement(locator));
	}

}
