package DAO;

import DomainModel.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryDAO {
    private final Connection connection;

    public CategoryDAO(Connection connection) {
        this.connection = connection;
    }

    public Category getCategory(String categoryName) throws SQLException {
        String query = "SELECT name FROM Category WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, categoryName);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Category(rs.getString("name"));
                } else {
                    return null;
                }
            }
        }
    }

    public boolean addCategory(Category category) throws SQLException {
        String query = "INSERT INTO Category (name) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, category.getName());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean removeCategory(Category category) throws SQLException {
        String query = "DELETE FROM Category WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, category.getName());
            return stmt.executeUpdate() > 0;
        }
    }
}
