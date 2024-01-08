package ru.muzafarov.teamcity.api.generators;

import io.restassured.specification.RequestSpecification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.muzafarov.teamcity.api.models.BuildType;
import ru.muzafarov.teamcity.api.models.NewProjectDescription;
import ru.muzafarov.teamcity.api.models.User;
import ru.muzafarov.teamcity.api.requests.UncheckedRequests;
import ru.muzafarov.teamcity.api.spec.Specifications;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestData {
    private User user;
    private NewProjectDescription project;
    private BuildType buildType;
    @Builder.Default private RequestSpecification spec = Specifications.getSpec().superUserSpec();

    public void deleteAll() {
        deleteProject(project.getId());
        new UncheckedRequests(spec).getUncheckedUserReq().deleteByUserName(user.getUserName());
    }

    public static void deleteProject(String projectId) {
        RequestSpecification spec = Specifications.getSpec().superUserSpec();
        new UncheckedRequests(spec).getUncheckedProjectReq().deleteById(projectId);
    }
}
