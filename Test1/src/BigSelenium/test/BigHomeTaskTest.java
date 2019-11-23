package BigSelenium.test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BigHomeTaskTest {

	
	WebDriver driver;
	String bookName = "New Book For Test";
	String bookSum = "New Books Summary";
	String isbn = "isbn41";
	String[] authors = {"General, Kenobi","Abnett, Dan","Bot, Test","Carroll, Lewis","Gladwell, Malcolm","Kene, Marina","King, Stephen","London, Jack","Novik, Naomi"};
	String randomAuthor = (authors[new Random().nextInt(authors.length)]);

	@BeforeMethod
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "/usr/lib/chromium-browser/chromedriver");
		driver = new ChromeDriver(); // launch chrome
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
	}

	@Test(priority = 1)
	public void CreateBookTest() {
		driver.get("https://raamatukogu.herokuapp.com/catalog/book/create");
		// INSERT BOOKS NAME
		driver.findElement(By.xpath("//input[@name='title']")).clear();
		driver.findElement(By.xpath("//input[@name='title']")).sendKeys(bookName);

		// INSERT BOOKS SUMMARY
		driver.findElement(By.xpath("//input[@name='summary']")).clear();
		driver.findElement(By.xpath("//input[@name='summary']")).sendKeys(bookSum);

		// INSERT BOOKS ISBN
		driver.findElement(By.xpath("//input[@name='isbn']")).clear();
		driver.findElement(By.xpath("//input[@name='isbn']")).sendKeys(isbn);

		// SELECT  Non-Fiction + Fiction
		WebElement fiction = driver
				.findElement(By.xpath("//input[@class='checkbox-input' and @id='5b6714c93809970014e31c9a']"));
		fiction.click();
		WebElement nonFiction = driver
				.findElement(By.xpath("//input[@class='checkbox-input' and @id='5b6714c73809970014e31c99']"));
		nonFiction.click();
		// List the DropDown
		WebElement openDrop = driver.findElement(By.xpath("//select[(@class='form-control' and @id='author')]"));
		openDrop.click();
		WebElement selectAuthor = driver
				.findElement(By.xpath(String.format("//select[@id='author']/option[text()='%s']", randomAuthor)));
		selectAuthor.click();
		// CLICK Submit 
		WebElement submit = driver.findElement(By.xpath("//button[contains(text(),'Submit')]"));
		submit.click();

		// CHECK THAT EVERYTHING IS CORRECT
		
		boolean b0 = driver.findElement(By.xpath(String.format("//h1[text()='Title: %s']", bookName))).isDisplayed();
		boolean b1 = driver.findElement(By.xpath(String.format("//p[text()]//parent::a[text()='%s']", randomAuthor))).isDisplayed();
		boolean b2 = driver.findElement(By.xpath("//p[text()]//parent::a[text()='Fiction']")).isDisplayed();
		boolean b3 = driver.findElement(By.xpath("//p[text()]//parent::a[text()='Non-fiction']")).isDisplayed();
		boolean b4 = driver.findElement(By.xpath(String.format("//p[text()]//parent::p[text()=' %s']", isbn))).isDisplayed();
		boolean b5 = driver.findElement(By.xpath(String.format("//p[text()]//parent::p[text()=' %s']", bookSum))).isDisplayed();
		Assert.assertTrue(b0);
		Assert.assertTrue(b1);
		Assert.assertTrue(b2);
		Assert.assertTrue(b3);
		Assert.assertTrue(b4);
		Assert.assertTrue(b5);

	}
	@Test(priority=2) 
	public void FindTheBook() {
		driver.get("https://raamatukogu.herokuapp.com/catalog/books");
		//FIND + SELECT BOOK FROM THE LIST
		driver.findElement(By.linkText(bookName)).click();
		//CHECK THAT THIS IS CORRECT BOOK
		boolean b0 = driver.findElement(By.xpath(String.format("//h1[text()='Title: %s']", bookName))).isDisplayed();
		boolean b1 = driver.findElement(By.xpath(String.format("//p[text()]//parent::a[text()='%s']", randomAuthor))).isDisplayed();
		boolean b2 = driver.findElement(By.xpath("//p[text()]//parent::a[text()='Fiction']")).isDisplayed();
		boolean b3 = driver.findElement(By.xpath("//p[text()]//parent::a[text()='Non-fiction']")).isDisplayed();
		boolean b4 = driver.findElement(By.xpath(String.format("//p[text()]//parent::p[text()=' %s']", isbn))).isDisplayed();
		boolean b5 = driver.findElement(By.xpath(String.format("//p[text()]//parent::p[text()=' %s']", bookSum))).isDisplayed();
		Assert.assertTrue(b0);
		Assert.assertTrue(b1);
		Assert.assertTrue(b2);
		Assert.assertTrue(b3);
		Assert.assertTrue(b4);
		Assert.assertTrue(b5);
	  
	  }
	  
	
	
	@Test(priority = 3/* ,invocationCount = 10 */) 
	public void RemoveTheBook() {
		driver.get("https://raamatukogu.herokuapp.com/catalog/books");
		driver.findElement(By.linkText(bookName)).click();
		driver.findElement(By.linkText("Delete Book")).click(); 
		WebElement delete = driver.findElement(By.xpath("//button[contains(text(),'Delete')]"));
		delete.click(); 
		boolean b = driver.findElement(By.xpath("//h1[text()='Book List']")).isDisplayed();
		Assert.assertTrue(b);
	  
	  }
	

	@AfterMethod
	public void tearDown() {
		driver.quit();

	}

}
