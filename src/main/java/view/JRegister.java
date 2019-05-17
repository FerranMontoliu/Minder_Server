package view;

import controller.ControllerRegister;

import javax.swing.*;
import javax.swing.border.TitledBorder;
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
    private JCheckBox jcbNoFilter;
    private GridBagConstraints gbc;
    private char ecoChar;
    private static String a;


    /**
     * Constructor del panell sense paràmetres
     */
    public JRegister(){
        createPreferences();
        mergeFields();
        createPasswordField();
        createPremiumOptions();
        createAgeFilters();
        createRegisterOption();
    }

    /**
     * Metode que inclou el boto de registre al panell
     */
    private void createRegisterOption() {
        jbRegister = new JButton("Sign-Up",new ImageIcon("icons/sign_up.png"));
        jpRegister.add(jbRegister);
        add(jpRegister);
    }

    /**
     * Metode que genera els camps de contrassenya i confirmacio de contrassenya, aixi com del button que permet visualitzar
     * el contingut d'aquests camps, que s'amaguen sota uns caracters predefinits pel sistema operatiu
     */
    private void createPasswordField() {
        //Guardar el eco del Password
        ecoChar = jpfPassword.getEchoChar();

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

    }

    /**
     * Metode que afegeix els camps principals d'informacio a escriure sota un panell enquadrat amb un TitleBorder
     */
    private void mergeFields() {

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridy = 1;
        TitledBorder tb = new TitledBorder("Signup");
        tb.setTitleColor(new Color(7,23,80));
        this.setBorder(tb);
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
    }

    /**
     * Metode que crea els botos de Premium i no premium (sel.leccionant per defecte no premium)
     */
    private void createPremiumOptions() {
        jrbPremium = new JRadioButton("Yes");
        jrbNoPremium = new JRadioButton("No");

        //Grup de radioButtons
        ButtonGroup bg = new ButtonGroup();
        bg.add(jrbNoPremium);
        bg.add(jrbPremium);
        jrbNoPremium.setSelected(true);

        JPanel jpRadioButton = new JPanel();
        jpRadioButton.add(jrbPremium);
        jpRadioButton.add(jrbNoPremium);

        JLabel jl3 = new JLabel("Premium Membership:");
        jl3.setHorizontalAlignment(JLabel.CENTER);
        add(jl3);
        add(jpRadioButton);
    }

    /**
     * Metode que crea les preferencies d'aquest panell
     */
    private void createPreferences() {
        Dimension d = new Dimension(10000,200);
        setLayout(new GridLayout(19,2));

        //Inicialitzar els elements

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
        a = "                                        ";
        jcbViewPassword = new JCheckBox();
        gbc = new GridBagConstraints();

    }

    /**
     * Metode que crea els dos JComboBox (desplegables) amb les etiquetes corresponents a edat minima i maxima (de 18 a 100 anys)
     */
    private void createAgeFilters() {
        //MINIMUM Age
        jcbMinAgeFilter = new JComboBox<>();
        jcbMinAgeFilter.setEditable(false);
        ((JLabel)jcbMinAgeFilter.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        jcbMinAgeFilter.setAlignmentX(SwingConstants.CENTER);
        jcbMinAgeFilter.setSize(20, 30);

        //omplim el comboBox amb les edats de 18 a 100
        for (int i = 18; i <= 100; ++i) {
            jcbMinAgeFilter.addItem(i);
        }

        //MAXIMUM Age
        jcbMaxAgeFilter = new JComboBox<>();
        jcbMaxAgeFilter.setEditable(false);
        ((JLabel)jcbMaxAgeFilter.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        jcbMaxAgeFilter.setAlignmentX(SwingConstants.CENTER);
        jcbMaxAgeFilter.setSize(20, 30);

        for (int i = 18; i <= 100; ++i) {
            jcbMaxAgeFilter.addItem(i);
        }

        JPanel jp2 = new JPanel();
        jp2.setLayout(new BoxLayout(jp2,0));

        JLabel jl = new JLabel("Minimum age:");
        jl.setHorizontalAlignment(JLabel.CENTER);
        add(jl);
        add(jcbMinAgeFilter);

        JLabel jl2 = new JLabel("Maximum age:");
        jl2.setHorizontalAlignment(JLabel.CENTER);
        add(jl2);
        add(jcbMaxAgeFilter);

        //No filter checkBox
        jcbNoFilter = new JCheckBox("I don't want any age filter");
        jcbNoFilter.setHorizontalAlignment(SwingConstants.CENTER);
        add(jcbNoFilter);
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

        resetJComboBox(); //Mètode de la classe

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

        jcbNoFilter.addActionListener(controller);
        jcbNoFilter.setActionCommand("NO FILTER");
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

    /**
     * Getter del CheckBox que indica si l'usuari vol utilitzar el filtre d'edat o no
     *
     * @return true en cas que l'usuari no vulgui cap filtre d'edat, false en cas de que haguem de fer cas dels valors
     * continguts als  ComboBox
     */
    public boolean isNoFilterChecked(){
        return jcbNoFilter.isSelected();
    }

    /**
     * Metode que habilita o desabilita el filtre d'edat (els dos desplegables) per tal que l'usuari pugui modificar-los
     *
     * @param b indica si volem habilitar (true) o desabilitar (false) l'edicio dels desplegables
     */
    public void enableFilter(boolean b) {
        jcbMinAgeFilter.setEnabled(b);
        jcbMaxAgeFilter.setEnabled(b);
    }
}