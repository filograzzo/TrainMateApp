package DAO;

import DomainModel.Exercise;
import DomainModel.Machine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MachineDAO {
    private final Connection connection;

    public MachineDAO(Connection connection) {
        this.connection = connection;
    }

    public Machine getMachineById(int machineId) throws SQLException {
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

    public Machine getMachineByName(String name) throws SQLException {
        String query = "SELECT * FROM Machine WHERE name = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);

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


    public boolean updateMachine(Machine machine) throws SQLException {
        String query = "UPDATE Machine SET name = ?, description = ?, state = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, machine.getName());
            stmt.setString(2, machine.getDescription());
            stmt.setBoolean(3, machine.getState());
            stmt.setInt(4, machine.getId());

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

    public boolean removeMachineBy(Machine machine) throws SQLException {
        String query = "DELETE FROM Machine WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, machine.getId());
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

    public List<Machine> getAllMachines() throws SQLException {
        String query = "SELECT * FROM Machine";
        List<Machine> machines = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Machine machine = new Machine(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getBoolean("state")
                );
                machines.add(machine);
            }
        }
        return machines;
    }

}