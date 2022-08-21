package stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static stellarburgers.IngredientsAction.*;

@DisplayName("Order creation cases")
public class CreateOrderTest extends TestBase {

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

    @Test
    @DisplayName("Authorized user order creation test")
    public void authUserOrderCreationTest() {
        OrdersJson order = createOrder(accessToken, ingredientList).body().as(OrdersJson.class);
        assertThat(order.getOrder().getIngredients().get(0).get_id(), equalTo(ingredientList.getIngredients().get(0)));
        assertThat(order.getOrder().getIngredients().get(1).get_id(), equalTo(ingredientList.getIngredients().get(1)));
        assertThat(order.getOrder().getIngredients().get(2).get_id(), equalTo(ingredientList.getIngredients().get(2)));
        assertThat(order.getOrder().getNumber(), notNullValue());
        assertThat(order.getOrder().getOwner().getEmail(), equalTo(user.getEmail()));
        assertThat(order.isSuccess(), equalTo(true));
        assertThat(order.getName(), equalTo("Метеоритный space spicy бургер"));
    }

    @Test
    @DisplayName("Non-authorized user order creation test")
    public void nonAuthUserOrderCreationTest() {
        OrdersJson order = createOrder("", ingredientList).body().as(OrdersJson.class);
        assertThat(order.isSuccess(), equalTo(true));
        assertThat(order.getOrder().getNumber(), notNullValue());
        assertThat(order.getName(), equalTo("Метеоритный space spicy бургер"));
        assertThat(order.getOrder().getIngredients(), nullValue());
        assertThat(order.getOrder().getOwner(), nullValue());
    }

    @Test
    @DisplayName("No ingredients order creation test")
    public void noIngredientsOrderCreationTest() {
        createOrder(accessToken, emptyIngredientList)
                .then().statusCode(400)
                .assertThat().body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Wrong ingredients order creation test")
    public void wrongIngredientsOrderCreationTest() {
        createOrder(accessToken, wrongIngredientList)
                .then().statusCode(500);
    }

    @Test
    @DisplayName("Wrong ingredients hash order creation test")
    public void wrongIngredientsHashOrderCreationTest() {
        String invalidHashIngredients = "dummy";
        List<String> invalidIngredients = new ArrayList<>();
        invalidIngredients.add(invalidHashIngredients);
        HashMap<String, List> invalidOrderHash = new HashMap<>();
        invalidOrderHash.put("ingredients", invalidIngredients);
        createOrderHash(accessToken, invalidOrderHash)
                .then().statusCode(500);
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            UserActions.deleteUser(accessToken);
        }
    }
}