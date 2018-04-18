import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class Header {
    
    private static WebDriver driver = null;

    public static void main(String[] args) {
    	
    	System.setProperty("webdriver.chrome.driver", "/users/ericlynch/documents/chromedriver");
    	
        String homePage = "http://dev.gonutre.com";
        
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(homePage);
        
        try
        {
            System.out.println(driver.findElements(By.className("topbar")).size());
        }
        catch (NoSuchElementException e)
        {
        	System.out.println("No such element");
        }
        
        driver.quit();

    }
}

