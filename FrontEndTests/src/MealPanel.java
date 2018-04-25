import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

public class MealPanel extends FrontEndTest {

	private void addNewMeal(String name) throws InterruptedException {
		
		driver.findElement(By.xpath("//div[@id='root']/div/div/div/div/div/div/div[2]/button")).click();
		driver.findElement(By.cssSelector("input.pt-fill:nth-child(2)")).clear();
		driver.findElement(By.cssSelector("input.pt-fill:nth-child(2)")).sendKeys(name);
	    driver.findElement(By.cssSelector("div.pt-input-group.pt-large > input.pt-input")).clear();
	    driver.findElement(By.cssSelector("div.pt-input-group.pt-large > input.pt-input")).sendKeys("20.00");
	    new Select(driver.findElement(By.cssSelector("select"))).selectByVisibleText("Dinner");
	    driver.findElement(By.xpath("//div[8]/label/textarea")).clear();
	    driver.findElement(By.xpath("//div[8]/label/textarea")).sendKeys("a, b, c");
	    driver.findElement(By.xpath("//div[12]/button")).click();
		
	}
	
	@Test
	public void run() throws InterruptedException {
		
		driver.findElement(By.linkText("Account")).click();
    	Thread.sleep(1000);
    	
    	driver.findElement(By.linkText("Control Panel")).click();
		
		
		addNewMeal("New Meal");
		
	    String bodyText = driver.findElement(By.tagName("body")).getText();
    	Assert.assertTrue("Meal wasn't added!", bodyText.contains("New Meal"));
		
    	removeTableElement("New Meal");
		
	}
	
}
