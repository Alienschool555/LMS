package utils.reports;

import org.testng.ITestListener;
import org.testng.ITestResult;

public final class ExceptionListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        Throwable exception = result.getThrowable();
        LoggerUtils.logError("ERROR: caused by " + exception.getClass().getSimpleName());
        LoggerUtils.logException("EXCEPTION: " + exception.getMessage());
    }
}
