package DomainModel;

import javax.swing.*;
import java.awt.*;

public abstract class BaseUser {
    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private String username;
    private String password;
    private String email;
    private String role;

    public BaseUser(int id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }
    public boolean isValid() {
        return !username.isEmpty() && !password.isEmpty() && !email.isEmpty();
    }
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
