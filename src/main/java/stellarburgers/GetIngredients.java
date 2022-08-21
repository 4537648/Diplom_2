package stellarburgers;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetIngredients {

    private boolean success;
    private List<IngredientsJson> data;

    public GetIngredients() {
    }
}