package stellarburgers;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Ingredient {

    private List<String> ingredients;

    public Ingredient() {
    }

    public Ingredient(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}