package DriverFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import ApplicationLayer.AdminLogOut;
import ApplicationLayer.EmpPage;
import ApplicationLayer.LoginPage;

public class TestScript {
WebDriver driver;
@BeforeTest
public void setUp()throws Throwable
{
	System.setProperty("webdriver.chrome.driver", "./CommonDrivers/chromedriver.exe");
	driver=new ChromeDriver();
	driver.manage().deleteAllCookies();
	driver.manage().window().maximize();
	driver.get("http://orangehrm.qedgetech.com/");
	Thread.sleep(3000);
	LoginPage login =PageFactory.initElements(driver, LoginPage.class);//Pagefactory class will acess all the methods and locators that are stored in loginpage class and store into login object
	login.verifyLogin("Admin", "Qedge123!@#");
}
@Test
public void employeecreation()throws Throwable
{
	EmpPage emp=PageFactory.initElements(driver, EmpPage.class);
	emp.verifyEmp("Testing", "Selenium");
}
@AfterTest
public void tearDown()throws Throwable
{
	AdminLogOut logout=PageFactory.initElements(driver, AdminLogOut.class);
	logout.verifyAdminLogOut();
	driver.close();
}
}
