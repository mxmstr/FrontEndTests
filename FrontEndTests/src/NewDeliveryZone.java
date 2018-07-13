import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;


public class NewDeliveryZone extends FrontEndTest {

	private void addDeliveryZone(String name, String price, String zip) throws InterruptedException {
		
		click(select.ControlPanel_Delivery);
		click(select.ControlPanel_Delivery_Add);
		
		Thread.sleep(1000);
		
		clear(select.ControlPanel_Delivery_Add_Title);
		sendKeys(select.ControlPanel_Delivery_Add_Title, name);
		clear(select.ControlPanel_Delivery_Add_Price);
		sendKeys(select.ControlPanel_Delivery_Add_Price, price);
		clear(select.ControlPanel_Delivery_Add_Zips);
		sendKeys(select.ControlPanel_Delivery_Add_Zips, zip);
		click(select.ControlPanel_Delivery_Add_Create);
		
	}
	
	private void changeDeliveryInfo(String street, String city, String zip, String number) throws InterruptedException {
		
		if (isElementPresent(select.Account_Delivery_Edit)) {
			clickJS(select.Account_Delivery_Edit);
		    Thread.sleep(1000);
		    clickJS(select.Account_Delivery_Edit_Delete);
		    Thread.sleep(1000);
		}
		
		click(select.Account_Delivery_Add);
		Thread.sleep(3000);
		
		
		clear(select.Account_Delivery_Add_Street);
		sendKeys(select.Account_Delivery_Add_Street, street);
		
		clear(select.Account_Delivery_Add_City);
		sendKeys(select.Account_Delivery_Add_City, city);
		
		clear(select.Account_Delivery_Add_Zip);
		sendKeys(select.Account_Delivery_Add_Zip, zip);
		
		clear(select.Account_Delivery_Add_Number);
		sendKeys(select.Account_Delivery_Add_Number, number);
	    
	    if (!isSelected(select.Account_Delivery_Add_Default_Check))
	    	click(select.Account_Delivery_Add_Default_Label);
	    
	    click(select.Account_Delivery_Add_Default_Save);
	    
	}
	
	private void validateDeliveryZone(String street, String city, String zip, String phone) throws InterruptedException {
		
		click(select.Header_Account);
	    click(select.Account_Delivery);
	    
	    Thread.sleep(1000);
	    
	    changeDeliveryInfo(street, city, zip, phone);
	    
	    Thread.sleep(1000);
	    
	    openCart();
	    
	    Thread.sleep(1000);

	    clickJS(select.Cart_Shipping);
	    
	    Thread.sleep(1000);

	    click(select.Cart_Shipping_Address1);
	    click(select.Cart_Shipping_Confirm);
	    
	    Thread.sleep(1000);
    	
	}
	
	@Test
	public void run() throws InterruptedException {
		
		System.out.println("//");
		System.out.println("// Testing Add Delivery Zone");
		System.out.println("//");
		
		click(select.Header_Account);
		
    	Thread.sleep(1000);
    	
    	click(select.Account_ControlPanel);
		
		addDeliveryZone(
				System.getProperty("deliveryName"), 
				System.getProperty("deliveryPrice"), 
				System.getProperty("deliveryZips")
				);
		
		
		System.out.println("//");
		System.out.println("// Testing Delivery Zone In Checkout");
		System.out.println("//");
		
		Thread.sleep(1000);
		
		clickJS(select.Header_Logo_ControlPanel);
		
		Thread.sleep(1000);
		
    	validateDeliveryZone(
    			System.getProperty("shippingStreet"), 
				System.getProperty("shippingCity"), 
				System.getProperty("shippingZip"), 
				System.getProperty("shippingPhone")
				);
    	
    	Assert.assertTrue(
    			"New zip code not accepted!", 
    			!textOnPage("Sorry, we're not servicing your area at this time."));
    	
    	sendEscapeKey();
    	
    	click(select.Account_ControlPanel);
    	
		Thread.sleep(1000);
    	
		
		System.out.println("//");
		System.out.println("// Testing Remove Delivery Zone");
		System.out.println("//");
		
		click(select.ControlPanel_Delivery);
		
		Thread.sleep(1000);
		
		removeTableElement("New Delivery");
		
		
		System.out.println("//");
		System.out.println("// Testing Delivery Zone In Checkout");
		System.out.println("//");
		
		click(select.Header_Logo_ControlPanel);
		
		Thread.sleep(1000);
		
		validateDeliveryZone(
    			System.getProperty("shippingStreet"), 
				System.getProperty("shippingCity"), 
				System.getProperty("shippingZip"), 
				System.getProperty("shippingPhone")
				);
    	
    	Assert.assertTrue("Old zip code accepted!", textOnPage("Sorry, we're not servicing your area at this time."));
		
	}
	
}
