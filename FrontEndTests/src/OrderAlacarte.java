import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;


public class OrderAlacarte extends FrontEndTest {
	
	public void addItemsToCart(MealInfo[] meals) throws InterruptedException {
		
		for (MealInfo info : meals) {
			for (WebElement e : getElements(select.Alacarte_Menu_Item)) {
		    	if (getElement(e, select.Alacarte_Item_Title).getText().equals(info.name)) {
	    			selectFromDropdown(e, Integer.toString(info.quantity));
	    			if (info.size != null)
	    				selectMealSize(e, info.size);
	    			getElement(e, select.Alacarte_Item_Add).click();
	    			break;
		    	}
			}
		}
		
	}
	
	@Test
	public void run() throws InterruptedException {
		
		String bodyText;
		
		System.out.println("//");
		System.out.println("// Testing Adding Items");
		System.out.println("//");
		
		for (String orderName : orders_to_test) {
			for (Order order : order_table.orders) {
				if (order.name.contains(orderName)) {
					
					System.out.println("//");
					System.out.println("// Testing Order: " + orderName);
					System.out.println("//");
					
					click(select.Header_Account);
					
					removePaymentInfo();
					
					click(select.Header_Alacarte);
					
					Thread.sleep(4000);
				    
					sendEscapeKey();
					
				    Thread.sleep(1000);
					
				    addItemsToCart(order.meals);
					
					Thread.sleep(1000);
					
					
					System.out.println("//");
					System.out.println("// Testing Inactive Promo Code");
					System.out.println("//");

					scrollUpJS();
					openCart();
			    	
					Thread.sleep(1000);
					
			    	redeemPromoCode(System.getProperty("inactiveCode"));
			    	
			    	Thread.sleep(1000);
			    	
			    	Assert.assertTrue("Inactive code accepted!", textOnPage("Code is invalid or already used."));
			    	
			    	
			    	System.out.println("//");
					System.out.println("// Testing Invalid Promo Code");
					System.out.println("//");
					
			    	redeemPromoCode(System.getProperty("invalidCode"));
			    	
			    	Thread.sleep(1000);
			    	
			    	Assert.assertTrue("Invalid code accepted!", textOnPage("Code is invalid or already used."));
					
			    	sendEscapeKey();
				    Thread.sleep(1000);
				    
			    	
			    	System.out.println("//");
					System.out.println("// Testing Checkout With Invalid Card");
					System.out.println("//");
			    	
					scrollUpJS();
					openCart();
					
			    	Thread.sleep(1000);

			    	click(select.Cart_Shipping);
					
			    	Thread.sleep(1000);
			    	
			    	click(select.Cart_Shipping_Pickup);
			    	click(select.Cart_Shipping_Pickup);
				    click(select.Cart_Shipping_Confirm);
				    
				    Thread.sleep(1000);
				    
				    click(select.Cart_Checkout);
				    
				    Thread.sleep(1000);
				    
				    
				    if (textOnPage("You must to have at least 10 meal items in the cart.")) {
			    		System.out.println("//");
						System.out.println("// Meal failed to check out");
						System.out.println("//");
						
						for (WebElement e : getElements(select.Cart_Item))
					    	getElement(e, select.Cart_Item_Remove).click();
					    
			    		sendEscapeKey();
					    Thread.sleep(1000);
					    
			    		break;
			    	}
				    
				    
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
				    
				    Thread.sleep(7000);
				    
				    Assert.assertTrue("Valid card not accepted!", !isElementPresent(select.Cart_Checkout_Card_Frame));
					
				    
				    break;
				}
	    	}
	    }
		
		
		
    	
    	/*System.out.println("//");
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
		
		*/
	}
	
}
