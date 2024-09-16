package BusinessLogic.Service.PersonalTrainer;
import DAO.PersonalTrainerDAO;
import DomainModel.BaseUser;
import DomainModel.PersonalTrainer;
import java.sql.SQLException;
public class ProfilePTService {
    private PersonalTrainer personalTrainer;
    private PersonalTrainerDAO personalTrainerDAO;
    BaseUser currentUser;
    public ProfilePTService(PersonalTrainerDAO personalTrainerDAO) {
        this.personalTrainerDAO = personalTrainerDAO;
    }
    public void setPersonalTrainer(BaseUser currentUser) {
        this.currentUser = currentUser;
    }

    public void modifyUsername(int id, String username) throws SQLException {
        personalTrainerDAO.updateUsername(id, username);
        currentUser.setUsername(username);
    }
    public void modifyPassword(int id, String password) throws SQLException {
        personalTrainerDAO.updatePassword(id, password);
        currentUser.setPassword(password);
    }
    public void modifyEmail(int id, String email) throws SQLException {
        personalTrainerDAO.updateEmail(id, email);
        currentUser.setEmail(email);
    }


}
