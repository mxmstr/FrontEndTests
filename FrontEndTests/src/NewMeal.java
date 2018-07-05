import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;


public class NewMeal extends FrontEndTest {

	private void addNewMeal(String name, String price, String category, String zips) throws InterruptedException {
		
		click(select.ControlPanel_Meal_Add);
		
		clear(select.ControlPanel_Meal_Add_Name);
		sendKeys(select.ControlPanel_Meal_Add_Name, name);
		clear(select.ControlPanel_Meal_Add_Price);
		sendKeys(select.ControlPanel_Meal_Add_Price, price);
		
	    new Select(getElement(select.ControlPanel_Meal_Add_Category)).selectByVisibleText(category);
	    
	    clear(select.ControlPanel_Meal_Add_Ingredients);
	    sendKeys(select.ControlPanel_Meal_Add_Ingredients, zips);
	    
	    Thread.sleep(1000);
		scrollTo(select.ControlPanel_Meal_Add_Create);
	    
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
		addNewMeal(
				System.getProperty("mealName"), 
				System.getProperty("mealPrice"), 
				System.getProperty("mealCategory"), 
				System.getProperty("mealDescription")
				);
		
		Thread.sleep(1000);
		
		Assert.assertTrue("Meal wasn't added!", textOnPage("New Meal"));

    	removeTableElement("New Meal");
		
	}
	
}
