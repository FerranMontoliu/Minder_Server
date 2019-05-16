package view;
import model.entity.User;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Classe que implementa la taula del top-5 d'usuaris en funció del nombre de matches
 */
public class JTop extends JPanel {

    private JTable jTable;
    private DefaultTableModel defaultTableModel;
    private Vector columnNames;

    /**
     * Constructor del panell dels Top5 usuaris
     * @param users array d'usuaris ordenats per numero de matches
     */
    public JTop(ArrayList<User> users){

        //this.users = users;
        defaultTableModel = new DefaultTableModel();
        TableModelListener[] listeners = defaultTableModel.getTableModelListeners();


        // Column Names
        columnNames = new Vector();
        columnNames.addElement("Name");
        columnNames.addElement("Matches");


        // Data to be displayed in the JTable
        Vector<String> element = new Vector<>();
        Vector<Vector> data = new Vector<>();
        for(int i = 0; i< users.size() && i< 5;i++){
            element.addElement(users.get(i).getUsername());
            element.addElement(users.get(i).getUsername());
            defaultTableModel.addRow(element);

        }

        // Initializing the JTable
        jTable = new JTable(defaultTableModel);
        defaultTableModel.setColumnIdentifiers(columnNames);
        //defaultTableModel.fireTableDataChanged();

        jTable.setEnabled(false);
        add(new JScrollPane(jTable));

    }


    /**
     * Fucio que actualitza la taula del Top5
     * @param users array d'usuaris ordenats per ordre de matches
     */
    public void updateTop5(ArrayList<User> users){
        //this.users = users;

        defaultTableModel.setRowCount(0);
        Vector<String> element = new Vector<String>();
        Vector<Vector> data = new Vector<Vector>();

        for(int i = 0; i< users.size() && i< 5;i++){
            element = new Vector<String>();
            element.addElement(users.get(i).getUsername());
            element.addElement(Integer.toString(users.get(i).getMatches()));
            data.addElement(element);

        }
        defaultTableModel.setDataVector(data, columnNames);
        defaultTableModel.fireTableDataChanged();

    }
}
