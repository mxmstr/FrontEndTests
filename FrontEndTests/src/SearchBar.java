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
		    
			clear(select.Header_Search_Input);
			sendKeys(select.Header_Search_Input, "p");
		    click(select.Header_Search_Result1);
		    
		    Assert.assertTrue(
		    		"Autofill didn't close!",
		    		!isElementPresent(select.Header_Search));
		    
		}
		catch (InvalidElementStateException e) {
			System.out.println("Screen is too small to display search bar.");
		}
		
	}
	
}
