import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;


public class OrderSubscription extends FrontEndTest {

	@Test
	public void run() throws InterruptedException {
		
		System.out.println("//");
		System.out.println("// Testing Add Subscription");
		System.out.println("//");
		
		click(select.Header_Account);
		
		removePaymentInfo();
		removeSubscription();
		
		click(select.Header_Plan);
	    //Thread.sleep(1000);
		click(select.Header_Plan_Complete);
	    
	    //Thread.sleep(1000);
	    
	    addSubscription();
	    
    	//Thread.sleep(1000);
	    
    	click(select.Cart_Shipping);
    	
    	//Thread.sleep(1000);
	    
    	click(select.Cart_Shipping_Pickup);
    	click(select.Cart_Shipping_Pickup);
	    click(select.Cart_Shipping_Confirm);
    	
	    //Thread.sleep(1000);

	    click(select.Cart_Checkout);
	    
	    //Thread.sleep(1000);
	    
	    checkout("4242 4242 4242 4242", "02 / 20", "123", "02116");
	    
	    Thread.sleep(7000);
		
	}

}
