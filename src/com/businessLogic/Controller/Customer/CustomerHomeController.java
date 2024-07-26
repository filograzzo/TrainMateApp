package com.businessLogic.Controller.Customer;

import com.businessLogic.Controller.BaseController;
import com.businessLogic.NavigationManager;
import com.businessLogic.State;

public class CustomerHomeController extends BaseController {
    private void showMenu(State state){
        System.out.println("Benvenuto in TrainMate!" + state.getLoggedUser().getUsername());
        System.out.println("\n");
        System.out.println("Cosa vuoi fare?");
        System.out.println("1) Visualizza allenamenti");//databse:vedere e poter aggiungere allenamenti,orario
        System.out.println("2) Prenota un corso");//database:mostra tutti,quando scegli vede se è stato raggiunto il max
        System.out.println("3) Visualizza progressi");//
        System.out.println("4) Prenota un incontro con un personal trainer");
        System.out.println("5) Visualizza il tuo profilo");//database:visualizza i dati del cliente,li può modificare
        System.out.println("6) Logout");
    }
    @Override
    public void update(State state){
        showMenu(state);
        int choice = askNumberInRange(1, 6);
        switch (choice){
            case 1:
                navigationManager.switchToController(NavigationManager.ControllerId.WORKOUTS);
                break;
            case 2:
                navigationManager.switchToController(NavigationManager.ControllerId.BOOK_COURSE);
                break;
            case 3:
                navigationManager.switchToController(NavigationManager.ControllerId.PROGRESS);//da creare
                break;
            case 4:
                navigationManager.switchToController(NavigationManager.ControllerId.BOOK_PT);
                break;
            case 5:
                navigationManager.switchToController(NavigationManager.ControllerId.CUSTOMER_PROFILE);
                break;
            case 6:
                state.setLoggedUser(null);
                navigationManager.switchToController(NavigationManager.ControllerId.LOGIN);
                break;
        }


    }
}
