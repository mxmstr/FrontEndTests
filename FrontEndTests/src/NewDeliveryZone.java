import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;


public class NewDeliveryZone extends FrontEndTest {

	private void addDeliveryZone(String name, String price, String zip) throws InterruptedException {
		
		click(select.ControlPanel_Delivery);
		click(select.ControlPanel_Delivery_Add);
		
		clear(select.ControlPanel_Delivery_Add_Title);
		sendKeys(select.ControlPanel_Delivery_Add_Title, name);
		clear(select.ControlPanel_Delivery_Add_Price);
		sendKeys(select.ControlPanel_Delivery_Add_Price, price);
		clear(select.ControlPanel_Delivery_Add_Zips);
		sendKeys(select.ControlPanel_Delivery_Add_Zips, zip);
		click(select.ControlPanel_Delivery_Add_Create);
		
	}
	
	private void validateDeliveryZone(String street, String city, String zip, String phone) throws InterruptedException {
		
		click(select.Header_Account);
	    click(select.Account_Delivery);
	    
	    changeDeliveryInfo(
	    		System.getProperty("street"), 
	    		System.getProperty("city"), 
	    		System.getProperty("state"), 
	    		System.getProperty("shippingZip"), 
	    		System.getProperty("phone")
	    		);
	    
	    openCart();

	    clickJS(select.Cart_Shipping);
	    click(select.Cart_Shipping_Address1);
	    click(select.Cart_Shipping_Confirm);
    	
	}
	
	@Test
	public void run() throws InterruptedException {
		
		System.out.println("//");
		System.out.println("// Testing Add Delivery Zone");
		System.out.println("//");
		
		click(select.Header_Account);
    	click(select.Account_ControlPanel);
		
		addDeliveryZone(
				System.getProperty("deliveryName"), 
				System.getProperty("deliveryPrice"), 
				System.getProperty("deliveryZips")
				);
		
		
		System.out.println("//");
		System.out.println("// Testing Delivery Zone In Checkout");
		System.out.println("//");
		
		clickJS(select.Header_Logo_ControlPanel);
		
    	validateDeliveryZone(
    			System.getProperty("shippingStreet"), 
				System.getProperty("shippingCity"), 
				System.getProperty("shippingZip"), 
				System.getProperty("shippingPhone")
				);
    	
    	Thread.sleep(1000);
    	
    	Assert.assertTrue(
    			"New zip code not accepted!", 
    			!textOnPage("Sorry, we're not servicing your area at this time."));
    	
    	sendEscapeKey();
    	
    	click(select.Account_ControlPanel);
    	
		
		System.out.println("//");
		System.out.println("// Testing Remove Delivery Zone");
		System.out.println("//");
		
		click(select.ControlPanel_Delivery);
		
		removeTableElement("New Delivery");
		
		
		System.out.println("//");
		System.out.println("// Testing Delivery Zone In Checkout");
		System.out.println("//");
		
		click(select.Header_Logo_ControlPanel);
		
		validateDeliveryZone(
    			System.getProperty("shippingStreet"), 
				System.getProperty("shippingCity"), 
				System.getProperty("shippingZip"), 
				System.getProperty("shippingPhone")
				);
		
		Thread.sleep(1000);
		
    	Assert.assertTrue("Old zip code accepted!", textOnPage("Sorry, we're not servicing your area at this time."));
		
	}
	
}
