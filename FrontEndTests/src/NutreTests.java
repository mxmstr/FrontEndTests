import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class NutreTests {
	
	private static String driverPath = "/users/ericlynch/documents/chromedriver";
	private static String homePage = "http://dev.gonutre.com";
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
	
	private void testSignin(){
		
	    try {
	    	driver.findElement(By.linkText("Log In")).click();
		    driver.findElement(By.cssSelector("input[type='email']")).clear();
		    driver.findElement(By.cssSelector("input[type='email']")).sendKeys("lynch.er18@gmail.com");
		    driver.findElement(By.cssSelector("input[type='password']")).clear();
		    driver.findElement(By.cssSelector("input[type='password']")).sendKeys("123123");
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
		driver = new ChromeDriver();
        
        driver.manage().window().maximize();
        driver.manage().window().setSize(
        		new Dimension(
        				1440,
        				driver.manage().window().getSize().getHeight()));
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(homePage);
        
        testSignin();
	
	}
	
	//@Test
	public void HeaderFooter() {
		
        addThread(new Search(driver, homePage));
        joinThreads();
		
	}
	
	//@Test
	public void Menu() {
		
		new Menu(driver, homePage);
        joinThreads();
		
	}
	
	@Test
	public void Promo() {

	    try {
	    	
	    	String bodyText;
	    	
	    	driver.findElement(By.cssSelector(
					"button.pt-button.pt-minimal.pt-icon-shopping-cart.topbar__cart-btn.topbar__ma-top-8")).click();
			
	    	driver.findElement(By.cssSelector("input.pt-input.pt-fill")).clear();
		    driver.findElement(By.cssSelector("input.pt-input.pt-fill")).sendKeys("123123");
		    driver.findElement(By.cssSelector("button.cart__promocode-btn")).click();
	    	
			Thread.sleep(2000);
			
			bodyText = driver.findElement(By.tagName("body")).getText();
	    	Assert.assertTrue("Used code accepted!", bodyText.contains("Code is invalid or already used."));
	    	
	    	driver.findElement(By.cssSelector("input.pt-input.pt-fill")).clear();
		    driver.findElement(By.cssSelector("input.pt-input.pt-fill")).sendKeys("abc");
		    driver.findElement(By.cssSelector("button.cart__promocode-btn")).click();
	    	
			Thread.sleep(2000);
			
			bodyText = driver.findElement(By.tagName("body")).getText();
	    	Assert.assertTrue("Invalid code accepted!", bodyText.contains("Code is invalid or already used."));
		
		} 
	    catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
	}
	
	@After
	public void tearDown() {
		
		driver.quit();
		
	}

}
