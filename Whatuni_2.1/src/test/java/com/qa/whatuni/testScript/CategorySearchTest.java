package com.qa.whatuni.testScript;

import java.io.File;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.qa.whatuni.base.BasePage;
import com.qa.whatuni.page.HomePage;
import com.qa.whatuni.page.SearchResultPage;
import com.qa.whatuni.util.SearchText;

public class CategorySearchTest extends BasePage {

	BasePage basePage;
	Properties prop;
	WebDriver driver;
	HomePage homePage;
	SearchResultPage searchResultPage;
	SearchText searchText;

	/*@BeforeTest
	public void SetupReport() {
		BasePage.spark = new ExtentSparkReporter(
				new File(System.getProperty("user.dir") + "./src/main/java/com/qa/myidpc/TestReports/"
						+ BasePage.dateformat.format(BasePage.date) + "_Whatuni_Regression.html"));
		BasePage.extent = new ExtentReports();
		BasePage.extent.attachReporter(BasePage.spark);
	}*/

	@BeforeClass
	public void setUp() {
		basePage = new BasePage();
		prop = basePage.initializePropertyFile();
		driver = basePage.initializeBrowser(prop.getProperty("browser"));
		driver.get(prop.getProperty("url"));
		homePage = new HomePage(driver);
	}

	@Test(priority = 1)
	public void verifyHomePage() {
		BasePage.logger = BasePage.extent.createTest("Category Search");
		homePage.VerifyHomePageTitle();

	}

	@Test(priority = 2)
	public void categorySearchTest() {
		searchText = new SearchText();
		searchText.setSearchText(prop.getProperty("categorySearch"));
		homePage.landingPageCourseSearch(searchText.getSearchText());
		BasePage.logger.info("Verify Category Search result");
		searchResultPage = new SearchResultPage(driver);
		searchResultPage.verifySRPageTitle();
		searchResultPage.verifyCourseSearchResult();
			
	}

	@Test(priority = 3,dependsOnMethods={"categorySearchTest"})
	public void VerifyProviderResult() {
		searchResultPage.VerifyProviderResultPage();
		
	}
	
	@Test(priority = 4,dependsOnMethods={"VerifyProviderResult"})
	public void VerifyCourseDetailPage() {
		searchResultPage.VerifyCourseDetailPage();
		
	}
	
	@Test(priority = 5,dependsOnMethods={"VerifyCourseDetailPage"},enabled=true)
	public void VerifyProviderPage() {
		searchResultPage.VerifyProviderLandingPage();
		
	}
	
	@AfterClass
	public void flush() {
		driver.quit();

	}

	@AfterTest
	public void reportFlush() {
		BasePage.extent.flush();
	}

}
