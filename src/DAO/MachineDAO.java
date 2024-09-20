package DAO;
/**
 * DAO class for Machine:Il personal trainer aggiunge informazioni sulle macchine presenti in palestra:get,add,remove
 */
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

    public Machine getMachine(String name) throws SQLException {
        String query = "SELECT * FROM Machine WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Machine(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getString("state"));
                } else {
                    return null;
                }
            }
        }
    }

    public boolean addMachine(String name, String description, String state) throws SQLException {
        String query = "INSERT INTO Machine (name, description, state) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setString(3, state);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean removeMachine(String name) throws SQLException {
        String query = "DELETE FROM Machine WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            return stmt.executeUpdate() > 0;
        }
    }
}
