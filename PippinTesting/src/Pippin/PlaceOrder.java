package Pippin;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import PageObjects.Common;
import PageObjects.HomePage;
import PageObjects.LoginPage;
import PageObjects.OrderDetails;
import PageObjects.PlaceNewOrder;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;


public class PlaceOrder extends Keywords {
	
	public static ExtentTest test;
	public static ExtentReports report;
	static WebDriver driver;
	Properties prop;
	LoginPage objLoginPage;
	HomePage objHomePage;
	PlaceNewOrder objPlaceNewOrder;
	OrderDetails objOrderDetails;
	Common objCommon;

	@BeforeClass
	public static void setup() throws IOException{
		report = new ExtentReports(System.getProperty("user.dir")+"\\TestReports\\ExtentReportResults.html", true);
		test = report.startTest("PlaceOrder");	
		System.setProperty("webdriver.chrome.driver", "WebDrivers//chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);		
	}
	
	@Test
	public void test() throws IOException, InterruptedException{
		prop = loadProperties();
		objLoginPage = new LoginPage(driver);
		objHomePage = new HomePage(driver);
		objPlaceNewOrder = new PlaceNewOrder(driver);
		objOrderDetails = new OrderDetails(driver);
		objCommon = new Common(driver);
		
		launchApplication(getProperty("url"),driver);
		
		objLoginPage.login(getProperty("emailAddress"),getProperty("password"));
		
		waitUntillVisibilityOfElementLocated(objHomePage.placeOrderElement(),10,driver);
		
		objHomePage.clickPlaceOrder();
		
		waitUntillVisibilityOfElementLocated(objPlaceNewOrder.placeNewOrderTextElement(),20,driver);
		
		selectProduct(getProperty("product"),objPlaceNewOrder.allProducts(),driver);
		
		objPlaceNewOrder.enterOwnerSellerFullName(getProperty("ownerSellerName"));
		
		objPlaceNewOrder.enterPropertySearch(getProperty("propertySearch"));
		
		selectPropertyFromSuggestions(getProperty("propertySuggestion"),objPlaceNewOrder.propertyAutoSuggestions(),objPlaceNewOrder.autoCompleteElement(),driver );

		objPlaceNewOrder.enterClientReference(getProperty("clientReference"));
		
		objPlaceNewOrder.uploadDocumentsToPlaceOrder(getFilePath("file1Path") + "\n " + getFilePath("file2Path") + "\n " + getFilePath("file3Path"));
		
		objPlaceNewOrder.deleteUploadedFile("File2.pdf");
				
		objPlaceNewOrder.deleteUploadedFile("File3.pdf");
		
		objPlaceNewOrder.clickContinue();
		
		objPlaceNewOrder.acceptTermAndConditions();
		
		objPlaceNewOrder.clickSubmitButton();
		
		waitUntillVisibilityOfElementLocated(objOrderDetails.orderDetailsTextElement(),10,driver);
		
		String orderID = objOrderDetails.getOrderID();
		
		String currentDateTime = getCurrentDateTime();
		
		String message = ""+getProperty("ownerSellerName")+"_"+orderID+"_"+currentDateTime+"";
		
		objOrderDetails.enterMessage(message);
		
		objOrderDetails.clickSendButton();	
		
		objOrderDetails.getYourMessageAndVerify(message);
		
		objCommon.logout();	
	}
	
	@AfterClass
	public static void endTest(){
	report.endTest(test);
	report.flush();
	report.close();
	driver.quit();
	}
	
}

