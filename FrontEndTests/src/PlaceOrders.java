import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;


public class PlaceOrders extends FrontEndTest {
	
	public void addItemsToCart(MealInfo[] meals) throws InterruptedException {
		
		for (MealInfo info : meals) {
			for (WebElement e : getElements(select.Alacarte_Menu_Item)) {
		    	if (getElement(e, select.Alacarte_Item_Title).getText().equals(info.name)) {
	    			selectFromDropdown(getElement(e, select.Alacarte_Item_Select), Integer.toString(info.quantity));
	    			if (info.size != null)
	    				selectMealSize(e, info.size);
	    			getElement(e, select.Alacarte_Item_Add).click();
	    			break;
		    	}
			}
		}
		
	}
	
	public void CheckItemsMatchOrder(MealInfo[] meals) throws InterruptedException {
		
		Map<String, Integer> mealValues = new HashMap<String, Integer>();
		List<String> names = new ArrayList<String>();
		
		for (MealInfo info : meals)
			if (!names.contains(info.name))
				names.add(info.name);
		
		for (String name : names) {
			int mediumCount = 0;
			int largeCount = 0;
			
			for (MealInfo _info : meals) {
				if (_info.name.equals(name)) {
					if (_info.size == null || _info.size.equals("Large"))
						largeCount++;
					else if (_info.size.equals("Medium"))
						mediumCount++;
				}
			}
			
			int instances = 0;
			
			for (WebElement e : getElements(select.Cart_Item)) {
				scrollTo(e);
				
				String title = getElement(e, select.Cart_Item_Title).getText();
				String size = getElement(e, select.Cart_Item_Quantity).getText();
				String quantity = getFirstSelectedInDropdown(getElement(e, select.Cart_Item_Quantity)).getText();

				//System.out.println();
				//System.out.println(title + "  " + name);
				//System.out.println(title.equals(name));
				if (title.equals(name))
					instances++;
			}
			
			//System.out.println(instances);
			//System.out.println((mediumCount > 0 ? 1 : 0) + (largeCount > 0 ? 1 : 0));
			
			//Thread.sleep(1000);
			
		    Assert.assertTrue(
		    		"Orders not grouped properly! " + name, 
		    		instances == (mediumCount > 0 ? 1 : 0) + (largeCount > 0 ? 1 : 0)
		    		);
		}
		
	}
	
	@Test
	public void run() throws InterruptedException {
		
		System.out.println("//");
		System.out.println("// Testing Adding Items");
		System.out.println("//");
		
		click(select.Header_Account);
		removeSubscription();
		
		for (String orderName : orders_to_test) {
			for (Order order : order_table.orders) {
				if (order.name.contains(orderName)) {
					
					System.out.println("//");
					System.out.println("// Testing Order: " + orderName);
					System.out.println("//");
					
					click(select.Header_Account);
					removePaymentInfo();
					click(select.Account_Delivery);
				    changeDeliveryInfo(
				    		order.street,
				    		order.city,
				    		order.state,
				    		order.zip,
				    		order.phone
				    		);
					
				    Thread.sleep(1000);
				    
				    if (order.subscription == null)
				    	click(select.Header_Alacarte);
				    else {
				    	click(select.Header_Plan);
				    	click(getSelectorByString("Header_Plan_" + order.subscription));
				    }
					
					waitForPopup();
					
					if (order.meals != null)
						addItemsToCart(order.meals);
					
					if (order.subscription != null) {
						click(select.Plan_Create);
					    if (textOnPage("You didn't customize your order, are you sure you want to add it to your cart?"))
				    		click(select.Plan_Create_Confirm);
					}
					
					waitForPopup();
					openCart();
					
					//Thread.sleep(1000);
					
					
					System.out.println("//");
					System.out.println("// Checking Cart Items");
					System.out.println("//");
					
					if (order.meals != null)
						CheckItemsMatchOrder(order.meals);
					
					
					System.out.println("//");
					System.out.println("// Testing Promo Code");
					System.out.println("//");

					if (order.promo_code != null) {
					
						boolean discounted = redeemAndCheckDiscount(order.promo_code, order.promo_type);
				    	Thread.sleep(2000);
				    	Assert.assertTrue("New code was not accepted!", !textOnPage("Code is invalid or already used."));
				    	Assert.assertTrue("Discount was not applied!", discounted);
				    	
				    	//while (textOnPage("Code is invalid or already used."))
				    	//	;
				    	
					}
				    
			    	
			    	System.out.println("//");
					System.out.println("// Testing Checkout Tax");
					System.out.println("//");
					
			    	click(select.Cart_Shipping);
			    	click(select.Cart_Shipping_Address1);
				    click(select.Cart_Shipping_Confirm);
					
					String tax;
					Assert.assertTrue(
							"Order was not taxed!", 
							order.has_tax && 
							Double.parseDouble(
									getElement(select.Cart_Tax).getText().replaceAll("[$ ]","")
									) > 0
							);
			    	
			    	
			    	System.out.println("//");
					System.out.println("// Testing Checkout With Invalid Card");
					System.out.println("//");
					
				    click(select.Cart_Checkout);
				    
				    if (textOnPage("You must to have at least 10 meal items in the cart.")) {
			    		System.out.println("//");
						System.out.println("// Meal failed to check out");
						System.out.println("//");
						
						for (WebElement e : getElements(select.Cart_Item))
					    	getElement(e, select.Cart_Item_Remove).click();
					    
			    		sendEscapeKey();
			    		break;
			    	}
				    
				    
				    checkout(
				    		System.getProperty("invalidCardNumber"), 
				    		System.getProperty("invalidCardDate"), 
				    		System.getProperty("invalidCardCVC"), 
				    		System.getProperty("invalidCardZip")
				    		);
				    
				    Assert.assertTrue("Invalid card accepted!", isElementPresent(select.Cart_Checkout_Card_Frame));
					
				    
				    click(select.Cart_Checkout_Close);
				    click(select.Cart_Checkout);
				    
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
					
				    if (order.subscription == null) {
				    	click(select.Header_Account);
				    	removeSubscription();
				    }
				    
				    break;
				}
	    	}
	    }
		
	}
	
}
