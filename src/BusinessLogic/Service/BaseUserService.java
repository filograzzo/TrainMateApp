package BusinessLogic.Service;

import BusinessLogic.Service.PersonalTrainer.ProfilePTService;
import DAO.CustomerDAO;
import DAO.ExerciseDetailDAO;
import DAO.PersonalTrainerDAO;
import DAO.ScheduleDAO;
import DomainModel.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class BaseUserService {
    public CustomerDAO customerDAO;
    public PersonalTrainerDAO personalTrainerDAO;
    public ScheduleDAO scheduleDAO;
    public BaseUser currentUser;
    public ExerciseDetailDAO exerciseDetailDAO;
    private ProfilePTService profilePTService;
    private final Scanner input = new Scanner(System.in);

    public BaseUserService(CustomerDAO customerDAO, PersonalTrainerDAO personalTrainerDAO, ScheduleDAO scheduleDAO, ExerciseDetailDAO exerciseDetailDAO) {
        this.customerDAO = customerDAO;
        this.personalTrainerDAO = personalTrainerDAO;
        this.scheduleDAO = scheduleDAO;
        this.exerciseDetailDAO = exerciseDetailDAO;
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
            currentUser = personalTrainerDAO.getPersonalTrainer(username, password);
            profilePTService.setPersonalTrainer(currentUser);
            return (PersonalTrainer) currentUser;
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
                    if(personalTrainerDAO.create(username, password, email)) {
                        currentUser = personalTrainerDAO.getPersonalTrainer(username, password);
                        profilePTService.setPersonalTrainer(currentUser);
                    }
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
    public BaseUser getCurrentUser() {
        return currentUser ;
    }

    public void logout() {
        this.currentUser = null;
        System.out.println("The user has been logged out successfully.");
    }

    public boolean updateUserProfile(Customer customer) {
        try {
            return customerDAO.updateCustomer(customer);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePersonalTrainerProfile(PersonalTrainer personalTrainer) {
        try {
            return personalTrainerDAO.updatePersonalTrainer(personalTrainer);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Can be useful beofore changing the password
    public boolean validateCredentials(String username, String password) {
        try {
            return customerDAO.getCustomer(username, password) != null || personalTrainerDAO.getPersonalTrainer(username, password) != null;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Customer> getAllCustomers() {
        try {
            return customerDAO.getAllCustomers();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PersonalTrainer> getAllPersonalTrainers() {
        try {
            return personalTrainerDAO.getAllPersonalTrainers();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteCustomer(Customer customer) {
        try {
            return customerDAO.deleteCustomer(customer.getUsername(), customer.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePersonalTrainer(PersonalTrainer personalTrainer) {
        try {
            return personalTrainerDAO.deletePersonalTrainer(personalTrainer.getUsername(), personalTrainer.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //creation of a schedule

}
