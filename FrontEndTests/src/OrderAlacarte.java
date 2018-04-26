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
		
    	add10ItemsToCart();
    	Thread.sleep(1000);
    	
    	driver.findElement(By.cssSelector(
				"button.pt-button.pt-minimal.pt-icon-shopping-cart.topbar__cart-btn.topbar__ma-top-8")).click();
    	Thread.sleep(1000);
    	
    	
    	System.out.println("//");
		System.out.println("// Testing Used Promo Code");
		System.out.println("//");
    	
    	redeemPromoCode("123123");
    	Thread.sleep(1000);
    	
    	bodyText = driver.findElement(By.tagName("body")).getText();
    	Assert.assertTrue("Used code accepted!", bodyText.contains("Code is invalid or already used."));
    	
    	
    	System.out.println("//");
		System.out.println("// Testing Invalid Promo Code");
		System.out.println("//");
		
    	redeemPromoCode("abc");
    	Thread.sleep(1000);
    	
    	bodyText = driver.findElement(By.tagName("body")).getText();
    	Assert.assertTrue("Invalid code accepted!", bodyText.contains("Code is invalid or already used."));
		
    	
    	System.out.println("//");
		System.out.println("// Testing Checkout Without Shipping");
		System.out.println("//");
		
    	Assert.assertTrue(
    			"Checkout without shipping info!",
    			!driver.findElement(By.cssSelector("button.cart__checkout-btn")).isEnabled());
    	
    	
    	System.out.println("//");
		System.out.println("// Testing Checkout With Valid Card");
		System.out.println("//");
    	
    	driver.findElement(By.cssSelector(".pt-intent-orange")).click();
    	Thread.sleep(1000);
	    
    	driver.findElement(By.xpath("//label[contains(.,'Free Pick up')]")).click();
    	driver.findElement(By.xpath("//label[contains(.,'Free Pick up')]")).click();
    	driver.findElement(By.cssSelector("button.pt-button:nth-child(8)")).click();
	    Thread.sleep(1000);
	    
    	
	    checkout("4242 4242 4242 4242", "‎02 / ‎20", "123", "02116");
		
	}
	
}
