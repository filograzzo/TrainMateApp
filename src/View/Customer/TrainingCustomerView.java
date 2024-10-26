package View.Customer;

import Controller.Engine;
import Controller.NavigationManager;
import DomainModel.BaseUser;
import DomainModel.Schedule;
import DomainModel.Training;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

public class TrainingCustomerView extends JFrame {
    private Engine engine;
    private BaseUser baseUser; // L'utente corrente
    private JList<String> trainingList;
    private DefaultListModel<String> listModel;
    private List<Training> trainings;
    private List<Schedule> schedules; // Schedules per il cliente
    private NavigationManager navigationManager = NavigationManager.getIstance(this);

    public TrainingCustomerView(Engine engine, BaseUser baseUser) throws SQLException {
        this.engine = engine;
        this.baseUser = baseUser;
        setupWindow();
        JPanel mainPanel = createMainPanel();
        add(mainPanel);
        setVisible(true);
    }

    private void setupWindow() {
        setTitle("Training Management");
        setSize(1000, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JPanel createMainPanel() throws SQLException {
        JPanel mainPanel = new JPanel(new BorderLayout());
        listModel = new DefaultListModel<>();
        trainingList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(trainingList);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Training");
        JButton deleteButton = new JButton("Delete Training");
        JButton updateButton = new JButton("Update Training");
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        loadTrainings();
        buttonPanel.add(createBackButton());

        // Azione per aggiungere un allenamento
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    showAddTrainingPanel();  // Aggiorniamo qui
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // Azione per eliminare un allenamento
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = trainingList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Training selectedTraining = trainings.get(selectedIndex);
                    engine.deleteTraining(baseUser, selectedTraining);
                    try {
                        loadTrainings();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(TrainingCustomerView.this, "Please select a training to delete.");
                }
            }
        });

        // Azione per aggiornare un allenamento
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = trainingList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Training selectedTraining = trainings.get(selectedIndex);
                    showUpdateTrainingPanel(selectedTraining);
                } else {
                    JOptionPane.showMessageDialog(TrainingCustomerView.this, "Please select a training to update.");
                }
            }
        });

        return mainPanel;
    }

    private void loadTrainings() throws SQLException {
        trainings = engine.getAllTrainingsByUsername(baseUser);  // Recupera gli allenamenti per l'utente corrente
        if (trainings != null) {
            listModel.clear();
            for (Training training : trainings) {
                String trainingInfo = String.format("Training on %s, Start: %s, End: %s, Note: %s",
                        training.getDate(), training.getStartTime(), training.getEndTime(), training.getNote());
                listModel.addElement(trainingInfo);
            }
        } else {
            listModel.clear();
            listModel.addElement("No trainings available.");
        }
    }

    // Aggiungere un allenamento con un JComboBox che mostra solo le schedule del customer corrente
    private void showAddTrainingPanel() throws SQLException {
        JPanel addTrainingPanel = new JPanel();
        JTextField dateField = new JTextField(10);
        JTextField startTimeField = new JTextField(10);
        JTextField endTimeField = new JTextField(10);
        JTextField noteField = new JTextField(20);

        JComboBox<String> scheduleComboBox = new JComboBox<>();
        loadSchedulesForCustomer();  // Carica le schedule per il customer corrente

        for (Schedule schedule : schedules) {
            scheduleComboBox.addItem(schedule.getName());
        }

        JButton submitButton = new JButton("Submit");

        addTrainingPanel.add(new JLabel("Date (yyyy-MM-dd):"));
        addTrainingPanel.add(dateField);
        addTrainingPanel.add(new JLabel("Start Time (HH:mm:ss):"));
        addTrainingPanel.add(startTimeField);
        addTrainingPanel.add(new JLabel("End Time (HH:mm:ss):"));
        addTrainingPanel.add(endTimeField);
        addTrainingPanel.add(new JLabel("Note:"));
        addTrainingPanel.add(noteField);
        addTrainingPanel.add(new JLabel("Schedule:"));
        addTrainingPanel.add(scheduleComboBox);
        addTrainingPanel.add(submitButton);

        JDialog dialog = new JDialog(this, "Add Training", true);
        dialog.setContentPane(addTrainingPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String date = dateField.getText();
                Time startTime = Time.valueOf(startTimeField.getText());
                Time endTime = Time.valueOf(endTimeField.getText());
                String note = noteField.getText();
                String scheduleName = (String) scheduleComboBox.getSelectedItem();
                Schedule selectedSchedule = schedules.stream()
                        .filter(s -> s.getName().equals(scheduleName))
                        .findFirst()
                        .orElse(null);

                if (selectedSchedule != null) {
                    try {
                        engine.createTraining(java.sql.Date.valueOf(date), startTime, endTime, note, selectedSchedule.getId(), baseUser.getUsername());
                        loadTrainings();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }

    // Carica le schedule disponibili per il customer corrente
    private void loadSchedulesForCustomer() throws SQLException {
        schedules = engine.getSchedulesByUsername(baseUser);  // Questo metodo recupera le schedule per il customer
    }

    private void showUpdateTrainingPanel(Training training) {
        JPanel updateTrainingPanel = new JPanel();
        JTextField dateField = new JTextField(training.getDate().toString(), 10);
        JTextField startTimeField = new JTextField(training.getStartTime().toString(), 10);
        JTextField endTimeField = new JTextField(training.getEndTime().toString(), 10);
        JTextField noteField = new JTextField(training.getNote(), 20);
        JButton submitButton = new JButton("Submit");

        updateTrainingPanel.add(new JLabel("Date (yyyy-MM-dd):"));
        updateTrainingPanel.add(dateField);
        updateTrainingPanel.add(new JLabel("Start Time (HH:mm:ss):"));
        updateTrainingPanel.add(startTimeField);
        updateTrainingPanel.add(new JLabel("End Time (HH:mm:ss):"));
        updateTrainingPanel.add(endTimeField);
        updateTrainingPanel.add(new JLabel("Note:"));
        updateTrainingPanel.add(noteField);
        updateTrainingPanel.add(submitButton);

        JDialog dialog = new JDialog(this, "Update Training", true);
        dialog.setContentPane(updateTrainingPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                training.setDate(java.sql.Date.valueOf(dateField.getText()));
                training.setStartTime(Time.valueOf(startTimeField.getText()));
                training.setEndTime(Time.valueOf(endTimeField.getText()));
                training.setNote(noteField.getText());
                engine.updateTraining(baseUser, training);
                try {
                    loadTrainings();
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
