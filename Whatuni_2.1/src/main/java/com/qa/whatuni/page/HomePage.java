package com.qa.whatuni.page;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.qa.whatuni.base.BasePage;
import com.qa.whatuni.util.AppConstant;
import com.qa.whatuni.util.ElementUtils;
import com.qa.whatuni.util.JavaScriptUtil;
import com.qa.whatuni.util.Xls_Reader;

import freemarker.log.Logger;

/**
 * @author Prabhakaran.P
 * @contains home page functions
 */
public class HomePage extends BasePage {
	WebDriver driver;
	WebDriverWait wait;
	ElementUtils elementUtil;
	Xls_Reader xls_Reader;
	JavaScriptUtil javaScriptUtil;
	String searchText;

	By signUp_Login_Link = By.linkText("Sign up / Log in");
	By FBLogin = By.id("ab_fb_nav");
	By signIn = By.id("ab_signin_nav");
	By signUp = By.id("ab_signup_nav");

	public HomePage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtils(driver);
		javaScriptUtil = new JavaScriptUtil(driver);

	}

	public void VerifyHomePageTitle() {
		// BasePage.logger.info("Navigated to Whatuni Home Page url:
		// "+elementUtil.getCurrentPageURL());
		System.out.println("HomePage- getHomePageTitle function executed");
		elementUtil.VerifyCurrentPageTitle(AppConstant.homePageTitle, "HomePage");

	}

	/*
	 * public void VerifyHomePageTitle() {
	 * elementUtil.waitUntilTitleContains(AppConstant.homePageTitle, 20); String
	 * title = driver.getTitle(); System.out.println(title);
	 * Assert.assertEquals(AppConstant.homePageTitle, title); }
	 */

	public void openLoginUpWindow() {
		elementUtil.waitUntilElementClickable(signUp_Login_Link, 20);
		elementUtil.clickElement(signUp_Login_Link, "Sign up / Log in link");
		elementUtil.waitUntilElementVisible(FBLogin, 20);
		if (elementUtil.isDisplayed(FBLogin) && elementUtil.isDisplayed(signUp)) {
			BasePage.logger.pass("Sign up window is opened",
					MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot()).build());
		} else {
			BasePage.logger.fail("Sign up window is not opened")
					.info(MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot()).build());
			System.out.println("Login window not opened");
			Assert.fail("Sign up window not opened");

		}

	}

	// Clearing Search
	By CourseTab = By.xpath("//ul[@id='srchUlId']/li[text()='Courses']");
	By courseSearchTxt = By.xpath("//input[@id='keywordTpNav']");
	By searchAjax = By.xpath("//div[@id='ajax_listOfOptions']");
	By AjaxList = By.xpath("//div[@class='optionDiv']/child::span[@class='ajx_sub']");
	By landingPageSearchBttn = By.xpath("//input[@id='locKwdFlg']/preceding::button[@id='landingSearch']");

	public void landingPageCourseSearch(String searchKeyWord) {
		searchText = searchKeyWord;

		elementUtil.waitUntilElementVisible(CourseTab, 20);
		elementUtil.clickElement(CourseTab, "Course Tab");
		elementUtil.waitUntilElementVisible(courseSearchTxt, 20);
		elementUtil.sendKeysWithAction(courseSearchTxt, searchText, " course search field");
		elementUtil.waitUntilElementVisible(searchAjax, 30);
		List<WebElement> AjaxValue = driver.findElements(AjaxList);
		for (int i = 0; i < AjaxValue.size(); i++) {
			String temp = AjaxValue.get(i).getText();
			if (!temp.contains(searchText)) {
				BasePage.logger.fail("Subjects loaded in ajax does not conatains search text '" + searchText + "'",
						MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot()).build());
				assertTrue(temp.contains(searchText));
				break;
			} else {
				System.out.println("subject loaded in ajax are relevent to serchText" + temp);
			}
		}
		BasePage.logger.pass("All Subjects loaded in ajax conatains search text '" + searchText + "'",
				MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot()).build());
		elementUtil.ClickWithAction(
				By.xpath("//div[@class='optionDiv']/child::span/span[2]/span[text()='" + searchText + "']"),
				"Search text "+searchKeyWord+" from ajax");
		elementUtil.clickByJs(landingPageSearchBttn, " 'Search button' in landing page course search ");
		

	}


}
