package ru.muzafarov.teamcity.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import ru.muzafarov.teamcity.api.models.Project;
import ru.muzafarov.teamcity.ui.Selectors;
import ru.muzafarov.teamcity.ui.elements.BuildTypeElement;
import ru.muzafarov.teamcity.ui.pages.admin.EditProjectPage;

import java.util.List;

import static com.codeborne.selenide.Selenide.element;
import static com.codeborne.selenide.Selenide.elements;

public class ProjectPage extends Page {
    private String projectName;
    private String projectId;
    SelenideElement pageHeader = element(Selectors.byClass("ProjectPageHeader__title--ih"));
    SelenideElement editProjectButton = element(Selectors.byTitle("Edit project..."));
    private ElementsCollection buildTypes = elements(Selectors.byClass("BuildTypes__item--UX"));
    private static final String PROJECT_URL = "/project/";

    public ProjectPage(Project project) {
        this.projectId = project.getId();
        this.projectName = project.getName();
    }
    public ProjectPage open(){
        Selenide.open(PROJECT_URL + projectId);
        waitUntilPageIsLoaded();
        pageHeader.shouldBe(Condition.visible);
        pageHeader.shouldBe(Condition.text(projectName));
        return this;
    }

    public EditProjectPage goToEditProjectPage() {
        editProjectButton.click();
        return new EditProjectPage();
    }

    public List<BuildTypeElement> getBuildTypes(){
        return generatePageElements(buildTypes, BuildTypeElement::new);

    }
}
