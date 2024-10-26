package View.Customer;

import Controller.Engine;
import Controller.NavigationManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegisterCustomer extends JFrame {
    private JTextField usernameField;
    private JTextField emailField;
    private JTextField passwordField;
    NavigationManager navigationManager = NavigationManager.getIstance(this);
    private Engine engine;
    public RegisterCustomer(Engine engine) {
        this.engine = engine;
        setupWindow();
        JPanel mainPanel = createMainPanel();
        add(mainPanel);
        setVisible(true);
    }

    private JPanel createTitlePanel() {

        JLabel label = new JLabel("Register", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 36));

        JPanel labelPanel = new JPanel(new FlowLayout());
        labelPanel.add(label);

        return labelPanel;
    }

    private JToggleButton createButton(String title, ButtonGroup buttonGroup, Runnable action) {
        JToggleButton button = new JToggleButton(title);
        buttonGroup.add(button);
        if (action != null) {
            button.addActionListener(e -> action.run());
        }
        return button;
    }
    private JPanel createEmailPanel() {
        JLabel emailLabel = new JLabel("Email");
        emailField = new JTextField(20);

        JPanel emailPanel = new JPanel(new FlowLayout());
        emailPanel.add(emailLabel);
        emailPanel.add(emailField);

        return emailPanel;
    }
    private JPanel createUsernamePanel() {
        JLabel usernameLabel = new JLabel("Username");
        usernameField = new JTextField(20);

        JPanel usernamePanel = new JPanel(new FlowLayout());
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);

        return usernamePanel;
    }
    private JPanel createPasswordPanel() {
        JLabel passwordLabel = new JLabel("Password");
        passwordField = new JPasswordField(20);

        JPanel passwordPanel = new JPanel(new FlowLayout());
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        return passwordPanel;
    }

    private JPanel createRegisterButton() {
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String email = emailField.getText();
            engine.registerCustomer(username, password, email);
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(registerButton);

        return buttonPanel;
    }
    private JPanel createBackButtonPanel() {
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> navigationManager.navigateToRegistrationPage());
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(backButton);

        return buttonPanel;
    }
    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(createTitlePanel());
        mainPanel.add(createUsernamePanel());
        mainPanel.add(createEmailPanel());
        mainPanel.add(createPasswordPanel());
        mainPanel.add(createRegisterButton());
        mainPanel.add(createBackButtonPanel());
        return mainPanel;

    }

    private void setupWindow() {
        setSize(1000, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
