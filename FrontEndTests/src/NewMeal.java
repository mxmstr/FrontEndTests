import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

public class NewMeal extends FrontEndTest {

	private void addNewMeal(String name) throws InterruptedException {
		
		click(select.ControlPanel_Meal_Add);
		
		clear(select.ControlPanel_Meal_Add_Name);
		sendKeys(select.ControlPanel_Meal_Add_Name, name);
		clear(select.ControlPanel_Meal_Add_Price);
		sendKeys(select.ControlPanel_Meal_Add_Price, "20.00");
		
	    new Select(getElement(select.ControlPanel_Meal_Add_Category)).selectByVisibleText("Dinner");
	    
	    clear(select.ControlPanel_Meal_Add_Ingredients);
	    sendKeys(select.ControlPanel_Meal_Add_Ingredients, "a, b, c");
	    
	    click(select.ControlPanel_Meal_Add_Create);
		
	}
	
	@Test
	public void run() throws InterruptedException {
		
		System.out.println("//");
		System.out.println("// Testing Add Meal");
		System.out.println("//");
		
		click(select.Header_Account);
		
    	Thread.sleep(1000);
    	
    	click(select.Account_ControlPanel);
		addNewMeal("New Meal");
		
		Assert.assertTrue("Meal wasn't added!", textOnPage("New Meal"));
		
    	removeTableElement("New Meal");
		
	}
	
}
