package PageObjects;
import Pippin.Keywords;
import Pippin.PlaceOrder;
import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.relevantcodes.extentreports.LogStatus;

public class LoginPage extends Keywords {
	
	WebDriver driver;
	
	public LoginPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="Email_Address") 
	WebElement emailInputField;
	
	@FindBy(id="User_Password") 
	WebElement passwordInputField;
	
	@FindBy(id="loginBtnLogin") 
	WebElement loginButton;
	
//--------------------------------------------------------------------------------------------------------------------
	
	public void enterEmailAddress(String email) throws IOException {
		try {
		sendKeys(emailInputField, email);
		PlaceOrder.test.log(LogStatus.PASS,"Email address entered");
		}
		catch(Exception e) {
			PlaceOrder.test.log(LogStatus.FAIL,PlaceOrder.test.addScreenCapture(screenCapture(driver)), "Exception occured while entering Email address");
		}
	}
	
	public void enterPassword(String password) throws IOException {
		try {
		sendKeys(passwordInputField, password);
		PlaceOrder.test.log(LogStatus.PASS, "Password entered");
		}
		catch(Exception e) {
			PlaceOrder.test.log(LogStatus.FAIL,PlaceOrder.test.addScreenCapture(screenCapture(driver)),"Exception occured while entering Password");
		}
	}
	
	public void clickGoButton() throws IOException {
		try {
		passwordInputField.submit();
		PlaceOrder.test.log(LogStatus.PASS,"Clicked on Go button");
		}
		catch(Exception e) {
			PlaceOrder.test.log(LogStatus.FAIL,PlaceOrder.test.addScreenCapture(screenCapture(driver)), "Exception occured while cliking on GO button");
		}
	}
	
	public void login(String email, String password) throws IOException {
		enterEmailAddress(email);
		enterPassword(password);
		clickGoButton();
		PlaceOrder.test.log(LogStatus.PASS,PlaceOrder.test.addScreenCapture(screenCapture(driver)), "Logged in SUCCESSFULLY");

	}
	
}
