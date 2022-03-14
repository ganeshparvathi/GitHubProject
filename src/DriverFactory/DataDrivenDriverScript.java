package DriverFactory;
import java.io.File;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import Utilities.ExcelFileUtil;

public class DataDrivenDriverScript {
	WebDriver driver;
	String inputpath="D:\\Automation_Selenium\\Automation_FrameWorks\\TestInput\\LoginData.xlsx";
	String outputpath="D:\\Automation_Selenium\\Automation_FrameWorks\\TestOutput\\DataDrivenResults.xlsx";
Properties config;
ExtentReports report;
ExtentTest test;
@BeforeTest
public void setUp()
{
	//define path to generate reports
	report=new ExtentReports("./Reports/Datadrivern.html");
	System.setProperty("webdriver.chrome.driver", "./CommonDrivers/chromedriver.exe");
	driver=new ChromeDriver();
	driver.manage().deleteAllCookies();
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
}
@Test
public void verifyLogin()throws Throwable
{
	config=new Properties();
	config.load(new FileInputStream("D:\\Automation_Selenium\\Automation_FrameWorks\\PropertyFile\\Login.properties"));
//create reference object to call all excel methods
	ExcelFileUtil xl= new ExcelFileUtil(inputpath);
	//count no of rows in a sheet
	int rc= xl.rowCount("Login");
	Reporter.log("No of rows are:: "+rc,true);
	for(int i=1;i<=rc;i++)
	{
		test=report.startTest("Validate Login");
		driver.get(config.getProperty("Url"));
		//read cell data from excel
		String username= xl.getCellData("Login", i, 0);
		String password= xl.getCellData("Login", i, 1);
		driver.findElement(By.xpath(config.getProperty("ObjUser"))).sendKeys(username);
		driver.findElement(By.xpath(config.getProperty("Objpass"))).sendKeys(password);
		driver.findElement(By.xpath(config.getProperty("ObjLogin"))).click();
		String expected="dashboard";
		String actual=driver.getCurrentUrl();
		if(actual.contains(expected))
		{
			//write as login success into results cell
		xl.setCellData("Login", i, 2, "Login success", outputpath);
		//write as pass into status cell
		xl.setCellData("Login", i, 3, "Pass",outputpath);
		test.log(LogStatus.PASS, "Login success:::"+expected+"          "+actual);
		Reporter.log("Login success::"+expected+"       "+actual,true);
		}
		else
		{
			//take screen shot for fail test
			File screen=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screen, new File("./Screens/Iteration/"+i+"Loginpage.png"));
			//capture error message
			String errormessage=driver.findElement(By.xpath(config.getProperty("Objmessage"))).getText();
			//write as login fail into results cell
			xl.setCellData("Login",i,2,errormessage,outputpath);
			xl.setCellData("Login",i,3,"Fail",outputpath);
			test.log(LogStatus.FAIL,errormessage+"       "+expected+"              "+actual);
			Reporter.log("Login Fail::"+errormessage+"   "+expected+"              "+actual,true);
		}
		report.endTest(test);
		report.flush();
	}
}
	@AfterTest
	public void tearDown()
	{
		driver.close();
}
}