import BusinessLogic.Service.*;
import BusinessLogic.Service.Customer.BookAppointmentService;
import BusinessLogic.Service.Customer.BookCourseService;
import BusinessLogic.Service.Customer.ProfileService;
import BusinessLogic.Service.Customer.TrainingService;
import BusinessLogic.Service.PersonalTrainer.AgendaService;
import BusinessLogic.Service.PersonalTrainer.MachineService;
import BusinessLogic.Service.PersonalTrainer.ProfilePTService;
import DAO.*;
import trainmate.DatabaseUtil;
import java.sql.Connection;
import java.sql.SQLException;

public class TestManager {

    private final Connection connection;
    private static TestManager instance;
    private ProfileService profileService_tst;
    private BookCourseService bookCourseService_tst;
    private BookAppointmentService bookAppointmentService_tst;
    private AgendaService agendaService_tst;
    private ProfilePTService profilePTService_tst;
    private ScheduleService scheduleService_tst;
    private TrainingService trainingService_tst;
    private ExerciseDetailService exerciseDetailService_tst;
    private ExerciseService exerciseService_tst;
    private MachineService machineService_tst;
    private BaseUserService baseUserService_tst;

    public ProfileService getProfileService_tst() {
        return profileService_tst;
    }

    public BookCourseService getBookCourseService_tst() {
        return bookCourseService_tst;
    }

    public BookAppointmentService getBookAppointmentService_tst() {
        return bookAppointmentService_tst;
    }

    public AgendaService getAgendaService_tst() {
        return agendaService_tst;
    }

    public ProfilePTService getProfilePTService_tst() {
        return profilePTService_tst;
    }

    public ScheduleService getScheduleService_tst() {
        return scheduleService_tst;
    }

    public TrainingService getTrainingService_tst() {
        return trainingService_tst;
    }

    public ExerciseDetailService getExerciseDetailService_tst() {
        return exerciseDetailService_tst;
    }

    public ExerciseService getExerciseService_tst() {
        return exerciseService_tst;
    }

    public MachineService getMachineService_tst() {
        return machineService_tst;
    }

    public BaseUserService getBaseUserService_tst() {
        return baseUserService_tst;
    }

    private TestManager() {
        try {
            connection = DatabaseUtil.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error while connecting to the database");
        }

        CustomerDAO customerDAO_tst = new CustomerDAO(connection);
        PersonalTrainerDAO personalTrainerDAO_tst = new PersonalTrainerDAO(connection);
        ExerciseDetailDAO excerciseDetailDAO_tst = new ExerciseDetailDAO(connection);
        MachineDAO machineDAO_tst = new MachineDAO(connection);
        ScheduleDAO scheduleDAO_tst = new ScheduleDAO(connection);
        SignedDAO signedDAO_tst = new SignedDAO(connection);
        TrainingDAO trainingDAO_tst = new TrainingDAO(connection);
        CourseDAO courseDAO_tst = new CourseDAO(connection);
        AppointmentDAO appointmentDAO_tst = new AppointmentDAO(connection);
        ExerciseDAO exerciseDAO_tst = new ExerciseDAO(connection);

        profileService_tst= new ProfileService(customerDAO_tst);
        profilePTService_tst= new ProfilePTService(personalTrainerDAO_tst);
        bookAppointmentService_tst = new BookAppointmentService(appointmentDAO_tst, personalTrainerDAO_tst);
        agendaService_tst = new AgendaService(personalTrainerDAO_tst,courseDAO_tst, scheduleDAO_tst, appointmentDAO_tst);
        baseUserService_tst = new BaseUserService(customerDAO_tst, personalTrainerDAO_tst, profileService_tst, profilePTService_tst, bookAppointmentService_tst, agendaService_tst);
        bookCourseService_tst = new BookCourseService(courseDAO_tst, signedDAO_tst);
        scheduleService_tst = new ScheduleService(scheduleDAO_tst, excerciseDetailDAO_tst);
        trainingService_tst = new TrainingService(trainingDAO_tst);
        exerciseDetailService_tst = new ExerciseDetailService(excerciseDetailDAO_tst, exerciseDAO_tst);
        exerciseService_tst = new ExerciseService(exerciseDAO_tst);
        machineService_tst = new MachineService(machineDAO_tst, exerciseDAO_tst);

    }

    public static TestManager getInstance() {
        if (instance == null)
            instance = new TestManager();
        return instance;
    }
}