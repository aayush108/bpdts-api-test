import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
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

        test = reports.createTest(result.getMethod().getMethodName());
        test.log(Status.INFO, result.getMethod().getMethodName());
        System.out.println(result.getTestClass().getTestName());
        System.out.println(result.getMethod().getMethodName());
    }

    public void onTestSuccess(ITestResult result) {
        test.log(Status.PASS, "Test is pass");

    }

    public void onTestFailure(ITestResult result) {
        test.log(Status.FAIL, "Test is fail");

    }

    public void onTestSkipped(ITestResult result) {
        test.log(Status.SKIP, "Test is skipped");

    }


    public void onStart(ITestContext context) {
        System.out.println(ReportLocation + "  ReportLocation");
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(ReportLocation + "ExtentReport.html");
        reports = new ExtentReports();
        reports.attachReporter(sparkReporter);
        test = reports.createTest("Setup");

    }

    public void onFinish(ITestContext context) {
        reports.flush();

    }

}
