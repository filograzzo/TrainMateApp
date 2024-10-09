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
    private ScheduleService scheduleService;
    private TrainingService trainingService;
    private ExerciseDetailService exerciseDetailService;
    private ExerciseService exerciseService;
    private MachineService machineService;

    private BaseUserService baseUserService;
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
    public ExerciseDetailService getExerciseDetailService(){return exerciseDetailService;}
    public ExerciseService getExerciseService(){return exerciseService;}
    public MachineService getMachineService(){return machineService;}

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
        AppointmentDAO appointmentDAO = new AppointmentDAO(connection);
        ExerciseDAO exerciseDAO = new ExerciseDAO(connection);

        profileService= new ProfileService(customerDAO);
        profilePTService= new ProfilePTService(personalTrainerDAO);
        bookAppointmentService = new BookAppointmentService(appointmentDAO, personalTrainerDAO);
        agendaService = new AgendaService(personalTrainerDAO,courseDAO, scheduleDAO, appointmentDAO);
        baseUserService = new BaseUserService(customerDAO, personalTrainerDAO, profileService, profilePTService, bookAppointmentService, agendaService);
        bookCourseService = new BookCourseService(courseDAO, signedDAO);
        scheduleService = new ScheduleService(scheduleDAO, excerciseDetailDAO);
        trainingService = new TrainingService(trainingDAO);
        exerciseDetailService = new ExerciseDetailService(excerciseDetailDAO, exerciseDAO);
        exerciseService = new ExerciseService(exerciseDAO);
        machineService = new MachineService(machineDAO, exerciseDAO);

    }

    public static ServiceManager getInstance() {
        if (instance == null)
            instance = new ServiceManager();
        return instance;
    }



}
