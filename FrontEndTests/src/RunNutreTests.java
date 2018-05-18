import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


public class RunNutreTests {
	
	private static int numTests = 9;
	
	
	public static void main(String[] args) throws IOException {
		
		int i = 0;
		boolean success = true;
		Result[] results = new Result[numTests];
		
		Class[] classes = {
				BrokenLinks.class,
				SearchBar.class,
				ChangeAccountDetails.class,
				ControlPanelEdit.class,
				OrderAlacarte.class,
				OrderSubscription.class,
				NewMeal.class,
				NewPromoCode.class,
				NewDeliveryZone.class
				};
		
		
		Properties configFile = new java.util.Properties();
		configFile.load(new FileInputStream(new File("config.cfg")));
		
		
		for (Class c : classes) {
			if (Integer.parseInt(configFile.getProperty(c.getName())) != 0)
				results[i] = JUnitCore.runClasses(c);
			i++;
		}
		
		
		for (Result r : results) {
			if (r != null) {
				for (Failure failure : r.getFailures())
					System.out.println(failure.toString());
				if (!r.wasSuccessful())
					success = false;
			}
		}
		
		
		if (success)
			System.out.println("Success");
		else
			System.out.println("Failure");
		
	}
	
}
