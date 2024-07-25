package com.businessLogic;

import com.businessLogic.Controller.BaseController;
import com.businessLogic.Controller.LoginController;
import com.businessLogic.Controller.RegistrationController;
import com.trainmate.DatabaseUtil;
import com.businessLogic.State;

import java.util.HashMap;

import java.util.Stack;


public class NavigationManager {

    private State state = new State();
    public enum ControllerId {
        LOGIN,
        REGISTRATION,
        HOME,
    }
    private final HashMap<ControllerId, BaseController> controllers;
    private final Stack<BaseController> states;

    private int lastId = 0;

    public NavigationManager() {
        states = new Stack<>();
        try{
            state.setConnection(DatabaseUtil.getConnection());
        }catch (Exception e){
            System.out.println("Error while connecting to the database");
        }
        controllers = new HashMap<>();
        controllers.put(ControllerId.LOGIN, new LoginController());
        controllers.put(ControllerId.REGISTRATION, new RegistrationController());
        for (BaseController c : controllers.values()) {
            c.setNavigationManager(this);
        }

    }

    public void start() {
        switchToController(ControllerId.LOGIN);
        run();
    }
    public void previousState() {
        states.pop();
    }

    public int getCurrentHandlerId() {
        for (HashMap.Entry<ControllerId, BaseController> entry : controllers.entrySet()) {
            if (entry.getValue().equals(states.peek()))
                return entry.getKey().ordinal();
        }
        return -1;
    }

    public void switchToController(ControllerId id) {
        if (controllers.containsKey(id)) {
            states.push(controllers.get(id));
        } else {
            throw new IllegalArgumentException("Controller not present");
        }
    }

    public void pushHandler(ControllerId id) {
        if (controllers.containsKey(id)) {
            states.push(controllers.get(id));
        }
    }

    public void run() {
        while (!states.empty()) {
            states.peek().update(state);
        }
    }


    public void stop() {
        lastId = getCurrentHandlerId();
        while (!states.empty()) {
            states.pop();
        }

        System.out.println("Swetify closed");
    }

    public int getLastId() {
        return lastId;
    }

    public State getState() {
        return state;
    }
}
