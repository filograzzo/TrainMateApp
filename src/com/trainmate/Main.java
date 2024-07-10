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
                                System.out.println("Login avvenuto con successo.");
                                System.out.println("I dati dell'user sono: "
                                        + user.getId() + " " + user.getUsername() + " "
                                        + user.getPassword() + " " + user.getEmail());
                                User.setUserService(userService);
                                handleUserActions(user, scanner);
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
                                System.out.println("Login avvenuto con successo.");
                                System.out.println("I dati del personal trainer sono: "
                                        + pt.getId() + " " + pt.getUsername() + " "
                                        + pt.getPassword() + " " + pt.getEmail());
                                PersonalTrainer.setPersonalTrainerService(personalTrainerService);
                                handlePersonalTrainerActions(pt, scanner);
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

    private static void handleUserActions(User user, Scanner scanner) {
        while (true) {
            System.out.println("Scegli un'azione: (1) Visualizza username (2) Cambia username (3) Visualizza password (4) Cambia password (5) Visualizza email (6) Cambia email (7) Esci");
            String action = scanner.nextLine();

            switch (action) {
                case "1":
                    System.out.println("Username: " + user.getUsername());
                    break;
                case "2":
                    System.out.print("Inserisci nuovo username: ");
                    String newUsername = scanner.nextLine();
                    user.setUsername(newUsername);
                    break;
                case "3":
                    System.out.println("Password: " + user.getPassword());
                    break;
                case "4":
                    System.out.print("Inserisci nuova password: ");
                    String newPassword = scanner.nextLine();
                    user.setPassword(newPassword);
                    break;
                case "5":
                    System.out.println("Email: " + user.getEmail());
                    break;
                case "6":
                    System.out.print("Inserisci nuova email: ");
                    String newEmail = scanner.nextLine();
                    user.setEmail(newEmail);
                    break;
                case "7":
                    return;
                default:
                    System.out.println("Azione non valida. Per favore inserisci un numero da 1 a 7.");
            }
        }
    }

    private static void handlePersonalTrainerActions(PersonalTrainer pt, Scanner scanner) {
        while (true) {
            System.out.println("Scegli un'azione: (1) Visualizza username (2) Cambia username (3) Visualizza password (4) Cambia password (5) Visualizza email (6) Cambia email (7) Esci");
            String action = scanner.nextLine();

            switch (action) {
                case "1":
                    System.out.println("Username: " + pt.getUsername());
                    break;
                case "2":
                    System.out.print("Inserisci nuovo username: ");
                    String newUsername = scanner.nextLine();
                    pt.setUsername(newUsername);
                    break;
                case "3":
                    System.out.println("Password: " + pt.getPassword());
                    break;
                case "4":
                    System.out.print("Inserisci nuova password: ");
                    String newPassword = scanner.nextLine();
                    pt.setPassword(newPassword);
                    break;
                case "5":
                    System.out.println("Email: " + pt.getEmail());
                    break;
                case "6":
                    System.out.print("Inserisci nuova email: ");
                    String newEmail = scanner.nextLine();
                    pt.setEmail(newEmail);
                    break;
                case "7":
                    return;
                default:
                    System.out.println("Azione non valida. Per favore inserisci un numero da 1 a 7.");
            }
        }
    }
}
