package view;
import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;

public class JRegister extends JPanel {
    private JTextField jtfName;
    private JTextField jtfPassword;
    private JTextField jtfAge;
    private JTextField jtfMail;
    private JTextField jtfPassword2;
    private JRadioButton jrbC;
    private JRadioButton jrbJava;



    public JRegister(){
        setLayout(new GridLayout(15,2));

        jtfAge = new JTextField();
        jtfName = new JTextField();
        jtfPassword = new JTextField();
        jtfMail = new JTextField();
        jtfPassword2 = new JTextField();
        jrbC = new JRadioButton("C");
        jrbJava = new JRadioButton("Java");


        add(new JLabel("User name"));
        add(jtfName);
        add(new JLabel("Mail"));
        add(jtfMail);
        add(new JLabel("Password"));
        add(jtfPassword);
        add(new JLabel("Repeat Password"));
        add(jtfPassword2);
        add(new JLabel("Age"));
        add(jtfAge);
        add(new JLabel("Programming languages:"));
        add(jrbC);
        add(jrbJava);



    }
}
