package ru.muzafarov.teamcity.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import ru.muzafarov.teamcity.ui.Selectors;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.element;

public class FirstStartPage extends Page {
    private final SelenideElement proceedButton = element(Selectors.byId("proceedButton"));
    private final SelenideElement acceptCheckBox = element(Selectors.byId("accept"));
    private final SelenideElement continueButton = element(Selectors.byName("Continue"));
    private final SelenideElement userNameInput = element(Selectors.byId("input_teamcityUsername"));
    private final SelenideElement userPasswordInput = element(Selectors.byId("password1"));
    private final SelenideElement confirmPasswordInput = element(Selectors.byId("retypedPassword"));
    private final SelenideElement createAccountButton = element(Selectors.byType("submit"));
    private final SelenideElement welcomeHeader = element(Selectors.byXpath("//*[text() = 'Welcome to TeamCity']"));
    private final SelenideElement dataBaseHeader = element(Selectors.byXpath("//*[text() = 'Database connection setup']"));
    private final SelenideElement licenseAgreementHeader = element(Selectors.byXpath("//h1[contains(text(), 'License Agreement for JetBrains')]"));
    private final SelenideElement createAdministratorHeader = element(Selectors.byXpath("//h1[contains(text(), 'Create Administrator Account')]"));

    public FirstStartPage open() {
        Selenide.open("/");
        return this;
    }

    public FirstStartPage proceed() {
        proceedButton.click();
        return this;
    }

    public FirstStartPage dataBaseConnectionSetup() {
        dataBaseHeader.shouldBe(Condition.visible, Duration.ofMinutes(1));
        proceedButton.click();
        return this;
    }

    public FirstStartPage acceptLicenseAgreement() {
        licenseAgreementHeader.shouldBe(Condition.visible, Duration.ofMinutes(3));
        acceptCheckBox.click();
        continueButton.shouldBe(Condition.enabled).click();
        return this;
    }

    public FirstStartPage createAdmin() {
        createAdministratorHeader.shouldBe(Condition.visible, Duration.ofMinutes(3));
        userNameInput.setValue("admin");
        userPasswordInput.setValue("admin");
        confirmPasswordInput.setValue("admin");
        createAccountButton.click();
        welcomeHeader.shouldBe(Condition.visible, Duration.ofMinutes(1));
        return this;
    }
}
