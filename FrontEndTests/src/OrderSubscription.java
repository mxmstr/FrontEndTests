import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;


public class OrderSubscription extends FrontEndTest {

	@Test
	public void run() throws InterruptedException {
		
		System.out.println("//");
		System.out.println("// Testing Add Subscription");
		System.out.println("//");
		
		removePaymentInfo();
		removeSubscription();
		
		driver.findElement(By.linkText("The Complete Plan")).click();
	    driver.findElement(By.linkText("Get Started")).click();
	    Thread.sleep(1000);
	    
	    addSubscription();
	    Thread.sleep(1000);
	    
	    driver.findElement(By.cssSelector(".pt-intent-orange")).click();
    	Thread.sleep(1000);
	    
    	driver.findElement(By.xpath("//label[contains(.,'Free Pick up')]")).click();
    	driver.findElement(By.xpath("//label[contains(.,'Free Pick up')]")).click();
    	driver.findElement(By.cssSelector("button.pt-button:nth-child(8)")).click();
	    Thread.sleep(1000);
	    
	    driver.findElement(By.cssSelector("button.cart__checkout-btn")).click();
	    Thread.sleep(1000);
	    
	    checkout("4242 4242 4242 4242", "02 / 20", "123", "02116");
	    Thread.sleep(10000);
		
	}

}
