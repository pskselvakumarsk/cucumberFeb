package com.flipkart.stepdefinition;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class MobilevalidationSteps {
	
	static WebDriver driver;


@Given("user launches flipkart application")
public void user_launches_flipkart_application() {
	//System.setProperty("webdriver.chrome.driver", "C:\\Users\\Test\\eclipse-workspace\\junit-first\\driver\\chromedriver.exe");
	WebDriverManager.chromedriver().setup();
	driver = new ChromeDriver();
	driver.get("https://www.flipkart.com/");
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
	
}

@Given("user login by entering valid crendentials")
public void user_login_by_entering_valid_crendentials() {
	
try {	
	WebElement button = driver.findElement(By.xpath("//button[text()='✕']"));
	button.isDisplayed();
	button.click();
	
}catch (Exception e) {
	System.out.println("pop up is not displayed");
}
    
}

@When("user search mobile")
public void user_search_mobile() throws IOException {
	WebElement search = driver.findElement(By.name("q"));
	search.sendKeys("realme",Keys.ENTER);
	
	File file = new File(".//Excel//Mobile.xlsx");
	FileOutputStream f = new FileOutputStream(file);
	

	XSSFWorkbook w = new XSSFWorkbook();
	XSSFSheet sheet = w.createSheet("mobile");
	
	
	List<WebElement> mobiles = driver.findElements(By.xpath("(//div[contains(text(),'realme')])"));
	
	for (int i = 1; i < mobiles.size(); i++) {
		
		WebElement mobile = mobiles.get(i);
		String name = mobile.getText();
		
		XSSFRow row = sheet.createRow(i);
		XSSFCell cell = row.createCell(0);
		
		cell.setCellValue(name);
	}
	
	 w.write(f);
	 w.close();
    
}

@When("user click on the mobile name")
public void user_click_on_the_mobile_name() throws InterruptedException {
	
	WebElement mobile = driver.findElement(By.xpath("(//div[contains(text(),'realme')])[4]"));
	mobile.click();
	
	String parentURL = driver.getWindowHandle();
	
	Set<String> childURL = driver.getWindowHandles();
	for(String child : childURL) {
		
		if(parentURL.equals(child)) {
			driver.switchTo().window(child);
		}
	}
   Thread.sleep(2000);
   
}

@Then("user validates the mobile names")
public void user_validates_the_mobile_names() throws IOException {
	
	WebElement mobileName2 = driver.findElement(By.xpath("//span[contains(text(),'realme')]"));
	String name = mobileName2.getText();
	System.out.println(name);
	
	File file = new File(".//Excel//Mobile.xlsx");
	FileInputStream f = new FileInputStream(file);
	
	XSSFWorkbook w = new XSSFWorkbook(f);
	XSSFSheet sheet = w.getSheet("mobile");
	XSSFCell cell = sheet.getRow(4).getCell(0);
	
	String namefromexcel = cell.getStringCellValue();
	
	if(name.equals(namefromexcel)) {
		System.out.println("pass");
	}else
		System.out.println("fail");
	
	
    
}


	
}
