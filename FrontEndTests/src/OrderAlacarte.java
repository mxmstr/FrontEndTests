import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;


public class OrderAlacarte extends FrontEndTest {

	@Test
	public void run() throws InterruptedException {
		
		String bodyText;
		
		System.out.println("//");
		System.out.println("// Testing Adding Items");
		System.out.println("//");
		
		removePaymentInfo();
		
		add1ItemToCart();
		Thread.sleep(1000);
		
		openCart();
		Thread.sleep(1000);
		
    	
    	System.out.println("//");
		System.out.println("// Testing Checkout Without Shipping");
		System.out.println("//");
		
    	Assert.assertTrue(
    			"Checkout without shipping info!",
    			!driver.findElement(By.cssSelector("button.cart__checkout-btn")).isEnabled());
    	
    	
    	System.out.println("//");
		System.out.println("// Testing Checkout With Less Than 10 items");
		System.out.println("//");
    	
    	driver.findElement(By.cssSelector(".pt-intent-orange")).click();
    	Thread.sleep(1000);
	    
    	driver.findElement(By.xpath("//label[contains(.,'Free Pick up')]")).click();
    	driver.findElement(By.xpath("//label[contains(.,'Free Pick up')]")).click();
    	driver.findElement(By.cssSelector("button.pt-button:nth-child(8)")).click();
	    Thread.sleep(1000);

	    driver.findElement(By.cssSelector("button.cart__checkout-btn")).click();
	    Thread.sleep(1000);
	    
		bodyText = driver.findElement(By.tagName("body")).getText();
    	Assert.assertTrue(
    			"Checkout with less than 10 items!", 
    			bodyText.contains("You must to have at least 10 meal items in the cart."));
		
    	sendEscapeKey();
    	Thread.sleep(1000);
    	
    	driver.findElement(By.cssSelector(".topbar__logo")).click();
    	
    	add10ItemsToCart();
    	Thread.sleep(1000);
    	
    	openCart();
		Thread.sleep(1000);
    	
    	
    	System.out.println("//");
		System.out.println("// Testing Inactive Promo Code");
		System.out.println("//");
    	
    	redeemPromoCode("789");
    	Thread.sleep(1000);
    	
    	bodyText = driver.findElement(By.tagName("body")).getText();
    	Assert.assertTrue("Inactive code accepted!", bodyText.contains("Code is invalid or already used."));
    	
    	
    	System.out.println("//");
		System.out.println("// Testing Invalid Promo Code");
		System.out.println("//");
		
    	redeemPromoCode("abc");
    	Thread.sleep(1000);
    	
    	bodyText = driver.findElement(By.tagName("body")).getText();
    	Assert.assertTrue("Invalid code accepted!", bodyText.contains("Code is invalid or already used."));
		
    	
    	System.out.println("//");
		System.out.println("// Testing Checkout With Invalid Card");
		System.out.println("//");
    	
    	driver.findElement(By.cssSelector(".pt-intent-orange")).click();
    	Thread.sleep(1000);
	    
    	driver.findElement(By.xpath("//label[contains(.,'Free Pick up')]")).click();
    	driver.findElement(By.xpath("//label[contains(.,'Free Pick up')]")).click();
    	driver.findElement(By.cssSelector("button.pt-button:nth-child(8)")).click();
	    Thread.sleep(1000);
	    
	    driver.findElement(By.cssSelector("button.cart__checkout-btn")).click();
	    Thread.sleep(1000);
	    
	    checkout("9999 9999 9999 9999", "02 / 20", "123", "02116");
	    Thread.sleep(1000);
	    Assert.assertTrue(
	    		"Invalid card accepted!", 
	    		isElementPresent(By.cssSelector(".__PrivateStripeElement > iframe:nth-child(1)")));
		
	    
	    //sendEscapeKey();
	    driver.findElement(By.cssSelector(".pt-dialog-close-button")).click();
	    Thread.sleep(1000);
	    
	    driver.findElement(By.cssSelector("button.cart__checkout-btn")).click();
	    Thread.sleep(1000);
	    
	    System.out.println("//");
		System.out.println("// Testing Checkout With Valid Card");
		System.out.println("//");
	    
	    checkout("4242 4242 4242 4242", "02 / 20", "123", "02116");
	    Thread.sleep(10000);
	    Assert.assertTrue(
	    		"Valid card not accepted!", 
	    		!isElementPresent(By.cssSelector(".__PrivateStripeElement > iframe:nth-child(1)")));
		
	}
	
}
