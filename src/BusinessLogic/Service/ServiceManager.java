package BusinessLogic.Service;
import BusinessLogic.Service.Customer.*;
import BusinessLogic.Service.PersonalTrainer.*;
import DAO.*;
import DomainModel.*;
import trainmate.DatabaseUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class ServiceManager {
    private final Connection connection;
    private static ServiceManager instance;
    private CustomerHomeService customerHomeService;
    private ProfileService profileService;
    private BookCourseService bookCourseService;
    private BookAppointmentService bookAppointmentService;
    private WorkoutService workoutService;
    private PersonalTrainerHomeService personalTrainerHomeService;
    private AgendaService agendaService;
    private ProfilePTService profilePTService;
    private UserService userService;
    private BaseUser user;
    public UserService getUserService() {
        return userService;
    }
    public AgendaService getAgendaService() {
        return agendaService;
    }
    public ProfilePTService getProfilePTService() {
        return profilePTService;
    }
    public PersonalTrainerHomeService getPersonalTrainerHomeService() {
        return personalTrainerHomeService;
    }
    public CustomerHomeService getCustomerHomeService() {
        return customerHomeService;
    }
    public BookAppointmentService getBookAppointmentService() {
        return bookAppointmentService;
    }
    public WorkoutService getWorkoutService() {
        return workoutService;
    }

    public BookCourseService getBookCourseService() {
        return bookCourseService;
    }
    public ProfileService getProfileService() {
        return profileService;
    }

    private ServiceManager()  {
        try {
            connection = DatabaseUtil.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error while connecting to the database");
        }
        //creare e assegnare i DAO ai vari service
        CustomerDAO customerDAO = new CustomerDAO(connection);
        PersonalTrainerDAO personalTrainerDAO = new PersonalTrainerDAO(connection);
        ExerciseDetailDAO excerciseDetailDAO = new ExerciseDetailDAO(connection);
        MachineDAO machineDAO = new MachineDAO(connection);
        ScheduleDAO scheduleDAO = new ScheduleDAO(connection);
        SignedDAO signedDAO = new SignedDAO(connection);
        TrainingDAO trainingDAO = new TrainingDAO(connection);


        userService = new UserService(customerDAO, personalTrainerDAO);
        //#TODO:costruire i vari service e assegnare qui i loro DAO
    }

    public static ServiceManager getInstance() {
        if (instance == null)
            instance = new ServiceManager();
        return instance;
    }



}
