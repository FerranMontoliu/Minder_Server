package view;
import model.entity.User;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;


public class JTop extends JPanel {
    private JTable jTable;
    private User[] users;
    private DefaultTableModel defaultTableModel;
    private Vector columnNames;

    public JTop(User[] users){

        this.users = users;
        defaultTableModel = new DefaultTableModel();
        TableModelListener[] listeners = defaultTableModel.getTableModelListeners();




        // Column Names
        columnNames = new Vector();
        columnNames.addElement("Name");
        columnNames.addElement("Password");


        // Data to be displayed in the JTable

        Vector<String> element = new Vector<String>();
        Vector<Vector> data = new Vector<Vector>();
        for(int i = 0; i< users.length;i++){
            element.addElement(users[i].getUsername());
            element.addElement("Password" + i);
            defaultTableModel.addRow(element);

        }

        // Initializing the JTable
        jTable = new JTable(defaultTableModel);
        defaultTableModel.setColumnIdentifiers(columnNames);
        //defaultTableModel.fireTableDataChanged();

        jTable.setEnabled(false);
        add(new JScrollPane(jTable));

        users[0] = new User("Hola", "18", true, "test@example.com", "Password1", "19", "20");
        users[1] = new User("Anna", "18", true, "test@example.com", "Password1", "31", "40");

        update(users);
    }


    public void update(User[] users){
        this.users = users;

        defaultTableModel.setRowCount(0);
        Vector<String> element = new Vector<String>();
        Vector<Vector> data = new Vector<Vector>();

        for(int i = 0; i< users.length;i++){
            System.out.println(users[i].getUsername());
            element = new Vector<String>();
            element.addElement(users[i].getUsername());
            element.addElement("Password " + i);
            data.addElement(element);

        }
        defaultTableModel.setDataVector(data, columnNames);
        defaultTableModel.fireTableDataChanged();

    }
}
