package stepDefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertEquals;

public class Steps {

    WebDriver driver;
    String url = "http://demo.guru99.com/v4";


    @Given("Open chrome and launch the application")
    public void open_chrome_and_launch_the_application() throws Throwable {

        //TODO 1st logic part
        System.out.println("This Step open Chrome and launch the application.");

        //TODO 2st logic part
      //  System.setProperty("webdriver.gecko.driver", "E://Selenium//Selenium_Jars//geckodriver.exe");
        driver= new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(url);
    }


//    @When("Enter the username and password")
//    public void enter_the_Username_and_Password() throws Throwable {
//        //TODO 1st logic part
//        System.out.println("This step enter the Username and Password on the login page.");
//
//        //TODO 2st logic part
//        driver.findElement(By.name("uid")).sendKeys("username12");
//        driver.findElement(By.name("password")).sendKeys("password12");
//    }


    @When("Enter the username and password {string}{string}")
    public void enter_the_username_and_password(String username,String password) throws Throwable
    {
        System.out.println("This step enter the username and password on the login page.");
        driver.findElement(By.name("uid")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
    }

    @Then("Reset the credential")
    public void reset_the_credential() throws Throwable {
        //TODO 1st logic part
        System.out.println("This step click on the Reset button.");

        //TODO 2st logic part
        driver.findElement(By.name("btnReset")).click();
        assertEquals(driver.findElement(By.name("uid")).getText(), "");
        assertEquals(driver.findElement(By.name("password")).getText(), "");
        driver.close();
    }

}