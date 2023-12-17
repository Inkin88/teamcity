package ru.muzafarov.teamcity.ui.elements;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import ru.muzafarov.teamcity.ui.Selectors;
@Getter
public class PageProjectElement extends PageElement {
    private final SelenideElement header;
    private final SelenideElement icon;
    public PageProjectElement(SelenideElement element) {
        super(element);
        this.header = findElement(Selectors.byDataTest("subproject"));
        this.icon = findElement("svg");
    }
}
