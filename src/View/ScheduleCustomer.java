package View;

import Controller.Engine;
import Controller.NavigationManager;
import DomainModel.BaseUser;
import DomainModel.Schedule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ScheduleCustomer extends JFrame {
    private Engine engine;
    private BaseUser baseUser; // L'utente corrente
    private JList<String> scheduleList;
    private DefaultListModel<String> listModel;
    private List<Schedule> schedules;
    private NavigationManager navigationManager = NavigationManager.getIstance(this);

    public ScheduleCustomer(Engine engine, BaseUser baseUser) throws SQLException {
        this.engine = engine;
        this.baseUser = baseUser;
        setupWindow();
        JPanel mainPanel = createMainPanel();
        add(mainPanel);
        setVisible(true);
    }

    private void setupWindow() {
        setTitle("My Schedules");
        setSize(1000, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JPanel createMainPanel() throws SQLException {
        JPanel mainPanel = new JPanel(new BorderLayout());
        listModel = new DefaultListModel<>();
        scheduleList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(scheduleList);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Schedule");
        JButton deleteButton = new JButton("Delete Schedule");
        JButton updateButton = new JButton("Update Schedule");
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        loadSchedules();
        buttonPanel.add(createBackButton());

        // Azione per aggiungere una schedule
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddSchedulePanel();
            }
        });

        // Azione per eliminare una schedule
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = scheduleList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Schedule selectedSchedule = schedules.get(selectedIndex);
                    engine.removeSchedule(baseUser, selectedSchedule);
                    try {
                        loadSchedules();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(ScheduleCustomer.this, "Please select a schedule to delete.");
                }
            }
        });

        // Azione per aggiornare una schedule
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = scheduleList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Schedule selectedSchedule = schedules.get(selectedIndex);
                    showUpdateSchedulePanel(selectedSchedule);
                } else {
                    JOptionPane.showMessageDialog(ScheduleCustomer.this, "Please select a schedule to update.");
                }
            }
        });

        // Azione per navigare ai dettagli di una schedule
        scheduleList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedIndex = scheduleList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Schedule selectedSchedule = schedules.get(selectedIndex);
                    try {
                        navigationManager.navigateToExerciseDetailCustomerView(selectedSchedule);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        return mainPanel;
    }

    private void loadSchedules() throws SQLException {
        // Carica solo le schedule dell'utente corrente (baseUser)
        schedules = engine.getSchedulesByUsername(baseUser);  // Metodo che recupera solo le schedule dell'utente
        if (schedules != null) {
            listModel.clear();
            for (Schedule schedule : schedules) {
                String scheduleInfo = String.format("Schedule Name: %s", schedule.getName());
                listModel.addElement(scheduleInfo);
            }
        } else {
            listModel.clear();
            listModel.addElement("No schedules available.");
        }
    }

    private void showAddSchedulePanel() {
        JPanel addSchedulePanel = new JPanel();
        JTextField nameField = new JTextField(20);
        JButton submitButton = new JButton("Submit");

        addSchedulePanel.add(new JLabel("Schedule Name:"));
        addSchedulePanel.add(nameField);
        addSchedulePanel.add(submitButton);

        JDialog dialog = new JDialog(this, "Add Schedule", true);
        dialog.setContentPane(addSchedulePanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String scheduleName = nameField.getText();
                engine.createSchedule(baseUser, scheduleName);  // Crea la schedule per l'utente corrente
                try {
                    loadSchedules();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }

    private void showUpdateSchedulePanel(Schedule schedule) {
        JPanel updateSchedulePanel = new JPanel();
        JTextField nameField = new JTextField(schedule.getName(), 20);
        JButton submitButton = new JButton("Submit");

        updateSchedulePanel.add(new JLabel("Schedule Name:"));
        updateSchedulePanel.add(nameField);
        updateSchedulePanel.add(submitButton);

        JDialog dialog = new JDialog(this, "Update Schedule", true);
        dialog.setContentPane(updateSchedulePanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                schedule.setName(nameField.getText());
                engine.updateSchedule(baseUser, schedule);  // Aggiorna la schedule per l'utente corrente
                try {
                    loadSchedules();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }

    private JButton createBackButton() {
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> navigationManager.navigateToHomeCustomer());
        return backButton;
    }
}
