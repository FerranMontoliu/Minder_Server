package view;
import model.User;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class JTop extends JPanel {
    private JTable jTable;
    private User[] users;
    private DefaultTableModel defaultTableModel;

    public JTop(User[] users){

        this.users = users;
        defaultTableModel = new DefaultTableModel();
        TableModelListener[] listeners = defaultTableModel.getTableModelListeners();


        // Data to be displayed in the JTable

        String[][] data = new String[users.length][];
        for(int i = 0; i< users.length;i++){
            data[i] = new String[4];
            data[i][0] = users[i].getUsername();
            defaultTableModel.addRow(data[i]);

        }

        // Column Names
        String[] columnNames = new String[] { "Name", "PassWord"};

        // Initializing the JTable
        jTable = new JTable(data, columnNames);
        //jTable.setModel(defaultTableModel);

        for (TableModelListener l : listeners) {
            defaultTableModel.addTableModelListener(l);
        }

        jTable.setEnabled(false);
        add(new JScrollPane(jTable));

        users[0] = new User("Hola", "18", true, "test@example.com", "Password1", "Password1");
       // update(users);
    }


    public void update(User[] users){
        this.users = users;

        defaultTableModel.setRowCount(0);

        String[][] data = new String[users.length][];

        for(int i = 0; i< users.length;i++){
            data[i] = new String[1];
            data[i][0] = users[i].getUsername();

            defaultTableModel.addRow(data[i]);
        }

        defaultTableModel.fireTableDataChanged();

    }
}
