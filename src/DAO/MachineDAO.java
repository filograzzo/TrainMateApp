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
                    );
                } else {
                    return null;
                }
            }
        }
    }


    public boolean addMachine(Machine machine) throws SQLException {
        String query = "INSERT INTO Machine (id, name, description, state) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, machine.getId());
            stmt.setString(2, machine.getName());
            stmt.setString(3, machine.getDescription());
            stmt.setBoolean(4, machine.getState());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean removeMachine(Machine machine) throws SQLException {
        String query = "DELETE FROM Machine WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, machine.getId());
            return stmt.executeUpdate() > 0;
        }
    }
}
