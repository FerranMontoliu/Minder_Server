package view;

import controller.ControllerRegister;
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
    private JCheckBox jcbViewPassword;
    private JComboBox jcbMinAgeFilter;
    private JComboBox jcbMaxAgeFilter;
    private char ecoChar;



    public JRegister(){
        setLayout(new GridLayout(21,1));
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
        jcbViewPassword = new JCheckBox();



        //Grup de radioButtons
        ButtonGroup bg = new ButtonGroup();
        bg.add(jrbNoPremium);
        bg.add(jrbPremium);
        jrbNoPremium.setSelected(true);


        //Guardar el eco del Password
        ecoChar = jpfPassword.getEchoChar();

        //Afegir components al panell
        add(new JLabel("User name"));
        add(jtfName);
        add(new JLabel("Mail"));
        add(jtfMail);
        add(new JLabel("Password"));
        add(jpfPassword);
        add(new JLabel("Repeat Password"));
        add(jpfPassword2);
        JPanel jpViewPassword = new JPanel(new FlowLayout());
        jpViewPassword.add(jcbViewPassword);
        jpViewPassword.add(new JLabel("Mostra passwords"));
        add(jpViewPassword);
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

        createAgeFilters();
        //add (new JLabel("Minmum age filter"));
        //add (jcbMinAgeFilter);
        //add (new JLabel("Maximum age filter"));
        //add (jcbMaxAgeFilter);
        add (jbRegister);


    }

    public User getUser() {
        //TODO: update age filter parameters (last two nulls)
        User u = new User(jtfName.getText(), jtfAge.getText(), jrbPremium.isSelected(), jtfMail.getText(), new String (jpfPassword.getPassword()),jcbMinAgeFilter.getSelectedItem().toString(), jcbMaxAgeFilter.getSelectedItem().toString());

        return u;
    }

    private void createAgeFilters() {
        JPanel jpFilterAge = new JPanel();
        jpFilterAge.setLayout(new FlowLayout());

        JPanel jpBothFilters = new JPanel();
        jpBothFilters.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        //Minimum Age
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1.0f;
        constraints.weighty = 1.0f;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.fill = GridBagConstraints.WEST;

        JLabel jlMin = new JLabel("Minimum age:");
        jlMin.setHorizontalAlignment(SwingConstants.CENTER);
        jpBothFilters.add(jlMin, constraints);

        //Min age comboBox
        jcbMinAgeFilter = new JComboBox<>();
        jcbMinAgeFilter.setEditable(false);
        jcbMinAgeFilter.setPreferredSize(new Dimension(100,30));
        jcbMinAgeFilter.setMaximumSize(jcbMinAgeFilter.getPreferredSize());

        for (int i = 18; i <= 100; ++i) {
            jcbMinAgeFilter.addItem(i);
        }

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weightx = 1.0f;
        constraints.weighty = 1.0f;
        constraints.insets = new Insets(5, 2, 5, 5);
        constraints.fill = GridBagConstraints.CENTER;

        jpBothFilters.add(jcbMinAgeFilter, constraints);

        //Maximum Age
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 1.0f;
        constraints.weighty = 1.0f;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.fill = GridBagConstraints.WEST;

        JLabel jlMax = new JLabel("Maximum age:");
        jlMax.setHorizontalAlignment(SwingConstants.CENTER);
        jpBothFilters.add(jlMax, constraints);

        //Max age comboBox

        jcbMaxAgeFilter = new JComboBox<>();
        jcbMaxAgeFilter.setEditable(false);
        jcbMaxAgeFilter.setPreferredSize(new Dimension(100,30));
        jcbMaxAgeFilter.setMaximumSize(jcbMaxAgeFilter.getPreferredSize());

        for (int i = 18; i <= 100; ++i) {
            jcbMaxAgeFilter.addItem(i);
        }

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.weightx = 1.0f;
        constraints.weighty = 1.0f;
        constraints.insets = new Insets(5, 5, 5, 2);
        constraints.fill = GridBagConstraints.CENTER;

        jpBothFilters.add(jcbMaxAgeFilter, constraints);

        jpFilterAge.add(jpBothFilters);

        add(jpFilterAge);
        //add(Box.createVerticalStrut(10));
    }

    public void removeRegister(){
        jtfAge.setText("");
        jtfMail.setText("");
        jtfName.setText("");
        jpfPassword.setText("");
        jpfPassword2.setText("");

        jrbNoPremium.setSelected(true);

        jcbC.setSelected(false);
        jcbJava.setSelected(false);

    }




    public void registerController(ControllerRegister controller) {
        jbRegister.addActionListener(controller);
        jbRegister.setActionCommand("REGISTER");

        jcbViewPassword.addActionListener(controller);
        jcbViewPassword.setActionCommand("PASSWORD");
    }

    public void changeViewPassword() {
        if(jcbViewPassword.isSelected()){

            jpfPassword.setEchoChar((char) 0);
            jpfPassword2.setEchoChar((char) 0);

        }else{

            jpfPassword.setEchoChar((ecoChar));
            jpfPassword2.setEchoChar((ecoChar));

        }
    }
}