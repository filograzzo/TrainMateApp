package DAO;
/**
 * DAO class for Schedule:Il cliente deve poter visualizzare la lista di training che deve fare,in che giorni
 è un insieme di training
 */
import DomainModel.Schedule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
public class ScheduleDAO {
    private final Connection connection;

    public ScheduleDAO(Connection connection) {
        this.connection = connection;
    }

}
