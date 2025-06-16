package UtilsAndApi;

import AllPages.*;
import org.junit.After;
import org.openqa.selenium.WebDriver;
import praktikum.WebDriverStarts;

public class BaseTest {
    protected WebDriver driver;
    public RegisterPage registerPage;
    public MainPage mainPage;
    public LoginPage loginPage;
    public PersonalAccountPage PersonalAccountPage;
    public RecoveryPage recoveryPage;
    public static final String BASE_URL = "https://stellarburgers.nomoreparties.site/";

    public void initDriver(String browserType) {
        driver = WebDriverStarts.createDriver(browserType);
        registerPage = new RegisterPage(driver);
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        PersonalAccountPage = new PersonalAccountPage(driver);
        recoveryPage = new RecoveryPage(driver);
    }

    protected void openBaseUrl() {
        driver.get(BASE_URL);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
