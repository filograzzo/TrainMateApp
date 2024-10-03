/**
 * DAO class for Signed:Il cliente si iscrive o disiscrive ad un corso,vuole sapere se non sono già pieni:get,add,remove subscribtion,check if full
 */
package DAO;

import DomainModel.Signed;
import DomainModel.Course;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignedDAO {
    private final Connection connection;

    public SignedDAO(Connection connection) {
        this.connection = connection;
    }
    public int SignedToCoursebyId(int courseId) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM Signed WHERE course_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, courseId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    if( rs.getInt("count")==0){
                        return 0;

                    }else{
                        int count = rs.getInt("count");
                        return count;
                    }

                } else {
                    System.out.println("Error in SignedToCoursebyId");
                }
            }
        }
        return 0;
    }
    public Signed getSigned(int courseId, int customerId) throws SQLException {
        String query = "SELECT * FROM Signed WHERE course_id = ? AND customer_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, courseId);
            stmt.setInt(2, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Signed(rs.getInt("course_id"), rs.getInt("customer_id"));
                } else {
                    return null;
                }
            }
        }
    }

    public boolean addSigned(int customerId, int courseId, int max_participants) throws SQLException {
        if (SignedToCoursebyId(courseId) >= max_participants) {
            return false;
        } else {
            // Check if the entry already exists
            String checkQuery = "SELECT COUNT(*) FROM Signed WHERE course_id = ? AND customer_id = ?";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
                checkStmt.setInt(1, courseId);
                checkStmt.setInt(2, customerId);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        // Entry already exists
                        return false;
                    }
                }
            }

            // Insert new entry
            String query = "INSERT INTO Signed (course_id, customer_id) VALUES (?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, courseId);
                stmt.setInt(2, customerId);
                return stmt.executeUpdate() > 0;
            }
        }
    }
    public boolean userAlreadySigned(int id,int courseid){
        String query = "SELECT * FROM Signed WHERE course_id = ? AND customer_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, courseid);
            stmt.setInt(2, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeSigned(int courseId, int customerId) throws SQLException {
        String query = "DELETE FROM Signed WHERE course_id = ? AND customer_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, courseId);
            stmt.setInt(2, customerId);
            return stmt.executeUpdate() > 0;
        }
    }



}