package com.businessLogic.Controller;

import com.backend.ORM.CustomerDAO;
import com.backend.ORM.PersonalTrainerDAO;
import com.businessLogic.NavigationManager;
import com.businessLogic.State;
import com.domainModel.Customer;
import com.domainModel.PersonalTrainer;

import java.sql.SQLException;
import java.util.Scanner;

public class LoginController extends BaseController {

    public Customer loginUser(String username, String password, State state) {
        CustomerDAO customerDAO = new CustomerDAO(state.getConnection());
        try {
            return customerDAO.getUser(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public PersonalTrainer loginPersonalTrainer(String username, String password, State state) {
        PersonalTrainerDAO personalTrainerDAO = new PersonalTrainerDAO(state.getConnection());
        try {
            return personalTrainerDAO.findByUsernameAndPassword(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(State state) {
        PersonalTrainerDAO personalTrainerDAO = new PersonalTrainerDAO(state.getConnection());
        CustomerDAO customerDAO = new CustomerDAO(state.getConnection());
        Scanner scanner = new Scanner(System.in);
        Boolean loggedIn = false;
        System.out.println("Vuoi registrarti o fare il login? (1) Registrati (2) Login");
        int choiceType = askNumberInRange(1, 2);
        if (choiceType == 1) {
            navigationManager.switchToController(NavigationManager.ControllerId.REGISTRATION);
        } else {
            while (!loggedIn) {
                System.out.println("Inserisci username: ");
                String username = scanner.nextLine();
                System.out.println("Inserisci password: ");
                String password = scanner.nextLine();
                System.out.println("Sei un utente normale o un personal trainer? (1) Utente normale (2) Personal trainer");
                int loginType = askNumberInRange(1, 2);
                if (loginType == 1) {
                    state.setLoggedUser(loginUser(username, password, state));
                    loggedIn = true;
                } else if (loginType == 2) {
                    state.setLoggedUser(loginPersonalTrainer(username, password, state));
                    loggedIn = true;
                }
            }
        }
    }
}
