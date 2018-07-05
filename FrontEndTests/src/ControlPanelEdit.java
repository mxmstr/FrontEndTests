import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ControlPanelEdit extends FrontEndTest  {

	private void changeItemPrice(String price) {
		
		click(select.ControlPanel_Meal_Edit);
		clear(select.ControlPanel_Meal_Edit_Price);
		sendKeys(select.ControlPanel_Meal_Edit_Price, price);
		click(select.ControlPanel_Meal_Edit_Save);
		
	}
	
	@Test
	public void run() throws InterruptedException {
		
		System.out.println("//");
		System.out.println("// Testing Control Panel Edit Module");
		System.out.println("//");
		
		click(select.Header_Account);
		
    	Thread.sleep(1000);
    	
    	click(select.Account_ControlPanel);
    	
		
		changeItemPrice(System.getProperty("newPrice"));
        Thread.sleep(1000);
        
        WebElement table = getElement(select.ControlPanel_Table);
    	List<WebElement> tableRows = table.findElements(By.tagName("tr"));
    	
        Assert.assertTrue(
        		"Edit module did not update!",
        		tableRows.get(1).getText().contains(System.getProperty("newPrice")));
    	
        changeItemPrice(System.getProperty("defaultPrice"));
        Thread.sleep(1000);
		
	}
	
}
