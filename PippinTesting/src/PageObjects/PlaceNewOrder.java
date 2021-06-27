package PageObjects;
import Pippin.Keywords;
import Pippin.PlaceOrder;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.relevantcodes.extentreports.LogStatus;

public class PlaceNewOrder extends Keywords{
	WebDriver driver;
	
	public PlaceNewOrder(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//div/h5[normalize-space('Place New Order')]") 
	WebElement placeNewOrderText;
	
	@FindBy(xpath="//label[normalize-space('Common Products')]/parent::div[@class='ng-star-inserted']//mat-radio-button//span[2]")
	List<WebElement> allProducts;

	@FindBy(xpath="//input[@placeholder='Owner/Seller']")
	WebElement ownerSellerInputField;
	
	@FindBy(xpath="//input[@placeholder='Property Search']")
	WebElement propertySearchInputField;
	
	@FindBy(xpath="//div[contains(@class,'pac-container')]//div[@class='pac-item']")
	List<WebElement> propertyAutoSuggestions;
	
	@FindBy(xpath="//input[@placeholder='Client Reference *']")
	WebElement clientReferenceInputField;
	
	@FindBy(xpath="//span[text()='Auto Complete']")
	WebElement autoCompleteCheckbox;
	
	@FindBy(xpath="//button[contains(@class,'btn btn-primary') and contains(text(),'Upload Documents')]/following-sibling::input")
	WebElement uploadDocumentButton;
	
	@FindBy(xpath="//div[@class='preloader-full ng-star-inserted']")
	WebElement loadingScreen;
	
	@FindBy(xpath="//h4[text()='Delete Document']/ancestor::div[@class='modal-content']//input[@id='conOk']")
	WebElement deleteDocumentConfirmButton;
	
	@FindBy(xpath="//button[contains(@class,'btn btn-primary') and contains(text(),'Continue')]")
	WebElement continueButton;
	
	@FindBy(xpath="//span[@class='termspoint' and normalize-space('I accept the')]/..//input[@id='mat-checkbox-1-input']")
	WebElement termAndConditionCheckboxInputField;
	
	@FindBy(xpath="(//span[@class='termspoint' and normalize-space('I accept the')]/..//div)[1]")
	WebElement termAndConditionCheckbox;
	
	@FindBy(xpath="//button[contains(@class,'btn btn-primary') and contains(text(),'Submit')]")
	WebElement submitButton;
//	------------------------------------------------------------------------------------------------------------------------------------------------------------

	public List<WebElement> allProducts() {
		return allProducts;
	}
	
	public void enterOwnerSellerFullName(String fullName) throws IOException {
		sendKeys(ownerSellerInputField, fullName);
		PlaceOrder.test.log(LogStatus.PASS,"Owner seller name entered successfully");
	}
	
	public void enterClientReference(String clientRef) throws IOException {
		sendKeys(clientReferenceInputField, clientRef);
		PlaceOrder.test.log(LogStatus.PASS,"Client reference entered successfully");
	}
	
	public void enterPropertySearch(String property) throws IOException {
		sendKeys(propertySearchInputField, property);
		PlaceOrder.test.log(LogStatus.PASS,"Property entered successfully");
	}
	
	public List<WebElement> propertyAutoSuggestions() {
		return propertyAutoSuggestions;
	}
	
	public WebElement placeNewOrderTextElement() {
			return placeNewOrderText;
	}
	
	public WebElement propertySearchElement() {
		return propertySearchInputField;
	}

	public WebElement autoCompleteElement() {
		return autoCompleteCheckbox;
	}
	
	public void uploadDocumentToPlaceOrder(String filePath) throws IOException {
		uploadDocument(filePath, uploadDocumentButton);
		File file = new File(filePath);
		String fileName = file.getName();
		WebElement we = driver.findElement(By.xpath("//div[contains(@class,'d-flex align-items-center ng-star-inserted')]/div[contains(text(),'"+fileName+"')]"));
		if(we.isDisplayed()) {
			PlaceOrder.test.log(LogStatus.PASS,"File successfully uploaded");
		}
		else {
			PlaceOrder.test.log(LogStatus.FAIL,PlaceOrder.test.addScreenCapture(screenCapture(driver)),"Failed to upload file");
		}
	}
	
//	public void uploadDocumentsToPlaceOrder(String ... filePath ) {
//		for(String path:filePath) {
//			System.out.println(path);
//			uploadDocumentToPlaceOrder(path);
//		}	
//	}
	
	public void uploadDocumentsToPlaceOrder(String filePath ) throws IOException {
			uploadDocumentToPlaceOrder(filePath);
	}
	
	public void deleteUploadedFile(String fileName) throws IOException{
		waitUntillInvisibilityOfElementLocated(driver.findElement(By.xpath("//div[@class='preloader-full ng-star-inserted']")),30,driver);
		clickOnDeleteIcon(fileName, driver);
		if(isElementDisplayed(deleteDocumentConfirmButton)) {
			clickOnElement(deleteDocumentConfirmButton);
			PlaceOrder.test.log(LogStatus.PASS,"Document "+fileName+" deleted");
		}
		else {
			PlaceOrder.test.log(LogStatus.FAIL,PlaceOrder.test.addScreenCapture(screenCapture(driver)), "Failed to delete document - "+fileName+"");
		}
	}
	
	public void clickContinue() throws IOException {
		try {
		waitUntillVisibilityOfElementLocated(continueButton, 20, driver);
		clickOnElementJavaScript(continueButton, driver);
		PlaceOrder.test.log(LogStatus.PASS,"Clicked on continue button");
		}
		catch(Exception e) {
			PlaceOrder.test.log(LogStatus.FAIL,PlaceOrder.test.addScreenCapture(screenCapture(driver)), "Exception occured while cliking on Submit button - "+e);
		}
	}
	
	public void acceptTermAndConditions() throws IOException{
		try {
		waitUntillVisibilityOfElementLocated(termAndConditionCheckbox, 20, driver);
		executeJavaScript("arguments[0].scrollIntoView();",termAndConditionCheckbox,driver);
		clickOnElementJavaScript(termAndConditionCheckbox, driver);
		if(termAndConditionCheckboxInputField.isSelected()) {
			PlaceOrder.test.log(LogStatus.PASS,"Terms and Conditions accepted");
		}
		else {
			PlaceOrder.test.log(LogStatus.FAIL,PlaceOrder.test.addScreenCapture(screenCapture(driver)), "Failed to accept Terms and Conditons");
		}
		}
		catch(Exception e) {
			PlaceOrder.test.log(LogStatus.FAIL,PlaceOrder.test.addScreenCapture(screenCapture(driver)), "Exception occured while accepting Terms and Conditons -" +e);
		}
	}
	
	public void clickSubmitButton() {
		clickOnElementJavaScript(submitButton,driver);
		PlaceOrder.test.log(LogStatus.PASS,"Clicked on Submit button");
	}
	

	
}
