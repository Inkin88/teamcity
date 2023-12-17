package ru.muzafarov.teamcity.ui.elements;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import ru.muzafarov.teamcity.ui.Selectors;

public abstract class PageElement {
    private final SelenideElement element;

    public PageElement(SelenideElement element) {
        this.element = element;
    }

    public SelenideElement findElement(String str) {
        return element.find(str);
    }

    public SelenideElement findElement(By by) {
        return element.find(by);
    }

    public ElementsCollection findElements(By by) {
        return element.findAll(by);
    }
}
