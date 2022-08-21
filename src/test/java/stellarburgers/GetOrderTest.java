package stellarburgers;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static stellarburgers.IngredientsAction.createOrder;
import static stellarburgers.IngredientsAction.getIngredientList;

@DisplayName("User order list cases")
public class GetOrderTest extends TestBase {

    private final User user = new User("John Appleseed", "user@example.com", "AM941");
    private String accessToken;
    private Ingredient ingredientList;
    private final Ingredient emptyIngredientList = new Ingredient();
    private final Ingredient wrongIngredientList = new Ingredient(List.of("wrongIngredientOne", "wrongIngredientTwo"));

    @Before
    public void setUp() {
        UserActions.createUser(user);
        accessToken = UserActions.getUserAccessToken(user);
        ingredientList = new Ingredient(List.of(getIngredientList().get(2).get_id(), getIngredientList().get(4).get_id(), getIngredientList().get(6).get_id()));
    }

    @Step("Get user order list")
    public Response getUserOrderList(String accessToken) {
        return given().auth().oauth2(accessToken)
                .header("Content-type", "application/json")
                .and()
                .when()
                .get("/api/orders");
    }

    @Test
    @DisplayName("Authorized user order list test")
    public void authUserOrderListTest() {
        createOrder(accessToken, ingredientList);
        getUserOrderList(accessToken)
                .then().statusCode(200)
                .assertThat()
                .body("success", equalTo(true))
                .body("orders.ingredients[0].size()", equalTo(ingredientList.getIngredients().size()));
    }

    @Test
    @DisplayName("Non-authorized user order list test")
    public void nonAuthUserOrderListTest() {
        getUserOrderList("")
                .then().statusCode(401)
                .assertThat()
                .body("success", equalTo(false));
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            UserActions.deleteUser(accessToken);
        }
    }
}