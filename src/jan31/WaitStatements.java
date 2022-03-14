package jan31;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitStatements {

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "./CommonDrivers\\chromedriver.exe");
		WebDriver driver= new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://primusbank.qedgetech.com/");
		driver.manage().deleteAllCookies();
		driver.findElement(By.name("txtuId")).sendKeys("Admin");
		driver.findElement(By.name("txtPword")).sendKeys("Admin");
		driver.findElement(By.name("login")).click();;
		driver.findElement(By.xpath("(//img)[5]")).click();
		driver.findElement(By.xpath("(//img)[2]")).click();
		driver.findElement(By.xpath("(//img)[6]")).click();
		driver.findElement(By.xpath("(//img)[2]")).click();
		driver.findElement(By.xpath("(//img)[7]")).click();
		driver.findElement(By.xpath("(//img)[2]")).click();
		driver.findElement(By.xpath("(//img)[8]")).click();
		driver.findElement(By.xpath("(//img)[2]")).click();
		WebDriverWait wait=new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Customer')]")));
		driver.findElement(By.xpath("//a[contains(text(),'Customer')]")).click();
		driver.close();

	}

}
