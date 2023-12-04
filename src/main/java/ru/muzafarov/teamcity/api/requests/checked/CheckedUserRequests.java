package ru.muzafarov.teamcity.api.requests.checked;

import io.restassured.specification.RequestSpecification;
import ru.muzafarov.teamcity.api.models.User;
import ru.muzafarov.teamcity.api.requests.CrudInterface;
import ru.muzafarov.teamcity.api.requests.Request;
import ru.muzafarov.teamcity.api.requests.unchecked.UncheckedUserRequests;

public class CheckedUserRequests extends Request implements CrudInterface {

    public CheckedUserRequests(RequestSpecification spec) {
        super(spec);
    }

    @Override
    public User create(Object obj) {
        return new UncheckedUserRequests(spec)
                .create(obj)
                .then()
                .statusCode(200)
                .extract().as(User.class);
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
    public Object delete(String id) {
        return new UncheckedUserRequests(spec)
                .delete(id)
                .then()
                .statusCode(204)
                .extract().asString();
    }
}
