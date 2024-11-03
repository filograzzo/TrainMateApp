import BusinessLogic.Service.ServiceFactory;
import Controller.Engine;
import DAO.AppointmentDAO;
import DAO.CourseDAO;
import DAO.CustomerDAO;
import DAO.PersonalTrainerDAO;
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
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.sql.SQLException;
import java.sql.Time;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import DomainModel.Constants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class Engine_tst {

    private static Engine engine;
    private ServiceFactory sf;
    private Scanner input = new Scanner(System.in);
    private BaseUser user;
    private static AppointmentDAO appointmentDAO;
    private static PersonalTrainerDAO personalTrainerDAO;
    private static CustomerDAO customerDAO;
    private static CourseDAO courseDAO;
    private static BookAppointmentService bookAppointmentService;
    private static Customer testCustomer;
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



    //CORE FUNCTION -------------------------------------------------------
    private Engine_tst() {
        sf =  ServiceFactory.getInstance();
        if(engine==null)
            engine = Engine.getInstance();
    }

    @BeforeAll
    public static void setUp() throws SQLException {
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
    }

    @AfterAll
    public static void tearDown() throws SQLException{
        int idC = customerDAO.getIdUserCustomer(testCustomerUsername);
        customerDAO.deleteCustomer(idC);
        customerDAO.deleteUserCustomer(testCustomerUsername, testCustomerPassword, testCustomerMail);

        int idP = personalTrainerDAO.getIdUserPT(testPTUsername);
        personalTrainerDAO.deletePT(idP);
        personalTrainerDAO.deleteUserPT(testPTUsername, testPTPassword, testPTMail);
        courseDAO.deleteCourse(testCourse.getId());
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
        assertEquals(goal, testCustomer.getGoal());


        // ---delete---

        assertTrue(engine.deletePersonalData(customerId));
        assertEquals(0, testCustomer.getHeight());
        assertEquals(0, testCustomer.getWeight());
        assertEquals(0, testCustomer.getAge());
        assertNull(gender, testCustomer.getGender());
        assertNull(goal, testCustomer.getGoal());
    }

    @Test
    public void bookAndCancelCourse() throws SQLException{

    }


}
