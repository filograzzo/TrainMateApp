package DAO;

import DomainModel.Machine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class MachineDAO {
    private final Connection connection;

    public MachineDAO(Connection connection) {
        this.connection = connection;
    }

    public Machine getMachine(int machineId) throws SQLException {
        String query = "SELECT * FROM Machine WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, machineId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Machine(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getBoolean("state")
                            //TODO: nel service dovrà essere aggiunta la lista di esercizi che utilizzano il macchinario andando a cercare nel
                            //TODO: database di exercise e cercando per nome di macchinario usando la foreign key (machine_name).
                    );
                } else {
                    return null;
                }
            }
        }
    }

    public boolean updateMachine(int id, Machine machine) throws SQLException {
        String query = "UPDATE Machine SET name = ?, description = ?, state = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, machine.getName());
            stmt.setString(2, machine.getDescription());
            stmt.setBoolean(3, machine.getState());
            stmt.setInt(4, id);

            return stmt.executeUpdate() > 0;  // Restituisce true se almeno una riga è stata aggiornata
        }
    }

    public boolean addMachine(String name, String description, Boolean state) throws SQLException {
        String query = "INSERT INTO Machine ( name, description, state) VALUES ( ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setBoolean(3, state);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean removeMachineById(int id) throws SQLException {
        String query = "DELETE FROM Machine WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean removeMachineByName(String name) throws SQLException {
        String query = "DELETE FROM Machine WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            return stmt.executeUpdate() > 0;
        }
    }


}
