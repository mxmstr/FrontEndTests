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
		
    	Assert.assertTrue("Checkout without shipping info!", !isEnabled(select.Cart_Checkout));
    	
    	Thread.sleep(1000);
    	
    	
    	System.out.println("//");
		System.out.println("// Testing Checkout With Less Than 10 items");
		System.out.println("//");
    	
    	click(select.Cart_Shipping);
    	
    	Thread.sleep(1000);
	    
    	click(select.Cart_Shipping_Pickup);
    	click(select.Cart_Shipping_Pickup);
	    click(select.Cart_Shipping_Confirm);
	    
	    Thread.sleep(1000);

	    click(select.Cart_Checkout);
	    
	    Thread.sleep(1000);
	    
		Assert.assertTrue(
    			"Checkout with less than 10 items!", 
    			textOnPage("You must to have at least 10 meal items in the cart."));
		
    	sendEscapeKey();
    	
    	Thread.sleep(1000);
    	
    	click(select.Header_Logo);
    	
    	add10ItemsToCart();
    	
    	Thread.sleep(1000);
    	
    	openCart();
    	
		Thread.sleep(1000);
    	
    	
    	System.out.println("//");
		System.out.println("// Testing Inactive Promo Code");
		System.out.println("//");
    	
    	redeemPromoCode(System.getProperty("inactiveCode"));
    	
    	Thread.sleep(1000);
    	
    	Assert.assertTrue("Inactive code accepted!", textOnPage("Code is invalid or already used."));
    	
    	
    	System.out.println("//");
		System.out.println("// Testing Invalid Promo Code");
		System.out.println("//");
		
    	redeemPromoCode(System.getProperty("invalidCode"));
    	
    	Thread.sleep(1000);
    	
    	Assert.assertTrue("Invalid code accepted!", textOnPage("Code is invalid or already used."));
		
    	
    	System.out.println("//");
		System.out.println("// Testing Checkout With Invalid Card");
		System.out.println("//");
    	
		click(select.Cart_Shipping);
    	
    	Thread.sleep(5000);
	    
    	click(select.Cart_Shipping_Pickup);
    	click(select.Cart_Shipping_Pickup);
	    click(select.Cart_Shipping_Confirm);
	    
	    Thread.sleep(1000);
	    
	    click(select.Cart_Checkout);
	    
	    Thread.sleep(1000);
	    
	    checkout(
	    		System.getProperty("invalidCardNumber"), 
	    		System.getProperty("invalidCardDate"), 
	    		System.getProperty("invalidCardCVC"), 
	    		System.getProperty("invalidCardZip")
	    		);
	    
	    Thread.sleep(1000);
	    
	    Assert.assertTrue("Invalid card accepted!", isElementPresent(select.Cart_Checkout_Card_Frame));
		
	    
	    click(select.Cart_Checkout_Close);
	    
	    Thread.sleep(1000);
	    
	    click(select.Cart_Checkout);
	    
	    Thread.sleep(1000);
	    
	    System.out.println("//");
		System.out.println("// Testing Checkout With Valid Card");
		System.out.println("//");
	    
		checkout(
	    		System.getProperty("validCardNumber"), 
	    		System.getProperty("validCardDate"), 
	    		System.getProperty("validCardCVC"), 
	    		System.getProperty("validCardZip")
	    		);
	    
	    Thread.sleep(10000);
	    
	    Assert.assertTrue("Valid card not accepted!", !isElementPresent(select.Cart_Checkout_Card_Frame));
		
	}
	
}
