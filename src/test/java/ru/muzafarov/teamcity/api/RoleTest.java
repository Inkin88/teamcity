package ru.muzafarov.teamcity.api;

import org.testng.annotations.Test;
import ru.muzafarov.teamcity.api.enums.Role;
import ru.muzafarov.teamcity.api.models.User;
import ru.muzafarov.teamcity.api.requests.CheckedRequests;
import ru.muzafarov.teamcity.api.requests.UncheckedRequests;
import ru.muzafarov.teamcity.api.spec.Specifications;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static ru.muzafarov.teamcity.api.generators.TestDataGenerator.generateRole;
import static ru.muzafarov.teamcity.api.utils.StringUtils.format;

public class RoleTest extends BaseApiTest {
    @Test
    public void unAuthorizeUserShouldNotHaveRightsToCreateProjectTest() {
        var testData = testDataStorage.addTestData();
        var uncheckedProject = new UncheckedRequests(Specifications.getSpec().unAuthSpec());

        uncheckedProject
                .getUncheckedProjectReq()
                .create(testData.getProject())
                .then()
                .statusCode(401)
                .body(equalTo("Authentication required\n" +
                        "To login manually go to \"/login.html\" page"));

        new UncheckedRequests(Specifications.getSpec().superUserSpec())
                .getUncheckedProjectReq()
                .get(testData.getProject().getId())
                .then()
                .statusCode(404)
                .body(containsString(format("No project found by locator 'count:1,id:{}'.", testData.getProject().getId())));
    }

    @Test
    public void systemAdminShouldHaveRightsToCreateProjectTest() {
        var testData = testDataStorage.addTestData();
        testData.getUser().setRoles(generateRole(Role.SYSTEM_ADMIN));

        checkedWithSuperUserRequest.getCheckedUserReq().create(testData.getUser());

        var project = new CheckedRequests(Specifications.getSpec()
                .authSpec(testData.getUser()))
                .getCheckedProjectReq()
                .create(testData.getProject());

        softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
    }

    @Test
    public void projectAdminShouldHaveRightsToCreateBuildConfigToHisProjectTest() {
        var testData = testDataStorage.addTestData();

        checkedWithSuperUserRequest.getCheckedProjectReq()
                .create(testData.getProject());

        User user = testData.getUser();
        user.setRoles(generateRole(Role.PROJECT_ADMIN, format("p:{}", testData.getProject().getId())));

        checkedWithSuperUserRequest.getCheckedUserReq()
                .create(user);

        var buildConfig = new CheckedRequests(Specifications.getSpec().authSpec(user))
                .getCheckedBuildConfigReq()
                .create(testData.getBuildType());

        softy.assertThat(buildConfig.getId()).isEqualTo(testData.getBuildType().getId());
    }

    @Test
    public void projectAdminShouldNotHaveRightsToCreateBuildConfigToAnotherProjectTest() {
        var firstTestData = testDataStorage.addTestData();
        var secondTestData = testDataStorage.addTestData();

        checkedWithSuperUserRequest.getCheckedProjectReq()
                .create(firstTestData.getProject());

        checkedWithSuperUserRequest.getCheckedProjectReq()
                .create(secondTestData.getProject());

        User firstUser = firstTestData.getUser();
        firstUser.setRoles(generateRole(Role.PROJECT_ADMIN, format("p:{}", firstTestData.getProject().getId())));

        User secondUser = secondTestData.getUser();
        secondUser.setRoles(generateRole(Role.PROJECT_ADMIN, format("p:{}", secondTestData.getProject().getId())));

        checkedWithSuperUserRequest.getCheckedUserReq()
                .create(firstUser);

        checkedWithSuperUserRequest.getCheckedUserReq()
                .create(secondUser);

        new UncheckedRequests(Specifications.getSpec().authSpec(firstUser))
                .getUncheckedBuildReq()
                .create(secondTestData.getBuildType())
                .then()
                .statusCode(403)
                .body(containsString("Access denied. Check the user has enough permissions to perform the operation."));
    }
}
