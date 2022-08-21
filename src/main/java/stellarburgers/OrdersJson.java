package stellarburgers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdersJson {

    private boolean success;
    private String name;
    private Order order;

    public OrdersJson() {
    }

    public OrdersJson(boolean success, String name, Order order) {
        this.success = success;
        this.name = name;
        this.order = order;
    }
}