import java.net.HttpURLConnection;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class Header extends Thread {
	
	private WebDriver driver;
	private String homePage;
	private static HttpURLConnection conn = null;
	
	public Header(WebDriver driver, String homePage) {
		
		this.driver = driver;
		this.homePage = homePage;
	
	}
   
	public void run() {
		
		try
        {
            System.out.println(driver.findElements(By.className("topbar__menu")).size());
        }
        catch (NoSuchElementException e)
        {
        	System.out.println("No such element");
        }
		
	}
	
}

