package ru.muzafarov.teamcity.api;

import org.testng.annotations.Test;
import ru.muzafarov.teamcity.api.requests.UncheckedRequests;
import ru.muzafarov.teamcity.api.spec.Specifications;

import static org.testng.Assert.assertTrue;

public class SetupFirstAgentStartTest extends BaseApiTest {
    @Test
    public void setupTeamCityAgentTest() {
        assertTrue(new UncheckedRequests(Specifications.getSpec().superUserSpec()).getUncheckedAgentReq().setAuthorizedAgent("1", true)
                .jsonPath().getBoolean("status"), "Agent not authorized");
    }
}
