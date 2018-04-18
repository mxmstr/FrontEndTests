import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class NutreTests {
	
	private static String driverPath = "/users/ericlynch/documents/chromedriver";
	private static String homePage = "http://dev.gonutre.com";
	
	@Test
	public void test() {
		
		System.setProperty("webdriver.chrome.driver", driverPath);
    	
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(homePage);
        
        try {
        	Thread t = new BrokenLinks(driver, homePage);
            t.start();
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        driver.quit();
		
	}

}
