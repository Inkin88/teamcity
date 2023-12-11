package ui;

import com.codeborne.selenide.selector.ByAttribute;
import org.testng.annotations.Test;
import ru.muzafarov.teamcity.api.requests.checked.CheckedUserRequests;
import ru.muzafarov.teamcity.api.spec.Specifications;

import static com.codeborne.selenide.Selenide.element;
import static com.codeborne.selenide.Selenide.open;

public class CreateNewProjectTest extends BaseUiTest {
    @Test
    public void authorizedUserShouldBeAbleCreateNewProject() {
        var testData = testDataStorage.addTestData();
        new CheckedUserRequests(Specifications.getSpec().superUserSpec()).create(testData.getUser());

        open("/login.html");

        var userNameInput = element(new ByAttribute("id", "username"));
        var passwordInput = element(new ByAttribute("id", "password"));

        userNameInput.setValue(testData.getUser().getUserName());
        passwordInput.setValue(testData.getUser().getPassword());

        element(new ByAttribute("name", "submitLogin")).click();

    }
}
