package view;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class JTop extends JPanel {
    private JTable scrTbl;

    public JTop(){
        int  nmbrRows;

        setLayout(null);
        Vector colHdrs;

        //create column headers

        colHdrs = new Vector(10);
        colHdrs.addElement(new String("Ticker"));

        // more statements like the above to establish all col. titles

        nmbrRows = 5;
        DefaultTableModel tblModel = new DefaultTableModel(nmbrRows, colHdrs.size());
        tblModel.setColumnIdentifiers(colHdrs);

        scrTbl = new JTable(tblModel);
        scrTbl.setBounds(25, 50, 950, 600);
        scrTbl.setRowHeight(23);
        add(scrTbl);


    }
}
