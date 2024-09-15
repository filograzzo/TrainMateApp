package BusinessLogic.Service;

import DAO.CustomerDAO;
import DAO.PersonalTrainerDAO;
import DomainModel.Constants;
import DomainModel.Customer;
import DomainModel.PersonalTrainer;

import java.sql.SQLException;

public class UserService {
    public CustomerDAO customerDAO;
    public PersonalTrainerDAO personalTrainerDAO;
    public UserService(CustomerDAO customerDAO, PersonalTrainerDAO personalTrainerDAO) {
        this.customerDAO = customerDAO;
        this.personalTrainerDAO = personalTrainerDAO;
    }

    public Customer loginUser(String username, String password) {
        try {
            Customer c = customerDAO.getCustomer(username, password);
            if (c == null) {
                System.err.println("The user you are trying to log in does not exist.");
            } else {
                System.out.println("The user has been logged in successfully.");
            }
            return c;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public PersonalTrainer loginPersonalTrainer(String username, String password) {
        try {
            return personalTrainerDAO.getPersonalTrainer(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean registerUser(String username, String password, String email) {
        try {
            if (customerDAO.userExists(username, password)) {
                System.err.println("The user you are trying to create already exists.");
                return false;
            } else {
                    System.out.println("The user has been registered successfully.");
                    return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean registerPersonalTrainer(String username, String password, String email, String accessCode) {
        try {

            if (personalTrainerDAO.exists(username, password)) {
                System.err.println("The personal trainer you are trying to create already exists.");
                return false;
            } else {
                if (Constants.ACCESSCODE.equals(accessCode)) {
                        System.out.println("The personal trainer has been registered successfully.");
                        return true;
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
}
