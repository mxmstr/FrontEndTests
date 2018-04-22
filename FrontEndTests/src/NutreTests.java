import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;


public class NutreTests {
	
	private static String driverPath = "/users/ericlynch/documents/chromedriver";
	private static String homePage = "http://dev.gonutre.com";
	private static String email = "lynch.er18@gmail.com";
	private static String password = "123123";
	
	private WebDriver driver;
	private ArrayList<AsyncTester> threads;
	
	
	private void addThread(Thread t) {
		
		AsyncTester test = new AsyncTester(t);
        test.start();
        threads.add(test);
		
	}
	
	private void joinThreads() {
		
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
	
	private void signIn(){
		
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
	
	@Before
	public void setUp() throws Exception {
		
		System.setProperty("webdriver.chrome.driver", driverPath);
		
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
	
	//@Test
	public void HeaderFooter() {
		
        addThread(new Search(driver, homePage));
        joinThreads();
		
	}
	
	private void add10ItemsToCart() throws InterruptedException {
		
	    driver.findElement(By.linkText("A la Carte")).click();
	    driver.findElement(By.xpath("//label[contains(.,'Dinner')]")).click();
	    
	    Thread.sleep(1000);
	    
	    new Select(driver.findElement(By.cssSelector("select"))).selectByVisibleText("5");
	    new Select(driver.findElement(By.xpath("//div[@id='root']/div/div/div[2]/div[2]/div[2]/div[2]/div/div/div[2]/div/div/select"))).selectByVisibleText("5");
	    
	    Thread.sleep(1000);
	    
	    driver.findElement(By.cssSelector("button[name=\"Soup 1\"]")).click();
	    driver.findElement(By.cssSelector("button[name=\"Pizza\"]")).click();
	    
	}
	
	private void redeemPromoCode(String code) {
			
		driver.findElement(By.cssSelector("input.pt-input.pt-fill")).clear();
	    driver.findElement(By.cssSelector("input.pt-input.pt-fill")).sendKeys(code);
	    driver.findElement(By.cssSelector("button.cart__promocode-btn")).click();
    	
	}
	
	//@Test
	public void checkoutItems() throws InterruptedException {
		
		String bodyText;
		
    	add10ItemsToCart();
    	Thread.sleep(1000);
    	
    	driver.findElement(By.cssSelector(
				"button.pt-button.pt-minimal.pt-icon-shopping-cart.topbar__cart-btn.topbar__ma-top-8")).click();
    	Thread.sleep(1000);
    	
    	
    	redeemPromoCode("123123");
    	Thread.sleep(1000);
    	
    	bodyText = driver.findElement(By.tagName("body")).getText();
    	Assert.assertTrue("Used code accepted!", bodyText.contains("Code is invalid or already used."));
    	
    	
    	redeemPromoCode("abc");
    	Thread.sleep(1000);
    	
    	bodyText = driver.findElement(By.tagName("body")).getText();
    	Assert.assertTrue("Invalid code accepted!", bodyText.contains("Code is invalid or already used."));
		
    	
    	Assert.assertTrue(
    			"Checkout without shipping info!",
    			!driver.findElement(By.cssSelector("button.cart__checkout-btn")).isEnabled());
    	
    	
    	driver.findElement(By.xpath("//button[@type='button']")).click();
    	driver.findElement(By.xpath("(//button[@type='button'])[3]")).click();
    	//driver.findElement(By.name("Apply")).click();
        driver.findElement(By.cssSelector("button.cart__checkout-btn")).click();
        driver.findElement(By.name("cardnumber")).clear();
        driver.findElement(By.name("cardnumber")).sendKeys("4242 4242 4242 4242");
        driver.findElement(By.name("exp-date")).clear();
        driver.findElement(By.name("exp-date")).sendKeys("‎02 / ‎20");
        driver.findElement(By.name("cvc")).clear();
        driver.findElement(By.name("cvc")).sendKeys("123");
        driver.findElement(By.name("postal")).clear();
        driver.findElement(By.name("postal")).sendKeys("02116");
        driver.findElement(By.xpath("//form/div/div/button")).click();
    	
	}
	
	private void changeAccountName(String name) {
		
		driver.findElement(By.xpath("//div[@id='root']/div/div/div[2]/div/div/div[2]/h2/span")).click();
		
	    WebElement nameField = driver.findElement(By.cssSelector("div.content__input-ma:nth-child(1) > div:nth-child(2) > input:nth-child(1)"));
	    nameField.clear();
	    nameField.sendKeys(name);
	    
	    driver.findElement(By.cssSelector("button.nu-button-h.content__button")).click();
	    driver.findElement(By.xpath("//div[@id='root']/div/div/div[2]/div/div/div[2]/h2/span")).click();
		
	}
	
	@Test
	public void changeAccountDetails() throws InterruptedException {
		
		driver.findElement(By.linkText("Account")).click();
    	Thread.sleep(1000);
		
	    changeAccountName("Joe");
	    
	    Assert.assertTrue(
	    		"Name was not updated!",
	    		driver.findElement(By.cssSelector(
	    				"div.row:nth-child(3) > div:nth-child(2)")).getText().contains("Joe"));
	    
	    changeAccountName("John");
	    
	}
	
	@After
	public void tearDown() {
		
		driver.quit();
		
	}

}
