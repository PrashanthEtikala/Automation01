package PageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import CommonLibraries.Driver;

public class WalletHub_RateandReview {
	public static By lnk_FourStar = By.xpath("//*[text()=' Rate 4 and review ']");
	public static By obj_StarHighligt = By.xpath("//*[text()=' Rate 3 and review ']//following::*[@fill='#4ae0e1'][1]");
	public static By Obj_HighlightedStars_Window= By.xpath("//*[text()=' Rate 3 and review ']//following::*[@fill='#4ae0e1'][1]");
	public static By drpdwn_Policy = By.xpath("//*[@class='dropdown-placeholder' and text()='Select...']");
	public static By drpdwn_Policy_HealthInsurance = By.xpath("//li[text()='Health Insurance']");
	public static By txtarea_WriteReview = By.className("textarea wrev-user-input validate");
	public static By btn_Submit = By.className("sbn-action semi-bold-font btn fixed-w-c tall");
	
	
	
	
}