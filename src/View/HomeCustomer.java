package View;

import Controller.Engine;
import Controller.NavigationManager;

import javax.swing.*;
import java.awt.*;

public class HomeCustomer extends JFrame {
    private Engine engine;

    public HomeCustomer(Engine engine) {
        this.engine = engine;
        setupWindow();
        JPanel mainPanel = createMainPanel();
        add(mainPanel);
        setVisible(true);
    }

    private void setupWindow() {
        setSize(1000, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create and add the "View Profile" button to the top-right corner
        JButton viewProfileButton = new JButton("View Profile");
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.add(viewProfileButton);
        NavigationManager navigationManager = NavigationManager.getIstance(this);
        navigationManager.setEngine(this.engine);
        viewProfileButton.addActionListener(e -> {
            navigationManager.navigateToCustomerProfile();
            // Handle back action
        });
        mainPanel.add(topPanel, BorderLayout.NORTH);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            // Logic to handle logout
            engine.logout();
            navigationManager.navigateToLoginPage();
        });
        topPanel.add(logoutButton);

        // Create the central panel with a GridLayout
        JPanel centralPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        JButton viewCoursesButton = new JButton("View Courses");
        viewCoursesButton.addActionListener(e -> {
            navigationManager.navigateToCourses();
        });
        centralPanel.add(viewCoursesButton);

        centralPanel.add(new JButton("Book Appointment"));
        centralPanel.add(new JButton("View Schedule"));
        centralPanel.add(new JButton("View Trainings"));
        centralPanel.add(new JButton("View Exercises"));

        // Add the central panel to the main panel
        mainPanel.add(centralPanel, BorderLayout.CENTER);

        return mainPanel;
    }

}
