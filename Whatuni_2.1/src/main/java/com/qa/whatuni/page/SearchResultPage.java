package com.qa.whatuni.page;

import static org.testng.Assert.fail;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.qa.whatuni.base.BasePage;
import com.qa.whatuni.util.AppConstant;
import com.qa.whatuni.util.ElementUtils;
import com.qa.whatuni.util.SearchText;

public class SearchResultPage extends BasePage {
	WebDriver driver;
	HomePage homePage;
	ElementUtils elementUtils;
	SearchText searchText;
	String noOfCourse;
	// String searchText;

	By okGotIt = By.id("OkGotIt");
	By noOfDegrees = By.xpath("//a[@id='sr_to_pr_0']");
	By providerTitle = By.xpath("//a[@id='sr_to_pr_0']/preceding::a[@data-index='0']");
	By providerPageTittle = By.xpath("//div[@class='rat_rht']/h1");

	public SearchResultPage(WebDriver driver) {
		this.driver = driver;
		homePage = new HomePage(driver);
		elementUtils = new ElementUtils(driver);
	}

	public void verifySRPageTitle() {

		elementUtils.waitUntilTitleContains(AppConstant.serchResultPageTitle, 30);
		String searchResultPageTitle = driver.getTitle();
		if (searchResultPageTitle.equals(AppConstant.serchResultPageTitle)) {
			BasePage.logger.pass("Successfully navigate to Search result Page",
					MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot()).build());
		} else {
			BasePage.logger.pass("Unable to navigate to Search result Page",
					MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot()).build());
			Assert.assertEquals(searchResultPageTitle, AppConstant.serchResultPageTitle);
		}

	}

	public void verifyCourseSearchResult() {
		elementUtils.waitUntilElementClickable(okGotIt, 20);
		if (elementUtils.findElement(okGotIt).isDisplayed()) {
			elementUtils.clickElement(okGotIt, "Pop up button");
		}

		elementUtils.waitUntilElementVisible(noOfDegrees, 20);
		noOfCourse = elementUtils.getText(noOfDegrees);
		System.out.println(noOfCourse);

		searchText = new SearchText();

		System.out.println("Search text value" + searchText.getSearchText());
		if (noOfCourse.contains(searchText.getSearchText())) {
			BasePage.logger.pass("Search result are relevent to search Text '" + searchText.getSearchText() + "'",
					MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot()).build());
		} else {
			BasePage.logger.fail("Search result are relevent to " + searchText.getSearchText(),
					MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot()).build());

		}

	}

	public void VerifyProviderResultPage() {
		// VerifySRPage();
		elementUtils.clickByJs(noOfDegrees, noOfCourse + " link");
		elementUtils.waitUntilTitleContains(searchText.getSearchText() + " Undergraduate At", 30);
		String providerResultTitle = driver.getTitle();
		if (providerResultTitle.contains(searchText.getSearchText() + " Undergraduate At")) {
			BasePage.logger.pass("Successfully navigated to Provider result page",
					MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot()).build());
		} else {
			BasePage.logger.fail("Unable to navigated to Provider result page",
					MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot()).build());
			fail("Unable to navigated to Provider result page");

		}
		driver.navigate().back();
	}

	/*
	 * public void VerifySRPage(){
	 * elementUtils.waitUntilElementClickable(okGotIt, 20);
	 * elementUtils.clickElement(okGotIt, "Pop up button");
	 * 
	 * }
	 */

	public void VerifyProviderLandingPage() {
		// VerifySRPage();
		elementUtils.waitUntilElementVisible(providerTitle, 20);
		String providerName = elementUtils.getText(providerTitle);
		elementUtils.clickElement(providerTitle, providerName + " Link ");
		elementUtils.waitUntilElementVisible(providerPageTittle, 30);
		if (elementUtils.getText(providerPageTittle).equals(providerName)) {
			BasePage.logger.pass("Successfully navigated to Provider landing page",
					MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot()).build());
		} else {
			BasePage.logger.fail("Failed to navigate to Provider landing page",
					MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot()).build());
			fail("Failed to navigate to Provider landing page");

		}
		driver.navigate().back();

	}

	By courseNameLink = By.xpath("//a[@id='sr_to_cd_0']");
	By cdPageCourseTitle = By.id("cdCourseTitle");

	public void VerifyCourseDetailPage() {
		// verifySRPageTitle();
		elementUtils.waitUntilElementVisible(courseNameLink, 30);
		String courseName = elementUtils.getText(courseNameLink);
		elementUtils.clickElement(courseNameLink, "Course title link");
		elementUtils.waitUntilElementVisible(cdPageCourseTitle, 30);

		if (elementUtils.getText(cdPageCourseTitle).equals(courseName)) {
			BasePage.logger.pass("Navigated to Course detail page successfully",
					MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot()).build());
		} else {
			BasePage.logger.fail("Navigation to Course detail page failed",
					MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot()).build());
		}
		driver.navigate().back();

	}

}
