import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class PageLoad {
    
    private static WebDriver driver = null;

    public static void main(String[] args) {
    	
    	System.setProperty("webdriver.chrome.driver", "/users/ericlynch/documents/chromedriver");
    	
        String homePage = "http://dev.gonutre.com";
        
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(homePage);
        
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
        
        driver.quit();

    }
}

