package PageObjects;
import Pippin.Keywords;
import Pippin.PlaceOrder;
import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.LogStatus;

public class Common extends Keywords{
	WebDriver driver;
	
	public Common(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	LoginPage objLoginPage = new LoginPage(driver);
	
	@FindBy(xpath="//*[@variant='title']/..//i[normalize-space('arrow_drop_down')]")
	WebElement menuDropDownIcon;
	
	@FindBy(xpath="//div[@class='mat-menu-content']//button[text()='Logout']")
	WebElement logoutButton;
	
	@FindBy(xpath="//a[text()='LOGIN']")
	WebElement loginText;
//	------------------------------------------------------------------------------------------------------------------------------------------

	public void clickonMenuDropDown() throws IOException {
		try {
		clickOnElement(menuDropDownIcon);
		if(isElementDisplayed(logoutButton)) {
			PlaceOrder.test.log(LogStatus.PASS, "Drop down menu opened");
		}
		else {
			PlaceOrder.test.log(LogStatus.FAIL,PlaceOrder.test.addScreenCapture(screenCapture(driver)), "Failed to open drop down menu");
		}
		}
		catch(Exception e) {
			PlaceOrder.test.log(LogStatus.FAIL,PlaceOrder.test.addScreenCapture(screenCapture(driver)), "Exception occured while clicking on Drop down menu icon.");
		}
	}
	
	public void clickLogout() throws IOException {
		try {
		clickOnElement(logoutButton);	
		}
		catch(Exception e) {
			PlaceOrder.test.log(LogStatus.FAIL,PlaceOrder.test.addScreenCapture(screenCapture(driver)), "Exception occured while clicking on Logout button.");
		}
	}
	
	public void logout() throws IOException {
		clickonMenuDropDown();
		clickLogout();
		waitUntillVisibilityOfElementLocated(loginText,10,driver);
		if(isElementDisplayed(loginText)) {
			PlaceOrder.test.log(LogStatus.PASS,"Successfully Logged out from application");
		}
		else {
			PlaceOrder.test.log(LogStatus.FAIL,PlaceOrder.test.addScreenCapture(screenCapture(driver)), "Failed to logout from application");
		}
	}
	
	
}
