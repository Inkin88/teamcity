package ru.muzafarov.teamcity.api;

import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ru.muzafarov.teamcity.api.generators.TestDataStorage;
import ru.muzafarov.teamcity.api.requests.CheckedRequests;
import ru.muzafarov.teamcity.api.requests.UncheckedRequests;
import ru.muzafarov.teamcity.api.spec.Specifications;

public class BaseTest {
    protected SoftAssertions softy;
    public TestDataStorage testDataStorage;
    public CheckedRequests checkedWithSuperUserRequest = new CheckedRequests(Specifications.getSpec().superUserSpec());
    public UncheckedRequests unCheckedWithSuperUserRequest = new UncheckedRequests(Specifications.getSpec().superUserSpec());

    @BeforeMethod
    public void beforeTest() {
        softy = new SoftAssertions();
        testDataStorage = TestDataStorage.getTestDataStorage();
    }

    @AfterMethod
    public void afterTest() {
        softy.assertAll();
        testDataStorage.delete();
    }

}
