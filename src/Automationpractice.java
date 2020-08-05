import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class Automationpractice {

    protected final Logger logger = LogManager.getLogger(getClass());

    WebDriver driver;

    String url = "http://automationpractice.com/index.php";

    private static final String ERROR_START = "\n\nElement not found in: ";
    private static final String ERROR_MIDDLE = " within: ";
    private static final String ERROR_END = " seconds!\n\n";

    @Before
    public void init() {

        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }


    @After
    public void close() {

        //   assertEquals(extractJSLogs(driver), false);
        //     driver.close();
        //     driver.quit();
    }


    @Test
    public void testValidLogin() {

        //condition
        driver.get(url);
        WebElement signInButton = driver.findElement(By.xpath("//*[@id=\"header\"]/div[2]/div/div/nav/div[1]/a"));

        //action1
        signInButton.click();

        // Check AC
        WebElement authLabel = driver.findElement(By.xpath("//*[@id=\"center_column\"]/h1"));
        assertTrue(authLabel.getText().equals("AUTHENTICATION"));
        assertTrue(authLabel.isDisplayed());

        //action2
        WebElement email = driver.findElement(By.id("email"));
        WebElement pass = driver.findElement(By.id("passwd"));
        email.sendKeys("vkodimer@gmail.com");
        pass.sendKeys("12345678");
        WebElement loginBtn = driver.findElement(By.id("SubmitLogin"));
        loginBtn.click();

        // Check AC
        WebElement signOffBtn = driver.findElement(By.xpath("//*[@id=\"header\"]/div[2]/div/div/nav/div[2]/a"));
        assertTrue(signOffBtn.isDisplayed());

        WebElement userLabel = driver.findElement(By.xpath("//*[@id=\"header\"]/div[2]/div/div/nav/div[1]/a/span"));
        assertTrue(userLabel.isDisplayed());
        assertTrue(userLabel.getText().equals("Viktoriia Prokopchuk"));

        logger.info("Valid login test passed");


    }

    @Test
    public void tesInvalidLogin() throws InterruptedException {

        //condition
        driver.get(url);
        WebElement signInButton = driver.findElement(By.xpath("//*[@id=\"header\"]/div[2]/div/div/nav/div[1]/a"));

        //action1
        signInButton.click();

        // Check AC

        Thread.sleep(3000);
        WebElement authLabel = driver.findElement(By.xpath("//*[@id=\"center_column\"]/h1"));
        assertEquals(("AUTHENTICATION"), authLabel.getText());
        assertTrue(authLabel.isDisplayed());

        //action2
        WebElement email = driver.findElement(By.id("email"));
        WebElement pass = driver.findElement(By.id("passwd"));
        email.sendKeys("vkodimer1@gmail.com");
        pass.sendKeys("12345678");
        WebElement loginBtn = driver.findElement(By.id("SubmitLogin"));
        loginBtn.click();

        // Check AC
        WebElement alertLabel = driver.findElement(By.xpath("//*[@id=\"center_column\"]/div[1]"));
        assertTrue(alertLabel.isDisplayed());
        WebElement errorMessage = alertLabel.findElement(By.tagName("li"));
        assertEquals(("Authentication failed."), errorMessage.getText());

        logger.info("Valid invalidLogin test passed");


    }

    @Test
    public void testCreateUser() throws InterruptedException {

        //condition
        driver.get(url);
        WebElement signInButton = driver.findElement(By.xpath("//*[@id=\"header\"]/div[2]/div/div/nav/div[1]/a"));

        //action1
        signInButton.click();

        // Check AC
        WebElement authLabel = driver.findElement(By.xpath("//*[@id=\"center_column\"]/h1"));
        assertEquals(("AUTHENTICATION"), authLabel.getText());
        assertTrue(authLabel.isDisplayed());

        WebElement emailCreate = driver.findElement(By.id("email_create"));

        String user = "user" + String.valueOf((int) (Math.random() * 1000000)) + "@gmail.com";
        emailCreate.sendKeys(user);

        String user1 = "user" + String.valueOf(UUID.randomUUID()) + "@gmail.com";
        System.out.println(user1);

        WebElement createBtn = driver.findElement(By.id("SubmitCreate"));
        createBtn.click();

        Thread.sleep(3000);

        // assertEquals(driver.findElement(By.xpath("//*[@id=\"noSlide\"]/h1")), "CREATE AN ACCOUNT");

        driver.findElement(By.id("firstname")).click();

        WebElement state = driver.findElement(By.id("id_state"));
        state.findElements(By.tagName("option")).get(3).click();


        logger.info("Create user test passed");

        //TODO To finish


    }

    @Test
    public void testCreateAccountInna() throws InterruptedException {

        String user = "user" + String.valueOf((int) (Math.random() * 1000000)) + "@gmail.com";


        driver.get(url);
        WebElement signInButton = driver.findElement(By.xpath("//*[@id=\"header\"]/div[2]/div/div/nav/div[1]/a"));
        signInButton.click();
        WebElement email = driver.findElement(By.id("email_create"));
        //testing for an invalid email
        // assertion error??
        email.sendKeys("wrongEmail");

        WebElement createAccountButton = driver.findElement(By.id("SubmitCreate"));
        createAccountButton.click();
        WebElement createError = driver.findElement(By.xpath("//*[@id=\"create_account_error\"]/ol/li"));
        assertTrue(createError.isDisplayed());
        email.clear();

        //testing for a valid email
        email.sendKeys(user);
        createAccountButton.click();
        WebElement firstName = driver.findElement(By.id("customer_firstname"));
        firstName.sendKeys("some name");
        WebElement lastName = driver.findElement(By.id("customer_lastname"));
        lastName.sendKeys("some last name");
        WebElement customerEmail = driver.findElement(By.id("email"));
        assertEquals(user, customerEmail.getAttribute("value"));
        WebElement password = driver.findElement(By.id("passwd"));
        password.sendKeys("some_pass");
        WebElement address = driver.findElement(By.id("address1"));
        address.sendKeys("some address");
        WebElement city = driver.findElement(By.id("city"));
        city.sendKeys("some city");
        WebElement state = driver.findElement(By.id("id_state"));
        //Custom wait example
      //  state.click();
         List<WebElement> stateOptions = state.findElements(By.tagName("option"));
         waitForListIsLoaded(3, stateOptions);

        state.findElements(By.tagName("option")).get(3).click();// Arizona
        WebElement zip = driver.findElement(By.id("postcode"));
        zip.sendKeys("12345");
        WebElement country = driver.findElement(By.id("id_country"));
        country.findElements(By.tagName("option")).get(1).click();// United States
        WebElement phone = driver.findElement(By.id("phone_mobile"));
        phone.sendKeys("012345");
        WebElement alias = driver.findElement(By.id("alias"));
        alias.sendKeys("alias");
        WebElement register = driver.findElement(By.id("submitAccount"));
        register.click();
        WebElement signOffBtn = driver.findElement(By.xpath("//*[@id=\"header\"]/div[2]/div/div/nav/div[2]/a"));
        assertTrue(signOffBtn.isDisplayed());
        WebElement userLabel = driver.findElement(By.xpath("//*[@id=\"header\"]/div[2]/div/div/nav/div[1]/a/span"));
        assertTrue(userLabel.isDisplayed());
        assertEquals("some name some last name", userLabel.getText());
        logger.info("User registration test passed");
        // testing for the existing user

        signOffBtn.click();
        WebElement email1 = driver.findElement(By.id("email_create"));
        email1.sendKeys(user);
        WebElement createAccountButton1 = driver.findElement(By.id("SubmitCreate"));
        createAccountButton1.click();
        WebElement createError1 = driver.findElement(By.xpath("//*[@id=\"create_account_error\"]/ol/li"));
        assertTrue(createError1.isDisplayed());
        logger.info("Existing user registration test passed");
    }


    //TODO To write test for Category selection (via checkBox and via Button)
    //TODO Infra with WebDriverRule and WebDriverTest

    @Test
    public void testErrorInConsoleSaveScreen() {
        driver.get(url);
        driver.findElement((By.xpath("//*[@id=\"htmlcontent_home\"]/ul/li[2]/a/img"))).click();
        try {
            assertEquals(extractJSLogs(), false);
        } catch (AssertionError error) {
            takeScreenShot(new Object() {
            }.getClass().getEnclosingMethod().getName());
            fail();
        }
    }


    @Test
    public void testErrorInConsoleSaveScreen1() {
        driver.get(url);
        driver.findElement((By.xpath("//*[@id=\"htmlcontent_home\"]/ul/li[2]/a/img"))).click();

        assertEquals(extractJSLogs(), false);

    }


    private boolean extractJSLogs() {
        LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
        System.out.println("\n\nThe following errors were found in the console: \n");

        for (LogEntry entry : logEntries) {

            if (entry.getLevel().equals(Level.SEVERE)) {
                System.out.println(new Date(entry.getTimestamp()) + " " + entry.getLevel() +
                        " " + entry.getMessage());
                return true;
            }

        }

        return false;
    }


    private void takeScreenShot(String methodName) {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File("src/screenshots/" + LocalDateTime.now().toString().substring(0, 19).replace(":", "-") + "_" + methodName + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void waitForListIsLoaded(int seconds, List<WebElement> list) {
        WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, seconds)
                .ignoring(StaleElementReferenceException.class);
        wait.withMessage(ERROR_START + list + ERROR_MIDDLE + seconds + ERROR_END);
        wait.until((ExpectedCondition<Boolean>) webDriver -> list != null && list.size() > 1);
    }

}
