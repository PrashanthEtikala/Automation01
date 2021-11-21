package StepDefinations.Steps;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.GenericLib.PropertiesFileLib;

import CommonLibraries.CommonLib;
import CommonLibraries.Driver;
import PageFactory.LoginPage_PF;
import PageFactory.WalletHub_RateandReview;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
public class RateandReview extends Driver{ 
	String PostedReview;
	@And("^navigate to profile$")
	public void navigate_to_profile() { 
        System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver.exe");  
        WebDriver driver=new ChromeDriver();  
        //Externalized Strings
	    String WH_URL = PropertiesFileLib.ReadPropertiesFile("PropertiesFileName","WalletHub_URL");
	    driver.navigate().to(WH_URL);  
        driver.manage().window().maximize();
        //Using Page Object Model performed driver actions
	    CommonLib.sendKeys(LoginPage_PF.txt_username,WH_URL);
		
	}
	@Then("^verify check the hoverover star lit$")
	public void verify_check_the_hoverover_star_lit() { 
		Actions actions = new Actions(driver);
    	WebElement HoverHover_Star4 = driver.findElement(By.xpath("//*[text()=' Rate 4 and review ']")); 
    	actions.moveToElement(HoverHover_Star4).perform();
    	if(driver.findElements(By.xpath("//*[text()=' Rate 3 and review ']//following::*[@fill='#4ae0e1'][1]")).isEmpty()){
    		System.out.println("Failed: Star Lit is not displayed");
    	}else {
    		System.out.println("Passed: Star Lit is displayed as expected");
    	}
    	
	}
	@And("^post a review$")
	public void post_a_review() { 
		CommonLib.clickUsingJS(WalletHub_RateandReview.lnk_FourStar);
		CommonLib.waitForElementToAppear(WalletHub_RateandReview.drpdwn_Policy,3);
	    PostedReview = PropertiesFileLib.ReadPropertiesFile("PropertiesFileName","SampleText");
		CommonLib.sendKeys(WalletHub_RateandReview.txtarea_WriteReview, PostedReview);
		CommonLib.selectByValue(WalletHub_RateandReview.drpdwn_Policy_HealthInsurance,"Health Insurance");
		CommonLib.clickUsingJS(WalletHub_RateandReview.btn_Submit);
		// For some reason i am unable to review via profile so i used test profile link provided in assignment
	}
	@Then("^verify the posted review$")
	public void verify_the_posted_review() { 
		//1996test1996test123 username i selected for Automatiom purpose
		WebElement element = driver.findElement(By.xpath("//*[text()=' 1996test1996test123']//following::div[@class='rvtab-ci-content with-links text-select'][1]"));
		String Expected_Text = element.getText();
		//Using assert checking the Expected Posted Review and Actual Posted Review
		Assert.assertEquals(PostedReview,Expected_Text);
	}
    
}
