import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class ChangeAccountDetails extends FrontEndTest {

	private void changeAccountName(String name) throws InterruptedException {
		
		click(select.Account_Detail_Edit);
		
		Thread.sleep(1000);
		
		clear(select.Account_Detail_Edit_FirstName);
		sendKeys(select.Account_Detail_Edit_FirstName, name);
		
		click(select.Account_Detail_Edit_Save);
		click(select.Account_Detail_Edit);
	    
	}
	
	@Test
	public void run() throws InterruptedException {
		
		System.out.println("//");
		System.out.println("// Testing Account Edit Module");
		System.out.println("//");
		
		click(select.Header_Account);
		
    	Thread.sleep(1000);
		
	    changeAccountName(System.getProperty("newName"));
	    Thread.sleep(1000);
	    
	    Assert.assertTrue(
	    		"Edit module did not update!",
	    		getElement(select.Account_Detail_Name).getText().contains(System.getProperty("newName")));
	    
	    changeAccountName(System.getProperty("defaultName"));
	    
	    
	    System.out.println("//");
		System.out.println("// Testing Sign Out");
		System.out.println("//");
	    
	    click(select.Account_SignOut);
	    Thread.sleep(1000);
	    
	    Assert.assertTrue("Sign out access not downgraded!", !isElementPresent(select.Header_Account));
	    
	}
	
}
