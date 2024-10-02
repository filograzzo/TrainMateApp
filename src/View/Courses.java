package View;

import Controller.Engine;
import DomainModel.Course;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Courses extends JFrame {
    private Engine engine;
    private JList<String> courseList;
    private DefaultListModel<String> listModel1;
    private List<Course> courses;

    public Courses(Engine engine) {
        this.engine = engine;
        setupWindow();
        JPanel mainPanel = createMainPanel();
        add(mainPanel);
        setVisible(true);
    }

    private void setupWindow() {
        setTitle("Available Courses");
        setSize(1000, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        listModel1 = new DefaultListModel<>();
        courseList = new JList<>(listModel1);
        JScrollPane scrollPane = new JScrollPane(courseList);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton bookButton = new JButton("Book Course");
        JButton cancelButton = new JButton("Cancel Booking");
        buttonPanel.add(bookButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);

        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = courseList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Course selectedCourse = courses.get(selectedIndex);
                    engine.bookCourse(selectedCourse.getId());
                    JOptionPane.showMessageDialog(Courses.this, "Course booked successfully!");
                    loadCourses();

                } else {
                    JOptionPane.showMessageDialog(Courses.this, "Please select a course to book.");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = courseList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Course selectedCourse = courses.get(selectedIndex);
                    boolean success = engine.cancelBooking(selectedCourse.getId());
                    if (success) {
                        JOptionPane.showMessageDialog(Courses.this, "Booking canceled successfully!");
                        loadCourses();  // Reload the course list to reflect updated participants count
                    } else {
                        JOptionPane.showMessageDialog(Courses.this, "Unable to cancel the booking.");
                    }
                } else {
                    JOptionPane.showMessageDialog(Courses.this, "Please select a course to cancel.");
                }
            }
        });


        loadCourses();
        return mainPanel;
    }

    private void loadCourses() {
        // Fetch updated course data from the engine
        courses = engine.viewAvailableCourses();

        if (courses != null) {
            listModel1.clear();  // Clear the existing list items
            for (Course course : courses) {
                // Format the course details to display in the list;
                String courseDetails = String.format(
                        "Name: %s, Body Parts Trained: %s, Max Participants: %d, Participants: %d, Trainer ID: %d",
                        course.getName(), course.getBodyPartsTrained(), course.getMaxParticipants(), course.getParticipants(), course.getIDTrainer()
                );
                listModel1.addElement(courseDetails);  // Add the updated course details to the list
            }
        } else {
            // If no courses are available, show a placeholder message
            listModel1.addElement("No courses available.");
        }
    }


}