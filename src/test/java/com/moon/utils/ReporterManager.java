package com.moon.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.ViewName;
import org.testng.ITestResult;

public class ReporterManager {

    private static ExtentReports extent;
    private static ExtentSparkReporter spark;
    private static ExtentTest test;
    private static ExtentTest node;

    private static final String reportPath = PropertiesManager.getProperty("reportPath");

    public static void createExtentReport() {
        extent = new ExtentReports();
    }

    private static void createSparkReporter() {
        String timestamp = PropertiesManager.getProperty("timestamp");
        String fileName = "Report-" + timestamp;
        PropertiesManager.setProperty("reportName", fileName);
        spark = new ExtentSparkReporter(reportPath + fileName + ".html");
        spark.config().setDocumentTitle(fileName);
        setViewOrder();
    }

    public static void createTest(String testName) {
        createSparkReporter();
        extent.attachReporter(spark);
        test = extent.createTest(testName);
    }

    public static void deleteTest(String testName) {
        extent.removeTest(testName);
    }

    public static void saveTest() {
        extent.flush();
    }

    public static void createNode(String nodeName) {
        node = test.createNode(nodeName);
    }

    public static void removeNode(String nodeName) {
        deleteTest(nodeName);
    }

    public static void testInfo(String message) {
        test.info(message);
    }

    public static void testPass(String message) {
        test.pass(message);
    }

    public static void testWarn(String message) {
        test.warning(message);
    }

    public static void testSkip(String message) {
        test.skip(message);
    }

    public static void testFail(String message) {
        test.fail(message);
    }

    public static void info(String message) {
        Markup label = MarkupHelper.createLabel(message, ExtentColor.BLUE);
        node.info(label);
    }

    public static void pass(String message) {
        Markup label = MarkupHelper.createLabel(message, ExtentColor.GREEN);
        node.pass(label);
    }

    public static void warn(String message) {
        Markup label = MarkupHelper.createLabel(message, ExtentColor.YELLOW);
        node.warning(label);
    }

    public static void skip(String message) {
        Markup label = MarkupHelper.createLabel(message, ExtentColor.GREY);
        node.skip(label);
    }

    public static void fail(String message, Throwable exception) {
        Markup label = MarkupHelper.createLabel(message, ExtentColor.RED);
        node.fail(label).fail(exception);
    }

    public static void addScreenCaptureFrom(ITestResult testResult) {
        String screenPath = "../screenshots/" + PropertiesManager.getProperty("timestamp") + "/" + ScreenManager.fileName;
        if (testResult.getStatus() == ITestResult.SUCCESS) {
            node.pass(MediaEntityBuilder.createScreenCaptureFromPath(screenPath).build());
        } else if (testResult.getStatus() == ITestResult.FAILURE) {
            node.fail(MediaEntityBuilder.createScreenCaptureFromPath(screenPath).build());
        } else if (testResult.getStatus() == ITestResult.SKIP) {
            node.skip(MediaEntityBuilder.createScreenCaptureFromPath(screenPath).build());
        } else {
            node.info(MediaEntityBuilder.createScreenCaptureFromPath(screenPath).build());
        }
    }

    private static void setViewOrder() {
        spark.viewConfigurer().viewOrder().as(new ViewName[]{
                ViewName.DASHBOARD,
                ViewName.TEST,
                ViewName.EXCEPTION,
                ViewName.CATEGORY,
//                ViewName.AUTHOR,
//                ViewName.DEVICE,
                ViewName.LOG,
        });
    }
}