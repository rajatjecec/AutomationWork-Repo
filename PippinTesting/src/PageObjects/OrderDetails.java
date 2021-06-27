package PageObjects;
import Pippin.Keywords;
import Pippin.PlaceOrder;
import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.LogStatus;

public class OrderDetails extends Keywords{
	WebDriver driver;
	
	public OrderDetails(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//h3[text()='Order Details']") 
	WebElement orderDetailsText;

	@FindBy(id="Order_ID")
	WebElement orderID;
	
	@FindBy(xpath="//input[@value='Message']")
	WebElement messageButton;
	
	@FindBy(xpath="//textarea[@id='msg-area']")
	WebElement messageInputField;
	
	@FindBy(xpath="//*[@role='progressbar' and contains(@class,'loader mat-spinner mat-progress-spinner')]")
	WebElement loadingIcon;
	
	@FindBy(id="msgSend")
	WebElement sendMessageButton;
	
	@FindBy(xpath="//h3[text()='Related Messages ']")
	WebElement relatedMessageText;
	
	@FindBy(xpath="(//h3[text()='Related Messages ']/..//li//div[@class='msgFormat'])[1]")
	WebElement yourMessage;
//	-----------------------------------------------------------------------------------------------------------------
	public WebElement orderDetailsTextElement() {
		return orderDetailsText;
	}
	
	public String getOrderID() throws InterruptedException, IOException {
		executeJavaScript("arguments[0].scrollIntoView();",orderID,driver);
		Thread.sleep(2000);
		String orderId = getValue(orderID);
		PlaceOrder.test.log(LogStatus.PASS,"Order ID is "+orderId);
		return orderId;
	}
	
	public void enterMessage(String message) throws IOException{
		clickOnElement(messageButton);
		waitUntillVisibilityOfElementLocated(messageInputField,20,driver);
		sendKeys(messageInputField, message);
		if(getValue(messageInputField).equals(message)) {
			PlaceOrder.test.log(LogStatus.PASS,PlaceOrder.test.addScreenCapture(screenCapture(driver)),"Message successfuly entered - "+message);
		}
		else {
			PlaceOrder.test.log(LogStatus.FAIL,PlaceOrder.test.addScreenCapture(screenCapture(driver)), "Failed to enter message");
		}
	}
	
	public void clickSendButton() throws IOException {
		clickOnElement(sendMessageButton);
		PlaceOrder.test.log(LogStatus.PASS,"Send button clicked successfully");
	}
	
	public void getYourMessageAndVerify(String message) throws IOException {
		if(loadingIcon.isDisplayed()) {
			clickOnElement(loadingIcon);
		}
		verifyIfMessageDisplayedUnderRelatedMessage(yourMessage, message, driver);	
	}
	
}
