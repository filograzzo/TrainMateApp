package View;

import Controller.Engine;
import Controller.NavigationManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class LoginPage extends JFrame {
    private JTextField usernameField;
    private JTextField emailField;
    private JTextField passwordField;
    private Engine engine;
    public LoginPage(Engine engine) {
        this.engine = engine;
        setupWindow();
        JPanel mainPanel = createMainPanel();
        add(mainPanel);
        setVisible(true);

    }
    private JPanel createTitlePanel() {

        JLabel label = new JLabel("Access", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 36));

        JPanel labelPanel = new JPanel(new FlowLayout());
        labelPanel.add(label);

        return labelPanel;
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

    private JPanel createLoginAsCustomerButton() {
        JButton loginButton = new JButton("Login as Customer");
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String email = emailField.getText();
            if(engine.loginUser(username, password, email)){
                NavigationManager navigationManager = NavigationManager.getIstance(this);
                navigationManager.setEngine(this.engine);
                navigationManager.navigateToHomeCustomer();
            };

        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(loginButton);

        return buttonPanel;
    }
    private JPanel createLoginAsPT(){
        JButton loginAsPTButton = new JButton("Login as PT");
        loginAsPTButton.addActionListener(e -> {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            engine.loginPersonalTrainer(username, password,email);

        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(loginAsPTButton);
        return buttonPanel;
    }
    private JPanel createLoginButtonsPanel() {
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.add(createLoginAsCustomerButton());
        buttonsPanel.add(createLoginAsPT());
        return buttonsPanel;
    }
    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(createTitlePanel());
        mainPanel.add(createUsernamePanel());
        mainPanel.add(createEmailPanel());
        mainPanel.add(createPasswordPanel());
        mainPanel.add(createLoginButtonsPanel());
        mainPanel.add(createRegisterLink());
        return mainPanel;

    }
    private JPanel createRegisterLink() {
        JLabel registerLabel = new JLabel("<html><a href=''>Click here to register</a></html>");
        registerLabel.setForeground(Color.BLUE);
        registerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        NavigationManager navigationManager = NavigationManager.getIstance(this);
        navigationManager.setEngine(this.engine);
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Open registration window or perform registration action
                navigationManager.navigateToRegistrationPage();
            }
        });
        JPanel linkPanel = new JPanel(new FlowLayout());
        linkPanel.add(registerLabel);
        return linkPanel;
    }

    private void setupWindow() {
        setSize(1000, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
