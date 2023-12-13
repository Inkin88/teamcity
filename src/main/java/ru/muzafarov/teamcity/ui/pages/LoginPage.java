package ru.muzafarov.teamcity.ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import ru.muzafarov.teamcity.api.models.User;
import ru.muzafarov.teamcity.ui.Selectors;

import static com.codeborne.selenide.Selenide.element;
@Getter
public class LoginPage extends Page {
    private static final String LOGIN_PAGE_URL = "/login.html";
    private SelenideElement userNameInput = element(Selectors.byId("username"));
    private SelenideElement passwordInput = element(Selectors.byId("password"));
    private SelenideElement loginButton = element(Selectors.byName("submitLogin"));

    public LoginPage open() {
        Selenide.open(LOGIN_PAGE_URL);
        return this;
    }

    public void login(User user) {
        userNameInput.setValue(user.getUserName());
        passwordInput.setValue(user.getPassword());
        loginButton.click();
    }
}
