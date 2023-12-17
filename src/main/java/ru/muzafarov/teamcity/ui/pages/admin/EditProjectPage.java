package ru.muzafarov.teamcity.ui.pages.admin;

import com.codeborne.selenide.SelenideElement;
import ru.muzafarov.teamcity.ui.Selectors;
import ru.muzafarov.teamcity.ui.pages.Page;

import static com.codeborne.selenide.Selenide.element;
import static ru.muzafarov.teamcity.api.utils.StringUtils.format;

public class EditProjectPage extends Page {
    private SelenideElement deleteAction = element(Selectors.byTitle("Delete project"));
    private SelenideElement actions = element(Selectors.byDataHintContainerId("project-admin-actions"));
    private String createBuildConfXpath = "/admin/createObjectMenu.html?projectId={}&showMode=createBuildTypeMenu&cameFromUrl=%2Fadmin%2FeditProject.html%3FprojectId%3D{}";

    public void deleteProject() {
        actions.click();
        deleteAction.click();
    }

    public CreateNewBuildConfigurationPage goToCreateBuildConfigurationPage(String projectId) {
        element(Selectors.byHref(format(createBuildConfXpath, projectId, projectId)))
                .click();
        return new CreateNewBuildConfigurationPage();
    }

}
