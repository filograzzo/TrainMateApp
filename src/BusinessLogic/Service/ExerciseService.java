package BusinessLogic.Service;

import DAO.ExerciseDAO;
import DAO.MachineDAO;
import DomainModel.BaseUser;
import DomainModel.Exercise;

import java.sql.SQLException;
import java.util.List;

public class ExerciseService {

    private ExerciseDAO exerciseDAO;
    private BaseUser baseUser;

    public ExerciseService(ExerciseDAO exerciseDAO) {
        this.exerciseDAO = exerciseDAO;
    }

    public void setCurrentUser(BaseUser baseUser) {
        this.baseUser = baseUser;
    }

    public boolean createExercise(String name, String category, String machine, String description) throws SQLException {
        return exerciseDAO.addExercise(name, category, machine, description);
    }

    public boolean deleteExercise(Exercise exercise) throws SQLException {
        return exerciseDAO.removeExerciseById(exercise.getId());
    }

    public Exercise getExerciseByName(String name) throws SQLException {
        return exerciseDAO.getExerciseByName(name);
    }

    public List<Exercise> getAllExercises() throws SQLException {
        return exerciseDAO.getAllExercises();
    }

    public List<Exercise> getExercisesByCategory(String category) throws SQLException {
        return exerciseDAO.getExercisesByCategory(category);
    }

    public String getExerciseNameById(int id) throws SQLException {
        return exerciseDAO.getExerciseNameById(id);
    }


}
