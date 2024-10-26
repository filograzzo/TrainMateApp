package View.PT;

import Controller.Engine;
import Controller.NavigationManager;
import DomainModel.Appointment;
import DomainModel.Course;

import javax.swing.*;
import java.awt.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.text.SimpleDateFormat;


public class Agenda extends JFrame {
    private Engine engine;
    private JPanel calendarPanel; // Instance variable to be accessible in the whole class
    private List<String> days;  // For day mapping
    NavigationManager navigationManager = NavigationManager.getIstance(this);
    private String[] times;

    public Agenda(Engine engine) {
        this.engine = engine;
        setupWindow();
        JPanel mainPanel = createMainPanel();
        add(mainPanel);
        populateAgendawithCourses(); // Call this method to populate the agenda on load
        populateAgendawithAppointments();
        setVisible(true);
    }

    private void setupWindow() {
        setTitle("Agenda");
        setSize(1000, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JPanel createMainPanel() {
        calendarPanel = new JPanel(new GridBagLayout()); // Use GridBagLayout for flexible positioning
        GridBagConstraints gbc = new GridBagConstraints();
        days = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        times = new String[]{"09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00"};

        // Add empty top-left corner
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;  // Slight weight for the empty corner
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        calendarPanel.add(createBackButton()); // Empty top-left cell

        // Add days of the week at the top (row 0, starting from column 1)
        for (int i = 0; i < days.size(); i++) {
            gbc.gridx = i + 1;
            gbc.gridy = 0;
            gbc.weightx = 1.0;  // Stretch evenly across
            gbc.weighty = 0.1;  // Small vertical weight for the header
            calendarPanel.add(new JLabel(days.get(i), SwingConstants.CENTER), gbc);
        }

        // Add time labels in the left column
        for (int timeIndex = 0; timeIndex < times.length; timeIndex++) {
            gbc.gridx = 0;
            gbc.gridy = timeIndex + 1;
            gbc.weightx = 0.1;  // Less horizontal weight for time labels
            gbc.weighty = 1.0;  // Vertical weight to stretch time rows
            calendarPanel.add(new JLabel(times[timeIndex]), gbc);  // Time label column

            // Create placeholders (empty panels) for each day's time slots
            for (int dayIndex = 0; dayIndex < days.size(); dayIndex++) {
                gbc.gridx = dayIndex + 1;
                gbc.gridy = timeIndex + 1;
                gbc.weightx = 1.0;  // Stretch evenly in horizontal direction
                gbc.weighty = 1.0;  // Stretch evenly in vertical direction
                JPanel slotPanel = new JPanel();
                slotPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Optional: Add border to slots
                gbc.fill = GridBagConstraints.BOTH;
                calendarPanel.add(slotPanel, gbc);
            }
        }

        return calendarPanel;
    }

    public JPanel getSlotPanel(int dayIndex, int timeIndex) {
        // Return specific slot panel (based on index of day and time)
        int componentIndex = (timeIndex + 1) * 8 + (dayIndex + 1);  // Calculate correct component index
        return (JPanel) calendarPanel.getComponent(componentIndex);
    }

    // Method to populate the agenda with courses
    public void populateAgendawithCourses() {
        List<Course> courses = engine.viewCoursesToTake();  // Retrieve courses via Engine
        if (courses == null || courses.isEmpty()) {
            System.out.println("No courses to display.");
            return;
        }else{
            for (Course course : courses) {
                // Extract the day and time information from the course
                int dayIndex = getDayIndex(course.getDay());
                int timeIndex = getTimeIndex(course.getTime());

                // Ensure indices are valid before populating the panel
                if (dayIndex != -1 && timeIndex != -1) {
                    JPanel slotPanel = getSlotPanel(dayIndex, timeIndex);
                    slotPanel.setLayout(new BorderLayout());

                    JLabel courseLabel = new JLabel("<html><b>" + course.getName(), SwingConstants.CENTER);
                    slotPanel.add(courseLabel, BorderLayout.CENTER);
                    slotPanel.setBackground(Color.LIGHT_GRAY); // Optional: highlight the slot for a course
                }
            }

        }
    }
    public void populateAgendawithAppointments(){
        ArrayList<Appointment> appointments = engine.viewAppointmentsToHave();
        if(appointments == null || appointments.isEmpty()){
            System.out.println("No appointments to display.");
            return;
        }else{
            System.out.println("Appointments to display.");
            for(Appointment appointment: appointments){
                // Extract the day and time information from the course
                int dayIndex = getDayIndex(appointment.getDay());
                int timeIndex = getTimeIndex(appointment.getTime());

                System.out.println("Appointment for Day: " + appointment.getDay() + " has Day Index: " + dayIndex);
                System.out.println("Appointment for Time: " + appointment.getTime() + " has Time Index: " + timeIndex);


                // Ensure indices are valid before populating the panel
                if (dayIndex != -1 && timeIndex != -1) {
                    JPanel slotPanel = getSlotPanel(dayIndex, timeIndex);
                    slotPanel.setLayout(new BorderLayout());

                    JLabel appointmentLabel = new JLabel("<html>Appointment with<b>" +engine.getClientname( appointment.getCustomerId()), SwingConstants.CENTER);
                    slotPanel.add(appointmentLabel, BorderLayout.CENTER);
                    slotPanel.setBackground(Color.LIGHT_GRAY); // Optional: highlight the slot for a course
                }
            }
        }

    }
    // Helper method to map the course's day to the day index
    private int getDayIndex(String day) {
        List<String> daysOfWeek = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        return daysOfWeek.indexOf(day);  // This will return the index of the day (0 = Monday, 6 = Sunday)
    }

    // Helper method to map the course's time to the time index
    private int getTimeIndex(Time time) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String timeStr = timeFormat.format(time);  // Properly format the Time object
        System.out.println("Comparing Time: " + timeStr);
        for (int i = 0; i < times.length; i++) {
            if (times[i].equals(timeStr)) {
                return i;
            }
        }
        System.out.println("Time index not found for: " + timeStr);
        return -1;
    }

    private JButton createBackButton() {
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> navigationManager.navigateToHomePT());

        return backButton;
    }
}



