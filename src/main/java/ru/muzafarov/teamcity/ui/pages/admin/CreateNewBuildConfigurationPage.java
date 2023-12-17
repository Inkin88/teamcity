package ru.muzafarov.teamcity.ui.pages.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.muzafarov.teamcity.api.models.BuildType;
import ru.muzafarov.teamcity.ui.Selectors;
import ru.muzafarov.teamcity.ui.pages.Page;

import static com.codeborne.selenide.Selenide.element;

public class CreateNewBuildConfigurationPage extends Page {
    private final SelenideElement buildNameInput = element(Selectors.byId("buildTypeName"));
    private final SelenideElement buildIdInput = element(Selectors.byId("buildTypeExternalId"));
    private final SelenideElement createButton = element(Selectors.byName("createBuildType"));
    private final SelenideElement createManuallyTab = element(Selectors.byDataHintContainerId("create-build-configuration"));
    private final SelenideElement createManuallyError= element(Selectors.byId("error_buildTypeName"));

    public void createBuildConfiguration(BuildType buildType) {
        createManuallyTab.click();
        buildNameInput.clear();
        buildNameInput.setValue(buildType.getName());
        buildIdInput.clear();
        buildIdInput.setValue(buildType.getId());
        createButton.click();
    }

    public String getCreateBuildConfigurationError(BuildType buildType) {
        createBuildConfiguration(buildType);
        return createManuallyError.shouldBe(Condition.visible).getText();
    }


}
