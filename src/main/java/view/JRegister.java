package view;

import model.entity.User;

import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;

public class JRegister extends JPanel {
    private JTextField jtfName;
    private JPasswordField jpfPassword;
    private JTextField jtfAge;
    private JTextField jtfMail;
    private JPasswordField jpfPassword2;
    private JCheckBox jcbC;
    private JCheckBox jcbJava;
    private JRadioButton jrbPremium;
    private JRadioButton jrbNoPremium;
    private JButton jbRegister;



    public JRegister(){
        setLayout(new GridLayout(16,1));

        //Inicialitzar els elements
        jtfAge = new JTextField();
        jtfName = new JTextField();
        jpfPassword = new JPasswordField();
        jtfMail = new JTextField();
        jpfPassword2 = new JPasswordField();
        jcbC = new JCheckBox("C");
        jcbJava = new JCheckBox("Java");
        jrbPremium = new JRadioButton("Yes");
        jrbNoPremium = new JRadioButton("No");
        jbRegister = new JButton("Register");

        //Grup de radioButtons
        ButtonGroup bg = new ButtonGroup();
        bg.add(jrbNoPremium);
        bg.add(jrbPremium);
        jrbNoPremium.setSelected(true);

        //Afegir components al panell
        add(new JLabel("User name"));
        add(jtfName);
        add(new JLabel("Mail"));
        add(jtfMail);
        add(new JLabel("Password"));
        add(jpfPassword);
        add(new JLabel("Repeat Password"));
        add(jpfPassword2);
        add(new JLabel("Age"));
        add(jtfAge);
        add(new JLabel("Programming languages:"));
        add(jcbC);
        add(jcbJava);
        JPanel jpRadioButton = new JPanel(new GridLayout(1,2));
        jpRadioButton.add(jrbNoPremium);
        jpRadioButton.add(jrbPremium);
        add (new JLabel("Premium"));
        add (jpRadioButton);
        add(jbRegister);


    }

    public User getUser() {

        //User u = new User();

        return null;
    }

    public void removeRegister(){

    }


    /*
    public void registerController(ControllerRegister controller) {
        jbRegister.addActionListener(controller);
        jbRegister.setActionCommand("REGISTER");
    }
    */
}