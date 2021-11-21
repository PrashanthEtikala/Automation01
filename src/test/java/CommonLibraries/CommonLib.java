package CommonLibraries;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.Status;
import com.cucumber.listener.Reporter;
import com.google.common.base.Function;import cucumber.api.DataTable;
public class CommonLib extends Driver {

	static String screenShotName;
	public static WebDriverWait wait;

	public static void sendKeys(By Locator, String Value) {

		/*
		 * try{ wait = (WebDriverWait) new WebDriverWait(driver,20)
		 * .withTimeout(30, TimeUnit.SECONDS) .pollingEvery(5, TimeUnit.SECONDS)
		 * .ignoring(NoSuchElementException.class);
		 * wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));
		 * 
		 * WebElement ele = driver.findElement(Locator);
		 * 
		 * ele.clear(); ele.sendKeys(Value); System.out.println(
		 * "Entered Text In Locator "+Locator+" as - "+Value); } catch(Exception
		 * e){ screenShotName="Exception"; takeScreenshot(); System.out.println(
		 * "Exception "+e); }
		 */
		wait = (WebDriverWait) new WebDriverWait(driver, 20).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
		wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));

		WebElement ele = driver.findElement(Locator);

		ele.clear();
		ele.sendKeys(Value);
		System.out.println("Entered Text In Locator " + Locator + " as - " + Value);

	}

	public static void takeScreenshot() {
		File Srcscreenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		String destination = "C:\\SFDCScreenShots\\" + screenShotName + dateName + ".png";
		File finalDestination = new File(destination);

		try {
			FileUtils.copyFile(Srcscreenshot, finalDestination);
			Reporter.addScreenCaptureFromPath(destination);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void quitDriver() {
		WebDriver driver = null;
		driver.quit();
	}

	public static void refreshProject() {

	}

	public static void click(By Locator) {

		/*
		 * try{ wait = (WebDriverWait) new WebDriverWait(driver,20)
		 * .withTimeout(30, TimeUnit.SECONDS) .pollingEvery(5, TimeUnit.SECONDS)
		 * .ignoring(NoSuchElementException.class);
		 * wait.until(ExpectedConditions.elementToBeClickable(Locator));
		 * 
		 * WebElement ele = driver.findElement(Locator);
		 * 
		 * ele.click();
		 * 
		 * System.out.println("Element identified by Locator "+Locator+
		 * " is clicked successfully"); } catch(Exception e){
		 * screenShotName="Exception"; takeScreenshot(); System.out.println(
		 * "Exception "+e); }
		 */
		wait = (WebDriverWait) new WebDriverWait(driver, 20).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
		wait.until(ExpectedConditions.elementToBeClickable(Locator));

		WebElement ele = driver.findElement(Locator);

		ele.click();

		System.out.println("Element identified by Locator " + Locator + " is clicked successfully");

	}

	public static void refreshPage() {
		driver.navigate().refresh();
		waitUntilPageLoadingIsCompleted();
	}

	public static void waitUntilPageLoadingIsCompleted() {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		try {
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver, 40);
			wait.until(expectation);
		} catch (Exception e) {
			// failureLogger("Timeout waiting for Page Load Request to complete:
			// " + e);
		}
	}

	public static void selectByText(By Locator, String Value) {
		
		try{
			wait = (WebDriverWait) new WebDriverWait(driver,20)
				.withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);
			wait.until(ExpectedConditions.presenceOfElementLocated(Locator));
		
			WebElement ele = driver.findElement(Locator);
			
			Select sel = new Select(ele);
			sel.selectByVisibleText(Value);
		
			System.out.println("Selected text in Locator "+Locator+" as - "+Value);
		} catch(Exception e){
			screenShotName="Exception";
			 //takeScreenshot();
			System.out.println("Exception "+e);
		}
		
	}

	public static void scrollElementIntoView(By object) {
		JavascriptExecutor javascriptexecutorJE = (JavascriptExecutor) driver;
		javascriptexecutorJE.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(object));
	}

	public static void scrollPageUsingJS() {
		JavascriptExecutor javascriptexecutorJE = (JavascriptExecutor) driver;
		javascriptexecutorJE.executeScript("window.scrollBy(0,100)");
	}

	public static void scrollPageUntilElementClickable(By object, int maxScrolls) {
		int clicked = 0;
		int loopcount = 0;
		do {
			loopcount++;
			try {
				click(object);
				clicked = 1;
			} catch (Exception e) {
				scrollPageUsingJS();
				System.out.println("Scrolling..");
			}
		} while (loopcount <= maxScrolls && clicked == 0);

	}

	public static void selectByIndex(By Locator, int Value) {

		try {
			wait = (WebDriverWait) new WebDriverWait(driver, 20).withTimeout(30, TimeUnit.SECONDS)
					.pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
			wait.until(ExpectedConditions.presenceOfElementLocated(Locator));

			WebElement ele = driver.findElement(Locator);

			Select sel = new Select(ele);
			sel.selectByIndex(Value);

			WebElement option = sel.getFirstSelectedOption();

			String value = option.getText();

			System.out.println("Selected text in Locator " + Locator + " as - " + value);
		} catch (Exception e) {

			System.out.println("Exception " + e);
		}

	}

	public static void selectByValue(By Locator, String Value) {

		try {
			wait = (WebDriverWait) new WebDriverWait(driver, 20).withTimeout(30, TimeUnit.SECONDS)
					.pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
			wait.until(ExpectedConditions.presenceOfElementLocated(Locator));

			WebElement ele = driver.findElement(Locator);

			Select sel = new Select(ele);
			sel.selectByValue(Value);

			System.out.println("Selected text in Locator " + Locator + " as - " + Value);
		} catch (Exception e) {
			System.out.println("Exception " + e);
		}

	}

	public static void selectCheckbox(By Locator) {

		try {
			wait = (WebDriverWait) new WebDriverWait(driver, 20).withTimeout(30, TimeUnit.SECONDS)
					.pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
			wait.until(ExpectedConditions.presenceOfElementLocated(Locator));

			WebElement ele = driver.findElement(Locator);

			if (!ele.isSelected()) {
				ele.click();
			}
			System.out.println("Checkbox Identified By Locator " + Locator + " is clicked.");
		} catch (Exception e) {
			screenShotName = "Exception";
			takeScreenshot();
			System.out.println("Exception " + e.getMessage());
		}

	}

	public static void specialSendKeys(By Locator, String Value) {

		try {
			wait = (WebDriverWait) new WebDriverWait(driver, 20).withTimeout(30, TimeUnit.SECONDS)
					.pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
			wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));

			WebElement ele = driver.findElement(Locator);
			ele.sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END), Value);

			System.out.println("Entered Text In Locator " + Locator + " as - " + Value);
		} catch (Exception e) {
			System.out.println("Exception " + e.getMessage());
		}

	}

	public static void clickUsingJS(By Locator) {

		/*
		 * try{ wait = (WebDriverWait) new WebDriverWait(driver,20)
		 * .withTimeout(30, TimeUnit.SECONDS) .pollingEvery(5, TimeUnit.SECONDS)
		 * .ignoring(NoSuchElementException.class);
		 * wait.until(ExpectedConditions.elementToBeClickable(Locator));
		 * WebElement element = driver.findElement(Locator); JavascriptExecutor
		 * executor = (JavascriptExecutor) driver;
		 * executor.executeScript("arguments[0].click();", element);
		 * 
		 * System.out.println("Element identified by Locator "+Locator+
		 * " is clicked successfully"); } catch(Exception e){
		 * System.out.println("Exception "+e.getMessage()); }
		 */
		wait = (WebDriverWait) new WebDriverWait(driver, 20).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
		wait.until(ExpectedConditions.elementToBeClickable(Locator));
		WebElement element = driver.findElement(Locator);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);

		System.out.println("Element identified by Locator " + Locator + " is clicked successfully");

	}

	public static void clickUsingActions(By Locator) {

		/*
		 * try{ wait = (WebDriverWait) new WebDriverWait(driver,20)
		 * .withTimeout(30, TimeUnit.SECONDS) .pollingEvery(5, TimeUnit.SECONDS)
		 * .ignoring(NoSuchElementException.class);
		 * wait.until(ExpectedConditions.elementToBeClickable(Locator));
		 * 
		 * WebElement ele = driver.findElement(Locator); Actions action = new
		 * Actions(driver); action.click(ele).build().perform();
		 * 
		 * System.out.println("Element identified by Locator "+Locator+
		 * " is clicked successfully"); } catch(Exception e){
		 * System.out.println("Exception "+e.getMessage()); }
		 */
		wait = (WebDriverWait) new WebDriverWait(driver, 20).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
		wait.until(ExpectedConditions.elementToBeClickable(Locator));

		WebElement ele = driver.findElement(Locator);
		Actions action = new Actions(driver);
		action.click(ele).build().perform();

		System.out.println("Element identified by Locator " + Locator + " is clicked successfully");
	}

	public static void robotSendTabKey() {
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
		} catch (AWTException e) {
			e.printStackTrace();
		}

	}

	public static void robotSendEnterKey() {
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			System.out.println("Successfully Sent Enter key");
		} catch (AWTException e) {
			e.printStackTrace();
		}

	}

	public static void waitForPageLoaded() {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		try {
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(expectation);
		} catch (Throwable error) {
			Assert.fail("Timeout waiting for Page Load Request to complete.");
		}
	}

	public static String getSubstring(String fullString) {
		try {
			if (!(fullString.equals(" ") || fullString.equals(""))) {
				fullString = fullString.substring(fullString.indexOf(':') + 1, fullString.length());
				return fullString;
			} else {
				System.out.println("blank or null");
				return " ";
			}

		} catch (Exception e) {
			System.out.println("Catch blank or null");
			return " ";
		}
	}

	public static void verifyContains(String ActualValue, String ExpectedValue, String Message)  {
		 String Actual = ActualValue.toLowerCase().trim();
	     String Expected = ExpectedValue.toLowerCase().trim();
	     
	     System.out.println("Comparing "+Actual+" : "+Expected);
	     boolean compare = (Actual.contains(Expected));
	     System.out.println("=================================================================");
	     if (compare) {
	    	 Assert.assertTrue(Message + " Validation passed for : " + ActualValue, true);
	         System.out.println(Message + " Validation passed for expected Text : " + ExpectedValue + "  : Actual text :" + ActualValue);
	     } else {
	         Assert.assertFalse(Message + " Validation failed for expected Text : " + ExpectedValue + "  : Actual text :" + ActualValue, true);
	     }
	}
	public static void verifyActualExpected(String ActualValue, String ExpectedValue, String Message)  {
		 String Actual = ActualValue.toLowerCase().trim();
	     String Expected = ExpectedValue.toLowerCase().trim();
	     
	     System.out.println("Comparing "+Actual+" : "+Expected);
	     boolean compare = (Actual.equals(Expected));
	     System.out.println("=================================================================");
	     if (compare) {
	    	 Assert.assertTrue(Message + " Validation passed for : " + ActualValue, true);
	    	 //Reporter.addStepLog(Message + "\n"+ " Expected Text : " + ExpectedValue + "\n"+" Actual text : " + ActualValue);
	    	 ResultUtils.fnReportEvent(Message + "\n"+ " Expected Text : " + ExpectedValue + "\n"+" Actual text : " + ActualValue, "Info");
	         System.out.println(Message + " Validation passed for expected Text : " + ExpectedValue + "  : Actual text : " + ActualValue);
	     } else {
	    	
//	    	 try{
	         //Assert.assertFalse(" Validation failed for expected Text : " + ExpectedValue + "  : Actual text : " + ActualValue, true);
	    		 
	    		 Assert.assertFalse(" Expected Text : " + ExpectedValue + "\n"+" Actual text : " + ActualValue,true);
	    		 
//	    	 }
//	    	 catch (AssertionError e)
//	    	 {
//	    		 screenShotName="Fail";
//		    	 takeScreenshot();
//	    		 Reporter.addStepLog(e + "\n" + " Expected Text : " + ExpectedValue + "\n"+" Actual text : " + ActualValue);
//	    		
//	    		 System.out.println( Message + " Validation failed for expected Text : " + ExpectedValue + "  : Actual text : " + ActualValue);
//	    	 }
	     }
	}


	public static String getDefaultSelectedOption(By Locator) {
		try {
			wait = (WebDriverWait) new WebDriverWait(driver, 30).withTimeout(20, TimeUnit.SECONDS)
					.pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
			wait.until(ExpectedConditions.presenceOfElementLocated(Locator));
			Select select = new Select(driver.findElement(Locator));
			WebElement option = select.getFirstSelectedOption();
			String defaultItem = option.getText();
			System.out.println(
					"Default Selected Option in element identified by locator " + Locator + " is " + defaultItem);
			return defaultItem;
		} catch (Exception e) {
			System.out.println("Exception - " + e.getMessage());
			return "";
		}
	}

	public static String getElementText(By Locator) {
		try {
			wait = (WebDriverWait) new WebDriverWait(driver, 30).withTimeout(20, TimeUnit.SECONDS)
					.pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
			wait.until(ExpectedConditions.presenceOfElementLocated(Locator));

			WebElement ele = driver.findElement(Locator);
			String data = ele.getText().toString();
			System.out.println("Text present in element identified by locator " + Locator + " is " + data);
			return data;
		} catch (Exception e) {
			System.out.println("Exception - " + e.getMessage());
			return "";
		}
	}

	public static String getElementAttributeValue(By Locator, String AttributeName) {
		try {
			wait = (WebDriverWait) new WebDriverWait(driver, 30).withTimeout(20, TimeUnit.SECONDS)
					.pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
			wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));

			WebElement ele = driver.findElement(Locator);
			String data = ele.getAttribute(AttributeName);
			System.out.println("Attribute value present in element identified by locator " + Locator + " is " + data);
			return data;
		} catch (Exception e) {
			System.out.println("Exception - " + e.getMessage());
			return "";
		}
	}

	public static boolean IsElementPresent(By Locator) {
		try {
			if (driver.findElements(Locator).isEmpty()) {
				System.out.println("Element identified by Locator - " + Locator + " is not present on the webpage");
				return false;

			} else {
				System.out.println("Element identified by Locator - " + Locator + " is present on the webpage");
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean IsElementDisplayed(By Locator) {
		try {
			if (driver.findElements(Locator).isEmpty()) {
				System.out.println("Element identified by Locator - " + Locator + " is not present on the webpage");
				return false;
			} else {
				System.out.println("Element identified by Locator - " + Locator + " is present on the webpage");
				if (driver.findElement(Locator).isDisplayed()) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean IsElementEnabled(By Locator) {
		return driver.findElement(Locator).isEnabled();
	}

	public static String convertGivenDateToYYYYMMDDHHMM(String date) {
		int index = 0;
		String time = "";

		if (date.indexOf("AM") == date.length() - 2 || date.indexOf("PM") == date.length() - 2) {

		}

		else if (date.contains("AM") || date.contains("PM")) {
			if (date.contains("AM")) {
				index = date.indexOf("AM");
				System.out.println("AM index" + index);
				time = " AM";
			} else if (date.contains("PM")) {
				index = date.indexOf("PM");
				System.out.println("PM index" + index);
				time = " PM";
			}

			String date1 = date.substring(0, index);
			String date2 = date.substring(index + 2, date.length());
			date = date1 + date2 + time;

		}

		date = date.replace('-', '/');
		date = date.replace('.', '/');
		System.out.println(date);
		String formattedDate = new SimpleDateFormat("yyyyMMddhhmm").format(new Date(date));
		return formattedDate;

	}

	public static void HandleAlert() {
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}


	public static void writePropertiesfile(String string, String quoteNumber) {
		// TODO Auto-generated mi was ethod stub

	}

	public static void captureScreenshotWithTestName(String result) {

		File Srcscreenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		// String destination = "C:\\Users\\e2e\\Downloads\\" + result + "_" +
		// dateName + ".png";
		String destination = System.getProperty("user.dir") + "" + ".//test-output//Screenshots//" + result + "_"
				+ dateName + ".png";

		File finalDestination = new File(destination);

		try {
			FileUtils.copyFile(Srcscreenshot, finalDestination);
			Reporter.addScreenCaptureFromPath(destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} // try
		catch (NoAlertPresentException Ex) {
			return false;
		} // catch
	}
	
	public static void waitUntilElementClickable(By object, int maxTries) throws Throwable {
        Thread.sleep(2000);
        int clicked=0;
        int loopcount=0;
        do {
               loopcount++;
               try {
               click(object);
               clicked=1;
               } catch (Exception e) {
                     System.out.println("Retrying..");
               }
        } while(loopcount<=maxTries && clicked==0);
        Thread.sleep(2000);
 }





 public static boolean waitForElementToBeEnabled(final By Locator, int maxWaitTime) {
        try {
               new FluentWait<WebElement>(driver.findElement(Locator)).withTimeout(maxWaitTime, TimeUnit.SECONDS).pollingEvery(1, TimeUnit.SECONDS).ignoring(NoSuchElementException.class,ElementClickInterceptedException.class).until(new Function<WebElement, Boolean>() {
                     //@Override
                     public Boolean apply(WebElement element) {
                            System.out.println("Waiting for element to enabled. " + Locator);
                            return element.isEnabled();
                     }
               });
        } catch (TimeoutException e) {
               return false;
        } catch (Exception e) {
               return false;
        }
        return true;
 }




 public static void waitForElementToAppear(By Locator, int maxWaitTime) {
        WebElement elem = driver.findElement(Locator);
        try {
               System.out.println("Waiting for element to appear: " + elem);
               WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);
               wait.until(ExpectedConditions.visibilityOf(elem));
               wait.until(ExpectedConditions.elementToBeClickable(elem));
        } catch (Exception e) {
               Assert.assertFalse("Exception occured while waiting for element to appear: " + elem + e, true);
        }
 }
 
	public static boolean waitforElement(By locator) {
		int noOfTrials = 0;
		List<WebElement> allElements = null;
		try {

			driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
			allElements = driver.findElements(locator);
			while (allElements.size() <= 0) {
				Thread.sleep(1000);
				if (noOfTrials < 3) {
					System.out.println(String.format("Polling to check for the Element -- %s -- ", locator));
				} else {
					System.out.println("Still polling to check for the Element !");
				}
				allElements = driver.findElements(locator);
				noOfTrials++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (allElements != null && allElements.size() > 0) {
				System.out.println(String.format("Object successfuly -- %s -- found on page!", locator));
				return true;
			} else {
				System.out.println(String.format("Object -- %s -- NOT found on Page!", locator));
			}
		}
		return false;
	}
	
	public static Map<String, LinkedHashMap<String, String>> dataTableToHashMap(DataTable table) {
		List<List<String>> data = table.raw();
		Map<String, LinkedHashMap<String, String>> dataMap = new LinkedHashMap<String, LinkedHashMap<String, String>>();

		for (int i = 1; i < data.size(); i++) {
			LinkedHashMap<String, String> temp = new LinkedHashMap<String, String>();
			for (int j = 0; j < data.get(i).size(); j++) {
				temp.put(data.get(0).get(j), data.get(i).get(j));
			}
			dataMap.put(Integer.toString(i), temp);
		}
		return dataMap;
	}
	
	public static void selectDropDownByValueJetUI(By dropdownByLocator, String valueToSelect) throws Throwable {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownByLocator));
			CommonLib.clickUsingJS(dropdownByLocator);
			CommonLib.clickUsingJS(dropdownByLocator);
			Thread.sleep(2000);
			String xpathtoSelect = "//div[@class='oj-listbox-result-label'][@data-value='" + valueToSelect+ "' or @aria-label='" + valueToSelect + "' or contains(text(),'" + valueToSelect + "')]";
			
			List<WebElement> listElements = driver.findElements(By.xpath(xpathtoSelect));
			
			if (listElements.size() > 0) {
				CommonLib.fnScrollIntoView(By.xpath(xpathtoSelect));
				listElements.get(0).click();
				System.out.println(String.format("List item : %s found from the dropdown list : %s :: ", valueToSelect,
						dropdownByLocator.toString()));
			} else {
				System.out.println(String.format("List item : %s not found from the dropdown list : %s :: ",
						valueToSelect, dropdownByLocator.toString()));
				CommonLib.click(dropdownByLocator);
				CommonLib.click(By.xpath(xpathtoSelect));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void fnScrollIntoView(By ObjectName) throws InterruptedException {

		JavascriptExecutor javascriptexecutorJE = (JavascriptExecutor) driver;
		String attributevalue = "border:3px solid red;";
		String getattrib = driver.findElement(ObjectName).getAttribute("style");
		javascriptexecutorJE.executeScript("arguments[0].setAttribute('style', arguments[1]);", driver.findElement(ObjectName), attributevalue);
		javascriptexecutorJE.executeScript("arguments[0].setAttribute('style', arguments[1]);", driver.findElement(ObjectName), getattrib);
		javascriptexecutorJE.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(ObjectName));

	}


}
