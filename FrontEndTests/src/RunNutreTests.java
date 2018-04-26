import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.openqa.selenium.WebDriver;


public class RunNutreTests {
	
	
	public static void main(String[] args) {
		
		/*System.setProperty("webdriver.chrome.driver", "/users/ericlynch/documents/chromedriver");
		System.setProperty("homePage", "http://dev.gonutre.com");
		System.setProperty("email", "lynch.er18@gmail.com");
		System.setProperty("password", "123123");*/
		
		
		Result result = JUnitCore.runClasses(
				BrokenLinks.class
				/*PageLoad.class,
				HeaderFooter.class,
				SearchBar.class,
				OrderAlacarte.class,
				ChangeAccountDetails.class,
				ControlPanelEdit.class,
				NewMeal.class,
				NewPromoCode.class,
				NewDeliveryZone.class*/
				);

		for (Failure failure : result.getFailures())
			System.out.println(failure.toString());
		
		if (result.wasSuccessful())
			System.out.println("Success");
		else
			System.out.println("Failure");
		
	}
	
}
