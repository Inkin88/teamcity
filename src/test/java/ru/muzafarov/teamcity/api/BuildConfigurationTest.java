package ru.muzafarov.teamcity.api;


import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;
import ru.muzafarov.teamcity.api.requests.checked.CheckedProjectRequests;
import ru.muzafarov.teamcity.api.requests.checked.CheckedUserRequests;
import ru.muzafarov.teamcity.api.spec.Specifications;

@Log4j2
public class BuildConfigurationTest extends BaseApiTest {

    @Test
    public void buildConfigurationTest() {
        var testData = testDataStorage.addTestData();

        new CheckedUserRequests(Specifications.getSpec().superUserSpec()).create(testData.getUser());

        var project = new CheckedProjectRequests(Specifications.getSpec()
                .authSpec(testData.getUser()))
                .create(testData.getProject());

        softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
    }
}
