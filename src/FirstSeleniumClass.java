import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

//@Slf4j
public class FirstSeleniumClass {

    WebDriver driver;

    String amazonUrl = "https://www.amazon.in";

    String flipkartUrl = "https://www.flipkart.com";
    String googleUrl = "http://google.com";
    String calculatorUrl = "file:///Users/viktoriia.prokopchuk/QA course/Workspace/SeleniumCourse/src/page/calculator.html";


    @Before
    public void init() {


      //  System.setProperty("webdriver.chrome.driver", "D:\\QA course\\Utils\\selenium\\drivers\\geckodriver.exe");
//			driver = new FirefoxDriver();

        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }


    @Test
    public void test1(){
        driver.get(calculatorUrl);
    }

    @After
    public void close() {
        driver.close();
        driver.quit();
    }

    @Test
    public void testAddition() throws InterruptedException {
        driver.get(calculatorUrl);
        WebElement parameterA = driver.findElement(By.id("a"));
        parameterA.sendKeys("10");



       // WebElement parameterB = driver.findElement(By.id("b"));
        WebElement parameterB = driver.findElement(By.xpath("//*[@id=\"b\"]"));
        parameterB.sendKeys("20");

        WebElement operation = driver.findElement(By.id("operation"));
        operation.sendKeys("+");

        WebElement buttonCalculate = driver.findElement(By.name("calc"));
        buttonCalculate.click();

        assertTrue(driver.findElement(By.name("result")).getAttribute("value").equals("30"));

//
//        driver.findElement(By.id("a")).sendKeys("1");
//        driver.findElement(By.id("b")).sendKeys("2");
//        driver.findElement(By.id("operation")).sendKeys("+");
//        driver.findElement(By.name("calc")).click();


       // Thread.sleep(2000);

    }


    @Test
    public void testFlipCart() throws InterruptedException {


        driver.get(flipkartUrl);
        //    WebElement temp = driver.switchTo().activeElement();
        driver.findElement(By.xpath("/html/body/div[2]/div/div/button")).click();
        WebElement searchBox = driver.findElement(By.name("q"));

        assertTrue(searchBox.isDisplayed());
        assertTrue(searchBox.isEnabled());

        Thread.sleep(3000);


    }


    @Test
    public void findJonnyDepp() throws InterruptedException {
        driver.get(googleUrl);
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("Jonny Depp movies");
        searchBox.submit();

        Thread.sleep(5000);

        List<WebElement> links = driver.findElements(By.className("LC20lb"));

        links.forEach((x) -> {
            System.out.println("Text " + x.getText());
            if (!x.getText().equals(""))

            assertTrue(x.getText().toLowerCase().contains("johnny depp"));

        });


    }


    @Test
    public void testAmazon() throws InterruptedException {
        driver.get(amazonUrl);

        WebElement searchString = driver.findElement(By.id("twotabsearchtextbox"));
        searchString.sendKeys("phone");
        searchString.submit();
        Thread.sleep(3000);
        // WebElement searchBtn = driver.findElement(By.cssSelector("#nav-search
        // > form > div.nav-right > div > input"));
        // searchBtn.click();

    }


}
