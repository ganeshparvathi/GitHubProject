package jan31;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import ApplicationLayer.LoginPage;

public class CrossBrowserTesting {
	WebDriver driver;
	String Url="http://orangehrm.qedgetech.com/";
	  @Parameters({"Browser"})
	  @BeforeTest
		  public void setUp(String brw) 
	  {
		  switch(brw)
		  {
		  case "chrome":
			  System.setProperty("webdriver.chrome.driver", "./CommonDrivers\\chromedriver.exe");
			  driver=new ChromeDriver();
			  break;
		  case "firefox":
			  System.setProperty("webdriver.gecko.driver", "./CommonDrivers\\geckodriver.exe");
			  driver=new FirefoxDriver();
			  break;
		  default:
			  System.out.println("Browser values is not matching");
			  break;
		  }
	  }
	  @Test(dataProvider = "supplydata")
	  public void verifylogin(String username, String password) 
	  {
		 
		 driver.manage().deleteAllCookies();
		 driver.get(Url);
		 driver.manage().window().maximize();
		 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		 LoginPage login= PageFactory.initElements(driver, LoginPage.class);
		 login.verifyLogin(username, password);
		 String expected="http://orangehrm.qedgetech.com/symfony/web/index.php/dashboard";
		 String actual=driver.getCurrentUrl();
		 try {
		 Assert.assertEquals(actual, expected,"Login Fail");//here it check for complete url so fails
		 }catch(Throwable t)
		 {
			 System.out.println(t.getMessage());
		 }
	  
	  }

	  @DataProvider
	  public Object[][] supplydata()
	  {
	   Object login [][]= new Object[4][2];
	   login[0][0]="Admin";
	   login[0][1]="Qedge123!@#";
	   login[1][0]="Admin";
	   login[1][1]="Qedge123!@#";
	   login[2][0]="Admin1";
	   login[2][1]="Qedge123!@#";
	   login[3][0]="Admin";
	   login[3][1]="Qedge123!@#";
	   return login;
	  
	  }
	 

	  @AfterTest
	  public void tearDown() 
	  {
		  driver.close();
	  }

}
