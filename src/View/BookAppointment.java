package View;

import Controller.Engine;
import Controller.NavigationManager;
import DomainModel.Appointment;
import DomainModel.PersonalTrainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BookAppointment extends JFrame {
    private Engine engine;
    private JList<String> trainersList;
    private DefaultListModel<String> listModel;
    NavigationManager navigationManager = NavigationManager.getIstance(this);
    private ArrayList<PersonalTrainer> personalTrainers;
    private DefaultListModel<String> appointmentsListModel;
    private JList<String> appointmentsList;

    public BookAppointment(Engine engine) {
        this.engine = engine;
        setupWindow();
        JPanel mainPanel = createMainPanel();
        add(mainPanel);
        setVisible(true);
    }

    private void setupWindow() {
        setTitle("Personal Trainers available for an appointment");
        setSize(1000, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        listModel = new DefaultListModel<>();
        trainersList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(trainersList);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton bookAppointmentButton = new JButton("Book Appointment");
        buttonPanel.add(bookAppointmentButton);
        buttonPanel.add(createBackButton());
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        bookAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = trainersList.getSelectedIndex();
                if (selectedIndex != -1) {
                    showAppointmentDialog(personalTrainers.get(selectedIndex));
                } else {
                    JOptionPane.showMessageDialog(BookAppointment.this, "Please select a trainer first.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton ViewAppointmentsButton = new JButton("View Appointments");
        buttonPanel.add(ViewAppointmentsButton);

        ViewAppointmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAppointmentsPanel();
            }
        });

        loadPersonalTrainers();
        return mainPanel;
    }
    private void showAppointmentsPanel() {
        ArrayList<Appointment> appointments = engine.getAllAppointments(engine.getUser().getId());
        JPanel appointmentsPanel = new JPanel(new BorderLayout());
        appointmentsListModel = new DefaultListModel<>();
        appointmentsList = new JList<>(appointmentsListModel);
        JScrollPane scrollPane = new JScrollPane(appointmentsList);
        appointmentsPanel.add(scrollPane, BorderLayout.CENTER);

        JButton deleteAppointmentButton = new JButton("Delete Appointment");
        appointmentsPanel.add(deleteAppointmentButton, BorderLayout.SOUTH);

        if (appointments != null && !appointments.isEmpty()) {
            for (Appointment appointment : appointments) {
                appointmentsListModel.addElement("PT: " + engine.getPTname(appointment.getPersonalTrainerId()) +
                        " " + appointment.getDay() +
                        " " + appointment.getTime());
            }
        } else {
            appointmentsListModel.addElement("No appointments available.");
        }

        JDialog dialog = new JDialog(this, "View Appointments", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.add(appointmentsPanel);

        deleteAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Delete button clicked"); // Debugging output
                int selectedIndex = appointmentsList.getSelectedIndex();

                if (selectedIndex != -1) {
                    String selectedAppointment = appointmentsListModel.getElementAt(selectedIndex);
                    String[] parts = selectedAppointment.split(" ");
                    String day = parts[2];
                    String timeStr = parts[3];

                    // Ensure the time string is in the correct format
                    if (!timeStr.contains(":")) {
                        timeStr += ":00"; // Ensure the correct format
                    }

                    try {
                        Time time = Time.valueOf(timeStr); // Correct format for Time.valueOf()

                        int id = engine.getAppointmentIdbyDAYandTime(day, time);
                        System.out.println("Appointment ID: " + id); // Debugging output

                        int PTid = engine.getPTidByAppointmentId(id);
                        System.out.println("Personal Trainer ID: " + PTid); // Debugging output

                        // Only proceed if the appointment exists and deletion is successful
                        if (id != -1 && engine.removeAppointment(id, PTid)) {
                            // Remove from the list model if deletion from DB is successful
                            appointmentsListModel.remove(selectedIndex);
                            JOptionPane.showMessageDialog(BookAppointment.this, "Appointment deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            // If removal fails, show the error dialog
                            JOptionPane.showMessageDialog(BookAppointment.this, "Error deleting appointment. Appointment may not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IllegalArgumentException ex) {
                        ex.printStackTrace(); // This will print the exception details to the console
                        JOptionPane.showMessageDialog(BookAppointment.this, "Invalid time format: " + timeStr, "Error", JOptionPane.ERROR_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(BookAppointment.this, "Please select an appointment first.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        });
        dialog.setVisible(true);
    }



    private void loadPersonalTrainers() {
        personalTrainers = engine.getAllPersonalTrainers();
        if (personalTrainers != null) {
            listModel.clear();
            for (PersonalTrainer trainer : personalTrainers) {
                listModel.addElement(trainer.getUsername());
            }
        } else {
            listModel.addElement("No personal trainers available.");
        }
    }

    private void showAppointmentDialog(PersonalTrainer trainer) {
        JDialog dialog = new JDialog(this, "Book Appointment", true);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        JLabel dayLabel = new JLabel("Day:");
        JTextField dayField = new JTextField();
        JLabel timeLabel = new JLabel("Time:"+"(hh:mm)");
        JTextField timeField = new JTextField();

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String day = dayField.getText();
                String time = timeField.getText();
                try {
                    String date = getNextDayName(day);
                    Time sqlTime = Time.valueOf(time + ":00");
                    if(engine.checkifFreePT(trainer.getId(), date, sqlTime)){
                        if(engine.bookAppointment(trainer.getId(),engine.getUser().getId(), date, sqlTime)){
                            dialog.dispose();
                            JOptionPane.showMessageDialog(BookAppointment.this, "Appointment booked successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                        else{
                            JOptionPane.showMessageDialog(BookAppointment.this, "Error booking appointment.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(BookAppointment.this, "The personal trainer is not available at that time.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (ParseException | IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(BookAppointment.this, "Invalid date or time format.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(dayLabel);
        panel.add(dayField);
        panel.add(timeLabel);
        panel.add(timeField);
        panel.add(new JLabel()); // Empty cell
        panel.add(submitButton);

        dialog.add(panel);
        dialog.setVisible(true);
    }
    private String getNextDayName(String day) throws ParseException {
        String[] weekdays = new DateFormatSymbols(Locale.ENGLISH).getWeekdays();
        int dayOfWeek = -1;
        for (int i = 0; i < weekdays.length; i++) {
            if (weekdays[i].equalsIgnoreCase(day)) {
                dayOfWeek = i;
                break;
            }
        }
        if (dayOfWeek == -1) {
            throw new ParseException("Invalid day of the week: " + day, 0);
        }

        Calendar calendar = Calendar.getInstance();
        int currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int daysUntilNext = (dayOfWeek - currentDayOfWeek + 7) % 7;
        if (daysUntilNext == 0) {
            daysUntilNext = 7; // If the day is today, set it to the next week
        }
        calendar.add(Calendar.DAY_OF_YEAR, daysUntilNext);

        return weekdays[calendar.get(Calendar.DAY_OF_WEEK)];
    }
    private JButton createBackButton() {
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> navigationManager.navigateToHomeCustomer());

        return backButton;
    }
}
