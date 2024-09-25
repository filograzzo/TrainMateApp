// File: com/trainmate/Main.java
package trainmate;

import BusinessLogic.Service.ServiceManager;
import BusinessLogic.Service.BaseUserService;

class main{
    public static void main(String[] args) {

        ServiceManager sm = ServiceManager.getInstance();
        BaseUserService us = sm.getUserService();
        us.loginUser("Nayla","21Luglio");//prova per vedere s efunziona il login


    }
}
