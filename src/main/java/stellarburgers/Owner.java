package stellarburgers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Owner {

    private String name;
    private String email;
    private String createdAt;
    private String updatedAt;

    public Owner() {
    }

    public Owner(String name, String email, String createdAt, String updatedAt) {
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}