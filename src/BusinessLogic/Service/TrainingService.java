package BusinessLogic.Service;

import DAO.TrainingDAO;
import DomainModel.BaseUser;
import DomainModel.Training;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public class TrainingService {
    private BaseUser currentUser;
    private TrainingDAO trainingDAO;

    public TrainingService(TrainingDAO trainingDAO){ this.trainingDAO = trainingDAO;}

    public void setCurrentUser(BaseUser currentUser) {
        this.currentUser = currentUser;
    }

    public boolean createTraining(Date date, Time startTime, Time endTime, String note, int scheduleId, String username) throws SQLException {
        boolean done = trainingDAO.addTraining(date, startTime, endTime, note, scheduleId, username);
        return done;
    }

    public boolean deleteTraining(Training training) throws SQLException {
        boolean done = trainingDAO.removeTraining(training.getId());
        return done;
    }

    public boolean updateTraining(Training training) throws SQLException{
        return trainingDAO.updateTraining(training);
    }

    public List<Training> getAllTrainingsByUsername(BaseUser baseUser) throws SQLException {
        List<Training> trainings = trainingDAO.getAllTrainingsByUsername(baseUser.getUsername());
        return trainings;
    }

    public List<Training> getAllTraiingsBySchedule(int scheduleID) throws SQLException {
        List<Training> trainings = trainingDAO.getAllTrainingsBySchedule(scheduleID);
        return trainings;
    }

}
