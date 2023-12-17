package ru.muzafarov.teamcity.ui.pages.favorites;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.muzafarov.teamcity.ui.Selectors;
import ru.muzafarov.teamcity.ui.pages.Page;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.element;

public class FavoritesPage extends Page {
    private SelenideElement header = element(Selectors.byClass("ProjectPageHeader__title--ih"));

    public void waitUntilFavoritePageIsLoaded() {
        waitUntilPageIsLoaded();
        header.shouldBe(Condition.visible, Duration.ofSeconds(30));
    }
}
