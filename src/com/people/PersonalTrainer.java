package com.people;
import com.trainmate.DatabaseUtil;

import java.sql.Connection;
import java.sql.SQLException;

import static com.people.Constants.ACCESSCODE;

public class PersonalTrainer {
    private int id;
    private String username;
    private String password;
    private String email;
    private String accessCode;

    //Un personal trainer dev'essere creabile solamente facendo il login: non posso creare un oggetto pt che non sia nel database
    PersonalTrainer(int id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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