package ru.muzafarov.teamcity.api;

import org.testng.annotations.Test;
import ru.muzafarov.teamcity.api.requests.UncheckedRequests;
import ru.muzafarov.teamcity.api.spec.Specifications;

import static org.testng.Assert.assertTrue;

public class SetupFirstAgentStartTest extends BaseApiTest {
    @Test
    public void setupTeamCityAgentTest() {
        Integer id = new UncheckedRequests(Specifications.getSpec().superUserSpec()).getUncheckedAgentReq().getAgentsList()
                .jsonPath().getInt("agent[0].id");
        assertTrue(new UncheckedRequests(Specifications.getSpec().superUserSpec()).getUncheckedAgentReq().setAuthorizedAgent(String.valueOf(id), true)
                .jsonPath().getBoolean("status"), "Agent not authorized");
    }
}
