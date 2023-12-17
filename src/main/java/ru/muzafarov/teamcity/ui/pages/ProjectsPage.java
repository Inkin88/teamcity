package ru.muzafarov.teamcity.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import ru.muzafarov.teamcity.ui.Selectors;
import ru.muzafarov.teamcity.ui.elements.PageProjectElement;
import ru.muzafarov.teamcity.ui.pages.favorites.FavoritesPage;

import java.util.List;

import static com.codeborne.selenide.Selenide.elements;

public class ProjectsPage extends FavoritesPage {
    private static final String FAVORITE_PROJECTS_URL = "/favorite/projects";
    private ElementsCollection subprojects = elements(Selectors.byClass("Subproject__container--Px"));
    public ProjectsPage open() {
        Selenide.open(FAVORITE_PROJECTS_URL);
        waitUntilFavoritePageIsLoaded();
        return this;
    }

    public List<PageProjectElement> getSubProjects() {
        return generatePageElements(subprojects, PageProjectElement::new);
    }
}
