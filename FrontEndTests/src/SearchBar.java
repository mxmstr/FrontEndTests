import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;

public class SearchBar extends FrontEndTest {

	@Test
	public void run() {
		
		System.out.println("//");
		System.out.println("// Testing Search Bar Autofill");
		System.out.println("//");
		
		try {
		    
		    driver.findElement(By.cssSelector("input.pt-input")).clear();
		    driver.findElement(By.cssSelector("input.pt-input")).sendKeys("p");
		    driver.findElement(By.linkText("Pasta")).click();
		    
		    Assert.assertTrue(
		    		"Autofill didn't close!",
		    		!isElementPresent(By.cssSelector("div.topbar__search-block")));
		    
		}
		catch (InvalidElementStateException e) {
			System.out.println("Screen is too small to display search bar.");
		}
		
	}
	
}
