package View;

import Controller.Engine;
import Controller.NavigationManager;
import DomainModel.Exercise;
import DomainModel.Machine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ExercisesPT extends JFrame {
    private Engine engine;
    private JList<String> exerciseList;
    private DefaultListModel<String> listModel;
    private List<Exercise> exercises;
    private NavigationManager navigationManager = NavigationManager.getIstance(this);

    public ExercisesPT(Engine engine) throws SQLException {
        this.engine = engine;
        setupWindow();
        JPanel mainPanel = createMainPanel();
        add(mainPanel);
        setVisible(true);
    }

    private void setupWindow() {
        setTitle("Personal Trainer Exercises");
        setSize(1000, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JPanel createMainPanel() throws SQLException {
        JPanel mainPanel = new JPanel(new BorderLayout());
        listModel = new DefaultListModel<>();
        exerciseList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(exerciseList);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Exercise");
        JButton deleteButton = new JButton("Delete Exercise");
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        loadExercises();
        buttonPanel.add(createBackButton());

        // Azione per aggiungere un esercizio
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddExercisePanel();
            }
        });

        // Azione per eliminare un esercizio
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = exerciseList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Exercise selectedExercise = exercises.get(selectedIndex);
                    engine.deleteExercise(selectedExercise);
                    try {
                        loadExercises();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(ExercisesPT.this, "Please select an exercise to delete.");
                }
            }
        });

        return mainPanel;
    }

    private void loadExercises() throws SQLException {
        exercises = engine.getAllExercises();
        if (exercises != null) {
            listModel.clear();
            for (Exercise exercise : exercises) {
                // Ottieni l'ID della macchina dall'esercizio
                int machineId = exercise.getMachine();
                // Ottieni il nome della macchina utilizzando l'ID
                String machineName = (machineId == 0) ? "None" : engine.getMachineNameById(machineId);

                // Crea la stringa dettagli dell'esercizio
                String exerciseDetail = String.format("Name: %s, Category: %s, Description: %s, Machine: %s",
                        exercise.getName(), exercise.getCategory(), exercise.getDescription(), machineName);
                listModel.addElement(exerciseDetail);
            }
        } else {
            listModel.clear();
            listModel.addElement("There are no exercises available.");
        }
    }

    private void showAddExercisePanel() {
        JPanel addExercisePanel = new JPanel();
        JTextField nameField = new JTextField(20);
        JComboBox<String> categoryComboBox = new JComboBox<>(Exercise.getValidCategories().toArray(new String[0]));
        JTextField descriptionField = new JTextField(20);
        JComboBox<String> machineComboBox = new JComboBox<>(); // Ora contiene oggetti Machine

        machineComboBox.addItem("None"); // Aggiungi "None" come opzione
        List<Machine> machines = engine.getAllMachines();
        for (Machine machine : machines) {
            machineComboBox.addItem(machine.getName()); // Aggiungi l'intero oggetto Machine
        }

        JButton submitButton = new JButton("Submit");

        addExercisePanel.add(new JLabel("Exercise Name:"));
        addExercisePanel.add(nameField);
        addExercisePanel.add(new JLabel("Category:"));
        addExercisePanel.add(categoryComboBox);
        addExercisePanel.add(new JLabel("Description:"));
        addExercisePanel.add(descriptionField);
        addExercisePanel.add(new JLabel("Machine:"));
        addExercisePanel.add(machineComboBox);
        addExercisePanel.add(submitButton);

        JDialog dialog = new JDialog(this, "Add Exercise", true);
        dialog.setContentPane(addExercisePanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String category = (String) categoryComboBox.getSelectedItem();
                String description = descriptionField.getText();
                Machine selectedMachine = (Machine) machineComboBox.getSelectedItem(); // Ottieni l'oggetto Machine selezionato
                int machineId = selectedMachine != null ? selectedMachine.getId() : 0; // Ottieni l'ID della macchina o 0 se è "None"

                // Crea l'esercizio con il machineId
                engine.createExercise(name, category, machineId, description);
                try {
                    loadExercises();
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
        backButton.addActionListener(e -> navigationManager.navigateToHomePT());
        return backButton;
    }
}
