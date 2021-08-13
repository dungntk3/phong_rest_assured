package log;

import java.lang.reflect.Method;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.slf4j.Logger;

import api.util.RestUtil;

public class SetupLog implements ITestListener{
	
	public final Logger log = LogHelper.getLogger();
	
	@BeforeMethod
	public void beforeMethod(Method method) {
		RestUtil.testCaseName = method.getName();
		log.info(" __RunTCStart__ " + RestUtil.testCaseName + " __RunTCStart__ " + RestUtil.LINE_BREAK);
	}
	
	@AfterMethod
	public void afterMethod(Method method, ITestResult result){
		try {
			log.info(" __RunTCEnd__ " + method.getName() + " __RunTCEnd__ " + RestUtil.LINE_BREAK);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

}
