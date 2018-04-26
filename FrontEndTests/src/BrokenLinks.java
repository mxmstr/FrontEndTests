import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class BrokenLinks extends FrontEndTest {
	
	ArrayList<String> checkedLinks = new ArrayList<String>();;
	
	@Test
	public void run() {
		
		boolean broken = false;
		HttpURLConnection conn = null;
		String url = "";
		List<WebElement> links = driver.findElements(By.tagName("a"));
		Iterator<WebElement> it = links.iterator();
		
		System.out.println("//");
		System.out.println("// Testing Broken Links");
		System.out.println("//");
		
		while (it.hasNext()) {
	       
			url = it.next().getAttribute("href");
			
		    if(url == null || url.isEmpty()) {
		    	System.out.println(url + " URL is either not configured for anchor tag or it is empty");
		    	continue;
		    }
		    
	        if(!url.startsWith(homePage)){
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
	        		
	        		//if (url != homePage)
	        		//	checkedLinks.add(url);
	        		
	        	}
	        } 
	        catch (MalformedURLException e) {
	        	e.printStackTrace();
	        } 
	        catch (IOException e) {
	        	e.printStackTrace();
	        }
	       
		}
		
		
		/*System.setProperty("homePage", url);
		
		addThread( new Thread(new Runnable() {
            public void run() {
            	JUnitCore.runClasses(BrokenLinks.class);
            }
        }));
		
		joinThreads();*/
		
		if (broken)
			fail("One or more links are broken");
		
	}
	
}
