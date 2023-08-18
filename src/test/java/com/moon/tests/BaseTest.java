package com.moon.tests;

import com.moon.pages.GooglePage;
import com.moon.utils.LogGenerator;
import com.moon.utils.PropertiesManager;
import com.moon.utils.ReporterManager;
import com.moon.utils.ScreenManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import com.moon.pages.BasePage;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.IResultMap;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class BaseTest {

    private static String currentClassName;
    private WebDriver driver;
    protected BasePage basePage;
    protected GooglePage googlePage;

    @BeforeSuite
    public void setUpSuite() {
        PropertiesManager.setProperty("date", PropertiesManager.dateFormatted);
        PropertiesManager.setProperty("time", PropertiesManager.timeFormatted);
        PropertiesManager.setProperty("timestamp", PropertiesManager.timestamp);
    }

    @AfterSuite
    public void tearDownSuite() {
    }

    @BeforeClass
    public void setUpClass(ITestContext testContext) {
        String[] currentClassArr = this.getClass().getName().split("\\.");
        currentClassName = currentClassArr[currentClassArr.length - 1];
        // Logs
        String message = String.format("Loading CP %s ...", currentClassName);
        LogGenerator.info(message);
        // Create Report
        ReporterManager.createExtentReport();
        ReporterManager.createTest(currentClassName);

        // Setup WebDriver
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.google.com/");
    }

    @AfterClass
    public void tearDownClass(ITestContext context) {
        // Logs
        IResultMap result = context.getFailedTests();
        String message = String
                .format("Closing CP %s", currentClassName);
        if (result.size() == 0) {
            LogGenerator.info(message + " With Result SUCCESS");
            ReporterManager.testPass("Result: <b>SUCCESS</b>");
        } else {
            LogGenerator.error(message + " With Result FAILURE");
            ReporterManager.testFail("Result: <b>FAILURE</b>");
        }

        //Closing Session
        driver.quit();
    }

    @BeforeMethod
    public void setUp() {
        basePage = new BasePage();
        basePage.setDriver(driver);
        googlePage = new GooglePage();
    }

    @AfterMethod
    public void tearDown(ITestResult testResult) {
        ScreenManager.takeScreenshot(testResult, driver);
        ReporterManager.addScreenCaptureFrom(testResult);
    }
}
