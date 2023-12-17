package ru.muzafarov.teamcity.api.requests.unchecked;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import ru.muzafarov.teamcity.api.requests.CrudInterface;
import ru.muzafarov.teamcity.api.requests.GetDetailsInterface;
import ru.muzafarov.teamcity.api.requests.Request;

import static io.restassured.RestAssured.given;
import static ru.muzafarov.teamcity.api.utils.StringUtils.format;

public class UncheckedBase extends Request implements CrudInterface, GetDetailsInterface {
    private final String endpoint;

    public UncheckedBase(RequestSpecification spec, String endpoint) {
        super(spec);
        this.endpoint = endpoint;
    }
    @Override
    public Response create(Object obj) {
        return given().spec(spec)
                .body(obj)
                .post(endpoint);
    }

    @Override
    public Response get(String id) {
        return given().spec(spec)
                .get(endpoint+ format("/id:{}", id));
    }

    @Override
    public Object update(Object obj) {
        return null;
    }

    @Override
    public Response deleteById(String id) {
        return given().spec(spec)
                .delete(endpoint + format("/id:{}", id));
    }

    public Response deleteByUserName(String name) {
        return given().spec(spec)
                .delete(endpoint + format("/username:{}", name));
    }

    @Override
    public Response getList(String typeList) {
        return given().spec(spec)
                .get(endpoint);
    }
}
