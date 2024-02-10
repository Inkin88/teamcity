package ru.muzafarov.teamcity.ui;

import com.codeborne.selenide.Condition;
import org.testng.annotations.Test;
import ru.muzafarov.teamcity.api.models.Project;
import ru.muzafarov.teamcity.api.requests.CheckedRequests;
import ru.muzafarov.teamcity.api.spec.Specifications;
import ru.muzafarov.teamcity.ui.pages.ProjectsPage;
import ru.muzafarov.teamcity.ui.pages.admin.CreateNewProject;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

@Test(groups = "Regression")
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

    @Test
    public void createProjectWithoutNameShouldBeNotCreated() {
        var testData = testDataStorage.addTestData();
        new CheckedRequests(Specifications.getSpec().superUserSpec()).getCheckedUserReq().create(testData.getUser());

        loginAsUser(testData.getUser());

        Project project = testData.getProject().toProject();
        project.setName("");
        assertEquals(new CreateNewProject().open(testData.getProject().getParentProject().getLocator())
                .createProjectManuallyWithOutProjectName(project), "Project name is empty");
    }

    @Test
    public void authorizedUserShouldBeAbleCreateNewProjectManually() {
        var testData = testDataStorage.addTestData();
        new CheckedRequests(Specifications.getSpec().superUserSpec()).getCheckedUserReq().create(testData.getUser());

        loginAsUser(testData.getUser());

        Project project = testData.getProject().toProject();
        new CreateNewProject().open(testData.getProject().getParentProject().getLocator())
                .createProjectManually(project);

        new ProjectsPage().open()
                .getSubProjects()
                .stream().reduce((first, second) -> second).get()
                .getHeader().shouldHave(Condition.text(project.getName()));
    }

    @Test
    public void authorizedUserShouldBeAbleDeleteProject() {
        var testData = testDataStorage.addTestData();
        new CheckedRequests(Specifications.getSpec().superUserSpec()).getCheckedUserReq().create(testData.getUser());
        Project project = new CheckedRequests(Specifications.getSpec().superUserSpec()).getCheckedProjectReq().create(testData.getProject());

        loginAsUser(testData.getUser());
        new ProjectsPage().open()
                .goToProjectPage(project)
                .goToEditProjectPage()
                .deleteProject();

        assertFalse(new ProjectsPage().open()
                .getSubProjects()
                .stream().anyMatch(x -> x.getHeader().getText().equals(project.getName())), "Project is not deleted");
    }
}
