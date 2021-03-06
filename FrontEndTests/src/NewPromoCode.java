import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class NewPromoCode extends FrontEndTest {
	
	String generated_code;
	
	private void addNewPromoCode(Code code) throws InterruptedException {
		
		click(select.ControlPanel_Coupon);
		click(select.ControlPanel_Coupon_Add);
		
		//Thread.sleep(1000);
	    
		clear(select.ControlPanel_Coupon_Title);
		sendKeys(select.ControlPanel_Coupon_Title, code.title);
		selectFromDropdown(getElement(select.ControlPanel_Coupon_Type), code.type);
	    clear(select.ControlPanel_Coupon_Value);
	    sendKeys(select.ControlPanel_Coupon_Value, code.value);
	    //clear(select.ControlPanel_Coupon_Code);
	    //sendKeys(select.ControlPanel_Coupon_Code, code.code);
	    
		generated_code = getElement(select.ControlPanel_Coupon_Code).getAttribute("value");
	    
	    if (!code.percentage)
	    	click(select.ControlPanel_Coupon_Percent);
	    if (!code.reusable)
	    	click(select.ControlPanel_Coupon_Reusable);
	    
	    click(select.ControlPanel_Coupon_Create);
	    
	    //Thread.sleep(1000);
	    
	    if (textOnPage("The code has already been taken."))
	    	sendEscapeKey();
	    
	}
	
	@Test
	public void run() throws InterruptedException {
		
		System.out.println("//");
		System.out.println("// Testing Add Promo Code");
		System.out.println("//");
		
		click(select.Header_Account);
		
		removePaymentInfo();
		
    	//Thread.sleep(1000);
    	
    	click(select.Account_ControlPanel);
		click(select.ControlPanel_Coupon);
		
		//Thread.sleep(1000);
		
		
		for (String codeName : codes_to_test)
			for (Code code : code_table.codes)
				if (code.title.equals(codeName))
					addNewPromoCode(code);
		
		
		//Thread.sleep(1000);
		
		clickJS(select.Header_Logo_ControlPanel);
		
		
		System.out.println("//");
		System.out.println("// Testing Valid Promo Code");
		System.out.println("//");
		
		
		for (String codeName : codes_to_test) {
			for (Code code : code_table.codes) {
				if (code.title.equals(codeName)) {
		
					boolean discounted;
					
					System.out.println("//");
					System.out.println("// Testing " + code.title);
					System.out.println("//");
					
					add10ItemsToCart();
					scrollUpJS();
					openCart();
					
					//Thread.sleep(1000);
			    	
			    	discounted = redeemAndCheckDiscount(generated_code, code.type);
			    	
			    	Thread.sleep(2000);
			    	
			    	Assert.assertTrue("New code was not accepted!", !textOnPage("Code is invalid or already used."));
			    	Assert.assertTrue("Discount was not applied!", discounted);
			    	
			    	if (false) {//!code.reusable) {
			    		
			    		click(select.Cart_Shipping);
						
				    	//Thread.sleep(1000);
				    	
				    	click(select.Cart_Shipping_Pickup);
				    	click(select.Cart_Shipping_Pickup);
				    	//Thread.sleep(1000);
					    click(select.Cart_Shipping_Confirm);
					    
					    //Thread.sleep(1000);
					    
					    click(select.Cart_Checkout);
			    		
			    		checkout(
					    		System.getProperty("validCardNumber"), 
					    		System.getProperty("validCardDate"), 
					    		System.getProperty("validCardCVC"), 
					    		System.getProperty("validCardZip")
					    		);
					    
					    Thread.sleep(7000);
			    		
					    driver.navigate().refresh();
					    
					    add10ItemsToCart();
						scrollUpJS();
						openCart();
					    
						redeemPromoCode(code.code);
				    	Thread.sleep(3000);
				    	Assert.assertTrue("Used code was accepted!", textOnPage("Code is invalid or already used."));
				    	
			    	}
			    	
			    	for (WebElement e : getElements(select.Cart_Item))
				    	getElement(e, select.Cart_Item_Remove).click();
			    	
			    	sendEscapeKey();
			    	driver.navigate().refresh();
			    	
			    	Thread.sleep(4000);
				    
					sendEscapeKey();
					
				}
			}
		}
    	
		
    	System.out.println("//");
		System.out.println("// Testing Remove Promo Code");
		System.out.println("//");
    	
    	driver.findElement(By.linkText("Account")).click();
    	//Thread.sleep(1000);
    	
    	driver.findElement(By.linkText("Control Panel")).click();
		driver.findElement(By.linkText("Coupons")).click();
		
		//Thread.sleep(1000);
    	
		
		for (String codeName : codes_to_test)
			for (Code code : code_table.codes)
				if (code.title.equals(codeName)) 
					removeTableElement(code.title);
    	
		
    	//Thread.sleep(1000);
    	
    	click(select.Header_Logo_ControlPanel);
    	
    	
    	for (String codeName : codes_to_test) {
			for (Code code : code_table.codes) {
				if (code.title.equals(codeName)) {
    	
		    		System.out.println("//");
					System.out.println("// Testing " + code.title);
					System.out.println("//");
		    		
					openCart();
					
					//Thread.sleep(1000);
					
					redeemPromoCode(generated_code);
					Thread.sleep(2000);
					Assert.assertTrue("Old code was accepted!", textOnPage("Code is invalid or already used."));
					
					sendEscapeKey();
			    	driver.navigate().refresh();
			    	
			    	Thread.sleep(4000);
					
					sendEscapeKey();
					
				}
			}
    	}
			
	}
	
}
