package AllPages;
import UtilsAndApi.BaseTest;
import UtilsAndApi.UserApiService;
import UtilsAndApi.DataTest;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import praktikum.CreateUser;

import java.time.Duration;
import io.restassured.response.Response;

import static org.junit.Assert.assertEquals;



public class MainPage extends BaseTest {
    private WebDriver webDriver;
    private final By enterAccount = By.xpath(".//button[text()='Войти в аккаунт']");
    private final By enterLK = By.xpath(".//p[text()='Личный Кабинет']");
    private final By constructorButton = By.xpath(".//p[text()='Конструктор']");
    private final By logoBurgers = By.xpath(".//div[@class ='AppHeader_header__logo__2D0X2']/descendant::a");
    private String currentUserToken;
    private CreateUser createdUser;
    private final By bunsTab = By.xpath("//span[text()='Булки']");
    private final By saucesTab = By.xpath("//span[text()='Соусы']");
    private final By fillingsTab = By.xpath("//span[text()='Начинки']");
    private final By activeTab = By.xpath("//div[contains(@class, 'tab_tab_type_current')]/span");

    public MainPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    protected WebElement waitForElementToBeClickable(By locator) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(15));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    @Step("Кликнуть по кнопке войти в аккаунт")
    public MainPage clickEnterInAccount() {
        WebElement element = waitForElementToBeClickable(enterAccount);
        element.click();
        return this;
    }
    @Step("Кликнуть по кнопке войти в аккаунт")
    public MainPage clickLogoBurgers() {
        WebElement element = waitForElementToBeClickable(logoBurgers);
        element.click();
        return this;
    }

    @Step("Кликнуть по кнопке конструктор")
    public MainPage clickConstructorButton() {
        WebElement element = waitForElementToBeClickable(constructorButton);
        element.click();
        return this;
    }

    @Step("Проверить что после перехода мы на главной странице")
    public String verifyMainPageUrlConstructor() {
        WebElement element = waitForElementToBeClickable(constructorButton);
        String actualUrl = webDriver.getCurrentUrl();
        assertEquals("Ссылка ведёт не на главную страницу после клика на кнопку конструктор", "https://stellarburgers.nomoreparties.site/", actualUrl);
        return actualUrl;
    }

    @Step("Проверить что после перехода мы на главной странице")
    public String verifyMainPageUrlBurger() {
        WebElement element = waitForElementToBeClickable(constructorButton);
        String actualUrl = webDriver.getCurrentUrl();
        assertEquals("Ссылка ведёт не на главную страницу после клика на логотип", "https://stellarburgers.nomoreparties.site/", actualUrl);
        return actualUrl;
    }

    @Step("Кликнуть по кнопке личный кабинет")
    public MainPage clickEnterLK() {
        WebElement element = waitForElementToBeClickable(enterLK);
        element.click();
        return this;
    }

    @Step("Создание нового пользователя через API")
    public MainPage createUserViaApi() {
        DataTest data = new DataTest();
        createdUser = new CreateUser(data.getEmail(), data.getPassword(), data.getName());
        Response response = UserApiService.createUser(createdUser);
        response.then().statusCode(200);
        Response loginResponse = UserApiService.login(createdUser.getEmail(), createdUser.getPassword());
        loginResponse.then().statusCode(200);
        currentUserToken = UserApiService.getAccessToken(loginResponse);
        return this;
    }

    @Step("Клик по вкладке 'Булки'")
    public MainPage clickBunsTab() {
        WebElement element = waitForElementToBeClickable(bunsTab);
        element.click();
        return this;
    }

    @Step("Клик по вкладке 'Соусы'")
    public MainPage clickSaucesTab() {
        WebElement element = waitForElementToBeClickable(saucesTab);
        element.click();
        return this;
    }

    @Step("Клик по вкладке 'Начинки'")
    public MainPage clickFillingsTab() {
        WebElement element = waitForElementToBeClickable(fillingsTab);
        element.click();
        return this;
    }

    @Step("Проверка, что активна вкладка 'Булки'")
    public boolean isBunsTabActive() {
        WebElement element = webDriver.findElement(activeTab);
        return element.getText().equals("Булки");
    }


    @Step("Проверка, что активна вкладка 'Соусы'")
    public boolean isSaucesTabActive() {
        WebElement element = webDriver.findElement(activeTab);
        return element.getText().equals("Соусы");
    }


    @Step("Проверка, что активна вкладка 'Начинки'")
    public boolean isFillingsTabActive() {
        WebElement element = webDriver.findElement(activeTab);
        return element.getText().equals("Начинки");
    }

    @Step("Проверка, что активна вкладка 'Булки'")
    public void assertBunsTabIsActive() {
        WebElement element = webDriver.findElement(activeTab);
        assertEquals("Вкладка 'Соусы' не активна", "Булки", element.getText());
    }

    @Step("Проверка, что активна вкладка 'Соусы'")
    public void assertSaucesTabIsActive() {
        WebElement element = webDriver.findElement(activeTab);
        assertEquals("Вкладка 'Соусы' не активна", "Соусы", element.getText());
    }

    @Step("Проверка, что активна вкладка 'Начинки'")
    public void assertFillingsTabIsActive() {
        WebElement element = webDriver.findElement(activeTab);
        assertEquals("Вкладка 'Соусы' не активна", "Начинки", element.getText());
    }

    public String getCurrentUserToken() {
        return currentUserToken;
    }

    public String getCreatedUserEmail() {
        return createdUser != null ? createdUser.getEmail() : null;
    }

    public String getCreatedUserPassword() {
        return createdUser != null ? createdUser.getPassword() : null;
    }


}