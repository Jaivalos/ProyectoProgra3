
package Controllers;

import Models.Medidas;
import Models.MedidasDao;
import Models.Tables;
import Views.PanelAdmin;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class MedidasControllers implements ActionListener, MouseListener, KeyListener{

    private final Medidas med;
    private final MedidasDao medDao;
    private final PanelAdmin views;
    DefaultTableModel modelo = new DefaultTableModel();
    public MedidasControllers(Medidas med, MedidasDao medDao, PanelAdmin views) {
        this.med = med;
        this.medDao = medDao;
        this.views = views;
        this.views.btnRegistrarMedida.addActionListener(this);
        this.views.btnNuevomedida.addActionListener(this);
        this.views.btnModificarMedida.addActionListener(this);
        this.views.TableMedida.addMouseListener(this);
        this.views.JlabelMedidas.addMouseListener(this);
        this.views.jMenuEliminarMed.addActionListener(this);
        this.views.jMenuReingresarMed.addActionListener(this);
        listarMedidas();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btnRegistrarMedida) {
            if (views.txtNombremedida.getText().equals("") || views.txtnombrecortomed.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todo los campos son obligatorios"); 
            }else{
                med.setNombre(views.txtNombremedida.getText());
                med.setNombre_corto(views.txtnombrecortomed.getText());
                if (medDao.registrar(med)) {
                    Nuevo();
                    limpiarTable();
                    listarMedidas();
                    JOptionPane.showMessageDialog(null, "Medida Registrado"); 
                }else{
                    JOptionPane.showMessageDialog(null, "La medida ya existe");
                }
            }
        }else if (e.getSource() == views.btnModificarMedida){
            if (views.txtNombremedida.getText().equals("") || views.txtnombrecortomed.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todo los campos son obligatorios"); 
            }else{
                med.setNombre(views.txtNombremedida.getText());
                med.setNombre_corto(views.txtnombrecortomed.getText());
                med.setId(Integer.parseInt(views.txtIdMed.getText()));
                if (medDao.modificar(med)) {
                    Nuevo();
                    limpiarTable();
                    listarMedidas();
                    JOptionPane.showMessageDialog(null, "Medida Modificado"); 
                }
            }
        }else if (e.getSource() == views.jMenuEliminarMed){
            if (views.txtIdMed.getText().equals("") || views.txtIdMed.getText() == null) {
                JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar"); 
            }else{
                int pregunta = JOptionPane.showConfirmDialog(null, "Estas seguro de eliminar", "Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (pregunta == 0) {
                    int id = Integer.parseInt(views.txtIdMed.getText());
                    if (medDao.accion("Inactivo", id)) {
                        Nuevo();
                        limpiarTable();
                        listarMedidas();
                        JOptionPane.showMessageDialog(null, "Medida Eliminado"); 
                    }
                }
            }
        }else if (e.getSource() == views.jMenuReingresarMed){
            if (views.txtIdMed.getText().equals("") || views.txtIdMed.getText() == null) {
                JOptionPane.showMessageDialog(null, "Seleccione una fila para reingresar"); 
            }else{
                int pregunta = JOptionPane.showConfirmDialog(null, "Estas seguro de reingresar", "Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (pregunta == 0) {
                    int id = Integer.parseInt(views.txtIdMed.getText());
                    if (medDao.accion("Activo", id)) {
                        Nuevo();
                        limpiarTable();
                        listarMedidas();
                        JOptionPane.showMessageDialog(null, "Medida Reingresado"); 
                    }
                }
            }
        }else if(e.getSource() == views.btnNuevomedida){
            Nuevo();
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.TableMedida) {
            int fila = views.TableMedida.rowAtPoint(e.getPoint());
            views.txtIdMed.setText(views.TableMedida.getValueAt(fila, 0).toString());
            views.txtNombremedida.setText(views.TableMedida.getValueAt(fila, 1).toString());
            views.txtnombrecortomed.setText(views.TableMedida.getValueAt(fila, 2).toString());
        }else if(e.getSource() == views.JlabelMedidas){
            views.jTabbedPane1.setSelectedIndex(5);
            limpiarTable();
            listarMedidas();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    private void Nuevo() {
        views.txtIdMed.setText("");
        views.txtNombremedida.setText("");
        views.txtnombrecortomed.setText("");
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void listarMedidas() {
        Tables color = new Tables();
        views.TableMedida.setDefaultRenderer(views.TableMedida.getColumnClass(0), color);
        List<Medidas> lista = medDao.ListarMed();
        modelo = (DefaultTableModel) views.TableMedida.getModel();
        Object[] ob = new Object[4];
        for (int i = 0; i < lista.size(); i++) {
            ob[0] = lista.get(i).getId();
            ob[1] = lista.get(i).getNombre();
            ob[2] = lista.get(i).getNombre_corto();
            ob[3] = lista.get(i).getEstado();
            modelo.addRow(ob);
        }
        views.TableMedida.setModel(modelo);
        JTableHeader header = views.TableMedida.getTableHeader();
        header.setOpaque(false);
        header.setBackground(Color.blue);
        header.setForeground(Color.white);
    }

    public void limpiarTable() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

}
