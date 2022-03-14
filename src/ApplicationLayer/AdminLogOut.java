package ApplicationLayer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class AdminLogOut {
WebDriver driver;
public  AdminLogOut(WebDriver driver)
{
	this.driver=driver;
	
}
@FindBy(xpath="//a[@id='welcome']")
WebElement clickwelcome;
@FindBy(xpath="//a[normalize-space()='Logout']")
WebElement clicklogout;
public void verifyAdminLogOut()throws Throwable
{
	Actions ac=new Actions(driver);
	ac.moveToElement(clickwelcome).click().perform();
	Thread.sleep(3000);
	ac.moveToElement(clicklogout).click().perform();
}
}
