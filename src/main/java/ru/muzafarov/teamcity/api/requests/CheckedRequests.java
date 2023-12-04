package ru.muzafarov.teamcity.api.requests;

import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import ru.muzafarov.teamcity.api.requests.checked.CheckedBuildConfigRequests;
import ru.muzafarov.teamcity.api.requests.checked.CheckedProjectRequests;
import ru.muzafarov.teamcity.api.requests.checked.CheckedUserRequests;

@Getter
public class CheckedRequests {
    private CheckedBuildConfigRequests checkedBuildConfigReq;
    private CheckedProjectRequests checkedProjectReq;
    private CheckedUserRequests checkedUserReq;

    public CheckedRequests(RequestSpecification spec) {
        this.checkedUserReq = new CheckedUserRequests(spec);
        this.checkedProjectReq = new CheckedProjectRequests(spec);
        this.checkedBuildConfigReq = new CheckedBuildConfigRequests(spec);
    }
}
