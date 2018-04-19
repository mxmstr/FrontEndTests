import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class NutreTests {
	
	private static String driverPath = "/users/ericlynch/documents/chromedriver";
	private static String homePage = "http://dev.gonutre.com";
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
	
	@Test
	public void test() {
		
		System.setProperty("webdriver.chrome.driver", driverPath);
    	
		threads = new ArrayList<AsyncTester>();
        WebDriver driver = new ChromeDriver();
        
        driver.manage().window().maximize();
        driver.manage().window().setSize(
        		new Dimension(
        				1440,
        				driver.manage().window().getSize().getHeight()));
        driver.get(homePage);
        
        addThread(new Search(driver, homePage));
        joinThreads();
        
        driver.quit();
		
	}

}
