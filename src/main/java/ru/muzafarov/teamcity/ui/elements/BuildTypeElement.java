package ru.muzafarov.teamcity.ui.elements;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import ru.muzafarov.teamcity.ui.Selectors;

@Getter
public class BuildTypeElement extends PageElement {
    private final SelenideElement header;

    public BuildTypeElement(SelenideElement element) {
        super(element);

        this.header = findElement(Selectors.byRole("heading"));
    }
}

