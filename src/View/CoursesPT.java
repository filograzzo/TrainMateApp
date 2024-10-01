package View;

import Controller.Engine;
import DomainModel.Course;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CoursesPT extends JFrame {
    private Engine engine;
    private JList<String> courseList;
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
                    // Logic to update a course
                    // Example: showUpdateCourseDialog(selectedCourse);
                } else {
                    JOptionPane.showMessageDialog(CoursesPT.this, "Please select a course to update.");
                }
            }
        });

        loadCourses();
        return mainPanel;
    }

    private void showAddCoursePanel() {
        JPanel addCoursePanel = new JPanel();
        JTextField nameField = new JTextField(20);
        JTextField bodyPartsTrainedField = new JTextField(20);
        JTextField maxParticipantsField = new JTextField(20);
        JButton submitButton = new JButton("Submit");

        addCoursePanel.add(new JLabel("Course Name:"));
        addCoursePanel.add(nameField);
        addCoursePanel.add(new JLabel("Body Parts Trained:"));
        addCoursePanel.add(bodyPartsTrainedField);
        addCoursePanel.add(new JLabel("Max Participants:"));
        addCoursePanel.add(maxParticipantsField);

        addCoursePanel.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                int maxParticipants = Integer.parseInt(maxParticipantsField.getText());
                String bodyPartsTrained = bodyPartsTrainedField.getText();
                Course c= new Course(0, name, maxParticipants, engine.getUser().getId(), bodyPartsTrained);
                engine.addorUpdateCourse(c);
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
                String courseDetails = String.format("Name: %s, Body Parts Trained: %s, Max Participants: %d, Participants: %d, Trainer ID: %d",
                        course.getName(), course.getBodyPartsTrained(), course.getMaxParticipants(), course.getParticipants(), course.getIDTrainer());
                listModel.addElement(courseDetails);
            }
        } else {
            JTextField error = new JTextField("You don't have to teach any course");
        }

    }
}
