package ru.muzafarov.teamcity.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import ru.muzafarov.teamcity.api.models.Project;
import ru.muzafarov.teamcity.ui.Selectors;
import ru.muzafarov.teamcity.ui.elements.PageProjectElement;
import ru.muzafarov.teamcity.ui.pages.favorites.FavoritesPage;

import java.util.List;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.elements;
import static ru.muzafarov.teamcity.api.utils.StringUtils.format;

public class ProjectsPage extends FavoritesPage {
    private static final String FAVORITE_PROJECTS_URL = "/favorite/projects";
    private static final String PROJECT_TITLE = "//a[@title = '{}']";

    public ProjectsPage open() {
        Selenide.open(FAVORITE_PROJECTS_URL);
        waitUntilFavoritePageIsLoaded();
        return this;
    }

    public ProjectPage goToProjectPage(Project project) {
        $x(format(PROJECT_TITLE, project.getName())).click();
        return new ProjectPage(project);
    }

    public List<PageProjectElement> getSubProjects() {
        waitUntilProjectsAreLoaded();
        ElementsCollection subprojects = elements(Selectors.byClass("Subproject__container--Px"));
        return generatePageElements(subprojects, PageProjectElement::new);
    }
}
