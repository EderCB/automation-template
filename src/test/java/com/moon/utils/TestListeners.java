package com.moon.utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListeners implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        String message = String.format("Step %d) %s", result.getMethod().getPriority(), result.getMethod().getDescription());
        LogGenerator.info(message);
        ReporterManager.createNode(result.getMethod().getDescription());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String message = "Step Result: SUCCESS";
        LogGenerator.info(message);
        ReporterManager.pass(message);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String message = "Step Result: FAILURE";
        String cause = result.getThrowable().getMessage();
        LogGenerator.error(message + " - Cause: " + cause);
        Throwable exception = result.getThrowable();
        ReporterManager.fail("Step Result: FAILURE", exception);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String message = "Step Result: TEST SKIPPED";
        LogGenerator.warn(message);
        ReporterManager.skip(message);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        ITestListener.super.onTestFailedWithTimeout(result);
    }

    @Override
    public void onStart(ITestContext context) {
        String message = String.format("###### Starting Test %s ######", context.getName());
        LogGenerator.debug(message);
    }

    @Override
    public void onFinish(ITestContext context) {
        String message = String.format("###### Closing Test %s ######", context.getName());
        LogGenerator.debug(message);
        ReporterManager.saveTest();
    }
}
