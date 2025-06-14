package AllPages;
import UtilsAndApi.BaseTest;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RecoveryPage extends BaseTest {
    private WebDriver webDriver;
    private static final String Recovery_Password = "https://stellarburgers.nomoreparties.site/forgot-password";
    private final By linkEnter = By.xpath(".//a[text()='Войти']");

    public RecoveryPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Step("Открыть страницу востановления пароля")
    public void openForgotUrl() {
        webDriver.get(Recovery_Password);
    }

    @Step("Нажать войти на форме регистрации")
    public RecoveryPage clickEnterForgotButton() {
        webDriver.findElement(linkEnter).click();
        return this;
    }
}
