package pomRepo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import genericLibrary.ExcelUtil;
import genericLibrary.JavaScriptUtil;
import junit.framework.Assert;

public class LoginPage {
	
	ExcelUtil eutil = new ExcelUtil();
	
	public LoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	
	@FindBy(name="email") private WebElement emailTextField;
	
	@FindBy(name="password") private WebElement passwordTextField;
	
	@FindBy(xpath="//span[text()='Login']") private WebElement loginButton;

	public WebElement getEmailTextField() {
		return emailTextField;
	}

	public WebElement getPasswordTextField() {
		return passwordTextField;
	}

	public WebElement getLoginButton() {
		return loginButton;
	}
	
	public void loginToApplication(WebDriver driver) {
		emailTextField.sendKeys(eutil.readStringDataFromExcel("Sheet1", 4, 0));
		passwordTextField.sendKeys(eutil.readStringDataFromExcel("Sheet1", 4, 1));
		WebDriverWait wait = new WebDriverWait(driver, 30);
		JavaScriptUtil js = new JavaScriptUtil(driver);
		js.jsScrollBy(640, 500);
		wait.until(ExpectedConditions.visibilityOfAllElements(loginButton ));
		loginButton.click();
	}

	
	
	
}
