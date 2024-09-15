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

    public Machine getMachine(int id) throws SQLException {
        String query = "SELECT * FROM Machine WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Machine(rs.getInt("id"), rs.getString("name"), rs.getString("description"));
                } else {
                    return null;
                }
            }
        }
    }

    public boolean addMachine(int id, String name, String description) throws SQLException {
        String query = "INSERT INTO Machine (id, name, description) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, description);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean removeMachine(int id) throws SQLException {
        String query = "DELETE FROM Machine WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}
