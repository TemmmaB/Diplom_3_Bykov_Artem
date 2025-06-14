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
public class UserRegistrationTests extends BaseTest {
    private final DataTest user = new DataTest();
    private String token;
    private final String browserType;
    private final String password = "12345";


    public UserRegistrationTests(String browserType) {
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
        initDriver(browserType); // инициализируем драйверы бразеров
    }

    @Test
    @Step("Успешная регистрация")
    public void testRegistrationSuccess() {
        registerPage.openRegisterUrl();
        registerPage.inputName(user.getName());
        registerPage.inputEmail(user.getEmail());
        registerPage.inputPassword(user.getPassword());
        registerPage.registerButtonClick();
        token = registerPage.loginViaApi(user);// Проверяем созданного пользователя через авторизацию
    }

    @Test
    @Step("Тест проверки некорректного пароля")
    public void testIncorrectPasswordError(){
        registerPage.openRegisterUrl();
        registerPage.inputName(user.getName());
        registerPage.inputEmail(user.getEmail());
        registerPage.inputPassword(password);
        registerPage.clickEmail();
        Assert.assertEquals(
                "Проверка ошибки, если пароль менее 6 символов",
                "Некорректный пароль",
                registerPage.errorIncorrectPassword()
        );
    }
     @After
    public void tearDown() {
        driver.quit();//удалили пользователя которым залогинились
        if (token != null) {
            UserApiService.deleteUser(token)//удалили созданного пользователя
                    .then()
                    .statusCode(202);
        }
    }
}
