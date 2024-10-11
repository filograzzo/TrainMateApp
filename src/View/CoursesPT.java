package View;

import Controller.Engine;
import Controller.NavigationManager;
import DomainModel.Course;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

public class CoursesPT extends JFrame {
    private Engine engine;
    private JList<String> courseList;
    NavigationManager navigationManager = NavigationManager.getIstance(this);
    private DefaultListModel<String> listModel;
    private List<Course> courses;

    public CoursesPT(Engine engine) {
        this.engine = engine;
        setupWindow();
        JPanel mainPanel = createMainPanel();
        add(mainPanel);
        setVisible(true);
    }

    private void setupWindow() {
        setTitle("Personal Trainer Courses");
        setSize(1000, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }



    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        listModel = new DefaultListModel<>();
        courseList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(courseList);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Course");
        JButton deleteButton = new JButton("Delete Course");
        JButton updateButton = new JButton("Update Course");
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        loadCourses();
        buttonPanel.add(createBackButton());


        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddCoursePanel();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = courseList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Course selectedCourse = courses.get(selectedIndex);
                    engine.deleteCourse(selectedCourse.getId());
                    loadCourses();
                } else {
                    JOptionPane.showMessageDialog(CoursesPT.this, "Please select a course to delete.");
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = courseList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Course selectedCourse = courses.get(selectedIndex);

                    JPanel updateCoursePanel = new JPanel(new GridBagLayout());
                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.insets = new Insets(5, 5, 5, 5);
                    gbc.fill = GridBagConstraints.HORIZONTAL;

                    JTextField nameField = new JTextField(selectedCourse.getName(), 20);
                    JTextField bodyPartsField = new JTextField(selectedCourse.getBodyPartsTrained(), 20);
                    JTextField maxParticipantsField = new JTextField(String.valueOf(selectedCourse.getMaxParticipants()), 20);
                    JTextField dayField = new JTextField(selectedCourse.getDay(), 20);
                    JTextField timeField = new JTextField(selectedCourse.getTime().toString().substring(0, 5), 20);

                    // Prima riga
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    updateCoursePanel.add(new JLabel("Course Name:"), gbc);
                    gbc.gridx = 1;
                    updateCoursePanel.add(nameField, gbc);

                    // Seconda riga
                    gbc.gridx = 0;
                    gbc.gridy = 1;
                    updateCoursePanel.add(new JLabel("Body Parts Trained:"), gbc);
                    gbc.gridx = 1;
                    updateCoursePanel.add(bodyPartsField, gbc);

                    // Terza riga
                    gbc.gridx = 0;
                    gbc.gridy = 2;
                    updateCoursePanel.add(new JLabel("Max Participants:"), gbc);
                    gbc.gridx = 1;
                    updateCoursePanel.add(maxParticipantsField, gbc);

                    // Quarta riga
                    gbc.gridx = 0;
                    gbc.gridy = 3;
                    updateCoursePanel.add(new JLabel("On what day?:"), gbc);
                    gbc.gridx = 1;
                    updateCoursePanel.add(dayField, gbc);

                    // Quinta riga
                    gbc.gridx = 0;
                    gbc.gridy = 4;
                    updateCoursePanel.add(new JLabel("At what time?(HH:mm):"), gbc);
                    gbc.gridx = 1;
                    updateCoursePanel.add(timeField, gbc);

                    // Mostra il dialog senza icona
                    int result = JOptionPane.showConfirmDialog(
                            null,
                            updateCoursePanel,
                            "Update Course",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE // Rimuove l'icona predefinita
                    );

                    if (result == JOptionPane.OK_OPTION) {
                        String newName = nameField.getText();
                        int newMaxParticipants = Integer.parseInt(maxParticipantsField.getText());
                        String newBodyPartsTrained = bodyPartsField.getText();
                        String newDate = dayField.getText();
                        String newTimeStr = timeField.getText() + ":00";
                        Time newTime = Time.valueOf(newTimeStr);

                        engine.updateCourse(selectedCourse.getId(), newName, newMaxParticipants, selectedCourse.getIDTrainer(), newBodyPartsTrained, newDate, newTime);
                        loadCourses();
                    }
                } else {
                    JOptionPane.showMessageDialog(CoursesPT.this, "Please select a course to update.");
                }
            }
        });





        return mainPanel;
    }

    private void showAddCoursePanel() {
        // Imposta il layout del pannello per l'aggiunta di un corso
        JPanel addCoursePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Margini tra i componenti
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Crea i campi di input
        JTextField nameField = new JTextField(20);
        JTextField bodyPartsTrainedField = new JTextField(20);
        JTextField maxParticipantsField = new JTextField(20);
        JTextField dayField = new JTextField(20);
        JTextField timeField = new JTextField(20);

        // Prima riga: Course Name
        gbc.gridx = 0; // Colonna 0
        gbc.gridy = 0; // Riga 0
        addCoursePanel.add(new JLabel("Course Name:"), gbc);
        gbc.gridx = 1; // Colonna 1
        addCoursePanel.add(nameField, gbc);

        // Seconda riga: Body Parts Trained
        gbc.gridx = 0;
        gbc.gridy = 1;
        addCoursePanel.add(new JLabel("Body Parts Trained:"), gbc);
        gbc.gridx = 1;
        addCoursePanel.add(bodyPartsTrainedField, gbc);

        // Terza riga: Max Participants
        gbc.gridx = 0;
        gbc.gridy = 2;
        addCoursePanel.add(new JLabel("Max Participants:"), gbc);
        gbc.gridx = 1;
        addCoursePanel.add(maxParticipantsField, gbc);

        // Quarta riga (vai a capo): On what day?
        gbc.gridx = 0;
        gbc.gridy = 3;
        addCoursePanel.add(new JLabel("On what day?:"), gbc);
        gbc.gridx = 1;
        addCoursePanel.add(dayField, gbc);

        // Quinta riga: At what time? (HH:mm)
        gbc.gridx = 0;
        gbc.gridy = 4;
        addCoursePanel.add(new JLabel("At what time?(HH:mm):"), gbc);
        gbc.gridx = 1;
        addCoursePanel.add(timeField, gbc);

        // Aggiungi il bottone "Submit" alla fine
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2; // Estendi il bottone su entrambe le colonne
        gbc.anchor = GridBagConstraints.CENTER; // Centra il bottone
        JButton submitButton = new JButton("Submit");
        addCoursePanel.add(submitButton, gbc);

        // Azione del bottone "Submit"
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Estrai i dati inseriti dall'utente
                    String name = nameField.getText();
                    int maxParticipants = Integer.parseInt(maxParticipantsField.getText());
                    String bodyPartsTrained = bodyPartsTrainedField.getText();
                    String day = dayField.getText();
                    String timeStr = timeField.getText() + ":00"; // Aggiungi i secondi
                    Time time = Time.valueOf(timeStr);

                    // Aggiungi il corso tramite il motore
                    engine.addCourse(name, maxParticipants, engine.getUser().getId(), bodyPartsTrained, day, time);

                    // Ricarica la lista dei corsi
                    loadCourses();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(CoursesPT.this, "Please enter valid data for Max Participants and Time.");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(CoursesPT.this, "Invalid time format. Please enter time in HH:mm format.");
                }
            }
        });

        // Crea e mostra il dialogo per aggiungere il corso
        JDialog dialog = new JDialog(this, "Add Course", true);
        dialog.setContentPane(addCoursePanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void loadCourses() {
        courses = engine.viewCoursesToTake();
        if (courses != null) {
            listModel.clear();
            for (Course course : courses) {
                String namePT = engine.getCoursePTname(course.getIDTrainer());
                String courseDetails = String.format("Name: %s, Body Parts Trained: %s, Max Participants: %d, Participants: %d, Trainer: %s, Day: %s, Time: %s",
                        course.getName(), course.getBodyPartsTrained(), course.getMaxParticipants(), course.getParticipants(), namePT, course.getDay(), course.getTime());
                listModel.addElement(courseDetails);
            }
        } else {
            listModel.clear();
            listModel.addElement("You don't have to teach any course");
        }
    }


    private JButton createBackButton() {
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> navigationManager.navigateToHomePT());

        return backButton;
    }
}
