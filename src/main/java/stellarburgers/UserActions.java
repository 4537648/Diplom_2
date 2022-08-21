package stellarburgers;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserActions {

    @Step("Create user")
    public static Response createUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post("/api/auth/register");
    }

    @Step("Login user")
    public static Response loginUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post("/api/auth/login");
    }

    @Step("Get user's access token")
    public static String getUserAccessToken(User user) {
        return loginUser(user)
                .then().extract().body().path("accessToken").toString().substring(7);
    }

    @Step("Get user information")
    public static Response getUserInformation(String token) {
        return given()
                .auth().oauth2(token)
                .get("/api/auth/user");
    }

    @Step("Update user information")
    public static Response updateUserInformation(User user, String token) {
        return given()
                .auth().oauth2(token)
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .patch("/api/auth/user");
    }

    @Step("Delete user")
    public static Response deleteUser(String token) {
        return given()
                .auth().oauth2(token)
                .delete("/api/auth/user");
    }
}