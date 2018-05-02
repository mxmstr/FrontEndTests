import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Properties;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


public class RunNutreTests {
	
	
	public static void main(String[] args) {
		
		System.setProperty("webdriver.chrome.driver", "/Users/Lynch/Documents/chromedriver.exe");
		System.setProperty("homePage", "http://dev.gonutre.com");
		System.setProperty("email", "v.yesaulov@gmail.com");//"lynch.er18@gmail.com");
		System.setProperty("password", "123123");
		
		
		Properties configFile = new java.util.Properties();
		try {
			configFile.load(RunNutreTests.class.getClassLoader().getResourceAsStream("config.cfg"));
			
			System.setProperty("webdriver.chrome.driver", configFile.getProperty("driver"));
			System.setProperty("homePage", configFile.getProperty("homePage"));
			System.setProperty("email", configFile.getProperty("email"));
			System.setProperty("password", configFile.getProperty("password"));
			
			if (Integer.parseInt(configFile.getProperty("log")) != 0) {
				try {
					System.setOut(new PrintStream(new File("RunNutreTests.log")));
				} 
				catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
				
		}
		catch(Exception e) {}
		
		
		Result result = JUnitCore.runClasses(
				BrokenLinks.class,
				HeaderFooter.class,
				SearchBar.class,
				OrderAlacarte.class,
				OrderSubscription.class,
				ChangeAccountDetails.class,
				ControlPanelEdit.class,
				NewMeal.class,
				NewPromoCode.class,
				NewDeliveryZone.class
				);
 
		for (Failure failure : result.getFailures())
			System.out.println(failure.toString());
		
		if (result.wasSuccessful())
			System.out.println("Success");
		else
			System.out.println("Failure");
		
	}
	
}
