package ru.muzafarov.teamcity.ui;

import org.testng.annotations.Test;
import ru.muzafarov.teamcity.api.requests.CheckedRequests;
import ru.muzafarov.teamcity.api.spec.Specifications;
import ru.muzafarov.teamcity.ui.pages.admin.CreateNewProject;

public class CreateNewProjectTest extends BaseUiTest {
    @Test
    public void authorizedUserShouldBeAbleCreateNewProject() {
        var testData = testDataStorage.addTestData();
        new CheckedRequests(Specifications.getSpec().superUserSpec()).getCheckedUserReq().create(testData.getUser());
        var url = "https://github.com/Inkin88/automation";

        loginAsUser(testData.getUser());

        new CreateNewProject().open(testData.getProject().getParentProject().getLocator())
                .createProjectByUrl(url)
                .setupProject(testData.getProject().getName(), testData.getBuildType().getName());
    }
}
