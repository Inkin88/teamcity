package ru.muzafarov.teamcity.api.requests.unchecked;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import ru.muzafarov.teamcity.api.requests.CrudInterface;
import ru.muzafarov.teamcity.api.requests.Request;

import static io.restassured.RestAssured.given;
import static ru.muzafarov.teamcity.api.utils.StringUtils.format;

public class UncheckedUserRequests extends Request implements CrudInterface {
    private static final String USER_ENDPOINT = "/app/rest/users";

    public UncheckedUserRequests(RequestSpecification spec) {
        super(spec);
    }

    @Override
    public Response create(Object obj) {
        return given()
                .spec(spec)
                .body(obj)
                .post(USER_ENDPOINT);
    }

    @Override
    public Object get(String id) {
        return null;
    }

    @Override
    public Object update(Object obj) {
        return null;
    }

    @Override
    public Response delete(String userName) {
        return given().spec(spec)
                .delete(USER_ENDPOINT + format("/username:{}", userName));
    }
}
