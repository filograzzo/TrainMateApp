package com.people;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private static UserService userService;

    public static void setUserService(UserService service) {
        userService = service;
    }

    //Questa classe è una classe di oggetti temporanei: i dati restano sempre salvati nel database e solo al momento del login
    //voglio compiere un'azione allora viene creato un oggetto User che, essendo il costruttore package private,
    //può essere chiamato solo all'interno dello stesso package da altri metodi (nel nostro caso solo dal metodo per il login).


    User(int id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    //TODO: Controllare che i nuovi metodi di set funzionino

    //L'id non può essere cambiato
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (userService != null && userService.updateUserUsername(this.id, username)) {
            this.username = username;
        } else {
            System.err.println("Failed to update username in the database.");
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (userService != null && userService.updateUserPassword(this.id, password)) {
            this.password = password;
        } else {
            System.err.println("Failed to update password in the database.");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (userService != null && userService.updateUserEmail(this.id, email)) {
            this.email = email;
        } else {
            System.err.println("Failed to update email in the database.");
        }
    }
}