import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;

public class ExtentReportListener implements ITestListener {
    protected static ExtentReports reports;
    protected static ExtentTest test;
    String ReportLocation = System.getProperty("user.dir") + "/TestReport/";

    public static void deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (null != files) {
                for (File file : files) {
                    System.out.println(file.getName());
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
        }
    }

    public void onTestStart(ITestResult result) {

        test = reports.startTest(result.getMethod().getMethodName());
        test.log(LogStatus.INFO, result.getMethod().getMethodName());
        System.out.println(result.getTestClass().getTestName());
        System.out.println(result.getMethod().getMethodName());
    }

    public void onTestSuccess(ITestResult result) {
        test.log(LogStatus.PASS, "Test is pass");

    }

    public void onTestFailure(ITestResult result) {
        test.log(LogStatus.FAIL, "Test is fail");

    }

    public void onTestSkipped(ITestResult result) {
        test.log(LogStatus.SKIP, "Test is skipped");

    }


    public void onStart(ITestContext context) {
        System.out.println(ReportLocation + "  ReportLocation");
        reports = new ExtentReports(ReportLocation + "ExtentReport.html");
        test = reports.startTest("");

    }

    public void onFinish(ITestContext context) {
        reports.endTest(test);
        reports.flush();

    }

}
