package ru.muzafarov.teamcity.api;


import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;
import ru.muzafarov.teamcity.api.requests.CheckedRequests;
import ru.muzafarov.teamcity.api.spec.Specifications;

@Log4j2
@Test(groups = "Regression")
public class BuildConfigurationTest extends BaseApiTest {

    @Test
    public void buildConfigurationTest() {
        var testData = testDataStorage.addTestData();
        checkedWithSuperUserRequest.getCheckedUserReq().create(testData.getUser());


        var project = new CheckedRequests(Specifications.getSpec()
                .authSpec(testData.getUser()))
                .getCheckedProjectReq()
                .create(testData.getProject());

        softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
    }
}
