package ru.muzafarov.teamcity.ui.elements;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import ru.muzafarov.teamcity.ui.Selectors;

@Getter
public class BuildTypeElement extends PageElement {
    private final SelenideElement header;
    private final SelenideElement link;

    public BuildTypeElement(SelenideElement element) {
        super(element);
        this.header = findElement(Selectors.byRole("heading"));
        this.link = findElement(Selectors.byDataTest("ring-link"));
    }
}

