package stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

@DisplayName("User creation cases")
public class CreateUserTest extends TestBase {

    private final User user = new User("John Appleseed", "user@example.com", "AM941");

    @Test
    @DisplayName("User creation test")
    public void userCreationTest() throws InterruptedException {
        Thread.sleep(1000);
        UserActions.createUser(user)
                .then().assertThat().statusCode(200)
                .body("success", equalTo(true));
        UserActions.deleteUser(UserActions.getUserAccessToken(user));
    }

    @Test
    @DisplayName("Duplicate user creation test")
    public void duplicateUserCreationTest() throws InterruptedException {
        Thread.sleep(1000);
        UserActions.createUser(user);
        Thread.sleep(1000);
        UserActions.createUser(user)
                .then().assertThat().statusCode(403)
                .body("message", equalTo("User already exists"));
        UserActions.deleteUser(UserActions.getUserAccessToken(user));
    }
}