package AllPages;

import UtilsAndApi.BaseTest;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import static org.junit.Assert.assertEquals;

public class LoginPage extends BaseTest {
    private WebDriver webDriver;
    private final By emailInput = By.xpath(".//label[text()='Email']/following-sibling::input");
    private final By passwordInput = By.xpath(".//label[text()='Пароль']/following-sibling::input");
    private final By enterButton = By.xpath(".//button[text()='Войти']");

    protected WebElement waitForElementToBeClickable(By locator) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(15));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public LoginPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }
    @Step("Ввести email")
    public LoginPage inputEmail(String email) {
        webDriver.findElement(emailInput).sendKeys(email);
        return this;
    }

    @Step("Ввести пароль")
    public LoginPage inputPassword(String password) {
        webDriver.findElement(passwordInput).sendKeys(password);
        return this;
    }

    @Step("Нажать на кнопку войти")
    public LoginPage enterButtonClick() {
        webDriver.findElement(enterButton).click();
        return this;
    }
    @Step("Проверить что мы на странице авторизации после выхода из ЛК")
    public String verifyExitLkURL() {
        WebElement element = waitForElementToBeClickable(enterButton);
        String actualUrl = webDriver.getCurrentUrl();
        assertEquals("Ссылка ведёт не на форму авторизации", "https://stellarburgers.nomoreparties.site/login", actualUrl);
        return actualUrl;
    }
}
