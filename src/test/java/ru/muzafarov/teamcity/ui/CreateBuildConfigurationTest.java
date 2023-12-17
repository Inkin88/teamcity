package ru.muzafarov.teamcity.ui;

import org.testng.annotations.Test;
import ru.muzafarov.teamcity.api.models.Project;
import ru.muzafarov.teamcity.api.requests.CheckedRequests;
import ru.muzafarov.teamcity.api.spec.Specifications;
import ru.muzafarov.teamcity.ui.pages.ProjectPage;
import ru.muzafarov.teamcity.ui.pages.ProjectsPage;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CreateBuildConfigurationTest extends BaseUiTest {

    @Test
    public void authorizedUserShouldBeAbleCreateBuildConfiguration() {
        var testData = testDataStorage.addTestData();
        new CheckedRequests(Specifications.getSpec().superUserSpec()).getCheckedUserReq().create(testData.getUser());
        Project project = new CheckedRequests(Specifications.getSpec().superUserSpec()).getCheckedProjectReq().create(testData.getProject());

        loginAsUser(testData.getUser());
        new ProjectsPage().open()
                .goToProjectPage(project)
                .goToEditProjectPage()
                .goToCreateBuildConfigurationPage(project.getId())
                .createBuildConfiguration(testData.getBuildType());
        assertTrue(new ProjectPage(project).open()
                .getBuildTypes()
                .stream()
                .anyMatch(x -> x.getHeader().getText().equals(testData.getBuildType().getName())));
    }

    @Test
    public void createProjectWithOutNameCannotCreated() {
        var testData = testDataStorage.addTestData();
        new CheckedRequests(Specifications.getSpec().superUserSpec()).getCheckedUserReq().create(testData.getUser());
        Project project = new CheckedRequests(Specifications.getSpec().superUserSpec()).getCheckedProjectReq().create(testData.getProject());
        testData.getBuildType().setName("");
        loginAsUser(testData.getUser());
        String errorMessage = new ProjectsPage().open()
                .goToProjectPage(project)
                .goToEditProjectPage()
                .goToCreateBuildConfigurationPage(project.getId())
                .getCreateBuildConfigurationError(testData.getBuildType());
        assertEquals(errorMessage, "Name must not be empty");
    }
}
