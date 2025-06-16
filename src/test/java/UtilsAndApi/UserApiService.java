package UtilsAndApi;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import praktikum.CreateUser;

import static UtilsAndApi.BaseTest.BASE_URL;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class UserApiService {
    public static Response createUser(CreateUser user) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/api/auth/register");
    }

    public static Response login(String email, String password) {
        Response response = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body("{\"email\":\"" + email + "\", \"password\":\"" + password + "\"}")
                .when()
                .post("/api/auth/login");

        // Проверки успешности (можно оставить или убрать по необходимости)
        response.then().statusCode(200);
        response.then().body("success", equalTo(true));
        response.then().body("accessToken", notNullValue());
        return response;
    }

    public static Response deleteUser(String accessToken) {
        return   given()
                .baseUri(BASE_URL)
                .header("Authorization", accessToken)
                .when()
                .delete("/api/auth/user");
    }

    public static String getAccessToken(Response response) {
        return response.path("accessToken");
    }
}
