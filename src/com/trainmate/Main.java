// File: com/trainmate/Main.java
package com.trainmate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import com.people.*;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            UserService userService = new UserService(connection);
            PersonalTrainerService personalTrainerService = new PersonalTrainerService(connection);
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Scegli un'opzione: (1) Registrati (2) Login");
                String choice = scanner.nextLine();

                if (choice.equals("1") || choice.equals("2")) {
                    System.out.println("Sei un utente normale o un personal trainer? (1) Utente normale (2) Personal trainer");
                    String userType = scanner.nextLine();

                    if (userType.equals("1")) {
                        if (choice.equals("1")) {
                            // Registrazione utente normale
                            System.out.println("Registrazione utente normale");
                            System.out.print("Inserisci username: ");
                            String username = scanner.nextLine();
                            System.out.print("Inserisci password: ");
                            String password = scanner.nextLine();
                            System.out.print("Inserisci email: ");
                            String email = scanner.nextLine();

                            if (userService.registerUser(username, password, email)) {
                                System.out.println("Registrazione avvenuta con successo.");
                            } else {
                                System.out.println("Errore durante la registrazione.");
                            }
                        } else {
                            // Login utente normale
                            System.out.println("Login utente normale");
                            System.out.print("Inserisci username: ");
                            String username = scanner.nextLine();
                            System.out.print("Inserisci password: ");
                            String password = scanner.nextLine();

                            User user = userService.loginUser(username, password);
                            if (user != null) {
                                System.out.println("Login avvenuto con successo. I dati dell'user sono: "
                                        + user.getId() + " " + user.getUsername() + " "
                                        + user.getPassword() + " " + user.getEmail());
                            } else {
                                System.out.println("Errore durante il login. Username o password errati.");
                            }
                        }
                    } else if (userType.equals("2")) {
                        if (choice.equals("1")) {
                            // Registrazione personal trainer
                            System.out.println("Registrazione personal trainer");
                            System.out.print("Inserisci username: ");
                            String username = scanner.nextLine();
                            System.out.print("Inserisci password: ");
                            String password = scanner.nextLine();
                            System.out.print("Inserisci email: ");
                            String email = scanner.nextLine();
                            System.out.print("Inserisci access code: ");
                            String accessCode = scanner.nextLine();

                            if (personalTrainerService.registerPersonalTrainer(username, password, email, accessCode)) {
                                System.out.println("Registrazione avvenuta con successo.");
                            } else {
                                System.out.println("Errore durante la registrazione.");
                            }
                        } else {
                            // Login personal trainer
                            System.out.println("Login personal trainer");
                            System.out.print("Inserisci username: ");
                            String username = scanner.nextLine();
                            System.out.print("Inserisci password: ");
                            String password = scanner.nextLine();

                            PersonalTrainer pt = personalTrainerService.loginPersonalTrainer(username, password);
                            if (pt != null) {
                                System.out.println("Login avvenuto con successo. I dati del personal trainer sono: "
                                        + pt.getId() + " " + pt.getUsername() + " "
                                        + pt.getPassword() + " " + pt.getEmail());
                            } else {
                                System.out.println("Errore durante il login. Username o password errati.");
                            }
                        }
                    } else {
                        System.out.println("Scelta non valida. Per favore inserisci 1 per utente normale o 2 per personal trainer.");
                    }
                } else {
                    System.out.println("Scelta non valida. Per favore inserisci 1 per registrarti o 2 per fare il login.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
