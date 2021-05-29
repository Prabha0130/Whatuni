package com.qa.whatuni.testScript;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.qa.whatuni.base.BasePage;
import com.qa.whatuni.page.HomePage;
import com.qa.whatuni.page.RegistrationPage;
import com.qa.whatuni.util.AppConstant;

public class RegistrationTest extends BasePage {

	BasePage basePage;
	WebDriver driver;
	Properties prop;
	HomePage homePage;
	RegistrationPage registrationPage;

	@BeforeTest
	public void SetupReport() {
		BasePage.spark = new ExtentSparkReporter(
				new File(System.getProperty("user.dir") + "./src/main/java/com/qa/myidpc/TestReports/"
						+ BasePage.dateformat.format(BasePage.date) + "_Whatuni_Regression.html"));
		BasePage.extent = new ExtentReports();
		BasePage.extent.attachReporter(BasePage.spark);
	}

	@BeforeClass
	public void setUp() {

		System.out.println(System.getProperty("user.dir"));
		basePage = new BasePage();
		prop = basePage.initializePropertyFile();
		basePage.initializePropertyFile();
		driver = basePage.initializeBrowser(prop.getProperty("browser"));
		driver.get(prop.getProperty("url"));

	}

	@Test(priority = 1)
	public void verifyHomePageTitle() {
		BasePage.logger = BasePage.extent.createTest("Verify HomePage");
		homePage = new HomePage(driver);
		homePage.VerifyHomePageTitle();

	}

	@Test(priority = 2)
	public void openSignUpWindow() {
		BasePage.logger = BasePage.extent.createTest("Registration without any data");
		homePage.openLoginUpWindow();
	}

	@Test(priority = 3)
	public void signUpTestWithNoData() {
		registrationPage = new RegistrationPage(driver);
		registrationPage.openSingUpWindow();
		BasePage.logger.info("Registrattion without entering Mandatory fields");
		registrationPage.signUpWithNoData();
		
	}

	@Test(priority = 4)
	public void singUpTestWithValidData() throws InterruptedException {
		BasePage.logger = BasePage.extent.createTest("Registration with valid data");
		registrationPage.signUpWithValidData();
	}

	@Test(priority = 5, dependsOnMethods = { "singUpTestWithValidData" })
	public void VerifySigUpTest() throws InterruptedException {
		// homePage.VerifyHomePageTitle();
		//BasePage.logger.createNode("Verify Registered user");
		homePage.VerifyHomePageTitle();
		registrationPage.verifyLoggedInUser();
		registrationPage.logout();
		
	}

	@Test(priority = 6, dependsOnMethods = { "VerifySigUpTest" })
	public void VerifySignIn() throws InterruptedException {
		BasePage.logger = BasePage.extent.createTest("Sign-In test with registered credential");
		homePage.VerifyHomePageTitle();
		registrationPage.Login();
		registrationPage.verifyLoggedInUser();
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
