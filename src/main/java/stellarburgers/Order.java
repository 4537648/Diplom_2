package stellarburgers;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Order {

    private List<IngredientsJson> ingredients;
    private String _id;
    private Owner owner;
    private String status;
    private String name;
    private int number;
    private int price;
    private String createdAt;
    private String updatedAt;

    public Order() {
    }

    public Order(List<IngredientsJson> ingredients, String _id, Owner owner, String status, String name, int number, int price, String createdAt, String updatedAt) {
        this.ingredients = ingredients;
        this._id = _id;
        this.owner = owner;
        this.status = status;
        this.name = name;
        this.number = number;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}