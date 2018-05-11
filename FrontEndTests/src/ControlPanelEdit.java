import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

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
    	
		
		changeItemPrice("10.00");
        Thread.sleep(1000);
        
        Assert.assertTrue(
        		"Edit module did not update!",
        		getElement(select.ControlPanel_Meal_FirstItem).getText().contains("10.00"));
    	
        changeItemPrice("9.00");
        Thread.sleep(1000);
		
	}
	
}
