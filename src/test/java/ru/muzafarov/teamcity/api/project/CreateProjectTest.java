package ru.muzafarov.teamcity.api.project;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.muzafarov.teamcity.api.BaseApiTest;
import ru.muzafarov.teamcity.api.models.Project;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static ru.muzafarov.teamcity.api.generators.TestDataGenerator.generateNewProjectDescription;
import static ru.muzafarov.teamcity.api.utils.StringUtils.format;

public class CreateProjectTest extends BaseApiTest {

    @Test
    public void createProjectWithValidDataTest() {
        var expectedProject = testDataStorage.addProjectTestData();

        checkedWithSuperUserRequest.getCheckedProjectReq().create(expectedProject);
        Project actualProject = checkedWithSuperUserRequest.getCheckedProjectReq().get(expectedProject.getId());

        assertEquals(actualProject, expectedProject.toProject());
    }

    @Test
    public void createProjectWhenParentProjectNotRootTest() {
        var parentProjectData = testDataStorage.addProjectTestData();
        var childProjectData = testDataStorage.addProjectTestData();

        Project parentProject = checkedWithSuperUserRequest.getCheckedProjectReq().create(parentProjectData);

        childProjectData.setParentProject(parentProject);

        Project childProject = checkedWithSuperUserRequest.getCheckedProjectReq().create(childProjectData);

        assertEquals(parentProjectData.getId(), childProject.getParentProjectId());
    }

    @Test
    public void createProjectWithParentProjectEqualsNullCannotCreatedTest() {
        String expectedMessage = "No project specified. Either 'id', 'internalId' or 'locator' attribute should be present.";
        var parentProjectData = Project.builder().build();
        var childProjectData = generateNewProjectDescription();
        childProjectData.setParentProject(parentProjectData);

        String errorMessage = unCheckedWithSuperUserRequest.getUncheckedProjectReq().create(childProjectData)
                .then()
                .statusCode(400)
                .extract().asString();

        assertTrue(errorMessage.contains(expectedMessage), format("No string '{}' in Response", expectedMessage));
    }

    @Test
    public void createProjectWithNotExistParentProjectCannotCreatedTest() {
        var parentProjectData = generateNewProjectDescription();
        var childProjectData = generateNewProjectDescription();
        childProjectData.setParentProject(parentProjectData.toProject());

        String expectedMessage = format("Project cannot be found by external id '{}'", parentProjectData.getId());

        String errorMessage = unCheckedWithSuperUserRequest.getUncheckedProjectReq().create(childProjectData)
                .then()
                .statusCode(404)
                .extract().asString();

        assertTrue(errorMessage.contains(expectedMessage), format("No string '{}' in Response", expectedMessage));
    }

    @Test(dataProvider = "projectNameData")
    public void createProjectWithInvalidNameCannotCreatedTest(String projectName, int expectedStatusCode, String expectedErrorMessage) {
        var projectData = generateNewProjectDescription();
        projectData.setName(projectName);

        String errorMessage = unCheckedWithSuperUserRequest.getUncheckedProjectReq().create(projectData)
                .then()
                .statusCode(expectedStatusCode)
                .extract()
                .asString();

        assertTrue(errorMessage.contains(expectedErrorMessage), format("No string '{}' in Response", expectedErrorMessage));
    }

    @Test
    public void createProjectWithVeryLongNameTest() {
        String expectedErrorMessage = "The name length must not exceed 100 characters";
        var projectData = testDataStorage.addProjectTestData();
        projectData.setName(RandomStringUtils.randomAlphabetic(300));

        String errorMessage = unCheckedWithSuperUserRequest.getUncheckedProjectReq().create(projectData)
                .then()
                .statusCode(400)
                .extract().asString();

        assertTrue(errorMessage.contains(expectedErrorMessage), format("No string '{}' in Response", expectedErrorMessage));
    }

    @Test(dataProvider = "projectIdData")
    public void createProjectWithInvalidIdCannotCreatedTest(String projectId) {
        var projectData = generateNewProjectDescription();
        String expectedErrorMessage = "Project ID must not be empty.";
        projectData.setId(projectId);

        String errorMessage = unCheckedWithSuperUserRequest.getUncheckedProjectReq().create(projectData)
                .then()
                .statusCode(500)
                .extract()
                .asString();

        assertTrue(errorMessage.contains(expectedErrorMessage), format("No string '{}' in Response", expectedErrorMessage));
    }

    @Test
    public void createProjectWithAlreadyExistNameCannotCreatedTest() {
        var projectData = testDataStorage.addProjectTestData();
        String expectedErrorMessage = format("Project with this name already exists: {}", projectData.getName());
        checkedWithSuperUserRequest.getCheckedProjectReq().create(projectData);

        var secondProjectData = generateNewProjectDescription();
        secondProjectData.setName(projectData.getName());

        String errorMessage = unCheckedWithSuperUserRequest.getUncheckedProjectReq().create(secondProjectData)
                .then()
                .statusCode(400)
                .extract()
                .asString();

        assertTrue(errorMessage.contains(expectedErrorMessage), format("No string '{}' in Response", expectedErrorMessage));
    }

    @Test
    public void createProjectWithAlreadyExistIdCannotCreatedTest() {
        var projectData = testDataStorage.addProjectTestData();
        String expectedErrorMessage = format("Project ID \"{}\" is already used by another project", projectData.getId());
        checkedWithSuperUserRequest.getCheckedProjectReq().create(projectData);

        var secondProjectData = generateNewProjectDescription();
        secondProjectData.setId(projectData.getId());

        String errorMessage = unCheckedWithSuperUserRequest.getUncheckedProjectReq().create(secondProjectData)
                .then()
                .statusCode(400)
                .extract()
                .asString();

        assertTrue(errorMessage.contains(expectedErrorMessage), format("No string '{}' in Response", expectedErrorMessage));
    }

    @Test
    public void createProjectWithMaxLengthIdTest() {
        var projectData = testDataStorage.addProjectTestData();
        projectData.setId(RandomStringUtils.randomAlphabetic(225));

        Project createdProject = unCheckedWithSuperUserRequest.getUncheckedProjectReq().create(projectData)
                .then()
                .statusCode(200)
                .extract()
                .as(Project.class);

        assertEquals(createdProject.getId(), projectData.getId());
    }

    @Test
    public void createProjectWithMaxLengthPlusOneIdTest() {
        var projectData = testDataStorage.addProjectTestData();
        projectData.setId(RandomStringUtils.randomAlphabetic(226));
        String expectedErrorMessage = format("Project ID \"{}\" is invalid: it is 226 characters long while the maximum length is 225. ID should start with a latin letter and contain only latin letters, digits and underscores (at most 225 characters).", projectData.getId());

        String errorMessage = unCheckedWithSuperUserRequest.getUncheckedProjectReq().create(projectData)
                .then()
                .statusCode(500)
                .extract()
                .asString();

        assertTrue(errorMessage.contains(expectedErrorMessage), format("No string '{}' in Response", expectedErrorMessage));
    }

    @DataProvider(name = "projectNameData")
    public Object[][] nameData() {
        return new Object[][]{
                {"", 400, "Project name cannot be empty."},
                {" ", 500, "Given project name is empty"},
                {null, 400, "Project name cannot be empty."}
        };
    }

    @DataProvider(name = "projectIdData")
    public Object[][] idData() {
        return new Object[][]{
                {""},
                {" "},
                {null}
        };
    }
}
