package DAO_tst;

import DAO.MachineDAO;
import DomainModel.Machine;
import trainmate.DatabaseUtil;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MachineDAO_tst {
    private static Connection connection;
    private static MachineDAO machineDAO;

    @BeforeAll
    public static void setUp() throws SQLException {
        connection = DatabaseUtil.getConnection();
        machineDAO = new MachineDAO(connection);
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    public void testAddAndRemoveMachine() throws SQLException {
        String name = "Leg Bress";
        String description = "Machine for leg exercises";
        boolean state = true;

        // Aggiunge la macchina e verifica se è stata inserita con successo
        boolean added = machineDAO.addMachine(name, description, state);
        assertTrue(added, "Machine should be added successfully.");

        // Recupera la macchina per nome
        Machine machine = machineDAO.getMachineByName(name);
        assertNotNull(machine, "Machine should exist after being added.");
        assertEquals(name, machine.getName());
        assertEquals(description, machine.getDescription());

        // Rimuove la macchina e verifica se è stata rimossa con successo
        boolean removed = machineDAO.removeMachineBy(machine);
        assertTrue(removed, "Machine should be removed successfully.");
        assertNull(machineDAO.getMachineByName(name), "Machine should not exist after removal.");
}

    @Test
    public void testGetMachineById() throws SQLException {
        String name = "Bench Press";
        String description = "Machine for chest exercises";
        boolean state = true;

        // Aggiunge la macchina
        machineDAO.addMachine(name, description, state);
        Machine machine = machineDAO.getMachineByName(name);

        // Recupera la macchina per ID
        Machine retrievedMachine = machineDAO.getMachineById(machine.getId());
        assertNotNull(retrievedMachine, "Machine should be retrieved by ID.");
        assertEquals(name, retrievedMachine.getName());
        assertEquals(description, retrievedMachine.getDescription());

        // Pulisce i dati
        machineDAO.removeMachineBy(retrievedMachine);
    }

    @Test
    public void testUpdateMachine() throws SQLException {
        String name = "Polder Press";
        String description = "Machine for shoulder exercises";
        boolean state = true;

        // Aggiunge una macchina
        machineDAO.addMachine(name, description, state);
        Machine machine = machineDAO.getMachineByName(name);

        // Aggiorna la macchina
        String updatedDescription = "Updated shoulder machine";
        machine.setDescription(updatedDescription);
        machine.setState(false);

        boolean updated = machineDAO.updateMachine(machine);
        assertTrue(updated, "Machine should be updated successfully.");

        // Recupera e verifica i dettagli aggiornati
        Machine updatedMachine = machineDAO.getMachineById(machine.getId());
        assertEquals(updatedDescription, updatedMachine.getDescription(), "Updated description should match.");
        assertFalse(updatedMachine.getState(), "Updated state should be false.");

        // Pulisce i dati
        machineDAO.removeMachineBy(updatedMachine);
    }

    @Test
    public void testGetAllMachines() throws SQLException {
        // Aggiungi le macchine necessarie per il test
        machineDAO.addMachine("Treadmill", "Cardio machine", true);
        machineDAO.addMachine("Rowing Machine", "Full body workout machine", false);

        // Recupera tutte le macchine
        List<Machine> machines = machineDAO.getAllMachines();
        assertTrue(machines.size() >= 2, "There should be at least two machines in the database.");

        // Verifica che siano presenti le macchine aggiunte
        assertTrue(machines.stream().anyMatch(m -> m.getName().equals("Treadmill")), "Treadmill should be in the list.");
        assertTrue(machines.stream().anyMatch(m -> m.getName().equals("Rowing Machine")), "Rowing Machine should be in the list.");

        // Pulisce i dati inseriti nel test usando `removeMachineByName`
        machineDAO.removeMachineByName("Treadmill");
        machineDAO.removeMachineByName("Rowing Machine");
    }


    @Test
    public void testGetMachineNameById() throws SQLException {
        String name = "Lat Pulldown";
        machineDAO.addMachine(name, "Back exercise machine", true);

        Machine machine = machineDAO.getMachineByName(name);
        String retrievedName = machineDAO.getMachineNameById(machine.getId());
        assertEquals(name, retrievedName, "Retrieved machine name should match the expected name.");

        // Pulisce i dati
        machineDAO.removeMachineBy(machine);
    }

    @Test
    public void testRemoveMachineByName() throws SQLException {
        String name = "Stepper";
        machineDAO.addMachine(name, "Lower body workout machine", true);

        // Verifica che la macchina esista
        Machine machine = machineDAO.getMachineByName(name);
        assertNotNull(machine, "Machine should exist before removal.");

        // Rimuove la macchina per nome e verifica che sia stata rimossa
        boolean removed = machineDAO.removeMachineByName(name);
        assertTrue(removed, "Machine should be removed successfully by name.");
        assertNull(machineDAO.getMachineByName(name), "Machine should not exist after removal by name.");
    }
}
