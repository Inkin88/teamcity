package ru.muzafarov.teamcity.api.requests;

import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import ru.muzafarov.teamcity.api.requests.unchecked.UncheckedBase;
@Getter
public class UncheckedRequests {

    private UncheckedBase uncheckedBuildReq;
    private UncheckedBase uncheckedProjectReq;
    private UncheckedBase uncheckedUserReq;
    private UncheckedBase uncheckedAgentReq;

    public UncheckedRequests(RequestSpecification spec) {
        this.uncheckedBuildReq = new UncheckedBase(spec, "/app/rest/buildTypes");
        this.uncheckedProjectReq = new UncheckedBase(spec, "/app/rest/projects");
        this.uncheckedUserReq = new UncheckedBase(spec, "/app/rest/users");
        this.uncheckedAgentReq = new UncheckedBase(spec, "/app/rest/agents");
    }
}
