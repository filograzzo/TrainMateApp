package BusinessLogic.Service;

import BusinessLogic.Service.Customer.BookAppointmentService;
import BusinessLogic.Service.Customer.ProfileService;
import BusinessLogic.Service.PersonalTrainer.AgendaService;
import BusinessLogic.Service.PersonalTrainer.MachineService;
import BusinessLogic.Service.PersonalTrainer.ProfilePTService;
import DAO.*;
import DomainModel.BaseUser;
import DomainModel.Constants;
import DomainModel.Customer;
import DomainModel.PersonalTrainer;

import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class BaseUserService {
    public CustomerDAO customerDAO;
    public PersonalTrainerDAO personalTrainerDAO;
    public ScheduleDAO scheduleDAO;
    public ExerciseDetailDAO exerciseDetailDAO;
    private ProfileService profileService;
    private AgendaService agendaService;
    private MachineService machineService;
    private BookAppointmentService bookAppointmentService;
    private BaseUser currentUser;
    private ProfilePTService profilePTService;
    public BaseUserService(CustomerDAO customerDAO, PersonalTrainerDAO personalTrainerDAO, ProfileService profileService,ProfilePTService profilePTService,BookAppointmentService bookAppointmentService,AgendaService agendaService) {
        this.profileService = profileService;
        this.profilePTService = profilePTService;
        this.customerDAO = customerDAO;
        this.personalTrainerDAO = personalTrainerDAO;
        this.bookAppointmentService=bookAppointmentService;
        this.agendaService=agendaService;
    }

    public Customer loginUser(String username, String password,String email) {
        try {
            currentUser = customerDAO.getCustomer(username, password,email);
            if (currentUser == null ) {
                return null;
            } else {
                profileService.setCustomer((Customer)currentUser);
                bookAppointmentService.setCurrentUser(currentUser);
                return (Customer) currentUser;

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public PersonalTrainer loginPersonalTrainer(String username, String password,String email) {
        try {
            currentUser = personalTrainerDAO.getPersonalTrainer(username, password,email);
            if (currentUser == null ) {
                return null;
            } else {
                profilePTService.setPersonalTrainer((PersonalTrainer)currentUser);
                agendaService.setPersonalTrainer((PersonalTrainer)currentUser);
                return (PersonalTrainer) currentUser;


            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public String getPTnamebyId(int id){
        try {
            return personalTrainerDAO.getNamePersonalTrainerbyId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public String getCustomerNameById(int id){
        try {
            return customerDAO.getNameCustomerbyId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean registerUser(String username, String password, String email) {
        try {
            if (customerDAO.usernameExists(username)) {//aggiungere il check dell'email già esistente?
                System.err.println("This username has already been taken. Please choose another one.");
                return false;
            } else {
                if(customerDAO.insertUser(username, password, email)) {
                    int id=customerDAO.getIdUserCustomer(username);
                    customerDAO.insertCustomer(id);
                    currentUser = customerDAO.getCustomer(username, password,email);
                }
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

            if (personalTrainerDAO.usernameExists(username)) {
                System.err.println("This username has already been taken. Please choose another one.");
                return false;
            } else {
                if (Constants.ACCESSCODE.equals(accessCode)) {
                    if(personalTrainerDAO.create(username, password, email)) {
                        int id=personalTrainerDAO.getIdUserPT(username);
                        personalTrainerDAO.createPT(id);
                        currentUser = personalTrainerDAO.getPersonalTrainer(username, password,email);
                        //profilePTService.setPersonalTrainer(currentUser);
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
    public boolean checkCredentialsCustomer(String username, String password,String email) {
        try {
            return customerDAO.userExists(username, password,email);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean checkCredentialsPT(String username, String password,String email) {
        try {
            return personalTrainerDAO.exists(username, password,email);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public Customer getCustomerByUsername(String username) throws SQLException {
        return customerDAO.getCustomerByUsername(username);
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

    public boolean isPersonalTrainer(BaseUser baseUser) {
        return personalTrainerDAO.isPersonalTrainer(baseUser);
    }

    public List<Customer> getAllCustomers() {
        try {
            return customerDAO.getAllCustomers();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<PersonalTrainer> getAllPersonalTrainers() {
        try {
            return personalTrainerDAO.getAllPersonalTrainers();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteCustomer(String username,String password,String email) {
        try {
            if(customerDAO.deleteUserCustomer(username, password,email)){
                return customerDAO.deleteCustomer(customerDAO.getIdUserCustomer(username));
            }else{
                return false;

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePersonalTrainer(String username, String password, String email) {
        try {
            if( personalTrainerDAO.deleteUserPT(username, password,email)){
                return personalTrainerDAO.deletePT(personalTrainerDAO.getIdUserPT(username));
            }else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean checkIfFreePT(int idPT, String day, Time time) {
        try {
            return personalTrainerDAO.checkIfFree(idPT, day, time);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}