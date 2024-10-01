package Controller;
import View.*;


import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Stack;

public class NavigationManager {
    static NavigationManager istance;
    private JFrame currentFrame;
    private Dimension frameSize;
    private Point frameLocation;
    private Engine engine;
    private Stack<JFrame> history = new Stack<>();

    public void setEngine(Engine engine){
        this.engine = engine;
    }

    public Engine getEngine(){
        return engine;
    }

    private NavigationManager(JFrame currentFrame) {
        this.currentFrame = currentFrame;
        this.frameSize = currentFrame.getSize(); // get the size of the current frame
        this.frameLocation = currentFrame.getLocation();
        this.engine=Engine.getInstance();
    }

    public static NavigationManager getIstance(JFrame currentFrame){
        if(istance==null){
            istance =new NavigationManager(currentFrame);
        }
        return istance;
    }
    public void goBack() {
        if (!history.isEmpty()) {
            currentFrame.setVisible(false);
            currentFrame = history.pop();
            currentFrame.setVisible(true);
        }
    }
    public void navigateToLoginPage(){
        // Store the size and location of the current window
        frameSize = currentFrame.getSize();
        frameLocation = currentFrame.getLocation();

        // Close the current window
        currentFrame.dispose();

        // Open the DailyPlan window
        history.push(currentFrame);
        LoginPage loginView = new LoginPage(engine);
        loginView.setSize(frameSize); // set the size of the new window
        loginView.setLocation(frameLocation); // set the location of the new window
    }
    public void navigateToRegistrationPage(){
        // Store the size and location of the current window
        frameSize = currentFrame.getSize();
        frameLocation = currentFrame.getLocation();
        history.push(currentFrame);
        // Close the current window
        currentFrame.dispose();

        // Open the DailyPlan window
        RegistrationPage registerView = new RegistrationPage();
        registerView.setSize(frameSize); // set the size of the new window
        registerView.setLocation(frameLocation); // set the location of the new window
    }
    public void navigateToRegisterCustomer(){
        // Store the size and location of the current window
        frameSize = currentFrame.getSize();
        frameLocation = currentFrame.getLocation();
        history.push(currentFrame);
        // Close the current window
        currentFrame.dispose();

        // Open the DailyPlan window
        RegisterCustomer registerView = new RegisterCustomer(engine);
        registerView.setSize(frameSize); // set the size of the new window
        registerView.setLocation(frameLocation); // set the location of the new window
    }
    public void navigateToRegisterPT(){
        // Store the size and location of the current window
        frameSize = currentFrame.getSize();
        frameLocation = currentFrame.getLocation();
        history.push(currentFrame);
        // Close the current window
        currentFrame.dispose();

        // Open the DailyPlan window
        RegisterPT registerView = new RegisterPT(engine);
        registerView.setSize(frameSize); // set the size of the new window
        registerView.setLocation(frameLocation); // set the location of the new window
    }

    public void navigateToHomeCustomer(){
        // Store the size and location of the current window
        frameSize = currentFrame.getSize();
        frameLocation = currentFrame.getLocation();
        history.push(currentFrame);
        // Close the current window
        currentFrame.dispose();
        //Open the HomeCustomer window
        HomeCustomer homeCustomer = new HomeCustomer(engine);
        homeCustomer.setSize(frameSize);
        homeCustomer.setLocation(frameLocation);
    }
    public void navigateToCustomerProfile(){
        // Store the size and location of the current window
        frameSize = currentFrame.getSize();
        frameLocation = currentFrame.getLocation();
        history.push(currentFrame);
        // Close the current window
        currentFrame.dispose();
        //Open the CustomerProfile window
        CustomerProfile customerProfile = new CustomerProfile(engine);
        customerProfile.setSize(frameSize);
        customerProfile.setLocation(frameLocation);
    }

    public void navigateToCourses(){
        // Store the size and location of the current window
        frameSize = currentFrame.getSize();
        frameLocation = currentFrame.getLocation();
        history.push(currentFrame);
        // Close the current window
        currentFrame.dispose();
        //Open the Courses window
        Courses courses = new Courses(engine);
        courses.setSize(frameSize);
        courses.setLocation(frameLocation);
    }
    public void navigateToHomePT(){
        // Store the size and location of the current window
        frameSize = currentFrame.getSize();
        frameLocation = currentFrame.getLocation();
        history.push(currentFrame);
        // Close the current window
        currentFrame.dispose();
        //Open the HomePT window
        HomePT homePT = new HomePT(engine);
        homePT.setSize(frameSize);
        homePT.setLocation(frameLocation);
    }
    public void navigateToProfilePT(){
        // Store the size and location of the current window
        frameSize = currentFrame.getSize();
        frameLocation = currentFrame.getLocation();
        history.push(currentFrame);
        // Close the current window
        currentFrame.dispose();
        //Open the ProfilePT window
        ProfilePT profilePT = new ProfilePT(engine);
        profilePT.setSize(frameSize);
        profilePT.setLocation(frameLocation);
    }
    public void navigateToCoursesPT(){
        // Store the size and location of the current window
        frameSize = currentFrame.getSize();
        frameLocation = currentFrame.getLocation();
        history.push(currentFrame);
        // Close the current window
        currentFrame.dispose();
        //Open the CoursesPT window
        CoursesPT coursesPT = new CoursesPT(engine);
        coursesPT.setSize(frameSize);
        coursesPT.setLocation(frameLocation);
    }
}
