import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;


public abstract class FrontEndTest {
	
	public String homePage;
	public String email;
	public String password;
	public WebDriver driver;
	public ArrayList<AsyncTester> threads;
	
	
	@Before
	public void setUp() throws Exception {
		
		homePage = System.getProperty("homePage");
		email = System.getProperty("email");
		password = System.getProperty("password");
		
		threads = new ArrayList<AsyncTester>();
		
		while (true) {
			
			try {
				driver = new ChromeDriver();
				break;
			}
			catch (WebDriverException e) {}
			
		}
        
        driver.manage().window().maximize();
        driver.manage().window().setSize(
        		new Dimension(
        				1440,
        				driver.manage().window().getSize().getHeight()));
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(homePage);
        
        signIn();
	
	}
	
	@After
	public void tearDown() {
		
		driver.quit();
		
	}
	
	public void addThread(Thread t) {
		
		AsyncTester test = new AsyncTester(t);
        test.start();
        threads.add(test);
		
	}
	
	public void joinThreads() {
		
		for (AsyncTester test : threads) {
			try {
				test.join();
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			catch (java.lang.AssertionError e) {
				System.out.println("Assertion error");
				fail();
			}
		}
		
	}
	
	public boolean isElementPresent(By by) {
		
		try {
			driver.findElement(by);
			return true;
	    } 
		catch (NoSuchElementException e) {
			return false;
	    }
		
	}
	
	public void clickJS(WebElement e) {
		
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", e);
		
	}
	
	public void sendEscapeKey() throws InterruptedException {
		
		Actions action = new Actions(driver);
    	action.sendKeys(Keys.ESCAPE).build().perform();
    	Thread.sleep(1000);
		
	}
	
	public void signIn(){
		
	    try {
	    	driver.findElement(By.linkText("Log In")).click();
		    driver.findElement(By.cssSelector("input[type='email']")).clear();
		    driver.findElement(By.cssSelector("input[type='email']")).sendKeys(email);
		    driver.findElement(By.cssSelector("input[type='password']")).clear();
		    driver.findElement(By.cssSelector("input[type='password']")).sendKeys(password);
		    driver.findElement(By.xpath("//button[contains(.,'Log In')]")).click();
		    
			Thread.sleep(500);
		} 
	    catch (InterruptedException e) {
			e.printStackTrace();
		}
	    
	}
	
	public void add10ItemsToCart() throws InterruptedException {
		
	    driver.findElement(By.linkText("A la Carte")).click();
	    driver.findElement(By.xpath("//label[contains(.,'Dinner')]")).click();
	    
	    Thread.sleep(1000);
	    
	    new Select(driver.findElement(By.cssSelector("select"))).selectByVisibleText("5");
	    new Select(driver.findElement(By.xpath("//div[@id='root']/div/div/div[2]/div[2]/div[2]/div[2]/div/div/div[2]/div/div/select"))).selectByVisibleText("5");
	    
	    Thread.sleep(1000);
	    
	    driver.findElement(By.cssSelector("button[name=\"Soup 1\"]")).click();
	    driver.findElement(By.cssSelector("button[name=\"Pizza\"]")).click();
	    
	}
	
	public void redeemPromoCode(String code) {
			
		driver.findElement(By.cssSelector("input.pt-input.pt-fill")).clear();
	    driver.findElement(By.cssSelector("input.pt-input.pt-fill")).sendKeys(code);
	    driver.findElement(By.cssSelector("button.cart__promocode-btn")).click();
    	
	}
	
	public void removeTableElement(String name) throws InterruptedException {
		
		WebElement table = driver.findElement(By.cssSelector(".pt-table"));
    	List<WebElement> tableRows = table.findElements(By.tagName("tr"));
    	
    	for (WebElement e : tableRows) {
    		if (e.getText().contains(name)) {

    			clickJS(e);
    			driver.findElement(By.cssSelector("button.pt-button:nth-child(4)")).click();
    			driver.findElement(By.cssSelector("button.pt-intent-danger:nth-child(1)")).click();
    			Thread.sleep(1000);
    			break;
    			
    		}
    	}
		
	}

}
