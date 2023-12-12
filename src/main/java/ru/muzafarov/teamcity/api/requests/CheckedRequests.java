package ru.muzafarov.teamcity.api.requests;

import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import ru.muzafarov.teamcity.api.models.BuildType;
import ru.muzafarov.teamcity.api.models.Project;
import ru.muzafarov.teamcity.api.models.User;
import ru.muzafarov.teamcity.api.requests.checked.CheckedBase;

@Getter
public class CheckedRequests {
    private CheckedBase<BuildType> checkedBuildConfigReq;
    private CheckedBase<Project> checkedProjectReq;
    private CheckedBase<User> checkedUserReq;

    public CheckedRequests(RequestSpecification spec) {
        this.checkedUserReq = new CheckedBase<>(spec, User.class, "/app/rest/users");
        this.checkedProjectReq = new CheckedBase<>(spec, Project.class, "/app/rest/projects");
        this.checkedBuildConfigReq = new CheckedBase<>(spec, BuildType.class, "/app/rest/buildTypes");
    }
}
