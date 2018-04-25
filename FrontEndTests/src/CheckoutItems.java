import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;


public class CheckoutItems extends FrontEndTest {

	private void removePaymentInfo() {
		
		driver.findElement(By.linkText("Account")).click();
	    driver.findElement(By.linkText("Payment Information")).click();
	    
	    if (isElementPresent(By.xpath("//div[@id='root']/div/div/div[2]/div/div/div[2]/div[3]/div/span"))) {
	    	driver.findElement(By.xpath("//div[@id='root']/div/div/div[2]/div/div/div[2]/div[3]/div/span")).click();
	    	driver.findElement(By.xpath("//div[@id='root']/div/div/div[2]/div/div/div[2]/div[3]/div/div[3]/table/tbody/tr/td[5]/button")).click();
	    }
	    	
	}
	
	private void checkout(String cardnumber, String date, String cvc, String postal) throws InterruptedException {
	
		driver.findElement(By.cssSelector("button.cart__checkout-btn")).click();
	    Thread.sleep(1000);
	    
	    driver.switchTo().frame(driver.findElement(By.cssSelector(
	    		".__PrivateStripeElement > iframe:nth-child(1)")));
	    
	    driver.findElement(By.name("cardnumber")).clear();
	    driver.findElement(By.name("cardnumber")).sendKeys(cardnumber);
	    driver.findElement(By.name("exp-date")).clear();
	    driver.findElement(By.name("exp-date")).sendKeys(date);
	    driver.findElement(By.name("cvc")).clear();
	    driver.findElement(By.name("cvc")).sendKeys(cvc);
	    driver.findElement(By.name("postal")).clear();
	    driver.findElement(By.name("postal")).sendKeys(postal);
	    
	    driver.switchTo().defaultContent();
	    
	    driver.findElement(By.xpath("//form/div/div/button")).click();
		
	    Thread.sleep(5000);
	    
	}
	
	@Test
	public void run() throws InterruptedException {
		
		String bodyText;
		
		removePaymentInfo();
		
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
    	
    	
    	driver.findElement(By.cssSelector(".pt-intent-orange")).click();
    	Thread.sleep(1000);
	    
    	driver.findElement(By.xpath("//label[contains(.,'Free Pick up')]")).click();
    	driver.findElement(By.xpath("//label[contains(.,'Free Pick up')]")).click();
    	driver.findElement(By.cssSelector("button.pt-button:nth-child(8)")).click();
	    Thread.sleep(1000);
	    
    	
	    checkout("4242 4242 4242 4242", "‎02 / ‎20", "123", "02116");
		
	}
	
}
