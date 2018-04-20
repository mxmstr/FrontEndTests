import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class PageLoad extends Thread {
	
	private WebDriver driver;
	private String homePage;
	
	public PageLoad(WebDriver driver, String homePage) {
		
		this.driver = driver;
		this.homePage = homePage;
	
	}
   
	public void run() {
		
		Long navigationStart = (Long)((JavascriptExecutor)driver).executeScript(
        		"return window.performance.timing.navigationStart");
        Long responseStart = (Long)((JavascriptExecutor)driver).executeScript(
        		"return window.performance.timing.responseStart");
        Long domComplete = (Long)((JavascriptExecutor)driver).executeScript(
        		"return window.performance.timing.domComplete");

		Long backendPerformance = responseStart - navigationStart;
		Long frontendPerformance = domComplete - responseStart;

		System.out.println("Back End: " + backendPerformance);
		System.out.println("Front End: " + frontendPerformance);
	   
	}
}

