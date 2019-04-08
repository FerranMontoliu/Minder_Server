package view;
import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;

public class JRegister extends JPanel {
    private JTextField jtfName;
    private JPasswordField jpfPassword;
    private JTextField jtfAge;
    private JTextField jtfMail;
    private JPasswordField jpfPassword2;
    private JRadioButton jrbC;
    private JRadioButton jrbJava;
    private JRadioButton jrbPremium;
    private JButton jbRegister;



    public JRegister(){
        setLayout(new GridLayout(16,1));

        //Inicialitzar els text fields
        jtfAge = new JTextField();
        jtfName = new JTextField();
        jpfPassword = new JPasswordField();
        jtfMail = new JTextField();
        jpfPassword2 = new JPasswordField();
        jrbC = new JRadioButton("C");
        jrbJava = new JRadioButton("Java");
        jrbPremium = new JRadioButton("Yes");
        jbRegister = new JButton("Register");


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
        add(jrbC);
        add(jrbJava);
        add (new JLabel("Premium"));
        add (jrbPremium);
        add(jbRegister);



    }
}
