package ru.muzafarov.teamcity.ui.pages.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import ru.muzafarov.teamcity.api.models.Project;
import ru.muzafarov.teamcity.ui.Selectors;
import ru.muzafarov.teamcity.ui.pages.Page;

import static com.codeborne.selenide.Selenide.element;
import static ru.muzafarov.teamcity.api.utils.StringUtils.format;

public class CreateNewProject extends Page {
    private final SelenideElement urlInput = element(Selectors.byId("url"));
    private final SelenideElement proceedButton = element(Selectors.byName("createProjectFromUrl"));
    private final SelenideElement createProjectButton = element(Selectors.byName("createProject"));
    private final SelenideElement submitCreateProjectButton = element(Selectors.byName("submitCreateProject"));
    private final SelenideElement projectNameInput = element(Selectors.byId("projectName"));
    private final SelenideElement buildTypeInput = element(Selectors.byId("buildTypeName"));
    private final SelenideElement nameProjectInput = element(Selectors.byId("name"));
    private final SelenideElement idProjectInput = element(Selectors.byId("externalId"));
    private final SelenideElement manuallyTab = element(Selectors.byDataHintContainerId("create-project"));
    private final SelenideElement projectNameError = element(Selectors.byId("errorName"));

    public CreateNewProject open(String parentProjectId) {
        Selenide.open(format("/admin/createObjectMenu.html?projectId={}&showMode=createProjectMenu", parentProjectId));
        waitUntilPageIsLoaded();
        return this;
    }

    public CreateNewProject createProjectByUrl(String url) {
        urlInput.setValue(url);
        proceedButton.click();
        waitUntilDataIsSaved();
        return this;
    }

    public CreateNewProject createProjectManually(Project project) {
        manuallyTab.click();
        nameProjectInput.shouldBe(Condition.visible).setValue(project.getName());
        idProjectInput.clear();
        idProjectInput.shouldBe(Condition.visible).setValue(project.getId());
        submitCreateProjectButton.click();
        waitUntilDataIsSaved();
        return this;
    }

    public String createProjectManuallyWithOutProjectName(Project project) {
        createProjectManually(project);
        projectNameError.shouldBe(Condition.visible);
        return projectNameError.getText();
    }

    public void setupProject(String projectName, String buildTypeName) {
        projectNameInput.clear();
        projectNameInput.setValue(projectName);
        buildTypeInput.clear();
        buildTypeInput.setValue(buildTypeName);
        createProjectButton.click();
    }
}
