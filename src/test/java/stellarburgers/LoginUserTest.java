package stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("User login cases")
public class LoginUserTest extends TestBase {

    private final User user = new User("John Appleseed", "user@example.com", "AM941");
    String accessToken;

    @Before
    public void setUp() {
        UserActions.createUser(user);
        accessToken = UserActions.getUserAccessToken(user);
    }

    @Test
    @DisplayName("User login with proper credentials")
    public void userLoginWithProperCredentialsTest() {
        UserActions.loginUser(user)
                .then().assertThat().statusCode(200)
                .body("accessToken", notNullValue());
    }

    @Test
    @DisplayName("User login with wrong password")
    public void userLoginWithWrongPasswordTest() {
        UserActions.loginUser(new User(user.getName(), user.getEmail(), "wrongPassword"))
                .then().assertThat().statusCode(401)
                .body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("User login with wrong email")
    public void userLoginWithWrongEmailTest() {
        UserActions.loginUser(new User(user.getName(), "wrong@email.com", user.getPassword()))
                .then().assertThat().statusCode(401)
                .body("message", equalTo("email or password are incorrect"));
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            UserActions.deleteUser(accessToken);
        }
    }
}