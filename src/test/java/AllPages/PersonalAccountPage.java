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

public class PersonalAccountPage extends BaseTest {
    private WebDriver webDriver;
    private static final String login_form = "https://stellarburgers.nomoreparties.site/login";
    private final By nameInput = By.xpath(".//label[text()='Имя']/following-sibling::input");
    private final By loginInput = By.xpath(".//label[text()='Логин']/following-sibling::input");
    private final By exitButton = By.xpath(".//button[text()='Выход']");
    private final By profileLink = By.xpath(".//a[text()='Профиль']");
    private final By historySales = By.xpath(".//a[text()='История заказов']");
    private final By enterButton = By.xpath(".//button[text()='Войти']");

    public PersonalAccountPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Step("Открыть страницу авторизации")
    public void openAutorizePersonalAccountUrl() {
        webDriver.get(login_form);
    }

    protected WebElement waitForElementToBeClickable(By locator) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(15));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected WebElement waitForElementToBeVisible(By locator) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(15));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    @Step("Нажать на кнопку войти")
    public PersonalAccountPage clickExitButton() {
        WebElement element = waitForElementToBeClickable(exitButton);
        webDriver.findElement(exitButton).click();
        return this;
    }

    @Step("Получить текст поля логин")
    public String getPersonalAccountLoginText() {
        WebElement element = waitForElementToBeVisible(loginInput);
        return element.getAttribute("value");
    }

    @Step("Получить URL текущей страницы и проверить его")
    public String verifyPersonalAccountURL() {
        WebElement element = waitForElementToBeClickable(exitButton);
        String actualUrl = webDriver.getCurrentUrl();
        assertEquals("Ссылка ведёт не на страницу личного кабинета", "https://stellarburgers.nomoreparties.site/account/profile", actualUrl);
        return actualUrl;
    }
}