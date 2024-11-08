import BusinessLogic.Service.ServiceFactory;
import Controller.Engine;
import DAO.*;
import DomainModel.BaseUser;
import BusinessLogic.Service.*;
import BusinessLogic.Service.Customer.BookAppointmentService;
import BusinessLogic.Service.Customer.BookCourseService;
import BusinessLogic.Service.Customer.ProfileService;
import BusinessLogic.Service.Customer.TrainingService;
import BusinessLogic.Service.PersonalTrainer.AgendaService;
import BusinessLogic.Service.PersonalTrainer.MachineService;
import BusinessLogic.Service.PersonalTrainer.ProfilePTService;
import BusinessLogic.Service.BaseUserService;
import DomainModel.*;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import DomainModel.Constants;
import trainmate.DatabaseUtil;

import static org.junit.jupiter.api.Assertions.*;


public class Engine_tst {

    private static Engine engine;
    private static Connection connection;
    private ServiceFactory sf;
    private Scanner input = new Scanner(System.in);
    private BaseUser user;
    private static AppointmentDAO appointmentDAO;
    private static PersonalTrainerDAO personalTrainerDAO;
    private static ScheduleDAO scheduleDAO;
    private static TrainingDAO trainingDAO;
    private static CustomerDAO customerDAO;
    private static SignedDAO signedDAO;
    private static CourseDAO courseDAO;
    private static BookAppointmentService bookAppointmentService;
    private static ProfileService profileService;
    private static ProfilePTService profilePTService;
    private static BookCourseService bookCourseService;
    private static TrainingService trainingService;
    private static AgendaService agendaService;
    private static MachineService machineService;
    private static BaseUserService baseUserService;
    private static ExerciseDetailService exerciseDetailService;
    private static ExerciseService exerciseService;
    private static ScheduleService scheduleService;

    private static MachineDAO machineDAO;
    private static ExerciseDetailDAO exerciseDetailDAO;
    private static Customer testCustomer;
    private static ExerciseDAO exerciseDAO;
    private static PersonalTrainer testTrainer;
    private static String testCustomerUsername = "testCustomer";
    private static String testCustomerPassword = "password";
    private static String testCustomerMail = "testCuestomer@mail.com";
    private static String testPTUsername = "testPT";
    private static String testPTPassword = "password";
    private static String testPTMail = "testPT@mail.com";
    private static int customerId;
    private static int trainerPT;
    private static Course testCourse;
    private static String testCourseName = "testCourse";
    private static int testMaxPartecipants = 10;
    private static String testBodyPartsTrained = "All body";
    private static String testDay = "Monday";
    private static Time testTime = Time.valueOf("09:00:00");
    private static Schedule testSchedule;
    private static String testMachineName = "testMachine";
    private static String testMachineDescription = "testDescription";
    private static boolean testMachineActive = true;
    private static Machine testMachine;

    //CORE FUNCTION -------------------------------------------------------
    private Engine_tst() {
        sf =  ServiceFactory.getInstance();
        if(engine==null)
            engine = Engine.getInstance();
    }

    @BeforeAll
    public static void setUp() throws SQLException {
        connection = DatabaseUtil.getConnection();
        appointmentDAO = new AppointmentDAO(connection);
        scheduleDAO = new ScheduleDAO(connection);
        personalTrainerDAO = new PersonalTrainerDAO(connection);
        customerDAO = new CustomerDAO(connection);
        machineDAO = new MachineDAO(connection);
        courseDAO = new CourseDAO(connection);
        trainingDAO = new TrainingDAO(connection);
        signedDAO = new SignedDAO(connection);
        exerciseDAO = new ExerciseDAO(connection);
        exerciseDetailDAO = new ExerciseDetailDAO(connection);
        bookAppointmentService = new BookAppointmentService(appointmentDAO, personalTrainerDAO);
        bookCourseService = new BookCourseService(courseDAO,  signedDAO);
        profileService = new ProfileService(customerDAO);
        trainingService = new TrainingService(trainingDAO);
        agendaService = new AgendaService(personalTrainerDAO, courseDAO, scheduleDAO, appointmentDAO);
        machineService = new MachineService(machineDAO, exerciseDAO);
        profilePTService = new ProfilePTService(personalTrainerDAO);
        baseUserService = new BaseUserService(customerDAO, personalTrainerDAO, profileService, profilePTService, bookAppointmentService, agendaService);
        exerciseDetailService = new ExerciseDetailService(exerciseDetailDAO, exerciseDAO);
        exerciseService = new ExerciseService(exerciseDAO);
        scheduleService = new ScheduleService(scheduleDAO, exerciseDetailDAO);

        //creo e prendo il customer
        customerDAO.insertUser(testCustomerUsername, testCustomerPassword, testCustomerMail);
        customerId = customerDAO.getIdUserCustomer(testCustomerUsername);
        customerDAO.insertCustomer(customerId);
        testCustomer = customerDAO.getCustomerByUsername(testPTUsername);

        //creo e prendo il pt
        personalTrainerDAO.create(testPTUsername, testPTPassword, testPTMail);
        trainerPT = personalTrainerDAO.getIdUserPT(testPTUsername);
        personalTrainerDAO.createPT(trainerPT);
        testTrainer = personalTrainerDAO.getPersonalTrainer(testPTUsername, testPTPassword, testPTMail);

        //creo il corso
        courseDAO.addCourse(testCourseName, testMaxPartecipants, testTrainer.getId(), testBodyPartsTrained, testDay, testTime);
        testCourse = courseDAO.getCourseByDayAndTime(testDay, testTime);

        bookCourseService.setCurrentUser(testCustomer);
        bookCourseService.setCurrentUser(testCustomer);
        trainingService.setCurrentUser(testCustomer);
        machineService.setCurrentUser(testCustomer);
        exerciseDetailService.setCurrentUser(testCustomer);
        exerciseService.setCurrentUser(testCustomer);
        scheduleService.setCurrentUser(testCustomer);
        profileService.setCustomer(testCustomer);
        profilePTService.setPersonalTrainer(testTrainer);
        baseUserService.loginUser(testCustomerUsername, testCustomerPassword, testCustomerMail);
    }

    @AfterAll
    public static void tearDown() throws SQLException{
        int idC = customerDAO.getIdUserCustomer(testCustomerUsername);
        customerDAO.deleteCustomer(idC);
        customerDAO.deleteUserCustomer(testCustomerUsername, testCustomerPassword, testCustomerMail);

        int idP = personalTrainerDAO.getIdUserPT(testPTUsername);
        courseDAO.deleteCourse(testCourse.getId());
        personalTrainerDAO.deletePT(idP);
        personalTrainerDAO.deleteUserPT(testPTUsername, testPTPassword, testPTMail);
    }

    @Test
    public void registerCustomerTest() throws SQLException{
        String customerU = "testC";
        String customerP = "password";
        String customerE = "testC@mail.com";
        assertTrue(engine.registerCustomer(customerU, customerP, customerE));

        int id = customerDAO.getIdUserCustomer(customerU);
        customerDAO.deleteCustomer(id);
        customerDAO.deleteUserCustomer(customerU, customerP, customerE);
    }

    @Test
    public void registerPTTest() throws SQLException{
        String trainerU = "testP";
        String trainerP = "password";
        String trainerE = "testP@mail.com";
        assertTrue(engine.registerPT(trainerU, trainerP, trainerE ,Constants.ACCESSCODE));


        int id = personalTrainerDAO.getIdUserPT(trainerU);
        personalTrainerDAO.deletePT(id);
        personalTrainerDAO.deleteUserPT(trainerU, trainerP, trainerE);
    }
/*
    @Test
    public void insertAndDeletePersonalDataCustomer() throws SQLException{
        float height = 180.6f;
        float weight = 78.3f;
        int age = 23;
        String gender = "Male";
        String goal = "Lose fat";

        assertTrue(engine.updatePersonalData(customerId, height, weight, age, gender, goal));
        assertEquals(height, testCustomer.getHeight());
        assertEquals(weight, testCustomer.getWeight());
        assertEquals(age, testCustomer.getAge());
        assertEquals(gender, testCustomer.getGender());
        assertEquals(goal, testCustomer.getGoal())


        // ---delete---

        assertTrue(engine.deletePersonalData(customerId));
        assertEquals(0, testCustomer.getHeight());
        assertEquals(0, testCustomer.getWeight());
        assertEquals(0, testCustomer.getAge());
        assertNull(gender, testCustomer.getGender());
        assertNull(goal, testCustomer.getGoal());
    }*/
/*
    @Test
    public void createAndDestroySchedulePT() throws SQLException{
        String scheduleName = "testSchedule";
        //assertTrue(engine.createSchedule(testCustomer, scheduleName));
        //testSchedule = scheduleDAO.getScheduleByName(scheduleName);

        List<Schedule> schedules1 = engine.getSchedulesByUsername(testCustomer);
        assertFalse(schedules1.isEmpty());

        assertTrue(engine.removeSchedule(testCustomer, testSchedule));
        List<Schedule> schedules2 = engine.getSchedulesByUsername(testCustomer);
        assertTrue(schedules1.size() > schedules2.size());
    }*/

    @Test
    public void activeDisactiveMachine() throws SQLException{
        assertTrue(engine.createMachine(testMachineName, testMachineDescription, testMachineActive));
        testMachine = engine.getMachineByName(testMachineName);

        assertTrue(engine.getMachineState(testMachine));
        testMachine.setState(false);
        assertTrue(engine.updateMachine(testMachine));
        assertFalse(engine.getMachineState(testMachine));

        assertTrue(engine.deleteMachine(testMachine));
    }
}
