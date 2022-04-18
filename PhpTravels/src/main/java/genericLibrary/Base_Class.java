package genericLibrary;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import genericLibrary.FrameworkConstants;
import io.github.bonigarcia.wdm.WebDriverManager;
import pomRepo.DashBoardPage;
import pomRepo.LoginPage;


/***
 * 
 * @author Rahul
 *
 */
public class Base_Class implements FrameworkConstants {
   
	public static WebDriver driver;
	public WebDriverWait explicitWait;
	public ExcelUtil eutil = new ExcelUtil();
	

	@Parameters("browser")
	@BeforeClass(alwaysRun = true)
	public void openTheBrowser(@Optional("chrome") String browserName) {

		if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			Reporter.log("Successfully Launched Chrome Browser", true);
		} else if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			Reporter.log("Successfully Launched Firefox Browser", true);
		} else {
			Reporter.log("Enter valid Browser name");
		}
		driver.manage().window().maximize();
		Reporter.log("Browser window is maximized successfully", true);
		driver.manage().timeouts().implicitlyWait(IMPLICIT_TIMEOUT, TimeUnit.SECONDS);
		explicitWait = new WebDriverWait(driver, EXPLICIT_TIMEOUT);
	}

	@BeforeMethod(alwaysRun = true)
	public void loginToApplication() {
		
         String url = eutil.readStringDataFromExcel("Sheet1", 1, 0);
		 driver.get(url);
		 String expectedLoginPageTitle = eutil.readStringDataFromExcel("Sheet1", 1, 1);
	     String actualLoginPageTitle = driver.getTitle();
	     Assert.assertEquals(actualLoginPageTitle, expectedLoginPageTitle, "LoginPage is not displayed");
	     Reporter.log("Loginpage is displayed",true);  
	     LoginPage loginpage = new LoginPage(driver);
	     loginpage.loginToApplication(driver);
	     String expectedDashBoardPageTitle = "Dashboard - PHPTRAVELS";
	     explicitWait.until(ExpectedConditions.titleContains(expectedDashBoardPageTitle));
	     String actualDashBoardPageTitle = driver.getTitle();
	     Assert.assertEquals(actualDashBoardPageTitle, expectedDashBoardPageTitle, "DashBoardPage is not displayed"); 
	     Reporter.log("DashBoard page is displayed", true);
	     try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     
	     
	}
	
	@AfterMethod(alwaysRun = true)
	public void logoutOfApplication() {
		DashBoardPage dashboard = new DashBoardPage(driver);
		dashboard.getAccount().click();
		dashboard.getLogout().click();
		
		
		
	}
		
	@AfterClass(alwaysRun = true)
	public void closeTheBrowser() {
		driver.quit();
	}
}
