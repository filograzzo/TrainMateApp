package BusinessLogic.Service;

import DAO.ExerciseDAO;
import DAO.ExerciseDetailDAO;
import DomainModel.BaseUser;
import DomainModel.ExerciseDetail;

import java.sql.SQLException;
import java.util.List;

public class ExerciseDetailService {
    private ExerciseDetailDAO exerciseDetailDAO;
    private ExerciseDAO exerciseDAO;
    private BaseUser baseUser;

    public ExerciseDetailService(ExerciseDetailDAO exerciseDetailDAO, ExerciseDAO exerciseDAO) {
        this.exerciseDetailDAO = exerciseDetailDAO;
        this.exerciseDAO = exerciseDAO;
    }
    public void setCurrentUser(BaseUser baseUser) {
        this.baseUser = baseUser;
    }

    public boolean createExerciseDetail(int serie, int reps, int weight, int schedule_id, int exercise_id) throws SQLException {
        boolean done = exerciseDetailDAO.addExerciseDetail(serie, reps, weight, schedule_id, exercise_id);
        return done;
    }

    public boolean removeExerciseDetail(ExerciseDetail exerciseDetail) throws SQLException {
        boolean done = exerciseDetailDAO.deleteExerciseDetailById(exerciseDetail.getId());
        return done;
    }

    public boolean updateExerciseDetail(int id, int serie, int reps, int weight, int exerciseID) throws SQLException {
        boolean done = exerciseDetailDAO.updateExerciseDetail(id, serie, reps, weight, exerciseID);
        return done;
    }

    public ExerciseDetail getExerciseDetailById(int id) throws SQLException {
        return exerciseDetailDAO.getExerciseDetailById(id);
    }

    public int getExerciseIdByName(String exerciseName) throws SQLException {
        return exerciseDAO.getExerciseIdByName(exerciseName);
    }

    public List<ExerciseDetail> getExerciseDetailsBySchedule(int schedule_id) throws SQLException {
        return exerciseDetailDAO.getExerciseDetailsByScheduleId(schedule_id);
    }
}