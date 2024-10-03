package View;

import Controller.Engine;
import Controller.NavigationManager;
import DomainModel.Machine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MachinesPT extends JFrame {
    private Engine engine;
    private JList<String> machineList;
    private NavigationManager navigationManager = NavigationManager.getIstance(this);
    private DefaultListModel<String> listModel;
    private List<Machine> machines;

    public MachinesPT(Engine engine) {
        this.engine = engine;
        setupWindow();
        JPanel mainPanel = createMainPanel();
        add(mainPanel);
        setVisible(true);
    }

    private void setupWindow() {
        setTitle("Personal Trainer Machines");
        setSize(1000, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        listModel = new DefaultListModel<>();
        machineList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(machineList);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Machine");
        JButton deleteButton = new JButton("Delete Machine");
        JButton updateButton = new JButton("Update Machine");
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        loadMachines();
        buttonPanel.add(createBackButton());

        // Azione per aggiungere una macchina
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddMachinePanel();
            }
        });

        // Azione per eliminare una macchina
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = machineList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Machine selectedMachine = machines.get(selectedIndex);
                    engine.deleteMachine(selectedMachine);
                    loadMachines();
                } else {
                    JOptionPane.showMessageDialog(MachinesPT.this, "Please select a machine to delete.");
                }
            }
        });

        // Azione per aggiornare una macchina
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = machineList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Machine selectedMachine = machines.get(selectedIndex);
                    showUpdateMachinePanel(selectedMachine);
                } else {
                    JOptionPane.showMessageDialog(MachinesPT.this, "Please select a machine to update.");
                }
            }
        });

        return mainPanel;
    }

    private void loadMachines() {
        machines = engine.viewMachinesToTake();
        if (machines != null) {
            listModel.clear();
            for (Machine machine : machines) {
                String stateString = machine.getState() ? "Active" : "Inactive";
                String machineDetail = String.format("Name: %s, Description: %s, State: %s",
                        machine.getName(), machine.getDescription(), stateString);
                listModel.addElement(machineDetail);
            }
        } else {
            listModel.clear();
            listModel.addElement("There are no machines in the gym");
        }
    }

    private void showAddMachinePanel() {
        JPanel addMachinePanel = new JPanel();
        JTextField nameField = new JTextField(20);
        JTextField descriptionField = new JTextField(20);
        JCheckBox stateCheckBox = new JCheckBox("Is Active?");
        JButton submitButton = new JButton("Submit");

        addMachinePanel.add(new JLabel("Machine Name:"));
        addMachinePanel.add(nameField);
        addMachinePanel.add(new JLabel("Description:"));
        addMachinePanel.add(descriptionField);
        addMachinePanel.add(stateCheckBox);
        addMachinePanel.add(submitButton);

        JDialog dialog = new JDialog(this, "Add Machine", true);
        dialog.setContentPane(addMachinePanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String description = descriptionField.getText();
                boolean state = stateCheckBox.isSelected();
                engine.createMachine(name, description, state);
                loadMachines();
                dialog.dispose();  // Chiudi la finestra automaticamente
            }
        });

        dialog.setVisible(true);
    }


    private void showUpdateMachinePanel(Machine machine) {
        JPanel updateMachinePanel = new JPanel();
        JTextField nameField = new JTextField(machine.getName(), 20);
        JTextField descriptionField = new JTextField(machine.getDescription(), 20);
        JCheckBox stateCheckBox = new JCheckBox("Is Active?", machine.getState());
        JButton submitButton = new JButton("Submit");

        updateMachinePanel.add(new JLabel("Machine Name:"));
        updateMachinePanel.add(nameField);
        updateMachinePanel.add(new JLabel("Description:"));
        updateMachinePanel.add(descriptionField);
        updateMachinePanel.add(stateCheckBox);
        updateMachinePanel.add(submitButton);

        JDialog dialog = new JDialog(this, "Update Machine", true);
        dialog.setContentPane(updateMachinePanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                machine.setName(nameField.getText());
                machine.setDescription(descriptionField.getText());
                machine.setState(stateCheckBox.isSelected());
                engine.updateMachine(machine);
                loadMachines();
                dialog.dispose();  // Chiudi la finestra automaticamente
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