package ru.muzafarov.teamcity.api.requests;

import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import ru.muzafarov.teamcity.api.requests.unchecked.UncheckedBuildConfigRequests;
import ru.muzafarov.teamcity.api.requests.unchecked.UncheckedProjectRequest;
import ru.muzafarov.teamcity.api.requests.unchecked.UncheckedUserRequests;
@Getter
public class UncheckedRequests {

    private UncheckedBuildConfigRequests uncheckedBuildReq;
    private UncheckedProjectRequest uncheckedProjectReq;
    private UncheckedUserRequests uncheckedUserReq;

    public UncheckedRequests(RequestSpecification spec) {
        this.uncheckedBuildReq = new UncheckedBuildConfigRequests(spec);
        this.uncheckedProjectReq = new UncheckedProjectRequest(spec);
        this.uncheckedUserReq = new UncheckedUserRequests(spec);
    }
}
