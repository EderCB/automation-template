package com.moon.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class BasePage {

    public static WebDriver driver;
    private static final HashMap<String, String> pages = new HashMap<>(5);

    public void setDriver(WebDriver driver) {
        BasePage.driver = driver;
    }

    protected void loadPage(String URL) {
        driver.get(URL);
    }

    protected void reloadPage() {
        driver.navigate().refresh();
    }

    protected void closePage() {
        driver.close();
    }

    protected void maximize() {
        driver.manage().window().maximize();
    }

    protected void minimize() {
        driver.manage().window().minimize();
    }

    protected String getCurrentWindowHandle() {
        return driver.getWindowHandle();
    }

    protected Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }

    private void saveWindowHandle(String windowHandle, String windowName) {
        System.out.println("Saving Window: " + windowName +
                " - Handle: " + windowHandle);
        pages.put(windowName, windowHandle);
    }

    protected void saveCurrentWindowHandle(String windowName) {
        String windowHandle = getCurrentWindowHandle();
        saveWindowHandle(windowHandle, windowName);
    }

    private void switchTo(String windowHandle) {
        System.out.println("Switching to New Window With Handle: " + windowHandle);
        driver.switchTo().window(windowHandle);
    }

    private void switchTo(String windowHandle, String windowName) {
        System.out.println("Switching to Window: " + windowName +
                " - Handle: " + windowHandle);
        driver.switchTo().window(windowHandle);
    }

    protected void switchToWindow(String windowName) {
        String windowHandle = pages.get(windowName);
        switchTo(windowHandle, windowName);
    }

    protected void switchToNewWindowAndSaveIt(String newWindowName) {
        for (String windowHandle : getWindowHandles()) {
            if (!pages.containsValue(windowHandle)) {
                switchTo(windowHandle);
                saveCurrentWindowHandle(newWindowName);
            }
        }
    }

    private void createNewTabAndSwitchToIt() {
        driver.switchTo().newWindow(WindowType.TAB);
    }

    protected void createNewTabAndSwitchToIt(String newTabName) {
        createNewTabAndSwitchToIt();
        saveCurrentWindowHandle(newTabName);
    }

    private void createNewWindowAndSwitchToIt() {
        driver.switchTo().newWindow(WindowType.WINDOW);
    }

    protected void createNewWindowAndSwitchToIt(String newWindowName) {
        createNewWindowAndSwitchToIt();
        saveCurrentWindowHandle(newWindowName);
    }

    protected WebElement find(By locator) {
        return wait(5000)
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected WebElement find(By locator, int timeoutInMilliseconds) {
        return wait(timeoutInMilliseconds)
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected Select select(By locator) {
        return new Select(find(locator));
    }

    protected List<WebElement> findElements(By locator) {
        wait(2000).until(ExpectedConditions.presenceOfElementLocated(locator));
        return driver.findElements(locator);
    }

    protected void clear(By locator) {
        find(locator).clear();
    }

    protected void clear(WebElement element) {
        element.clear();
    }

    protected void click(By locator) {
        find(locator).click();
    }

    protected void click(WebElement element) {
        element.click();
    }

    protected String getText(By locator) {
        String text = find(locator).getText();
        System.out.println("Text: " + text);
        return text;
    }

    protected String getText(WebElement element) {
        return element.getText();
    }

    protected void setText(By locator, String text) {
        find(locator).sendKeys(text);
    }

    protected void setText(WebElement element, String text) {
        element.sendKeys(text);
    }

    protected boolean isDisplayed(By locator) {
        try {
            return find(locator).isDisplayed();
        } catch (Exception e) {
            System.out.println("\n WebElement Is Not Displayed \n" + e.getCause());
            return false;
        }
    }

    protected boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            System.out.println("\n WebElement Is Not Displayed \n" + e.getCause());
            return false;
        }
    }

    protected boolean isEnabled(By locator) {
        try {
            return find(locator).isEnabled();
        } catch (Exception e) {
            System.out.println("\n WebElement Is Not Enabled \n" + e.getCause());
            return false;
        }
    }

    protected boolean isEnabled(WebElement element) {
        try {
            return element.isEnabled();
        } catch (Exception e) {
            System.out.println("\n WebElement Is Not Enabled \n" + e.getCause());
            return false;
        }
    }

    protected boolean isSelected(By locator) {
        try {
            return find(locator).isSelected();
        } catch (Exception e) {
            System.out.println("\n WebElement Is Not Selected \n" + e.getCause());
            return false;
        }
    }

    protected boolean isSelected(WebElement element) {
        try {
            return element.isSelected();
        } catch (Exception e) {
            System.out.println("\n WebElement Is Not Selected \n" + e.getCause());
            return false;
        }
    }

    protected WebDriverWait wait(int timeoutInMilliseconds) {
        return new WebDriverWait(driver, Duration.ofMillis(timeoutInMilliseconds));
    }

    protected WebDriverWait wait(int timeoutInMilliseconds, int pollingTime) {
        return new WebDriverWait(driver,
                Duration.ofMillis(timeoutInMilliseconds),
                Duration.ofMillis(pollingTime));
    }

    protected Wait<WebDriver> fluentWait(int timeoutInMilliseconds, int pollingTime) {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofMillis(timeoutInMilliseconds))
                .pollingEvery(Duration.ofMillis(pollingTime))
                .ignoring(NoSuchElementException.class);
    }

    protected void waitForPageToLoad(int timeoutInMilliseconds) {
        wait(timeoutInMilliseconds)
                .until((ExpectedCondition<Boolean>) driver -> ExpectedConditions.jsReturnsValue("return document.readyState").equals("complete"));
    }

    protected Actions actions() {
        return new Actions(driver);
    }
}
