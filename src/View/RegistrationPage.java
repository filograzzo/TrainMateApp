package View;

import Controller.Engine;
import Controller.NavigationManager;
import javax.swing.*;
import java.awt.*;

public class RegistrationPage extends JFrame {
    NavigationManager navigationManager = NavigationManager.getIstance(this);
    public RegistrationPage() {

        setupWindow();
        JPanel mainPanel = createMainPanel();
        add(mainPanel);
        setVisible(true);
    }
    private JPanel createTitlePanel() {

        JLabel label = new JLabel("Register as Customer or Personal Trainer?", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.add(label);

        return labelPanel;
    }
    private JPanel createBackButtonPanel() {
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> navigationManager.navigateToLoginPage());
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(backButton);

        return buttonPanel;
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(createTitlePanel());
        mainPanel.add(createRegisterButtonsPanel());
        mainPanel.add(createBackButtonPanel());
        return mainPanel;
    }

    private JPanel createRegisterButtonsPanel() {
        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 0, 10, 0); // Add some spacing between buttons
        JButton registerAsCustomerButton = new JButton("Register as Customer");
        registerAsCustomerButton.setPreferredSize(new Dimension(200, 100)); // Set button size
        registerAsCustomerButton.addActionListener(e -> {
            // Event handling code for Register as Customer button click
            navigationManager.navigateToRegisterCustomer();
            // Add additional actions here
        });
        buttonsPanel.add(registerAsCustomerButton, gbc);

        JButton registerAsPTButton = new JButton("Register as Personal Trainer");
        registerAsPTButton.setPreferredSize(new Dimension(200, 100)); // Set button size
        registerAsPTButton.addActionListener(e -> {
            // Event handling code for Register as Personal Trainer button click
            navigationManager.navigateToRegisterPT();
            // Add additional actions here
        });
        buttonsPanel.add(registerAsPTButton, gbc);

        return buttonsPanel;
    }

    private void setupWindow() {
        setSize(1000, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}