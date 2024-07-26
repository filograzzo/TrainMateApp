package com.businessLogic.Controller.PersonalTrainer;

import com.businessLogic.Controller.BaseController;
import com.businessLogic.NavigationManager;
import com.businessLogic.State;

public class PersonalTrainerHomeController extends BaseController {

    private void showMenu(State state){
        System.out.println("Benvenuto in TrainMate!" + state.getLoggedUser().getUsername());
        System.out.println("\n");
        System.out.println("Cosa vuoi fare?");
        System.out.println("1) Visualizza agenda settimanale");
        System.out.println("2) Visualizza macchinari");
        System.out.println("3) Visualizza il tuo profilo");
        System.out.println("4) Logout");
    }
    @Override
    public void update(State state){
        showMenu(state);
        int choice = askNumberInRange(1, 4);
        switch (choice){
            case 1:
                navigationManager.switchToController(NavigationManager.ControllerId.PT_AGENDA);
                break;
            case 2:
                System.out.println("Visualizza macchinari");//da creare
                break;
            case 3:
                navigationManager.switchToController(NavigationManager.ControllerId.PT_PROFILE);
                break;
            case 4:
                state.setLoggedUser(null);
                navigationManager.switchToController(NavigationManager.ControllerId.LOGIN);
                break;
        }

    }
}
