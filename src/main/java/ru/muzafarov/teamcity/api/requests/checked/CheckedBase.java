package ru.muzafarov.teamcity.api.requests.checked;

import io.restassured.specification.RequestSpecification;
import ru.muzafarov.teamcity.api.requests.CrudInterface;
import ru.muzafarov.teamcity.api.requests.GetDetailsInterface;
import ru.muzafarov.teamcity.api.requests.Request;
import ru.muzafarov.teamcity.api.requests.unchecked.UncheckedBase;

import java.util.List;

public class CheckedBase<T> extends Request implements CrudInterface, GetDetailsInterface {

    private final Class<T> responseType;
    private final String endpoint;

    public CheckedBase(RequestSpecification spec, Class<T> responseType, String endpoint) {
        super(spec);
        this.responseType = responseType;
        this.endpoint = endpoint;
    }

    @Override
    public T create(Object obj) {
        return new UncheckedBase(spec, endpoint)
                .create(obj)
                .then()
                .statusCode(200)
                .extract().as(responseType);
    }

    @Override
    public T get(String id) {
        return new UncheckedBase(spec, endpoint)
                .get(id)
                .then()
                .statusCode(200)
                .extract().as(responseType);
    }

    @Override
    public Object update(Object obj) {
        return null;
    }

    @Override
    public Object delete(String id) {
        return new UncheckedBase(spec, endpoint)
                .delete(id)
                .then()
                .statusCode(204)
                .extract()
                .asString();
    }


    @Override
    public List<T> getList(String typeList) {
        return new UncheckedBase(spec, endpoint).getList(typeList)
                .jsonPath()
                .getList(typeList, responseType);
    }
}