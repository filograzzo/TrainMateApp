package View;

import Controller.Engine;
import Controller.NavigationManager;
import DomainModel.Exercise;
import DomainModel.Machine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ExercisesPT extends JFrame {
    private Engine engine;
    private JList<String> exerciseList;
    private DefaultListModel<String> listModel;
    private List<Exercise> exercises;
    private NavigationManager navigationManager = NavigationManager.getIstance(this);

    public ExercisesPT(Engine engine) {
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

    private JPanel createMainPanel() {
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
                    loadExercises();
                } else {
                    JOptionPane.showMessageDialog(ExercisesPT.this, "Please select an exercise to delete.");
                }
            }
        });

        return mainPanel;
    }

    private void loadExercises() {
        exercises = engine.getAllExercises();
        if (exercises != null) {
            listModel.clear();
            for (Exercise exercise : exercises) {
                String exerciseDetail = String.format("Name: %s, Category: %s, Machine: %s",
                        exercise.getName(), exercise.getCategory(),
                        exercise.getMachine().isEmpty() ? "None" : exercise.getMachine());
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
        JComboBox<String> machineComboBox = new JComboBox<>();

        machineComboBox.addItem("None");
        List<Machine> machines = engine.getAllMachines();
        for (Machine machine : machines) {
            machineComboBox.addItem(machine.getName());
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
                String machine = machineComboBox.getSelectedItem().toString();
                if (machine.equals("None")) machine = "";

                engine.createExercise(name, category, machine, description);
                loadExercises();
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
