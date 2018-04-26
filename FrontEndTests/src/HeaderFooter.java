import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;


public class HeaderFooter extends FrontEndTest {

	@Test
	public void run() {
		
		System.out.println("//");
		System.out.println("// Testing Header Exists");
		System.out.println("//");
		
		Assert.assertTrue(
	    		"Header does not exist!",
	    		isElementPresent(By.className("topbar__menu")));
		
	}
	
}
