package Controller;

import BusinessLogic.Service.PersonalTrainer.ProfilePTService;
import BusinessLogic.Service.ScheduleService;
import BusinessLogic.Service.ServiceFactory;
import DomainModel.BaseUser;
import DomainModel.Schedule;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Engine {
    private ServiceFactory sf;
    private Scanner input = new Scanner(System.in);


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

    //SCHEDULE

    public void createSchedule(BaseUser baseUser){
        if(baseUser.isValid()) {
            ScheduleService scheduleService = (ScheduleService) sf.getService(sf.SCHEDULE_SERVICE);
            try {
                System.out.println("What would you like to call your new schedule?");
                String scheduleName = input.nextLine();

                boolean done = scheduleService.createSchedule(baseUser, scheduleName);
                if (!done) {
                    throw new CustomizedException("There has been an error in the creation of the schedule.");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (CustomizedException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void removeSchedule(BaseUser baseUser, Schedule schedule){
        if(baseUser.isValid()){
            ScheduleService scheduleService = (ScheduleService) sf.getService(sf.SCHEDULE_SERVICE);
            try{
                boolean done = scheduleService.removeSchedule(schedule);
                if(!done)
                    throw new CustomizedException("There has been an error. Your schedule has not been eliminated.");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (CustomizedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void updateSchedule(BaseUser baseUser, Schedule schedule){
        if(baseUser.isValid()){
            ScheduleService scheduleService = (ScheduleService) sf.getService(sf.SCHEDULE_SERVICE);
            try{
                System.out.println("What do you want to change the name of the schedule to?");
                String newName = input.nextLine();
                schedule.setName(newName);
                 boolean done = scheduleService.updateSchedule(schedule);
                 if(!done)
                     throw new CustomizedException("The update failed. You did not change the name of your schedule.");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (CustomizedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<Schedule> getSchedules(BaseUser baseUser){
        List <Schedule> schedules = new ArrayList<>();
        if(baseUser.isValid()){
            ScheduleService scheduleService = (ScheduleService) sf.getService(sf.SCHEDULE_SERVICE);
            try{
                schedules = scheduleService.getSchedules(baseUser);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return schedules;
    }

}
