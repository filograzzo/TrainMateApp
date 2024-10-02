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
                    String newName = JOptionPane.showInputDialog(CoursesPT.this, "Enter new name:");
                    int newMaxParticipants = Integer.parseInt(JOptionPane.showInputDialog(CoursesPT.this, "Enter new max participants:"));
                    String newBodyPartsTrained = JOptionPane.showInputDialog(CoursesPT.this, "Enter new body parts trained:");
                    String newDate=JOptionPane.showInputDialog(CoursesPT.this, "Enter new date:");
                    String newTimeStr=JOptionPane.showInputDialog(CoursesPT.this, "Enter new time(HH:mm):")+":00";
                    Time newTime= Time.valueOf(newTimeStr);
                    engine.updateCourse(selectedCourse.getId(), newName, newMaxParticipants, selectedCourse.getIDTrainer(), newBodyPartsTrained,newDate,newTime);
                    loadCourses();
                } else {
                    JOptionPane.showMessageDialog(CoursesPT.this, "Please select a course to update.");
                }
            }
        });

        return mainPanel;
    }

    private void showAddCoursePanel() {
        JPanel addCoursePanel = new JPanel();
        JTextField nameField = new JTextField(20);
        JTextField bodyPartsTrainedField = new JTextField(20);
        JTextField maxParticipantsField = new JTextField(20);
        JTextField dayField = new JTextField(20);
        JTextField timeField = new JTextField(20);
        JButton submitButton = new JButton("Submit");

        addCoursePanel.add(new JLabel("Course Name:"));
        addCoursePanel.add(nameField);
        addCoursePanel.add(new JLabel("Body Parts Trained:"));
        addCoursePanel.add(bodyPartsTrainedField);
        addCoursePanel.add(new JLabel("Max Participants:"));
        addCoursePanel.add(maxParticipantsField);
        addCoursePanel.add(new JLabel("On what day?:"));
        addCoursePanel.add(dayField);
        addCoursePanel.add(new JLabel("At what time?(HH:mm):"));
        addCoursePanel.add(timeField);

        addCoursePanel.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                int maxParticipants = Integer.parseInt(maxParticipantsField.getText());
                String bodyPartsTrained = bodyPartsTrainedField.getText();
                String day = dayField.getText();
                String timeStr = timeField.getText() + ":00"; // Append seconds to the time string
                Time time = Time.valueOf(timeStr);
                engine.addCourse(name, maxParticipants, engine.getUser().getId(), bodyPartsTrained, day, time);
                loadCourses();
            }
        });

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
        backButton.addActionListener(e -> navigationManager.goBack());

        return backButton;
    }
}
