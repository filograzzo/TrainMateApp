package BusinessLogic.Service.Customer;

import DAO.CustomerDAO;
import DomainModel.Customer;

import java.sql.SQLException;

public class ProfileService {
    private CustomerDAO customerDAO;

    public Customer getCurrentUser() {
        return currentUser;
    }

    Customer currentUser;
    public ProfileService(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }
    public void setCustomer(Customer currentUser) {
        this.currentUser = currentUser;
    }

    public boolean modifyUsername( String username,String newUsername) throws SQLException {
        if(!customerDAO.usernameExists(newUsername)){
            if(customerDAO.updateUsername(username,newUsername)){
                currentUser.setUsername(newUsername);
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }

    }
    public boolean modifyPassword(String username, String password,String oldPassword) throws SQLException {
        if(customerDAO.updatePassword(username, password,oldPassword)){
            currentUser.setPassword(password);
            return true;
        }else{
            return false;
        }

    }
    public boolean modifyEmail(String username, String email) throws SQLException {
        if(!customerDAO.emailExists(email)){
            if(customerDAO.updateEmail(username, email)){
                currentUser.setEmail(email);
                return true;
            }
            else{
                return false;
            }
        }else{
            return false;
        }

    }

    public boolean updatePersonalData(int id, float height, float weight, int age, String gender, String goal) throws SQLException {
        if(customerDAO.updatePersonalData(id, height, weight,age,gender,goal)){
            ((Customer)currentUser).setHeight(height);
            ((Customer)currentUser).setWeight(weight);
            ((Customer)currentUser).setAge(age);
            ((Customer)currentUser).setGender(gender);
            ((Customer)currentUser).setGoal(goal);
            return true;
        }
        else{
            return false;
        }
    }
    public boolean deletePersonalData() throws SQLException {
        if(customerDAO.deletePersonalData(currentUser.getId())){
            ((Customer)currentUser).setHeight(0);
            ((Customer)currentUser).setWeight(0);
            ((Customer)currentUser).setAge(0);
            ((Customer)currentUser).setGender(null);
            ((Customer)currentUser).setGoal(null);
            return true;
        }
        else{
            return false;
        }

    }
    public boolean getPersonalData(int clientId) throws SQLException {
        return customerDAO.getPersonalData(clientId);
    }

}
