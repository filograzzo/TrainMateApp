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
    private ProfileService profileService;
    private BookCourseService bookCourseService;
    private BookAppointmentService bookAppointmentService;
    private AgendaService agendaService;
    private ProfilePTService profilePTService;
    private BaseUserService baseUserService;
    private ScheduleService scheduleService;
    private TrainingService trainingService;

    private BaseUser user;
    public BaseUserService getUserService() {
        return baseUserService;
    }
    public AgendaService getAgendaService() {
        return agendaService;
    }
    public ProfilePTService getProfilePTService() {
        return profilePTService;
    }
    public BookAppointmentService getBookAppointmentService() {
        return bookAppointmentService;
    }
    public BookCourseService getBookCourseService() {
        return bookCourseService;
    }
    public ProfileService getProfileService() {
        return profileService;
    }
    public ScheduleService getScheduleService(){return scheduleService;}
    public TrainingService getTrainingService(){return trainingService;}

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
        CourseDAO courseDAO = new CourseDAO(connection);
        PersonalDataClientDAO personalDataClientDAO = new PersonalDataClientDAO(connection);
        AppointmentDAO appointmentDAO = new AppointmentDAO(connection);


        baseUserService = new BaseUserService(customerDAO, personalTrainerDAO, scheduleDAO, excerciseDetailDAO);
        profilePTService= new ProfilePTService(personalTrainerDAO);
        agendaService = new AgendaService(personalTrainerDAO,courseDAO, scheduleDAO);
        profileService= new ProfileService(customerDAO,personalDataClientDAO);
        bookCourseService = new BookCourseService(courseDAO, signedDAO);
        bookAppointmentService = new BookAppointmentService(appointmentDAO);
        scheduleService = new ScheduleService(scheduleDAO, excerciseDetailDAO);
        trainingService = new TrainingService(trainingDAO);

        //#TODO:costruire i vari service e assegnare qui i loro DAO
    }

    public static ServiceManager getInstance() {
        if (instance == null)
            instance = new ServiceManager();
        return instance;
    }



}
