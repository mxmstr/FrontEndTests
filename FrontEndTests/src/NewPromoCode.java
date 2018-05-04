import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;


public class NewPromoCode extends FrontEndTest {
	
	private void addNewPromoCode(String name, String percent, String code) throws InterruptedException {
		
		driver.findElement(By.linkText("Coupons")).click();
	    driver.findElement(By.cssSelector("button.pt-button:nth-child(1)")).click();
		Thread.sleep(1000);
	    
		driver.findElement(By.cssSelector(
				"div.col-6:nth-child(1) > label:nth-child(1) > input:nth-child(2)")).clear();
		driver.findElement(By.cssSelector(
				"div.col-6:nth-child(1) > label:nth-child(1) > input:nth-child(2)")).sendKeys(name);
	    driver.findElement(By.cssSelector("div.pt-input-group.pt-large > input.pt-input")).clear();
	    driver.findElement(By.cssSelector("div.pt-input-group.pt-large > input.pt-input")).sendKeys(percent);
	    driver.findElement(By.cssSelector(
	    		"div.col-6:nth-child(4) > label:nth-child(1) > input:nth-child(2)")).clear();
	    driver.findElement(By.cssSelector(
	    		"div.col-6:nth-child(4) > label:nth-child(1) > input:nth-child(2)")).sendKeys(code);
	    
	    driver.findElement(By.xpath("//div[11]/button")).click();
	    
	}
	
	@Test
	public void run() throws InterruptedException {
		
		System.out.println("//");
		System.out.println("// Testing Add Promo Code");
		System.out.println("//");
		
		driver.findElement(By.linkText("Account")).click();
    	Thread.sleep(1000);
    	
    	driver.findElement(By.linkText("Control Panel")).click();
		driver.findElement(By.linkText("Coupons")).click();
		Thread.sleep(1000);
		
		addNewPromoCode("New Code", "10", "abc123");
		Thread.sleep(1000);
		
		clickJS(driver.findElement(By.cssSelector(".app-topbar__logo")));
		
		
		System.out.println("//");
		System.out.println("// Testing Valid Promo Code");
		System.out.println("//");
		
		openCart();
		Thread.sleep(1000);
    	
    	redeemPromoCode("abc123");
    	Thread.sleep(1000);
    	
    	String bodyText = driver.findElement(By.tagName("body")).getText();
    	Assert.assertTrue("New code was not accepted!", !bodyText.contains("Code is invalid or already used."));
    	
    	sendEscapeKey();
    	
    	
    	System.out.println("//");
		System.out.println("// Testing Remove Promo Code");
		System.out.println("//");
    	
    	driver.findElement(By.linkText("Account")).click();
    	Thread.sleep(1000);
    	
    	driver.findElement(By.linkText("Control Panel")).click();
		driver.findElement(By.linkText("Coupons")).click();
		Thread.sleep(1000);
    	
    	removeTableElement("New Code");
    	Thread.sleep(1000);
    	
    	clickJS(driver.findElement(By.cssSelector(".app-topbar__logo")));
    	
    	
    	add10ItemsToCart();
    	Thread.sleep(1000);
    	
    	openCart();
		Thread.sleep(1000);
    	
    	redeemPromoCode("abc123");
    	Thread.sleep(1000);
    	
    	bodyText = driver.findElement(By.tagName("body")).getText();
    	Assert.assertTrue("Old code was accepted!", bodyText.contains("Code is invalid or already used."));
    	
    	sendEscapeKey();
		
	}
	
}
