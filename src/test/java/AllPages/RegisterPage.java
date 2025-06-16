package AllPages;

import UtilsAndApi.BaseTest;
import UtilsAndApi.UserApiService;
import UtilsAndApi.DataTest;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.restassured.response.Response;

public class RegisterPage extends BaseTest {
    private WebDriver webDriver;
    private static final String Register_URL = "https://stellarburgers.nomoreparties.site/register";
    private final By nameTextEdit = By.xpath(".//label[text()='Имя']/following-sibling::input");
    private final By emailTextEdit = By.xpath(".//label[text()='Email']/following-sibling::input");
    private final By passwordTextEdit = By.xpath(".//label[text()='Пароль']/following-sibling::input");
    private final By registerButton = By.xpath(".//button[text()='Зарегистрироваться']");
    private final By errorIncorrectPassword = By.xpath(".//p[text()='Некорректный пароль']");
    private final By linkEnter = By.xpath(".//a[text()='Войти']");

    public RegisterPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Step("Нажать войти на форме регистрации")
    public RegisterPage clickEnterRegisterButton() {
        webDriver.findElement(linkEnter).click();
        return this;
    }

    @Step("Открыть страницу регистрации")
    public void openRegisterUrl() {
        webDriver.get(Register_URL);
    }
    @Step("Ввести имя")
    public RegisterPage inputName(String name) {
        webDriver.findElement(nameTextEdit).sendKeys(name);
        return this;
    }
    @Step("Ввести email")
    public RegisterPage inputEmail(String email) {
        webDriver.findElement(emailTextEdit).sendKeys(email);
        return this;
    }
    @Step("Кликнуть на email")
    public RegisterPage clickEmail() {
        webDriver.findElement(emailTextEdit).click();
        return this;
    }

    @Step("Ввести пароль")
    public RegisterPage inputPassword(String password) {
        webDriver.findElement(passwordTextEdit).sendKeys(password);
        return this;
    }
    @Step("Нажать на кнопку зарегестрироватся")
    public RegisterPage registerButtonClick() {
        webDriver.findElement(registerButton).click();
        return this;
    }
    @Step("Получить текст сообщения о некоректности пароля")
    public String errorIncorrectPassword() {
        String incorrectPassword = webDriver.findElement(errorIncorrectPassword).getText();
        return incorrectPassword;
    }
    @Step("Логин через API созданным пользователем")
    public String loginViaApi(DataTest user) {
        Response response = UserApiService.login(user.getEmail(), user.getPassword());
        response.then().statusCode(200);
        return UserApiService.getAccessToken(response);
    }
}
