import com.github.javafaker.Faker;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class ProjektKoncowy {

    @Test
    public void shouldVerifyNegativeLoginMissingLogin() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://34.171.101.114/");
        WebElement goToAccountPage = driver.findElement(By.id("menu-item-125"));
        goToAccountPage.click();
        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys("!kinworeik!");
        WebElement loginButton = driver.findElement(By.cssSelector("button[name='login']"));
        loginButton.click();
        WebElement missedLoginError = driver.findElement(By.className("woocommerce-error"));
        String missedLoginErrorTextVerification = "Błąd: Nazwa użytkownika jest wymagana.";
        Assertions.assertEquals(missedLoginErrorTextVerification, missedLoginError.getText());
        driver.quit();
    }

    @Test
    public void shouldVerifyNegativeLoginMissingPassword() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://34.171.101.114/");
        WebElement goToAccountPage = driver.findElement(By.id("menu-item-125"));
        goToAccountPage.click();
        WebElement userNameInput = driver.findElement(By.id("username"));
        userNameInput.sendKeys("kierownik");
        WebElement loginButton = driver.findElement(By.cssSelector("button[name='login']"));
        loginButton.click();
        WebElement missedPasswordError = driver.findElement(By.className("woocommerce-error"));
        String missedPasswordErrorTextVerification = "Błąd: pole hasła jest puste.";
        Assertions.assertEquals(missedPasswordErrorTextVerification, missedPasswordError.getText());
        driver.quit();
    }

    @Test
    public void shouldVerifyPositiveRegistrationOfNewUser() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://34.171.101.114/");
        WebElement goToRegistrationPage = driver.findElement(By.id("menu-item-146"));
        goToRegistrationPage.click();
        WebElement userNameInput = driver.findElement(By.cssSelector("input[name='user_login']"));
        WebElement userPasswordInput = driver.findElement(By.cssSelector("input[name='user_pass']"));
        WebElement userEmailInput = driver.findElement(By.cssSelector("input[name='user_email']"));
        WebElement userConfirmPasswordInput = driver.findElement(By.cssSelector("input[name='user_confirm_password']"));

        Faker faker = new Faker(new Locale("pl-PL"));
        String userName = faker.name().username();
        String password = faker.internet().password();
        String password2 = password;
        String emailAddress = faker.internet().emailAddress();

        userNameInput.sendKeys(userName);
        userPasswordInput.sendKeys(password);
        userEmailInput.sendKeys(emailAddress);
        userConfirmPasswordInput.sendKeys(password2);

        WebElement submit = driver.findElement(By.xpath("//button[contains(.,'Submit')]"));
        submit.click();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        String userRegistered = driver.findElement(By.id("ur-submit-message-node")).getText();
        Assertions.assertEquals("User successfully registered.", userRegistered);

        WebElement goToAccountPage = driver.findElement(By.id("menu-item-125"));
        goToAccountPage.click();

        WebElement userNameInput2 = driver.findElement(By.id("username"));
        userNameInput2.sendKeys(userName);
        WebElement passwordInput2 = driver.findElement(By.id("password"));
        passwordInput2.sendKeys(password);

        WebElement loginButton2 = driver.findElement(By.cssSelector("button[name='login']"));
        loginButton2.click();

        WebElement dashboard = driver.findElement
                (By.className("woocommerce-MyAccount-navigation-link--dashboard"));
        Assertions.assertEquals("Kokpit", dashboard.getText());

        driver.quit();
    }

    @Test
    public void shouldVerifyElementsOnMainPage() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://34.171.101.114/");
        WebElement logoOnMainPage = driver.findElement(By.xpath("//a[contains(.,'Metal Shop')]"));
        Assertions.assertEquals("Metal Shop", logoOnMainPage.getText());

        WebElement searchBarOnMainPage = driver.findElement(By.id("woocommerce-product-search-field-0"));
        Assertions.assertTrue(searchBarOnMainPage.isDisplayed());
        driver.quit();
    }

    @Test
    public void shouldVerifyElementsOnAccountPage() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://34.171.101.114/");
        WebElement goToAccountPage = driver.findElement(By.id("menu-item-125"));
        goToAccountPage.click();

        WebElement logoOnAccountPage = driver.findElement(By.xpath("//a[contains(.,'Metal Shop')]"));
        Assertions.assertEquals("Metal Shop", logoOnAccountPage.getText());

        WebElement searchBarOnAccountPage = driver.findElement(By.id("woocommerce-product-search-field-0"));
        Assertions.assertTrue(searchBarOnAccountPage.isDisplayed());
        driver.quit();
    }

    @Test
    public void shouldVerifyGoToContactPage() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://34.171.101.114/");
        WebElement goToContactPage = driver.findElement(By.id("menu-item-132"));
        goToContactPage.click();

        WebElement contact = driver.findElement(By.className("entry-title"));
        Assertions.assertEquals("Kontakt", contact.getText());
        driver.quit();
    }

    @Test
    public void shouldVerifyGoToMainPageFromAccountPage() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://34.171.101.114/");
        WebElement goToAccountPage = driver.findElement(By.id("menu-item-125"));
        goToAccountPage.click();
        WebElement goToMainPage = driver.findElement(By.id("menu-item-124"));
        goToMainPage.click();

        WebElement shop = driver.findElement(By.className("woocommerce-products-header__title"));
        Assertions.assertEquals("Sklep", shop.getText());
        driver.quit();
    }

    @Test
    public void shouldVerifySendMessageOnContactPage() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://34.171.101.114/");
        WebElement goToContactPage = driver.findElement(By.id("menu-item-132"));
        goToContactPage.click();

        WebElement yourName = driver.findElement(By.name("your-name"));
        WebElement yourEmail = driver.findElement(By.name("your-email"));
        WebElement yourMessage = driver.findElement(By.name("your-message"));

        Faker faker = new Faker(new Locale("pl-PL"));
        String userName = faker.name().fullName();
        String emailAddress = faker.internet().emailAddress();

        String message = "This is a random message.";

        yourName.sendKeys(userName);
        yourEmail.sendKeys(emailAddress);
        yourMessage.sendKeys(message);

        WebElement send = driver.findElement(By.xpath("//input[@value='Wyślij']"));
        send.click();

        Wait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        WebElement messageSentInfo = driver.findElement(By.className("wpcf7-response-output"));
        wait.until(ExpectedConditions.visibilityOf(messageSentInfo));

        String messageSent = driver.findElement(By.className("wpcf7-response-output")).getText();
        Assertions.assertEquals("Twoja wiadomość została wysłana. Dziękujemy!", messageSent);
        driver.quit();
    }

    @Test
    public void addProductsToTheCart() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://34.171.101.114/");
        WebElement cart = driver.findElement(By.id("site-header-cart"));
        cart.click();

        String emptyCart = driver.findElement(By.className("cart-empty")).getText();
        Assertions.assertEquals("Twój koszyk aktualnie jest pusty.", emptyCart);

        WebElement goToMainPage = driver.findElement(By.id("menu-item-124"));
        goToMainPage.click();

        WebElement addToCartCoin24 = driver.findElement
                (By.xpath("//a[contains(@href,'?add-to-cart=24')]"));
        addToCartCoin24.click();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement productInACart = driver.findElement
                (By.xpath("//span[@class='count'][contains(text(),'1 Produkt')]"));

        String cartElement = productInACart.getText();
        Assertions.assertEquals("1 Produkt", cartElement);
        driver.quit();
    }

    @Test
    public void shouldAddProductToCartAndRemoveIt (){
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://34.171.101.114/");
        WebElement addToCartCoin24 = driver.findElement
                (By.xpath("//a[contains(@href,'?add-to-cart=24')]"));
        addToCartCoin24.click();

        Wait wait = new WebDriverWait(driver, Duration.ofSeconds(12));
        wait.until(ExpectedConditions.invisibilityOfElementLocated
                (By.xpath("//span[@class='count'][contains(text(),'1 Produkt')]")));


        WebElement cart = driver.findElement(By.id("site-header-cart"));
        cart.click();

        WebElement removeProduct = driver.findElement
                (By.cssSelector("a[class='remove'][aria-label='Usuń element']"));
        removeProduct.click();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        WebElement removeProductAlert = driver.findElement
                (By.className("woocommerce-message"));
        String removeProductText = removeProductAlert.getText();
        Assertions.assertEquals("Usunięto: „Srebrna moneta 5g - UK 1980”. Cofnij?", removeProductText);
        driver.quit();
    }
}
