package ru.muzafarov.teamcity.api;

import lombok.Getter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ru.muzafarov.teamcity.api.generators.TestDataStorage;
import ru.muzafarov.teamcity.api.requests.CheckedRequests;
import ru.muzafarov.teamcity.api.requests.UncheckedRequests;
import ru.muzafarov.teamcity.api.spec.Specifications;

@Getter
public class BaseApiTest extends BaseTest {
    public TestDataStorage testDataStorage;
    public CheckedRequests checkedWithSuperUserRequest = new CheckedRequests(Specifications.getSpec().superUserSpec());
    public UncheckedRequests unCheckedWithSuperUserRequest = new UncheckedRequests(Specifications.getSpec().superUserSpec());

    @BeforeMethod
    public void setupTest() {
        testDataStorage = TestDataStorage.getTestDataStorage();
    }

    @AfterMethod
    public void cleanTest() {
        testDataStorage.delete();
    }
}
