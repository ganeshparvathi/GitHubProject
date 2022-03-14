package CommonFunctions;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;

import Constant.PBConstant;

public class FunctionLibrary extends PBConstant{
	//method for login
	public static boolean verifyLogin(String username,String password)throws Throwable
	{
		driver.findElement(By.xpath(config.getProperty("Objusename"))).sendKeys(username);
		driver.findElement(By.xpath(config.getProperty("Objpassword"))).sendKeys(password);
		driver.findElement(By.xpath(config.getProperty("Objloginbtn"))).click();
		Thread.sleep(4000);
		String expected="adminflow";
		String actual=driver.getCurrentUrl();
		Thread.sleep(2000);
		if(actual.toLowerCase().contains(expected.toLowerCase()))
		{
			Reporter.log("Login success::"+expected+"      "+actual,true);
			return true;
		}
		else
		{
			Reporter.log("Login Fail::"+expected+"    "+actual,true);
			return false;
		}

	}
	//method for click branches
	public static void clickBranches()throws Throwable
	{
		driver.findElement(By.xpath(config.getProperty("Objbranches"))).click();
		Thread.sleep(4000);
	}
	//method for branch creation
	public static boolean verifyNewBranch(String bname,String Address1,String Address2,String Address3,String Area,String zip,String country,String state,String city)
			throws Throwable{
		driver.findElement(By.xpath(config.getProperty("Objnewbranch"))).click();
		Thread.sleep(4000);
		driver.findElement(By.xpath(config.getProperty("Objbname"))).sendKeys(bname);
		driver.findElement(By.xpath(config.getProperty("ObjAddress1"))).sendKeys(Address1);
		driver.findElement(By.xpath(config.getProperty("ObjAddress2"))).sendKeys(Address2);
		driver.findElement(By.xpath(config.getProperty("ObjAddress3"))).sendKeys(Address3);
		driver.findElement(By.xpath(config.getProperty("ObjArea"))).sendKeys(Area);
		driver.findElement(By.xpath(config.getProperty("Objzipcode"))).sendKeys(zip);
		new Select(driver.findElement(By.xpath(config.getProperty("Objcountry")))).selectByVisibleText(country);
		Thread.sleep(4000);
		new Select(driver.findElement(By.xpath(config.getProperty("Objstate")))).selectByVisibleText(state);
		Thread.sleep(4000); 
		new Select(driver.findElement(By.xpath(config.getProperty("Objcity")))).selectByVisibleText(city);
		Thread.sleep(4000);
		driver.findElement(By.xpath(config.getProperty("Objsubmitbtn"))).click();
		//capture alert message
		String expectedalert=driver.switchTo().alert().getText();
		Thread.sleep(4000);
		driver.switchTo().alert().accept();
		String actualAlert="New Branch with";
		if(expectedalert.toLowerCase().contains(actualAlert.toLowerCase()))
		{
			Reporter.log("Branch creation Success::"+expectedalert+"   "+actualAlert,true);
			return true;
		}
		else
		{
			Reporter.log("Branch creation Fail::"+expectedalert+"   "+actualAlert,true);
			return false;
		}
	}

	//method for branch updation
	public static boolean verifyBranchUpdation(String branch,String address1,String zipcode)throws Throwable
	{
		driver.findElement(By.xpath(config.getProperty("Editbtn"))).click();
		Thread.sleep(4000);
		WebElement branchname= driver.findElement(By.xpath(config.getProperty("ObjBranch")));
		branchname.clear();
		branchname.sendKeys(branch);
		WebElement Add=driver.findElement(By.xpath(config.getProperty("ObjAddress")));
		Add.clear();
		Add.sendKeys(address1);
		Thread.sleep(4000);
		WebElement Objzipcode=driver.findElement(By.xpath(config.getProperty("Objzip")));
		Objzipcode.clear();
		Objzipcode.sendKeys(zipcode);
		Thread.sleep(4000);
		driver.findElement(By.xpath(config.getProperty("Objupdatebtn"))).click();
		String expectedupadatealert=driver.switchTo().alert().getText();
		driver.switchTo().alert().accept();
		String actualupadatealert= "Branch updated";
		if(expectedupadatealert.toLowerCase().contains(actualupadatealert.toLowerCase()))
		{
			Reporter.log("Branch update success::"+expectedupadatealert+"   "+actualupadatealert);
			return true;
		}
		else
		{
			Reporter.log("Branch update fail::"+expectedupadatealert+"   "+actualupadatealert);
			return false;
		}
	}
	//method for logout
	public static boolean verifyLogout()throws Throwable
	{
		driver.findElement(By.xpath(config.getProperty("Objlogout"))).click();
		Thread.sleep(4000);
		if(driver.findElement(By.xpath(config.getProperty("Objloginbtn"))).isDisplayed())
		{
			Reporter.log("Admin Logout success:::",true);
			return true;
		}
		else
		{
			Reporter.log("Admin Logout fail:::",true);
			return false;
		} 
	}
	public static String generateDate()
	{
		Date date= new Date();
		DateFormat df=new SimpleDateFormat("YYYY_MM_dd hh_mm_ss");
		return df.format(date);
	}

}
