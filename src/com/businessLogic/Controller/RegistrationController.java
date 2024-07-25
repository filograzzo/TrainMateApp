package com.businessLogic.Controller;

import com.backend.ORM.CustomerDAO;
import com.businessLogic.NavigationManager;
import com.businessLogic.State;
import com.businessLogic.TypeOfSession;
import com.domainModel.PersonalTrainer;
import com.backend.ORM.PersonalTrainerDAO;
import com.domainModel.Constants;
import java.sql.SQLException;
import java.util.Scanner;

public class RegistrationController extends BaseController {

    public boolean registerUser(String username, String password, String email, State state) {
        CustomerDAO customerDAO = new CustomerDAO(state.getConnection());
        try {
            if (customerDAO.userExists(username, password)) {
                System.err.println("The user you are trying to create already exists.");
                return false;
            } else {
                boolean success = customerDAO.insertUser(username, password, email);
                if (success) {
                    state.setLoggedUser(customerDAO.getUser(username, password));
                    state.setSessionType(TypeOfSession.CUSTOMER);
                    System.out.println("The user has been registered successfully.");
                }
                return success;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean registerPersonalTrainer(String username, String password, String email, String accessCode, State state) {

        PersonalTrainerDAO personalTrainerDAO = new PersonalTrainerDAO(state.getConnection());
        try {

            if (personalTrainerDAO.exists(username, password)) {
                System.err.println("The personal trainer you are trying to create already exists.");
                return false;
            } else {
                if (Constants.ACCESSCODE.equals(accessCode)) {
                    boolean created = personalTrainerDAO.create(username, password, email);
                    if (created) {
                        state.setLoggedUser(personalTrainerDAO.findByUsernameAndPassword(username, password));
                        state.setSessionType(TypeOfSession.PERSONAL_TRAINER);
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

    @Override
    public void update(State state) {
        PersonalTrainerDAO personalTrainerDAO = new PersonalTrainerDAO(state.getConnection());
        CustomerDAO customerDAO = new CustomerDAO(state.getConnection());
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserisci username: ");
        String username = scanner.nextLine();
        System.out.println("Inserisci password: ");
        String password = scanner.nextLine();
        System.out.println("Inserisci email: ");
        String email = scanner.nextLine();
        System.out.println("Sei un utente normale o un personal trainer? (1) Utente normale (2) Personal trainer");
        int registrationType = askNumberInRange(1, 2);
        String accessCode = "";
        if(registrationType==2){
            System.out.println("Inserisci access code: ");
            accessCode = scanner.nextLine();
        }
        if(registrationType==1){
            registerUser(username, password, email, state);
        }
        else if(registrationType==2){
            registerPersonalTrainer(username, password, email, accessCode, state);
        }
        navigationManager.switchToController(NavigationManager.ControllerId.LOGIN);

    }

}
