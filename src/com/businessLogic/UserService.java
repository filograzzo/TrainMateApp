package com.businessLogic;

import com.backend.UserDAO;
import com.domainModel.User;
import java.sql.SQLException;

public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean registerUser(String username, String password, String email) {
        try {
            if (userDAO.userExists(username, password)) {
                System.err.println("The user you are trying to create already exists.");
                return false;
            } else {
                boolean success = userDAO.insertUser(username, password, email);
                if (success) {
                    System.out.println("The user has been registered successfully.");
                }
                return success;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User loginUser(String username, String password) {
        try {
            return userDAO.getUser(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean removeUser(String username, String password) {
        try {
            boolean success = userDAO.deleteUser(username, password);
            if (success) {
                System.out.println("The user has been deleted successfully.");
            } else {
                System.err.println("No user was removed. Incorrect username or password.");
            }
            return success;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUserUsername(int id, String newUsername) {
        try {
            return userDAO.updateUsername(id, newUsername);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUserPassword(int id, String newPassword) {
        try {
            return userDAO.updatePassword(id, newPassword);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUserEmail(int id, String newEmail) {
        try {
            return userDAO.updateEmail(id, newEmail);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
