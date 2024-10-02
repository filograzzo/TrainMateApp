package View;

import Controller.Engine;
import Controller.NavigationManager;
import DomainModel.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class ProfilePT extends JFrame {
    private Engine engine;
    NavigationManager navigationManager = NavigationManager.getIstance(this);
    private JButton modifyUsernameButton;
    private JButton modifyPasswordButton;
    private JButton modifyEmailButton;
    private JButton backButton;
    public ProfilePT(Engine engine) {
        this.engine = engine;
        setupWindow();
        JPanel mainPanel = createMainPanel();
        add(mainPanel);
        setVisible(true);
    }
    private void setupWindow() {
        setTitle("Customer Profile");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private void setPanelWidth(JPanel panel, int width) {
        Dimension size = panel.getPreferredSize();
        size.width = width;
        panel.setPreferredSize(size);
    }
    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        // Create empty panels for left and right margins
        JPanel leftMargin = new JPanel();
        setPanelWidth(leftMargin, 300);
        JPanel rightMargin = new JPanel();
        setPanelWidth(rightMargin, 300);

        // Add the title panel to the top
        mainPanel.add(createTitlePanel(), BorderLayout.NORTH);

        // Add the form panel to the center
        mainPanel.add(createFormPanel(), BorderLayout.CENTER);

        // Add the empty panels to the left and right
        mainPanel.add(leftMargin, BorderLayout.WEST);
        mainPanel.add(rightMargin, BorderLayout.EAST);
        // Add the button panel to the bottom
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);
        return mainPanel;
    }
    private JPanel createTitlePanel() {
        JLabel label = new JLabel("Customer Profile", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 36));
        JPanel labelPanel = new JPanel(new FlowLayout());
        labelPanel.add(label);
        return labelPanel;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 1, 1)); // 2 rows, 2 columns, with spacing between components

        // Get username and email from the engine
        String username = engine.getUser().getUsername();
        String email = engine.getUser().getEmail();

        // Add labels and values
        formPanel.add(new JLabel("Username")); // First column, first row
        formPanel.add(new JLabel(username));    // Second column, first row

        formPanel.add(new JLabel("Email"));     // First column, second row
        formPanel.add(new JLabel(email));       // Second column, second row
        return formPanel;
    }

    private void showModifyUsernamePanel() {
        JPanel modifyUsernamePanel = new JPanel(new FlowLayout());
        JTextField usernameField = new JTextField(20);
        JButton submitUsernameButton = new JButton("Submit");

        submitUsernameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String oldUsername = engine.getUser().getUsername();
                String newUsername = usernameField.getText();
                engine.modifyUsernamePT(oldUsername,newUsername);
                refreshFormPanel();
            }
        });

        modifyUsernamePanel.add(new JLabel("New Username:"));
        modifyUsernamePanel.add(usernameField);
        modifyUsernamePanel.add(submitUsernameButton);

        JDialog dialog = new JDialog(this, "Modify Username", true);
        dialog.setContentPane(modifyUsernamePanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

    }
    private void showModifyEmailPanel() {
        JPanel modifyEmailPanel = new JPanel(new FlowLayout());
        JTextField emailField = new JTextField(20);
        JButton submitEmailButton = new JButton("Submit");

        submitEmailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newEmail = emailField.getText();
                engine.modifyEmailPT(engine.getUser().getId(), newEmail);
                refreshFormPanel();
            }
        });

        modifyEmailPanel.add(new JLabel("New Email:"));
        modifyEmailPanel.add(emailField);
        modifyEmailPanel.add(submitEmailButton);

        JDialog dialog = new JDialog(this, "Modify Email", true);
        dialog.setContentPane(modifyEmailPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    private void showModifyPasswordPanel() {
        JPanel modifyPasswordPanel = new JPanel(new FlowLayout());
        JPasswordField oldPasswordField = new JPasswordField(20);
        JPasswordField newPasswordField = new JPasswordField(20);
        JButton submitPasswordButton = new JButton("Submit");

        submitPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String oldPassword = new String(oldPasswordField.getPassword());
                String newPassword = new String(newPasswordField.getPassword());
                engine.modifyPasswordPT(engine.getUser().getId(), newPassword, oldPassword);
                refreshFormPanel();
            }
        });

        modifyPasswordPanel.add(new JLabel("Old Password:"));
        modifyPasswordPanel.add(oldPasswordField);
        modifyPasswordPanel.add(new JLabel("New Password:"));
        modifyPasswordPanel.add(newPasswordField);
        modifyPasswordPanel.add(submitPasswordButton);

        JDialog dialog = new JDialog(this, "Modify Password", true);
        dialog.setContentPane(modifyPasswordPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }


    private void refreshFormPanel() {
        getContentPane().removeAll();
        JPanel mainPanel = createMainPanel();
        add(mainPanel);
        revalidate();
        repaint();
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());

        modifyUsernameButton = new JButton("Modify Username");
        modifyPasswordButton = new JButton("Modify Password");
        modifyEmailButton = new JButton("Modify Email");
        backButton = new JButton("Back");

        // Add action listeners for each button
        modifyUsernameButton.addActionListener(e -> {
            showModifyUsernamePanel();
        });

        modifyPasswordButton.addActionListener(e -> {
            showModifyPasswordPanel();
        });

        modifyEmailButton.addActionListener(e -> {
            showModifyEmailPanel();
        });

        backButton.addActionListener(e -> {
            navigationManager.goBack();
        });

        // Add buttons to the panel
        buttonPanel.add(modifyUsernameButton);
        buttonPanel.add(modifyPasswordButton);
        buttonPanel.add(modifyEmailButton);
        buttonPanel.add(backButton);

        return buttonPanel;
    }


}

