package ru.muzafarov.teamcity.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.muzafarov.teamcity.ui.Selectors;
import ru.muzafarov.teamcity.ui.elements.PageElement;
import ru.muzafarov.teamcity.ui.elements.PageProjectElement;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.codeborne.selenide.Selenide.element;

public abstract class Page {
    private SelenideElement submitButton = element(Selectors.byType("submit"));
    private SelenideElement savingWaitingMarker = element(Selectors.byId("saving"));
    private SelenideElement pageWaitingMarker = element(Selectors.byDataTest("ring"));

    public void submit() {
        submitButton.click();
        waitUntilDataIsSaved();
    }

    public void waitUntilPageIsLoaded() {
        pageWaitingMarker.shouldNotBe(Condition.visible, Duration.ofMinutes(1));

    }

    public void waitUntilDataIsSaved() {
        savingWaitingMarker.shouldBe(Condition.visible);
        savingWaitingMarker.shouldNotBe(Condition.visible, Duration.ofSeconds(30));
    }

    public  <T extends PageElement> List<T> generatePageElements(ElementsCollection collection, Function<SelenideElement, T> creator) {
        var elements = new ArrayList<T>();
        collection.forEach(element -> elements.add(creator.apply(element)));
        return elements;
    }
}
