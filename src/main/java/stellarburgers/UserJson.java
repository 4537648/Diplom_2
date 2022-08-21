package stellarburgers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserJson {

    private User user;
    private boolean success;
    private String message;

    public UserJson() {
    }

    public UserJson(User user, boolean success, String message) {
        this.user = user;
        this.success = success;
        this.message = message;
    }
}