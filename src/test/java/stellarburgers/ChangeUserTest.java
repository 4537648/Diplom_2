package stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

@DisplayName("User credentials change cases")
public class ChangeUserTest extends TestBase {

    User user = new User("John Appleseed", "user@example.com", "AM941");
    String accessToken;

    @Before
    public void setUp() {
        UserActions.createUser(user);
        accessToken = UserActions.getUserAccessToken(user);
    }

    @Test
    @DisplayName("Authorized user email changing test")
    public void authUserEmailChangingTest() {
        String newEmail = "john.appleseed@example.com";
        UserJson userJson = UserActions.updateUserInformation(new User(null, newEmail, null), accessToken).as(UserJson.class);
        MatcherAssert.assertThat(userJson.getUser(), notNullValue());
        Assert.assertEquals(newEmail, userJson.getUser().getEmail());
    }

    @Test
    @DisplayName("Authorized user email changing to existing test")
    public void authUserEmailToExistingChangingTest() {
        User user2 = new User("Anne Haro", "haro@example.com", "PM941");
        UserActions.createUser(user2);
        UserJson userJson = UserActions.updateUserInformation(new User(null, user2.getEmail(), null), accessToken).as(UserJson.class);
        Assert.assertEquals("User with such email already exists", userJson.getMessage());
        UserActions.deleteUser(UserActions.getUserAccessToken(user2));
    }

    @Test
    @DisplayName("Authorized user name changing test")
    public void authUserNameChangingTest() {
        String newName = "Johnny Ive";
        UserJson userJson = UserActions.updateUserInformation(new User(newName, user.getEmail(), null), accessToken).as(UserJson.class);
        MatcherAssert.assertThat(userJson.getUser(), notNullValue());
        Assert.assertEquals(newName, userJson.getUser().getName());
    }

    @Test
    @DisplayName("Authorized user password changing test")
    public void authUserPasswordChangingTest() {
        UserActions.updateUserInformation(new User(user.getName(), user.getEmail(), "PM941"), accessToken).then().assertThat().statusCode(200);
        UserActions.loginUser(new User(user.getName(), user.getEmail(), "PM941")).then().assertThat().statusCode(200);
    }

    @Test
    @DisplayName("Non-authorized user email changing test")
    public void nonAuthUserEmailChangingTest() {
        String newEmail = "unauth@example.com";
        UserActions.updateUserInformation(new User(null, newEmail, null), "")
                .then().assertThat().statusCode(401)
                .body("message", CoreMatchers.equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Non-authorized user name changing test")
    public void nonAuthUserNameChangingTest() {
        String newName = "Bill Gates";
        UserActions.updateUserInformation(new User(newName, null, null), "")
                .then().assertThat().statusCode(401)
                .body("message", CoreMatchers.equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Non-authorized user password changing test")
    public void nonAuthUserPasswordChangingTest() {
        UserActions.updateUserInformation(new User(null, null, "Win95"), "")
                .then().assertThat().statusCode(401)
                .body("message", CoreMatchers.equalTo("You should be authorised"));
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            UserActions.deleteUser(accessToken);
        }
    }
}