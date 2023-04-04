package StepDefPack;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.*;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.edge.EdgeDriver;

import java.time.Duration;
import java.util.Random;

public class MyStepdefs {

    private WebDriver driver;
    private WebDriverWait wait;

    // Väljer webbläsare och navigerar till Mailchimp.com. Wait väntar 20sek.
    @Given("I choose a {string} and brows to Mailchimp.com")

    public void iChooseA(String browser) {

        if (browser.equalsIgnoreCase("Chrome")) {
            System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("Edge")) {
            System.setProperty("webdriver.edge.driver", "C:\\Selenium\\msedgedriver.exe");
            driver = new EdgeDriver();
        }
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.get("https://login.mailchimp.com/signup/");
        driver.manage().window().maximize();
    }

    // Skriver in email adress
    @Given("I enter a {string} adress")
    public void iEnterAAdress(String email) {

        if (email.equalsIgnoreCase("123")) {
            sendKeys(By.id("email"), email);
        }
        if (email.equalsIgnoreCase("yep1@yep.com")) {
            sendKeys(By.id("email"), "yep1@yep.com");

        } else {
            Random randomEmail = new Random();
            int randomCombination = randomEmail.nextInt(1000);

            sendKeys(By.id("email"), randomCombination + email);
        }
    }

    // Skriver användarnamn och lösenord
    @And("I type a {string} and a {string}")
    public void iTypeAAndA(String userName, String passWord) {

        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));

        WebElement clickUsername = driver.findElement(By.id("new_username"));
        clickUsername.click();
        clickUsername.clear();

        if (userName.equals("Miguel")) {
            String randomUsername = getLongRandomUsername(95) + userName;

            sendKeys(By.id("new_username"), randomUsername);
            sendClick(By.name("password"));
            sendKeys(By.id("new_password"), passWord);

        }

        if (userName.equals("lol1@lol.com")) {
            sendKeys(By.id("new_username"), "lol1@lol.com");

            sendKeys(By.id("new_password"), passWord);

        } else {

            Random randomUserName = new Random();
            int randomCombination = randomUserName.nextInt(1000);

            sendKeys(By.id("new_username"), randomCombination + userName);

            sendKeys(By.id("new_password"), passWord);
        }
    }

    //Klickar på sign up och säkerställer så rätt information visas.
    @Then("I click the sign up button and a new user is {string}")
    public void aNewUserIs(String userCreated) throws InterruptedException {

        driver.findElement(By.id("create-account-enabled")).click();


        if (userCreated.equalsIgnoreCase("work")) {

            sendClick(By.id("create-account-enabled"));

            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#signup-success > div > div.content.line.section > section > div > h1"))).getText();

            String expected = "Check your email";
            String actual = driver.findElement(By.cssSelector("#signup-success > div > div.content.line.section > section > div > h1")).getText();

            assertEquals(expected, actual);
        }

        if (userCreated.equalsIgnoreCase("no")) {

            sendClick(By.id("create-account-enabled"));

            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("invalid-error"))).getAttribute("value");

            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#signup-form > fieldset > div:nth-child(2) > div > span")));

            String expected = "Enter a value less than 100 characters long";
            String actual = driver.findElement(By.className("invalid-error")).getText();

            assertEquals(expected, actual);
        }

        if (userCreated.equalsIgnoreCase("fail")) {

            sendClick(By.id("create-account-enabled"));

            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("invalid-error"))).getAttribute("value");

            String expected = "Great minds think alike - someone already has this username. If it's you, log in.";
            String actual = driver.findElement(By.className("invalid-error")).getText();

            assertEquals(expected, actual);
        }
        if (userCreated.equalsIgnoreCase("nope")) {

            sendClick(By.id("create-account-enabled"));

            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("invalid-error"))).getText();

            String expected = "An email address must contain a single @.";
            String actual = driver.findElement(By.className("invalid-error")).getText();

            assertEquals(expected, actual);
        }

    }

    // Stänger ner alla fönster
    @After
    public void tearDown() {
        driver.close();
        driver.quit();
    }

    // Hämtar random bokstäver för att testa ett användarnamn med fler är 100 tecken.
    public String getLongRandomUsername(int length) {
        String string = "";
        String letters = "abcdefghijklmnopqrstuvwxyz1234567890";

        for (int i = 0; i < length; i++) {
            string += letters.charAt((int) Math.floor(Math.random() * letters.length()));
        }
        return string;

    }

    // Skickar värdet key, som kan vara lite olika saker beroende på värdet i featurefilen
    private void sendKeys(By by, String text) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
        element.sendKeys(text);

    }

    // Metod som klickar på ställen som jag väljer
    private void sendClick(By by) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
        element.click();
    }
}