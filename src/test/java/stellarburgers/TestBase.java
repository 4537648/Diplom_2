package stellarburgers;

import io.restassured.RestAssured;
import org.junit.Before;

public class TestBase {

    @Before
    public void init() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

}