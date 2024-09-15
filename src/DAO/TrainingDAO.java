package DAO;

import DomainModel.Training;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO class for Training:Il personal trainer aggiunge,rimuove,modifica i piani di allenamento dei clienti:add,remove e update training
 * il cliente deve poter accedere al suo piano di allenamento:get training
 */
public class TrainingDAO {
    private final Connection connection;

    public TrainingDAO(Connection connection) {
        this.connection = connection;
    }

    public Training getTraining(int customerId) throws SQLException {
        String query = "SELECT * FROM Training WHERE customer_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Training(rs.getInt("id"), rs.getInt("customer_id"), rs.getString("plan"));
                } else {
                    return null;
                }
            }
        }
    }

    public boolean addTraining(int customerId, String plan) throws SQLException {
        String query = "INSERT INTO Training (customer_id, plan) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            stmt.setString(2, plan);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean removeTraining(int customerId) throws SQLException {
        String query = "DELETE FROM Training WHERE customer_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateTraining(int customerId, String newPlan) throws SQLException {
        String query = "UPDATE Training SET plan = ? WHERE customer_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newPlan);
            stmt.setInt(2, customerId);
            return stmt.executeUpdate() > 0;
        }
    }
}
