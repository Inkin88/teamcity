package ru.muzafarov.teamcity.api.requests.checked;

import io.restassured.specification.RequestSpecification;
import ru.muzafarov.teamcity.api.models.BuildTypeList;
import ru.muzafarov.teamcity.api.models.Project;
import ru.muzafarov.teamcity.api.requests.CrudInterface;
import ru.muzafarov.teamcity.api.requests.Request;
import ru.muzafarov.teamcity.api.requests.unchecked.UncheckedProjectRequest;

public class CheckedProjectRequests extends Request implements CrudInterface {

    public CheckedProjectRequests(RequestSpecification spec) {
        super(spec);
    }

    @Override
    public Project create(Object obj) {
        return new UncheckedProjectRequest(spec)
                .create(obj)
                .then()
                .assertThat()
                .statusCode(200)
                .extract().as(Project.class);
    }

    @Override
    public Project get(String id) {
        return new UncheckedProjectRequest(spec)
                .get(id)
                .then()
                .statusCode(200)
                .extract().as(Project.class);
    }

    @Override
    public Object update(Object obj) {
        return null;
    }

    @Override
    public String delete(String id) {
        return new UncheckedProjectRequest(spec)
                .delete(id)
                .then()
                .statusCode(204)
                .extract().asString();
    }

    public BuildTypeList getBuildTypesList(String id) {
        return new UncheckedProjectRequest(spec)
                .getBuildTypesList(id)
                .then()
                .statusCode(200)
                .extract().as(BuildTypeList.class);
    }
}
