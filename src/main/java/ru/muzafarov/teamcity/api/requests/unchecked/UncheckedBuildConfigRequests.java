package ru.muzafarov.teamcity.api.requests.unchecked;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import ru.muzafarov.teamcity.api.requests.CrudInterface;
import ru.muzafarov.teamcity.api.requests.Request;

import static io.restassured.RestAssured.given;
import static ru.muzafarov.teamcity.api.utils.StringUtils.format;

public class UncheckedBuildConfigRequests extends Request implements CrudInterface {

    private static final String BUILD_ENDPOINT = "/app/rest/buildTypes";

    public UncheckedBuildConfigRequests(RequestSpecification spec) {
        super(spec);
    }

    @Override
    public Response create(Object obj) {
        return given().spec(spec)
                .body(obj)
                .post(BUILD_ENDPOINT);
    }

    @Override
    public Response get(String id) {
        return given().spec(spec)
                .get(BUILD_ENDPOINT+ format("/id:{}", id));
    }

    @Override
    public Object update(Object obj) {
        return null;
    }

    @Override
    public Response delete(String id) {
        return given().spec(spec)
                .delete(BUILD_ENDPOINT + format("/id:{}", id));
    }
}
