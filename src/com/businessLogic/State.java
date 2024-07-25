package com.businessLogic;

import com.domainModel.BaseUser;
import com.domainModel.Customer;

import java.sql.Connection;


public class State {

    private BaseUser loggedUser;
    private TypeOfSession sessionType;

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }



    public BaseUser getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(BaseUser loggedUser) {
        this.loggedUser = loggedUser;
    }

    public TypeOfSession getSessionType() {
        return sessionType;
    }

    public void setSessionType(TypeOfSession sessionType) {
        this.sessionType = sessionType;
    }


}
