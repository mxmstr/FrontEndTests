import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;


public class BrokenLinks extends FrontEndTest {
	
	ArrayList<String> checkedLinks = new ArrayList<String>();
	
	private void parseLink(String homepage) throws TimeoutException {
		
		boolean broken = false;
		HttpURLConnection conn = null;
		String url = "";
		List<WebElement> links = driver.findElements(By.tagName("a"));
		ArrayList<String> linksToParse = new ArrayList<String>();
		Iterator<WebElement> it = links.iterator();
		
		
		long startTime = System.currentTimeMillis();
		long elapsedTime = 0L;
		
		while (driver.findElements(By.cssSelector(".pt-spinner-track")).size() > 0) {
			elapsedTime = (new Date()).getTime() - startTime;
			if (elapsedTime > 30 * 1000)
				throw new TimeoutException();
		}
		
		
		while (it.hasNext()) {
	       
			url = it.next().getAttribute("href");
			
		    if (url == null || url.isEmpty()) {
		    	System.out.println(url + " URL is either not configured for anchor tag or it is empty");
		    	continue;
		    }
		    
	        if (!url.startsWith(homePage)) {
	        	System.out.println(url + " URL belongs to another domain, skipping it.");
	        	continue;
	        }
	        
	        try {
	        	conn = (HttpURLConnection)(new URL(url).openConnection());
	        	conn.setRequestMethod("HEAD");
	        	conn.connect();
	        	
	        	if(conn.getResponseCode() >= 400) {
	        		System.out.println(url + " is a broken link");
	        		broken = true;
	        	}
	        	else {
	        		System.out.println(url + " is a valid link");
	        		
        			if (!checkedLinks.contains(url)) {
        				linksToParse.add(url);
        				checkedLinks.add(url);
        			}
	        			
	        	}
	        	
	        	
	        } 
	        catch (MalformedURLException e) {
	        	e.printStackTrace();
	        } 
	        catch (IOException e) {
	        	e.printStackTrace();
	        }
	        
		}
	    
		for (String link : linksToParse) {
			
			try {
				if (!link.contains("logout")) {
					driver.get(link);
					parseLink(link);
				}
			} catch (TimeoutException e) {
				System.out.println("Page: " + link + " did not load within 15 seconds!");
				fail("One or more pages did not load");
			}
			
		}
        
        if (broken)
			fail("One or more links are broken");
		
	}
	
	@Test
	public void run() {
		
		System.out.println("//");
		System.out.println("// Testing Broken Links");
		System.out.println("//");

        driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);
        
		try {
			parseLink(homePage);
		} catch (TimeoutException e) {
			System.out.println("Page: " + homePage + " did not load within 15 seconds!");
			fail("One or more pages did not load");
		}
		
		
		joinThreads();
		
	}
	
}
