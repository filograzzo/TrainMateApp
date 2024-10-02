// File: com/trainmate/Main.java
package trainmate;

import Controller.Engine;
import View.LoginPage;

class main{
    public static void main(String[] args) {
        Engine engine = Engine.getInstance();

        new LoginPage(engine);


    }
}
