package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class LoginPage extends JFrame {
    private JTextField emailField;
    private JTextField passwordField;
    private JTextField codeField;
    public LoginPage() {
        setupWindow();
        JPanel mainPanel = createMainPanel();
        add(mainPanel);
        setVisible(true);

    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        /*mainPanel.add(createTitlePanel());
        mainPanel.add(createEmailPanel());
        mainPanel.add(createPasswordPanel());
        mainPanel.add(createCodePanel());//appare sempre ma se clicchi su login as user può essere vuoto
        mainPanel.add(createLoginButton());*/
        return mainPanel;

    }

    private void setupWindow() {
        setSize(1000, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
