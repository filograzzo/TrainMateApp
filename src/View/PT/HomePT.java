package View.PT;

import Controller.Engine;
import Controller.NavigationManager;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class HomePT extends JFrame {
    private Engine engine;

    public HomePT(Engine engine) {
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
            navigationManager.navigateToProfilePT();
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
        JButton viewAgendaButton = new JButton("View Agenda");//nella pagina agenda visualizza corsi da tenere e gestisce qui gli appuntamenti con clienti
        viewAgendaButton.addActionListener(e -> {
            navigationManager.navigateToAgenda();
        });
        centralPanel.add(viewAgendaButton);

        JButton viewCourses = new JButton("Manage Courses");
        viewCourses.addActionListener(e -> {
            navigationManager.navigateToCoursesPT();
        });
        centralPanel.add(viewCourses);

        JButton manageMachinesButton = new JButton("Manage Machines");
        manageMachinesButton.addActionListener(e -> {
            navigationManager.navigateToMachinesPT();
        });
        centralPanel.add(manageMachinesButton);

        JButton assignSchedulesButton = new JButton("Assign Schedules");
        assignSchedulesButton.addActionListener(e -> {
            try {
                navigationManager.navigateToSchedulesAssignmentPT();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        centralPanel.add(assignSchedulesButton);

        JButton manageExercisesButton = new JButton("Manage Exercises");
        manageExercisesButton.addActionListener(e -> {
            try {
                navigationManager.navigateToExercisesPT();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        centralPanel.add(manageExercisesButton);

        // Add the central panel to the main panel
        mainPanel.add(centralPanel, BorderLayout.CENTER);

        return mainPanel;
    }

}
