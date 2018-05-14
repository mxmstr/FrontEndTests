import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;


public class NewPromoCode extends FrontEndTest {
	
	private void addNewPromoCode(String name, String percent, String code) throws InterruptedException {
		
		click(select.ControlPanel_Coupon);
		click(select.ControlPanel_Coupon_Add);
		
		Thread.sleep(1000);
	    
		clear(select.ControlPanel_Coupon_Name);
		sendKeys(select.ControlPanel_Coupon_Name, name);
	    clear(select.ControlPanel_Coupon_Percent);
	    sendKeys(select.ControlPanel_Coupon_Percent, percent);
	    clear(select.ControlPanel_Coupon_Code);
	    sendKeys(select.ControlPanel_Coupon_Code, code);
	    
	    click(select.ControlPanel_Coupon_Create);
	    
	}
	
	@Test
	public void run() throws InterruptedException {
		
		System.out.println("//");
		System.out.println("// Testing Add Promo Code");
		System.out.println("//");
		
		click(select.Header_Account);
		
    	Thread.sleep(1000);
    	
    	click(select.Account_ControlPanel);
		click(select.ControlPanel_Coupon);
		
		Thread.sleep(1000);
		
		addNewPromoCode("New Code", "10", "abc123");
		
		Thread.sleep(1000);
		
		click(select.Header_Logo_ControlPanel);
		
		
		System.out.println("//");
		System.out.println("// Testing Valid Promo Code");
		System.out.println("//");
		
		openCart();
		
		Thread.sleep(1000);
    	
    	redeemPromoCode("abc123");
    	
    	Thread.sleep(1000);
    	
    	Assert.assertTrue("New code was not accepted!", !textOnPage("Code is invalid or already used."));
    	
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
    	
    	clickJS(select.Header_Logo_ControlPanel);
    	
    	
    	add10ItemsToCart();
    	
    	Thread.sleep(1000);
    	
    	openCart();
    	
		Thread.sleep(1000);
    	
    	redeemPromoCode("abc123");
    	
    	Thread.sleep(1000);
    	
    	Assert.assertTrue("Old code was accepted!", textOnPage("Code is invalid or already used."));
    	
    	sendEscapeKey();
		
	}
	
}
