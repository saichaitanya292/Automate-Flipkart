package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator; // Add this import statement
import java.util.stream.Collectors;
import java.util.logging.Level;


// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases {
    ChromeDriver driver;

    /*
     * TODO: Write your tests here with testng @Test annotation. 
     * Follow `testCase01` `testCase02`... format or what is provided in instructions
     */

     @Test(enabled = true)
     public void testCase01()
     {
     WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
     driver.get("https://www.flipkart.com/");
     By search_element = By.name("q");
     WebElement search = wait.until(ExpectedConditions.visibilityOfElementLocated(search_element));
     search.sendKeys("Washing Machine");
     search.sendKeys(Keys.ENTER);
     By search_result = By.xpath("//*[@id='container']/div/div[3]/div[1]/div[2]/div[1]/div/div/div[2]/div[2]");
     WebElement search_result_element = wait.until(ExpectedConditions.visibilityOfElementLocated(search_result));
     search_result_element.click();
    By product = By.xpath("//span[@class='Y1HWO0']");
    WebElement product_element = wait.until(ExpectedConditions.visibilityOfElementLocated(product));
    List<WebElement> ratings = driver.findElements(product);
    int count = 1;
    for(WebElement rating:ratings)
    {
        if(Float.parseFloat(rating.getText()) <= 4)
        {
            count++;   
        }
    }
    System.out.println("Number of products with rating less than or equal to 4 is: "+count);
}

@Test(enabled = true)
public void testCase02()
{

    WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
    driver.get("https://www.flipkart.com/");
    By search_element = By.name("q");
    WebElement search = wait.until(ExpectedConditions.visibilityOfElementLocated(search_element));
    search.sendKeys("iPhone");
    search.sendKeys(Keys.ENTER);
    //div[@class="KzDlHZ"]
    By search_result = By.xpath("//div[@class='KzDlHZ']");
    WebElement search_result_element = wait.until(ExpectedConditions.visibilityOfElementLocated(search_result));
    List<WebElement> search_results = driver.findElements(search_result);
    //div[@class="UkUFwK"]
    By product = By.xpath("//div[@class='UkUFwK']");
    WebElement product_element = wait.until(ExpectedConditions.visibilityOfElementLocated(product));
    List<WebElement> products = driver.findElements(product);
    for(WebElement element:products)
    {
        String text = element.getText().replace("% off", "");
        if (Integer.parseInt(text) >= 17) {
            System.out.println("loop");
            WebElement titleElement = element.findElement(By.xpath(".//a[@class='_2cLu-l']"));
            WebElement discountElement = element.findElement(By.xpath(".//div[@class='_3Ay6Sb']/span"));
            String title = titleElement.getText();
            String discount = discountElement.getText();
            System.out.println("Title: " + title + ", Discount: " + discount);
        }
         

    }
}
    @Test(enabled = true)   
    public void testCase03()
    {
        driver.get("https://www.flipkart.com/");
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    By search_element = By.name("q");
    WebElement search = wait.until(ExpectedConditions.visibilityOfElementLocated(search_element));
    search.sendKeys("Coffee Mug");
    search.sendKeys(Keys.ENTER);


    
    By rating_filter = By.xpath("(//*[@class=\"XqNaEv\"])[1]");
    WebElement rating_filter_element = wait.until(ExpectedConditions.visibilityOfElementLocated(rating_filter));
    rating_filter_element.click();


    By product_title = By.xpath("//*[@class=\"wjcEIp\"]");
    By product_image = By.xpath("//*[@class=\"DByuf4\"]");
    By reviews = By.xpath("//*[@class='Wphh3N']");
    driver.navigate().refresh();
    List<WebElement> review = driver.findElements(reviews);
    List<WebElement> titles = driver.findElements(product_title);
    List<WebElement> images = driver.findElements(product_image);
   
        List<Integer> ratingsList = new ArrayList<>();
        for (WebElement element : review) {
            String text = element.getText().replaceAll("[()]", "");
            ratingsList.add(Integer.parseInt(text.replace(",", "")));
        }

        List<Integer> sortedRatingsList = ratingsList.stream()
            .sorted(Comparator.reverseOrder())
            .limit(5)
            .collect(Collectors.toList());

        List<Integer> sortedRatingsIndexes = new ArrayList<>();
        for (int i = 0; i < sortedRatingsList.size(); i++) {
            int rating = sortedRatingsList.get(i);
            int index = ratingsList.indexOf(rating);
            sortedRatingsIndexes.add(index);
        }

        List<WebElement> sortedTitles = sortedRatingsIndexes.stream()
            .map(index -> titles.get(index))
            .collect(Collectors.toList());

        List<WebElement> sortedImages = sortedRatingsIndexes.stream()
            .map(index -> images.get(index))
            .collect(Collectors.toList());

        for (int i = 0; i < sortedRatingsList.size(); i++) {
            int rating = sortedRatingsList.get(i);
            WebElement titleElement = sortedTitles.get(i);
            WebElement imageElement = sortedImages.get(i);

            String title = titleElement.getText();
            String imgUrl = imageElement.getAttribute("src");

            System.out.println("Rating: " + rating);
            System.out.println("Title: " + title);
            System.out.println("Image URL: " + imgUrl);
        }
       

   
    }






     
    /*
     * Do not change the provided methods unless necessary, they will help in automation and assessment
     */
    @BeforeTest
    public void startBrowser()
    {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log"); 

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }
    
   

    @AfterTest
    public void endTest()
    {
        driver.close();
        driver.quit();

    }
}