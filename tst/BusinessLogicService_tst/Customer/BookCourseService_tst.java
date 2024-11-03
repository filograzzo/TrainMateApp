package BusinessLogicService_tst.Customer;

import BusinessLogic.Service.Customer.BookCourseService;
import DAO.CourseDAO;
import DAO.PersonalTrainerDAO;
import DAO.SignedDAO;
import DAO.CustomerDAO;
import DomainModel.BaseUser;
import DomainModel.Course;
import DomainModel.PersonalTrainer;
import org.junit.jupiter.api.*;

import trainmate.DatabaseUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BookCourseService_tst {
    private static Connection connection;
    private static CourseDAO courseDAO;
    private static SignedDAO signedDAO;
    private static CustomerDAO customerDAO;
    private static PersonalTrainerDAO personalTrainerDAO;
    private static BookCourseService bookCourseService;
    private static BaseUser testUser;
    private static int testUserId;
    private static int testPTId;
    private static PersonalTrainer testPT;
    private static int testCourseId;
    private static String day;
    private static Time time;


    @BeforeAll
    public static void setUp() throws SQLException {
        connection = DatabaseUtil.getConnection();
        courseDAO = new CourseDAO(connection);
        signedDAO = new SignedDAO(connection);
        customerDAO = new CustomerDAO(connection);
        bookCourseService = new BookCourseService(courseDAO, signedDAO);
        personalTrainerDAO = new PersonalTrainerDAO(connection);
        day = "Friday";
        time = Time.valueOf("18:00:00");

        // Crea un utente di test
        String customerUsername = "testCustomer";
        String password = "password";
        String email = "testCustomer@example.com";

        String personalTrainerUsername = "testPT";
        String passwordPT = "password";
        String emailPT = "testPT@example.com";

        customerDAO.insertUser(customerUsername, password, email);
        testUserId = customerDAO.getIdUserCustomer(customerUsername);
        customerDAO.insertCustomer(testUserId);

        personalTrainerDAO.create(personalTrainerUsername, passwordPT, emailPT);
        testPTId = personalTrainerDAO.getIdUserPT(personalTrainerUsername);
        personalTrainerDAO.createPT(testPTId);

        testUser = customerDAO.getCustomerByUsername(customerUsername);
        testPT = personalTrainerDAO.getPersonalTrainer(personalTrainerUsername, passwordPT, emailPT);
        testPTId = testPT.getId();

        courseDAO.addCourse("Test Course", 20, testPTId, "Test", day, time);
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        // Rimuove il corso e l'utente di test
        courseDAO.deleteCourse(testCourseId);
        customerDAO.deleteCustomer(testUserId);
        customerDAO.deleteUserCustomer("testCustomer", "password", "testCustomer@example.com");
        personalTrainerDAO.deletePT(testPTId);
        personalTrainerDAO.deleteUserPT("testPT", "password", "testPT@example.com");

        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    public void testBookCourseSuccess() throws SQLException {
        // Verifica che il corso sia prenotabile
        boolean result = bookCourseService.bookCourse(testCourseId);
        assertTrue(result, "La prenotazione dovrebbe essere riuscita");

        // Verifica che l'utente sia registrato al corso
        assertTrue(bookCourseService.isSigned(testCourseId), "L'utente dovrebbe essere registrato al corso");

        // Pulisce la prenotazione
        boolean cancelResult = bookCourseService.cancelBooking(testCourseId);
        assertTrue(cancelResult, "La cancellazione della prenotazione dovrebbe essere riuscita");
    }


    @Test
    public void testCancelBooking() throws SQLException {
        // Prenota un corso per garantire una registrazione valida
        bookCourseService.bookCourse(testCourseId);

        // Cancella la prenotazione
        boolean result = bookCourseService.cancelBooking(testCourseId);
        assertTrue(result, "La cancellazione della prenotazione dovrebbe essere riuscita");

        // Verifica che l'utente non sia più registrato
        assertFalse(bookCourseService.isSigned(testCourseId), "L'utente non dovrebbe più essere registrato al corso");
    }

    @Test
    public void testIsSigned() throws SQLException {
        // Prima prenota il corso
        bookCourseService.bookCourse(testCourseId);

        // Controlla se l'utente è registrato
        assertTrue(bookCourseService.isSigned(testCourseId), "L'utente dovrebbe essere registrato");

        // Cancella e verifica che l'utente non sia più registrato
        bookCourseService.cancelBooking(testCourseId);
        assertFalse(bookCourseService.isSigned(testCourseId), "L'utente non dovrebbe essere più registrato");
    }
}
