package com.qa.whatuni.page;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.qa.whatuni.base.BasePage;
import com.qa.whatuni.util.AppConstant;
import com.qa.whatuni.util.ElementUtils;
import com.qa.whatuni.util.JavaScriptUtil;
import com.qa.whatuni.util.Xls_Reader;

public class RegistrationPage extends BasePage {
	WebDriver driver;
	ElementUtils elementUtil;
	Xls_Reader xls_Reader;
	JavaScriptUtil javaScriptUtil;
	HomePage homePage;

	public RegistrationPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtils(driver);
		javaScriptUtil = new JavaScriptUtil(driver);
		homePage = new HomePage(driver);

	}

	// By userProfileMenu = By.xpath("//span[@class='sgnup_ui']");
	By logOut = By.xpath("//a[text()='Logout']");
	By signUp_Login_Link = By.linkText("Sign up / Log in");
	By SignInLink = By.id("ab_signin_nav");

	By EmailTxtLoginWindow = By.xpath("//input[@id='loginemail']");
	By PasswordTxt_Loginwindow = By.xpath("//input[@id='textpwd']");
	By SigninBttn_LoginWindow = By.xpath("//a[@title='Sign in'][@id='btnLogin']");

	// open signUPWindow
	By sigUpBttn = By.xpath("//a[@id='btnLogin' ][@title='Register']");
	By signUp = By.id("ab_signup_nav");

	public void openSingUpWindow() {

		elementUtil.waitUntilElementVisible(signUp, 20);
		elementUtil.clickElement(signUp, "Sign up with email link");
		elementUtil.Scroll();

	}

	// SingUP form errors
	By firstNameError = By.id("firstName_error");
	By lastNameError = By.id("surName_error");
	By emailFieldError = By.xpath("//p[@id='registerErrMsg']");
	By passwordError = By.id("password_error");
	By termAndConditionError = By.id("termsc_error");

	public void signUpWithNoData() {

		elementUtil.clickElement(sigUpBttn, "Sign up button");
		elementUtil.waitUntilElementVisible(firstNameError, 20);

		System.out.println(elementUtil.getText(firstNameError) + "d   b" + AppConstant.firstNameErr);

		// Assert.assertEquals(elementUtil.getText(firstNameError),
		// AppConstant.firstNameErr);
		// Assert.assertEquals(elementUtil.getText(lastNameError),
		// AppConstant.lastNameErr);
		// Assert.assertEquals(elementUtil.getText(emailFieldError),
		// AppConstant.emailErr);
		// Assert.assertEquals(elementUtil.getText(passwordError),
		// AppConstant.passWordErr);
		// Assert.assertEquals(elementUtil.getText(termAndConditionError),
		// AppConstant.termsAndConditionErr);

		elementUtil.verifyElementText(firstNameError, AppConstant.firstNameErr, "FirstName error message");
		elementUtil.verifyElementText(lastNameError, AppConstant.lastNameErr, "LastName error message");
		elementUtil.verifyElementText(emailFieldError, AppConstant.emailErr, "Email error message");
		elementUtil.verifyElementText(passwordError, AppConstant.passWordErr, "Password error message");
		elementUtil.verifyElementText(termAndConditionError, AppConstant.termsAndConditionErr,
				"T&C check box error message");
		BasePage.logger.info("Registration without any valid field passed - Expected error message thrown",
				MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot()).build());

	}

	// SignUp elements

	By firstNameTxt = By.xpath("//input[@id='firstName']");
	By lastNameTxt = By.xpath("//input[@id='surName']");
	By emailTxt = By.xpath("//input[@id='emailAddress']");
	By emailAjax = By.xpath("//div[@id='autoEmailId']");
	By passwordTxt = By.xpath("//input[@id='password']");
	By termsConditonCheckBox = By.xpath("//input[@id='termsc']");
	By userProfileMenu= By.xpath("//a[@id='loglink']/child::span[@class='sgnup_ui']");
	//By userProfileMenu = By.cssSelector(".cnt_ui.fnt_lbd.ml5.mr10");
	By Myprofile = By.xpath("//div[@id='mob_menu_act']/ul/li[1]/a");
	By articlePod = By.xpath("//*[@id='articlesPod']/article/article[2]/a");

	// email Error
	By emailError = By.xpath("//p[@id='registerErrMsg']");

	public void signUpWithValidData() throws InterruptedException {

		xls_Reader = new Xls_Reader("src/main/java/com/qa/whatuni/testData/WhatuniTestData.xlsx");
		elementUtil.sendKeys(firstNameTxt, xls_Reader.getCellData("registration", "firstname", 2),
				"First Name text box");
		elementUtil.sendKeys(lastNameTxt, xls_Reader.getCellData("registration", "lastname", 2), "Last Name text box");
		// Email
		String emailId = xls_Reader.getCellData("registration", "email", 2);
		elementUtil.sendKeys(emailTxt, emailId, "Email text box");
		if (elementUtil.isDisplayed(emailAjax)) {
			elementUtil.clickElement(By.xpath("//input[@id='emailAddress']//following::div[text()='" + emailId + "']"),
					"Email id from Ajax");
		} else {
			BasePage.logger.warning("Ajax not displayed for email field");
			System.out.println("email Ajax not created");
		}

		/*
		 * Password is non interactable so we are using Java script, Failed with
		 * SendKeys and sendKeyWithAction
		 * 
		 */
		javaScriptUtil.sendKeysUsingJsWithId("password", xls_Reader.getCellData("registration", "password", 2),
				"Password Field");

		String yearOfEntry = xls_Reader.getCellData("registration", "yoe", 2);
		System.out.println(yearOfEntry);

		elementUtil.clickElement(By.xpath("//input[@name='yeartoJoinCourse'][@value='" + yearOfEntry + "']"),
				"Year of entry '" + yearOfEntry + "'");
		elementUtil.clickElement(termsConditonCheckBox, "T&C check box");

		Thread.sleep(50);
		elementUtil.clickElement(sigUpBttn, "Sign Up button");
		//Thread.sleep(500);
		//System.out.println("Email assert" + elementUtil.getText(emailError).contains("is already registered with us"));
		if (elementUtil.getText(emailError).contains("is already registered with us")) {
			System.out.println("inside if condition - Email fail");
			BasePage.logger.fail("Email id already exist. Please try with new email id",
					MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot()).build());
			fail("Email id already exist. Please try with new email id");

		} else {
			BasePage.logger.info("Registration completed",
					MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot()).build());
		}

		// System.out.println(javaScriptUtil.getDocumentLoadStatus())

	}

	// My Profile
	By firstNameInfo = By.xpath("//input[@id='firstName']");
	By chatBot = By.xpath("//*[@id='need_help_text']/a[1]");
	By chatBotButton = By.xpath("//*[@id='yesNoDiv']/ul/li/a");

	public void verifyLoggedInUser() throws InterruptedException {

		System.out.println("inside verifyLoggedInUser");
		BasePage.logger.info("Verify logged in user case");
		//homePage.VerifyHomePageTitle();
		elementUtil.waitUntilElementVisible(chatBot, 20);
		elementUtil.ClickWithAction(chatBot,"Chat bot is clicked");
		// elementUtil.waitUntilElementVisible(chatBotButton, 10);
		elementUtil.waitUntilElementVisible(userProfileMenu, 20);
		elementUtil.clickByJs(userProfileMenu, "User profile menu");
		//elementUtil.waitUntilElementVisible(Myprofile, 20);
		elementUtil.clickByJs(Myprofile, "My Profile link");
		elementUtil.waitUntilElementVisible(firstNameInfo, 20);
		String actualFirstName = elementUtil.getAttributeValue(firstNameInfo);
		String expectedFirstName = xls_Reader.getCellData("registration", "firstname", 2);
		System.out.println("Actual:" + actualFirstName + " Expected:" + expectedFirstName);
		if (expectedFirstName.equals(actualFirstName)) {
			BasePage.logger.pass(
					"Login is successfull - First name in user profile (" + actualFirstName
							+ ") matches with expected first name : " + expectedFirstName,
					MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot()).build());

		} else {
			BasePage.logger.fail("Registration failed - First name in user profile(" + actualFirstName
					+ ") does not matches with expected first name : " + expectedFirstName);
			assertEquals(expectedFirstName, actualFirstName);
		}
	}

	public void logout() {

		elementUtil.waitUntilElementClickable(userProfileMenu, 30);
		elementUtil.clickByJs(userProfileMenu, "UserProfile menu");
		elementUtil.waitUntilElementVisible(logOut, 30);
		elementUtil.clickElement(logOut, "Logout button");

		elementUtil.waitUntilElementPresent(signUp_Login_Link, 30);
		String SignUp_SignInText = elementUtil.getText(signUp_Login_Link);
		if (SignUp_SignInText.equals(AppConstant.SignUp_SignInText)) {
			BasePage.logger.pass("Logout completed successfully",
					MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot()).build());
		} else {
			BasePage.logger.fail("Logout action failed",
					MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot()).build());
			Assert.assertEquals(SignUp_SignInText, AppConstant.SignUp_SignInText);
		}

	}

	public void Login() {
		// xls_Reader = new
		// Xls_Reader("src/main/java/com/qa/whatuni/testData/WhatuniTestData.xlsx");
		elementUtil.clickElement(signUp_Login_Link, "Sign up / Log in link");
		elementUtil.waitUntilElementVisible(SignInLink, 30);
		elementUtil.clickElement(SignInLink, "Sign In link");

		elementUtil.waitUntilElementVisible(EmailTxtLoginWindow, 30);
		String email = xls_Reader.getCellData("registration", "email", 2);
		String password = xls_Reader.getCellData("registration", "password", 2);
		elementUtil.sendKeysWithAction(EmailTxtLoginWindow, email, "email field");
		elementUtil.sendKeysWithAction(PasswordTxt_Loginwindow, password, "password field");
		BasePage.logger.info(MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot()).build());
		elementUtil.clickElement(SigninBttn_LoginWindow, "Sign In button");

	}

}
