package com.businessLogic;

import com.domainModel.PersonalTrainer;
import com.backend.PersonalTrainerDAO;
import com.domainModel.Constants;
import java.sql.SQLException;

public class PersonalTrainerService {
    private final PersonalTrainerDAO personalTrainerDAO;

    public PersonalTrainerService(PersonalTrainerDAO personalTrainerDAO) {
        this.personalTrainerDAO = personalTrainerDAO;
    }

    public boolean registerPersonalTrainer(String username, String password, String email, String accessCode) {
        try {
            if (personalTrainerDAO.exists(username, password)) {
                System.err.println("The personal trainer you are trying to create already exists.");
                return false;
            } else {
                if (Constants.ACCESSCODE.equals(accessCode)) {
                    boolean created = personalTrainerDAO.create(username, password, email);
                    if (created) {
                        System.out.println("The personal trainer has been registered successfully.");
                    }
                    return created;
                } else {
                    System.err.println("The access code you entered is incorrect. No personal trainer was registered.");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public PersonalTrainer loginPersonalTrainer(String username, String password) {
        try {
            return personalTrainerDAO.findByUsernameAndPassword(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean removePersonalTrainer(String username, String password, String accessCode) {
        if (Constants.ACCESSCODE.equals(accessCode)) {
            try {
                boolean deleted = personalTrainerDAO.delete(username, password);
                if (deleted) {
                    System.out.println("The personal trainer has been deleted successfully.");
                }
                return deleted;
            } catch (SQLException e) {
                System.err.println("Incorrect username or password, no personal trainer was removed: " + e.getMessage());
                return false;
            }
        } else {
            System.err.println("The access code you provided is not correct. No personal trainer was removed.");
            return false;
        }
    }

    public boolean updatePersonalTrainerUsername(int id, String newUsername) {
        try {
            return personalTrainerDAO.updateUsername(id, newUsername);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePersonalTrainerPassword(int id, String newPassword) {
        try {
            return personalTrainerDAO.updatePassword(id, newPassword);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePersonalTrainerEmail(int id, String newEmail) {
        try {
            return personalTrainerDAO.updateEmail(id, newEmail);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

