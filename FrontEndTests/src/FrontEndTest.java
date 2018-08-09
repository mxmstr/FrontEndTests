import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
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
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;



public abstract class FrontEndTest {
	
	public static class SelectorString {
		
		public String by;
		public String selector;
		
	}
	
	public static class SelectorData {
		
		public SelectorString Body;
		public SelectorString Spinner;
		public SelectorString Chat_Frame;
		public SelectorString Chat_Dismiss;
		
		public SelectorString Header_Topbar;
		public SelectorString Header_Logo;
		public SelectorString Header_Logo_ControlPanel;
		public SelectorString Header_Alacarte;
		public SelectorString Header_Plan;
		public SelectorString Header_Plan_Complete;
		public SelectorString Header_Search;
		public SelectorString Header_Search_Input;
		public SelectorString Header_Search_Result1;
		public SelectorString Header_Login;
		public SelectorString Header_Account;
		public SelectorString Header_Cart;

		public SelectorString Alacarte_Subscribe_Dialogue;
		public SelectorString Alacarte_Dinner_Checkbox;
		public SelectorString Alacarte_Menu_Item;
		public SelectorString Alacarte_Item_Title;
		public SelectorString Alacarte_Item_Select;
		public SelectorString Alacarte_Item_Medium;
		public SelectorString Alacarte_Item_Large;
		public SelectorString Alacarte_Item_Add;
		public SelectorString Alacarte_Item1_Add;
		public SelectorString Alacarte_Item2_Dropdown;
		public SelectorString Alacarte_Item2_Add;
		
		public SelectorString Plan_Gluten_Checkbox;
		public SelectorString Plan_Gluten_Label;
		public SelectorString Plan_Create;
		public SelectorString Plan_Create_Confirm;

		public SelectorString Cart_Subtotal;
		public SelectorString Cart_Total;
		public SelectorString Cart_Item;
		public SelectorString Cart_Item_Title;
		public SelectorString Cart_Item_Size;
		public SelectorString Cart_Item_Quantity;
		public SelectorString Cart_Item_Remove;
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
		public SelectorString ControlPanel_Coupon_Title;
		public SelectorString ControlPanel_Coupon_Type;
		public SelectorString ControlPanel_Coupon_Value;
		public SelectorString ControlPanel_Coupon_Code;
		public SelectorString ControlPanel_Coupon_Percent;
		public SelectorString ControlPanel_Coupon_Reusable;
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
	
	public static class MealInfo {
		
		public String name;
		public int quantity;
		public String size;
		
	}
	
	public static class Order {
		
		public String name;
		public MealInfo[] meals;
		
	}
	
	public static class OrderTable {
		
		public Order[] orders;
		
	}
	
	public static class Code {
		
		public String title;
		public String type;
		public boolean percentage;
		public boolean reusable;
		public String value;
		public String code;
		
	}
	
	public static class CodeTable {
		
		public Code[] codes;
		
	}
	
	public String homePage;
	public String email;
	public String password;
	public WebDriver driver;
	public ArrayList<AsyncTester> threads;
	public SelectorTable selector_table;
	public SelectorData select;
	public OrderTable order_table;
	public String[] orders_to_test;
	public CodeTable code_table;
	public String[] codes_to_test;
	public static int timeout = 5;
	public static String elementJson = "PageElements.json";
	public static String ordersJson = "MealOrders.json";
	public static String codesJson = "PromoCodes.json";
	
	
	@Before
	public void setUp() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		selector_table = mapper.readValue(new File(elementJson), SelectorTable.class);
		select = selector_table.selectors;
		order_table = mapper.readValue(new File(ordersJson), OrderTable.class);
		code_table = mapper.readValue(new File(codesJson), CodeTable.class);
		
		
		Properties configFile = new java.util.Properties();
		try {
			configFile.load(new FileInputStream(new File("config.cfg")));
			
			for (String prop : configFile.stringPropertyNames())
				System.setProperty(prop, configFile.getProperty(prop));
		}
		catch(Exception e) {}
		
		orders_to_test = System.getProperty("orders").split(",");
		codes_to_test = System.getProperty("codes").split(",");
		
		
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
        driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        driver.get(homePage);
        
        waitForPageLoad();
        
        Thread.sleep(4000);
        
        removeElement(getElement(select.Chat_Frame));
        
        /*if (getElement(select.Chat_Frame).isDisplayed()) {
	        driver.switchTo().frame(getElement(select.Chat_Frame));
	        click(select.Chat_Dismiss);
	        driver.switchTo().defaultContent();
        }*/
        
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
	
	public void waitForPageLoad() {
		
		new WebDriverWait(driver, timeout).until(
				webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
		
	}
	
	public By invokeByMethod(SelectorString s) {
		
		try {
			java.lang.reflect.Method method = By.class.getMethod(s.by, String.class);
			return (By)method.invoke(null, s.selector);
		}
		catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public void removeElement(WebElement e) {
		
		((JavascriptExecutor)driver).executeScript("arguments[0].remove();", e);
		//((JavascriptExecutor)driver).executeScript("return document.getElementByClassName('" + className + "').remove();");
		
	}
	
	public WebElement getElement(SelectorString s) {
		
		By by = invokeByMethod(s);
		return by == null ? null : driver.findElement(by);
		
	}
	
	public WebElement getElement(WebElement inElement, SelectorString s) {
		
		By by = invokeByMethod(s);
		return by == null ? null : inElement.findElement(by);
		
	}
	
	public List<WebElement> getElements(SelectorString s) {
		
		By by = invokeByMethod(s);
		return by == null ? null : driver.findElements(by);
		
	}
	
	public List<WebElement> getElements(WebElement inElement, SelectorString s) {
		
		By by = invokeByMethod(s);
		return by == null ? null : inElement.findElements(by);
		
	}
	
	public boolean isElementPresent(SelectorString s) {
		
		try {
			By by = invokeByMethod(s);
			return by == null ? false : driver.findElement(by) != null;
		}
		catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	public boolean isElementPresent(WebElement inElement, SelectorString s) {
		
		By by = invokeByMethod(s);
		return by == null ? false : inElement.findElement(by) != null;
		
	}
	
	public boolean isEnabled(SelectorString s) {
		
		return getElement(s).isEnabled();
		
	}
	
	public boolean isEnabled(WebElement e) {
		
		return e.isEnabled();
		
	}
	
	public boolean isSelected(SelectorString s) {
		
		return getElement(s).isSelected();
		
	}
	
	public boolean textOnPage(String string) {
		
		String bodyText = driver.findElement(By.tagName("body")).getText();
    	return bodyText.contains(string);
		
	}
	
	public void click(SelectorString s) {
		
		WebElement e = getElement(s);
		long startTime = System.currentTimeMillis();
		
		while (true) {
			try {
				e.click();
				break;
			}
			catch (WebDriverException exception) {}
			
			long elapsedTime = (new Date()).getTime() - startTime;
			if (elapsedTime > 30000)
				throw new TimeoutException();
		}
		
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
	
	public void scrollTo(SelectorString s) {
		
		((JavascriptExecutor)driver).executeScript(
				"arguments[0].scrollIntoView();", 
				getElement(s)
				);
		
	}
	
	public void scrollTo(WebElement e) {
		
		((JavascriptExecutor)driver).executeScript(
				"arguments[0].scrollIntoView();", 
				e
				);
		
	}
	
	public void waitForPopup() throws InterruptedException {
		
		Thread.sleep(4000);
		sendEscapeKey();
		
	}
	
	public void sendEscapeKey() throws InterruptedException {
		
		Actions action = new Actions(driver);
    	action.sendKeys(Keys.ESCAPE).build().perform();
    	//Thread.sleep(1000);
		
	}
	
	public void signIn() throws InterruptedException {
		
    	clickJS(select.Header_Login);
    	
    	//Thread.sleep(1000);
    	
    	clear(select.Login_Email);
    	sendKeys(select.Login_Email, email);
    	clear(select.Login_Password);
    	sendKeys(select.Login_Password, password);
    	click(select.Login_Confirm);
    	
		//Thread.sleep(1000);
	    
	}
	
	public void openCart() throws InterruptedException {
		
		scrollUpJS();
		
		//Thread.sleep(1000);
		
		click(select.Header_Cart);
		
	}
	
	public void selectFromDropdown(WebElement e, String option) {
		
		new Select(e).selectByVisibleText(option);
		
	}
	
	public WebElement getFirstSelectedInDropdown(WebElement e) {
		
		return new Select(e).getFirstSelectedOption();
		
	}
	
	public void selectMealSize(WebElement e, String option) {
		
		if (option.equals("Large"))
			getElement(e, select.Alacarte_Item_Large).click();
		else
			getElement(e, select.Alacarte_Item_Medium).click();
		
	}
	
	public void add1ItemToCart() throws InterruptedException {
		
		//System.out.println(getElement(select.Alacarte_Subscribe_Dialogue).isDisplayed());
		
		click(select.Header_Alacarte);
		
		Thread.sleep(3000);
		
		if (getElement(select.Alacarte_Subscribe_Dialogue).isDisplayed())
			sendEscapeKey();
		
		click(select.Alacarte_Dinner_Checkbox);
		
	    //Thread.sleep(1000);
	    
	    //selectFromDropdown(System.getProperty("item1Name"), "1");
	    
	    //Thread.sleep(1000);
	    
	    getElement(select.Alacarte_Menu_Item).findElement(
	    		By.cssSelector("button[name='" + System.getProperty("item1Name") + "']")
	    		).click();
	    
	}

	public void add10ItemsToCart() throws InterruptedException {
		
		click(select.Header_Alacarte);

	    Thread.sleep(3000);
	    
		sendEscapeKey();
		
		//click(select.Alacarte_Dinner_Checkbox);
	    
	    //Thread.sleep(1000);
	    
		for (WebElement e : getElements(select.Alacarte_Menu_Item)) {
			if (getElement(e, select.Alacarte_Item_Title).getText().equals("Soup 1")) {
				selectFromDropdown(getElement(e, select.Alacarte_Item_Select), "10");
    			getElement(e, select.Alacarte_Item_Add).click();
    			break;
			}
		}
	    
	    //Thread.sleep(1000);
	    
	    //driver.findElement((By.cssSelector("button[name='" + System.getProperty("item1Name") + "']"))).click();
	    //driver.findElement((By.cssSelector("button[name='" + System.getProperty("item2Name") + "']"))).click();
	    
	}
	
	
	
	public void removeSubscription() throws InterruptedException  {
		
		click(select.Account_Subscription);
		
	    //Thread.sleep(1000);
	    
	    if (isElementPresent(select.Account_Subscription_Cancel)) {
		    click(select.Account_Subscription_Cancel);
		    //Thread.sleep(1000);
		    click(select.Account_Subscription_Cancel_Confirm);
		    //Thread.sleep(1000);
	    }
		
	}
	
	public void addSubscription() throws InterruptedException {
		
		if (isSelected(select.Plan_Gluten_Checkbox))
			click(select.Plan_Gluten_Label);
		
	    //Thread.sleep(1000);
	    
	    click(select.Plan_Create);
	    
	    //Thread.sleep(1000);
	    
	    if (textOnPage("You didn't customize your order, are you sure you want to add it to your cart?"))
    		click(select.Plan_Create_Confirm);
	    
	}
	
	public void redeemPromoCode(String code) throws InterruptedException {
			
		clear(select.Cart_Code_Input);
		sendKeys(select.Cart_Code_Input, code);
		clickJS(select.Cart_Code_Apply);
    	
	}
	
	public void removeTableElement(String name) throws InterruptedException {
		
		WebElement table = getElement(select.ControlPanel_Table);
    	List<WebElement> tableRows = table.findElements(By.tagName("tr"));
    	
    	for (WebElement e : tableRows) {
    		if (e.getText().contains(name)) {

    			((JavascriptExecutor)driver).executeScript("arguments[0].click();", e);
    			//Thread.sleep(1000);
    			clickJS(select.ControlPanel_Table_Delete);
    			clickJS(select.ControlPanel_Table_Delete_Confirm);
    			//Thread.sleep(1000);
    			break;
    			
    		}
    	}
		
	}
	
	public void removePaymentInfo() {
		
		click(select.Account_Payment);
	    
		waitForPageLoad();
		
	    if (isElementPresent(select.Account_Payment_Delete)) {
	    	click(select.Account_Payment_Delete);
	    	click(select.Account_Payment_Delete_Confirm);
	    }
	    
	}
	
	public void checkout(String cardnumber, String date, String cvc, String postal) throws InterruptedException {
		
	    driver.switchTo().frame(getElement(select.Cart_Checkout_Card_Frame));
	    
	    clear(select.Cart_Checkout_Card_Number);
	    sendKeys(select.Cart_Checkout_Card_Number, cardnumber);
	    
	    //Thread.sleep(1000);
	    
	    clear(select.Cart_Checkout_Card_Date);
	    sendKeys(select.Cart_Checkout_Card_Date, date);
	    
	    //Thread.sleep(1000);
	    
	    clear(select.Cart_Checkout_Card_CVC);
	    sendKeys(select.Cart_Checkout_Card_CVC, cvc);
	    
	    //Thread.sleep(1000);
	    
	    if (isElementPresent(select.Cart_Checkout_Card_Zip)) {
	    	clear(select.Cart_Checkout_Card_Zip);
		    sendKeys(select.Cart_Checkout_Card_Zip, postal);
	    }
	    
	    driver.switchTo().defaultContent();
	    
	    click(select.Cart_Checkout_Confirm);
	    
	}

}
