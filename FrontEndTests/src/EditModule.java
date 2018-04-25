import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

public class EditModule extends FrontEndTest  {

	private void changeItemPrice(String price) {
		
        driver.findElement(By.cssSelector("span.app__psedo-link")).click();
        driver.findElement(By.cssSelector("div.pt-input-group.pt-large > input.pt-input")).clear();
        driver.findElement(By.cssSelector("div.pt-input-group.pt-large > input.pt-input")).sendKeys(price);
        driver.findElement(By.xpath("//div[12]/button")).click();
		
	}
	
	@Test
	public void run() throws InterruptedException {
		
		driver.findElement(By.linkText("Account")).click();
    	Thread.sleep(1000);
    	
    	driver.findElement(By.linkText("Control Panel")).click();
    	
		
		changeItemPrice("10.00");
        Thread.sleep(1000);
        
        Assert.assertTrue(
        		"Edit module did not update!",
	    		driver.findElement(By.cssSelector(
		        		".pt-table > tbody:nth-child(2) > tr:nth-child(1) > td:nth-child(3)")).getText().contains("10.00"));
    	
        changeItemPrice("9.00");
        Thread.sleep(1000);
		
	}
	
}
