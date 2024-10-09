package View;

import Controller.Engine;
import Controller.NavigationManager;
import DomainModel.Exercise;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.List;

public class ExercisesCustomer extends JFrame {
    private Engine engine;
    private JList<String> exerciseList;
    private DefaultListModel<String> listModel;
    private List<Exercise> exercises;
    private NavigationManager navigationManager = NavigationManager.getIstance(this);

    public ExercisesCustomer(Engine engine) {
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

        // Pannello per i filtri
        JPanel filterPanel = new JPanel();
        JComboBox<String> sortComboBox = new JComboBox<>(new String[]{"Ordina per Nome", "Ordina per Categoria"});
        filterPanel.add(new JLabel("Ordinamento:"));
        filterPanel.add(sortComboBox);
        mainPanel.add(filterPanel, BorderLayout.NORTH);

        // Carica gli esercizi e ordina in base alla selezione del filtro
        loadExercises();
        sortComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedSort = (String) sortComboBox.getSelectedItem();
                if ("Ordina per Nome".equals(selectedSort)) {
                    exercises.sort(Comparator.comparing(Exercise::getName));
                } else if ("Ordina per Categoria".equals(selectedSort)) {
                    exercises.sort(Comparator.comparing(Exercise::getCategory));
                }
                updateExerciseList();
            }
        });

        // Aggiungi il pulsante per tornare indietro
        mainPanel.add(createBackButton(), BorderLayout.SOUTH);

        return mainPanel;
    }

    private void loadExercises() {
        exercises = engine.getAllExercises();
        if (exercises != null) {
            updateExerciseList();
        } else {
            listModel.clear();
            listModel.addElement("There are no exercises available.");
        }
    }

    private void updateExerciseList() {
        listModel.clear();
        for (Exercise exercise : exercises) {
            String exerciseDetail = String.format("Name: %s, Category: %s, Machine: %s",
                    exercise.getName(), exercise.getCategory(),
                    exercise.getMachine().isEmpty() ? "None" : exercise.getMachine());
            listModel.addElement(exerciseDetail);
        }
    }

    private JButton createBackButton() {
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> navigationManager.navigateToHomeCustomer());
        return backButton;
    }
}
