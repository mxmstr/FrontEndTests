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
		
		if (System.getProperty("webdriver.chrome.driver") == null) {
			System.setProperty("webdriver.chrome.driver", "/Users/Lynch/Documents/chromedriver.exe");
			System.setProperty("homePage", "http://dev.gonutre.com");
			System.setProperty("email", "lynch.er18@gmail.com");
			System.setProperty("password", "123123");
		}
		
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
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
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
	
	public void add1ItemToCart() throws InterruptedException {
		
	    driver.findElement(By.linkText("A la Carte")).click();
	    driver.findElement(By.xpath("//label[contains(.,'Dinner')]")).click();
	    
	    Thread.sleep(1000);
	    
	    new Select(driver.findElement(By.cssSelector("select"))).selectByVisibleText("1");
	    
	    Thread.sleep(1000);
	    
	    driver.findElement(By.cssSelector("button[name=\"Soup 1\"]")).click();
	    
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
	
	public void removeSubscription() throws InterruptedException  {
		
	    driver.findElement(By.linkText("Subscription")).click();
	    Thread.sleep(1000);
	    
	    if (isElementPresent(By.xpath("//div[@id='root']/div/div/div[2]/div/div/div[2]/div/div[2]/button[2]"))) {
		    driver.findElement(By.xpath("//div[@id='root']/div/div/div[2]/div/div/div[2]/div/div[2]/button[2]")).click();
		    Thread.sleep(1000);
		    driver.findElement(By.cssSelector("button.nu-button.subscription__cancel__confirm")).click();
	    }
		
	}
	
	public void addSubscription() throws InterruptedException {
		
		
		if (driver.findElement(By.cssSelector("label.pt-control:nth-child(7) > span:nth-child(2)")).isSelected())
			driver.findElement(By.xpath("//label[contains(.,'Gluten Free')]")).click();
	    Thread.sleep(1000);
	    
	    //new Select(driver.findElement(By.xpath("//div[@id='root']/div/div/div[2]/div[2]/div[2]/div[2]/div[4]/div/div/div/div/select"))).selectByVisibleText("10");
	    //new Select(driver.findElement(By.xpath("//div[@id='root']/div/div/div[2]/div[2]/div[2]/div[2]/div[2]/div/div/div/div/select"))).selectByVisibleText("5");
	    Thread.sleep(1000);
	    
	    driver.findElement(By.cssSelector("button.nu-button.carte__plan-weekly-btn")).click();
	    Thread.sleep(1000);
	    
	    String bodyText = driver.findElement(By.tagName("body")).getText();
    	if (bodyText.contains("You didn't customize your order, are you sure you want to add it to your cart?"))
    		driver.findElement(By.cssSelector(
    				"body > div.pt-portal > div > span > div.pt-dialog-container.pt-overlay-content > div > div.pt-alert-footer > button:nth-child(1) > span")).click();
	    
	    
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
	
	public void removePaymentInfo() {
		
		driver.findElement(By.linkText("Account")).click();
	    driver.findElement(By.linkText("Payment Information")).click();
	    
	    if (isElementPresent(By.xpath("//div[@id='root']/div/div/div[2]/div/div/div[2]/div[3]/div/span"))) {
	    	driver.findElement(By.xpath("//div[@id='root']/div/div/div[2]/div/div/div[2]/div[3]/div/span")).click();
	    	driver.findElement(By.xpath("//div[@id='root']/div/div/div[2]/div/div/div[2]/div[3]/div/div[3]/table/tbody/tr/td[5]/button")).click();
	    }
	    
	}
	
	public void checkout(String cardnumber, String date, String cvc, String postal) throws InterruptedException {
		
	    driver.switchTo().frame(driver.findElement(By.cssSelector(
	    		".__PrivateStripeElement > iframe:nth-child(1)")));
	    
	    driver.findElement(By.name("cardnumber")).clear();
	    driver.findElement(By.name("cardnumber")).sendKeys(cardnumber);
	    Thread.sleep(1000);
	    driver.findElement(By.name("exp-date")).clear();
	    driver.findElement(By.name("exp-date")).sendKeys(date);
	    Thread.sleep(1000);
	    driver.findElement(By.name("cvc")).clear();
	    driver.findElement(By.name("cvc")).sendKeys(cvc);
	    Thread.sleep(1000);
	    if (isElementPresent(By.name("postal"))) {
		    driver.findElement(By.name("postal")).clear();
		    driver.findElement(By.name("postal")).sendKeys(postal);
	    }
	    
	    driver.switchTo().defaultContent();
	    
	    driver.findElement(By.xpath("//form/div/div/button")).click();
	    
	}

}
