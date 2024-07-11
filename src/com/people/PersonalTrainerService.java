package com.people;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.people.Constants.ACCESSCODE;

//todo: dividere in service e dao

//todo:dividere le classi in perople, service e dao

public class PersonalTrainerService {
    private final Connection connection;

    public PersonalTrainerService(Connection connection) {
        this.connection = connection;
    }

    public boolean registerPersonalTrainer(String username, String password, String email, String accessCode) {
        //1- Controllo che non esista già l'utente
        //2- Controllo che l'accesscode sia corretto
        //3- Creo il personal trainer
        String query = "SELECT * FROM PersonalTrainer WHERE username = ? AND password = ? ";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) { //executequery si usa per i select di solito, quando si vuole ottenere dati dal database
                if(rs.next()){
                    System.err.println("The personal trainer you are trying to create already exists.");
                    return false;
                }else{
                    if(ACCESSCODE.equals(accessCode)) { //controllo che l'accesscode sia corretto
                        String query1 = "INSERT INTO PersonalTrainer (  username, password, email) VALUES ( ?, ?, ?)";
                        try (PreparedStatement stmt1 = connection.prepareStatement(query1)) {
                            stmt1.setString(1, username);
                            stmt1.setString(2, password);
                            stmt1.setString(3, email);
                            int rows = stmt1.executeUpdate();//executeupdate serve per aggiornare i dati nel database
                            if(rows>0)
                                System.out.println("The personal trainer has been registered successfully.");
                            return rows > 0;
                        } catch (SQLException e) {
                            e.printStackTrace();
                            return false;
                        }
                    }else{
                        System.err.println("The access code you entered is incorrect. No personal trainer was registered.");
                        return false;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public PersonalTrainer loginPersonalTrainer(String username, String password) {
        //1- Controllo che colui che il pt che sto cercando esista nel database
        //2- Creo e ritorno un oggetto pt con le caratteristiche cercate
        String query = "SELECT * FROM PersonalTrainer WHERE username = ? AND password = ? ";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) { //executequery si usa per i select di solito, quando si vuole ottenere dati dal database
                if(rs.next())
                    return new PersonalTrainer(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("email"));
                else
                    return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean removePersonalTrainer(String username, String password, String accessCode) {
        //1- Controllo che l'access code sia corretto
        //2- Rimuovo il personal trainer dal database
        if (ACCESSCODE.equals(accessCode)){
            String query = "DELETE FROM PersonalTrainer WHERE username = ? AND password = ? "; //definizione della query
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, username);//assegna i parametri della query contrassegnati con ?
                stmt.setString(2, password);
                int rows = stmt.executeUpdate();//esegue la query, ritorna il numero di righe influenzate dall'operazione
                if(rows>0)
                    System.out.println("The personal trainer has been deleted successfully.");
                return rows > 0;
            } catch (SQLException e) {
                System.err.println("Incorrect username or password, no personal trainer was removed: " + e.getMessage() );
                return false;
            }
        } else {
            // Messaggio di errore per accessCode non valido
            System.err.println("The access code you provided is not correct. No personal trainer was removed.");
            return false;
        }
    }

    public boolean updatePersonalTrainerUsername(int id, String newUsername) {
        String query = "UPDATE PersonalTrainer SET username = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newUsername);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePersonalTrainerPassword(int id, String newPassword) {
        String query = "UPDATE PersonalTrainer SET password = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newPassword);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePersonalTrainerEmail(int id, String newEmail) {
        String query = "UPDATE PersonalTrainer SET email = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newEmail);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}


