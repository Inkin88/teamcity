package ru.muzafarov.teamcity.api.requests.unchecked;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import ru.muzafarov.teamcity.api.requests.CrudInterface;
import ru.muzafarov.teamcity.api.requests.Request;

import static io.restassured.RestAssured.given;
import static ru.muzafarov.teamcity.api.utils.StringUtils.format;

public class UncheckedProjectRequest extends Request implements CrudInterface {
    private static final String PROJECT_ENDPOINT = "/app/rest/projects";

    public UncheckedProjectRequest(RequestSpecification spec) {
        super(spec);
    }

    @Override
    public Response create(Object project) {
        return given()
                .spec(spec)
                .body(project)
                .post(PROJECT_ENDPOINT);
    }

    @Override
    public Response get(String id) {
        return given()
                .spec(spec)
                .get(PROJECT_ENDPOINT + "/id:" + id);
    }

    @Override
    public Object update(Object obj) {
        return null;
    }

    @Override
    public Response delete(String id) {
        return given()
                .spec(spec)
                .delete(PROJECT_ENDPOINT + "/id:" + id);

    }

    public Response getBuildTypesList(String id) {
        return given()
                .spec(spec)
                .get(PROJECT_ENDPOINT + format("/id:{}/buildTypes", id));
    }
}
