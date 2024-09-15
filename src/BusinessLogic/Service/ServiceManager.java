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

    public UserService getUserService() {
        return userService;
    }

    private UserService userService;
    private BaseUser user;
    private ServiceManager()  {
        try {
            connection = DatabaseUtil.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error while connecting to the database");
        }
        CustomerDAO customerDAO = new CustomerDAO(connection);
        PersonalTrainerDAO personalTrainerDAO = new PersonalTrainerDAO(connection);
        userService = new UserService(customerDAO, personalTrainerDAO);
    }

    public static ServiceManager getInstance() {
        if (instance == null)
            instance = new ServiceManager();
        return instance;
    }



}
