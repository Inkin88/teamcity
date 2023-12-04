package ru.muzafarov.teamcity.api.requests.checked;

import io.restassured.specification.RequestSpecification;
import ru.muzafarov.teamcity.api.models.BuildType;
import ru.muzafarov.teamcity.api.requests.CrudInterface;
import ru.muzafarov.teamcity.api.requests.Request;
import ru.muzafarov.teamcity.api.requests.unchecked.UncheckedBuildConfigRequests;

public class CheckedBuildConfigRequests extends Request implements CrudInterface {
    public CheckedBuildConfigRequests(RequestSpecification spec) {
        super(spec);
    }

    @Override
    public BuildType create(Object obj) {
        return new UncheckedBuildConfigRequests(spec)
                .create(obj)
                .then()
                .statusCode(200)
                .extract().as(BuildType.class);
    }

    @Override
    public BuildType get(String id) {
        return new UncheckedBuildConfigRequests(spec)
                .get(id)
                .then()
                .statusCode(200)
                .extract().as(BuildType.class);
    }

    @Override
    public Object update(Object obj) {
        return null;
    }

    @Override
    public String delete(String id) {
        return new UncheckedBuildConfigRequests(spec)
                .delete(id)
                .then()
                .statusCode(204)
                .extract()
                .asString();
    }
}
