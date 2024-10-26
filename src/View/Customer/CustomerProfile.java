package View.Customer;

import Controller.Engine;
import Controller.NavigationManager;
import DomainModel.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class CustomerProfile extends JFrame {
    private Engine engine;
    NavigationManager navigationManager = NavigationManager.getIstance(this);
    private JButton modifyUsernameButton;
    private JButton modifyPasswordButton;
    private JButton modifyEmailButton;
    private JButton updatePersonalDataButton;
    private JButton deletePersonalDataButton;
    private JButton backButton;
    public CustomerProfile(Engine engine) {
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
        JPanel formPanel = new JPanel(new GridLayout(8, 2,1,2));

        String username = engine.getUser().getUsername();
        formPanel.add(new JLabel("Username"));
        formPanel.add(new JLabel(username));

        String email = engine.getUser().getEmail();
        formPanel.add(new JLabel("email"));
        formPanel.add(new JLabel(email));

        Float height = ((Customer)engine.getUser()).getHeight();
        formPanel.add(new JLabel("Height"));
        formPanel.add(new JLabel(height != null ? height.toString() : ""));

        Float weight = ((Customer)engine.getUser()).getWeight();
        formPanel.add(new JLabel("Weight"));
        formPanel.add(new JLabel(weight != null ? weight.toString() : ""));

        Integer age = ((Customer)engine.getUser()).getAge();
        formPanel.add(new JLabel("Age"));
        formPanel.add(new JLabel(age != null ? age.toString() : ""));

        String gender = ((Customer)engine.getUser()).getGender();
        formPanel.add(new JLabel("Gender"));
        formPanel.add(new JLabel(gender != null ? gender : ""));

        String goal = ((Customer)engine.getUser()).getGoal();
        formPanel.add(new JLabel("Goal"));
        formPanel.add(new JLabel(goal != null ? goal : ""));
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
                engine.updateUsernameClient(oldUsername,newUsername);
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
                engine.updateEmailClient(engine.getUser().getUsername(), newEmail);
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
                engine.updatePasswordClient(engine.getUser().getUsername(), newPassword, oldPassword);
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
    private void showUpdatePersonalDataPanel() {
        JPanel addPersonalDataPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        JTextField heightField = new JTextField(20);
        JTextField weightField = new JTextField(20);
        JTextField ageField = new JTextField(20);
        JTextField genderField = new JTextField(20);
        JTextField goalField = new JTextField(20);
        JButton submitPersonalDataButton = new JButton("Submit");
        addPersonalDataPanel.add(new JLabel("Height:"));
        addPersonalDataPanel.add(heightField);
        addPersonalDataPanel.add(new JLabel("Weight:"));
        addPersonalDataPanel.add(weightField);
        addPersonalDataPanel.add(new JLabel("Age:"));
        addPersonalDataPanel.add(ageField);
        addPersonalDataPanel.add(new JLabel("Gender:"));
        addPersonalDataPanel.add(genderField);
        addPersonalDataPanel.add(new JLabel("Goal:"));
        addPersonalDataPanel.add(goalField);
        addPersonalDataPanel.add(submitPersonalDataButton);

        submitPersonalDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Float height = Float.parseFloat(heightField.getText());
                Float weight = Float.parseFloat(weightField.getText());
                Integer age = Integer.parseInt(ageField.getText());
                String gender = genderField.getText();
                String goal = goalField.getText();
                engine.updatePersonalData(((Customer)engine.getUser()).getId(), height, weight,age,gender,goal);

                refreshFormPanel();
            }
        });

        JDialog dialog = new JDialog(this, "Add Personal Data", true);
        dialog.setContentPane(addPersonalDataPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    private void showDeletePersonalDataPanel() {
        JPanel deletePersonalDataPanel = new JPanel(new FlowLayout());
        JButton deletePersonalDataButton = new JButton("I want to delete my personal data");
        deletePersonalDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                engine.deletePersonalData(((Customer)engine.getUser()).getId());
                refreshFormPanel();
            }
        });
        deletePersonalDataPanel.add(deletePersonalDataButton);

        JDialog dialog = new JDialog(this, "Delete Personal Data", true);
        dialog.setContentPane(deletePersonalDataPanel);
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
        updatePersonalDataButton = new JButton("Update Personal Data");
        deletePersonalDataButton = new JButton("Delete Personal Data");
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

        updatePersonalDataButton.addActionListener(e -> {
            showUpdatePersonalDataPanel();
        });

        deletePersonalDataButton.addActionListener(e -> {
            showDeletePersonalDataPanel();
            // Handle delete personal data action
        });

        backButton.addActionListener(e -> {
            navigationManager.navigateToHomeCustomer();
        });

        // Add buttons to the panel
        buttonPanel.add(modifyUsernameButton);
        buttonPanel.add(modifyPasswordButton);
        buttonPanel.add(modifyEmailButton);
        buttonPanel.add(updatePersonalDataButton);
        buttonPanel.add(deletePersonalDataButton);
        buttonPanel.add(backButton);

        return buttonPanel;
    }


}
