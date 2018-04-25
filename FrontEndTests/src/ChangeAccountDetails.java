import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class ChangeAccountDetails extends FrontEndTest {

	private void changeAccountName(String name) {
		
		driver.findElement(By.xpath("//div[@id='root']/div/div/div[2]/div/div/div[2]/h2/span")).click();
		
	    WebElement nameField = driver.findElement(By.cssSelector("div.content__input-ma:nth-child(1) > div:nth-child(2) > input:nth-child(1)"));
	    nameField.clear();
	    nameField.sendKeys(name);
	    
	    driver.findElement(By.cssSelector("button.nu-button-h.content__button")).click();
	    driver.findElement(By.xpath("//div[@id='root']/div/div/div[2]/div/div/div[2]/h2/span")).click();
		
	}
	
	@Test
	public void run() throws InterruptedException {
		
		driver.findElement(By.linkText("Account")).click();
    	Thread.sleep(1000);
		
	    changeAccountName("Joe");
	    Thread.sleep(1000);
	    
	    Assert.assertTrue(
	    		"Edit module did not update!",
	    		driver.findElement(By.cssSelector(
	    				"div.row:nth-child(3) > div:nth-child(2)")).getText().contains("Joe"));
	    
	    changeAccountName("John");
	    
	    driver.findElement(By.linkText("Sign Out")).click();
	    Thread.sleep(1000);
	    
	    Assert.assertTrue("Sign out access not downgraded!", !isElementPresent(By.linkText("Account")));
	    
	}
	
}
