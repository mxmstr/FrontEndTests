import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;


public class NewDeliveryZone extends FrontEndTest {

	private void addDeliveryZone(String name, String price, String zip) throws InterruptedException {
		
		driver.findElement(By.linkText("Delivery Zone")).click();
		driver.findElement(By.xpath("//div[@id='root']/div/div/div/div/div/div/div/button")).click();
		Thread.sleep(1000);
		
		driver.findElement(By.cssSelector("input.pt-fill")).clear();
		driver.findElement(By.cssSelector("input.pt-fill")).sendKeys(name);
		driver.findElement(By.cssSelector("div.pt-input-group.pt-large > input.pt-input")).clear();
		driver.findElement(By.cssSelector("div.pt-input-group.pt-large > input.pt-input")).sendKeys(price);
		driver.findElement(By.cssSelector("textarea.pt-input.pt-fill")).clear();
		driver.findElement(By.cssSelector("textarea.pt-input.pt-fill")).sendKeys(zip);
		driver.findElement(By.cssSelector("button.pt-large")).click();
		
	}
	
	private void changeDeliveryInfo(String street, String city, String zip, String number) throws InterruptedException {
		
		if (isElementPresent(By.cssSelector(".account__edit"))) {
			clickJS(driver.findElement(By.cssSelector(".account__edit")));
		    Thread.sleep(1000);
		    driver.findElement(By.cssSelector("a.pt-button:nth-child(1)")).click();
		    Thread.sleep(1000);
		}
		else {
			driver.findElement(By.cssSelector("a.pt-button")).click();
			Thread.sleep(1000);
		}
			 
	
		driver.findElement(By.cssSelector(
	    		"div.account__box:nth-child(4) > div:nth-child(1) > div:nth-child(2) > label:nth-child(1) > input:nth-child(1)")).clear();
	    driver.findElement(By.cssSelector(
	    		"div.account__box:nth-child(4) > div:nth-child(1) > div:nth-child(2) > label:nth-child(1) > input:nth-child(1)")).sendKeys(street);
	    driver.findElement(By.cssSelector(
	    		"div.content__input-ma:nth-child(2) > div:nth-child(2) > label:nth-child(1) > input:nth-child(1)")).clear();
	    driver.findElement(By.cssSelector(
	    		"div.content__input-ma:nth-child(2) > div:nth-child(2) > label:nth-child(1) > input:nth-child(1)")).sendKeys(city);
	    driver.findElement(By.cssSelector(
	    		"label.pt-label:nth-child(3) > input:nth-child(1)")).clear();
	    driver.findElement(By.cssSelector(
	    		"label.pt-label:nth-child(3) > input:nth-child(1)")).sendKeys(zip);
	    driver.findElement(By.cssSelector(
	    		"div.content__input-value > input.pt-input.pt-large")).clear();
	    driver.findElement(By.cssSelector(
	    		"div.content__input-value > input.pt-input.pt-large")).sendKeys(number);
	    
	    if (!driver.findElement(By.cssSelector(".pt-control > input:nth-child(1)")).isSelected())
	    	driver.findElement(By.xpath("//label[contains(.,'Set as default')]")).click();
	    
	    driver.findElement(By.cssSelector("button.nu-button-h.content__button")).click();
	    
	}
	
	private void validateDeliveryZone() throws InterruptedException {
		
		driver.findElement(By.linkText("Account")).click();
	    driver.findElement(By.linkText("Delivery Information")).click();
	    Thread.sleep(1000);
	    
	    
	    changeDeliveryInfo("Street", "City", "12345", "(555) 555-555");
	    
	    
	    driver.findElement(By.cssSelector(".pt-button")).click();
	    driver.findElement(By.cssSelector(".pt-intent-orange")).click();
	    Thread.sleep(1000);
	    
	    driver.findElement(By.cssSelector("div.row:nth-child(5) > div:nth-child(2) > label:nth-child(1)")).click();
	    driver.findElement(By.cssSelector("button.pt-button:nth-child(8)")).click();
	    Thread.sleep(1000);
	    
	    String bodyText = driver.findElement(By.tagName("body")).getText();
    	Assert.assertTrue("New zip code not accepted!", !bodyText.contains("Sorry, we're not servicing your area at this time."));
    	
    	
    	sendEscapeKey();
    	
	}
	
	@Test
	public void run() throws InterruptedException {
		
		System.out.println("//");
		System.out.println("// Testing Add Delivery Zone");
		System.out.println("//");
		
		driver.findElement(By.linkText("Account")).click();
    	Thread.sleep(1000);
    	
    	driver.findElement(By.linkText("Control Panel")).click();
		
		
		addDeliveryZone("New Delivery", "50", "12345");
		
		
		System.out.println("//");
		System.out.println("// Testing Delivery Zone In Checkout");
		System.out.println("//");
		
		clickJS(driver.findElement(By.cssSelector(".app-topbar__logo")));
		Thread.sleep(1000);
		
    	validateDeliveryZone();
    	
    	driver.findElement(By.linkText("Control Panel")).click();
		Thread.sleep(1000);
    	
		
		System.out.println("//");
		System.out.println("// Testing Remove Delivery Zone");
		System.out.println("//");
		
		driver.findElement(By.linkText("Delivery Zone")).click();
		Thread.sleep(1000);
		removeTableElement("New Delivery");
		
	}
	
}
