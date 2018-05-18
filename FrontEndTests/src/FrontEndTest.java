import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.Select;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;



public abstract class FrontEndTest {
	
	public String homePage;
	public String email;
	public String password;
	public WebDriver driver;
	public ArrayList<AsyncTester> threads;
	public SelectorTable table;
	public SelectorData select;
	public static String elementJson = "PageElements.json";
	
	
	public static class SelectorString {
		
		public String by;
		public String selector;
		
	}
	
	public static class SelectorData {
		
		public SelectorString Body;
		public SelectorString Spinner;
		
		public SelectorString Header_Topbar;
		public SelectorString Header_Logo;
		public SelectorString Header_Logo_ControlPanel;
		public SelectorString Header_Alacarte;
		public SelectorString Header_Plan;
		public SelectorString Header_Search;
		public SelectorString Header_Search_Input;
		public SelectorString Header_Search_Result1;
		public SelectorString Header_Login;
		public SelectorString Header_Account;
		public SelectorString Header_Cart;

		public SelectorString Alacarte_Dinner_Checkbox;
		public SelectorString Alacarte_Menu_Item;
		public SelectorString Alacarte_Item_Select;
		public SelectorString Alacarte_Item1_Add;
		public SelectorString Alacarte_Item2_Dropdown;
		public SelectorString Alacarte_Item2_Add;
		
		public SelectorString Plan_GoToMenu;
		public SelectorString Plan_Gluten_Checkbox;
		public SelectorString Plan_Gluten_Label;
		public SelectorString Plan_Create;
		public SelectorString Plan_Create_Confirm;

		public SelectorString Cart_Subtotal;
		public SelectorString Cart_Code_Input;
		public SelectorString Cart_Code_Apply;
		public SelectorString Cart_Shipping;
		public SelectorString Cart_Shipping_Address1;
		public SelectorString Cart_Shipping_Pickup;
		public SelectorString Cart_Shipping_Confirm;
		public SelectorString Cart_Checkout;
		public SelectorString Cart_Checkout_Card_Frame;
		public SelectorString Cart_Checkout_Card_Number;
		public SelectorString Cart_Checkout_Card_Date;
		public SelectorString Cart_Checkout_Card_CVC;
		public SelectorString Cart_Checkout_Card_Zip;
		public SelectorString Cart_Checkout_Close;
		public SelectorString Cart_Checkout_Confirm;
		
		public SelectorString Account_Detail_Name;
		public SelectorString Account_Detail_Edit;
		public SelectorString Account_Detail_Edit_FirstName;
		public SelectorString Account_Detail_Edit_Save;
		public SelectorString Account_Delivery;
		public SelectorString Account_Delivery_Edit;
		public SelectorString Account_Delivery_Edit_Delete;
		public SelectorString Account_Delivery_Add;
		public SelectorString Account_Delivery_Add_Street;
		public SelectorString Account_Delivery_Add_City;
		public SelectorString Account_Delivery_Add_Zip;
		public SelectorString Account_Delivery_Add_Number;
		public SelectorString Account_Delivery_Add_Default_Check;
		public SelectorString Account_Delivery_Add_Default_Label;
		public SelectorString Account_Delivery_Add_Default_Save;
		public SelectorString Account_Payment;
		public SelectorString Account_Payment_Delete;
		public SelectorString Account_Payment_Delete_Confirm;
		public SelectorString Account_Subscription;
		public SelectorString Account_Subscription_Cancel;
		public SelectorString Account_Subscription_Cancel_Confirm;
		public SelectorString Account_ControlPanel;
		public SelectorString Account_SignOut;
		
		public SelectorString Login_Email;
		public SelectorString Login_Password;
		public SelectorString Login_Confirm;
		
		public SelectorString ControlPanel_Table;
		public SelectorString ControlPanel_Table_Delete;
		public SelectorString ControlPanel_Table_Delete_Confirm;
		public SelectorString ControlPanel_Meal_Add;
		public SelectorString ControlPanel_Meal_Add_Name;
		public SelectorString ControlPanel_Meal_Add_Price;
		public SelectorString ControlPanel_Meal_Add_Category;
		public SelectorString ControlPanel_Meal_Add_Ingredients;
		public SelectorString ControlPanel_Meal_Add_Create;
		public SelectorString ControlPanel_Meal_Edit;
		public SelectorString ControlPanel_Meal_Edit_Price;
		public SelectorString ControlPanel_Meal_Edit_Save;
		public SelectorString ControlPanel_Meal_FirstItem;
		public SelectorString ControlPanel_Coupon;
		public SelectorString ControlPanel_Coupon_Add;
		public SelectorString ControlPanel_Coupon_Name;
		public SelectorString ControlPanel_Coupon_Percent;
		public SelectorString ControlPanel_Coupon_Code;
		public SelectorString ControlPanel_Coupon_Create;
		public SelectorString ControlPanel_Delivery;
		public SelectorString ControlPanel_Delivery_Add;
		public SelectorString ControlPanel_Delivery_Add_Title;
		public SelectorString ControlPanel_Delivery_Add_Price;
		public SelectorString ControlPanel_Delivery_Add_Zips;
		public SelectorString ControlPanel_Delivery_Add_Create;
		
	}
	
	public static class SelectorTable {
		
		public SelectorData selectors;
		
	}
	
	
	@Before
	public void setUp() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		table = mapper.readValue(new File(elementJson), SelectorTable.class);
		select = table.selectors;
		
		
		Properties configFile = new java.util.Properties();
		try {
			configFile.load(new FileInputStream(new File("config.cfg")));
			
			for (String prop : configFile.stringPropertyNames()) {
				System.setProperty(prop, configFile.getProperty(prop));
			}
		}
		catch(Exception e) {}
		
		
		if (Integer.parseInt(System.getProperty("log")) != 0)
			System.setOut(new PrintStream(new File("RunNutreTests.log")));
		
		
		homePage = System.getProperty("homePage");
		email = System.getProperty("email");
		password = System.getProperty("password");
		
		threads = new ArrayList<AsyncTester>();
		
		while (true) {
			
			try {
				switch(Integer.parseInt(System.getProperty("browser"))) {
					case 0:
						driver = new ChromeDriver();
						break;
					case 1:
						driver = new FirefoxDriver();
						break;
					case 2:
						driver = new SafariDriver();
						break;
				}
				break;
			}
			catch (WebDriverException e) {}
			
		}
        
        driver.manage().window().maximize();
        driver.manage().window().setSize(
        		new Dimension(
        				1440,
        				driver.manage().window().getSize().getHeight()));
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.get(homePage);
        
        signIn();
	
	}
	
	@After
	public void tearDown() {
		
		driver.quit();
		
	}
	
	public void addThread(Thread t) {
		
		AsyncTester test = new AsyncTester(t);
        test.start();
        threads.add(test);
		
	}
	
	public void joinThreads() {
		
		for (AsyncTester test : threads) {
			try {
				test.join();
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			catch (java.lang.AssertionError e) {
				System.out.println("Assertion error");
				fail();
			}
		}
		
	}
	
	public WebElement getElement(SelectorString s) {
		
		try {
			java.lang.reflect.Method method = By.class.getMethod(s.by, String.class);
			return driver.findElement((By)method.invoke(null, s.selector));
		}
		catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public WebElement getElement(WebElement inElement, SelectorString s) {
		
		try {
			java.lang.reflect.Method method = By.class.getMethod(s.by, String.class);
			return inElement.findElement((By)method.invoke(null, s.selector));
		}
		catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public List<WebElement> getElements(SelectorString s) {
		
		try {
			java.lang.reflect.Method method = By.class.getMethod(s.by, String.class);
			return driver.findElements((By)method.invoke(null, s.selector));
		}
		catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public boolean isElementPresent(SelectorString s) {
		
		try {
			java.lang.reflect.Method method = By.class.getMethod(s.by, String.class);
			driver.findElement((By)method.invoke(null, s.selector));
			return true;
	    } 
		catch (NoSuchElementException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			return false;
	    }
		
	}
	
	public boolean isEnabled(SelectorString s) {
		
		return getElement(s).isEnabled();
		
	}
	
	public boolean isSelected(SelectorString s) {
		
		return getElement(s).isSelected();
		
	}
	
	public boolean textOnPage(String string) {
		
		String bodyText = driver.findElement(By.tagName("body")).getText();
    	return bodyText.contains(string);
		
	}
	
	public void click(SelectorString s) {
		
		getElement(s).click();
		
	}
	
	public void clickJS(SelectorString s) {
		
		WebElement e = getElement(s);
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", e);
		
	}
	
	public void clear(SelectorString s) {
		
		getElement(s).clear();
		
	}
	
	public void sendKeys(SelectorString s, String keys) {
		
		getElement(s).sendKeys(keys);
		
	}
	
	public void selectByVisibleText(SelectorString s, String text) {
		
		new Select(getElement(s)).selectByVisibleText(text);
		
	}
	
	public void scrollUpJS() {
		
		WebElement header = driver.findElement(By.id("root"));
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", header);
		
	}
	
	public void sendEscapeKey() throws InterruptedException {
		
		Actions action = new Actions(driver);
    	action.sendKeys(Keys.ESCAPE).build().perform();
    	Thread.sleep(1000);
		
	}
	
	public void signIn() throws InterruptedException {
		
    	clickJS(select.Header_Login);
    	
    	Thread.sleep(1000);
    	
    	clear(select.Login_Email);
    	sendKeys(select.Login_Email, email);
    	clear(select.Login_Password);
    	sendKeys(select.Login_Password, password);
    	click(select.Login_Confirm);
    	
		Thread.sleep(1000);
	    
	}
	
	public void openCart() throws InterruptedException {
		
		scrollUpJS();
		
		Thread.sleep(1000);
		
		click(select.Header_Cart);
		
	}
	
	public void selectFromDropdown(String itemName, SelectorString itemElement, String option) {
		
		for (WebElement e : getElements(itemElement)) {
	    	if (e.getText().contains(itemName)) {
	    		new Select(getElement(e, select.Alacarte_Item_Select)).selectByVisibleText(option);
	    		break;
	    	}
	    }
		
	}
	
	public void add1ItemToCart() throws InterruptedException {
		
		click(select.Header_Alacarte);
		click(select.Alacarte_Dinner_Checkbox);
		
	    Thread.sleep(1000);
	    
	    selectFromDropdown(System.getProperty("item1Name"), select.Alacarte_Menu_Item, "1");
	    
	    Thread.sleep(1000);
	    
	    click(select.Alacarte_Item1_Add);
	    
	}

	public void add10ItemsToCart() throws InterruptedException {
		
		click(select.Header_Alacarte);
		click(select.Alacarte_Dinner_Checkbox);
	    
	    Thread.sleep(1000);
	    
	    selectFromDropdown(System.getProperty("item1Name"), select.Alacarte_Menu_Item, "5");
	    selectFromDropdown(System.getProperty("item2Name"), select.Alacarte_Menu_Item, "5");
	    //selectByVisibleText(select.Alacarte_Item1_Dropdown, "5");
	    //selectByVisibleText(select.Alacarte_Item2_Dropdown, "5");
	    
	    Thread.sleep(1000);
	    
	    click(select.Alacarte_Item1_Add);
	    click(select.Alacarte_Item2_Add);
	    
	}
	
	public void removeSubscription() throws InterruptedException  {
		
		click(select.Account_Subscription);
		
	    Thread.sleep(1000);
	    
	    if (isElementPresent(select.Account_Subscription_Cancel)) {
		    click(select.Account_Subscription_Cancel);
		    Thread.sleep(1000);
		    click(select.Account_Subscription_Cancel_Confirm);
	    }
		
	}
	
	public void addSubscription() throws InterruptedException {
		
		if (isSelected(select.Plan_Gluten_Checkbox))
			click(select.Plan_Gluten_Label);
		
	    Thread.sleep(1000);
	    
	    click(select.Plan_Create);
	    
	    Thread.sleep(1000);
	    
	    if (textOnPage("You didn't customize your order, are you sure you want to add it to your cart?"))
    		click(select.Plan_Create_Confirm);
	    
	}
	
	public void redeemPromoCode(String code) {
			
		clear(select.Cart_Code_Input);
		sendKeys(select.Cart_Code_Input, code);
		click(select.Cart_Code_Apply);
    	
	}
	
	public void removeTableElement(String name) throws InterruptedException {
		
		WebElement table = getElement(select.ControlPanel_Table);
    	List<WebElement> tableRows = table.findElements(By.tagName("tr"));
    	
    	for (WebElement e : tableRows) {
    		if (e.getText().contains(name)) {

    			((JavascriptExecutor)driver).executeScript("arguments[0].click();", e);
    			click(select.ControlPanel_Table_Delete);
    			click(select.ControlPanel_Table_Delete_Confirm);
    			Thread.sleep(1000);
    			break;
    			
    		}
    	}
		
	}
	
	public void removePaymentInfo() {
		
		click(select.Header_Account);
		click(select.Account_Payment);
	    
	    if (isElementPresent(select.Account_Payment_Delete)) {
	    	click(select.Account_Payment_Delete);
	    	click(select.Account_Payment_Delete_Confirm);
	    }
	    
	}
	
	public void checkout(String cardnumber, String date, String cvc, String postal) throws InterruptedException {
		
	    driver.switchTo().frame(getElement(select.Cart_Checkout_Card_Frame));
	    
	    clear(select.Cart_Checkout_Card_Number);
	    sendKeys(select.Cart_Checkout_Card_Number, cardnumber);
	    
	    Thread.sleep(1000);
	    
	    clear(select.Cart_Checkout_Card_Date);
	    sendKeys(select.Cart_Checkout_Card_Date, date);
	    
	    Thread.sleep(1000);
	    
	    clear(select.Cart_Checkout_Card_CVC);
	    sendKeys(select.Cart_Checkout_Card_CVC, cvc);
	    
	    Thread.sleep(1000);
	    
	    if (isElementPresent(select.Cart_Checkout_Card_Zip)) {
	    	clear(select.Cart_Checkout_Card_Zip);
		    sendKeys(select.Cart_Checkout_Card_Zip, postal);
	    }
	    
	    driver.switchTo().defaultContent();
	    
	    click(select.Cart_Checkout_Confirm);
	    
	}

}
