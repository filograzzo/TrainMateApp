package com.businessLogic.Controller;

import com.businessLogic.NavigationManager;
import com.businessLogic.State;

import java.util.Scanner;

public abstract class BaseController {

    public abstract void update(State state);
    protected static NavigationManager navigationManager;
    protected static int askNumberInRange(int min, int max){
        int navigationOption = -1;
        boolean validNavigationOption = false;
        Scanner input = new Scanner(System.in);
        while (!validNavigationOption) {
            try {
                navigationOption = Integer.parseInt(input.nextLine());
                if (navigationOption>=min&&navigationOption<=max) {
                    validNavigationOption = true;
                }
                else{
                    System.out.println("not in range");
                }
            } catch (NumberFormatException e) {
                System.out.println("not a number");
            }
        }

        return navigationOption;
    }

    public void setNavigationManager(NavigationManager navigationManager) {

        BaseController.navigationManager = navigationManager;
    }
}
