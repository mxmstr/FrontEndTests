import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;


public class PageLoad extends FrontEndTest {
	
	@Test
	public void run() {
		
		System.out.println("//");
		System.out.println("// Testing Page Load");
		System.out.println("//");
		
		Long navigationStart = (Long)((JavascriptExecutor)driver).executeScript(
        		"return window.performance.timing.navigationStart");
        Long responseStart = (Long)((JavascriptExecutor)driver).executeScript(
        		"return window.performance.timing.responseStart");
        Long domComplete = (Long)((JavascriptExecutor)driver).executeScript(
        		"return window.performance.timing.domComplete");

		Long backendPerformance = responseStart - navigationStart;
		Long frontendPerformance = domComplete - responseStart;

		System.out.println("Back End: " + backendPerformance);
		System.out.println("Front End: " + frontendPerformance);
		
	}
	
}

