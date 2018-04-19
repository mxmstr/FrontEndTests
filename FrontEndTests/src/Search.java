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
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	
	
	public Search(WebDriver driver, String homePage) {
		
		this.driver = driver;
		this.homePage = homePage;
	
	}
   
	  public void setUp() {
	    //driver = new FirefoxDriver();
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  }
	  
	  public void tearDown() {
	    driver.quit();
	    String verificationErrorString = verificationErrors.toString();
	    if (!"".equals(verificationErrorString)) {
	      fail(verificationErrorString);
	    }
	  }

	  private boolean isElementPresent(By by) {
	    try {
	      driver.findElement(by);
	      return true;
	    } catch (NoSuchElementException e) {
	      return false;
	    }
	  }

	  private boolean isAlertPresent() {
	    try {
	      driver.switchTo().alert();
	      return true;
	    } catch (NoAlertPresentException e) {
	      return false;
	    }
	  }

	  private String closeAlertAndGetItsText() {
	    try {
	      Alert alert = driver.switchTo().alert();
	      String alertText = alert.getText();
	      if (acceptNextAlert) {
	        alert.accept();
	      } else {
	        alert.dismiss();
	      }
	      return alertText;
	    } finally {
	      acceptNextAlert = true;
	    }
	  }
	
	
	public void run() {
		
		try {
				
			setUp();
			
		    driver.get(homePage + "/");
		    
		    
		    
		    WebElement searchInput = driver.findElement(By.cssSelector("input.pt-input"));
		    searchInput.clear();
		    searchInput.sendKeys("p");
		    
		    driver.findElement(By.linkText("Pasta")).click();
		    
		    if (isElementPresent(By.cssSelector("div.topbar__search-block")))
		    	System.out.println("Autofill didn't close.");
		    
		    tearDown();
		    
		}
		catch (InvalidElementStateException e) {
			System.out.println("Screen is too small to display search bar.");
		}
		    
	}
	
}