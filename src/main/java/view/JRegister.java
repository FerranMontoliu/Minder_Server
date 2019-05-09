package view;

import controller.ControllerRegister;

import javax.swing.*;
import java.awt.*;

/**
 * Classe que implementa la vista del servidor en la que es pot fer el signup d'un nou usuari
 */
public class JRegister extends JPanel {

    private JTextField jtfName;
    private JPasswordField jpfPassword;
    private JTextField jtfAge;
    private JTextField jtfMail;
    private JPasswordField jpfPassword2;
    private JRadioButton jrbPremium;
    private JRadioButton jrbNoPremium;
    private JPanel jpRegister;
    private JButton jbRegister;
    private JCheckBox jcbViewPassword;
    private JComboBox jcbMinAgeFilter;
    private JComboBox jcbMaxAgeFilter;
    private char ecoChar;
    private static String a;


    /**
     * Constructor del panell sense paràmetres
     */
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
        jrbPremium = new JRadioButton("Yes");
        jrbNoPremium = new JRadioButton("No");
        jbRegister = new JButton("Sign-Up",new ImageIcon("icons/sign_up.png"));
        a = "                                        ";
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
        add(new JLabel("Confirm Password"),gbc);
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

        JPanel jpRadioButton = new JPanel();
        jpRadioButton.add(jrbPremium);
        jpRadioButton.add(jrbNoPremium);
        add (new JLabel(a+a+"  Premium Membership"));
        add(jpRadioButton);
        this.createAgeFilters();

        jpRegister.add(jbRegister);
        add(jpRegister);


    }

    /**
     * Metode que crea els dos JComboBox (desplegables) amb les etiquetes corresponents a edat minima i maxima (de 18 a 100 anys)
     */
    private void createAgeFilters() {
        //MINIMUM Age
        jcbMinAgeFilter = new JComboBox<>();
        jcbMinAgeFilter.setEditable(false);
        jcbMinAgeFilter.setPreferredSize(new Dimension(100, 30));
        jcbMinAgeFilter.setMaximumSize(jcbMinAgeFilter.getPreferredSize());
        for (int i = 18; i <= 100; ++i) {
            jcbMinAgeFilter.addItem(i);
        }
        add(jcbMinAgeFilter);

        //MAXIMUM Age
        jcbMaxAgeFilter = new JComboBox<>();
        jcbMaxAgeFilter.setEditable(false);
        jcbMaxAgeFilter.setPreferredSize(new Dimension(100, 30));
        jcbMaxAgeFilter.setMaximumSize(jcbMaxAgeFilter.getPreferredSize());

        for (int i = 18; i <= 100; ++i) {
            jcbMaxAgeFilter.addItem(i);
        }

        JPanel jp2 = new JPanel();

        jp2.setLayout(new BoxLayout(jp2,0));
        jp2.add(new JLabel("                                                                                      "));
        jp2.add(jcbMinAgeFilter);
        JPanel jp = new JPanel();

        jp.setLayout(new BoxLayout(jp,0));
        jp.add(new JLabel("                                                                                      "));
        jp.add(jcbMaxAgeFilter);
        add(new JLabel(a+a+"       Minimum age: "));
        add(jp2);
        add(new JLabel(a+a+"       Maximum age:"));
        add(jp);
    }

    /**
     * Mètode que neteja els camps del Login
     */
    public void removeRegister(){
        jtfAge.setText("");
        jtfMail.setText("");
        jtfName.setText("");
        jpfPassword.setText("");
        jpfPassword2.setText("");
        jrbNoPremium.setSelected(true);

        this.resetJComboBox(); //Mètode de la classe

    }

    /**
     * Mètode que retorna el nom del username
     * @return: String Username
     */
    public String getUsername() {
        return jtfName.getText();
    }

    /**
     * Mètode que retorna l'edat de l'usuari
     * @return: String Edat
     */
    public String getAge() {
        return jtfAge.getText();
    }

    /**
     * Mètode que retorna el mail
     * @return: String mail
     */
    public String getMail() {
        return jtfMail.getText();
    }

    /**
     * Mètode que retorna la password
     * @return
     */
    public String getPassword() {
        return String.valueOf(jpfPassword.getPassword());
    }

    /**
     * Mètode que mostra dos OptionPane en funció de si el login és correcte
     * @param ok: paràmetre que indica si el sign up és correcte
     */
    public void showMessage(boolean ok) {
        if(ok) {
            JOptionPane.showMessageDialog(this, "Sign-Up Successful!", "Minder", JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(this, "At least one field(s) contain errors!", "Minder", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Mètode que retorna l'edat mínima de la JComboBox
     * @return: Int edat min
     */
    public int getMinimumAge() {
        return (int) jcbMinAgeFilter.getSelectedItem();
    }

    /**
     * Mètode que retorna l'edat màxima de la JComboBox
     * @return: Int edat max
     */
    public int getMaximumAge() {
        return (int) jcbMaxAgeFilter.getSelectedItem();
    }

    /**
     * Mètode que reseteja les combobox i la posa per defecte a 18/18
     */
    public void resetJComboBox() {
        jcbMinAgeFilter.setSelectedIndex(0);
        jcbMaxAgeFilter.setSelectedIndex(0);
    }

    /**
     * Mètode que comprova si l'usuari vol ser premium o no
     * @return
     */
    public boolean isPremium() {
        if(jrbPremium.getText().equals("Yes")) {
            return true;
        }
        return false;
    }

    /**
     * Mètode que registra la vista al controlador
     * @param controller: Controlador de la vista del server
     */
    public void registerController(ControllerRegister controller) {
        jbRegister.addActionListener(controller);
        jbRegister.setActionCommand("REGISTER");

        jcbViewPassword.addActionListener(controller);
        jcbViewPassword.setActionCommand("PASSWORD");
    }

    /**
     * Mètode que canvia d'amagar-se a mostrar-se la password i viceversa
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
     * @return retorna la contrassenya
     */
    public String getConfirmPassword() {
        return String.valueOf(jpfPassword2.getPassword());
    }
}