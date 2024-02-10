package ru.muzafarov.teamcity.ui;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.BeforeSuite;
import ru.muzafarov.teamcity.api.BaseTest;
import ru.muzafarov.teamcity.api.config.Config;
import ru.muzafarov.teamcity.api.models.User;
import ru.muzafarov.teamcity.ui.pages.LoginPage;

public class BaseUiTest extends BaseTest {
    @BeforeSuite
    public void setupUiTests() {
        Configuration.browser = "chrome";
        Configuration.baseUrl = "http://" + Config.getProperty("host");
        Configuration.remote = Config.getProperty("remote");

        Configuration.reportsFolder = "target/surefire-reports";
        Configuration.downloadsFolder ="target/downloads";
        BrowserSettings.setup(Config.getProperty("browser"));
    }

    public void loginAsUser(User user) {
        new LoginPage().open().login(user);
    }
}
