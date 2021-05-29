package com.qa.whatuni.util;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.whatuni.base.BasePage;

public class JavaScriptUtil {
	WebDriver driver;
	JavascriptExecutor javaScript;
	ElementUtils elementUtils;

	public JavaScriptUtil(WebDriver driver) {
		this.driver = driver;
		javaScript = (JavascriptExecutor) driver;
		elementUtils = new ElementUtils(driver);

	}

	public void scrollDown() {

		javaScript.executeScript("window.scrollTo(0,document.body.scrollHeight)");

	}

	public void scroll() {

		javaScript.executeScript("scroll(0, 500);");
	}

	public void scrollDownToElementView(By locator) {

		javaScript.executeScript("arguments[0].scrollIntoView(true)", elementUtils.findElement(locator));

		// arguments[0].scrollIntoView();", Element

	}

	public void flash(WebElement element) {

		String bgColor = element.getCssValue("backgroundcolor");
		for (int i = 0; i < 20; i++) {
			changeColor("rgb(0,200,0)", element, driver);
			changeColor(bgColor, element, driver);
		}
	}

	public void changeColor(String color, WebElement element, WebDriver driver) {

		javaScript.executeScript("arguments[0].style.backgroundColor='" + color + "'", element);
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {

		}

	}
	
	public void scrollToTop(){
		javaScript.executeScript("window.scrollTo(0, 0);");


		
	}

	public void DrawBorder(WebElement element) {
		javaScript.executeScript("arguments[0].style.border='3px solid red'", element);
	}

	public void generateAlert(String message) {
		javaScript.executeScript("alert('" + message + "')");

	}

	public void clickByJs(WebElement element) {
		javaScript.executeScript("arguments[0].click();", element);
	}

	public void pageRefreshByJs() {
		javaScript.executeScript("history.go(0)");
	}

	public String getPageTitleByJS() {
		String title = javaScript.executeScript("return document.title;").toString();
		return title;

	}

	public String getInnerTextByJs() {
		String innerText = javaScript.executeScript("return document.documentElement.innerText;").toString();
		return innerText;

	}

	public String getBrowserInfo() {
		String innerText = javaScript.executeScript("return document.documentElement.innerText;").toString();
		return innerText;

	}

	public void sendKeysUsingJsWithId(String id, String value,String fieldName) {
		
		try{
			javaScript.executeScript("document.getElementById('" + id + "').value ='" + value + "'");
			BasePage.logger.pass("Sendy key action with Java script is completed for "+fieldName);
			
		}catch(Exception e){
			BasePage.logger.fail("Sendy key action with Java script is Failed for "+fieldName);
		}

	}

	public void sendKeysUsingJsWithClassName(String className, String value) {
		javaScript.executeScript("document.getElementsByClassName('" + className + "')values(" + value + ")");

	}

	public void sendKeysUsingJavaScriptWithName(String name, String value) {
		javaScript.executeScript("document.getElementsByName('" + name + "').values('" + value + "')");

		// document.getElementsByName("institutionName").values("i love you");

	}

	public String returnCurrentFrame() {
		String currentFrame = javaScript.executeScript("return self.name").toString();
		return currentFrame;
	}
	
	public String getDocumentLoadStatus(){
		String domStatus= javaScript.executeScript("return document.readyState").toString();
		return domStatus;
	}

}
