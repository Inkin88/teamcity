package ru.muzafarov.teamcity.api.requests.checked;

import io.restassured.RestAssured;
import lombok.AllArgsConstructor;
import ru.muzafarov.teamcity.api.models.User;
import ru.muzafarov.teamcity.api.spec.Specifications;

@AllArgsConstructor
public class AuthRequest {
    private User user;
    private static final String CSRF_TOKEN_ENDPOINT = "/authenticationTest.html?csrf";

    public String getCsrfToken() {
        return RestAssured
                .given()
                .spec(Specifications.getSpec().authSpec(
                        user))
                .get(CSRF_TOKEN_ENDPOINT)
                .then().statusCode(200)
                .extract()
                .asString();
    }
}
