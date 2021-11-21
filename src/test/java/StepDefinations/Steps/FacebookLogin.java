package StepDefinations.Steps;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.GenericLib.PropertiesFileLib;

import CommonLibraries.CommonLib;
import CommonLibraries.Driver;
import PageFactory.LoginPage_PF;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import sun.security.krb5.Config;
public class FacebookLogin extends Driver{  
	
	@Given("^login facebook$")
	public void login_facebook() {   
        System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver.exe");  
        WebDriver driver=new ChromeDriver();  
        //Externalized Strings
	    String FB_Username = PropertiesFileLib.ReadPropertiesFile("PropertiesFileName","Facebook_Username");
	    String FB_Password = PropertiesFileLib.ReadPropertiesFile("PropertiesFileName","Facebook_Password");
	    String FB_URL = PropertiesFileLib.ReadPropertiesFile("PropertiesFileName","Facebook_URL");
	    driver.navigate().to(FB_URL);  
        driver.manage().window().maximize();
        //Using Page Object Model performed driver actions
	    CommonLib.sendKeys(LoginPage_PF.txt_username,FB_Username); //Externalized String
	    CommonLib.sendKeys(LoginPage_PF.txt_password,FB_Password); //Externalized String
	    CommonLib.click(LoginPage_PF.btn_login);
	    System.out.println("Login successfull");  
	    
}
	@Then("^post a status \"([^\"]*)\" $")
	public void post_a_status(String Post_Message) {
		// Explicit Wait
	    WebDriverWait wait = new WebDriverWait(driver,6);
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),' on your mind')]")));
		// Explicit Wait End
	    //Using Page Object Model performed driver actions
	    CommonLib.clickUsingJS(LoginPage_PF.txtarea_PostAStatus); // click on textarea to wirte a post
	    CommonLib.waitForElementToAppear(LoginPage_PF.txtarea_PostAStatus,3); // writes a post in new window
	    CommonLib.sendKeys(LoginPage_PF.txtarea_PostAStatus,Post_Message); // sendkeys as 'Hello World'//Externalized String
	    CommonLib.click(LoginPage_PF.btn_Post); // Posted
	    CommonLib.waitForPageLoaded();
	}
}
