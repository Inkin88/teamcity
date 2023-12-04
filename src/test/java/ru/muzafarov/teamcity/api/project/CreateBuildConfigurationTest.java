package ru.muzafarov.teamcity.api.project;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.muzafarov.teamcity.api.BaseApiTest;
import ru.muzafarov.teamcity.api.models.BuildType;
import ru.muzafarov.teamcity.api.models.BuildTypeList;
import ru.muzafarov.teamcity.api.models.NewProjectDescription;

import static org.testng.Assert.*;
import static ru.muzafarov.teamcity.api.generators.TestDataGenerator.generateBuildType;
import static ru.muzafarov.teamcity.api.generators.TestDataGenerator.generateNewProjectDescription;
import static ru.muzafarov.teamcity.api.utils.StringUtils.format;

public class CreateBuildConfigurationTest extends BaseApiTest {

    @Test
    public void createBuildConfigurationWithValidDataTest() {
        var testData = testDataStorage.addTestData();
        BuildType expectedBuildType = testData.getBuildType();

        checkedWithSuperUserRequest.getCheckedProjectReq().create(testData.getProject());
        checkedWithSuperUserRequest.getCheckedBuildConfigReq().create(expectedBuildType);
        var actualBuildConf = checkedWithSuperUserRequest.getCheckedBuildConfigReq().get(expectedBuildType.getId());

        assertEquals(actualBuildConf, expectedBuildType);
    }

    @Test
    public void createBuildConfigurationToProjectWithExistBuildConf() {
        var testData = testDataStorage.addTestData();
        BuildType dataBuildType = testData.getBuildType();
        NewProjectDescription project = testData.getProject();

        checkedWithSuperUserRequest.getCheckedProjectReq().create(project);
        checkedWithSuperUserRequest.getCheckedBuildConfigReq().create(dataBuildType);

        generateBuildType(project);
        BuildType buildType = checkedWithSuperUserRequest.getCheckedBuildConfigReq().create(generateBuildType(project));

        BuildTypeList buildTypesList = checkedWithSuperUserRequest.getCheckedProjectReq().getBuildTypesList(project.getId());

        assertEquals(2, buildTypesList.getCount());
        assertTrue(buildTypesList.getBuildType().stream().anyMatch(x -> x.getId().equals(buildType.getId())));
    }

    @Test
    public void createBuildConfigurationWithoutProjectTest() {
        String expectedMessage = "Build type creation request should contain project node.";
        var buildConfig = generateBuildType(generateNewProjectDescription());

        buildConfig.setProject(null);

        String errorMessage = unCheckedWithSuperUserRequest.getUncheckedBuildReq()
                .create(buildConfig)
                .then()
                .statusCode(400)
                .extract()
                .asString();

        assertTrue(errorMessage.contains(expectedMessage), format("No string '{}' in Response", expectedMessage));
    }

    @Test
    public void createBuildConfigurationWithNotExistProjectTest() {
        NewProjectDescription newProjectDescription = generateNewProjectDescription();
        String projectId = newProjectDescription.getId();
        var buildConfig = generateBuildType(newProjectDescription);
        String expectedMessage = format("No project found by locator 'count:1,id:{}'. Project cannot be found by external id '{}'",
                projectId, projectId);

        String errorMessage = unCheckedWithSuperUserRequest.getUncheckedBuildReq()
                .create(buildConfig)
                .then()
                .statusCode(404)
                .extract()
                .asString();

        assertTrue(errorMessage.contains(expectedMessage), format("No string '{}' in Response", expectedMessage));
    }

    @Test
    public void createBuildConfigurationWithDifferentProjectNameMustBeNotValidateTest() {
        var testData = testDataStorage.addTestData();
        var buildConf = testData.getBuildType();
        checkedWithSuperUserRequest.getCheckedProjectReq().create(testData.getProject());
        String newProjectName = RandomStringUtils.randomAlphabetic(10);
        buildConf.setProjectName(newProjectName);

        BuildType actualBuildType = checkedWithSuperUserRequest.getCheckedBuildConfigReq().create(buildConf);
        assertNotEquals(newProjectName, actualBuildType.getName());
    }

    @Test
    public void createBuildConfigurationWithExistProjectNameAndNotExistProjectTest() {
        var testData = testDataStorage.addTestData();
        NewProjectDescription secondProject = generateNewProjectDescription();
        checkedWithSuperUserRequest.getCheckedProjectReq().create(testData.getProject());

        String projectId = secondProject.getId();
        var buildConfig = generateBuildType(testData.getProject());
        buildConfig.setProject(secondProject.toProject());
        String expectedMessage = format("No project found by locator 'count:1,id:{}'. Project cannot be found by external id '{}'",
                projectId, projectId);

        String errorMessage = unCheckedWithSuperUserRequest.getUncheckedBuildReq()
                .create(buildConfig)
                .then()
                .statusCode(404)
                .extract()
                .asString();

        assertTrue(errorMessage.contains(expectedMessage), format("No string '{}' in Response", expectedMessage));
    }

    @Test(dataProvider = "data")
    public void createBuildConfigurationWithInvalidNameCannotCreatedTest(String buildName) {
        String expectedErrorMessage = "When creating a build type, non empty name should be provided.";
        var testData = testDataStorage.addTestData();
        var buildConf = testData.getBuildType();
        checkedWithSuperUserRequest.getCheckedProjectReq().create(testData.getProject());

        buildConf.setName(buildName);

        String errorMessage = unCheckedWithSuperUserRequest.getUncheckedBuildReq().create(buildConf)
                .then()
                .statusCode(400)
                .extract()
                .asString();

        assertTrue(errorMessage.contains(expectedErrorMessage), format("No string '{}' in Response", expectedErrorMessage));
    }

    @Test(dataProvider = "data")
    public void createBuildConfigurationWithInvalidIdCannotCreatedTest(String buildId) {
        String expectedErrorMessage = "Build configuration or template ID must not be empty.";
        var testData = testDataStorage.addTestData();
        var buildConf = testData.getBuildType();
        checkedWithSuperUserRequest.getCheckedProjectReq().create(testData.getProject());

        buildConf.setId(buildId);

        String errorMessage = unCheckedWithSuperUserRequest.getUncheckedBuildReq().create(buildConf)
                .then()
                .statusCode(500)
                .extract()
                .asString();

        assertTrue(errorMessage.contains(expectedErrorMessage), format("No string '{}' in Response", expectedErrorMessage));
    }

    @Test
    public void createBuildConfigurationWithVeryLongNameTest() {
        var testData = testDataStorage.addTestData();
        checkedWithSuperUserRequest.getCheckedProjectReq().create(testData.getProject());
        testData.getBuildType().setName(RandomStringUtils.randomAlphabetic(30000));

        unCheckedWithSuperUserRequest.getUncheckedBuildReq().create(testData.getBuildType())
                .then()
                .statusCode(400);
    }

    @Test
    public void createBuildConfigurationWithMaxPlusOneLengthIdTest() {
        String expectedMessage = "ID should start with a latin letter and contain only latin letters, digits and underscores (at most 225 characters).";
        var testData = testDataStorage.addTestData();
        checkedWithSuperUserRequest.getCheckedProjectReq().create(testData.getProject());
        testData.getBuildType().setId(RandomStringUtils.randomAlphabetic(226));

        String errorMessage = unCheckedWithSuperUserRequest.getUncheckedBuildReq().create(testData.getBuildType())
                .then()
                .statusCode(500)
                .extract().asString();

        assertTrue(errorMessage.contains(expectedMessage), format("No string '{}' in Response", expectedMessage));
    }

    @Test
    public void createBuildConfigurationWithMaxLengthIdTest() {
        var testData = testDataStorage.addTestData();
        checkedWithSuperUserRequest.getCheckedProjectReq().create(testData.getProject());
        testData.getBuildType().setId(RandomStringUtils.randomAlphabetic(225));

        BuildType buildType = unCheckedWithSuperUserRequest.getUncheckedBuildReq().create(testData.getBuildType())
                .then()
                .statusCode(200)
                .extract().as(BuildType.class);

        assertEquals(buildType.getId(), testData.getBuildType().getId());
    }

    @DataProvider(name = "data")
    public Object[][] data() {
        return new Object[][]{
                {""},
                {" "},
                {null}
        };
    }
}
