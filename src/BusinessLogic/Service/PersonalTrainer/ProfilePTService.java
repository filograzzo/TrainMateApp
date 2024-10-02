package BusinessLogic.Service.PersonalTrainer;
import DAO.PersonalTrainerDAO;
import DomainModel.BaseUser;
import DomainModel.PersonalTrainer;
import java.sql.SQLException;
public class ProfilePTService {
    private PersonalTrainer personalTrainer;
    private PersonalTrainerDAO personalTrainerDAO;
    PersonalTrainer currentUser;
    public ProfilePTService(PersonalTrainerDAO personalTrainerDAO) {
        this.personalTrainerDAO = personalTrainerDAO;
    }
    public void setPersonalTrainer(PersonalTrainer currentUser) {
        this.currentUser = currentUser;
    }

    public boolean modifyUsername(String oldUsername, String username) throws SQLException {
        if(!personalTrainerDAO.usernameExists(username)){
            if(personalTrainerDAO.updateUsername(oldUsername, username)) {
                currentUser.setUsername(username);
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }

    }
    public boolean modifyPassword(int id, String password,String oldPassword) throws SQLException {
        if(personalTrainerDAO.updatePassword(id, password,oldPassword)){
            currentUser.setPassword(password);
            return true;
        } else{
            return false;

        }
    }
    public boolean modifyEmail(int id, String email) throws SQLException {
        if(personalTrainerDAO.updateEmail(id, email)){
            currentUser.setEmail(email);
            return true;
        } else{
            return false;
        }
    }
    public String getUsername(int id) throws SQLException {
        return personalTrainerDAO.getNamePersonalTrainerbyId(id);
    }


}
