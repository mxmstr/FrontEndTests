import static org.junit.Assert.fail;

import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;


public class Menu extends Thread {
	
	private WebDriver driver;
	private String homePage;
	
	public Menu(WebDriver driver, String homePage) {
		
		this.driver = driver;
		this.homePage = homePage;
	
	}

	private boolean isElementPresent(By by) {
		
		try {
			driver.findElement(by);
			return true;
	    } 
		catch (NoSuchElementException e) {
			return false;
	    }
		
	}
	
	public void run() {
		
	    driver.findElement(By.linkText("A la Carte")).click();
	    driver.findElement(By.xpath("(//input[@value='on'])[3]")).click();
	    //driver.findElement(By.xpath("//div[@id='root']/div/div/div[2]/div[2]/div/div/div[2]/label[3]")).click();
	    /*new Select(driver.findElement(By.cssSelector("select"))).selectByVisibleText("3");
	    new Select(driver.findElement(By.xpath("//div[@id='root']/div/div/div[2]/div[2]/div[2]/div[2]/div/div/div[2]/div/div/select"))).selectByVisibleText("3");
	    driver.findElement(By.cssSelector("button[name=\"Soup 1\"]")).click();
	    driver.findElement(By.name("Pizza")).click();
	    driver.findElement(By.cssSelector("button[name=\"Soup 1\"]")).click();
	    driver.findElement(By.name("Pizza")).click();
	    driver.findElement(By.xpath("//div[@id='root']/div/div/div/div/div/div/div/button")).click();*/
		    
	}
	
}