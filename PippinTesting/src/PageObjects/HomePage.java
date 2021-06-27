package PageObjects;
import Pippin.Keywords;
import Pippin.PlaceOrder;
import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.relevantcodes.extentreports.LogStatus;

public class HomePage extends Keywords{
	WebDriver driver;
	public HomePage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//a[contains(@class,'btn order-btn text-left')]/strong[text()='Place Order']") 
	WebElement placeOrderButton;
	
//	------------------------------------------------------------------------------------------------------------------------------------------

	public void clickPlaceOrder() throws IOException {
		try {
		clickOnElement(placeOrderButton);
		PlaceOrder.test.log(LogStatus.PASS,"Clicked on Place Order button");
		}
		catch(Exception e) {
			PlaceOrder.test.log(LogStatus.FAIL,PlaceOrder.test.addScreenCapture(screenCapture(driver)), "Exception occured while clicking on Place Order button");
		}
	}
	
	public WebElement placeOrderElement() {
		return placeOrderButton;	
	}
}
