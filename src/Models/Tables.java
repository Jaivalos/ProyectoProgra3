
package Models;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class Tables extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int row, int co1) {
        super.getTableCellRendererComponent(jtable, o, bln, bln1, row, co1);
        if (jtable.getValueAt(row, co1).toString().equals("inactivo")) {
            setBackground(Color.red);
            setForeground(Color.white);
        }else{
            setBackground(Color.white);
            setForeground(Color.BLACK);
        }
        return this;
    }
}
