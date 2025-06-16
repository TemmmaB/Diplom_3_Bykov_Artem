import UtilsAndApi.BaseTest;
import UtilsAndApi.DataTest;
import UtilsAndApi.UserApiService;
import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class PageTransitionTests extends BaseTest {
    private final String browserType;
    public PageTransitionTests (String browserType) {
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
    @Step("Проверка перехода по клику на «Личный кабинет»")
    public void testLKverify() throws InterruptedException {
        mainPage.createUserViaApi(); //создали пользователя
        PersonalAccountPage.openAutorizePersonalAccountUrl(); //перешли на страницу авторизации
        loginPage.inputEmail(mainPage.getCreatedUserEmail());
        loginPage.inputPassword(mainPage.getCreatedUserPassword());
        loginPage.enterButtonClick();
        mainPage.clickEnterLK();//перешли в лк c главной страницы
        PersonalAccountPage.verifyPersonalAccountURL();// Проверили что URL это URL личного кабинета
    }

    @Test
    @Step("Проверить, что при клике на «Конструктор» из ЛК происходит переход на главную страницу")
    public void testConstructorButtonRedirectsToMainPage() {
        mainPage.createUserViaApi(); // Авторизация
        PersonalAccountPage.openAutorizePersonalAccountUrl(); //перешли на страницу авторизации
        loginPage.inputEmail(mainPage.getCreatedUserEmail());//ввод Email
        loginPage.inputPassword(mainPage.getCreatedUserPassword());//Ввод Пароля
        loginPage.enterButtonClick();//Нажать войти
        mainPage.clickEnterLK();//Перейти в ЛК
        mainPage.clickConstructorButton();//Кликнуть на конструктор
        mainPage.verifyMainPageUrlConstructor();//Проверить что мы на главной странице после клика
    }

    @Test
    @Step("Проверить, что при клике на логотип из ЛК происходит переход на главную страницу")
    public void testLogoClickRedirectsToMainPage() {
        mainPage.createUserViaApi();// Авторизация
        PersonalAccountPage.openAutorizePersonalAccountUrl(); //перешли на страницу авторизации
        loginPage.inputEmail(mainPage.getCreatedUserEmail());//ввод Email
        loginPage.inputPassword(mainPage.getCreatedUserPassword());//Ввод Пароля
        loginPage.enterButtonClick();//Нажать войти
        mainPage.clickEnterLK();//Перейти в ЛК
        mainPage.clickLogoBurgers();//Кликнуть на конструктор
        mainPage.verifyMainPageUrlBurger();//Проверить что мы на главной странице после клика
    }

    @Test
    @Step("Проверка выхода из личного кабинета")
    public void testExitLK() throws InterruptedException {
        mainPage.createUserViaApi(); //создали пользователя
        PersonalAccountPage.openAutorizePersonalAccountUrl(); //перешли на страницу авторизации
        loginPage.inputEmail(mainPage.getCreatedUserEmail());//Ввели почту
        loginPage.inputPassword(mainPage.getCreatedUserPassword());//Ввели пароль
        loginPage.enterButtonClick();// Нажали на кнопку войти
        mainPage.clickEnterLK();//перешли в лк c главной страницы
        PersonalAccountPage.clickExitButton();//нажали выйти
        loginPage.verifyExitLkURL();//здесь мы убеждаемся что находимся на странице с логином после выхода
    }


    @Test
    @Step("Проверка перехода к разделу Соусы")
    public void testSaucesTabIsActive() {
        openBaseUrl();
        mainPage.clickSaucesTab();
        mainPage.assertSaucesTabIsActive();
    }

    @Test
    @Step("Проверка перехода к разделу Начинки")
    public void testFillingsTabIsActive() {
        openBaseUrl();
        mainPage.clickFillingsTab();
        mainPage.assertFillingsTabIsActive();
    }

    @Test
    @Step("Проверка перехода к разделу Булки")
    public void testBunsTabIsActive() throws InterruptedException {
        openBaseUrl();
        mainPage.clickFillingsTab();//Так как булки активен и на него нельзя сделать клик сначала кликаем на начинки а потом на булки
        mainPage.clickBunsTab();//кликаем на булки
        Thread.sleep(1000);
        mainPage.assertBunsTabIsActive();//Проверяем, что при клике на вкладку 'Булки' она становится активной"
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
