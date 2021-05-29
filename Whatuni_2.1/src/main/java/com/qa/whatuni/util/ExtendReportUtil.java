package com.qa.whatuni.util;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtendReportUtil {
	public ExtentSparkReporter spark;
	public ExtentReports extent;
	public ExtentTest logger;
	private static final String CODE1 = "{\n    \"theme\": \"standard\",\n    \"encoding\": \"utf-8\n}";
	private static final String CODE2 = "{\n    \"protocol\": \"HTTPS\",\n    \"timelineEnabled\": false\n}";

	@Test
	public void ExtendReportInitialization() {
		spark = new ExtentSparkReporter(
				System.getProperty("user.dir") + "./src/main/java/com/qa/myidpc/TestReports/Whatuni_Regression.html");
		extent = new ExtentReports();
		extent.attachReporter(spark);

		extent.createTest("ScreenCapture").addScreenCaptureFromPath("extent.png")
				.pass(MediaEntityBuilder.createScreenCaptureFromPath("extent.png").build());

		logger = extent.createTest("LogLevels");
		logger.info("Prabha");
		logger.pass("pass");
		logger.warning("warn");
		logger.skip("skip");
		logger.fail("Nirlama be carefull");

		extent.createTest("CodeBlock").generateLog(Status.PASS, MarkupHelper.createCodeBlock(CODE1, CODE2));
		
		extent.flush();
		extent.attachReporter(spark);

		
		extent.createTest("ParentWithChild").createNode("Child")
				.pass("This test is created as a toggle as part of a child test of 'ParentWithChild'");

		extent.createTest("Tags").assignCategory("MyTag")
				.pass("The test 'Tags' was assigned by the tag <span class='badge badge-primary'>MyTag</span>");

		extent.createTest("Authors").assignAuthor("TheAuthor")
				.pass("This test 'Authors' was assigned by a special kind of author tag.");

		extent.createTest("Devices").assignDevice("TheDevice")
				.pass("This test 'Devices' was assigned by a special kind of devices tag.");

		extent.createTest("Exception! <i class='fa fa-frown-o'></i>")
				.fail(new RuntimeException("A runtime exception occurred!"));

		extent.flush();
	}

}
