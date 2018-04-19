import static org.junit.Assert.fail;

import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
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
	
	
	public void run() {
		
		try {
				
			setUp();
			
		    driver.get(homePage + "/");
		    driver.findElement(By.cssSelector("input.pt-input")).clear();
		    driver.findElement(By.cssSelector("input.pt-input")).sendKeys("p");
		    driver.findElement(By.linkText("Pasta")).click();
		    
		    tearDown();
		    
		}
		catch (InvalidElementStateException e) {
			System.out.println("Screen is too small to display search bar.");
		}
		    
	}
	
}