package PageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import CommonLibraries.Driver;

public class LoginPage_PF {
	public static By txt_username = By.id("name");
	public static By txt_password = By.id("password");
	public static By btn_login = By.name("login");
	public static By txtarea_PostAStatus = By.xpath("//*[contains(text(),' on your mind')]");
	public static By btn_Post = By.xpath("//*[@aria-label='Post']");
	
	
			
	
	
	
	/*
	 * @FindBy(id="name") WebElement txt_username;
	 * 
	 * @FindBy(id="password") WebElement txt_password;
	 * 
	 * @FindBy(name="login") WebElement btn_login;
	 * 
	 * @FindBy(xpath="//*[contains(text(),' on your mind')]") WebElement
	 * txtarea_PostAStatus;
	 * 
	 * 
	 * 
	 * public WebDriver driver;
	 * 
	 * public LoginPage_PF(WebDriver driver) { this.driver = driver;
	 * PageFactory.initElements(driver,this); }
	 * 
	 * public void username(String username) { txt_username.sendKeys(username); }
	 * public void password(String password) { txt_password.sendKeys(password); }
	 * public void PostAStatus(String Status) {
	 * txtarea_PostAStatus.sendKeys(Status); }
	 * 
	 * 
	 * public void click() { btn_login.click();
	 * 
	 * }
	 */
}
