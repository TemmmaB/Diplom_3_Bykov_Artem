import UtilsAndApi.BaseTest;
import UtilsAndApi.DataTest;
import UtilsAndApi.UserApiService;
import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class LoginTests extends BaseTest {

    private final String browserType;

    public LoginTests(String browserType) {
        this.browserType = browserType;
    }

    @Parameterized.Parameters //Запускаем тесты сначала в хроме потом в яндекс браузере
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
                {"chrome"},
                {"yandex"},
        });
    }

    @Before
    public void setUp() {
        initDriver(browserType);
    }

    @Test
    @Step("Войти по кнопке - Войти в аккаунт на главной")
    public void testEnterButtonAccount() throws InterruptedException {
        mainPage.createUserViaApi(); //создали пользователя
        openBaseUrl();
        mainPage.clickEnterInAccount();
        loginPage.inputEmail(mainPage.getCreatedUserEmail());
        loginPage.inputPassword(mainPage.getCreatedUserPassword());
        loginPage.enterButtonClick();
        mainPage.clickEnterLK();//перешли в лк c главной страницы
        Assert.assertEquals(
                "Проверяем, что созданный пользователь отображается в интерфейсе после авторизации",
                mainPage.getCreatedUserEmail(),
                PersonalAccountPage.getPersonalAccountLoginText()
        );
    }

    @Test
    @Step("вход через кнопку «Личный кабинет»")
    public void testEnterLKAccount() throws InterruptedException {
        mainPage.createUserViaApi(); //создали пользователя
        openBaseUrl();
        mainPage.clickEnterLK();
        loginPage.inputEmail(mainPage.getCreatedUserEmail());
        loginPage.inputPassword(mainPage.getCreatedUserPassword());
        loginPage.enterButtonClick();
        mainPage.clickEnterLK();//перешли в лк c главной страницы
        Assert.assertEquals(
                "Проверяем, что созданный пользователь отображается в интерфейсе после авторизации",
                mainPage.getCreatedUserEmail(),
                PersonalAccountPage.getPersonalAccountLoginText()
        );
    }

    @Test
    @Step("вход через кнопку войти на форме «Регистрация»")
    public void testEnterRegisterAccount() throws InterruptedException {
        mainPage.createUserViaApi(); //создали пользователя
        registerPage.openRegisterUrl();//перешли на url регистрации
        registerPage.clickEnterRegisterButton();//Кликаем на кнопку войти на форме регистрации
        loginPage.inputEmail(mainPage.getCreatedUserEmail());
        loginPage.inputPassword(mainPage.getCreatedUserPassword());
        loginPage.enterButtonClick();
        mainPage.clickEnterLK();//перешли в лк c главной страницы
        Assert.assertEquals(
                "Проверяем, что созданный пользователь отображается в интерфейсе после авторизации",
                mainPage.getCreatedUserEmail(),
                PersonalAccountPage.getPersonalAccountLoginText()
        );
    }

    @Test
    @Step("вход через кнопку войти на форме «Востановления пароля»")
    public void testEnterRecoveryAccount() throws InterruptedException {
        mainPage.createUserViaApi(); //создали пользователя
        recoveryPage.openForgotUrl();//перешли на url востановления пароля
        recoveryPage.clickEnterForgotButton();//Кликаем на кнопку войти на форме востановления пароля
        loginPage.inputEmail(mainPage.getCreatedUserEmail());
        loginPage.inputPassword(mainPage.getCreatedUserPassword());
        loginPage.enterButtonClick();
        mainPage.clickEnterLK();//перешли в лк c главной страницы
        Assert.assertEquals(
                "Проверяем, что созданный пользователь отображается в интерфейсе после авторизации",
                mainPage.getCreatedUserEmail(),
                PersonalAccountPage.getPersonalAccountLoginText()
        );
    }


    @After
    public void tearDown() {
        driver.quit();//удалили пользователя которого создали через RestApi
        if (mainPage.getCurrentUserToken() != null) {
            UserApiService.deleteUser(mainPage.getCurrentUserToken())//удалили созданного пользователя
                    .then()
                    .statusCode(202);
        }
    }
}
