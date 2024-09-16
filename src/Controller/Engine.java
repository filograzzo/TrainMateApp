package Controller;

import BusinessLogic.Service.PersonalTrainer.ProfilePTService;
import BusinessLogic.Service.ServiceFactory;

public class Engine {
    private ServiceFactory sf;


    //CORE FUNCTION -------------------------------------------------------
    private Engine() {
        sf =  ServiceFactory.getInstance();
    }

    /*Personal Trainer*/
    public void modifyUsernamePT(int id, String username) {
        ProfilePTService profilePTService = (ProfilePTService) sf.getService(sf.PROFILEPT_SERVICE);
        try {
            profilePTService.modifyUsername(id, username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void modifyPasswordPT(int id, String password) {
        ProfilePTService profilePTService = (ProfilePTService) sf.getService(sf.PROFILEPT_SERVICE);
        try {
            profilePTService.modifyPassword(id, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void modifyEmailPT(int id, String email) {
        ProfilePTService profilePTService = (ProfilePTService) sf.getService(sf.PROFILEPT_SERVICE);
        try {
            profilePTService.modifyEmail(id, email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
