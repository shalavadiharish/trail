package com.qsp.ohrm.test;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qsp.ohrm.page.OrangeDashboardPage;
import com.qsp.ohrm.page.OrangeHRMLoginPage;
import com.qsp.ohrm.utils.ConfigFileReader;
import com.qsp.ohrm.utils.DriverUtils;
import com.qsp.ohrm.utils.ExtentReportFactory;
import com.qsp.ohrm.utils.Log;
import com.qsp.ohrm.utils.OrangeHRMUtils;
import com.qsp.ohrm.utils.Screenshots;

public class ScriptDevelopment extends BaseTest{

	OrangeHRMLoginPage oLoginpage= null;
	OrangeDashboardPage odp = null;
	
	@BeforeClass
 	public void preConfig(){
 		Log.startReport("setup");
 		
		driver = DriverUtils.getWebDriver();
		oLoginpage = new OrangeHRMLoginPage(driver);
		odp = new OrangeDashboardPage(driver);
		
 	}
	//@Test(groups={"smoke"})
	public void validateLoginTest(){
		Log.startReport(DriverUtils.getMethodName());
		OrangeHRMUtils.launchApp(driver, config.getApplicationUrl());
		Log.pass("Login to Applicaiton Success");
		Log.info("--Completeds Executing Test - validateLoginTest");
 		//Log.endReport("validateLoginTest");
	}
	
	@AfterMethod
	public void tearDown(ITestResult testResult) throws IOException {
		Log.info("inside after method with " +testResult.getStatus());
		Log.info(String.valueOf(ITestResult.FAILURE));
		if (testResult.getStatus() == ITestResult.FAILURE) {
			String path = Screenshots.takeScreenshot(driver, testResult.getName()+ExtentReportFactory.getCurrentDateAndTime());
			Log.info("Path " + path);
			Log.ssPath.add(path);
			Log.attachScreenShot(path);
		}
		
		Log.endReport();
	}
	
	
	@AfterClass
	public void tearDown(){
		try{
			ExtentReportFactory.sendReportByGMail();
		}catch(Exception ex){
			System.out.println(ex);
		}finally{
			if(driver!=null){
					driver.close();
					driver = null;
			}
		}
	}
}
