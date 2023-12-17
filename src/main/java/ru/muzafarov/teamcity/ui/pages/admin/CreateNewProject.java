package ru.muzafarov.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import ru.muzafarov.teamcity.ui.Selectors;
import ru.muzafarov.teamcity.ui.pages.Page;

import static com.codeborne.selenide.Selenide.element;
import static ru.muzafarov.teamcity.api.utils.StringUtils.format;

public class CreateNewProject extends Page {
    private SelenideElement urlInput = element(Selectors.byId("url"));
    private SelenideElement proceedButton = element(Selectors.byName("createProjectFromUrl"));
    private SelenideElement createProjectButton = element(Selectors.byName("createProject"));
    private SelenideElement projectNameInput = element(Selectors.byId("projectName"));
    private SelenideElement buildTypeInput = element(Selectors.byId("buildTypeName"));

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

    public void setupProject(String projectName, String buildTypeName) {
        projectNameInput.clear();
        projectNameInput.setValue(projectName);
        buildTypeInput.clear();
        buildTypeInput.setValue(buildTypeName);
        createProjectButton.click();
    }
}
