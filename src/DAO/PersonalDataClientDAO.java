package DAO;

import DomainModel.PersonalDataClient;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonalDataClientDAO {
    private final Connection connection;

    public PersonalDataClientDAO(Connection connection) {
        this.connection = connection;
    }

    public PersonalDataClient getPersonalData(int clientId) throws SQLException {
        String query = "SELECT * FROM PersonalData WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, clientId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new PersonalDataClient(
                            rs.getInt("id"),
                            rs.getFloat("height"),
                            rs.getFloat("weight"),
                            rs.getInt("age"),
                            rs.getString("gender"),
                            rs.getString("activity"),
                            rs.getString("goal")
                    );
                } else {
                    return null;
                }
            }
        }
    }

    public boolean addPersonalData(PersonalDataClient personalData) throws SQLException {
        String query = "INSERT INTO PersonalData (id, height, weight, age, gender, activity, goal) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, personalData.getId());
            stmt.setFloat(2, personalData.getHeight());
            stmt.setFloat(3, personalData.getWeight());
            stmt.setInt(4, personalData.getAge());
            stmt.setString(5, personalData.getGender());
            stmt.setString(6, personalData.getActivity());
            stmt.setString(7, personalData.getGoal());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updatePersonalData(PersonalDataClient personalData) throws SQLException {
        String query = "UPDATE PersonalData SET height = ?, weight = ?, age = ?, gender = ?, activity = ?, goal = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setFloat(1, personalData.getHeight());
            stmt.setFloat(2, personalData.getWeight());
            stmt.setInt(3, personalData.getAge());
            stmt.setString(4, personalData.getGender());
            stmt.setString(5, personalData.getActivity());
            stmt.setString(6, personalData.getGoal());
            stmt.setInt(7, personalData.getId());
            return stmt.executeUpdate() > 0;
        }
    }
    public boolean deletePersonalData(int clientId) throws SQLException {
        String query = "DELETE FROM PersonalData WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, clientId);
            return stmt.executeUpdate() > 0;
        }
    }

}
