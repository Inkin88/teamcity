package ru.muzafarov.teamcity.ui;

import com.codeborne.selenide.Condition;
import org.testng.annotations.Test;
import ru.muzafarov.teamcity.api.requests.CheckedRequests;
import ru.muzafarov.teamcity.api.spec.Specifications;
import ru.muzafarov.teamcity.ui.pages.ProjectsPage;
import ru.muzafarov.teamcity.ui.pages.admin.CreateNewProject;

public class CreateNewProjectTest extends BaseUiTest {
    @Test
    public void authorizedUserShouldBeAbleCreateNewProject() {
        var testData = testDataStorage.addTestData();
        new CheckedRequests(Specifications.getSpec().superUserSpec()).getCheckedUserReq().create(testData.getUser());
        var url = "https://github.com/Inkin88/basejava";

        loginAsUser(testData.getUser());

        String projectName = testData.getProject().getName();
        new CreateNewProject().open(testData.getProject().getParentProject().getLocator())
                .createProjectByUrl(url)
                .setupProject(projectName, testData.getBuildType().getName());

        new ProjectsPage().open()
                .getSubProjects()
                .stream().reduce((first, second) -> second).get()
                .getHeader().shouldHave(Condition.text(projectName));
    }
}
