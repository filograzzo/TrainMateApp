package BusinessLogic.Service.Customer;

import DAO.CustomerDAO;
import DAO.PersonalDataClientDAO;
import DomainModel.BaseUser;
import DomainModel.Customer;
import DomainModel.PersonalDataClient;

import java.sql.SQLException;

public class ProfileService {
    private PersonalDataClientDAO personalDataClientDAO;
    private CustomerDAO customerDAO;
    BaseUser currentUser;
    public ProfileService(CustomerDAO customerDAO, PersonalDataClientDAO personalDataClientDAO) {
        this.personalDataClientDAO = personalDataClientDAO;
        this.customerDAO = customerDAO;
    }
    public void setCustomer(BaseUser currentUser) {
        this.currentUser = currentUser;
    }

    public void modifyUsername(int id, String username) throws SQLException {
        customerDAO.updateUsername(id, username);
        currentUser.setUsername(username);
    }
    public void modifyPassword(int id, String password) throws SQLException {
        customerDAO.updatePassword(id, password);
        currentUser.setPassword(password);
    }
    public void modifyEmail(int id, String email) throws SQLException {
        customerDAO.updateEmail(id, email);
        currentUser.setEmail(email);
    }


    public boolean addOrUpdatePersonalData(PersonalDataClient personalData) throws SQLException {
        if (personalDataClientDAO.getPersonalData(personalData.getId()) != null) {
            return personalDataClientDAO.updatePersonalData(personalData);
        } else {
            return personalDataClientDAO.addPersonalData(personalData);
        }
        //TODO modificare anche localmente((Customer)currentUser).setPersonalDataClient(personalData);
    }

    public boolean deletePersonalData() throws SQLException {
        return personalDataClientDAO.deletePersonalData(currentUser.getId());
        //TODO modificare anche localmente
    }

}
