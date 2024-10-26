package View.Customer;

import Controller.Engine;
import Controller.NavigationManager;
import DomainModel.Exercise;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class ExercisesCustomer extends JFrame {
    private Engine engine;
    private JList<String> exerciseList;
    private DefaultListModel<String> listModel;
    private List<Exercise> exercises;
    private NavigationManager navigationManager = NavigationManager.getIstance(this);

    public ExercisesCustomer(Engine engine) throws SQLException {
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
                try {
                    updateExerciseList();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // Aggiungi il pulsante per tornare indietro
        mainPanel.add(createBackButton(), BorderLayout.SOUTH);

        return mainPanel;
    }

    private void loadExercises() throws SQLException {
        exercises = engine.getAllExercises();
        if (exercises != null) {
            updateExerciseList();
        } else {
            listModel.clear();
            listModel.addElement("There are no exercises available.");
        }
    }

    private void updateExerciseList() throws SQLException {
        listModel.clear();
        for (Exercise exercise : exercises) {
            // Ottieni l'ID della macchina
            int machineId = exercise.getMachine();
            // Ottieni il nome della macchina tramite l'engine
            String machineName = (machineId == 0) ? "None" : engine.getMachineNameById(machineId);

            // Aggiorna i dettagli dell'esercizio
            String exerciseDetail = String.format("Name: %s, Category: %s, Description: %s, Machine: %s",
                    exercise.getName(), exercise.getCategory(), exercise.getDescription(), machineName);
            listModel.addElement(exerciseDetail);
        }
    }

    private JButton createBackButton() {
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> navigationManager.navigateToHomeCustomer());
        return backButton;
    }
}
