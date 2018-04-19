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


public class Search extends Thread {
	
	private WebDriver driver;
	private String homePage;
	private static HttpURLConnection conn = null;
	
	
	public Search(WebDriver driver, String homePage) {
		
		this.driver = driver;
		this.homePage = homePage;
	
	}

	  private boolean isElementPresent(By by) {
	    try {
	      driver.findElement(by);
	      return true;
	    } catch (NoSuchElementException e) {
	      return false;
	    }
	  }
	
	
	public void run() {
		
		try {
			
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		    driver.get(homePage + "/");
		    
		    driver.findElement(By.cssSelector("input.pt-input")).clear();
		    driver.findElement(By.cssSelector("input.pt-input")).sendKeys("p");
		    
		    driver.findElement(By.linkText("Pasta")).click();
		    
		    assert(!isElementPresent(By.cssSelector("div.topbar__search-block")));
		    //System.out.println("Autofill didn't close.");
		    
		}
		catch (InvalidElementStateException e) {
			System.out.println("Screen is too small to display search bar.");
		}
		    
	}
	
}