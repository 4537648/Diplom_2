package stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.equalTo;

@DisplayName("User creation required fields cases")
@RunWith(Parameterized.class)
public class CreateUserRequiredFieldsParamTest extends TestBase {

    private final String name;
    private final String email;
    private final String password;

    public CreateUserRequiredFieldsParamTest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {null, "user@example.com", "AM941"},
                {"", "user@example.com", "AM941"},
                {"John Appleseed", null, "AM941"},
                {"John Appleseed", "", "AM941"},
                {"John Appleseed", "user@example.com", null},
                {"John Appleseed", "user@example.com", ""}
        };
    }

    @Test
    @DisplayName("User creation required fields test")
    public void userCreationRequiredFieldsTest() throws InterruptedException {
        Thread.sleep(1000);
        int statusCode = 403;
        String expectedMessage = "Email, password and name are required fields";
        UserActions.createUser(new User(name, email, password))
                .then().assertThat().statusCode(statusCode)
                .body("message", equalTo(expectedMessage));
    }
}