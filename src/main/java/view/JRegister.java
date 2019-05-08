package view;

import controller.ControllerRegister;
import model.entity.User;

import javax.swing.*;
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
    private JPanel jpRegister;
    private JButton jbRegister;
    private JCheckBox jcbViewPassword;
    private JComboBox jcbMinAgeFilter;
    private JComboBox jcbMaxAgeFilter;
    private char ecoChar;



    public JRegister(){
        Dimension d = new Dimension(10000,200);
        setLayout(new GridLayout(19,2));

        //Inicialitzar els elements
        GridBagConstraints gbc = new GridBagConstraints();
        jpRegister = new JPanel(new GridBagLayout());
        jtfAge = new JTextField();
        jtfAge.setPreferredSize(d);
        jtfName = new JTextField();
        jtfName.setPreferredSize(d);
        jpfPassword = new JPasswordField();
        jpfPassword.setPreferredSize(d);
        jtfMail = new JTextField();
        jtfMail.setPreferredSize(d);
        jpfPassword2 = new JPasswordField();
        jpfPassword2.setPreferredSize(d);
        jcbC = new JCheckBox("C    ");
        jcbJava = new JCheckBox("Java");
        jrbPremium = new JRadioButton("Yes");
        jrbNoPremium = new JRadioButton("No");
        jbRegister = new JButton("Sign-Up",new ImageIcon("icons/sign_up.png"));
        jcbViewPassword = new JCheckBox();

        //Grup de radioButtons
        ButtonGroup bg = new ButtonGroup();
        bg.add(jrbNoPremium);
        bg.add(jrbPremium);
        jrbNoPremium.setSelected(true);


        //Guardar el eco del Password
        ecoChar = jpfPassword.getEchoChar();

        //Afegir components al panell
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("--------------------------------------------- Sign Up ---------------------------------------------"));
        add(new JLabel("Username"),gbc);
        add(jtfName);
        add(new JLabel("Age"));
        add(jtfAge);
        add(new JLabel("Mail"),gbc);
        add(jtfMail);
        add(new JLabel("Password"),gbc);
        add(jpfPassword);
        add(new JLabel("Repeat Password"),gbc);
        add(jpfPassword2);

        JPanel jpViewPassword = new JPanel(new GridBagLayout());
        gbc.gridy = 0;
        gbc.gridy = 1;
        gbc.gridx = 0;
        jpViewPassword.add(jcbViewPassword,gbc);
        gbc.gridy = 1;
        gbc.gridx = 1;
        JLabel gg = new JLabel("Show Password");
        jpViewPassword.add(gg,gbc);

        JLabel gg4 = new JLabel("");
        jpViewPassword.add(gg4,gbc);
        gbc.gridy = 2;

        jpViewPassword.add(gg4,gbc);
        add(jpViewPassword);
        JPanel jp = new JPanel(new GridBagLayout());
        jp.setMinimumSize(new Dimension(1000,100));
        JPanel jp2 = new JPanel(new GridBagLayout());
        String a = "                    ";


        JPanel jpRadioButton = new JPanel();
        jpRadioButton.add(jrbPremium);
        jpRadioButton.add(jrbNoPremium);
        add (new JLabel(a+a+a+a+"  Premium Membership"));
        add(jpRadioButton);
        this.createAgeFilters();

        jpRegister.add(jbRegister);
        add(jpRegister);


    }

    public User getUser() {

        //User u = new User(jtfName.getText(), jtfAge.getText(), jrbPremium.isSelected(), jtfMail.getText(), new String (jpfPassword.getPassword()));

        return null;
    }

    //TODO: incloure desplegables per al filtre d'edat: aqui hi ha el codi que s'ha sobreescrit amb el commit de l'Agustí,
    //TODO: que s'hauria d'adequar al disseny actual
    /**
     * Metode que crea els dos JComboBox (desplegables) amb les etiquetes corresponents a edat minima i maxima (de 18 a 100 anys)
     */
    private void createAgeFilters() {
        //MINIMUM AGE
        //Min age comboBox
        jcbMinAgeFilter = new JComboBox<>();
        jcbMinAgeFilter.setEditable(false);
        jcbMinAgeFilter.setPreferredSize(new Dimension(100, 30));
        jcbMinAgeFilter.setMaximumSize(jcbMinAgeFilter.getPreferredSize());
        for (int i = 18; i <= 100; ++i) {
            jcbMinAgeFilter.addItem(i);
        }
        add(jcbMinAgeFilter);
        //MAXIMUM AGE
        JLabel a = new JLabel("                                 ");
        //Max age comboBox
        jcbMaxAgeFilter = new JComboBox<>();
        jcbMaxAgeFilter.setEditable(false);
        jcbMaxAgeFilter.setPreferredSize(new Dimension(100, 30));
        jcbMaxAgeFilter.setMaximumSize(jcbMaxAgeFilter.getPreferredSize());
        for (int i = 18; i <= 100; ++i) {
            jcbMaxAgeFilter.addItem(i);
        }
        //add(jcbMaxAgeFilter);
        JPanel jp2 = new JPanel();

        jp2.setLayout(new BoxLayout(jp2,0));
        jp2.add(new JLabel("                                                                                      "));
        jp2.add(jcbMinAgeFilter);
        JPanel jp = new JPanel();

        jp.setLayout(new BoxLayout(jp,0));
        jp.add(new JLabel("                                                                                      "));
        jp.add(jcbMaxAgeFilter);
        add(new JLabel("                                                                                       Minimum age: "));
        add(jp2);
        add(new JLabel("                                                                                       Maximum age:"));
        add(jp);
    }

    /**
     * Funcio que esborra el contingut dels JTextFields del panell del registre
     */
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
    public String getUsername() {
        return jtfName.getText();
    }
    public String getAge() {
        return jtfAge.getText();
    }
    public String getMail() {
        return jtfMail.getText();
    }
    public String getPassword() {
        return String.valueOf(jpfPassword.getPassword());
    }
    public void showMessage(boolean ok) {
        if(ok) {
            JOptionPane.showMessageDialog(this, "Sign-Up Successful!", "Minder", JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(this, "At least one field(s) contain errors!", "Minder", JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * Funcio que enregistra el controlador als elements de la finestra
     * @param controller controlador
     */
    public void registerController(ControllerRegister controller) {
        jbRegister.addActionListener(controller);
        jbRegister.setActionCommand("REGISTER");

        jcbViewPassword.addActionListener(controller);
        jcbViewPassword.setActionCommand("PASSWORD");
    }

    /**
     * Funcio que visualitza i/o amaga la contrassenya
     */
    public void changeViewPassword() {
        if(jcbViewPassword.isSelected()){

            jpfPassword.setEchoChar((char) 0);
            jpfPassword2.setEchoChar((char) 0);

        }else{

            jpfPassword.setEchoChar((ecoChar));
            jpfPassword2.setEchoChar((ecoChar));

        }
    }

    /**
     * Funcio que retorna la contrassenya de confirmacio
     * @return
     */
    public String getConfirmPassword() {
        return String.valueOf(jpfPassword2.getPassword());
    }
}