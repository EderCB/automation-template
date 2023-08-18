package com.moon.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;

public class ScreenManager {

    public static String screenshotDirectory = PropertiesManager.getProperty("screenshotPath");
    public static String currentScreenDir = screenshotDirectory + PropertiesManager.getProperty("timestamp");
    public static String fileName;
    private static boolean directoryVerified = false;

    private static void validateScreenshotDirectory() {
        File directory = new File(currentScreenDir);
        if (directory.exists() && directory.isDirectory()) {
            System.out.println("\n Directory Already Created \n");
        } else if (!directory.mkdirs())
            throw new RuntimeException("\n Failed to Create The Screenshot Directory \n");
        directoryVerified = true;
    }

    public static void takeScreenshot(ITestResult testResult, WebDriver driver) {
        if (!directoryVerified) validateScreenshotDirectory();
        fileName = testResult.isSuccess()
                ? testResult.getName() + "_success.png"
                : testResult.getName() + "_failure.png";
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File source = screenshot.getScreenshotAs(OutputType.FILE);
        File destination = new File(currentScreenDir + "/" + fileName);

        try {
            FileHandler.copy(source, destination);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void takeElementScreenshot(WebElement element, String name) {
        if (!directoryVerified) validateScreenshotDirectory();
        File srcFile = element.getScreenshotAs(OutputType.FILE);
        try {
            FileHandler.copy(srcFile, new File(screenshotDirectory + name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
