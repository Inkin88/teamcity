package ru.muzafarov.teamcity.api.spec;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import ru.muzafarov.teamcity.api.config.Config;
import ru.muzafarov.teamcity.api.models.User;

import static ru.muzafarov.teamcity.api.utils.StringUtils.format;

public class Specifications {
    private static Specifications spec;

    private Specifications() {

    }

    public static Specifications getSpec() {
        if (spec == null) {
            spec = new Specifications();
        }
        return spec;
    }

    private RequestSpecBuilder requestSpecBuilder() {
        var requestBuilder = new RequestSpecBuilder();
        requestBuilder.setBaseUri("http://" + Config.getProperty("host"));
        requestBuilder.addFilter(new RequestLoggingFilter());
        requestBuilder.addFilter(new ResponseLoggingFilter());
        requestBuilder.setContentType(ContentType.JSON);
        requestBuilder.setAccept(ContentType.JSON);
        return requestBuilder;
    }

    public RequestSpecification unAuthSpec() {
        var requestBuilder = requestSpecBuilder();
        return requestBuilder.build();
    }

    public RequestSpecification authSpec(User user) {
        var requestBuilder = requestSpecBuilder();
        requestBuilder.setBaseUri("http://" + user.getUserName() + ":" + user.getPassword() + "@" + Config.getProperty("host"));
        return requestBuilder.build();
    }

    public RequestSpecification superUserSpec() {
        var requestBuilder = requestSpecBuilder();
        requestBuilder.setBaseUri(format("http://:{}@{}", Config.getProperty("superUserToken"), Config.getProperty("host")));
        return requestBuilder.build();
    }
}
