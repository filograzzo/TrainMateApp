package View;

import Controller.Engine;
import Controller.NavigationManager;
import DomainModel.Exercise;
import DomainModel.ExerciseDetail;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ExerciseDetailCustomerView extends JFrame {
    private Engine engine;
    private int scheduleID; // Viene passato dal contesto della schedule
    private JList<String> exerciseDetailList;
    private DefaultListModel<String> listModel;
    private List<ExerciseDetail> exerciseDetails;
    private NavigationManager navigationManager = NavigationManager.getIstance(this);

    public ExerciseDetailCustomerView(Engine engine, int scheduleID) throws SQLException {
        this.engine = engine;
        this.scheduleID = scheduleID;
        setupWindow();
        JPanel mainPanel = createMainPanel();
        add(mainPanel);
        setVisible(true);
    }

    private void setupWindow() {
        setTitle("Exercise Details");
        setSize(1000, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JPanel createMainPanel() throws SQLException {
        JPanel mainPanel = new JPanel(new BorderLayout());
        listModel = new DefaultListModel<>();
        exerciseDetailList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(exerciseDetailList);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Exercise Detail");
        JButton deleteButton = new JButton("Delete Exercise Detail");
        JButton updateButton = new JButton("Update Exercise Detail");
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        loadExerciseDetails();
        buttonPanel.add(createBackButton());

        // Azione per aggiungere un dettaglio esercizio
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddExerciseDetailPanel();
            }
        });

        // Azione per eliminare un dettaglio esercizio
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = exerciseDetailList.getSelectedIndex();
                if (selectedIndex != -1) {
                    ExerciseDetail selectedExerciseDetail = exerciseDetails.get(selectedIndex);
                    engine.deleteExerciseDetail(selectedExerciseDetail);
                    try {
                        loadExerciseDetails();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(ExerciseDetailCustomerView.this, "Please select an exercise detail to delete.");
                }
            }
        });

        // Azione per aggiornare un dettaglio esercizio
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = exerciseDetailList.getSelectedIndex();
                if (selectedIndex != -1) {
                    ExerciseDetail selectedExerciseDetail = exerciseDetails.get(selectedIndex);
                    showUpdateExerciseDetailPanel(selectedExerciseDetail);
                } else {
                    JOptionPane.showMessageDialog(ExerciseDetailCustomerView.this, "Please select an exercise detail to update.");
                }
            }
        });

        return mainPanel;
    }

    private void loadExerciseDetails() throws SQLException {
        exerciseDetails = engine.getExerciseDetailsBySchedule(scheduleID);  // Recupera i dettagli degli esercizi per la schedule corrente
        if (exerciseDetails != null) {
            listModel.clear();
            for (ExerciseDetail exerciseDetail : exerciseDetails) {
                String exerciseDetailInfo = String.format("Series: %d, Reps: %d, Weight: %d, Exercise: %s",
                        exerciseDetail.getSerie(), exerciseDetail.getReps(), exerciseDetail.getWeight(), engine.getExerciseNameById(exerciseDetail.getExercise()));
                listModel.addElement(exerciseDetailInfo);
            }
        } else {
            listModel.clear();
            listModel.addElement("No exercise details found for this schedule.");
        }
    }

    private void showAddExerciseDetailPanel() {
        JPanel addExerciseDetailPanel = new JPanel();
        JTextField serieField = new JTextField(5);
        JTextField repsField = new JTextField(5);
        JTextField weightField = new JTextField(5);
        JComboBox<String> exerciseComboBox = new JComboBox<>();

        List<Exercise> exercises = engine.getAllExercises();  // Recupera tutti gli esercizi esistenti
        for (Exercise exercise : exercises) {
            exerciseComboBox.addItem(exercise.getName());
        }

        JButton submitButton = new JButton("Submit");

        addExerciseDetailPanel.add(new JLabel("Series:"));
        addExerciseDetailPanel.add(serieField);
        addExerciseDetailPanel.add(new JLabel("Reps:"));
        addExerciseDetailPanel.add(repsField);
        addExerciseDetailPanel.add(new JLabel("Weight:"));
        addExerciseDetailPanel.add(weightField);
        addExerciseDetailPanel.add(new JLabel("Exercise:"));
        addExerciseDetailPanel.add(exerciseComboBox);
        addExerciseDetailPanel.add(submitButton);

        JDialog dialog = new JDialog(this, "Add Exercise Detail", true);
        dialog.setContentPane(addExerciseDetailPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int serie = Integer.parseInt(serieField.getText());
                int reps = Integer.parseInt(repsField.getText());
                int weight = Integer.parseInt(weightField.getText());
                String exerciseName = (String) exerciseComboBox.getSelectedItem();
                int exerciseID = 0;
                try {
                    exerciseID = engine.getExerciseIdByName(exerciseName);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                engine.createExerciseDetail(serie, reps, weight, scheduleID, exerciseID);
                try {
                    loadExerciseDetails();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }

    private void showUpdateExerciseDetailPanel(ExerciseDetail exerciseDetail) {
        JPanel updateExerciseDetailPanel = new JPanel();
        JTextField serieField = new JTextField(String.valueOf(exerciseDetail.getSerie()), 5);
        JTextField repsField = new JTextField(String.valueOf(exerciseDetail.getReps()), 5);
        JTextField weightField = new JTextField(String.valueOf(exerciseDetail.getWeight()), 5);
        JComboBox<String> exerciseComboBox = new JComboBox<>();

        List<Exercise> exercises = engine.getAllExercises();  // Recupera tutti gli esercizi esistenti
        for (Exercise exercise : exercises) {
            exerciseComboBox.addItem(exercise.getName());
        }

        JButton submitButton = new JButton("Submit");

        updateExerciseDetailPanel.add(new JLabel("Series:"));
        updateExerciseDetailPanel.add(serieField);
        updateExerciseDetailPanel.add(new JLabel("Reps:"));
        updateExerciseDetailPanel.add(repsField);
        updateExerciseDetailPanel.add(new JLabel("Weight:"));
        updateExerciseDetailPanel.add(weightField);
        updateExerciseDetailPanel.add(new JLabel("Exercise:"));
        updateExerciseDetailPanel.add(exerciseComboBox);
        updateExerciseDetailPanel.add(submitButton);

        JDialog dialog = new JDialog(this, "Update Exercise Detail", true);
        dialog.setContentPane(updateExerciseDetailPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int serie = Integer.parseInt(serieField.getText());
                int reps = Integer.parseInt(repsField.getText());
                int weight = Integer.parseInt(weightField.getText());
                String exerciseName = (String) exerciseComboBox.getSelectedItem();
                int exerciseID = 0;
                try {
                    exerciseID = engine.getExerciseIdByName(exerciseName);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                engine.updateExerciseDetail(exerciseDetail.getId(), serie, reps, weight, scheduleID, exerciseID);
                try {
                    loadExerciseDetails();
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
        backButton.addActionListener(e -> {
            try {
                navigationManager.navigateToScheduleCustomer();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        return backButton;
    }
}
