package ru.muzafarov.teamcity.ui;

import org.testng.annotations.Test;
import ru.muzafarov.teamcity.api.models.BuildType;
import ru.muzafarov.teamcity.api.models.Project;
import ru.muzafarov.teamcity.api.requests.CheckedRequests;
import ru.muzafarov.teamcity.api.spec.Specifications;
import ru.muzafarov.teamcity.ui.elements.BuildTypeElement;
import ru.muzafarov.teamcity.ui.pages.ProjectPage;
import ru.muzafarov.teamcity.ui.pages.ProjectsPage;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static ru.muzafarov.teamcity.api.utils.StringUtils.format;

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
        List<BuildTypeElement> buildTypesListUI = new ProjectPage(project).open()
                .getBuildTypes();
        assertTrue(buildTypesListUI
                        .stream()
                        .anyMatch(x -> x.getLink().getText().equals(testData.getBuildType().getName())),
                format("BuildConfiguration not found in {} project UI form", project.getName()));
        List<BuildType> buildTypeListAPI = new CheckedRequests(Specifications.getSpec().superUserSpec()).getCheckedBuildConfigReq()
                .getList("buildType");
        assertTrue(buildTypeListAPI.stream().anyMatch(x -> x.getName().equals(testData.getBuildType().getName())),
                format("BuildConfiguration not found in {} project API", project.getName()));
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
