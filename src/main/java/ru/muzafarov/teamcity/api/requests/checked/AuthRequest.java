package ru.muzafarov.teamcity.api.requests.checked;

import io.restassured.RestAssured;
import lombok.AllArgsConstructor;
import ru.muzafarov.teamcity.api.models.User;
import ru.muzafarov.teamcity.api.spec.Specifications;

@AllArgsConstructor
public class AuthRequest {
    private User user;

    public String getCsrfToken() {
        return RestAssured
                .given()
                .spec(Specifications.getSpec().authSpec(
                        user))
                .get("/authenticationTest.html?csrf")
                .then().statusCode(200)
                .extract()
                .asString();
    }
}
