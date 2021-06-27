package Pippin;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.relevantcodes.extentreports.LogStatus;


public class Keywords{
	Properties prop;
	WebDriver driver;
	
	public Properties loadProperties() throws IOException {
		prop = new Properties();
		FileInputStream fis = new FileInputStream("testData.properties");
		prop.load(fis);
		return prop;	
	}
	
	public void launchApplication(String url, WebDriver driver) throws IOException {
		driver.get(url);
		PlaceOrder.test.log(LogStatus.PASS,PlaceOrder.test.addScreenCapture(screenCapture(driver)),"Application launched successfully");		
	}
	
	public String getProperty(String property) throws IOException {
		prop = loadProperties();
		return prop.getProperty(property);
	}
	
	public void sendKeys(WebElement we, String inputText) throws IOException {
		try{
			we.sendKeys(inputText);
		}
		catch(Exception e){
			PlaceOrder.test.log(LogStatus.FAIL,PlaceOrder.test.addScreenCapture(screenCapture(driver)),"Exception occured while entering text i.e. "+e);
		}
	}
	
	public void clickOnElement(WebElement we) throws IOException {
		try{
			we.click();
		}
		catch(Exception e){
			PlaceOrder.test.log(LogStatus.FAIL,PlaceOrder.test.addScreenCapture(screenCapture(driver)), "Exception occured while clicking on element i.e. "+e);
		}
	}
	
	
	public void waitUntillVisibilityOfElementLocated(WebElement we, int toWait, WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, toWait);
		wait.until(ExpectedConditions.visibilityOf(we));
	}
	
	public void waitUntillElementIsClickable(WebElement we, int toWait, WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, toWait);
		wait.until(ExpectedConditions.elementToBeClickable(we));
	}
	
	public void waitUntillInvisibilityOfElementLocated(WebElement we, int toWait, WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, toWait);
		wait.until(ExpectedConditions.invisibilityOfAllElements(we));
	}
	
//	public void fluentWait(WebElement we,WebDriver driver) {
//		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
//				.withTimeout(Duration.ofSeconds(30))
//				.pollingEvery(Duration.ofMillis(250))
//				.ignoring(NoSuchElementException.class);
//		WebElement webEle = wait.until(new Function<WebDriver,WebElement>()
//				{
//					public WebElement apply(WebDriver driver) {
//						return we;
//					}
//				});
//		System.out.println("WebElement is displayed - " + webEle.isDisplayed());	
//	}
	
	public void selectProduct(String product, List<WebElement> weList, WebDriver driver) throws IOException {
		try {
			for(int i=0;i<weList.size();i++) {
				if(weList.get(i).getText().equals(product)) {
					clickOnElementJavaScript(weList.get(i),driver);
				}
			}	
			PlaceOrder.test.log(LogStatus.PASS,PlaceOrder.test.addScreenCapture(screenCapture(driver)), "Product "+product+" selected SUCCESSFULLY");
		}
		catch(Exception e) {
			
			PlaceOrder.test.log(LogStatus.FAIL,PlaceOrder.test.addScreenCapture(screenCapture(driver)),"Exception occured while clicking on element i.e. "+e);
		}
	}
	
	public void selectPropertyFromSuggestions(String property, List<WebElement> weList, WebElement scrollTo, WebDriver driver) throws IOException {
		try {
		for(int i=0;i<weList.size();i++) {
			if(weList.get(i).getText().contains(property)) {
				PlaceOrder.test.log(LogStatus.PASS, weList.get(i).getText()+ " autofill option available");
				executeJavaScript("arguments[0].scrollIntoView();",scrollTo,driver);
				Thread.sleep(2000);
				clickOnElement(weList.get(i));
				PlaceOrder.test.log(LogStatus.PASS,PlaceOrder.test.addScreenCapture(screenCapture(driver)), "Property "+property+" selected SUCCESSFULLY");
				break;
			}
		}
		}
		catch(Exception e) {
			PlaceOrder.test.log(LogStatus.FAIL,PlaceOrder.test.addScreenCapture(screenCapture(driver)), "Exception occured while clicking on element i.e. "+e);
		}
	}
	
	public void clickOnElementJavaScript(WebElement we, WebDriver driver) {
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", we);
	}
	
	public void executeJavaScript(String script, WebElement we, WebDriver driver) {
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript(script, we);
	}
	
	public void uploadDocument(String filePath, WebElement we) throws IOException {
		sendKeys(we,filePath);
	}
	
	public void clickOnDeleteIcon(String fileName, WebDriver driver) throws IOException {
		
		WebElement we = driver.findElement(By.xpath("//div[contains(@class,'d-flex align-items-center ng-star-inserted')]/div[contains(text(),'"+fileName+"')]/following-sibling::button//i[normalize-space('Delete')]"));
		if (isElementDisplayed(we)) {
			clickOnElement(we);
		}
		else {
			waitUntillVisibilityOfElementLocated(we,15,driver);
			clickOnElement(we);
		}
		PlaceOrder.test.log(LogStatus.PASS,PlaceOrder.test.addScreenCapture(screenCapture(driver)), "Clicked Delete icon SUCCESSFULLY");
	}
	
	public String getValue(WebElement we) throws IOException {
		String value = we.getAttribute("value");
		if(value.length()>0) {
			PlaceOrder.test.log(LogStatus.PASS,"Value successfully captured for element"+we);
			}
		else {
			PlaceOrder.test.log(LogStatus.FAIL,PlaceOrder.test.addScreenCapture(screenCapture(driver)), "Failed to get value of Element - "+we);
		}
		return value;	
	}
	
	public String getCurrentDateTime(){
		DateFormat dateFormat = new SimpleDateFormat("MMM dd,yyyy,HH:mm:ss");
		Date date = new Date();
		PlaceOrder.test.log(LogStatus.PASS,"Current Date and Time is - "+dateFormat.format(date));
		return dateFormat.format(date);
	}
	
	public void verifyIfMessageDisplayedUnderRelatedMessage(WebElement we, String msg, WebDriver driver) throws IOException {
		if(we.getText().equals(msg)) {
			PlaceOrder.test.log(LogStatus.PASS, "Message displayed under Related message section as expected");
//			System.out.println("Verified that message displayed under Related message section");
		}
		else {
			PlaceOrder.test.log(LogStatus.FAIL,PlaceOrder.test.addScreenCapture(screenCapture(driver)), "Verified that message displayed under Related message section");

//			fail("Message not displayed under Related message section");
		}
		
	}
//	public void scheduledExecutor(int wait) {
//		ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
//        Runnable task1 = () -> System.out.println("Running task1...");
//        ses.schedule(task1, wait, TimeUnit.SECONDS);
//        ses.shutdown();	
//	}
	
	public Boolean isElementDisplayed(WebElement we) {
		try {
		Boolean displayed = we.isDisplayed();
		if(displayed) {
			return true;
		}
		else {
			return false;
		}
		}
		catch(Exception e) {
			return false;
		}
	}
	
	public String screenCapture(WebDriver driver) throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String fileName = new SimpleDateFormat("yyyyMMDDhhmmss.'png'").format(new Date());
		File Dest = new File(System.getProperty("user.dir")+"/Screenshots/" + fileName);
		String path = Dest.getAbsolutePath();
		FileUtils.copyFile(scrFile, Dest);
		return path;
		}
	
	public String getFilePath(String fileProperty) throws IOException {
		File file1Path = new File(System.getProperty("user.dir")+ getProperty(fileProperty));
		String path = file1Path.getAbsolutePath();
		PlaceOrder.test.log(LogStatus.PASS, "File path returned i.e. "+path);
		return path;
	}
	
}
